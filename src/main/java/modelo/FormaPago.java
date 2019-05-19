package modelo;

import java.util.List;

import dto.FormaPagoDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.FormaPagoDAO;

public class FormaPago {
	private FormaPagoDAO formapago;
	
	public FormaPago(DAOAbstractFactory metodo_persistencia){
		this.formapago = metodo_persistencia.createFormaPagoDAO();
	}
	
	public void agregarFormaPago(FormaPagoDTO formap) {
		this.formapago.insert(formap);
	}
	public void borrarFormaPago(FormaPagoDTO fp_a_eliminar) {
		this.formapago.delete(fp_a_eliminar);
	}
	public void editarFormaPago(FormaPagoDTO fp_a_editar){
		this.formapago.update(fp_a_editar);
	}
	public List<FormaPagoDTO> obtenerFormaPago(){
		return this.formapago.readAll();
	}
	
	public FormaPagoDTO getFormaPagoById(int idFormaPago)
	{
		return this.formapago.getFormaPagoById(idFormaPago);
	}

}
