package dto;

public class CiudadDTO {
	private int idCiudad;
	private String nombre;
	
	public CiudadDTO(int idCiudad, String nombre) {
		super();
		this.idCiudad = idCiudad;
		this.nombre = nombre;
	}

	public CiudadDTO() {
	}

	public int getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(int idCiudad) {
		this.idCiudad = idCiudad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	

	
}
