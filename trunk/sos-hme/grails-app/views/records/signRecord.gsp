<?xml version="1.0" encoding="ISO-8859-1" ?>
<html>
  <head>
    <meta name="layout" content="ehr" />
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <title><g:message code="trauma.sign.title" /></title>
    <g:javascript library="prototype/prototype" />
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
      <div class="error">
        <ul><li><g:message code="${flash.error}" /></li></ul></div>
    </g:if>
    <g:if test="${flash.message}">
        <div class="message"><ul><li><g:message code="${flash.message}" /></li></ul></div>
    </g:if>
    <g:if test="${!patient && !flash.error}">
      <div class="message"><ul><li><g:message code="trauma.sign.noPatientSelected" /></li></ul></div>
    </g:if>
    <%--  
    <g:form url="[action:'signRecord', id:params.id]" method="post" id="form1" class="ehrform">
      
      <table id="sign_table" align="center">
        <g:isSignedRecord episodeId="${episodeId}">
          <tr>
            <td colspan="2" class="message"><g:message code="trauma.sign.registryAlreadySigned" /></td>
          </tr>
        </g:isSignedRecord>
        <tr>
          <th><g:message code="auth.login.label.userid" /></th>
          <td><input type="text" id="user" name="user" size="24" /></td>
        </tr>
        <tr>
          <th><g:message code="auth.login.label.password" /></th>
          <td><input type="password" name="pass" size="24" /></td>
        </tr>
      </table>
      
      <g:isNotSignedRecord episodeId="${episodeId}">
        <br/>
       
        <input type="submit" name="doit" value="${message(code:'trauma.sign.action.sign')}" class="boton1" />
       
      </g:isNotSignedRecord>
      

      
    </g:form>--%>
    </div>
  </body>
</html>
