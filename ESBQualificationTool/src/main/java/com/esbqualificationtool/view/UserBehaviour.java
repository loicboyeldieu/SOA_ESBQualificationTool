package com.esbqualificationtool.view;

import com.esbqualificationtool.mq.SenderToFlowQueue;
import java.util.ArrayList;

public class UserBehaviour extends Thread{

    private int userTimeBeforeStopApplicationInSec;
    private ArrayList consumerList;

    public UserBehaviour(int timeBeforeStop){
        this.userTimeBeforeStopApplicationInSec = timeBeforeStop;
        consumerList = new ArrayList();
        for (int i=0; i<=10; i++){
            this.consumerList.add("consumer"+i);
        }
    }

    public void run(){
        try {
            Thread.sleep(userTimeBeforeStopApplicationInSec * 1000);
            for (int i=0; i<10; i++){
                System.out.println((String)consumerList.get(i));
                //SenderToFlowQueue sender = new SenderToFlowQueue((String)consumerList.get(i));
                //sender.sendFlowStringToQueue("STOP_SIGNAL");
            }
            
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }

}
