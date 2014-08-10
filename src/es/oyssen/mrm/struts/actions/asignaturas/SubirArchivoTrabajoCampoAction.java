package es.oyssen.mrm.struts.actions.asignaturas;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.oyssen.mrm.negocio.vo.PortafolioVO;
import es.oyssen.mrm.negocio.vo.TrabajoDeCampoInfoVO;
import es.oyssen.mrm.struts.actions.MrmAction;
import es.oyssen.mrm.struts.forms.asignaturas.SubirArchivoCasoClinicoForm;
import es.oyssen.mrm.struts.forms.asignaturas.SubirArchivoTrabajoCampoForm;
import es.oyssen.mrm.struts.forms.ficheros.SubirFicheroForm;

public class SubirArchivoTrabajoCampoAction extends MrmAction {

	private static final int MAX_SIZE_MYSQL = 1048576; //max_allowed_packet=1M / to be changed in my.ini
	
	
	@Override
	public ActionForward process(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		SubirArchivoTrabajoCampoForm f = (SubirArchivoTrabajoCampoForm) form;	
		String nombre = f.getFichero().getFileName();
		String[] sp = nombre.split("\\.");
		if(((sp[1].toLowerCase()).equals("pdf") || (sp[1].toLowerCase()).equals("doc") || (sp[1].toLowerCase()).equals("docx")) && f.getFichero().getFileSize()<MAX_SIZE_MYSQL) { 

			
			getTrabajosDeCampoInfoService().process(f);
			return mapping.findForward("success");
		}
		
		return mapping.findForward("error");
	}

}
