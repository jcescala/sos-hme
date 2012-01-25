<%@ page import="com.thoughtworks.xstream.XStream" %>
<%@ page import="hce.core.data_types.text.DvCodedText" %>
<%@ page import="tablasMaestras.Cie10Trauma" %>
<?xml version="1.0" encoding="ISO-8859-1" ?>
<html>
  <head>
    <meta name="layout" content="ehr" />
    
   <script type="text/javascript">
    
      var codigos;
      
     

    $('#form_diagnosticos').ajaxForm({
        // dataType identifies the expected content type of the server response
        dataType:  'json',

        // success identifies the function to invoke when the server response
        // has been received
        success:   _after
    });
    

      function select( id )
      {
        alert('select id:'+id);
      
        var code = null;
        
        // codigos es global
        $.each(codigos, function(c) {
          if (this.id == id) code = this;
        });
        
        if (code)
        {
          //alert(code.id + ' ' + code.nombre);
          
          // TODO: esto deberia ser un form con campos hidden con los
          //       ids de los codigos seleccionados para diagnosticos.
          //
          $('#seleccionados').append('<div id="selected_'+code.id+'">' +
                                          '<input type="hidden" name="codes" value="'+ code.id +'" />'+
                                          '('+ ((code.codigo) ? code.codigo : code.subgrupo) + ') ' + code.nombre + // si no es un codigo, para que no muestre null
                                          ' <a href="javascript:unselect(\'' + code.id + '\');">[borrar]</a> ' +
                                          '</div>');
        }
        else
          alert('code es null');
      }
      
      
      function unselect( id )
      {
         $('#selected_'+id).remove();
      }
      
    
      function _after(response)
      {
      
        var json = response;
        //codigos es una variable global
        codigos = json.codigos;
        var odd = 0;
        var html = '';
        
        // Si no se encuentran codigos para el texto ingresado.
        if (codigos.length == 0)
        {
           html += '<g:message code="section.DIAGNOSTICO-diagnosticos.label.emptySearchResult" />';
        }
        else
        {
          html = '<table cellpadding="3" cellspacing="1">';
          
          $.each(response.codigos ,function(index) {
           
            if (!this.codigo)
            {
              html += '<tr class="group">';
            }
            else
            {
              html += '<tr'+ ((odd)?' class="odd"':' class="even"') +'>';
            }
            
            html += '<td>' + this.subgrupo + '</td>';
            html += '<td>' + ((this.codigo) ? this.codigo : '') + '</td>'; // si no muestra 'null'
            html += '<td class="name">' + this.nombre + '</td>';
            html += '<td class="select_code">';
            html += '<a href="javascript:select(\'' + this.id + '\');">[seleccionar]</a>';
            html += '</td>';
            
            html += '</tr>';
            
            odd = (odd+1)%2;
            
         });
         
         html += '</table>'
          
        } // si hay algun resultado

        $('#result').html(html);
     
  }
    </script>
  

    <style>
      #result table {
         border: 1px solid #000000;
      }
      td .select_code {
        text-align: center;
      }
      .group {
        font-weight: bold;
        background-color: #ccccff;
      }
      .odd {
        background-color: #efefef;
      }
      .even {
        background-color: #dfdfdf;
      }
      #seleccionados {
        border: 1px solid #3366ff;
        background-color: #99ccff;
        padding: 10px;
      }
      .highlight {
        background-color: #ffff80;
      }
      textarea {
        width: 100%;
        height: 75px;
      }
      .name {
        font-family: tahoma;
        font-size: 11px;
      }
    </style>
   </head>
  <body>
    <h1>Diagnosticos</h1>
  
    <%--
    <g:if test="${rmNode}">
      ${rmNode.data.events[0].data.items}
      <textarea style="width: 800px; height: 400px;">${new XStream().toXML(rmNode.data.events[0].data.errors)}</textarea>
    </g:if>
    --%>
    <g:if test="${rmNode && rmNode.data.events[0].data.errors.hasErrors()}">
      <div class="error">
        <g:renderErrors bean="${rmNode.data.events[0].data}" as="list" />
      </div>
    </g:if>
  
    <%-- update="[success:'message',failure:'error']" --%>
    <%-- onSuccess="_after(codigos)" --%>
    <div class="ehrform1">
      <g:form url="[controller:'ajaxApi', action:'findCIE10']"
                    name="form_diagnosticos"
                    id="form_diagnosticos">
                    
        <input type="text" name="text" />
        <input id="buscarDiagnostico" type="submit" value="Buscar" />
        
        p.e: 'traumatismo cuello', 'quemadura cabeza', 'esguince tobillo', ...
        
      </g:form >
      
      <div id="message"></div>
      <div id="error"></div>
      
      <h3><g:message code="section.DIAGNOSTICO-diagnosticos.label.diagnosesSearchResult" /></h3>
      <div id="result"></div><br/>
      
      <g:form class="ehrform" controller="ajaxApi" action="saveDiagnostico">
        
        <input type="hidden" name="mode" value="${mode}" />
        
        <h3><g:message code="section.DIAGNOSTICO-diagnosticos.label.selectedDiagnoses" /></h3>
        <div id="seleccionados">
          <g:if test="${mode=='edit'}">
            <%--
            ${rmNode.class}<br/>
            ${rmNode.data.class}<br/>
            ${rmNode.data.events.class}<br/>
            ${rmNode.data.events.data.class}<br/>
            ${rmNode.data.events.data.items.class}<br/>
            --%>
            <g:each in="${rmNode.data.events[0].data.items}" var="element">
              <%--x: ${element.value}<br/>--%>
              <g:if test="${element.value.getClassName() == 'DvCodedText'}">
                <%--
                ${element.value.value}
                ${element.value.definingCode.codeString}<br/>
                --%>
                <g:set var="code" value="${Cie10Trauma.findByCodigo(element.value.definingCode.codeString)}" />
                <g:if test="${!code}">
                  <g:set var="code" value="${Cie10Trauma.findBySubgrupo(element.value.definingCode.codeString)}" />
                </g:if>
                
                <div id="selected_${code.id}">
                  <input type="hidden" name="codes" value="${code.id}" />
                  ( ${( (code.codigo) ? code.codigo : code.subgrupo )} ) ${code.nombre}
                  <a href="javascript:unselect('${code.id}');">[borrar]</a>
                </div>
                <hr/>
                
              </g:if>
              <g:else>
                <!-- DvText es la description -->
                <g:set var="descripcion" value="${element.value.value}" />
              </g:else>
            </g:each>
          </g:if>
        </div>
        
        <h3><g:message code="section.DIAGNOSTICO-diagnosticos.label.description" /></h3>
        <textarea name="descripcion">${descripcion}</textarea><br/><br/>
        
        <div class="bottom_actions">
          <g:submitButton name="doit" value="${message(code:'section.DIAGNOSTICO-diagnosticos.action.save')}" />
        </div>

      </g:form>
    </div>
  </body>
</html>