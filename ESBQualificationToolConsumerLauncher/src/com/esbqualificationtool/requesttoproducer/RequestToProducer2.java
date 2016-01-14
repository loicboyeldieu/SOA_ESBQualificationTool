package com.esbqualificationtool.requesttoproducer;

import com.esbqualificationtool.jaxbhandler.Scenario.Flow;
import com.esbqualificationtool.jaxbhandler.Scenario.Flow.Request;

public class RequestToProducer2 extends RequestToProducerAbstract {

    public RequestToProducer2(Request request, Flow flow) {
        super(request, flow);
    }

    public byte[] callESBQualificationToolService(int messageSize, int processingTime) {
        byte[] message = null;


        try { // Call Web Service Operation

            System.out.println("[RequestToProducer2] Ready to invoke webservice");
            esbqualificationtoolcompositeapp2.ESBQualificationToolCompositeApp2Service1 service = new esbqualificationtoolcompositeapp2.ESBQualificationToolCompositeApp2Service1();
            esbqualificationtoolcompositeapp2.WebServiceProducer2 port = service.getCasaPort1();

            // process result here
            message = port.qualificationToolService(messageSize, processingTime);
            System.out.println("[RequestToProducer2] Webservice response received");

        } catch (Exception ex) {
            // TODO handle custom exceptions here
            System.out.println("[RequestToProducer2] Error: Service has not been invoked.");
        }

        return message;
    }
}

