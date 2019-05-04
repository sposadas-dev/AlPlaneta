package persistencia.dao.interfaz;

import java.util.List;

import dto.EstadoPasajeDTO;


public interface EstadoPasajeDAO {
	
	public boolean insert(EstadoPasajeDTO estado);

	public boolean delete(EstadoPasajeDTO estado_a_eliminar);

	public List<EstadoPasajeDTO> readAll();

	public boolean update(EstadoPasajeDTO estado);
	
	public EstadoPasajeDTO getEstadoPasajeByNombre(String nombre);
	
	public EstadoPasajeDTO getEstadoPasajeById(int id);


}
