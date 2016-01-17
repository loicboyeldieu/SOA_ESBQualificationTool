package com.esbqualificationtool.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SenderToFlowQueue {

    public static final String EXCHANGE_NAME = "flowQueue";
    private static final String QUEUE_HOST = "192.168.0.104";

    public SenderToFlowQueue() {
       
    }

   
    public void sendFlowStringToQueue(String routingKey, String flowString) {
        try {

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(QUEUE_HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");


            channel.basicPublish(EXCHANGE_NAME, routingKey, null, flowString.getBytes());
            System.out.println(" [SendertoFlow] Sent : " + routingKey +": " + flowString + "'");


            channel.close();
            connection.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (TimeoutException ex) {
            ex.printStackTrace();
        }
    }
}
