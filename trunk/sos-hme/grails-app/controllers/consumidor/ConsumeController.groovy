package consumidor
import cda.*

class ConsumeController {


def simpleServiceClient
//def secureServiceClient
def customSecureServiceClient

    def index = {


        /*cxf.client.demo.simple.SimpleRequest request1 = new cxf.client.demo.simple.SimpleRequest(age: 32, name: "Christian")
        cxf.client.demo.simple.SimpleResponse response1 = simpleServiceClient.simpleMethod1(request1)


        cxf.client.demo.simple.SimpleRequest request2 = new cxf.client.demo.simple.SimpleRequest(age: 32, name: "Christian")
        cxf.client.demo.simple.SimpleResponse response2 = simpleServiceClient.simpleMethod2(request2)

        render(view: '/index', model: [simpleRequest1: request1, simpleResponse1: response1, simpleRequest2: request2, simpleResponse2: response2])
*/

    //cda.LogueoResponse response1 = simpleServiceClient.logueo("login","pass")
    def response1 = customSecureServiceClient.secureMethod()
        render("<h1>Bien</h1>")

    }
}
