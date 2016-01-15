package com.esbqualificationtool.jaxbhandler;

import com.esbqualificationtool.jaxbhandler.Scenario.*;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.xml.sax.SAXException;

public class JAXBScenarioHandler {

    private JAXBContext jc;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    private String xmlUrl;
    private Scenario scenario;

    public JAXBScenarioHandler(String xmlUrl) {
        try {
            jc = JAXBContext.newInstance(Scenario.class);
            marshaller = jc.createMarshaller();
            unmarshaller = jc.createUnmarshaller();
            this.xmlUrl = xmlUrl;
            scenario = UnmarshalEntireXMLtoScenarioObject();
        } catch (JAXBException ex) {
            Logger.getLogger(JAXBScenarioHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Scenario UnmarshalEntireXMLtoScenarioObject() {
        try {
            return (Scenario) unmarshaller.unmarshal(new File(xmlUrl));
        } catch (JAXBException ex) {
            Logger.getLogger(JAXBScenarioHandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Scenario getScenario() {
        return scenario;
    }

    public String getFlowXMLStringFromFlowObject(Flow flow) {
        StringWriter flowString = new StringWriter();

        try {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(new JAXBElement(new QName("", "flow"), Flow.class, flow), flowString);
            return flowString.toString();
        } catch (JAXBException ex) {
            Logger.getLogger(JAXBScenarioHandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

//    public static void main(String[] args) throws JAXBException, XMLStreamException, IOException, SAXException, ParserConfigurationException {
//
//        String xmlUrl = "src/com/esbqualificationtool/xmlHelper/ScenarioExample.xml";
//
//        JAXBScenarioHandler jaxbhandler = new JAXBScenarioHandler(xmlUrl);
//        Scenario s = jaxbhandler.getScenario();
//
//        // The different flows to xml string
//        for (Flow flow : s.getFlow()) {
//            String flowString = jaxbhandler.getFlowXMLStringFromFlowObject(flow);
//            System.out.println(flowString);
//            System.out.println("");
//            System.out.println("Next flow : ");
//        }
//    }
}
