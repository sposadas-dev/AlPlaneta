package modelo;

import java.util.List;

import dto.PasajeroDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.PasajeroDAO;

public class Pasajero {

	private PasajeroDAO pasajero;

	public Pasajero(DAOAbstractFactory metodo_persistencia) {
		this.pasajero = metodo_persistencia.createPasajeroDAO();
	}

	public void agregarPasajero(PasajeroDTO pasajeroInsert){
		this.pasajero.insert(pasajeroInsert);
	}

	public List<PasajeroDTO> obtenerPasajeros() {
		return this.pasajero.readAll();
	}

	public void borrarPasajero(PasajeroDTO pasajeroDelete) {
		this.pasajero.delete(pasajeroDelete);
	}

	public void editarPasajero(PasajeroDTO pasajeroUpdate) {
		this.pasajero.update(pasajeroUpdate);
	}
	
	public PasajeroDTO getPasajeroByDni(String dniPasajero){
		return this.pasajero.getPasajeroByDni(dniPasajero);
	}

	public PasajeroDTO getUltimoRegistroPasajero() {
		return this.pasajero.getUltimoRegistroPasajero();
	}
}