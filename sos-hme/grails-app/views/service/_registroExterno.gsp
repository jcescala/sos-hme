        <g:if test="${!conexionImp}">

          <p><g:message code="service.imp.conexionImp.false" /></p>
        </g:if>
        <g:elseif test="${!externo}">

          <p><g:message code="service.imp.cdaCoincidentes.false" /></p>
        </g:elseif>
        <g:else>
          <table >

            <tr>
              <th><g:message code="hce.service.registroExterno.titulo" /></th>
              <th><g:message code="hce.service.registroExterno.fechaCreacion" /></th>
              <th colspan="2"><g:message code="hce.service.registroExterno.acciones" /></th>


            </tr>

            <g:each in="${externo}" var="cda">
              <tr>
                <td>${cda.titulo}</td>
                <td>${cda.fechaCreacion}</td>


                <td><g:link controller="service" action="buscarCdaById" id="${cda.id}" params="[idPaciente:idPaciente,render: 'cda']"><g:message code="hce.cda.verCda" /> </g:link></td>
              <td><g:link controller="service" action="buscarCdaById" id="${cda.id}" params="[idPaciente:idPaciente,render: 'xml']"><g:message code="hce.cda.verXML" /> </g:link></td>

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
 