package dto;

public class TransporteDTO {
	
	private int idTransporte;
	private String nombre;

	public TransporteDTO(int idTransporte, String nombre) {
		super();
		this.idTransporte = idTransporte;
		this.nombre = nombre;
	}

	public TransporteDTO() {
		super();
	}

	public int getIdTransporte() {
		return idTransporte;
	}

	public void setIdTransporte(int idTransporte) {
		this.idTransporte = idTransporte;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}