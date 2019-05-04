package persistencia.dao.interfaz;

import java.util.List;

import dto.PagoDTO;

public interface PagoDAO {
	
	public boolean insert(PagoDTO pagoInsert);

	public boolean delete(PagoDTO pagoDelete);

	public List<PagoDTO> readAll();

	public boolean update(PagoDTO pagoUpdate);

	public PagoDTO browse(int idPago);

}
