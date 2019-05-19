package persistencia.dao.interfaz;

import java.util.List;

import dto.RolDTO;

public interface RolDAO {
	
	public boolean insert(RolDTO rol) ;

	public boolean delete(RolDTO rol);

	public List<RolDTO> readAll();

	public boolean update(RolDTO rol);
	
	public RolDTO getById(int id);
}
