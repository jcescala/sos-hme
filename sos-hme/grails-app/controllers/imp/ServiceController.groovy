package imp

//import org.grails.plugins.wsclient.service.WebService
import imp.Paciente
import com.thoughtworks.xstream.*
import java.io.OutputStream.*
import org.codehaus.groovy.grails.commons.ApplicationHolder
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource
import cda.BuscarCDAByRangoResponse
import cda.BuscarCDAByIdResponse
import cda.BuscarCDAByPacienteResponse
import cda.BuscarCDAByPacienteAndOrganizacionResponse
import cda.BuscarCDAByPacienteAndRangoResponse

import cda.CdaArr
import converters.*
import java.util.ArrayList

class ServiceController {
    /*
    WebService webService
    def wsdl = ApplicationHolder.application.config.wsdl
    def proxy
     */
    def customSecureServiceClient
    // def complexServiceClient
    def index = {

        customSecureServiceClient.serviceMethod()

        render("<h1>Bien</h1>")
    }


    def registrarCda = {
        XStream x = new XStream()

    

        println "----------------------"

        println "Cosumiendo web service"

        // String cda_string = cadenaXML.toString()

        def result1 = customSecureServiceClient.registrarCDA("")

        //  println cadenaXML.toString()
        println "----------------------"

       


        render "<p>Listo revisar </p> <p>${result1}</p>"



    }

 
    def buscarCda = {
       
        List<cda.BuscarCDAByRangoResponse> result = customSecureServiceClient.buscarCDAByRango(
            XMLGregorianCalendarConverter.getXMLCalendar("2010-08-30","yyyy-MM-dd"),
            XMLGregorianCalendarConverter.getXMLCalendar("2010-10-30","yyyy-MM-dd"),
            0)
        
        println "Resultado CDAs: "+ result
        if(result){
            
            render(view: "listaCdas", model: [result: result])
        
        }else{
            render(view: "listaCdas", model: [result: null])
            //  render "<p>No se encontró CDA </p>"
        }

    }

    def buscarCdaByPaciente = {

    
        //CUAL ES MI ID DE ORGANIZACION ????

        List<cda.BuscarCDAByPacienteResponse> result = customSecureServiceClient.buscarCDAByPaciente("12345678",0)
        println "Resultado CDAs: "+ result

        if(result){

            render(view: "listaCdas", model: [result: result])
        }else{
            render(view: "listaCdas", model: [result: null])
            //  render "<p>No se encontró CDA </p>"
        }


    }
    def buscarCdaByPacienteAndOrganizacion = {

        //CUAL ES MI ID DE ORGANIZACION ????

        List<cda.BuscarCDAByPacienteAndOrganizacionResponse> result = customSecureServiceClient.buscarCDAByPacienteAndOrganizacion("12345678","2",0)
        println "Resultado CDAs: "+ result.id
        if(result){
            render(view: "listaCdas", model: [result: result])
        }else{
            render(view: "listaCdas", model: [result: null])
            //  render "<p>No se encontró CDA </p>"
        }


    }

     def buscarCdaByPacienteAndRango = {

        //CUAL ES MI ID DE ORGANIZACION ????

        List<cda.BuscarCDAByPacienteAndRangoResponse> result = customSecureServiceClient.buscarCDAByPacienteAndRango(
            "12345678",
            XMLGregorianCalendarConverter.getXMLCalendar("2010-08-30","yyyy-MM-dd"),
            XMLGregorianCalendarConverter.getXMLCalendar("2010-10-30","yyyy-MM-dd"),
            0)
        
        println "Resultado CDAs: "+ result.id
        if(result){
            render(view: "listaCdas", model: [result: result])
        }else{
            render(view: "listaCdas", model: [result: null])
            //  render "<p>No se encontró CDA </p>"
        }


    }


