package es.oyssen.mrm.struts.actions.asignaturas;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.oyssen.mrm.negocio.vo.CasoClinicoVO;
import es.oyssen.mrm.struts.actions.MrmAction;
import es.oyssen.mrm.struts.forms.asignaturas.DescargarCasoClinicoForm;

public class DescargarCasoClinicoAction extends MrmAction {

	@Override
	public ActionForward process(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		CasoClinicoVO caso = new CasoClinicoVO();
		DescargarCasoClinicoForm f = (DescargarCasoClinicoForm) form;
		caso.setIdPortafolio(f.getIdPortafolio());
		caso.setIdCasoClinico(f.getIdCasoClinico());
	
		caso = getCasosClinicosService().findByIDs(caso);
		
		if(caso.getCasoClinico()!=null){ ///SE RALLA EL JSP!!!! CUANDO HAY UN CASO CLINICO IGUAL A NULL!!!!!! :@
			try{
				
					String[] sp = caso.getNombre().split("\\.");
				
					response.setHeader("Content-Disposition", "attachment; filename=CasoClinico(" + sp[0] + ")." + sp[1]);
			
					ServletOutputStream outputStream = response.getOutputStream();
					if (sp[0].equals("pdf")) response.setContentType("application/pdf");
					else if ((sp[0]).equals("doc")) response.setContentType("application/msword");
					else if ((sp[0]).equals("docx")) response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
					else response.setContentType("text/plain");
					
					
					response.setContentLength(caso.getCasoClinico().length);
					outputStream.write(caso.getCasoClinico()); 
					outputStream.flush();
					outputStream.close();
				
			
			} catch (Exception e2) {
				System.out.println("Error in " + getClass().getName() + "\n" + e2);
			}
		
		}

		return mapping.findForward("success");
	}

}
