package dto;

import java.math.BigDecimal;
import java.sql.Date;

public class ViajeDTO {
	private int idViaje;
	private CiudadDTO origenViaje;
	private CiudadDTO destinoViaje;

	private Date fechaSalida;
	private Date fechaLlegada;

	private BigDecimal precio;

	public ViajeDTO(int id, CiudadDTO origenViaje, CiudadDTO destinoViaje, Date fechaSalida, Date fechaLlegada,
			BigDecimal precio) {
		super();
		this.idViaje = id;
		this.origenViaje = origenViaje;
		this.destinoViaje = destinoViaje;
		this.fechaSalida = fechaSalida;
		this.fechaLlegada = fechaLlegada;
		this.precio = precio;
	}

	public int getId() {
		return idViaje;
	}

	public void setId(int id) {
		this.idViaje = id;
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

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setImporte(BigDecimal importe) {
		this.precio = importe;
	}

}
