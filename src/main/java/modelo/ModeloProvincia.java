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
	
	public void agregarProvincia(ProvinciaDTO provincia) throws Exception{
		this.provincia.insert(provincia);
	}
	
	public List<ProvinciaDTO> obtenerProvincias() throws Exception{
		return this.provincia.readAll();		
	}

	public void borrarProvincia(ProvinciaDTO provinciaDelete) throws Exception {
		this.provincia.delete(provinciaDelete);
	}

	public void editarProvincia(ProvinciaDTO provinciaUpdate) throws Exception {
		this.provincia.update(provinciaUpdate);
	}
	
	public void obtenerProvinciaByID(int idProvincia) throws Exception {
		this.provincia.getProvinciaById(idProvincia);
	}
	
	public List<ProvinciaDTO> obtenerProvinciaPorIdPais(int idPais) throws Exception {
		return this.provincia.readAllByIdPais(idPais);
	}
}