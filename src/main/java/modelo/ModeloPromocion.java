package modelo;

import java.util.List;

import dto.PromocionDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.PromocionDAO;

public class ModeloPromocion {

	private PromocionDAO promocion;
	
	public ModeloPromocion(DAOAbstractFactory metodo_persistencia){
		this.promocion = metodo_persistencia.createPromocionDAO();
	}
	
	public void agregarPromocion(PromocionDTO promocion){
		this.promocion.insert(promocion);
	}
	
	public void editarPromocion(PromocionDTO promocion) {
		this.promocion.update(promocion);
	}
	
	public List<PromocionDTO> obtenerPromocion(){
		return this.promocion.readAll();		
	}
	
}