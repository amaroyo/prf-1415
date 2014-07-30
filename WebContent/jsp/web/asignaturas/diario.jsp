<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" errorPage="error.jsp" %>
<%@ include file="../../common/taglibs.jsp" %>
<%@  page import="java.util.Enumeration"%>
<%@ page import="es.oyssen.mrm.Const"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>

<html>
	<head>
	    <link rel="stylesheet" type="text/css" href="../css/estilos.css">
	    <link rel="stylesheet" type="text/css" href="../css/templates.css">
	    <link rel="stylesheet" type="text/css" href="../css/estilosMenu.css">
	    <script type="text/javascript" src="../js/utilsajax.js"></script>
	    <script type="text/javascript" src="../js/general.js"></script>
	    
		<link rel="stylesheet" type="text/css" href="../js/dhtmlxSuite/dhtmlx.css">
		<script type="text/javascript" src="../js/dhtmlxSuite/dhtmlx.js"></script>
	    <script src="../js/dhtmlxSuite/patterns/dhtmlxlayout_pattern4l.js"></script>
	    

	    <script type="text/javascript">
	    
    		dhtmlx.image_path='../js/dhtmlxSuite/imgs/';
	    	var main_layout, idAsignatura, nombreAsignatura, gridProfesores,gridAlumnos,tab, profesor,a,b,idSession;
	    	
	    	dhtmlxEvent(window,"load",function() {
	    		
	    		//inicializo profesor a falso para tener un poco de seguridad
	    		profesor=false;
	    		
	    		<% String idAsignatura = request.getParameter("idAsignatura");%>
	    		idAsignatura="<%=idAsignatura%>";	
	    		<% String sessionIdUser = (String) session.getAttribute("idUsuario"); %>
				idSession = <%=sessionIdUser%>;
	    		

				
				
	    		
	    		<logic:match scope="session" name="usuarioYPermisos" value="<permiso>1</permiso>" >
					profesor=true;
					main_layout = new dhtmlXLayoutObject(document.body, '2U');
		    		a = main_layout.cells('a');
		    		b = main_layout.cells('b');
		    		b.setWidth(350);
		    		a.hideHeader();
					b.setText('<bean:message key="label.diario.reflexivo.alumno"/>');
				</logic:match>
		
				<logic:notMatch scope="session" name="usuarioYPermisos" value="<permiso>1</permiso>" >
					profesor=false;
					main_layout = new dhtmlXLayoutObject(document.body, '1C');
		    		a = main_layout.cells('a');
		    		a.hideHeader();
				</logic:notMatch>	
	    		
	    		
	    		
				
				
		    	toolbarServicios = a.attachToolbar();
		    	toolbarServicios.setIconsPath('../img/toolbar/');

		    	
		    	toolbarServicios.loadXML('../xml/toolbars/dhtxtoolbar-trabajos-campo.xml', function(){
		    		toolbarServicios.setItemText('subirPractica',"<bean:message key="button.subir.practica"/>");
		    		toolbarServicios.setItemText('descargarTodos',"<bean:message key="button.descargar.diarios"/>");
		    		toolbarServicios.setItemText('subirCorrecciones',"<bean:message key="button.subir.correcciones"/>");
		    		toolbarServicios.setItemText('fechaLimite',"<bean:message key="button.fecha.limite"/>");
		    		toolbarServicios.setItemText('refresh',"<bean:message key="button.actualizar"/>");
		    		
		    		toolbarServicios.hideItem('subirCorrecciones');
		    		toolbarServicios.hideItem('sep3');
		    		toolbarServicios.hideItem('fechaLimite');
		    		toolbarServicios.hideItem('sep4');
		    	
		    		<logic:match scope="session" name="usuarioYPermisos" value="<permiso>1</permiso>" >
			    		toolbarServicios.hideItem('subirPractica');
			    		toolbarServicios.hideItem('sep1');
	    			</logic:match>
	    			<logic:notMatch scope="session" name="usuarioYPermisos" value="<permiso>1</permiso>" >    	
			    		toolbarServicios.hideItem('descargarTodos');
			    		toolbarServicios.hideItem('sep2');
	    			</logic:notMatch>
		    		
		    	
		    	});
		    	
		    	if (profesor) goGridProfesores();
		    	else goGridAlumnos();
				
				
	    		
				
				
	    	});
	    	

			
			function subirPractica(){
				alert("subir Practica");
			}
			
			function descargarTodos(){
				alert("Descargar Todos");
			}
			
			function subirCorrecciones(){
				alert("Subir Correcciones");
			}
			
			function fechaLimite(){
				alert("Fecha Limite");
			}
			
			function goActualizar() {
				if (profesor) gridProfesores.clearAndLoad("gridUsuariosDiariosReflexivosAsignatura.do?idAsignatura=" + idAsignatura);	 
				else gridAlumnos.clearAndLoad("gridDiariosReflexivosUsuarioAsignatura.do?idAsignatura=" + idAsignatura + "&idAlumno=" + idSession);		    	
		    	tabbar.clearAll();		    	
		    }
			
			function goGridAlumnos(){
				
				gridAlumnos = a.attachGrid();
		    	
				gridAlumnos.setHeader(["<bean:message key="label.nombre" />","<bean:message key="label.fecha" />","<bean:message key="label.enlace" />"]);
				gridAlumnos.setColTypes("ro,ro,ro");
		    	
				gridAlumnos.setColSorting('str,str,str');
				gridAlumnos.enableMultiselect(false);
				gridAlumnos.init();
		    	
		    	var gridProcessorPro = new dataProcessor("gridDiariosReflexivosUsuarioAsignatura.do?idAsignatura=" + idAsignatura + "&idAlumno=" + idSession);
		    	gridProcessorPro.enableUTFencoding('simple');
		    	gridProcessorPro.init(gridAlumnos);	  
		    	gridProcessorPro.attachEvent("onAfterUpdate", function(sid, action, tid, tag){
					if(action == 'error'){
		    			dhtmlx.message(tag.firstChild.data,action,4000);
		    		}
		    	});
		    	
		    	gridAlumnos.clearAndLoad("gridDiariosReflexivosUsuarioAsignatura.do?idAsignatura=" + idAsignatura + "&idAlumno=" + idSession);
				
			}
			
			function goGridProfesores(){
				
				gridProfesores = a.attachGrid();
		    	
				gridProfesores.setHeader(["<bean:message key="label.nombre" />","<bean:message key="label.apellido" />","<bean:message key="label.dni" />","<bean:message key="label.telefono" />","<bean:message key="label.user.email" />"]);
				gridProfesores.setColTypes("ro,ro,ro,ro,ro");
		    	
				gridProfesores.setColSorting('str,str,str,str,str');
				gridProfesores.enableMultiselect(false);
				gridProfesores.init();
		    	
		    	var gridProcessorPro = new dataProcessor("gridUsuariosDiariosReflexivosAsignatura.do?idAsignatura=" + idAsignatura);
		    	gridProcessorPro.enableUTFencoding('simple');
		    	gridProcessorPro.init(gridProfesores);	  
		    	gridProcessorPro.attachEvent("onAfterUpdate", function(sid, action, tid, tag){
					if(action == 'error'){
		    			dhtmlx.message(tag.firstChild.data,action,4000);
		    		}
		    	});

		    	
		    	gridProfesores.attachEvent("onRowSelect",doOnRowSelected);
		    	
		    	gridProfesores.clearAndLoad("gridUsuariosDiariosReflexivosAsignatura.do?idAsignatura=" + idAsignatura);
				
			}
			
			function doOnRowSelected(rowID,celInd){
				
				var gridProfesoresAlumno = b.attachGrid();
				
		    	
				gridProfesoresAlumno.setHeader(["<bean:message key="label.nombre" />", "<bean:message key="label.fecha" />", "<bean:message key="label.enlace" />"]);
				gridProfesoresAlumno.setColTypes("ro,ro,ro");
		    	
				gridProfesoresAlumno.setColSorting('str,str,str');
				gridProfesoresAlumno.enableMultiselect(false);
				gridProfesoresAlumno.init();
		    	
		    	var gridProcessorPro = new dataProcessor("gridDiariosReflexivosAsignaturaUsuario.do?idPortafolio=" + rowID);
		    	gridProcessorPro.enableUTFencoding('simple');
		    	gridProcessorPro.init(gridProfesoresAlumno);	  
		    	gridProcessorPro.attachEvent("onAfterUpdate", function(sid, action, tid, tag){
					if(action == 'error'){
		    			dhtmlx.message(tag.firstChild.data,action,4000);
		    		}
		    	});

		    	
		    	gridProfesoresAlumno.attachEvent("onRowSelect",doOnRowSelectedOptions); 
		    	
		    	gridProfesoresAlumno.clearAndLoad("gridDiariosReflexivosAsignaturaUsuario.do?idPortafolio=" + rowID);
		    	
		    }
			
			function doOnRowSelectedOptions(rowID,celInd){
				if(celInd=='2') {
					var parts = rowID.split("-");
					alert("Descargar Archivo con idPortafolio=" + parts[0] + " y idDiarioReflexivo=" + parts[1]);
				}
	
			}
			
			
			
	    	
	   </script>
	</head>
	<body>
	</body>
</html>