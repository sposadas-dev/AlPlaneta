package dto;

import java.math.BigDecimal;
import java.sql.Date;

public class SueldoDTO {

	private int idSueldo;
	private BigDecimal montoSueldo;
	private Date mes;
	private RolDTO rol;
	
	public SueldoDTO(int idSueldo, BigDecimal montoSueldo, Date mes, RolDTO rol){
		this.idSueldo = idSueldo;
		this.montoSueldo = montoSueldo;
		this.mes = mes;
		this.rol = rol;
	}

	public SueldoDTO(){
		super();
	}
	
	public int getIdSueldo() {
		return idSueldo;
	}

	public void setIdSueldo(int idSueldo) {
		this.idSueldo = idSueldo;
	}

	public BigDecimal getMontoSueldo() {
		return montoSueldo;
	}

	public void setMontoSueldo(BigDecimal montoSueldo) {
		this.montoSueldo = montoSueldo;
	}

	public Date getMes() {
		return mes;
	}

	public void setMes(Date mes) {
		this.mes = mes;
	}

	public RolDTO getRol() {
		return rol;
	}

	public void setRol(RolDTO rol) {
		this.rol = rol;
	}
}