package dto;

import java.sql.Date;

public class PromocionDTO {
	private int idPromocion;
//	private List<ViajeDTO> viajes;
	private int porcentaje;
	private int stock;
	private Date fechaVencimiento;
	private String estado;

//	public PromocionDTO(int idPromocion, List<ViajeDTO> viajes, int porcentaje, int stock, Date fechaVencimiento, String estado) {
	public PromocionDTO(int idPromocion, int porcentaje, int stock, Date fechaVencimiento, String estado) {
		super();
		this.idPromocion=idPromocion;
//		this.viajes= new ArrayList<ViajeDTO>();
		this.porcentaje=porcentaje;
		this.stock=stock;
		this.fechaVencimiento=fechaVencimiento;
		this.estado = estado;
	}

	public PromocionDTO() {
		super();
	}

	public int getIdPromocion() {
		return idPromocion;
	}

//	public List<ViajeDTO> getViajes() {
//		return viajes;
//	}

	public int getPorcentaje() {
		return porcentaje;
	}

	public int getStock() {
		return stock;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}
	
	public String getEstado() {
		return estado;
	}

	public void setIdPromocion(int idPromocion) {
		this.idPromocion = idPromocion;
	}

//	public void setViaje(List<ViajeDTO> viaje) {
//		this.viajes = viaje;
//	}

	public void setPorcentaje(int porcentaje) {
		this.porcentaje = porcentaje;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}



}