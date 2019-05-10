package dto;

public class DatosLoginDTO {
	private  int idDatosLogin;
	private String usuario;
	private String contrasena;

	public DatosLoginDTO(int idDatosLogin, String usuario, String contrasena) {
		super();
		this.idDatosLogin = idDatosLogin;
		this.usuario = usuario;
		this.contrasena = contrasena;
	}

	public DatosLoginDTO() {
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public int getIdDatosLogin() {
		return idDatosLogin;
	}

	public void setIdDatosLogin(int idDatosLogin) {
		this.idDatosLogin = idDatosLogin;
	}

	
	
}
