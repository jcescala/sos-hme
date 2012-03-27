<%@ page import="org.codehaus.groovy.grails.commons.ApplicationHolder" %>

<g:if test="${!conexionImp}">
  <p><g:message code="service.imp.conexionImp.false" /></p>
</g:if>

<g:elseif test="${!result}">

  <p><g:message code="service.imp.pacientesCoincidentes.false" /></p>
</g:elseif>
<g:else>


  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tabla1">

    <tr>
<%--  <th>ID Paciente</th> --%>

      <th><g:message code="hce.service.candidatos.idOrganizacion" /></th>

    <th><g:message code="hce.service.candidatos.identificadores" /></th>

    <th><g:message code="hce.service.candidatos.nombre" /></th>
    
    <th>Fecha Nacimiento</th>

    <th><g:message code="hce.service.candidatos.acciones" /></th>

    </tr>

    <g:each in="${result}" var="paciente">
      <tr>
<%-- <td>${paciente.idPaciente}</td>--%>
        
        <td>${paciente.nombreCentro}</td>


        <td>
      <g:if test="${paciente.cedula}">
${paciente.cedula} [CI] <br/>
      </g:if>

      <g:if test="${paciente.pasaporte}">
${paciente.pasaporte}[Pasaporte] </td>
      </g:if>

      <td>
        
        <a class="ficha iframe" href="${ApplicationHolder.application.config.service.serverURL}/imp-cda/imagenPaciente/imagen?idPaciente=${paciente.idPaciente}&idOrg=${paciente.idCentro}"><img src="${ApplicationHolder.application.config.service.serverURL}/imp-cda/imagenPaciente/imagen?idPaciente=${paciente.idPaciente}&idOrg=${paciente.idCentro}" style="width: 30px; height: auto;"/></a>

        ${paciente.primerNombre} ${paciente.segundoNombre} ${paciente.primerApellido} ${paciente.segundoApellido}
        </td>
        <td>
          ${paciente.fechaNacimiento}
        </td>  



		<td><div class="addImpRelation"><g:link controller="service"
			action="agregarRelacionPaciente"
			params="[idCentroImp: paciente.idCentro,idPacienteImp: paciente.idPaciente ,idPacienteOrg: idPacienteOrg]"
			class="boton2">Seleccionar</g:link>
			<input id="param1" value="${paciente.idCentro}" style="display:none;" >
			<input id="param2" value="${paciente.idPaciente}" style="display:none;" >
			<input id="param3" value="${idPacienteOrg}" style="display:none;" >
			</div>
		</td>

      </tr>
    </g:each>
  </table>
  
<script>
jQuery(function ($) {
	var OSX = {
		container: null,
		init: function () {
			$(".addImpRelation").click(function (e) {
				e.preventDefault();	
				
				var a1 = jQuery(this).children("#param1").val();
				jQuery('#idaddr1').val(a1);
				var a2 = jQuery(this).children("#param2").val();
				jQuery('#idaddr2').val(a2);
				var a3 = jQuery(this).children("#param3").val();
				jQuery('#idaddr3').val(a3);
				
				
				$("#osx-modal-content-addImpRelation").modal({
					overlayId: 'osx-overlay',
					containerId: 'osx-container',
					closeHTML: null,
					minHeight: 80,
					opacity: 65, 
					position: ['15%',],
					overlayClose: true,
					onOpen: OSX.open,
					onClose: OSX.close
				});
			});
		},
		open: function (d) {
			var self = this;
			self.container = d.container[0];
			d.overlay.fadeIn('slow', function () {
				$("#osx-modal-content-addImpRelation", self.container).show();
				var title = $("#osx-modal-title", self.container);
				title.show();
				d.container.slideDown('slow', function () {
					setTimeout(function () {
						var h = $("#osx-modal-data", self.container).height()
							+ 40; // padding
						d.container.animate(
							{height: h}, 
							200,
							function () {
								$("div.close", self.container).show();
								$("#osx-modal-data", self.container).show();
							}
						);
					}, 300);
				});
			})
		},
		close: function (d) {

				var self = this; // this = SimpleModal object
				d.container.animate(
					{top:"-" + (d.container.height() + 20)},
					500,
					function () {
						self.close(); // or $.modal.close();
					}
				);
			

		}
	};

	OSX.init();

});
</script>

  <util:remotePaginate controller="service"
                       action="buscarPaciente"
                       params="[id: idPacienteOrg]"
                       total="${total}"
                       update="[success: 'resultadoCandidatos', failure: 'errorResultadoCandidatos']"
                       max="10"
                       />

</g:else>