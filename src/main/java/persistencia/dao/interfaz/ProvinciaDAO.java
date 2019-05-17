package persistencia.dao.interfaz;

import java.util.List;

import dto.ProvinciaDTO;

public interface ProvinciaDAO {
	
	public boolean insert(ProvinciaDTO provincia);

	public boolean delete(ProvinciaDTO provincia_a_eliminar);

	public List<ProvinciaDTO> readAll();
	public boolean update(ProvinciaDTO provincia);

	public ProvinciaDTO getProvinciaById(int id);

	public List<ProvinciaDTO> readAllByIdPais(int idPais);

}
