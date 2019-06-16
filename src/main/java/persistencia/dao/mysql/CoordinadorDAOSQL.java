package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.CoordinadorDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.CoordinadorDAO;

public class CoordinadorDAOSQL implements CoordinadorDAO {
	
	private static final String insert = "INSERT INTO coordinador(nombre, apellido, dni, idLogin, mail, idLocal)  VALUES (?, ?, ?, ?, ?,?)";
	private static final String readall = "SELECT * FROM coordinador";
	private static final String update = "UPDATE coordinador SET nombre = ?, apellido = ?, dni = ? WHERE idCoordinador = ?";
	private static final String browse = "SELECT * FROM coordinador WHERE idCoordinador = ?";
	private static final String browseLogin = "SELECT * FROM coordinador WHERE idLogin = ?";
	private static final String browseByMail = "SELECT * FROM coordinador WHERE mail = ?";
	private static final String delete = "DELETE FROM coordinador WHERE idCoordinador = ?";
	
	@Override
	public boolean insert(CoordinadorDTO coordinador) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setString(1, coordinador.getNombre());
			statement.setString(2, coordinador.getApellido());
			statement.setString(3, coordinador.getDni());
			statement.setInt(4, coordinador.getDatosLogin().getIdDatosLogin());
			statement.setString(5, coordinador.getMail());
			statement.setInt(6, coordinador.getLocal().getIdLocal());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

	@Override
	public List<CoordinadorDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();

		ArrayList<CoordinadorDTO> coordinador = new ArrayList<CoordinadorDTO>();
		LoginDAOSQL login = new LoginDAOSQL();
		LocalDAOSQL local = new LocalDAOSQL();
		
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				coordinador.add(
						new CoordinadorDTO(
								resultSet.getInt("idCoordinador"),
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
		return coordinador;
	}

	@Override
	public boolean update(CoordinadorDTO coordinador) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);

			statement.setString(1, coordinador.getNombre());
			statement.setString(2, coordinador.getApellido());
			statement.setString(3, coordinador.getDni());
			statement.setInt(4, coordinador.getIdCoordinador()); 

			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public CoordinadorDTO getByLoginId(int id) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		CoordinadorDTO dto;
		
		LoginDAOSQL login = new LoginDAOSQL();
		LocalDAOSQL local = new LocalDAOSQL();
		try{
			statement = conexion.getSQLConexion().prepareStatement(browseLogin);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()){
				dto = new CoordinadorDTO(
						resultSet.getInt("idCoordinador"),
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
	public CoordinadorDTO getById(int id) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		CoordinadorDTO dto;
		
		LoginDAOSQL login = new LoginDAOSQL();
		LocalDAOSQL local = new LocalDAOSQL();
		try{
			statement = conexion.getSQLConexion().prepareStatement(browse);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()){
				dto = new CoordinadorDTO(
						resultSet.getInt("idCoordinador"),
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
	public CoordinadorDTO getByMail(String mail) {
			PreparedStatement statement;
			ResultSet resultSet;
			Conexion conexion = Conexion.getConexion();
			CoordinadorDTO dto;
			
			LoginDAOSQL login = new LoginDAOSQL();
			LocalDAOSQL local = new LocalDAOSQL();
			try{
				statement = conexion.getSQLConexion().prepareStatement(browseByMail);
				statement.setString(1, mail);
				resultSet = statement.executeQuery();
				
				if(resultSet.next()){
					dto = new CoordinadorDTO(
							resultSet.getInt("idCoordinador"),
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
	
	public boolean deleteCoordinador(int idCoordinador) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			
			statement.setInt(1, idCoordinador);
			if(statement.executeUpdate() > 0) {
				return true;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
