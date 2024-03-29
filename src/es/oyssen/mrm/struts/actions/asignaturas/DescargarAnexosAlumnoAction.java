package es.oyssen.mrm.struts.actions.asignaturas;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.oyssen.mrm.negocio.vo.AnexoVO;
import es.oyssen.mrm.negocio.vo.PortafolioVO;
import es.oyssen.mrm.negocio.vo.UsuarioVO;
import es.oyssen.mrm.struts.actions.MrmAction;


public class DescargarAnexosAlumnoAction extends MrmAction {

	@Override
	public ActionForward process(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		
		AnexoVO a = new AnexoVO();
		String idPortafolio = (String)request.getParameter("idPortafolio");
		a.setIdPortafolio(idPortafolio);
		
		PortafolioVO p = new PortafolioVO();
		p.setIdPortafolio(idPortafolio);
	
		//Lista con todos los casos clinicos de un portafolio
		List<AnexoVO> anexos =  getAnexosService().findAllByPortafolio(a);
		
		//Informacion de un alumno
		UsuarioVO u = getPortafoliosService().findAlumnoByPortafolio(p);
		
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		
		if(anexos != null){
			for (AnexoVO ax : anexos) {				
				if(ax.getAnexo()!=null){
					String[] nmbreArchivo = ax.getNombre().split("\\.");
					nmbreArchivo[0] = nmbreArchivo[0] + "_" + (ax.getFechaSubida()).replaceAll(" ", "_");
					nmbreArchivo[0] = nmbreArchivo[0].replaceAll("\\s","");
					String[] s = nmbreArchivo[0].split("\\.");
					ZipEntry entry = new ZipEntry(s[0].replaceAll(":", "_")+"." + nmbreArchivo[1]);
					entry.setSize(ax.getAnexo().length);
					zos.putNextEntry(entry);
					zos.write(ax.getAnexo());
					zos.closeEntry();
				}

			}
		}
		
		zos.close();
		byte[] zippedFile = baos.toByteArray();
	
		
		try{
			
			String nmbreArchivo = "Anexos_de_" + u.getApellido1() + "_" + u.getApellido2()+"_" + u.getNombre()+"_"+u.getDni();
			
			//**********************************************
			response.setHeader("Content-Disposition", "attachment; filename="+ nmbreArchivo +".zip");
	
			ServletOutputStream outputStream = response.getOutputStream();
			response.setContentType("application/zip");
			response.setContentLength(zippedFile.length);
			outputStream.write(zippedFile); 
			outputStream.flush();
			outputStream.close();
		
		} catch (Exception e2) {
			System.out.println("Error in " + getClass().getName() + "\n" + e2);
		}
		
		

		return mapping.findForward("success");
	}

}
