package com.esbqualificationtool.flowlauncher;

import java.util.ArrayList;

public class FlowLauncherExecutor extends Thread {

    private ArrayList messageFlowsList;
    private ArrayList flowLauncherList;

    public FlowLauncherExecutor(ArrayList messageFlowsList) {
        this.messageFlowsList = messageFlowsList;
        this.flowLauncherList = new ArrayList();
    }

    @Override
    public void run() {
        for (int i = 0; i < messageFlowsList.size(); i++) {
            String flowMessage = (String) messageFlowsList.get(i);
            FlowLauncher flowLauncher = new FlowLauncher();
            flowLauncherList.add(flowLauncher);
            flowLauncher.launchFlows(flowMessage);
        }
    }

    public void stopFlowExecution() {
        for (int i = 0; i < flowLauncherList.size(); i++) {
            FlowLauncher flowLauncher = (FlowLauncher) flowLauncherList.get(i);
            flowLauncher.stopFlows();
        }
    }
}
