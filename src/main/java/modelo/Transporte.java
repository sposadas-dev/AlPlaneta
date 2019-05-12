package modelo;

import java.util.List;
import dto.TransporteDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.TransporteDAO;

public class Transporte {

	private TransporteDAO transporte;
	
	public Transporte(DAOAbstractFactory metodo_persistencia){
		this.transporte = metodo_persistencia.createTransporteDAO();
	}
	
	public void agregarTransporte(TransporteDTO nuevoTransporte){
		this.transporte.insert(nuevoTransporte);
	}
	
	public void borrarTransporte(TransporteDTO transporte_a_eliminar) {
		this.transporte.delete(transporte_a_eliminar);
	}
	
	public List<TransporteDTO> obtenerTransportes(){
		return this.transporte.readAll();	
	}
	
	public void editarTransporte(TransporteDTO transporte_a_editar){
		this.transporte.update(transporte_a_editar);
	}
	
	public TransporteDTO getTransporteById(int idTransporte){
		return this.transporte.getTransporteById(idTransporte);
	}
}