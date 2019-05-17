package dto;

public class ProvinciaDTO {
	private int idProvincia;
	private String nombre;
	private PaisDTO pais;

	public ProvinciaDTO(int idProvincia, String nombre, PaisDTO ciudad) {
		super();
		this.idProvincia = idProvincia;
		this.nombre = nombre;
		this.pais = ciudad;
	}

	public ProvinciaDTO() {
	}

	public int getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(int idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public PaisDTO getPais() {
		return pais;
	}

	public void setPais(PaisDTO pais) {
		this.pais = pais;
	}


}
