package com.esbqualificationtool.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ESBQualificationToolWindow extends JFrame{

    private JPanel pannel = new JPanel();
    private JButton loadScenarioButton = new JButton("Load Scenario");
    private JButton launchScenarioButton = new JButton("Launch Scenario");
    private JButton displayResultButton = new JButton("Display Result");

    public ESBQualificationToolWindow(){
        JFrame mainFrame = new JFrame();

        // Parametrize the JFrame
        mainFrame.setTitle("ESB Qualification Tool");
        this.setSize(600, 70);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pannel.add(loadScenarioButton);
        pannel.add(launchScenarioButton);
        pannel.add(displayResultButton);

        this.setContentPane(pannel);

        this.setVisible(true);
    }

}
