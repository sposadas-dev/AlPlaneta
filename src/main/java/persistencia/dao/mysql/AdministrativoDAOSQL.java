package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.AdministrativoDTO;
import modelo.Administrativo;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.AdministrativoDAO;

public class AdministrativoDAOSQL implements AdministrativoDAO {

	private static final String insert = "INSERT INTO administrativo(idAdministrativo, nombre)" + " VALUES (?, ?)";

	private static final String readall = "SELECT * FROM administrativo";

	private static final String update = "UPDATE administrativo SET nombre = ? WHERE idAdministrativo = ?";

	@Override
	public boolean insert(AdministrativoDTO administativo) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, administativo.getIdAdministrativo());
			statement.setString(2, administativo.getNombre());

			if (statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

	@Override
	public boolean delete(AdministrativoDTO administrativo_a_eliminar) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<AdministrativoDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();

		ArrayList<AdministrativoDTO> administrativos = new ArrayList<AdministrativoDTO>();

		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				administrativos.add(
						new AdministrativoDTO(resultSet.getInt("idAdministrativo"), resultSet.getString("nombre")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return administrativos;
	}

	@Override
	public boolean update(AdministrativoDTO administrativo) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);

			statement.setString(1, administrativo.getNombre());
			statement.setInt(2, administrativo.getIdAdministrativo()); // deberia

			chequeoUpdate = statement.executeUpdate();
			if (chequeoUpdate > 0) // Si se ejecutó devuelvo true
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) {
	
	AdministrativoDAOSQL daoSQL = new AdministrativoDAOSQL();

/*Probamos El Insert en la tabla, luego verificar de forma manual que este registrado en la tabla*/	
	AdministrativoDTO DTO = new AdministrativoDTO(0,"LizzAdministrativa");
	AdministrativoDTO DTO2 = new AdministrativoDTO(0,"MicaAdministrativa");
	AdministrativoDTO DTO3 = new AdministrativoDTO(0,"SolAdministrativa");
	
	daoSQL.insert(DTO);
	daoSQL.insert(DTO2);
	daoSQL.insert(DTO3);
	
/*Probamos el ReadALL*/	
	ArrayList<AdministrativoDTO> administratives = (ArrayList<AdministrativoDTO>) daoSQL.readAll();
	
	for(AdministrativoDTO ad: administratives)
		System.out.println(ad.getNombre());
	}
}
