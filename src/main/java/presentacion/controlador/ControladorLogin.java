package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.UUID;

import correo.CorreoTexto;
import dto.AdministradorDTO;
import dto.AdministrativoDTO;
import dto.ClienteDTO;
import dto.LoginDTO;
import dto.MedioContactoDTO;
import modelo.Administrador;
import modelo.Administrativo;
import modelo.Cliente;
import modelo.Login;
import modelo.MedioContacto;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.VentanaClaveOlvidada;
import presentacion.vista.VentanaLogin;
import presentacion.vista.administrador.VistaAdministrador;
import presentacion.vista.administrativo.VistaAdministrativo;

public class ControladorLogin {

	private VentanaLogin ventanaLogin;
	private VentanaClaveOlvidada ventanaClaveOlvidada;
	private VistaAdministrativo vistaAdministrativo;
	private CorreoTexto envioDeCorreo;
	private Login login;
	private LoginDTO usuarioLogueado;
	private Cliente modeloCliente;
	private AdministrativoDTO administrativoLogueado;
	private ClienteDTO clienteLogueado;
	private AdministradorDTO administradorLogueado;
	private VistaAdministrador vistaAdministrador;
	private String mailDeRecuperacion;
	private String contrasenaProvisoria;
	private MedioContacto modeloMedioContacto;
	private Integer idMedioContactoBuscado;
	
	public ControladorLogin(VentanaLogin ventanaLogin, Login login){
		this.ventanaLogin = ventanaLogin;
		this.vistaAdministrativo = new VistaAdministrativo(); //cambiar esto por getInstance() 
		this.vistaAdministrador = VistaAdministrador.getInstance();
		this.ventanaClaveOlvidada = VentanaClaveOlvidada.getInstance();
		this.modeloMedioContacto = new MedioContacto(new DAOSQLFactory());
		this.modeloCliente = new Cliente(new DAOSQLFactory());
	
		this.login = login;
		this.usuarioLogueado = null;
		this.administradorLogueado = null;
		this.mailDeRecuperacion = null;
		this.contrasenaProvisoria = null;
		this.idMedioContactoBuscado = null;
		this.envioDeCorreo = new CorreoTexto();
		
		this.ventanaLogin.getBtnLogin().addActionListener(log->loguearse(log));
		this.ventanaClaveOlvidada.getBtnRecuperarContraseña().addActionListener(e->realizarCambioContraseña(e));;
		
		
		
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
	
// CONTROLAR QUE EL SERVICIO DE MAIL FUNCIONE CORRECTAMENTE??????
	
	private void realizarCambioContraseña(ActionEvent e){
		if(mailEsValido()){
		enviarContrasenaViaMail();
		guardarNuevaContraseñaEnDB();
		}
	}
	
	private boolean mailEsValido() {
		this.mailDeRecuperacion = this.ventanaClaveOlvidada.getTextUsuario().getText();
		this.idMedioContactoBuscado = obtenerIdContacto(mailDeRecuperacion,contrasenaProvisoria);
		return idMedioContactoBuscado!=null;
	}
	
	private int obtenerIdContacto(String mailDeRecuperacion2, String contrasenaProvisoria2) {
		Integer idContacto = null;
		ArrayList<MedioContactoDTO> medios = (ArrayList<MedioContactoDTO>) this.modeloMedioContacto.obtenerMediosContacto();
		for(MedioContactoDTO m: medios){
			if(m.getEmail().equals(mailDeRecuperacion2))
				idContacto = m.getIdMedioContacto();
		}
		return idContacto;
	}

	private void enviarContrasenaViaMail() {
		this.contrasenaProvisoria = obtenerContrasenaProvisoria();
		this.envioDeCorreo.enviarCorreo(mailDeRecuperacion, contrasenaProvisoria);
		this.ventanaClaveOlvidada.setVisible(false);
	}
	
	private void guardarNuevaContraseñaEnDB() {
		ClienteDTO clienteBuscado = buscarMedioContactoEnClientes(idMedioContactoBuscado);
		if(clienteBuscado!=null){
			System.out.println("CLIENTE ENCONTRADO, PROCEDO A CAMBIAR LA CONTRASENA");
			clienteBuscado.getLogin().setContrasena(contrasenaProvisoria);
			modeloCliente.actualizar(clienteBuscado);
		}
	}
	
	private ClienteDTO buscarMedioContactoEnClientes(int idContacto){
		return this.modeloCliente.getByIdContacto(idContacto);
	}

	private String obtenerContrasenaProvisoria() {
		return UUID.randomUUID().toString().toUpperCase().substring(0, 8);
	}
	
	private void pedirMail(){
		this.ventanaClaveOlvidada.setVisible(true);
		this.ventanaLogin.mostrarVentana(false);
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
	}
	
	/*Mostrar la ventana principal del cliente*/
	private void mostrarVentanaCliente() {
		System.out.println("Se Loguea como Cliente");
		System.out.println(clienteLogueado.getNombre());
		this.ventanaLogin.setVisible(false);	
	}

	/*Mostrar la ventana principal del personal administrativo*/
	private void mostrarVentanaAdministrativo() {
		System.out.println("Se Loguea Como Administrativo");
		System.out.println(administrativoLogueado.getNombre());
		this.ventanaLogin.setVisible(false);
		ControladorPrueba controladorPrueba = new ControladorPrueba(vistaAdministrativo,administrativoLogueado);
		controladorPrueba.inicializar();
	}
	
	private void mostrarVentanaAdministrador() {
		System.out.println("Se Loguea Como Administrador");
		System.out.println(administradorLogueado.getNombre());
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