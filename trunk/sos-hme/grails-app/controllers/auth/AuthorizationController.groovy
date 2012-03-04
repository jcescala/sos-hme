package auth;

import auth.AuthorizationService
import util.HCESession
import demographic.role.*
import authorization.LoginAuth

class AuthorizationController {
    
    def authorizationService
    
    def login = {
        
        if (params.doit)
        {
		
			//en esta linea se verifica el usuario y password  para acceder al la aplicacion.
            def login = authorizationService.getLogin(params.user, params.pass)
            if (login)
            {

				// se busca el rol asociado con la clase persona asignada al login
                def roles = Role.withCriteria {
                    eq('performer', login.person)
                }

                def roleKeys = roles.type
                
				// si el usuario posee login de administrador 
                if ( roleKeys.intersect([Role.ADMIN]).size() > 0 ){

                    
					//Asigna el tiempo a la session
					session.setMaxInactiveInterval(1800);

					// Pone al usuario en session
					session.traumaContext = new HCESession( userId: login.id )
					
					//informacion de transaccion para el log.info
					log.info("Acceso valido a SOS Telemedicina Administracion"+
					" de Usuarios: {userId:"+session.traumaContext.userId+", user: "+LoginAuth.get(session.traumaContext.userId).user+", person: "+
					LoginAuth.get(session.traumaContext.userId).person+ ", roles: "+LoginAuth.get(session.traumaContext.userId).person.roles.type+"}")
					
					//log.info("soy admin: "+ LoginAuth.get(session.traumaContext.userId).person.roles.type)
					//se redirecciona a el area de administracion usuarios
					redirect(controller:'loginAuth', action:'list')
					return

						// de lo contrario
                }else{
                
					//Asigna el tiempo a la session
					session.setMaxInactiveInterval(1800);

					// Pone al usuario en session
					session.traumaContext = new HCESession( userId: login.id )
					
					//informacion de transaccion para el log.info
					log.info("Acceso valido a SOS Telemedicina HME: {userId:"+session.traumaContext.userId+", user: "+
					LoginAuth.get(session.traumaContext.userId).user+", person: "+LoginAuth.get(session.traumaContext.userId).person+ 
					", roles: "+LoginAuth.get(session.traumaContext.userId).person.roles.type+"}")
	
					//se redirecciona a la vista de dominios medicos.
					redirect(controller:'domain', action:'list')
					return

                    
                }			
			

            
			
			
			
			
			
		}
            else
            {
                // FIXME: i18n
                flash.message = "Login incorrrecto"
                log.info("Acceso invalido a SOS Telemedicina {user: "+params.user+"}")
            }
        }
        return []
    }
    
    def logout = {
        

        log.info("Session finalizada correctamente {userId:"+session.traumaContext.userId+", user: "+
					LoginAuth.get(session.traumaContext.userId).user+", person: "+LoginAuth.get(session.traumaContext.userId).person+ 
					", roles: "+LoginAuth.get(session.traumaContext.userId).person.roles.type+"}")
        redirect(action:'login')
        session.traumaContext = null
    }
}
