import javax.security.auth.callback.Callback
import javax.security.auth.callback.CallbackHandler
import javax.security.auth.callback.UnsupportedCallbackException
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor
import org.apache.ws.security.WSConstants
import org.apache.ws.security.WSPasswordCallback
import org.apache.ws.security.handler.WSHandlerConstants
import imp.Organizacion
class BootStrap {

    def secureServiceFactory
    def cdaServiceFactory

    def authInterceptor

    def init = { servletContext ->

        def org = new Organizacion()
        org.setNombre("Hospital")
        org.setLogin("abc")
        org.setPassword("123")
        org.save()

        //CONFIGURACION PARA LEER HEADERS DE ENTRADA
      

        Map<String, Object> inProps = [:]
        inProps.put(WSHandlerConstants.ACTION, org.apache.ws.security.handler.WSHandlerConstants.USERNAME_TOKEN+" "+org.apache.ws.security.handler.WSHandlerConstants.TIMESTAMP +" "+org.apache.ws.security.handler.WSHandlerConstants.SIGNATURE+ " "+org.apache.ws.security.handler.WSHandlerConstants.ENCRYPT )
        inProps.put(WSHandlerConstants.PASSWORD_TYPE, org.apache.ws.security.WSConstants.PW_DIGEST);
        inProps.put(WSHandlerConstants.PW_CALLBACK_REF, new CallbackHandler() {

              //SE CREA UNA INSTANCIA DE LA CLASE CallbackHandler
              //
              //Se sobreescribe el método handle()

            void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
               
                    WSPasswordCallback pc = (WSPasswordCallback) callbacks[0]

                 //   println "El usuario es: " + pc.getPassword()

                    //VERIFICAR QUIEN SE ESTÁ CONECTANDO
                    pc.setPassword("keystore");

                  /* def resultado =  Organizacion.withCriteria{

                        eq("login", pc.identifier)
                        eq("password", pc.password)

                    }
                    if(!resultado){
                    println "error :: wrong password"
                    throw new IOException("wrong password")
                    }else{
                        println "Organizacion conectada: "+ resultado
                    }*/


                /*if(pc.identifier != "ususer" || pc.password != "secret") {
                    println "error :: wrong password"
                    throw new IOException("wrong password")
                }*/
            }
        })




        inProps.put(WSHandlerConstants.SIG_PROP_FILE, "server.properties")
        inProps.put(WSHandlerConstants.DEC_PROP_FILE, "server_key.properties")
        
        secureServiceFactory.getInInterceptors().add(new WSS4JInInterceptor(inProps))
        cdaServiceFactory.getInInterceptors().add(new WSS4JInInterceptor(inProps))

        //---------------------------------------------------------------------------



        //AÑADIENDO HEADERS AL MENSAJE DE SALIDA
        Map<String, Object> outProps = [:]
        outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.TIMESTAMP);
        secureServiceFactory.getOutInterceptors().add(new WSS4JOutInterceptor(outProps))
        cdaServiceFactory.getOutInterceptors().add(new WSS4JOutInterceptor(outProps))

    }

    def destroy = {
    }
}