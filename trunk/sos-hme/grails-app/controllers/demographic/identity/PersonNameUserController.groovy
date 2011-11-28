package demographic.identity

class PersonNameUserController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [personNameUserInstanceList: PersonNameUser.list(params), personNameUserInstanceTotal: PersonNameUser.count()]
    }

    def create = {
        def personNameUserInstance = new PersonNameUser()
        personNameUserInstance.properties = params
        return [personNameUserInstance: personNameUserInstance]
    }

    def save = {
        def personNameUserInstance = new PersonNameUser(params)
        if (personNameUserInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'personNameUser.label', default: 'PersonNameUser'), personNameUserInstance.id])}"
            redirect(action: "show", id: personNameUserInstance.id)
        }
        else {
            render(view: "create", model: [personNameUserInstance: personNameUserInstance])
        }
    }

    def show = {
        def personNameUserInstance = PersonNameUser.get(params.id)
        if (!personNameUserInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'personNameUser.label', default: 'PersonNameUser'), params.id])}"
            redirect(action: "list")
        }
        else {
            [personNameUserInstance: personNameUserInstance]
        }
    }

    def edit = {
        def personNameUserInstance = PersonNameUser.get(params.id)
        if (!personNameUserInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'personNameUser.label', default: 'PersonNameUser'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [personNameUserInstance: personNameUserInstance]
        }
    }

    def update = {
        def personNameUserInstance = PersonNameUser.get(params.id)
        if (personNameUserInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (personNameUserInstance.version > version) {
                    
                    personNameUserInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'personNameUser.label', default: 'PersonNameUser')] as Object[], "Another user has updated this PersonNameUser while you were editing")
                    render(view: "edit", model: [personNameUserInstance: personNameUserInstance])
                    return
                }
            }
            personNameUserInstance.properties = params
            if (!personNameUserInstance.hasErrors() && personNameUserInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'personNameUser.label', default: 'PersonNameUser'), personNameUserInstance.id])}"
                redirect(action: "show", id: personNameUserInstance.id)
            }
            else {
                render(view: "edit", model: [personNameUserInstance: personNameUserInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'personNameUser.label', default: 'PersonNameUser'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def personNameUserInstance = PersonNameUser.get(params.id)
        if (personNameUserInstance) {
            try {
                personNameUserInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'personNameUser.label', default: 'PersonNameUser'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'personNameUser.label', default: 'PersonNameUser'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'personNameUser.label', default: 'PersonNameUser'), params.id])}"
            redirect(action: "list")
        }
    }
}
