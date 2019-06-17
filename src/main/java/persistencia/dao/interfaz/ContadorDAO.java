package persistencia.dao.interfaz;

import java.util.List;

import dto.ContadorDTO;

public interface ContadorDAO {

	public boolean insert(ContadorDTO contador);

	public boolean delete(int idContador);
	
	public List<ContadorDTO> readAll();

	public boolean update(ContadorDTO contador);
	
	public ContadorDTO getById(int idContador);

	public ContadorDTO getByLoginId(int idLogin);
	
	public ContadorDTO getByMail(String email);

	public boolean updateMail(ContadorDTO contador2);
}
