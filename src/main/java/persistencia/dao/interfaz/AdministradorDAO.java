package persistencia.dao.interfaz;

import java.util.List;

import dto.AdministradorDTO;
import dto.AdministrativoDTO;

public interface AdministradorDAO {

	public boolean insert(AdministradorDTO administrador);

	public boolean delete(AdministradorDTO administrador_a_eliminar);
	
	public List<AdministradorDTO> readAll();

	public boolean update(AdministradorDTO administrador);
	
	public AdministradorDTO getById(int id );

	public AdministradorDTO getByLoginId(int id);
	
	public AdministradorDTO getByMail(String email);

	public boolean updateMail(AdministradorDTO administrador2);
}
