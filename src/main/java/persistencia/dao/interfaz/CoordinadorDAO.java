package persistencia.dao.interfaz;

import java.util.List;

import dto.CoordinadorDTO;

public interface CoordinadorDAO {
	
	public boolean insert(CoordinadorDTO coordinador);

	public List<CoordinadorDTO> readAll();

	public boolean update(CoordinadorDTO coordinador);

}
