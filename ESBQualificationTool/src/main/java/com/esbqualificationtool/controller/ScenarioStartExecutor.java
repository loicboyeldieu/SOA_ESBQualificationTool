package com.esbqualificationtool.controller;

import com.esbqualificationtool.jaxbhandler.Scenario;
import com.esbqualificationtool.model.ScenarioResult;
import com.esbqualificationtool.mq.ReceiverFromResultQueue;
import com.esbqualificationtool.mq.SenderToFlowQueue;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.apache.lucene.util.IOUtils;

public class ScenarioStartExecutor extends Thread {

    private static final String SEND_TO_BROADCAST_KEY = ".all";
    private static final String START_ACTION = "START";


    public ScenarioStartExecutor(){
    }

    public void run() {
        SenderToFlowQueue sender = new SenderToFlowQueue();
        sender.sendFlowStringToQueue(SEND_TO_BROADCAST_KEY, START_ACTION);
    }
//    public void run() {
//
//        // loic's code for start
//
//
//        System.out.println("[ScenarioExecutor] start method called");
//
//        ReceiverFromResultQueue receiverFromResultQueue = new ReceiverFromResultQueue(selectedScenario, controller);
//        System.out.println("[Controller] Ready to receive results");
//
//        try {
//            receiverFromResultQueue.start();
//        } catch (Exception ex) {
//            controller.getView().displayPopUp("Starting scenario", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
//        }
//
//        while(receiverFromResultQueue.isNeedToBeTerminated() == false) {
//            // queue is receiving - thread blocked here until receiving STOP or END
//        }
//
//        System.out.println("[Controller] ReceiverFromResultQueue is terminated");
//
//        ScenarioResult sr = new ScenarioResult(selectedScenario.getName());
//        File input = new File(ReceiverFromResultQueue.FILE_TEMP);
//        File output = new File(sr.getFileResultsAbsUrl());
//
//        try {
//            IOUtils.copy(input, output);
//            controller.getResults().addElement(sr);
//        } catch (IOException ex) {
//            controller.getView().displayPopUp("Error copying scenario result file", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
//        }
//
//        controller.informViewScenarioLaunchingIsFinished();
//        System.out.println("[Controller] startScenarioExecution method is finished");
//
//    }
}
