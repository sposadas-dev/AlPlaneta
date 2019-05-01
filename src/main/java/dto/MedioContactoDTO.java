package dto;

public class MedioContactoDTO {
	private String telefonoFijo;
	private String telefonoCelular;
	private String email;

	public MedioContactoDTO(String telefonoFijo, String telefonoCelular, String email) {
		this.telefonoFijo = telefonoFijo;
		this.telefonoCelular = telefonoCelular;
		this.email = email;
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