package dto;

public class AdministrativoDTO {
	private int idAdministrativo;
	private String nombre;
	private DatosLoginDTO datosLogin;

	public AdministrativoDTO(int idAdministrativo, String nombre, DatosLoginDTO datosLogin) {
		super();
		this.idAdministrativo = idAdministrativo;
		this.nombre = nombre;
		this.datosLogin = datosLogin;
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

	public DatosLoginDTO getDatosLogin() {
		return datosLogin;
	}

	public void setDatosLogin(DatosLoginDTO datosLogin) {
		this.datosLogin = datosLogin;
	}
	
	
	

}
