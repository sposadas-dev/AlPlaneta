package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Transporte;
import dto.RolDTO;
import dto.TransporteDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.TransporteDAO;

public class TransporteDAOSQL implements TransporteDAO {

	private static final String insert = "INSERT INTO transporte(idTransporte, nombreTransporte) VALUES (?, ?)";
	private static final String delete = "DELETE FROM transporte WHERE idTransporte = ?";
	private static final String readall = "SELECT * FROM transporte";
	private static final String update = "UPDATE transporte SET nombreTransporte = ? WHERE idTransporte = ?";
	private static final String browse = "SELECT * FROM transporte WHERE idTransporte = ?";
	
	@Override
	public boolean insert(TransporteDTO transporte) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, transporte.getIdTransporte());
			statement.setString(2, transporte.getNombre());

			if (statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean delete(TransporteDTO transporte_a_eliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setInt(1, transporte_a_eliminar.getIdTransporte());
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
	public List<TransporteDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();
		ArrayList<TransporteDTO> transportes = new ArrayList<TransporteDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				transportes.add(new TransporteDTO(resultSet.getInt("idTransporte"),
												  resultSet.getString("nombreTransporte")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transportes;
	}
	
	@Override
	public boolean update(TransporteDTO transporte_a_editar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);
			statement.setString(1, transporte_a_editar.getNombre());
			statement.setInt(2,transporte_a_editar.getIdTransporte());
			
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
	public TransporteDTO getTransporteById(int idTransporte ){
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		TransporteDTO transporte;
		try{
			statement = conexion.getSQLConexion().prepareStatement(browse);
			statement.setInt(1, idTransporte);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()){
				transporte = new TransporteDTO(resultSet.getInt("idTransporte"),
											   resultSet.getString("nombreTransporte")
											   );
				return transporte;
			}
			
		}catch (SQLException e){
			 e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		Transporte dao = new Transporte(new DAOSQLFactory());
		
		List<TransporteDTO> list = dao.obtenerTransportes();
		
		for(TransporteDTO elem: list){
			System.out.println(elem.getNombre());
		}
		System.out.println("- - - - - - - - - - - - -");
		System.out.println(dao.getTransporteById(1).getNombre());
	}
}