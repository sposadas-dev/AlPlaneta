package persistencia.dao.interfaz;

import java.util.List;

import dto.PasajeroDTO;

public interface PasajeroDAO {

	public boolean insert(PasajeroDTO pasajeroInsert) ;

	public boolean delete(PasajeroDTO pasajeroDelete) ;

	public List<PasajeroDTO> readAll();

	public boolean update(PasajeroDTO pasajeroUpdate) ;

	public PasajeroDTO browse(int idPasajero);

}
