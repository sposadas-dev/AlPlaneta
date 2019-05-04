package persistencia.dao.interfaz;

import java.util.List;

import dto.CiudadDTO;

public interface CiudadDAO {
	
	public boolean insert(CiudadDTO ciudad) throws Exception;

	public boolean delete(CiudadDTO ciudad_a_eliminar) throws Exception;

	public List<CiudadDTO> readAll() throws Exception;

	public boolean update(CiudadDTO ciudad) throws Exception;

	CiudadDTO browse(int idCiudad) throws Exception;


}
