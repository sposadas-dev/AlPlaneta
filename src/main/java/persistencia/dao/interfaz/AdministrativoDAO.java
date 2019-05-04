package persistencia.dao.interfaz;

import java.util.List;

import dto.AdministrativoDTO;


public interface AdministrativoDAO {
	
	public boolean insert(AdministrativoDTO administativo);

	public boolean delete(AdministrativoDTO administrativo_a_eliminar);
	
	public List<AdministrativoDTO> readAll();

	public boolean update(AdministrativoDTO administrativo);

}
