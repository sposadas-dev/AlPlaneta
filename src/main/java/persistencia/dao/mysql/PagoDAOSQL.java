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

	private static final String insert = "INSERT INTO pago (idPago, fechaPago, monto, idformapago) VALUES (?,?,?,?)";
	private static final String readall = "SELECT * FROM pago";
	private static final String delete = "DELETE FROM pago WHERE idPago=?";
	private static final String update = "UPDATE pago SET fechaPago=?, monto=? WHERE idPago=?;";
	private static final String browse = "SELECT * FROM pago WHERE idPago=?";
	
	@Override
	public boolean insert(PagoDTO pagoInsert) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			
			statement.setInt(1, pagoInsert.getIdPago());
			statement.setDate(2, pagoInsert.getFechaPago());
			statement.setBigDecimal(3, pagoInsert.getMonto());
			statement.setInt(4,pagoInsert.getIdFormaPago().getIdFormaPago());
		
			if (statement.executeUpdate() > 0) 
				return true;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(PagoDTO pagoDelete) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(pagoDelete.getIdPago()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0)
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
		FormaPagoDAOSQL formapagoDAOSQL = new FormaPagoDAOSQL();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				pagos.add(new PagoDTO(resultSet.getInt("idPago"),
								      resultSet.getDate("fechaPago"),
									  resultSet.getBigDecimal("monto"),
									  formapagoDAOSQL.getFormaPagoById(resultSet.getInt("idformapago"))
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
		FormaPagoDAOSQL pagoDAOSQL = new FormaPagoDAOSQL();
		try {
			statement = conexion.getSQLConexion().prepareStatement(browse);
			
			statement.setInt(1, idPago);
			resultSet = statement.executeQuery();
			if (resultSet.next()){
				pago = new PagoDTO(resultSet.getInt("idPago"),
										   resultSet.getDate("fechaPago"),
										   resultSet.getBigDecimal("monto"),
										   pagoDAOSQL.getFormaPagoById(resultSet.getInt("idformapago")) );
				return pago;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
