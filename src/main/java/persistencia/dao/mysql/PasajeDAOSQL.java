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
	
	private static final String insert = "INSERT INTO pasaje(idPasaje, numeroComprobante,fechaVencimiento, valorViaje, montoAPagar, idCliente, idViaje, idAdministrativo, idEstadoPasaje,motivoCancelacion, fechaCancelacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
	private static final String delete = "DELETE FROM pasaje  WHERE idPasaje = ?";
	private static final String readall = "SELECT * FROM pasaje";
	private static final String update = "UPDATE pasaje SET idEstadoPasaje=?, montoAPagar=?, motivoCancelacion=?, fechaCancelacion=? WHERE idPasaje = ?;";
	private static final String browse = "SELECT * FROM pasaje WHERE idPasaje=?";
	private static final String ultimoRegistro = "SELECT * FROM pasaje ORDER BY idPasaje desc limit 1";
	
	
	@Override
	public boolean insert(PasajeDTO pasaje) {

		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, pasaje.getIdPasaje());
			statement.setString(2, pasaje.getNumeroComprobante());
			statement.setDate(3, pasaje.getFechaVencimiento());
			statement.setBigDecimal(4, pasaje.getValorViaje());
			statement.setBigDecimal(5, pasaje.getMontoAPagar());
			statement.setInt(6, pasaje.getCliente().getIdCliente());
			statement.setInt(7, pasaje.getViaje().getIdViaje());
			statement.setInt(8, pasaje.getAdministrativo().getIdAdministrativo());
			statement.setInt(9, pasaje.getEstadoDelPasaje().getIdEstadoPasaje());
			statement.setString(10, pasaje.getMotivoCancelacion());
			statement.setDate(11, pasaje.getDateCancelacion());
			
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
						resultSet.getString("numeroComprobante"),
						viajeDAOSQL.getViajeById(resultSet.getInt("idViaje")),
						administrativoDAOSQL.getById(resultSet.getInt("idAdministrativo")),
						clienteDAOSQL.getClienteById(resultSet.getInt("idCliente")),
						resultSet.getDate("fechaVencimiento"),
						resultSet.getBigDecimal("valorViaje"),
						resultSet.getBigDecimal("montoAPagar"),
						estadoPasajeDAOSQL.getEstadoPasajeById(resultSet.getInt("idEstadoPasaje")),
						pasajeros.traerPasajerosDePasaje(resultSet.getInt("idPasaje")),
						resultSet.getString("motivoCancelacion"),
						resultSet.getDate("fechaCancelacion")
						));							
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
			statement.setInt(1, pasaje_editar.getEstadoDelPasaje().getIdEstadoPasaje());
			statement.setBigDecimal(2, pasaje_editar.getMontoAPagar());
			statement.setString(3, pasaje_editar.getMotivoCancelacion());
			statement.setDate(4, pasaje_editar.getDateCancelacion());
			statement.setInt(5, pasaje_editar.getIdPasaje());

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
							resultSet.getString("numeroComprobante"),
							viajeDAOSQL.getViajeById(resultSet.getInt("idViaje")),
							administrativoDAOSQL.getById(resultSet.getInt("idAdministrativo")),
							clienteDAOSQL.getClienteById(resultSet.getInt("idCliente")),
							resultSet.getDate("fechaVencimiento"),
							resultSet.getBigDecimal("valorViaje"),
							resultSet.getBigDecimal("montoAPagar"),
							estadoPasajeDAOSQL.getEstadoPasajeById(resultSet.getInt("idEstadoPasaje")),
							pasajeros.traerPasajerosDePasaje(resultSet.getInt("idPasaje")),
							resultSet.getString("motivoCancelacion"),
							resultSet.getDate("fechaCancelacion")
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
						resultSet.getString("numeroComprobante"),
						viajeDAOSQL.getViajeById(resultSet.getInt("idViaje")),
						administrativoDAOSQL.getById(resultSet.getInt("idAdministrativo")),
						clienteDAOSQL.getClienteById(resultSet.getInt("idCliente")),
						resultSet.getDate("fechaVencimiento"),
						resultSet.getBigDecimal("valorViaje"),
						resultSet.getBigDecimal("montoAPagar"),
						estadoPasajeDAOSQL.getEstadoPasajeById(resultSet.getInt("idEstadoPasaje")),
						pasajeros.traerPasajerosDePasaje(resultSet.getInt("idPasaje")),
						resultSet.getString("motivoCancelacion"),
						resultSet.getDate("fechaCancelacion")
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