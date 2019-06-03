package dto;
import java.sql.Date;
public class PuntoDTO {
	
	private int idPuntos;
	private int punto;
	private int ARS;
	private Date vencimiento;

	public PuntoDTO(int idPunto, int punto, int ARS, Date vencimiento) {
		super();
		this.idPuntos = idPunto;
		this.punto = punto;
		this.ARS = ARS;
		this.vencimiento = vencimiento;
		
	}


	public int getPunto() {
		return punto;
	}

	public void setPunto(int punto) {
		this.punto = punto;
	}

	public PuntoDTO() {
		super();
	}

	public int getIdPunto() {
		return idPuntos;
	}

	public void setIdPunto(int idPunto) {
		this.idPuntos = idPunto;
	}

	public int getARS() {
		return ARS;
	}

	public void setARS(int aRS) {
		ARS = aRS;
	}

	public Date getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}

	

}
