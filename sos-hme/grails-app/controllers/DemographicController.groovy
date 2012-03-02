/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 *
 */

import demographic.party.*
import demographic.identity.PersonName
import demographic.role.*
import hce.core.support.identification.UIDBasedID

import hce.HceService

import tablasMaestras.TipoIdentificador

import hce.core.composition.*

import com.thoughtworks.xstream.XStream

import converters.DateConverter

// TEST
import demographic.PixPdqDemographicAccess
import org.springframework.context.MessageSource
import org.codehaus.groovy.grails.commons.ApplicationHolder

// Para manejar eventos
import events.*

import util.RandomGenerator
import hce.core.common.archetyped.Locatable

/*Demograficos paciente*/
import demographic.identity.*
import java.text.SimpleDateFormat
import java.util.*
import java.text.*
/**/

import cda.*
import converters.*

import hce.core.common.directory.Folder
import hce.core.common.generic.*

import java.io.*
import org.codehaus.groovy.grails.web.context.ServletContextHolder as SCH


/*reportes*/
import templates.TemplateManager
import tablasMaestras.Cie10Trauma

/*imagenes*/
import javax.activation.MimetypesFileTypeMap
import java.io.File
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import org.apache.commons.io.FileUtils

class DemographicController{

    def hceService
    def demographicService
    
    def customSecureServiceClientCda //Servicios del IMP - CDA
    def customSecureServiceClientImp //Servicios del IMP - IMP
    def index = {
        // Por defecto es la busqueda de pacientes
        redirect(action:'admisionPaciente')
    }
    
    /**
     * Comienzo de la admision del paciente (proceso de identificacion
     * y seleccion del paciente). Es la pantalla de ingreso de criterio de busqueda.
     */
    def admisionPaciente = {
        // TODO: me deberia venir un id de episodio para el cual
        // quiero seleccionar un paciente.
        def tiposIds = TipoIdentificador.list()
        return [tiposIds: tiposIds]
    }
    
    /**
     * Busqueda de candidatos.
     */
    def findPatient = {
        
        println "PARAMS: " + params + "\n"

        //    def pixpdq = new PixPdqDemographicAccess()
        //pixpdq.findIdsById( new UIDBasedID(value:params.identificador) )
        //pixpdq.findPersonById( new UIDBasedID(value:params.identificador) )
        
        //def person = new Person()
        //person.properties = params
        //bindData(person, params, 'person')
        //println "Person: " + person
        
        //if (!params.identificador)
        /*
        if (!params.('person.ids[0].value'))
        {
        flash.message = "Identificador requerido"
        redirect(action:'admisionPaciente')
        return
        }
         */
        
        // TODO: aca va la consulta PIX al maciel.
        // Deberia hacerse como un strategy, definiendo una interfaz comun,
        // tanto para la entrada como para la salida.
        // Cual estrategia se elige, deberia sacarse de la config.
        // En la config dice que IMP se usa.
    
        
        def id = null
        if (params.identificador)
        //println "identificador->: "+params.identificador
        id = new UIDBasedID(value:params.root+'::'+params.identificador)

        // TODO: usar rango de fechas... si viene solo una se usa esa como bd.
        
        // Para la fecha no funciona el bindData, lo hago a mano
        def bd = DateConverter.dateFromParams( params, 'fechaNacimiento_' ) // Si no vienen todos los datos, que sea null
        
        /*
        if (params.useBirthDate)
        {
        //String fecha = params.fechaNacimiento_day+'-'+params.fechaNacimiento_month+'-'+params.fechaNacimiento_year
        //java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy")
        //bd = sdf.parse(fecha)
            
        bd = DateConverter.dateFromParams( params, 'fechaNacimiento_' )
        //println "Date: " + bd
        }
         */

        // TODO: si no hay datos para los nombres, no crear pn.
        def pn = null
        if (params.'personName.primerNombre'  || params.'personName.segundoNombre'||
            params.'personName.primerApellido' || params.'personName.segundoApellido')
        {
            //pn = new PersonName()
            
            //new 
            pn = new PersonName()
            bindData(pn, params, 'personName')
            //println "Person Name: " + pn
        }
        
        /*
        // TODO: todavia no uso el sexo
        def candidatos2 = demographicService.findByPersonData(pn, bd, null)
        println "CANDIDATOS2 : " + candidatos2
         */
        
        println "======================================="
        println "buscando por: "
        println "   PN: "+ pn
        println "   BD: "+ bd
        println "======================================="
        
        //findByPersonDataAndIdAndRole( PersonName n, Date bithdate, String sex, UIDBasedID id, String roleType )
        //def candidatos = demographicService.findByPersonDataAndId(pn, bd, null, id)
        
        def candidatos = []
        try // La comunicacion puede tirar timeout
        {
            // busco en la base local
            candidatos = demographicService.findByPersonDataAndIdAndRole(
                pn,
                bd,
                null,
                id,
                Role.PACIENTE )
    
            // Si el IMP es remoto, salvo los resultados localmente como un cache para facilitar el pedido
            // y mostrado de los datos de los pacientes seleccionados.
            // TODO: vaciar el cache despues de cierto tiempo, y si el paciente de un episodio no esta en
            //       el repositorio local y el IMP es remoto, pedirlo de nuevo con PDQ y cachearlo.
            //       El borrado deberia hacerse con cuidado de no eliminar personal del hospital, el cual
            //       se guarda en la misma tabla que los pacientes y tiene logins asignados.
            //       
            if (!ApplicationHolder.application.config.hce.patient_administration.serviceType.local)
            {
                def candidatosCache = candidatos // Los que se trageron del IMP remoto
                candidatos = [] // Los que se cargan del cache, incluyendo los que se trageron y se guardaron ahora en cache.
                
                candidatosCache.each{ per ->
                    
                    // Ver si esta en cache
                    
                    def perid = per.ids.toArray()[0]
                    def cache = Person.withCriteria {
                        ids {
                            eq('value', perid.value)
                        }
                    }
                    
                    // Ya esta en cache
                    if (cache.size()>0)
                    {
                        // Toma la entrada del cache para no ingresar 2 veces el mismo paciente en el cache
                        per = cache[0]
                    }
                    else // No esta
                    {
                        // FIXME: falta asignar rol paciente.
                        if ( !per.save(flush:true) )
                        {
                            println "Error al salvar persona en cache: " + per.errors
                        }
                        
                        def role = new Role(timeValidityFrom:new Date(), type:Role.PACIENTE, performer:per)
                        if (!role.save())
                        {
                            println "Error al salvar rol en cache: " + role.errors
                        }
                    }
                    
                    // Todos los pacientes resultados de la busqueda, los que ya
                    // estaban en cache y los que se guardaron ahora.
                    candidatos << per
                }
            }
        }
        catch (Exception e)
        {
            flash.message = "Ocurrio un error en la comunicacion, intente de nuevo"
            println "Ocurrio un error en la comunicacion " + e.getMessage()
        }
        
        // ==================================================================
        // TEST
        //pixpdq.findByPersonData(pn, bd, "M")
        //    def result = pixpdq.findByPersonData(pn, bd, null)
        //    XStream xstream = new XStream()
        //    println xstream.toXML(result) + "\n\n"
        // /TEST
        // ==================================================================
        
        
        // OJO! los candidatos de pix y pdq no tienen ID!!!!!
        // lo puse para hacer el OR entre cand 1 y cand 2
        //        def candidatos = candidatos1.plus( candidatos2 ) //.unique{ it.id }
        
        println("numero cantidatos:->"+candidatos.size())

        //Verificar di ya hay un paciente seleccionado para el episodio actual
        def pacienteSeleccionado
        if(session.traumaContext.episodioId){
            pacienteSeleccionado = true

        }else{
            pacienteSeleccionado = false
        }


        render(view:'listaCandidatos', model:[candidatos:candidatos,pacienteSeleccionado:pacienteSeleccionado])
        
    } // findPatient
    
