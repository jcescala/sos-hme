/**
 * 
 */
package demographic

import demographic.DemographicAccess
import demographic.LocalDemographicAccess

import hce.core.support.identification.UIDBasedID

import demographic.identity.PersonName
import demographic.identity.*
import demographic.party.Person
import demographic.role.Role


// Configuracion de consulta local o remota
import org.codehaus.groovy.grails.commons.ApplicationHolder

import java.util.*;

//creacion de archivos xml
import groovy.xml.MarkupBuilder
import org.custommonkey.xmlunit.*
import java.lang.Object.*
import groovy.xml.XmlUtil
import groovy.util.XmlSlurper
import javax.xml.parsers.*
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;


/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */
class DemographicService {

    DemographicAccess demographicAccess
    def grailsApplication
    //int queryCount = 0
    
    // FIXME: ojo, capaz necesita instanciarse distinto por GRAILS, los servicesson singletons...
    def DemographicService()
    {
        if (ApplicationHolder.application.config.hce.patient_administration.serviceType.local)
        {
            println "DemographicService - PA LOCAL"
            // TODO: instanciar demographicAccess segun configuracion
            demographicAccess = new LocalDemographicAccess()
        }
        else
        {
            println "DemographicService - PA REMOTO"
            demographicAccess = new PixPdqDemographicAccess()
        }
    }
    
    /**
     * True si la persona tiene 1 id, primer nombre, primer apellido, sexo y fecha nacimiento.
     * @param p
     * @return
     */
    public boolean personHasAllData( Person p )
    {
        //println "Person: "+ p
        //println "Identities: " + p.identities
        
        if (!p.identities || p.identities.size()==0) return false
        
        def pn = p.identities.find{ it.purpose == 'PersonName' }
        if (!pn) return false
        
        return ( p.sexo && p.fechaNacimiento && pn.primerNombre && pn.primerApellido && p.ids.size()>0 )
    }
    
    /**
     * Encuentra otros identificadores de la persona, a partir de un identificador conocido.
     * Se corresponde a PIX ids query con un solo identificador como criterio de busqueda.
     */
    public List<UIDBasedID> findIdsById( UIDBasedID id )
    {
        //this.queryCount++
        //println queryCount
        return demographicAccess.findIdsById(id)
    }
    
    /**
     * Encuentra otros identificadores de la persona, a partir de los identificadores conocidos.
     * Se corresponde a PIX ids query con muchos identificadores como criterio de busqueda.
     * El resultado debe ser el mismo que si se invoca findIdsById para cada id de ids y luego se hace merge de los resultados.
     */
    public List<UIDBasedID> findIdsByIds( List<UIDBasedID> ids )
    {
        return demographicAccess.findIdsByIds(ids)
    }
    
    /**
     * Busca por extension, Pacientes. 
	 * Cambiar nombre a: findPersonPatientById
     */
    public List<Person> findPersonById( UIDBasedID id )
    {
        //this.queryCount++
        //println queryCount
			
			
			//este query es necesario para filtrar la busqueda de personas por  un id asociado
			//y por el tipo de identidad (patient) que esta tenga.
			def existPerson = Person.withCriteria{
				ids{
					eq("value", id.value)
				}
				identities{
					eq("purpose", "PersonNamePatient")
				}
			
			}
        //return  demographicAccess.findPersonById(id)
		
		return existPerson
    }
	
    /**
     * Busca por extension, Usuarios.
     */
    public List<Person> findPersonUserById( UIDBasedID id )
    {

			def existPerson = Person.withCriteria{
				ids{
					eq("value", id.value)
				}
				identities{
					eq("purpose", "PersonNameUser")
				}
			
			}
		
		return existPerson
    }    
    public List<Person> findByPersonData( PersonName n, Date bithdate, String sex )
    {
        //this.queryCount++
        //println queryCount
        return demographicAccess.findByPersonData(n, bithdate, sex)
    }
    
    public List<Person> findByPersonDataAndId( PersonName n, Date bithdate, String sex, UIDBasedID id )
    {
        return demographicAccess.findByPersonDataAndId(n, bithdate, sex, id)
    }
    
