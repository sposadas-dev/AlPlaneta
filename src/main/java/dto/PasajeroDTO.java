package dto;

import java.sql.Date;

public class PasajeroDTO {
	private int idPasajero;
	private String nombre;
	private String apellido;
	private String dni;
	private Date fechaNacimiento;
	private String telefono; 
	private String email;

	public PasajeroDTO(int idPasajero, String nombre, String apellido, String dni, Date fechaNacimiento, String telefono, String email) {
		super();
		this.idPasajero = idPasajero;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.fechaNacimiento = fechaNacimiento;
		this.telefono = telefono;
		this.email = email;
	}

	public PasajeroDTO() {
		super();
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

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}