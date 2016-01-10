package com.esbqualificationtool.consumerlauncher;

public class Consumer{

    private int requestNumber;
    private int messageSize;
    private int producerProcessingTime;

    public Consumer(int requestNumber, int byteSize, int processingTime){
        this.requestNumber = requestNumber;
        this.messageSize = byteSize;
        this.producerProcessingTime = processingTime;
    }

    public void doRequest(){
        for (int i=1; i<= requestNumber; i++){
            RequestToProducer request = new RequestToProducer(messageSize, producerProcessingTime);
            request.start();
        }
    }

    public int getRequestNumber(){
        return this.requestNumber;
    }

    public int getbyteSize(){
        return this.messageSize;
    }

    public int getProcessingTime(){
        return this.producerProcessingTime;
    }

    public void setRequestNumber(int requestNumber){
        this.requestNumber = requestNumber;
    }

    public void setByteSize(int byteSize){
        this.messageSize = byteSize;
    }

    public void setProcessingTime(int processingTime){
        this.producerProcessingTime = processingTime;
    }
}
