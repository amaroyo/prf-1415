package es.oyssen.mrm.struts.actions.usuarios;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.oyssen.mrm.struts.actions.MrmAction;

public class LogOutUsuarioAction extends MrmAction {

	@Override
	public ActionForward process(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//request.getSession().removeAttribute("toolbarObj");
		request.getSession().removeAttribute("usuarioYPermisos");		
		request.getSession().removeAttribute("idGrupoUsuario");
		/*request.getSession().removeAttribute("usuarioIdCanal");
		request.getSession().removeAttribute("usuarioIdDistribuidor");
		request.getSession().removeAttribute("usuarioIdComercial");*/
		request.getSession().removeAttribute("correo");
		request.getSession().removeAttribute("idUsuario");
		request.getSession().removeAttribute("anyo_academico");
		
		
		return mapping.findForward("success");
	}

}
