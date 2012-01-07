        <g:if test="${!conexionImp}">

          <p>No hay conexion con Imp</p>
        </g:if>
        <g:elseif test="${!externo}">

          <p>No hay CDAs coincidentes</p>
        </g:elseif>
        <g:else>
          <table >

            <tr>
              <th>Titulo</th>
              <th>Fecha Creacion</th>
              <th colspan="2">Acciones</th>


            </tr>

            <g:each in="${externo}" var="cda">
              <tr>
                <td>${cda.titulo}</td>
                <td>${cda.fechaCreacion}</td>


                <td><g:link controller="service" action="buscarCdaById" id="${cda.id}" params="[idPaciente:idPaciente,render: 'cda']">Ver CDA </g:link></td>
              <td><g:link controller="service" action="buscarCdaById" id="${cda.id}" params="[idPaciente:idPaciente,render: 'xml']">Ver XML </g:link></td>

              </tr>
            </g:each>
          </table>

          <util:remotePaginate controller="service"
                           action="${llamar}"
                           params="[id: idPaciente, idOrg:idOrg,desde: desde, hasta: hasta, marca:'pag']"
                           total="${total}"
                           update="[success: 'resultadoExterno', failure: 'errorResultadoExterno']"
                           max="10"
                           />
        </g:else>
 