    /**
     * Selecciona a un paciente en el sistema para ser atendido (identificacion positiva).
     * PRE: el paciente debe tener por lo menos 1 id.
     */
    def abc = {

        render "<h1><p>SI FunCA</p></h1>"
        //render(template: 'digo')//[arm: "digo yo"]
    }

    def busquedaExterna = {

        println "ID:::::"+params.id
        println "params.offsetoFFSET::::"+params.offset
        println "params.desde::::"+params.desde
        println "params.hasta::::"+params.hasta
        //params.max
        // params.offset


        def desde = params.desde
        def hasta = params.hasta

        def d
        def h
        def offset

        if(params.marca=='fil'){


            if(desde==null) desde = new Date()
            if(hasta==null) hasta = new Date()

            d = desde.format("yyyy-MM-dd").toString()
            h = hasta.format("yyyy-MM-dd").toString()
            offset = 0

        }else if(params.marca=='pag'){

          
            offset= (Integer) params.offset.toInteger()
            d= desde
            h = hasta
        }

        //BUSCAR EN EL SERVICIO IMP ------------------------
        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id
        cda.ConjuntoCda result = customSecureServiceClientCda.buscarCDAByPacienteAndRango(
            params.id,
            XMLGregorianCalendarConverter.getXMLCalendar(d,"yyyy-MM-dd"),
            XMLGregorianCalendarConverter.getXMLCalendar(h,"yyyy-MM-dd"),
            offset,
            idOrganizacion)

        //-------------------------------
        if(result){

            render(template: 'registroExterno', model: [idPaciente: params.id, externo: result.listCdaArr, total: result.total, llamar: 'busquedaExterna', desde:d,hasta:h] )
        }else{

            render(template: 'registroExterno', model: [externo: null])


        }


    }
    def busquedaAllExterna = {

        println "ID:::::"+params.id
        println "params.offsetoFFSET::::"+params.offset
        
        def offset

        if(params.marca=='fil'){
  
            offset = 0

        }else if(params.marca=='pag'){

        
            offset= (Integer) params.offset.toInteger()
        }

        //BUSCAR EN EL SERVICIO IMP ------------------------
        String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id

        cda.ConjuntoCda result = customSecureServiceClientCda.buscarCDAByPaciente(
            params.id,
            offset,
            idOrganizacion)

        //-------------------------------
        if(result){

            render(template: 'registroExterno', model: [idPaciente: params.id,externo: result.listCdaArr, total: result.total, llamar: 'busquedaAllExterna' ])
        }else{

            render(template: 'registroExterno', model: [externo: null])


        }


    }

