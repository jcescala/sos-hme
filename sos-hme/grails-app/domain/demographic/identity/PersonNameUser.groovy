package demographic.identity

class PersonNameUser extends PersonName{


    String telfhabitacion
    String telfcelular
    String email


    static constraints = {
        primerNombre (blank:false, matches: "[a-zA-Z]+")
        segundoNombre (blank:false, matches: "[a-zA-Z]+")
        primerApellido (blank:false, matches: "[a-zA-Z]+")
        segundoApellido (blank:false, matches: "[a-zA-Z]+")


        telfhabitacion (nullable:true,matches:"[0-9]+")
        telfcelular (nullable:true,matches:"[0-9]+")
        email (nullable:true, email:true)


    }
}
