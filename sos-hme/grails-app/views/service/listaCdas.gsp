<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Sample title</title>
  </head>
  <body>
    <h1>Lista de CDAs</h1>

    <g:if test="${!result}">

  <p>No hay CDAs coincidentes</p>
</g:if>
      <g:else>
        <table border="1px">

          <tr>
          <th>Titulo</th>
          <th>Fecha Creacion</th>
        <th colspan="2">Acciones</th>


          </tr>

          <g:each in="${result}" var="cda">
	        <tr>
                  <td>${cda.titulo}</td>
                  <td>${cda.fechaCreacion}</td>


                  <td><g:link controller="service" action="buscarCdaById" id="${cda.id}" params="[render: 'cda']">Ver CDA </g:link></td>
                  <td><g:link controller="service" action="buscarCdaById" id="${cda.id}" params="[render: 'xml']">Ver XML </g:link></td>

                </tr>
	      </g:each>
        </table>
      </g:else>


  </body>
</html>
