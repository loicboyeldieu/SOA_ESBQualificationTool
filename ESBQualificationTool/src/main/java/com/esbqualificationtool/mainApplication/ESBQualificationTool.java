package com.esbqualificationtool.mainApplication;

import com.esbqualificationtool.controller.ESBQualificationToolController;
import com.esbqualificationtool.view.ESBQualificationToolView;

public class ESBQualificationTool {

// An XML Scenario is available here :
// /home/ubuntu/SOA_ESBQualificationTool/ESBQualificationTool/src/main/java/com/esbqualificationtool/resources/ScenarioExample.xml
    public static void main(String[] args) {

        ESBQualificationToolController controller = new ESBQualificationToolController(null);
        ESBQualificationToolView view = new ESBQualificationToolView(controller);
        controller.setView(view);

        while (true) {  }

    }
}
