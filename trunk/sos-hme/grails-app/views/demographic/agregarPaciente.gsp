<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="basicregistro" />
    <title><g:message code="demographic.agregar_paciente.title" /></title>
    <g:javascript library="prototype" />
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.2.6/jquery.min.js"></script>
	<g:javascript src='formToWizard.js' />
        <g:javascript src='funciones.js' />
        <g:javascript src='jquery.validate.js' />
        
        <link rel="stylesheet" href="${resource(dir:'css',file:'wizard.css')}" />
    <style>
      label {
        display: block;
      }
    </style>
    <g:javascript>
          
         function updateSubCats( category ){
            ${remoteFunction( 
              controller:'demographic', 
              action:'ajaxGetEstados', 
              update:'entidadnace', 
              params:'\'id=\' + category')} 
          }
          
          function updateMunicipios(estado){
            ${remoteFunction( 
                controller:'demographic', 
                action:'ajaxGetMunicipios', 
                update:'municnace', 
                params:'\'id=\' + estado')}
          }
          function updateMunicipiosReside(estado){
            ${remoteFunction( 
                controller:'demographic', 
                action:'ajaxGetMunicipios', 
                update:'municresid', 
                params:'\'id=\' + estado')}
          }
          
          function updateParroquiaReside(municipio){
            ${remoteFunction( 
                controller:'demographic', 
                action:'ajaxGetMunicipios', 
                update:'parroresid', 
                params:'\'id=\' + municipio')}
          }
      </g:javascript>
	  <style type="text/css">
        
    </style>
	<script type="text/javascript">
	var $j = jQuery.noConflict();
        $j(document).ready(function(){
            $j("#nuevopaciente").formToWizard({ submitButton: 'doit' })
        });
    </script>
  </head>
  
  <body>
    <div id="main">
   
    <g:if test="${flash.message}">
      <div style="color:red;">
        <g:message code="${flash.message}" />
      </div>
    </g:if>
    <g:form action="agregarPaciente" name="nuevopaciente" enctype="multipart/form-data">
      <fieldset>
        <legend>Identificaci&oacute;n</legend>
          <label for="primerApellido"> <g:message code="persona.primerApellido" /></label>
		  <g:textField name="primerApellido" value="${params.primerApellido}"/>
                  
          <label for="segundoApellido"> <g:message code="persona.segundoApellido" /></label>
          <g:textField name="segundoApellido" value="${params.segundoApellido}" />
		  
          <label for="primerNombre"><g:message code="persona.primerNombre" /></label>
          <g:textField name="primerNombre" value="${params.primerNombre}" />
        
          <label for="segundoNombre"><g:message code="persona.segundoNombre" /></label>
          <g:textField name="segundoNombre" value="${params.segundoNombre}" />
		  
          <label for="identificador"><g:message code="persona.identificador" /></label>
          <g:textField name="extension" value="${params.identificador}" />
          <g:select name="root" class="selectci" from="${tiposIds}" optionKey="codigo" optionValue="nombreCorto" />
		  
          <label for="fechaNacimiento"><g:message code="persona.fechaNacimiento" /></label>
          <g:datePicker name="fechaNacimiento" value="none" precision="day" noSelection="['':'']"/>
        
          <label for="sexo"><g:message code="persona.sexo" /></label>
            <g:select name="sexo" class="selectsex" noSelection="['-1':'Seleccione']" from="['Masculino', 'Femenino']" value="${params.sexo}" />
          
            <label for="foto"><g:message code="persona.foto" /></label>
            <!--label for="foto">Foto del Paciente</label-->
            <input type="file" name="foto" id="foto"/>  
      </fieldset>
	  
      <fieldset>
        <legend>Procedencia</legend>
          <label for="etnia"><g:message code="persona.etnia" /></label>
          <g:select name="etnia.id" class="selectci" value="${params.etnia}" from="${etniasIds}" optionKey="id" optionValue="nombre" noSelection="['-1':'Seleccione Etnia']"/>
          
		  <label for="nacionalidad"><g:message code="persona.nacionalidad" /></label>
          <g:select name="nacionalidad.id" class="selectci" from="${paisesIds}" optionKey="id" optionValue="nombre" />
		  
          <label for="paisnacimiento"><g:message code="persona.paisnace"/></label>
		  <g:select name="paisnace" class="selectci" from="${paisesIds}" optionKey="id" optionValue="nombre" noSelection="['-1':'Seleccione País']" onchange="updateSubCats(this.value)" />
		  
          <label for="entidadnacimiento"><g:message code="persona.entidadnace"/></label>
		  <g:select name="entidadnace" class="selectci" disabled="false" noSelection="['-1':'Seleccione Entidad']" onchange="updateMunicipios(this.value)"/>
		  
          <label for="municipionacimiento">
            <g:message code="persona.municipionace"/></br>
            <g:select name="lugar.id" id="municnace" class="selectci" value="${params.lugarnacimiento}" disabled="false" noSelection="['-1':'Seleccione Municipio']"/>
          </label>
		  
          <label for="ciudadnacimiento"><g:message code="persona.ciudadnace"/></label>
		  <g:textField name="ciudadnacimiento" value="${params.ciudadnacimiento}" />
                  </br>
      </fieldset>
      
      
      <fieldset>
        <legend>Datos Personales</legend>
          <label for="situacionconyugal"><g:message code="persona.situacionConyugal"/></label>
          <table style="margin-left:10px; font-size: 13px;">
            <g:each var="conyugal" in="${conyugalIds}">
              <g:if test="${conyugal.id == 1}">
                <tr>
                  <td>${conyugal.nombre}</td><td> <g:radio name="situacionconyugal" value="${conyugal.id}" checked="true"/></td>
                </tr>
              </g:if>
              <g:else>
                <tr>
                  <td>${conyugal.nombre}</td><td> <g:radio name="situacionconyugal" value="${conyugal.id}"/></td>
                </tr>
              </g:else>
            </g:each>
          </table>
          <label for="analfabeta"><g:message code="persona.analfabeta"/> &nbsp;&nbsp;</label>
            Si <g:radio name="analfabeta" value="1" optionValue="Si"/>
            No <g:radio name="analfabeta" value="0" checked="true"/>
			
          <label for="niveleducativo"><g:message code="persona.niveleducativo"/></label>
          <table style="margin-left:10px; font-size: 13px;">
              <g:each var="niveledu" in="${nivelEducIds}">
              <g:if test="${niveledu.id == 1}">
                <tr>
                  <td>${niveledu.nombre}</td><td> <g:radio name="niveleducativo" value="${niveledu.id}" checked="true"/></td>
                </tr>
              </g:if>
              <g:else>
                <tr>
                  <td>${niveledu.nombre}</td> <td><g:radio name="niveleducativo" value="${niveledu.id}" /></td>
                </tr>
              </g:else>
            </g:each>
          </table>		
          <label for="anosaprobados"><g:message code="persona.anosaprobados"/></label>
		  <g:textField name="anosaprobados" value="${params.anosaprobados}" />
		  
          <label for="ocupacion"><g:message code="persona.ocupacion"/></label>
		  <g:select name="ocupacion.id" id="ocupacion" class="selectci" from="${ocupacionIds}" optionKey="id" optionValue="nombre" noSelection="['-1':'Seleccione Ocupación']" />  
      </fieldset>
      
      <fieldset>
        <legend>Direcci&oacute;n de Habitaci&oacute;n</legend>
          <label for="entidadresidencia"><g:message code="persona.entidadreside"/></label>
		  <g:select name="entidresid" class="selectci" from="${entidadesIds}" optionKey="id" optionValue="nombre" noSelection="['':'Seleccione Entidad']" onchange="updateMunicipiosReside(this.value)" />
		  
          <label for="municipioresidencia"><g:message code="persona.municipioreside"/></label>
		  <g:select name="municresid" class="selectci" disabled="false" noSelection="['':'- Seleccione Municipio']" onchange="updateParroquiaReside(this.value)"/>
		  
          <label for="parroquiresidencia"><g:message code="persona.parroquiareside"/></label>
		  <g:select name="direccion.id" class="selectci" id="parroresid" disabled="false" noSelection="['-1':'Seleccione Parroquia']"/>
		  
          <label for="localidadreside"><g:message code="persona.localidadreside"/></label>
		  <g:textField name="ciudad" value="${params.ciudad}" />
        
          <label for="urbreside"><g:message code="persona.urbreside"/></label>
		  <g:textField name="urbasector" value="${params.urbasector}" />
        
          <label for="avenireside"><g:message code="persona.avenireside"/></label>
		  <g:textField name="avenidacalle" value="${params.avenidacalle}" />
        
          <label for="casareside"><g:message code="persona.casareside"/></label>
		  <g:textField name="casaedif" value="${params.casaedif}" />
        
          <label for="pisoreside"><g:message code="persona.pisoreside"/></label>
		  <g:textField name="pisoplanta" value="${params.pisoplanta}" />
		  
          <label for="ptoreferencia"><g:message code="persona.puntoreferencia"/></label>
		  <g:textField name="ptoreferenica" value="${params.ptoreferenica}" />
          
          <label for="tiemporesidencia"><g:message code="persona.tiemporesidencia"/></label> 
		  <g:textField name="tiemporesidencia" value="${params.tiemporesidencia}" />
        </fieldset>
		
        <fieldset>
              <legend>Contacto</legend>
              <label for="telfhab"><g:message code="persona.telfhabitacion"/></label>
			  <g:textField name="telfhabitacion" value="${params.telfhabitacion}" />
			  
              <label for="telfcel"><g:message code="persona.telfcelular"/></label>
			  <g:textField name="telfcelular" value="${params.telfcelular}" />
			  
          <label for="email"><g:message code="persona.email"/></label>
		  <g:textField name="email" value="${params.email}" />
        </fieldset>
        
      
      <fieldset>
        <legend>Datos Familiariares</legend>
          <label for="nombremadre"><g:message code="persona.nombremadre"/></label>
		  <g:textField name="nombremadre" value="${params.nombremadre}" />
		  
          <label for="nombrepadre"><g:message code="persona.nombrepadre"/></label>
		  <g:textField name="nombrepadre" value="${params.nombrepadre}" />
		  
          <label for="otradireccion"><g:message code="persona.otradireccion"/></label>
		  <g:textArea name="otradireccion" class="inputtextfield" value="${params.otradireccion}" rows="1" cols="20"/>
		  
		  <label for="contactoemergencia"><g:message code="persona.contactoemergencia"/></label>
		  <g:textArea name="contactoemergencia" class="inputtextfield" value="${params.contactoemergencia}" rows="1" cols="20"/>
	  </fieldset>
        <p>
          <g:submitButton name="doit" value="${message(code:'demographic.agregar_paciente.agregar')}" />
          <g:link action="admisionPaciente"><g:message code="demographic.lista_candidatos.action.admisionPaciente" /></g:link>
        </p>    
      
    </g:form>
    </div>

  </body>
</html>