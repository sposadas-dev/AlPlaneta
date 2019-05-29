package modelo;

import java.util.List;
import dto.EventoDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.EventoDAO;

public class ModeloEvento {

	private EventoDAO viaje;
	
	public ModeloEvento(DAOAbstractFactory metodo_persistencia){
		this.viaje = metodo_persistencia.createEventoDAO();
	}
	
	public void agregarEvento(EventoDTO evento){
		this.viaje.insert(evento);
	}
	
	public List<EventoDTO> obtenerEvento(){
		return this.viaje.readAll();		
	}
	
}