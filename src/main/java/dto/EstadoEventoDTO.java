package dto;

public class EstadoEventoDTO {
	private int idEstadoEvento;
	private String nombre;
	private String descripcion;

	public EstadoEventoDTO(int idEstadoEvento, String nombre, String descripcion) {
		super();
		this.idEstadoEvento = idEstadoEvento;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public int getIdEstadoEvento() {
		return idEstadoEvento;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setIdEstadoEvento(int idEstadoEvento) {
		this.idEstadoEvento = idEstadoEvento;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
