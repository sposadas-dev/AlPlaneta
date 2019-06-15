package dto;
import java.sql.Date;
public class RegimenPuntoDTO {
	
	private int idPuntos;
	private int punto;
	private int ARS;
	private int vencimiento;

	public RegimenPuntoDTO(int idPunto, int punto, int ARS, int vencimiento) {
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

	public RegimenPuntoDTO() {
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

	public int getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(int vencimiento) {
		this.vencimiento = vencimiento;
	}

	

}
