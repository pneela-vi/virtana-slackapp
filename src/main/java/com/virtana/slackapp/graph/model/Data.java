package com.virtana.slackapp.graph.model;

import java.util.List;

public class Data {
    String[] labels;
    List<DataSets> datasets;

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public List<DataSets> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<DataSets> datasets) {
        this.datasets = datasets;
    }
}
