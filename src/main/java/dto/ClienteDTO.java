package dto;

import java.sql.Date;

public class ClienteDTO {

	private int idCliente;
	private String nombre;
	private String apellido;
	private String dni;
	private Date fechaNacimiento;
	private MedioContactoDTO medioContacto;

	public ClienteDTO(int idCliente, String nombre, String apellido, String dni, Date fechaNacimiento, MedioContactoDTO medioContacto){
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.fechaNacimiento = fechaNacimiento;
		this.medioContacto = medioContacto;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
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

	public MedioContactoDTO getMedioContacto() {
		return medioContacto;
	}

	public void setMedioContacto(MedioContactoDTO medioContacto) {
		this.medioContacto = medioContacto;
	}
	
//	public String getFechaOrdenada() {
//		SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
//		return date.format(fechaNacimiento);
//	}
}