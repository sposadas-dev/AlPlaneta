package dto;

public class FormaPagoDTO {
	
	private int idFormaPago;
	private String tipo;
	
	public FormaPagoDTO(int idfp, String  tipo){
		this.idFormaPago = idfp;
		this.tipo = tipo;
	}

	public FormaPagoDTO() {
		super();
	}
	
	public FormaPagoDTO(String tipo) {
		this.tipo = tipo;
	}

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