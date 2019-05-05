package dto;

public class HorarioReservaDTO {
	private int idHorarioDisponible;
	private String horaInicio;
	private String horaFin;
	
	public HorarioReservaDTO(int idHorarioDisponible, String horaInicio, String horaFin) {
		this.idHorarioDisponible = idHorarioDisponible;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
	}

	public HorarioReservaDTO() {
		super();
	}

	public int getIdHorarioDisponible() {
		return idHorarioDisponible;
	}

	public void setIdHorarioDisponible(int idHorarioDisponible) {
		this.idHorarioDisponible = idHorarioDisponible;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}
	
	

}
