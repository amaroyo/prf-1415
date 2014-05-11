<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" errorPage="error.jsp" %>
<%@ include file="../../common/taglibs.jsp" %>

<html>
	<head>
	    <link rel="stylesheet" type="text/css" href="../css/estilos.css">
	    <link rel="stylesheet" type="text/css" href="../css/templates.css">
	    <link rel="stylesheet" type="text/css" href="../css/estilosMenu.css">
	    <link rel="stylesheet" type="text/css" href="../skins/dhtmlx.css">
	    <script type="text/javascript" src="../skins/dhtmlx.js"></script>
	    <script type="text/javascript" src="../js/utilsajax.js"></script>
	    <script type="text/javascript" src="../js/general.js"></script>
	    

	    <script type="text/javascript">
	    
	    	dhtmlx.image_path='../skins/imgs/';
	    	
	    	var main_layout, areaTrabajoCursos, listado, toolbarCursos,
	    	gridCursos, tabbarCursos, tabInfo, formInfo;
	    	
		    dhtmlxEvent(window,"load",function() {
		    	
		    	dhtmlxError.catchError("ALL",errorHandler);
		    	<% String opcion = request.getParameter("opcion");%>
		    	opcionSeminarioAsignatura="<%=opcion%>";
			    main_layout = new dhtmlXLayoutObject(document.body, '2U');
			    listado = main_layout.cells('a');
			    listado.setWidth(600);
			    areaTrabajoCursos = main_layout.cells('b');
			    
			    //autosize(horizontal,vertical)
			    //"a;b" 'a' and 'b' will autosize when changing horizontal dimensions of layout
			    //listado.setAutoSize("a;b",null)
			    
		    	listado.setText("<strong><bean:message key="title.seminarios" /></strong>");
			    areaTrabajoCursos.setText("<bean:message key="title.propiedades.seminario" />");
			    	
			    toolbarCursos = listado.attachToolbar();
			    toolbarCursos.setIconsPath('../skins/imgs/toolbar/');
			    toolbarCursos.loadXML('../xml/toolbars/dhxtoolbar-cursos.xml', function(){
	    		toolbarCursos.setItemText('new',"<bean:message key="button.create.seminario"/>");
	    		toolbarCursos.setItemText('delete',"<bean:message key="button.eliminar.seminario"/>");
		    	toolbarCursos.setItemText('refresh',"<bean:message key="button.actualizar"/>");
		    		
	    		<logic:notMatch scope="session" name="usuarioYPermisos" value="<permiso>1</permiso>" >	    	
		    		toolbarCursos.hideItem('new');
		    		toolbarCursos.hideItem('sep1');    	
		    		toolbarCursos.hideItem('delete');
		    		toolbarCursos.hideItem('sep2');
				</logic:notMatch>
		    });
			    
			    
			    gridCursos = listado.attachGrid();
			    gridCursos.setIconsPath('../skins/imgs/');
			    gridCursos.setHeader(["<strong><bean:message key="label.nombre.seminario" /></strong>",
			    	                      "<strong><bean:message key="label.codigo.seminario" /></strong>",
			    	                      "<strong><bean:message key="label.curso.seminario" /></strong>",
			    	                      "<strong><bean:message key="label.descripcion.seminario" /></strong>"]);
			    
				
			    //ro = readonly
			    //nombre codigo curso descripcion
			    gridCursos.setColTypes("ro,ro,ro,ro");
			    gridCursos.setColSorting('str,str,str,str');
			    gridCursos.enableMultiselect(false);
			    gridCursos.init();
			    gridCursosProcessor = new dataProcessor("gridcursos.do");
			    gridCursosProcessor.enableUTFencoding('simple');
			    gridCursosProcessor.init(gridCursos);	  
			    gridCursosProcessor.attachEvent("onAfterUpdate", function(sid, action, tid, tag){
					if(action == 'error'){
		    			dhtmlx.message(tag.firstChild.data,action,4000);
		    		}
		    	});
			 	// TAG: Prueba
				gridCursos.addRow('1','A01,Anatomía,1º,Prácticas de Anatomía');
			    
			    gridCursos.attachEvent("onRowSelect", doOnRowSelected);				  
		    });
		    
		    
		    function doOnRowSelected(rowID,celInd){
		    	toolbarCursos.enableItem('delete');

			    tabbarCursos = areaTrabajoCursos.attachTabbar();
			    tabbarCursos.addTab('tabInfo','<bean:message key="label.info.general"/>','');
		    	tabInfo = tabbarCursos.cells('tabInfo');
		    	tabbarCursos.setTabActive('tabInfo');
		    	formInfo = tabInfo.attachForm();
		    	loadFormSeminario();
		    	// obtener el nombre del curso de la bbdd y añadirlo como header a la dcha
				areaTrabajoCursos.setText(areaTrabajoCursos.getText() + "");
		    }
		    
		    function loadFormSeminario(){
	    		formInfo.loadStruct('../xml/forms/seminario_informacion_form.xml', function(){
	    			formInfo.setItemLabel('data','<bean:message key="title.info.general.seminario"/>');
	    			formInfo.setItemLabel('nombre','<bean:message key="label.nombre.seminario"/>');
	    			formInfo.setItemLabel('codigo','<bean:message key="label.codigo.seminario"/>');
	    			formInfo.setItemLabel('curso','<bean:message key="label.curso.seminario"/>');
	    			formInfo.setItemLabel('profesor','<bean:message key="label.profesor.seminario"/>');
	    			formInfo.setItemLabel('descripcion','<bean:message key="label.descripcion.seminario"/>');
	    			permisosForm();
	    			
		    		form.load('editarseminario.do?idSeminario=' + idSeminario, function () {			    			
		    			form.attachEvent("onButtonClick", function(id){
		    				if (id == "aceptar") {
			    				form.send("actualizarseminario.do?!nativeeditor_status=save&idSeminario=" + idSeminario ,"post", function(xml) {
			    					
			    				});
			    				buscar();
		    				}
		    			});
		    		});
	    		});
		    }
		    
		    
		    function buscar() {
		    	gridCursos.clearAndLoad("gridcursos.do");		    	
		    }
	    	
	    	function permisosForm(){
	    		<logic:notMatch scope="session" name="usuarioYPermisos" value="<permiso>1</permiso>" >    	
	    			formInfo.forEachItem(function(id){
	    				if(getType(id) == "input"){
	    			   		myForm.setReadOnly(id, true);
	    				}
	    				else if(getType(id) == "button"){
	    					formInfo.hideItem(id);
	    				}
	    			});
				</logic:notMatch>	
				
				
	    	}
		  
        </script>
	</head>
	<body>
	</body>
</html>