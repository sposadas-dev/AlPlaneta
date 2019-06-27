package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.EgresosDTO;
import dto.RolDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.EgresoDAO;

public class EgresoDAOSQL implements EgresoDAO{

	private static final String insert = "INSERT INTO egreso(idEgreso, idSueldoEmpleado, idServicio, idPasaje) VALUES(?,?,?,?)";
	private static final String readall = "SELECT * FROM egreso";
	
	@Override
	public boolean insert(EgresosDTO egreso) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, egreso.getIdEgreso());
			statement.setInt(2, egreso.getSueldos_empleados().getIdSueldoEmpleado());
			statement.setInt(3, egreso.getServicio().getIdServicio());
			statement.setInt(4, egreso.getPasaje().getIdPasaje());
			if(statement.executeUpdate() > 0) 
				return true;
		} 
		catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public List<EgresosDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<EgresosDTO> egresos = new ArrayList<EgresosDTO>();
		ServicioDAOSQL servicioDAOSQL = new ServicioDAOSQL();
		SueldosEmpleadosDAOSQL sueldos_empleados = new SueldosEmpleadosDAOSQL();
		PasajeDAOSQL pasaje = new PasajeDAOSQL();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				egresos.add(new EgresosDTO(
						resultSet.getInt("idEgreso"),
						sueldos_empleados.getSueldoEmpleadoById(resultSet.getInt("idSueldoEmpleado")),
						servicioDAOSQL.getServicioById(resultSet.getInt("idServicio")),
						pasaje.getPasajeById(resultSet.getInt("idPasaje")
						)));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return egresos;
	}
}
