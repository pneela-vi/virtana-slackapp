package com.virtana.slackapp.graph.model;

public class Options {
    String legend;
    Scales scales;

    public Options(String legend, Scales scales) {
        this.legend = legend;
        this.scales = scales;
    }

    public String getLegend() {
        return legend;
    }

    public void setLegend(String legend) {
        this.legend = legend;
    }

    public Scales getScales() {
        return scales;
    }

    public void setScales(Scales scales) {
        this.scales = scales;
    }
}
