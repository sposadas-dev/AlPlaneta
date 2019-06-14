package persistencia.dao.interfaz;

import java.util.List;

import dto.ViajeDTO;
import dto.Viaje_PromocionDTO;

public interface Viaje_PromocionDAO {

	public List<Viaje_PromocionDTO> readAll();

//	public boolean getById(int id);
	
//	public boolean update(Viaje_PromocionDTO viaje_promocion);

	public boolean delete(Viaje_PromocionDTO viaje_promocion);

	public boolean insert(Viaje_PromocionDTO viaje_promocion);

	public List<ViajeDTO> obtenerViajesEnPromo(int idPromocion);
	
//	public List<PromocionDTO> traerPromocionesDeViaje(int idViaje);
}