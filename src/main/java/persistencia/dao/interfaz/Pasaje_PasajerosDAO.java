package persistencia.dao.interfaz;

import java.util.List;

import dto.PasajeDTO;
import dto.Pasaje_PasajerosDTO;

public interface Pasaje_PasajerosDAO {

	public List<PasajeDTO> readAll();

	public boolean getById(int id);
	

	public boolean update(Pasaje_PasajerosDTO pasaje_pasajeros);

	public boolean delete(Pasaje_PasajerosDTO pasaje_pasajeros);

	public boolean insert(Pasaje_PasajerosDTO pasaje_pasajeros);
	
}
