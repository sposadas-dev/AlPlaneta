package persistencia.dao.interfaz;

import java.util.List;

import dto.PaisDTO;

public interface PaisDAO {
	
	public boolean insert(PaisDTO pais) throws Exception;

	public boolean delete(PaisDTO pais_a_eliminar) throws Exception;

	public List<PaisDTO> readAll() throws Exception;

	public boolean update(PaisDTO pais) throws Exception;

	public PaisDTO getPaisById(int id);

}
