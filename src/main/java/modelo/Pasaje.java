package modelo;

import java.util.List;

import dto.PagoDTO;
import dto.PasajeDTO;
import dto.TransporteDTO;
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
	
	public void borrarPasaje(PasajeDTO pasaje_a_eliminar){
		this.pasaje.delete(pasaje_a_eliminar);
	}
	
	public List<PasajeDTO> obtenerPasajes(){
		return this.pasaje.readAll();		
	}
	
//	public PasajeDTO getPasajeById(int idPasaje){
//		return this.pasaje.getPasajeById(idPasaje);
//	}
	
	public PasajeDTO getUltimoRegistroPasaje() {
		return this.pasaje.getUltimoRegistroPasaje();
	}
}