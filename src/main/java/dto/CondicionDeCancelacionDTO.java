package dto;

public class CondicionDeCancelacionDTO {
	private int idCondicion;
	private int inicio;
	private int fin;
	private int porcentaje;
	private String estadoDelPasaje;
	
	public CondicionDeCancelacionDTO(int idCondicion, int inicio, int fin, int porcentaje,String estado) {
		super();
		this.idCondicion = idCondicion;
		this.inicio = inicio;
		this.fin = fin;
		this.porcentaje = porcentaje;
		this.estadoDelPasaje = estado;
	}

	public CondicionDeCancelacionDTO() {
	}

	public int getIdCondicion() {
		return idCondicion;
	}

	public void setIdCondicion(int idCondicion) {
		this.idCondicion = idCondicion;
	}

	public int getInicio() {
		return inicio;
	}

	public void setInicio(int inicio) {
		this.inicio = inicio;
	}

	public int getFin() {
		return fin;
	}

	public void setFin(int fin) {
		this.fin = fin;
	}

	public int getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(int porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getEstadoDelPasaje() {
		return estadoDelPasaje;
	}

	public void setEstadoDelPasaje(String estadoDelPasaje) {
		this.estadoDelPasaje = estadoDelPasaje;
	}

	
}

