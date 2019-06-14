package modelo;

import java.util.List;

import dto.RegimenPuntoDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.RegimenPuntoDAO;

public class ModeloRegimenPunto {
	
private RegimenPuntoDAO punto;
	
	public ModeloRegimenPunto(DAOAbstractFactory metodo_persistencia){
		this.punto = metodo_persistencia.createRegimenPuntoDAO();
	}
	
	public void agregarPunto(RegimenPuntoDTO nuevoPunto){
		this.punto.insert(nuevoPunto);
	}
	
	public void borrarPunto(RegimenPuntoDTO punto_a_punto) {
		this.punto.delete(punto_a_punto);
	}
	
	public List<RegimenPuntoDTO> obtenerPunto(){
		return this.punto.readAll();	
	}
	
	public void editarPunto(RegimenPuntoDTO punto_a_editar){
		this.punto.update(punto_a_editar);
	}
	
	public RegimenPuntoDTO getPuntoById(int idPunto){
		return this.punto.getPuntoById(idPunto);
	}
	
	public RegimenPuntoDTO obtenerUltimoRegistro(){
		return this.punto.getUltimoRegistroRegimenPunto();
	}

}