    public List<Person> findByPersonDataAndIds( PersonName n, Date bithdate, String sex, List<UIDBasedID> ids )
    {
        return demographicAccess.findByPersonDataAndIds(n, bithdate, sex, ids)
    }
    
    public List<Person> findByPersonDataAndRole( PersonName n, Date bithdate, String sex, Role role )
    {
        return demographicAccess.findByPersonDataAndRole(n, bithdate, sex, role)
    }
	
    /*
     *@author Angel Rodriguez Leon
     *
     *Busca en la BD los valores de nombres de usuario identificados por el id
	 *y los trae para la creacion de un paciente a partir de estos datos.
     * */  
    public List<Person> findPatientById(UIDBasedID id){
		def candidatosPacientes = Person.withCriteria{

				ids{
					eq("value", id.value)
				}
				identities{

					eq("purpose", "PersonNamePatient")
				}
			
			}
		return candidatosPacientes
	
	}
	
    public List<Person> findUserById(UIDBasedID id){
		def candidatosUsuarios = Person.withCriteria{

				ids{
					eq("value", id.value)
				}
				identities{

					eq("purpose", "PersonNameUser")
				}
			
			}
		return candidatosUsuarios
	
	}
    public List<Person> findByPersonDataAndIdAndRole( PersonName n, Date bithdate, String sex, UIDBasedID id, String roleType )
    {
        return demographicAccess.findByPersonDataAndIdAndRole(n, bithdate, sex, id, roleType)
		/*
				if(id==null){
					id.value="1"
				}

				println "primer nombre: "+n.primerNombre
				if(n.primerNombre == null){
					n.primerNombre = "22"}
				println "segundo nombre: "+n.segundoNombre
				if(n.segundoNombre == null){
					n.segundoNombre="22"}
				println "primer Apellido: "+n.primerApellido
				if(n.primerApellido == null){
					n.primerApellido="22"}
				println "segundo apellido: "+n.segundoApellido
				if(n.segundoApellido == null){
					n.segundoApellido="22"}
				
				println "id: "+id+" date: "+bithdate
				println "sexo: "+sex 
				def candidatosPacientes = Person.withCriteria{
					//eq("sexo",sex)
					and{
						or{
						
							ids{
								eq("value", id.value)
							}
							
							//or{
								identities{
									eq("primerNombre", n.primerNombre)
									
								}
								identities{
									eq("segundoNombre", n.segundoNombre)
									
								}
								identities{
									eq("primerApellido", n.primerApellido)
									
								}
								identities{
									eq("segundoApellido", n.segundoApellido)
									
								}
							//}

						}
						identities{
							eq("purpose", "PersonNamePatient")
						}
						

					}
				}
				println "candidatos: "+candidatosPacientes
				
		return candidatosPacientes*/
	}
    
    public List<Person> findByPersonDataAndIdsAndRole( PersonName n, Date bithdate, String sex, List<UIDBasedID> ids, Role role )
    {
        return demographicAccess.findByPersonDataAndIdsAndRole(n, bithdate, sex, ids, role)
    }
    
    /*
     *@author Juan Carlos Escalante
     *regresa el nombre en los lugares recursivos a partir del id del ultimo hijo 
     *dicho id esta identificado con el nombre Lugar y Direccion de la clase PersonNamePatient*/
    
    public String findFullAddress(Integer lugar){
        def parroquia = null
        def municipio = null
        def estado = null
        def pais = null
        def fullAddress = null
        
        if(lugar){
            def sitio=Lugar.get(lugar)
            //println(sitio.tipolugar)
            if(sitio.tipolugar=="Municipio"){
                municipio =  sitio.nombre
                estado = Lugar.get(sitio.padre.id)
                fullAddress = "municipio "+ municipio + ", estado "+estado.nombre
            }
            if(sitio.tipolugar=="Parroquia"){
                parroquia = sitio.nombre
                municipio = Lugar.get(sitio.padre.id)
                estado = Lugar.get(municipio.padre.id)
                fullAddress = "parroquia "+ parroquia + ", municipio "+ municipio.nombre + ", estado "+estado.nombre
            }
        }
        return fullAddress
    }
    
