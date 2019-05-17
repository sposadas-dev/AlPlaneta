package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.CiudadDTO;
import dto.ProvinciaDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ProvinciaDAO;
/*RENOMBRAR TABLA ESTADO A PROVINCIA*/
public class ProvinciaDAOSQL implements ProvinciaDAO {
	private static final String insert = "INSERT INTO provincia" + "(idProvincia, nombre, idPais)" + "VALUE(?,?,?)";
	private static final String readall = "SELECT * FROM provincia";
	private static final String delete = "DELETE FROM provincia WHERE idProvincia = ?";
	private static final String update = "UPDATE provincia SET nombre = ? WHERE idProvincia = ?";
	private static final String browse = "SELECT * FROM provincia WHERE idProvincia = ?";
	private static final String browseLogin = "SELECT * FROM provincia WHERE idPais = ?";


	@Override
	public boolean insert(ProvinciaDTO provincia) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, provincia.getIdProvincia());
			statement.setString(2, provincia.getNombre());
			statement.setInt(3, provincia.getPais().getIdPais());
			if(statement.executeUpdate() > 0) 
				return true;
		} 
		catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<ProvinciaDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<ProvinciaDTO> provincia = new ArrayList<ProvinciaDTO>();
		Conexion conexion = Conexion.getConexion();
		PaisDAOSQL dao = new PaisDAOSQL();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()){
				provincia.add(new ProvinciaDTO(
						resultSet.getInt("idProvincia"),
						resultSet.getString("nombre"),
						dao.getPaisById(resultSet.getInt("idPais"))));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return provincia;
	}
	
	@Override
	public List<ProvinciaDTO> readAllByIdPais(int idPais) {
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<ProvinciaDTO> provincia = new ArrayList<ProvinciaDTO>();
		Conexion conexion = Conexion.getConexion();
		PaisDAOSQL dao = new PaisDAOSQL();
		
		try {
			statement = conexion.getSQLConexion().prepareStatement(browseLogin);
			statement.setInt(1, idPais);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()){
				provincia.add(new ProvinciaDTO(
						resultSet.getInt("idProvincia"),
						resultSet.getString("nombre"),
						dao.getPaisById(resultSet.getInt("idPais"))));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return provincia;
	}
	
	@Override
	public boolean update(ProvinciaDTO provinciaNueva) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);
			statement.setString(1, provinciaNueva.getNombre());
			statement.setInt(2, provinciaNueva.getIdProvincia());

			if(statement.executeUpdate() > 0)
				return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(ProvinciaDTO provincia_a_eliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(provincia_a_eliminar.getIdProvincia()));
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
	public ProvinciaDTO getProvinciaById(int idProvincia) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		ProvinciaDTO provincia;
		PaisDAOSQL dao = new PaisDAOSQL();
		try{
			statement = conexion.getSQLConexion().prepareStatement(browse);
			statement.setInt(1, idProvincia);
			resultSet = statement.executeQuery();
				
			if(resultSet.next()){
				provincia = new ProvinciaDTO(
						resultSet.getInt("idProvincia"),
						resultSet.getString("nombre"),
						dao.getPaisById(resultSet.getInt("idPais")));
				return provincia;
			}
				
		}catch (SQLException e){
			 e.printStackTrace();
		}
		return null;
	}
	
		
	public static void main(String[] args) {
		ProvinciaDAOSQL adm = new ProvinciaDAOSQL();
		List<ProvinciaDTO> administratives = adm.readAll();
		int cont = 0; 
		for(ProvinciaDTO ad: administratives) {
		//	System.out.println(ad.getNombre());
			cont++;
		}
		//System.out.println("cantProvincias: "+cont);
		for(ProvinciaDTO P : adm.readAllByIdPais(5))
			System.out.println(P.getNombre());
	}
	
	
}
