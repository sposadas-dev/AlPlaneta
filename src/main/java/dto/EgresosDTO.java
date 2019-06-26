package dto;

public class EgresosDTO {

	private int idEgreso;
	private Sueldos_EmpleadosDTO sueldos_empleados;
	private ServicioDTO servicio;
	private PasajeDTO pasaje;
	
	public EgresosDTO(int idEgreso, Sueldos_EmpleadosDTO sueldos_empleados, ServicioDTO servicio, PasajeDTO pasaje){
		this.idEgreso = idEgreso;
		this.sueldos_empleados = sueldos_empleados;
		this.servicio = servicio;
		this.pasaje = pasaje;
	}

	public EgresosDTO() {
		super();
	}

	public int getIdEgreso() {
		return idEgreso;
	}

	public void setIdEgreso(int idEgreso) {
		this.idEgreso = idEgreso;
	}

	public Sueldos_EmpleadosDTO getSueldos_empleados() {
		return sueldos_empleados;
	}

	public void setSueldos_empleados(Sueldos_EmpleadosDTO sueldos_empleados) {
		this.sueldos_empleados = sueldos_empleados;
	}

	public ServicioDTO getServicio() {
		return servicio;
	}

	public void setServicio(ServicioDTO servicio) {
		this.servicio = servicio;
	}

	public PasajeDTO getPasaje() {
		return pasaje;
	}

	public void setPasaje(PasajeDTO pasaje) {
		this.pasaje = pasaje;
	}
}