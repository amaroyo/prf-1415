package es.oyssen.mrm.struts.actions.administrar;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.oyssen.mrm.negocio.exceptions.ServiceException;
import es.oyssen.mrm.negocio.vo.ErrorLogVO;
import es.oyssen.mrm.negocio.vo.UsuarioVO;
import es.oyssen.mrm.struts.actions.MrmAction;

public class CerrarCursoAcademicoAction extends MrmAction {

	@Override
	public ActionForward process(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
		String mensaje="";
		ErrorLogVO e = new ErrorLogVO();
		e.setIdError("1");
		String act="";
		
		try {
			e = getErroresLogService().findAnyoAcademico(e);
			String fecha = e.getFecha();
			String[] sp = fecha.split(" ");
			String[] s = sp[0].split("-");
			act = s[0] + "/" + Integer.toString(Integer.parseInt(s[0])+1);
		} catch (ServiceException e1) {
			String actual = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			String pasado = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)-1);
			act = pasado + "/" + actual;
			//Ha habido un error cerrando el curso academico.
			mensaje="0";
		}
		
		
		String spp[] = act.split("/");
		int izq = Integer.parseInt(spp[0]) + 1;
		int der = Integer.parseInt(spp[1]) + 1;
		
		if (izq - Calendar.getInstance().get(Calendar.YEAR) <= 1){
			//Se ha cerrado satisfactoriamente el curso academico.
			mensaje="1";
			request.getSession().setAttribute("anyoAcademico", izq + "/" + der);
			e.setFecha(izq + "-01-01 00:00:01");
			getErroresLogService().updateAnyo(e);
			
			List<UsuarioVO> list = getUsuariosService().findIndefinidos();
			
			if(!list.isEmpty()){
				for (UsuarioVO u : list){
					getUsuariosService().delete(u);
				}
			}
			
		}
		else {
			//Ya ha cerrado el curso academico. El proximo cierre de curso se realizara al anyo que siguiente.
			mensaje = "2";
		}
		
		
		response.setContentType("text/xml;charset=utf-8");
		PrintWriter out;
	    out = response.getWriter();
	    out.print(parseXML(mensaje));

		
		out.flush();
		out.close();
		return mapping.findForward("success");
	}
	
	
	public String parseXML(Object o) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("<cambio>");
		
		if (o != null){
			String t = (String) o;			
			sb.append("<nombre><![CDATA[" + t  + "]]></nombre>");
			
		}
		
		sb.append("</cambio>");
		return sb.toString();
	}

}
