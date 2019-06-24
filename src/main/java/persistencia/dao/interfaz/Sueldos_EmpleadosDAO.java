package persistencia.dao.interfaz;

import java.util.List;

import dto.SueldoDTO;
import dto.Sueldos_EmpleadosDTO;

public interface Sueldos_EmpleadosDAO {

	public boolean insert(Sueldos_EmpleadosDTO sueldo);

	public SueldoDTO getSueldoByEmpleado(int idEmpleado, int idRol);
}
