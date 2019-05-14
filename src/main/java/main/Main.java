package main;

import modelo.Login;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.controlador.Controlador;
import presentacion.controlador.ControladorLogin;
import presentacion.controlador.ControladorPrueba;
import presentacion.vista.VentanaLogin;
import presentacion.vista.Vista;
import presentacion.vista.VistaLogin;
import presentacion.vista.VistaPrueba;

public class Main {
	
	public static void main(String[] args) throws Exception {
//		Vista vista = new Vista();
//		Controlador controlador = new Controlador(vista);
//		controlador.inicializar();
		
//		VistaPrueba vista = new VistaPrueba();
//		ControladorPrueba controlador = new ControladorPrueba(vista);
//		controlador.inicializar();

		/*Para ver el login descomentar las siguientes lineas*/	
		
		Login login = new Login(new DAOSQLFactory());
		VentanaLogin ventanaLogin = new VentanaLogin();
		ControladorLogin controladorLogin = new ControladorLogin(ventanaLogin, login);
		controladorLogin.iniciar();
		
	}
}
