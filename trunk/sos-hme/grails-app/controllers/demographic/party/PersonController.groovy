package demographic.party
import hce.core.support.identification.UIDBasedID
import converters.DateConverter
import tablasMaestras.TipoIdentificador
import demographic.role.Role
import util.RandomGenerator
import demographic.identity.*

class PersonController {



	//private List getSubsections( String section )



    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	
	
	
	
    def index = {
	
	
        redirect(action: "list", params: params)
    }

    def list = {
		//def tiposIds = TipoIdentificador.list()
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		def pl = Person.createCriteria()
		def resultado = pl.list(params){
			
			like("class","%demographic.party.Person%")
			identities{
					like("purpose","%PersonNameUser%")
			
			}
			
		} 

        
        return [personInstanceList: resultado, personInstanceTotal: resultado.totalCount]
		
		
    }
	
    def create = {
	
		def tiposIds = TipoIdentificador.list()
        def personInstance = new Person()
        personInstance.properties = params
        return [personInstance: personInstance, tiposIds: tiposIds]
    }

	def find = {
		def tiposIds = TipoIdentificador.list()
        
		def pl = Person.createCriteria()
		def personPatients = pl.list(params){
			identities{
					like("purpose","%PersonNamePatient%")
					
					
			}
			
		} 
		
		
		//personInstance.properties = params
        return [personPatients: personPatients, tiposIds: tiposIds]
	}
	def copyperson = {
		def tiposIds = TipoIdentificador.list()
        def personInstance = new Person()
		def personInstance2 = new Person()
		def personPatientInstance = new PersonNameUser()
		
        //personInstance.properties = params
        println("id de la persona: "+params.personid)
		
		
		
		
		
		personInstance = Person.get(params.personid)
		
		
		
		//def bd = DateConverter.dateFromParams( params, 'fechaNacimiento_' )
        
		
		personInstance2.setFechaNacimiento( personInstance.getFechaNacimiento() )
		personInstance2.setSexo(personInstance.getSexo())
		
		
		def ids = personInstance.ids
		
		
		
		def id
		for(i in ids){
			id = UIDBasedID.findByValue(i.value)
			println("identificador: "+id+"\n")
			personInstance2.addToIds( id )
		}
		
		def identidad = PersonNamePatient.findById(personInstance.identities?.id[0])
		
		def identidad2 = new PersonNameUser()
		
		identidad2.setPrimerNombre(identidad.getPrimerNombre())
		identidad2.setSegundoNombre(identidad.getSegundoNombre())
		identidad2.setPrimerApellido(identidad.getPrimerApellido())
		identidad2.setSegundoApellido(identidad.getSegundoApellido())
		
		
		
		personInstance2.addToIdentities(identidad2)
		
		
		//return [personInstance: personInstance, tiposIds: tiposIds]
		
		
		
		
		if (personInstance2.save(flush: true)) {
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'person.label', default: 'Person'), personInstance.id])}"
				redirect(action: "show", id: personInstance2.id)
		}
		else {
				render(view: "create", model: [personInstance: personInstance2])
		}
		//redirect(action: "list", params: params)
	}
	
    def save = {
	
	

		if (params.create){	

		    def id = null
            if (params.root == TipoIdentificador.AUTOGENERADO)
            {
                // Verificar si este ID existe, para no repetir
                def extension = RandomGenerator.generateDigitString(8)
                id = UIDBasedID.create(params.root, extension)
                
                // Se deberia hacer con doWhile para no repetir el codigo pero groovy no tiene doWhile
                while ( UIDBasedID.findByValue(id.value) )
                {
                    extension = RandomGenerator.generateDigitString(8)
                    id = UIDBasedID.create(params.root, extension)
                }
            }else{
				// se verifica que ambos parametros no sean null
                if (params.extension && params.root)
                {
                    id = UIDBasedID.create(params.root, params.extension) // TODO: if !hasExtension => error
                    
                    // FIXME: verificar que no hay otro usuario con el mismo id
                    println "===================================================="
                    println "Busco por id para ver si existe: " + id.value
                    
					def existId = UIDBasedID.findByValue(id.value)
                    if (existId)
                    {
                        println "Ya existe!"
                        flash.message = "Ya existe la persona con id: " + id.value + ", verifique el id ingresado o vuelva a buscar la persona"
                        def tiposIds = TipoIdentificador.list()
                        render(view: "create", model: [tiposIds: tiposIds])
						return
                    }
                    else
                    println "No existe!"
                }
                else
                {
					println "identificador obligatorio!!"
                    // Vuelve a la pagina
                    flash.message = "identificador obligatorio, si no lo tiene seleccione 'Autogenerado' en el tipo de identificador"
                    def tiposIds = TipoIdentificador.list()
                    //return [tiposIds: tiposIds]
					render(view: "create", model: [tiposIds: tiposIds])
					return
                }
			}
            
			
			
			
			
			def personInstance = new Person( params ) // sexo, fechaNac (no mas)
            
            def bd = DateConverter.dateFromParams( params, 'fechaNacimiento_' )
            personInstance.setFechaNacimiento( bd )

            personInstance.addToIds( id )
            
            //def name = new PersonName(params)
            //person.addToIdentities( name )
            
            //def datos = new PersonNameUser(params)
            //personInstance.addToIdentities(datos)
            
            
			
			
			/*
			
			if (!person.save()) println person.errors
            
            
            def role = new Role(timeValidityFrom: new Date(), type: "paciente", performer: person)
            if (!role.save()) println role.errors
            
            redirect(action:'seleccionarPaciente', id:person.id)
            return		
		
		*/
		
		
			
		
			
			if (personInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'person.label', default: 'Person'), personInstance.id])}"
				redirect(action: "show", id: personInstance.id)
			}
			else {
				render(view: "create", model: [personInstance: personInstance])
			}
		
		
		
		
		}
    }
	

	


    def show = {
	
		def tiposIds = TipoIdentificador.list()
        def personInstance = Person.get(params.id)
        if (!personInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [personInstance: personInstance, tiposIds: tiposIds]
        }
		
	}

    def edit = {
		def tiposIds = TipoIdentificador.list()
        def personInstance = Person.get(params.id)
        if (!personInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [personInstance: personInstance, tiposIds: tiposIds]
        }
    }

	

	
	
    def update = {
        def personInstance = Person.get(params.id)
        if (personInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (personInstance.version > version) {
                    
                    personInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'person.label', default: 'Person')] as Object[], "Another user has updated this Person while you were editing")
                    render(view: "edit", model: [personInstance: personInstance])
                    return
                }
            }
