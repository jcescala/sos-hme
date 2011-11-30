

<%@ page import="imp.Paciente" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'paciente.label', default: 'Paciente')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${pacienteInstance}">
            <div class="errors">
                <g:renderErrors bean="${pacienteInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${pacienteInstance?.id}" />
                <g:hiddenField name="version" value="${pacienteInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="idPaciente"><g:message code="paciente.idPaciente.label" default="Id Paciente" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pacienteInstance, field: 'idPaciente', 'errors')}">
                                    <g:textField name="idPaciente" value="${pacienteInstance?.idPaciente}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="cedula"><g:message code="paciente.cedula.label" default="Cedula" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pacienteInstance, field: 'cedula', 'errors')}">
                                    <g:textField name="cedula" value="${pacienteInstance?.cedula}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pasaporte"><g:message code="paciente.pasaporte.label" default="Pasaporte" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pacienteInstance, field: 'pasaporte', 'errors')}">
                                    <g:textField name="pasaporte" value="${pacienteInstance?.pasaporte}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="primerNombre"><g:message code="paciente.primerNombre.label" default="Primer Nombre" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pacienteInstance, field: 'primerNombre', 'errors')}">
                                    <g:textField name="primerNombre" value="${pacienteInstance?.primerNombre}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="segundoNombre"><g:message code="paciente.segundoNombre.label" default="Segundo Nombre" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pacienteInstance, field: 'segundoNombre', 'errors')}">
                                    <g:textField name="segundoNombre" value="${pacienteInstance?.segundoNombre}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="primerApellido"><g:message code="paciente.primerApellido.label" default="Primer Apellido" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pacienteInstance, field: 'primerApellido', 'errors')}">
                                    <g:textField name="primerApellido" value="${pacienteInstance?.primerApellido}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="segundoApellido"><g:message code="paciente.segundoApellido.label" default="Segundo Apellido" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pacienteInstance, field: 'segundoApellido', 'errors')}">
                                    <g:textField name="segundoApellido" value="${pacienteInstance?.segundoApellido}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="cdas"><g:message code="paciente.cdas.label" default="Cdas" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pacienteInstance, field: 'cdas', 'errors')}">
                                    
<ul>
<g:each in="${pacienteInstance?.cdas?}" var="c">
    <li><g:link controller="cda" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="cda" action="create" params="['paciente.id': pacienteInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'cda.label', default: 'Cda')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="indice"><g:message code="paciente.indice.label" default="Indice" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pacienteInstance, field: 'indice', 'errors')}">
                                    <g:select name="indice.id" from="${imp.IndicePaciente.list()}" optionKey="id" value="${pacienteInstance?.indice?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
