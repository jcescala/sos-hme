
<%@ page import="imp.Organizacion" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'organizacion.label', default: 'Organizacion')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'organizacion.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="login" title="${message(code: 'organizacion.login.label', default: 'Login')}" />
                        
                            <g:sortableColumn property="nombre" title="${message(code: 'organizacion.nombre.label', default: 'Nombre')}" />
                        
                            <g:sortableColumn property="password" title="${message(code: 'organizacion.password.label', default: 'Password')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${organizacionInstanceList}" status="i" var="organizacionInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${organizacionInstance.id}">${fieldValue(bean: organizacionInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: organizacionInstance, field: "login")}</td>
                        
                            <td>${fieldValue(bean: organizacionInstance, field: "nombre")}</td>
                        
                            <td>${fieldValue(bean: organizacionInstance, field: "password")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${organizacionInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
