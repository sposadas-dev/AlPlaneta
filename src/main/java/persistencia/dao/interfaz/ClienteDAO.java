package persistencia.dao.interfaz;

import java.util.List;

import dto.ClienteDTO;

public interface ClienteDAO {
	
	public boolean insert(ClienteDTO Cliente);

	//public boolean delete(VentaDTO venta_a_eliminar);
	
	public List<ClienteDTO> readAll();

	public boolean update(ClienteDTO cliente_editar);

}
