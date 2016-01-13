package com.esbqualificationtool.controller;

import com.esbqualificationtool.jaxbhandler.JAXBScenarioHandler;
import com.esbqualificationtool.jaxbhandler.Scenario;
import com.esbqualificationtool.jaxbhandler.Scenario.Flow;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class ESBQualificationToolController {

    private static final String EXCHANGE_NAME = "consumer1";
    private static final String HOST = "192.168.0.104";

    public void startApplication() {
        System.out.println("ApplicationStarted");
        sendScenarioToQueue();
    }

    public void sendScenarioToQueue(){
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            String message = "<flow id=\"1\"> " +
                    "<consumer>consumer1</consumer>" +
                    "<totalExecTimeInSec>10</totalExecTimeInSec>" +
                    "<frequencyInSec>2</frequencyInSec>" +
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


            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            channel.close();
            connection.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (TimeoutException ex) {
            ex.printStackTrace();
        }
    }

    public void manageScenario(){
        // Test if XLM is valid

        JAXBScenarioHandler jaxbScenarioHandler = new JAXBScenarioHandler("/home/ubuntu/Programmation/SOA_ESB/SOA_ESBQualificationTool/ESBQualificationTool/src/main/java/com/esbqualificationtool/resources/ScenarioExample.xml");
        Scenario scenario = jaxbScenarioHandler.getScenario();

        for (int i = 0 ; i < scenario.getFlow().size(); i++){
            String flowString = jaxbScenarioHandler.flowXMLStringFromIndex(scenario.getFlow(), i);
        }

    }

}
