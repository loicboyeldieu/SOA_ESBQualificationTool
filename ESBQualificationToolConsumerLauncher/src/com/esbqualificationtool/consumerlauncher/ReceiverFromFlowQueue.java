package com.esbqualificationtool.consumerlauncher;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiverFromFlowQueue {

    private static final String EXCHANGE_NAME = "consumer1";
    private static final String HOST = "192.168.0.104";

    public void receiveFlows() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, "");
            System.out.println(" FLOW QUEUE [*] Waiting for messages. To exit press CTRL+C");
            Consumer consumer = new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    FlowLauncher flowLauncher = new FlowLauncher();
                    flowLauncher.launchFlows(message);

                }
            };
            channel.basicConsume(queueName, true, consumer);

        } catch (TimeoutException ex) {
            Logger.getLogger(ConsumerLauncher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsumerLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
