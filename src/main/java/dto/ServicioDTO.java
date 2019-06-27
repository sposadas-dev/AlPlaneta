package dto;

import java.math.BigDecimal;
import java.sql.Date;

public class ServicioDTO {

	private int idServicio;
	private String nombreServicio;
	private BigDecimal monto;
	private Date mes;
	private LocalDTO local;
	
	public ServicioDTO(int idServicio, String nombreServicio, BigDecimal monto, Date mes,LocalDTO local){
		this.idServicio = idServicio;
		this.nombreServicio = nombreServicio;
		this.monto = monto;
		this.mes = mes;
		this.local = local;
	}

	public ServicioDTO(){
		super();
	}
	
	public int getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(int idServicio) {
		this.idServicio = idServicio;
	}

	public String getNombreServicio() {
		return nombreServicio;
	}

	public void setNombreServicio(String nombreServicio) {
		this.nombreServicio = nombreServicio;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public Date getMes() {
		return mes;
	}

	public void setMes(Date mes) {
		this.mes = mes;
	}

	public LocalDTO getLocal() {
		return local;
	}

	public void setLocal(LocalDTO local) {
		this.local = local;
	}
}