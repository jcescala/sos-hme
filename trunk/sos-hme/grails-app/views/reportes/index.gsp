<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta name="layout" content="basicrecord" />
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
        <%--div class="reportlist">
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
        </div--%>
        <div class="reportlist">
          <g:form action="epi10general">
            <div class="titulorepo"><g:message code="reportes.epi10general" /></div>
            <div class="rangofechasrepo">
              <label for="desde"><g:message code="buscar.desde" /></label>
              <%--g:datePicker name="desde" id="desde" value="" precision="day" noSelection="['':'']" /--%>
              <input name="desde" value="" type="text" class="DateSos" />
              
              <label for="hasta"><g:message code="buscar.hasta" /></label>
              <%--g:datePicker name="hasta" value="" precision="day" noSelection="['':'']" /--%>
              <input name="hasta" value="" type="text" class="DateSos" /> 
            </div>
            <div class="accionrepo">
              <g:submitButton name="generarreporte" value="${message(code:'reportes.generate')}"/>
              <g:if test="${params.creado10general=='true'}">
                <g:link action="descargar" params="[archivo:params.tipo]">Descargar</g:link>
              </g:if>
              <g:if test="${params.creado10general=='false'}">
                <div class="mensajereportefalla">
                  No Hay Registro en el Rango de Fechas Seleccionado
                </div>
              </g:if>
              <g:else>
                <div class="mensajereporteexito">
                    Reporte No Generado
                </div>
              </g:else>  
            </div>
          </g:form>
        </div>
        <div class="reportlist">
          <g:form action="epi13morbilidad">
            <div class="titulorepo"><g:message code="reportes.epi13morbilidad" /></div>
            <div class="rangofechasrepo">
              <label for="desde"><g:message code="buscar.desde" /></label>
              <input name="desde" value="" type="text" class="DateSos" />

              <label for="desde"><g:message code="buscar.hasta" /></label>
              <input name="hasta" value="" type="text" class="DateSos" />
            </div>
            <div class="accionrepo">
              <g:submitButton name="generarreporte" value="${message(code:'reportes.generate')}"/>
              <g:if test="${params.creado13morbilidad=='true'}">
                <g:link action="descargar" params="[archivo:params.tipo]">Descargar</g:link>
              </g:if>
              <g:if test="${params.creado13morbilidad=='false'}">
                <div class="mensajereportefalla">
                  No Hay Registro en el Rango de Fechas Seleccionado
                </div>
                
              </g:if>
              <g:else>
                <div class="mensajereporteexito">
                  Reporte No Generado
                </div>
              </g:else>
            </div>
            
          </g:form>
        </div>
        
        <div class="reportlist">
          <g:form action="epi12morbilidad">
          <div class="titulorepo"><g:message code="reportes.epi12morbilidad" /></div>
          <div class="rangofechasrepo">
              <label for="desde"><g:message code="buscar.desde" /></label>
              <input name="desde" value="" type="text" class="DateSos" />

              <label for="desde"><g:message code="buscar.hasta" /></label>
              <input name="hasta" value="" type="text" class="DateSos" />
            </div>
          <div class="accionrepo">
              <g:submitButton name="generarreporte" value="${message(code:'reportes.generate')}"/>
              <g:if test="${params.creado12morbilidad}">
                <g:link action="descargar" params="[archivo:params.tipo]">Descargar</g:link>
              </g:if>
              <g:else>
               <div class="mensajereporteexito">
                  Reporte No Generado
                </div>
              </g:else>
          </div>
          </g:form>
        </div>
        
        
      </div>
    </div>
    
  </body>
</html>
