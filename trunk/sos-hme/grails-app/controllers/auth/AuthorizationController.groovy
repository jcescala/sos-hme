package auth;

import auth.AuthorizationService
import util.HCESession
import util.FormatLog
import demographic.role.*
import authorization.LoginAuth
import java.util.*

/**
 * @author Pablo Pazos Gutierrez (pablo.swp@gmail.com)
 */
class AuthorizationController {
    

	
	
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
					logged("Acceso valido a SOS Telemedicina Administracion", "info", session.traumaContext.userId)
					
					redirect(controller:'loginAuth', action:'list')
					return

						// de lo contrario
                }else{
                
					//Asigna el tiempo a la session
					session.setMaxInactiveInterval(1800);

					// Pone al usuario en session
					session.traumaContext = new HCESession( userId: login.id )
					
					//informacion de transaccion para el log.info
					logged("Acceso valido a SOS Telemedicina HME:","info",session.traumaContext.userId)

					//se redirecciona a la vista de dominios medicos.
					redirect(controller:'domain', action:'list')
					return

                    
                }		
			

            
			
			
			
			
			
			}
            else
            {

                // FIXME: i18n
                flash.message = "Login incorrrecto"
				logged("Acceso invalido a SOS Telemedicina user: "+params.user+" ","info", -1)
            }
        }
        return []
    }
    
    def logout = {
        
		logged("Session finalizada correctamente", "info", session.traumaContext.userId)
        
		redirect(action:'login')
        session.traumaContext = null
    }
}
