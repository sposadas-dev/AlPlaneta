package dto;

public class FormaPagoDTO {
	
	private int idFormaPago;
	private String tipo;
	
	public FormaPagoDTO(int idfp, String  tipo){
		this.idFormaPago = idfp;
		this.tipo = tipo;
	}

<<<<<<< src/main/java/dto/FormaPagoDTO.java
	public FormaPagoDTO() {
		super();
	}


=======
	public FormaPagoDTO(){
		super();
	}
	
>>>>>>> src/main/java/dto/FormaPagoDTO.java
	public int getIdFormaPago() {
		return idFormaPago;
	}

	public void setIdFormaPago(int idFormaPago) {
		this.idFormaPago = idFormaPago;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}
