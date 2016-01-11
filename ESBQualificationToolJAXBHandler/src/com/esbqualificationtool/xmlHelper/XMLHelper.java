package com.esbqualificationtool.xmlHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLHelper {

    public static boolean isXMLWellFormed(String xmlUrl) throws IOException, SAXException, ParserConfigurationException {

        boolean isXMLValid = false;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = (Document) builder.parse(new InputSource(xmlUrl));
        isXMLValid = true;

        return isXMLValid;
    }

    public static boolean doesXMLMatchXSD(String xmlUrl, String xsdUrl) throws SAXException, FileNotFoundException, IOException {

        boolean doesMatchXSD = false;

        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        SAXSource sourceXSD = new SAXSource(new InputSource(new FileInputStream(new File(xsdUrl))));
        Schema schema = factory.newSchema(sourceXSD);
        Validator validator = (Validator) schema.newValidator();
        validator.validate(new StreamSource(new File(xmlUrl)));

        doesMatchXSD = true;
        return doesMatchXSD;
    }

    public static void main(String[] args) throws JAXBException, XMLStreamException, IOException, SAXException, ParserConfigurationException {

        String xmlUrl = "/home/ubuntu/Desktop/ScenarioExample.xml";
        String xsdUrl = "/home/ubuntu/Desktop/ScenarioSchema.xsd";

        System.out.println(XMLHelper.isXMLWellFormed(xmlUrl));
        System.out.println(XMLHelper.doesXMLMatchXSD(xmlUrl, xsdUrl));
    }
}
