package dto;

public class EgresosDTO {

	private int idEgreso;
	private Sueldos_EmpleadosDTO sueldos_empleados;
	private ServicioDTO servicio;
	private PasajeDTO pasaje;
	
	public EgresosDTO(int idEgreso, Sueldos_EmpleadosDTO sueldos_empleados, ServicioDTO servicio,PasajeDTO pasaje){
		this.idEgreso = idEgreso;
		this.sueldos_empleados = sueldos_empleados;
		this.servicio = servicio;
		this.pasaje = pasaje;
	}
	
	
}
