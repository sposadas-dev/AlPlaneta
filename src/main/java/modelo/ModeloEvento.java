package modelo;

import java.util.List;
import dto.EventoDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.EventoDAO;

public class ModeloEvento {

	private EventoDAO evento;
	
	public ModeloEvento(DAOAbstractFactory metodo_persistencia){
		this.evento = metodo_persistencia.createEventoDAO();
	}
	
	public void agregarEvento(EventoDTO evento){
		this.evento.insert(evento);
	}
	
	public void editarEvento(EventoDTO evento) {
		this.evento.update(evento);
	}
	
	public void editarVistoEvento(EventoDTO evento) {
		this.evento.updateVisto(evento);
	}
	
	public List<EventoDTO> obtenerEvento(){
		return this.evento.readAll();		
	}
	
	public List<EventoDTO> obtenerBetween(String dateA, String dateB){
		return this.evento.readBetween(dateA, dateB);
	}
	
}