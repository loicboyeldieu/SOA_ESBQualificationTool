package com.esbqualificationtool.consumerlauncher;

public class ConsumerLauncher {

    public static void main(String[] Args) {
        ReceiverFromFlowQueue receiver = new ReceiverFromFlowQueue();
        receiver.receiveFlows();
    }
}
