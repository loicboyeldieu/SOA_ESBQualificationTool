package com.esbqualificationtool.webservices;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebParam;

@javax.jws.WebService
public class WebServiceProducer {
    
    public byte[] QualificationToolService(@WebParam(name = "byteSize") int byteSize, @WebParam(name = "msProcessingTime") int msProcessingTime){

        byte[] message = new byte[byteSize];

        try {
            Thread.sleep(msProcessingTime);
        } catch (InterruptedException ex) {
            Logger.getLogger(WebServiceProducer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return message;
    }

}
