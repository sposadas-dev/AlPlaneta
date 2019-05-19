package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.CiudadDTO;
import dto.ProvinciaDTO;
import dto.TransporteDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.CiudadDAO;

public class CiudadDAOSQL implements CiudadDAO {
	private static final String insert = "INSERT INTO ciudad" + "(idCiudad, ciudadNombre, idProvincia)" + "VALUE(?,?,?)";
	private static final String readall = "SELECT * FROM ciudad";
	private static final String delete = "DELETE FROM ciudad WHERE idCiudad = ?";
	private static final String update = "UPDATE ciudad SET ciudadNombre = ? WHERE idCiudad = ?";
	private static final String browse = "SELECT * FROM ciudad WHERE idCiudad = ?";
	private static final String browseLogin = "SELECT * FROM ciudad WHERE idProvincia = ?";


	@Override
	public boolean insert(CiudadDTO ciudad) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, ciudad.getIdCiudad());
			statement.setString(2, ciudad.getNombre());
			statement.setInt(3, ciudad.getProvincia().getIdProvincia());
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
		
		ProvinciaDAOSQL dao = new ProvinciaDAOSQL();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				ciudades.add(new CiudadDTO(
						resultSet.getInt("idCiudad"),
						resultSet.getString("ciudadNombre"),
						dao.getProvinciaById(resultSet.getInt("idProvincia"))
						));
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
			statement.setString(1,ciudadNueva.getNombre());
			statement.setInt(2, ciudadNueva.getIdCiudad());

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
	public CiudadDTO getCiudadById(int idCiudad) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		CiudadDTO ciudad;
		ProvinciaDAOSQL dao = new ProvinciaDAOSQL();
		try{
			statement = conexion.getSQLConexion().prepareStatement(browse);
			statement.setInt(1, idCiudad);
			resultSet = statement.executeQuery();
				
			if(resultSet.next()){
				ciudad = new CiudadDTO(
						resultSet.getInt("idCiudad"),
						resultSet.getString("ciudadNombre"),
						dao.getProvinciaById(resultSet.getInt("idProvincia"))
						);
				return ciudad;
			}
				
		}catch (SQLException e){
			 e.printStackTrace();
		}
		return null;
	}
		


	@Override
	public List<CiudadDTO> readAllByIdprovincia(int idProvincia) {
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<CiudadDTO> ciudades = new ArrayList<CiudadDTO>();
		Conexion conexion = Conexion.getConexion();
		ProvinciaDAOSQL dao = new ProvinciaDAOSQL();
		
		try {
			statement = conexion.getSQLConexion().prepareStatement(browseLogin);
			statement.setInt(1, idProvincia);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()){
				ciudades.add(new CiudadDTO(
						resultSet.getInt("idCiudad"),
						resultSet.getString("ciudadNombre"),
						dao.getProvinciaById(resultSet.getInt("idProvincia"))));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return ciudades;
	}
	public static void main(String[] args) {
		CiudadDAOSQL adm = new CiudadDAOSQL();
		
		CiudadDTO ciudad = adm.getCiudadById(1);
		System.out.println(ciudad.getNombre());
		ciudad.setNombre("Cafayate");
		adm.update(ciudad);
		System.out.println(adm.getCiudadById(1).getNombre());
		
//		List<CiudadDTO> administratives = adm.readAll();

//		int cont = 0; 
//		for(CiudadDTO ad: administratives) {
//			//System.out.println(ad.getNombre());
//			cont++;
//		}
		//System.out.println("cantCiudades: "+cont);
//		for(CiudadDTO c : adm.readAllByIdprovincia(1818))
//			System.out.println(c.getNombre());
	}
}