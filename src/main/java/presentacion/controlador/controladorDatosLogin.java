package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.util.UUID;

import javax.swing.JOptionPane;

import correo.EnvioDeCorreo;
import dto.AdministradorDTO;
import dto.AdministrativoDTO;
import dto.ClienteDTO;
import dto.CoordinadorDTO;
import modelo.Administrador;
import modelo.Administrativo;
import modelo.Cliente;
import modelo.Coordinador;
import modelo.Login;
import modelo.MedioContacto;
import persistencia.dao.interfaz.AdministradorDAO;
import persistencia.dao.interfaz.AdministrativoDAO;
import persistencia.dao.interfaz.CiudadDAO;
import persistencia.dao.interfaz.ClienteDAO;
import persistencia.dao.interfaz.CoordinadorDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.EstadoEventoDAO;
import persistencia.dao.interfaz.EstadoPasajeDAO;
import persistencia.dao.interfaz.EventoDAO;
import persistencia.dao.interfaz.FormaPagoDAO;
import persistencia.dao.interfaz.LoginDAO;
import persistencia.dao.interfaz.MedioContactoDAO;
import persistencia.dao.interfaz.PagoDAO;
import persistencia.dao.interfaz.Pagos_PasajeDAO;
import persistencia.dao.interfaz.PaisDAO;
import persistencia.dao.interfaz.PasajeDAO;
import persistencia.dao.interfaz.Pasaje_PasajerosDAO;
import persistencia.dao.interfaz.PasajeroDAO;
import persistencia.dao.interfaz.PromocionDAO;
import persistencia.dao.interfaz.ProvinciaDAO;
import persistencia.dao.interfaz.PuntoDAO;
import persistencia.dao.interfaz.RolDAO;
import persistencia.dao.interfaz.TransporteDAO;
import persistencia.dao.interfaz.ViajeDAO;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.VentanaClaveOlvidada;

public class controladorDatosLogin {
	private VentanaClaveOlvidada ventanaClaveOlvidada;
	private String mailRecuperacion;
	private String mailDeRecuperacion;
	private String contrasenaProvisoria;
	private Administrador modeloAdministrador;
	private Login modeloLogin;
	private MedioContacto modeloMedioContacto;
	private Administrativo modeloAdministrativo;
	private Cliente modeloCliente;
	private Coordinador modeloCoordinador;
	private EnvioDeCorreo envioDeCorreo;

	public controladorDatosLogin() {
		this.ventanaClaveOlvidada = VentanaClaveOlvidada.getInstance();
		this.ventanaClaveOlvidada.getBtnRecuperarContrasena().addActionListener(r -> recuperarContrasena(r));

		this.envioDeCorreo = new EnvioDeCorreo();
		
		this.modeloLogin = new Login(new DAOSQLFactory());
		this.modeloAdministrador = new Administrador(new DAOSQLFactory());
		this.modeloMedioContacto = new MedioContacto(new DAOSQLFactory());
		this.modeloAdministrativo = new Administrativo(new DAOSQLFactory());
		this.modeloCliente = new Cliente(new DAOSQLFactory());
		this.modeloAdministrador = new Administrador(new DAOSQLFactory());
		this.modeloCoordinador = new Coordinador(new DAOSQLFactory());
	}

	public void restablecerContrasena() {
		this.ventanaClaveOlvidada.getTextUsuario().setText("");
		this.ventanaClaveOlvidada.setVisible(true);
	}

	private void recuperarContrasena(ActionEvent r) {
		obtenerDatosDeRecuperacion();
		if (cambioDeContrasena()) {
			System.out.println("Se cambio la contrasena");
			enviarContrasenaViaMail();
			JOptionPane.showMessageDialog(null, "Se le ha enviado la nueva contrasena al mail", "Mensaje",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void obtenerDatosDeRecuperacion() {
		this.mailDeRecuperacion = this.ventanaClaveOlvidada.getTextUsuario().getText();
		this.contrasenaProvisoria = obtenerContrasenaProvisoria();
	}

	private boolean cambioDeContrasena() {
			AdministrativoDTO administrativo = modeloAdministrativo.buscarPorEmail(mailDeRecuperacion);
			if(administrativo!=null){
				administrativo.getDatosLogin().setContrasena(contrasenaProvisoria);
				modeloLogin.editarLogin(administrativo.getDatosLogin());
				return true;
			}
		
			AdministradorDTO administrador = modeloAdministrador.buscarPorEmail(mailDeRecuperacion);
			if(administrador!=null){
				administrador.getDatosLogin().setContrasena(contrasenaProvisoria);
				modeloLogin.editarLogin(administrador.getDatosLogin());
				return true;
			}
			
			ClienteDTO cliente = modeloCliente.buscarPorEmail(mailDeRecuperacion);
			if(cliente!=null){ 	
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
			
			return false;
	}

	private void enviarContrasenaViaMail() {
		this.envioDeCorreo.enviarNuevaContrasena(mailDeRecuperacion, contrasenaProvisoria,"Recuperacion de Contrasena");
		this.ventanaClaveOlvidada.setVisible(false);
	}

	private String obtenerContrasenaProvisoria() {
		return UUID.randomUUID().toString().toUpperCase().substring(0, 8);
	}
}
