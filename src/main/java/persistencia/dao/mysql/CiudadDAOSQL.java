package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.CiudadDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.CiudadDAO;

public class CiudadDAOSQL implements CiudadDAO {

	private static final String insert = "INSERT INTO ciudad(idCiudad, nombre) VALUES(?, ?)";
	private static final String delete = "DELETE FROM ciudad WHERE idCiudad = ?";
	private static final String readall = "SELECT * FROM ciudad";
	private static final String update = "UPDATE pasajero SET nombre=?";
	private static final String browse = "SELECT * FROM ciudad WHERE idCiudad=?";

	@Override
	public boolean insert(CiudadDTO ciudadInsert) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, ciudadInsert.getIdCiudad());
			statement.setString(2, ciudadInsert.getNombre());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean delete(CiudadDTO ciudadDelete) throws Exception {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(ciudadDelete.getIdCiudad()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<CiudadDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; 
		ArrayList<CiudadDTO> ciudades = new ArrayList<CiudadDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				ciudades.add(new CiudadDTO(resultSet.getInt("idCiudad"), resultSet.getString("nombre"))
						    );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ciudades;
	}

	@Override
	public boolean update(CiudadDTO ciudadUpdate) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);

			statement.setString(1, ciudadUpdate.getNombre());
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public CiudadDTO browse(int idCiudad) throws Exception {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		CiudadDTO ciudad;
		try {
			statement = conexion.getSQLConexion().prepareStatement(browse);
			
			statement.setInt(1, idCiudad);
			resultSet = statement.executeQuery();
			if (resultSet.next()){
				ciudad = new CiudadDTO(resultSet.getInt("idPasajero"),
									   resultSet.getString("Nombre")
									  );
				return ciudad;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
