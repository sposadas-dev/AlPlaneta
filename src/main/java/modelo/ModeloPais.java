package modelo;

import java.util.List;
import dto.PaisDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.PaisDAO;

public class ModeloPais {

	private PaisDAO pais;
	
	public ModeloPais(DAOAbstractFactory metodo_persistencia){
		this.pais = metodo_persistencia.createPaisDAO();
	}
	
	public void agregarPais(PaisDTO pais){
		this.pais.insert(pais);
	}
	
	public void borrarPais(PaisDTO pais) {
		this.pais.delete(pais);
	}
	
	public List<PaisDTO> obtenerPaises(){
		return this.pais.readAll();	
	}
	
	public void editarPais(PaisDTO pais){
		this.pais.update(pais);
	}
	
	public PaisDTO getPaisById(int idPais){
		return this.pais.getPaisById(idPais);
	}
}