package dto;

public class PaisDTO {
	private int idPais;
	private String nombre;

	public PaisDTO(int idPais, String nombre) {
		this.idPais = idPais;
		this.nombre = nombre;
	}

	public PaisDTO() {
		super();
	}

	public int getIdPais() {
		return idPais;
	}

	public void setIdPais(int idPais) {
		this.idPais = idPais;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