    def busquedaInterna = {
        println "ID:::::"+params.id
        println "params.offsetoFFSET::::"+params.offset
        println "params.desde::::"+params.desde
        println "params.hasta::::"+params.hasta


        def persona = Person.get(params.id)
        def compos = [] //lista de composiciones (registros internos)

        def desde
        def hasta
        def offset
        def max

            
        if(params.marca=='fil'){
            desde = params.desde
            hasta = params.hasta
            offset =0
            max = 4

            if(desde==null) desde = new Date()
            if(hasta==null) hasta = new Date()
        
            
            
        }else if(params.marca=='pag'){

            offset = params.offset.toInteger()
            max = params.max.toInteger()

            desde = Date.parse("yyyy-MM-dd", params.desde)
            hasta = Date.parse("yyyy-MM-dd", params.hasta)



        }


        compos = hceService.getAllCompositionForPatient(persona, desde, hasta)
        if(compos){

            //if(max >= compos.size()){
            //    max = compos.size()
            //}
            def set = offset + max - 1
            if(set>=compos.size()){
                set = compos.size()-1
            }

            def rango = new IntRange(offset, set)
            def rangoCompos = compos.getAt(rango)
            def d = desde.format("yyyy-MM-dd").toString()
            def h = hasta.format("yyyy-MM-dd").toString()
            
            render(template: 'registroInterno', model: [idPaciente: params.id , compositions: rangoCompos, compositionsSize: compos.size(), compositionsMax: max,desde:d,hasta:h,llamar: 'busquedaInterna'])
                
        }else{
            render(template: 'registroInterno', model: [idPaciente: params.id , compositions: compos, compositionsSize: 0,compositionsMax: max ])
                
        }


        

       
        
    }
    
    def busquedaAllInterna = {
        println "ID:::::"+params.id
        println "params.offsetoFFSET::::"+params.offset
           


        def persona = Person.get(params.id)
        def compos = [] //lista de composiciones (registros internos)

          
        def offset
        def max


        if(params.marca=='fil'){
           
            offset =0
            max = 4

                
        }else if(params.marca=='pag'){

            offset = params.offset.toInteger()
            max = params.max.toInteger()
        }


        compos = hceService.getAllCompositionForPatient(persona, new Date(0), new Date())
        if(compos){

            //if(max >= compos.size()){
            //    max = compos.size()
            //}
            def set = offset + max - 1
            if(set>compos.size()){
                set = compos.size()-1
            }

            def rango = new IntRange(offset, set)
            def rangoCompos = compos.getAt(rango)
            render(template: 'registroInterno', model: [idPaciente: params.id, compositions: rangoCompos, compositionsSize: compos.size(), compositionsMax: max, llamar: 'busquedaAllInterna'])

        }else{
            render(template: 'registroInterno', model: [idPaciente: params.id, compositions: compos, compositionsSize: 0,compositionsMax: max ])

        }






    }

