package com.virtana.slackapp.graph.model;

import io.quickchart.QuickChart;

public class GraphBuilder {

    String chartType = "line";

    public void buildChart(){
        QuickChart chart = new QuickChart();
        chart.setWidth(500);
        chart.setHeight(300);

    }
}
