package dto;

import java.sql.Date;

public class ServicioDTO {

	private int idServicio;
	private String nombreServicio;
	private Date mes;
	private LocalDTO local;
	
	public ServicioDTO(int idServicio, String nombreServicio, Date mes,LocalDTO local){
		this.idServicio = idServicio;
		this.nombreServicio = nombreServicio;
		this.mes = mes;
		this.local = local;
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