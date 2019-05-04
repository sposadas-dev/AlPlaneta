package persistencia.dao.mysql;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.AdministrativoDTO;
import dto.ClienteDTO;
import dto.EstadoPasajeDTO;
import dto.PagoDTO;
import dto.PasajeDTO;
import dto.PasajeroDTO;
import dto.TransporteDTO;
import dto.ViajeDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PasajeDAO;

public class PasajeDAOSQL implements PasajeDAO {
	
	private static final String insert = "INSERT INTO pasajes (idPasaje, viaje, administrativo,"
			+ "cantidadPasajeros, cliente, transporte, fechaVencimiento, valorViaje,"
			+ "estadoDelPasaje, pago, pasajeros)"
			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String delete = "DELETE FROM pasajes  WHERE idPasaje = ?";
	private static final String readall = "SELECT * FROM Pasaje";
	
	private static final String update = "UPDATE pasajes SET viaje.fechaSalida= ? WHERE idPasaje = ?;";

	@Override
	public boolean insert(PasajeDTO reserva) {

		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
//			statement.setInt(1, reserva.getIdReserva());
//			statement.setString(2, reserva.getCodigo());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	@Override
	public boolean delete(PasajeDTO reserva_a_eliminar) {

		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
//			statement.setString(1, Integer.toString(reserva_a_eliminar.getIdReserva()));
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
//				reservas.add(new ReservaDTO(resultSet.getInt("idReserva"),resultSet.getInt("idCliente"), resultSet.getInt("idPersonalAdm"), resultSet.(fechitaReserva), resultSet.getDate(columnIndex)));
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
