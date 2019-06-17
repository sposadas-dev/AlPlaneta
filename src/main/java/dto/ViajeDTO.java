package dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class ViajeDTO {
	private int idViaje;
	private CiudadDTO ciudadOrigen;
	private CiudadDTO ciudadDestino;
	private ProvinciaDTO provinciaOrigen;
	private ProvinciaDTO provinciaDestino;
	private PaisDTO paisOrigen;
	public ProvinciaDTO getProvinciaOrigen() {
		return provinciaOrigen;
	}

	public void setProvinciaOrigen(ProvinciaDTO provinciaOrigen) {
		this.provinciaOrigen = provinciaOrigen;
	}

	public ProvinciaDTO getProvinciaDestino() {
		return provinciaDestino;
	}

	public void setProvinciaDestino(ProvinciaDTO provinciaDestino) {
		this.provinciaDestino = provinciaDestino;
	}

	public PaisDTO getPaisOrigen() {
		return paisOrigen;
	}

	public void setPaisOrigen(PaisDTO paisOrigen) {
		this.paisOrigen = paisOrigen;
	}

	public PaisDTO getPaisDestino() {
		return paisDestino;
	}

	public void setPaisDestino(PaisDTO paisDestino) {
		this.paisDestino = paisDestino;
	}

	private PaisDTO paisDestino;
	private Date fechaSalida;
	private Date fechaLlegada;
	private String horaSalida;
	private int horasEstimadas;
	
	private TransporteDTO transporte;
	private int capacidad;
	private BigDecimal precio;

	public ViajeDTO(int idViaje, CiudadDTO ciudadOrigen, CiudadDTO ciudadDestino, ProvinciaDTO provinciaOrigen, 
			ProvinciaDTO provinciaDestino, PaisDTO paisOrigen, PaisDTO paisDestino, Date fechaSalida, Date fechaLlegada,
			String horaSalida, int horasEstimadas,TransporteDTO transporte,int capacidad, BigDecimal precio ) {
		super();
		this.idViaje = idViaje;
		this.ciudadOrigen = ciudadOrigen;
		this.ciudadDestino = ciudadDestino;
		this.provinciaOrigen = provinciaOrigen;
		this.provinciaDestino = provinciaDestino;
		this.paisOrigen = paisOrigen;
		this.paisDestino = paisDestino;
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

	public CiudadDTO getCiudadOrigen() {
		return ciudadOrigen;
	}

	public void setCiudadOrigen(CiudadDTO ciudadOrigen) {
		this.ciudadOrigen = ciudadOrigen;
	}

	public CiudadDTO getCiudadDestino() {
		return ciudadDestino;
	}

	public void setCiudadDestino(CiudadDTO ciudadDestino) {
		this.ciudadDestino = ciudadDestino;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public String getFechaSalidaParseada(){
		SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
		return formatFecha.format(this.fechaSalida);
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