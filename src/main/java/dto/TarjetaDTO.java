package dto;

public class TarjetaDTO {
	
	
	private int idTarjeta;
	private String nroTarjeta;
	private String fechaVencimiento;
	
	
	public TarjetaDTO(int idTarjeta,  String nroTarjeta, String fechaVencimiento ){
		super();
		this.idTarjeta = idTarjeta;
		this.nroTarjeta = nroTarjeta;
		this.fechaVencimiento =  fechaVencimiento;
	}

	public TarjetaDTO() {
		super();
	}
	
	public int getIdTarjeta() {
		return idTarjeta;
	}


	public void setIdTarjeta(int idTarjeta) {
		this.idTarjeta = idTarjeta;
	}


	public String getNroTarjeta() {
		return nroTarjeta;
	}


	public void setNroTarjeta(String nroTarjeta) {
		this.nroTarjeta = nroTarjeta;
	}


	public String getVencimiento() {
		return fechaVencimiento;
	}


	public void setVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

}
