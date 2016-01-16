package com.esbqualificationtool.model;

import java.io.File;


public class ScenarioResult {

    private String scenarioName ;
    private String fileResultsUrl ;

    public ScenarioResult(String scenarioName) {
        this.scenarioName = scenarioName;
        this.fileResultsUrl = scenarioName + "-results.txt" ;
    }

    public String getKibanaLink() {
        return "" + scenarioName + "" ;
    }

    

    public String getFileResultsAbsUrl() {
       File f = new File(fileResultsUrl) ;
        return f.getAbsolutePath();
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setFileResultsUrl(String fileResultsUrl) {
        this.fileResultsUrl = fileResultsUrl;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public String toString() {
        return scenarioName;
    }









}
