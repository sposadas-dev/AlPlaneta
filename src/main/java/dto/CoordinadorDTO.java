package dto;

public class CoordinadorDTO {
	
	private int idCoordinador;
	private String nombre;
	private String apellido;
	private String dni;
	private LoginDTO datosLogin;
	private String mail;
	private LocalDTO local;
		
	public CoordinadorDTO( int idCoordinador, String nombre, String apellido, String dni, LoginDTO datosLogin, String mail, LocalDTO local){
		super();
		this.idCoordinador =  idCoordinador;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.datosLogin = datosLogin;
		this.mail = mail;
		this.local = local;
	}

	public CoordinadorDTO() {
	}

	public int getIdCoordinador() {
		return idCoordinador;
	}


	public void setIdCoordinador(int idCoordinador) {
		this.idCoordinador = idCoordinador;
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
