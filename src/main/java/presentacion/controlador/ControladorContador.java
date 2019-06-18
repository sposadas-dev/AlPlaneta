package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import modelo.Login;
import dto.ContadorDTO;
import dto.LoginDTO;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.contador.VentanaCambiarContrasena;
import presentacion.vista.contador.VistaContador;

public class ControladorContador implements ActionListener {

	private VistaContador vistaContador;
	private VentanaCambiarContrasena ventanaCambiarContrasenia;
	private ContadorDTO contadorLogueado;
	private Login login;

	public ControladorContador(VistaContador vistaContador,ContadorDTO contadorLogueado) {
	
		this.vistaContador = vistaContador;		
		this.contadorLogueado = contadorLogueado;
		this.ventanaCambiarContrasenia = VentanaCambiarContrasena.getInstance();
		
		this.vistaContador.getItemCambiarContrasenia().addActionListener(dp->mostrarVentanaCambiarContrasenia(dp));
		
		this.ventanaCambiarContrasenia.getBtnAceptar().addActionListener(c->cambiarContrasenia(c));
		this.ventanaCambiarContrasenia.getBtnCancelar().addActionListener(c->salirVentanaCambiarContrasenia(c));
		this.login = new Login(new DAOSQLFactory());
		this.contadorLogueado = contadorLogueado;

	}

	public void inicializar(){
		this.vistaContador.mostrarVentana();
		this.vistaContador.getMenuUsuarioLogueado().setText(contadorLogueado.getNombre()+" "+contadorLogueado.getApellido());

	}
	
	private void cambiarContrasenia(ActionEvent c) {
		
		String passwordActual = new String(this.ventanaCambiarContrasenia.getPassActual().getPassword());
		String passwordConfirmacion1 = new String(this.ventanaCambiarContrasenia.getPassNueva().getPassword());
		String passwordConfirmacion2 = new String(this.ventanaCambiarContrasenia.getConfirmacionContrasena().getPassword());
		
		System.out.println(passwordConfirmacion1+" "+passwordConfirmacion2);
		
		if(!passwordConfirmacion1.equals(passwordConfirmacion2)){
			JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden ", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
		else if(!passwordActual.equals(contadorLogueado.getDatosLogin().getContrasena())){
			JOptionPane.showMessageDialog(null, "La contraseña actual es incorrecta", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}else{
			LoginDTO loginDTO = new LoginDTO();
			loginDTO.setIdDatosLogin(contadorLogueado.getDatosLogin().getIdDatosLogin());
			loginDTO.setUsuario(contadorLogueado.getDatosLogin().getUsuario());
			loginDTO.setRol(contadorLogueado.getDatosLogin().getRol());
			loginDTO.setEstado(contadorLogueado.getDatosLogin().getEstado());
		
			String password = new String(this.ventanaCambiarContrasenia.getPassNueva().getPassword());
			loginDTO.setContrasena(password);
			this.login.editarLogin(loginDTO);
			this.ventanaCambiarContrasenia.mostrarVentana(false);
			this.ventanaCambiarContrasenia.limpiarCampos();
			JOptionPane.showMessageDialog(null, "Contraseña actualizada", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
		}
	}
		private void mostrarVentanaCambiarContrasenia(ActionEvent dp) {
			this.ventanaCambiarContrasenia.limpiarCampos();
			this.ventanaCambiarContrasenia.mostrarVentana(true);
	}
	
		private void salirVentanaCambiarContrasenia(ActionEvent c) {
			this.ventanaCambiarContrasenia.limpiarCampos();
			this.ventanaCambiarContrasenia.mostrarVentana(false);;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
    }
}