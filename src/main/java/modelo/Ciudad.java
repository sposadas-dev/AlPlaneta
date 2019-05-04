package modelo;

import java.util.List;

import dto.CiudadDTO;
import persistencia.dao.interfaz.CiudadDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;

public class Ciudad {

	private CiudadDAO ciudad;
	
	public Ciudad(DAOAbstractFactory metodo_persistencia){
		this.ciudad = metodo_persistencia.createCiudadDAO();
	}
	
	public void agregarCiudad(CiudadDTO nuevaCiudad){
		this.ciudad.insert(nuevaCiudad);
	}
	
	public List<CiudadDTO> obtenerCiudades(){
		return this.ciudad.readAll();		
	}
}