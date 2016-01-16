package com.esbqualificationtool.controller;

import com.esbqualificationtool.jaxbhandler.*;
import com.esbqualificationtool.jaxbhandler.Scenario.Flow;
import com.esbqualificationtool.model.ScenarioResult;
import com.esbqualificationtool.mq.ReceiverFromResultQueue;
import com.esbqualificationtool.mq.SenderToFlowQueue;
import com.esbqualificationtool.view.ESBQualificationToolView;
import com.esbqualificationtool.xmlHelper.XMLHelper;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import org.apache.lucene.util.IOUtils;

public class ESBQualificationToolController extends Thread {

    private JAXBScenarioHandler jaxbScenarioHandler;
    private ESBQualificationToolView view;
    private DefaultListModel scenarios;
    private DefaultListModel results;
    private Scenario selectedScenario;

    public ESBQualificationToolController(ESBQualificationToolView view) {
        this.view = view;
        this.scenarios = new DefaultListModel();
        this.results = new DefaultListModel();
        this.selectedScenario = null;
    }



    /* ******************************************** */
    /* ****************** METHODS ***************** */
    /* ******************************************** */
    public void loadScenario(String xmlUrl) {

        boolean isValid = false;

        try {
            isValid = XMLHelper.isXMLWellFormed(xmlUrl);
            // scenarios.addElement(s);
        } catch (Exception ex) {
            view.displayPopUp("Scenario error : XML not well-formed", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }

        if (isValid) {
            isValid = false;
            try {
                isValid = XMLHelper.doesXMLMatchXSD(xmlUrl);
            } catch (Exception ex) {
                view.displayPopUp("Scenario error : XML doesn't match with XSD file", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        }

        if (isValid) {
            jaxbScenarioHandler = new JAXBScenarioHandler(xmlUrl);
            Scenario s = jaxbScenarioHandler.getScenario();

            // add a unique prefix to the name to avoid scenario names conflicts
            s.setName(s.getName() + "-" + System.currentTimeMillis());

            scenarios.addElement(s);
            view.displayPopUp("Scenario file OK", "Scenario imported successfully", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void deleteScenario(Scenario scenario) {
        String scenarioDeletedName = scenario.getName() ;
        scenarios.removeElement(scenario);
        System.out.println("[Controller] "+ scenarioDeletedName + " scenario is deleted");
    }

    public void launchScenario(Scenario scenario) {

       informViewScenarioIsLaunching();

        System.out.println("[Controller] launchScenario method called");
        selectedScenario = scenario ; 

        for (int i = 0; i < scenario.getFlow().size(); i++) {
            Flow flow = (Flow) scenario.getFlow().get(i);
            String flowString = jaxbScenarioHandler.getFlowXMLStringFromFlowObject(flow);

            ConsumerType consumer = flow.getConsumer();
            SenderToFlowQueue queueSender = new SenderToFlowQueue(consumer.value());
            queueSender.sendFlowStringToQueue(flowString);
            System.out.println("[Controller] Sent one flow to queue");
        }
        System.out.println("[Controller] All the flows have been sent to queue");
    }

    public void run() {
        startScenarioExecution() ; 
    }

    private void  startScenarioExecution() {
    System.out.println("[Controller] startScenarioExecution method called");

        ReceiverFromResultQueue receiverFromResultQueue = new ReceiverFromResultQueue(selectedScenario, this);
        System.out.println("[Controller] Ready to receive results");

        try {
            receiverFromResultQueue.start();
        } catch (Exception ex) {
            view.displayPopUp("Starting scenario", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }

        while(receiverFromResultQueue.isNeedToBeTerminated() == false) {
            
        }
        
        try {
            receiverFromResultQueue.getChannel().close();
            receiverFromResultQueue.getConnection().close();
        } catch (IOException ex) {
            view.displayPopUp("Error IO closing queue", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        } catch (TimeoutException ex) {
            view.displayPopUp("Error Timeout closing queue", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }

       receiverFromResultQueue.stop() ;

        ScenarioResult sr = new ScenarioResult(selectedScenario.getName());
        File input = new File(ReceiverFromResultQueue.FILE_TEMP);
        File output = new File(sr.getFileResultsAbsUrl());

        try {
            IOUtils.copy(input, output);
            results.addElement(sr);
        } catch (IOException ex) {
            view.displayPopUp("Error copying scenario result file", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }

        informViewScenarioLaunchingIsFinished();
        System.out.println("[Controller] startScenarioExecution method is finished");
    }

    public void stopScenarioExecution() {
        System.out.println("[Controller] stopScenarioExcution method called");
    }

    public void informViewScenarioIsLaunching() {
        view.scenarioIsLaunching();
    }

    public void informViewScenarioLaunchingIsFinished() {
        view.scenarioLaunchingIsFinished();
    }

    public void informErrorToView(String title, String message) {
        view.displayPopUp(title, message, JOptionPane.ERROR_MESSAGE);
    }


    /* ******************************************* */
    /* ******************GETTERS ***************** */
    /* ******************************************* */
    public DefaultListModel getResults() {
        return results;
    }

    public DefaultListModel getScenarios() {
        return scenarios;
    }

    public Scenario getSelectedScenario() {
        return selectedScenario;
    }

    public ESBQualificationToolView getView() {
        return view;
    }

    /* ******************************************* */
    /* ******************SETTERS ***************** */
    /* ******************************************* */
    public void setResults(DefaultListModel results) {
        this.results = results;
    }

    public void setScenarios(DefaultListModel scenarios) {
        this.scenarios = scenarios;
    }

    public void setSelectedScenario(Scenario selectedScenario) {
        this.selectedScenario = selectedScenario;
    }

    public void setView(ESBQualificationToolView view) {
        this.view = view;
    }



}
