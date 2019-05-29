package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dto.EstadoEventoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.EstadoEventoDAO;

public class EstadoEventoDAOSQL implements EstadoEventoDAO {
	private static final String insert = "INSERT INTO estadoevento" + "(idEstadoEvento, nombre, descripcion)" + "VALUES(?,?,?)";
	private static final String readall = "SELECT * FROM estadoevento";
	private static final String delete = "DELETE FROM estadoevento WHERE idEstadoEvento = ?";
	private static final String update = "UPDATE estadoevento SET nombre = ? WHERE idEstadoEvento = ?";

	@Override
	public boolean insert(EstadoEventoDTO estado) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, estado.getIdEstadoEvento());
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
	public List<EstadoEventoDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<EstadoEventoDTO> estados = new ArrayList<EstadoEventoDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				estados.add(new EstadoEventoDTO(resultSet.getInt("idEstadoEvento"),resultSet.getString("nombre"),resultSet.getString("descripcion")));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return estados;
	}

	@Override
	public boolean update(EstadoEventoDTO estado) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);
			statement.setInt(1, estado.getIdEstadoEvento());
			statement.setString(2, estado.getNombre());
			statement.setString(3, estado.getDescripcion());
			
			if(statement.executeUpdate() > 0)
				return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(EstadoEventoDTO estado) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(estado.getIdEstadoEvento()));
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
	public EstadoEventoDTO getEstadoEventoByNombre(String nombre) {
		PreparedStatement statement;
		ResultSet resultSet; 
		ArrayList<EstadoEventoDTO> estados= new ArrayList<EstadoEventoDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				estados.add(new EstadoEventoDTO(resultSet.getInt("idEstadoEvento"),resultSet.getString("nombre"),resultSet.getString("descripcion")));
			}
		} 
		catch (SQLException e)	{
			e.printStackTrace();
		}
		EstadoEventoDTO ret = null;
		
		for(EstadoEventoDTO estado : estados){
			if(estado.getNombre().equals(nombre))
				ret = estado;
		}
		return ret;
	}
	
	@Override
	public EstadoEventoDTO getEstadoEventoById(int id) {
		PreparedStatement statement;
		ResultSet resultSet;
		ArrayList<EstadoEventoDTO> estados = new ArrayList<EstadoEventoDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				estados.add(new EstadoEventoDTO(resultSet.getInt("idEstadoEvento"),resultSet.getString("nombre"),resultSet.getString("descripcion")));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		EstadoEventoDTO ret = null;
		
		for(EstadoEventoDTO estado : estados){
			if(estado.getIdEstadoEvento()==id)
				ret = estado;
		}
		return ret;
	}
	
	public static void main(String[] args) {
		EstadoEventoDAOSQL estadoeventoDAOSQL = new EstadoEventoDAOSQL();
		EstadoEventoDTO estadoEventoDTO = new EstadoEventoDTO(5,"Estado nuevo","Este es un nuevo estado");
		
		estadoeventoDAOSQL.insert(estadoEventoDTO);
		ArrayList<EstadoEventoDTO> ESTADOS = (ArrayList<EstadoEventoDTO>) estadoeventoDAOSQL.readAll();
		
		for(EstadoEventoDTO e : ESTADOS) {
			System.out.println(e.getNombre()+" - "+e.getDescripcion());
		}
		System.out.println("-- FIN ESTADOS --");
	}
	
}