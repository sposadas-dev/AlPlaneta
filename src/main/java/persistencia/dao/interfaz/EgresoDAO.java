package persistencia.dao.interfaz;

import java.util.List;

import dto.EgresosDTO;

public interface EgresoDAO {

	public boolean insert(EgresosDTO egreso);

	public List<EgresosDTO> readAll();
}
