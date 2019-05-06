package dto;

public class Pasaje_PasajerosDTO {
	private int idPasajePasajero;
	private int idPasaje;
	private int idPasajero;

	public Pasaje_PasajerosDTO(int idPasajePasajero, int idPasaje, int idPasajero) {
		super();
		this.idPasajePasajero = idPasajePasajero;
		this.idPasaje = idPasaje;
		this.idPasajero = idPasajero;
	}

	public Pasaje_PasajerosDTO() {
		super();
	}

	public int getIdPasaje() {
		return idPasaje;
	}

	public void setIdPasaje(int idPasaje) {
		this.idPasaje = idPasaje;
	}

	public int getIdPasajePasajero() {
		return idPasajePasajero;
	}

	public void setIdPasajePasajero(int idPasajePasajero) {
		this.idPasajePasajero = idPasajePasajero;
	}

	public int getIdPasajero() {
		return idPasajero;
	}

	public void setIdPasajero(int idPasajero) {
		this.idPasajero = idPasajero;
	}



}
