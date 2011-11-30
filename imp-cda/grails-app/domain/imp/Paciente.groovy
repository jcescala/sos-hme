package imp

class Paciente {
    static scaffold = true

    String idPaciente
    String cedula
    String pasaporte

    String primerNombre
    String segundoNombre
    String primerApellido
    String segundoApellido
    
    static hasMany = [cdas: Cda]
    static belongsTo = [indice: IndicePaciente]
    Organizacion centro
    
    static mapping = {
    
    table 'paciente'
    version false
  //  id column: 'id_paciente'
    }

    static constraints = {

        idPaciente(nullable:false)
        cedula(unique:true, nullable:true)
        pasaporte(unique:true, nullable:true)
        primerNombre(nullable:true)
        segundoNombre(nullable:true)
        primerApellido(nullable:true)
        segundoApellido(nullable:true)
        


    }

    static def existePaciente(String cedula){

        def p = Paciente.findByCedula(cedula)
        //SE USA EL OPERADOR TERNARIO
        //return result = p ? true : false
        //
        //SE USA EL OPERADOR ELVIS
        return p ?: false

    }
}