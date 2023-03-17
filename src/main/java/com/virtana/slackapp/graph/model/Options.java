package com.virtana.slackapp.graph.model;

import java.util.Map;

public class Options {
    Map<String,Boolean> legend;

    Map<String,Map<String,String>> elements;
    Scales scales;

    public Options(Map<String,Boolean> legend, Scales scales, Map<String,Map<String,String>> elements) {
        this.legend = legend;
        this.scales = scales;
    }

    public Map<String,Boolean> getLegend() {
        return legend;
    }

    public void setLegend(Map<String,Boolean> legend) {
        this.legend = legend;
    }

    public Scales getScales() {
        return scales;
    }

    public void setScales(Scales scales) {
        this.scales = scales;
    }

    public Map<String, Map<String, String>> getElements() {
        return elements;
    }

    public void setElements(Map<String, Map<String, String>> elements) {
        this.elements = elements;
    }
}
