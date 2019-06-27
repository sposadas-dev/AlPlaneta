package modelo;

import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.SueldoDAO;
import dto.SueldoDTO;

public class Sueldo {

	private SueldoDAO sueldo;

	public Sueldo(DAOAbstractFactory metodo_persistencia){
		this.sueldo = metodo_persistencia.createSueldoDAO();
	}
	
	public void agregarSueldo(SueldoDTO nuevoSueldo){
		this.sueldo.insert(nuevoSueldo);
	}
	
	public SueldoDTO getUltimoRegistroSueldo(){
		return this.sueldo.getUltimoRegistroSueldo();
	}
}