package persistencia.dao.interfaz;

import java.util.List;

import dto.PuntoDTO;

public interface PuntoDAO {
	
	public boolean insert(PuntoDTO punto);

	public boolean delete(PuntoDTO punto_a_eliminar);
	
	public List<PuntoDTO> readAll();

	public boolean update(PuntoDTO punto);
	
	public PuntoDTO getPuntoById(int id);

}
