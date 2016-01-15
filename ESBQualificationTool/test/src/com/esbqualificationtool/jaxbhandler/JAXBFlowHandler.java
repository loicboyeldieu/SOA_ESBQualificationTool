package com.esbqualificationtool.jaxbhandler;

import com.esbqualificationtool.jaxbhandler.Scenario.*;


import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.xml.sax.SAXException;

public class JAXBFlowHandler {

    private JAXBContext jc;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    private String xmlFlowString;
    private Flow flowGenerated;

    public JAXBFlowHandler(String xmlFlowString) {
        try {
            jc = JAXBContext.newInstance(Flow.class);
            marshaller = jc.createMarshaller();
            unmarshaller = jc.createUnmarshaller();
            this.xmlFlowString = xmlFlowString;
            flowGenerated = unmarshalFlowStringToFlowObject(xmlFlowString);
        } catch (JAXBException ex) {
            Logger.getLogger(JAXBScenarioHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Flow unmarshalFlowStringToFlowObject(String flowString) {
        try {
            XMLInputFactory xif = XMLInputFactory.newInstance();
            XMLStreamReader xsr = (XMLStreamReader) xif.createXMLStreamReader(new StringReader(flowString.toString()));
            JAXBElement<Flow> flowJAXB = unmarshaller.unmarshal(xsr, Flow.class);
            return flowJAXB.getValue();
        } catch (JAXBException ex) {
            Logger.getLogger(JAXBScenarioHandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (XMLStreamException ex) {
            Logger.getLogger(JAXBScenarioHandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Flow getFlow() {
        return flowGenerated;
    }

//    public static void main(String[] args) throws JAXBException, XMLStreamException, IOException, SAXException, ParserConfigurationException {
//
//        String test = "<flow id=\"1\"> " +
//                "<consumer>consumer1</consumer>" +
//                "<totalExecTimeInSec>60</totalExecTimeInSec>" +
//                "<frequencyInSec>5</frequencyInSec>" +
//                "<delayBetweenEachRequestInMs>0</delayBetweenEachRequestInMs>" +
//                "<request id=\"1\">" +
//                "<producer>producer1</producer>" +
//                "<messageSize>512</messageSize>" +
//                "<processingTimeInMs>2000</processingTimeInMs>" +
//                "</request>" +
//                "<request id=\"2\">" +
//                "<producer>producer1</producer>" +
//                "<messageSize>256</messageSize>" +
//                "<processingTimeInMs>5000</processingTimeInMs>" +
//                "</request>" +
//                "</flow>";
//
//        JAXBFlowHandler jaxbFlowHandler = new JAXBFlowHandler(test);
//        Flow flowtest = jaxbFlowHandler.getFlow();
//        System.out.println(flowtest.getRequest().get(1).getProcessingTimeInMs());
//
//    }
}

