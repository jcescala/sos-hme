package imp
import imp.*
import admin.User

//import com.thoughtworks.xstream.*
//import java.io.OutputStream.*
import converters.DateConverter
//mport javax.jws.*
//import org.springframework.web.context.request.RequestContextHolder


import com.cxf.demo.PacienteArr

class ImpService {
    
    static expose=['cxf']
    static transactional = true
    
   


    boolean agregarPaciente(PacienteArr paciente, String idOrganizacion){

        //        def x = new XStream();
        //
        //        def objPaciente = (Paciente) x.fromXML(paciente)
        //
        //        //FIXME: debe buscarse en el indice primero para seleccionar un paciente
        //
        //        //Crear un indice cableado
        //
        //        def indice = new IndicePaciente()
        //        indice.save()
        //        objPaciente.setIndice(indice)
        //        if(objPaciente.save()){
        //
        //
        //            return true
        //        }else{
        //
        //            return false
        //        }

        def p = new Paciente()

       
        p.setIdPacienteOrg(paciente.getIdPaciente())
        p.setCedula(paciente.getCedula())
        p.setPasaporte(paciente.getPasaporte())
        p.setPrimerNombre(paciente.getPrimerNombre())
        p.setSegundoNombre(paciente.getSegundoNombre())
        p.setPrimerApellido(paciente.getPrimerApellido())
        p.setSegundoApellido(paciente.getSegundoApellido())


        //PREGUNTAR SI SE DESEA CRUZAR O AGREGAR UNO NUEVO!!!
        def indice = new IndicePaciente()
        indice.save()
        p.setIndice(indice)
        ////////////////////////////////////////////////////




        // def org = new Organizacion()
        def org= Organizacion.findByUniqueIdentifier(idOrganizacion)

        if(org){

            //La organizacion existe y es valida
            if(!Paciente.findByIdPacienteOrgAndCentro(paciente.getIdPaciente(),org)){
                p.setCentro(org)
                if(p.save()){

                    return true

                }else{
                
                    throw new RuntimeException("Error al guardar paciente")
                    return false
                }
            
            
            }else{

                throw new RuntimeException("El ID paciente ya esta registrado para la organizacion")
                return false

            }
        }else{

            throw new RuntimeException("El ID-TOKEN ("+idOrganizacion+") de Organizacion es invalido")
            return false

        }


    }

    def editarPaciente(String paciente){
        //MANDAR UUID ???

        return true
    }

    boolean eliminarPaciente(String IdPacienteOrg, String idOrganizacion){


        def org = Organizacion.findByUniqueIdentifier(idOrganizacion)

        if(org){


            def paciente = Paciente.findByIdPacienteOrgAndCentro(IdPacienteOrg, org)

            if(paciente){

             /* def ind = IndicePaciente.findByUniqueIdentifier(paciente.indice.uniqueIdentifier)
              println "INDICE "+ind
              */

                //COMO 'paciente' es una instancia de trancision debo llamar a delete(flush:true)
                //de lo contrario de generaria un error en la eliminacion
                try {
                    paciente.delete(flush:true)
                    return true
                }
                catch(org.springframework.dao.DataIntegrityViolationException e) {
                    e.printStackTrace()
                    throw new RuntimeException("No se pudo eliminar el paciente")
                    return false
                }
               
            }else{
                throw new RuntimeException("El ID paciente no esta registrado para la organizacion")
                return false
            }

        }else{throw new RuntimeException("El ID-TOKEN ("+idOrganizacion+") de Organizacion es invalido")
            return false


        }


    }


    def buscarCandidatos(){


        return true
    }

    def agregarRelacionPaciente(){
        
        
        return true
    }

    def modificarRelacionPaciente(){
        return true
    }

    def eliminarRelacionPaciente(){
        return true
    }

    /*
    def buscarPaciente(String paciente){
    def x = new XStream();
    def objPaciente = (Paciente) x.fromXML(paciente)

    //Plantear algoritmo de busqueda que coloque los resultados según su coincidencia
    //BUSCAR LISTA DE CANDIDATOS
    def candidatos = Paciente.withCriteria(){
    or{
    eq('cedula', objPaciente.getCedula())
    eq('pasaporte', objPaciente.getPasaporte())
    eq('primerNombre', objPaciente.getPrimerNombre())
    eq('segundoNombre', objPaciente.getSegundoNombre())
    eq('primerApellido', objPaciente.getPrimerApellido())
    eq('segundoApellido', objPaciente.getSegundoApellido())
    }
    }
    //println "Clase: " + candidatos

        

    //def candidatos = Paciente.executeQuery("select pac.primerNombre, pac.primerApellido from Paciente pac")
    if(candidatos){


    candidatos = OrderService.ordenarCandidatos(objPaciente, candidatos)

    //OMITO LOS CAMPOS INDICE, CDAS Y ID para estar de acorde al POJO
    x.omitField(candidatos[0].class, "indice")
    x.omitField(candidatos[0].class, "cdas")
    x.omitField(candidatos[0].class, "id")
    x.omitField(candidatos[0].class, "centro")


    //println "CandidatoS: " + candidatos
        
    def aux = x.toXML(candidatos)

    //println aux
    return aux
    }else{

    return false
    }


        
    }
     */
    

    
   




}
