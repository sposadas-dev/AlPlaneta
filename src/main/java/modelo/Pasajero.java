package modelo;

import java.util.List;

import dto.PasajeroDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.PasajeroDAO;

public class Pasajero {

	private PasajeroDAO pasajero;
	
	public Pasajero(DAOAbstractFactory metodo_persistencia){
		this.pasajero = metodo_persistencia.createPasajeroDAO();
	}
	
	public void agregarPasajero(PasajeroDTO nuevoPasajero){
		this.pasajero.insert(nuevoPasajero);
	}
	
	public List<PasajeroDTO> obtenerPasajeros(){
		return this.pasajero.readAll();		
	}
}