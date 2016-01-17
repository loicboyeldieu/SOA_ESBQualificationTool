package com.esbqualificationtool.controller;

import com.esbqualificationtool.jaxbhandler.*;
import com.esbqualificationtool.jaxbhandler.Scenario.Flow;
import com.esbqualificationtool.mq.ReceiverFromResultQueue;
import com.esbqualificationtool.mq.SenderToFlowQueue;
import com.esbqualificationtool.view.ESBQualificationToolView;
import com.esbqualificationtool.xmlHelper.XMLHelper;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class ESBQualificationToolController {

    private static final String END_FLOWS_TOKEN = "SCENARIO_END_OF_FLOWS";
    private static final String SEND_TO_BROADCAST_KEY = ".all";
    
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
        String scenarioDeletedName = scenario.getName();
        scenarios.removeElement(scenario);
        System.out.println("[Controller] " + scenarioDeletedName + " scenario is deleted");
    }

    public void launchScenario(Scenario scenario) {

        informViewScenarioIsLaunching();

        System.out.println("[Controller] launchScenario method called");
        selectedScenario = scenario;

        ReceiverFromResultQueue receiverFromResultQueue = new ReceiverFromResultQueue(selectedScenario, this);
        System.out.println("[Controller] Ready to receive results");

        try {
            receiverFromResultQueue.start();
        } catch (Exception ex) {
            this.getView().displayPopUp("Starting scenario", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }

        for (int i = 0; i < scenario.getFlow().size(); i++) {
            Flow flow = (Flow) scenario.getFlow().get(i);
            String flowString = jaxbScenarioHandler.getFlowXMLStringFromFlowObject(flow);

            ConsumerType consumer = flow.getConsumer();
            SenderToFlowQueue queueSender = new SenderToFlowQueue();
            queueSender.sendFlowStringToQueue(consumer.value().concat("."), flowString);
            System.out.println("[Controller] Sent one flow to queue");
        }

        SenderToFlowQueue sender = new SenderToFlowQueue();
        sender.sendFlowStringToQueue(SEND_TO_BROADCAST_KEY, END_FLOWS_TOKEN);

        System.out.println("[Controller] All the flows have been sent to queue");
    }

    public void startScenarioExecution() {
        ScenarioStartExecutor scenarioStartExecutor = new ScenarioStartExecutor();
        scenarioStartExecutor.start();
    }

    public void stopScenarioExecution() {
        ScenarioStopExecutor scenarioStopExecutor = new ScenarioStopExecutor();
        scenarioStopExecutor.start();
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
