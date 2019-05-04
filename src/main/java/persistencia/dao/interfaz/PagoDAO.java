package persistencia.dao.interfaz;

import java.util.List;

import dto.PagoDTO;

public interface PagoDAO {
	public boolean insert(PagoDTO pago);

	public boolean delete(PagoDTO pago_a_eliminar);

	public List<PagoDTO> readAll();

	public boolean update(PagoDTO pago);

}
