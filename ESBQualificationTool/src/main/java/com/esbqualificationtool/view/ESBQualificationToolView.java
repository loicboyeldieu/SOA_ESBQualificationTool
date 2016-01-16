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

    public MainFrameJFrame getMainFrameJFrame() {
        return mainFrameJFrame;
    }

    

    public void informControllerToLoadScenario(String xmlURL) {
        controller.loadScenario(xmlURL);
    }

    public void informControllerToDeleteScenario(Scenario s) {
        controller.deleteScenario(s);
    }

    public void informControllerToLaunchScenario(Scenario s) {
        controller.launchScenario(s);
    }

    public void informControllerToStartScenarioExecution() {        
        controller.start();
    }

    public void informControllerToStopScenarioExecution() {
        controller.stopScenarioExecution();
    }


    public void scenarioIsLaunching() {
        mainFrameJFrame.setVisible(false);
        scenarioLaunchingJFrame.setVisible(true);
    }

    public void scenarioLaunchingIsFinished() {
        displayPopUp("Scenario launching is finished", "Scenario was launched succesfully. \nYou can now see results in the result file or in Kibana", JOptionPane.INFORMATION_MESSAGE) ;
        mainFrameJFrame.setVisible(true);
        scenarioLaunchingJFrame.resetJButtonEnable() ;
        scenarioLaunchingJFrame.setVisible(false);
    }

    public void displayPopUp(String title, String msg, int joptionPaneTypeOfMessage) {
        JOptionPane.showMessageDialog(null, msg, title, joptionPaneTypeOfMessage );
    }

}
