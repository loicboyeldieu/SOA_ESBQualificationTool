package com.esbqualificationtool.controller;

import com.esbqualificationtool.jaxbhandler.Scenario;
import com.esbqualificationtool.model.ElasticsearchUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Receiver {

    public static void main(String[] args) throws IOException, TimeoutException{
        receiveAllRequestResultsFromExecution();
    }

    private static final String EXCHANGE_NAME = "test2";
    private static final String QUEUE_HOST = "192.168.0.104";


    public static void receiveAllRequestResultsFromExecution() throws IOException, TimeoutException {

        // file temp init



        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(QUEUE_HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        System.out.println("[ReceiverFromResultsQueue] Waiting for results...");
        Consumer consumer = new DefaultConsumer(channel) {

            //@Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String resultString = new String(body, "UTF-8");
                System.out.println(resultString);

            }
        };
        channel.basicConsume(queueName, true, consumer);

    }
}
