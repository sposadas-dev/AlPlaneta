package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.AdministradorDTO;
import dto.AdministrativoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.AdministradorDAO;

public class AdministradorDAOSQL implements AdministradorDAO {
	private static final String insert = "INSERT INTO administrador(idAdministrador, nombre, idLogin)" + " VALUES (?, ?, ?)";

	private static final String readall = "SELECT * FROM administrador";

	private static final String update = "UPDATE administrador SET nombre = ? WHERE idAdministrador = ?";
	
	private static final String browse = "SELECT * FROM administrador WHERE idAdministrador = ?";
	private static final String browseLogin = "SELECT * FROM administrador WHERE idLogin = ?";

	@Override
	public boolean insert(AdministradorDTO administrador) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, administrador.getIdAdministrador());
			statement.setString(2, administrador.getNombre());
			statement.setInt(3, administrador.getDatosLogin().getIdDatosLogin());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

	@Override
	public List<AdministradorDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();

		ArrayList<AdministradorDTO> administrador = new ArrayList<AdministradorDTO>();
		LoginDAOSQL dao = new LoginDAOSQL();
		
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				administrador.add(
						new AdministradorDTO(
								resultSet.getInt("idAdministrador"),
								resultSet.getString("nombre"),
								dao.getById(resultSet.getInt("idLogin"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return administrador;
	}

	@Override
	public boolean update(AdministradorDTO administrador) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);

			statement.setString(1, administrador.getNombre());
			statement.setInt(2, administrador.getIdAdministrador()); // deberia
			statement.setInt(3, administrador.getDatosLogin().getIdDatosLogin());

			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	

//	public AdministradorDTO getByDatosLogin(String usuario, String constrasena) {
//		ArrayList<AdministradorDTO> personales = (ArrayList<AdministradorDTO>) readAll();
//		for(AdministradorDTO adm: personales)
//			if(adm.getDatosLogin().getUsuario().equals(usuario)&&
//					adm.getDatosLogin().getContrasena().equals(constrasena))
//						return adm;
//		return null;
//	}
	@Override
	public AdministradorDTO getById(int id ){
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		AdministradorDTO dto;
		
		LoginDAOSQL dao = new LoginDAOSQL();
		try{
			statement = conexion.getSQLConexion().prepareStatement(browse);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()){
				dto = new AdministradorDTO(
						resultSet.getInt("idAdministrador"),
						resultSet.getString("nombre"),
						dao.getById(resultSet.getInt("idLogin")));
				return dto;
			}
			
		}catch (SQLException e){
			 e.printStackTrace();
		}
		return null;
	}
	
	public AdministradorDTO getByLoginId(int id ){
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		AdministradorDTO dto;
		
		LoginDAOSQL dao = new LoginDAOSQL();
		try{
			statement = conexion.getSQLConexion().prepareStatement(browseLogin);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()){
				dto = new AdministradorDTO(
						resultSet.getInt("idAdministrador"),
						resultSet.getString("nombre"),
						dao.getById(resultSet.getInt("idLogin")));
				return dto;
			}
			
		}catch (SQLException e){
			 e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		AdministradorDAOSQL dao = new AdministradorDAOSQL();
		
		List<AdministradorDTO> list = dao.readAll();
		
		for(AdministradorDTO elem: list){
			System.out.println(elem.getNombre());
		}
		System.out.println(dao.getByLoginId(2).getNombre());
		System.out.println(dao.getByLoginId(5).getNombre());
	}

	@Override
	public boolean delete(AdministradorDTO administrador_a_eliminar) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
