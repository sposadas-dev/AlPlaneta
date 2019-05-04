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

	public void agregarPasajero(PasajeroDTO pasajeroInsert) throws Exception {
		this.pasajero.insert(pasajeroInsert);
	}

	public List<PasajeroDTO> obtenerPasajeros() throws Exception {
		return this.pasajero.readAll();
	}

	public void borrarPasajero(PasajeroDTO pasajeroDelete) throws Exception {
		this.pasajero.delete(pasajeroDelete);
	}

	public void editarPasajero(PasajeroDTO pasajeroUpdate) throws Exception {
		this.pasajero.update(pasajeroUpdate);
	}
}