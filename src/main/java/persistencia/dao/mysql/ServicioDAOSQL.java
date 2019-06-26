package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ServicioDAO;
import dto.PasajeDTO;
import dto.RolDTO;
import dto.ServicioDTO;

public class ServicioDAOSQL implements ServicioDAO{

	private static final String insert = "INSERT INTO servicio (idServicio,nombreServicio, monto, mes, idLocal) VALUES(?,?,?,?,?)";
	private static final String readall = "SELECT * FROM servicio";
	private static final String update = "UPDATE servicio SET nombreServicio = ?, monto=?, mes=?, idLocal=? WHERE idServicio = ?";
	private static final String delete = "DELETE FROM servicio WHERE idServicio = ?";
	private static final String browse = "SELECT * FROM servicio WHERE idServicio=?";

	
	@Override
	public boolean insert(ServicioDTO nuevoServicio) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, nuevoServicio.getIdServicio());
			statement.setString(2, nuevoServicio.getNombreServicio());
			statement.setBigDecimal(3, nuevoServicio.getMonto());
			statement.setDate(4, nuevoServicio.getMes());
			statement.setInt(5, nuevoServicio.getLocal().getIdLocal());
			if(statement.executeUpdate() > 0) 
				return true;
		} 
		catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public List<ServicioDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<ServicioDTO> servicios = new ArrayList<ServicioDTO>();
		LocalDAOSQL localDAOSQL = new LocalDAOSQL();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				servicios.add(new ServicioDTO(
						resultSet.getInt("idServicio"),
						resultSet.getString("nombreServicio"),
						resultSet.getBigDecimal("monto"),
						resultSet.getDate("mes"),
						localDAOSQL.getById(resultSet.getInt("idLocal"))));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return servicios;
	}
	
	@Override
	public boolean update(ServicioDTO servicio_a_editar) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);
			statement.setString(1, servicio_a_editar.getNombreServicio());
			statement.setBigDecimal(2, servicio_a_editar.getMonto());
			statement.setDate(3, servicio_a_editar.getMes());
			statement.setInt(4, servicio_a_editar.getLocal().getIdLocal());
			statement.setInt(5, servicio_a_editar.getIdServicio());
			if(statement.executeUpdate() > 0)
				return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(ServicioDTO eliminarServicio) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setInt(1, eliminarServicio.getIdServicio());
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
	public ServicioDTO getServicioById(int idServicio ){
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		LocalDAOSQL localDAOSQL = new LocalDAOSQL();
		ServicioDTO servicio;
		
		try{
			statement = conexion.getSQLConexion().prepareStatement(browse);
			statement.setInt(1, idServicio);
			resultSet = statement.executeQuery();
				
				if(resultSet.next()){
					servicio = new ServicioDTO(
							resultSet.getInt("idServicio"),
							resultSet.getString("nombreServicio"),
							resultSet.getBigDecimal("monto"),
							resultSet.getDate("mes"),
							localDAOSQL.getById(resultSet.getInt("idLocal")));
										
				return servicio;
				}
				
			}catch (SQLException e){
				 e.printStackTrace();
			}
			return null;
		}
	
}
