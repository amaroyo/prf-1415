package es.oyssen.mrm.negocio.services.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.oyssen.mrm.negocio.dao.DAOUsuariosPermisos;
import es.oyssen.mrm.negocio.dao.exceptions.DAOException;
import es.oyssen.mrm.negocio.exceptions.ServiceException;
import es.oyssen.mrm.negocio.services.UsuariosPermisosService;
import es.oyssen.mrm.negocio.vo.PermisoVO;
import es.oyssen.mrm.negocio.vo.UsuarioPermisosVO;
import es.oyssen.mrm.negocio.vo.UsuarioVO;


public class UsuariosPermisosServiceImpl implements UsuariosPermisosService{
	
	private static Log log = LogFactory.getLog(UsuariosPermisosServiceImpl.class);

	private DAOUsuariosPermisos daoUsuariosPermisos;
	
	
	public void setDaoUsuariosPermisos(DAOUsuariosPermisos daoUsuariosPermisos) {
		this.daoUsuariosPermisos = daoUsuariosPermisos;
	}
	
	
	public List<PermisoVO> findByUsuario(UsuarioVO usuario)
			throws ServiceException {
		try {
			return daoUsuariosPermisos.findByUsuario(usuario);
		} catch (DAOException e) {
			e.printStackTrace();
			log.error("Error findByUsuario usuario permiso", e);
			throw new ServiceException(e);
		}
	}

	public void delete(UsuarioPermisosVO usuarioPermiso) throws ServiceException {
		try {
			daoUsuariosPermisos.delete(usuarioPermiso);
		} catch (DAOException e) {
			e.printStackTrace();
			log.error("Error eliminando el permiso del usuario", e);
			throw new ServiceException(e);
		}		
	}

	public void insert(UsuarioPermisosVO usuarioPermiso) throws ServiceException {
		try {
			daoUsuariosPermisos.insert(usuarioPermiso);	
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error creando el permiso para el usuario", e);
			throw new ServiceException(e);
		}
	}


	@Override
	public List<PermisoVO> findRestantes(UsuarioPermisosVO up)
			throws ServiceException {
		try {
			return daoUsuariosPermisos.findRestantes(up);
		} catch (DAOException e) {
			e.printStackTrace();
			log.error("Error findRestantes usuario permiso", e);
			throw new ServiceException(e);
		}
	}			
	
}
