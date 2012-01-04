package imp


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
import imp.PacienteArr
import converters.*
import java.util.ArrayList

import demographic.party.*

class ServiceController {
    /*
    WebService webService
    def wsdl = ApplicationHolder.application.config.wsdl
    def proxy
     */
    def customSecureServiceClientCda
    def customSecureServiceClientImp
    // def complexServiceClient

    def demographicService

    def index = {

        customSecureServiceClientCda.serviceMethod()

        render("<h1>Bien</h1>")
    }


    def registrarCda = {

        //ID 'token' asignado a la organizacion en el IMP
        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id

        def cda = new CdaArr()

        cda.id = params.id //ESTE ES EL ID QUE TIENE ASIGNADO EN ESTE SISTEMA
        cda.titulo = "Algun titulo"
        cda.fechaCreacion = "1988-05-20" //formato dd-MM-yyyy
        def cda_xml = new File(ApplicationHolder.application.config.hce.rutaDirCDAs + '\\' + params.id + '.xml')

        if(!cda_xml.exists()){

            render "<p> Archivo: NO Existe</p>"
            return false
        }else{


            cda.documento = cda_xml.getText() //xml

            def result1 = customSecureServiceClientCda.registrarCDA(cda,"15",idOrganizacion)
            render "<p>Listo revisar </p> <p>${result1}</p>"
            return true

        }

        



    }
    def eliminarCda = {
        //ID 'token' asignado a la organizacion en el IMP
        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id

        long a = 19
        def result = customSecureServiceClientCda.eliminarCDA(a,"15",idOrganizacion)

        render "<p>"+result+"</p>"

    }

