// Place your Spring DSL code here

beans = {
    myCustomInterceptor(com.cxf.demo.security.CustomSecurityInterceptor){
    user = "myAlias"
    pass = "keystore"

    }
  

}