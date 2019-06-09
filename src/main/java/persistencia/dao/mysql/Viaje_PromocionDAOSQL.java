package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ViajeDTO;
import dto.Viaje_PromocionDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.Viaje_PromocionDAO;

public class Viaje_PromocionDAOSQL implements Viaje_PromocionDAO {
	
	private static final String insert = "INSERT INTO viaje_promocion(idViajePromocion, idViaje, idPromocion) VALUES (?, ?, ?)";
	private static final String delete = "DELETE FROM viaje_promocion WHERE idViajePromocion = ?";
	private static final String readall = "SELECT * FROM viaje_promocion";
//	private static final String update = "UPDATE viaje_promocion SET idViaje=?, idPromocion=? WHERE idViajePromocion=?;";
	private static final String getViajesByIdPromocion = "SELECT * FROM viaje_promocion WHERE idPromocion=?";

	
	@Override
	public boolean insert(Viaje_PromocionDTO viaje_promocion) {

		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, viaje_promocion.getIdViajePromocion());
			statement.setInt(2, viaje_promocion.getIdViaje());
			statement.setInt(3, viaje_promocion.getIdPromocion());
			
			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	@Override
	public boolean delete(Viaje_PromocionDTO viaje_promocion) {

		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(viaje_promocion.getIdViajePromocion()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public List<Viaje_PromocionDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		List<Viaje_PromocionDTO> pasajes = new ArrayList<Viaje_PromocionDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				pasajes.add(
						new Viaje_PromocionDTO(
						resultSet.getInt("idViajePromocion"),
						resultSet.getInt("idViaje"), 
						resultSet.getInt("idPromocion")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pasajes;
	}
//	@Override
//	public boolean update(Viaje_PromocionDTO viaje_promocion) {
//		PreparedStatement statement;
//		int chequeoUpdate = 0;
//		Conexion conexion = Conexion.getConexion();
//		try {
//			statement = conexion.getSQLConexion().prepareStatement(update);
//
//			// 
//
//			chequeoUpdate = statement.executeUpdate();
//			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
//				return true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//
//	}
	@Override
	public List<ViajeDTO> obtenerViajesEnPromo(int idPromocion){
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<ViajeDTO> viaje_promocion = new ArrayList<ViajeDTO>();
		ViajeDAOSQL viajeDAOSQL = new ViajeDAOSQL();
		Conexion conexion = Conexion.getConexion();
		try{
			statement = conexion.getSQLConexion().prepareStatement(getViajesByIdPromocion);
			statement.setInt(1, idPromocion);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				viaje_promocion.add(viajeDAOSQL.getViajeById(resultSet.getInt("idViaje")));
			}			
			
		}catch (SQLException e){
			 e.printStackTrace();
		}
		return viaje_promocion;
	}
	
	public static void main(String[] args) {
		Viaje_PromocionDAOSQL viajePromoDAOSQL = new Viaje_PromocionDAOSQL();
		for(Viaje_PromocionDTO X : viajePromoDAOSQL.readAll()) {
			System.out.println(X.getIdViajePromocion());	
		}
		for(ViajeDTO x : viajePromoDAOSQL.obtenerViajesEnPromo(1) ) {
			System.out.println("Viajes en promo 1: " +x.getIdViaje());
		}		
	}
}