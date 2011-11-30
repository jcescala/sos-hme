package cda
import imp.*
import com.thoughtworks.xstream.*
import java.io.OutputStream.*
import converters.DateConverter
import javax.jws.*
import org.springframework.web.context.request.RequestContextHolder

class CdaService {
    
    
    
    static expose=['cxf']
    static transactional = true

    def serviceMethod() {

        println "Es seguro el servicio"

    }
    def logueo(String login, String password){

    println "L : "+ login
    println "P : "+ password

      }

  def registrarCDA(String cda){

        //cda es un documento xml CDA completo para ser parseado

       def f = new File("CDAs/CDA-4-V0-20100930215321.xml")

        if(!f.exists()){

            println "Archivo: NO Existe"
            return false
        }


        def ClinicalDocument = new XmlParser().parseText(f.getText())

        //CLINICAL DOCUMENT
        def id = ClinicalDocument.id.@extension[0]
        def titulo = ClinicalDocument.title.text() //Titulo del CDA
        def fechaCreacion = ClinicalDocument.effectiveTime.@value[0]

        //RECORD TARGET (PACIENTE)
        def idPaciente = ClinicalDocument.recordTarget.patientRole.id.@extension[0]
        def nombrePaciente = ClinicalDocument.recordTarget.patientRole.patient.name.given.text()
        def apellidoPaciente = ClinicalDocument.recordTarget.patientRole.patient.name.family.text()


        //AUTHOR
        def idAutor =  ClinicalDocument.author.assignedAuthor.id.@extension[0]
        def nombreAutor = ClinicalDocument.author.assignedAuthor.assignedPerson.name.given.text()
        def apellidoAutor = ClinicalDocument.author.assignedAuthor.assignedPerson.name.family.text()
        def idOrganizacionAutor = ClinicalDocument.author.assignedAuthor.representedOrganization.id.@root[0]
        def nombreOrganizacionAutor = ClinicalDocument.author.assignedAuthor.representedOrganization.name.text()

        //CUSTODIAN
        def idCustodio = ClinicalDocument.custodian.assignedCustodian.representedCustodianOrganization.id.@root[0]
        def nombreCustodio = ClinicalDocument.custodian.assignedCustodian.representedCustodianOrganization.name.text()


        //LEGAL AUTENTIFICATOR
        def idAutentificador =  ClinicalDocument.legalAuthenticator.assignedEntity.id.@extension[0]
        def nombreAutentificador = ClinicalDocument.legalAuthenticator.assignedEntity.assignedPerson.name.given.text()
        def apellidoAutentificador = ClinicalDocument.legalAuthenticator.assignedEntity.assignedPerson.name.family.text()

        def idOrganizacionAutentificador = ClinicalDocument.legalAuthenticator.assignedEntity.representedOrganization.id.@root[0]
        def nombreOrganizacionAutentificador = ClinicalDocument.legalAuthenticator.assignedEntity.representedOrganization.name.text()

        //---------------------------------------------------------------------------------------
        //CREANDO EL OBJETO CDA

        def docCda = new Cda()

        docCda.titulo = titulo
        docCda.fechaCreacion = DateConverter.fromHL7DateFormat(fechaCreacion).format("yyyy-MM-dd")
        docCda.documento =f.getText()



        def per = Personal.existePersonal(idAutor)
        if(!per){
            per = new Personal()
            per.setCedula(idAutor)
            per.setNombre(nombreAutor)
            per.setApellido(apellidoAutor)
            per.save()
        }
        docCda.setPersonalAutor(per)


        def org1 = Organizacion.existeOrganizacion(nombreOrganizacionAutor)
        if(!org1){
            org1=new Organizacion()
            org1.nombre = nombreOrganizacionAutor
            org1.login = "ninguno1"
            org1.password = "ninguno"

            org1.save()
        }

        docCda.setOrganizacionAutora(org1)

        def pac = Paciente.existePaciente(idPaciente)
        if(!pac){
            pac= new Paciente()
            pac.setIdPaciente(idPaciente)
            pac.setCedula(idPaciente)
            pac.setPrimerNombre(nombrePaciente)
            pac.setPrimerApellido(apellidoPaciente)

            def ind = new IndicePaciente()
            ind.save()
            pac.setIndice(ind)

            //POR DEFECTO ESTA ES LA ORGANIZACION A LA QUE PERTEECE EL PACIENTE

            pac.setCentro(org1)


            pac.save()
        }
        docCda.setPaciente(pac)

















        def org2 = Organizacion.existeOrganizacion(nombreCustodio)
        if(!org2){
            org2=new Organizacion()
            org2.nombre = nombreCustodio
            org2.login = "ninguno2"
            org2.password = "ninguno"
            org2.save()
        }
        docCda.setOrganizacionCustodia(org2)

        def per2 = Personal.existePersonal(idAutentificador)
        if(!per2){
            per2= new Personal()
            per2.setCedula(idAutentificador)
            per2.setNombre(nombreAutentificador)
            per2.setApellido(apellidoAutentificador)
            per2.save()
        }
        docCda.setPersonalAutentificador(per2)

        def org3 = Organizacion.existeOrganizacion(nombreOrganizacionAutentificador)
        if(!org3){
            org3=new Organizacion()
            org3.nombre = nombreOrganizacionAutentificador
            org3.login = "ninguno3"
            org3.password = "ninguno"
            org3.save()
        }
        docCda.setOrganizacionAutentificadora(org3)


        if( !docCda.save() ) {


            docCda.errors.each {
                println it
            }
            return false
        }else{
            println "Si se guardo"
            return true
        }

return true

    }
    def buscarCDAByRango(Date desde, Date hasta){
        def x = new XStream();


        def result = Cda.withCriteria{
            between('fechaCreacion',desde.format("yyyy-MM-dd"),hasta.format("yyyy-MM-dd"))
        }

        if(result){

            //OMITO LOS CAMPOS INDICE, CDAS Y ID para estar de acorde al POJO
            x.omitField(result[0].class, "paciente")
            x.omitField(result[0].class, "personalAutor")
            x.omitField(result[0].class, "personalAutentificador")
            x.omitField(result[0].class, "organizacionAutora")
            x.omitField(result[0].class, "organizacionCustodia")
            x.omitField(result[0].class, "organizacionAutentificadora")


            def aux = x.toXML(result)
            println aux
            println "Hay resultados"
            return aux

        }else{

            return false

        }

        /*    def resultado = Cda.executeQuery("select cda.documento from Cda cda")

        if(resultado){

        return x.toXML(resultado)

        }else{

        return false
        }
         */

    }
    def buscarCDAById(int id){
        XStream x = new XStream()
        def result = Cda.findAllById(id)

        if(result){
            println "Hay resultados"
            println "Resultado: "+ result


            x.omitField(result[0].class, "paciente")
            x.omitField(result[0].class, "personalAutor")
            x.omitField(result[0].class, "personalAutentificador")
            x.omitField(result[0].class, "organizacionAutora")
            x.omitField(result[0].class, "organizacionCustodia")
            x.omitField(result[0].class, "organizacionAutentificadora")


            def aux = x.toXML(result)
            println aux
            // println "Hay resultados"
            return aux



        }else{

            return false

        }

    }
    def buscarCDAByPaciente(String paciente){
        XStream x = new XStream()
        def result = Paciente.findByIdPaciente(paciente)
        //def result = Paciente.findAllById(paciente)
        if(result){


            //DEBERIA EXISTIR UN SOLO PACIENTE CON ESE ID
            println result.cdas.id[0]
            result = Cda.findAllById(result.cdas.id[0])


            x.omitField(result[0].class, "paciente")
            x.omitField(result[0].class, "personalAutor")
            x.omitField(result[0].class, "personalAutentificador")
            x.omitField(result[0].class, "organizacionAutora")
            x.omitField(result[0].class, "organizacionCustodia")
            x.omitField(result[0].class, "organizacionAutentificadora")



            def aux = x.toXML(result)
            println aux
            return aux
        }else{


            return false
        }


    }
    def buscarCDAByPacienteAndOrganizacion(String paciente, String organizacion){
        XStream x = new XStream()
        // def result = Paciente.findByIdPacienteAndCentro(paciente,organizacion)
        //def result = Paciente.findAllById(paciente)

        //CUAL ES LA ORGANIZACION ID ????
        def org = Organizacion.get(1)

        def result = Paciente.withCriteria{

            and {
                eq('idPaciente', paciente)
                eq('centro',org)
            }
        }


       // def result = Paciente.findAll("from Paciente as pac where pac.centro=?", [1])

        if(result){
            println "HAY RESULTADO POSITIVO: "+result

            //DEBERIA EXISTIR UN SOLO PACIENTE CON ESE ID
            println result.cdas.id[0]
            result = Cda.getAll(result.cdas.id[0])


            x.omitField(result[0].class, "paciente")
            x.omitField(result[0].class, "personalAutor")
            x.omitField(result[0].class, "personalAutentificador")
            x.omitField(result[0].class, "organizacionAutora")
            x.omitField(result[0].class, "organizacionCustodia")
            x.omitField(result[0].class, "organizacionAutentificadora")



            def aux = x.toXML(result)
            println aux
            return aux
        }else{

             println "NO HAY RESULTADO POSITIVO"
            return false
        }

        return true

    }

/*

    def buscarCDAByPacienteAndRango(String paciente){

        return true
    }

    def buscarCDAByCustodio(String custodio){
        return true
    }
    def buscarCDAByCustodioAndRango(String custodio){
        return true
    }
*/

    
}
