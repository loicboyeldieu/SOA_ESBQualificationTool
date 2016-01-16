package com.esbqualificationtool.controller;

import com.esbqualificationtool.jaxbhandler.Scenario;
import com.esbqualificationtool.model.ScenarioResult;
import com.esbqualificationtool.mq.ReceiverFromResultQueue;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.apache.lucene.util.IOUtils;


public class ScenarioStartExecutor extends Thread {

    private Scenario selectedScenario ;
    private ESBQualificationToolController controller ;

    public ScenarioStartExecutor(Scenario selectedScenario, ESBQualificationToolController controller) {
        this.selectedScenario = selectedScenario;
        this.controller = controller;
    }
    
    public void run() {

        // loic's code for start 


        System.out.println("[ScenarioExecutor] start method called");

        ReceiverFromResultQueue receiverFromResultQueue = new ReceiverFromResultQueue(selectedScenario, controller);
        System.out.println("[Controller] Ready to receive results");

        try {
            receiverFromResultQueue.start();
        } catch (Exception ex) {
            controller.getView().displayPopUp("Starting scenario", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }

        while(receiverFromResultQueue.isNeedToBeTerminated() == false) {
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

        controller.informViewScenarioLaunchingIsFinished();
        System.out.println("[Controller] startScenarioExecution method is finished");

    }






}
