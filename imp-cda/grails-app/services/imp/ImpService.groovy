package imp
import imp.*
import com.thoughtworks.xstream.*
import java.io.OutputStream.*
import converters.DateConverter
import javax.jws.*

class ImpService {
    
    static expose=['cxf']
    static transactional = true
    
    //def alcance
    def serviceMethod() {

    }


    def crearPaciente(String paciente){

        def x = new XStream();

        def objPaciente = (Paciente) x.fromXML(paciente)

        //FIXME: debe buscarse en el indice primero para seleccionar un paciente

        //Crear un indice cableado

        def indice = new IndicePaciente()
        indice.save()
        objPaciente.setIndice(indice)
        if(objPaciente.save()){


            return true
        }else{

            return false
        }

        

    }

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

    def eliminarPaciente(String idPaciente){



        
    }


  



/*

    def pruebaAlcance(){

        alcance = "Usado"

        println alcance
    }

    
    int otroAlcance(){


        println alcance
        return 0
    }


*/


}
