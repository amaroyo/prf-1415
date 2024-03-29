package es.oyssen.mrm.negocio.services;

import java.util.List;

import es.oyssen.mrm.negocio.exceptions.ServiceException;
import es.oyssen.mrm.negocio.vo.PermisoVO;
import es.oyssen.mrm.negocio.vo.UsuarioPermisosVO;
import es.oyssen.mrm.negocio.vo.UsuarioVO;


public interface UsuariosPermisosService {
	
	public List<PermisoVO> findByUsuario(UsuarioVO usuario) throws ServiceException;
	
	public void insert(UsuarioPermisosVO usuarioPermiso) throws ServiceException;
	
	public void delete(UsuarioPermisosVO usuarioPermiso) throws ServiceException;

	public List<PermisoVO> findRestantes(UsuarioPermisosVO up) throws ServiceException;

}
