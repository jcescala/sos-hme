package cda;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.4.4
 * 2011-11-30T03:16:07.523-04:30
 * Generated source version: 2.4.4
 * 
 */
@WebServiceClient(name = "CdaService", 
                  wsdlLocation = "docs/cda.wsdl",
                  targetNamespace = "http://cda/") 
public class CdaService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://cda/", "CdaService");
    public final static QName CdaServicePort = new QName("http://cda/", "CdaServicePort");
    static {
        URL url = null;
        try {
            url = new URL("docs/cda.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(CdaService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "docs/cda.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public CdaService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public CdaService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CdaService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns CdaServicePortType
     */
    @WebEndpoint(name = "CdaServicePort")
    public CdaServicePortType getCdaServicePort() {
        return super.getPort(CdaServicePort, CdaServicePortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CdaServicePortType
     */
    @WebEndpoint(name = "CdaServicePort")
    public CdaServicePortType getCdaServicePort(WebServiceFeature... features) {
        return super.getPort(CdaServicePort, CdaServicePortType.class, features);
    }

}
