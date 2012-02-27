package hce;

import hce.core.common.directory.Folder
import hce.HceService


class DomainController {

   def hceService
   
   def index = {
      redirect(action: 'list')
   }
   
   /**
    * Lista dominios existentes.
    * TODO: filtrar dominios por permisos del usuario.
    */
   def list = {
      
      if (session.traumaContext)
      {
          // Si hay un dominio seleccionado, se deselecciona
          session.traumaContext.domainPath = null
      }
      
      // Lista de codigos de dominios de la config
      //def domains = grailsApplication.config.domains
      
      // Lista de folders creados a partir de los codigos de dominios de la configuracion
      def folders = Folder.findAllByPathLike("/domain%") // Las paths de los folders de dominios empiezan con /domain
      
      //println domains
      //println Folder.list()
      
      //return [domains: domains]
      return [folders: folders]
   }
   
   def selectDomain = {
      
      if (!hceService.domainHasTemplates(params.path))
      {		
			//informacion de transaccion para el log.info
			log.info("Dominio incorrecto: {domainPath: "+ params.path+"}")
			flash.message = 'domain.selectDomain.flash'
			redirect(action: "list")
			return
      }
      
      if (session.traumaContext)
      {
			session.traumaContext.domainPath = params.path
      }
      
	  //informacion de transaccion para el log.info
	  log.info("Dominio seleccionado correctamente: {domainPath: "+ params.path+"}")
	  redirect(controller: 'records', action: 'list')
   }
}
