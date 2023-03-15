package com.virtana.slackapp;


import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse;
import com.slack.api.bolt.App;
import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;
import com.virtana.slackapp.handlers.CommandHandlerImpl;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class SlackApp {
    public static void main(String[] args) throws Exception {

        // App expects env variables (SLACK_BOT_TOKEN, SLACK_SIGNING_SECRET)

        App app = new App();

        app.command("/virtana", (req, ctx) -> {
            String commandParams = req.getPayload().getText();
            SlashCommandResponse response = new SlashCommandResponse();
            switch (commandParams){
                case "IPM": {
                    response = ipmResponse(req, ctx);
                    break;
                }
                default:
                    response.setText("Command is not supported");
            }
            ctx.respond(response);
            //LayoutBlock

            return ctx.ack();
        });

        SlackAppServer server = new SlackAppServer(app);
        server.start(); // http://localhost:3000/slack/events
    }

    public static SlashCommandResponse ipmResponse(SlashCommandRequest req, SlashCommandContext ctx){
        CommandHandlerImpl chl = new CommandHandlerImpl();
        Response response;
        try {
             response = Response.ok(chl.ipmDashboard());
            JsonParser parser = new JsonParser();
            JsonElement tradeElement = parser.parse(response.getBody());
            tradeElement.getAsJsonArray().get(0).getAsJsonObject().get("appname").getAsString();

            System.out.println(tradeElement.getAsJsonArray().get(0).getAsJsonObject().get("appname").getAsString());
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }

        SlashCommandResponse cmdResp = new SlashCommandResponse();
        cmdResp.setResponseType("ephemeral");
        cmdResp.setText(response.getBody());
        return cmdResp;
    }
}
