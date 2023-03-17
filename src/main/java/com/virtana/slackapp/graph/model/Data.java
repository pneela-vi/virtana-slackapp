package com.virtana.slackapp.graph.model;

import java.util.List;

public class Data {
    String[] xLabels;

    String[] yLabels;
    List<DataSets> datasets;

    public String[] getxLabels() {
        return xLabels;
    }

    public void setxLabels(String[] labels) {
        this.xLabels = labels;
    }

    public String[] getyLabels() {
        return yLabels;
    }

    public void setyLabels(String[] yLabels) {
        this.yLabels = yLabels;
    }

    public List<DataSets> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<DataSets> datasets) {
        this.datasets = datasets;
    }
}
