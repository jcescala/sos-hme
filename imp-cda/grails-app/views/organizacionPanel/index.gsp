<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->



<%@ page import="imp.Organizacion" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
       
        <title>Panel de Organnizacion</title>
    </head>
  <body>
    <h1>Datos de la organizacion</h1>

    <p>Identificador UUID: ${organizacion.uniqueIdentifier}<p>
    <p>Nombre: ${organizacion.nombre}<p>
    <p>Usuario:  ${organizacion.user.username}</p>
    <p> Número de documentos CDA´s compartidos:

        <p>Autora: ${cdas.autora}</p>
        <p>Custodia: ${cdas.custodia}</p>
        <p>Atentifica: ${cdas.autentifica}</p>
    </p>

  <div class="body">
    <div class="list">
                <table>
                    <thead>
                        <tr>

                            <g:sortableColumn property="id" title="${message(code: 'organizacion.id.label', default: 'Nro')}" />

                            <g:sortableColumn property="nombre" title="${message(code: 'organizacion.nombre.label', default: 'Nombre')}" />

                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${organizacionInstanceList}" status="i" var="organizacionInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                            <td><g:link action="show" id="${organizacionInstance.id}">${fieldValue(bean: organizacionInstance, field: "id")}</g:link></td>

                            <td>${fieldValue(bean: organizacionInstance, field: "nombre")}</td>

                           

                        </tr>
                    </g:each>
                    </tbody>
                </table>
  </div>
  <div class="paginateButtons">
                <g:paginate total="${organizacionInstanceTotal}" />
            </div>

</div>
  <g:link controller="#" action="#">Descargar clave pública</g:link>



    <br />
            <g:link controller="logout" action="index"><g:message code="default.session.sing.in"/></g:link>
       
  </body>
</html>
