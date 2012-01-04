<g:if test="${!result}">

  <p>No hay pacientes coincidentes</p>
</g:if>
      <g:else>

        
        <table >

          <tr>
          <th>ID Paciente</th>
          <th>ID Organizacion</th>

          <th>Identificadores</th>
          
          <th>Primer Nombre</th>
          <th>Segundo Nombre</th>

          <th>Primer Apellido</th>
          <th>Segundo Apellido</th>

          <th>Acciones</th>

          </tr>

          <g:each in="${result}" var="paciente">
	        <tr>
                  <td>${paciente.idPaciente}</td>
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