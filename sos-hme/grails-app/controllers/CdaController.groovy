
import hce.core.composition.*
import util.RMLoader
import cda.*
import com.thoughtworks.xstream.XStream
import org.codehaus.groovy.grails.commons.ApplicationHolder
//import org.codehaus.groovy.grails.commons.ApplicationHolder
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

//service
import imp.CdaRecords

class CdaController {

    void imprimirObjetoXML(Object o){
        println "-----------------"
        XStream xstream = new XStream();
        String xml = xstream.toXML(o);
        println xml
        println "-----------------"
    }

    def index = { }

    def create = {
        println "Create CdaControler: " + params

        int idEpisodio = Integer.parseInt(params['id'])
        println "IdEpisodio: " + idEpisodio

        def cd = new CdaRecords()
       def nombreArchCDA=  cd.registrarCda(idEpisodio)
       redirect(controller: "service", action: "registrarCda", id: nombreArchCDA)
        // Creo el archivo CDA
//        def cdaMan = new ManagerCDA()
//        cdaMan.createFileCDA(idEpisodio)

      //  redirect(controller:'records', action:'list')
    }

    def ver = {
//Armando Prieto
//Ccs-Ven
//armando.prieto@ciens.ucv.ve

        println "Ver CdaControler: " + params

        //VER EL CDA ASOCIADO AL EPISODIO
      

        def cda_xml = new File(ApplicationHolder.application.config.hce.rutaDirCDAs + '/' + params['id'] + '.xml')
        def cda_xsl = new File(ApplicationHolder.application.config.hce.rutaDirCDAs + '/' + 'CDA.xsl')
      //def cda_xsl = new File(ApplicationHolder.application.config.hce.rutaDirCDAs + '/' + 'CDA_CDATA.xsl')

        def factory = TransformerFactory.newInstance()
        def transformer = factory.newTransformer(new StreamSource(cda_xsl))

        StringWriter salida = new StringWriter()
        transformer.transform(new StreamSource(cda_xml), new StreamResult(salida))

        render(text:salida.toString().replace("&lt;","<").replace("&gt;",">"),contentType:"text/html",encoding:"UTF-8")
    }

    //--------------------------------------------------------------------------

    //String getStringFecha(Date fecha){
    //    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss")
    //}

    //--------------------------------------------------------------------------

    //String getNombreArchCDA(int numVersion, Date fecha){
    //    return "CDA-" + idEpisodio + "-" + "V" + numVersion + "-" + getStringFecha(fecha) + ".xml"
    //}

    //--------------------------------------------------------------------------
}