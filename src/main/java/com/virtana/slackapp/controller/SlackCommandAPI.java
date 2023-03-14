package com.virtana.slackapp.controller;

import com.slack.api.bolt.App;
import com.slack.api.bolt.context.builtin.EventContext;
import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import com.virtana.slackapp.service.CommandHandlerImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.slack.api.bolt.context.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

@RestController
public class SlackCommandAPI {

    @Autowired
    CommandHandlerImpl chl;
    @PostMapping(value = "/event")
    public void slackCommand() throws IOException, InterruptedException, SlackApiException {

        App app = new App();

        app.command("/hellos", (req, ctx) -> {
            System.out.println("here@@@@@@@@@");
            return ctx.ack(":wave: Hello!");
        });
        String resp = chl.ipmDashboard();


    }
}
