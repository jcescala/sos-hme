

        <g:if test="${!externo}">

          <p>No hay CDAs coincidentes</p>
        </g:if>
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


                <td><g:link controller="service" action="buscarCdaById" id="${cda.id}" params="[render: 'cda']">Ver CDA </g:link></td>
              <td><g:link controller="service" action="buscarCdaById" id="${cda.id}" params="[render: 'xml']">Ver XML </g:link></td>

              </tr>
            </g:each>
          </table>

          <util:remotePaginate controller="demographic"
                           action="${llamar}"
                           params="[id: idPaciente, desde: desde, hasta: hasta, marca:'pag']"
                           total="${total}"
                           update="[success: 'resultadoExterno', failure: 'errorResultadoExterno']"
                           max="10"
                           />
        </g:else>
 