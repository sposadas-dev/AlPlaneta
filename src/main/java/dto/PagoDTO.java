package dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class PagoDTO {
	private int idPago;
	private AdministrativoDTO administrativo;
	private FormaPagoDTO idFormaPago;
	private Date fechaPago;
	private BigDecimal monto;

	public PagoDTO(int idPago, AdministrativoDTO administrativo, Date fechaPago, BigDecimal monto, FormaPagoDTO pago) {
		super();
		this.idPago = idPago;
		this.administrativo = administrativo;
		this.fechaPago = fechaPago;
		this.monto = monto;
		this.idFormaPago = pago;
	}

	public PagoDTO() {
		super();
	}

	public int getIdPago() {
		return idPago;
	}

	public void setIdPago(int idPago) {
		this.idPago = idPago;
	}

	public AdministrativoDTO getAdministrativo() {
		return administrativo;
	}

	public void setAdministrativo(AdministrativoDTO administrativo) {
		this.administrativo = administrativo;
	}

	public FormaPagoDTO getIdFormaPago() {
		return idFormaPago;
	}

	public void setIdFormaPago(FormaPagoDTO idFormaPago) {
		this.idFormaPago = idFormaPago;
	}


	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getFechaParseada(){
		SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
		return formatFecha.format(this.fechaPago);
	}
}
