package sos

import demographic.party.*
import demographic.identity.PersonName
import demographic.role.*
import hce.core.support.identification.UIDBasedID

import hce.HceService
import tablasMaestras.TipoIdentificador
import hce.core.composition.*
import org.codehaus.groovy.grails.commons.ApplicationHolder
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
    /*
     *@author Juan Carlos Escalante
     *
     *suite de reportes estadísticos sobre episodios clínicos*/
class ReportesController {
    
    def hceService
    def demographicService
    
    def index = { }
    
    def epi10emergencia = {
        def compos = []
        def paciente = []
        //def sexo = []
        def sexo
        //def fullDireccion = [] //se cambia por variable unica a reescribir en casa pasada del ciclo
        def fullDireccion
        //def ocupacion = [] // situacion similar al fullDireccion
        def ocupacion
        //def edad = []
        def edad
        def composition = null
        
        def inicio = params.desde
        def fin = params.hasta
        def j = 0 // variable unica para loop sobre las compositions
        def nombreDoc = "epi10emergencia"
        
       println("inicio:->"+inicio)
       println("fin:->"+fin)
        
       Folder domain = Folder.findByPath( session.traumaContext.domainPath )
       compos = hceService.getAllCompositionForDate(inicio, fin)
       
       def archivo = demographicService.createXML(nombreDoc) // 
        
       if(compos !=null){
        
            while(compos[j]!=null){
                composition = compos[j]
                session.traumaContext.episodioId = composition.id
                def composi = Composition.get(composition.id)
                
                // paciente de cada composition
                def patient = hceService.getPatientFromComposition(composi)
                if(patient){
                    def datos = patient.identities.find{it.purpose == 'PersonNamePatient'}
                    if(datos!=null){
                        def direccion = demographicService.findFullAddress((int)datos.direccion.id)
                        //fullDireccion << "Ciudad "+ datos.ciudad + ", Urb/Sector " + datos.urbasector + ", Av/Calle " + datos.avenidacalle + ", Casa/Res " + datos.casaedif + ", "+direccion
                        fullDireccion = "Ciudad "+ datos.ciudad + ", Urb/Sector " + datos.urbasector + ", Av/Calle " + datos.avenidacalle + ", Casa/Res " + datos.casaedif + ", "+direccion
                        //sexo << patient.sexo
                        sexo = patient.sexo
                        paciente << patient
                        //ocupacion << demographicService.getOcupacion((int)datos.ocupacion.id)
                        ocupacion = demographicService.getOcupacion((int)datos.ocupacion.id)

                        if(patient.fechaNacimiento){
                            def myFormatter = new SimpleDateFormat("yyyy")
                            //println(myFormatter.format(patient.fechaNacimiento))
                            def hoy = new Date()
                            //edad << Integer.parseInt(myFormatter.format(hoy)) - Integer.parseInt(myFormatter.format(patient.fechaNacimiento))
                            edad = Integer.parseInt(myFormatter.format(hoy)) - Integer.parseInt(myFormatter.format(patient.fechaNacimiento))
                        }
                        
                        println("nombre paciente:->"+patient.identities.primerNombre[0])
                        // diagnóstico asosiciado al patient en la composition
                        def elemento = hceService.getCompositionContentItemForTemplate(composi, "DIAGNOSTICO-diagnosticos")
                        if(elemento!=null){
                            def rmNode =  Locatable.findByName(elemento.name) //enlace al nodo de la composition en el modelo de referencia   
                            def rmNodeData =  rmNode.data
                            def rmNodeDataEvents = rmNodeData.events
                            def rmNodeDataEventsData = rmNodeDataEvents.data
                            //def rmNodeDataEventsDataItems = rmNodeDataEventsData.items

                            def element = rmNodeDataEvents[0].data.items
                            println("------------------paciente------------------")
                                println("fecha: ->"+composi.context.startTime.value)
                                println("cedula: ->"+patient.ids.value[0].extension)
                                println("Nombre y Apellido: ->"+patient.identities.primerNombre[0]+" "+patient.identities.segundoNombre[0]+" "+patient.identities.primerApellido[0])
                                println("Direccion de Residencia: ->"+fullDireccion)
                                println("Ocupacion: ->"+ocupacion)
                                println("Edad: ->"+edad)
                                println("Sexo: ->"+sexo)
                                println("Diagnósticos: ->")
                                    def k=0 // variable de ciclo, usada en caso de que la composition tenga varios diagnósticos
                                    def codigos = []
                                    while(element[k]!=null){
                                        def codigo = Cie10Trauma.findByCodigo(element[k].value.definingCode.codeString)
                                        //println("Nombre Diagnostico: ->"+codigo.nombre)
                                        println("Dianostico "+(k+1)+" "+codigo.nombre)
                                        codigos << codigo.nombre
                                    k++
                                    }
                            println("--------------------------------------------")
                            def formatofecha = composi.context.startTime.value
                            def agregarNodo = demographicService.crearXmlEpi10(
                                                                                nombreDoc, 
                                                                                composi.context.startTime.toDate().format("dd/MM/yyyy"),
                                                                                //composi.context.startTime.value,
                                                                                patient.ids.value[0].extension,
                                                                                patient.identities.primerNombre[0]+" "+patient.identities.segundoNombre[0]+" "+patient.identities.primerApellido[0],
                                                                                fullDireccion,
                                                                                ocupacion,
                                                                                Integer.toString(edad),
                                                                                sexo,
                                                                                codigos as String[]
                                                                            )
                        }
                    }
                }
            j++
            }
        
        }
    }
    