    def show ={

        //Mostrar detalle de un paciente
         def persona = Person.get(params.id)
        if (persona.ids.size() == 0) // Debe tener un id!
            {
                redirect( action : 'findPatient',
                    params : ['flash.message': 'El paciente seleccionado no tiene identificadores, debe tener por lo menos uno.'] )
                return
            }

        //Folder domain = Folder.findByPath( session.traumaContext.domainPath )
            String idOrganizacion = ApplicationHolder.application.config.imp.organizacion.id
            def agreImp
            def relaImp
            def conexionImp = SCH.servletContext.conexionImp


            //MANEJANDO EXCEPCION DE CONEXION AL IMP
            if(conexionImp){
            try{
            agreImp = customSecureServiceClientImp.existePaciente(params.id, idOrganizacion)

            if(agreImp){

                relaImp = customSecureServiceClientImp.existeRelacionPaciente(params.id, idOrganizacion)
            }else{
                relaImp = false

            }

            }catch(Exception e){

                //OCURRIO UNA EXCEPCION NO SE PUEDE CONECTAR AL IMP
                conexionImp = false
            }
            }

            def ids = persona.ids.toArray()

            println "ObjectID ": ids[0].root +"::"+ids[0].extension
            render( view:'show', model: [ userId: session.traumaContext.userId,persona: persona, root: ids[0].root, extension: ids[0].extension, conexionImp: conexionImp, agregadoImp: agreImp, relacionadoImp:relaImp])



    }
    def seleccionarPaciente = {
        
        // FIXME: esta hecho en base al id en la base, que pasa cuando la
        // seleccion se hace sobre un paciente en un IMP remoto y no esta en la base?
        
        // Guardo los resultados de consultar el IMP remoto en la base como cache.
        def persona = Person.get(params.id)
        
        
        // =====================================================================
        // 1) Si no hay un episodio seleccionado, muestro la patalla de show del
        // paciente que tiene un boton "crear episodio" para abrir un episodio
        // para ese paciente, es la apertura desde admision.
        
        // =====================================================================
        // 2) Si hay un episodio seleccionado, entonces admision o el medico esta
        // seleccionando un paciente para ese episodio. Es un paciente existente
        // o uno ingresado en el momento. Previo a asignar a esta persona al
        // episodio se debe verificar que no se tenga ya una persona seleccionada
        // (o simplemente pongo la que me digan y que ellos corrijan).
        // Cada correccion debe tener un log de quien lo hizo.
        // Vuelve a la pantalla principal del episodio seleccionado (show).

        if (!session.traumaContext?.episodioId) // caso 1)
        {
            println "No hay epidosio seleccionado"

            

            redirect(action:'show', params: params)




        }
        else // caso 2)
        {
            println "Hay un episodio seleccionado"
            
            // Pide el episodio a la base para agregarle la participation del paciente
            def composition = Composition.get( session.traumaContext.episodioId )
            
            // PRE: el episodio no deberia tener un paciente asignado.
            // FIXME: esta tira una except si hay mas de un pac con el mismo id, hacer catch
            if ( hceService.getPatientFromComposition( composition ) )
            {
                flash.message = 'trauma.show.feedback.patientAlreadySelectedForThisEpisode'
                redirect( controller:'records', action:'show',
                    params: [id: session.traumaContext.episodioId, 'flash.message': 'trauma.show.feedback.patientAlreadySelectedForThisEpisode'] )
                return
            }
            
            //println "IDS: " + persona.ids
            
            // Crea la participacion del paciente para la compostion del episodio
            if (persona.ids.size() == 0) // Debe tener un id!
            {
                redirect( controller:'records', action:'show',
                    params: [id: session.traumaContext.episodioId, 'flash.message': 'El paciente seleccionado no tiene identificadores, debe tener por lo menos uno.'] )
                return
            }
            
            // Crea un PartyRef desde la composition hacia la persona usando una copia del id de la persona,
            // esto crea otra instancia de ObjectID con el valor igual al id de la persona.
            def ids = persona.ids.toArray()
            def partySelf = hceService.createPatientPartysSelf( ids[0].root, ids[0].extension )
            def participation = hceService.createParticipationToPerformer( partySelf )

            // TODO: agregar un participation a la composition deberia hacerse tambien en HceService.
            composition.context.addToParticipations( participation )
            
            // Si no le pongo flush:true parece que demora un poco mas en guardar el partyself y
            // vuelve a la pagina rapido y muestra que el episodio no tiene paciente.
            if (!composition.save(flush:true))
            {
                println "ERROR compo: " + composition.errors
            }
            
            
            // Ejecuta eventos cuando el paciente seleccionado con exito.
            EventManager.getInstance().handle("post_seleccionar_paciente_ok", [composition:composition, persona:persona])
            
            
            //println "ERROR participation: " + participation.errors
            //println "ERROR partySelf: " + partySelf.errors
            
            // FIXME: cuando selecciona un paciente y vuelve al show del episodio,
            //        no se ve el paciente, si se hace reload de la pagina, se ve el paciente...
            //        puede ser un tema de aca (hay que hacer flush de la session o algo),
            //        o es un tema de carga lazy en el records.show para las participations del
            //        episodio.
            
            redirect( controller:'records', action:'show',
                params: [id: session.traumaContext.episodioId] )
        }
        
        
        
        // Mientras rederea aprovecho para lanzar el evento
        EventManager.getInstance().handle("paciente_seleccionado",
            [
                patient: persona,
                episodeId: session.traumaContext?.episodioId // puede ser null
            ]
        )
        
        //render('Selecciona paciente: ' + persona)
    }
    
