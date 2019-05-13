package presentacion.controlador;

import java.awt.event.ActionEvent;

import dto.AdministrativoDTO;
import dto.ClienteDTO;
import dto.LoginDTO;
import modelo.Login;
import persistencia.dao.mysql.AdministrativoDAOSQL;
import persistencia.dao.mysql.ClienteDAOSQL;
import persistencia.dao.mysql.DAOSQLFactory;
import persistencia.dao.mysql.LoginDAOSQL;
import presentacion.vista.VentanaLogin;
import presentacion.vista.VistaLogin;
import presentacion.vista.VistaPrueba;

public class ControladorLogin {

	private VentanaLogin ventanaLogin;
	private Login login;
	private LoginDTO usuarioLogueado;
	private AdministrativoDTO administrativoLogueado;
	private ClienteDTO clienteLogueado;
	
	public ControladorLogin(VentanaLogin ventanaLogin, Login login){
		this.ventanaLogin = ventanaLogin;
		this.login = login;
		this.usuarioLogueado = null;
		
		this.ventanaLogin.getBtnLogin().addActionListener(log->loguearse(log));
	}
	
	public void iniciar(){
		this.ventanaLogin.setVisible(true);
	}
	
	private void loguearse(ActionEvent log) {
		String usuario = ventanaLogin.getTextUsuario().getText();
		String password = new String(ventanaLogin.getPasswordField().getPassword());
		try {
			usuarioLogueado = login.getLoginByDatos(usuario, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(usuarioLogueado==null){
			this.ventanaLogin.getLblError().setVisible(true);
			System.out.println("EL USUARIO O CONTRASENA ES INCORRECTO");
		}else{
			System.out.println("SE LOGEO CORRECTAMENTE CON:"+ usuarioLogueado.getUsuario()+usuarioLogueado.getContrasena());
			if(usuarioLogueado.getRol().getIdRol()==2){
				 administrativoLogueado = obtenerAdministrativo(usuarioLogueado);
//				 mostrarVentanaAdministrativo();
//				 VistaPrueba vista = new VistaPrueba();
//				 ControladorPrueba controladorPrueba = new ControladorPrueba(vista);
				 
			}else{
				if(usuarioLogueado.getRol().getIdRol()==5){
					 clienteLogueado = obtenerCliente(usuarioLogueado);
					 mostrarVentanaCliente();
				}}
		}
		
	}
	

	private void mostrarVentanaCliente() {
		System.out.println("Se Logea como Cliente");
		System.out.println(clienteLogueado.getNombre());
		this.ventanaLogin.setVisible(false);
		
	}

	/*MOSTRAR LA VENTANA PRINCIPAL DEL PERSONAL ADMINISTRATIVO*/
	private void mostrarVentanaAdministrativo() {
		System.out.println("Se Logea Como Administrativo");
		System.out.println(administrativoLogueado.getNombre());
		this.ventanaLogin.setVisible(false);

	}
/*-----------------------METODOS BUSCADOR POR ROLES ---------------------*/
	private AdministrativoDTO obtenerAdministrativo(LoginDTO loginUsuario) {
		AdministrativoDAOSQL dao = new AdministrativoDAOSQL();
		return dao.getByLoginId(loginUsuario.getIdDatosLogin());
	}
	
	private ClienteDTO obtenerCliente(LoginDTO loginUsuario) {
		ClienteDAOSQL sql = new ClienteDAOSQL();
		return sql.getByLoginId(loginUsuario.getIdDatosLogin());
	}
}