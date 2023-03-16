package com.virtana.slackapp.graph.model;

public class Scales {
    String[] xAxes;
    String[] yAxes;

    public Scales(String[] xAxes, String[] yAxes) {
        this.xAxes = xAxes;
        this.yAxes = yAxes;
    }

    public String[] getxAxes() {
        return xAxes;
    }

    public void setxAxes(String[] xAxes) {
        this.xAxes = xAxes;
    }

    public String[] getyAxes() {
        return yAxes;
    }

    public void setyAxes(String[] yAxes) {
        this.yAxes = yAxes;
    }
}
