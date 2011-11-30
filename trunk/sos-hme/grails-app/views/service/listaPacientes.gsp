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
    <h1>Lista de Pacientes</h1>

<g:if test="${!result}">

  <p>No hay pacientes coincidentes</p>
</g:if>
      <g:else>
        <table border="1px">

          <tr>
          <th>Cedula</th>
          <th>Pasaporte</th>

          <th>Primer Nombre</th>
          <th>Segundo Nombre</th>

          <th>Primer Apellido</th>
          <th>Segundo Apellido</th>

          <th>Acciones</th>

          </tr>

          <g:each in="${result}" var="paciente">
	        <tr>
                  <td>${paciente.cedula}</td>
                  <td>${paciente.pasaporte}</td>

                  <td>${paciente.primerNombre}</td>
                  <td>${paciente.segundoNombre}</td>

                  <td>${paciente.primerApellido}</td>
                  <td>${paciente.segundoNombre}</td>


	        <td><a href="#">Seleccionar</a></td>

                </tr>
	      </g:each>
        </table>
      </g:else>

  

  </body>
</html>
