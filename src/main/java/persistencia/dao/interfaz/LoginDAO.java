package persistencia.dao.interfaz;

import java.util.List;

import dto.CiudadDTO;
import dto.LoginDTO;

public interface LoginDAO {
	
	public boolean insert(LoginDTO datos) throws Exception;

	public boolean delete(LoginDTO datos_a_eliminar) throws Exception;

	public List<LoginDTO> readAll() throws Exception;

	public boolean update(LoginDTO datos) throws Exception;

	public LoginDTO getByDatos(String usr,String pass)throws Exception;

}
