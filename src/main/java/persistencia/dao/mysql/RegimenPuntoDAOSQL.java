package persistencia.dao.mysql;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.RegimenPuntoDTO;
import modelo.ModeloRegimenPunto;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.RegimenPuntoDAO;

public class RegimenPuntoDAOSQL implements RegimenPuntoDAO{
	
	private static final String insert = "INSERT INTO regimenpunto(idRegimenPunto, punto, ARS, vencimiento ) VALUES (?,?, ?, ?)";
	private static final String delete = "DELETE FROM regimenpunto WHERE idRegimenPunto = ?";
	private static final String readall = "SELECT * FROM regimenpunto";
	private static final String update = "UPDATE regimenpunto SET punto= ?, ARS = ?, vencimiento =? WHERE idRegimenPunto = ?";
	private static final String browse = "SELECT * FROM regimenpunto WHERE idRegimenPunto = ?";
	private static final String ultimoRegistro = "SELECT * FROM regimenpunto ORDER BY idRegimenPunto desc limit 1";
	
	@Override
	public boolean insert(RegimenPuntoDTO punto) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, punto.getIdPunto());
			statement.setInt(2, punto.getPunto());
			statement.setInt(3, punto.getARS());
			statement.setInt(4, punto.getVencimiento());

			if (statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean delete(RegimenPuntoDTO punto_a_eliminar) {
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
	public List<RegimenPuntoDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();
		ArrayList<RegimenPuntoDTO> puntos = new ArrayList<RegimenPuntoDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				puntos.add(new RegimenPuntoDTO(resultSet.getInt("idRegimenPunto"),
												  resultSet.getInt("punto"),
												  resultSet.getInt("ARS"),
												  resultSet.getInt("vencimiento")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return puntos;
	}
	@Override
	public boolean update(RegimenPuntoDTO punto_a_editar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);
			statement.setInt(1, punto_a_editar.getPunto());
			statement.setInt(2,punto_a_editar.getARS());
			statement.setInt(3, punto_a_editar.getVencimiento());
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
	public RegimenPuntoDTO getPuntoById(int idPunto ){
		
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		RegimenPuntoDTO punto;
		try{
			statement = conexion.getSQLConexion().prepareStatement(browse);
			statement.setInt(1, idPunto);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()){
				punto = new RegimenPuntoDTO(resultSet.getInt("idRegimenPunto"),
											   resultSet.getInt("punto"),
											   resultSet.getInt("ARS"),
											   resultSet.getInt("vencimiento")
											   );
				return punto;
			}
			
		}catch (SQLException e){
			 e.printStackTrace();
		}
		return null;
	}
	public RegimenPuntoDTO getUltimoRegistroRegimenPunto() {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		RegimenPuntoDTO regimenPunto;
		try {
			statement = conexion.getSQLConexion().prepareStatement(ultimoRegistro);
			resultSet = statement.executeQuery();
			
			if (resultSet.next()){
				regimenPunto = new RegimenPuntoDTO(
						resultSet.getInt("idRegimenPunto"),
						  resultSet.getInt("punto"),
					      resultSet.getInt("ARS"),
						  resultSet.getInt("vencimiento")
						  );
				return regimenPunto;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static void main(String[] args) {
		ModeloRegimenPunto dao = new ModeloRegimenPunto(new DAOSQLFactory());
		RegimenPuntoDTO reg = dao.obtenerUltimoRegistro();
		System.out.println(reg.getVencimiento());
//		
//		List<PuntoDTO> list = dao.obtenerPunto(); 
//		
//		for(PuntoDTO elem: list){
//			System.out.println(elem.getPunto());
//			elem.setPunto(20);
//			dao.editarPunto(elem);
//			
//		}
//		System.out.println("- - - - - - - - - - - - -");
//		System.out.println(dao.getPuntoById(1).getARS());
//		dao.agregarPunto();
//		tiene id, cantpuntos, ARS, y vencimiento
//		 
//	        BigDecimal big = new BigDecimal("925.0000");
//	        String[] parts = big.toString().split("\\.");
//	        Integer parteEntera= Integer.parseInt(parts[0]);
//	        Integer parteDecimal= Integer.parseInt(parts[1]);
	        
//	        System.out.println("Parte entera: " + parteEntera);
//	        System.out.println("Parte decimal: " + parteDecimal);

	} 

}
