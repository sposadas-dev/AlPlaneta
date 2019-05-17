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
	
	public void agregarCiudad(CiudadDTO ciudad){
		this.ciudad.insert(ciudad);
	}
	
	public List<CiudadDTO> obtenerCiudades(){
		return this.ciudad.readAll();		
	}

	public void borrarCiudad(CiudadDTO ciudadDelete){
		this.ciudad.delete(ciudadDelete);
	}

	public void editarCiudad(CiudadDTO ciudadUpdate){
		this.ciudad.update(ciudadUpdate);
	}

	public List<CiudadDTO> obtenerCiudadPorIdProvincia(int idProvincia) {
		return this.ciudad.readAllByIdprovincia(idProvincia);
	}
}