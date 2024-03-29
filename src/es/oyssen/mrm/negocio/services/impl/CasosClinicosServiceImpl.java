package es.oyssen.mrm.negocio.services.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.oyssen.mrm.negocio.dao.DAOCasosClinicos;
import es.oyssen.mrm.negocio.dao.exceptions.DAOException;
import es.oyssen.mrm.negocio.exceptions.ServiceException;
import es.oyssen.mrm.negocio.services.CasosClinicosService;
import es.oyssen.mrm.negocio.vo.CasoClinicoVO;
import es.oyssen.mrm.struts.forms.asignaturas.SubirArchivoForm;

public class CasosClinicosServiceImpl implements CasosClinicosService{
	
	private static Log log = LogFactory.getLog(CasosClinicosServiceImpl.class);
	private DAOCasosClinicos daoCasosClinicos;

	
	public void setDaoCasosClinicos(DAOCasosClinicos daoCasosClinicos) {
		this.daoCasosClinicos = daoCasosClinicos;
	}
	
	/*public void insert(CasoClinicoVO casoClinico)
			throws es.oyssen.mrm.negocio.exceptions.ServiceException,
			es.oyssen.mrm.negocio.dao.exceptions.DAOException {
		try {
			daoCasosClinicos.insert(casoClinico);	
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error creando caso clinico", e);
			throw new ServiceException(e);
		}		
	}*/

	public void update(CasoClinicoVO casoClinico) 
			throws es.oyssen.mrm.negocio.exceptions.ServiceException,
			es.oyssen.mrm.negocio.dao.exceptions.DAOException {
		try {
			daoCasosClinicos.update(casoClinico);
		} catch (DAOException e) {
			e.printStackTrace();
			log.error("Error update caso clinico", e);
			throw new ServiceException(e);
		}		
	}

	public void delete(CasoClinicoVO casoClinico)
			throws es.oyssen.mrm.negocio.exceptions.ServiceException,
			es.oyssen.mrm.negocio.dao.exceptions.DAOException {
		try {			
			daoCasosClinicos.delete(casoClinico);			
		} catch (DAOException e) {
			e.printStackTrace();
			log.error("Error delete caso clinico", e);
			throw new ServiceException(e);
		}			
	}

	
	
	public List<CasoClinicoVO> findAllByPortafolio(CasoClinicoVO casoClinico) throws ServiceException {
		try {
			return daoCasosClinicos.findAllByPortafolio(casoClinico);
		} catch (DAOException e) {
			e.printStackTrace();
			log.error("Error findAllByPortafolio caso clinico", e);
			throw new ServiceException(e.getMessage());
		}
	}


	public void process(SubirArchivoForm f) throws ServiceException, 
				es.oyssen.mrm.negocio.dao.exceptions.DAOException{
		try {
			log.debug("Procesamos fichero.........");
			
			CasoClinicoVO caso = new CasoClinicoVO();
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			//get current date time with Date()dateFormat.format(date)
			Date date = new Date();
			
			String n = f.getFichero().getFileName();
			String[] sp = n.split("\\.");
			
			if(f.getNombre().equals("")){	
				caso.setNombre(sp[0]  + "." + sp[1].toLowerCase());
			}
			else caso.setNombre(f.getNombre() + "." + sp[1].toLowerCase());
			caso.setCasoClinico(f.getFichero().getFileData());
			
			
			caso.setFechaSubida(dateFormat.format(date));
			caso.setIdPortafolio(f.getIdPortafolio());
			daoCasosClinicos.insert(caso);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error procesando fichero SubirArchivoCasoClinicoForm", e);
			throw new ServiceException(e);
		}
		
	}

	@Override
	public CasoClinicoVO findByIDs(CasoClinicoVO caso) throws ServiceException {
		try {
			return daoCasosClinicos.findByIDs(caso);
		} catch (DAOException e) {
			e.printStackTrace();
			log.error("Error findByIDs caso clinico", e);
			throw new ServiceException(e.getMessage());
		}
	}
	
	
	
}
