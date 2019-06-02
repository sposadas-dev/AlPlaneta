package presentacion.controlador;

import java.awt.event.ActionEvent;

import dto.AdministradorDTO;
import dto.AdministrativoDTO;
import dto.ClienteDTO;
import dto.LoginDTO;
import modelo.Administrador;
import modelo.Administrativo;
import modelo.Cliente;
import modelo.Login;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.VentanaLogin;
import presentacion.vista.administrador.VistaAdministrador;
import presentacion.vista.administrativo.VistaAdministrativo;
import presentacion.vista.cliente.VistaCliente;

public class ControladorLogin {

	private VentanaLogin ventanaLogin;
	private VistaAdministrador vistaAdministrador;
	private VistaAdministrativo vistaAdministrativo;
	private VistaCliente vistaCliente;
	private Login login;
	private LoginDTO usuarioLogueado;
	private AdministrativoDTO administrativoLogueado;
	private ClienteDTO clienteLogueado;
	private AdministradorDTO administradorLogueado;
	
	
	public ControladorLogin(VentanaLogin ventanaLogin, Login login){
		this.ventanaLogin = ventanaLogin;
		this.vistaAdministrador = VistaAdministrador.getInstance();
		this.vistaAdministrativo = VistaAdministrativo.getInstance();
		this.vistaCliente = VistaCliente.getInstance();
		
		this.login = login;
		this.usuarioLogueado = null;
		this.administradorLogueado = null;
		this.clienteLogueado = null;
	
		this.ventanaLogin.getBtnLogin().addActionListener(log->loguearse(log));
	}

	public void iniciar(){
		this.ventanaLogin.mostrarVentana(true);
	}
	
	private void loguearse(ActionEvent log) {
		String usuario = ventanaLogin.getTextUsuario().getText();
		String password = new String(ventanaLogin.getPasswordField().getPassword());
		try {
			usuarioLogueado = login.getLoginByDatos(usuario, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(usuarioLogueado != null) {
			if( usuarioLogueado.getEstado().equals("Activo") ) {
				System.out.println("SE LOGEO CORRECTAMENTE CON:"+ usuarioLogueado.getUsuario()+usuarioLogueado.getContrasena());
				if(usuarioLogueado.getRol().getIdRol()==2){
					 administrativoLogueado = obtenerAdministrativo(usuarioLogueado);
					 mostrarVentanaAdministrativo();
	
				}else{
					if(usuarioLogueado.getRol().getIdRol()==5){
						 clienteLogueado = obtenerCliente(usuarioLogueado);
						 mostrarVentanaCliente();
					}
					else{
						if(usuarioLogueado.getRol().getIdRol()==1){
							 administradorLogueado = obtenerAdministrador(usuarioLogueado);
							 mostrarVentanaAdministrador();
						}
					}
				}
			}
			else{
				this.ventanaLogin.getLblErrorInactividad().setVisible(true);
				System.out.println("EL USUARIO ESTA INACTIVO");
			}
		}else{
			this.ventanaLogin.getLblError().setVisible(true);
			System.out.println("EL USUARIO O CONTRASENA ES INCORRECTO");
		}
	}
	
	/*Mostrar la ventana principal del cliente*/
	private void mostrarVentanaCliente() {
		System.out.println("Se Loguea como Cliente");
		System.out.println(clienteLogueado.getNombre());
		this.ventanaLogin.setVisible(false);	
		ControladorUsuario controladorUsuario = new ControladorUsuario(vistaCliente,clienteLogueado);
		controladorUsuario.inicializar();
	}

	/*Mostrar la ventana principal del personal administrativo*/
	private void mostrarVentanaAdministrativo() {
		System.out.println("Se Loguea Como Administrativo");
		System.out.println(administrativoLogueado.getNombre());
		this.ventanaLogin.setVisible(false);
		ControladorAdministrativo controladorPrueba = new ControladorAdministrativo(vistaAdministrativo,administrativoLogueado);
		controladorPrueba.inicializar();
	}
	
	private void mostrarVentanaAdministrador() {
		System.out.println("Se Loguea Como Administrador");
//		System.out.println(administradorLogueado.getNombre());
		this.ventanaLogin.setVisible(false);
		ControladorAdministrador controladorAdministrador = new ControladorAdministrador(vistaAdministrador);
		controladorAdministrador.inicializar();
	}
		
		
/*-----------------------METODOS BUSCADOR POR ROLES ---------------------*/
	private AdministrativoDTO obtenerAdministrativo(LoginDTO loginUsuario) {
		Administrativo administrativo = new Administrativo(new DAOSQLFactory());
		return administrativo.getByLoginId(loginUsuario.getIdDatosLogin());
	}
	
	private AdministradorDTO obtenerAdministrador(LoginDTO loginUsuario) {
		Administrador administrador = new Administrador(new DAOSQLFactory());
		return administrador.getByLoginId(loginUsuario.getIdDatosLogin());
	}
	
	private ClienteDTO obtenerCliente(LoginDTO loginUsuario) {
		Cliente cliente = new Cliente(new DAOSQLFactory());
		return cliente.getByLoginId(loginUsuario.getIdDatosLogin());
	}
}