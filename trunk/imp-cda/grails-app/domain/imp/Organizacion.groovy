package imp

class Organizacion {

    static scaffold = true

    String nombre
    String login
    String password

    static hasMany = [pacientes: Paciente,personal: Personal]
  

    static mapping = {

  //  id column: 'id_organizacion'
    version false


    }



    static constraints = {
        login(unique:true)
    }

      static def existeOrganizacion(String nombre){

        def p = Organizacion.findByNombre(nombre)
        //SE USA EL OPERADOR TERNARIO
        //return result = p ? true : false
        //
        //SE USA EL OPERADOR ELVIS
        return p ?: false

    }



}
