package persistencia.dao.interfaz;

import java.util.List;

import dto.ProvinciaDTO;

public interface ProvinciaDAO {
	
	public boolean insert(ProvinciaDTO provincia) throws Exception;

	public boolean delete(ProvinciaDTO provincia_a_eliminar) throws Exception;

	public List<ProvinciaDTO> readAll() throws Exception;

	public boolean update(ProvinciaDTO provincia) throws Exception;

	public ProvinciaDTO getProvinciaById(int id);

	public List<ProvinciaDTO> readAllByIdPais(int idPais);

}
