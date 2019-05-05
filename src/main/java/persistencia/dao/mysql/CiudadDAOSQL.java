package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.CiudadDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.CiudadDAO;

public class CiudadDAOSQL implements CiudadDAO {
	private static final String insert = "INSERT INTO ciudad" + "(idCiudad, nombre)" + "VALUE(?,?)";
	private static final String readall = "SELECT * FROM ciudad";
	private static final String delete = "DELETE FROM ciudad WHERE idCiudad = ?";
	private static final String update = "UPDATE ciudad SET nombre = ? WHERE idCiudad = ?";

	@Override
	public boolean insert(CiudadDTO ciudad) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, ciudad.getIdCiudad());
			statement.setString(2, ciudad.getNombre());
			if(statement.executeUpdate() > 0) 
				return true;
		} 
		catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<CiudadDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<CiudadDTO> ciudades = new ArrayList<CiudadDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				ciudades.add(new CiudadDTO(resultSet.getInt("idCiudad"),resultSet.getString("nombre")));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return ciudades;
	}

	@Override
	public boolean update(CiudadDTO ciudadNueva) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);
			statement.setInt(1, ciudadNueva.getIdCiudad());
			statement.setString(2, ciudadNueva.getNombre());

			if(statement.executeUpdate() > 0)
				return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}



	@Override
	public boolean delete(CiudadDTO ciudad_a_eliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(ciudad_a_eliminar.getIdCiudad()));
			chequeoUpdate = statement.executeUpdate();
			if(chequeoUpdate > 0)
				return true;
		} 
		catch (SQLException e) 	{
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public CiudadDTO getCiudadByNombre(String nombre) {
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<CiudadDTO> ciudades= new ArrayList<CiudadDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				ciudades.add(new CiudadDTO(resultSet.getInt("idCiudad"),resultSet.getString("nombre")));
			}
		} 
		catch (SQLException e)	{
			e.printStackTrace();
		}
		CiudadDTO ret = null;
		
		for(CiudadDTO ciudad: ciudades){
			if(ciudad.getNombre().equals(nombre))
				ret = ciudad;
		}
		return ret;
	}
	
	@Override
	public CiudadDTO getCiudadById(int id) {
		PreparedStatement statement;
		ResultSet resultSet; 
		ArrayList<CiudadDTO> ciudades= new ArrayList<CiudadDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				ciudades.add(new CiudadDTO(resultSet.getInt("idCiudad"),resultSet.getString("nombre")));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		CiudadDTO ret = null;
		
		for(CiudadDTO ciudad: ciudades){
			if(ciudad.getIdCiudad()==id)
				ret = ciudad;
		}
		return ret;
	}
		
	public static void main(String[] args) {
		CiudadDAOSQL adm = new CiudadDAOSQL();
		List<CiudadDTO> administratives = adm.readAll();
		
		for(CiudadDTO ad: administratives)
			System.out.println(ad.getNombre());
		}
	

	
}