    def buscarCdaByRango = {
        
       //ID 'token' asignado a la organizacion en el IMP
        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id
        cda.ConjuntoCda result = customSecureServiceClientCda.buscarCDAByRango(
            XMLGregorianCalendarConverter.getXMLCalendar("1987-08-30","yyyy-MM-dd"),
            XMLGregorianCalendarConverter.getXMLCalendar("2010-10-30","yyyy-MM-dd"),
            0,
            idOrganizacion)
        
       // println "Resultado CDAs: "+ result
        if(result){
            
            render(view: "listaCdas", model: [result: result.listCdaArr, total: result.total])
        
        }else{
            render(view: "listaCdas", model: [result: null])
            //  render "<p>No se encontró CDA </p>"
        }

    }
    def buscarCdaByPaciente = {
        
    
        //ID 'token' asignado a la organizacion en el IMP
        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id

        cda.ConjuntoCda result = customSecureServiceClientCda.buscarCDAByPaciente(params.id,0,idOrganizacion)
        //println "Resultado CDAs: "+ result

        if(result){

            render(view: "listaCdas", model: [result: result.listCdaArr, total: result.total])
        }else{
            render(view: "listaCdas", model: [result: null])
            //  render "<p>No se encontró CDA </p>"
        }


    }
    def buscarCdaByPacienteAndOrganizacion = {
        //ID 'token' asignado a la organizacion en el IMP
        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id
        
        long a= params.org.toLong()
        cda.ConjuntoCda result = customSecureServiceClientCda.buscarCDAByPacienteAndOrganizacion(
            params.pac,
            a,
            0,
            idOrganizacion)
    
      //  println "Resultado CDAs: "+ result.id
        if(result){
            render(view: "listaCdas", model: [pacienteId: params.pac, result: result.listCdaArr, total: result.total, llamar:'busquedaAllExterna'])
        }else{
            render(view: "listaCdas", model: [pacienteId: params.pac,result: null])
            //  render "<p>No se encontró CDA </p>"
        }


    }
    def buscarCdaByPacienteAndRango = {

        
        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id
        List<cda.BuscarCDAByPacienteAndRangoResponse> result = customSecureServiceClientCda.buscarCDAByPacienteAndRango(
            params.id,
            XMLGregorianCalendarConverter.getXMLCalendar("1950-08-30","yyyy-MM-dd"),
            XMLGregorianCalendarConverter.getXMLCalendar("2000-10-30","yyyy-MM-dd"),
            0,
            idOrganizacion)
        
        println "Resultado CDAs: "+ result.id
        if(result){
            render(view: "listaCdas", model: [result: result])
        }else{
            render(view: "listaCdas", model: [result: null])
            //  render "<p>No se encontró CDA </p>"
        }


    }
    def buscarCdaById = {
        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id
        cda.CdaArr result = customSecureServiceClientCda.buscarCDAById(params.id.toInteger(), idOrganizacion)
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
    def listarOrganizaciones = {

        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id
        List<cda.OrgArr> result = customSecureServiceClientCda.listarOrganizacionesByPaciente(params.id, idOrganizacion)

        
        render(template: "../demographic/listadoOrganizaciones", model: [pacienteId:params.id, result: result])
        


    }
    def agregarPaciente = {

        def person=   Person.get(params.id)

        def personNamePatient = person.identities.find{
            it.purpose == 'PersonNamePatient'
        }

        
        if(personNamePatient){
            //   def paciente = Paciente.get(params.id)

            //RECIBIR PARAMS DEL PACIENTE

            def p = new PacienteArr()
            p.setIdPaciente(params.id) //ESTE ES EL ID QUE TIENE ASIGNADO EN ESTE SISTEMA
            //  p.setCedula(paciente.getCedula())
            //  p.setPasaporte(paciente.getPasaporte())
            p.setPrimerNombre(personNamePatient.getPrimerNombre())
            p.setSegundoNombre(personNamePatient.getSegundoNombre())
            p.setPrimerApellido(personNamePatient.getPrimerApellido())
            p.setSegundoApellido(personNamePatient.getSegundoApellido())



            //ID 'token' asignado a la organizacion en el IMP
            String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id

            def result = customSecureServiceClientImp.agregarPaciente(p, idOrganizacion)
            if(result){
               flash.message = "service.imp.agregarPaciente.true"
                flash.clase = "ok"

                 
            }else{
               flash.message = "service.imp.agregarPaciente.false"
                flash.clase = "error"
            }
            
            redirect(controller:'demographic', action: 'seleccionarPaciente', params: [id: params.id] )

        }else{

            render("<p>El paciente no existe</p>")

        }
    }
    def eliminarPaciente = {

        def person=   Person.get(params.id)

        if(person){
            def personNamePatient = person.identities.find{
                it.purpose == 'PersonNamePatient'
            }

            if(personNamePatient){
                String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id
                def result = customSecureServiceClientImp.eliminarPaciente(params.id, idOrganizacion)
                if(result){
                    flash.message = "service.imp.eliminarPaciente.true"
                    flash.clase = "ok"
                  

                }else{
                    flash.message = "service.imp.eliminarPaciente.false"
                    flash.clase = "error"
                   
                }

                redirect(controller:'demographic', action: 'seleccionarPaciente', params: [id: params.id] )
            }else{

                render("<p>El paciente no existe</p>")

            }
        }else{

            render("<p>El Person no existe</p>")
        }

    }
    def buscarPaciente = {
        println "OFFSET PARAMS::::"+params.offset
        def persona = Person.get(params.id)
        def name = persona.identities.find{ it.purpose == 'PersonNamePatient'}
        
        def objPaciente = new PacienteArr()
        objPaciente.setIdPaciente(params.id)
        
        objPaciente.setPrimerNombre(name.primerNombre)
        objPaciente.setSegundoNombre(name.segundoNombre)
        objPaciente.setPrimerApellido(name.primerApellido)
        objPaciente.setSegundoApellido(name.segundoApellido)


        // objPaciente.setCedula("18304748")
        //  objPaciente.setPasaporte("45697")

       // def offset= (Integer) (params.offset.toInteger() / 10)
        def offset= params.offset.toInteger()
        println "El otro::::"+offset
         String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id
        imp.ConjuntoPaciente result = customSecureServiceClientImp.buscarCandidatos(objPaciente,offset,idOrganizacion )
        //println result
        if(result){

            //println "Este es el resultado: "+aux

            // render result.idCentro


            render(template: "candidatos", model: [idPacienteOrg: params.id, result: result.listPacienteArr, total: result.total])
        }else{
            render(template: "candidatos", model: [idPacienteOrg: params.id, result: null])
        }



    }
    def agregarRelacionPaciente = {

        //ID 'token' asignado a la organizacion en el IMP
        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id

        long idCentroImp= params.idCentroImp.toLong()
        String idPacienteImp= params.idPacienteImp
        String idPacienteOrg = params.idPacienteOrg
        def result = customSecureServiceClientImp.agregarRelacionPaciente(idCentroImp,idPacienteImp,idPacienteOrg,idOrganizacion)

         if(result){
                    flash.message = "service.imp.agregarRelacionPaciente.true"
                    flash.clase = "ok"
                   // flash.args = [ "The Stand" ]
                   // flash.default = "Paciente eliminado"

                }else{
                    flash.message = "service.imp.agregarRelacionPaciente.false"
                    flash.clase = "error"
                   // flash.args = [ "The Stand" ]
                  //  flash.default = "Paciente no eliminado"
                }

                redirect(controller:'demographic', action: 'seleccionarPaciente', params: [id: params.idPacienteOrg] )


    }
    def eliminarRelacionPaciente = {

        //ID 'token' asignado a la organizacion en el IMP
        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id

        def result = customSecureServiceClientImp.eliminarRelacionPaciente(params.id,idOrganizacion)

          if(result){
                    flash.message = "service.imp.eliminarRelacionPaciente.true"
                    flash.clase = "ok"
                   // flash.args = [ "The Stand" ]
                   // flash.default = "Paciente eliminado"

                }else{
                    flash.message = "service.imp.eliminarRelacionPaciente.false"
                    flash.clase = "error"
                   // flash.args = [ "The Stand" ]
                  //  flash.default = "Paciente no eliminado"
                }

                redirect(controller:'demographic', action: 'seleccionarPaciente', params: [id: params.id] )


    }


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



