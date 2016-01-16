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
        return "http://192.168.0.103:5601/#/visualize/create?type=line&indexPattern=esbqualtool&_g=%28refreshInterval:%28display:Off,section:0,value:0%29,time:%28from:now-5y,mode:quick,to:now%29%29&_a=%28filters:!%28%29,linked:!f,query:%28query_string:%28analyze_wildcard:!t," +
                "query:%27_type:%22" + scenarioName + "%22%27%29%29,vis:%28aggs:!%28%28id:%271%27,params:%28field:RTT%29,schema:metric,type:avg%29,%28id:%272%27,params:%28field:requestID,order:desc,orderAgg:%28id:%272-orderAgg%27,params:%28field:requestID%29,schema:orderAgg,type:min%29,orderBy:custom,size:50%29,schema:segment,type:terms%29,%28id:%273%27,params:%28field:flowId,order:desc,orderBy:%271%27,size:50%29,schema:group,type:terms%29%29,listeners:%28%29,params:%28addLegend:!t,addTooltip:!t,defaultYExtents:!f,shareYAxis:!t%29,type:line%29%29"  ;
                
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
