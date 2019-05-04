package dto;

public class MedioContactoDTO {
	
	private int idMedioContacto;
	private String telefonoFijo;
	private String telefonoCelular;
	private String email;

	public MedioContactoDTO(int idMedioContacto,String telefonoFijo, String telefonoCelular, String email) {
		this.idMedioContacto = idMedioContacto;
		this.telefonoFijo = telefonoFijo;
		this.telefonoCelular = telefonoCelular;
		this.email = email;
	}

	public int getIdMedioContacto() {
		return idMedioContacto;
	}

	public void setIdMedioContacto(int idMedioContacto) {
		this.idMedioContacto = idMedioContacto;
	}

	public String getTelefonoFijo() {
		return telefonoFijo;
	}

	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	public String getTelefonoCelular() {
		return telefonoCelular;
	}

	public void setTelefonoCelular(String telefonoCelular) {
		this.telefonoCelular = telefonoCelular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}