package modelo;

import java.util.ArrayList;
import java.util.List;

import dto.ClienteDTO;
import dto.PuntoDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.PuntoDAO;

public class ModeloPunto {
	
private PuntoDAO punto;
	
	public ModeloPunto(DAOAbstractFactory metodo_persistencia){
		this.punto = metodo_persistencia.createPuntoDAO();
	}
	
	public void agregarPunto(PuntoDTO nuevoPunto){
		this.punto.insert(nuevoPunto);
	}
	
	public void borrarPunto(PuntoDTO punto_a_punto) {
		this.punto.delete(punto_a_punto);
	}
	
	public List<PuntoDTO> obtenerPunto(){
		return this.punto.readAll();	
	}
	
	public void editarPunto(PuntoDTO punto_a_editar){
		this.punto.update(punto_a_editar);
	}
	
	public PuntoDTO getPuntoById(int idPunto){
		return this.punto.getPuntoById(idPunto);
	}
	
	public ArrayList<PuntoDTO> getPuntosByClienteID(ClienteDTO cliente){
		return this.punto.readAllByClienteID(cliente);
	}

}
