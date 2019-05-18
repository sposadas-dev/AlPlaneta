package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.AdministrativoDTO;
import dto.LoginDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.AdministrativoDAO;

public class AdministrativoDAOSQL implements AdministrativoDAO {

	private static final String insert = "INSERT INTO administrativo(idAdministrativo, nombre, idLogin)" + " VALUES (?, ?, ?)";

	private static final String readall = "SELECT * FROM administrativo";

	private static final String update = "UPDATE administrativo SET nombre = ? WHERE idAdministrativo = ?";
	
	private static final String browse = "SELECT * FROM administrativo WHERE idAdministrativo = ?";
	private static final String browseLogin = "SELECT * FROM administrativo WHERE idLogin = ?";

	@Override
	public boolean insert(AdministrativoDTO administrativo) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, administrativo.getIdAdministrativo());
			statement.setString(2, administrativo.getNombre());
			statement.setInt(3, administrativo.getDatosLogin().getIdDatosLogin());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

	@Override
	public List<AdministrativoDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();

		ArrayList<AdministrativoDTO> administrativos = new ArrayList<AdministrativoDTO>();
		LoginDAOSQL dao = new LoginDAOSQL();
		
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				administrativos.add(
						new AdministrativoDTO(
								resultSet.getInt("idAdministrativo"),
								resultSet.getString("nombre"),
								dao.getById(resultSet.getInt("idLogin"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return administrativos;
	}

	@Override
	public boolean update(AdministrativoDTO administrativo) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);

			statement.setString(1, administrativo.getNombre());
			statement.setInt(2, administrativo.getIdAdministrativo()); // deberia

			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public AdministrativoDTO getByDatosLogin(String usuario, String constrasena) {
		ArrayList<AdministrativoDTO> personales = (ArrayList<AdministrativoDTO>) readAll();
		for(AdministrativoDTO adm: personales)
			if(adm.getDatosLogin().getUsuario().equals(usuario)&&
					adm.getDatosLogin().getContrasena().equals(constrasena))
						return adm;
		return null;
	}
	
	public AdministrativoDTO getById(int id ){
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		AdministrativoDTO dto;
		
		LoginDAOSQL dao = new LoginDAOSQL();
		try{
			statement = conexion.getSQLConexion().prepareStatement(browse);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()){
				dto = new AdministrativoDTO(
						resultSet.getInt("idAdministrativo"),
						resultSet.getString("nombre"),
						dao.getById(resultSet.getInt("idLogin")));
				return dto;
			}
			
		}catch (SQLException e){
			 e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public AdministrativoDTO getByLoginId(int id ){
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		AdministrativoDTO dto;
		
		LoginDAOSQL dao = new LoginDAOSQL();
		try{
			statement = conexion.getSQLConexion().prepareStatement(browseLogin);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()){
				dto = new AdministrativoDTO(
						resultSet.getInt("idAdministrativo"),
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
		AdministrativoDAOSQL dao = new AdministrativoDAOSQL();
		
		List<AdministrativoDTO> list = dao.readAll();
		
		for(AdministrativoDTO elem: list){
			System.out.println(elem.getNombre());
		}
		
		System.out.println(dao.getByLoginId(3).getNombre());
	}
	
}