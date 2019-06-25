package persistencia.dao.interfaz;

import dto.TarjetaDTO;
import java.util.List;

public interface TarjetaDAO {
	
	
	public boolean insert(TarjetaDTO tarjeta);
	
	public List<TarjetaDTO> readAll();

	public TarjetaDTO getTarjetaById(int idtarjeta);

	public TarjetaDTO getUltimoRegistroTarjeta();
	
//	public boolean update(TarjetaDTO tarjeta);
	
}
