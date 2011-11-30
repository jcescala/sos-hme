package cda


class SecureService {

    static expose = ['cxf']

    boolean secureMethod() {
        println "Es seguro el servicio"
       

        return true
    }
}
