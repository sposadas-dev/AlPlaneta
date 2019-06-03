package persistencia.dao.interfaz;

import java.util.List;

import dto.AdministrativoDTO;
import dto.CoordinadorDTO;

public interface CoordinadorDAO {
	
	public boolean insert(CoordinadorDTO coordinador);

	public List<CoordinadorDTO> readAll();

	public boolean update(CoordinadorDTO coordinador);
	
	public CoordinadorDTO getByMail(String email);

	public CoordinadorDTO getByLoginId(int idLogin);
	
	public CoordinadorDTO getById(int id );

}
