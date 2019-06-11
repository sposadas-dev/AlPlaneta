package modelo;

import java.util.List;
import dto.Pagos_PasajeDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.Pagos_PasajeDAO;

public class Pagos_Pasaje {

private Pagos_PasajeDAO pagos_pasaje;
	
	public Pagos_Pasaje(DAOAbstractFactory metodo_persistencia){
		this.pagos_pasaje = metodo_persistencia.createPagos_PasajeDAO();
	}
	
	public void agregarPagoPasaje(Pagos_PasajeDTO nuevoPagoPasaje){
		this.pagos_pasaje.insert(nuevoPagoPasaje);
	}
	
	public List<Pagos_PasajeDTO> obtenerPagosPasaje(){
		return this.pagos_pasaje.readAll();		
	}
	
	public Pagos_PasajeDTO getPasajeById(int id){
		return this.pagos_pasaje.getPasajeById(id);
	}
}
