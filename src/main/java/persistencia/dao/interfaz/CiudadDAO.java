package persistencia.dao.interfaz;

import java.util.List;

import dto.CiudadDTO;

public interface CiudadDAO {
	
	public boolean insert(CiudadDTO ciudad) ;

	public boolean delete(CiudadDTO ciudad_a_eliminar) ;

	public List<CiudadDTO> readAll() ;

	public boolean update(CiudadDTO ciudad) ;

	public CiudadDTO getCiudadById(int id);

	public List<CiudadDTO> readAllByIdprovincia(int idProvincia);

}
