package com.virtana.slackapp.handlers;

import java.io.IOException;

public interface CommandHandler {
    public String ipmDashboard() throws IOException, InterruptedException;
    public String usersByOrg();

    public String rightSizingDashboard();
}

