package cda;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.4.4
 * 2011-11-30T03:16:07.445-04:30
 * Generated source version: 2.4.4
 * 
 */
@WebService(targetNamespace = "http://cda/", name = "CdaServicePortType")
@XmlSeeAlso({ObjectFactory.class})
public interface CdaServicePortType {

    @WebResult(name = "return", targetNamespace = "http://cda/")
    @RequestWrapper(localName = "buscarCDAByRango", targetNamespace = "http://cda/", className = "cda.BuscarCDAByRango")
    @WebMethod
    @ResponseWrapper(localName = "buscarCDAByRangoResponse", targetNamespace = "http://cda/", className = "cda.BuscarCDAByRangoResponse")
    public java.lang.Object buscarCDAByRango(
        @WebParam(name = "desde", targetNamespace = "http://cda/")
        javax.xml.datatype.XMLGregorianCalendar desde,
        @WebParam(name = "hasta", targetNamespace = "http://cda/")
        javax.xml.datatype.XMLGregorianCalendar hasta
    );

    @WebResult(name = "return", targetNamespace = "http://cda/")
    @RequestWrapper(localName = "buscarCDAByPacienteAndOrganizacion", targetNamespace = "http://cda/", className = "cda.BuscarCDAByPacienteAndOrganizacion")
    @WebMethod
    @ResponseWrapper(localName = "buscarCDAByPacienteAndOrganizacionResponse", targetNamespace = "http://cda/", className = "cda.BuscarCDAByPacienteAndOrganizacionResponse")
    public java.lang.Object buscarCDAByPacienteAndOrganizacion(
        @WebParam(name = "paciente", targetNamespace = "http://cda/")
        java.lang.String paciente,
        @WebParam(name = "organizacion", targetNamespace = "http://cda/")
        java.lang.String organizacion
    );

    @WebResult(name = "return", targetNamespace = "http://cda/")
    @RequestWrapper(localName = "buscarCDAByPaciente", targetNamespace = "http://cda/", className = "cda.BuscarCDAByPaciente")
    @WebMethod
    @ResponseWrapper(localName = "buscarCDAByPacienteResponse", targetNamespace = "http://cda/", className = "cda.BuscarCDAByPacienteResponse")
    public java.lang.Object buscarCDAByPaciente(
        @WebParam(name = "paciente", targetNamespace = "http://cda/")
        java.lang.String paciente
    );

    @WebResult(name = "return", targetNamespace = "http://cda/")
    @RequestWrapper(localName = "registrarCDA", targetNamespace = "http://cda/", className = "cda.RegistrarCDA")
    @WebMethod
    @ResponseWrapper(localName = "registrarCDAResponse", targetNamespace = "http://cda/", className = "cda.RegistrarCDAResponse")
    public java.lang.Object registrarCDA(
        @WebParam(name = "cda", targetNamespace = "http://cda/")
        java.lang.String cda
    );

    @WebResult(name = "return", targetNamespace = "http://cda/")
    @RequestWrapper(localName = "serviceMethod", targetNamespace = "http://cda/", className = "cda.ServiceMethod")
    @WebMethod
    @ResponseWrapper(localName = "serviceMethodResponse", targetNamespace = "http://cda/", className = "cda.ServiceMethodResponse")
    public java.lang.Object serviceMethod();

    @WebResult(name = "return", targetNamespace = "http://cda/")
    @RequestWrapper(localName = "logueo", targetNamespace = "http://cda/", className = "cda.Logueo")
    @WebMethod
    @ResponseWrapper(localName = "logueoResponse", targetNamespace = "http://cda/", className = "cda.LogueoResponse")
    public java.lang.Object logueo(
        @WebParam(name = "login", targetNamespace = "http://cda/")
        java.lang.String login,
        @WebParam(name = "password", targetNamespace = "http://cda/")
        java.lang.String password
    );

    @WebResult(name = "return", targetNamespace = "http://cda/")
    @RequestWrapper(localName = "buscarCDAById", targetNamespace = "http://cda/", className = "cda.BuscarCDAById")
    @WebMethod
    @ResponseWrapper(localName = "buscarCDAByIdResponse", targetNamespace = "http://cda/", className = "cda.BuscarCDAByIdResponse")
    public java.lang.Object buscarCDAById(
        @WebParam(name = "id", targetNamespace = "http://cda/")
        int id
    );
}
