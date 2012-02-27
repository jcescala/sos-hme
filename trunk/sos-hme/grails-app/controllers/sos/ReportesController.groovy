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
import org.codehaus.groovy.grails.commons.ApplicationHolder
import javax.servlet.*
import java.net.*


/*reportes*/
import templates.TemplateManager
import tablasMaestras.Cie10Trauma
import java.util.HashMap
import net.sf.jasperreports.engine.data.JRXmlDataSource
import net.sf.jasperreports.engine.export.JRPdfExporter
import net.sf.jasperreports.engine.*



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
        
        }else{
            redirect(controller:'reportes', action:'index')
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
        def generarReporte = false
        def inicio
        def fin
        
        if(params.desde && params.hasta){
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy")
            java.util.Date d =  sdf.parse(params.desde.toString())
            inicio =  d
            java.util.Date h =  sdf.parse(params.hasta.toString())
            fin =  h
        }
        println("inicio:->"+inicio)
        println("fin:->"+fin)
        
        
        def j = 0 //loop
        def nombreDoc = "epi10general"
        def etnia
        def niveleducativo
        def generado = false
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
                        etnia = datos.etnia.id
                        niveleducativo = datos.niveleducativo
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
                            generarReporte = true
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
                                                                                  Long.toString(etnia),
                                                                                  Integer.toString(niveleducativo),
                                                                                  Integer.toString(edad), 
                                                                                  codigos as String[]
                                                                                  ) 
                        }
                    }
                }
            j++    
            }
                         
                 if(generarReporte==true){
                     def FileName = []
                     FileName << ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/reportes/epi10consultageneralNuevoFormato.jasper")
                     FileName << ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/reportes/epi10consultaGeneralP2.jasper")
                     //def outFile = "C:/Users/juan/Desktop/sosDeve/sos-hme/web-app/data/reports/reportes/epi10consultageneral.pdf"
                     def outFile = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/documentos/epi10consultageneral.pdf")
                     //def xmlFile = "C:/Users/juan/Desktop/sosDeve/sos-hme/web-app/data/reports/source/epi10general.xml"
                     def xmlFile = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/epi10general.xml")
                     def record = "/pacientes/paciente"
                     generado = reportsOutput(FileName as String[], outFile, xmlFile, record)
                     if(generado){
                        redirect(controller:'reportes', action:'index', params:[creado10general:true,tipo:outFile])
                        }else{
                            redirect(controller:'reportes', action:'index', params:[creado10general:""])
                        }
                     }  
            }else{
                redirect(controller:'reportes', action:'index', params:[creado10general:""])
            }
        
        
        
    }
    
    def epi13morbilidad ={
        def compos = []
        def paciente = []
        def sexo 
        def fullDireccion
        def edad
        def composition = null
        def fechaNace
        def desde
        def hasta
        def generarReporte = false
        
        
        if(params.desde && params.hasta){
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy")
            java.util.Date d =  sdf.parse(params.desde.toString())
            desde =  d
            java.util.Date h =  sdf.parse(params.hasta.toString())
            hasta =  h
        }
        
        def j = 0 //loop
        def nombreDoc = "epi13morbilidad"
        def generado = false
        Folder domain = Folder.findByPath( session.traumaContext.domainPath )
        compos = hceService.getAllCompositionForDate(desde, hasta)
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
                        def direccion = demographicService.tokenDireccion((int)datos.direccion.id)
                        StringTokenizer st = new StringTokenizer(direccion,"#",false)
                        def parroquia = st.nextToken()
                        def municipio = st.nextToken()
                        def estado = st.nextToken()    
                        fullDireccion = "Ciudad "+ datos.ciudad + ", Urb/Sector " + datos.urbasector + ", Av/Calle " + datos.avenidacalle + ", Casa/Res " + datos.casaedif
                        sexo = patient.sexo
                        //paciente << patient
                        if(patient.fechaNacimiento){
                            fechaNace = patient.fechaNacimiento
                            def myFormatter = new SimpleDateFormat("yyyy")
                            //println(myFormatter.format(patient.fechaNacimiento))
                            def hoy = new Date()
                            edad = Integer.parseInt(myFormatter.format(hoy)) - Integer.parseInt(myFormatter.format(patient.fechaNacimiento))
                            def elemento = hceService.getCompositionContentItemForTemplate(composi, "DIAGNOSTICO-diagnosticos")
                            
                            println("fechaInicioComposi:->"+composi.context.startTime.value)
                            
                            if(elemento != null){
                               def rmNode =  Locatable.findByName(elemento.name) //enlace al nodo de la composition en el modelo de referencia   
                                def rmNodeData =  rmNode.data
                                def rmNodeDataEvents = rmNodeData.events

                                def element = rmNodeDataEvents[0].data.items
                                def k=0 // variable de ciclo, usada en caso de que la composition tenga varios diagnósticos
                                def codigos = []
                                while(element[k]!=null){
                                    def codigo = Cie10Trauma.findByCodigo(element[k].value.definingCode.codeString)
                                    //println("Dianostico "+(k+1)+" "+codigo.nombre)
                                    println("codigo diagnostico:->"+codigo.subgrupo)
                                    def notificable = demographicService.verificaEnfermedadNotificable(codigo.subgrupo,codigo.codigo)
                                    println("notificable:->"+notificable)
                                    
                                    if(notificable==true){
                                        generarReporte = true
                                        codigos << codigo.nombre
                                        paciente << patient
                                        def agregarNodoXml =  demographicService.crearXmlEPI13Morbilidad(nombreDoc,
                                                                                  patient.ids.value[0].extension,
                                                                                  patient.identities.primerNombre[0]+" "+patient.identities.segundoNombre[0]+" "+patient.identities.primerApellido[0],
                                                                                  patient.fechaNacimiento.format("dd/MM/yyyy"), 
                                                                                  fullDireccion,
                                                                                  parroquia,
                                                                                  municipio,
                                                                                  estado,
                                                                                  composi.context.startTime.value,
                                                                                  sexo,
                                                                                  codigos as String[],
                                                                                  params.desde,
                                                                                  params.hasta
                                                                                  )
                                    }
                                k++
                                }
                            }
                            
                        }
                }
            
                
            }
          j++      
        }
        
        }
        
         if(generarReporte == true){
            def FileName = []
             FileName << ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/reportes/epi13morbilidad.jasper")
             def outFile = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/documentos/epi13morbilidad.pdf")
             def xmlFile = ApplicationHolder.application.parentContext.servletContext.getRealPath("/data/reports/source/epi13morbilidad.xml")
             def record = "/pacientes/paciente"
             generado = reportsOutput(FileName as String[], outFile, xmlFile, record)
             if(generado){
                redirect(controller:'reportes', action:'index', params:[creado13morbilidad:true,tipo:outFile])
                }
         }else{
                redirect(controller:'reportes', action:'index', params:[creado13morbilidad:""])
            }   
         
        
        
        
        
    }
    
    def epi12morbilidad ={
        
    }
    
    
    
    public static boolean reportsOutput(String[] reportFileName, String outFileName, String xmlFileName, String recordPath){
       JRXmlDataSource jrxmlds = new JRXmlDataSource(xmlFileName,recordPath)
       HashMap hm = new HashMap()
       //List jpList = new ArrayList()
       List<JasperPrint> jpList = new ArrayList<JasperPrint>();
       try
          {
              def i =0
              def j = reportFileName.size()
              for(i=0;i<=j-1;i++){
                  println("ciclo:->"+i)
                  println("path:->"+reportFileName[i])
                  //jpList.add(net.sf.jasperreports.engine.util.JRLoader.loadObjectFromFile(reportFileName[i]));
                  //JasperReport i = JasperCompileManager.compileReport(reportFileName[i]);
                  JasperPrint reporte = JasperFillManager.fillReport(reportFileName[i],new HashMap(),new JRXmlDataSource(xmlFileName,recordPath))
                  jpList.add(reporte)
              }
              
            
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,outFileName);
            //exporter.setParameter(JRExporterParameter.JASPER_PRINT,print);
            
            exporter.setParameter(net.sf.jasperreports.engine.export.JRPdfExporterParameter.JASPER_PRINT_LIST, jpList);
            
            exporter.exportReport()
              
            System.out.println("Created file: " + outFileName)
            return true
          }
          catch (JRException e){
              e.printStackTrace();
              System.exit(1);
              return false
          }
          catch (Exception e){
              e.printStackTrace();
              System.exit(1);
              return false
          } 
    }
    
    def descargar={
        String filename = params.archivo
        File file = new File(filename);
        response.setContentType(new javax.activation.MimetypesFileTypeMap().getContentType(file));
        response.setContentLength((int)file.length());
        response.setHeader("content-disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
        InputStream is = new FileInputStream(file);
        org.springframework.util.FileCopyUtils.copy(is, response.getOutputStream());
        return null;

    }
    
}
