package dto;

public class EstadoPasajeDTO {
	private int idEstadoPasaje;

	private String nombre;
	private String descripcion;

	public EstadoPasajeDTO(int idEstadoPasaje, String nombre, String descripcion) {
		super();
		this.idEstadoPasaje = idEstadoPasaje;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public int getIdEstadoPasaje() {
		return idEstadoPasaje;
	}

	public void setIdEstadoPasaje(int idEstadoPasaje) {
		this.idEstadoPasaje = idEstadoPasaje;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
