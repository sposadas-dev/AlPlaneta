package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.SueldoDAO;
import dto.PagoDTO;
import dto.SueldoDTO;

public class SueldoDAOSQL implements SueldoDAO{
	
	private static final String insert = "INSERT INTO sueldo (idSueldo, montoSueldo, mes, idRol) VALUES(?,?,?,?)";
	private static final String ultimoRegistro = "SELECT * FROM sueldo ORDER BY idSueldo desc limit 1";
	
	@Override
	public boolean insert(SueldoDTO sueldo) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, sueldo.getIdSueldo());
			statement.setBigDecimal(2, sueldo.getMontoSueldo());
			statement.setDate(3, sueldo.getMes());
			statement.setInt(4, sueldo.getRol().getIdRol());
			if(statement.executeUpdate() > 0) 
				return true;
		} 
		catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
//	@Override
//	public SueldoDTO getSueldoById(int idEmpleado) {
//		PreparedStatement statement;
//		ResultSet resultSet;
//		Conexion conexion = Conexion.getConexion();
//		SueldoDTO sueldo;
//		RolDAOSQL rolDAOSQL = new RolDAOSQL();
//		try {
//			statement = conexion.getSQLConexion().prepareStatement(ultimoRegistro);
//			resultSet = statement.executeQuery();
//			
//			if (resultSet.next()){
//				sueldo = new SueldoDTO(resultSet.getInt("idSueldo"),
//						  (resultSet.getBigDecimal("montoSueldo")),
//					      resultSet.getDate("mes"),
//					      rolDAOSQL.getById(resultSet.getInt("idRol"))
//						  );
//				return sueldo;
//			}
//		} 
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	@Override
	public SueldoDTO getUltimoRegistroSueldo() {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		SueldoDTO sueldo;
		RolDAOSQL rolDAOSQL = new RolDAOSQL();
		try {
			statement = conexion.getSQLConexion().prepareStatement(ultimoRegistro);
			resultSet = statement.executeQuery();
			
			if (resultSet.next()){
				sueldo = new SueldoDTO(resultSet.getInt("idSueldo"),
						  (resultSet.getBigDecimal("montoSueldo")),
					      resultSet.getDate("mes"),
					      rolDAOSQL.getById(resultSet.getInt("idRol"))
						  );
				return sueldo;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
