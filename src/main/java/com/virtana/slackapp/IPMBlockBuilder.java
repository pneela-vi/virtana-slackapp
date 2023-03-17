package com.virtana.slackapp;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.slack.api.model.block.DividerBlock;
import com.slack.api.model.block.ImageBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.composition.TextObject;
import com.virtana.slackapp.graph.model.*;
import io.quickchart.QuickChart;

import java.util.*;

public class IPMBlockBuilder {

    public List<LayoutBlock> getIPMDataAsBlockMesage(String response){

        GraphData graphData = new GraphData();
        graphData.setType("line");
        Data data = new Data();
        List<DataSets> dataSetsList = new ArrayList<>();


        data.setxLabels(new String[]{"-12","-11","-10","-9","-8","-7","-6","-5","-4","-3","-2","-1"});
        //String val = "[{\"timerange\":1,\"appname\":\"Isilon on VMware\",\"datacenter\":\"Los Angeles\",\"maxsevperhour\":[{\"sev\":1,\"ago\":-23},{\"sev\":1,\"ago\":-22},{\"sev\":1,\"ago\":-21},{\"sev\":1,\"ago\":-20},{\"sev\":1,\"ago\":-19},{\"sev\":1,\"ago\":-18},{\"sev\":1,\"ago\":-17},{\"sev\":1,\"ago\":-16},{\"sev\":1,\"ago\":-15},{\"sev\":1,\"ago\":-14},{\"sev\":1,\"ago\":-13},{\"sev\":1,\"ago\":-12},{\"sev\":1,\"ago\":-11},{\"sev\":1,\"ago\":-10},{\"sev\":1,\"ago\":-9},{\"sev\":1,\"ago\":-8},{\"sev\":1,\"ago\":-7},{\"sev\":1,\"ago\":-6},{\"sev\":2,\"ago\":-5},{\"sev\":2,\"ago\":-4},{\"sev\":2,\"ago\":-3},{\"sev\":2,\"ago\":-2},{\"sev\":2,\"ago\":-1},{\"sev\":2,\"ago\":0}],\"vrn\":\"//c1b1bee2-4171-4f9e-b778-0093ca6df6d2/vw/-/vw.application/11111111-1111-1111-1111-111111111111/Application#vcs/0569dcb39c974e0ebf7a5316e2af430e\",\"region\":\"America\",\"url\":\"https://VI-Appliance/#inventory/edit/0569dcb39c974e0ebf7a5316e2af430e\",\"maxsev\":\"1\"},{\"timerange\":1,\"appname\":\"Linux only\",\"datacenter\":\"JohnnysVM146\",\"maxsevperhour\":[{\"sev\":2,\"ago\":-23},{\"sev\":2,\"ago\":-22},{\"sev\":2,\"ago\":-21},{\"sev\":2,\"ago\":-20},{\"sev\":2,\"ago\":-19},{\"sev\":2,\"ago\":-18},{\"sev\":2,\"ago\":-17},{\"sev\":2,\"ago\":-16},{\"sev\":2,\"ago\":-15},{\"sev\":2,\"ago\":-14},{\"sev\":2,\"ago\":-13},{\"sev\":2,\"ago\":-12},{\"sev\":2,\"ago\":-11},{\"sev\":2,\"ago\":-10},{\"sev\":2,\"ago\":-9},{\"sev\":2,\"ago\":-8},{\"sev\":2,\"ago\":-7},{\"sev\":2,\"ago\":-6},{\"sev\":2,\"ago\":-5},{\"sev\":2,\"ago\":-4},{\"sev\":2,\"ago\":-3},{\"sev\":2,\"ago\":-2},{\"sev\":2,\"ago\":-1},{\"sev\":2,\"ago\":0}],\"vrn\":\"//c1b1bee2-4171-4f9e-b778-0093ca6df6d2/vw/-/vw.application/11111111-1111-1111-1111-111111111111/Application#vcs/97f121108c3c4e5da7c09518f10e4963\",\"region\":\"America\",\"url\":\"https://VI-Appliance/#inventory/edit/97f121108c3c4e5da7c09518f10e4963\",\"maxsev\":\"2\"},{\"timerange\":1,\"appname\":\"SampleAppConfiguration\",\"datacenter\":\"JohnnysVM146\",\"maxsevperhour\":[{\"sev\":3,\"ago\":-23},{\"sev\":3,\"ago\":-22},{\"sev\":3,\"ago\":-21},{\"sev\":3,\"ago\":-20},{\"sev\":3,\"ago\":-19},{\"sev\":3,\"ago\":-18},{\"sev\":3,\"ago\":-17},{\"sev\":3,\"ago\":-16},{\"sev\":3,\"ago\":-15},{\"sev\":3,\"ago\":-14},{\"sev\":3,\"ago\":-13},{\"sev\":3,\"ago\":-12},{\"sev\":3,\"ago\":-11},{\"sev\":3,\"ago\":-10},{\"sev\":3,\"ago\":-9},{\"sev\":3,\"ago\":-8},{\"sev\":3,\"ago\":-7},{\"sev\":3,\"ago\":-6},{\"sev\":3,\"ago\":-5},{\"sev\":3,\"ago\":-4},{\"sev\":3,\"ago\":-3},{\"sev\":3,\"ago\":-2},{\"sev\":3,\"ago\":-1},{\"sev\":3,\"ago\":0}],\"vrn\":\"//c1b1bee2-4171-4f9e-b778-0093ca6df6d2/vw/-/vw.application/11111111-1111-1111-1111-111111111111/Application#vcs/ead5da229a2d4462b94e0c0b484eee76\",\"region\":\"America\",\"url\":\"https://VI-Appliance/#inventory/edit/ead5da229a2d4462b94e0c0b484eee76\",\"maxsev\":\"3\"}]";
        JsonParser parser = new JsonParser();
        JsonElement tradeElement = parser.parse(response);
        JsonArray tradeArray = tradeElement.getAsJsonArray();
        List<JsonElement> list = tradeArray.asList();
        List<ResponseData> responseList = new ArrayList<>();
        int size = list.size()+2;
        String[] yLables = new String[size];
        int value = 1;
        yLables[0]="";
        for(JsonElement element : list){
            String appName=element.getAsJsonObject().get("appname").getAsString();
            yLables[value]=appName;
            String dataCenter = element.getAsJsonObject().get("datacenter").getAsString();
            String region = element.getAsJsonObject().get("region").getAsString();
            String url = element.getAsJsonObject().get("url").getAsString();
            responseList.add(new ResponseData(appName,dataCenter,region,url));
            JsonArray maxSev = element.getAsJsonObject().get("maxsevperhour").getAsJsonArray();
            List<JsonElement> sortedList =maxSev.asList();
            List<JsonElement> sublist;
            if(sortedList.size() > 11) {
                sublist = sortedList.subList(11,maxSev.asList().size());
            }else {
                sublist = sortedList;
            }
            int count =0;
            DataSets greenSet = new DataSets();
            DataSets orangeSet = new DataSets();
            DataSets redSet = new DataSets();
            DataSets greySet = new DataSets();
            String[] greenArray = new String[sublist.size()];
            String[] orrangeArray = new String[sublist.size()];
            String[] redArray = new String[sublist.size()];
            String[] greyArray = new String[sublist.size()];
            boolean greenData = false; boolean orrangeData = false;boolean redData = false;boolean greyData = false;
            for(JsonElement jsonElement :sublist){
                int sev = jsonElement.getAsJsonObject().get("sev").getAsInt();
                if(sev ==1){
                    greenData = true;
                    greenArray[count] = appName;
                }else  if(sev == 2){
                    orrangeData = true;
                    orrangeArray[count]=appName;
                }else  if(sev == 3){
                    redData= true;
                    redArray[count]=appName;
                }
                else  if(sev == 0){
                    greyData= true;
                    greyArray[count]=appName;
                }
                count++;
            }
            if(greenData){
                greenSet.setLabel(appName);
                greenSet.setData(greenArray);
                greenSet.setFill(false);
                greenSet.setShowLine(false);
                greenSet.setBorderColor("green");
                greenSet.setBackgroundColor("green");
                greenSet.setPointRadius(5);
                dataSetsList.add(greenSet);
            }
            if(orrangeData){
                orangeSet.setLabel(appName);
                orangeSet.setData(orrangeArray);
                orangeSet.setFill(false);
                orangeSet.setShowLine(false);
                orangeSet.setBorderColor("orange");
                orangeSet.setBackgroundColor("orange");
                orangeSet.setPointRadius(5);
                dataSetsList.add(orangeSet);
            }
            if(redData){
                redSet.setLabel(appName);
                redSet.setData(redArray);
                redSet.setFill(false);
                redSet.setShowLine(false);
                redSet.setBorderColor("red");
                redSet.setBackgroundColor("red");
                redSet.setPointRadius(5);
                dataSetsList.add(redSet);
            }
            if(greyData){
                greySet.setLabel(appName);
                greySet.setData(greyArray);
                greySet.setFill(false);
                greySet.setBorderColor("grey");
                greySet.setBackgroundColor("grey");
                greySet.setShowLine(false);
                greySet.setPointRadius(5);
                dataSetsList.add(greySet);
            }
            value++;
        }
        yLables[value]="";
        data.setyLabels(yLables);
        Gson gson = new Gson();
        data.setDatasets(dataSetsList);
        graphData.setData(data);

        ScaleLabel xscaleLabel=new ScaleLabel(true,"#ff","Hours","bold");
        Map<String,ScaleLabel> xscalMap = new HashMap<>();
        xscalMap.put("scaleLabel",xscaleLabel);
        //xscalMap.put("display","true");
        List<Map<String,ScaleLabel>> xscaList=new ArrayList<>();
        xscaList.add(xscalMap);
        ScaleLabel yscaleLabel=new ScaleLabel(true,"#ff","","bold");
        Map<String,Object> yscalMap = new HashMap<>();
        yscalMap.put("type","category");
        yscalMap.put("position","left");
        yscalMap.put("display","true");
        yscalMap.put("scaleLabel",yscaleLabel);
        Map<String,String> ticksData = new HashMap<>();
        ticksData.put("reverse","true");
        ticksData.put("min","0");
        ticksData.put("max","50");
        yscalMap.put("ticks",ticksData);
        List<Map<String,Object>> yscaList=new ArrayList<>();
        yscaList.add(yscalMap);
        Scales scales = new Scales(xscaList,yscaList);
        Map<String,Boolean> map =new HashMap();
        map.put("display",false);
        Map<String,Map<String,String>> elements =new HashMap<>();
        Map<String,String> point =new HashMap();
        point.put("pointStyle","circle");
        elements.put("elements",point);
        Options options = new Options(map,scales,elements);
        graphData.setOptions(options);

        String json = gson.toJson(graphData);
        json=json.replaceAll("null","");
        System.out.println("Graph Data ::"+ json);

        QuickChart chart = new QuickChart();
        chart.setWidth(500);
        chart.setHeight(300);
        chart.setVersion("2.9.4");
        chart.setConfig(json);

        // Print the chart image URL
        System.out.println(chart.getUrl());
        // Or get the image
        // byte[] imageBytes = chart.toByteArray();

        List<LayoutBlock> message = new ArrayList();

        SectionBlock sec= new SectionBlock();
        List<TextObject> textList=new ArrayList<>();
        MarkdownTextObject markdownTextObject=new MarkdownTextObject();
        //markdownTextObject.setText("AppName : "+appName);
        textList.add(markdownTextObject);
        sec.setFields(textList);
        for(ResponseData respData :responseList) {
            message.add(SectionBlock
                    .builder()
                    .fields(Arrays.asList(
                            MarkdownTextObject
                                    .builder()
                                    .text("*AppName ::* " + respData.getAppName())
                                    .build(),
                            MarkdownTextObject
                                    .builder()
                                    .text("*Data Center ::* " + respData.getDataCenter())
                                    .build(),
                            MarkdownTextObject
                                    .builder()
                                    .text("*Region ::* " + respData.getRegion())
                                    .build(),
                            MarkdownTextObject
                                    .builder()
                                    .text("*URL ::* " + respData.getUrl())
                                    .build()

                    ))
                    .build());
            message.add(DividerBlock
                    .builder()
                    .build());
        }
        message.add(ImageBlock.builder().imageUrl(chart.getShortUrl()).altText("Status").title(PlainTextObject.builder().text("Status ").build()).build());

        return message;
    }
}
