package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.TransporteDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.TransporteDAO;

public class TransporteDAOSQL implements TransporteDAO {

	private static final String insert = "INSERT INTO transporte(idTransporte,capacidad,nombreTransporte,"
			+ "precioBase)" + " VALUES (?, ?, ?, ?)";

	private static final String readall = "SELECT * FROM transporte";

	@Override
	public boolean insert(TransporteDTO transporte) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, transporte.getIdTransporte());
			statement.setInt(2, transporte.getCapacidad());
			statement.setString(3, transporte.getNombreTransporte());
			statement.setBigDecimal(4, transporte.getPrecioBase());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

	@Override
	public boolean delete(TransporteDTO transporte_a_eliminar) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(TransporteDTO transporte) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<TransporteDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();

		ArrayList<TransporteDTO> transportes = new ArrayList<TransporteDTO>();

		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				transportes.add(new TransporteDTO(resultSet.getInt("idTransporte"), resultSet.getInt("capacidad"),
						resultSet.getString("nombreTransporte"), resultSet.getBigDecimal("precioBase")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transportes;
	}

}
