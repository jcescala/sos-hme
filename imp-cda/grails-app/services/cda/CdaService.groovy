package cda
import imp.*
import com.thoughtworks.xstream.*
import java.io.OutputStream.*
import converters.DateConverter
import javax.jws.*
//import org.springframework.web.context.request.RequestContextHolder

import converters.*
import com.cxf.demo.CdaResponse
import com.cxf.demo.CdaArr

import admin.User

class CdaService {
    
    
    
    static expose=['cxf']
    static transactional = true
    def max = 10
   

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

            org1.user = User.get(1)


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
            org2.user = User.get(1)

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
            org3.user = User.get(1)

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


    /**
     * Busca un conjunto de documentos CDA en un rango de fechas
     *
     * @param desde rango de fecha de inicio
     * @param desde rango de fecha de final
     * @return List<CdaArr> una lista de documentos CDA´s,
     * retorna null en caso de no encontrar coincidencias.
     */
    List<CdaArr> buscarCDAByRango(def desde, def hasta, def offset){

        //Formateando los datos a tipo java.lang.Date
        desde= XMLGregorianCalendarConverter.asDate(desde)
        hasta= XMLGregorianCalendarConverter.asDate(hasta)


        def result = Cda.withCriteria{
            between('fechaCreacion',desde.format("yyyy-MM-dd"),hasta.format("yyyy-MM-dd"))
            order("fechaCreacion","desc")
            maxResults(this.max)
            firstResult(offset)
        }

        if(result){

            def cd
            cd = []
            result.each{

                def cda = new CdaArr()
                cda.id=it.id.toString()
                cda.fechaCreacion=it.fechaCreacion
                cda.titulo=it.titulo
                cda.documento=""
                cd.add(cda)

            }

            println "La clase es:"+  cd.getClass()
            return cd
        }else{

            return null

        }


    }

    /**
     * Busca un documentos CDA por su identificador unico
     *
     * @param id identificador del documento
     * @return CdaArr documento CDA,
     * retorna null en caso de no encontrar coincidencia.
     */
    CdaArr buscarCDAById(int id){

        def result = Cda.findById(id)

        if(result){


            def cda = new CdaArr()
            cda.id=result.id.toString()
            cda.fechaCreacion=result.fechaCreacion
            cda.titulo=result.titulo
            cda.documento=result.documento



            println "La clase es:"+  cda.getClass()
            return cda
        }else{

            return null

        }

    }

    /**
     * Busca un conjunto de documentos CDA pertenecientes a un paciente
     *
     * @param paciente identificador unico de paciente
     * perteneciente a la organizacion que realiza la consulta
     * @return List<CdaArr> una lista de documentos CDA´s,
     * retorna null en caso de no encontrar coincidencias.
     */
    List<CdaArr> buscarCDAByPaciente(String paciente, def offset){

        //  XStream x = new XStream()
      /*  def result = Paciente.findByIdPaciente(paciente)
        //def result = Paciente.findAllById(paciente)
        if(result){
            //DEBERIA EXISTIR UN SOLO PACIENTE CON ESE ID
            //result = Cda.findAllById(result.cdas.id)
            def cd
            cd = []
            result.cdas.each{

                def cda = new CdaArr()
                cda.id=it.id.toString()
                cda.fechaCreacion=it.fechaCreacion
                cda.titulo=it.titulo
                cda.documento=""
                cd.add(cda)

            }

            return cd
        }else{
            return null
        }*/

        def pac = Paciente.findByIdPaciente(paciente)

        def result = Cda.findAllByPaciente(pac, [max:this.max, offset: offset])

        def cd = []
        if(result){

            result.each{

                def cda = new CdaArr()
                cda.id=it.id.toString()
                cda.fechaCreacion=it.fechaCreacion
                cda.titulo=it.titulo
                cda.documento=""
                cd.add(cda)


            }
            return cd

        }else{

            return null
        }


    }

    /**
     * Busca un conjunto de documentos CDA pertenecientes a un paciente y
     * en un rango determinado
     *
     * @param paciente identificador unico de paciente
     * perteneciente a la organizacion que realiza la consulta
     * @param desde rango de fecha de inicio
     * @param desde rango de fecha de final
     * @return List<CdaArr> una lista de documentos CDA´s,
     * retorna null en caso de no encontrar coincidencias.
     */
    List<CdaArr> buscarCDAByPacienteAndRango(def paciente, def desde, def hasta, def offset){
        //Formateando los datos a tipo java.lang.Date
        desde= XMLGregorianCalendarConverter.asDate(desde)
        hasta= XMLGregorianCalendarConverter.asDate(hasta)

        def pac = Paciente.findByIdPaciente(paciente)
        def result = Cda.withCriteria{
            between("fechaCreacion",desde.format("yyyy-MM-dd"),hasta.format("yyyy-MM-dd"))
            eq("paciente", pac)
            maxResults(this.max)
            firstResult(offset)

        }

        if(result){

            def cd
            cd = []
            result.each{

                def cda = new CdaArr()
                cda.id=it.id.toString()
                cda.fechaCreacion=it.fechaCreacion
                cda.titulo=it.titulo
                cda.documento=""
                cd.add(cda)

            }

            println "La clase es:"+  cd.getClass()
            return cd
        }else{

            return null

        }



    }


    /**
     * Busca un conjunto de documentos CDA pertenecientes a un paciente y
     * a una organizacion determinada
     *
     * @param paciente identificador unico de paciente
     * perteneciente a la organizacion que realiza la consulta
     * @param organizacion identificador unico de la organizacion a consultar
     * @return List<CdaArr> una lista de documentos CDA´s,
     * retorna null en caso de no encontrar coincidencias.
     */
    List<CdaArr> buscarCDAByPacienteAndOrganizacion(String paciente, String organizacion,def offset){

        //CUAL ES LA ORGANIZACION ID ????
        def org = Organizacion.get(organizacion.toInteger())

        //SI PREGUNTO CON CRITERIA DEVUELVE UN OBJETO Y NO UN LIST
        /*        def result = Paciente.withCriteria{

        and {
        eq('cedula', paciente)
        eq('centro',org)
        }
        }
         */
        def pac = Paciente.findByIdPacienteAndCentro(paciente,org)
        def result = Cda.findAllByPaciente(pac, [max:this.max, offset: offset])

        if(result){
            println "HAY RESULTADO POSITIVO: "+result

            //DEBERIA EXISTIR UN SOLO PACIENTE CON ESE ID
            def cd
            cd = []
            result.each{

                def cda = new CdaArr()

                cda.id=it.id.toString()
                cda.fechaCreacion=it.fechaCreacion
                cda.titulo=it.titulo
                cda.documento=""
                cd.add(cda)

            }

            return cd
        }else{

            println "NO HAY RESULTADO POSITIVO"
            return null
        }



    }





    
}
