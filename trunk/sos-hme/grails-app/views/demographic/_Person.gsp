<%@ page import="util.DateDifference" %><%@ page import="java.text.SimpleDateFormat" %>

<%-- in: person:Person --%>


<%
def nombres = person.identities.find{ it.purpose == 'PersonNamePatient' }
%>

<p>${nombres.primerNombre}
${nombres.segundoNombre}
${nombres.primerApellido}
${nombres.segundoApellido}

  ( ${person.sexo} )
</p>
<%--
                ${person.fechaNacimiento.getClass()}
              --%>


<p><g:render template="../demographic/UIDBasedID" collection="${person.ids}" var="id" />




              <%
              if (person.fechaNacimiento)
              {
                def myFormatter = new SimpleDateFormat( "yyyy-MM-dd" )
                print "Nac "+ myFormatter.format(person.fechaNacimiento)
                
                //print " ( " + DateDifference.numberOfYears(paciente.fechaNacimiento, new Date()) + " )"
              }
              %>

</p>