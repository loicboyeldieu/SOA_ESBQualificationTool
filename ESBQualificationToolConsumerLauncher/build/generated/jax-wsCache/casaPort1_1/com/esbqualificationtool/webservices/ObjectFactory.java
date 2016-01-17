
package com.esbqualificationtool.webservices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.esbqualificationtool.webservices package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _QualificationToolServiceResponseReturn_QNAME = new QName("", "return");
    private final static QName _QualificationToolService_QNAME = new QName("http://webservices.esbqualificationtool.com/", "QualificationToolService");
    private final static QName _QualificationToolServiceResponse_QNAME = new QName("http://webservices.esbqualificationtool.com/", "QualificationToolServiceResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.esbqualificationtool.webservices
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QualificationToolServiceResponse }
     * 
     */
    public QualificationToolServiceResponse createQualificationToolServiceResponse() {
        return new QualificationToolServiceResponse();
    }

    /**
     * Create an instance of {@link QualificationToolService }
     * 
     */
    public QualificationToolService createQualificationToolService() {
        return new QualificationToolService();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "return", scope = QualificationToolServiceResponse.class)
    public JAXBElement<byte[]> createQualificationToolServiceResponseReturn(byte[] value) {
        return new JAXBElement<byte[]>(_QualificationToolServiceResponseReturn_QNAME, byte[].class, QualificationToolServiceResponse.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QualificationToolService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.esbqualificationtool.com/", name = "QualificationToolService")
    public JAXBElement<QualificationToolService> createQualificationToolService(QualificationToolService value) {
        return new JAXBElement<QualificationToolService>(_QualificationToolService_QNAME, QualificationToolService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QualificationToolServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.esbqualificationtool.com/", name = "QualificationToolServiceResponse")
    public JAXBElement<QualificationToolServiceResponse> createQualificationToolServiceResponse(QualificationToolServiceResponse value) {
        return new JAXBElement<QualificationToolServiceResponse>(_QualificationToolServiceResponse_QNAME, QualificationToolServiceResponse.class, null, value);
    }

}
