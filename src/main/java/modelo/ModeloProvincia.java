package modelo;

import java.util.List;

import dto.ProvinciaDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.ProvinciaDAO;

public class ModeloProvincia {

	private ProvinciaDAO provincia;
	
	public ModeloProvincia(DAOAbstractFactory metodo_persistencia){
		this.provincia = metodo_persistencia.createProvinciaDAO();
	}
	
	public void agregarProvincia(ProvinciaDTO provincia){
		this.provincia.insert(provincia);
	}
	
	public void borrarProvincia(ProvinciaDTO provincia) {
		this.provincia.delete(provincia);
	}
	
	public List<ProvinciaDTO> obtenerProvincias(){
		return this.provincia.readAll();	
	}
	
	public void editarProvincia(ProvinciaDTO provincia){
		this.provincia.update(provincia);
	}
	
	public ProvinciaDTO getProvinciaById(int idProvincia){
		return this.provincia.getProvinciaById(idProvincia);
	}

	public List<ProvinciaDTO> obtenerProvinciaPorIdPais(int idPais){
		return this.provincia.readAllByIdPais(idPais);
	}

}