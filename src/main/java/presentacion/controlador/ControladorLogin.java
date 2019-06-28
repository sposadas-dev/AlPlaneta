package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.UUID;

import javax.swing.JOptionPane;

import correo.EnvioDeCorreo;
import dto.AdministradorDTO;
import dto.AdministrativoDTO;
import dto.ClienteDTO;
import dto.ContadorDTO;
import dto.CoordinadorDTO;
import dto.LoginDTO;
import modelo.Administrador;
import modelo.Administrativo;
import modelo.Cliente;
import modelo.Contador;
import modelo.Coordinador;
import modelo.Login;
import modelo.MedioContacto;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.VentanaClaveOlvidada;
import presentacion.vista.VentanaLogin;
import presentacion.vista.administrador.VistaAdministrador;
import presentacion.vista.administrativo.VistaAdministrativo;
import presentacion.vista.cliente.VistaCliente;
import presentacion.vista.contador.VistaContador;
import presentacion.vista.coordinador.VistaCoordinador;

public class ControladorLogin {

	private VentanaLogin ventanaLogin;
	private VentanaClaveOlvidada ventanaClaveOlvidada;
	private EnvioDeCorreo envioDeCorreo;
	private VistaAdministrador vistaAdministrador;
	private VistaAdministrativo vistaAdministrativo;
	private VistaCoordinador vistaCoordinador;
	private VistaContador vistaContador;
	private Coordinador modeloCoordinador;
	private Contador modeloContador;
	private VistaCliente vistaCliente;
	private Login modeloLogin;
	private LoginDTO usuarioLogueado;
	private Cliente modeloCliente;
	private Administrador modeloAdministrador;
	private AdministrativoDTO administrativoLogueado;
	private ClienteDTO clienteLogueado;
	private AdministradorDTO administradorLogueado;
	private CoordinadorDTO coordinadorLogueado;
	private ContadorDTO contadorLogueado;
	
	private String mailDeRecuperacion;
	private String contrasenaProvisoria;
	private MedioContacto modeloMedioContacto;
	private Administrativo modeloAdministrativo;
	private Integer idMedioContactoBuscado;

	
	public ControladorLogin(VentanaLogin ventanaLogin, Login login){
		this.ventanaLogin = ventanaLogin;
		this.vistaAdministrador = VistaAdministrador.getInstance();
		this.vistaAdministrativo = VistaAdministrativo.getInstance();
		
		this.vistaCoordinador = VistaCoordinador.getInstance();
		this.vistaContador = VistaContador.getInstance();
		
		this.ventanaClaveOlvidada = VentanaClaveOlvidada.getInstance();
		this.modeloMedioContacto = new MedioContacto(new DAOSQLFactory());
		this.modeloAdministrativo = new Administrativo(new DAOSQLFactory());
		this.modeloCliente = new Cliente(new DAOSQLFactory());
		this.modeloAdministrador = new Administrador(new DAOSQLFactory());
		this.modeloCoordinador = new Coordinador(new DAOSQLFactory());
		this.modeloContador = new Contador(new DAOSQLFactory());
	
		this.modeloLogin = login;
		this.usuarioLogueado = null;
		this.mailDeRecuperacion = null;
		this.contrasenaProvisoria = null;
		this.idMedioContactoBuscado = null;
		this.envioDeCorreo = new EnvioDeCorreo();

		this.vistaCliente = VistaCliente.getInstance();
		
		this.modeloLogin = login;
		this.usuarioLogueado = null;
		this.administradorLogueado = null;
		this.clienteLogueado = null;
		this.coordinadorLogueado = null;
	
		this.ventanaLogin.getBtnLogin().addActionListener(log->loguearse(log));
		this.ventanaClaveOlvidada.getBtnRecuperarContrasena().addActionListener(e->realizarCambioContrasena(e));;
		
		this.ventanaLogin.getLblClaveOlvidada().addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				pedirMail();
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		});		
		
	}

	public void iniciar(){
		this.ventanaLogin.mostrarVentana(true);
	}
	
	private void pedirMail(){
		this.ventanaClaveOlvidada.setVisible(true);
		this.ventanaLogin.mostrarVentana(false);
	}
	
