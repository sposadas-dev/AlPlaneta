package modelo;

import java.util.List;

import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.LoginDAO;
import persistencia.dao.interfaz.MedioContactoDAO;
import dto.LoginDTO;
import dto.MedioContactoDTO;

public class Login {

	private LoginDAO login;
	
	public Login(DAOAbstractFactory metodo_persistencia){
		this.login = metodo_persistencia.createLoginDAO();
	}
	
	public void agregarLogin(LoginDTO login){
		this.login.insert(login);
	}
	
	public List<LoginDTO> obtenerLogin(){
		return this.login.readAll();		
	}
	
	public LoginDTO getLoginByDatos(String usr, String pass){
		return this.login.getByDatos(usr, pass);
	} 
}
