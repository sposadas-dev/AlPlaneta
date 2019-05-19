package modelo;

import java.util.List;

import dto.PasajeDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.PasajeDAO;

public class Pasaje {

	private PasajeDAO pasaje;
	
	public Pasaje(DAOAbstractFactory metodo_persistencia){
		this.pasaje = metodo_persistencia.createPasajeDAO();
	}
	
	public void agregarPasaje(PasajeDTO nuevoPasaje){
		this.pasaje.insert(nuevoPasaje);
	}
	
	public List<PasajeDTO> obtenerPasajes(){
		return this.pasaje.readAll();		
	}
}