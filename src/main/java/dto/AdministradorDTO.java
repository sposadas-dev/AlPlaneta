package dto;

public class AdministradorDTO {
	private int idAdministrador;
	private String nombre;
	private String apellido;
	private String dni;
	private LoginDTO datosLogin;
	private String mail;
	private LocalDTO local;

	public AdministradorDTO(int idAdministrador, String nombre, String apellido, String dni, LoginDTO datosLogin, String mail, LocalDTO local) {
		super();
		this.idAdministrador = idAdministrador;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.datosLogin = datosLogin;
		this.mail = mail;
		this.local = local;
	}
	
	public AdministradorDTO() {
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getIdAdministrador() {
		return idAdministrador;
	}
	
	public void setIdAdministrador(int idAdministrador) {
		this.idAdministrador = idAdministrador;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LoginDTO getDatosLogin() {
		return datosLogin;
	}

	public void setDatosLogin(LoginDTO datosLogin) {
		this.datosLogin = datosLogin;
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

	public LocalDTO getLocal() {
		return local;
	}

	public void setLocal(LocalDTO local) {
		this.local = local;
	}
	
}
