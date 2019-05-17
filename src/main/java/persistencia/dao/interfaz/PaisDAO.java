package persistencia.dao.interfaz;

import java.util.List;

import dto.PaisDTO;

public interface PaisDAO {
	
	public boolean insert(PaisDTO pais);

	public boolean delete(PaisDTO pais_a_eliminar);

	public List<PaisDTO> readAll();

	public boolean update(PaisDTO pais);

	public PaisDTO getPaisById(int id);

}
