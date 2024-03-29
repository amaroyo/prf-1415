package es.oyssen.mrm.negocio.dao.rowmappers;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.jdbc.core.RowMapper;

import es.oyssen.mrm.negocio.vo.UsuarioVO;
import es.oyssen.mrm.util.StringUtil;


public class UsuarioMapper implements RowMapper {

	public static final String FIELD_ID = "id_usuario";
	public static final String FIELD_ID_GRUPO = "id_grupo";
    public static final String FIELD_CORREO = "correo";
    public static final String FIELD_CONTRASENYA = "contrasenya";
    public static final String FIELD_NOMBRE = "nombre";
    public static final String FIELD_APELLIDO1 = "apellido1";
    public static final String FIELD_APELLIDO2 = "apellido2";
    public static final String FIELD_DNI = "dni";
    public static final String FIELD_TELEFONO = "telefono";
    public static final String FIELD_FOTO = "foto";
    
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    	UsuarioVO o = new UsuarioVO();
    	o.setIdUsuario(StringUtil.nullToString(rs.getString(FIELD_ID)));
    	o.setIdGrupo(StringUtil.nullToString(rs.getString(FIELD_ID_GRUPO)));
    	o.setCorreo(StringUtil.nullToString(rs.getString(FIELD_CORREO)));
    	o.setContrasenya(StringUtil.nullToString(rs.getString(FIELD_CONTRASENYA)));
    	o.setNombre(StringUtil.nullToString(rs.getString(FIELD_NOMBRE)));
    	o.setApellido1(StringUtil.nullToString(rs.getString(FIELD_APELLIDO1)));
    	o.setApellido2(StringUtil.nullToString(rs.getString(FIELD_APELLIDO2)));
    	o.setDni(StringUtil.nullToString(rs.getString(FIELD_DNI)));
    	o.setTelefono(StringUtil.nullToString(rs.getString(FIELD_TELEFONO)));
    	
    	
    	try {
        	InputStream datos = rs.getBinaryStream(FIELD_FOTO);
        	String foto;
        	if(datos != null) {
        		foto = new String(new Base64().encode(IOUtils.toByteArray(datos)));
        	}
        	else{
        		foto = "";
        	}
        	o.setFotoImagen(foto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return o;
	}
}

