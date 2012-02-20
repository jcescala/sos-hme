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
       
       
        
      </ul>
    </div>  


    <%-- BUSQUEDA REGISTROS--%>
    <div id="registroInterno">
      
      <g:formRemote name="busquedaRegistros"
                    url="[controller:'records',action:'listFiltro']"
                    update="[success: 'resultadoInterno', failure: 'errorResultadoInterno']"
                    >
        <br/>
        <label for="rangoFechas">
          <b> <g:message code="buscar.rango_fechas" /></b>
        </label>



        <label for="desde">
          <g:message code="buscar.desde" />
        </label>
        <%--<g:datePicker name="desde" value="" precision="day" noSelection="['':'']" />--%>
        <input name="desde" value="" type="text" class="DateSos" /> 


        <label for="hasta">
          <g:message code="buscar.hasta" />
        </label>
        <%--<g:datePicker name="hasta" value="" precision="day" noSelection="['':'']" />--%>
        <input name="hasta" value="" type="text" class="DateSos" />

        <select name="estado">
          <option value="todos"><g:message code="records.list.find.select.todos" /></option>
          <option value="ehr.lifecycle.incomplete"><g:message code="records.list.find.select.incompletos" /></option>
          <option value="ehr.lifecycle.signed"><g:message code="records.list.find.select.firmados" /></option>
         

        </select>
        <g:submitButton name="doit" value="${message(code:'buscar.filtro')}" />


      </g:formRemote>
      




      <div id="resultadoInterno">

        <g:render template="listado" model="['compositions':compositions,'userId':userId, 'domain': domain, 'total': total, 'estado':estado]"/>

      </div>
      <div id="errorResultadoInterno"></div>

    </div>




  </body>
</html>