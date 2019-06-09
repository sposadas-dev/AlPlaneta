package dto;

public class Viaje_PromocionDTO {
	private int idViajePromocion;
	private int idViaje;
	private int idPromocion;

	public Viaje_PromocionDTO(int idViajePromocion, int idViaje, int idPromocion) {
		super();
		this.idViajePromocion = idViajePromocion;
		this.idViaje = idViaje;
		this.idPromocion = idPromocion;
	}

	public Viaje_PromocionDTO() {
		super();
	}

	public int getIdViaje() {
		return idViaje;
	}

	public void setIdViaje(int id) {
		this.idViaje = id;
	}

	public int getIdViajePromocion() {
		return idViajePromocion;
	}

	public void setIdViajePromocion(int id) {
		this.idViajePromocion = id;
	}

	public int getIdPromocion() {
		return idPromocion;
	}

	public void setIdPromocion(int id) {
		this.idPromocion = id;
	}
}
