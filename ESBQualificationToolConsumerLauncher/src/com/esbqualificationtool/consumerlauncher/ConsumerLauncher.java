package com.esbqualificationtool.consumerlauncher;

import com.esbqualificationtool.jaxbhandler.ConsumerType;
import com.esbqualificationtool.mq.ReceiverFromFlowQueue;

public class ConsumerLauncher {

    public static void main(String[] args) {

        if (!(args.length < 0)) {

            try {
                ConsumerType consumer = ConsumerType.fromValue(args[0]);

                ReceiverFromFlowQueue receiver = new ReceiverFromFlowQueue(consumer.value());
                System.out.println(" [Consumer Launcher] Consumer launcher is starting...");
                System.out.println(" [Consumer Launcher] Ready to receive flows.");
                receiver.receiveFlows();
            } catch (Exception e) {
                System.out.println(" [Consumer Launcher] Consumer not recognized ! Please provide a valid consumer in argument");
            }

        } else {
            System.out.println(" [Consumer Launcher] Please provide a consumer name in argument");
        }

    }
}
