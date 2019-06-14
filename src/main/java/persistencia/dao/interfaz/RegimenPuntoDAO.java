package persistencia.dao.interfaz;

import java.util.List;

import dto.RegimenPuntoDTO;

public interface RegimenPuntoDAO {
	
	public boolean insert(RegimenPuntoDTO punto);

	public boolean delete(RegimenPuntoDTO punto_a_eliminar);
	
	public List<RegimenPuntoDTO> readAll();

	public boolean update(RegimenPuntoDTO punto);
	
	public RegimenPuntoDTO getPuntoById(int id);

	public RegimenPuntoDTO getUltimoRegistroRegimenPunto();

}
