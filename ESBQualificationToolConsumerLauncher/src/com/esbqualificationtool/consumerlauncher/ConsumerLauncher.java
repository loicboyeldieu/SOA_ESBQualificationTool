package com.esbqualificationtool.consumerlauncher;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsumerLauncher {

    public static void main(String[] args){

        while(true){

            // Read the queue and take only message from the Consumer
            // and to the Producer we predifined for that launcher

            // get this data from the queue (from the xml)
            // It means I want to do 5 request every 1000ms (freq) during 1h
            int requestNumber = 0;       // number of request
            int processingTime = 0;      // in ms
            int messageSize = 0;            // in byte
            int frequency = 0;           // in ms
            int executionLengthTime = 0; // in ms

            long startTime = System.currentTimeMillis();
            long endTime = startTime + executionLengthTime;

            while (System.currentTimeMillis() < endTime){

                try {
                    Consumer consumer = new Consumer(requestNumber, messageSize, processingTime);
                    consumer.doRequest();
                    // We wait the next wave of requests
                    Thread.sleep(frequency);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ConsumerLauncher.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }

        }

    }

}
