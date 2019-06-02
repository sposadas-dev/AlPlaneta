package dto;

public class LoginDTO {
	private  int idDatosLogin;
	private String usuario;
	private String contrasena;
	private RolDTO rol;
	private String estado;

	public LoginDTO(int idDatosLogin, String usuario, String contrasena, RolDTO rol, String estado) {
		super();
		this.idDatosLogin = idDatosLogin;
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.rol = rol;
		this.estado = estado;
		
	}

	public LoginDTO() {
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

	public RolDTO getRol() {
		return rol;
	}

	public void setRol(RolDTO rol) {
		this.rol = rol;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}

}
