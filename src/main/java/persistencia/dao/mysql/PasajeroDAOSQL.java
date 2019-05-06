package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.AdministrativoDTO;
import dto.PasajeroDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PasajeroDAO;

public class PasajeroDAOSQL implements PasajeroDAO {

	private static final String insert = "INSERT INTO pasajero(idPasajero, nombre, apellido, dni) VALUES(?, ?, ?, ?)";
	private static final String delete = "DELETE FROM pasajero WHERE idPasajero = ?";
	private static final String readall = "SELECT * FROM pasajero";
	private static final String update = "UPDATE pasajero SET nombre=?, apellido=?, dni=? WHERE idPasajero=?;";
	private static final String browse = "SELECT * FROM pasajero WHERE idPasajero=?";

	@Override
	public boolean insert(PasajeroDTO pasajeroInsert) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, pasajeroInsert.getIdPasajero());
			statement.setString(2, pasajeroInsert.getNombre());
			statement.setString(3, pasajeroInsert.getApellido());
			statement.setString(4, pasajeroInsert.getDni());
			if (statement.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean delete(PasajeroDTO pasajeroDelete) throws Exception {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(pasajeroDelete.getIdPasajero()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<PasajeroDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		ArrayList<PasajeroDTO> pasajeros = new ArrayList<PasajeroDTO>();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				pasajeros.add(new PasajeroDTO(resultSet.getInt("idPasajero"), resultSet.getString("nombre"),
						resultSet.getString("apellido"), resultSet.getString("dni")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pasajeros;
	}

	@Override
	public boolean update(PasajeroDTO pasajeroUpdate) throws Exception {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);

			statement.setString(1, pasajeroUpdate.getNombre());
			statement.setString(2, pasajeroUpdate.getApellido());
			statement.setString(3, pasajeroUpdate.getDni());
			statement.setInt(4, pasajeroUpdate.getIdPasajero());
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public PasajeroDTO browse(int idPasajero) throws Exception {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		PasajeroDTO pasajero;
		try {
			statement = conexion.getSQLConexion().prepareStatement(browse);
			
			statement.setInt(1, idPasajero);
			resultSet = statement.executeQuery();
			if (resultSet.next()){
				pasajero = new PasajeroDTO(resultSet.getInt("idPasajero"),
										   resultSet.getString("Nombre"),
										   resultSet.getString("Apellido"),
										   resultSet.getString("Dni")
										   );
				return pasajero;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	
	
	public static void main(String[] args) {
		
		PasajeroDAOSQL daoSQL = new PasajeroDAOSQL();

	/*Probamos El Insert en la tabla, luego verificar de forma manual que este registrado en la tabla*/	
		PasajeroDTO DTO = new PasajeroDTO(0,"LizzAdministrativa","Moreno","36584266");
		PasajeroDTO DTO2 = new PasajeroDTO(0,"MicaAdministrativa","Perez","32125322");
		PasajeroDTO DTO3 = new PasajeroDTO(0,"SolAdministrativa","Hoyos","25652544");
		
		daoSQL.insert(DTO);
		daoSQL.insert(DTO2);
		daoSQL.insert(DTO3);
		
	/*Probamos el ReadALL*/	
		ArrayList<PasajeroDTO> array = (ArrayList<PasajeroDTO>) daoSQL.readAll();
		
		for(PasajeroDTO ad: array)
			System.out.println(ad.getNombre());
		}

}