package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ContadorDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ContadorDAO;

public class ContadorDAOSQL implements ContadorDAO {

	private static final String insert = "INSERT INTO contador(nombre, apellido, dni, idLogin, mail, idLocal)"
			+ " VALUES (?, ?, ?, ?, ?, ?)";
	private static final String readall = "SELECT * FROM contador";
	private static final String update = "UPDATE contador SET nombre = ?, apellido = ?, dni = ? WHERE idContador = ?";
	private static final String updateMail = "UPDATE contador SET mail = ? WHERE idContador = ?";
	private static final String browse = "SELECT * FROM contador WHERE idContador = ?";
	private static final String browseLogin = "SELECT * FROM contador WHERE idLogin = ?";
	private static final String browseByMail = "SELECT * FROM contador WHERE mail = ?";
	private static final String delete = "DELETE FROM contador WHERE idContador = ?";

	public boolean insert(ContadorDTO contador) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setString(1, contador.getNombre());
			statement.setString(2, contador.getApellido());
			statement.setString(3, contador.getDni());
			statement.setInt(4, contador.getDatosLogin().getIdDatosLogin());
			statement.setString(5, contador.getMail());
			statement.setInt(6, contador.getLocal().getIdLocal());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<ContadorDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();

		ArrayList<ContadorDTO> contador = new ArrayList<ContadorDTO>();
		LoginDAOSQL login = new LoginDAOSQL();
		LocalDAOSQL local = new LocalDAOSQL();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				contador.add(new ContadorDTO(resultSet.getInt("idContador"), resultSet.getString("nombre"),
						resultSet.getString("apellido"), resultSet.getString("dni"),
						login.getById(resultSet.getInt("idLogin")), resultSet.getString("mail"),
						local.getById(resultSet.getInt("idLocal"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contador;
	}

	public boolean update(ContadorDTO contador) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);

			statement.setString(1, contador.getNombre());
			statement.setString(2, contador.getApellido());
			statement.setString(3, contador.getDni());
			statement.setInt(4, contador.getIdContador());

			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateMail(ContadorDTO contador) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(updateMail);

			statement.setString(1, contador.getMail());
			statement.setInt(2, contador.getIdContador()); // deberia

			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public ContadorDTO getById(int id) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		ContadorDTO contador;

		LoginDAOSQL login = new LoginDAOSQL();
		LocalDAOSQL local = new LocalDAOSQL();
		try {
			statement = conexion.getSQLConexion().prepareStatement(browse);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				contador = new ContadorDTO(resultSet.getInt("idContador"), resultSet.getString("nombre"),
						resultSet.getString("apellido"), resultSet.getString("dni"),
						login.getById(resultSet.getInt("idLogin")), resultSet.getString("mail"),
						local.getById(resultSet.getInt("idLocal")));
				return contador;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ContadorDTO getByLoginId(int id) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		ContadorDTO contador;

		LoginDAOSQL login = new LoginDAOSQL();
		LocalDAOSQL local = new LocalDAOSQL();
		try {
			statement = conexion.getSQLConexion().prepareStatement(browseLogin);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				contador = new ContadorDTO(resultSet.getInt("idContador"), resultSet.getString("nombre"),
						resultSet.getString("apellido"), resultSet.getString("dni"),
						login.getById(resultSet.getInt("idLogin")), resultSet.getString("mail"),
						local.getById(resultSet.getInt("idLocal")));
				return contador;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ContadorDTO getByMail(String mail) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		ContadorDTO contador;

		LoginDAOSQL login = new LoginDAOSQL();
		LocalDAOSQL local = new LocalDAOSQL();
		try {
			statement = conexion.getSQLConexion().prepareStatement(browseByMail);
			statement.setString(1, mail);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				contador = new ContadorDTO(resultSet.getInt("idAdministrador"), resultSet.getString("nombre"),
						resultSet.getString("apellido"), resultSet.getString("dni"),
						login.getById(resultSet.getInt("idLogin")), resultSet.getString("mail"),
						local.getById(resultSet.getInt("idLocal")));
				return contador;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean delete(int idContador) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();

		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setInt(1, idContador);

			if (statement.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}
