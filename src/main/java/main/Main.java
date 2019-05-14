package main;

import modelo.Login;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.controlador.Controlador;
import presentacion.controlador.ControladorLogin;
import presentacion.controlador.ControladorPrueba;
import presentacion.vista.VentanaLogin;
import presentacion.vista.Vista;
import presentacion.vista.administrador.VistaAdministrador;
import presentacion.vista.administrativo.VistaAdministrativo;

public class Main {
	
	public static void main(String[] args) throws Exception {

//		VistaPrueba vista = new VistaPrueba();
//		ControladorPrueba controlador = new ControladorPrueba(vista);
//		controlador.inicializar();		

//		VistaAdministrador administrador= new VistaAdministrador();
//		administrador.mostrarVentana(true);
		Login login = new Login(new DAOSQLFactory());
		VentanaLogin ventanaLogin = new VentanaLogin();
		ControladorLogin controladorLogin = new ControladorLogin(ventanaLogin, login);
		controladorLogin.iniciar();
		
	}
}