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
	
	public void editarAdministrativo(AdministrativoDTO editarAdministrativo) {
		this.administrativo.update(editarAdministrativo);
	}
	
	public List<AdministrativoDTO> obtenerAdministrativos(){
		return this.administrativo.readAll();		
	}
	
	public boolean actualizar(AdministrativoDTO editarAdministrativo){
		return this.administrativo.updateContrasena(editarAdministrativo);
	}

	public AdministrativoDTO getByLoginId(int idLogin) {
		return this.administrativo.getByLoginId(idLogin);
	}

	public AdministrativoDTO buscarPorEmail(String email) {
		return this.administrativo.getByMail(email);
	}
	
	public boolean delete(int idAdministrativo) {
		return this.administrativo.delete(idAdministrativo);
	}
}
