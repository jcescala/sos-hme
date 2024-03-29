package org.opensih.webservices;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.3
 * Tue Dec 15 16:04:03 GMT 2009
 * Generated source version: 2.2.3
 * 
 */
 
@WebService(targetNamespace = "http://webservices.opensih.org/", name = "OpenSIH - Prototipo WebService Publisher")
@XmlSeeAlso({ObjectFactory.class})
public interface OpenSIH_0020_0020Prototipo_0020WebService_0020Publisher {

    @ResponseWrapper(localName = "PDQSupplier_QUQI_IN000003UV01_CancelResponse", targetNamespace = "http://webservices.opensih.org/", className = "org.opensih.webservices.PDQSupplierQUQIIN000003UV01CancelResponse")
    @RequestWrapper(localName = "PDQSupplier_QUQI_IN000003UV01_Cancel", targetNamespace = "http://webservices.opensih.org/", className = "org.opensih.webservices.PDQSupplierQUQIIN000003UV01Cancel")
    @WebMethod(operationName = "PDQSupplier_QUQI_IN000003UV01_Cancel")
    public void pdqSupplierQUQIIN000003UV01Cancel(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @ResponseWrapper(localName = "PDQSupplier_PRPA_IN201305UVResponse", targetNamespace = "http://webservices.opensih.org/", className = "org.opensih.webservices.PDQSupplierPRPAIN201305UVResponse")
    @RequestWrapper(localName = "PDQSupplier_PRPA_IN201305UV", targetNamespace = "http://webservices.opensih.org/", className = "org.opensih.webservices.PDQSupplierPRPAIN201305UV")
    @WebResult(name = "return", targetNamespace = "")
    @WebMethod(operationName = "PDQSupplier_PRPA_IN201305UV")
    public java.lang.String pdqSupplierPRPAIN201305UV(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @ResponseWrapper(localName = "consultaPacienteResponse", targetNamespace = "http://webservices.opensih.org/", className = "org.opensih.webservices.ConsultaPacienteResponse")
    @RequestWrapper(localName = "consultaPaciente", targetNamespace = "http://webservices.opensih.org/", className = "org.opensih.webservices.ConsultaPaciente")
    @WebResult(name = "return", targetNamespace = "")
    @WebMethod
    public java.lang.String consultaPaciente(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @ResponseWrapper(localName = "PIXManager_PRPA_IN201304UV02Response", targetNamespace = "http://webservices.opensih.org/", className = "org.opensih.webservices.PIXManagerPRPAIN201304UV02Response")
    @RequestWrapper(localName = "PIXManager_PRPA_IN201304UV02", targetNamespace = "http://webservices.opensih.org/", className = "org.opensih.webservices.PIXManagerPRPAIN201304UV02")
    @WebMethod(operationName = "PIXManager_PRPA_IN201304UV02")
    public void pixManagerPRPAIN201304UV02(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @ResponseWrapper(localName = "PDQSupplier_QUQI_IN000003UV01_ContinueResponse", targetNamespace = "http://webservices.opensih.org/", className = "org.opensih.webservices.PDQSupplierQUQIIN000003UV01ContinueResponse")
    @RequestWrapper(localName = "PDQSupplier_QUQI_IN000003UV01_Continue", targetNamespace = "http://webservices.opensih.org/", className = "org.opensih.webservices.PDQSupplierQUQIIN000003UV01Continue")
    @WebResult(name = "return", targetNamespace = "")
    @WebMethod(operationName = "PDQSupplier_QUQI_IN000003UV01_Continue")
    public java.lang.String pdqSupplierQUQIIN000003UV01Continue(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @ResponseWrapper(localName = "PIXManager_PRPA_IN201302UV02Response", targetNamespace = "http://webservices.opensih.org/", className = "org.opensih.webservices.PIXManagerPRPAIN201302UV02Response")
    @RequestWrapper(localName = "PIXManager_PRPA_IN201302UV02", targetNamespace = "http://webservices.opensih.org/", className = "org.opensih.webservices.PIXManagerPRPAIN201302UV02")
    @WebMethod(operationName = "PIXManager_PRPA_IN201302UV02")
    public void pixManagerPRPAIN201302UV02(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @ResponseWrapper(localName = "altaPacienteResponse", targetNamespace = "http://webservices.opensih.org/", className = "org.opensih.webservices.AltaPacienteResponse")
    @RequestWrapper(localName = "altaPaciente", targetNamespace = "http://webservices.opensih.org/", className = "org.opensih.webservices.AltaPaciente")
    @WebResult(name = "return", targetNamespace = "")
    @WebMethod
    public java.lang.String altaPaciente(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );
}
