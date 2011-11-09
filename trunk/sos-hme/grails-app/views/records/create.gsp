<html>
  <head>
    <meta name="layout" content="basicrecord" />
    <style>
      textarea {
        width: 600px;
        height: 160px;
      }


 .ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }
 .ui-timepicker-div dl { text-align: left; }
.ui-timepicker-div dl dt { height: 25px; }
.ui-timepicker-div dl dd { margin: -25px 10px 10px 65px; }
 .ui-timepicker-div td { font-size: 90%; }
 .ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; }
    </style>
  <r:require module="jquery-ui"/>
  <g:javascript library="jquery" />
  <jqui:resources themeCss="/sos/css/jquery/jquery-ui-1.8.16.custom.css"/>
  
  <script type="text/javascript" src="/sos/js/jquery/jquery-ui-i18n.min.js"></script>
  <script type="text/javascript" src="/sos/js/jquery/jquery-ui-timepicker-addon.js"> </script>

  <script type="text/javascript">
      $(document).ready(function()
      {
$.datepicker.setDefaults($.datepicker.regional['es']);
      
        $("#datepicker").datepicker({dateFormat: 'dd-mm-yy',
                                     changeYear: true,
                                     altField: '#actualDate',
                                     buttonText: 'Calendario',
                                     showOn: 'both',
                                     buttonImage: '/sos/images/datepicker.gif',
                                     maxDate: new Date(),
                                     minDate: new Date(2007, 9, 15),
                                     constrainInput: true,
                                     showButtonPanel: true,
                                     showOn: 'both'
                          
                          
                         });
       $('#datepicker1').datetimepicker({dateFormat: 'dd-mm-yy',
	ampm: true,
        changeYear: true,
                                     
                                     buttonText: 'Calendario',
                                     buttonImage: '/sos/images/datepicker.gif',
                                     maxDate: new Date(),
                                     minDate: new Date(2007, 9, 15),

                                     showButtonPanel: true,
                                     showOn: 'both'

});
      });
  </script>

</head>
<body>
  <h1><g:message code="trauma.create.title" /></h1>



<g:form action="create">

<%-- se crea el episodio para una persona seleccionada por admision --%>
  <g:if test="${params.root && params.extension}">
    Se crea episodio para la persona con identificador: ${params.root}::${params.extension}<br/>
    <input type="hidden" name="root" value="${params.root}" />
    <input type="hidden" name="extension" value="${params.extension}" />
  </g:if>




  <g:message code="trauma.create.label.fechaIngreso" /><br/>
<%--  <g:datePicker name="startDate" value="${((params.startDate) ? new Date(params.startDate) : null)}" />
  <g:formatDate type='date' style='FULL'/>
  <g:formatDate format="hh:mm aa"/>
  <br/><br/>
--%>
<div>
    <p> <input name="startDate" type="text" id="datepicker"> </p>
    <p> <input name="startDate1" type="text" id="datepicker1"> </p>

</div>
  <g:message code="trauma.create.label.observaciones" /><br/>
  <textarea name="otherContext">${params.otherContext}</textarea>
  <br/><br/>

  <div id="bottom_actions">
    <g:submitButton name="doit" value="${message(code:'trauma.create.action')}" />
  </div>
  
</g:form>
</body>
</html>