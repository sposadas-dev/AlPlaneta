package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.CiudadDTO;
import dto.EstadoPasajeDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.EstadoPasajeDAO;

public class EstadoPasajeDAOSQL implements EstadoPasajeDAO {
	private static final String insert = "INSERT INTO estadospasaje" + "(idEstadoPasaje, nombre, descripcion)" + "VALUES(?,?,?)";
	private static final String readall = "SELECT * FROM estadospasaje";
	private static final String delete = "DELETE FROM estadospasaje WHERE idEstadoPasaje = ?";
	private static final String update = "UPDATE estadosPasaje SET nombre = ? WHERE idEstadoPasaje = ?";

	@Override
	public boolean insert(EstadoPasajeDTO estado) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, estado.getIdEstadoPasaje());
			statement.setString(2, estado.getNombre());
			statement.setString(3,  estado.getDescripcion());
			if(statement.executeUpdate() > 0)
				return true;
		} 
		catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<EstadoPasajeDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<EstadoPasajeDTO> estados = new ArrayList<EstadoPasajeDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				estados.add(new EstadoPasajeDTO(resultSet.getInt("idEstadoPasaje"),resultSet.getString("nombre"),resultSet.getString("descripcion")));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return estados;
	}

	@Override
	public boolean update(EstadoPasajeDTO estadoPasajeNuevo) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);
			statement.setInt(1, estadoPasajeNuevo.getIdEstadoPasaje());
			statement.setString(2, estadoPasajeNuevo.getNombre());
			statement.setString(3, estadoPasajeNuevo.getDescripcion());
			
			if(statement.executeUpdate() > 0)
				return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}



	@Override
	public boolean delete(EstadoPasajeDTO estadoPasaje) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(estadoPasaje.getIdEstadoPasaje()));
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
	public EstadoPasajeDTO getEstadoPasajeByNombre(String nombre) {
		PreparedStatement statement;
		ResultSet resultSet; 
		ArrayList<EstadoPasajeDTO> estados= new ArrayList<EstadoPasajeDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				estados.add(new EstadoPasajeDTO(resultSet.getInt("idEstadoPasaje"),resultSet.getString("nombre"),resultSet.getString("descripcion")));
			}
		} 
		catch (SQLException e)	{
			e.printStackTrace();
		}
		EstadoPasajeDTO ret = null;
		
		for(EstadoPasajeDTO estado : estados){
			if(estado.getNombre().equals(nombre))
				ret = estado;
		}
		return ret;
	}
	
	@Override
	public EstadoPasajeDTO getEstadoPasajeById(int id) {
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<EstadoPasajeDTO> estados = new ArrayList<EstadoPasajeDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				estados.add(new EstadoPasajeDTO(resultSet.getInt("idEstadoPasaje"),resultSet.getString("nombre"),resultSet.getString("descripcion")));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		EstadoPasajeDTO ret = null;
		
		for(EstadoPasajeDTO estado : estados){
			if(estado.getIdEstadoPasaje()==id)
				ret = estado;
		}
		return ret;
	}
	
	
//	public static void main(String[] args) {
//		EstadoPasajeDAOSQL ciudadDAOSQL = new EstadoPasajeDAOSQL();
//		EstadoPasajeDTO estadoDTO = new EstadoPasajeDTO(0,"PAGO TOTAL","El pasaje está pagado completamente");
//		
//		ciudadDAOSQL.insert(estadoDTO);
//		ArrayList<EstadoPasajeDTO> ESTADOS = (ArrayList<EstadoPasajeDTO>) ciudadDAOSQL.readAll();
//		
//		for(EstadoPasajeDTO e : ESTADOS) {
//			System.out.println(e.getNombre()+" - "+e.getDescripcion());
//		}
		
//	}

}
