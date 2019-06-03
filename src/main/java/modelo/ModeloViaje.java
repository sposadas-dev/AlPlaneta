package modelo;

import java.util.List;

import dto.TransporteDTO;
import dto.ViajeDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.ViajeDAO;

public class ModeloViaje {

	private ViajeDAO viaje;
	
	public ModeloViaje(DAOAbstractFactory metodo_persistencia){
		this.viaje = metodo_persistencia.createViajeDAO();
	}
	
	public void agregarViaje(ViajeDTO nuevoViaje){
		this.viaje.insert(nuevoViaje);
	}
	
	public void editarViaje(ViajeDTO viaje_editar){
		this.viaje.update(viaje_editar);
	}
	
	public void borrarViaje(ViajeDTO viaje_a_eliminar){
		this.viaje.delete(viaje_a_eliminar);
	}
	
	public List<ViajeDTO> obtenerViajes(){
		return this.viaje.readAll();		
	}
}