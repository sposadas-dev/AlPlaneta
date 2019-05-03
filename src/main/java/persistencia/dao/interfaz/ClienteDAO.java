package persistencia.dao.interfaz;

import java.util.List;

import dto.ClienteDTO;

public interface ClienteDAO {

	public boolean insert(ClienteDTO cliente);

	public List<ClienteDTO> readAll();
	
}
