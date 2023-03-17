package com.virtana.slackapp.handlers;

import java.io.IOException;

public interface CommandHandler {
    public String ipmDashboard() throws IOException, InterruptedException;
    public String usersByOrg(String limit) throws IOException, InterruptedException;

    public String idleResourcesDashboard() throws IOException, InterruptedException;
    public String rightSizingDashboard() throws IOException, InterruptedException;
}

