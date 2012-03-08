package imp

/**
 *
 * @author Armando
 */
class ImagenPacienteController {

    def impService

    def index ={
    }

    def imagen = {
        def org = Organizacion.get(params.idOrg)
        def paciente
        if(impService.existePaciente(params.idPaciente, org.uniqueIdentifier)){
            paciente = Paciente.findByIdPacienteOrgAndCentro(params.idPaciente, org)
            if(!paciente.imagen || !paciente.tipoImagen){
                
                //El paciente no tiene imagen
                //response.sendError(404)
            File f = new File('web-app/images/pacientes/silueta.gif')
            
            
            response.setContentType('image/gif')
            response.setContentLength((int)f.size())
            OutputStream out = response.getOutputStream()
            out.write(f.getBytes())
            out.close()    
                
                
            
            }else{
            response.setContentType(paciente.tipoImagen)
            response.setContentLength(paciente.imagen.size())
            OutputStream out = response.getOutputStream()
            out.write(paciente.imagen)
            out.close()
            }
        }else{
            response.sendError(404)
            return
        }
    }
}

