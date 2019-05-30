package dto;

public class AdministrativoDTO {
	private int idAdministrativo;
	private String nombre;
	private LoginDTO datosLogin;
	private String mail;

	public AdministrativoDTO(int idAdministrativo, String nombre, LoginDTO datosLogin, String mail) {
		super();
		this.idAdministrativo = idAdministrativo;
		this.nombre = nombre;
		this.datosLogin = datosLogin;
		this.mail = mail;
	}
	
	public AdministrativoDTO() {
		super();
	}
	
	public int getIdAdministrativo() {
		return idAdministrativo;
	}
	
	public void setIdAdministrativo(int idAdministrativo) {
		this.idAdministrativo = idAdministrativo;
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	
}