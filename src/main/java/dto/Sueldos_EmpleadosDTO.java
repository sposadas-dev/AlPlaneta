package dto;

public class Sueldos_EmpleadosDTO {

	private int idSueldoEmpleado;
	private int idEmpleado;
	private int idSueldo;
	
	public Sueldos_EmpleadosDTO(int idSueldoEmpleado, int idEmpleado, int idSueldo){
		this.idSueldoEmpleado = idSueldoEmpleado;
		this.idEmpleado = idEmpleado;
		this.idSueldo = idSueldo;
	}

	public Sueldos_EmpleadosDTO(){
		super();
	}

	public int getIdSueldoEmpleado() {
		return idSueldoEmpleado;
	}

	public void setIdSueldoEmpleado(int idSueldoEmpleado) {
		this.idSueldoEmpleado = idSueldoEmpleado;
	}

	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public int getIdSueldo() {
		return idSueldo;
	}

	public void setIdSueldo(int idSueldo) {
		this.idSueldo = idSueldo;
	}
}