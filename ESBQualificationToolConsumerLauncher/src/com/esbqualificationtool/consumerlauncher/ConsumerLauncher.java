package com.esbqualificationtool.consumerlauncher;

import com.esbqualificationtool.mq.ReceiverFromFlowQueue;

public class ConsumerLauncher {

    

    public static void main(String[] args) {

        if (!(args.length < 0)) {

            String ConsumerName = "consumer1" ;
            ReceiverFromFlowQueue receiver = new ReceiverFromFlowQueue(ConsumerName);
            System.out.println(" [Consumer Launcher] Consumer launcher is starting...");
            System.out.println(" [Consumer Launcher] Ready to receive flows.");
            receiver.receiveFlows();

        } else {
            System.out.println(" [Consumer Launcher] Please provide a consumer name in argument");
        }


    }
}
