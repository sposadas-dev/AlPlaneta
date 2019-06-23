package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.LocalDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.LocalDAO;

public class LocalDAOSQL implements LocalDAO{

	private static final String insert = "INSERT INTO local (nombre, direccion) VALUES (?,?)";
	private static final String delete = "DELETE FROM local WHERE idLocal = ?";
	private static final String readAll = "SELECT * FROM local";
	private static final String update = "UPDATE local SET nombre = ?, direccion = ? WHERE idLocal = ?";
	private static final String browse = "SELECT * FROM local WHERE idLocal = ?";
	private static final String browseName = "SELECT * FROM local WHERE nombre = ?";
	
	@Override
	public boolean insert(LocalDTO local) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setString(1, local.getNombreLocal());
			statement.setString(2, local.getDireccionLocal());
			
			if(statement.executeUpdate() > 0) {
				return true;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(int idLocal) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setInt(1, idLocal);
			if(statement.executeUpdate() > 0) {
				return true;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<LocalDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		List<LocalDTO> locales = new ArrayList<LocalDTO>();
		
		try {
			statement = conexion.getSQLConexion().prepareStatement(readAll);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				locales.add(new LocalDTO(resultSet.getInt("idLocal"),
										 resultSet.getString("nombre"),
										 resultSet.getString("direccion")));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return locales;
	}
	
	public LocalDTO readOne(String nombreLocal) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		LocalDTO local = null;
		
		try {
			statement = conexion.getSQLConexion().prepareStatement(browseName);
			statement.setString(1, nombreLocal);
			
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				local = new LocalDTO(resultSet.getInt("idLocal"),
						resultSet.getString("nombre"),
						resultSet.getString("direccion"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return local;
	}

	@Override
	public boolean update(LocalDTO local) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);
			statement.setString(1, local.getNombreLocal());
			statement.setString(2, local.getDireccionLocal());
			statement.setInt(3, local.getIdLocal());
			if(statement.executeUpdate() > 0) {
				return true;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public LocalDTO getById(int idLocal) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		LocalDTO local; 
		try {
			statement = conexion.getSQLConexion().prepareStatement(browse);
			statement.setInt(1, idLocal);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				local = new LocalDTO(resultSet.getInt("idLocal"),
									 resultSet.getString("nombre"),
									 resultSet.getString("direccion"));
				return local;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
