<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.codehaus.groovy.grails.commons.ApplicationHolder" %>
<%@ page import="hce.core.common.directory.Folder" %>

<%--<?xml version="1.0" encoding="ISO-8859-1?>--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<g:set var="startmsec" value="${System.currentTimeMillis()}"/>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>

    <%-- No quiero paginas cacheadas --%>
    <%--
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache" />
    <META HTTP-EQUIV="Expires" CONTENT="-1" />
    <!-- META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE" /-->
    <META HTTP-EQUIV="Cache-Control" content="no-cache, must-revalidate" />
    --%>
    <%-- en FF no funca --%>
    <META Http-Equiv="Cache-Control" Content="no-cache">
    <META Http-Equiv="Pragma" Content="no-cache">
    <META Http-Equiv="Expires" Content="0"> 
   <%--
    <g:javascript>
      // Para evitar el boton de volver del navegador.
      window.history.go(1);
    </g:javascript>
    
    
    <g:javascript>
      Event.observe(window, 'load', function () {
 
        //alert( $$('a.clone') );
      
        $$('a.clone').each( function(item) {
        
          // item es cada link con class=clone
          item.observe('click', function(event) {
            
            //alert('clic');
            
            //alert( item.parentNode ); // parentNode es de DOM
            //alert( $(item.parentNode).previous() ); // parentNode es de DOM
            
            nodeToClone = $(item.parentNode).previous();
            
            // Extiendo DOM para .previous de prototype
            /*
            nodeToClone.setStyle({
              //backgroundColor: '#900',
              border: '3px solid #ffff00'
            });
            */
            
            // Object.clone tira un Object y necesito un Element para hacer insert
            //newNode = Object.clone(nodeToClone); //nodeToClone.clone();
            newNode = nodeToClone.cloneNode(true); // cloneNode tira un Element y me deja hacer insert
            
            //alert(newNode);
            
            nodeToClone.insert({
              after: newNode
            });
            
            /*
            item.insert({
              before: newNode
            });
            */
          });
        });
      });
    </g:javascript>
    --%>
    <title><g:layoutTitle/> | <g:message code="hce.nombre" /> | v${ApplicationHolder.application.metadata['app.version']}</title>
    <%--
    <link rel="stylesheet" href="${createLinkTo(dir:'css', file:'ehr.css')}" />
    --%>
    <link rel="stylesheet" href="${createLinkTo(dir:'css' ,file:'ehr_contenido_grande.css')}" />
   


        <r:require module="jquery-ui"/>
  <g:javascript library="jquery" />
  <jqui:resources themeCss="/sos/css/jquery/jquery-ui-1.8.16.custom.css"/>

  <script type="text/javascript" src="/sos/js/jquery/jquery.form.js"></script>
  <script type="text/javascript" src="/sos/js/jquery/jquery-ui-i18n.min.js"></script>
  <script type="text/javascript" src="/sos/js/jquery/jquery-ui-timepicker-addon.js"> </script>
  <script type="text/javascript">
    // 'modificado' establece si los valores de la seccion han sido modificados
    var modificado = false;
    // 'secc' establece seccion actual
    var secc = '${section}';
    function deseaGuardar(){
      
      return confirm("Desea guardar los cambios efectuados?");
    }
    function guardadoAutomatico(seccion, generarShow, itemId){
      if(!modificado){
        $(location).attr('href',"${createLink(controller:'records', action:'registroClinico2')}"+"?section="+seccion);
        return;
      }
      if(deseaGuardar()){
      //SI generarShow es true, significa que el registro ya está guardado previamente
      //<input id="mode" type="hidden" name="mode" value="show" />
      if ($(".ehrform").length > 0){
        
          if($('#mode').val() =='edit'){
            
          $(".ehrform").append("<input type='hidden' name='autoSave' value='"+seccion+"' />");
          $(".ehrform").submit();
          }else if($('#mode').val() =='show'){

            if(generarShow==true){
                $(location).attr('href',"${createLink(controller:'guiGen', action:'generarShow')}"+"?id="+itemId);
            }else{
                $(location).attr('href',"${createLink(controller:'records', action:'registroClinico2')}"+"?section="+seccion);
            }

          }else{
            //AQUI DEBERIA ENTRAR CUANDO SE GUARDA POR PRIMERA VEZ
            $(".ehrform").append("<input type='hidden' name='autoSave' value='"+seccion+"' />");
             $(".ehrform").submit();
          }
      
      }else if(generarShow==true){
        $(location).attr('href',"${createLink(controller:'guiGen', action:'generarShow')}"+"?id="+itemId);
      }else{
        $(location).attr('href',"${createLink(controller:'records', action:'registroClinico2')}"+"?section="+seccion);
      }
  }else{


    $(location).attr('href',"${createLink(controller:'records', action:'registroClinico2')}"+"?section="+seccion);

  }
    }


    


   $(document).ready(function(){
       //Capturar cambios realizados en el registro
       $('input, textarea, select').change(function() {
        modificado = true;
       });

       $('.contextoEhr').click(function(event){
         
         //si mode no existe, estamos fuera de contexto de alguna seccion
         //si mode es diferente a show es porque se está editando
         if(($('#mode').length > 0) && ($('#mode').val() !='show')){
         
         //capturar el valor del href, desde el controlador se realizará en direccioamiento
          var href = $(this).attr('href');
          if(href !='#'){
          
             if(!modificado){
              return;
              }
              if(deseaGuardar()){
                //detener el vinculo
                event.preventDefault();

              //SI generarShow es true, significa que el registro ya está guardado previamente
              //<input id="mode" type="hidden" name="mode" value="show" />
                  if ($(".ehrform").length > 0){
                  alert('guardando');
                  //pasar el valor del href al controlador
                  $(".ehrform").append("<input type='hidden' name='autoSaveHref' value='"+href+"' />");
                  $(".ehrform").submit();
                
                }
              }
          }
        }

     });

 

    $.datepicker.setDefaults($.datepicker.regional['${session.'org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE'}']);


        $(".DateSos").datepicker({dateFormat: 'dd-mm-yy',
         
         
                                     changeYear: true,
                                     //altField: '#actualDate',
                                     buttonText: 'Calendario',
                                     buttonImage: '/sos/images/datepicker.gif',
                                     maxDate: new Date(),
                                     minDate: new Date(2007, 9, 15),
                                     constrainInput: true,
                                     showButtonPanel: true,
                                     showOn: 'button'
                                     


        });
        $(".DateSos").attr("readonly",true);
        $(".DateSos").click(function(){
            $(this).val('');
        });

        $('.DateTimeSos').datetimepicker({dateFormat: 'dd-mm-yy',
          
          
                                     ampm: true,
                                     changeYear: true,
                                     buttonText: 'Calendario',
                                     buttonImage: '/sos/images/datepicker.gif',
                                     maxDate: new Date(),
                                     minDate: new Date(2007, 9, 15),
                                     showButtonPanel: true,
                                     showOn: 'button'

        });
        $(".DateTimeSos").attr("readonly",true);
        $(".DateTimeSos").click(function(){
            $(this).val('');
        });


     $('.clone').click(function(){
       //alert('clonando');
       var nodeToClone = $(this).parent().prev();
       var newNode = nodeToClone.clone();

       //Blanquear text areas
       newNode.find( 'textarea' ).each(function(i){
        $(this).val('');
       });
       //Blanquear combos
       newNode.find( 'select' ).each(function(i){
        $(this).val('');
       });
       //Blanquear inputs
       newNode.find( 'input' ).each(function(i){
        $(this).val('');
       });




       newNode.insertAfter(nodeToClone);


      });

 });
  </script>

 <g:layoutHead />



  </head>
  <body>
    <div id="user_bar">
      <b><g:message code="hce.nombre" /></b> v${ApplicationHolder.application.metadata['app.version']} |
      <g:datosUsuario userId="${userId}" />
      <span class="user_actions">
        
        <%-- FECHA ACTUAL --%>
        <span class="currentDate">
          <g:formatDate date="${new Date()}" formatName="default.date.format.text" />
        </span>
        
        <ul class="userBar">
          <g:langSelector>
            <li ${(session.locale.getLanguage()==it)?'class="active"':''}>
              <%-- no dejo cambiar el idioma si la accion es save 
                   http://code.google.com/p/open-ehr-sa/issues/detail?id=65
              --%>
              <g:if test="${actionName=='save'}">
                 <a href="#" class="contextoEhr"><g:message code="common.lang.${it}" /></a>
              </g:if>
              <g:else>
                <a href="?sessionLang=${it}&templateId=${params.templateId}" class="contextoEhr"><g:message code="common.lang.${it}" /></a>
              </g:else>
            </li>
          </g:langSelector>
        </ul>

        <ul class="userBar">
          <li ${(['domain'].contains(controllerName))?"class='active'":''}>
            <g:link controller="domain" action="list"><g:message code="domain.action.list" /></g:link>
          </li>
          <li>
           <g:set var="folder" value="${Folder.findByPath(session.traumaContext.domainPath)}" />
           (${folder.name.value})
          </li>
          <li ${(['records'].contains(controllerName))?"class='active'":''}>
            <g:link controller="records" action="list"><g:message code="records.action.list" /></g:link>
          </li>
          <li ${(controllerName=='demographic')?"class='active'":''}>
            <g:link controller="demographic" action="admisionPaciente"><g:message code="demographic.action.admisionPaciente" /></g:link>
          </li>
        </ul>
        
        <g:link controller="authorization" action="logout"><g:message code="authorization.action.logout" /></g:link>
        
      </span>
    </div>
  
    <div id="body">
    
      <%-- El registro clinico ya tiene un flash para mostrar mensajes, saco este para que no muestre doble.
      <g:if test="${flash.message}">
        <div id="message" class="error">
          <g:message code="${flash.message}" args="${flash.args}" />
        </div>
      </g:if>
      --%>
      
      <table cellpadding="0" cellspacing="0">
        <tr>
          <td id="body_table" rowspan="2">
            <g:resumenEpisodio episodeId="${episodeId}" />

           
            <g:layoutBody />
          </td>
          <td>
            <div id="infoPaciente">
              <h2><g:message code="trauma.title.informacionPaciente" /></h2>
              <%-- A patient lo manda como modelo guiGenController.generarTemplate --%>
              <g:if test="${patient}">
                <g:render template="../demographic/Person" model="[person:patient]" />
                
                <g:canEditPatient patient="${patient}">
                  <g:link controller="demographic" action="edit" id="${patient.id}" class="contextoEhr">Completar datos</g:link>
                </g:canEditPatient>
              </g:if>
              <g:else>
                <g:message code="trauma.layout.pacienteNoIdentificado.label" />:<br/>
                <g:link controller="demographic" action="admisionPaciente" class="contextoEhr">
                  <g:message code="trauma.layout.identificarPaciente.action" />
                </g:link>
              </g:else>
            </div>
            <div id="menu">
              
              <ul>
                <br />
                <li>
                  <g:link controller="records" action="list" class="contextoEhr">
                    <g:message code="trauma.menu.list" />
                  </g:link>
                </li>
                <br />
                <li ${((controllerName=='records'&&['show'].contains(actionName)) ? "class='active'" : '')}>
                  <g:link controller="records" action="show" id="${episodeId}" class="contextoEhr">
                    <g:message code="trauma.menu.show" />
                  </g:link>
                </li>
                
                <g:canFillClinicalRecord>
                
                  <li ${((controllerName=='records'&&['registroClinico'].contains(actionName)) ? "class='active'" : '')}>
                    <g:link controller="records" action="registroClinico" id="${episodeId}" class="contextoEhr">
                      <g:message code="trauma.menu.registroClinico" />
                    </g:link>
                  </li>
                  
                  <%--
                  TODO: desde lo estudios img hasta el registro clinico no puede ser
                        visto por un administrativo.
                  --%>
                  
                  <g:if test="${( ['guiGen','records','ajaxApi'].contains(controllerName) && ['generarShow','generarTemplate','show','saveDiagnostico','showRecord'].contains(actionName) )}">
                    <br />
                   
                    <g:each in="${sections}" var="section">

                      <li ${(( template?.id?.startsWith(section) ) ? "class='active'" : '')}>

                       <g:if test="${template?.id?.startsWith(section)}">
                       <%--Valor de la seccion actual--%>
                       <g:javascript> secc = '${section}';</g:javascript>
                       </g:if>
                      
                        <%-- allSubsections: ${allSubsections}<br/> --%>
                        <%-- se fija si el registro ya fue hecho --%>
                        <%
                        //def subsection = subsections.find{it.startsWith(section)}
                        def subsection = allSubsections[section][0]
                        if (!subsection) subsection = " " // para que no sea null o vacia en la llamada a g:hasContentItemForTemplate
                                                          // que espera no null y no vacio el templateId.
                        %>
                        <%-- subsection: ${subsection}<br/> --%>
                        <g:hasContentItemForTemplate episodeId="${episodeId}" templateId="${section+'-'+subsection}">
                          <g:if test="${it.hasItem}">
                           <%-- GUARDADO PREVIAMENTE, GENERAR SHOW --%>

                           <%-- <g:link controller="guiGen" action="generarShow" id="${it.itemId}">
                              <g:message code="${'section.'+section}" /> (+) <%-- + es que se hizo algun registro en la seccion --%>
                           <%-- </g:link>
                            --%>
                         <a href="#" onClick="guardadoAutomatico('${section}',true,${it.itemId});" class="contextoEhr"><g:message code='${"section."+section}' /> (+)</a>
                          </g:if>
                          <g:else>
                            <%-- SIN GUARDAR, GENERAR RECORDS INPUTS (registrao clinico2) --%>

                         <a href="#" onClick="guardadoAutomatico('${section}',false,false);" class="contextoEhr"><g:message code='${"section."+section}' /></a>
                           <%--<g:link controller="records" action="registroClinico2" params="[section:section]">
                              <g:message code="${'section.'+section}" />
                            </g:link>
                            --%>


                          </g:else>
                        </g:hasContentItemForTemplate>
                      </li>
                    </g:each>
                  </g:if>
                  <br />
                  <li ${((controllerName=='records'&&['signRecord'].contains(actionName)) ? "class='active'" : '')}>
                    <g:link controller="records" action="signRecord" id="${episodeId}" class="contextoEhr">
                      <g:message code="registro.menu.close" />
                      <g:isSignedRecord episodeId="${episodeId}">
                        (+)
                      </g:isSignedRecord>
                    </g:link>
                  </li>

                  
                </g:canFillClinicalRecord>
              </ul>
            </div>
            
          </td>
        </tr>
      </table>
    </div>
  </body>
</html>