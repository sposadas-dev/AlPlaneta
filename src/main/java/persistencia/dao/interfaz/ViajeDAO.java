package persistencia.dao.interfaz;

import java.util.List;

import dto.ViajeDTO;

public interface ViajeDAO {
	
	public boolean insert(ViajeDTO viaje);

	public boolean delete(ViajeDTO viaje_a_eliminar);
	
	public List<ViajeDTO> readAll();

	public boolean update(ViajeDTO viaje_editar);

	public ViajeDTO getViajeById(int idViaje);
}