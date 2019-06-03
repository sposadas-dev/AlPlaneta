package persistencia.dao.interfaz;

import java.util.List;

import dto.Pagos_PasajeDTO;

public interface Pagos_PasajeDAO {

	public boolean insert(Pagos_PasajeDTO nuevoPagoPasaje);

	public List<Pagos_PasajeDTO> readAll();
}
