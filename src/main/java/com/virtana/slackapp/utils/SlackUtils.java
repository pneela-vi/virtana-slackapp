package com.virtana.slackapp.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlackUtils {
    public SlackUtils() {

    }
    private static final String SPACE = " ";
    private static char PIPE = 9474;


//    public static void main(String[] args) {
//
//        String  jsonString = "[  {  \"id\": \"0624€569-005-459b-b563-37012345\",  \"email\": \"baig.asadulla@virtana.com\", \"name\": \"aig Asadulla\",  \"status\": \"verified\",\"roles\": [  {      \"id\": \"4933\",      \"name\": \"Virtana-platform-developer\",      \"friendlyName\": \"Virtana Developer\"  }  ],  \"authenticationMethod\": null},{  \"id\": \"0624€569-005-459b-b563-123\",  \"email\": \"pavan.neela@virtana.com\",  \"name\": \"Pavan Neela\",  \"status\": \"verified\",\"roles\": [{      \"id\": \"4933\",  \"name\": \"Virtana-platform-developer\",     \"friendlyName\": \"Virtana Developer\"},    {      \"id\": \"4934\",      \"name\": \"Virtana-platform-admin\",      \"friendlyName\": \"Admin\"  }  ],  \"authenticationMethod\": null}]";
//
//        Gson gson = new Gson();
//        JsonArray convertedObject = new Gson().fromJson(jsonString, JsonArray.class);
//
//        SlackUtils su = new SlackUtils();
//        String finalResponse = su.getTableResponseForUsers(convertedObject);
//        System.out.println( finalResponse);
//    }

    public String getTableResponseForUsers(JsonArray users_data) {

        /**
         *
         * [
         {
         "id": "0624€569-005-459b-b563-37012345",
         "email": "baig.asadulla@virtana.com",
         "Name": "aig Asadulla",
         "status": "verified",
         "roles": [
         {
         "id": "4933",
         "name": "Virtana-platform-developer",
         "friendlyName": "Virtana Developer"
         }
         ],
         "authenticationMethod": null
         },
         {
         "id": "0624€569-005-459b-b563-123",
         "email": "pavan.neela@virtana.com",
         "name": "Pavan Neela",
         "status": "verified",
         "roles": [
         {
         "id": "4933",
         "name": "Virtana-platform-developer",
         "friendlyName": "Virtana Developer"
         },
         {
         "id": "4934",
         "name": "Virtana-platform-admin",
         "friendlyName": "Virtana Admin"
         }
         ],
         "authenticationMethod": null
         }
         ]
         * */
        List<String> headers = new ArrayList<>();
        List<List> rows = new ArrayList<>();

        headers.add("SNo.");
        headers.add("Name");
        headers.add("Email");
        headers.add("Status");
        headers.add("Roles");


        //	JsonArray users_data = data.getAsJsonArray();

        for (int i = 0; i < users_data.size(); i++) {

            JsonObject user =  users_data.get(i).getAsJsonObject();

            JsonArray  user_roles = user.get("roles").getAsJsonArray();
            String user_roles_string = "";
            for (int j = 0; j < user_roles.size(); j++) {

                JsonObject user_role = user_roles.get(j).getAsJsonObject();

                user_roles_string = user_roles_string + user_role.get("friendlyName");

                if(j != (user_roles.size()-1) ) {
                    user_roles_string = user_roles_string + ", ";
                }
            }

            List user_record = new ArrayList<>();
            user_record.add((i+1)); 															// SNo.
            user_record.add(user.get("name")!=null?user.get("name").getAsString():"NA");		// Name
            user_record.add(user.get("email")!=null?user.get("email").getAsString():"NA");		// Email
            user_record.add(user.get("status")!=null?user.get("status").getAsString():"NA");	// Status
            user_record.add(user_roles_string);													// Roles

            rows.add(user_record);
        }

        Map map = new HashMap<>();
        map.put("headers", headers);
        map.put("rows", rows);

        return getTableResponse(map);
    }

    public String getTableResponseForIdleResources(JsonArray idle_resources) {

        List<String> headers = new ArrayList<>();
        List<List> rows = new ArrayList<>();

       // headers.add("SNo.");
        headers.add("Entity");
        headers.add("Name");
        headers.add("Cloud");
        headers.add("IdleType");
        headers.add("MonthlyCost");

        //	JsonArray users_data = data.getAsJsonArray();

        for (int i = 0; i < 10; i++) {

            JsonObject user =  idle_resources.get(i).getAsJsonObject();

//            JsonArray  user_roles = user.get("roles").getAsJsonArray();
//            String user_roles_string = "";
//            for (int j = 0; j < user_roles.size(); j++) {
//
//                JsonObject user_role = user_roles.get(j).getAsJsonObject();
//
//                user_roles_string = user_roles_string + user_role.get("friendlyName");
//
//                if(j != (user_roles.size()-1) ) {
//                    user_roles_string = user_roles_string + ", ";
//                }
//            }

            List user_record = new ArrayList<>();
          //  user_record.add((i+1)); 															// SNo.
            user_record.add(user.get("elementType")!=null?user.get("elementType").getAsString():"NA");		// Entity
            user_record.add(user.get("elementName")!=null?user.get("elementName").getAsString():"NA");		// Name
            user_record.add(user.get("cloudProvider")!=null?user.get("cloudProvider").getAsString():"NA");	// Cloud

            user_record.add(user.get("attributes").getAsJsonObject().get("idleType")!=null?user.get("attributes").getAsJsonObject().get("idleType").getAsString():"NA");	// IdleType
           // user_record.add(user_roles_string);													// IdleType
            user_record.add(user.get("totalCost")!=null?user.get("totalCost").getAsString():"NA");		// MonthlyCost

            rows.add(user_record);
        }

        Map map = new HashMap<>();
        map.put("headers", headers);
        map.put("rows", rows);

        return getTableResponse(map);
    }
    private String getTableResponse(Map tableData) {

        Map<Integer, Integer> maxLenghthOfColumns = new HashMap<>();

        List<String> headers = (List<String>) tableData.get("headers");
        List<List> rows = (List<List>) tableData.get("rows");

        for (int i = 0; i < headers.size(); i++) {

            String header = headers.get(i);
            maxLenghthOfColumns.put(i, header.length());
        }

        for (int i = 0; i < rows.size(); i++) {

            List<String> row = rows.get(i);
            for (int j = 0; j < row.size(); j++) {

                String cell = ((Object)row.get(j)).toString();
                if(cell.length() > maxLenghthOfColumns.get(j)) {
                    maxLenghthOfColumns.put(j, cell.length());
                }
            }
        }
        System.out.println("possible max lengths of columns : "+maxLenghthOfColumns);

        // append spaces if required.
        Map responseData = new HashMap<>();
        List<String> response_headers = new ArrayList<>();
        List<List> response_rows = new ArrayList<>();

        for (int i = 0; i < headers.size(); i++) {

            String header = headers.get(i);
            String hdr = getSpaceAppendedText(header, maxLenghthOfColumns.get(i));
            response_headers.add(hdr);
        }
        for (int i = 0; i < rows.size(); i++) {

            List<String> response_row = new ArrayList<>();
            List<String> row = rows.get(i);
            for (int j = 0; j < row.size(); j++) {

                String cell = ((Object)row.get(j)).toString();
                response_row.add(getSpaceAppendedText(cell, maxLenghthOfColumns.get(j)));
            }
            response_rows.add(response_row);
        }
        responseData.put("headers", response_headers);
        responseData.put("rows", response_rows);

        // prepare header top, header bottom, middle HR, bottom HR
        Map hrs = prepareHorizontalLines(response_headers);
        StringBuilder finalResponse = new StringBuilder();

        finalResponse.append("```");
        finalResponse.append(hrs.get("headerTop"));
        finalResponse.append("\n");
        for (int i = 0; i < response_headers.size(); i++) {

            finalResponse.append(PIPE);
            finalResponse.append(response_headers.get(i));

            if(i == (response_headers.size()-1)) {
                finalResponse.append("│\n");
            }
        }
        finalResponse.append(hrs.get("headerBottom"));
        finalResponse.append("\n");

        for (int i = 0; i < response_rows.size(); i++) {

            List<String> row = response_rows.get(i);
            for (int j = 0; j < row.size(); j++) {

                finalResponse.append(PIPE);
                String cell = row.get(j);
                finalResponse.append(cell);
                if(j == (row.size()-1)) {
                    finalResponse.append(PIPE);
                    finalResponse.append("\n");
                }
            }
            if(i != (response_rows.size()-1)) {
                finalResponse.append(hrs.get("middleHR"));
                finalResponse.append("\n");
            }
        }
        finalResponse.append(hrs.get("bottomHR"));
        finalResponse.append("```");

        return finalResponse.toString();
    }
    private Map<String, String> prepareHorizontalLines(List<String> headers) {

        StringBuilder headerTopBuilder = new StringBuilder();
        StringBuilder headerBottomBuilder = new StringBuilder();
        StringBuilder middleHRBuilder = new StringBuilder();
        StringBuilder bottomHRBuilder = new StringBuilder();

        headerTopBuilder.append("╒");
        headerBottomBuilder.append("╞");
        middleHRBuilder.append("├");
        bottomHRBuilder.append("└");

        for (int i = 0; i < headers.size(); i++) {

            String header = (String) headers.get(i);

            appendGivenString(headerTopBuilder, "═", header.length() );
            appendGivenString(headerBottomBuilder, "═", header.length() );
            appendGivenString(middleHRBuilder, "─", header.length() );
            appendGivenString(bottomHRBuilder, "─", header.length() );

            if(i == (headers.size() -1)) { // end of the line

                headerTopBuilder.append("╕");
                headerBottomBuilder.append("╡");
                middleHRBuilder.append("┤");
                bottomHRBuilder.append("┘");
            }else {
                headerTopBuilder.append("╤");
                headerBottomBuilder.append("╪");
                middleHRBuilder.append("┼");
                bottomHRBuilder.append("┴");
            }
        }

        /**
         String returnData = "```╒════════════════╤═══════╤══════════════╤═══════════════════╤═══════════════╤═══════════════════╤══════════════════════════════════════╤═══════╕\n"
         +"│\"p.DisplayLabel\"│\"id(p)\"│\"Type(r)\"     │\"r.EndTime\"        │\"n.Type\"       │\"n.EndTime\"        │\"n.DisplayLabel\"                      │\"id(n)\"│\n"
         +"╞════════════════╪═══════╪══════════════╪═══════════════════╪═══════════════╪═══════════════════╪══════════════════════════════════════╪═══════╡\n"
         +"│\"bi40grt1a004:8\"│3285080│\"ChildEntity\" │9223372036854775807│\"LogicalSwitch\"│9223372036854775807│\"bi40grt1a004:FID128-100050eb1ae7ec5c\"│3285125│\n"
         +"├────────────────┼───────┼──────────────┼───────────────────┼───────────────┼───────────────────┼──────────────────────────────────────┼───────┤\n"
         +"│\"bi40grt1a004:8\"│3285080│\"SystemDevice\"│9223372036854775807│\"LogicalSwitch\"│9223372036854775807│\"bi40grt1a004:FID128-100050eb1ae7ec5c\"│3285125│\n"
         +"├────────────────┼───────┼──────────────┼───────────────────┼───────────────┼───────────────────┼──────────────────────────────────────┼───────┤\n"
         +"│\"bi40grt1a004:8\"│3285080│\"Member\"      │9223372036854775807│\"LogicalFabric\"│9223372036854775807│\"FID128-100050eb1ae7ec5c\"             │3285047│\n"
         └────────────────┴───────┴──────────────┴───────────────────┴───────────────┴───────────────────┴──────────────────────────────────────┴───────┘```";
         * */

        Map<String, String> hrs = new HashMap<>();

        hrs.put("headerTop", headerTopBuilder.toString());
        hrs.put("headerBottom", headerBottomBuilder.toString());
        hrs.put("middleHR", middleHRBuilder.toString());
        hrs.put("bottomHR", bottomHRBuilder.toString());

        return hrs;
    }

    private String getSpaceAppendedText(String text, Integer maxSizeOfCell) {

        String responseText = text;
        int requiredSpaces = maxSizeOfCell - text.length();
        for (int i = 0; i < requiredSpaces; i++) {
            responseText = responseText+SPACE;
        }
        return responseText;
    }

    private void appendGivenString(StringBuilder stringBuilder, String data, int noOfTimes) {

        for (int j = 0; j < noOfTimes; j++) {
            stringBuilder.append(data);
        }
    }
}
