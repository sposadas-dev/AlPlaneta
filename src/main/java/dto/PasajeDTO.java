package dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class PasajeDTO {
	private int idPasaje;
	private ViajeDTO viaje;
	private AdministrativoDTO administrativo;
	private int cantidadPasajeros;
	private ClienteDTO cliente;
	private TransporteDTO transporte;
	private Date fechaVencimiento;
	private BigDecimal valorViaje;
	private EstadosPasajeDTO estadoDelPasaje;
	private PagoDTO pago;
	private List<PasajeroDTO> pasajeros;

	public PasajeDTO(int idPasaje, ViajeDTO viaje, AdministrativoDTO administrativo, int cantidadPasajeros,
			ClienteDTO cliente, TransporteDTO transporte, Date fechaVencimiento, BigDecimal valorViaje,
			EstadosPasajeDTO estadoDelPasaje, PagoDTO pago, List<PasajeroDTO> pasajeros) {
		super();
		this.idPasaje = idPasaje;
		this.viaje = viaje;
		this.administrativo = administrativo;
		this.cantidadPasajeros = cantidadPasajeros;
		this.cliente = cliente;
		this.transporte = transporte;
		this.fechaVencimiento = fechaVencimiento;
		this.valorViaje = valorViaje;
		this.estadoDelPasaje = estadoDelPasaje;
		this.pago = pago;
		this.pasajeros = pasajeros;
	}

	public PasajeDTO() {
		super();
	}

	public int getIdPasaje() {
		return idPasaje;
	}

	public void setIdPasaje(int idPasaje) {
		this.idPasaje = idPasaje;
	}

	public ViajeDTO getViaje() {
		return viaje;
	}

	public void setViaje(ViajeDTO viaje) {
		this.viaje = viaje;
	}

	public AdministrativoDTO getAdministrativo() {
		return administrativo;
	}

	public void setAdministrativo(AdministrativoDTO administrativo) {
		this.administrativo = administrativo;
	}

	public int getCantidadPasajeros() {
		return cantidadPasajeros;
	}

	public void setCantidadPasajeros(int cantidadPasajeros) {
		this.cantidadPasajeros = cantidadPasajeros;
	}

	public ClienteDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}

	public TransporteDTO getTransporte() {
		return transporte;
	}

	public void setTransporte(TransporteDTO transporte) {
		this.transporte = transporte;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public BigDecimal getValorViaje() {
		return valorViaje;
	}

	public void setValorViaje(BigDecimal valorViaje) {
		this.valorViaje = valorViaje;
	}

	public EstadosPasajeDTO getEstadoDelPasaje() {
		return estadoDelPasaje;
	}

	public void setEstadoDelPasaje(EstadosPasajeDTO estadoDelPasaje) {
		this.estadoDelPasaje = estadoDelPasaje;
	}

	public PagoDTO getPago() {
		return pago;
	}

	public void setPago(PagoDTO pago) {
		this.pago = pago;
	}

	public List<PasajeroDTO> getPasajeros() {
		return pasajeros;
	}

	public void setPasajeros(List<PasajeroDTO> pasajeros) {
		this.pasajeros = pasajeros;
	}

}