package main;
import java.util.Properties;

import javax.swing.UIManager;

import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;

import modelo.Login;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.controlador.ControladorLogin;
import presentacion.vista.VentanaLogin;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		Properties props = new Properties();
		props.put("logoString", "");
		GraphiteLookAndFeel.setCurrentTheme(props);
		UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
		
		Login login = new Login(new DAOSQLFactory());
		VentanaLogin ventanaLogin = new VentanaLogin();
		ControladorLogin controladorLogin = new ControladorLogin(ventanaLogin, login);
		controladorLogin.iniciar();
	}
}