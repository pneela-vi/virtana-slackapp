package com.virtana.slackapp.graph.model;

public class ResponseData {

    String appName;
    String dataCenter;
    String region;
    String url;

    public ResponseData(String appName, String dataCenter, String region, String url) {
        this.appName = appName;
        this.dataCenter = dataCenter;
        this.region = region;
        this.url = url;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDataCenter() {
        return dataCenter;
    }

    public void setDataCenter(String dataCenter) {
        this.dataCenter = dataCenter;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
