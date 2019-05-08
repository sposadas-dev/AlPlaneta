package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.AdministrativoDTO;
import dto.CiudadDTO;
import dto.MedioContactoDTO;
import dto.ViajeDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.ViajeDAO;

public class ViajeDAOSQL implements ViajeDAO {

	private static final String insert = "INSERT INTO viaje (idViaje, fechaSalida, fechaLlegada, precio, idCiudadOrigen, idCiudadDestino, horaSalida) VALUES (?,?,?,?,?,?,?)";
	private static final String delete = "DELETE FROM viaje WHERE idViaje = ?";
	private static final String readall = "SELECT * FROM viaje";
	private static final String update = "UPDATE viaje SET precio =? WHERE idViaje= ?;";

	@Override
	public boolean insert(ViajeDTO viaje) {

		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, viaje.getId());
			statement.setDate(2, viaje.getFechaSalida());
			statement.setDate(3, viaje.getFechaLlegada());
			statement.setBigDecimal(4, viaje.getPrecio());
			statement.setInt(5, viaje.getOrigenViaje().getIdCiudad());
			statement.setInt(6, viaje.getDestinoViaje().getIdCiudad());
			statement.setString(7, viaje.getHoraSalida());

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
			statement.setString(1, Integer.toString(viaje_a_eliminar.getId()));
			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
//			e.printStackTrace();;
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
		
	try 
	{
		statement = conexion.getSQLConexion().prepareStatement(readall);
		resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			viajes.add(new ViajeDTO(resultSet.getInt("idViaje"),
			ciudadDAOSQL.getCiudadById(resultSet.getInt("idCiudadOrigen")),
			ciudadDAOSQL.getCiudadById(resultSet.getInt("idCiudadDestino")),
								   resultSet.getDate("fechaSalida"),
								   resultSet.getDate("fechaLlegada"),
								   resultSet.getBigDecimal("precio"),
								   resultSet.getString("horaSalida"))
									);
		}
	} 
	catch (SQLException e) 
	{
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
			statement.setInt(2, viaje_editar.getId());
		
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
	
	public static void main(String[] args) {
		ViajeDAOSQL adm = new ViajeDAOSQL();
		List<ViajeDTO> administratives = adm.readAll();
		
		for(ViajeDTO ad: administratives)
			System.out.println(ad.getOrigenViaje().getNombre());
		}
	
}
