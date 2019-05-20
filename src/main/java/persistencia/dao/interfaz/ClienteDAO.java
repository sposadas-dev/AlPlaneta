package persistencia.dao.interfaz;

import java.util.List;

import dto.ClienteDTO;
import dto.TransporteDTO;

public interface ClienteDAO {
	
	public boolean insert(ClienteDTO cliente);

//	public boolean delete(ClienteDTO cliente_a_eliminar);
	
	public List<ClienteDTO> readAll();

	public boolean update(ClienteDTO cliente);

	public ClienteDTO getClienteById(int idCliente);
	
	public ClienteDTO getByLoginId(int idCliente);

	
}
