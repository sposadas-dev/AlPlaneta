package persistencia.dao.interfaz;

import java.util.ArrayList;
import java.util.List;

import dto.ClienteDTO;
import dto.PuntoDTO;

public interface PuntoDAO {
	
	public boolean insert(PuntoDTO punto);

	public boolean delete(PuntoDTO punto_a_eliminar);
	
	public List<PuntoDTO> readAll();

	public boolean update(PuntoDTO punto);
	
	public PuntoDTO getPuntoById(int id);

	public PuntoDTO getUltimoRegistroPunto();

	public ArrayList<PuntoDTO> readAllByClienteID(ClienteDTO cliente);

}
