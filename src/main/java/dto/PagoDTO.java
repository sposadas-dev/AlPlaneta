package dto;

import java.math.BigDecimal;
import java.sql.Date;

public class PagoDTO {
	private int idPago;
	private FormaPagoDTO idFomaPago;
	private Date fechaPago;
	private BigDecimal monto;

	public PagoDTO(int idPago, Date fechaPago, BigDecimal monto, FormaPagoDTO pago) {
		this.idPago = idPago;
		this.fechaPago = fechaPago;
		this.monto = monto;
		this.idFomaPago = pago;
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

	public FormaPagoDTO getIdFomaPago() {
		return idFomaPago;
	}

	public void setIdFomaPago(FormaPagoDTO idFomaPago) {
		this.idFomaPago = idFomaPago;
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
