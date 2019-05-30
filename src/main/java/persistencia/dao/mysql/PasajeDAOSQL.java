package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Pasaje;
import modelo.Transporte;
import dto.PagoDTO;
import dto.PasajeDTO;
import dto.PasajeroDTO;
import dto.TransporteDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PasajeDAO;

public class PasajeDAOSQL implements PasajeDAO {
	
	private static final String insert = "INSERT INTO pasaje(idPasaje, fechaVencimiento, valorViaje, idCliente, idViaje, idAdministrativo, idEstadoPasaje) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String delete = "DELETE FROM pasaje  WHERE idPasaje = ?";
	private static final String readall = "SELECT * FROM pasaje";
	private static final String update = "UPDATE pasaje SET viaje.fechaSalida= ? WHERE idPasaje = ?;";
	private static final String browse = "SELECT * FROM pasaje WHERE idPasaje=?";
	private static final String ultimoRegistro = "SELECT * FROM pasaje ORDER BY idPasaje desc limit 1";
	
	@Override
	public boolean insert(PasajeDTO pasaje) {

		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, pasaje.getIdPasaje());
			statement.setDate(2, pasaje.getFechaVencimiento());
			statement.setBigDecimal(3, pasaje.getValorViaje());
			statement.setInt(4, pasaje.getCliente().getIdCliente());
			statement.setInt(5, pasaje.getViaje().getIdViaje());
			statement.setInt(6, pasaje.getAdministrativo().getIdAdministrativo());
			statement.setInt(7, pasaje.getEstadoDelPasaje().getIdEstadoPasaje());
			
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
		ArrayList<PasajeDTO> pasajes = new ArrayList<PasajeDTO>();
		ClienteDAOSQL clienteDAOSQL = new ClienteDAOSQL();
		ViajeDAOSQL viajeDAOSQL = new ViajeDAOSQL();
		AdministrativoDAOSQL administrativoDAOSQL = new AdministrativoDAOSQL();
		EstadoPasajeDAOSQL estadoPasajeDAOSQL = new EstadoPasajeDAOSQL();
		PagoDAOSQL pagoDAOSQL = new PagoDAOSQL();
		Pasaje_PasajerosDAOSQL pasajeros = new Pasaje_PasajerosDAOSQL();

		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				pasajes.add(
						new PasajeDTO(
						resultSet.getInt("idPasaje"),
						viajeDAOSQL.getViajeById(resultSet.getInt("idViaje")),
						administrativoDAOSQL.getById(resultSet.getInt("idAdministrativo")),
						clienteDAOSQL.getClienteById(resultSet.getInt("idCliente")),
						resultSet.getDate("fechaVencimiento"),
						resultSet.getBigDecimal("valorViaje"),
						estadoPasajeDAOSQL.getEstadoPasajeById(resultSet.getInt("idEstadoPasaje")),
						pasajeros.traerPasajerosDePasaje(resultSet.getInt("idPasaje")))
						);							
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pasajes;
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
	public PasajeDTO getPasajeById(int idPasaje ){
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		PasajeDTO pasaje;
		ClienteDAOSQL clienteDAOSQL = new ClienteDAOSQL();
		ViajeDAOSQL viajeDAOSQL = new ViajeDAOSQL();
		AdministrativoDAOSQL administrativoDAOSQL = new AdministrativoDAOSQL();
		EstadoPasajeDAOSQL estadoPasajeDAOSQL = new EstadoPasajeDAOSQL();
		Pasaje_PasajerosDAOSQL pasajeros = new Pasaje_PasajerosDAOSQL();
		try{
			statement = conexion.getSQLConexion().prepareStatement(browse);
			statement.setInt(1, idPasaje);
			resultSet = statement.executeQuery();
				
				if(resultSet.next()){
					pasaje = new PasajeDTO(resultSet.getInt("idPasaje"),
							viajeDAOSQL.getViajeById(resultSet.getInt("idViaje")),
							administrativoDAOSQL.getById(resultSet.getInt("idAdministrativo")),
							clienteDAOSQL.getClienteById(resultSet.getInt("idCliente")),
							resultSet.getDate("fechaVencimiento"),
							resultSet.getBigDecimal("valorViaje"),
							estadoPasajeDAOSQL.getEstadoPasajeById(resultSet.getInt("idEstadoPasaje")),
							pasajeros.traerPasajerosDePasaje(resultSet.getInt("idPasaje"))
							);
					
				return pasaje;
				}
				
			}catch (SQLException e){
				 e.printStackTrace();
			}
			return null;
		}
	@Override
	public PasajeDTO getUltimoRegistroPasaje() {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		PasajeDTO pasaje;
		ClienteDAOSQL clienteDAOSQL = new ClienteDAOSQL();
		ViajeDAOSQL viajeDAOSQL = new ViajeDAOSQL();
		AdministrativoDAOSQL administrativoDAOSQL = new AdministrativoDAOSQL();
		EstadoPasajeDAOSQL estadoPasajeDAOSQL = new EstadoPasajeDAOSQL();
		Pasaje_PasajerosDAOSQL pasajeros = new Pasaje_PasajerosDAOSQL();
		try {
			statement = conexion.getSQLConexion().prepareStatement(ultimoRegistro);
			resultSet = statement.executeQuery();
				
			if (resultSet.next()){
				pasaje = new PasajeDTO(resultSet.getInt("idPasaje"),
						viajeDAOSQL.getViajeById(resultSet.getInt("idViaje")),
						administrativoDAOSQL.getById(resultSet.getInt("idAdministrativo")),
						clienteDAOSQL.getClienteById(resultSet.getInt("idCliente")),
						resultSet.getDate("fechaVencimiento"),
						resultSet.getBigDecimal("valorViaje"),
						estadoPasajeDAOSQL.getEstadoPasajeById(resultSet.getInt("idEstadoPasaje")),
						pasajeros.traerPasajerosDePasaje(resultSet.getInt("idPasaje"))
						);
				
			return pasaje;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}