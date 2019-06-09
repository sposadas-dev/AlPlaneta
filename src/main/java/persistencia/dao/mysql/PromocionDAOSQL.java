package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.PromocionDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PromocionDAO;

public class PromocionDAOSQL implements PromocionDAO {

	private static final String insert = "INSERT INTO promocion (idPromocion, porcentaje, stock, fechaVencimiento, estado) VALUES (?,?,?,?,?)";
	private static final String readall = "SELECT * FROM promocion";
	private static final String updateEstado = "UPDATE promocion SET estado=? WHERE idPromocion= ?";
	private static final String update = "UPDATE promocion SET porcentaje=?, stock=?, fechaVencimiento=? WHERE idPromocion= ?";
	private static final String browse = "SELECT * FROM promocion WHERE idPromocion = ?";

	@Override
	public boolean insert(PromocionDTO promocion) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, promocion.getIdPromocion());
//			statement.setObject(2, promocion.getViajes());
			statement.setInt(2, promocion.getPorcentaje());
			statement.setInt(3, promocion.getStock());
			statement.setDate(4, promocion.getFechaVencimiento());
			statement.setString(5, promocion.getEstado());
			
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
	
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()){
				promocions.add(new PromocionDTO(
					resultSet.getInt("idPromocion"),
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
	public boolean updateEstado(PromocionDTO promocion) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(updateEstado);
			
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
	public boolean update(PromocionDTO promocion) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);
			
			statement.setInt(1, promocion.getPorcentaje());
			statement.setInt(2, promocion.getStock());
			statement.setDate(3, promocion.getFechaVencimiento());
			statement.setInt(4, promocion.getIdPromocion());
		
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

		PromocionDTO ret;
		try{
			statement = conexion.getSQLConexion().prepareStatement(browse);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()){
				ret = new PromocionDTO(resultSet.getInt("idPromocion"),
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