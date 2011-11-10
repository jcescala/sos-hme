<%@ page import="org.codehaus.groovy.grails.commons.ApplicationHolder" %>
<%@ page import="hce.core.common.change_control.Version" %>
<%@ page import="hce.core.composition.Composition" %>
<%@ page import="java.text.SimpleDateFormat" %>
<html>
  <head>
    <!--meta name="layout" content="ehr-modal" /-->
    <meta name="layout" content="basicregistro" />
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
    <h1><g:message code="demographic.show.title" /></h1>
  
    <ul class="top_actions">
      <li>
        <g:link action="admisionPaciente" class="back"><g:message code="demographic.lista_candidatos.action.admisionPaciente" /></g:link>
      </li>
      <li>
        <g:link controller="records" action="create" params="[root:root, extension:extension]" class="create"><g:message code="demographic.show.action.createEpisode" /></g:link>
      </li>
      <%-- TODO: que otra accion sea seleccionar un episodio existente --%>
    </ul>
  
    <table id="list">
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
        <g:set var="name" value="${persona.identities.find{ it.purpose == 'PersonName'} }" />
        <td>${name?.primerNombre}</td>
        <td>${name?.segundoNombre}</td>
        <td>${name?.primerApellido}</td>
        <td>${name?.segundoApellido}</td>
        <td>${persona.fechaNacimiento}</td>
        <td>${persona.sexo}</td>
      </tr>
    </table>

 <%-- FIXME: NO TE REPITAS TU MISMO , Armando, generar template list.gsp --%>
    <g:set var="person_id" value="${persona.id}" />
    <g:form controller="demographic" action="seleccionarPaciente" params="[id: person_id]">
  <br/>
    <label for="rangoFechas">
      <g:message code="buscar.rango_fechas" />
    </label>

      <br />
      
    <label for="desde">
      <g:message code="buscar.desde" />
    </label>
    <g:datePicker name="desde" value="" precision="day" noSelection="['':'']" />
  <br />

    
    <label for="hasta">
      <g:message code="buscar.hasta" />
    </label>
    <g:datePicker name="hasta" value="" precision="day" noSelection="['':'']" />



    
    <br/>
    <g:submitButton name="doit" value="${message(code:'buscar.filtro')}" />

  </g:form>

    <table id="list1">
      <tr>
        <th><g:message code="trauma.list.label.id" /></th>
        <th><g:message code="trauma.list.label.startTime" /></th>
        <th><g:message code="trauma.list.label.endTime" /></th>
        <th><g:message code="trauma.list.label.observations" /></th>
        <th><g:message code="trauma.list.label.state" /></th>
        <th><g:message code="trauma.list.label.actions" /></th>
      </tr>
      <g:each in="${compositions}" var="composition">
        <tr>
          <td>${composition.id}</td>
          <td><g:format date="${composition.context.startTime?.toDate()}" /></td>
          <td><g:format date="${composition.context.endTime?.toDate()}" /></td>
          <td>
            <%-- OJO: Solo funciona si el otherContext es ItemSingle y el value del Element es DvText --%>
            ${composition.context.otherContext.item.value.value}
          </td>
          <td>
            <%--
            // El .toString es por esto:
	        // Exception Message: No signature of method:
	        // org.codehaus.groovy.grails.context.support.PluginAwareResourceBundleMessageSource.getMessage()
	        // is applicable for argument types: (org.codehaus.groovy.grails.web.util.StreamCharBuffer, null,
	        // org.codehaus.groovy.grails.web.util.StreamCharBuffer, java.util.Locale) values:
	        // [ehr.lifecycle.incomplete, null, ehr.lifecycle.incomplete, es]
            --%>
            <g:message code="${g.stateForComposition(episodeId:composition.id).toString()}" />
          </td>
          <td>
            <g:link controller="records" action="show" id="${composition.id}"><g:message code="trauma.list.action.show" /></g:link>
            <br />
              <g:if test="${(g.stateForComposition(episodeId:composition.id) == Version.STATE_SIGNED)}">
                <g:set var="version" value="${Version.findByData(composition)}"/>
                <g:set var="archivoCDA" value="${new File(ApplicationHolder.application.config.hce.rutaDirCDAs + '\\' + version.nombreArchCDA)}"/>
                <g:if test="${!archivoCDA.exists()}">
                  <g:link controller="cda" action="create" id="${composition.id}">Crear CDA</g:link>
                </g:if>
                <g:else>
                  <g:message code="Documento Clinico Creado" /> <!-- TODO i18n -->
                  <g:link controller="cda" action="ver" id="${version.nombreArchCDA}"><g:message code="Ver CDA" /></g:link> <!-- TODO i18n -->

                </g:else>
              </g:if>
          </td>
        </tr>
      </g:each>
    </table>
  </body>
</html>