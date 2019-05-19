package modelo;

import java.util.List;

import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.EstadoPasajeDAO;
import persistencia.dao.interfaz.FormaPagoDAO;
import dto.EstadoPasajeDTO;
import dto.FormaPagoDTO;

public class EstadoPasaje {
	
	private EstadoPasajeDAO estadoPasaje;
	
	public EstadoPasaje(DAOAbstractFactory metodo_persistencia){
		this.estadoPasaje = metodo_persistencia.createEstadoPasajeDAO();
	}
	
	public void agregarFormaPago(EstadoPasajeDTO estadoPasaje) {
		this.estadoPasaje.insert(estadoPasaje);
	}
	
	public List<EstadoPasajeDTO> obtenerEstadosPasajes(){
		return this.estadoPasaje.readAll();
	}
	
	public EstadoPasajeDTO getFormaPagoById(int idEstadoPasaje){
		return this.estadoPasaje.getEstadoPasajeById(idEstadoPasaje);
	}
	
	public EstadoPasajeDTO getFormaPagoByName(String name){
		return this.estadoPasaje.getEstadoPasajeByNombre(name);
	}
}
