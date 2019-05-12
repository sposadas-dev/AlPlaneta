package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.RolDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.RolDAO;

public class RolDAOSQL implements RolDAO{
	private static final String insert = "INSERT INTO rol" + "(idRol, descripcion)" + "VALUE(?,?)";
	private static final String readall = "SELECT * FROM rol";
	private static final String delete = "DELETE FROM rol WHERE idRol = ?";
	private static final String update = "UPDATE rol SET descripcion = ?,WHERE idRol = ?";
	
	private static final String browse = "SELECT * FROM rol WHERE idRol = ?";

	public boolean insert(RolDTO rol) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, rol.getIdRol());
			statement.setString(2, rol.getNombre());
			if(statement.executeUpdate() > 0) 
				return true;
		} 
		catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	public List<RolDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<RolDTO> roles = new ArrayList<RolDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				roles.add(new RolDTO(
						resultSet.getInt("idRol"),
						resultSet.getString("descripcion")));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return roles;
	}

	public boolean update(RolDTO rolupdate) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);
			statement.setInt(1, rolupdate.getIdRol());
			statement.setString(2, rolupdate.getNombre());

			if(statement.executeUpdate() > 0)
				return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}



	public boolean delete(RolDTO rol) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(rol.getIdRol()));
			chequeoUpdate = statement.executeUpdate();
			if(chequeoUpdate > 0)
				return true;
		} 
		catch (SQLException e) 	{
			e.printStackTrace();
		}
		return false;
	}
/*------------------------------------------------------*/
	public RolDTO getById(int idRol ){
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		RolDTO roldto;
		try{
			statement = conexion.getSQLConexion().prepareStatement(browse);
			statement.setInt(1, idRol);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()){
				roldto = new RolDTO(resultSet.getInt("idRol"),
				resultSet.getString("descripcion"));
				return roldto;
			}
			
		}catch (SQLException e){
			 e.printStackTrace();
		}
		return null;
	}
	
/*-------------------------------------------------------*/
//	public RolDTO getById(int id) {
//			PreparedStatement statement;
//			ResultSet resultSet; 
//			ArrayList<RolDTO> roles= new ArrayList<RolDTO>();
//			Conexion conexion = Conexion.getConexion();
//			try {
//				statement = conexion.getSQLConexion().prepareStatement(readall);
//				resultSet = statement.executeQuery();
//				while(resultSet.next()){
//					roles.add(new RolDTO(
//							resultSet.getInt("idRol"), 
//							resultSet.getString("descripcion")));
//				}
//			} 
//			catch (SQLException e) {
//				e.printStackTrace();
//			}
//			RolDTO ret = null;
//			
//			for(RolDTO r: roles){
//				if(r.getIdRol()==id)
//					ret = r;
//			}
//			return ret;
//	}
	
	public static void main(String[] args) {
		RolDAOSQL dao = new RolDAOSQL();
		
		List<RolDTO> list = dao.readAll();
		
		for(RolDTO elem: list){
			System.out.println(elem.getNombre());
		}
		System.out.println("- - - - - - - - - - - - -");
		System.out.println(dao.getById(1).getNombre());
	}
	
}
