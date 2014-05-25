package es.oyssen.mrm.negocio.dao;

import java.util.List;

import es.oyssen.mrm.negocio.dao.exceptions.DAODeleteException;
import es.oyssen.mrm.negocio.dao.exceptions.DAOException;
import es.oyssen.mrm.negocio.dao.exceptions.DAOInsertException;
import es.oyssen.mrm.negocio.dao.exceptions.DAOUpdateException;
import es.oyssen.mrm.negocio.vo.SeminarioRealizadoVO;

public interface DAOSeminariosRealizados {

	public void insert(SeminarioRealizadoVO seminarioRealizado) throws DAOException, DAOInsertException;
	
	public void update(SeminarioRealizadoVO seminarioRealizado) throws DAOException, DAOUpdateException;
	
	public void delete(SeminarioRealizadoVO seminarioRealizado) throws DAOException, DAODeleteException;
		
	public List<SeminarioRealizadoVO> findAllByPortafolio(SeminarioRealizadoVO seminarioRealizado) throws DAOException;
	
	
	

}
