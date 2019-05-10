package persistencia.dao.interfaz;

import java.util.List;

import dto.CiudadDTO;
import dto.DatosLoginDTO;

public interface DatosLoginDAO {
	
	public boolean insert(DatosLoginDTO datos) throws Exception;

	public boolean delete(DatosLoginDTO datos_a_eliminar) throws Exception;

	public List<DatosLoginDTO> readAll() throws Exception;

	public boolean update(DatosLoginDTO datos) throws Exception;

	public DatosLoginDTO getByDatos(String usr,String pass)throws Exception;

}
