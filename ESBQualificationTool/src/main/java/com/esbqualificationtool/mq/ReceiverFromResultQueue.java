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


    // TODO Configuration file
    public static final String FILE_TEMP = "resultsFileTemp";
    private static final String EXCHANGE_NAME = "requestResult";
    
    private static final String QUEUE_HOST = "192.168.0.104";
    private static final int CONSUMERS_NUMBER = 2;

    private static final String SEND_TO_BROADCAST_KEY = ".all";

    private static final String END_FLOWS_TOKEN = "SCENARIO_END_OF_FLOWS";
    private static final String START_ACTION = "START";
    private static final String STOP_ACTION = "STOP";
    private static final String READY_MESSAGE = "READY";
    private static final String FLOW_STOPED_MESSAGE = "FLOW_STOPPED";

    private Scenario scenario;
    private int consumerReadyMessages;
    private int consumerStoppedMessages;
    private ESBQualificationToolController controller;
    private boolean needToBeTerminated;
    private Connection connection;
    private Channel channel;

    public ReceiverFromResultQueue(Scenario scenario, ESBQualificationToolController controller) {
        this.scenario = scenario;
        this.consumerReadyMessages = 0;
        this.consumerStoppedMessages = 0;
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

                    System.out.println("[ReceiverFromResultsQueue - ConsumerHandleDelivery] Result received: " + resultString);

                    if (resultString.equals(READY_MESSAGE)) {
                        consumerReadyMessages++;
                        System.out.println("[ReceiverFromResultsQueue - ConsumerHandleDelivery] Ready message received");
                        if (consumerReadyMessages == CONSUMERS_NUMBER) {
                            // TODO Allow the start button to be clicked
                            // And put the following code to the start button
                            
                            System.out.println("[ReceiverFromResultsQueue - ConsumerHandleDelivery] All Consumers are ready to start");
                        }
                    } else if (resultString.equals("ENDED_ALL_REQUEST_TREATED")) {
                        System.out.println("[ReceiverFromResultsQueue - ConsumerHandleDelivery] Application has been executed");
                        needToBeTerminated = true;

                    }
                    else if (resultString.equals(FLOW_STOPED_MESSAGE)){
                        consumerStoppedMessages++;
                        System.out.println("[ReceiverFromResultsQueue - ConsumerHandleDelivery] Ready message received");
                        if (consumerStoppedMessages == CONSUMERS_NUMBER) {
                            needToBeTerminated = true;
                        }
                    }
                    else {
                        System.out.println("[ReceiverFromResultsQueue - ConsumerHandleDelivery] Add results received to a text file");
                        FileWriter writer = new FileWriter(FILE_TEMP, true);
                        writer.write(resultString);
                        writer.close();
                        System.out.println("SCENARIO NAME: "+scenario.getName());

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
            System.out.println("[ReceiverFromResultQueue] Received result and thread is terminated");
        } catch (Exception ex) {
            controller.informErrorToView("Error during receiving : " + ex.getLocalizedMessage(), ex.getMessage());
        }

    }
}
