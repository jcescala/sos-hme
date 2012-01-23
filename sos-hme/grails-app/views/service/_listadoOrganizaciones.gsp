<g:if test="${!conexionImp}">

          <p><g:message code="service.imp.conexionImp.false" /></p>
</g:if>

<g:elseif test="${!result}">

  <p><g:message code="service.imp.pacientesCoincidentes.false" /></p>
</g:elseif>
      <g:else>
        <table>

          <tr>
          <th><g:message code="hce.service.listadoOrganizaciones.nombre" /></th>
          <th><g:message code="hce.service.listadoOrganizaciones.acciones" /></th>


          </tr>

          <g:each in="${result}" var="org">
	        <tr>
                  <td>${org.nombre}</td>

                  <td><g:link controller="service" action="buscarCdaByPacienteAndOrganizacion" params="[org:org.numeroOrg ,pac: pacienteId]">Ver lista de CDA´s </g:link></td>

                </tr>
	      </g:each>
        </table>
      </g:else>

