<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
     <meta name="layout" content="ehr-modal" />
       <g:javascript library="jquery" />
    <title>Sample title</title>
  </head>
  <body>

    <ul class="top_actions">
      <li>
      <g:link controller="demographic" action="seleccionarPaciente" params="[id: pacienteId ]" class="back">Atras</g:link>
      </li>
      
    </ul>

    <h1>Lista de CDAs</h1>

     <div id="resultadoExterno" >


    <g:render template="../demographic/registroExterno" model="['externo':result, 'total': total, llamar: llamar]" />

    
      </div>
      <div id="errorResultadoExterno" >
      </div>
  </body>
</html>
