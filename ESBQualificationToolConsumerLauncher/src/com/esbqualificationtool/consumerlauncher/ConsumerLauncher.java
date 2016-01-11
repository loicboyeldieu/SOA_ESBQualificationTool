package com.esbqualificationtool.consumerlauncher;

import com.esbqualificationtool.jaxbhandler.JAXBFlowHandler;
import com.esbqualificationtool.jaxbhandler.Scenario.Flow;
import com.esbqualificationtool.jaxbhandler.Scenario.Flow.Request;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsumerLauncher {

    public static void main(String[] args){

        while(true){
            System.out.println("Flow launching.");
            // Read the queue and take only message from the Consumer
            // and to the Producer we predifined for that launcher
            String flowStringFromQueue = "<flow id=\"1\"> " +
                "<consumer>consumer1</consumer>" +
                "<totalExecTimeInSec>30</totalExecTimeInSec>" +
                "<frequencyInSec>10</frequencyInSec>" +
                "<delayBetweenEachRequestInMs>0</delayBetweenEachRequestInMs>" +
                "<request id=\"1\">" +
                "<producer>producer1</producer>" +
                "<messageSize>2</messageSize>" +
                "<processingTimeInMs>0</processingTimeInMs>" +
                "</request>" +
                "<request id=\"2\">" +
                "<producer>producer1</producer>" +
                "<messageSize>5</messageSize>" +
                "<processingTimeInMs>0</processingTimeInMs>" +
                "</request>" +
                "</flow>";

            JAXBFlowHandler jaxbFlowHandler = new JAXBFlowHandler(flowStringFromQueue);
            Flow flow = jaxbFlowHandler.getFlow();

            long startTime = System.currentTimeMillis();
            long endTime = startTime + flow.getTotalExecTimeInSec();

            while (System.currentTimeMillis() < endTime){

                try {
                    for (Request request : flow.getRequest()){
                        RequestToProducer requestToProducer = new RequestToProducer(request);
                        requestToProducer.start();
                        System.out.println("request - " + request.getId() + " - has been invoked");
                        Thread.sleep(flow.getDelayBetweenEachRequestInMs());
                    }
                    System.out.println("flow - " + flow.getId() + " - has been executed");
                    Thread.sleep(flow.getFrequencyInSec()*1000);
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(ConsumerLauncher.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }

        }

    }

}
