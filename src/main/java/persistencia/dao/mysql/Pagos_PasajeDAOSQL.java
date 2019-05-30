package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Pagos_Pasaje;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.Pagos_PasajeDAO;
import dto.Pagos_PasajeDTO;

public class Pagos_PasajeDAOSQL implements Pagos_PasajeDAO{

	private static final String insert = "INSERT INTO pagos_pasaje (idPagoPasaje, idPago, idPasaje) VALUES (?,?,?)";
	private static final String readall = "SELECT * FROM pagos_pasaje";
//	private static final String browse = "SELECT * FROM pagos_pasaje WHERE idPagoPasaje=?";

	@Override
	public boolean insert(Pagos_PasajeDTO nuevoPagoPasaje) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			
			statement.setInt(1, nuevoPagoPasaje.getIdPagoPasaje());
			statement.setInt(2, nuevoPagoPasaje.getPago().getIdPago());
			statement.setInt(3, nuevoPagoPasaje.getPasaje().getIdPasaje());
		
			if (statement.executeUpdate() > 0) 
				return true;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public List<Pagos_PasajeDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		ArrayList<Pagos_PasajeDTO> pagos_pasaje = new ArrayList<Pagos_PasajeDTO>();
		PasajeDAOSQL pasajeDAOSQL = new PasajeDAOSQL();
		PagoDAOSQL pagoDAOSQL = new PagoDAOSQL();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				pagos_pasaje.add(new Pagos_PasajeDTO(resultSet.getInt("idPagoPasaje"),
									  pagoDAOSQL.getPagoById(resultSet.getInt("idPago")),
									  pasajeDAOSQL.getPasajeById(resultSet.getInt("idPasaje"))
								));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pagos_pasaje;
	}
	
	public static void main(String[] args){
		Pagos_Pasaje p = new Pagos_Pasaje(new DAOSQLFactory());
		List<Pagos_PasajeDTO> e = p.obtenerPagosPasaje();
		
		for(Pagos_PasajeDTO pago : e){
			System.out.println(pago.getPago().getMonto() + " "+ pago.getPasaje().getValorViaje());
		}		
	}
}