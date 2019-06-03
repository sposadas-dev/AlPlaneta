package persistencia.dao.mysql;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Punto;

import dto.PuntoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PuntoDAO;

public class PuntoDAOSQL implements PuntoDAO{
	
	
	
	private static final String insert = "INSERT INTO punto(idPunto, punto, ARS, vencimiento ) VALUES (?,?, ?, ?)";
	private static final String delete = "DELETE FROM punto WHERE idPunto = ?";
	private static final String readall = "SELECT * FROM punto";
	private static final String update = "UPDATE punto SET punto= ?, ARS = ?, vencimiento =? WHERE idPunto = ?";
	private static final String browse = "SELECT * FROM punto WHERE idPunto = ?";
	
	@Override
	public boolean insert(PuntoDTO punto) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, punto.getIdPunto());
			statement.setInt(2, punto.getPunto());
			statement.setInt(3, punto.getARS());
			statement.setDate(4, punto.getVencimiento());

			if (statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean delete(PuntoDTO punto_a_eliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(punto_a_eliminar.getIdPunto()));
			chequeoUpdate = statement.executeUpdate();
			if(chequeoUpdate > 0) //Si se ejecutó devuelvo true
				return true;
		} 
		catch (SQLException e) {
			 e.printStackTrace();
		}
		return false;
	}
	@Override
	public List<PuntoDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();
		ArrayList<PuntoDTO> puntos = new ArrayList<PuntoDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				puntos.add(new PuntoDTO(resultSet.getInt("idPunto"),
												  resultSet.getInt("punto"),
												  resultSet.getInt("ARS"),
												  resultSet.getDate("vencimiento")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return puntos;
	}
	@Override
	public boolean update(PuntoDTO punto_a_editar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);
			statement.setInt(1, punto_a_editar.getPunto());
			statement.setInt(2,punto_a_editar.getARS());
			statement.setDate(3, punto_a_editar.getVencimiento());
			statement.setInt(4, punto_a_editar.getIdPunto()); 
			
			
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
	public PuntoDTO getPuntoById(int idPunto ){
		
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		PuntoDTO punto;
		try{
			statement = conexion.getSQLConexion().prepareStatement(browse);
			statement.setInt(1, idPunto);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()){
				punto = new PuntoDTO(resultSet.getInt("idPunto"),
											   resultSet.getInt("punto"),
											   resultSet.getInt("ARS"),
											   resultSet.getDate("vencimiento")
											   );
				return punto;
			}
			
		}catch (SQLException e){
			 e.printStackTrace();
		}
		return null;
	}
	

	public static void main(String[] args) {
		Punto dao = new Punto(new DAOSQLFactory());
		
		List<PuntoDTO> list = dao.obtenerPunto(); 
		
		for(PuntoDTO elem: list){
			System.out.println(elem.getPunto());
			elem.setPunto(20);
			dao.editarPunto(elem);
			
		}
		System.out.println("- - - - - - - - - - - - -");
//		System.out.println(dao.getPuntoById(1).getARS());
//		dao.agregarPunto();
//		tiene id, cantpuntos, ARS, y vencimiento
//		 
		 
	} 

}
