package es.oyssen.mrm.struts.forms.asignaturas;

import es.oyssen.mrm.struts.forms.dhtmlx.DhtmlxGridForm;

public class GridTrabajosCampoUsuarioAsignaturaForm extends DhtmlxGridForm {



	private String idAlumno;
	private String idAsignatura;
	private String nombreTrabajoCampo;
	
	
	public String getIdAlumno() {
		return idAlumno;
	}
	public void setIdAlumno(String idUsuario) {
		this.idAlumno = idUsuario;
	}
	public String getIdAsignatura() {
		return idAsignatura;
	}
	public void setIdAsignatura(String idAsignatura) {
		this.idAsignatura = idAsignatura;
	}
	public String getNombreTrabajoCampo() {
		return nombreTrabajoCampo;
	}
	public void setNombreTrabajoCampo(String nombreTrabajoCampo) {
		this.nombreTrabajoCampo = nombreTrabajoCampo;
	}
	


	

	
}