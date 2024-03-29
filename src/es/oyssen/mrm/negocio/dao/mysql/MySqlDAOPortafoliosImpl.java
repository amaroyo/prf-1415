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
import es.oyssen.mrm.negocio.dao.DAOPortafolios;
import es.oyssen.mrm.negocio.dao.exceptions.DAODeleteException;
import es.oyssen.mrm.negocio.dao.exceptions.DAOException;
import es.oyssen.mrm.negocio.dao.exceptions.DAOInsertException;
import es.oyssen.mrm.negocio.dao.exceptions.DAOUpdateException;
import es.oyssen.mrm.negocio.dao.rowmappers.AnyoMapper;
import es.oyssen.mrm.negocio.dao.rowmappers.CasoClinicoMapper;
import es.oyssen.mrm.negocio.dao.rowmappers.DatosUsuarioEstanciaUnidadClinicaMapper;
import es.oyssen.mrm.negocio.dao.rowmappers.DiarioReflexivoMapper;
import es.oyssen.mrm.negocio.dao.rowmappers.PortafolioMapper;
import es.oyssen.mrm.negocio.dao.rowmappers.TrabajoDeCampoMapper;
import es.oyssen.mrm.negocio.dao.rowmappers.UsuarioEstanciaUnidadClinicaMapper;
import es.oyssen.mrm.negocio.dao.rowmappers.UsuarioMapper;
import es.oyssen.mrm.negocio.dao.rowmappers.UsuarioPortafolioMapper;
import es.oyssen.mrm.negocio.vo.CasoClinicoVO;
import es.oyssen.mrm.negocio.vo.DatosUsuarioEstanciaUnidadClinicaVO;
import es.oyssen.mrm.negocio.vo.DiarioReflexivoVO;
import es.oyssen.mrm.negocio.vo.PortafolioVO;
import es.oyssen.mrm.negocio.vo.TrabajoDeCampoVO;
import es.oyssen.mrm.negocio.vo.UsuarioEstanciaUnidadClinicaVO;
import es.oyssen.mrm.negocio.vo.UsuarioPortafolioVO;
import es.oyssen.mrm.negocio.vo.UsuarioVO;


public class MySqlDAOPortafoliosImpl extends DAOBase implements DAOPortafolios{
	
	private static String SQL_INSERT = "insert into portafolios (id_alumno, id_profesor, id_asignatura, anyo_academico) values (?,?,?,?)";
	private static String SQL_UPDATE = "update portafolios set id_profesor=?";
	private static String SQL_UPDATE_ESTANCIA = "update estancias_unidad_clinica set centro_asociado=?, unidad_clinica=?, turno=?, fecha_inicio=?, fecha_fin=?";
	private static String SQL_DELETE = "delete from portafolios where id_portafolio = ?";
	private static String SQL_FIND_ALL = "select * from portafolios where anyo_academico = ?";
	private static String SQL_FIND_BY_ID = "select * from portafolios where id_portafolio = ?";
	private static String SQL_FIND_BY_ALUMNO_ASIGNATURA = "select * from portafolios where id_alumno=? and id_asignatura=? and anyo_academico=?";
	private static String SQL_FIND_BY_ALUMNO = "select * from portafolios where id_alumno = ? and anyo_academico = ?";
	private static String SQL_FIND_BY_PROFESOR = "select * from portafolios where id_profesor = ? and anyo_academico = ?";
	private static String SQL_FIND_BY_ASIGNATURA = "select * from portafolios where id_asignatura = ? and anyo_academico = ?";
	private static String SQL_FIND_BY_ANYO_ACADEMICO = "select * from portafolios where anyo_academico = ?";
	private static String SQL_FIND_ALUMNO_BY_PORTAFOLIO = "select u.* from usuarios as u, portafolios as p where u.id_usuario=p.id_alumno and p.id_portafolio=?";
	private static String SQL_FIND_USUARIOS_ESTANCIA_UNIDAD_CLINICA = "select u.id_usuario, u.nombre, u.apellido1, u.apellido2, u.dni, e.centro_asociado, " +
																		"e.unidad_clinica, e.turno, p.id_profesor " +
																		"from portafolios as p, usuarios as u, estancias_unidad_clinica as e " +
																		"where p.id_alumno = u.id_usuario and p.id_portafolio = e.id_portafolio and " +
																		"p.id_asignatura = ? and p.anyo_academico = ?";

	private static String SQL_FIND_DATOS_USUARIOS_ESTANCIA_UNIDAD_CLINICA = "select p.id_portafolio, e.id_estancia_unidad, e.centro_asociado, e.unidad_clinica, e.turno, e.fecha_inicio, e.fecha_fin, " +
			"u.nombre, u.apellido1, u.apellido2 " +
			"from portafolios as p, usuarios as u, estancias_unidad_clinica as e " +
			"where p.id_profesor = u.id_usuario and p.id_portafolio = e.id_portafolio and " +
			"p.id_asignatura = ? and p.anyo_academico = ? and p.id_alumno = ? ";
	
