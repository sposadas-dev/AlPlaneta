package persistencia.dao.mysql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ClienteDTO;
import dto.PuntoDTO;
import dto.RegimenPuntoDTO;
import modelo.Cliente;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PuntoDAO;

public class PuntoDAOSQL implements PuntoDAO{
	
	private static final String insert = "INSERT INTO punto(idPunto, punto, vencimiento, idCliente ) VALUES (?,?, ?, ?)";
	private static final String delete = "DELETE FROM punto WHERE idPunto = ?";
	private static final String readall = "SELECT * FROM punto";
	private static final String readallByIdCliente = "SELECT * FROM punto WHERE idCliente = ?";
	private static final String update = "UPDATE punto SET punto= ?, vencimiento =?, idCLiente=? WHERE idPunto = ?";
	private static final String browse = "SELECT * FROM punto WHERE idPunto = ?";
	private static final String ultimoRegistro = "SELECT * FROM punto ORDER BY idPunto desc limit 1";
	private static final String getAllAscendente = "SELECT * FROM punto WHERE idCliente = ? ORDER BY idPunto asc";
	
	@Override
	public boolean insert(PuntoDTO punto) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, punto.getIdPunto());
			statement.setInt(2, punto.getPuntos());
			statement.setDate(3, punto.getVencimiento());
			statement.setInt(4, punto.getCliente().getIdCliente());

			if (statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean delete(PuntoDTO punto_a_eliminar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(punto_a_eliminar.getIdPunto()));
			chequeoUpdate = statement.executeUpdate();
			if(chequeoUpdate > 0) //Si se ejecutó devuelvo true
				return true;
		} 
		catch (SQLException e) {
			 e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<PuntoDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();
		ArrayList<PuntoDTO> puntos = new ArrayList<PuntoDTO>();
		Cliente modeloCliente = new Cliente(new DAOSQLFactory());
		try {
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				puntos.add(new PuntoDTO(resultSet.getInt("idPunto"),
												  resultSet.getInt("punto"),
												  resultSet.getDate("vencimiento"),
												  modeloCliente.getByClienteById(resultSet.getInt("idCliente")))
												  );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return puntos;
	}
	
	public ArrayList<PuntoDTO> readAllByClienteID(ClienteDTO cliente) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();
		ArrayList<PuntoDTO> puntos = new ArrayList<PuntoDTO>();
		Cliente modeloCliente = new Cliente(new DAOSQLFactory());
		try {
			statement = conexion.getSQLConexion().prepareStatement(readallByIdCliente);
			statement.setInt(1, cliente.getIdCliente());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				puntos.add(new PuntoDTO(resultSet.getInt("idPunto"),
												  resultSet.getInt("punto"),
												  resultSet.getDate("vencimiento"),
												  cliente)
												  );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return puntos;
	}

	
@Override
	public boolean update(PuntoDTO punto_a_editar) {
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(update);
			statement.setInt(1, punto_a_editar.getPuntos());
			statement.setDate(2,punto_a_editar.getVencimiento());
			statement.setInt(3, punto_a_editar.getCliente().getIdCliente());
			statement.setInt(4, punto_a_editar.getIdPunto()); 
			
			
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
	public PuntoDTO getPuntoById(int idPunto ){
		
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		PuntoDTO punto;
		Cliente modeloCliente = new Cliente(new DAOSQLFactory());
		try{
			statement = conexion.getSQLConexion().prepareStatement(browse);
			statement.setInt(1, idPunto);
			resultSet = statement.executeQuery();
			
			if(resultSet.next()){
				punto = new PuntoDTO(resultSet.getInt("idPunto"),
											   resultSet.getInt("punto"),
											   resultSet.getDate("vencimiento"),
											   modeloCliente.getByClienteById(resultSet.getInt("idCliente"))
											   );
				return punto;
			}
			
		}catch (SQLException e){
			 e.printStackTrace();
		}
		return null;
	}
	
	public RegimenPuntoDTO getUltimoRegistroRegimenPunto() {
		PreparedStatement statement;
		ResultSet resultSet;
		Conexion conexion = Conexion.getConexion();
		RegimenPuntoDTO regimenPunto;
		try {
			statement = conexion.getSQLConexion().prepareStatement(ultimoRegistro);
			resultSet = statement.executeQuery();
			
			if (resultSet.next()){
				regimenPunto = new RegimenPuntoDTO(
						resultSet.getInt("idRegimenPunto"),
						  resultSet.getInt("punto"),
					      resultSet.getInt("ARS"),
						  resultSet.getInt("vencimiento")
						  );
				return regimenPunto;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<PuntoDTO> readAllASC(int idCliente) {
		PreparedStatement statement;
		ResultSet resultSet; // Guarda el resultado de la query
		Conexion conexion = Conexion.getConexion();
		ArrayList<PuntoDTO> puntos = new ArrayList<PuntoDTO>();
		Cliente modeloCliente = new Cliente(new DAOSQLFactory());
		try {
			statement = conexion.getSQLConexion().prepareStatement(getAllAscendente);
			statement.setInt(1, idCliente);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				puntos.add(new PuntoDTO(resultSet.getInt("idPunto"),
												  resultSet.getInt("punto"),
												  resultSet.getDate("vencimiento"),
												  modeloCliente.getByClienteById(idCliente))
												  );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return puntos;
	}
	
	
	
	
	public static void main(String[] args) {
		PuntoDAOSQL dao =  new PuntoDAOSQL();
		ArrayList<PuntoDTO> puntos = dao.readAllASC(1);
		for(int i=0; i<puntos.size(); i++){
			System.out.println(puntos.get(i).getIdPunto());
		}
//		dao.insert(punto)
//		RegimenPuntoDTO reg = dao.obtenerUltimoRegistro();
//		System.out.println(reg.getVencimiento());
//		
		
//		List<PuntoDTO> list = dao.obtenerPunto(); 
//		
//		for(PuntoDTO elem: list){
//			System.out.println(elem.getPunto());
//			elem.setPunto(20);
//			dao.editarPunto(elem);
//			
//		}
//		System.out.println("- - - - - - - - - - - - -");
//		System.out.println(dao.getPuntoById(1).getARS());
//		dao.agregarPunto();
//		tiene id, cantpuntos, ARS, y vencimiento
//		 
//	        BigDecimal big = new BigDecimal("925.0000");
//	        String[] parts = big.toString().split("\\.");
//	        Integer parteEntera= Integer.parseInt(parts[0]);
//	        Integer parteDecimal= Integer.parseInt(parts[1]);
	        
//	        System.out.println("Parte entera: " + parteEntera);
//	        System.out.println("Parte decimal: " + parteDecimal);

	}

@Override
	public PuntoDTO getUltimoRegistroPunto() {
		// TODO Auto-generated method stub
		return null;
	} 

}
