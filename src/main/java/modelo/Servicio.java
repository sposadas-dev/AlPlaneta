package modelo;

import java.util.List;

import dto.ServicioDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.ServicioDAO;
import persistencia.dao.interfaz.SueldoDAO;

public class Servicio {

	private ServicioDAO servicio;

	public Servicio(DAOAbstractFactory metodo_persistencia){
		this.servicio = metodo_persistencia.createServicioDAO();
	}
	
	public void agregarServicio(ServicioDTO nuevoServicio){
		this.servicio.insert(nuevoServicio);
	}
	
	public List<ServicioDTO> obtenerServicios(){
		return this.servicio.readAll();
	}
	
	public void editarServicio(ServicioDTO editarServicio){
		this.servicio.update(editarServicio);
	}
	
	public void eliminarServicio(ServicioDTO eliminarServicio){
		this.servicio.delete(eliminarServicio);
	}
}
