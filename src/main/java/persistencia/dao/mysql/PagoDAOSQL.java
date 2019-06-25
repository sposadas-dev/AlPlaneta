package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.PagoDTO;
import dto.PasajeroDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PagoDAO;

public class PagoDAOSQL implements PagoDAO {

	private static final String insert = "INSERT INTO pago (idPago, idAdministrativo, fechaPago, monto, idformapago, idtarjeta) VALUES (?,?,?,?,?,?)";
	private static final String readall = "SELECT * FROM pago";
	private static final String delete = "DELETE FROM pago WHERE idPago=?";
	private static final String update = "UPDATE pago SET fechaPago=?, monto=? WHERE idPago=?;";
	private static final String browse = "SELECT * FROM pago WHERE idPago=?";
	private static final String ultimoRegistro = "SELECT * FROM pago ORDER BY idPago desc limit 1";
	
	@Override
	public boolean insert(PagoDTO pagoInsert) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			
			statement.setInt(1, pagoInsert.getIdPago());
			statement.setInt(2, pagoInsert.getAdministrativo().getIdAdministrativo());
			statement.setDate(3, pagoInsert.getFechaPago());
			statement.setBigDecimal(4, pagoInsert.getMonto());
			statement.setInt(5,pagoInsert.getIdFormaPago().getIdFormaPago());
			if(pagoInsert.getIdFormaPago().getIdFormaPago() == 2)
			{
				statement.setInt(6, pagoInsert.getIdtarjeta().getIdTarjeta());
				}else {statement.setInt(6, 0);}
				
			if (statement.executeUpdate() > 0) 
				return true;
			
			} catch(SQLException e) {
				e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<PagoDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		ArrayList<PagoDTO> pagos = new ArrayList<PagoDTO>();
		AdministrativoDAOSQL administrativoDAOSQL = new AdministrativoDAOSQL();
		FormaPagoDAOSQL formapagoDAOSQL = new FormaPagoDAOSQL();
		TarjetaDAOSQL tarjetaDAOSQL = new TarjetaDAOSQL();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				pagos.add(new PagoDTO(resultSet.getInt("idPago"),
									  administrativoDAOSQL.getById(resultSet.getInt("idAdministrativo")),
								      resultSet.getDate("fechaPago"),
									  resultSet.getBigDecimal("monto"),
									  formapagoDAOSQL.getFormaPagoById(resultSet.getInt("idformapago")),									
									  tarjetaDAOSQL.getTarjetaById(resultSet.getInt("idtarjeta"))
									  ));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pagos;
	}

	@Override
	public boolean update(PagoDTO pagoUpdate) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);

			statement.setDate(1, pagoUpdate.getFechaPago());
			statement.setBigDecimal(2, pagoUpdate.getMonto());
			statement.setInt(3, pagoUpdate.getIdPago());
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0)
				return true;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public PagoDTO getPagoById(int idPago) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		PagoDTO pago;
		AdministrativoDAOSQL administrativoDAOSQL = new AdministrativoDAOSQL();
		FormaPagoDAOSQL formapagoDAOSQL = new FormaPagoDAOSQL();
		TarjetaDAOSQL tarjetaDAOSQL = new TarjetaDAOSQL();
		try {
			statement = conexion.getSQLConexion().prepareStatement(browse);
			
			statement.setInt(1, idPago);
			resultSet = statement.executeQuery();
			if (resultSet.next()){
				pago = new PagoDTO(resultSet.getInt("idPago"),
						  administrativoDAOSQL.getById(resultSet.getInt("idAdministrativo")),
					      resultSet.getDate("fechaPago"),
						  resultSet.getBigDecimal("monto"),
						  formapagoDAOSQL.getFormaPagoById(resultSet.getInt("idformapago")),
						  tarjetaDAOSQL.getTarjetaById(resultSet.getInt("idtarjeta"))
						);
				return pago;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PagoDTO getUltimoRegistroPago() {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		PagoDTO pago;
		AdministrativoDAOSQL administrativoDAOSQL = new AdministrativoDAOSQL();
		FormaPagoDAOSQL formapagoDAOSQL = new FormaPagoDAOSQL();
		TarjetaDAOSQL tarjetaDAOSQL = new TarjetaDAOSQL();
		try {
			statement = conexion.getSQLConexion().prepareStatement(ultimoRegistro);
			resultSet = statement.executeQuery();
			
			if (resultSet.next()){
				pago = new PagoDTO(resultSet.getInt("idPago"),
						  administrativoDAOSQL.getById(resultSet.getInt("idAdministrativo")),
					      resultSet.getDate("fechaPago"),
						  resultSet.getBigDecimal("monto"),
						  formapagoDAOSQL.getFormaPagoById(resultSet.getInt("idformapago")),
						  tarjetaDAOSQL.getTarjetaById(resultSet.getInt("idtarjeta"))
						  );
				return pago;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean delete(PagoDTO pagoDelete) {
		// TODO Auto-generated method stub
		return false;
	}	
}