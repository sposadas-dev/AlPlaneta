package persistencia.dao.interfaz;

import java.util.List;

import dto.TransporteDTO;


public interface TransporteDAO {
	
	public boolean insert(TransporteDTO transporte);

	public boolean delete(TransporteDTO transporte_a_eliminar);
	
	public List<TransporteDTO> readAll();

	public boolean update(TransporteDTO transporte);

}
