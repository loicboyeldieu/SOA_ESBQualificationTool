package com.esbqualificationtool.requesttoproducer;

import com.esbqualificationtool.mq.SenderToResultQueue;
import com.esbqualificationtool.consumerlauncher.*;
import com.esbqualificationtool.jaxbhandler.Scenario.Flow;
import com.esbqualificationtool.jaxbhandler.Scenario.Flow.Request;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class RequestToProducerAbstract extends Thread {

    private Request request;
    private Flow flow;
    private long RTT;
    private int success;

    public RequestToProducerAbstract(Request request, Flow flow) {
        this.request = request;
        this.flow = flow;
        this.RTT = -1;
        this.success = 0;
    }

    @Override
    public void run() {

        System.out.println(" [RequestToProducer] Thread started");

        long startTime = System.currentTimeMillis();
        callESBQualificationToolService(request.getMessageSize(), request.getProcessingTimeInMs());
        long endTime = System.currentTimeMillis();

        //Send results to the queue before the thread is over
        this.RTT = (endTime - startTime) - request.getProcessingTimeInMs();
        this.success = 100;

        SenderToResultQueue resultSender = new SenderToResultQueue();
        resultSender.sendToResultQueue(resultToJSONString());

        System.out.println(" [RequestToProducer] Thread ended");
    }

    public String resultToJSONString() {
        String result = null;
        try {
            JSONObject jsonRequestResult = new JSONObject(request);
            if (success != 0) {
                jsonRequestResult.put("RTT", RTT);
            }
            jsonRequestResult.put("producer", request.getProducer().value());
            jsonRequestResult.put("consumer", flow.getConsumer());
            jsonRequestResult.put("flowId", flow.getId());
            jsonRequestResult.put("Success", success);
            result = jsonRequestResult.toString();

        } catch (JSONException ex) {
            Logger.getLogger(RequestToProducerAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public abstract byte[] callESBQualificationToolService(int messageSize, int processingTime);
}
