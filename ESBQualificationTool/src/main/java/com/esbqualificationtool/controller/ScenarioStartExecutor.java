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
    private Scenario selectedScenario;
    private ESBQualificationToolController controller;

    public ScenarioStartExecutor(Scenario scenario, ESBQualificationToolController controller) {
        this.selectedScenario = scenario;
        this.controller = controller;
    }

    public void run() {
        SenderToFlowQueue sender = new SenderToFlowQueue();
        sender.sendFlowStringToQueue(SEND_TO_BROADCAST_KEY, START_ACTION);



        while (controller.getReceiverFromResultQueue().isNeedToBeTerminated() == false) {
            // queue is receiving - thread blocked here until receiving STOP or END
        }

        System.out.println("[Controller] ReceiverFromResultQueue is terminated");

        ScenarioResult sr = new ScenarioResult(selectedScenario.getName());
        File input = new File(ReceiverFromResultQueue.FILE_TEMP);
        File output = new File(sr.getFileResultsAbsUrl());

        try {
            IOUtils.copy(input, output);
            controller.getResults().addElement(sr);
        } catch (IOException ex) {
            controller.getView().displayPopUp("Error copying scenario result file", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }


        if(controller.isScenarioExecStopped()){
        controller.informViewScenarioLaunchingIsFinished("was stopped");
        }
        else {
         controller.informViewScenarioLaunchingIsFinished("was completely launched succesfully");
        }
        controller.setScenarioExecStopped(false) ;
        System.out.println("[Controller] startScenarioExecution method is finished");

    }
}
