package modelo;

import java.util.List;

import dto.CondicionDeCancelacionDTO;
import persistencia.dao.interfaz.CondicionDeCancelacionDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;

public class ModeloCondicionDeCancelacion {
	
private CondicionDeCancelacionDAO condicion;
	
	public ModeloCondicionDeCancelacion(DAOAbstractFactory metodo_persistencia){
		this.condicion = metodo_persistencia.createCondicionDeCancelacionDAO();
	}
	
	public void agregarCondicionDeCancelacion(CondicionDeCancelacionDTO nuevoCondicion){
		this.condicion.insert(nuevoCondicion);
	}
	
	public boolean eliminarCondicionDeCancelacion(int idCondicion) {
		return this.condicion.delete(idCondicion);
	}
	
	public boolean updateCondicionDeCancelacion(CondicionDeCancelacionDTO updateCondicion) {
		return this.condicion.update(updateCondicion);
	}
	
	public List<CondicionDeCancelacionDTO> obtenerCondiciones(){
		return this.condicion.readAll();		
	}
	
	public List<CondicionDeCancelacionDTO> getByEstado(String estado){
		return this.condicion.getByEstado(estado);
	}
	
}
