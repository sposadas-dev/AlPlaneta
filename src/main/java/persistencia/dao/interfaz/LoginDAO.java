package persistencia.dao.interfaz;

import java.util.List;

import dto.CiudadDTO;
import dto.LoginDTO;

public interface LoginDAO {
	
	public boolean insert(LoginDTO datos);

	public boolean delete(LoginDTO datos_a_eliminar);

	public List<LoginDTO> readAll();

	public boolean update(LoginDTO datos);

	public LoginDTO getByDatos(String usr,String pass);

}
