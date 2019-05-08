package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ClienteDTO;
import dto.MedioContactoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ClienteDAO;

public class ClienteDAOSQL implements ClienteDAO {

	private static final String insert = "INSERT INTO cliente(idCliente, nombre, apellido, dni, fechaNacimiento, idMedioContacto) VALUES(?, ?, ?, ?, ?, ?)";
	private static final String readall = "SELECT * FROM cliente";
	private static final String update = "UPDATE cliente SET nombre=? , apellido=? , dni=? , fechaNacimiento=? , idMedioContacto= ? WHERE idCliente=? ;";

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

		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				System.out.println(resultSet.getInt("idMedioContacto"));

				clientes.add(new ClienteDTO(resultSet.getInt("idCliente"),
						resultSet.getString("nombre"), 
						resultSet.getString("apellido"), 
						resultSet.getString("dni"), 
						resultSet.getDate("fechaNacimiento"),
						medioContactoDAOSQL.getMedioContactoById(resultSet.getInt("idMedioContacto"))
						)
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
			statement.setInt(5, cliente_editar.getIdCliente());

			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) {
//		ClienteDAOSQL adm = new ClienteDAOSQL();
//		List<ClienteDTO> administratives = adm.readAll();
//		
//		for(ClienteDTO ad: administratives)
//			System.out.println(ad.getMedioContacto().getTelefonoCelular());
//		}
		ClienteDAOSQL adm = new ClienteDAOSQL();
		List<ClienteDTO> administratives = adm.readAll();
//		ClienteDTO m= adm.readAll()(29);
		
		for(ClienteDTO ad: administratives)
			System.out.println(ad.getMedioContacto().getTelefonoCelular());
	}
}