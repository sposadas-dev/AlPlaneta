package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.LoginDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.LoginDAO;

public class LoginDAOSQL implements LoginDAO{
	private static final String insert = "INSERT INTO login (idLogin, usuario, contrasena, idRol, estado) VALUES (?,?,?,?,?)";
	private static final String readall = "SELECT * FROM login";
	private static final String delete = "DELETE FROM login WHERE idLogin = ?";
	private static final String update = "UPDATE login SET usuario = ?, contrasena = ?, idRol = ?, estado=? WHERE idLogin = ?";
	private static final String updateStatus = "UPDATE login SET estado = ? WHERE idLogin = ?";
	private static final String browse = "SELECT * FROM login WHERE idLogin = ?";

	@Override
	public boolean insert(LoginDTO datos) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, datos.getIdDatosLogin());
			statement.setString(2, datos.getUsuario());
			statement.setString(3, datos.getContrasena());
			statement.setInt(4, datos.getRol().getIdRol());
			statement.setString(5, datos.getEstado());
			if(statement.executeUpdate() > 0) 
				return true;
		} 
		catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<LoginDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<LoginDTO> datosLogin = new ArrayList<LoginDTO>();
		Conexion conexion = Conexion.getConexion();
		RolDAOSQL dao = new RolDAOSQL();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				datosLogin.add(new LoginDTO(
						resultSet.getInt("idLogin"),
						resultSet.getString("usuario"),
						resultSet.getString("contrasena"),
						dao.getById(resultSet.getInt("idRol")),
						resultSet.getString("estado"))
						);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return datosLogin;
	}

	@Override
	public boolean update(LoginDTO datosNuevos) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);
			statement.setString(1, datosNuevos.getUsuario());
			statement.setString(2, datosNuevos.getContrasena());
			statement.setInt(3, datosNuevos.getRol().getIdRol());
			statement.setString(4, datosNuevos.getEstado());
			statement.setInt(5, datosNuevos.getIdDatosLogin());

			if(statement.executeUpdate() > 0)
				return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateEstado(String estado, int idLogin) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(updateStatus);
			statement.setString(1, estado);
			statement.setInt(2, idLogin);
			
			if(statement.executeUpdate() > 0)
				return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(LoginDTO datos_a_eliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(datos_a_eliminar.getIdDatosLogin()));
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
	public LoginDTO getByDatos(String usr, String pass) {
		ArrayList<LoginDTO> datos = (ArrayList<LoginDTO>) this.readAll();
		for(LoginDTO d:datos)
			if(d.getUsuario().equals(usr)&&d.getContrasena().equals(pass))
				return d;
		return null;
	}

	public LoginDTO getById(int id) {
			PreparedStatement statement;
			ResultSet resultSet;
			Conexion conexion = Conexion.getConexion();
			LoginDTO dto;
			RolDAOSQL dao = new RolDAOSQL();
			try{
				statement = conexion.getSQLConexion().prepareStatement(browse);
				statement.setInt(1, id);
				resultSet = statement.executeQuery();
				
				if(resultSet.next()){
					dto = new LoginDTO(
							resultSet.getInt("idLogin"),
					resultSet.getString("usuario"),
					resultSet.getString("contrasena"),
					dao.getById(resultSet.getInt("idRol")),
					resultSet.getString("estado"));
					return dto;
				}
				
			}catch (SQLException e){
				 e.printStackTrace();
			}
			return null;
		}
	
	public static void main(String[] args) {
		LoginDAOSQL dao = new LoginDAOSQL();
		
		List<LoginDTO> list = dao.readAll();
		
		for(LoginDTO elem: list){
			System.out.println(elem.getUsuario());
		}
		
		System.out.println(dao.getById(1).getRol().getNombre());
	}
	
}
