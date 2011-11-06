<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="layout" content="main" />
    <title><g:message code="demographic.agregar_paciente.title" /></title>
    <g:javascript library="prototype" />    
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
  </head>
  <style>
        span.reference{
            position:fixed;
            left:5px;
            top:5px;
            font-size:10px;
            text-shadow:1px 1px 1px #fff;
        }
        span.reference a{
            color:#555;
            text-decoration:none;
			text-transform:uppercase;
        }
        span.reference a:hover{
            color:#000;
            
        }
        h1{
            color:#ccc;
            font-size:36px;
            text-shadow:1px 1px 1px #fff;
            padding:20px;
        }
    </style>
  <body>
    <div id="contentslide">
    <div id="wrapper">
    <div id="steps">
    <!--h1><g:message code="demographic.agregar_paciente.title" /></h1-->
    
    <g:if test="${flash.message}">
      <div style="color:red;">
        <g:message code="${flash.message}" />
      </div>
    </g:if>
    <g:form action="agregarPaciente" name="nuevopaciente">
      <fieldset class="step">
        <legend>Identificaci&oacute;n</legend>
        <p>
        
          <label for="primerApellido">
            <g:message code="persona.primerApellido" />
          </label>
        
          <g:textField name="primerApellido" value="${params.primerApellido}" />
        
        
          <label for="segundoApellido">
            <g:message code="persona.segundoApellido" />
          </label>
        
          <g:textField name="segundoApellido" value="${params.segundoApellido}" />
        
        </p>
        <p>
          <label for="primerNombre">
            <g:message code="persona.primerNombre" />
          </label>
          <g:textField name="primerNombre" value="${params.primerNombre}" />
        
          <label for="segundoNombre">
            <g:message code="persona.segundoNombre" />
          </label>
          <g:textField name="segundoNombre" value="${params.segundoNombre}" />
        </p>
        <p>
          <label for="identificador">
            <g:message code="persona.identificador" />
          </label>
          <g:textField name="extension" value="${params.identificador}" />
          <g:select name="root" from="${tiposIds}" optionKey="codigo" optionValue="nombreCorto" />
        </p>
        <p>
          <label for="fechaNacimiento">
            <g:message code="persona.fechaNacimiento" />
          </label>
          <br/>
          <br/>
          <g:datePicker name="fechaNacimiento" value="none" precision="day" noSelection="['':'']"/>
          <br/>
        
          <label for="sexo">
            <g:message code="persona.sexo" />
          </label>
          <br/>
          <br/>
            <g:select name="sexo" noSelection="['':'']" from="['M', 'F']" value="${params.sexo}" />
          <br/>
          <br/>
        </p>
        
      </fieldset>
      <fieldset class="step">
        <legend>Procedencia</legend>
        <p>
          <label for="etnia">
            <g:message code="persona.etnia" />
          </label>
          <g:select name="etnia.id" value="${params.etnia}" from="${etniasIds}" optionKey="id" optionValue="nombre" noSelection="['':'- Seleccione Etnia']"/>
        </p>
        <p>
          <label for="nacionalidad">
            <g:message code="persona.nacionalidad" />
          </label>
          <g:select name="nacionalidad.id" from="${paisesIds}" optionKey="id" optionValue="nombre" />
        </p>
        <p>
          <label for="paisnacimiento">
            <g:message code="persona.paisnace"/>
            <g:select name="paisnace" from="${paisesIds}" optionKey="id" optionValue="nombre" noSelection="['':'- Seleccione País']" onchange="updateSubCats(this.value)" />
          </label>
        </p>
        <p>
          <label for="entidadnacimiento">
            <g:message code="persona.entidadnace"/>
            <g:select name="entidadnace"  disabled="false" class="comboboxlugar" noSelection="['':'- Seleccione Entidad']" onchange="updateMunicipios(this.value)"/>
          </label>
        </p>
        <p>
          <label for="municipionacimiento">
            <g:message code="persona.municipionace"/>
            <g:select name="lugar.id" id="municnace" value="${params.lugarnacimiento}" disabled="false" class="comboboxlugar" noSelection="['':'- Seleccione Municipio']"/>
          </label>
        </p>
        <p>
          <label for="ciudadnacimiento">
            <g:message code="persona.ciudadnace"/>
            <g:textField name="ciudadnacimiento" value="${params.ciudadnacimiento}" /> 
          </label>
        </p>
      </fieldset>
      
      
      <fieldset class="step">
        <legend>Datos Personales</legend>
        <p>
          <label for="situacionconyugal">
            <g:message code="persona.situacionConyugal"/>
            <br/>
            <g:each var="conyugal" in="${conyugalIds}">
              <g:if test="${conyugal.id == 1}">
                ${conyugal.nombre} <g:radio name="situacionconyugal" value="${conyugal.id}" checked="true"/>
                &nbsp;
              </g:if>
              <g:else>
                ${conyugal.nombre} <g:radio name="situacionconyugal" value="${conyugal.id}"/>
                &nbsp;
              </g:else>
            </g:each>
          </label>
        </p>
        <p>
          <label for="analfabeta">
            <g:message code="persona.analfabeta"/> &nbsp;&nbsp;
            Si <g:radio name="analfabeta" value="1" optionValue="Si"/>
            No <g:radio name="analfabeta" value="0" checked="true"/>
          </label>
        </p>
        <p>
          <label for="niveleducativo">
            <g:message code="persona.niveleducativo"/>
            <br/>
            <g:each var="niveledu" in="${nivelEducIds}">
              <g:if test="${niveledu.id == 1}">
                ${niveledu.nombre} <g:radio name="niveleducativo" value="${niveledu.id}" checked="true"/>
              </g:if>
              <g:else>
                ${niveledu.nombre} <g:radio name="niveleducativo" value="${niveledu.id}" />
              </g:else>
            </g:each>
          </label>
        </p>
        <p>
          <label for="anosaprobados">
            <g:message code="persona.anosaprobados"/>
            <g:textField name="anosaprobados" value="${params.anosaprobados}" />
          </label>
        </p>
        <!--p>
          <label for="profesion">
            <g:message code="persona.profesion"/>
            <g:select name="profesion" from="${profesionIds}" optionKey="id" optionValue="nombre" noSelection="['':'- Seleccione Profesión']" />  
          </label>
		</p--> 
        <p>
          <label for="ocupacion">
            <g:message code="persona.ocupacion"/>
            <g:select name="ocupacion.id" from="${ocupacionIds}" optionKey="id" optionValue="nombre" noSelection="['':'- Seleccione Ocupación']" />  
          </label>
        </p>
      </fieldset>
      
      <fieldset class="step">
        <legend>Direcci&oacute;n de Habitaci&oacute;n</legend>
        <p>
          <label for="entidadresidencia">
            <g:message code="persona.entidadreside"/>
            <g:select name="entidresid" from="${entidadesIds}" optionKey="id" optionValue="nombre" noSelection="['':'- Seleccione Entidad']" onchange="updateMunicipiosReside(this.value)" />
          </label>
        </p>
        <p>
          <label for="municipioresidencia">
            <g:message code="persona.municipioreside"/>
            <g:select name="municresid" disabled="false" class="comboboxlugar" noSelection="['':'- Seleccione Municipio']" onchange="updateParroquiaReside(this.value)"/>
          </label>
        </p>
        <p>
          <label for="parroquiresidencia">
            <g:message code="persona.parroquiareside"/>
            <g:select name="direccion.id" id="parroresid" disabled="false" class="comboboxlugar" noSelection="['':'- Seleccione Parroquia']"/>
          </label>
        </p>
        <p>
          <label for="localidadreside">
            <g:message code="persona.localidadreside"/>
            <g:textField name="ciudad" value="${params.ciudad}" />
          </label>
        
          <label for="urbreside">
            <g:message code="persona.urbreside"/>
            <g:textField name="urbasector" value="${params.urbasector}" />
          </label>
        
          <label for="avenireside">
            <g:message code="persona.avenireside"/>
            <g:textField name="avenidacalle" value="${params.avenidacalle}" />
          </label>
        
          <label for="casareside">
            <g:message code="persona.casareside"/>
            <g:textField name="casaedif" value="${params.casaedif}" />
          </label>
        
          <label for="pisoreside">
            <g:message code="persona.pisoreside"/>
            <g:textField name="pisoplanta" value="${params.pisoplanta}" />
          </label>
		  
          <label for="ptoreferencia">
            <g:message code="persona.puntoreferencia"/>
            <g:textField name="ptoreferenica" value="${params.ptoreferenica}" />
          </label>
          
		  <label for="tiemporesidencia">
                <g:message code="persona.tiemporesidencia"/>
                <g:textField name="tiemporesidencia" value="${params.tiemporesidencia}" />
          </label> 
		  
        </p>
        </fieldset>
        <fieldset class="step">
              <legend>Contacto</legend>
              <p>
              <label for="telfhab">
                <g:message code="persona.telfhabitacion"/>
                <g:textField name="telfhabitacion" value="${params.telfhabitacion}" />
              </label>
            </p>
            <p>
              <label for="telfcel">
                <g:message code="persona.telfcelular"/>
                <g:textField name="telfcelular" value="${params.telfcelular}" />
              </label>
            </p>
            <p>
          <label for="email">
            <g:message code="persona.email"/>
            <g:textField name="email" value="${params.email}" />
          </label>
        </p>
        </fieldset>
        
      
      <fieldset class="step">
        <legend>Datos Familiariares</legend>
        <p>
          <label for="nombremadre">
            <g:message code="persona.nombremadre"/>
            <g:textField name="nombremadre" value="${params.nombremadre}" />
          </label>
        </p>
        <p>
          <label for="nombrepadre">
            <g:message code="persona.nombrepadre"/>
            <g:textField name="nombrepadre" value="${params.nombrepadre}" />
          </label>
        </p>
        <p>  
          <label for="otradireccion">
            <g:message code="persona.otradireccion"/>
            <g:textArea name="otradireccion" value="${params.otradireccion}" rows="1" cols="20"/>
          </label>
		  
		  <label for="contactoemergencia">
			<g:message code="persona.contactoemergencia"/>
			<g:textArea name="contactoemergencia" value="${params.contactoemergencia}" rows="1" cols="20"/>
		  </label>
		  
        </p>
        <p class="submit">
          <g:submitButton name="doit" value="${message(code:'demographic.agregar_paciente.agregar')}" />
          <g:link action="admisionPaciente"><g:message code="demographic.lista_candidatos.action.admisionPaciente" /></g:link>
        </p>    
      </fieldset>
    </g:form>
    </div>
                <div id="navigation" style="display:none;">
                    <ul>
                        <li class="selected">
                          <a href="#">Identificaci&oacute;n</a>
                        </li>
                        <li>
                            <a href="#">Procedencia</a>
                        </li>
                        <li>
                            <a href="#">Personales</a>
                        </li>
                        <li>
                            <a href="#">Direcci&oacute;n</a>
                        </li>
                        <li>
                            <a href="#">Contacto</a>
                        </li>
			<li>
                            <a href="#">Familiares</a>
                        </li>
                        
                    </ul>
                </div> 
    
    </div>
  </div>
  </body>
</html>