

<%@ page import="demographic.party.Person" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${personInstance}">
            <div class="errors">
                <g:renderErrors bean="${personInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>

                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="identities"><g:message code="person.identities.label" default="Identities" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'identities', 'errors')}">
                                    <g:select name="identities" from="${demographic.identity.PersonNameUser.list()}" multiple="No" optionKey="id" size="5" value="${personInstance?.identities*.id}" /><br>
                                     <g:link controller="personNameUser" action="create" params="['person.id': personInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'role.label', default: 'PersonName')])}</g:link>
                                </td>


                            </tr>


                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fechaNacimiento"><g:message code="person.fechaNacimiento.label" default="Fecha Nacimiento" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'fechaNacimiento', 'errors')}">
                                    <g:datePicker name="fechaNacimiento" precision="day" value="${personInstance?.fechaNacimiento}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sexo"><g:message code="person.sexo.label" default="Sexo" /></label>
                                </td>
                               <!-- <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'sexo', 'errors')}">
                                    <g:textField name="sexo" value="${personInstance?.sexo}" />
                                </td>-->
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'sexo', 'errors')}">
                                    <g:select name="sexo" from="${personInstance.getSexCodes()}" multiple="No"  size="2" value="sexo" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="type"><g:message code="person.type.label" default="Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'type', 'errors')}">
                                    <g:select name="type" from="${demographic.role.Role.getRoleCodes()}" multiple="No" size="4" value="type" />
                                </td>


                            </tr>




                            
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
