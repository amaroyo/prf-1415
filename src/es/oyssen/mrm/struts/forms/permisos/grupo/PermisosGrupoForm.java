package es.oyssen.mrm.struts.forms.permisos.grupo;

import es.oyssen.mrm.struts.forms.dhtmlx.DhtmlxForm;

public class PermisosGrupoForm extends DhtmlxForm {

	private String idPermiso;
	private String idGrupo;
	private String idUsuario;
	
	
	public String getIdPermiso() {
		return idPermiso;
	}
	
	public void setIdPermiso(String idPermiso) {
		this.idPermiso = idPermiso;
	}
	
	public String getIdGrupo() {
		return idGrupo;
	}
	
	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	} 
	
	

}
