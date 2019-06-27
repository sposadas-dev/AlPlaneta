package persistencia.dao.interfaz;

import java.util.List;

import dto.ServicioDTO;

public interface ServicioDAO {

	public boolean insert(ServicioDTO servicio);

	public List<ServicioDTO> readAll();

	public boolean update(ServicioDTO servicio_a_editar);

	public boolean delete(ServicioDTO eliminarServicio);

	public ServicioDTO getServicioById(int idServicio);
}
