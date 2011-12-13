package imp

class IndicePaciente {

    String uniqueIdentifier

    //Ientidad global
   static hasMany = [pacientes: Paciente]

   /* static mapping = {
		//pacientes cascade:'all-delete-orphan'
	}
*/

    static constraints = {
       
        uniqueIdentifier(nullable:true)
    }

    transient beforeInsert = {
        uniqueIdentifier = java.util.UUID.randomUUID().toString()
    }
}
