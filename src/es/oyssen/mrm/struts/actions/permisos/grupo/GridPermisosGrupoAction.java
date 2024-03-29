package es.oyssen.mrm.struts.actions.permisos.grupo;

import es.oyssen.mrm.negocio.vo.GrupoPermisoVO;
import es.oyssen.mrm.negocio.vo.GrupoVO;
import es.oyssen.mrm.negocio.vo.UsuarioVO;
import es.oyssen.mrm.struts.actions.dhtmlx.DHTMLXGridAction;
import es.oyssen.mrm.struts.forms.dhtmlx.DhtmlxGridForm;
import es.oyssen.mrm.struts.forms.permisos.grupo.GridPermisosGrupoForm;
import es.oyssen.mrm.util.UtilXML;

public class GridPermisosGrupoAction extends DHTMLXGridAction {

	@Override
	public String search(DhtmlxGridForm f) throws Exception {
		GridPermisosGrupoForm form = (GridPermisosGrupoForm) f;
		GrupoVO grupo = new GrupoVO();
		if (form.getIdGrupo() != null){
			grupo.setIdGrupo(form.getIdGrupo());
		} else {
			UsuarioVO usuario = new UsuarioVO();
			usuario.setIdUsuario(form.getIdUsuario());			
			usuario = getUsuariosService().findById(usuario);			
			grupo.setIdGrupo(usuario.getIdGrupo());			
		}
		return UtilXML.buildXmlGridPermisosGrupo(getGrupoPermisosService().findByGrupo(grupo),grupo);
	}

	@Override
	public void insert(DhtmlxGridForm f) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(DhtmlxGridForm f) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(DhtmlxGridForm f) throws Exception {
		GrupoPermisoVO permisoGrupo = new GrupoPermisoVO();
		
		String[] sp = f.getGr_id().split("-");
		permisoGrupo.setIdPermiso(sp[0]);
		permisoGrupo.setIdGrupo(sp[1]);
		getGrupoPermisosService().delete(permisoGrupo);
	}

}
