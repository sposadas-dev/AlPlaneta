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

	private static final String insert = "INSERT INTO administrativo(nombre, apellido, dni, idLogin, mail, idLocal)" + " VALUES (?, ?, ?, ?, ?, ?)";
	private static final String readall = "SELECT * FROM administrativo";
	private static final String update = "UPDATE administrativo SET nombre = ?, apellido = ?, dni = ? WHERE idAdministrativo = ?";
	private static final String updateConstrasena = "UPDATE administrativo SET Login = ? WHERE idAdministrativo = ?";
	private static final String browse = "SELECT * FROM administrativo WHERE idAdministrativo = ?";
	private static final String browseLogin = "SELECT * FROM administrativo WHERE idLogin = ?";
	private static final String browseByMail = "SELECT * FROM administrativo WHERE mail = ?";
	private static final String delete = "DELETE FROM administrativo WHERE idAdministrativo = ?";

	private static final String browseByIdLocal = "SELECT * FROM administrativo WHERE idLocal = ?";

	@Override
	public boolean insert(AdministrativoDTO administrativo) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setString(1, administrativo.getNombre());
			statement.setString(2, administrativo.getApellido());
			statement.setString(3, administrativo.getDni());
			statement.setInt(4, administrativo.getDatosLogin().getIdDatosLogin());
			statement.setString(5, administrativo.getMail());
			statement.setInt(6, administrativo.getLocal().getIdLocal());

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
		LoginDAOSQL login = new LoginDAOSQL();
		LocalDAOSQL local = new LocalDAOSQL();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				administrativos.add(
						new AdministrativoDTO(
								resultSet.getInt("idAdministrativo"),
								resultSet.getString("nombre"),
								resultSet.getString("apellido"),
								resultSet.getString("dni"),
								login.getById(resultSet.getInt("idLogin")),
								resultSet.getString("mail" ),
								local.getById(resultSet.getInt("idLocal"))));
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
			statement.setString(2, administrativo.getApellido());
			statement.setString(3, administrativo.getDni());
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
	public boolean updateContrasena(AdministrativoDTO administrativo) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(updateConstrasena);

			statement.setString(1, administrativo.getMail());
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
		
		LoginDAOSQL login = new LoginDAOSQL();
		LocalDAOSQL local = new LocalDAOSQL();
		try{
			statement = conexion.getSQLConexion().prepareStatement(browse);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()){
				dto = new AdministrativoDTO(
						resultSet.getInt("idAdministrativo"),
						resultSet.getString("nombre"),
						resultSet.getString("apellido"),
						resultSet.getString("dni"),
						login.getById(resultSet.getInt("idLogin")),
						resultSet.getString("mail" ),
						local.getById(resultSet.getInt("idLocal")));
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
		
		LoginDAOSQL login = new LoginDAOSQL();
		LocalDAOSQL local = new LocalDAOSQL();
		try{
			statement = conexion.getSQLConexion().prepareStatement(browseLogin);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()){
				dto = new AdministrativoDTO(
						resultSet.getInt("idAdministrativo"),
						resultSet.getString("nombre"),
						resultSet.getString("apellido"),
						resultSet.getString("dni"),
						login.getById(resultSet.getInt("idLogin")),
						resultSet.getString("mail" ),
						local.getById(resultSet.getInt("idLocal")));
				return dto;
			}
			
		}catch (SQLException e){
			 e.printStackTrace();
		}
		return null;
	}

	public AdministrativoDTO getByMail(String mail){
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		AdministrativoDTO dto;
		
		LoginDAOSQL login = new LoginDAOSQL();
		LocalDAOSQL local = new LocalDAOSQL();
		try{
			statement = conexion.getSQLConexion().prepareStatement(browseByMail);
			statement.setString(1, mail);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()){
				dto = new AdministrativoDTO(
						resultSet.getInt("idAdministrativo"),
						resultSet.getString("nombre"),
						resultSet.getString("apellido"),
						resultSet.getString("dni"),
						login.getById(resultSet.getInt("idLogin")),
						resultSet.getString("mail" ),
						local.getById(resultSet.getInt("idLocal")));
				return dto;
			}
			
		}catch (SQLException e){
			 e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean delete(int idAdministrativo) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setInt(1, idAdministrativo);
			if (statement.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public List<AdministrativoDTO> getAdministrativosByLocal(int idLocal) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();

		ArrayList<AdministrativoDTO> administrativos = new ArrayList<AdministrativoDTO>();
		LoginDAOSQL login = new LoginDAOSQL();
		LocalDAOSQL local = new LocalDAOSQL();
		try {
			statement = conexion.getSQLConexion().prepareStatement(browseByIdLocal);
			statement.setInt(1, idLocal);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				administrativos.add(
						new AdministrativoDTO(
								resultSet.getInt("idAdministrativo"),
								resultSet.getString("nombre"),
								resultSet.getString("apellido"),
								resultSet.getString("dni"),
								login.getById(resultSet.getInt("idLogin")),
								resultSet.getString("mail" ),
								local.getById(resultSet.getInt("idLocal"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return administrativos;
	}
	
	public static void main(String[] args) {
		AdministrativoDAOSQL dao = new AdministrativoDAOSQL();
		AdministrativoDTO adm = dao.getByMail("ssol@gmail.com");
		if(adm!=null)
			System.out.println(adm.getNombre());
		else
			System.out.println("no existe");
//		
//		List<AdministrativoDTO> list = dao.readAll();
//			
//		for(AdministrativoDTO elem: list){
//			System.out.println(elem.getNom@bre());
//		}
		
//		System.out.println(dao.getByLoginId(3).getNombre());
//		System.out.println(dao.getByMail("sol@gmail.com").getNombre());
	}

	
}