package dto;

import java.math.BigDecimal;
import java.sql.Date;

public class ViajeDTO {
	private int idViaje;
	private CiudadDTO origenViaje;
	private CiudadDTO destinoViaje;
	private Date fechaSalida;
	private Date fechaLlegada;
	private String horaSalida;
	private int horasEstimadas;
	
	private TransporteDTO transporte;
	private int capacidad;
	private BigDecimal precio;

	public ViajeDTO(int idViaje, CiudadDTO origenViaje, CiudadDTO destinoViaje, Date fechaSalida, Date fechaLlegada,
			String horaSalida, int horasEstimadas,TransporteDTO transporte,int capacidad, BigDecimal precio ) {
		super();
		this.idViaje = idViaje;
		this.origenViaje = origenViaje;
		this.destinoViaje = destinoViaje;
		this.fechaSalida = fechaSalida;
		this.fechaLlegada = fechaLlegada;
		this.horaSalida = horaSalida;
		this.horasEstimadas = horasEstimadas;
		this.transporte = transporte;
		this.capacidad = capacidad;
		this.precio = precio;
	}

	public ViajeDTO() {
		super();
	}

	public int getIdViaje() {
		return idViaje;
	}

	public void setIdViaje(int idViaje) {
		this.idViaje = idViaje;
	}

	public CiudadDTO getOrigenViaje() {
		return origenViaje;
	}

	public void setOrigenViaje(CiudadDTO origenViaje) {
		this.origenViaje = origenViaje;
	}

	public CiudadDTO getDestinoViaje() {
		return destinoViaje;
	}

	public void setDestinoViaje(CiudadDTO destinoViaje) {
		this.destinoViaje = destinoViaje;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public Date getFechaLlegada() {
		return fechaLlegada;
	}

	public void setFechaLlegada(Date fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}

	public String getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(String horaSalida) {
		this.horaSalida = horaSalida;
	}

	public int getHorasEstimadas() {
		return horasEstimadas;
	}

	public void setHorasEstimadas(int horasEstimadas) {
		this.horasEstimadas = horasEstimadas;
	}

	public TransporteDTO getTransporte() {
		return transporte;
	}

	public void setTransporte(TransporteDTO transporte) {
		this.transporte = transporte;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
}