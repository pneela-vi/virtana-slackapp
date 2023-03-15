/*
package com.virtana.slackapp;
import com.slack.api.bolt.App;

public class GraphModel {
    public static Response generateGraph(SlashCommandRequest req, SlashCommandContext ctx) {
        // Extract the data from the input JSON string
        int[] data = {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0};

        // Create the bars for the chart
        List<BlockElement> bars = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            int value = data[i];
            BlockElement bar = BlockElements.builder()
                    .value(String.valueOf(value))
                    .color("#36a64f") // Green bars for values greater than 0
                    .build();
            if (value == 0) {
                bar.color("#e5e5e5"); // Gray bars for values equal to 0
            }
            bars.add(bar);
        }

        // Create the layout block for the chart
        PlainTextObject title = PlainTextObject.builder()
                .text("Max severity per hour")
                .build();
        LayoutBlock chart = Blocks.barChart(title, bars);

        // Create a view containing the chart
        Views views = Views.builder()
                .callbackId("chart-view")
                .type("modal")
                .title(PlainTextObject.builder().text("Chart").build())
                .close(PlainTextObject.builder().text("Close").build())
                .blocks(List.of(chart))
                .build();

        // Open the view in Slack
        App app = ctx.getApp();
        return ctx.ack(r -> r.responseAction("open-modal").view(views));
    }
}*/
