package dto;

public class CiudadDTO {
	private int idCiudad;
	private String nombre;
	private ProvinciaDTO provincia;
	
	public CiudadDTO(int idCiudad, String nombre, ProvinciaDTO provincia) {
		super();
		this.idCiudad = idCiudad;
		this.nombre = nombre;
		this.provincia = provincia;
	}

	public CiudadDTO() {
		super();
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

	public ProvinciaDTO getProvincia() {
		return provincia;
	}

	public void setProvincia(ProvinciaDTO provincia) {
		this.provincia = provincia;
	}
	
}
