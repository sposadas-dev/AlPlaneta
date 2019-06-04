package persistencia.dao.mysql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.PromocionDTO;
import dto.ViajeDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PromocionDAO;

public class PromocionDAOSQL implements PromocionDAO {

	private static final String insert = "INSERT INTO promocion (idPromocion, idViaje, porcentaje, stock, fechaVencimiento, estado) VALUES (?,?,?,?,?,?)";
	private static final String readall = "SELECT * FROM promocion";
	private static final String update = "UPDATE promocion SET estado=? WHERE idPromocion= ?";
	private static final String browse = "SELECT * FROM promocion WHERE idPromocion = ?";

	@Override
	public boolean insert(PromocionDTO promocion) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, promocion.getIdPromocion());
			statement.setInt(2, promocion.getViaje().getIdViaje());
			statement.setInt(3, promocion.getPorcentaje());
			statement.setInt(4, promocion.getStock());
			statement.setDate(5, promocion.getFechaVencimiento());
			statement.setString(6, promocion.getEstado());
			
			if (statement.executeUpdate() > 0)
				return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public List<PromocionDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; 
		ArrayList<PromocionDTO> promocions = new ArrayList<PromocionDTO>();
		Conexion conexion = Conexion.getConexion();
	
		ViajeDAOSQL viaje = new ViajeDAOSQL();
	
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()){
				promocions.add(new PromocionDTO(
					resultSet.getInt("idPromocion"),
					viaje.getViajeById(resultSet.getInt("idViaje")),
					resultSet.getInt("porcentaje"),
					resultSet.getInt("stock"),
					resultSet.getDate("fechaVencimiento"),
					resultSet.getString("estado"))
				);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return promocions;
	}

	@Override
	public boolean update(PromocionDTO promocion) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);
			
			statement.setString(1, promocion.getEstado());
			statement.setInt(2, promocion.getIdPromocion());
		
			chequeoUpdate = statement.executeUpdate();
			if(chequeoUpdate > 0) //Si se ejecutó devuelvo true
				return true;
		} 
		catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public PromocionDTO getPromocionById(int id ){
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();

		ViajeDAOSQL viaje = new ViajeDAOSQL();
		
		PromocionDTO ret;
		try{
			statement = conexion.getSQLConexion().prepareStatement(browse);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()){
				ret = new PromocionDTO(resultSet.getInt("idPromocion"),
						viaje.getViajeById(resultSet.getInt("idViaje")),
						resultSet.getInt("porcentaje"),
						resultSet.getInt("stock"),
						resultSet.getDate("fechaVencimiento"),
						resultSet.getString("estado")
						);
				return ret;
			}
			
		}
		catch (SQLException e){
			 e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
//		PromocionDAOSQL promocionDAOSQL = new PromocionDAOSQL();	
//		Date fechaPromocion = new java.sql.Date(2019,05,06);
//		ViajeDTO viaje = null;
//		
//		for(ViajeDTO v : new ViajeDAOSQL().readAll()) {
//			viaje = v;
//		}
//		PromocionDTO nuevoPromocion = new PromocionDTO(0,viaje,50,3000,fechaPromocion);
//				
//		promocionDAOSQL.insert(nuevoPromocion);
//		List<PromocionDTO> PROMOCION = promocionDAOSQL.readAll();
//		
//		for(PromocionDTO e: PROMOCION)
//			System.out.println(e.getFechaVencimiento().toString()+" "+e.getPorcentaje());
	}
		
}