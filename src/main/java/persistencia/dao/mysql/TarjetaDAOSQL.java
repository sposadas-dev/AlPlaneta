package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.FormaPagoDTO;
import dto.TarjetaDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.TarjetaDAO;

public class TarjetaDAOSQL implements TarjetaDAO {
	
	private static final String insert = "INSERT INTO tarjeta (idtarjeta, nrotarjeta, vencimiento) VALUES (?,?,?)";
	private static final String readall = "SELECT * from tarjeta";
	private static final String browse = "SELECT * FROM tarjeta WHERE idtarjeta=?";


	@Override
	public boolean insert(TarjetaDTO tarjeta) {
		
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, tarjeta.getIdTarjeta());
			statement.setString(2, tarjeta.getNroTarjeta());
			statement.setString(3,tarjeta.getVencimiento());			
		
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
						resultSet.getInt("idtarjeta"),
						resultSet.getString("nrotarjeta"),
						resultSet.getString("vencimiento"))
						);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tarjetas;
	}

	@Override
	public TarjetaDTO getTarjetaById(int idtarjeta) 
	{
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		TarjetaDTO tarjeta;
		try {
			statement = conexion.getSQLConexion().prepareStatement(browse);
			statement.setInt(1, idtarjeta);
			resultSet = statement.executeQuery();
			 
			if(resultSet.next()) {
				tarjeta = new TarjetaDTO(
						resultSet.getInt("idtarjeta"),
						resultSet.getString("nrotarjeta"),
						resultSet.getString("vencimiento")
						);
						return tarjeta;
						}
		}catch (SQLException e){
			 e.printStackTrace();
		}
		return null;
	}

}
