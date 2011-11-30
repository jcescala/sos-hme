package imp


class Cda {
    static scaffold = true

    String documento
    String titulo
    String fechaCreacion

    Personal personalAutor
    Organizacion organizacionAutora

    Organizacion organizacionCustodia

    Personal personalAutentificador
    Organizacion organizacionAutentificadora


    static belongsTo = [paciente:Paciente]
    // protected Paciente paciente

    static mapping = {
    
        version false
        documento(type: 'text')
        // id column: 'id_documento'

    }

    static constraints = {
        
    }
    /*
    public setDocumento(String documento){

    this.documento = documento
    }

    public setOrganizacion(Organizacion org){

    this.organizacion = org
    
    }
    public setPersonal(Personal per){

    this.personal = per
    }
     */
}
