package imp

class Paciente implements Serializable{
    static scaffold = true

   

    String idPacienteOrg //el id que tiene asignado en la organizacion
    String cedula
    String pasaporte

    String primerNombre
    String segundoNombre
    String primerApellido
    String segundoApellido
    
    static hasMany = [cdas: Cda]
    static belongsTo = [indice: IndicePaciente, centro: Organizacion]
   
    
    static mapping = {
    
    table 'paciente'
    version false
    id composite:['idPacienteOrg', 'centro']
  //  id column: 'id_paciente'
    }

    static constraints = {

        idPacienteOrg(nullable:false)
        cedula(nullable:true)
        pasaporte(nullable:true)
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