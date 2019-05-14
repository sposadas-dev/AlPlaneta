package modelo;

import java.util.List;

import dto.AdministrativoDTO;
import persistencia.dao.interfaz.AdministrativoDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;

public class Administrativo {

	private AdministrativoDAO administrativo;
	
	public Administrativo(DAOAbstractFactory metodo_persistencia){
		this.administrativo = metodo_persistencia.createAdministrativoDAO();
	}
	
	public void agregarAdministrativo(AdministrativoDTO nuevoAdministrativo){
		this.administrativo.insert(nuevoAdministrativo);
	}
	
	public List<AdministrativoDTO> obtenerAdministrativos(){
		return this.administrativo.readAll();		
	}

	public AdministrativoDTO getByLoginId(int idLogin) {
		return this.administrativo.getByLoginId(idLogin);
	}
}