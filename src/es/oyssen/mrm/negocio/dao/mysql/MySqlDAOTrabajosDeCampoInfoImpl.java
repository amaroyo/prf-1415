package es.oyssen.mrm.negocio.dao.mysql;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import es.oyssen.mrm.negocio.dao.DAOBase;
import es.oyssen.mrm.negocio.dao.DAOTrabajosDeCampoInfo;
import es.oyssen.mrm.negocio.dao.exceptions.DAODeleteException;
import es.oyssen.mrm.negocio.dao.exceptions.DAOException;
import es.oyssen.mrm.negocio.dao.exceptions.DAOInsertException;
import es.oyssen.mrm.negocio.dao.exceptions.DAOUpdateException;
import es.oyssen.mrm.negocio.dao.rowmappers.TrabajoDeCampoInfoMapper;
import es.oyssen.mrm.negocio.vo.TrabajoDeCampoInfoVO;
import es.oyssen.mrm.negocio.vo.TrabajoDeCampoVO;


public class MySqlDAOTrabajosDeCampoInfoImpl extends DAOBase implements DAOTrabajosDeCampoInfo{
	
	private static String SQL_INSERT = "insert into trabajos_de_campo_info (nombre, descripcion) values (?,?)";
	private static String SQL_UPDATE = "update trabajos_de_campo_info set nombre=?, enunciado=?, nombre_archivo=?, descripcion=?";
	private static String SQL_UPDATE_SIMPLE = "update trabajos_de_campo_info set nombre=?, descripcion=?";
	private static String SQL_UPDATE_DATES = "update trabajos_de_campo set fecha_limite=?";
	private static String SQL_DELETE = "delete from trabajos_de_campo_info where id_trabajo_info = ? ";
	private static String SQL_FIND_BY_ID = "select i.* from trabajos_de_campo_info as i where i.id_trabajo_info = ?";
	


	public String insert(final TrabajoDeCampoInfoVO trabajo) throws DAOException,
	DAOInsertException {
		try{
			KeyHolder kh = new GeneratedKeyHolder();
			getJdbcTemplate().update(new PreparedStatementCreator() {
				
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL_INSERT, new String[]{"id_trabajo_info"});
					ps.setString(1, trabajo.getNombre());
					ps.setString(2, trabajo.getDescripcion());
	
					return ps;
					
				}
			}
			,kh);
			return kh.getKey().toString();
			
		} catch (Exception e) {
			throw new DAOInsertException(e);
		}		
	}

	public void update(final TrabajoDeCampoInfoVO trabajo) throws DAOException,
	DAOUpdateException {
		try {
			 
			final String query = SQL_UPDATE + " where id_trabajo_info = ?";
			
			KeyHolder kh = new GeneratedKeyHolder();
			getJdbcTemplate().update(new PreparedStatementCreator() {
				
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn.prepareStatement(query, new String[]{""});
					ps.setString(1, trabajo.getNombre());
					
					InputStream datos = new ByteArrayInputStream(trabajo.getEnunciado());
					try {
						ps.setBinaryStream(2, datos, datos.available());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					ps.setString(3, trabajo.getNombreArchivo());
					ps.setString(4, trabajo.getDescripcion());
					ps.setString(5, trabajo.getIdTrabajoInfo());
	
					return ps;
					
				}
			}
			,kh);
					
		} catch(Exception e) {
			throw new DAOUpdateException(e);
		}

	}

	public void delete(TrabajoDeCampoInfoVO trabajo) throws DAOException,
	DAODeleteException {
		try {
			getJdbcTemplate().update(SQL_DELETE, new Object[]{trabajo.getIdTrabajoInfo()});
		} catch (Exception e) {
			throw new DAODeleteException(e);
		}

	}

	@Override
	public TrabajoDeCampoInfoVO findById(TrabajoDeCampoInfoVO trabajo) throws DAOException {
		try {
			return (TrabajoDeCampoInfoVO) getJdbcTemplate().queryForObject(SQL_FIND_BY_ID, new Object[]{trabajo.getIdTrabajoInfo()}, new TrabajoDeCampoInfoMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void updateSimple(TrabajoDeCampoInfoVO t) throws DAOException,
			DAOUpdateException {
		try {
			 
			String query = SQL_UPDATE_SIMPLE;

			query += " where id_trabajo_info=?";

			getJdbcTemplate().update(query, new Object[]{
					t.getNombre(),
					t.getDescripcion(),
					t.getIdTrabajoInfo()});
		} catch(Exception e) {
			throw new DAOUpdateException(e);
		}
		
	}

	@Override
	public void updateDates(TrabajoDeCampoVO t) throws DAOException,
			DAOUpdateException {
		try {
			 
			String query = SQL_UPDATE_DATES;

			query += " where id_trabajo_info=?";

			getJdbcTemplate().update(query, new Object[]{
					t.getFechaLimite(),
					t.getIdTrabajoInfo()});
		} catch(Exception e) {
			throw new DAOUpdateException(e);
		}
	}
	
	

}