    public String tokenDireccion(Integer lugar){
        def parroquia = null
        def municipio = null
        def estado = null
        def fullAddress = null
        if(lugar){
           def sitio=Lugar.get(lugar) 
           if(sitio.tipolugar=="Municipio"){
                municipio =  sitio.nombre
                estado = Lugar.get(sitio.padre.id)
                fullAddress = municipio + "#"+estado.nombre
            }
            if(sitio.tipolugar=="Parroquia"){
                parroquia = sitio.nombre
                municipio = Lugar.get(sitio.padre.id)
                estado = Lugar.get(municipio.padre.id)
                fullAddress = parroquia + "#"+ municipio.nombre + "#"+estado.nombre
            }
        }
      return fullAddress  
    }
    
    public String getOcupacion(Integer ocupacion){
        def ocupa = Ocupacion.get(ocupacion)
        return ocupa.nombre
    }
    
    
    public boolean createXML (String tipo){
        Date hoy = new Date()
        def xml = new groovy.xml.StreamingMarkupBuilder().bind(){
            mkp.xmlDeclaration()
            pacientes(){
                fechareporte(hoy.format("dd/MM/yyyy"))
                //nombre()
                //apellido()
            }
            
        }
        def output = XmlUtil.serialize(xml)
        
        def stringWriter = new StringWriter()
        def node = new XmlParser().parseText(output.toString());
        new XmlNodePrinter(new PrintWriter(stringWriter)).print(node)
        
        def writer = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/"+tipo+".xml"))
        //writer.write(output)
        writer.write(stringWriter.toString())
        
        return true
        
    }
    public boolean crearXmlEpi10(String docxml, String fecha, String cedula, String nombre, String direccion, String ocupacion, String edad, String sexo, String[] diagnosticos){
        def ruta = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/"+docxml+".xml")
        
        // bloque java
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(ruta);
        
        Node pacientes = doc.getFirstChild();
        //NamedNodeMap pacientesAttributes = pacientes.getAttributes();
        Node paciente = doc.createElement("paciente");
        pacientes.appendChild(paciente);
        
        Node fechaxml = doc.createElement("fecha")
        fechaxml.setTextContent(fecha)
        paciente.appendChild(fechaxml)
        
        Node cedulaxml = doc.createElement("cedula")
        cedulaxml.setTextContent(cedula)
        paciente.appendChild(cedulaxml)
        
        
        Node nombrexml = doc.createElement("nombre")
        nombrexml.setTextContent(nombre)
        paciente.appendChild(nombrexml)
        
        Node direccionxml = doc.createElement("direccion")
        direccionxml.setTextContent(direccion)
        paciente.appendChild(direccionxml)
        
        Node ocupacionxml = doc.createElement("ocupacion")
        ocupacionxml.setTextContent(ocupacion)
        paciente.appendChild(ocupacionxml)
        
        Node edadxml = doc.createElement("edad")
        edadxml.setTextContent(edad)
        paciente.appendChild(edadxml)
        
        Node sexoxml = doc.createElement("sexo")
        sexoxml.setTextContent(sexo)
        paciente.appendChild(sexoxml)
        
        Node diagnosticosxml = doc.createElement("diagnosticos")
        paciente.appendChild(diagnosticosxml)
        
        def i=diagnosticos.size()
        def j=0
        while(j<=i-1){
            Node diagnosticoxml = doc.createElement("diagnostico")
            diagnosticoxml.setTextContent(diagnosticos[j])
            diagnosticosxml.appendChild(diagnosticoxml)
            j++
        }
        
        def output = XmlUtil.serialize(pacientes)
        /*
        def stringWriter = new StringWriter()
        def node = new XmlParser().parseText(output.toString());
        new XmlNodePrinter(new PrintWriter(stringWriter)).print(node)
        */
        def writer = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/"+docxml+".xml"))
        //writer.write(stringWriter.toString())
        writer.write(output)
        
        return true
    }
    