// CONTROLAR QUE EL SERVICIO DE MAIL FUNCIONE CORRECTAMENTE??????
	private void realizarCambioContrasena(ActionEvent e){
		obtenerDatosDeRecuperacion();
		if(cambioDeContrasena()){
			System.out.println("Se cambio la contrasena");
			enviarContrasenaViaMail();
			JOptionPane.showMessageDialog(null, "Se le ha enviado la nueva contraseña al mail", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
			this.ventanaLogin.setVisible(false);
		}
	}
	
	private void obtenerDatosDeRecuperacion() {
		this.mailDeRecuperacion = this.ventanaClaveOlvidada.getTextUsuario().getText();
		this.contrasenaProvisoria = obtenerContrasenaProvisoria();
	}
	
	public boolean cambioDeContrasena(){
		AdministrativoDTO administrativo = modeloAdministrativo.buscarPorEmail(mailDeRecuperacion);
		if(administrativo!=null){
			System.out.println("Usuario es:" + administrativo.getNombre());
			administrativo.getDatosLogin().setContrasena(contrasenaProvisoria);
			modeloLogin.editarLogin(administrativo.getDatosLogin());
			return true;
		}
	
		AdministradorDTO administrador = modeloAdministrador.buscarPorEmail(mailDeRecuperacion);
		if(administrador!=null){
			System.out.println("Usuario es:" + administrador);
			administrador.getDatosLogin().setContrasena(contrasenaProvisoria);
			modeloLogin.editarLogin(administrador.getDatosLogin());
			return true;
		}
		
		ClienteDTO cliente = modeloCliente.buscarPorEmail(mailDeRecuperacion);
		if(cliente!=null){ 	
			System.out.println("Usuario es:" + cliente);
			cliente.getLogin().setContrasena(contrasenaProvisoria);
			modeloLogin.editarLogin(cliente.getLogin());
			return true;
		}
		
		CoordinadorDTO coordinador = modeloCoordinador.buscarPorEmail(mailDeRecuperacion);
		if(coordinador!=null){
			coordinador.getDatosLogin().setContrasena(contrasenaProvisoria);
			modeloLogin.editarLogin(coordinador.getDatosLogin());
			return true;
		}
		
		ContadorDTO contador= modeloContador.buscarPorEmail(mailDeRecuperacion);
		if(contador!=null){
			contador.getDatosLogin().setContrasena(contrasenaProvisoria);
			modeloLogin.editarLogin(contador.getDatosLogin());
			return true;
		}
		
		return false;
	}
	
	private void enviarContrasenaViaMail() {
		this.envioDeCorreo.enviarNuevaContrasena(mailDeRecuperacion, contrasenaProvisoria,"Recuperación de contraseña");
		this.ventanaClaveOlvidada.setVisible(false);
	}
	
	private String obtenerContrasenaProvisoria() {
		return UUID.randomUUID().toString().toUpperCase().substring(0, 8);
	}
	//TODO: Realizar el logueo de Contador.
	private void loguearse(ActionEvent log) {
		String usuario = ventanaLogin.getTextUsuario().getText();
		String password = new String(ventanaLogin.getPasswordField().getPassword());
		try {
			usuarioLogueado = modeloLogin.getLoginByDatos(usuario, password);
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
					}	else
						if(usuarioLogueado.getRol().getIdRol()==3){
							 coordinadorLogueado = obtenerCoordinador(usuarioLogueado);
							 mostrarVentanaCoordinador();
						}else if(usuarioLogueado.getRol().getIdRol()==4){
							contadorLogueado = obtenerContador(usuarioLogueado);
							mostrarVentanaContador();
						}
				}
			}
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
	
	/*Mostrar la ventana principal del coordinador*/
	private void mostrarVentanaCoordinador() {
		System.out.println("Se Loguea Como Coordinador");
		System.out.println(coordinadorLogueado.getNombre());
		this.ventanaLogin.setVisible(false);
		ControladorCOOR controladorCoordinador = new ControladorCOOR(vistaCoordinador,coordinadorLogueado);
		controladorCoordinador.inicializar();
	}
	
	private void mostrarVentanaContador() {
		System.out.println("Se Loguea Como Contador");
//		System.out.println(administradorLogueado.getNombre());
		this.ventanaLogin.setVisible(false);
		ControladorContador controladorContador = new ControladorContador(vistaContador, contadorLogueado);
		controladorContador.inicializar();
	}
	private void mostrarVentanaAdministrador() {
		System.out.println("Se Loguea Como Administrador");
//		System.out.println(administradorLogueado.getNombre());
		this.ventanaLogin.setVisible(false);
		ControladorAdministrador controladorAdministrador = new ControladorAdministrador(vistaAdministrador,administradorLogueado);
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
	
	private CoordinadorDTO obtenerCoordinador(LoginDTO loginUsuario) {
		Coordinador coordinador = new Coordinador(new DAOSQLFactory());
		return coordinador.getByLoginId(loginUsuario.getIdDatosLogin());
	}
	
	private ContadorDTO obtenerContador(LoginDTO loginUsuario) {
		Contador contador = new Contador(new DAOSQLFactory());
		return contador.getByLoginId(loginUsuario.getIdDatosLogin());
	}
		
	private ClienteDTO obtenerCliente(LoginDTO loginUsuario) {
		Cliente modeloCliente = new Cliente(new DAOSQLFactory());
		return modeloCliente.getByLoginId(loginUsuario.getIdDatosLogin());
	}
}