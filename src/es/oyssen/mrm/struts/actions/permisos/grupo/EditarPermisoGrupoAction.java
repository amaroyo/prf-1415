package es.oyssen.mrm.struts.actions.permisos.grupo;

import es.oyssen.mrm.negocio.vo.GrupoPermisoVO;
import es.oyssen.mrm.negocio.vo.UsuarioPermisosVO;
import es.oyssen.mrm.struts.actions.dhtmlx.DHTMLXFormAction;
import es.oyssen.mrm.struts.forms.dhtmlx.DhtmlxForm;
import es.oyssen.mrm.struts.forms.permisos.grupo.PermisosGrupoForm;
import es.oyssen.mrm.util.StringUtil;

public class EditarPermisoGrupoAction extends DHTMLXFormAction {

	@Override
	public Object load(DhtmlxForm f) throws Exception {
		return null;
	}

	@Override
	public String save(DhtmlxForm f) throws Exception {
		
		
		PermisosGrupoForm form = (PermisosGrupoForm) f;
		
		if(!StringUtil.isNullOrBlank(form.getIdGrupo())){
			GrupoPermisoVO permisoGrupo = new GrupoPermisoVO();
			permisoGrupo.setIdPermiso(form.getIdPermiso());
			permisoGrupo.setIdGrupo(form.getIdGrupo());
	
			getGrupoPermisosService().insert(permisoGrupo);
		}
		
		else if (!StringUtil.isNullOrBlank(form.getIdUsuario())){
			UsuarioPermisosVO up = new UsuarioPermisosVO();
			up.setIdUsuario(form.getIdUsuario());
			up.setIdPermiso(form.getIdPermiso());
			getUsuariosPermisosService().insert(up);
		}
		
		return "";
	}

	@Override
	public String parseXML(Object o) throws Exception {
		GrupoPermisoVO c = (GrupoPermisoVO) o;
		StringBuffer sb = new StringBuffer();
		sb.append("<data>");
		sb.append("<idPermiso><![CDATA[" + c.getIdPermiso() + "]]></idPermiso>");
		sb.append("<idGrupo><![CDATA[" + c.getIdGrupo() + "]]></idGrupo>");
		sb.append("</data>");
		
		return sb.toString();
	}

	@Override
	public String create(DhtmlxForm f) throws Exception {
		// TODO Auto-generated method stub
		return null;
		
	}

}
