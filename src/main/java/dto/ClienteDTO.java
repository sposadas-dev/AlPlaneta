package dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ClienteDTO {

	private int idCliente;
	private String nombre;
	private String apellido;
	private String dni;
	private Date fechaNacimiento;
	private MedioContactoDTO medioContacto;
	private LoginDTO login;
	private String mail;
	private int totalPuntos;
	private ArrayList<PuntoDTO> puntos;
	
	

	public ClienteDTO(int idCliente, String nombre, String apellido, 
			String dni, Date fechaNacimiento, MedioContactoDTO medioContacto, LoginDTO login){
		super();
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.fechaNacimiento = fechaNacimiento;
		this.medioContacto = medioContacto;
		this.login = login;
		this.mail = medioContacto.getEmail();
		this.totalPuntos = 0;
		this.puntos = new ArrayList<PuntoDTO>();
		
	}

	public ClienteDTO() {
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

	public LoginDTO getLogin() {
		return login;
	}

	public void setLogin(LoginDTO login) {
		this.login = login;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getTotalPuntos() {
		return totalPuntos;
	}

	public void setTotalPuntos(int totalPuntos) {
		this.totalPuntos = totalPuntos;
	}

	public ArrayList<PuntoDTO> getPuntos() {
		return puntos;
	}

	public void setPuntos(ArrayList<PuntoDTO> puntos) {
		this.puntos = puntos;
	}
}