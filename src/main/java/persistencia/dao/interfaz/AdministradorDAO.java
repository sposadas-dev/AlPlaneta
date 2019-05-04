package persistencia.dao.interfaz;

import java.util.List;

import dto.AdministradorDTO;

public interface AdministradorDAO {

	public boolean insert(AdministradorDTO administrador);

	public boolean delete(AdministradorDTO administrador_a_eliminar);
	
	public List<AdministradorDTO> readAll();

	public boolean update(AdministradorDTO administrador);
}
