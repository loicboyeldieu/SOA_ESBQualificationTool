package com.esbqualificationtool.flowlauncher;

import com.esbqualificationtool.consumerlauncher.*;
import com.esbqualificationtool.requesttoproducer.RequestToProducer1;
import com.esbqualificationtool.jaxbhandler.JAXBFlowHandler;
import com.esbqualificationtool.jaxbhandler.ProducerType;
import com.esbqualificationtool.jaxbhandler.Scenario.Flow;
import com.esbqualificationtool.jaxbhandler.Scenario.Flow.Request;
import com.esbqualificationtool.mq.SenderToResultQueue;
import com.esbqualificationtool.requesttoproducer.RequestToProducer2;
import com.esbqualificationtool.requesttoproducer.RequestToProducerAbstract;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlowLauncher {

    public static final String P1 = ProducerType.PRODUCER_1.value();
    public static final String P2 = ProducerType.PRODUCER_2.value();
    
    //private static final String END_FLOW_RESULTS = "FLOW_END";
    public static final int TIME_IN_SEC_TO_WAIT_BEFORE_END = 1;
    private ArrayList requestToProducerList = new ArrayList();

    public void launchFlows(String flowsString) {
        JAXBFlowHandler jaxbFlowHandler = new JAXBFlowHandler(flowsString);
        Flow flow = jaxbFlowHandler.getFlow();
        long startTime = System.currentTimeMillis();
        long endTime = startTime + flow.getTotalExecTimeInSec() * 1000;

        // wait before executing
        try {
            Thread.sleep(flow.getStartTimeInMs());
        } catch (InterruptedException ex) {
            Logger.getLogger(FlowLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (System.currentTimeMillis() < endTime) {
            try {
                for (Request request : flow.getRequest()) {
                    String producer = request.getProducer().value();

                    if (producer.equals(P1)) {
                        RequestToProducer1 requestToProducer1 = new RequestToProducer1(request, flow);
                        requestToProducerList.add(requestToProducer1);
                        requestToProducer1.start();
                    } // Ajouter des producers ici
                    else if (producer.equals(P2)) {
                        RequestToProducer2 requestToProducer2 = new RequestToProducer2(request, flow);
                        requestToProducerList.add(requestToProducer2);
                        requestToProducer2.start();
                    } else {
                        System.err.println("[Flow Launcher] Producer not recognized ! No message sent ");
                    }

                    System.out.println("[Flow Launcher] Request - " + request.getId() + " - has been invoked");
                    Thread.sleep(flow.getDelayBetweenEachRequestInMs());
                }
                System.out.println("[Flow Launcher] Flow - " + flow.getId() + " - has been executed");
                Thread.sleep(flow.getFrequencyInSec() * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ConsumerLauncher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // here the total time execution has been reached, we kill all pending thread 
        // and send END_FLOW to queue (for the application)
//        try {
//            Thread.sleep(TIME_IN_SEC_TO_WAIT_BEFORE_END * 1000);
//            System.out.println("[Flow Launcher] Flow - " + flow.getId() + " total time execution exceed");
//
//            stopFlows();
//            SenderToResultQueue endSender = new SenderToResultQueue();
//            endSender.sendToResultQueue(END_FLOW_RESULTS);
//
//        } catch (InterruptedException ex) {
//            Logger.getLogger(FlowLauncher.class.getName()).log(Level.SEVERE, null, ex);
//        }


    }

    public void stopFlows() {
        int requestToProducers = this.requestToProducerList.size();

        System.out.println("[Flow Launcher]  Method stopFlows() called");
        for (int i = 0; i < requestToProducers; i++) {
            RequestToProducerAbstract requestObject = (RequestToProducerAbstract) requestToProducerList.get(i);
            if (requestObject.isAlive()) {

                String requestFailedResult = requestObject.resultToJSONString();
                requestObject.destroy();
                System.out.println("[Flow Launcher]  1 Thread killed");

                SenderToResultQueue resultFailSender = new SenderToResultQueue();
                resultFailSender.sendToResultQueue(requestFailedResult);
                System.out.println("[Flow Launcher]  1 resultFailed sent");
            }
        }
    }
}
