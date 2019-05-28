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

	private static final String insert = "INSERT INTO pasajero(idPasajero, nombre, apellido, dni, fechaNacimiento,telefono, email) VALUES(?, ?, ?, ?, ?, ? ,?)";
	private static final String delete = "DELETE FROM pasajero WHERE idPasajero = ?";
	private static final String readall = "SELECT * FROM pasajero";
	private static final String update = "UPDATE pasajero SET nombre=?, apellido=?, dni=?, fechaNacimiento=?, telefono=?, email=? WHERE idPasajero=?;";
	private static final String browse = "SELECT * FROM pasajero WHERE idPasajero=?";
	
	private static final String browseByDni = "SELECT * FROM pasajero WHERE dni=?";
	
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
			statement.setDate(5, pasajeroInsert.getFechaNacimiento());
			statement.setString(6, pasajeroInsert.getTelefono());
			statement.setString(7, pasajeroInsert.getEmail());

			if (statement.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean delete(PasajeroDTO pasajeroDelete){
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
				pasajeros.add(new PasajeroDTO(resultSet.getInt("idPasajero"), 
											  resultSet.getString("nombre"),
											  resultSet.getString("apellido"), 
											  resultSet.getString("dni"),
											  resultSet.getDate("fechaNacimiento"),
											  resultSet.getString("telefono"),
											  resultSet.getString("email")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pasajeros;
	}

	@Override
	public boolean update(PasajeroDTO pasajeroUpdate) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);

			statement.setString(1, pasajeroUpdate.getNombre());
			statement.setString(2, pasajeroUpdate.getApellido());
			statement.setString(3, pasajeroUpdate.getDni());
			statement.setDate(4, pasajeroUpdate.getFechaNacimiento());
			statement.setString(5, pasajeroUpdate.getTelefono());
			statement.setString(6, pasajeroUpdate.getEmail());
			statement.setInt(7, pasajeroUpdate.getIdPasajero());
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public PasajeroDTO browse(int idPasajero) {
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
										   resultSet.getString("nombre"),
										   resultSet.getString("apellido"), 
										   resultSet.getString("dni"),
										   resultSet.getDate("fechaNacimiento"),
										   resultSet.getString("telefono"),
										   resultSet.getString("email")
						  );
				return pasajero;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public PasajeroDTO getPasajeroByDni(String dniPasajero) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		PasajeroDTO pasajero;
		try {
			statement = conexion.getSQLConexion().prepareStatement(browseByDni);
			
			statement.setString(1, dniPasajero);
			resultSet = statement.executeQuery();
			if (resultSet.next()){
				pasajero = new PasajeroDTO(resultSet.getInt("idPasajero"), 
										   resultSet.getString("nombre"),
										   resultSet.getString("apellido"), 
										   resultSet.getString("dni"),
										   resultSet.getDate("fechaNacimiento"),
										   resultSet.getString("telefono"),
										   resultSet.getString("email")
						  );
				return pasajero;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		
		PasajeroDAOSQL daoSQL = new PasajeroDAOSQL();
//
//	/*Probamos El Insert en la tabla, luego verificar de forma manual que este registrado en la tabla*/	
		PasajeroDTO DTO = new PasajeroDTO(0,"LizzAdministrativa","Moreno","36584266",null,"asd","asda");
//		PasajeroDTO DTO2 = new PasajeroDTO(0,"MicaAdministrativa","Perez","32125322");
//		PasajeroDTO DTO3 = new PasajeroDTO(0,"SolAdministrativa","Hoyos","25652544");
		
//		daoSQL.insert(DTO);
//		daoSQL.insert(DTO2);
//		daoSQL.insert(DTO3);
		System.out.println(daoSQL.getPasajeroByDni("36584266").getNombre());
//	/*Probamos el ReadALL*/	
//		ArrayList<PasajeroDTO> array = (ArrayList<PasajeroDTO>) daoSQL.readAll();
////		
//		for(PasajeroDTO ad: array)
//			System.out.println(ad.getNombre());
		}
	


}