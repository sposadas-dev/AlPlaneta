package persistencia.dao.interfaz;

import java.util.List;
import dto.PromocionDTO;

public interface PromocionDAO {
	
	public boolean insert(PromocionDTO promocion);
	
	public List<PromocionDTO> readAll();

	public boolean updateEstado(PromocionDTO promocion);
	
	public boolean update(PromocionDTO promocion);

	public PromocionDTO getPromocionById(int id);
}
