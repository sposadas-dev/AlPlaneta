package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.PasajeDTO;
import dto.Pasaje_PasajerosDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PasajeDAO;
import persistencia.dao.interfaz.Pasaje_PasajerosDAO;

public class Pasaje_PasajerosDAOSQL implements Pasaje_PasajerosDAO {
	
	private static final String insert = "INSERT INTO pasajes_pasajeros(idPasajePasajero, idPasaje, idPasajero)"
			+ " VALUES (?, ?, ?)";
	
	private static final String delete = "DELETE FROM pasajes_pasajeros  WHERE idPasajePasajero = ?";
	
	private static final String readall = "SELECT * FROM pasajes_pasajeros";
	
	private static final String update = "UPDATE pasajes_pasajeros SET idPasaje=?, idPasajero=? WHERE idPasajePasajero=?;";
	

	@Override
	public boolean insert(Pasaje_PasajerosDTO pasaje_pasajero) {

		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, pasaje_pasajero.getIdPasajePasajero());
			statement.setInt(2, pasaje_pasajero.getIdPasaje());
			statement.setInt(3, pasaje_pasajero.getIdPasajero());
			
			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	@Override
	public boolean delete(Pasaje_PasajerosDTO pasaje_pasajero_a_eliminar) {

		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(pasaje_pasajero_a_eliminar.getIdPasajePasajero()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public List<Pasaje_PasajerosDTO> readAll() {

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		List<Pasaje_PasajerosDTO> pasajes = new ArrayList<Pasaje_PasajerosDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				pasajes.add(
						new Pasaje_PasajerosDTO(
						resultSet.getInt("idPasajePasajero"),
						resultSet.getInt("idPasaje"), 
						resultSet.getInt("idPasajero")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pasajes;
	}
	@Override
	public boolean update(Pasaje_PasajerosDTO pasaje_pasajero_editar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);

			// 

			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
	@Override
	public boolean getById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	


}
