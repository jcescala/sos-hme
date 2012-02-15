<%@ page import="java.text.SimpleDateFormat" %>
<html>
  <head>
    <meta name="layout" content="basicrecord" />
    <title><g:message code="demographic.admision_paciente.title" /></title>
    <style>
      label {
        display: block;
      }
    </style>
    
  </head>
  <body style="height: 1100px;">
    <h1><g:message code="demographic.admision_paciente.title" /></h1>

    <ul class="top_actions">
      <g:if test="${session.traumaContext.episodioId}">
        <li><g:link controller="records" action="show" id="${session.traumaContext.episodioId}" class="home"><g:message code="demographic.lista_candidatos.action.backToEpisode" /></g:link></li>
      </g:if>
      <g:else>
        <li><g:link controller="records" action="list" class="home"><g:message code="episodio.list.title" /></g:link></li>

      </g:else>
    </ul>

  <g:compositionHasPatient episodeId="${session.traumaContext.episodioId}">
    <div style="color:red;">
      <g:message code="trauma.show.feedback.patientAlreadySelectedForThisEpisode" />
    </div><br/>
  </g:compositionHasPatient>

  <g:if test="${flash.message}">
    <div style="color:red;">
      <g:message code="${flash.message}" />
    </div>
  </g:if>

  <g:form action="findPatient">
    <label for="identificador">
      <g:message code="persona.identificador" />
    </label>
    <g:textField name="identificador" value="${params.identificador}" />
    <g:select name="root" from="${tiposIds}" optionKey="codigo" optionValue="nombreCorto" value="${((params.root) ? params.root : 'none')}" />

    <label for="primerNombre">
      <g:message code="persona.primerNombre" />
    </label>
    <g:textField name="personName.primerNombre" value="${params.('personName.primerNombre')}" />

    <label for="segundoNombre">
      <g:message code="persona.segundoNombre" />
    </label>
    <g:textField name="personName.segundoNombre" value="${params.('personName.segundoNombre')}" />

    <label for="primerApellido">
      <g:message code="persona.primerApellido" />
    </label>
    <g:textField name="personName.primerApellido" value="${params.('personName.primerApellido')}" />

    <label for="segundoApellido">
      <g:message code="persona.segundoApellido" />
    </label>
    <g:textField name="personName.segundoApellido" value="${params.('personName.segundoApellido')}" />

    <label for="fechaNacimiento">
      <g:message code="persona.fechaNacimiento" />
    </label>
    <g:datePicker name="fechaNacimiento" value="${((bd) ? bd : 'none')}" precision="day" noSelection="['':'']" />
    
    <br/>
    <br/>
    <g:submitButton name="doit" value="${message(code:'demographic.admision_paciente.buscar')}" />

  </g:form>
</body>
</html>