package com.esbqualificationtool.consumerlauncher;

import com.esbqualificationtool.jaxbhandler.Scenario.Flow.Request;

public abstract class RequestToProducerAbstract extends Thread {

    private Request request;
    private long RTT;
    private int success;

    public RequestToProducerAbstract(Request request) {
        this.request = request;
        this.RTT = -1;
        this.success = 0;
    }

    public void run(){
        long startTime = System.currentTimeMillis();
        callESBQualificationToolService(request.getMessageSize(), request.getProcessingTimeInMs());
        long endTime = System.currentTimeMillis();
        this.RTT = (endTime - startTime) - request.getProcessingTimeInMs();

        //TODO Send results to the queue before the thread is over

    }

    public abstract byte[] callESBQualificationToolService(int messageSize, int processingTime);

}
