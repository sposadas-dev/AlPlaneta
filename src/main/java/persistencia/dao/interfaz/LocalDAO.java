package persistencia.dao.interfaz;

import java.util.List;

import dto.LocalDTO;

public interface LocalDAO {

	public boolean insert(LocalDTO local);
	
	public boolean delete(int idLocal);
	
	public List<LocalDTO> readAll();

	public boolean update(LocalDTO local);
	
	public LocalDTO getById(int idLocal);
	
	public LocalDTO readOne(String local);
	
}
