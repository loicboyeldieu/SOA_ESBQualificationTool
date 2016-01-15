package com.esbqualificationtool.mainApplication;

import com.esbqualificationtool.controller.ESBQualificationToolController;
import com.esbqualificationtool.jaxbhandler.Scenario;
import com.esbqualificationtool.view.ESBQualificationToolView;

public class ESBQualificationTool {

    public static void main(String[] args){

String xmlUrl =  "/home/ubuntu/SOA_ESBQualificationTool/ESBQualificationTool/src/main/java/com/esbqualificationtool/resources/ScenarioExample.xml";


        ESBQualificationToolController controller = new ESBQualificationToolController(null);   
        ESBQualificationToolView view = new ESBQualificationToolView(controller);
        controller.setView(view);

        view.informControllerToLoadScenario(xmlUrl);
        view.informControllerToLaunchScenario((Scenario) controller.getScenarios().get(0)) ;
        view.informControllerToStartScenario(); 

        while(true) {
            
        }


    }

}
