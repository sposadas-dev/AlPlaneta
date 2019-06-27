package modelo;

import java.util.List;

import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.EgresoDAO;
import dto.EgresosDTO;

public class Egreso {

	private EgresoDAO egreso;
	
	public Egreso(DAOAbstractFactory metodo_persistencia){
		this.egreso= metodo_persistencia.createEgresoDAO();
	}
	
	public void agregarEgreso(EgresosDTO egreso) {
		this.egreso.insert(egreso);
	}
	
	public List<EgresosDTO> obtenerEgresos(){
		return this.egreso.readAll();
	}
}
