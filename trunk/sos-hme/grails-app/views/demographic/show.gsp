
<html>
  <head>
    <meta name="layout" content="ehr-modal" />
  <g:javascript library="jquery" />

  <g:javascript>

    $(document).ready( function() {
    $('#message').delay(1000).fadeOut();
    });
    function cargando(etiqueta){



    $(etiqueta).html("<img src='${createLinkTo(dir:"images", file:"spinner.gif")}'/> Cargando...");


    }

  </g:javascript>


  <title><g:message code="demographic.show.title" /></title>
  <style>
    table #list {
      background-color: #ffffdd;
      width: 100%;
      font-size: 12px;
      border: 1px solid #000;
    }
    #list th {
      background-color: #ccccdd;
    }
    #list td {
      text-align: center;
    }
  </style>




</head>
<body>
  <div class="bodydomainlist">

    <h2><g:message code="demographic.show.title" /></h2>

    <ul class="top_actions">
      <li>
      <g:link action="admisionPaciente" class="back"><g:message code="demographic.lista_candidatos.action.admisionPaciente" /></g:link>
      </li>
      <li>
      <g:link controller="records" action="create" params="[root:root, extension:extension]" class="create"><g:message code="demographic.show.action.createEpisode" /></g:link>
      </li>
      <!--
       <li>
       <g:link controller="service" action="agregarRalacionPaciente" params="[]" class="create">Agregar Relacion Paciente</g:link>
       </li>
       <li>
       <g:link controller="service" action="agregarRalacionPaciente" params="[]" class="create">Eliminar Relacion Paciente</g:link>
       </li>-->

<%-- TODO: que otra accion sea seleccionar un episodio existente --%>
    </ul>


    <g:set var="person_id" value="${persona.id}" />





<%-- DATOS DEMOGRAFICOS--%>
    <div id="demograficos">
      <g:set var="name" value="${persona.identities.find{ it.purpose == 'PersonNamePatient'} }" />


<%-- Preguntar primero si tiene foto--%>

      <g:if test="${persona.sexo=='Masculino'}">
        <img src="${createLinkTo(dir:"images", file:"man.png")}" style="width:120px; border-color: black;border-style: solid; float: left; margin:10px;"/>
      </g:if>
      <g:else>
        <img src="${createLinkTo(dir:"images", file:"woman.png")}" style="width:120px; border-color: black;border-style: solid; float: left; margin:10px;"/>
      </g:else>

      <h2>${name.toString()}</h2>
      <table id="list" class="listrecords">
        <tr>
          <th><g:message code="persona.identificadores" /></th>
        <th><g:message code="persona.primerNombre" /></th>
        <th><g:message code="persona.segundoNombre" /></th>
        <th><g:message code="persona.primerApellido" /></th>
        <th><g:message code="persona.segundoApellido" /></th>
        <th><g:message code="persona.fechaNacimiento" /></th>
        <th><g:message code="persona.sexo" /></th>
        </tr>
        <tr>
          <td><g:render template="UIDBasedID" collection="${persona.ids}" var="id" /></td>

        <td>${name?.primerNombre}</td>
        <td>${name?.segundoNombre}</td>
        <td>${name?.primerApellido}</td>
        <td>${name?.segundoApellido}</td>
        <td>${persona.fechaNacimiento}</td>
        <td>${persona.sexo}</td>
        </tr>
      </table>
    </div>
<%-- DATOS OPCIONES DE IMP--%>

    <g:if test="${conexionImp}">

    <div id="imp">

      <h4>Opciones de IMP</h4>
      <ul>

        <g:if test="${!agregadoImp}">
          <li><g:link controller="service" action="agregarPaciente" params="[id: person_id]" class="create">Agregar Paciente a IMP</g:link></li>
        </g:if>
        <g:else>
          <li><g:link controller="service" action="eliminarPaciente" params="[id: person_id]" class="create">Eliminar Paciente de IMP</g:link></li>




          <g:if test="${!relacionadoImp}">
            <li><g:remoteLink controller="service"
                              action="buscarPaciente"
                              params="[id: person_id, offset: '0']"
                              update="[success:'resultadoCandidatos',failure:'errorResultadoCandidatos']"
                              onLoading="cargando('#resultadoCandidatos')"
                              class="create">Agregar Relacion Paciente</g:remoteLink></li>
          </g:if>
          <g:else>

            <li><g:link controller="service" action="eliminarRelacionPaciente" params="[id: person_id]" class="create">Eliminar Relacion Paciente</g:link></li>
          </g:else>

        </g:else>
      </ul>

    </div>

    <div id="resultadoCandidatos">


    </div>
    <div id="errorResultadoCandidatos">


    </div>

    </g:if>
    <g:else>
    <p>No hay conexion con IMP</p>
    </g:else>



