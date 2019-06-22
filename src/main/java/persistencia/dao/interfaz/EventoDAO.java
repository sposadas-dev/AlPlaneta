package persistencia.dao.interfaz;

import java.util.List;
import dto.EventoDTO;

public interface EventoDAO {
	
	public boolean insert(EventoDTO evento);
	
	public List<EventoDTO> readAll();

	public boolean update(EventoDTO evento);
	
	public boolean updateVisto(EventoDTO evento);

	public EventoDTO getEventoById(int id);

	public List<EventoDTO> readBetween(String i, String j);

	public boolean updateAdministrativo(EventoDTO evento);
}
