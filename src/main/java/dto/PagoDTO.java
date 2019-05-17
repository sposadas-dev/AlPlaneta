package dto;

import java.math.BigDecimal;
import java.sql.Date;

public class PagoDTO {
	private int idPago;
	private FormaPagoDTO idFormaPago;
	private Date fechaPago;
	private BigDecimal monto;

	public PagoDTO(int idPago, Date fechaPago, BigDecimal monto, FormaPagoDTO pago) {
		this.idPago = idPago;
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

}
