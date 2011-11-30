package imp
import imp.*
class OrderService {

    static transactional = true

    def serviceMethod() {

    }

    static def ordenarCandidatos(origen, candidatos){

        /*
         *Algoritmo de ordenamiento
        cedula/pasaporte -> 50 %
        fecha nac -> 20%
        nombre -> 15%
        apellido -> 15%
         *
         *
         *
         **/
        //println "Origen: "+origen

        //println "Ordenar: "+candidatos

        def puntos

        puntos = 0.0
        def listado = []
        candidatos.each{
          
        if((origen.getCedula() == it.getCedula())||(origen.getPasaporte() == it.getPasaporte()) ){
              puntos = puntos + 50.0 } //50%
        if(origen.getPrimerNombre() == it.getPrimerNombre() ){puntos = puntos + 7.5 } //7.5%
        if(origen.getSegundoNombre() == it.getSegundoNombre() ){puntos = puntos + 7.5 } //7.5%
        if(origen.getPrimerApellido() == it.getPrimerApellido() ){puntos = puntos + 7.5 } //7.5%
        if(origen.getSegundoApellido() == it.getSegundoApellido() ){puntos = puntos +  7.5} //7.5%

        //OJO FALTA LA FECHA 20 %
        puntos = 100 - puntos //Complemento (1 - A) el mas cercano a cero, Mejor desempeño no es necesario voltear la lista
        def expandido = new Expando(candidato: it, puntaje: puntos)
        listado.add(expandido)

      //  println "Total puntos: "+puntos
        puntos=0.0
        }
        //Se ordena la lista
        listado = listado.sort{it.puntaje}
        def i=0
        listado.each{

            //println "Candidato "+i+": " + it.candidato
            candidatos[i] = it.candidato
            i++

        }


        //println "Puntos:  "+puntos
        return candidatos

        
    }
}
