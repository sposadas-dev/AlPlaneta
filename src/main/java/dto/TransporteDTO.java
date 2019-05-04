package dto;

import java.math.BigDecimal;

public class TransporteDTO {
	private int idTransporte;
	private int capacidad;
	private String nombreTransporte;
	private BigDecimal precioBase;

	public TransporteDTO(int idTransporte, int capacidad, String nombreTransporte, BigDecimal precioBase) {
		super();
		this.idTransporte = idTransporte;
		this.capacidad = capacidad;
		this.nombreTransporte = nombreTransporte;
		this.precioBase = precioBase;
	}

	public TransporteDTO() {
	}

	public int getIdTransporte() {
		return idTransporte;
	}

	public void setIdTransporte(int idTransporte) {
		this.idTransporte = idTransporte;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public String getNombreTransporte() {
		return nombreTransporte;
	}

	public void setNombreTransporte(String nombreTransporte) {
		this.nombreTransporte = nombreTransporte;
	}

	public BigDecimal getPrecioBase() {
		return precioBase;
	}

	public void setPrecioBase(BigDecimal precioBase) {
		this.precioBase = precioBase;
	}

}
