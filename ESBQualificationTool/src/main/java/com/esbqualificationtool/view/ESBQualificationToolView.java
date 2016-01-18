package com.esbqualificationtool.view;

import com.esbqualificationtool.controller.ESBQualificationToolController;
import com.esbqualificationtool.jaxbhandler.Scenario;
import javax.swing.JOptionPane;

public class ESBQualificationToolView {

    private ESBQualificationToolController controller;
    private MainFrameJFrame mainFrameJFrame;
    private ScenarioLaunchingJFrame scenarioLaunchingJFrame;

    public ESBQualificationToolView(ESBQualificationToolController controller) {
        this.controller = controller;
        this.mainFrameJFrame = new MainFrameJFrame(controller.getScenarios(), controller.getResults(), this);
        this.scenarioLaunchingJFrame = new ScenarioLaunchingJFrame(this);
        mainFrameJFrame.setVisible(true);
        scenarioLaunchingJFrame.setVisible(false);
    }

    public void informControllerToLoadScenario(String xmlURL) {
        controller.loadScenario(xmlURL);
    }

    public void informControllerToDeleteScenario(Scenario s) {
        controller.deleteScenario(s);
    }

    public void informControllerToLaunchScenario(Scenario s) {
        scenarioLaunchingJFrame.setjButtonStopVisible(false);
        scenarioLaunchingJFrame.setjButtonStartVisible(false);
        mainFrameJFrame.setVisible(false);
        scenarioLaunchingJFrame.setVisible(true);
        controller.launchScenario(s);
    }

    public void scenarioIsReadyToBeStarted() {
        scenarioLaunchingJFrame.setjButtonStartVisible(true);
    }

    public void informControllerToStartScenarioExecution() {
        scenarioLaunchingJFrame.setjButtonStartVisible(false);
        scenarioLaunchingJFrame.setjButtonStopVisible(true);
        controller.startScenarioExecution();
    }

    public void informControllerToStopScenarioExecution() {
        scenarioLaunchingJFrame.setVisible(false);
        mainFrameJFrame.setVisible(true);
        controller.stopScenarioExecution();
    }

    public void scenarioLaunchingIsFinished(String msg) {
        displayPopUp("Scenario launching is finished", "Scenario " + msg + ". \nYou can now see results in the result file or in Kibana", JOptionPane.INFORMATION_MESSAGE);
        scenarioLaunchingJFrame.setjButtonStopVisible(false); 
        scenarioLaunchingJFrame.setjButtonStopVisible(false);
        mainFrameJFrame.setVisible(true);
        scenarioLaunchingJFrame.setVisible(false);
    }

    public void displayPopUp(String title, String msg, int joptionPaneTypeOfMessage) {
        JOptionPane.showMessageDialog(null, msg, title, joptionPaneTypeOfMessage);
    }
}