    /**
     * Agrega un nuevo paciente cuando el paciente a atender no esta en el sistema.
     */
    def agregarPaciente = {
        
        //println params
        
        // FIXME: si viene el id, verificar que no hay otro paciente con ese id, si lo hay, no dejar dar de alta, decirle que ya existe.
        
        // FIXME:  <%-- Solo se puede agregar un nuevo paciente si el repositorio es local --%>
        //  <g:if test="${ApplicationHolder.application.config.hce.patient_administration.serviceType.local}">
        
        if (params.doit)
        {
            /*
             * Account.withTransaction { status ->
            def source = Account.get(params.from)
            def dest = Account.get(params.to)
            def amount = params.amount.toInteger()
            if(source.active)
            {
            source.balance -= amount
            if(dest.active) { dest.amount += amount }
            else { status.setRollbackOnly() }
            }
            }
             */
            
            // Veo si viene extension y root o si root es autogenerado
            def id = null
            if (params.root == TipoIdentificador.AUTOGENERADO)
            {
                // Verificar si este ID existe, para no repetir
                def extension = RandomGenerator.generateDigitString(8)
                id = UIDBasedID.create(params.root, extension)
                
                // Se deberia hacer con doWhile para no repetir el codigo pero groovy no tiene doWhile
                while ( UIDBasedID.findByValue(id.value) )
                {
                    extension = RandomGenerator.generateDigitString(8)
                    id = UIDBasedID.create(params.root, extension)
                }
            }
            else
            {
                if (params.extension && params.root)
                {
                    id = UIDBasedID.create(params.root, params.extension) // TODO: if !hasExtension => error
                    
                    // FIXME: verificar que no hay otro paciente con el mismo id
                    println "===================================================="
                    println "Busco por id para ver si existe: " + id.value
                    
					def existPerson = Person.withCriteria{
						ids{
							eq("value", id.value)
						}
						
						identities{
							eq("purpose", "PersonNamePatient")
						}
					
					}
					
					//def existId = UIDBasedID.findByValue(id.value)
                    if (existPerson)
                    {
                        println "Ya existe!"
                        flash.message = "Ya existe la persona con id: " + id.value + ", verifique el id ingresado o vuelva a buscar la persona"
                        def tiposIds = TipoIdentificador.list()
                        return [tiposIds: tiposIds]
                    }
                    else
                    println "No existe!"
                }
                
                
                else
                {
                    // Vuelve a la pagina
                    flash.message = "identificador obligatorio, si no lo tiene seleccione 'Autogenerado' en el tipo de identificador"
                    def tiposIds = TipoIdentificador.list()
                    return [tiposIds: tiposIds]
                }
                
            }
            

            def person = new Person() // sexo, fechaNac (no mas)
            
            //println("fechaNacimiento:->"+params.fechaNacimiento)
            def dia = params.fechaNacimiento.split('-')[0]
            def mes = params.fechaNacimiento.split('-')[1]
            def anio = params.fechaNacimiento.split('-')[2]
            String fecha = anio+'-'+mes+'-'+dia
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd")
            java.util.Date d =  sdf.parse(fecha.toString())
            person.fechaNacimiento =  d
            
            person.sexo = params.sexo
            




            person.addToIds( id )
            
            //def name = new PersonName(params)
            //person.addToIdentities( name )
            
            def datos = new PersonNamePatient(params)
            
            //foto del paciente

            if(params.foto){

                def x1 = params.x1 as Integer
                def y1 = params.y1 as Integer
                def x2 = params.x2 as Integer
                def y2 = params.y2 as Integer
                File tempPicture = new File(grailsApplication.config.images.location.toString() + File.separatorChar + params.foto)
                BufferedImage image = ImageIO.read(tempPicture)
                BufferedImage croppedImage = image.getSubimage(x1, y1, x2 - x1, y2 - y1)
                File profilePicture = new File(grailsApplication.config.images.location.toString() + File.separatorChar +"prueba.jpg")
                ImageIO.write(croppedImage, "jpg", profilePicture);
                FileUtils.deleteQuietly(tempPicture)


                //def f = request.getFile('foto')
                def okcontents = ['image/png' , 'image/jpeg' , 'image/gif']
                if(okcontents.contains(new MimetypesFileTypeMap().getContentType(profilePicture).toString())){
                    datos.foto = profilePicture.getBytes()
                    datos.tipofoto = new MimetypesFileTypeMap().getContentType(profilePicture).toString()
                }


            }
            /*---------------------------------*/
            person.addToIdentities(datos)
            
            
            if(person.save()){
                def role = new Role(timeValidityFrom: new Date(), type: "paciente", performer: person)
                if(role.save()){
                    redirect(action:'seleccionarPaciente', id:person.id)
                }
                else{
                    println role.errors
                }
            }else{
                println person.errors
            }

            redirect(action:'admisionPaciente')
        
       }
        
        // creacion de un nuevo paciente
        def tiposIds = TipoIdentificador.list()
        def etniasIds = Etnia.list()
        def profesionIds = Profesion.list()
        def conyugalIds = Conyugal.list()
        def nivelEducIds = Niveleducativo.list()
        def ocupacionIds = Ocupacion.list()
        def paisesIds = Lugar.findAllByTipolugarLike("Pais")
        def entidadesIds = Lugar.findAllByTipolugarLike("Estado")
        
        return [tiposIds: tiposIds,
            etniasIds : etniasIds,
            profesionIds : profesionIds,
            paisesIds : paisesIds,
            conyugalIds : conyugalIds,
            nivelEducIds : nivelEducIds,
            ocupacionIds : ocupacionIds,
            entidadesIds : entidadesIds]
    }


    def fotoPrevia = {
       def foto
       def tipoFoto
            def okcontents = ['image/png' , 'image/jpeg' , 'image/gif']
            def f = request.getFile('fotoPrevia')
	    if(!f.empty) {
                if(okcontents.contains(f.getContentType())){
	      //flash.message = 'Your file has been uploaded'
		  new File( grailsApplication.config.images.location.toString() ).mkdirs()
		  f.transferTo( new File( grailsApplication.config.images.location.toString() + File.separatorChar + f.getOriginalFilename() ) )
                
                  render f.getOriginalFilename()
                  return

                }
            }
            render ""
    }


    def mostrarFotoPrevia = {

        if(params.id){
            
            def f = new File( grailsApplication.config.images.location.toString() + File.separatorChar + params.id )
            if(f.exists()){
               response.setContentType(new MimetypesFileTypeMap().getContentType(f).toString())
               response.setContentLength(f.getBytes().size())
               OutputStream out = response.getOutputStream()
               out.write(f.getBytes())
               out.close()
            }
        }
    }
    
    /**
     * Editar un paciente con datos incompletos.
     * in: id
     */
    def edit = {
        
        println params
        
        // Si no viene el id, vuelvo a un punto seguro.
        if (!params.id)
        {
            if (session.traumaContext.episodioId)
            redirect(controller:'records', action:'show', id:session.traumaContext.episodioId)
            else
            redirect(controller:'records', action:'list')
            return
        }
        
        def patient = Person.get( params.id )
        def pn = patient.identities.find{ it.purpose == 'PersonName' }
        def tiposIds = TipoIdentificador.list()

        if (params.doit)
        {
            patient.setProperties( params )
            //pn.setProperties( params )
            
            println "PN:: " + pn
            
            // borra el viejo
            patient.removeFromIdentities(pn)
            pn.delete()

            // crea el nuevo
            pn = new PersonName(params)
            patient.addToIdentities( pn )


            /*
            def person = new Person( params ) // sexo, fechaNac (no mas)
            
            def bd = DateConverter.dateFromParams( params, 'fechaNacimiento_' )
            person.setFechaNacimiento( bd )
            
            person.addToIds( id )
             */
            
            if (!patient.save(flush:true))
            {
                println patient.errors
                return [patient:patient, pn:pn, tiposIds:tiposIds]
            }
            
            if (session.traumaContext.episodioId)
            redirect(controller:'records', action:'show', id:session.traumaContext.episodioId)
            else
            redirect(controller:'records', action:'list')
            return
        }
        
        // muestra pagina de edit
        return [patient:patient, pn:pn, tiposIds:tiposIds]
    }






