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
//        this.mainFrameJFrame = new MainFrameJFrame(controller.getScenarios(), controller.getResults(), this);
//        this.scenarioLaunchingJFrame = new ScenarioLaunchingJFrame();
//        mainFrameJFrame.setVisible(true);
//        scenarioLaunchingJFrame.setVisible(false);
    }

    public void informControllerToLoadScenario(String xmlURL) {
        controller.loadScenario(xmlURL);
    }

    public void informControllerToLaunchScenario(Scenario s) {
        controller.launchScenario(s);
    }

    public void informControllerToStartScenario() {
        controller.startScenarioExecution();
    }

    public void informControllerToStopScenario() {
        controller.stopScenario();
    }


    public void scenarioIsLaunching() {
//        mainFrameJFrame.setVisible(false);
//        scenarioLaunchingJFrame.setVisible(true);
    }

    public void scenarioLaunchingIsFinished() {
        displayPopUp("Scenario launching is finished", "Scenario was launched succesfully. \nYou can now see results in the result file or in Kibana", JOptionPane.INFORMATION_MESSAGE) ;
     //   mainFrameJFrame.setVisible(true);
     //   scenarioLaunchingJFrame.setVisible(false);
    }

    public void displayPopUp(String title, String msg, int joptionPaneTypeOfMessage) {
        JOptionPane.showMessageDialog(null, msg, title, joptionPaneTypeOfMessage );
    }

}
