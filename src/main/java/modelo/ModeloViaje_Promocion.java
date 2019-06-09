package modelo;

import java.util.List;

import dto.ViajeDTO;
import dto.Viaje_PromocionDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.Viaje_PromocionDAO;

public class ModeloViaje_Promocion {

	private Viaje_PromocionDAO viaje_promocionDAO;
	
	public ModeloViaje_Promocion(DAOAbstractFactory metodo_persistencia){
		this.viaje_promocionDAO = metodo_persistencia.createViaje_PromocionDAO();
	}
	
	public void agregarViajePromocion(Viaje_PromocionDTO viaje_promo){
		this.viaje_promocionDAO.insert(viaje_promo);
	}
	
	public List<Viaje_PromocionDTO> obtenerViajePromocion(){
		return this.viaje_promocionDAO.readAll();		
	}
	
	public List<ViajeDTO> obtenerViajesEnPromo(int id){
		return this.viaje_promocionDAO.obtenerViajesEnPromo(id);		
	}
}