    /*
     *@author Angel Rodriguez Leon
     *
     *Busca en la BD los valores de nombres de usuario identificados por el id
	 *y los trae para la creacion de un paciente a partir de estos datos.
     * */      
	 
	 def messageSource
    
	
		  
	def ajaxGetNombres = {	
		def m0 = messageSource.getMessage( 'persona.identificador', null, null)		
	
		def m1 = messageSource.getMessage( 'persona.primerApellido', null, null)
		
		def m2 = messageSource.getMessage( 'persona.segundoApellido', null, null)
		
		def m3 = messageSource.getMessage( 'persona.primerNombre', null, null)
		
		def m4 = messageSource.getMessage( 'persona.segundoNombre', null, null)	
	
		def m5 = messageSource.getMessage( 'persona.fechaNacimiento', null, null)
		
		def m6 = messageSource.getMessage( 'persona.sexo', null, null)
		
		def m7 = messageSource.getMessage( 'persona.foto', null, null)
		
		def codmsj = "1"
		println "estoy en la funcion ajaxGetNombres!!! id: "+ params.id
		
		def array
		def a = true

			array = params.id.split	("-")
			
			def id = null
			
			
			def candidatosUsuarios
			def existPatient
			try{
				id = new UIDBasedID(value:array[0]+'::'+array[1])
				
				candidatosUsuarios = demographicService.findUserById(id)
				existPatient = demographicService.findPatientById(id)
				
				if(existPatient){
						
						codmsj="2"
						render  "</select>"+"<label for='primerApellido'>"+m1+"</label>"+
								"<input type='text' name='primerApellido' id='primerApellido' value=''>"+

								"<label for='segundoApellido'>"+m2+"</label>"+
								"<input type='text' name='segundoApellido' id='segundoApellido' value=''>"+

								"<label for='primerNombre'>"+m3+"</label>"+
								"<input type='text' name='primerNombre' id='primerNombre' value=''>"+

								"<label for='segundoNombre'>"+m4+"</label>"+
								"<input type='text' name='segundoNombre' id='segundoNombre' value=''>"+
								"<label for='fechaNacimiento'>"+m5+"</label>"+

								"<input name='fechaNacimiento' type='text' id='fechaNacimiento' value=''/>  <br /><br />"+
								"<label for='sexo'>"+m6+"</label>"+
								"<select name='sexo' class='selectci' id='sexo' >"+
								"<option value=''>Seleccione</option>"+
								"<option value='Masculino' >Masculino</option>"+
								"<option value='Femenino' >Femenino</option></select>"+
								"<label for='foto'>"+m7+"</label>"+
								"<input type='text' name='foto' id='foto' style='width: 300px;'/></div>"+
								"<script>jQuery(document).ready(function(){"+
								"jQuery('.Date').datepicker({dateFormat: 'dd-mm-yy',changeYear: true, buttonText: 'Calendario', buttonImage: '/sos/images/datepicker.gif', maxDate: new Date(), yearRange: '1900:2100', constrainInput: true, showButtonPanel: true, showOn: 'button' });"+
								"jQuery('#fechaNacimiento').attr('readonly',true);jQuery('#foto').attr('readonly',true);jQuery('#foto').click(function (){jQuery('#inputFotoPrevia').click();});"+	
								"});</script>"
							
							
				}else{
					if(candidatosUsuarios){

						codmsj="1"
						def datos = [uno:"",dos:"",tres:"",cuatro:""]
						println "candidatos: "+candidatosUsuarios.identities.primerNombre
						if(candidatosUsuarios.identities[0].primerApellido[0]!=null)
							datos.put("uno", candidatosUsuarios.identities[0].primerApellido[0])
						
						if(candidatosUsuarios.identities[0].segundoApellido[0]!= null)
							datos.put("dos", candidatosUsuarios.identities[0].segundoApellido[0])
						
						if(candidatosUsuarios.identities[0].primerNombre[0]!=null)
							datos.put("tres", candidatosUsuarios.identities[0].primerNombre[0])

						if(candidatosUsuarios.identities[0].segundoNombre[0]!=null)
							datos.put("cuatro", candidatosUsuarios.identities[0].segundoNombre[0])

						if(candidatosUsuarios.fechaNacimiento[0]!=null){
							
							
							def formateador = new SimpleDateFormat("dd-MM-yyyy ")
							println "fechaa!!!! : " +formateador.format(candidatosUsuarios.fechaNacimiento[0])
							datos.put("cinco", formateador.format(candidatosUsuarios.fechaNacimiento[0]))
						}
						if(candidatosUsuarios.sexo[0]!=null)
							datos.put("seis", candidatosUsuarios.sexo[0])
						
						def selectFemenino = ""
						def selectMasculino = ""
						if(datos.get("seis")=="masculino"){
							selectMasculino = "selected"
						}else{
							selectFemenino = "selected"
						}
							
						render  "</select>"+"<label for='primerApellido'>"+m1+"</label>"+
								"<input type='text' name='primerApellido' id='primerApellido' value="+datos.get("uno")+">"+

								"<label for='segundoApellido'>"+m2+"</label>"+
								"<input type='text' name='segundoApellido' id='segundoApellido' value="+datos.get("dos")+">"+

								"<label for='primerNombre'>"+m3+"</label>"+
								"<input type='text' name='primerNombre' id='primerNombre' value="+datos.get("tres")+">"+

								"<label for='segundoNombre'>"+m4+"</label>"+
								"<input type='text' name='segundoNombre' id='segundoNombre' value="+datos.get("cuatro")+">"+
								"<label for='fechaNacimiento'>"+m5+"</label>"+
								"<input name='fechaNacimiento' type='text' class='Date' id='fechaNacimiento'  value="+datos.get("cinco")+"/>  <br /><br />"+
								"<label for='sexo'>"+m6+"</label>"+
								"<select name='sexo' class='selectci' id='sexo' >"+
								"<option value=''>Seleccione</option>"+
								"<option value='Masculino' "+selectMasculino+">Masculino</option>"+
								"<option value='Femenino' "+selectFemenino+">Femenino</option></select>"+
								"<label for='foto'>"+m7+"</label>"+
								"<input type='text' name='foto' id='foto' style='width: 300px;'/></div>"+
								"<script>jQuery(document).ready(function(){"+
								
								"jQuery('#fechaNacimiento').attr('readonly',true);jQuery('#foto').attr('readonly',true);jQuery('#foto').click(function (){jQuery('#inputFotoPrevia').click();});"+	
								"});</script>"
							
					}else{
							
						
					
						render	"</select>"+"<label for='primerApellido'>"+m1+"</label>"+
								"<input type='text' name='primerApellido' id='primerApellido' value=''>"+

								"<label for='segundoApellido'>"+m2+"</label>"+
								"<input type='text' name='segundoApellido' id='segundoApellido' value=''>"+

								"<label for='primerNombre'>"+m3+"</label>"+
								"<input type='text' name='primerNombre' id='primerNombre' value=''>"+

								"<label for='segundoNombre'>"+m4+"</label>"+
								"<input type='text' name='segundoNombre' id='segundoNombre' value=''>"+
								"<label for='fechaNacimiento'>"+m5+"</label>"+

								"<input name='fechaNacimiento' type='text' class='Date' id='fechaNacimiento' value=''/>  <br /><br />"+
								"<label for='sexo'>"+m6+"</label>"+
								"<select name='sexo' class='selectci' id='sexo' >"+
								"<option value=''>Seleccione</option>"+
								"<option value='Masculino' >Masculino</option>"+
								"<option value='Femenino' >Femenino</option></select>"+
								"<label for='foto'>"+m7+"</label>"+
								"<input type='text' name='foto' id='foto' style='width: 300px;'/></div>"+
								"<script>jQuery(document).ready(function(){"+
								"jQuery('.Date').datepicker({dateFormat: 'dd-mm-yy',changeYear: true, buttonText: 'Calendario', buttonImage: '/sos/images/datepicker.gif', maxDate: new Date(), yearRange: '1900:2100', constrainInput: true, showButtonPanel: true, showOn: 'button' });"+
								"jQuery('#fechaNacimiento').attr('readonly',true);jQuery('#foto').attr('readonly',true);jQuery('#foto').click(function (){jQuery('#inputFotoPrevia').click();});"+	
								"});</script>"
							
					}
				}
				
				println("funcion ajax get nombres finalizada con exito")
				
			}catch(ArrayIndexOutOfBoundsException e){
				println "estoy en el catch"
				
				render 	"</select>"+"<label for='primerApellido'>"+m1+"</label>"+
						"<input type='text' name='primerApellido' value=''>"+

						"<label for='segundoApellido'>"+m2+"</label>"+
						"<input type='text' name='segundoApellido' value=''>"+

						"<label for='primerNombre'>"+m3+"</label>"+
						"<input type='text' name='primerNombre' value=''>"+

						"<label for='segundoNombre'>"+m4+"</label>"+
						"<input type='text' name='segundoNombre' value=''>"+

						"<label for='fechaNacimiento'>"+m5+"</label>"+

						"<input name='fechaNacimiento' type='text' class='Date' id='fechaNacimiento' value=''/>  <br /><br />"+
						"<label for='sexo'>"+m6+"</label>"+
						"<select name='sexo' class='selectci' id='sexo' >"+
						"<option value=''>Seleccione</option>"+
						"<option value='Masculino' >Masculino</option>"+
						"<option value='Femenino' >Femenino</option></select>"+
						"<label for='foto'>"+m7+"</label>"+
						"<input type='text' name='foto' id='foto' style='width: 300px;'/></div>"+
						"<script>jQuery(document).ready(function(){"+
						"jQuery('.Date').datepicker({dateFormat: 'dd-mm-yy',changeYear: true, buttonText: 'Calendario', buttonImage: '/sos/images/datepicker.gif', maxDate: new Date(), yearRange: '1900:2100', constrainInput: true, showButtonPanel: true, showOn: 'button' });"+
						"jQuery('#fechaNacimiento').attr('readonly',true);jQuery('#foto').attr('readonly',true);jQuery('#foto').click(function (){jQuery('#inputFotoPrevia').click();});"+	
						"});</script>"
			}
	}
	
	
	
