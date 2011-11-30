
package cda;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cda package. 
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

    private final static QName _BuscarCDAById_QNAME = new QName("http://cda/", "buscarCDAById");
    private final static QName _BuscarCDAByRango_QNAME = new QName("http://cda/", "buscarCDAByRango");
    private final static QName _BuscarCDAByPaciente_QNAME = new QName("http://cda/", "buscarCDAByPaciente");
    private final static QName _RegistrarCDAResponse_QNAME = new QName("http://cda/", "registrarCDAResponse");
    private final static QName _BuscarCDAByIdResponse_QNAME = new QName("http://cda/", "buscarCDAByIdResponse");
    private final static QName _LogueoResponse_QNAME = new QName("http://cda/", "logueoResponse");
    private final static QName _BuscarCDAByRangoResponse_QNAME = new QName("http://cda/", "buscarCDAByRangoResponse");
    private final static QName _BuscarCDAByPacienteAndOrganizacion_QNAME = new QName("http://cda/", "buscarCDAByPacienteAndOrganizacion");
    private final static QName _RegistrarCDA_QNAME = new QName("http://cda/", "registrarCDA");
    private final static QName _ServiceMethodResponse_QNAME = new QName("http://cda/", "serviceMethodResponse");
    private final static QName _BuscarCDAByPacienteResponse_QNAME = new QName("http://cda/", "buscarCDAByPacienteResponse");
    private final static QName _BuscarCDAByPacienteAndOrganizacionResponse_QNAME = new QName("http://cda/", "buscarCDAByPacienteAndOrganizacionResponse");
    private final static QName _ServiceMethod_QNAME = new QName("http://cda/", "serviceMethod");
    private final static QName _Logueo_QNAME = new QName("http://cda/", "logueo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cda
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BuscarCDAByPacienteResponse }
     * 
     */
    public BuscarCDAByPacienteResponse createBuscarCDAByPacienteResponse() {
        return new BuscarCDAByPacienteResponse();
    }

    /**
     * Create an instance of {@link ServiceMethod }
     * 
     */
    public ServiceMethod createServiceMethod() {
        return new ServiceMethod();
    }

    /**
     * Create an instance of {@link BuscarCDAByPacienteAndOrganizacionResponse }
     * 
     */
    public BuscarCDAByPacienteAndOrganizacionResponse createBuscarCDAByPacienteAndOrganizacionResponse() {
        return new BuscarCDAByPacienteAndOrganizacionResponse();
    }

    /**
     * Create an instance of {@link Logueo }
     * 
     */
    public Logueo createLogueo() {
        return new Logueo();
    }

    /**
     * Create an instance of {@link BuscarCDAByPacienteAndOrganizacion }
     * 
     */
    public BuscarCDAByPacienteAndOrganizacion createBuscarCDAByPacienteAndOrganizacion() {
        return new BuscarCDAByPacienteAndOrganizacion();
    }

    /**
     * Create an instance of {@link RegistrarCDA }
     * 
     */
    public RegistrarCDA createRegistrarCDA() {
        return new RegistrarCDA();
    }

    /**
     * Create an instance of {@link ServiceMethodResponse }
     * 
     */
    public ServiceMethodResponse createServiceMethodResponse() {
        return new ServiceMethodResponse();
    }

    /**
     * Create an instance of {@link LogueoResponse }
     * 
     */
    public LogueoResponse createLogueoResponse() {
        return new LogueoResponse();
    }

    /**
     * Create an instance of {@link BuscarCDAByRangoResponse }
     * 
     */
    public BuscarCDAByRangoResponse createBuscarCDAByRangoResponse() {
        return new BuscarCDAByRangoResponse();
    }

    /**
     * Create an instance of {@link BuscarCDAById }
     * 
     */
    public BuscarCDAById createBuscarCDAById() {
        return new BuscarCDAById();
    }

    /**
     * Create an instance of {@link BuscarCDAByRango }
     * 
     */
    public BuscarCDAByRango createBuscarCDAByRango() {
        return new BuscarCDAByRango();
    }

    /**
     * Create an instance of {@link RegistrarCDAResponse }
     * 
     */
    public RegistrarCDAResponse createRegistrarCDAResponse() {
        return new RegistrarCDAResponse();
    }

    /**
     * Create an instance of {@link BuscarCDAByPaciente }
     * 
     */
    public BuscarCDAByPaciente createBuscarCDAByPaciente() {
        return new BuscarCDAByPaciente();
    }

    /**
     * Create an instance of {@link BuscarCDAByIdResponse }
     * 
     */
    public BuscarCDAByIdResponse createBuscarCDAByIdResponse() {
        return new BuscarCDAByIdResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarCDAById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "buscarCDAById")
    public JAXBElement<BuscarCDAById> createBuscarCDAById(BuscarCDAById value) {
        return new JAXBElement<BuscarCDAById>(_BuscarCDAById_QNAME, BuscarCDAById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarCDAByRango }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "buscarCDAByRango")
    public JAXBElement<BuscarCDAByRango> createBuscarCDAByRango(BuscarCDAByRango value) {
        return new JAXBElement<BuscarCDAByRango>(_BuscarCDAByRango_QNAME, BuscarCDAByRango.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarCDAByPaciente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "buscarCDAByPaciente")
    public JAXBElement<BuscarCDAByPaciente> createBuscarCDAByPaciente(BuscarCDAByPaciente value) {
        return new JAXBElement<BuscarCDAByPaciente>(_BuscarCDAByPaciente_QNAME, BuscarCDAByPaciente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistrarCDAResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "registrarCDAResponse")
    public JAXBElement<RegistrarCDAResponse> createRegistrarCDAResponse(RegistrarCDAResponse value) {
        return new JAXBElement<RegistrarCDAResponse>(_RegistrarCDAResponse_QNAME, RegistrarCDAResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarCDAByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "buscarCDAByIdResponse")
    public JAXBElement<BuscarCDAByIdResponse> createBuscarCDAByIdResponse(BuscarCDAByIdResponse value) {
        return new JAXBElement<BuscarCDAByIdResponse>(_BuscarCDAByIdResponse_QNAME, BuscarCDAByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LogueoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "logueoResponse")
    public JAXBElement<LogueoResponse> createLogueoResponse(LogueoResponse value) {
        return new JAXBElement<LogueoResponse>(_LogueoResponse_QNAME, LogueoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarCDAByRangoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "buscarCDAByRangoResponse")
    public JAXBElement<BuscarCDAByRangoResponse> createBuscarCDAByRangoResponse(BuscarCDAByRangoResponse value) {
        return new JAXBElement<BuscarCDAByRangoResponse>(_BuscarCDAByRangoResponse_QNAME, BuscarCDAByRangoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarCDAByPacienteAndOrganizacion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "buscarCDAByPacienteAndOrganizacion")
    public JAXBElement<BuscarCDAByPacienteAndOrganizacion> createBuscarCDAByPacienteAndOrganizacion(BuscarCDAByPacienteAndOrganizacion value) {
        return new JAXBElement<BuscarCDAByPacienteAndOrganizacion>(_BuscarCDAByPacienteAndOrganizacion_QNAME, BuscarCDAByPacienteAndOrganizacion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistrarCDA }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "registrarCDA")
    public JAXBElement<RegistrarCDA> createRegistrarCDA(RegistrarCDA value) {
        return new JAXBElement<RegistrarCDA>(_RegistrarCDA_QNAME, RegistrarCDA.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceMethodResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "serviceMethodResponse")
    public JAXBElement<ServiceMethodResponse> createServiceMethodResponse(ServiceMethodResponse value) {
        return new JAXBElement<ServiceMethodResponse>(_ServiceMethodResponse_QNAME, ServiceMethodResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarCDAByPacienteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "buscarCDAByPacienteResponse")
    public JAXBElement<BuscarCDAByPacienteResponse> createBuscarCDAByPacienteResponse(BuscarCDAByPacienteResponse value) {
        return new JAXBElement<BuscarCDAByPacienteResponse>(_BuscarCDAByPacienteResponse_QNAME, BuscarCDAByPacienteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarCDAByPacienteAndOrganizacionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "buscarCDAByPacienteAndOrganizacionResponse")
    public JAXBElement<BuscarCDAByPacienteAndOrganizacionResponse> createBuscarCDAByPacienteAndOrganizacionResponse(BuscarCDAByPacienteAndOrganizacionResponse value) {
        return new JAXBElement<BuscarCDAByPacienteAndOrganizacionResponse>(_BuscarCDAByPacienteAndOrganizacionResponse_QNAME, BuscarCDAByPacienteAndOrganizacionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceMethod }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "serviceMethod")
    public JAXBElement<ServiceMethod> createServiceMethod(ServiceMethod value) {
        return new JAXBElement<ServiceMethod>(_ServiceMethod_QNAME, ServiceMethod.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Logueo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cda/", name = "logueo")
    public JAXBElement<Logueo> createLogueo(Logueo value) {
        return new JAXBElement<Logueo>(_Logueo_QNAME, Logueo.class, null, value);
    }

}