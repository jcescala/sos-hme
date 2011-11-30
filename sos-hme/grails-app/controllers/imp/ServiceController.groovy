package imp

//import org.grails.plugins.wsclient.service.WebService
import imp.Paciente
import com.thoughtworks.xstream.*
import java.io.OutputStream.*
import org.codehaus.groovy.grails.commons.ApplicationHolder
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource
import cda.*
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat
class ServiceController {
/*
    WebService webService
    def wsdl = ApplicationHolder.application.config.wsdl
    def proxy
  */
 def customSecureServiceClient
    def index = {

    customSecureServiceClient.serviceMethod()

    render("<h1>Bien</h1>")
    }


  /*  def registrarCda = {
        XStream x = new XStream()

    

        println "----------------------"

        println "Cosumiendo web service"


        def wsdlURL = wsdl[1]
        println "ESTO ES : "+wsdlURL

        proxy = webService.getClient(wsdlURL)

       // String cda_string = cadenaXML.toString()

        def result1 = proxy.registrarCDA("")

      //  println cadenaXML.toString()
        println "----------------------"

       


        render "<p>Listo revisar </p> <p>${result1}</p>"



    }*/




	public static XMLGregorianCalendar getXMLCalendar(String strDate) throws Exception
	{
		Calendar sDate = Calendar.getInstance();
		DatatypeFactory dtf = DatatypeFactory.newInstance();
		XMLGregorianCalendar calendar = null;

		// Dates (CCYY-MM-DD)
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

		if(strDate != null)
		{
			sDate.setTime(DATE_FORMAT.parse(strDate));
			calendar = dtf.newXMLGregorianCalendar();
			calendar.setYear(sDate.get(Calendar.YEAR));
			calendar.setDay(sDate.get(Calendar.DAY_OF_MONTH));
			calendar.setMonth(sDate.get(Calendar.MONTH)+ 1);
		}

		return calendar;
	}
   
    def buscarCda = {
        XStream x = new XStream()

     /*   def wsdlURL = wsdl[1]
        proxy = webService.getClient(wsdlURL)
*/
        def result = customSecureServiceClient.buscarCDAByRango(getXMLCalendar("2010-08-30"),getXMLCalendar("2010-10-30"))
        println "Resultado CDAs: "+ result
        if(result){
            // Object string = (Object) x.fromXML(result)
            def aux =  x.fromXML(result)
            render(view: "listaCdas", model: [result: aux])
        }else{
              render(view: "listaCdas", model: [result: null])
          //  render "<p>No se encontró CDA </p>"
        }

    }

  /*  def buscarCdaByPaciente = {

     XStream x = new XStream()

        def wsdlURL = wsdl[1]
        proxy = webService.getClient(wsdlURL)

        //CUAL ES MI ID DE ORGANIZACION ????

        def result = proxy.buscarCDAByPaciente("12345678")
        println "Resultado CDAs: "+ result
        if(result){
            // Object string = (Object) x.fromXML(result)
            def aux =  x.fromXML(result)
            render(view: "listaCdas", model: [result: aux])
        }else{
              render(view: "listaCdas", model: [result: null])
          //  render "<p>No se encontró CDA </p>"
        }


    }*/
/* def buscarCdaByPacienteAndOrganizacion = {

     XStream x = new XStream()

        def wsdlURL = wsdl[1]
        proxy = webService.getClient(wsdlURL)

        //CUAL ES MI ID DE ORGANIZACION ????

        def result = proxy.buscarCDAByPacienteAndOrganizacion("12345678","1")
        println "Resultado CDAs: "+ result
        if(result){
            // Object string = (Object) x.fromXML(result)
            def aux =  x.fromXML(result)
            render(view: "listaCdas", model: [result: aux])
        }else{
              render(view: "listaCdas", model: [result: null])
          //  render "<p>No se encontró CDA </p>"
        }


    }*/


 /*   def buscarCdaById = {
        XStream x = new XStream()

        def wsdlURL = wsdl[1]
        proxy = webService.getClient(wsdlURL)

        def result = proxy.buscarCDAById(params.id.toInteger())
        println "Resultado CDAs: "+ result
        if(result){
            // Object string = (Object) x.fromXML(result)
            def aux =  x.fromXML(result)
            //render(view: "listaCdas", model: [result: aux])
          if(params.render == 'xml'){
            render(text: aux[0].documento,contentType:"text/xml",encoding:"UTF-8")

          }else if(params.render == 'cda'){


    
        def cda_xml = new StringReader(aux[0].documento)
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
    */
   

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