    /*
     *@author Juan Carlos Escalante
     *
     *returna los estados asociados a un país en particulo (inicialmente solo Venezuela)
     *@param id corresponde a la entidad de padre para la busqueda recursiva de sus hijos */
    def ajaxGetEstados = {
        
        if(params.id.toLong() == 1){
            def list = Lugar.createCriteria().list{
                padre{
                    eq('id',params.id.toLong())
                }
            }
            render list.collect{ """<option value="${it.id}">${it.nombre}</option>""" }
        }
        else{
            def list = Lugar.findByNombreLike("Venezuela")
            render {"<input type='hidden' id='generarestados' value=0 />"}
            //render list.collect{ "<option value=-1>- Aplica Sólo a Venezuela</option>"}
        }
    }
    
    /*
     *@author Juan Carlos Escalante
     *
     *returna los municpios y parroquias para entidades seleccionadas (inicialmente solo Venezuela)
     *@param id corresponde a la entidad de padre para la busqueda recursiva de sus hijos */
    def ajaxGetMunicipios = {
        
        if(params.id.toLong() >= 1){
            def list = Lugar.createCriteria().list{
                padre{
                    eq('id',params.id.toLong())
                }
            }
            render list.collect{ """<option value="${it.id}">${it.nombre}</option>""" }
        }
        else{
            def list = Lugar.findByNombreLike("Venezuela")
            //render list.collect{ "<option value=-1>- Aplica Sólo a Venezuela</option>" }
        }
    }
    /*
     *@author Juan Carlos Escalante
     *
     *suite de reportes estadísticos sobre episodios clínicos*/
    // fuera de uso. comportamiento migrado a ReportesController
    /*
    def reportIndex = {
        def compos = []
        def paciente = []
        def sexo = []
        def fullDireccion = []
        def ocupacion = []
        def edad = []
        Folder domain = Folder.findByPath( session.traumaContext.domainPath )
        
        compos = Composition.withCriteria{
            eq('rmParentId',domain.id)
        }
        
   
       
        println("episodio clinico")
        println(session.traumaContext?.episodioId)
        
        println("numero de composiciones")
        println(compos.size())
        
        def composition = null
        def j = 0
        while (compos[j]!=null){
            composition = compos[j]
            session.traumaContext.episodioId = composition.id
            def composi = Composition.get(composition.id)
            // BUSCANDO EL DIAGNOSTICO DESDE LA COMPOSITION
            println("al diagnostico")
            println(composi.content[0].name.value) // me indica que es un diganostico de trauma
            println(composi.content[0])
            
            def iter = composi.content.iterator()
            def item = iter.next()
            //DIAGNOSTICO-diagnosticos nombre del template
            def elemento = hceService.getCompositionContentItemForTemplate(composi, "DIAGNOSTICO-diagnosticos")

            if(elemento!=null){
                def rmNode =  Locatable.findByName(elemento.name) //enlace al nodo de la composition en el modelo de referencia   
                def rmNodeData =  rmNode.data
                def rmNodeDataEvents = rmNodeData.events
                def rmNodeDataEventsData = rmNodeDataEvents.data
                def rmNodeDataEventsDataItems = rmNodeDataEventsData.items
                
                def element = rmNodeDataEvents[0].data.items
                println("element[0]: ->"+element[0])
                
                def codigo = Cie10Trauma.findByCodigo(element[0].value.definingCode.codeString)
                println("codigo.id: ->"+codigo.nombre)
            }
            /////////////////////////////////////
            def patient = hceService.getPatientFromComposition(composi)
            if(patient){
                def datos = patient.identities.find{it.purpose == 'PersonNamePatient'}
                if(datos!=null){
                    def direccion = demographicService.findFullAddress((int)datos.direccion.id)
                    fullDireccion << "Ciudad "+ datos.ciudad + ", Urb/Sector " + datos.urbasector + ", Av/Calle " + datos.avenidacalle + ", Casa/Res " + datos.casaedif + ", "+direccion
                    sexo << patient.sexo
                    paciente << patient
                    ocupacion << demographicService.getOcupacion((int)datos.ocupacion.id)

                    if(patient.fechaNacimiento){
                        def myFormatter = new SimpleDateFormat("yyyy")
                        //println(myFormatter.format(patient.fechaNacimiento))
                        def hoy = new Date()
                        //println(Integer.parseInt(myFormatter.format(hoy)) - Integer.parseInt(myFormatter.format(patient.fechaNacimiento)))
                        edad << Integer.parseInt(myFormatter.format(hoy)) - Integer.parseInt(myFormatter.format(patient.fechaNacimiento))
                    }
                }
                
            }
            j++
        }
        
        def output = demographicService.xmlEPI10(ocupacion as String[], fullDireccion as String[])
        return [patient: paciente,
                dircompleta : fullDireccion,
                ocupacion : ocupacion,
                sexo : sexo,
                edad : edad]
        
    }
    */
    
    
    def fotopaciente = {
       def paciente = Person.get(params.persona)
       def datos = paciente.identities.find{ it.purpose == 'PersonNamePatient'}
       if(!datos || !datos.foto || !datos.tipofoto){
           response.sendError(404)
           return
       }
       response.setContentType(datos.tipofoto)
       response.setContentLength(datos.foto.size())
       OutputStream out = response.getOutputStream()
       out.write(datos.foto)
       out.close()
    }
    
}
