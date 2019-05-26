package persistencia.dao.interfaz;

import java.util.List;

import dto.PasajeDTO;
import dto.Pasaje_PasajerosDTO;
import dto.PasajeroDTO;

public interface Pasaje_PasajerosDAO {

	public List<Pasaje_PasajerosDTO> readAll();

	public boolean getById(int id);
	
	public boolean update(Pasaje_PasajerosDTO pasaje_pasajeros);

	public boolean delete(Pasaje_PasajerosDTO pasaje_pasajeros);

	public boolean insert(Pasaje_PasajerosDTO pasaje_pasajeros);

	public List<PasajeroDTO> traerPasajerosDePasaje(int idPasaje);
}
