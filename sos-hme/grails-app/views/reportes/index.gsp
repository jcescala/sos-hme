<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta name="layout" content="basicregistro" />
    <title><g:message code="reportes.suite.title" /></title>
  </head>
  <body>
    <h2>Control de Reportes EPI</h2>
    
    <div class="reportcentral">
      <div class="opcionesrepo">
        <div class ="titulorepo"><g:message code="reporte.titulolista"/></div>
        <div class ="rangofechasrepo"><g:message code="reporte.rangofecha"/></div>
        <div class ="accionrepo"><g:message code="reporte.accion"/></div>
      </div>
      <div class="listarepo">
        <div class="reportlist">
          <g:form action="epi10emergencia">
            <div class="titulorepo"><g:message code="reportes.epi10emer" /></div>
            <div class="rangofechasrepo">
              <label for="desde"><g:message code="buscar.desde" /></label>
              <g:datePicker name="desde" id="desde" value="" precision="day" noSelection="['':'']" />

              <label for="hasta"><g:message code="buscar.hasta" /></label>
              <g:datePicker name="hasta" value="" precision="day" noSelection="['':'']" />
            </div>
            <div class="accionrepo">
              <g:submitButton name="generarreporte" value="${message(code:'reportes.generate')}"/>
            </div>
          </g:form>
        </div>
        <div class="reportlist">
          <g:form action="epi10general">
            <div class="titulorepo"><g:message code="reportes.epi10general" /></div>
            <div class="rangofechasrepo">
              <label for="desde"><g:message code="buscar.desde" /></label>
              <g:datePicker name="desde" id="desde" value="" precision="day" noSelection="['':'']" />

              <label for="hasta"><g:message code="buscar.hasta" /></label>
              <g:datePicker name="hasta" value="" precision="day" noSelection="['':'']" />
            </div>
            <div class="accionrepo">
              <g:submitButton name="generarreporte" value="${message(code:'reportes.generate')}"/>
              <g:if test="${params.creado}">
                <g:link action="descargar" params="[archivo:params.tipo]">Descargar</g:link>
              </g:if>
              <g:else>
                Reporte No Generado
              </g:else>
            </div>
          </g:form>
        </div>
        
      </div>
    </div>
    
  </body>
</html>
