package modelo;

import java.util.List;

import dto.CiudadDTO;
import persistencia.dao.interfaz.CiudadDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;

public class ModeloCiudad {

	private CiudadDAO ciudad;
	
	public ModeloCiudad(DAOAbstractFactory metodo_persistencia){
		this.ciudad = metodo_persistencia.createCiudadDAO();
	}
	
	public void agregarCiudad(CiudadDTO ciudad) throws Exception{
		this.ciudad.insert(ciudad);
	}
	
	public List<CiudadDTO> obtenerCiudades() throws Exception{
		return this.ciudad.readAll();		
	}

	public void borrarCiudad(CiudadDTO ciudadDelete) throws Exception {
		this.ciudad.delete(ciudadDelete);
	}

	public void editarCiudad(CiudadDTO ciudadUpdate) throws Exception {
		this.ciudad.update(ciudadUpdate);
	}
	
	public CiudadDTO getCiudadByName(String nombreCiudad){
		return this.ciudad.getCiudadByNombre(nombreCiudad);
	}
}