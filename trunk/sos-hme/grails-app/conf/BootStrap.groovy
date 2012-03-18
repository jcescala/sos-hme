
import demographic.*
import demographic.contact.*
import demographic.party.*
import demographic.identity.*
import demographic.role.*
import hce.core.support.identification.*
import authorization.*
import hce.core.common.change_control.Version
import hce.HceService
import tablasMaestras.*
import hce.core.data_types.quantity.date_time.*

// TEST Folder
import hce.core.common.directory.Folder
import hce.core.data_types.text.*
import hce.core.common.archetyped.Archetyped
import org.springframework.web.context.support.WebApplicationContextUtils
//import org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib // Para usar g.message

//carga de data inicial
import groovy.sql.Sql
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.codehaus.groovy.grails.commons.ApplicationHolder


//--Interceptores
import javax.security.auth.callback.Callback
import javax.security.auth.callback.CallbackHandler
import javax.security.auth.callback.UnsupportedCallbackException
/*import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor
import org.apache.ws.security.WSConstants
import org.apache.ws.security.WSPasswordCallback
import org.apache.ws.security.handler.WSHandlerConstants
*/

class BootStrap {

    def hceService
    
    // Reference to Grails application. Lo inyecta.
    def grailsApplication
   
/*def customSecureServiceClientCdaFactory
def customSecureServiceClientImpFactory
  */
    def init = { servletContext ->

        println ""
        println "======= +++++++++ ======="
        println "======= Bootstrap ======="
        println "======= +++++++++ ======="
        println ""
        servletContext.conexionImp  = false //Se setea en falso el semaforo de conexion al IMP


   /*
        // TEST Folder
        //def g = new org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib()
        def appContext = WebApplicationContextUtils.getWebApplicationContext( servletContext )
        def messageSource = appContext.getBean( 'messageSource' )
        
        def folder
        def domains = grailsApplication.config.domains
        domains.each { domain ->
           
           folder = new Folder(
              //name: new DvText(value: g.message(code: domain)),
              name: new DvText(value: messageSource.getMessage(domain, new Object[2], new Locale('es'))),
              path: domain,
              archetypeNodeId: "at0001",         // Inventado
              archetypeDetails: new Archetyped(  // Inventado
                archetypeId: 'ehr.domain',
                templateId: 'ehr.domain',
                rmVersion: '1.0.2'
              )
           )
           
           // FIXME: no esta salvando...
           // TODO: setear atributos de Locatable
           
           if (!folder.save())
           {
              println folder.errors
              println folder.name.errors
              println folder.archetypeDetails.errors
           }
        }
        // /TEST Folder
        
        // Correccion de reloj segun uso horario
        // http://groovy.codehaus.org/JN0545-Dates
        // Esto si lo corrige!!!!
        //HORA DE CARACAS-VENEZUELA GMT-04:30
*/
        TimeZone.'default'= TimeZone.getTimeZone('GMT-04:30') //set the default time zone
 /*
 
        println " - START: Carga tablas maestras"
        
        // saco para acelerar la carga
        
        println "   - CIE 10..."
        def codigos = Cie10Trauma.getCodigos()
        codigos.each { codigo ->
           if (!codigo.save()) println codigo.errors
        }
        
        
        println "   - OpenEHR Concepts..."
        def oehr_concepts = OpenEHRConcept.getConcepts()
        oehr_concepts.each { concept ->
           if (!concept.save()) println concept.errors
        }
        
        println "   - Tipos de identificadores..."
        def identificadores = TipoIdentificador.getTipos()
        identificadores.each { id ->
           if (!id.save()) println id.errors
        }
        
        println "   - Motivos de consulta (idem tipos de evento)..."
        def eventos = MotivoConsulta.getTipos()
        eventos.each { evento ->
           if (!evento.save()) println evento.errors
        }
        
        println "   - Empresas emergencia movil..."
        def emergencias = EmergenciaMovil.getEmergencias()
        emergencias.each { emergencia ->
           if (!emergencia.save()) println emergencia.errors
        }
        
        println "   - Departamentos UY..."
        def departamentos = DepartamentoUY.getDepartamentos()
        departamentos.each { dpto ->
           if (!dpto.save()) println dpto.errors
        }
        
        println " - END: Carga tablas maestras"
        
        // TODO: no crear si ya existen
        
        // ----------------------------------------------------------------------------
        
       
        println " - Creacion de pacientes de prueba"
        
        def paciente = new Person()
        //paciente.addToIds( new UIDBasedID(root:'2.16.840.1.113883.2.14.2.1', value:'1234567') )
        //paciente.addToIds( new UIDBasedID(root:'2.16.840.1.113883.2.14.1.1.1.3.1.5.1', value:'6677') )
        paciente.addToIds( new UIDBasedID(value:'2.16.840.1.113883.2.14.2.1::1234567') )
        paciente.addToIds( new UIDBasedID(value:'2.16.840.1.113883.2.14.1.1.1.3.1.5.1::6677') )
        paciente.addToIdentities( new PersonNameUser(primerNombre:'Pedro', primerApellido:'Perez') )
        paciente.fechaNacimiento = new Date(81, 9, 24) // 24/10/1981
        paciente.type = "Person" // FIXME: el type no se setea solo con el nombre de la clase? (Person)
        paciente.sexo = "Masculino"
        if (!paciente.save()) println paciente.errors
        
        def pac2 = new Person()
        //pac2.addToIds( new UIDBasedID(root:'2.16.840.1.113883.2.14.2.4', value:'2345678') )
        //pac2.addToIds( new UIDBasedID(root:'2.16.840.1.113883.2.14.1.1.1.3.1.5.1', value:'3366') )
        pac2.addToIds( new UIDBasedID(value:'2.16.840.1.113883.2.14.2.4::2345678') )
        pac2.addToIds( new UIDBasedID(value:'2.16.840.1.113883.2.14.1.1.1.3.1.5.1::3366') )
        pac2.addToIdentities( new PersonNameUser(primerNombre:'Luis', primerApellido:'Lopez') )
        pac2.fechaNacimiento = new Date(82, 10, 25)
        pac2.type = "Person"
        pac2.sexo = "Masculino"
        if (!pac2.save()) println pac2.errors
        
        def persona3 = new Person()
        //pac3.addToIds( new UIDBasedID(root:'2.16.840.1.113883.4.330.858', value:'6667778') )
        //pac3.addToIds( new UIDBasedID(root:'2.16.840.1.113883.2.14.1.1.1.3.1.5.1', value:'444') )
        persona3.addToIds( new UIDBasedID(value:'2.16.840.1.113883.4.330.858::6667778') )
        persona3.addToIds( new UIDBasedID(value:'2.16.840.1.113883.2.14.1.1.1.3.1.5.1::444') )
        persona3.addToIdentities( new PersonNameUser(primerNombre:'Mata', primerApellido:'Lozano') )
        persona3.fechaNacimiento = new Date(83, 11, 26)
        persona3.type = "Person"
        persona3.sexo = "Femenino"
        if (!persona3.save()) println persona3.errors
        
        def persona4 = new Person()
        persona4.addToIds( new UIDBasedID(value:'2.16.840.1.113883.2.14.2.1::1234888') )
        persona4.addToIds( new UIDBasedID(value:'2.16.840.1.113883.2.14.1.1.1.3.1.5.1::44556') )
        persona4.addToIdentities( new PersonNameUser(primerNombre:'Carlos', primerApellido:'Cardozo') )
        persona4.fechaNacimiento = new Date(85, 9, 24) // 24/10/1981
        persona4.type = "Person"
        persona4.sexo = "Masculino"
        if (!persona4.save()) println persona4.errors
        
        def persona5 = new Person()
        persona5.addToIds( new UIDBasedID(value:'2.16.840.1.113883.4.330.858::45687543') )
        persona5.addToIds( new UIDBasedID(value:'2.16.840.1.113883.2.14.1.1.1.3.1.5.1::2233445') )
        persona5.addToIdentities( new PersonNameUser(primerNombre:'Marcos', primerApellido:'Carisma') )
        persona5.fechaNacimiento = new Date(80, 11, 26)
        persona5.type = "Person"
        persona5.sexo = "Masculino"
        if (!persona5.save()) println persona5.errors
        
        // Paciente con estudios imagenologicos en el CCServer local
        def persona6 = new Person()
        persona6.addToIds( new UIDBasedID(value:'2.16.840.1.113883.4.330.666::2178309') ) // id en el CCServer
        persona6.addToIdentities( new PersonNameUser(primerNombre:'CT', primerApellido:'Mister') )
        persona6.type = "Persona"        
        if (!persona6.save()) println persona6.errors
        
        def persona_administrativo = new Person()
        persona_administrativo.addToIds( new UIDBasedID(value:'2.16.840.1.113883.2.14.2.1::3334442') )
        persona_administrativo.addToIdentities( new PersonNameUser(primerNombre:'John', primerApellido:'Doe') )
        persona_administrativo.type = "Persona"        
        if (!persona_administrativo.save()) println persona_administrativo.errors
        
        // ROLES
        def role1 = new Role(timeValidityFrom:new Date(), timeValidityTo:new Date()+100, type:Role.PACIENTE, performer:paciente, status: '1')
        if (!role1.save()) println role1.errors
            
        def role2 = new Role(timeValidityFrom:new Date(), timeValidityTo:new Date()+100, type:Role.PACIENTE, performer:pac2, status: '1')
        if (!role2.save()) println role2.errors
            
        def role3 = new Role(timeValidityFrom:new Date(), timeValidityTo:new Date()+100, type:Role.PACIENTE, performer:persona4, status: '1')
        if (!role3.save()) println role3.errors
            
        def role4 = new Role(timeValidityFrom:new Date(), timeValidityTo:new Date()+100, type:Role.PACIENTE, performer:persona5, status: '1')
        if (!role4.save()) println role4.errors
            

        
        // Medico
        def role6 = new Role(timeValidityFrom:new Date(), timeValidityTo:new Date()+100, type:Role.MEDICO, performer:persona3, status: '1')
        if (!role6.save()) println role6.errors

        // Administrativo
        def role_adm = new Role(timeValidityFrom:new Date(), timeValidityTo:new Date()+100, type:Role.ADMINISTRATIVO, performer:persona_administrativo, status: '1')
        if (!role_adm.save()) println role_adm.errors
		
		// Admin
        def role_sudo = new Role(timeValidityFrom:new Date(), timeValidityTo:new Date()+100, type:Role.ADMIN, performer:persona6, status: '1')
        if (!role_sudo.save()) println role_sudo.errors
        
        
        // LOGINS
        // los password son encriptados antes de ser creados los login.
        String pass1 = "pass".encodeAsPassword()
        String pass2 = "1234".encodeAsPassword()
        String pass3 = "1234".encodeAsPassword()
		
		
        // Login para el medico   
        def login = new LoginAuth(user:'user', pass:pass1, person:persona3)
        if (!login.save())  println login.errors

        // Login para el adminsitrativo
        def login_adm = new LoginAuth(user:'adm', pass:pass2, person:persona_administrativo)
        if (!login_adm.save())  println login_adm.errors
		
		// Login para el administrador o super usuario
        def login_sudo = new LoginAuth(user:'suuu', pass:pass3, person:persona6)
        if (!login_sudo.save())  println login_sudo.errors
        
        // /Creacion de pacientes
        
        
        /* saco episodio de prueba para no generar problemas...
        
        println " - Creacion de episodio de prueba"
        
        def composition = hceService.createComposition( '2010-01-08 01:23:32', 'El paciente ingresa con dolor en el tobillo' )

        // Agrego el autor a la composición
        //def arrayIds = persona3.ids.toArray()
        //hceService.setCompositionComposer(composition, arrayIds[0].getRoot(), arrayIds[0].getExtension())

        def uidAutor = new UIDBasedID(value:'2.16.840.1.113883.2.14.1.1.1.3.1.5.1::444')
        hceService.setCompositionComposer(composition, uidAutor.getRoot(), uidAutor.getExtension())

        if (!composition.save())
        {
            println "Error: " + composition.errors
        }
        
        // Crea la version inicial
        def version = new Version(
          data: composition,
          timeCommited: new DvDateTime(
            value: '2010-01-08 01:23:32'
          )
        )
        
        if (!version.save())
        {
            println "ERROR: " + version.errors
        }
        
        // /Creacion de episodio
    */

        /*DATA INICIAL*/
       
    /*  
        println " - Datos Iniciales Tablas Demograficas"
        
       
        Sql sql = Sql.newInstance(CH.config.dataSource.url, CH.config.dataSource.username,
        CH.config.dataSource.password, CH.config.dataSource.driverClassName)
        
        String sqlFilePath = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/lugarOptimo.sql")
        String sqlString = new File(sqlFilePath).eachLine {
            sql.execute(it)
        }
        
        String sqlFilePathConyugal = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/situacionConyugal.sql")
        String sqlStringConyugal = new File(sqlFilePathConyugal).eachLine {
            sql.execute(it)
        }
        
        String sqlFilePathNivelEducativo = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/nivelEducativo.sql")
        String sqlStringNivelEducativo = new File(sqlFilePathNivelEducativo).eachLine {
            sql.execute(it)
        }
        
        String sqlFilePathProfesion = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/profesion.sql")
        String sqlStringProfesion = new File(sqlFilePathProfesion).eachLine {
            sql.execute(it)
        }
        
        String sqlFilePathOcupacion = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/ocupacion.sql")
        String sqlStringOcupacion = new File(sqlFilePathOcupacion).eachLine {
            sql.execute(it)
        }
        
        String sqlFilePathEtnia = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/etnia.sql")
        String sqlStringEtnia = new File(sqlFilePathEtnia).eachLine {
            sql.execute(it)
        }
      */ 
        /*FIN DATA INICIAL*/


 

      /*  Map<String, Object> inProps = [:]
        inProps.put(WSHandlerConstants.ACTION, org.apache.ws.security.handler.WSHandlerConstants.USERNAME_TOKEN+" "+org.apache.ws.security.handler.WSHandlerConstants.TIMESTAMP +" "+org.apache.ws.security.handler.WSHandlerConstants.SIGNATURE+ " "+org.apache.ws.security.handler.WSHandlerConstants.ENCRYPT )
        inProps.put(WSHandlerConstants.PASSWORD_TYPE, org.apache.ws.security.WSConstants.PW_DIGEST);
        inProps.put(WSHandlerConstants.PW_CALLBACK_REF, new CallbackHandler() {

              //SE CREA UNA INSTANCIA DE LA CLASE CallbackHandler
              //
              //Se sobreescribe el método handle()

            void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

                    WSPasswordCallback pc = (WSPasswordCallback) callbacks[0]
              
                    pc.setPassword("keystore")


            }
        })




        inProps.put(WSHandlerConstants.SIG_PROP_FILE, "server.properties")
        inProps.put(WSHandlerConstants.DEC_PROP_FILE, "server_key.properties")

        customSecureServiceClientCdaFactory.getInInterceptors().add(new WSS4JInInterceptor(inProps))
        */
        




        println ""
        println "======= +++++++++ ======="
        println "======= /Bootstrap ======="
        println "======= +++++++++ ======="
        println ""
        
     }
     def destroy = {
     }
} 