package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.RolDTO;
import dto.SueldoDTO;
import dto.Sueldos_EmpleadosDTO;
import dto.ViajeDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.Sueldos_EmpleadosDAO;

public class SueldosEmpleadosDAOSQL implements Sueldos_EmpleadosDAO {

	private static final String insert = "INSERT INTO sueldos_empleados (idSueldoEmpleado, idEmpleado, idSueldo) VALUES(?,?,?)";
	private static final String readall = "SELECT * FROM sueldos_empleados WHERE idEmpleado=?";
	private static final String browseSueldoById = "SELECT * FROM sueldo, sueldos_empleados WHERE sueldos_empleados.idEmpleado =? AND sueldo.idRol=? AND sueldo.idSueldo = sueldos_empleados.idSueldo";

	@Override
	public boolean insert(Sueldos_EmpleadosDTO sueldo) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, sueldo.getIdSueldoEmpleado());
			statement.setInt(2, sueldo.getIdEmpleado());
			statement.setInt(3, sueldo.getIdSueldo());
			if(statement.executeUpdate() > 0) 
				return true;
		} 
		catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public SueldoDTO getSueldoByEmpleado(int idEmpleado, int idRol) {
		PreparedStatement statement;
		SueldoDTO sueldo;
		ResultSet resultSet; //Guarda el resultado de la query
		RolDAOSQL rolDAOSQL = new RolDAOSQL();
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(browseSueldoById);
			statement.setInt(1, idEmpleado);
			statement.setInt(2,idRol);
			resultSet = statement.executeQuery();
			
			if (resultSet.next()){
				sueldo = new SueldoDTO(resultSet.getInt("idSueldo"),
						  (resultSet.getBigDecimal("montoSueldo")),
					      resultSet.getDate("mes"),
					      rolDAOSQL.getById(resultSet.getInt("idRol"))
						  );
				return sueldo;
			}	
		}catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}
}
