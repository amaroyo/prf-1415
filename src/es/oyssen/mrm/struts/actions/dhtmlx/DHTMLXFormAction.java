package es.oyssen.mrm.struts.actions.dhtmlx;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import es.oyssen.mrm.struts.actions.MrmAction;
import es.oyssen.mrm.struts.forms.dhtmlx.DhtmlxForm;

public abstract class DHTMLXFormAction extends MrmAction {
	
	public static final String RESULTADO_ERROR = "error";
	protected String anyoAcademico;
	protected String idGrupoUsuario;
	protected String idUsuario;

	public ActionForward process(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		anyoAcademico = (String) request.getSession().getAttribute("anyoAcademico");
		idUsuario = (String) request.getSession().getAttribute("idUsuario");
		idGrupoUsuario = (String) request.getSession().getAttribute("idGrupoUsuario");
		
		MessageResources resources = getResources(request);
		DhtmlxForm f = (DhtmlxForm) form;
		f.setNativeeditor_status(request.getParameter("!nativeeditor_status"));
		
		
			StringBuffer xml = new StringBuffer();			
			try {
				if ("save".equals(f.getNativeeditor_status()) ||
						"update".equals(f.getNativeeditor_status())) {
					xml.append(save(f));
				}
				else if ("create".equals(f.getNativeeditor_status())){
					xml.append(create(f));
				}
				else {
					xml.append(parseXML(load(f)));
				}

			} catch (Exception e) {

				e.printStackTrace();
				xml.append("<data>");
				xml.append("<action type='" + RESULTADO_ERROR + "'><![CDATA[" + resources.getMessage("error.operacion.ko") + "]]></action>");
				xml.append("</data>");
			}
			
			writeXML(response, xml.toString());

			return mapping.findForward("success");
	}

	protected void writeXML(HttpServletResponse response,
			String xmlGrid) throws IOException,
			UnsupportedEncodingException {
		response.setContentType("text/xml;charset=utf-8");
		ServletOutputStream sos = response.getOutputStream();
		sos.write(xmlGrid.getBytes("UTF-8"));
		sos.flush();
		sos.close();
	}

	public abstract Object load(DhtmlxForm f) throws Exception;
	
	public abstract String save(DhtmlxForm f) throws Exception;
	
	public abstract String create(DhtmlxForm f) throws Exception;	
	
	public abstract String parseXML(Object o) throws Exception;

}
