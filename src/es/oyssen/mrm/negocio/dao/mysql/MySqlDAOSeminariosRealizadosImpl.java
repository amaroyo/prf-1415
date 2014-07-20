package es.oyssen.mrm.negocio.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import es.oyssen.mrm.negocio.dao.DAOBase;
import es.oyssen.mrm.negocio.dao.DAOSeminariosRealizados;
import es.oyssen.mrm.negocio.dao.exceptions.DAODeleteException;
import es.oyssen.mrm.negocio.dao.exceptions.DAOException;
import es.oyssen.mrm.negocio.dao.exceptions.DAOInsertException;
import es.oyssen.mrm.negocio.dao.exceptions.DAOUpdateException;
import es.oyssen.mrm.negocio.dao.rowmappers.SeminarioRealizadoMapper;
import es.oyssen.mrm.negocio.dao.rowmappers.UsuarioAnyoSeminarioMapper;
import es.oyssen.mrm.negocio.dao.rowmappers.UsuarioMapper;
import es.oyssen.mrm.negocio.vo.SeminarioRealizadoVO;
import es.oyssen.mrm.negocio.vo.UsuarioAnyoSeminarioVO;
import es.oyssen.mrm.negocio.vo.UsuarioVO;


public class MySqlDAOSeminariosRealizadosImpl extends DAOBase implements DAOSeminariosRealizados{

	private static String SQL_INSERT = "insert into seminarios_realizados (id_seminario,id_portafolio) values (?,?)";
	private static String SQL_UPDATE = "";
	private static String SQL_DELETE = "delete from seminarios_realizados where id_portafolio = ? and id_seminario = ?";
	private static String SQL_FIND_BY_PORTAFOLIO = "select * from seminarios_realizados where id_portafolio = ?";
	private static String SQL_FIND_USERS_BY_PORTAFOLIO = "select u.id_usuario, u.correo, u.nombre, u.apellido1, u.apellido2, u.dni, u.telefono, p.anyo_academico "+
															"from usuarios as u, portafolios as p, seminarios_realizados as s " +
															"where u.id_usuario=p.id_alumno and p.id_portafolio=s.id_portafolio and s.id_seminario=?";


	
	

	public void insert(final SeminarioRealizadoVO seminarioRealizado) throws DAOException,
	DAOInsertException {
		try{
			
			getJdbcTemplate().update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL_INSERT, new String[]{});
					ps.setString(1, seminarioRealizado.getIdPortafolio());
					ps.setString(2, seminarioRealizado.getIdSeminario());
					return ps;

				}
			}
			);
			

		} catch (Exception e) {
			throw new DAOInsertException(e);
		}			
	}

	public void update(SeminarioRealizadoVO seminarioRealizado) throws DAOException,
	DAOUpdateException {
		try {
			 
			String query = SQL_UPDATE;

		} catch(Exception e) {
			throw new DAOUpdateException(e);
		}

	}

	public void delete(SeminarioRealizadoVO seminarioRealizado) throws DAOException,
	DAODeleteException {
		try {
			getJdbcTemplate().update(SQL_DELETE, new Object[]{seminarioRealizado.getIdSeminario(),seminarioRealizado.getIdPortafolio()});
		} catch (Exception e) {
			throw new DAODeleteException(e);
		}

	}


	public List<SeminarioRealizadoVO> findAllByPortafolio(SeminarioRealizadoVO seminarioRealizado) throws DAOException {
		try {
			return getJdbcTemplate().query(SQL_FIND_BY_PORTAFOLIO, new Object[]{seminarioRealizado.getIdPortafolio()}, new SeminarioRealizadoMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	
	public List<UsuarioAnyoSeminarioVO> findAllUsersByPortafolio(SeminarioRealizadoVO seminarioRealizado) throws DAOException {
		try {
			return getJdbcTemplate().query(SQL_FIND_USERS_BY_PORTAFOLIO, new Object[]{seminarioRealizado.getIdSeminario()}, new UsuarioAnyoSeminarioMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

}