    def buscarCdaById = {
        // XStream x = new XStream()

        /*  def wsdlURL = wsdl[1]
        proxy = webService.getClient(wsdlURL)
         */
        cda.CdaArr result = customSecureServiceClient.buscarCDAById(params.id.toInteger())
        println "Resultado CDAs: "+ result
        if(result){
            // Object string = (Object) x.fromXML(result)
            //  def aux =  x.fromXML(result)
            //render(view: "listaCdas", model: [result: aux])
            if(params.render == 'xml'){
                render(text: result.getDocumento(),contentType:"text/xml",encoding:"UTF-8")

            }else if(params.render == 'cda'){


                
                def cda_xml = new StringReader(result.getDocumento())
                def cda_xsl = new File(ApplicationHolder.application.config.hce.rutaDirCDAs + '\\' + 'CDA.xsl')
                //def cda_xsl = new File(ApplicationHolder.application.config.hce.rutaDirCDAs + '\\' + 'CDA_CDATA.xsl')

                def factory = TransformerFactory.newInstance()
                def transformer = factory.newTransformer(new StreamSource(cda_xsl))

                StringWriter salida = new StringWriter()
                transformer.transform(new StreamSource(cda_xml), new StreamResult(salida))

                render(text:salida.toString().replace("&lt;","<").replace("&gt;",">"),contentType:"text/html",encoding:"UTF-8")

            }
        }else{
            render(view: "listaCdas", model: [result: null])
            //  render "<p>No se encontró CDA </p>"
        }

    }
    
   

    /* def crearPaciente = {
    XStream x = new XStream()
       
    def wsdlURL = wsdl[0]
    proxy = webService.getClient(wsdlURL)

    def objPaciente = new Paciente()
    objPaciente.setIdPaciente("18-F")
    objPaciente.setPrimerNombre("Armando")
    objPaciente.setPrimerApellido("Prieto")
    objPaciente.setCedula("18304748")
    objPaciente.setPasaporte("456987")
        
    def xmlPaciente= x.toXML(objPaciente)
        
    def result = proxy.crearPaciente(xmlPaciente)

    if(result){

    render "<p>Bien</p>"
    }else{
    render "<p>Mal</p>"
    }


    }*/


    /*   def buscarPaciente = {

    XStream x = new XStream()
    def wsdlURL = wsdl[0]
    proxy = webService.getClient(wsdlURL)

    def objPaciente = new Paciente()
    objPaciente.setIdPaciente("18-F")
    objPaciente.setPrimerNombre("Armando")
    //   objPaciente.setSegundoNombre("Armando")

    objPaciente.setPrimerApellido("Prieto")
    // objPaciente.setSegundoApellido("Padron")


    // objPaciente.setCedula("18304748")
    //  objPaciente.setPasaporte("45697")

    def xmlPaciente= x.toXML(objPaciente)

    def result = proxy.buscarPaciente(xmlPaciente)
    //println result
    if(result){
    def aux =  x.fromXML(result)
    //println "Este es el resultado: "+aux
        
    render(view: "listaPacientes", model: [result: aux])
    }else{
    render(view: "listaPacientes", model: [result: null])
    }


       
    }*/

    /* def alcance = {

    def wsdlURL = wsdl
    proxy = webService.getClient(wsdlURL)


    def result = proxy.pruebaAlcance()

    render "<p>PROBADO</p>"
    }


    def otro = {

    def wsdlURL = wsdl
    proxy = webService.getClient(wsdlURL)
    //def r = proxy.setBasicAuthentication("user", "password")

    def result = proxy.otroAlcance()

    render "<p>OTRO: ${result}</p>"



    }
     */
    /*  def sinConexion = {


    render(view: 'sinServicio')


    }*/

    //Indica que el interceptor será 'conexion' y no interceptará a 'sinConexion'
    /*  def beforeInterceptor = [action: this.&conexion,except:'sinConexion']

    private def conexion = {
    //  redirect(action: 'sinConexion')
        
    try{
    def wsdlURL = wsdl
    proxy = webService.getClient(wsdlURL)
               
    }
    catch(Exception e){
    println "-----------Web Service Exception---------------"
    e.printStackTrace()
    println "-----------------------------------------------"
    }

    if(proxy){
    println "Si hay servicio"
    return true
    }else{

    println "No hay servicio"
    redirect(action: 'sinConexion')
    return false
    //redirect(controller:'records', action:'index')
            
    }


    }*/

    /*
    def logueo = {

    def wsdlURL = wsdl[1]
    proxy = webService.getClient(wsdlURL)
    proxy.logueo("hola", "lola")
      



    }*/


}



