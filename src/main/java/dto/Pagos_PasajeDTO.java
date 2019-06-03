package dto;

public class Pagos_PasajeDTO {
	private int idPagoPasaje;
	private PagoDTO pago;
	private PasajeDTO pasaje;

	public Pagos_PasajeDTO(int idPagoPasaje, PagoDTO pago, PasajeDTO pasaje) {
		super();
		this.idPagoPasaje = idPagoPasaje;
		this.pago = pago;
		this.pasaje = pasaje;
	}

	public Pagos_PasajeDTO() {
		super();
	}

	public int getIdPagoPasaje() {
		return idPagoPasaje;
	}

	public void setIdPagoPasaje(int idPagoPasaje) {
		this.idPagoPasaje = idPagoPasaje;
	}

	public PagoDTO getPago() {
		return pago;
	}

	public void setPago(PagoDTO pago) {
		this.pago = pago;
	}

	public PasajeDTO getPasaje() {
		return pasaje;
	}

	public void setPasaje(PasajeDTO pasaje) {
		this.pasaje = pasaje;
	}
}