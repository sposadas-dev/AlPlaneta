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
	
	public void agregarPago(PagoDTO pagoInsert){
		this.pago.insert(pagoInsert);
	}
	
	public List<PagoDTO> obtenerPagos(){
		return this.pago.readAll();		
	}
	
	public void borrarPago(PagoDTO pagoDelete) {
		this.pago.delete(pagoDelete);
	}
	
	public void editarPago(PagoDTO pagoUpdate) {
		this.pago.update(pagoUpdate);
	}
	
	public PagoDTO getPagoById(int idPago) {
		return this.pago.getPagoById(idPago);
	}
	
	public PagoDTO getUltimoRegistroPago() {
		return this.pago.getUltimoRegistroPago();
	}
}