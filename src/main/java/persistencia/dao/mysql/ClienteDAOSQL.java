package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import dto.ClienteDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ClienteDAO;

public class ClienteDAOSQL implements ClienteDAO{
	
	private static final String insert = "INSERT INTO cliente(idCliente, nombre, apellido, dni, fechaNacimiento, telefonoFijo, telefonoCelular, email ) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
	
	public boolean insert(ClienteDTO cliente){
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);		
			statement.setInt(1, cliente.getIdCliente());
			statement.setString(2, cliente.getNombre());
			statement.setString(3, cliente.getApellido());
			statement.setString(4, cliente.getDni());
			statement.setString(5, cliente.getFechaOrdenada());
			statement.setString(6, cliente.getMedioContacto().getTelefonoFijo());
			statement.setString(7, cliente.getMedioContacto().getTelefonoCelular());
			statement.setString(8, cliente.getMedioContacto().getEmail());

			if(statement.executeUpdate() > 0) //Si se ejecuto devuelvo true
				return true;
		} 
		catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}
}