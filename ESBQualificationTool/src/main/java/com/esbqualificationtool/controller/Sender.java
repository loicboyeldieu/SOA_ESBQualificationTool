package com.esbqualificationtool.controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

    private static final String QUEUE_HOST = "192.168.0.104";
    private static final String EXCHANGE_NAME = "flowQueue";

    public static void main(String[] argv) throws Exception {
        sendToQueue("consumer2.", "FLOW");
        sendToQueue("consumer1.", "CONSU");
        sendToQueue(".all", "STOPALL");

    }

    public static void sendToQueue(String routingKey, String message){
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(QUEUE_HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
            connection.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (TimeoutException ex) {
            ex.printStackTrace();
        }
    }
}
