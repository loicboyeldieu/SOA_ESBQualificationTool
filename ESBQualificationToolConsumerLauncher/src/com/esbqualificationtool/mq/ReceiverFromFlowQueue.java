package com.esbqualificationtool.mq;

import com.esbqualificationtool.flowlauncher.FlowLauncher;
import com.esbqualificationtool.consumerlauncher.*;
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
    private static final String END_FLOW_RESULTS = "FLOW_END";
    private static final String SCENARIO_ABORTED = "SCENARIO_ABORTED";
    private String exchange_name;
    private ArrayList flowLauncherList;

    public ReceiverFromFlowQueue(String exchange_name) {
        this.exchange_name = exchange_name;
        flowLauncherList = new ArrayList();
    }

    public void receiveFlows() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(FLOW_QUEUE_HOST);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(exchange_name, "fanout");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, exchange_name, "");
            System.out.println("[ReceiverFromFlowQueue] Waiting for messages. To exit press CTRL+C");
            Consumer consumer = new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("[ReceiverFromFlowQueue - Consumer] Message received from queue !");
                    String message = new String(body, "UTF-8");
                    System.out.println("!!!" + message + "!!!");
                    if (message.equals("STOP_SIGNAL")) {
                        for (int i=0; i<flowLauncherList.size(); i++){
                            FlowLauncher flowLauncher = (FlowLauncher) flowLauncherList.get(i);
                            flowLauncher.stopFlows();
                            SenderToResultQueue sender = new SenderToResultQueue();
                            sender.sendToResultQueue(SCENARIO_ABORTED);
                        }
                    } else {
                        FlowLauncher flowLauncher = new FlowLauncher();
                        flowLauncherList.add(flowLauncher);
                        flowLauncher.launchFlows(message);
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