    def epi10general = {
        def compos = []
        def paciente = []
        def sexo 
        def fullDireccion
        def edad
        def composition = null
        def fechaNace
        
        def inicio = params.desde
        def fin = params.hasta
        def j = 0 //loop
        def nombreDoc = "epi10general"
        
        Folder domain = Folder.findByPath( session.traumaContext.domainPath )
        compos = hceService.getAllCompositionForDate(inicio, fin)
        def archivo = demographicService.createXML(nombreDoc)
        
        if(compos != null){
            while(compos[j]!=null){
                composition = compos[j]
                session.traumaContext.episodioId = composition.id
                def composi = Composition.get(composition.id)
                def patient = hceService.getPatientFromComposition(composi)
                if(patient){
                    def datos = patient.identities.find{it.purpose == 'PersonNamePatient'}
                    if(datos != null){
                        def direccion = demographicService.findFullAddress((int)datos.direccion.id)
                        fullDireccion = "Ciudad "+ datos.ciudad + ", Urb/Sector " + datos.urbasector + ", Av/Calle " + datos.avenidacalle + ", Casa/Res " + datos.casaedif + ", "+direccion
                        sexo = patient.sexo
                        paciente << patient
                        
                        if(patient.fechaNacimiento){
                            fechaNace = patient.fechaNacimiento
                            def myFormatter = new SimpleDateFormat("yyyy")
                            //println(myFormatter.format(patient.fechaNacimiento))
                            def hoy = new Date()
                            //edad << Integer.parseInt(myFormatter.format(hoy)) - Integer.parseInt(myFormatter.format(patient.fechaNacimiento))
                            edad = Integer.parseInt(myFormatter.format(hoy)) - Integer.parseInt(myFormatter.format(patient.fechaNacimiento))
                        }
                        
                        def elemento = hceService.getCompositionContentItemForTemplate(composi, "DIAGNOSTICO-diagnosticos")
                        if(elemento != null){
                            def rmNode =  Locatable.findByName(elemento.name) //enlace al nodo de la composition en el modelo de referencia   
                            def rmNodeData =  rmNode.data
                            def rmNodeDataEvents = rmNodeData.events
                            
                            def element = rmNodeDataEvents[0].data.items
                            def k=0 // variable de ciclo, usada en caso de que la composition tenga varios diagnósticos
                            def codigos = []
                            while(element[k]!=null){
                                def codigo = Cie10Trauma.findByCodigo(element[k].value.definingCode.codeString)
                                //println("Nombre Diagnostico: ->"+codigo.nombre)
                                println("Dianostico "+(k+1)+" "+codigo.nombre)
                                codigos << codigo.nombre
                            k++
                            }
                        def agregarNodoXml =  demographicService.crearXmlEPI10Gen(nombreDoc,
                                                                                  patient.ids.value[0].extension,
                                                                                  patient.identities.primerNombre[0]+" "+patient.identities.segundoNombre[0]+" "+patient.identities.primerApellido[0],
                                                                                  patient.fechaNacimiento.format("dd/MM/yyyy"), 
                                                                                  fullDireccion,
                                                                                  sexo,
                                                                                  Integer.toString(edad), 
                                                                                  codigos as String[]
                                                                                  )   
                        }
                    }
                }
            j++    
            }
        }
        
        redirect(controller:'reportes', action:'index')
        
    }
    
    
    
}
