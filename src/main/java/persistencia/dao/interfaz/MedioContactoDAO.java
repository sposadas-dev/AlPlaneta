package persistencia.dao.interfaz;

import java.util.List;

import dto.MedioContactoDTO;


public interface MedioContactoDAO {
	
	public boolean insert(MedioContactoDTO medio);

	public boolean delete(MedioContactoDTO medio_a_eliminar);

	public List<MedioContactoDTO> readAll();

	public boolean update(MedioContactoDTO medio);


}
