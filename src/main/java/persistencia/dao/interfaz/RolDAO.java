package persistencia.dao.interfaz;

import java.util.List;

import dto.RolDTO;

public interface RolDAO {
	
	public boolean insert(RolDTO rol) throws Exception;

	public boolean delete(RolDTO rol) throws Exception;

	public List<RolDTO> readAll() throws Exception;

	public boolean update(RolDTO rol) throws Exception;
	
	public RolDTO getById(int id);
}
