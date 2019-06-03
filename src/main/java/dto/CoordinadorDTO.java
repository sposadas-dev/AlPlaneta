package dto;

public class CoordinadorDTO {
	
	private int idCoordinador;
	private String nombre;
	private LoginDTO datosLogin;
	
		
	public CoordinadorDTO( int idCoordinador, String nombre, LoginDTO datosLogin)
	{
		super();
		this.idCoordinador =  idCoordinador;
		this.nombre = nombre;
		this.datosLogin = datosLogin;
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
	

}
