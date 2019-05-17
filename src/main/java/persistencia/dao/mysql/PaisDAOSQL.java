package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.PaisDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PaisDAO;

public class PaisDAOSQL implements PaisDAO {
	private static final String insert = "INSERT INTO pais" + "(idPais, paisnombre)" + "VALUE(?,?)";
	private static final String readall = "SELECT * FROM pais";
	private static final String delete = "DELETE FROM pais WHERE idPais = ?";
	private static final String update = "UPDATE pais SET nombre = ? WHERE idPais = ?";
	private static final String browse = "SELECT * FROM pais WHERE idPais = ?";


	@Override
	public boolean insert(PaisDTO pais) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, pais.getIdPais());
			statement.setString(2, pais.getNombre());
			if(statement.executeUpdate() > 0) 
				return true;
		} 
		catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<PaisDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<PaisDTO> paises = new ArrayList<PaisDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				paises.add(new PaisDTO(
						resultSet.getInt("idPais"),
						resultSet.getString("paisnombre")));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return paises;
	}

	@Override
	public boolean update(PaisDTO paisNueva) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);
			statement.setString(1, paisNueva.getNombre());
			statement.setInt(2, paisNueva.getIdPais());
			if(statement.executeUpdate() > 0)
				return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(PaisDTO pais_a_eliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(pais_a_eliminar.getIdPais()));
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
	public PaisDTO getPaisById(int idPais) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		PaisDTO pais;
		
		try{
			statement = conexion.getSQLConexion().prepareStatement(browse);
			statement.setInt(1, idPais);
			resultSet = statement.executeQuery();
				
			if(resultSet.next()){
				pais = new PaisDTO(
						resultSet.getInt("idPais"),
						resultSet.getString("paisnombre"));
				return pais;
			}
				
		}catch (SQLException e){
			 e.printStackTrace();
		}
		return null;
	}
		
	public static void main(String[] args) {
		PaisDAOSQL adm = new PaisDAOSQL();
		List<PaisDTO> administratives = adm.readAll();
		int cont=0;
		for(PaisDTO ad: administratives) {
			cont++;
			System.out.println(ad.getNombre());			
		}
		System.out.println("cantPaises: "+cont);
	}
}