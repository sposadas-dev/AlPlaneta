package persistencia.dao.interfaz;

import java.util.List;

import dto.HorarioReservaDTO;

public interface HorarioReservaDAO {
		
		public boolean insert(String horario) throws Exception;

		public List<HorarioReservaDTO> readAll() throws Exception;

		public boolean update(HorarioReservaDTO horario) throws Exception;

		public HorarioReservaDTO getHorarioById(int id);

}
