package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.UUID;

import correo.EnvioDeCorreo;
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
import presentacion.vista.cliente.VistaCliente;

public class ControladorLogin {

	private VentanaLogin ventanaLogin;
	private VentanaClaveOlvidada ventanaClaveOlvidada;
	private VistaAdministrativo vistaAdministrativo;
	private EnvioDeCorreo envioDeCorreo;
	private VistaCliente vistaCliente;
	private Login login;
	private LoginDTO usuarioLogueado;
	private Cliente modeloCliente;
	private Administrador modeloAdministrador;
	private AdministrativoDTO administrativoLogueado;
	private ClienteDTO clienteLogueado;
	private AdministradorDTO administradorLogueado;
	private VistaAdministrador vistaAdministrador;
	private String mailDeRecuperacion;
	private String contrasenaProvisoria;
	private MedioContacto modeloMedioContacto;
	private Administrativo modeloAdministrativo;
	private Integer idMedioContactoBuscado;
	
	public ControladorLogin(VentanaLogin ventanaLogin, Login login){
		this.ventanaLogin = ventanaLogin;
		this.vistaAdministrador = VistaAdministrador.getInstance();
		this.ventanaClaveOlvidada = VentanaClaveOlvidada.getInstance();
		this.modeloMedioContacto = new MedioContacto(new DAOSQLFactory());
		this.modeloAdministrativo = new Administrativo(new DAOSQLFactory());
		this.modeloCliente = new Cliente(new DAOSQLFactory());
		this.modeloAdministrador = new Administrador(new DAOSQLFactory());
	
		this.login = login;
		this.usuarioLogueado = null;
		this.administradorLogueado = null;
		this.mailDeRecuperacion = null;
		this.contrasenaProvisoria = null;
		this.idMedioContactoBuscado = null;
		this.envioDeCorreo = new EnvioDeCorreo();
		
		this.vistaAdministrativo = new VistaAdministrativo(); //cambiar esto por getInstance() 
		this.vistaCliente = VistaCliente.getInstance();
		
		this.login = login;
		this.usuarioLogueado = null;
		this.administradorLogueado = null;
		this.clienteLogueado = null;
	
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
		obtenerDatosRecuperacionDeContrasena();
		enviarContrasenaViaMail();
		guardarNuevaContrasenaEnDB();
// VOLVER A PAGINA DE INICIO ?
	}
	
	private void obtenerDatosRecuperacionDeContrasena() {
		this.mailDeRecuperacion = this.ventanaClaveOlvidada.getTextUsuario().getText();
		this.contrasenaProvisoria = obtenerContrasenaProvisoria();
	}
	public boolean buscarPersonalAsociadoAlEmail(){
		AdministrativoDTO administrativo = modeloAdministrativo.buscarPorEmail(mailDeRecuperacion);
		if(administrativo!=null){
			administrativo.getDatosLogin().setContrasena(contrasenaProvisoria);
			//TODO: modeloAdministrativo.actualizar(administrativo)
			return true;
		}
	
		AdministradorDTO administrador = modeloAdministrador.buscarPorEmail(mailDeRecuperacion);
		if(administrador!=null){
			administrador.getDatosLogin().setContrasena(contrasenaProvisoria);
			//TODO: modeloAdministrador.actualizar(administrador)
			return true;
		}
		
		ClienteDTO cliente = modeloCliente.buscarPorEmail(mailDeRecuperacion);
		if(cliente!=null){
			cliente.getLogin().setContrasena(contrasenaProvisoria);
			//TODO: modeloCliente.actualizar(cliente)
			return true;
		}
		
		/*TODO:
		CoordinadirDTO coordinador = modeloCoordinador.buscarPorEmail(mailDeRecuperacion);
		if(coordinador!=null){
			coordinador.getLogin().setContrasena(contrasenaProvisoria);
			//TODO: modeloCoordinador.actualizar(coordinador)
			return true;
		}
		*/
		
		return false;
	}
	
//	private int obtenerIdContacto(String mailDeRecuperacion2, String contrasenaProvisoria2) {
//		Integer idContacto = null;
//		ArrayList<MedioContactoDTO> medios = (ArrayList<MedioContactoDTO>) this.modeloMedioContacto.obtenerMediosContacto();
//		for(MedioContactoDTO m: medios){
//			if(m.getEmail().equals(mailDeRecuperacion2))
//				idContacto = m.getIdMedioContacto();
//		}
//		return idContacto;
//	}

	private void enviarContrasenaViaMail() {
		this.envioDeCorreo.enviarNuevaContrasena(mailDeRecuperacion, contrasenaProvisoria);
		this.ventanaClaveOlvidada.setVisible(false);
	}
	
	private void guardarNuevaContrasenaEnDB() {
		ClienteDTO clienteBuscado = this.modeloCliente.getByIdContacto(idMedioContactoBuscado);
		if(clienteBuscado!=null){
			clienteBuscado.getLogin().setContrasena(contrasenaProvisoria);
			modeloCliente.actualizar(clienteBuscado);
		}
	}
	
	private String obtenerContrasenaProvisoria() {
		return UUID.randomUUID().toString().toUpperCase().substring(0, 8);
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