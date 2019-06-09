package modelo;

import java.util.List;

import dto.Pasaje_PasajerosDTO;
import dto.PasajeroDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.Pasaje_PasajerosDAO;

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
	
	public List<PasajeroDTO> obtenerPasajeros(int idPasaje){
		return this.pasaje_pasajerosDAO.traerPasajerosDePasaje(idPasaje);
	}
}