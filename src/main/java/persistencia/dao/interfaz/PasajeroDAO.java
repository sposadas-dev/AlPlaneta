package persistencia.dao.interfaz;

import java.util.List;

import dto.PasajeroDTO;


public interface PasajeroDAO {
	
	public boolean insert(PasajeroDTO pasajero);

	public boolean delete(PasajeroDTO pasajero_a_eliminar);
	
	public List<PasajeroDTO> readAll();

	public boolean update(PasajeroDTO pasajero);


}
