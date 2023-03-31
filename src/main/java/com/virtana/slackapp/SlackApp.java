package com.virtana.slackapp;


import com.google.gson.*;
import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse;
import com.slack.api.bolt.App;
import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.model.block.LayoutBlock;
import com.virtana.slackapp.handlers.CommandHandlerImpl;
import com.virtana.slackapp.utils.SlackUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class SlackApp {
    CommandHandlerImpl chl = new CommandHandlerImpl();
    public static void main(String[] args) throws Exception {

        // App expects env variables (SLACK_BOT_TOKEN, SLACK_SIGNING_SECRET)
        App app = new App();

        app.command("/virtana-platform", (req, ctx) -> {
            if(null == req.getPayload().getText()){
                SlashCommandResponse response = helpResponse(req, ctx);
                ctx.respond(response);
                return ctx.ack();
            }else{
                String[] commandParams = req.getPayload().getText().split(" ");
                String limit;
                try{
                    limit = String.valueOf(commandParams.length > 1?Integer.parseInt(commandParams[1]):10);
                }catch (NumberFormatException ex){
                   limit = "10";
                }
                SlashCommandResponse response = new SlashCommandResponse();
                switch (commandParams[0]){
                    case "ipm": {
                        response = ipmResponse(req, ctx);
                        break;
                    }
                    case "help": {
                        response = helpResponse(req, ctx);
                        break;
                    }
                    case "users-by-org": {
                        response = usersByOrgResponse(req, ctx, limit);
                        break;
                    }
                    case "idle-resources": {
                        response = idleResourcesResponse(req, ctx, limit);
                        break;
                    }
                    case "right-sizing": {
                        response = rightSizingResponse(req, ctx, limit);
                        break;
                    }
                    default:
                        response.setText("Command not supported. Please try \"virtana-platform help\" for available commands.");
                }
                ctx.respond(response);

                return ctx.ack();

            }
        });

        SlackAppServer server = new SlackAppServer(app);
        server.start(); // http://localhost:3000/slack/events
    }

    public static SlashCommandResponse ipmResponse(SlashCommandRequest req, SlashCommandContext ctx){
        CommandHandlerImpl chl = new CommandHandlerImpl();
        IPMBlockBuilder ipmBlockBuilder = new IPMBlockBuilder();
        Response response;
        List<LayoutBlock> ipmResponse;
        try {
            response = Response.ok(chl.ipmDashboard());
            ipmResponse = ipmBlockBuilder.getIPMDataAsBlockMesage(response.getBody());
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }

        SlashCommandResponse cmdResp = new SlashCommandResponse();
        cmdResp.setResponseType("ephemeral");
        cmdResp.setBlocks(ipmResponse);
        return cmdResp;
    }

    public static SlashCommandResponse helpResponse(SlashCommandRequest req, SlashCommandContext ctx){
        HelpBlockBuilder chl = new HelpBlockBuilder();
        List<LayoutBlock> blocks;
        blocks = chl.handleHelpCommand(req, ctx);
        SlashCommandResponse cmdResp = new SlashCommandResponse();
        cmdResp.setResponseType("ephemeral");
        cmdResp.setBlocks(blocks);
        return cmdResp;
    }

    public static SlashCommandResponse usersByOrgResponse(SlashCommandRequest req, SlashCommandContext ctx, String limit){
        CommandHandlerImpl chl = new CommandHandlerImpl();
        Response response;
        try {
            response = Response.ok(chl.usersByOrg(limit));
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
        SlashCommandResponse cmdResp = new SlashCommandResponse();
        cmdResp.setResponseType("ephemeral");
        cmdResp.setText(response.getBody());

        return cmdResp;
    }

    public static SlashCommandResponse idleResourcesResponse(SlashCommandRequest req, SlashCommandContext ctx, String limit){
        CommandHandlerImpl chl = new CommandHandlerImpl();
        Response response;
        try {
            response = Response.ok(chl.idleResourcesDashboard());
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
        SlackUtils slackUtils = new SlackUtils();
        JsonParser parser = new JsonParser();
        JsonElement tradeElement = parser.parse(response.getBody());
        JsonElement element = tradeElement.getAsJsonObject().get("analysis").getAsJsonObject().get("idleResources");
        JsonArray tradeArray = element.getAsJsonArray();
        String output =   slackUtils.getTableResponseForIdleResources(tradeArray, Integer.parseInt(limit));
        SlashCommandResponse cmdResp = new SlashCommandResponse();
        cmdResp.setResponseType("ephemeral");
        cmdResp.setText(output);
        return cmdResp;
    }

    public static SlashCommandResponse rightSizingResponse(SlashCommandRequest req, SlashCommandContext ctx, String limit){
        //TODO
        CommandHandlerImpl chl = new CommandHandlerImpl();
        Response response;
        try {
            response = Response.ok(chl.rightSizingDashboard());
            JsonParser parser = new JsonParser();
            JsonElement tradeElement = parser.parse(response.getBody());
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
        SlackUtils slackUtils = new SlackUtils();
        JsonParser parser = new JsonParser();
        JsonElement tradeElement = parser.parse(response.getBody());
        JsonElement element = tradeElement.getAsJsonObject().get("analysis").getAsJsonObject().get("rightSizing");
        JsonArray tradeArray = element.getAsJsonArray();
        String output =   slackUtils.getTableResponseForRightSizing(tradeArray, Integer.parseInt(limit));
        SlashCommandResponse cmdResp = new SlashCommandResponse();
        cmdResp.setResponseType("ephemeral");
        cmdResp.setText(output);
        return cmdResp;
    }
}
