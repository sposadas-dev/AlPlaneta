package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.AdministradorDTO;
import dto.AdministrativoDTO;
import dto.HorarioReservaDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.AdministradorDAO;
import persistencia.dao.interfaz.HorarioReservaDAO;

public class HorarioReservaDAOSQL implements HorarioReservaDAO {
	
	private static final String insert = "INSERT INTO horarioreserva(idHorario, horarioInicio,horarioFin)"
			+ " VALUES (?, ?,?)";
	
	private static final String readall = "SELECT * FROM horarioreserva";
	
	
	public boolean insert(AdministradorDAO adm) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public List<HorarioReservaDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();

		ArrayList<HorarioReservaDTO> horarioReservas = new ArrayList<HorarioReservaDTO>();

		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				horarioReservas.add(
						new HorarioReservaDTO(
								resultSet.getInt("idHorario"),
								resultSet.getString("horarioInicio"),
								resultSet.getString("horarioFin")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return horarioReservas;

	}

	@Override
	public boolean insert(String horario) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(HorarioReservaDTO horario) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HorarioReservaDTO getHorarioById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		
		HorarioReservaDAOSQL daoSQL = new HorarioReservaDAOSQL();
		
		ArrayList<HorarioReservaDTO> arry = (ArrayList<HorarioReservaDTO>) daoSQL.readAll();
		
		for(HorarioReservaDTO ad: arry)
			System.out.println(ad.getHoraInicio() +" "+ ad.getHoraFin());
		}

}