//ini
		    def id = null
            if (params.root == TipoIdentificador.AUTOGENERADO)
            {
                // Verificar si este ID existe, para no repetir
                def extension = RandomGenerator.generateDigitString(8)
                id = UIDBasedID.create(params.root, extension)
                
                // Se deberia hacer con doWhile para no repetir el codigo pero groovy no tiene doWhile
                while ( UIDBasedID.findByValue(id.value) )
                {
                    extension = RandomGenerator.generateDigitString(8)
                    id = UIDBasedID.create(params.root, extension)
                }
            }else{
                if (params.extension && params.root)
                {
                    id = UIDBasedID.create(params.root, params.extension) // TODO: if !hasExtension => error
                    
                    // FIXME: verificar que no hay otro paciente con el mismo id
                    println "===================================================="
                    println "Busco por id para ver si existeee: " + id.value
                    def existId = UIDBasedID.findByValueLike(id.value)
                    // id.value.split("::")[0]
					
					
					if (existId)
                    {
                        println "Ya existe!"
                        flash.message = "Ya existe la persona con id: " + id.value + ", verifique el id ingresado o vuelva a buscar la persona"

						def tiposIds = TipoIdentificador.list()
						render(view: "edit", model: [personInstance: personInstance, tiposIds: tiposIds])
						
						return
                    }
                    else
                    println "No existe!"
                }
                else
                {
                    // Vuelve a la pagina
                    flash.message = "identificador obligatorio, si no lo tiene seleccione 'Autogenerado' en el tipo de identificador"
                    def tiposIds = TipoIdentificador.list()
                    //return [tiposIds: tiposIds]
                }
			}

//fin		
			if(id){
				def existExtension = false
				for( i in personInstance.ids){
					println(i.value.split('::')[0]+"\n\t"+params.root+"\n")
				   if( i.value.split('::')[0].toString() == (params.root)){
					   println("son iguales\n")
						i.value = id.value
						existExtension = true
						break
				   }

					
				}
				
				
				//verificar si existe

				if(!existExtension){
					personInstance.addToIds( id )
					println("no exite y fue agregado!! \n")
				}else{

					println("existe y no fue agregado!!\n")

				}
			}
			
		/*	
			esta parte es para validar que el arreglo devuelto sea de un solo elemento y no de mas
			de esta forma no se agreguen varias identidades a un person.
		
			println("IDENTIDADES:" +params.identities+"tamanio"+[params.identities].size() +" \n\n\n")
	
			if(params.identities.size()>1){
				params.identities = params.identities[0]
			}else{
				//params.identities = params.identities
			}
			println("IDENTIDAD:" +params.identities+"\n\n\n")*/
			personInstance.properties = params
            if (!personInstance.hasErrors() && personInstance.save(flush: true)) {

                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'person.label', default: 'Person'), personInstance.id])}"
                redirect(action: "show", id: personInstance.id)


            }
            else {
                              
                render(view: "edit", model: [personInstance: personInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
            redirect(action: "list")
        }
    }

	
	
	
	
	
	
	
	
    def delete = {
		println("identidades:::"+params.identities)
        def personInstance = Person.get(params.id)


      //  def rolid = personInstance.roles.get(1)

     //   def rol = Role.get(rolid)

        
        if (personInstance) {
            try {
               // rol.delete(flush: true)
                personInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
            redirect(action: "list")
        }
    }
}