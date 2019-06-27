package persistencia.dao.interfaz;

import dto.SueldoDTO;

public interface SueldoDAO {

	public boolean insert(SueldoDTO sueldo);

	public SueldoDTO getUltimoRegistroSueldo();
}
