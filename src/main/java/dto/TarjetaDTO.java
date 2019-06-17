package dto;


import java.sql.Date;
public class TarjetaDTO {
	
	
	private int idTarjeta;
//	private PagoDTO idPago;
//	private FormaPagoDTO idFormaPago;	
	private int nroTarjeta;
	private Date fechaVencimiento;
	
	
	public TarjetaDTO(int idTarjeta,  int nroTarjeta, Date fechaVencimiento )
	{
		super();
		this.idTarjeta = idTarjeta;
		//this.idPago = idPago;
		this.nroTarjeta = nroTarjeta;
		this.fechaVencimiento =  fechaVencimiento;
	}


	public int getIdTarjeta() {
		return idTarjeta;
	}


	public void setIdTarjeta(int idTarjeta) {
		this.idTarjeta = idTarjeta;
	}


	public int getNroTarjeta() {
		return nroTarjeta;
	}


	public void setNroTarjeta(int nroTarjeta) {
		this.nroTarjeta = nroTarjeta;
	}


	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}


	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	
	
	

}
