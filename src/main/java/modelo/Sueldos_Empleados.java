package modelo;

import java.util.List;

import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.Sueldos_EmpleadosDAO;
import dto.SueldoDTO;
import dto.Sueldos_EmpleadosDTO;

public class Sueldos_Empleados {
	
	private Sueldos_EmpleadosDAO sueldos_empleados;

	public Sueldos_Empleados(DAOAbstractFactory metodo_persistencia){
		this.sueldos_empleados = metodo_persistencia.createSueldoEmpleadoDAO();
	}
	
	public void agregarSueldoEmpleado(Sueldos_EmpleadosDTO nuevoSueldoEmpleado){
		this.sueldos_empleados.insert(nuevoSueldoEmpleado);
	}
	
	public SueldoDTO obtenerSueldoEmpleado(int idEmpleado,int idRol){
		return this.sueldos_empleados.getSueldoByEmpleado(idEmpleado,idRol);
	}
}