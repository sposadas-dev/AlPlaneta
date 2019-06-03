package persistencia.dao.mysql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.AdministrativoDTO;
import dto.ClienteDTO;
import dto.EstadoEventoDTO;
import dto.EventoDTO;
import dto.LoginDTO;
import dto.MedioContactoDTO;
import dto.RolDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.EventoDAO;

public class EventoDAOSQL implements EventoDAO {

	private static final String insert = "INSERT INTO evento (idEvento, fechaIngreso, fechaEvento, horaEvento, descripcion, idCliente, idAdministrativo, idEstadoEvento, motivoReprogramacion,visto) VALUES (?,?,?,?,?,?,?,?,?,?)";
	private static final String readall = "SELECT * FROM evento";
	private static final String update = "UPDATE evento SET fechaEvento=?, horaEvento=?, idEstadoEvento=?, motivoReprogramacion=?, visto=? WHERE idEvento= ?";//VER
	private static final String updateVisto = "UPDATE evento SET visto=? WHERE idEvento= ?";//VER
	private static final String browse = "SELECT * FROM evento WHERE idEvento = ?";

	@Override
	public boolean insert(EventoDTO evento) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, evento.getIdEvento());
			statement.setDate(2, evento.getFechaIngreso());
			statement.setDate(3, evento.getFechaEvento());
			statement.setTime(4, evento.getHoraEvento());
			statement.setString(5, evento.getDescripcion());
			statement.setInt(6, evento.getCliente().getIdCliente());
			statement.setInt(7, evento.getAdministrativo().getIdAdministrativo());
			statement.setInt(8, evento.getEstadoEvento().getIdEstadoEvento());
			statement.setString(9, evento.getMotivoReprogramacion());
			statement.setInt(10, evento.getVisto());
			
			if (statement.executeUpdate() > 0)
				return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public List<EventoDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; 
		ArrayList<EventoDTO> eventos = new ArrayList<EventoDTO>();
		Conexion conexion = Conexion.getConexion();
	
		ClienteDAOSQL cliente = new ClienteDAOSQL();
		AdministrativoDAOSQL administrativo = new AdministrativoDAOSQL();
		EstadoEventoDAOSQL estadoEvento = new EstadoEventoDAOSQL();
	
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()){
				eventos.add(new EventoDTO(resultSet.getInt("idEvento"),
				resultSet.getDate("fechaIngreso"),
				resultSet.getDate("fechaEvento"),
				resultSet.getTime("horaEvento"),
				resultSet.getString("descripcion"),
				cliente.getClienteById(resultSet.getInt("idCliente")),
				administrativo.getById(resultSet.getInt("idAdministrativo")),
				estadoEvento.getEstadoEventoById(resultSet.getInt("idEstadoEvento")),
				resultSet.getString("motivoReprogramacion"),
						resultSet.getInt("visto"))
				);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return eventos;
	}

	@Override
	public boolean update(EventoDTO evento) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);
			
			statement.setDate(1, evento.getFechaEvento());
			statement.setTime(2, evento.getHoraEvento());
			statement.setInt(3, evento.getEstadoEvento().getIdEstadoEvento());
			statement.setString(4, evento.getMotivoReprogramacion());
			statement.setInt(5, evento.getVisto());
			statement.setInt(6, evento.getIdEvento());
		
			chequeoUpdate = statement.executeUpdate();
			if(chequeoUpdate > 0) //Si se ejecutó devuelvo true
				return true;
		} 
		catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean updateVisto(EventoDTO evento) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(updateVisto);
			
			statement.setInt(1, evento.getVisto());
			statement.setInt(2, evento.getIdEvento());
		
			chequeoUpdate = statement.executeUpdate();
			if(chequeoUpdate > 0) //Si se ejecutó devuelvo true
				return true;
		} 
		catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public EventoDTO getEventoById(int id ){
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();

		ClienteDAOSQL cliente = new ClienteDAOSQL();
		AdministrativoDAOSQL administrativo = new AdministrativoDAOSQL();
		EstadoEventoDAOSQL estadoEvento = new EstadoEventoDAOSQL();
		
		EventoDTO ret;
		try{
			statement = conexion.getSQLConexion().prepareStatement(browse);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()){
				ret = new EventoDTO(resultSet.getInt("idEvento"),
						resultSet.getDate("fechaIngreso"),
						resultSet.getDate("fechaEvento"),
						resultSet.getTime("horaEvento"),
						resultSet.getString("descripcion"),
						cliente.getClienteById(resultSet.getInt("idCliente")),
						administrativo.getById(resultSet.getInt("idAdministrativo")),
						estadoEvento.getEstadoEventoById(resultSet.getInt("idEstadoEvento")),
						resultSet.getString("motivoReprogramacion"),
						resultSet.getInt("visto"));
				return ret;
			}
			
		}
		catch (SQLException e){
			 e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		EventoDAOSQL eventoDAOSQL = new EventoDAOSQL();
		
		java.util.Date fecha = new java.util.Date(); java.sql.Date fechaIngreso = new java.sql.Date(fecha.getTime());
		Date fechaEvento = new java.sql.Date(2019,05,06);
		java.sql.Time horaEvento = new java.sql.Time(16, 30, 0);
		String descripcion = "Ofrecer viaje a Cancun";
		
		MedioContactoDTO medioContacto = new MedioContactoDTO(0,"44510021","1545899865","juan.perez@gmail.com");
		RolDTO rol = new RolDTO(5,"cliente");
		LoginDTO login = new LoginDTO (1,"usuariojuan","juan123",rol);
		ClienteDTO cliente = new ClienteDTO(2,"Nico","Avila","32125322",fechaIngreso,medioContacto,login);
		
		RolDTO rol2 = new RolDTO(2,"administrativo");
		LoginDTO login2 = new LoginDTO (1,"sol","sol123",rol2);
		AdministrativoDTO administrativo = new AdministrativoDTO(1,"soladministrativa",login2,"sol@gmail.com");
		
		EstadoEventoDTO estado = new EstadoEventoDTO(1,"pendiente","el evento aún no se realizó");
		
		EventoDTO nuevoEvento = new EventoDTO(0,fechaIngreso,fechaEvento,horaEvento,descripcion,cliente,administrativo,estado,"",0);
				
		eventoDAOSQL.insert(nuevoEvento);
		List<EventoDTO> EVENTOS = eventoDAOSQL.readAll();
		
		for(EventoDTO e: EVENTOS)
			System.out.println(e.getDescripcion());
		System.out.println("-- FIN EVENTOS --");
		System.out.println(fechaIngreso.toString());
	}
		
}