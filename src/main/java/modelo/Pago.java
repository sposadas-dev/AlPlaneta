package modelo;

import java.util.List;

import dto.PagoDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.PagoDAO;

public class Pago {

	private PagoDAO pago;
	
	public Pago(DAOAbstractFactory metodo_persistencia){
		this.pago = metodo_persistencia.createPagoDAO();
	}
	
	public void agregarPago(PagoDTO nuevoPago){
		this.pago.insert(nuevoPago);
	}
	
	public List<PagoDTO> obtenerPagos(){
		return this.pago.readAll();		
	}
}