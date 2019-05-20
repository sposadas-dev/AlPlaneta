package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.AdministrativoDTO;
import dto.CiudadDTO;
import dto.MedioContactoDTO;
import dto.TransporteDTO;
import dto.ViajeDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ViajeDAO;

public class ViajeDAOSQL implements ViajeDAO {

	private static final String insert = "INSERT INTO viaje (idViaje, fechaSalida, fechaLlegada, precio, idCiudadOrigen, idCiudadDestino, idProvinciaOrigen, idProvinciaDestino, idPaisOrigen, idPaisDestino, horaSalida, idTransporte, horasEstimadas, capacidad) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String delete = "DELETE FROM viaje WHERE idViaje = ?";
	private static final String readall = "SELECT * FROM viaje";
	private static final String update = "UPDATE viaje SET precio =? WHERE idViaje= ?;";
	private static final String browse = "SELECT * FROM viaje WHERE idViaje = ?";


	@Override
	public boolean insert(ViajeDTO viaje) {

		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, viaje.getIdViaje());
			statement.setDate(2, viaje.getFechaSalida());
			statement.setDate(3, viaje.getFechaLlegada());
			statement.setBigDecimal(4, viaje.getPrecio());
			statement.setInt(5, viaje.getCiudadOrigen().getIdCiudad());
			statement.setInt(6, viaje.getCiudadDestino().getIdCiudad());
			statement.setInt(7, viaje.getProvinciaOrigen().getIdProvincia());
			statement.setInt(8, viaje.getProvinciaDestino().getIdProvincia());
			statement.setInt(9, viaje.getPaisOrigen().getIdPais());
			statement.setInt(10, viaje.getPaisDestino().getIdPais());
			statement.setString(11, viaje.getHoraSalida());
			statement.setInt(12, viaje.getTransporte().getIdTransporte());
			statement.setInt(13, viaje.getHorasEstimadas());
			statement.setInt(14, viaje.getCapacidad());
			
			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean delete(ViajeDTO viaje_a_eliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(viaje_a_eliminar.getIdViaje()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();;
		}
		return false;

	}
	
	@Override
	public List<ViajeDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<ViajeDTO> viajes = new ArrayList<ViajeDTO>();
		Conexion conexion = Conexion.getConexion();
	
		CiudadDAOSQL ciudadDAOSQL = new CiudadDAOSQL();
		ProvinciaDAOSQL provinciaDAOSQL = new ProvinciaDAOSQL();
		PaisDAOSQL paisDAOSQL = new PaisDAOSQL();
		TransporteDAOSQL transporteDAOSQL = new TransporteDAOSQL();
	
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()){
				viajes.add(new ViajeDTO(resultSet.getInt("idViaje"),
				ciudadDAOSQL.getCiudadById(resultSet.getInt("idCiudadOrigen")),
				ciudadDAOSQL.getCiudadById(resultSet.getInt("idCiudadDestino")),
				provinciaDAOSQL.getProvinciaById(resultSet.getInt("idProvinciaOrigen")),
				provinciaDAOSQL.getProvinciaById(resultSet.getInt("idProvinciaDestino")),
				paisDAOSQL.getPaisById(resultSet.getInt("idPaisOrigen")),
				paisDAOSQL.getPaisById(resultSet.getInt("idPaisDestino")),
									   resultSet.getDate("fechaSalida"),
									   resultSet.getDate("fechaLlegada"),
									   resultSet.getString("horaSalida"),
									   resultSet.getInt("horasEstimadas"),
				transporteDAOSQL.getTransporteById(resultSet.getInt("idTransporte")),
										resultSet.getInt("capacidad"),
									   resultSet.getBigDecimal("precio"))
										);
				}
			} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return viajes;
	}

	@Override
	public boolean update(ViajeDTO viaje_editar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(update);
			
			statement.setBigDecimal(1,viaje_editar.getPrecio());
			statement.setInt(2, viaje_editar.getIdViaje());
		
			chequeoUpdate = statement.executeUpdate();
			if(chequeoUpdate > 0) //Si se ejecutó devuelvo true
				return true;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	

	@Override
	public ViajeDTO getViajeById(int idViaje ){
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		CiudadDAOSQL ciudadDAOSQL = new CiudadDAOSQL();
		ProvinciaDAOSQL provinciaDAOSQL = new ProvinciaDAOSQL();
		PaisDAOSQL paisDAOSQL = new PaisDAOSQL();
		TransporteDAOSQL transporteDAOSQL = new TransporteDAOSQL();
		
		ViajeDTO viaje;
		try{
			statement = conexion.getSQLConexion().prepareStatement(browse);
			statement.setInt(1, idViaje);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()){
				viaje = new ViajeDTO(resultSet.getInt("idViaje"),
						ciudadDAOSQL.getCiudadById(resultSet.getInt("idCiudadOrigen")),
						ciudadDAOSQL.getCiudadById(resultSet.getInt("idCiudadDestino")),
						provinciaDAOSQL.getProvinciaById(resultSet.getInt("idProvinciaOrigen")),
						provinciaDAOSQL.getProvinciaById(resultSet.getInt("idProvinciaDestino")),
						paisDAOSQL.getPaisById(resultSet.getInt("idPaisOrigen")),
						paisDAOSQL.getPaisById(resultSet.getInt("idPaisDestino")),
									resultSet.getDate("fechaSalida"),
											   resultSet.getDate("fechaLlegada"),
											   resultSet.getString("horaSalida"),
											   resultSet.getInt("horasEstimadas"),
						transporteDAOSQL.getTransporteById(resultSet.getInt("idTransporte")),
												resultSet.getInt("capacidad"),
											   resultSet.getBigDecimal("precio")
												);
				return viaje;
			}
			
		}catch (SQLException e){
			 e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		ViajeDAOSQL adm = new ViajeDAOSQL();
		List<ViajeDTO> administratives = adm.readAll();
		
		for(ViajeDTO ad: administratives)
			System.out.println(ad.getFechaLlegada());
		}
	
}