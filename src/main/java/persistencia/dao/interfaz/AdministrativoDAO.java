package persistencia.dao.interfaz;

import java.util.List;

import dto.AdministrativoDTO;


public interface AdministrativoDAO {
	
	public boolean insert(AdministrativoDTO administativo);

	public List<AdministrativoDTO> readAll();

	public boolean update(AdministrativoDTO administrativo);

	public AdministrativoDTO getByDatosLogin(String usuario, String contrasena);

	public AdministrativoDTO getByLoginId(int id);
	
	public AdministrativoDTO getByMail(String email);

}
