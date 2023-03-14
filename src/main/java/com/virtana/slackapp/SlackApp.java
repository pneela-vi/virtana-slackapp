package com.virtana.slackapp;

import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.bolt.App;
import com.slack.api.model.block.LayoutBlock;
import com.virtana.slackapp.service.CommandHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;


@Component
public class SlackApp {
    public static void main(String[] args) throws Exception {

        // App expects env variables (SLACK_BOT_TOKEN, SLACK_SIGNING_SECRET)
        App app = new App();

        app.command("/virtana", (req, ctx) -> {
            String commandParams = req.getPayload().getText();
            CommandHandlerImpl chl = new CommandHandlerImpl();
            String response;
            try {
                 response = chl.ipmDashboard();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ctx.respond(response);
            //LayoutBlock
            return ctx.ack();
        });

        SlackAppServer server = new SlackAppServer(app);
        server.start(); // http://localhost:3000/slack/events
    }
}
