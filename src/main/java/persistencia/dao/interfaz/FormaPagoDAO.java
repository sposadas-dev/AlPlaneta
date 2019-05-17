package persistencia.dao.interfaz;

import java.util.List;

import dto.FormaPagoDTO;

public interface FormaPagoDAO {
	
	
	public boolean insert(FormaPagoDTO estado);

	public boolean delete(FormaPagoDTO estado_a_eliminar);

	public List<FormaPagoDTO> readAll();

	public boolean update(FormaPagoDTO estado);

	public FormaPagoDTO getFormaPagoById(int idFormaPago);
	

}
