package es.oyssen.mrm.struts.actions.asignaturas;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import es.oyssen.mrm.negocio.vo.PuntuacionCriterioVO;
import es.oyssen.mrm.struts.actions.dhtmlx.DHTMLXFormAction;
import es.oyssen.mrm.struts.forms.asignaturas.NotasRubricaForm;
import es.oyssen.mrm.struts.forms.dhtmlx.DhtmlxForm;

public class NotasRubricaAction extends DHTMLXFormAction {

	@Override
	public Object load(DhtmlxForm f) throws Exception {
		
		NotasRubricaForm form = (NotasRubricaForm) f;
		PuntuacionCriterioVO puntuacionCriterio = new PuntuacionCriterioVO();
		
		//if (!StringUtil.isNullOrBlank(form.getIdAsignatura())){
			puntuacionCriterio.setIdPortafolio(form.getIdPortafolio());
			return getPuntuacionCriteriosService().findAllByPortafolio(puntuacionCriterio);
			
		//} 
	}

	@Override
	public String create(DhtmlxForm f) throws Exception {
		
		return null;
	}
	
	@Override
	public String save(DhtmlxForm f) throws Exception {
		NotasRubricaForm form = (NotasRubricaForm) f;
		PuntuacionCriterioVO puntuacionCriterio = new PuntuacionCriterioVO();
		puntuacionCriterio.setIdPortafolio(form.getIdPortafolio());
		
		Iterator it = form.getValues().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry)it.next();
			puntuacionCriterio.setIdCriterio((String) e.getKey());
			puntuacionCriterio.setNota((String) e.getValue());
			getPuntuacionCriteriosService().update(puntuacionCriterio);
		}
		return "notas changed";
	}
	
	@Override
	public String parseXML(Object o) throws Exception {
		List<PuntuacionCriterioVO> c = (List<PuntuacionCriterioVO>) o;
		StringBuffer sb = new StringBuffer();
		sb.append("<data>");
		Iterator it = c.iterator();
		while(it.hasNext()){
			PuntuacionCriterioVO puntuacionCriterio = (PuntuacionCriterioVO) it.next();
			String idCriterio = puntuacionCriterio.getIdCriterio();
			String nota = puntuacionCriterio.getNota();
			sb.append("<criterio>");
			sb.append("<idCriterio><![CDATA[" + idCriterio + "]]></idCriterio>");
			sb.append("<nota><![CDATA[" + nota + "]]></nota>");
			sb.append("</criterio>");
		}
		sb.append("</data>");
		return sb.toString();
	}

}