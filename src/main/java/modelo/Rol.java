package modelo;

import java.util.List;

import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.RolDAO;
import persistencia.dao.interfaz.TransporteDAO;
import dto.RolDTO;
import dto.TransporteDTO;

public class Rol {

	private RolDAO rol;

	public Rol(DAOAbstractFactory metodo_persistencia){
		this.rol = metodo_persistencia.createRolDAO();
	}
	
	public void agregarRol(RolDTO nuevoRol){
		this.rol.insert(nuevoRol);
	}
//	
//	public void borrarRol(TransporteDTO rol_a_eliminar) {
//		this.rol.delete(rol_a_eliminar);
//	}
	
	public List<RolDTO> obtenerRoles(){
		return this.rol.readAll();	
	}
}
