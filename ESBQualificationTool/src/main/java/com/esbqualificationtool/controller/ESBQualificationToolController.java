package com.esbqualificationtool.controller;

import com.esbqualificationtool.jaxbhandler.JAXBScenarioHandler;
import com.esbqualificationtool.jaxbhandler.*;
import com.esbqualificationtool.jaxbhandler.Scenario.Flow;
import com.esbqualificationtool.mq.ReceiverFromResultQueue;
import com.esbqualificationtool.mq.SenderToFlowQueue;
import com.esbqualificationtool.view.UserBehaviour;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ESBQualificationToolController {

    public void startApplication() {
        System.out.println("[Controller] Start");

        JAXBScenarioHandler jaxbScenarioHandler = new JAXBScenarioHandler("src/main/java/com/esbqualificationtool/resources/ScenarioExample.xml");

        Scenario scenario = jaxbScenarioHandler.getScenario();

        System.out.println("[Controller] Scenario parsed");

        for (int i = 0; i < scenario.getFlow().size(); i++) {
            Flow flow = (Flow) scenario.getFlow().get(i);
            String flowString = jaxbScenarioHandler.getFlowXMLStringFromFlowObject(flow) ; 

            ConsumerType consumer = flow.getConsumer();
            SenderToFlowQueue queueSender = new SenderToFlowQueue(consumer.value());
            queueSender.sendFlowStringToQueue(flowString);
            System.out.println("[Controller] Sent one flow to queue");
        }

        //UserBehaviour user = new UserBehaviour(5);
        //user.start();

        ReceiverFromResultQueue receiverFromResultQueue = new ReceiverFromResultQueue(scenario);
        System.out.println("[Controller] Ready to receive results") ;
        try {
            receiverFromResultQueue.receiveAllRequestResultsFromExecution();
            // result handler
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (TimeoutException ex) {
            ex.printStackTrace();
        }

        // result handler


    }
}
