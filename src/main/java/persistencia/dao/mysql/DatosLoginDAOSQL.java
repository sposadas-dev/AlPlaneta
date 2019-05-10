package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.CiudadDTO;
import dto.DatosLoginDTO;
import dto.MedioContactoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.CiudadDAO;
import persistencia.dao.interfaz.DatosLoginDAO;

public class DatosLoginDAOSQL implements DatosLoginDAO{
	private static final String insert = "INSERT INTO login" + "(idLogin, usuario, contrasena)" + "VALUE(?,?,?)";
	private static final String readall = "SELECT * FROM login";
	private static final String delete = "DELETE FROM login WHERE idLogin = ?";
	private static final String update = "UPDATE login SET nombre = ?, constrasena = ? WHERE idLogin = ?";

	@Override
	public boolean insert(DatosLoginDTO datos) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, datos.getIdDatosLogin());
			statement.setString(2, datos.getUsuario());
			statement.setString(3, datos.getContrasena());
			if(statement.executeUpdate() > 0) 
				return true;
		} 
		catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<DatosLoginDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<DatosLoginDTO> datosLogin = new ArrayList<DatosLoginDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				datosLogin.add(new DatosLoginDTO(
						resultSet.getInt("idLogin"),
						resultSet.getString("usuario"),
						resultSet.getString("contrasena")));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return datosLogin;
	}

	@Override
	public boolean update(DatosLoginDTO datosNuevos) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);
			statement.setInt(1, datosNuevos.getIdDatosLogin());
			statement.setString(2, datosNuevos.getUsuario());
			statement.setString(3, datosNuevos.getContrasena());

			if(statement.executeUpdate() > 0)
				return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}



	@Override
	public boolean delete(DatosLoginDTO datos_a_eliminar) {
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
	public DatosLoginDTO getByDatos(String usr, String pass) throws Exception {
		ArrayList<DatosLoginDTO> datos = (ArrayList<DatosLoginDTO>) this.readAll();
		for(DatosLoginDTO d:datos)
			if(d.getUsuario().equals(usr)&&d.getContrasena().equals(pass))
				return d;
		// TODO Auto-generated method stub
		return null;
	}

	public DatosLoginDTO getById(int id) {
			PreparedStatement statement;
			ResultSet resultSet; 
			ArrayList<DatosLoginDTO> datos= new ArrayList<DatosLoginDTO>();
			Conexion conexion = Conexion.getConexion();
			try {
				statement = conexion.getSQLConexion().prepareStatement(readall);
				resultSet = statement.executeQuery();
				while(resultSet.next()){
					datos.add(new DatosLoginDTO(
							resultSet.getInt("idLogin"), 
							resultSet.getString("usuario"),
							resultSet.getString("contrasena")));
				}
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
			DatosLoginDTO ret = null;
			
			for(DatosLoginDTO datosLogin: datos){
				if(datosLogin.getIdDatosLogin()==id)
					ret = datosLogin;
			}
			return ret;
	}
	
}
