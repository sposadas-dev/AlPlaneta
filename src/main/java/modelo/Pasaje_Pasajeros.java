package modelo;

import java.util.List;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.Pasaje_PasajerosDAO;
import dto.PasajeDTO;
import dto.Pasaje_PasajerosDTO;

public class Pasaje_Pasajeros {

	private Pasaje_PasajerosDAO pasaje_pasajerosDAO;
	
	public Pasaje_Pasajeros(DAOAbstractFactory metodo_persistencia){
		this.pasaje_pasajerosDAO = metodo_persistencia.createPasaje_PasajerosDAO();
	}
	
	public void agregarPasajePasajero(Pasaje_PasajerosDTO pasaje_pasajero){
		this.pasaje_pasajerosDAO.insert(pasaje_pasajero);
	}
	
	public List<Pasaje_PasajerosDTO> obtenerPasajesPasajeros(){
		return this.pasaje_pasajerosDAO.readAll();		
	}
}