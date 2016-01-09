
package esbqualificationtoolcompositeapp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.4-b01-
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "ESBQualificationToolCompositeAppService1", targetNamespace = "ESBQualificationToolCompositeApp", wsdlLocation = "http://192.168.0.100:9080/ESBQualificationToolCompositeAppService1/casaPort1?wsdl")
public class ESBQualificationToolCompositeAppService1
    extends Service
{

    private final static URL ESBQUALIFICATIONTOOLCOMPOSITEAPPSERVICE1_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(esbqualificationtoolcompositeapp.ESBQualificationToolCompositeAppService1 .class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = esbqualificationtoolcompositeapp.ESBQualificationToolCompositeAppService1 .class.getResource(".");
            url = new URL(baseUrl, "http://192.168.0.100:9080/ESBQualificationToolCompositeAppService1/casaPort1?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://192.168.0.100:9080/ESBQualificationToolCompositeAppService1/casaPort1?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        ESBQUALIFICATIONTOOLCOMPOSITEAPPSERVICE1_WSDL_LOCATION = url;
    }

    public ESBQualificationToolCompositeAppService1(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ESBQualificationToolCompositeAppService1() {
        super(ESBQUALIFICATIONTOOLCOMPOSITEAPPSERVICE1_WSDL_LOCATION, new QName("ESBQualificationToolCompositeApp", "ESBQualificationToolCompositeAppService1"));
    }

    /**
     * 
     * @return
     *     returns WebServiceProducer
     */
    @WebEndpoint(name = "casaPort1")
    public WebServiceProducer getCasaPort1() {
        return super.getPort(new QName("ESBQualificationToolCompositeApp", "casaPort1"), WebServiceProducer.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WebServiceProducer
     */
    @WebEndpoint(name = "casaPort1")
    public WebServiceProducer getCasaPort1(WebServiceFeature... features) {
        return super.getPort(new QName("ESBQualificationToolCompositeApp", "casaPort1"), WebServiceProducer.class, features);
    }

}
