<%@ page import="org.codehaus.groovy.grails.commons.ApplicationHolder" %>
<%@ page import="hce.core.common.change_control.Version" %>
<%@ page import="hce.core.composition.Composition" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="hce.HceService" %>
<html>
  <head>
    <meta name="layout" content="basicrecord" />
    <title><g:message code="episodio.list.title" /></title>
  </head>
  <body>
     <div class="bodydomainlist">
     
    <div id="listadominios" class="listadominios">
        <g:message code="episodio.list.title" />
    </div>
    
    <div id="menubar2" class="menubar2">
      <ul>
        <li>
          <g:link action="create" class="create"><g:message code="trauma.list.action.crearEpisodio" /></g:link>
        </li>
        <li>
          <g:link action="" class="find"><g:message code="trauma.list.action.buscarEpisodio" /></g:link>
        </li>
        <li>
          <g:link controller="demographic" action="admisionPaciente" class="find"><g:message code="demographic.admision_paciente.buscar_paciente" /></g:link>
        </li>
        <li>
          <g:link controller="reportes" action="index" class="find"><g:message code="reportes.link.title" /></g:link>
        </li>
      </ul>
    </div>  
    
    <table id="listrecords" class="listrecords">
      <tr>
        <th class="threcords"><g:message code="trauma.list.label.id" /></th>
        <th class="threcords"><g:message code="registro.paciente" /></th>
        <th class="threcords"><g:message code="trauma.list.label.startTime" /></th>
        <th class="threcords"><g:message code="trauma.list.label.endTime" /></th>
        <th class="threcords"><g:message code="trauma.list.label.observations" /></th>
        <th class="threcords"><g:message code="trauma.list.label.state" /></th>
        <th class="threcords"><g:message code="trauma.list.label.actions" /></th>
      </tr>
      <g:each in="${compositions}" var="composition">
        <tr>
          <td>${composition.id}</td>
          <td><g:person param1="${composition}" /></td>
          <td><g:formatDate date="${composition.context.startTime?.toDate()}" formatName="default.date.format.text"   /></td>
          <td><g:formatDate date="${composition.context.endTime?.toDate()}" formatName="default.date.format.text" /></td>
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
            <g:link action="show" id="${composition.id}"><g:message code="trauma.list.action.show" /></g:link>
            <br />
              <g:if test="${(g.stateForComposition(episodeId:composition.id) == Version.STATE_SIGNED)}">
                <g:set var="version" value="${Version.findByData(composition)}"/>
                <g:set var="archivoCDA" value="${new File(ApplicationHolder.application.config.hce.rutaDirCDAs + '//' + version.nombreArchCDA)}"/>
                <g:if test="${!archivoCDA.exists()}">
                  <g:link controller="cda" action="create" id="${composition.id}">Crear CDA</g:link>
                </g:if>
                <g:else>
                 <%-- <g:message code="Documento Clinico Creado" /> <!-- TODO i18n -->
                  <g:link controller="cda" action="ver" id="${version.nombreArchCDA}"><g:message code="hce.cda.verCda" /></g:link> <!-- TODO i18n -->
                    --%>
                </g:else>
              </g:if>
          </td>
        </tr>
      </g:each>
    </table>
    
    <%-- creo que esto estaba de test
    <ul>
      <g:each in="${templateNames}" var="template">
        <li><g:link action="generarTemplate" params="[templateId:template]">${template}</g:link></li>
      </g:each>
    </ul>
    --%>
    
    <g:paginate next="Siguiente" prev="Previo"
                maxsteps="5"
                controller="records" action="list"
                max="15"
                total="${Composition.countByRmParentId(domain.id)}" />
     </div>  
  </body>
</html>