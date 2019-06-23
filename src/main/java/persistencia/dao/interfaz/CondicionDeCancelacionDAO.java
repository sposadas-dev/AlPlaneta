package persistencia.dao.interfaz;

import java.util.List;

import dto.AdministradorDTO;
import dto.AdministrativoDTO;
import dto.CondicionDeCancelacionDTO;

public interface CondicionDeCancelacionDAO {

	public boolean insert(CondicionDeCancelacionDTO condicion);

	public boolean delete(int idCondicion);
	
	public List<CondicionDeCancelacionDTO> readAll();

	public boolean update(CondicionDeCancelacionDTO condicion);
	
	public CondicionDeCancelacionDTO getById(int id );

	public List<CondicionDeCancelacionDTO> getByEstado(String estado);

}
