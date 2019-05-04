package dto;

public class AdministrativoDTO {
	private int idAdministrativo;
	private String nombre;

	public AdministrativoDTO(int idAdministrativo, String nombre) {
		super();
		this.idAdministrativo = idAdministrativo;
		this.nombre = nombre;
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
	

}
