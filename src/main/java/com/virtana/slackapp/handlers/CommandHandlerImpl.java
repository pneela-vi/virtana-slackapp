package com.virtana.slackapp.handlers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.virtana.slackapp.utils.SlackUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CommandHandlerImpl implements CommandHandler{
    private static final String IPM_URL = "https://dev.cloud.virtana.com/cloudcapi/applications?timerange=1";

    private static final String USER_BY_ORG_URL = "https://dev.cloud.virtana.com/auth/users?org_id=c1b1bee2-4171-4f9e-b778-0093ca6df6d2&offset=0&limit=";
    private static final String RIGHT_SIZING_URL = "https://dev.cloud.virtana.com/api/capacity-analysis/analyze/c1b1bee2-4171-4f9e-b778-0093ca6df6d2";
    private static final String IDLE_RESOURCES_URL = "https://dev.cloud.virtana.com/api/capacity-analysis/analyze/c1b1bee2-4171-4f9e-b778-0093ca6df6d2";

    private static final String xIdToken = "x-id-token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjdXN0b206YWN0aXZlT3JnYW5pemF0aW9uSWQiOiJjMWIxYmVlMi00MTcxLTRmOWUtYjc3OC0wMDkzY2E2ZGY2ZDIiLCJjdXN0b206YWN0aXZlT3JnYW5pemF0aW9uTmFtZSI6IlZpcnRhbmEiLCJjdXN0b206YXNzdW1lcklkIjoiIiwiY3VzdG9tOmVtYWlsIjoiaGVsZW4uam9obkB2aXJ0YW5hLmNvbSIsImN1c3RvbTpnbG9iYWxSb2xlcyI6WyJHbG9iYWwtQWRtaW5zIiwiR2xvYmFsLVJlYWRPbmx5Il0sImN1c3RvbTpuYW1lIjoiSGVsZW4gSm9obiIsImN1c3RvbTpvcmdhbml6YXRpb25zIjpbeyJpZCI6ImMxYjFiZWUyLTQxNzEtNGY5ZS1iNzc4LTAwOTNjYTZkZjZkMiIsInJvbGVzIjpbeyJpZCI6NDkzM31dfV0sImN1c3RvbTpwYXJlbnRPcmdJZCI6IjAwMDAwMDAwLTAwMDAtMDAwMC0wMDAwLTAwMDAwMDAwMDAwMCIsImV4cCI6MTY4MTMzNzczMCwiaWF0IjoxNjc4NzQ1NzMwLCJpc3MiOiJhdXRoLXNlcnZpY2UiLCJuYmYiOjE2Nzg3NDU3MzAsInN1YiI6IjgxMTJkMGYzLTk0ODItNGVhZC1hOTYxLTU5OWVhYmRkNjJjYyJ9.drRXkQjHnLKH0AWDieho_GuW5ZZfPl9IeSpdQWIdeRA";
    private final HttpClient client = HttpClient.newBuilder().build();

    @Override
    public String ipmDashboard() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IPM_URL))
                .header("CONTENT_TYPE", "application/json")
                .header("COOKIE", xIdToken)
                .GET()
                .build();

        HttpResponse<String> response = client.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body().toString();
    }

    @Override
    public String usersByOrg(String limit) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USER_BY_ORG_URL+limit))
                .header("CONTENT_TYPE", "application/json")
                .header("COOKIE", xIdToken)
                .GET()
                .build();

        HttpResponse<String> response = client.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String responseData = response.body().toString();
        JsonObject convertedObject = new Gson().fromJson(responseData,JsonObject.class);
        JsonArray usersJsonArray = convertedObject.get("users").getAsJsonArray();
        SlackUtils su = new SlackUtils();
        String finalResponse = su.getTableResponseForUsers(usersJsonArray);
        return finalResponse;
    }

    @Override
    public String idleResourcesDashboard() throws IOException, InterruptedException {
        String requestBody = "{\"analysisType\":\"IDLE_RESOURCES\",\"rollup\":\"P7D\",\"savingsPeriod\":\"month\",\"perfStatistic\":\"p95\",\"startDate\":\"2023-03-13\",\"endDate\":\"2023-03-13\",\"elementFilter\":{\"tagsInclude\":[],\"tagsExclude\":[],\"attributesInclude\":[],\"attributesMatchType\":\"ANY\",\"attributesExclude\":[]},\"policies\":[{\"id\":7296,\"orgId\":\"c1b1bee2-4171-4f9e-b778-0093ca6df6d2\",\"analysisType\":\"IDLE_RESOURCES\",\"order\":1,\"name\":\"testHector\",\"elementFilter\":{\"tagsInclude\":{},\"tagsIncludeMatchType\":\"ANY\",\"tagsExclude\":{},\"tagsExcludeMatchType\":\"ANY\",\"attributesInclude\":{\"Account\":[\"407724209249\",\"0d9c4aa6-d865-4303-a3b2-fdab3387c5a7\"]},\"attributesIncludeMatchType\":\"ANY\",\"attributesExclude\":{},\"attributesExcludeMatchType\":\"ANY\"},\"perfStatistic\":\"p95\",\"perfStatisticContainer\":\"p95\",\"constraints\":[\"{\\\"type\\\":\\\"azureDiskAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"ebsAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"ebsOnStoppedEc2Aggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"elbAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"eipAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureDiskIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"ebsIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"ebsOnStoppedEc2IdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"elbIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"eipIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azurePublicIPAggressiveness\\\",\\\"monthlyCostOver\\\":1}\",\"{\\\"type\\\":\\\"azurePublicIPIdleDays\\\",\\\"idleDays\\\":1,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azureAppGatewayAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureAppGatewayIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azureLoadBalancerAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureLoadBalancerIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\"],\"changeManagerType\":null,\"requestCreationMethod\":\"M\"},{\"id\":8497,\"orgId\":\"c1b1bee2-4171-4f9e-b778-0093ca6df6d2\",\"analysisType\":\"IDLE_RESOURCES\",\"order\":2,\"name\":\"test123\",\"elementFilter\":{\"tagsInclude\":{},\"tagsIncludeMatchType\":\"ANY\",\"tagsExclude\":{},\"tagsExcludeMatchType\":\"ANY\",\"attributesInclude\":{\"Account\":[\"647724765282\",\"407724209249\"]},\"attributesIncludeMatchType\":\"ANY\",\"attributesExclude\":{},\"attributesExcludeMatchType\":\"ANY\"},\"perfStatistic\":\"p95\",\"perfStatisticContainer\":\"p95\",\"constraints\":[\"{\\\"type\\\":\\\"azureDiskAggressiveness\\\",\\\"monthlyCostOver\\\":1}\",\"{\\\"type\\\":\\\"ebsAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"ebsOnStoppedEc2Aggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"elbAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"eipAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureDiskIdleDays\\\",\\\"idleDays\\\":1,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"ebsIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"ebsOnStoppedEc2IdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"elbIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"eipIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azurePublicIPAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azurePublicIPIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azureAppGatewayAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureAppGatewayIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azureLoadBalancerAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureLoadBalancerIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\"],\"changeManagerType\":null,\"requestCreationMethod\":\"M\"},{\"id\":8478,\"orgId\":\"c1b1bee2-4171-4f9e-b778-0093ca6df6d2\",\"analysisType\":\"IDLE_RESOURCES\",\"order\":3,\"name\":\"btanisits\",\"elementFilter\":{\"tagsInclude\":{},\"tagsIncludeMatchType\":\"ANY\",\"tagsExclude\":{},\"tagsExcludeMatchType\":\"ANY\",\"attributesInclude\":{\"Account\":[\"647724765282\",\"407724209249\"]},\"attributesIncludeMatchType\":\"ANY\",\"attributesExclude\":{},\"attributesExcludeMatchType\":\"ANY\"},\"perfStatistic\":\"p95\",\"perfStatisticContainer\":\"p95\",\"constraints\":[\"{\\\"type\\\":\\\"azureDiskAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"ebsAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"ebsOnStoppedEc2Aggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"elbAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"eipAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureDiskIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"ebsIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"ebsOnStoppedEc2IdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"elbIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"eipIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azurePublicIPAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azurePublicIPIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azureAppGatewayAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureAppGatewayIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azureLoadBalancerAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureLoadBalancerIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\"],\"changeManagerType\":null,\"requestCreationMethod\":\"M\"},{\"id\":7996,\"orgId\":\"c1b1bee2-4171-4f9e-b778-0093ca6df6d2\",\"analysisType\":\"IDLE_RESOURCES\",\"order\":4,\"name\":\"Cypress_idle_resource\",\"elementFilter\":{\"tagsInclude\":{},\"tagsIncludeMatchType\":\"ANY\",\"tagsExclude\":{},\"tagsExcludeMatchType\":\"ANY\",\"attributesInclude\":{\"Account\":[\"502379301106\",\"397111407519\",\"407724209249\",\"0d9c4aa6-d865-4303-a3b2-fdab3387c5a7\"]},\"attributesIncludeMatchType\":\"ANY\",\"attributesExclude\":{},\"attributesExcludeMatchType\":\"ANY\"},\"perfStatistic\":\"p95\",\"perfStatisticContainer\":\"p95\",\"constraints\":[\"{\\\"type\\\":\\\"azureDiskAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"ebsAggressiveness\\\",\\\"monthlyCostOver\\\":-10}\",\"{\\\"type\\\":\\\"ebsOnStoppedEc2Aggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"elbAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"eipAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureDiskIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"ebsIdleDays\\\",\\\"idleDays\\\":-10,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"ebsOnStoppedEc2IdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"elbIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"eipIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azurePublicIPAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azurePublicIPIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azureAppGatewayAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureAppGatewayIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azureLoadBalancerAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureLoadBalancerIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\"],\"changeManagerType\":null,\"requestCreationMethod\":\"M\"},{\"id\":6827,\"orgId\":\"c1b1bee2-4171-4f9e-b778-0093ca6df6d2\",\"analysisType\":\"IDLE_RESOURCES\",\"order\":5,\"name\":\"test009\",\"elementFilter\":{\"tagsInclude\":{},\"tagsIncludeMatchType\":\"ANY\",\"tagsExclude\":{},\"tagsExcludeMatchType\":\"ANY\",\"attributesInclude\":{\"Account\":[\"502379301106\",\"397111407519\",\"407724209249\",\"0d9c4aa6-d865-4303-a3b2-fdab3387c5a7\"]},\"attributesIncludeMatchType\":\"ANY\",\"attributesExclude\":{},\"attributesExcludeMatchType\":\"ANY\"},\"perfStatistic\":\"p95\",\"perfStatisticContainer\":\"p95\",\"constraints\":[\"{\\\"type\\\":\\\"azureDiskAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"ebsAggressiveness\\\",\\\"monthlyCostOver\\\":2}\",\"{\\\"type\\\":\\\"ebsOnStoppedEc2Aggressiveness\\\",\\\"monthlyCostOver\\\":2}\",\"{\\\"type\\\":\\\"elbAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"eipAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureDiskIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"ebsIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"ebsOnStoppedEc2IdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"elbIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"eipIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azurePublicIPAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azurePublicIPIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azureAppGatewayAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureAppGatewayIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azureLoadBalancerAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureLoadBalancerIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\"],\"changeManagerType\":null,\"requestCreationMethod\":\"M\"},{\"id\":7995,\"orgId\":\"c1b1bee2-4171-4f9e-b778-0093ca6df6d2\",\"analysisType\":\"IDLE_RESOURCES\",\"order\":6,\"name\":\"Cypress_idle_resource\",\"elementFilter\":{\"tagsInclude\":{},\"tagsIncludeMatchType\":\"ANY\",\"tagsExclude\":{},\"tagsExcludeMatchType\":\"ANY\",\"attributesInclude\":{\"Account\":[\"502379301106\",\"397111407519\",\"407724209249\",\"0d9c4aa6-d865-4303-a3b2-fdab3387c5a7\"]},\"attributesIncludeMatchType\":\"ANY\",\"attributesExclude\":{},\"attributesExcludeMatchType\":\"ANY\"},\"perfStatistic\":\"p95\",\"perfStatisticContainer\":\"p95\",\"constraints\":[\"{\\\"type\\\":\\\"azureDiskAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"ebsAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"ebsOnStoppedEc2Aggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"elbAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"eipAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureDiskIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"ebsIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"ebsOnStoppedEc2IdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"elbIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"eipIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azurePublicIPAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azurePublicIPIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azureAppGatewayAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureAppGatewayIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azureLoadBalancerAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureLoadBalancerIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\"],\"changeManagerType\":null,\"requestCreationMethod\":\"M\"},{\"id\":7989,\"orgId\":\"c1b1bee2-4171-4f9e-b778-0093ca6df6d2\",\"analysisType\":\"IDLE_RESOURCES\",\"order\":7,\"name\":\"testt\",\"elementFilter\":{\"tagsInclude\":{},\"tagsIncludeMatchType\":\"ANY\",\"tagsExclude\":{},\"tagsExcludeMatchType\":\"ANY\",\"attributesInclude\":{\"Account\":[\"502379301106\",\"397111407519\",\"407724209249\"]},\"attributesIncludeMatchType\":\"ANY\",\"attributesExclude\":{},\"attributesExcludeMatchType\":\"ANY\"},\"perfStatistic\":\"p95\",\"perfStatisticContainer\":\"p95\",\"constraints\":[\"{\\\"type\\\":\\\"azureDiskAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"ebsAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"ebsOnStoppedEc2Aggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"elbAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"eipAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureDiskIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"ebsIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"ebsOnStoppedEc2IdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"elbIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"eipIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azurePublicIPAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azurePublicIPIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azureAppGatewayAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureAppGatewayIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azureLoadBalancerAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureLoadBalancerIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\"],\"changeManagerType\":null,\"requestCreationMethod\":\"M\"},{\"id\":7909,\"orgId\":\"c1b1bee2-4171-4f9e-b778-0093ca6df6d2\",\"analysisType\":\"IDLE_RESOURCES\",\"order\":8,\"name\":\"Cypress_idle_resource\",\"elementFilter\":{\"tagsInclude\":{},\"tagsIncludeMatchType\":\"ANY\",\"tagsExclude\":{},\"tagsExcludeMatchType\":\"ANY\",\"attributesInclude\":{\"Account\":[\"502379301106\",\"397111407519\",\"407724209249\",\"0d9c4aa6-d865-4303-a3b2-fdab3387c5a7\"]},\"attributesIncludeMatchType\":\"ANY\",\"attributesExclude\":{},\"attributesExcludeMatchType\":\"ANY\"},\"perfStatistic\":\"p95\",\"perfStatisticContainer\":\"p95\",\"constraints\":[\"{\\\"type\\\":\\\"azureDiskAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"ebsAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"ebsOnStoppedEc2Aggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"elbAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"eipAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureDiskIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"ebsIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"ebsOnStoppedEc2IdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"elbIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"eipIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azurePublicIPAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azurePublicIPIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azureAppGatewayAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureAppGatewayIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azureLoadBalancerAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureLoadBalancerIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\"],\"changeManagerType\":null,\"requestCreationMethod\":\"M\"},{\"id\":6818,\"orgId\":\"c1b1bee2-4171-4f9e-b778-0093ca6df6d2\",\"analysisType\":\"IDLE_RESOURCES\",\"order\":9,\"name\":\"test1\",\"elementFilter\":{\"tagsInclude\":{},\"tagsIncludeMatchType\":\"ANY\",\"tagsExclude\":{},\"tagsExcludeMatchType\":\"ANY\",\"attributesInclude\":{\"Account\":[\"502379301106\",\"397111407519\",\"407724209249\",\"0d9c4aa6-d865-4303-a3b2-fdab3387c5a7\"],\"AvailabilityZone\":[\"eu-west-1\"]},\"attributesIncludeMatchType\":\"ANY\",\"attributesExclude\":{},\"attributesExcludeMatchType\":\"ANY\"},\"perfStatistic\":\"p95\",\"perfStatisticContainer\":\"p95\",\"constraints\":[\"{\\\"type\\\":\\\"azureDiskAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"ebsAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"ebsOnStoppedEc2Aggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"elbAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"eipAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureDiskIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"ebsIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":false}\",\"{\\\"type\\\":\\\"ebsOnStoppedEc2IdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":false}\",\"{\\\"type\\\":\\\"elbIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"eipIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azurePublicIPAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azurePublicIPIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azureAppGatewayAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureAppGatewayIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azureLoadBalancerAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureLoadBalancerIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\"],\"changeManagerType\":\"snow\",\"requestCreationMethod\":\"M\"},{\"id\":7297,\"orgId\":\"c1b1bee2-4171-4f9e-b778-0093ca6df6d2\",\"analysisType\":\"IDLE_RESOURCES\",\"order\":10,\"name\":\"test2\",\"elementFilter\":{\"tagsInclude\":{},\"tagsIncludeMatchType\":\"ANY\",\"tagsExclude\":{},\"tagsExcludeMatchType\":\"ANY\",\"attributesInclude\":{\"Account\":[\"0d9c4aa6-d865-4303-a3b2-fdab3387c5a7\"]},\"attributesIncludeMatchType\":\"ANY\",\"attributesExclude\":{},\"attributesExcludeMatchType\":\"ANY\"},\"perfStatistic\":\"p95\",\"perfStatisticContainer\":\"p95\",\"constraints\":[\"{\\\"type\\\":\\\"azureDiskAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"ebsAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"ebsOnStoppedEc2Aggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"elbAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"eipAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureDiskIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"ebsIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"ebsOnStoppedEc2IdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"elbIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"eipIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azurePublicIPAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azurePublicIPIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azureAppGatewayAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureAppGatewayIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azureLoadBalancerAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureLoadBalancerIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\"],\"changeManagerType\":null,\"requestCreationMethod\":\"M\"},{\"id\":1870,\"orgId\":\"c1b1bee2-4171-4f9e-b778-0093ca6df6d2\",\"analysisType\":\"IDLE_RESOURCES\",\"order\":11,\"name\":\"Default Idle Resources Policy\",\"elementFilter\":{\"tagsInclude\":{},\"tagsIncludeMatchType\":\"ANY\",\"tagsExclude\":{},\"tagsExcludeMatchType\":\"ANY\",\"attributesInclude\":{},\"attributesIncludeMatchType\":\"ANY\",\"attributesExclude\":{},\"attributesExcludeMatchType\":\"ANY\"},\"perfStatistic\":\"p95\",\"perfStatisticContainer\":\"p95\",\"constraints\":[\"{\\\"type\\\":\\\"azureDiskAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"ebsAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"ebsOnStoppedEc2Aggressiveness\\\",\\\"monthlyCostOver\\\":2}\",\"{\\\"type\\\":\\\"elbAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"eipAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureDiskIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":false}\",\"{\\\"type\\\":\\\"ebsIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":false}\",\"{\\\"type\\\":\\\"ebsOnStoppedEc2IdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":false}\",\"{\\\"type\\\":\\\"elbIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":false}\",\"{\\\"type\\\":\\\"eipIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":false}\",\"{\\\"type\\\":\\\"azurePublicIPAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azurePublicIPIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azureAppGatewayAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureAppGatewayIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\",\"{\\\"type\\\":\\\"azureLoadBalancerAggressiveness\\\",\\\"monthlyCostOver\\\":0}\",\"{\\\"type\\\":\\\"azureLoadBalancerIdleDays\\\",\\\"idleDays\\\":0,\\\"includeUnknown\\\":true}\"],\"changeManagerType\":null,\"requestCreationMethod\":\"M\"}],\"limit\":250,\"start\":0,\"csv\":false}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IDLE_RESOURCES_URL))
                .header("Content-Type", "application/json")
                .header("COOKIE", xIdToken)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body().toString();
    }
    @Override
    public String rightSizingDashboard() throws IOException, InterruptedException {
        String requestBody = "{\"analysisType\":\"RIGHT_SIZING\",\"rollup\":\"P7D\",\"savingsPeriod\":\"month\",\"perfStatistic\":\"p95\",\"startDate\":\"2023-03-05\",\"endDate\":\"2023-03-11\",\"elementFilter\":{\"tagsInclude\":[],\"tagsExclude\":[],\"attributesInclude\":[],\"attributesMatchType\":\"ANY\",\"attributesExclude\":[]},\"policies\":[{\"id\":8469,\"orgId\":\"c1b1bee2-4171-4f9e-b778-0093ca6df6d2\",\"analysisType\":\"RIGHT_SIZING\",\"order\":1,\"name\":\"rrr2\",\"elementFilter\":{\"tagsInclude\":{},\"tagsIncludeMatchType\":\"ANY\",\"tagsExclude\":{},\"tagsExcludeMatchType\":\"ANY\",\"attributesInclude\":{\"Account\":[\"647724765282\",\"407724209249\",\"0d9c4aa6-d865-4303-a3b2-fdab3387c5a7\"]},\"attributesIncludeMatchType\":\"ANY\",\"attributesExclude\":{},\"attributesExcludeMatchType\":\"ANY\"},\"perfStatistic\":\"p95\",\"perfStatisticContainer\":\"p95\",\"constraints\":[\"{\\\"type\\\":\\\"instanceType\\\",\\\"rule\\\":\\\"allow\\\",\\\"provider\\\":\\\"AWS\\\",\\\"values\\\":[],\\\"scope\\\":\\\"instanceType\\\"}\",\"{\\\"type\\\":\\\"aggressiveness\\\",\\\"monthlySavings\\\":0}\"],\"changeManagerType\":null,\"requestCreationMethod\":\"M\"},{\"id\":8465,\"orgId\":\"c1b1bee2-4171-4f9e-b778-0093ca6df6d2\",\"analysisType\":\"RIGHT_SIZING\",\"order\":2,\"name\":\"rrr\",\"elementFilter\":{\"tagsInclude\":{},\"tagsIncludeMatchType\":\"ANY\",\"tagsExclude\":{},\"tagsExcludeMatchType\":\"ANY\",\"attributesInclude\":{\"Account\":[\"647724765282\",\"407724209249\",\"0d9c4aa6-d865-4303-a3b2-fdab3387c5a7\"]},\"attributesIncludeMatchType\":\"ANY\",\"attributesExclude\":{},\"attributesExcludeMatchType\":\"ANY\"},\"perfStatistic\":\"p95\",\"perfStatisticContainer\":\"p95\",\"constraints\":[\"{\\\"type\\\":\\\"instanceType\\\",\\\"rule\\\":\\\"allow\\\",\\\"provider\\\":\\\"AWS\\\",\\\"values\\\":[\\\"a1.medium\\\"],\\\"scope\\\":\\\"instanceType\\\"}\",\"{\\\"type\\\":\\\"aggressiveness\\\",\\\"monthlySavings\\\":0}\"],\"changeManagerType\":null,\"requestCreationMethod\":\"M\"},{\"id\":8412,\"orgId\":\"c1b1bee2-4171-4f9e-b778-0093ca6df6d2\",\"analysisType\":\"RIGHT_SIZING\",\"order\":3,\"name\":\"Test Policy by App\",\"elementFilter\":{\"tagsInclude\":{\"app\":[\"dns\",\"hq\"]},\"tagsIncludeMatchType\":\"ANY\",\"tagsExclude\":{},\"tagsExcludeMatchType\":\"ANY\",\"attributesInclude\":{\"Account\":[\"647724765282\",\"407724209249\",\"0d9c4aa6-d865-4303-a3b2-fdab3387c5a7\"]},\"attributesIncludeMatchType\":\"ANY\",\"attributesExclude\":{},\"attributesExcludeMatchType\":\"ANY\"},\"perfStatistic\":\"p95\",\"perfStatisticContainer\":\"p95\",\"constraints\":[\"{\\\"type\\\":\\\"aggressiveness\\\",\\\"monthlySavings\\\":0}\"],\"changeManagerType\":null,\"requestCreationMethod\":\"M\"},{\"id\":4470,\"orgId\":\"c1b1bee2-4171-4f9e-b778-0093ca6df6d2\",\"analysisType\":\"RIGHT_SIZING\",\"order\":4,\"name\":\"Default Right Sizing Policy\",\"elementFilter\":{\"tagsInclude\":{\"app\":[\"dns\",\"hq\"]},\"tagsIncludeMatchType\":\"ANY\",\"tagsExclude\":{},\"tagsExcludeMatchType\":\"ANY\",\"attributesInclude\":{\"AvailabilityZone\":[\"eu-west-2\",\"eu-west-2a\"]},\"attributesIncludeMatchType\":\"ANY\",\"attributesExclude\":{},\"attributesExcludeMatchType\":\"ANY\"},\"perfStatistic\":\"max\",\"perfStatisticContainer\":\"p95\",\"constraints\":[\"{\\\"type\\\":\\\"instanceType\\\",\\\"rule\\\":\\\"noChange\\\",\\\"scope\\\":\\\"series\\\"}\",\"{\\\"type\\\":\\\"cpu\\\",\\\"rule\\\":\\\"fixedFloor\\\",\\\"fixedFloor\\\":12222222222222}\",\"{\\\"type\\\":\\\"cpu\\\",\\\"rule\\\":\\\"dynamic\\\",\\\"fixedFloor\\\":0,\\\"maxUtil\\\":95,\\\"applyToContainer\\\":true}\",\"{\\\"type\\\":\\\"memory\\\",\\\"rule\\\":\\\"dynamic\\\",\\\"fixedFloor\\\":0,\\\"maxUtil\\\":95,\\\"defaultValue\\\":60,\\\"applyToContainer\\\":true}\",\"{\\\"type\\\":\\\"aggressiveness\\\",\\\"monthlySavings\\\":99}\",\"{\\\"type\\\":\\\"memory\\\",\\\"rule\\\":\\\"fixedFloor\\\",\\\"fixedFloor\\\":\\\"21231231231\\\",\\\"maxUtil\\\":95,\\\"defaultValue\\\":60}\",\"{\\\"type\\\":\\\"diskio\\\",\\\"rule\\\":\\\"fixedFloor\\\",\\\"fixedFloor\\\":1100303,\\\"requirePremiumIo\\\":false}\",\"{\\\"type\\\":\\\"awsEc2Tenancy\\\",\\\"rule\\\":\\\"dynamic\\\",\\\"value\\\":\\\"Shared\\\"}\"],\"changeManagerType\":\"jira\",\"requestCreationMethod\":\"M\"}],\"sort\":{\"colId\":\"PROJECTED_SAVINGS\",\"sort\":\"DESC\"},\"limit\":250,\"start\":0,\"csv\":false}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(RIGHT_SIZING_URL))
                .header("Content-Type", "application/json")
                .header("COOKIE", xIdToken)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body().toString();
    }
}

