package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.FormaPagoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.FormaPagoDAO;


public class FormaPagoDAOSQL implements FormaPagoDAO {
	
	private static final String insert = "INSERT INTO formapago(idformapago, tipo) VALUES (?,?)";
	private static final String readall = "SELECT * from formapago";
	private static final String update  = "UPDATE formapago SET tipo = ? WHERE idformapago = ?";
	private static final String browse = "SELECT * FROM formapago WHERE idformapago=?";
	private static final String delete = "DELETE FROM formapago WHERE idformapago = ? ";
	
	public boolean insert(FormaPagoDTO estado) {
		
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
	
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);	
			statement.setInt(1, estado.getIdFormaPago());
			statement.setString(2, estado.getTipo());
			
		if(statement.executeUpdate() > 0) //Si se ejecuto devuelvo true
			return true;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return false;

	}

	@Override
	public boolean delete(FormaPagoDTO estado_a_eliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement  = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(estado_a_eliminar.getIdFormaPago()));
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
	public List<FormaPagoDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet;//Guarda el resultado de la query
		ArrayList<FormaPagoDTO> formapagos =  new ArrayList<FormaPagoDTO>();
		Conexion conexion = Conexion.getConexion();
		
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				formapagos.add(new FormaPagoDTO(
						resultSet.getInt("idformapago"),
						resultSet.getString("tipo"))
						);
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return formapagos;
		
	}

	@Override
	public boolean update(FormaPagoDTO forma_de_pago_a_editar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);
			statement.setString(1, forma_de_pago_a_editar.getTipo());
			statement.setInt(2, forma_de_pago_a_editar.getIdFormaPago());
			
			
			chequeoUpdate = statement.executeUpdate();
			
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public FormaPagoDTO getFormaPagoById(int idFormaPago) {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		FormaPagoDTO formapago;
		try {
			statement = conexion.getSQLConexion().prepareStatement(browse);
			statement.setInt(1, idFormaPago);
			resultSet = statement.executeQuery();
			 
			if(resultSet.next()) {
				formapago = new FormaPagoDTO(
						resultSet.getInt("idformapago"),
						resultSet.getString("tipo")
						);
						return formapago;
						}
		}catch (SQLException e){
			 e.printStackTrace();
		}
		return null;
	}
}
