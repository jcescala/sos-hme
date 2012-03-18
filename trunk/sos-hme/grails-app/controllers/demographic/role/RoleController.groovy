package demographic.role
import demographic.party.Person
import util.FormatLog

/**
 * @author Angel Rodriguez (angel.rodriguez.leon@gmail.com)
 */

class RoleController {


    /*
     *@author Angel Rodriguez Leon
     *
     *Funcion que genera entradas en log correspondiente al nivel que se le pase por parametro.
	 *error o info
     * */ 
	private void logged(String message, String level, userId){

		def bla = new FormatLog()
		
		if(level.equals("info"))
			log.info(bla.createFormat(message, "long",userId))
		if(level == "error")
			log.error(bla.createFormat(message, "long",userId))
	}

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [roleInstanceList: Role.list(params), roleInstanceTotal: Role.count()]
    }

        /*
         *angel: Este create puede ser invocado de dos formas
         *       en la primera desde edit person donde se le pasa
         *       como parametro el id persona a quien se le asignara
         *       el rol
         *       y la segunda donde se crea el rol y se le asigna
         *       una persona
         *
         **/

    def create = {

        if(params.person != null){

            //println "parametro" + params.person.id
            def roleInstance = new Role()
            //roleInstance.properties = params
            return [roleInstance: roleInstance, personid: params.person.id]
        }else{
			def personUsers = Person.withCriteria{
				identities{
					eq("purpose", "PersonNameUser")
				}
			}
			
            def roleInstance = new Role()
            roleInstance.properties = params
            return [roleInstance: roleInstance, personUsers: personUsers]
        }


    }

    def save = {
        def roleInstance = new Role(params)
        def type = roleInstance.type
		def person = roleInstance.performer
		
		if (roleInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'role.label', default: 'Role'), roleInstance.id])}"
            logged("Role "+type+" creado correctamente para: "+person+" ","info", session.traumaContext.userId)
			redirect(action: "show", id: roleInstance.id)
        }
        else {
			def personUsers = Person.withCriteria{
				identities{
					eq("purpose", "PersonNameUser")
				}
			}
		
		
            render(view: "create", model: [roleInstance: roleInstance, personUsers: personUsers])
        }
    }

    def show = {
        def roleInstance = Role.get(params.id)
        if (!roleInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
            redirect(action: "list")
        }
        else {
            [roleInstance: roleInstance]
        }
    }

    def edit = {
        def roleInstance = Role.get(params.id)
        if (!roleInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [roleInstance: roleInstance]
        }
    }

    def update = {
        def roleInstance = Role.get(params.id)
        def persona = roleInstance.performer
		def type = roleInstance.type
		
        if (roleInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (roleInstance.version > version) {
                    
                    roleInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'role.label', default: 'Role')] as Object[], "Another user has updated this Role while you were editing")
                    render(view: "edit", model: [roleInstance: roleInstance])
                    return
                }
            }

            
            roleInstance.properties = params
            if (!roleInstance.hasErrors() && roleInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'role.label', default: 'Role'), roleInstance.id])}"
                
				logged("Role "+type+" actualizado correctamente para: "+persona+" ","info", session.traumaContext.userId)
				redirect(action: "show", id: roleInstance.id)
            }
            else {
                render(view: "edit", model: [roleInstance: roleInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def roleInstance = Role.get(params.id)
        def persona = roleInstance.performer
		def type = roleInstance.type
		if (roleInstance) {
            try {
                roleInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
                logged("Role "+type+" eliminado correctamente para: "+persona+" ","info", session.traumaContext.userId)
				redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
            redirect(action: "list")
        }
    }
}
