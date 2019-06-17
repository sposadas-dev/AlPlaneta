package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.TarjetaDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.TarjetaDAO;

public class TarjetaDAOSQL implements TarjetaDAO {
	
	private static final String insert = "INSERT INTO tarjeta (idTarjeta, nrotarjeta, fechaVencimiento) VALUES (?,?,?)";
	private static final String readall = "SELECT * from tarjeta";
	


	@Override
	public boolean insert(TarjetaDTO tarjeta) {
		
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, tarjeta.getIdTarjeta());
			statement.setInt(2, tarjeta.getNroTarjeta());
			statement.setDate(3,tarjeta.getFechaVencimiento());			
		
			if (statement.executeUpdate() > 0) 
				return true;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<TarjetaDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<TarjetaDTO> tarjetas = new ArrayList<TarjetaDTO>();
		Conexion conexion = Conexion.getConexion();
	
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				tarjetas.add(new TarjetaDTO(
						resultSet.getInt("idTarjeta"),
						resultSet.getInt("nrotarjeta"),
						resultSet.getDate("fechavencimiento"))
						);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tarjetas;
	}

//	@Override
//	public boolean update(TarjetaDTO tarjeta) {
//		
//		return false;
//	}

}
