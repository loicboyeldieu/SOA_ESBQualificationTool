package com.esbqualificationtool.consumerlauncher;

public class RequestToProducer extends Thread{

    private int id;
    private int messageSize;
    private int producerProcessingTime;
    private long RTT;

    public RequestToProducer(int id, int messageSize, int producerProcessingTime){
        this.id = id;
        this.messageSize = messageSize;
        this.producerProcessingTime = producerProcessingTime;
        this.RTT = 0;
    }

    public void run(){
        long startTime = System.currentTimeMillis();
        callESBQualificationToolService(messageSize, producerProcessingTime);
        long endTime = System.currentTimeMillis();
        this.RTT = (endTime - startTime) - producerProcessingTime;

        //TODO Send results to the queue before the thread is over

    }

    public byte[] callESBQualificationToolService(int messageSize, int processingTime){
        byte[] message = null;

        try {
            // Call Web Service Operation
            esbqualificationtoolcompositeapp.ESBQualificationToolCompositeAppService1 service = new esbqualificationtoolcompositeapp.ESBQualificationToolCompositeAppService1();
            esbqualificationtoolcompositeapp.WebServiceProducer port = service.getCasaPort1();

            // process result here
            message = port.qualificationToolService(messageSize, processingTime);

        } catch (Exception ex) {
            // TODO handle custom exceptions here
            System.out.println("Error: Service has not been invoked.");
        }

        return message;
    }


    public int getProcessingTime(){
        return this.producerProcessingTime;
    }

    public int getMessageSize(){
        return this.messageSize;
    }

    public void setProcessingTime(int processingTime){
        this.producerProcessingTime = processingTime;
    }

    public void setMessageSize(int messageSize){
        this.messageSize = messageSize;
    }
}
