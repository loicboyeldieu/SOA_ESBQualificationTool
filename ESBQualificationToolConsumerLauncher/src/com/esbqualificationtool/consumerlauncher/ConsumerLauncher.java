package com.esbqualificationtool.consumerlauncher;

import com.esbqualificationtool.mq.ReceiverFromFlowQueue;

public class ConsumerLauncher {

    private static String ConsumerName;

    public static void main(String[] args) {

        if (!(args.length < 1)) {

            ConsumerName = args[0] ;
            ReceiverFromFlowQueue receiver = new ReceiverFromFlowQueue(ConsumerName);
            System.out.println(" [Consumer Launcher] Consumer launcher is starting...");
            System.out.println(" [Consumer Launcher] Ready to receive flows.");
            receiver.receiveFlows();

        } else {
            System.out.println(" [Consumer Launcher] Please provide a consumer name in argument");
        }


    }
}
