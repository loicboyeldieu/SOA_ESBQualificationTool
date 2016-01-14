package com.esbqualificationtool.flowlauncher;

import com.esbqualificationtool.consumerlauncher.*;
import com.esbqualificationtool.requesttoproducer.RequestToProducer1;
import com.esbqualificationtool.jaxbhandler.JAXBFlowHandler;
import com.esbqualificationtool.jaxbhandler.ProducerType;
import com.esbqualificationtool.jaxbhandler.Scenario.Flow;
import com.esbqualificationtool.jaxbhandler.Scenario.Flow.Request;
import com.esbqualificationtool.requesttoproducer.RequestToProducer2;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlowLauncher {

    public static final String P1 = ProducerType.PRODUCER_1.value() ;
    public static final String P2 = "producer2" ;


    public void launchFlows(String flowsString){
        JAXBFlowHandler jaxbFlowHandler = new JAXBFlowHandler(flowsString);
        Flow flow = jaxbFlowHandler.getFlow();
        long startTime = System.currentTimeMillis();
        long endTime = startTime + flow.getTotalExecTimeInSec()*1000;
        while (System.currentTimeMillis() < endTime) {
            try {
                for (Request request : flow.getRequest()) {
                    String producer = request.getProducer().value();

                    if (producer.equals(P1)){
                        RequestToProducer1 requestToProducer1 = new RequestToProducer1(request, flow);
                        requestToProducer1.start(); 
                    }
                    // Ajouter des producers ici
                    else if (producer.equals(P2)){
                        RequestToProducer2 requestToProducer2 = new RequestToProducer2(request, flow);
                        requestToProducer2.start();
                    }
                    else {
                        System.err.println("[Flow Launcher] Producer not recognized ! No message sent ") ; 
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
    }

}
