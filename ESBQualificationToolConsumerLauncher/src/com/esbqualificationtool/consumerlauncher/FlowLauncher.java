package com.esbqualificationtool.consumerlauncher;

import com.esbqualificationtool.jaxbhandler.JAXBFlowHandler;
import com.esbqualificationtool.jaxbhandler.ProducerType;
import com.esbqualificationtool.jaxbhandler.Scenario.Flow;
import com.esbqualificationtool.jaxbhandler.Scenario.Flow.Request;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlowLauncher {

    public void launchFlows(String flowsString){
        JAXBFlowHandler jaxbFlowHandler = new JAXBFlowHandler(flowsString);
        Flow flow = jaxbFlowHandler.getFlow();
        long startTime = System.currentTimeMillis();
        long endTime = startTime + flow.getTotalExecTimeInSec()*1000;
        while (System.currentTimeMillis() < endTime) {
            try {
                for (Request request : flow.getRequest()) {
                    String producer = request.getProducer().value();

                    if (producer.equals(ProducerType.PRODUCER_1.value())){
                        RequestToProducer1 requestToProducer1 = new RequestToProducer1(request, flow);
                        requestToProducer1.start(); 
                    }
                    // Ajouter des producers ici
                    else {
                        
                    }

                    System.out.println("request - " + request.getId() + " - has been invoked");
                    Thread.sleep(flow.getDelayBetweenEachRequestInMs());
                }
                System.out.println("flow - " + flow.getId() + " - has been executed");
                Thread.sleep(flow.getFrequencyInSec() * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ConsumerLauncher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
