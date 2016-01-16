package com.esbqualificationtool.mq;

import com.esbqualificationtool.controller.ESBQualificationToolController;
import com.esbqualificationtool.jaxbhandler.Scenario;
import com.esbqualificationtool.model.ElasticsearchUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import javax.swing.JOptionPane;

// This class should be a singleton
// There can be only one ResultQueue created.
public class ReceiverFromResultQueue extends Thread {

    public static final String FILE_TEMP = "resultsFileTemp";
    private static final String EXCHANGE_NAME = "requestResult";
    private static final String END_FLOW_RESULTS = "FLOW_END";
    private static final String SCENARIO_ABORTED = "SCENARIO_ABORTED";
    private static final String QUEUE_HOST = "192.168.0.104";
    private Scenario scenario;
    private int endFlowMessagesReceived;
    private ESBQualificationToolController controller;
    private boolean needToBeTerminated;
    private Connection connection;
    private Channel channel;

    public ReceiverFromResultQueue(Scenario scenario, ESBQualificationToolController controller) {
        this.scenario = scenario;
        this.endFlowMessagesReceived = 0;
        this.controller = controller;
        this.needToBeTerminated = false;
        connection = null;
        channel = null;
    }

    public Channel getChannel() {
        return channel;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isNeedToBeTerminated() {
        return needToBeTerminated;
    }

    public void run() {
        try {

            // file temp containing all text results resetting
            FileWriter writer = null;
            writer = new FileWriter(FILE_TEMP, false);
            writer.close();

            final int flows = scenario.getFlow().size();
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(QUEUE_HOST);
            connection = factory.newConnection();
            channel = connection.createChannel();
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
                            System.out.println("[ReceiverFromResultsQueue - ConsumerHandleDelivery] Scenario's end has been reached");
                            needToBeTerminated = true;
                        }
                    } else if (resultString.equals(SCENARIO_ABORTED)) {
                        System.out.println("[ReceiverFromResultsQueue - ConsumerHandleDelivery] User has decided to stop application");
                        needToBeTerminated = true;

                    } else {
                        System.out.println("[ReceiverFromResultsQueue - ConsumerHandleDelivery] Add results received to a text file");
                        FileWriter writer = new FileWriter(FILE_TEMP, true);
                        writer.write(resultString);
                        writer.close();
                        System.out.println("[ReceiverFromResultsQueue - ConsumerHandleDelivery] Ready to index " + resultString);
                        ElasticsearchUtils.indexToES(resultString, scenario.getName());
                    }

                    if (needToBeTerminated) {
                        System.out.println("[ReceiverFromResultsQueue - ConsumerHandleDelivery] Ready to cancel the connection");
                        try {
                            this.getChannel().basicCancel(consumerTag);
                            this.getChannel().close();
                            this.getChannel().getConnection().close();
                            System.out.println("[ReceiverFromResultsQueue - ConsumerHandleDelivery] Connection with queue cancelled");
                        } catch (IOException ex) {
                            controller.getView().displayPopUp("Error IO closing queue", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
                        } catch (TimeoutException ex) {
                            controller.getView().displayPopUp("Error Timeout closing queue", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
                        }
                    }


                }
            };
            channel.basicConsume(queueName, true, consumer);
            System.out.println("[ReceiverFromResultQueue] Thread is terminated");
        } catch (Exception ex) {
            controller.informErrorToView("Error during receiving : " + ex.getLocalizedMessage(), ex.getMessage());
        }

    }
}
