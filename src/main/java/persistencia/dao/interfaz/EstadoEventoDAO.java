package persistencia.dao.interfaz;

import java.util.List;
import dto.EstadoEventoDTO;

public interface EstadoEventoDAO {
	
	public boolean insert(EstadoEventoDTO estado);

	public boolean delete(EstadoEventoDTO estado);

	public List<EstadoEventoDTO> readAll();

	public boolean update(EstadoEventoDTO estado);
	
	public EstadoEventoDTO getEstadoEventoByNombre(String nombre);
	
	public EstadoEventoDTO getEstadoEventoById(int id);

}
