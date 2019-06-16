package modelo;

import java.util.List;

import persistencia.dao.interfaz.AdministradorDAO;
import persistencia.dao.interfaz.AdministrativoDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import dto.AdministradorDTO;
import dto.AdministrativoDTO;

public class Administrador {
	
private AdministradorDAO administrador;
	
	public Administrador(DAOAbstractFactory metodo_persistencia){
		this.administrador = metodo_persistencia.createAdministradorDAO();
	}
	
	public void agregarAdministrador(AdministradorDTO nuevoAdministrador){
		this.administrador.insert(nuevoAdministrador);
	}
	
	public boolean eliminarAdministrador(int idAdministrador) {
		return this.administrador.delete(idAdministrador);
	}
	
	public boolean updateAdministrador(AdministradorDTO updateAdministrador) {
		return this.administrador.update(updateAdministrador);
	}
	
	public List<AdministradorDTO> obtenerAdministradores(){
		return this.administrador.readAll();		
	}

	public AdministradorDTO getByLoginId(int idLogin) {
		return this.administrador.getByLoginId(idLogin);
	}

	public AdministradorDTO buscarPorEmail(String mailDeRecuperacion) {
		return this.administrador.getByMail(mailDeRecuperacion);
	}

	public boolean actualizar(AdministradorDTO administrador2) {
		return this.administrador.updateMail(administrador2);
	}
}
