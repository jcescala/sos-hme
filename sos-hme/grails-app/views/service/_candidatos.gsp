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
          <th>FOTO</th>
          <th><g:message code="hce.service.candidatos.idOrganizacion" /></th>

          <th><g:message code="hce.service.candidatos.identificadores" /></th>
          
          <th><g:message code="hce.service.candidatos.nombre" /></th>
          
          <th><g:message code="hce.service.candidatos.acciones" /></th>

          </tr>

          <g:each in="${result}" var="paciente">
	        <tr>
                 <%-- <td>${paciente.idPaciente}</td>--%>
                 <td>
                 
                 <a class="ficha iframe" href="${ApplicationHolder.application.config.service.serverURL}/imp-cda/imagenPaciente/imagen?idPaciente=${paciente.idPaciente}&idOrg=${paciente.idCentro}">
  
                 <img src="http://127.0.0.1:8080/imp-cda/imagenPaciente/imagen?idPaciente=${paciente.idPaciente}&idOrg=${paciente.idCentro}" style="width: 30px; height: auto;"/>
                
                </a>
                 </td>
                  <td>${paciente.nombreCentro}</td>


                  <td>
                    <g:if test="${paciente.cedula}">
                    ${paciente.cedula} [CI] <br/>
                    </g:if>

                     <g:if test="${paciente.pasaporte}">
                    ${paciente.pasaporte}[Pasaporte] </td>
                    </g:if>

                  <td>${paciente.primerNombre} ${paciente.segundoNombre} ${paciente.primerApellido} ${paciente.segundoApellido}</td>
                  


	        <td><g:link controller="service"
                            action="agregarRelacionPaciente"
                            params="[idCentroImp: paciente.idCentro,idPacienteImp: paciente.idPaciente ,idPacienteOrg: idPacienteOrg]"
                            class="boton2">Seleccionar</td>
                </g:link>
                </tr>
	      </g:each>
        </table>


        <util:remotePaginate controller="service"
                           action="buscarPaciente"
                           params="[id: idPacienteOrg]"
                           total="${total}"
                           update="[success: 'resultadoCandidatos', failure: 'errorResultadoCandidatos']"
                           max="10"
                           />

      </g:else>