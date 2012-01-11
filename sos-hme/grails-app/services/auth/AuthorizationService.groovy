package auth;

import authorization.LoginAuth

class AuthorizationService {
    
    def loginExists( String user, String pass )
    {
        // TODO: pass 2 md5 params.pass.encodeAsPassword()
        def login = LoginAuth.findByUserAndPass(user, pass.encodeAsPassword())
        
        return login != null
    }
    
    def getLogin( String user, String pass )
    {
        // TODO: pass 2 md5
        def login = LoginAuth.findByUserAndPass(user, pass.encodeAsPassword())
        
        return login
    }
}
