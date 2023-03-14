package com.virtana.slackapp;


import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;
import com.virtana.slackapp.handlers.CommandHandlerImpl;
import org.springframework.stereotype.Component;


@Component
public class SlackApp {
    public static void main(String[] args) throws Exception {

        // App expects env variables (SLACK_BOT_TOKEN, SLACK_SIGNING_SECRET)
       // SLACK_BOT_TOKEN=xoxb-5159708801-4935972652371-YrrRiX6Pay5NtSYMiXIVLGDI
      //  SLACK_SIGNING_SECRET=aa5fe565b7cadc9e4d242c02fe76cf4c
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
