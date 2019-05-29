package dto;

import java.sql.Date;
import java.sql.Time;

public class EventoDTO {
	private int idEvento;
	private Date fechaIngreso;
	private Date fechaEvento;
	private Time horaEvento;
	private String descripcion;
	private ClienteDTO cliente;
	private AdministrativoDTO administrativo;
	private EstadoEventoDTO estado;

	public EventoDTO(int idEvento, Date fechaIngreso, Date fechaEvento, Time horaEvento, String descripcion, ClienteDTO cliente, AdministrativoDTO administrativo, EstadoEventoDTO estado) {
		super();
		this.idEvento=idEvento;
		this.fechaIngreso=fechaIngreso;
		this.fechaEvento=fechaEvento;
		this.horaEvento=horaEvento;
		this.descripcion=descripcion;
		this.cliente=cliente;
		this.administrativo=administrativo;
		this.estado=estado;
	}

	public EventoDTO() {
		super();
	}

	public int getIdEvento() {
		return idEvento;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public Date getFechaEvento() {
		return fechaEvento;
	}

	public Time getHoraEvento() {
		return horaEvento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public ClienteDTO getCliente() {
		return cliente;
	}

	public AdministrativoDTO getAdministrativo() {
		return administrativo;
	}

	public EstadoEventoDTO getEstadoEvento() {
		return estado;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public void setFechaEvento(Date fechaEvento) {
		this.fechaEvento = fechaEvento;
	}

	public void setHoraEvento(Time horaEvento) {
		this.horaEvento = horaEvento;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}

	public void setAdministrativo(AdministrativoDTO administrativo) {
		this.administrativo = administrativo;
	}

	public void setEstadoEvento(EstadoEventoDTO estado) {
		this.estado = estado;
	}
	
	

}