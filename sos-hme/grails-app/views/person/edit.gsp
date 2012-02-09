

<%@ page import="demographic.party.Person" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
      

		

        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${personInstance}">
            <div class="errors">
                <g:renderErrors bean="${personInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${personInstance?.id}" />
                <g:hiddenField name="version" value="${personInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>

                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="identities"><g:message code="person.identities.label" default="Identities" />:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'identities', 'errors')}">
                                    <g:select name="identities" from="${demographic.identity.PersonNameUser.list()}" optionKey="id" size="5" value="${personInstance?.identities*.id}" /><br>
                                     <g:link controller="personNameUser" action="create" params="['person.id': personInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'role.label', default: 'PersonName')])}</g:link>
                                </td>
                               

                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fechaNacimiento"><g:message code="person.fechaNacimiento.label" default="Fecha Nacimiento" />:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'fechaNacimiento', 'errors')}">
                                    <g:datePicker name="fechaNacimiento" precision="day" value="${personInstance?.fechaNacimiento}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="sexo"><g:message code="person.sexo.label" default="Sexo" />:</label>
                                </td>
                               <%-- <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'sexo', 'errors')}">
                                    <g:select name="sexo" value="${personInstance?.sexo}" />
                                </td>--%>

                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'sexo', 'errors')}">
                                    <g:select name="sexo" from="${personInstance.getSexCodes()}" multiple="No"  size="2" value="${personInstance?.sexo}" />
                                </td>

                            </tr>
							<%--
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="contacts"><g:message code="person.contacts.label" default="Contacts" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'contacts', 'errors')}">
                                    <g:select name="contacts" from="${demographic.contact.Contact.list()}" multiple="No" optionKey="id" size="5" value="${personInstance?.contacts*.id}" />
                                </td>
                            </tr>--%>
							
							<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="identificador"><g:message code="persona.ids.label" />:</label>
                                </td>
								<td valign="top" style="text-align: left;" class="value">
									<ul>
									<g:each in="${personInstance.ids}" var="r2">
										<li>${r2?.value.split('::')[0]} : <g:link controller="role" action="show" id="${r2.id}">${r2?.value.split('::')[1]}</g:link></li>
									</g:each>
									</ul>
								</td>
							</tr>
							
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="identificador"><g:message code="persona.id.label" /></label>:
                                </td>
                                <td valign="top" class="name">
									  <g:textField name="extension" value="${params.identificador}" />
									  <g:select name="root"  from="${tiposIds}" optionKey="codigo" optionValue="nombreCorto" />
                                </td>


                            </tr>
							
                         <!--   <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="relationships"><g:message code="person.relationships.label" default="Relationships" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'relationships', 'errors')}">
                                    
<ul>
<g:each in="${personInstance?.relationships?}" var="r">
    <li><g:link controller="partyRelationship" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="partyRelationship" action="create" params="['person.id': personInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'partyRelationship.label', default: 'PartyRelationship')])}</g:link>

                                </td>
                            </tr>-->
                       
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="roles"><g:message code="person.roles.label" default="Roles" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'roles', 'errors')}">
                                    
									<ul>
										<g:each in="${personInstance.roles}" var="r">
											<li><g:link controller="role" action="show" id="${r.id}">${r?.type}</g:link></li>
										</g:each>
									</ul><br>
									<g:link controller="role" action="create" params="['person.id': personInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'role.label', default: 'Role')])}</g:link>

                                </td>
                            </tr>

                            

                           <!-- <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="roles"><g:message code="person.roles.label" default="Roles" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'roles', 'errors')}">
                                    <g:select name="roles" from="${demographic.role.Role.list().id}" multiple="yes" size="4" value="${personInstance?.roles*.id}" />
                                </td>
                            </tr>-->
                        
                        <!--    <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="type"><g:message code="person.type.label" default="Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: personInstance, field: 'type', 'errors')}">
                                    <g:textField name="type" value="${personInstance?.type}" />
                                </td>
                            </tr>-->
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
					<span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
					<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
