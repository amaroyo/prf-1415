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
	    <link rel="stylesheet" type="text/css" href="../skins/dhtmlx.css">
	    <script type="text/javascript" src="../skins/dhtmlx.js"></script>
	    <script type="text/javascript" src="../js/utilsajax.js"></script>
	    <script type="text/javascript" src="../js/general.js"></script>
	    <script src="../skins/patterns/dhtmlxlayout_pattern4l.js"></script>
	    

	    <script type="text/javascript">
	    
	    	dhtmlx.image_path='../skins/imgs/';
	    	var main_layout, idAsignatura,nombreAsignatura, gridProfesor, gridAlumnoRealizado,gridAlumnoPendiente,toolbarServicios,tab;
	    	
	    	dhtmlxEvent(window,"load",function() {
	    		
	    		
	    		<% String idAsignatura = request.getParameter("idAsignatura");%>
	    		idAsignatura="<%=idAsignatura%>";	
	    		<% String sessionIdUser = (String) session.getAttribute("idUsuario"); %>
				var idSelectedUser = <%=sessionIdUser%>;
	    		
	    		
	    		<logic:match scope="session" name="usuarioYPermisos" value="<permiso>1</permiso>" >
					goProfesor();
				</logic:match>
			
				<logic:notMatch scope="session" name="usuarioYPermisos" value="<permiso>1</permiso>" >
					goAlumno();
				</logic:notMatch>	
	    		
				
				function goProfesor(){
					
					main_layout = new dhtmlXLayoutObject(document.body, '1C');
		    		var a = main_layout.cells('a');
		    		a.hideHeader();
		    		
		    			
				}
				
	
				
				function goAlumno(){
					
					main_layout = new dhtmlXLayoutObject(document.body, '1C');
		    		var a = main_layout.cells('a');
		    		a.hideHeader();
		    		
		    		tabbar = a.attachTabbar();
		    		
		    		//HE AQUI QUE TENEMOS QUE HACER UNA CONSULTA EN MYSQL PARA SABER CUANTAS PRACTICAS
		    		//VA A HABER SEGUN LA ASIGNATURA PINCHADA PARA HACER EL WHILE
		    		
		    		var numSeminarios=0;
		    		switch(idAsignatura){
			    		case "idAsignatura1": {numSeminarios=1;break;}
			        	case "idAsignatura2": {numSeminarios=2;break;}
			        	case "idAsignatura3": {numSeminarios=3;break;}
			        	case "idAsignatura4": {numSeminarios=4;break;}
			        	case "idAsignatura5": {numSeminarios=5;break;}
			        	case "idAsignatura6": {numSeminarios=6;break;}
			        	case "idAsignatura7": {numSeminarios=7;break;}
		    		}
					
		    	
		    		for (var i=0; i<numSeminarios;i++){
		    			
		    			var t = "tab_"+i;
		    			tabbar.addTab(t,'Práctica ' + i,'');
				    	
		    			//no se como hacer que sea activa y que ademas este seleccionada para 
		    			//disparar al metodo onSelect para que lo rellene con datos...
				    	//if(i==0) tabbar.setTabActive(t);
				    	
		    		}
		    		
		    		
		    		tabbar.attachEvent("onSelect", function(id) {
		    			contenido(id);
		    		    return true;
		    		});
	    		
				}
				
				
				
				
				function buscarAlumno() {
					gridAlumnoRealizado.clearAndLoad("gridusuarios.do");
					gridAlumnoPendiente.clearAndLoad("gridusuarios.do");
			    }
				
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
					buscarAlumno();			    	
			    	tabbar.clearAll();		    	
			    }
				
				function contenido(tabID){
					
					tab = tabbar.cells(tabID);
					
									
			    	toolbarServicios = tab.attachToolbar();
			    	toolbarServicios.setIconsPath('../img/toolbar/');
			    	toolbarServicios.loadXML('../xml/toolbars/dhtxtoolbar-trabajos-campo.xml', function(){
			    		toolbarServicios.setItemText('subirPractica',"<bean:message key="button.subir.practica"/>");
			    		toolbarServicios.setItemText('descargarTodos',"<bean:message key="button.descargar.practicas"/>");
			    		toolbarServicios.setItemText('subirCorrecciones',"<bean:message key="button.subir.correcciones"/>");
			    		toolbarServicios.setItemText('fechaLimite',"<bean:message key="button.fecha.limite"/>");
			    		toolbarServicios.setItemText('refresh',"<bean:message key="button.actualizar"/>");
		    		
			    		
			    	});
			    	
	
			    	var grid = tab.attachGrid();
			    	
			    	grid.setHeader(["<bean:message key="label.nombre" />","<bean:message key="label.fecha" />","<bean:message key="label.enlace" />"]);
			    	grid.setColTypes("ro,ro,ro");
			    	
			    	grid.setColSorting('str,str,str');
			    	grid.enableMultiselect(false);
			    	grid.init();
			    	
			    	var gridProcessorPro = new dataProcessor("gridusuarios.do");
			    	gridProcessorPro.enableUTFencoding('simple');
			    	gridProcessorPro.init(grid);	  
			    	gridProcessorPro.attachEvent("onAfterUpdate", function(sid, action, tid, tag){
						if(action == 'error'){
			    			dhtmlx.message(tag.firstChild.data,action,4000);
			    		}
			    	});
			    	
	    			grid.clearAndLoad("gridusuarios.do");
					
				}
				
				
				
	    });
	    	
	    	
	    	
	    
	    	
	   </script>
	</head>
	<body>
	</body>
</html>