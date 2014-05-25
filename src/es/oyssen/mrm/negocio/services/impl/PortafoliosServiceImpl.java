package es.oyssen.mrm.negocio.services.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.oyssen.mrm.negocio.dao.DAOPortafolios;
import es.oyssen.mrm.negocio.dao.exceptions.DAOException;
import es.oyssen.mrm.negocio.exceptions.ServiceException;
import es.oyssen.mrm.negocio.services.PortafoliosService;
import es.oyssen.mrm.negocio.vo.PortafolioVO;

public class PortafoliosServiceImpl implements PortafoliosService{
	
	private static Log log = LogFactory.getLog(PortafoliosServiceImpl.class);
	private DAOPortafolios daoPortafolios;

	
	public void setDaoPortafolios(DAOPortafolios daoPortafolios) {
		this.daoPortafolios = daoPortafolios;
	}
	
	public void insert(PortafolioVO portafolio)
			throws es.oyssen.mrm.negocio.exceptions.ServiceException,
			es.oyssen.mrm.negocio.dao.exceptions.DAOException {
		try {
			daoPortafolios.insert(portafolio);	
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error creando portafolio", e);
			throw new ServiceException(e);
		}		
	}

	public void update(PortafolioVO portafolio)
			throws es.oyssen.mrm.negocio.exceptions.ServiceException,
			es.oyssen.mrm.negocio.dao.exceptions.DAOException {
		try {
			daoPortafolios.update(portafolio);
		} catch (DAOException e) {
			e.printStackTrace();
			log.error("Error haciendo update portafolio", e);
			throw new ServiceException(e);
		}		
	}

	public void delete(PortafolioVO portafolio)
			throws es.oyssen.mrm.negocio.exceptions.ServiceException,
			es.oyssen.mrm.negocio.dao.exceptions.DAOException {
		try {			
			daoPortafolios.delete(portafolio);			
		} catch (DAOException e) {
			e.printStackTrace();
			log.error("Error eliminando portafolio", e);
			throw new ServiceException(e);
		}			
	}

	public List<PortafolioVO> findAll(PortafolioVO portafolio) throws ServiceException {
		try {
			return daoPortafolios.findAll(portafolio);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
	
	

	public PortafolioVO findById(PortafolioVO portafolio)
			throws ServiceException {
		try {
			return daoPortafolios.findById(portafolio);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	

	
	public List<PortafolioVO> findByAlumno(PortafolioVO portafolio)  throws ServiceException {
		try {
			return daoPortafolios.findByAlumno(portafolio);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	public List<PortafolioVO> findByAsignatura(PortafolioVO portafolio)  throws ServiceException {
		try {
			return daoPortafolios.findByAsignatura(portafolio);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
	
	public List<PortafolioVO> findByAnyoAcademico(PortafolioVO portafolio)  throws ServiceException {
		try {
			return daoPortafolios.findByAnyoAcademico(portafolio);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
	
}