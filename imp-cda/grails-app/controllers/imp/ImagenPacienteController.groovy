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
                response.sendError(404)
                return
            }
            response.setContentType(paciente.tipoImagen)
            response.setContentLength(paciente.imagen.size())
            OutputStream out = response.getOutputStream()
            out.write(paciente.imagen)
            out.close()
        }else{
            response.sendError(404)
            return
        }
    }
}

