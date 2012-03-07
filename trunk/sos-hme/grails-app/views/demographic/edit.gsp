<%@ page import="tablasMaestras.TipoIdentificador" %>
<html>
  <head>
    <meta name="layout" content="basicregistro" />
    <title><g:message code="demographic.edit.title" /></title>
    
    
    <g:javascript library="prototype" />
    <r:require module="jquery-ui"/>
    <g:javascript library="jquery" />
    
    
    <%--link rel="stylesheet" href="${resource(dir:'css/jquery',file:'jquery-ui-1.8.16.custom.css')}"
    <link rel="stylesheet" href="${resource(dir:'css',file:'jquery.Jcrop.css')}" />
    <script type="text/javascript" src="${createLinkTo(dir:'js/jquery',file:'jquery.Jcrop.js')}"></script>
    <script type="text/javascript" src="${createLinkTo(dir:'js/jquery',file:'jquery.Jcrop.min.js')}"></script>
    <script type="text/javascript" src="${createLinkTo(dir:'js/jquery',file:'jquery-ui-i18n.min.js')}"></script>
    
    <script type="text/javascript" src="${createLinkTo(dir:'js/jquery',file:'jquery-ui-timepicker-addon.js')}"></script>
    <script type="text/javascript" src="${createLinkTo(dir:'js/jquery' ,file:'jquery.form.js')}"></script>
    
    <g:javascript src='formToWizard.js' />
    <g:javascript src='funciones.js' />
    <g:javascript src='jquery.validate.js' />
    <link rel="stylesheet" href="${resource(dir:'css',file:'wizard.css')}" /--%>
    
    <jqui:resources themeCss="/sos/css/jquery/jquery-ui-1.8.16.custom.css"/>
    <link rel="stylesheet" href="${resource(dir:'css',file:'jquery.Jcrop.css')}" />
    <g:javascript src="../js/jquery/jquery.Jcrop.js" />
    <g:javascript src="../js/jquery/jquery.Jcrop.min.js" />

    <script type="text/javascript" src="/sos/js/jquery/jquery-ui-i18n.min.js"></script>
    <script type="text/javascript" src="/sos/js/jquery/jquery-ui-timepicker-addon.js"> </script>
     
      <script type="text/javascript" src="${createLinkTo(dir:'js/jquery' ,file:'jquery.form.js')}"></script>
    
    <g:javascript src='formToWizard.js' />
    <g:javascript src='funciones.js' />
    <g:javascript src='jquery.validate.js' />
    <link rel="stylesheet" href="${resource(dir:'css',file:'wizard.css')}" />
    
    
    <style>
      label {
        display: block;
      }
    </style>
    
    <script>
      jQuery(document).ready(function()
      {
          jQuery.datepicker.setDefaults(jQuery.datepicker.regional['${session.'org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE'}']);

                   jQuery('.Date').datepicker({dateFormat: 'dd-mm-yy',
                   changeYear: true,
                    //altField: '#actualDate',
                    buttonText: 'Calendario',
                    buttonImage: '/sos/images/datepicker.gif',
                    maxDate: new Date(),
                    minDate: new Date(1900, 9, 15),
                    yearRange: '1900:2100',
                    constrainInput: true,
                    showButtonPanel: true,
                    showOn: 'button'
          });
          jQuery("#fechaNacimiento").attr("readonly",true);
          jQuery("#fechaNacimiento").click(function(){
            jQuery(this).val('');
          });
      });
       jQuery(document).ready(function()
      {
        jQuery("#foto").attr("readonly",true);
        jQuery("#foto").click(function (){
          jQuery("#inputFotoPrevia").click();
          
        });
       jQuery("#inputFotoPrevia").change(function(){
         jQuery("#fotoPrevia").ajaxForm({success: mostrarRespuesta}).submit();
        });

      });
      function mostrarRespuesta(respuesta){

       if(respuesta != ""){
       
        jQuery("#imgPrevia").html("<img id='crop' src='${createLink(controller: "demographic",action: "mostrarFotoPrevia")}/"+respuesta+"' />")
        jQuery("#foto").val(respuesta);
        jQuery("#crop").Jcrop({
            aspectRatio: 1,
            setSelect: [0, 0, 100, 100],
            minSize: [30, 30],
            onSelect: cambiarCoordenadas,
            onChange: cambiarCoordenadas
        });
        }
      }

      function cambiarCoordenadas(c) {
     jQuery('#x1').val(c.x);
     jQuery('#y1').val(c.y);
     jQuery('#x2').val(c.x2);
     jQuery('#y2').val(c.y2);
 }

    </script>
    <g:javascript>
        
		function updateIdentidad( var1, var2 ){
            ${remoteFunction( 
              controller:'demographic', 
              action:'ajaxGetPrimerApellido', 
              update:'identidades', 
              params:'\'id=\' + var1 +\'-\' +var2')}
			
		
		}
		function updateNombres( root, extension, primerApellido, segundoApellido, primerNombre, segundoNombre, fechaNacimiento, sexo, fin){
			${remoteFunction( 
			controller:'demographic', 
			action:'ajaxGetNombres', 
			update:'nombres', 
			params:'\'id=\' + root +\'!\' +extension + \'!\' + primerApellido + \'!\' + segundoApellido + \'!\' + primerNombre + \'!\' + segundoNombre + \'!\' + fechaNacimiento + \'!\' + sexo + \'!\'+ fin' )}
			//params:'\'rt=\' +root+\'&ext=\'+extension' )}
			//params:'\'id=\' + root +\'-\' +extension + \'&pa=\' + primerApellido' )}
			//params:'\'id=\' + root +\'-\' +extension + \'&pa=\' + primerApellido + \'&sa=\' + segundoApellido' )}
		}
         function updateSubCats( category ){
              var selectpais = document.getElementById("paisnace");
              if (selectpais.options[selectpais.selectedIndex].value == 1){
              document.getElementById("entnace").style.visibility = "visible";
              document.getElementById("munnace").style.visibility = "visible";
              document.getElementById("ciunace").style.visibility = "visible";
              
              ${remoteFunction( 
              controller:'demographic', 
              action:'ajaxGetEstados', 
              update:'entidadnace', 
              params:'\'id=\' + category')} 
              
              }else{
                document.getElementById("entnace").style.visibility = "hidden";
                document.getElementById("munnace").style.visibility = "hidden";
                document.getElementById("ciunace").style.visibility = "hidden";
              }
              
          }
          
          function updateMunicipios(estado){
            ${remoteFunction( 
                controller:'demographic', 
                action:'ajaxGetMunicipios', 
                update:'municnace', 
                params:'\'id=\' + estado')}
          }
          function updateMunicipiosReside(estado){
            var selectparroquia = document.getElementById("parroresid");
            
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
                update:'direccion.id', 
                params:'\'id=\' + municipio')}
          }
      </g:javascript>
	  
    <script type="text/javascript">
      var $j = jQuery.noConflict();
      $j(document).ready(function(){
          $j("#nuevopaciente").formToWizard({ submitButton: 'doit' })
      });
    </script>
    
  </head>
  <body>
    <h1><g:message code="demographic.edit.title" /></h1>
    
    <g:if test="${flash.message}">
      <div style="color:red;">
        <g:message code="${flash.message}" />
      </div>
    </g:if>
    
    <g:form action="edit" id="${patient.id}" name="nuevopaciente">
      
      <fieldset>
        <legend>Identificaci&oacute;n</legend>
      
      
      <label for="identificador">
        <g:message code="persona.identificador" />
      </label>
      
      <g:each in="${patient.ids}" var="pid">
      
        <g:set var="codigo" value="${TipoIdentificador.findByCodigo(pid.root)}" />

        <g:textField name="extension" value="${pid.extension}" readonly="readonly"/>
        <g:select name="root" from="${tiposIds}" optionKey="codigo" optionValue="nombreCorto" class="selectci" value="${pid.root}" disabled="true"/>
        <br/>
      </g:each>
      
      <%-- No dejo cambiar los identificadores porque deberia lanzar un proceso que verifique donde hay referencias a lso identificadores que se eliminador o cambiaron por correccion.
      <g:textField name="extension" value="${pid.extension}" />
      <g:select name="root" from="${tiposIds}" optionKey="codigo" optionValue="nombreCorto" />
      --%>
      
      <%--g:textField name="extension" value="${patient.ids[0].extension}"/>
      <g:select name="root" from="${tiposIds}" optionKey="codigo" optionValue="nombreCorto" class="selectci" value="${patient.ids[0].root}"/--%>
      <input type='hidden' id="identificadorUnico" value="valido" name="identificadorUnico"/>
        
      <label for="primerNombre">
        <g:message code="persona.primerNombre" />
      </label>
      <g:textField name="primerNombre" value="${pn.primerNombre}" />
      
      <label for="segundoNombre">
        <g:message code="persona.segundoNombre" />
      </label>
      <g:textField name="segundoNombre" value="${pn.segundoNombre}" />
      
      <label for="primerApellido">
        <g:message code="persona.primerApellido" />
      </label>
      <g:textField name="primerApellido" value="${pn.primerApellido}" />
      
      <label for="segundoApellido">
        <g:message code="persona.segundoApellido" />
      </label>
      <g:textField name="segundoApellido" value="${pn.segundoApellido}" />
      
      <label for="fechaNacimiento"><g:message code="persona.fechaNacimiento" /></label>
	<input name="fechaNacimiento" type="text" id="fechaNacimiento" class="Date" value="${fechaNace}"/>  <br /><br />
      
      <label for="sexo"><g:message code="persona.sexo" /></label>
	<g:select name="sexo" class="selectci" noSelection="['':'Seleccione']" from="['Masculino', 'Femenino']" value="${patient.sexo}" />
      
      <label for="foto"><g:message code="persona.foto" /></label>
	<input type="text" name="foto" id="foto" style="width: 300px;"/>  
        
      </fieldset>  
        
        
       <fieldset>
        <legend>Procedencia</legend> 
      <label for="etnia"><g:message code="persona.etnia" /></label>
          <g:if test="${pn.etnia!=null}">
            <g:select name="etnia.id" class="selectci" value="${pn.etnia}" from="${etniasIds}" optionKey="id" optionValue="nombre"/>
          </g:if>
          <g:else>
            <g:select name="etnia.id" class="selectci" value="${pn.etnia}" from="${etniasIds}" optionKey="id" optionValue="nombre" noSelection="['':'Seleccione Etnia']"/>
          </g:else>
          
          <label for="nacionalidad"><g:message code="persona.nacionalidad" /></label>
            <g:select name="nacionalidad.id" class="selectci" from="${paisesIds}" optionKey="id" optionValue="nombre" />
          
          <g:if test="${paisNace!=null}">
            <label for="paisnacimiento"><g:message code="persona.paisnace"/></label>
            <g:select name="paisnace" class="selectci" value="${paisNace.id}" from="${paisesIds}" optionKey="id" optionValue="nombre"  disabled="false" onchange="updateSubCats(this.value)"/>
          </g:if>  
          <g:else>
            <label for="paisnacimiento"><g:message code="persona.paisnace"/></label>
            <g:select name="paisnace" class="selectci" from="${paisesIds}" optionKey="id" optionValue="nombre" noSelection="['':'Seleccione País']" onchange="updateSubCats(this.value)" />
          </g:else>  
          
          
          <g:if test="${entidadNace!=null}">
              <div id="entnace" style="visibility: visible;">
              <label for="entidadnacimiento"><g:message code="persona.entidadnace"/></label>
                <g:select name="entidadnace" class="selectci" value="${entidadNace.id}" from="${entidadesIds}" optionKey="id" optionValue="nombre"  disabled="false" onchange="updateMunicipios(this.value)"/>
              </div>
          </g:if>
          <g:else>  
              <div id="entnace" style="visibility: hidden;">
              <label for="entidadnacimiento"><g:message code="persona.entidadnace"/></label>
                      <g:select name="entidadnace" class="selectci" disabled="false" noSelection="['':'Seleccione Entidad']" onchange="updateMunicipios(this.value)"/>
              </div> 
          </g:else>
            
          
          <g:if test="${municipioNace!=null}">
              <div id="munnace" style="visibility: visible;">         
                <label for="municipionacimiento"><g:message code="persona.municipionace"/></label>
                <g:select name="lugar.id" id="municnace" class="selectci" value="${municipioNace.id}" from="${municipios}" optionKey="id" optionValue="nombre" disabled="false"/>
            </div>
          </g:if>    
            
          <g:else>
              <div id="munnace" style="visibility: hidden;">         
                <label for="municipionacimiento"><g:message code="persona.municipionace"/></label>
                  <g:select name="lugar.id" id="municnace" class="selectci" value="${pn.lugar}" disabled="false" noSelection="['':'Seleccione Municipio']"/>
                </div>
          </g:else> 
          
          
          <g:if test="${pn.ciudadnacimiento!=''}">
            <div id="ciunace" style="visibility: visible;">        
                <label for="ciudadnacimiento"><g:message code="persona.ciudadnace"/></label>
                <g:textField name="ciudadnacimiento" value="${pn.ciudadnacimiento}" />
            </div>
          </g:if>
          <g:else>
            <div id="ciunace" style="visibility: hidden;">        
                <label for="ciudadnacimiento"><g:message code="persona.ciudadnace"/></label>
                  <g:textField name="ciudadnacimiento" value="${pn.ciudadnacimiento}" />
              </div>
          </g:else>
          
          <br/>  
        
       </fieldset>
        
        <fieldset>
        <legend>Datos Personales</legend>
          <g:if test="${pn.situacionconyugal!=null}">
             <label for="situacionconyugal"><g:message code="persona.situacionConyugal"/></label>
             <g:select name="situacionconyugal" class="selectci" value="${pn.situacionconyugal}" from="${conyugalIds}" optionKey="id" optionValue="nombre"/>
          </g:if>
          <g:else>
            <label for="situacionconyugal"><g:message code="persona.situacionConyugal"/></label>
          <g:select name="situacionconyugal" class="selectci" from="${conyugalIds}" optionKey="id" optionValue="nombre" noSelection="['':'Seleccione']"/>
          </g:else>
          
          <g:if test="${pn.analfabeta!=null}">
            <label for="analfabeta"><g:message code="persona.analfabeta"/> &nbsp;&nbsp;</label>
            <g:select name="analfabeta" class="selectci" from="['No', 'Si']" value="${pn.analfabeta}" />
          </g:if>
          <g:else>
            <label for="analfabeta"><g:message code="persona.analfabeta"/> &nbsp;&nbsp;</label>
            <g:select name="analfabeta" class="selectci" noSelection="['':'Seleccione']" from="['No', 'Si']" value="${pn.analfabeta}" />
          </g:else>
          
            
          <g:if test="${pn.niveleducativo!=null}">
             <label for="niveleducativo"><g:message code="persona.niveleducativo"/></label>
          <g:select name="niveleducativo" class="selectci" from="${nivelEducIds}" optionKey="id" optionValue="nombre" value="${pn.niveleducativo}"/>
          </g:if>
          <g:else>
            <label for="niveleducativo"><g:message code="persona.niveleducativo"/></label>
            <g:select name="niveleducativo" class="selectci" from="${nivelEducIds}" optionKey="id" optionValue="nombre" noSelection="['':'Seleccione']"/>
          </g:else>
          
          <label for="anosaprobados"><g:message code="persona.anosaprobados"/></label>
		  <g:textField name="anosaprobados" value="${pn.anosaprobados}" />
          
          <g:if test="${pn.ocupacion!=null}">
            <label for="ocupacion"><g:message code="persona.ocupacion"/></label>
            <g:select name="ocupacion.id" id="ocupacion" class="selectci" from="${ocupacionIds}" optionKey="id" optionValue="nombre" value="${pn.ocupacion}" />  
          </g:if>
          <g:else>
              <label for="ocupacion"><g:message code="persona.ocupacion"/></label>
              <g:select name="ocupacion.id" id="ocupacion" class="selectci" from="${ocupacionIds}" optionKey="id" optionValue="nombre" noSelection="['':'Seleccione Ocupación']" />  
          </g:else>
          
      </fieldset>
        
       <fieldset>
        <legend>Direcci&oacute;n de Habitaci&oacute;n</legend>
          <label for="entidadresidencia"><g:message code="persona.entidadreside"/></label>
                  <g:select name="entidresid" class="selectci" value="${estado.id}" from="${entidadesIds}" optionKey="id" optionValue="nombre" onchange="updateMunicipiosReside(this.value)" />
                  
          <label for="municipioresidencia"><g:message code="persona.municipioreside"/></label>
		  <g:select name="municresid" class="selectci" value="${municipio.id}" from="${municipios}" optionKey="id" optionValue="nombre" onchange="updateParroquiaReside(this.value)"/>
		  
          <label for="parroquiresidencia"><g:message code="persona.parroquiareside"/></label>
		  <%--g:select name="direccion.id" class="selectci" id="parroresid" disabled="false" noSelection="['':'Seleccione Parroquia']"/--%>
                  <g:select name="direccion.id" class="selectci" value="${direccion}" from="${parroquias}" optionKey="id" optionValue="nombre" disabled="false" />
		  
          <label for="localidadreside"><g:message code="persona.localidadreside"/></label>
		  <g:textField name="ciudad" value="${pn.ciudad}" />
        
          <label for="urbreside"><g:message code="persona.urbreside"/></label>
		  <g:textField name="urbasector" value="${pn.urbasector}" />
        
          <label for="avenireside"><g:message code="persona.avenireside"/></label>
		  <g:textField name="avenidacalle" value="${pn.avenidacalle}" />
        
          <label for="casareside"><g:message code="persona.casareside"/></label>
		  <g:textField name="casaedif" value="${pn.casaedif}" />
        
          <label for="pisoreside"><g:message code="persona.pisoreside"/></label>
		  <g:textField name="pisoplanta" value="${pn.pisoplanta}" />
		  
          <label for="ptoreferencia"><g:message code="persona.puntoreferencia"/></label>
		  <g:textField name="ptoreferenica" value="${pn.ptoreferenica}" />
          
          <label for="tiemporesidencia"><g:message code="persona.tiemporesidencia"/></label> 
		  <g:textField name="tiemporesidencia" value="${pn.tiemporesidencia}" />
        </fieldset> 
        
        
        <fieldset>
              <legend>Contacto</legend>
              <label for="telfhab"><g:message code="persona.telfhabitacion"/></label>
	      <g:textField name="telfhabitacion" value="${pn.telfhabitacion}" />
			  
              <label for="telfcel"><g:message code="persona.telfcelular"/></label>
	      <g:textField name="telfcelular" value="${pn.telfcelular}" />
			  
              <label for="email"><g:message code="persona.email"/></label>
              <g:textField name="email" value="${pn.email}" />
              
              <label for="nombremadre"><g:message code="persona.nombremadre"/></label>
		  <g:textField name="nombremadre" value="${pn.nombremadre}" />
		  
              <label for="nombrepadre"><g:message code="persona.nombrepadre"/></label>
              <g:textField name="nombrepadre" value="${pn.nombrepadre}" />
		  
              <label for="otradireccion"><g:message code="persona.otradireccion"/></label>
              <g:textArea name="otradireccion" class="inputtextfield" value="${pn.otradireccion}" rows="1" cols="20"/>
		  
              <label for="contactoemergencia"><g:message code="persona.contactoemergencia"/></label>
              <g:textArea name="contactoemergencia" class="inputtextfield" value="${pn.contactoemergencia}" rows="1" cols="20"/>
        </fieldset>
        
        
        
        
      <br/>
      <br/>
      <g:submitButton name="doit" value="${message(code:'demographic.edit.save')}" />
      
    </g:form>
  <%-- Foto previa--%>
    <g:form action="fotoPrevia" name="fotoPrevia"  enctype="multipart/form-data">
     <input type="file" name="fotoPrevia" id="inputFotoPrevia" style="width: 300px; visibility: hidden;"/>
    </g:form>
  </body>
</html>