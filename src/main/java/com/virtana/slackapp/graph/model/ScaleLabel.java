package com.virtana.slackapp.graph.model;

public class ScaleLabel {

    boolean display;
    String fontColor;
    String labelString;
    String fontStyle;

    public ScaleLabel(boolean display, String fontColor, String labelString, String fontStyle) {
        this.display = display;
        this.fontColor = fontColor;
        this.labelString = labelString;
        this.fontStyle = fontStyle;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }


    public String getLabelString() {
        return labelString;
    }

    public void setLabelString(String labelString) {
        this.labelString = labelString;
    }

    public String getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }
}
