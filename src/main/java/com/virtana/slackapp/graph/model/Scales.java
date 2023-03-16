package com.virtana.slackapp.graph.model;

import java.util.List;
import java.util.Map;

public class Scales {
    List<Map<String,ScaleLabel>> xAxes;
    List<Map<String,Object>> yAxes;

    public Scales(List<Map<String,ScaleLabel>> xAxes, List<Map<String,Object>> yAxes) {
        this.xAxes = xAxes;
        this.yAxes = yAxes;
    }

    public List<Map<String,ScaleLabel>> getxAxes() {
        return xAxes;
    }

    public void setxAxes(List<Map<String,ScaleLabel>> xAxes) {
        this.xAxes = xAxes;
    }

    public List<Map<String,Object>> getyAxes() {
        return yAxes;
    }

    public void setyAxes(List<Map<String,Object>> yAxes) {
        this.yAxes = yAxes;
    }
}
