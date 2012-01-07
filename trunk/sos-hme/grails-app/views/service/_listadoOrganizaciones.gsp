<g:if test="${!conexionImp}">

          <p>No hay conexion con Imp</p>
</g:if>

<g:elseif test="${!result}">

  <p>No hay Organizaciones coincidentes</p>
</g:elseif>
      <g:else>
        <table>

          <tr>
          <th>Nombre</th>
          <th>Acciones</th>


          </tr>

          <g:each in="${result}" var="org">
	        <tr>
                  <td>${org.nombre}</td>

                  <td><g:link controller="service" action="buscarCdaByPacienteAndOrganizacion" params="[org:org.numeroOrg ,pac: pacienteId]">Ver lista de CDA´s </g:link></td>

                </tr>
	      </g:each>
        </table>
      </g:else>

