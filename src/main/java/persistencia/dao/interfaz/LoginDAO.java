package persistencia.dao.interfaz;

import java.util.List;

import dto.CiudadDTO;
import dto.LoginDTO;

public interface LoginDAO {
	
	public boolean insert(LoginDTO datos);

	public boolean delete(LoginDTO datos_a_eliminar);
	
	public boolean delete(int idLogin);

	public List<LoginDTO> readAll();

	public boolean update(LoginDTO datos);
	
	public boolean updateEstado(String estado, int idLogin);

	public LoginDTO getByDatos(String usr,String pass);

}
