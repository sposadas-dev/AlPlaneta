package dto;
import java.sql.Date;
public class PuntoDTO {
	
	private int idPunto;
	private int puntos;
	private Date vencimiento;
	private ClienteDTO cliente;

	public PuntoDTO(int idPunto, int puntos, Date vencimiento, ClienteDTO clienteDTO) {
		super();
		this.idPunto = idPunto;
		this.puntos = puntos;
		this.vencimiento = vencimiento;
		this.cliente = clienteDTO;
	}

	public PuntoDTO() {
		super();
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

	public ClienteDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}
	
	
}