    public boolean crearXmlEPI10Gen(String docxml, String cedula, String nombre, String fechanacimiento, String direccion, String sexo,String etnia, String niveleducativo,String edad, String[] diagnosticos){
        def ruta = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/"+docxml+".xml")
        
        Date hoy = new Date()
        println("hoy:->"+hoy.format("dd/MM/yyyy"))
        
        
        // bloque java
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(ruta);
        
        Node pacientes = doc.getFirstChild();
        Node paciente = doc.createElement("paciente");
        pacientes.appendChild(paciente);
        
        Node cedulaxml = doc.createElement("cedula");
        cedulaxml.setTextContent(cedula)
        paciente.appendChild(cedulaxml)
        
        Node nombrexml = doc.createElement("nombre");
        nombrexml.setTextContent(nombre)
        paciente.appendChild(nombrexml)
        
        Node fechanacexml = doc.createElement("fechanacimiento");
        fechanacexml.setTextContent(fechanacimiento)
        paciente.appendChild(fechanacexml)
        
        Node direccionxml = doc.createElement("direccion");
        direccionxml.setTextContent(direccion)
        paciente.appendChild(direccionxml)
        
        Node sexoxml = doc.createElement("sexo");
        sexoxml.setTextContent(sexo)
        paciente.appendChild(sexoxml)
        
        Node etniaxml = doc.createElement("etnia")
        etniaxml.setTextContent(etnia)
        paciente.appendChild(etniaxml)
        
        Node educativoxml = doc.createElement("niveleducativo")
        educativoxml.setTextContent(niveleducativo)
        paciente.appendChild(educativoxml)
        
        Node edadxml = doc.createElement("edad");
        edadxml.setTextContent(edad)
        paciente.appendChild(edadxml)
        
        Node diagnosticosxml = doc.createElement("diagnosticos")
        paciente.appendChild(diagnosticosxml)
        
        def i=diagnosticos.size()
        def j=0
        while(j<=i-1){
            Node diagnosticoxml = doc.createElement("diagnostico")
            diagnosticoxml.setTextContent(diagnosticos[j])
            diagnosticosxml.appendChild(diagnosticoxml)
            j++
        }
        
        def output = XmlUtil.serialize(pacientes)
        def writer = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/"+docxml+".xml"))
        writer.write(output)
        return true
    }
    
