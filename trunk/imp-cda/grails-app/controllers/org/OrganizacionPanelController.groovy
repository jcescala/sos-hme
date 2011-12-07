package org

class OrganizacionPanelController {



    def springSecurityService
    def index = {


        def user = springSecurityService.getCurrentUser()


        



    render(view:"/org/index", model: [user: user.username])
    }
}
