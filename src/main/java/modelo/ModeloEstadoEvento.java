package modelo;

import java.util.List;
import dto.EstadoEventoDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.EstadoEventoDAO;

public class ModeloEstadoEvento {
	
	private EstadoEventoDAO estadoEvento;
	
	public ModeloEstadoEvento(DAOAbstractFactory metodo_persistencia){
		this.estadoEvento = metodo_persistencia.createEstadoEventoDAO();
	}
	
	public void agregarEvento(EstadoEventoDTO estadoEvento) {
		this.estadoEvento.insert(estadoEvento);
	}
	
	public List<EstadoEventoDTO> obtenerEstadosEvento(){
		return this.estadoEvento.readAll();
	}
	
	public EstadoEventoDTO getEstadoEventoById(int id){
		return this.estadoEvento.getEstadoEventoById(id);
	}
	
	public EstadoEventoDTO getEstadoEventoByNombre(String nombre){
		return this.estadoEvento.getEstadoEventoByNombre(nombre);		
	}
	
}