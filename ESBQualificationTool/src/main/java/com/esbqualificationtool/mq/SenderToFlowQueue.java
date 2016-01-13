package com.esbqualificationtool.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SenderToFlowQueue {

    private String exchange_name;
    private static final String QUEUE_HOST = "192.168.0.104";

    public SenderToFlowQueue(String exchange_name) {
        this.exchange_name = exchange_name;
    }

//  private static final String xmlTest = "<flow id=\"1\"> " +
//                    "<consumer>consumer1</consumer>" +
//                    "<totalExecTimeInSec>10</totalExecTimeInSec>" +
//                    "<frequencyInSec>2</frequencyInSec>" +
//                    "<delayBetweenEachRequestInMs>0</delayBetweenEachRequestInMs>" +
//                    "<request id=\"1\">" +
//                    "<producer>producer1</producer>" +
//                    "<messageSize>2</messageSize>" +
//                    "<processingTimeInMs>0</processingTimeInMs>" +
//                    "</request>" +
//                    "<request id=\"2\">" +
//                    "<producer>producer1</producer>" +
//                    "<messageSize>5</messageSize>" +
//                    "<processingTimeInMs>0</processingTimeInMs>" +
//                    "</request>" +
//                    "</flow>";
    public void sendFlowStringToQueue(String flowString) {
        try {

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(QUEUE_HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(exchange_name, "fanout");


            channel.basicPublish(exchange_name, "", null, flowString.getBytes());
            System.out.println(" [SendertoFlow] Sent : " + flowString + "'");


            channel.close();
            connection.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (TimeoutException ex) {
            ex.printStackTrace();
        }
    }
}
