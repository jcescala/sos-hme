package auth;

import auth.AuthorizationService
import util.HCESession


class AuthorizationController {
    
    def authorizationService
    
    def login = {
        
        if (params.doit)
        {
		
			//en esta linea se verifica el usuario y password  para acceder al la aplicacion.
            def login = authorizationService.getLogin(params.user, params.pass)
            if (login)
            {

                //Asigna el tiempo a la session
                session.setMaxInactiveInterval(1800);

                // Pone al usuario en session
                session.traumaContext = new HCESession( userId: login.id )
                
                //redirect(controller:'records', action:'list')
                redirect(controller:'domain', action:'list')
                return
            }
            else
            {
                // FIXME: i18n
                flash.message = "Login incorrrecto"
            }
        }
        return []
    }
    
    def logout = {
        
        session.traumaContext = null
        redirect(action:'login')
    }
}