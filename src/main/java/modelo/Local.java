package modelo;

import java.util.List;

import dto.LocalDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.LocalDAO;

public class Local {

	private LocalDAO local;
	
	public Local(DAOAbstractFactory metodo_persistencia){
		this.local = metodo_persistencia.createLocalDAO();
	}
	
	public boolean insert(LocalDTO local) {
		return this.local.insert(local);
	}
	
	public boolean delete(int idLocal) {
		return this.local.delete(idLocal);
	}
	
	public List<LocalDTO> readAll(){
		return this.local.readAll();
	}
	
	public boolean update(LocalDTO local) {
		return this.local.update(local);
	}
	
	public LocalDTO getById(int idLocal) {
		return this.local.getById(idLocal);
	}
	
	public LocalDTO obtenerLocal(String nombreLocal) {
		return this.local.readOne(nombreLocal);
	}
	
}
