/*
*Aplicando efectos de Jquery en formularios
*
*By Armando Prieto
*armando.prieto@ciens.ucv.ve
*/


 $(document).ready(function(){
$('.CLUSTER .CLUSTER').find('.content').hide();
/*$(".CLUSTER .CLUSTER").click(function(){

$(this).find('.content').toggle('slow');

});*/
$(".openCluster").live('click', function() {
	var clus = $(this); 

			
			if( $(this).parent('span').parent('.CLUSTER').find('.content').is(':visible')){
					
					$(this).parent('span').append("<img class='openCluster' src='/sos/images/open.png' style='float: right; width: 15px; height: auto;'/>");
			
			
			}else{
					$(this).parent('span').append("<img class='openCluster' src='/sos/images/close.png' style='float: right; width: 15px; height: auto;'/>");
			
			}
			
			
			$(this).parent('span').parent('.CLUSTER').find('.content').fadeToggle();
			$(this).remove('img');

});


var u=0;

$('.CLUSTER').each(function (){
 var len = $(this).parents(".CLUSTER").length;
 
 var nivel = 1; //CAMBIAR ESTA VARIABLE EN CASO DE QUERER MAS O MENOS NIVELES DE EFECTOS SOBRE LOS CLUSTERS
 
	 if(len <= nivel){ //Indica el nivel de profundidad del CLUSTER empezando desde cero
	 
	 //OJO: si el nombre de la aplicacion cambia, esta href sería errada!
	 //si uso 'createLinkTo' no lo toma grails
	 //$(this).children('.label').append("<img class='openCluster' src='/sos/images/open.png' style='float: right; width: 15px; height: auto;'/>");
	 
	 //$(this).find('.content').hide();
	 
		if(len == 1){ 
			$(this).children('.label').append("<img class='openCluster' src='/sos/images/open.png' style='float: right; width: 15px; height: auto;'/>");
	 
		}
		if(len == 0){ 
			$(this).children('.label').append("<img class='openCluster' src='/sos/images/close.png' style='float: right; width: 15px; height: auto;'/>");
	 
		}
	 }
	 

});


});