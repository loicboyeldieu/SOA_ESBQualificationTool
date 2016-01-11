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

    public String flowXMLStringFromIndex(List<Flow> flows, int index) {
        StringWriter flowString = new StringWriter();
        if (index < flows.size()) {
            try {
                Flow flow = flows.get(index);
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(new JAXBElement(new QName("", "flow"), Flow.class, flow), flowString);
                return flowString.toString();
            } catch (JAXBException ex) {
                Logger.getLogger(JAXBScenarioHandler.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } else {
            return null;
        }
    }

    static public Flow UnmarshalFlowStringToFlowObject(String flowString) {
        try {
            // (Consumer side) : flowString is received
            XMLInputFactory xif = XMLInputFactory.newInstance();
            XMLStreamReader xsr = (XMLStreamReader) xif.createXMLStreamReader(new StringReader(flowString.toString()));

            JAXBContext jc2 = JAXBContext.newInstance(Flow.class);
            // marshaller = jc.createMarshaller();
            Unmarshaller unmarshaller2 = jc2.createUnmarshaller();

            JAXBElement<Flow> flowJAXB = unmarshaller2.unmarshal(xsr, Flow.class);
            return flowJAXB.getValue();
        } catch (JAXBException ex) {
            Logger.getLogger(JAXBScenarioHandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (XMLStreamException ex) {
            Logger.getLogger(JAXBScenarioHandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public static void main(String[] args) throws JAXBException, XMLStreamException, IOException, SAXException, ParserConfigurationException {

        String xmlUrl = "/home/ubuntu/Desktop/ScenarioExample.xml";

        JAXBScenarioHandler jaxbhandler = new JAXBScenarioHandler(xmlUrl);
        Scenario s = jaxbhandler.getScenario();

        // The different flows to xml string
        for (int i = 0; i < s.getFlow().size(); i++) {
            String flowString = jaxbhandler.flowXMLStringFromIndex(s.getFlow(), i);
            System.out.println(flowString);
        }
    }
}
