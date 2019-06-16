package dto;

public class AdministradorDTO {
	private int idAdministrador;
	private String nombre;
	private LoginDTO datosLogin;
	private String mail;

	public AdministradorDTO(int idAdministrador, String nombre, LoginDTO datosLogin, String mail) {
		super();
		this.idAdministrador = idAdministrador;
		this.nombre = nombre;
		this.datosLogin = datosLogin;
		this.mail = mail;
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
	
	
	

}
