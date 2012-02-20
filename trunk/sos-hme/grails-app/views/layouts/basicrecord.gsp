<html>
  <g:set var="startmsec" value="${System.currentTimeMillis()}"/>
  <head>
    <META Http-Equiv="Cache-Control" Content="no-cache">
    <META Http-Equiv="Pragma" Content="no-cache">
    <META Http-Equiv="Expires" Content="0"> 
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><g:layoutTitle default="grails" /></title>
    <link rel="stylesheet" href="${resource(dir:'css',file:'estilos.css')}" />
    <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
    <g:layoutHead />
    <g:javascript library="jquery" />
    <jqui:resources themeCss="${createLinkTo(dir:'css/jquery' ,file:'jquery-ui-1.8.16.custom.css')}"/>
    <script type="text/javascript" src="${createLinkTo(dir:'js/jquery' ,file:'jquery.form.js')}"></script>
    <script type="text/javascript" src="${createLinkTo(dir:'js/jquery' ,file:'jquery-ui-i18n.min.js')}"></script>
    <script type="text/javascript" src="${createLinkTo(dir:'js/jquery' ,file:'jquery-ui-timepicker-addon.js')}"> </script>



     <script type="text/javascript">

 $(document).ready(function(){
    
    $.datepicker.setDefaults($.datepicker.regional['${session.'org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE'}']);

        $(".DateSos").datepicker({dateFormat: 'dd-mm-yy',

                                     changeYear: true,
                                     //altField: '#actualDate',
                                     buttonText: 'Calendario',
                                     buttonImage: '/sos/images/datepicker.gif',
                                     maxDate: new Date(),
                                     minDate: new Date(1900, 9, 15),
                                     yearRange: '1900:2100',
                                     constrainInput: true,
                                     showButtonPanel: true,
                                     showOn: 'button'



        });
        $(".DateSos").attr("readonly",true);
        $(".DateSos").click(function(){
            $(this).val('');
        });

});


  
  </script>



    </head>
  <body class="bodybasic">
    <div id="headerbasic" class="headerbasic">
      <div id="logo" class="logobasic"></div>
      <div id="ucvbasic" class="ucvbasic"></div>
      <div id="sessionbasic" class="sessionbasic">
        <g:datosUsuario userId="${session.traumaContext.userId}" /> | <g:link controller="authorization" action="logout"><g:message code="authorization.action.logout" /></g:link>
      </div>
    </div>
    <div id="menubar" class="menubar">
        <ul>
          <!--li><a href="../domain/list">Dominios</a></li-->
          <li><g:link controller="domain" action="list">Dominios</g:link></li>
          <li><g:link controller="records" action="index">Registros</g:link></li>
          <li><g:link controller="demographic" action="admisionPaciente">Admisión</g:link></li>
        <li><g:link controller="reportes" action="index" class="find"><g:message code="reportes.link.title" /></g:link></li>
        </ul>
      </div>
    <div id="barrabasic" class="barrabasic"></div>
    
    <div id="contenidobasic" class="contenidobasic">
      <g:layoutBody />
    </div>
   <%--
    <div id="footerbasic" class="footercontenido">
      Centro de An&aacute;lisis de Im&aacute;genes Biom&eacute;dicas Computarizadas CAIBCO - Insituto de Medicina Tropical </br>
      Facultad de Medicina. Universiad Central de Venezuela </br>
      Tel&eacute;fonos: (0212) 605.37.46 / 35.94 </br>
      sostelemedicina&#64;ucv.ve
    </div>--%>
  </body>
</html>
