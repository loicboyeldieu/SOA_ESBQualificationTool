package com.esbqualificationtool.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SenderToResultQueue {

    public static final String EXCHANGE_NAME = "requestResult";
    public static final String RESULT_QUEUE_HOST = "192.168.0.104";

    public void sendToResultQueue(String result) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(RESULT_QUEUE_HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            channel.basicPublish(EXCHANGE_NAME, "", null, result.getBytes());
            System.out.println("[SenderToResultQueue] Thread sent '" + result + " to result queue successfully.");

            channel.close();
            connection.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (TimeoutException ex) {
            ex.printStackTrace();
        }

    }
}
