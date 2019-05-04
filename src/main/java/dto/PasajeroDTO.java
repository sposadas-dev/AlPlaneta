package dto;

public class PasajeroDTO {
	private int idPasajero;
	private String nombre;
	private String apellido;
	private String dni;

	public PasajeroDTO(int idPasajero, String nombre, String apellido, String dni) {
		super();
		this.idPasajero = idPasajero;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
	}

	public int getIdPasajero() {
		return idPasajero;
	}

	public void setIdPasajero(int idPasajero) {
		this.idPasajero = idPasajero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

}
