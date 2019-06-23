package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.AdministradorDTO;
import dto.CondicionDeCancelacionDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.CondicionDeCancelacionDAO;

public class CondicionDeCancelacionDAOSQL implements CondicionDeCancelacionDAO {
	private static final String insert = "INSERT INTO condicioncancelacion(idCondicion, inicio , fin,porcentaje, estado)" + " VALUES (?, ?, ?, ?, ?)";
	private static final String readall = "SELECT * FROM condicioncancelacion";
	private static final String update = "UPDATE condicioncancelacion SET inicio = ?, fin = ?, porcentaje = ?, estado = ? WHERE idCondicion = ?";
	private static final String browse = "SELECT * FROM condicioncancelacion WHERE idCondicion = ?";
	private static final String delete = "DELETE FROM condicioncancelacion WHERE idCondicion = ?";
	
	@Override
	public boolean insert(CondicionDeCancelacionDTO condicion) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, condicion.getIdCondicion());
			statement.setInt(2, condicion.getInicio());
			statement.setInt(3, condicion.getFin());
			statement.setInt(4, condicion.getPorcentaje());
			statement.setString(5, condicion.getEstadoDelPasaje());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean delete(int idCondicion) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setInt(1, idCondicion);
			
			if(statement.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public List<CondicionDeCancelacionDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();

		ArrayList<CondicionDeCancelacionDTO> condiciones = new ArrayList<CondicionDeCancelacionDTO>();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				condiciones.add(
						new CondicionDeCancelacionDTO(
								resultSet.getInt("idCondicion"),
								resultSet.getInt("inicio"),
								resultSet.getInt("fin"),
								resultSet.getInt("porcentaje"),
								resultSet.getString("estado")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return condiciones;
	}

	@Override
	public boolean update(CondicionDeCancelacionDTO condicion) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);

			statement.setInt(1, condicion.getInicio());
			statement.setInt(2, condicion.getFin());
			statement.setInt(3, condicion.getPorcentaje());
			statement.setString(4, condicion.getEstadoDelPasaje()); 
			statement.setInt(4, condicion.getIdCondicion()); 

			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public CondicionDeCancelacionDTO getById(int id) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		CondicionDeCancelacionDTO dto;
		
		try{
			statement = conexion.getSQLConexion().prepareStatement(browse);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()){
				dto = new CondicionDeCancelacionDTO(
						resultSet.getInt("idCondicion"),
						resultSet.getInt("inicio"),
						resultSet.getInt("fin"),
						resultSet.getInt("porcentaje"),
						resultSet.getString("estado"));
				return dto;
			}
			
		}catch (SQLException e){
			 e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		CondicionDeCancelacionDAOSQL dao = new CondicionDeCancelacionDAOSQL();
		dao.insert(new CondicionDeCancelacionDTO(1,0,6,25,"reservado"));
		
	}
}
