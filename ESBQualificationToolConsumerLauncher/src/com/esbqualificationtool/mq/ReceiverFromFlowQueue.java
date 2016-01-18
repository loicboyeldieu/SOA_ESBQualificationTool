package com.esbqualificationtool.mq;

import com.esbqualificationtool.flowlauncher.FlowLauncher;
import com.esbqualificationtool.consumerlauncher.*;
import com.esbqualificationtool.flowlauncher.FlowLauncherExecutor;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiverFromFlowQueue {

    private static final String FLOW_QUEUE_HOST = "192.168.0.104";
    private static final String EXCHANGE_NAME = "flowQueue";
    private static final String BROADCAST_TOPIC = "*.all";
    private static final String END_FLOWS_TOKEN = "SCENARIO_END_OF_FLOWS";
    private static final String START_ACTION = "START";
    private static final String STOP_ACTION = "STOP";
    private static final String READY_MESSAGE = "READY";
    private static final String FLOW_STOPED_MESSAGE = "FLOW_STOPPED";
    private String topic;
    private ArrayList messageFlowsList;
    private FlowLauncherExecutor flowExecutor;

    public ReceiverFromFlowQueue(String topic) {
        this.topic = topic;
        this.messageFlowsList = new ArrayList();
        this.flowExecutor = null;
    }

    public void receiveFlows() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(FLOW_QUEUE_HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            String queueName = channel.queueDeclare().getQueue();

            channel.queueBind(queueName, EXCHANGE_NAME, this.topic);
            channel.queueBind(queueName, EXCHANGE_NAME, BROADCAST_TOPIC);

            System.out.println("[ReceiverFromFlowQueue] Waiting for messages. To exit press CTRL+C");
            Consumer consumer = new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("[ReceiverFromFlowQueue - Consumer] Message received from queue !");
                    String message = new String(body, "UTF-8");

                    if (message.equals(START_ACTION)) {
                        System.out.println("START MESSAGE received");
                        flowExecutor.start();

                    } else if (message.equals(STOP_ACTION)) {
                        System.out.println("STOP MESSAGE received");
                        flowExecutor.stopFlowExecution();
                        flowExecutor.stop();
                        messageFlowsList = new ArrayList();
//                        if (flowLauncherExecutor.isAlive()){
//                            System.out.println("#####  EXECUTION IS RUNNING");
//                            flowLauncherExecutor.stopFlowExecution();
//                            System.out.println("#####  ALL FLOWS STOPPED");
//                            flowLauncherExecutor.destroy();
//                        }
                        SenderToResultQueue sender = new SenderToResultQueue();
                        sender.sendToResultQueue(FLOW_STOPED_MESSAGE);

                    } else if (message.equals(END_FLOWS_TOKEN)) {
                        // Send ready to the application
                        SenderToResultQueue sender = new SenderToResultQueue();
                        sender.sendToResultQueue(READY_MESSAGE);
                        flowExecutor = new FlowLauncherExecutor(messageFlowsList);

                    } else {
                        // We received a flow to store in the list
                        messageFlowsList.add(message);
                    }

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
