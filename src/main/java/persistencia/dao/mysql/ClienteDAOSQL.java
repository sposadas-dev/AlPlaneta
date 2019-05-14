package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.AdministrativoDTO;
import dto.ClienteDTO;
import dto.LoginDTO;
import dto.MedioContactoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ClienteDAO;

public class ClienteDAOSQL implements ClienteDAO {

	private static final String insert = "INSERT INTO cliente(idCliente, nombre, apellido, dni, fechaNacimiento, idMedioContacto, idLogin) VALUES(?, ?, ?, ?, ?, ?, ?)";
	private static final String readall = "SELECT * FROM cliente";
	private static final String update = "UPDATE cliente SET nombre=? , apellido=? , dni=? , fechaNacimiento=? , idMedioContacto= ? WHERE idCliente=? ;";
	private static final String browseLogin = "SELECT * FROM cliente WHERE idLogin = ?";
	
	@Override
	public boolean insert(ClienteDTO cliente) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, cliente.getIdCliente());
			statement.setString(2, cliente.getNombre());
			statement.setString(3, cliente.getApellido());
			statement.setString(4, cliente.getDni());
			statement.setDate(5, cliente.getFechaNacimiento());
			statement.setInt(6, cliente.getMedioContacto().getIdMedioContacto());
			statement.setInt(7,cliente.getLogin().getIdDatosLogin());
			
			if (statement.executeUpdate() > 0) // Si se ejecuto devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<ClienteDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<ClienteDTO> clientes = new ArrayList<ClienteDTO>();
		Conexion conexion = Conexion.getConexion();
		MedioContactoDAOSQL medioContactoDAOSQL = new MedioContactoDAOSQL();
		LoginDAOSQL dao =  new LoginDAOSQL();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {

				clientes.add(new ClienteDTO(
						resultSet.getInt("idCliente"),
						resultSet.getString("nombre"), 
						resultSet.getString("apellido"), 
						resultSet.getString("dni"), 
						resultSet.getDate("fechaNacimiento"),
						medioContactoDAOSQL.getMedioContactoById(resultSet.getInt("idMedioContacto")),
						dao.getById(resultSet.getInt("idLogin")))
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clientes;
	}

	@Override
	public boolean update(ClienteDTO cliente_editar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);
			statement.setString(1, cliente_editar.getNombre());
			statement.setString(2, cliente_editar.getApellido());
			statement.setString(3, cliente_editar.getDni());
			statement.setDate(4, cliente_editar.getFechaNacimiento());
			statement.setInt(5, cliente_editar.getMedioContacto().getIdMedioContacto());
			statement.setInt(6, cliente_editar.getIdCliente());

			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public ClienteDTO getByLoginId(int id) {
			PreparedStatement statement;
			ResultSet resultSet;
			Conexion conexion = Conexion.getConexion();
			ClienteDTO dto = null;
			MedioContactoDAOSQL medioDAO = new MedioContactoDAOSQL();
			LoginDAOSQL loginDAO = new LoginDAOSQL();
			
			try{
				statement = conexion.getSQLConexion().prepareStatement(browseLogin);
				statement.setInt(1, id);
				resultSet = statement.executeQuery();
				
				if(resultSet.next()){
					dto = new ClienteDTO(
							resultSet.getInt("idCliente"),
							resultSet.getString("nombre"),
							resultSet.getString("apellido"),
							resultSet.getString("dni"),
							resultSet.getDate("fechaNacimiento"),
							medioDAO.getMedioContactoById(resultSet.getInt("idMedioContacto")),
							loginDAO.getById(resultSet.getInt("idLogin")));
					return dto;
				}
				
			}catch (SQLException e){
				 e.printStackTrace();
			}
			return null;
		}
	
	public static void main(String[] args) {
		ClienteDAOSQL dao = new ClienteDAOSQL();
		
		List<ClienteDTO> list = dao.readAll();
		
		for(ClienteDTO elem: list){
			System.out.println(elem.getNombre());
		}
		
		System.out.println(dao.getByLoginId(5).getLogin().getUsuario());
		System.out.println(dao.getByLoginId(5).getMedioContacto().getTelefonoCelular());
	}
	
	
}