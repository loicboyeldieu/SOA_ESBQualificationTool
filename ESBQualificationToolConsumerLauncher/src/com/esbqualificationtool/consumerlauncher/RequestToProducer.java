package com.esbqualificationtool.consumerlauncher;

import com.esbqualificationtool.jaxbhandler.Scenario.Flow.Request;
import esbqualificationtoolcompositeapp.ESBQualificationToolCompositeAppService1;
import esbqualificationtoolcompositeapp.WebServiceProducer;

public class RequestToProducer extends RequestToProducerAbstract {

    public RequestToProducer(Request request) {
        super(request);
    }

    public byte[] callESBQualificationToolService(int messageSize, int processingTime) {
        byte[] message = null;

        try {
            System.out.println("DEBUT");
            // Call Web Service Operation
            ESBQualificationToolCompositeAppService1 service = new esbqualificationtoolcompositeapp.ESBQualificationToolCompositeAppService1();
            WebServiceProducer port = service.getCasaPort1();

            System.out.println("MILUE");
            // process result here
            message = port.qualificationToolService(messageSize, processingTime);

            System.out.println("FIN");

        } catch (Exception ex) {
            // TODO handle custom exceptions here
            System.out.println("Error: Service has not been invoked.");
        }

        return message;
    }
}
