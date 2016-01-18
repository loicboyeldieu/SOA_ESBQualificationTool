package com.esbqualificationtool.controller;

import com.esbqualificationtool.mq.SenderToFlowQueue;

public class ScenarioStopExecutor extends Thread {

    private static final String SEND_TO_BROADCAST_KEY = ".all";
    private static final String STOP_ACTION = "STOP";

    public void run() {
        SenderToFlowQueue sender = new SenderToFlowQueue();
        sender.sendFlowStringToQueue(SEND_TO_BROADCAST_KEY, STOP_ACTION);
    }
}
