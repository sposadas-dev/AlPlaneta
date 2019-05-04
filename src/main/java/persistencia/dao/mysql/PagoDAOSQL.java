package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import dto.PagoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PagoDAO;

public class PagoDAOSQL implements PagoDAO {

	private static final String insert = "INSERT INTO pago (idPago, fechaPago, monto) VALUES (?,?,?)";
	private static final String readall = "SELECT * FROM pago";
	private static final String delete = "DELETE FROM pago WHERE idPago=?";
	private static final String update = "UPDATE pago SET fechaPago=?, monto=? WHERE idPago=?;";
	
	@Override
	public boolean insert(PagoDTO pagoInsert) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			
			statement.setInt(1, pagoInsert.getIdPago());
			statement.setDate(2, pagoInsert.getFechaPago());
			statement.setBigDecimal(3, pagoInsert.getMonto());
			if (statement.executeUpdate() > 0) {
				return true;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(PagoDTO pagoDelete) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<PagoDTO> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(PagoDTO pagoUpdate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PagoDTO browse(int idPago) {
		// TODO Auto-generated method stub
		return null;
	}

}
