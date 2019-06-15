package dto;

public class LocalDTO {

	private int idLocal;
	private String nombreLocal;
	private String direccionLocal;
	
	public LocalDTO(int idLocal, String nombreLocal, String direccionLocal) {
		this.idLocal = idLocal;
		this.nombreLocal = nombreLocal;
		this.direccionLocal = direccionLocal;
	}
	
	public LocalDTO() {
		
	}
	
	public int getIdLocal() {
		return idLocal;
	}
	public void setIdLocal(int idLocal) {
		this.idLocal = idLocal;
	}
	public String getNombreLocal() {
		return nombreLocal;
	}
	public void setNombreLocal(String nombreLocal) {
		this.nombreLocal = nombreLocal;
	}
	public String getDireccionLocal() {
		return direccionLocal;
	}
	public void setDireccionLocal(String direccionLocal) {
		this.direccionLocal = direccionLocal;
	}
	
	
}
