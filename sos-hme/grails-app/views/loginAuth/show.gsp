
<%@ page import="authorization.LoginAuth" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'loginAuth.label', default: 'LoginAuth')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>

        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <%--<tr class="prop">
                            <td valign="top" class="name"><g:message code="loginAuth.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: loginAuthInstance, field: "id")}</td>
                            
                        </tr>--%>
                    
                       <%-- <tr class="prop">
                            <td valign="top" class="name"><g:message code="loginAuth.purpose.label" default="Purpose" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: loginAuthInstance, field: "purpose")}</td>
                            
                        </tr>--%>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="loginAuth.user.label" default="Usuario" />:</td>

                            <td valign="top" class="value">${fieldValue(bean: loginAuthInstance, field: "user")}</td>

                        </tr>
                        <%--
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="loginAuth.pass.label" default="Clave" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: loginAuthInstance, field: "pass")}</td>
                            
                        </tr>
                    --%>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="loginAuth.person.label" default="Person" />:</td>
                            
                            <td valign="top" class="value"><g:link controller="person" action="show" id="${loginAuthInstance?.person?.id}">${loginAuthInstance?.person?.identities?.toString()}</g:link></td>
                            
                        </tr>
                    

                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
					<span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
                    <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
                    <g:hiddenField name="id" value="${loginAuthInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
