package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.PasajeDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PasajeDAO;

public class PasajeDAOSQL implements PasajeDAO {
	
	private static final String insert = "INSERT INTO pasaje(idPasaje, cantidadPasajeros, fechaVencimiento, valorViaje, idCliente, idViaje, idTransporte, idAdministrativo, idEstadoPasaje, idPago)"
			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String delete = "DELETE FROM pasaje  WHERE idPasaje = ?";
	
	private static final String readall = "SELECT * FROM pasaje";
	
	private static final String update = "UPDATE pasaje SET viaje.fechaSalida= ? WHERE idPasaje = ?;";
	

	@Override
	public boolean insert(PasajeDTO pasaje) {

		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, pasaje.getIdPasaje());
			statement.setInt(2, pasaje.getCantidadPasajeros());
			statement.setDate(3, pasaje.getFechaVencimiento());
			statement.setBigDecimal(4, pasaje.getValorViaje());
			statement.setInt(5, pasaje.getCliente().getIdCliente());
			statement.setInt(6, pasaje.getViaje().getIdViaje());
			statement.setInt(7, pasaje.getTransporte().getIdTransporte());
			statement.setInt(8, pasaje.getAdministrativo().getIdAdministrativo());
			statement.setInt(9, pasaje.getEstadoDelPasaje().getIdEstadoPasaje());
			statement.setInt(10, pasaje.getPago().getIdPago());
			
			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	@Override
	public boolean delete(PasajeDTO pasaje_a_eliminar) {

		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(pasaje_a_eliminar.getIdPasaje()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public List<PasajeDTO> readAll() {

		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<PasajeDTO> reservas = new ArrayList<PasajeDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
//				reservas.add(
//						new PasajeDTO(
//						resultSet.getInt("idPasaje"),
//						resultSet.getInt("idCliente"), 
//						resultSet.getInt("idPersonalAdme pm"), 
//						resultSet.(fechitaReserva), 
//						resultSet.getDate(columnIndex)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reservas;
	}
	@Override
	public boolean update(PasajeDTO pasaje_editar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);

			// statement.setBytes(1,reserva_editar.getFecha_salida());
			// tatement.setInt(2, reserva_editar.getIdCliente()); //deberia ir datos del
			// cliente

			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
	@Override
	public boolean getById(PasajeDAO pasajeDTO) {
		// TODO Auto-generated method stub
		return false;
	}


}
