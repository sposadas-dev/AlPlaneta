package main;

import modelo.Login;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.controlador.ControladorAdministrador;
import presentacion.controlador.ControladorLogin;
import presentacion.vista.VentanaLogin;
import presentacion.vista.administrador.VistaAdministrador;

public class Main {
	
	public static void main(String[] args) throws Exception {

//		VistaPrueba vista = new VistaPrueba();
//		ControladorPrueba controlador = new ControladorPrueba(vista);
//		controlador.inicializar();		

		VistaAdministrador administrador = new VistaAdministrador();
		ControladorAdministrador controlador = new ControladorAdministrador(administrador);
		controlador.inicializar();
		
//		Login login = new Login(new DAOSQLFactory());
//		VentanaLogin ventanaLogin = new VentanaLogin();
//		ControladorLogin controladorLogin = new ControladorLogin(ventanaLogin, login);
//		controladorLogin.iniciar();
	}
}