    public boolean crearXmlEPI13Morbilidad(String docxml, String cedula, String nombre, String fechaNacimiento, 
                                           String direccion, String parroquia, String municipio, String estado,
                                           String fechaRegistro, String sexo, String[] diagnosticos, String semanaInicio, String semanaFin)
    {
        def ruta = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/"+docxml+".xml")
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(ruta);
        
        
        StringTokenizer st = new StringTokenizer(fechaRegistro,"-",false)
        def anioRegistro = st.nextToken().toString()
        def mesRegistro = st.nextToken().toString()
        def diaRegistroBasic = st.nextToken().toString()
        StringTokenizer stk = new StringTokenizer(diaRegistroBasic)
        def diaRegistro = stk.nextToken()
        String fechaRegistroFormato = diaRegistro+"/"+mesRegistro+"/"+anioRegistro
        
        Node pacientes = doc.getFirstChild();
        Node paciente = doc.createElement("paciente");
        pacientes.appendChild(paciente);
        
        Node fechaxml = doc.createElement("fecha");
        fechaxml.setTextContent(fechaRegistroFormato)
        paciente.appendChild(fechaxml)
        
        Node nombrexml = doc.createElement("nombre");
        nombrexml.setTextContent(nombre)
        paciente.appendChild(nombrexml)
        
        Node cedulaxml = doc.createElement("cedula");
        cedulaxml.setTextContent(cedula)
        paciente.appendChild(cedulaxml)
        
        def sexoSigla
        if(sexo=="Masculino"){
            sexoSigla="M"
        }else{
            sexoSigla="F"
        }
        Node sexoxml = doc.createElement("genero");
        sexoxml.setTextContent(sexoSigla)
        paciente.appendChild(sexoxml)
        
        Node fechaNacimientoxml = doc.createElement("fechanacimiento");
        fechaNacimientoxml.setTextContent(fechaNacimiento)
        paciente.appendChild(fechaNacimientoxml)
        
        Node direccionxml = doc.createElement("direccionresidencia");
        direccionxml.setTextContent(direccion)
        paciente.appendChild(direccionxml)
        
        Node estadoxml = doc.createElement("entidad");
        estadoxml.setTextContent(estado)
        paciente.appendChild(estadoxml)
        
        Node municipioxml = doc.createElement("municipio");
        municipioxml.setTextContent(municipio)
        paciente.appendChild(municipioxml)
        
        Node parroquiaxml = doc.createElement("parroquia");
        parroquiaxml.setTextContent(parroquia)
        paciente.appendChild(parroquiaxml)
        
        Node diagnosticosxml = doc.createElement("diagnosticos")
        paciente.appendChild(diagnosticosxml)
        
        def i=diagnosticos.size()
        def j=0
        while(j<=i-1){
            Node diagnosticoxml = doc.createElement("diagnostico")
            diagnosticoxml.setTextContent(diagnosticos[j])
            diagnosticosxml.appendChild(diagnosticoxml)
            j++
        }
        
        StringTokenizer stsi = new StringTokenizer(semanaInicio,"-",false)
        def semanaInicioDia = stsi.nextToken()
        def semanaInicioMes = stsi.nextToken()
        
        StringTokenizer stsf = new StringTokenizer(semanaFin,"-",false)
        def semanaFinDia = stsf.nextToken()
        def semanaFinMes = stsf.nextToken()
        
        String semanaRegistro = "Del "+semanaInicioDia+"/"+semanaInicioMes+" al "+semanaFinDia+"/"+semanaFinMes
        Node semanaxml = doc.createElement("semana");
        semanaxml.setTextContent(semanaRegistro)
        paciente.appendChild(semanaxml)
        
        Node anioxml = doc.createElement("anioregistro");
        anioxml.setTextContent(stsi.nextToken())
        paciente.appendChild(anioxml)
        
        
        def output = XmlUtil.serialize(pacientes)
        def writer = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/"+docxml+".xml"))
        writer.write(output)
        return true
                                           
    }
        
    
    
    
    
    public List getSections(String ruta){
        def sections = []
        this.getDomainTemplates(ruta).keySet().each {

            sections << it
        }
        
        return sections
    }
    
    public Map getDomainTemplates(String path){
        // Nuevo: para devolver los templates del dominio seleccionado
        //def domain = session.traumaContext.domainPath
        def domain = path
        def domainTemplates = grailsApplication.config.templates2."$domain"
        
        return domainTemplates
    }
    
    public List getSubsections( String section, String ruta ){
        def subsections = []
        
        this.getDomainTemplates(ruta)."$section".each { subsection ->
           subsections << section + "-" + subsection
        }
        return subsections
    }
    
    public boolean verificaEnfermedadNotificable(String subgrupo, String codigo){
        def contenido = false
        def gruposNotificables = ["A00","A08","A09","B15","A15","A16","A17","A18","A17","A18","A19","J10","J11",
                                  "A50","Z21","B20","B21","B22","B23","B24","A37","B26","A33","A34","A35","A36",
                                  "B05","B06","A90","A91","A95","B50","B51","B52","B53","B54","B56","B57","A82",
                                  "A27","A87","G00","B01","B16","B17","B19","J12","J13","J14","J15","J16","J17",
                                  "J18","T60","A82","R50","Y40","Y41","Y42","Y43","Y44","Y45","Y46","Y47","Y48",
                                  "Y49","Y50","Y51","Y52","Y53","Y54","Y55","Y56","Y57","Y58","Y59"]
        
        def codigosNotificables = ["A01.0","A92.2","A39.0","A39.9","B17.1","B18.2","G82.0"]
        
        if(codigo=="NULL"){
            if(subgrupo in gruposNotificables){
                contenido = true
            }else{
                contenido = false
            }
        }
        else{
            if(codigo in codigosNotificables || subgrupo in gruposNotificables){
                contenido = true
            }
            else{
                contenido = false
            }
        }
        return contenido
    }
}