<%-- REGISTROS INTERNOS --%>
    <div id="registroInterno" style="border: 2px coral solid;margin-top: 10px;padding: 5px;">
      <h3>Registros Internos</h3> <hr/>
      <g:formRemote name="busquedaInterna"
                    url="[controller:'service',action:'busquedaInterna', params: [id: person_id,marca: 'fil']]"
                    update="[success: 'resultadoInterno', failure: 'errorResultadoInterno']"
                    onLoading="cargando('#resultadoInterno')">
        <br/>
        <label for="rangoFechas">
          <b> <g:message code="buscar.rango_fechas" /></b>
        </label>



        <label for="desde">
          <g:message code="buscar.desde" />
        </label>
        <g:datePicker name="desde" value="" precision="day" noSelection="['':'']" />



        <label for="hasta">
          <g:message code="buscar.hasta" />
        </label>
        <g:datePicker name="hasta" value="" precision="day" noSelection="['':'']" />


        <g:submitButton name="doit" value="${message(code:'buscar.filtro')}" />


      </g:formRemote>
      <g:remoteLink name="busquedaAllInterna"
                    url="[controller:'service',action:'busquedaAllInterna',params: [id: person_id, offset:'0', marca:'fil' ]]"
                    update="[success: 'resultadoInterno', failure: 'errorResultadoInterno']"
                    onLoading="cargando('#resultadoInterno')">Todos los registros

      </g:remoteLink>




      <div id="resultadoInterno"></div>
      <div id="errorResultadoInterno"></div>


    </div>


<g:if test="${conexionImp && agregadoImp}">
<%-- REGISTROS EXTERNOS --%>


    <div id="registroExterno" style="border: 2px coral solid;margin-top: 10px;padding: 5px;">
      <h3>Registros Externos</h3> <hr/>
      <div id="busquedaExterna">
        <g:formRemote name="busquedaExterna"
                      url="[controller:'service',action:'busquedaExterna',params: [id: person_id, offset:0, marca: 'fil']]"
                      update="[success: 'resultadoExterno', failure: 'errorResultadoExterno']"
                      onLoading="cargando('#resultadoExterno')">
          <br/>
          <label for="rangoFechas">
            <b> <g:message code="buscar.rango_fechas" /></b>
          </label>



          <label for="desde">
            <g:message code="buscar.desde" />
          </label>
          <g:datePicker name="desde" value="" precision="day" noSelection="['':'']" />



          <label for="hasta">
            <g:message code="buscar.hasta" />
          </label>
          <g:datePicker name="hasta" value="" precision="day" noSelection="['':'']" />

          <g:submitButton name="doit" type="submit" value="${message(code:'buscar.filtro')}" />
        </g:formRemote>
        <g:remoteLink name="busquedaAllExterna"
                      url="[controller:'service',action:'busquedaAllExterna',params: [id: person_id, offset:'0', marca:'fil' ]]"
                      update="[success: 'resultadoExterno', failure: 'errorResultadoExterno']"
                      onLoading="cargando('#resultadoExterno')">Todos los CDAs

        </g:remoteLink>

      </div>
      <div id="resultadoExterno" >


      </div>

      <div id="errorResultadoExterno"></div>



    </div>

<%-- ORGANIZACIONES RELACIONADAS--%>

    <div id="listadoOrganizaciones" style="border: 2px coral solid;margin-top: 10px;padding: 5px;">
      <h3>Listado de Organizaciones</h3> <hr/>


      <g:remoteLink controller="service"
                    action="listarOrganizaciones"
                    id="${person_id}"
                    update="[success:'resultadoOrganizaciones',failure:'errorResultadoOrganizaciones']"
                    on404="alert('not found');">Ver Organizaciones</g:remoteLink>
      <div id="resultadoOrganizaciones">
      </div>
      <div id="errorResultadoOrganizaciones">
      </div>


    </div>
</g:if>

  </div>

</body>
</html>