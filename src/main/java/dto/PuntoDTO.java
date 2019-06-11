package dto;
import java.sql.Date;
public class PuntoDTO {
	
	private int idPunto;
	private int puntos;
	private Date vencimiento;

	public PuntoDTO(int idPunto, int puntos, Date vencimiento) {
		super();
		this.idPunto = idPunto;
		this.puntos = puntos;
		this.vencimiento = vencimiento;
	}

	public int getIdPunto() {
		return idPunto;
	}

	public void setIdPunto(int idPunto) {
		this.idPunto = idPunto;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public Date getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}
}