	private static String SQL_FIND_USUARIOS_BY_ASIGNATURA = "select u.id_usuario, u.correo, u.nombre, u.apellido1, u.apellido2, u.dni, u.telefono, p.id_portafolio "+
																"from portafolios as p, usuarios as u " + 
																"where p.id_alumno = u.id_usuario and p.id_asignatura=? and p.anyo_academico=?";
	
	
	private static String SQL_FIND_TRABAJOS_BY_PORTAFOLIO = "select t.*, i.* " +
															"from trabajos_de_campo as t, portafolios as p, trabajos_de_campo_info as i " +
															"where t.id_portafolio = p.id_portafolio and t.id_trabajo_info = i.id_trabajo_info and " +
															"p.id_alumno=? and p.id_asignatura=? and p.anyo_academico=?";
	
	private static String SQL_FIND_CASOS_BY_PORTAFOLIO = "select c.* " +
															"from casos_clinicos as c, portafolios as p " +
															"where c.id_portafolio = p.id_portafolio and " +
															"p.id_alumno=? and p.id_asignatura=? and p.anyo_academico=?";

	private static String SQL_FIND_DIARIOS_BY_PORTAFOLIO = "select d.* " +
															"from diarios_reflexivos as d, portafolios as p " +
															"where d.id_portafolio = p.id_portafolio and " +
															"p.id_alumno=? and p.id_asignatura=? and p.anyo_academico=?";

	
	private static String SQL_FIND_ANYOS = "select distinct anyo_academico from portafolios";
	
	
	private static String SQL_FIND_ALUMNOS_POTENCIALES = "select distinct p1.id_alumno, p1.id_portafolio, u.nombre, u.apellido1, u.apellido2, u.dni, u.correo, u.telefono, u.id_usuario "+
															"from portafolios as p1, usuarios as u "+
															"where p1.id_alumno = u.id_usuario and 7 = (select count(distinct p2.id_asignatura) "+
															"from portafolios as p2 "+
															"where p1.id_alumno = p2.id_alumno)";
	
	
	@Override
	public DatosUsuarioEstanciaUnidadClinicaVO findDatosUsuarioEstanciaUnidadClinica(
			PortafolioVO portafolio) throws DAOException {
		
		try {
			
			return (DatosUsuarioEstanciaUnidadClinicaVO) getJdbcTemplate().queryForObject(SQL_FIND_DATOS_USUARIOS_ESTANCIA_UNIDAD_CLINICA, new Object[]{portafolio.getIdAsignatura(), portafolio.getAnyoAcademico(), portafolio.getIdAlumno()}, new DatosUsuarioEstanciaUnidadClinicaMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}
		
		
		
	}
	
	
	@Override
	public List<UsuarioEstanciaUnidadClinicaVO> findUsuariosEstanciaUnidadClinica(PortafolioVO portafolio) throws DAOException {
		try {
			return getJdbcTemplate().query(SQL_FIND_USUARIOS_ESTANCIA_UNIDAD_CLINICA, new Object[]{portafolio.getIdAsignatura(), portafolio.getAnyoAcademico()}, new UsuarioEstanciaUnidadClinicaMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	
	
	public List<PortafolioVO> findAll(PortafolioVO portafolio) throws DAOException {
		try {
			return getJdbcTemplate().query(SQL_FIND_ALL, new Object[]{portafolio.getAnyoAcademico()}, new PortafolioMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}


	@Override
	public PortafolioVO findById(PortafolioVO portafolio) throws DAOException {
		try {
			return (PortafolioVO) getJdbcTemplate().queryForObject(SQL_FIND_BY_ID, new Object[]{portafolio.getIdPortafolio()}, new PortafolioMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public List<PortafolioVO> findByAlumno(PortafolioVO portafolio) throws DAOException {
		try {
			return getJdbcTemplate().query(SQL_FIND_BY_ALUMNO, new Object[]{portafolio.getIdAlumno(), portafolio.getAnyoAcademico()}, new PortafolioMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public List<PortafolioVO> findByProfesor(PortafolioVO portafolio) throws DAOException {
		try {
			return getJdbcTemplate().query(SQL_FIND_BY_PROFESOR, new Object[]{portafolio.getIdProfesor(), portafolio.getAnyoAcademico()}, new PortafolioMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public List<PortafolioVO> findByAsignatura(PortafolioVO portafolio) throws DAOException {
		try {
			return getJdbcTemplate().query(SQL_FIND_BY_ASIGNATURA, new Object[]{portafolio.getIdAsignatura(), portafolio.getAnyoAcademico()}, new PortafolioMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public List<PortafolioVO> findByAnyoAcademico(PortafolioVO portafolio) throws DAOException {
		try {
			return getJdbcTemplate().query(SQL_FIND_BY_ANYO_ACADEMICO, new Object[]{portafolio.getAnyoAcademico()}, new PortafolioMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	

	@Override
	public String insert(final PortafolioVO portafolio) throws DAOException, DAOInsertException {
		try{
			KeyHolder kh = new GeneratedKeyHolder();
			getJdbcTemplate().update(new PreparedStatementCreator() {
				
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL_INSERT, new String[]{"id_portafolio"});
					ps.setString(1, portafolio.getIdAlumno());
					ps.setString(2, portafolio.getIdProfesor());
					ps.setString(3, portafolio.getIdAsignatura());
					ps.setString(4, portafolio.getAnyoAcademico());
					return ps;
					
				}
			}
			,kh);
			portafolio.setIdPortafolio(kh.getKey().toString());
			return kh.getKey().toString();
			
		} catch (Exception e) {
			throw new DAOInsertException(e);
		}
		
	}

	@Override
	public void update(PortafolioVO portafolio) throws DAOException, DAOUpdateException {
		try {
			 
			String query = SQL_UPDATE;

			query += " where id_portafolio = ?";

			getJdbcTemplate().update(query, new Object[]{
					portafolio.getIdProfesor(),
					portafolio.getIdPortafolio()});
		} catch(Exception e) {
			throw new DAOUpdateException(e);
		}
		
	}

	@Override
	public void delete(PortafolioVO portafolio) throws DAOException, DAODeleteException {
		try {
			getJdbcTemplate().update(SQL_DELETE, new Object[]{portafolio.getIdPortafolio()});
		} catch (Exception e) {
			throw new DAODeleteException(e);
		}
		
	}


	@Override
	public void updateEstancia(DatosUsuarioEstanciaUnidadClinicaVO data) throws DAOException, DAOUpdateException {
		try {
			 
			String query = SQL_UPDATE_ESTANCIA;

			query += " where id_portafolio = ? and id_estancia_unidad = ?";

			getJdbcTemplate().update(query, new Object[]{
					data.getCentroAsociado(),
					data.getUnidadClinica(), 
					data.getTurno(),
					data.getFechaInicio(),
					data.getFechaFin(),
					data.getIdPortafolio(),
					data.getIdEstanciaUnidad()
				
			});
		} catch(Exception e) {
			throw new DAOUpdateException(e);
		}
		
	}


	@Override
	public List<UsuarioPortafolioVO> findUsuariosByAsignatura(PortafolioVO p) throws DAOException {
		try {
			return getJdbcTemplate().query(SQL_FIND_USUARIOS_BY_ASIGNATURA, new Object[]{p.getIdAsignatura(),p.getAnyoAcademico()}, new UsuarioPortafolioMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}


	@Override
	public List<TrabajoDeCampoVO> findTrabajosByPortafolio(PortafolioVO p) throws DAOException {
		try {
			return getJdbcTemplate().query(SQL_FIND_TRABAJOS_BY_PORTAFOLIO, new Object[]{p.getIdAlumno(),p.getIdAsignatura(),p.getAnyoAcademico()}, new TrabajoDeCampoMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}


	@Override
	public List<CasoClinicoVO> findCasosByPortafolio(PortafolioVO p) throws DAOException {
		try {
			return getJdbcTemplate().query(SQL_FIND_CASOS_BY_PORTAFOLIO, new Object[]{p.getIdAlumno(),p.getIdAsignatura(),p.getAnyoAcademico()}, new CasoClinicoMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}


	@Override
	public List<DiarioReflexivoVO> findDiariosByPortafolio(PortafolioVO p) throws DAOException {
		try {
			return getJdbcTemplate().query(SQL_FIND_DIARIOS_BY_PORTAFOLIO, new Object[]{p.getIdAlumno(),p.getIdAsignatura(),p.getAnyoAcademico()}, new DiarioReflexivoMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}


	@Override
	public PortafolioVO findByAlumnoAsignatura(PortafolioVO p)
			throws DAOException {
		try {
			return (PortafolioVO) getJdbcTemplate().queryForObject(SQL_FIND_BY_ALUMNO_ASIGNATURA, new Object[]{p.getIdAlumno(),p.getIdAsignatura(),p.getAnyoAcademico()}, new PortafolioMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}


	@Override
	public UsuarioVO findAlumnoByPortafolio(PortafolioVO p) throws DAOException {
		try {
			return (UsuarioVO) getJdbcTemplate().queryForObject(SQL_FIND_ALUMNO_BY_PORTAFOLIO, new Object[]{p.getIdPortafolio()}, new UsuarioMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}


	@Override
	public List<PortafolioVO> findAnyos() throws DAOException {
		try {
			return getJdbcTemplate().query(SQL_FIND_ANYOS, new Object[]{}, new AnyoMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}


	@Override
	public List<UsuarioPortafolioVO> findAlumnosPotencialesCertificado()
			throws DAOException {
		try {
			return getJdbcTemplate().query(SQL_FIND_ALUMNOS_POTENCIALES, new Object[]{}, new UsuarioPortafolioMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}






	
}


