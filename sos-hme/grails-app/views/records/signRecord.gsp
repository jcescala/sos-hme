<?xml version="1.0" encoding="ISO-8859-1" ?>
<html>
  <head>
    <meta name="layout" content="ehr" />
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <title><g:message code="trauma.sign.title" /></title>
    <g:javascript library="prototype/prototype" />
	<g:javascript library="jquery" />
<%--carga de codigos js y css para ventana modal--%>
<link rel="stylesheet" type="text/css" href="${createLinkTo(dir:'css/', file:'basic.css')}"/>
<script type="text/javascript" src="${createLinkTo(dir:'js/', file:'jquery.simplemodal.js')}"></script>
<script type="text/javascript" src="${createLinkTo(dir:'js/', file:'basic.js')}"></script>

    <g:javascript>
      <!--
        Event.observe(window, 'load', function(event) {
        
          // Focus en input nombre de usuario
          $('user').focus();
          
        });
      -->
    </g:javascript>
    <style>
      table {      
        border: 0px;
      }
      th {
        background: none;
        text-align: right;
        vertical-align: middle;
        padding-right: 10px;
        width: 80px;
      }
    
     
      
     
      table #sign_table {
        width: 290px;
      }
      #form1 input[type=submit] {
        position: relative;
        float: right;
      }
    </style>
  </head>
  <body>
    <div id="nivel3">
    <h2><g:message code="trauma.sign.title" /></h2>

    <g:if test="${flash.error}">
      <div id="logine"><div class="error">
        <ul><li><g:message code="${flash.error}" /></li></ul></div>
	  </div>
    </g:if>
    <g:if test="${flash.message}">
        <div class="message"><ul><li><g:message code="${flash.message}" /></li></ul></div>
    </g:if>
    <g:if test="${!patient && !flash.error}">
      <div class="message"><ul><li><g:message code="trauma.sign.noPatientSelected" /></li></ul></div>
    </g:if>

    </div>
  </body>
</html>
