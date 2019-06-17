package dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class PasajeDTO {
	private int idPasaje;
	private Date fechaEmision;
	private String numeroComprobante;
	private ViajeDTO viaje;
	private AdministrativoDTO administrativo;
	private ClienteDTO cliente;
	private Date fechaVencimiento;
	private BigDecimal valorViaje;
	private BigDecimal montoAPagar;
	private EstadoPasajeDTO estadoDelPasaje;
	private List<PasajeroDTO> pasajeros;
	private String motivoCancelacion;
	private Date dateCancelacion;
	private BigDecimal montoAReembolsar;
	private SimpleDateFormat formatFecha;

	public PasajeDTO(int idPasaje, Date fechaEmision, String numeroComprobante,ViajeDTO viaje, AdministrativoDTO administrativo,
			ClienteDTO cliente, Date fechaVencimiento, BigDecimal valorViaje,
			BigDecimal montoAPagar,EstadoPasajeDTO estadoDelPasaje, List<PasajeroDTO> pasajeros,
			String motivoCancelacion, Date dateCancelacion,BigDecimal montoAReembolsar) {
		super();
		this.idPasaje = idPasaje;
		this.fechaEmision = fechaEmision;
		this.numeroComprobante = numeroComprobante;
		this.viaje = viaje;
		this.administrativo = administrativo;
		this.cliente = cliente;
		this.fechaVencimiento = fechaVencimiento;
		this.valorViaje = valorViaje;
		this.montoAPagar = montoAPagar;
		this.estadoDelPasaje = estadoDelPasaje;
		this.pasajeros = pasajeros;
		this.motivoCancelacion = motivoCancelacion;
		this.dateCancelacion = dateCancelacion;
		this.montoAReembolsar = montoAReembolsar;
		formatFecha = new SimpleDateFormat("dd-MM-yyyy");
	}

	public BigDecimal getMontoAReembolsar() {
		return montoAReembolsar;
	}

	public void setMontoAReembolsar(BigDecimal montoAReembolsar) {
		this.montoAReembolsar = montoAReembolsar;
	}

	public Date getFechaEmision() {
		return fechaEmision;
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

	public String getNumeroComprobante() {
		return numeroComprobante;
	}

	public void setNumeroComprobante(String numeroComprobante) {
		this.numeroComprobante = numeroComprobante;
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

	public ClienteDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
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

	public BigDecimal getMontoAPagar() {
		return montoAPagar;
	}

	public void setMontoAPagar(BigDecimal montoAPagar) {
		this.montoAPagar = montoAPagar;
	}

	public EstadoPasajeDTO getEstadoDelPasaje() {
		return estadoDelPasaje;
	}

	public void setEstadoDelPasaje(EstadoPasajeDTO estadoDelPasaje) {
		this.estadoDelPasaje = estadoDelPasaje;
	}

	public List<PasajeroDTO> getPasajeros() {
		return pasajeros;
	}

	public void setPasajeros(List<PasajeroDTO> pasajeros) {
		this.pasajeros = pasajeros;
	}

	public String getMotivoCancelacion() {
		return motivoCancelacion;
	}

	public void setMotivoCancelacion(String motivoCancelacion) {
		this.motivoCancelacion = motivoCancelacion;
	}

	public Date getDateCancelacion() {
		return dateCancelacion;
	}

	public void setDateCancelacion(Date dateCancelacion) {
		this.dateCancelacion = dateCancelacion;
	}
	
	public String getFechaVencimientoParseada(){
		return formatFecha.format(this.fechaVencimiento);
	}
	
	public String getFechaSalidaParseada(){
		return formatFecha.format(this.viaje.getFechaSalida() );
	}
	
	public String getFechaLlegadaParseada(){
		return formatFecha.format(this.viaje.getFechaLlegada());
	}
	
	public String getFechaCancelacionParseada(){
		return formatFecha.format(this.dateCancelacion);
	}
}