<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><g:layoutTitle default="grails" /></title>
    <link rel="stylesheet" href="${resource(dir:'css',file:'estilos.css')}" />
    <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
    <g:layoutHead />
    <g:javascript library="application" />
  </head>
  <body class="bodybasic">
    <div id="headerbasic" class="headerbasic">
      <div id="logo" class="logobasic"></div>
      <div id="ucvbasic" class="ucvbasic"></div>
      <div id="sessionbasic" class="sessionbasic">
        <g:datosUsuario userId="${session.traumaContext.userId}" /> | <g:link controller="authorization" action="logout"><g:message code="authorization.action.logout" /></g:link>
      </div>
    </div>
    <div id="barrabasic" class="barrabasic"></div>
    
    <div id="contenidobasic" class="basicregistro">
      
      <g:layoutBody />
    </div>
    
    <div id="footerbasic" class="footercontenido">
      Centro de An&aacute;lisis de Im&aacute;genes Biom&eacute;dicas Computarizadas CAIBCO - Insituto de Medicina Tropical </br>
      Facultad de Medicina. Universiad Central de Venezuela </br>
      Tel&eacute;fonos: (0212) 605.37.46 / 35.94 </br>
      sostelemedicina&#64;ucv.ve
    </div>
  </body>
</html>
