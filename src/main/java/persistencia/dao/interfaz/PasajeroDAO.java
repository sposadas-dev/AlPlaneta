package persistencia.dao.interfaz;

import java.util.List;

import dto.PasajeroDTO;

public interface PasajeroDAO {

	public boolean insert(PasajeroDTO pasajeroInsert) throws Exception;

	public boolean delete(PasajeroDTO pasajeroDelete) throws Exception;

	public List<PasajeroDTO> readAll() throws Exception;

	public boolean update(PasajeroDTO pasajeroUpdate) throws Exception;

	public PasajeroDTO browse(int idPasajero) throws Exception;

}
