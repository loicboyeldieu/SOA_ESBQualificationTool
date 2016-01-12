package com.esbqualificationtool.consumerlauncher;

import com.esbqualificationtool.jaxbhandler.JAXBFlowHandler;
import com.esbqualificationtool.jaxbhandler.Scenario.Flow;
import com.esbqualificationtool.jaxbhandler.Scenario.Flow.Request;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlowLauncher {

    public void launchFlows(String flowsString){
        JAXBFlowHandler jaxbFlowHandler = new JAXBFlowHandler(flowsString);
        Flow flow = jaxbFlowHandler.getFlow();
        long startTime = System.currentTimeMillis();
        long endTime = startTime + flow.getTotalExecTimeInSec();
        while (System.currentTimeMillis() < endTime) {
            try {
                for (Request request : flow.getRequest()) {
                    RequestToProducer requestToProducer = new RequestToProducer(request);
                    requestToProducer.start();
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
