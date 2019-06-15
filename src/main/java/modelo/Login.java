package modelo;

import java.util.List;

import dto.LoginDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.LoginDAO;

public class Login {

	private LoginDAO login;
	
	public Login(DAOAbstractFactory metodo_persistencia){
		this.login = metodo_persistencia.createLoginDAO();
	}
	
	public void agregarLogin(LoginDTO login){
		this.login.insert(login);
	}
	
	public boolean deleteLogin(int idLogin) {
		return this.login.delete(idLogin);
	}
	
	public void editarLogin(LoginDTO login){
		this.login.update(login);
	}
	
	public void editarEstado(String estado, int idLogin) {
		this.login.updateEstado(estado, idLogin);
	}
	
	public List<LoginDTO> obtenerLogin(){
		return this.login.readAll();		
	}
	
	public LoginDTO getLoginByDatos(String usr, String pass){
		return this.login.getByDatos(usr, pass);
	} 
}
