<g:if test="${!conexionImp}">
<p><g:message code="service.imp.conexionImp.false" /></p>
</g:if>

<g:elseif test="${!result}">

  <p><g:message code="service.imp.pacientesCoincidentes.false" /></p>
</g:elseif>
      <g:else>

        
        <table >

          <tr>
         <%--  <th>ID Paciente</th> --%>
          <th><g:message code="hce.service.candidatos.idOrganizacion" /></th>

          <th><g:message code="hce.service.candidatos.identificadores" /></th>
          
          <th><g:message code="hce.service.candidatos.primerNombre" /></th>
          <th><g:message code="hce.service.candidatos.segundoNombre" /></th>

          <th><g:message code="hce.service.candidatos.primerApellido" /></th>
          <th><g:message code="hce.service.candidatos.segundoApellido" /></th>

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

                  <td>${paciente.primerNombre}</td>
                  <td>${paciente.segundoNombre}</td>

                  <td>${paciente.primerApellido}</td>
                  <td>${paciente.segundoApellido}</td>


	        <td><g:link controller="service"
                            action="agregarRelacionPaciente"
                            params="[idCentroImp: paciente.idCentro,idPacienteImp: paciente.idPaciente ,idPacienteOrg: idPacienteOrg]">Seleccionar</td>
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