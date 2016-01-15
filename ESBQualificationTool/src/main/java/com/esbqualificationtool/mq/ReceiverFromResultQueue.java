package com.esbqualificationtool.mq;

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

// This class should be a singleton
// There can be only one ResultQueue created.
public class ReceiverFromResultQueue {

    private static final String EXCHANGE_NAME = "requestResult";
    private static final String END_FLOW_RESULTS = "FLOW_END";
    private static final String SCENARIO_ABORTED = "SCENARIO_ABORTED";

    private static final String QUEUE_HOST = "192.168.0.104";
    private Scenario scenario;
    private int endFlowMessagesReceived;

    public ReceiverFromResultQueue(Scenario scenario) {
        this.scenario = scenario;
        this.endFlowMessagesReceived = 0;
    }

    public void receiveAllRequestResultsFromExecution() throws IOException, TimeoutException {

        // file temp init

        final int flows = scenario.getFlow().size();

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
                System.out.println("[ReceiverFromResultsQueue - ConsumerHandleDelivery] One Result received ! ");
                if (resultString.equals(END_FLOW_RESULTS)) {
                    endFlowMessagesReceived++;
                    System.out.println("[ReceiverFromResultsQueue - ConsumerHandleDelivery] End message received");
                    if (endFlowMessagesReceived == flows) {
                        System.out.println("Scenario's end has been reached");
                        System.exit(0);
                    }
                }
                else if (resultString.equals(SCENARIO_ABORTED)){
                    System.out.println("User has decided to stop application");
                    System.exit(0);
                }
                else {
                    System.out.println("[ReceiverFromResultsQueue - ConsumerHandleDelivery] Ready to index " + resultString);

                    // file temp handler 
                    ElasticsearchUtils.indexToES(resultString, scenario.getName());
                }
            }
        };
        channel.basicConsume(queueName, true, consumer);

    }
}
