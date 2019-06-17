package modelo;

import java.util.List;

import dto.TarjetaDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.TarjetaDAO;


public class Tarjeta {
	
	private TarjetaDAO tarjeta;
	
	public Tarjeta(DAOAbstractFactory metodo_persistencia){
		this.tarjeta = metodo_persistencia.createTarjetaDAO();
	}
	
	public void agregarTarjeta(TarjetaDTO tarjetaInsert){
		this.tarjeta.insert(tarjetaInsert);
	}
	
//	public List<TarjetaDTO> obtenerTarjetas(){
//		return this.tarjeta.readAll();		
//	}
//	
//	public void borrarTarjeta(TarjetaDTO TarjetaDelete) {
//		this.tarjeta.delete(TarjetaDelete);
//	}
//	
//	public void editarTarjeta(TarjetaDTO TarjetaUpdate) {
//		this.tarejta.update(pagoUpdate);
//	}
//	
//	public PagoDTO getTarjetaById(int idPago) {
//		return this.tarjeta.getPagoById(idPago);
//	}
//	
//	public PagoDTO getUltimoRegistroPago() {
//		return this.tarjeta.getUltimoRegistroPago();
//	}
	
	

}
