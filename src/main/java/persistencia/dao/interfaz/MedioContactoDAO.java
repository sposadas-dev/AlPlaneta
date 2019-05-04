package persistencia.dao.interfaz;

import java.util.List;

import dto.MedioContactoDTO;


public interface MedioContactoDAO {
	
	public boolean insert(MedioContactoDTO medioContacto);

	public boolean delete(MedioContactoDTO medioContacto_a_eliminar);

	public List<MedioContactoDTO> readAll();

	public boolean update(MedioContactoDTO medioContacto);


}
