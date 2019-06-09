package modelo;
import java.util.List;
import dto.CoordinadorDTO;
import persistencia.dao.interfaz.CoordinadorDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;

public class Coordinador {
	
private CoordinadorDAO coordinador;
	
	public Coordinador(DAOAbstractFactory metodo_persistencia){
		this.coordinador = metodo_persistencia.createCoordinadorDAO();
	}
	
	public void agregarCoordinador(CoordinadorDTO nuevoCoordinador){
		this.coordinador.insert(nuevoCoordinador);
	}
	
	public List<CoordinadorDTO> obtenerCoordinadores(){
		return this.coordinador.readAll();		
	}

	public CoordinadorDTO getByLoginId(int idLogin) {
		return this.coordinador.getByLoginId(idLogin);
	}

	public CoordinadorDTO buscarPorEmail(String mailDeRecuperacion) {
		return this.coordinador.getByMail(mailDeRecuperacion);
	}

	public void actualizar(CoordinadorDTO coordinador) {
		this.coordinador.update(coordinador);
	}
}
