package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Cliente;
import modelo.Login;
import modelo.Pasaje;
import dto.ClienteDTO;
import dto.LoginDTO;
import dto.PasajeDTO;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.VentanaReserva;
import presentacion.vista.cliente.VentanaCambiarContrasena;
import presentacion.vista.cliente.VentanaReservas;
import presentacion.vista.cliente.VentanaViajes;
import presentacion.vista.cliente.VentanaVisualizarDatos;
import presentacion.vista.cliente.VistaCliente;

public class ControladorUsuario implements ActionListener {

	private VistaCliente vistaCliente;
	private ClienteDTO cliente;
	private Pasaje pasaje;
	private Login login;
	private List<PasajeDTO> pasajes_en_tabla;
	private VentanaViajes ventanaViajes;
	private VentanaReservas ventanaReservas;
	private VentanaVisualizarDatos ventanaVisualizarDatos;
	private VentanaCambiarContrasena ventanaCambiarContrasenia;
	
	public ControladorUsuario(VistaCliente vistaCliente, ClienteDTO cliente){
		this.vistaCliente = vistaCliente;
		this.cliente = cliente;
		
		this.ventanaReservas = VentanaReservas.getInstance();
		this.ventanaViajes = VentanaViajes.getInstance();
		this.ventanaVisualizarDatos = VentanaVisualizarDatos.getInstance();
		this.ventanaCambiarContrasenia = VentanaCambiarContrasena.getInstance();
		
		this.vistaCliente.getItemVisualizarReservas().addActionListener(vr->mostrarVentanaReservas(vr));
		this.vistaCliente.getItemVisualizarViajesHistoricos().addActionListener(vr->mostrarVentanaViajes(vr));
		this.vistaCliente.getItemVisualizarDatos().addActionListener(vd->mostrarVentanaVisualizarDatos(vd));
		this.vistaCliente.getItemCambiarContrasenia().addActionListener(c->mostrarVentanaCambiarContrasenia(c));
	
		this.ventanaReservas.getBtnAceptar().addActionListener(vd->cerrarVentanaReserva(vd));
		this.ventanaViajes.getBtnAceptar().addActionListener(vd->cerrarVentanaViajes(vd));
		this.ventanaVisualizarDatos.getBtnAceptar().addActionListener(vd->cerrarVentana(vd));
		this.ventanaCambiarContrasenia.getBtnAceptar().addActionListener(c->cambiarContrasenia(c));
		this.ventanaCambiarContrasenia.getBtnCancelar().addActionListener(c->salirVentanaCambiarContrasenia(c));

		this.login = new Login(new DAOSQLFactory());
		this.pasaje = new Pasaje(new DAOSQLFactory());
	}


	public void inicializar(){
		this.vistaCliente.mostrarVentana();
		JOptionPane.showMessageDialog(null, "Bienvenido al sistema de la empresa de turismo Al Planeta", "Bienvenido"+ " "+cliente.getNombre(), JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void mostrarVentanaReservas(ActionEvent vr) {
		this.ventanaReservas.mostrarVentana(true);
		llenarTablaReservas();
		
	}
	
	private void cerrarVentanaReserva(ActionEvent vd) {
		this.ventanaReservas.mostrarVentana(false);
	}
	
	private void cerrarVentanaViajes(ActionEvent vd) {
		this.ventanaViajes.mostrarVentana(false);

	}

	private void mostrarVentanaViajes(ActionEvent vr) {
		this.ventanaViajes.mostrarVentana(true);
		llenarTablaViajes();
	}
	
	private void mostrarVentanaVisualizarDatos(ActionEvent vd) {
		this.ventanaVisualizarDatos.mostrarVentana(true);
		this.ventanaVisualizarDatos.getLblNombreDelCliente().setText(cliente.getNombre());
		this.ventanaVisualizarDatos.getLblApellidoDelCliente().setText(cliente.getApellido());
		this.ventanaVisualizarDatos.getLblDniDelCliente().setText(cliente.getDni());
		this.ventanaVisualizarDatos.getLblFechaNacimientoDelCliente().setText(cliente.getFechaNacimiento().toString());
		this.ventanaVisualizarDatos.getLblNumeroFijoDelCliente().setText(cliente.getMedioContacto().getTelefonoFijo());
		this.ventanaVisualizarDatos.getLblNumeroCelularDelCliente().setText(cliente.getMedioContacto().getTelefonoCelular());
		this.ventanaVisualizarDatos.getLblEmailDelCliente().setText(cliente.getMedioContacto().getEmail());
	}

	private void cerrarVentana(ActionEvent vd) {
		this.ventanaVisualizarDatos.mostrarVentana(false);
	}

	
	private void mostrarVentanaCambiarContrasenia(ActionEvent c) {
		this.ventanaCambiarContrasenia.mostrarVentana(true);
		
	}
	
	private void cambiarContrasenia(ActionEvent c) {
		String passwordActual = new String(this.ventanaCambiarContrasenia.getPassActual().getPassword());
		if(!passwordActual.equals(cliente.getLogin().getContrasena())){
			JOptionPane.showMessageDialog(null, "La contraseña actual es incorrecta", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}else{
			LoginDTO loginDTO = new LoginDTO();
			loginDTO.setIdDatosLogin(cliente.getLogin().getIdDatosLogin());
			loginDTO.setUsuario(cliente.getLogin().getUsuario());
			String password = new String(this.ventanaCambiarContrasenia.getPassNueva().getPassword());
			loginDTO.setContrasena(password);
			this.login.editarLogin(loginDTO);
			this.ventanaCambiarContrasenia.mostrarVentana(false);
			JOptionPane.showMessageDialog(null, "Contraseña actualizada", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void salirVentanaCambiarContrasenia(ActionEvent c) {
		this.ventanaCambiarContrasenia.dispose();
	}


	public void llenarTablaReservas(){
		this.ventanaReservas.getModelReservas().setRowCount(0); //Para vaciar la tabla
		this.ventanaReservas.getModelReservas().setColumnCount(0);
		this.ventanaReservas.getModelReservas().setColumnIdentifiers(this.ventanaReservas.getColumnasReservas());
			
		this.pasajes_en_tabla = pasaje.obtenerPasajes();
		
		for (int i = 0; i < this.pasajes_en_tabla .size(); i++){
			int idClienteReserva = pasajes_en_tabla.get(i).getCliente().getIdCliente();
			if (idClienteReserva == cliente.getIdCliente() &&
					pasajes_en_tabla.get(i).getEstadoDelPasaje().getIdEstadoPasaje()== 2){
				
			Object[] fila = {
					this.pasajes_en_tabla.get(i).getViaje().getPaisOrigen().getNombre()+ ","+ 
					this.pasajes_en_tabla.get(i).getViaje().getCiudadOrigen().getNombre(),
					this.pasajes_en_tabla.get(i).getViaje().getPaisDestino().getNombre()+ ","+ 
					this.pasajes_en_tabla.get(i).getViaje().getCiudadDestino().getNombre(),
					this.pasajes_en_tabla.get(i).getFechaVencimiento(),
					this.pasajes_en_tabla.get(i).getViaje().getFechaSalida(),
					this.pasajes_en_tabla.get(i).getViaje().getFechaLlegada(),
					this.pasajes_en_tabla.get(i).getViaje().getHoraSalida(),
					this.pasajes_en_tabla.get(i).getViaje().getTransporte().getNombre()
			};
			this.ventanaReservas.getModelReservas().addRow(fila);
		}}		
	}
	
	public void llenarTablaViajes(){
		this.ventanaViajes.getModelViajes().setRowCount(0); //Para vaciar la tabla
		this.ventanaViajes.getModelViajes().setColumnCount(0);
		this.ventanaViajes.getModelViajes().setColumnIdentifiers(this.ventanaViajes.getColumnasViajes());
			
		this.pasajes_en_tabla = pasaje.obtenerPasajes();
		
		for (int i = 0; i < this.pasajes_en_tabla .size(); i++){
			int idClienteReserva = pasajes_en_tabla.get(i).getCliente().getIdCliente();
			if (idClienteReserva == cliente.getIdCliente() &&
					pasajes_en_tabla.get(i).getEstadoDelPasaje().getIdEstadoPasaje()== 1){
				
			Object[] fila = {
					this.pasajes_en_tabla.get(i).getViaje().getPaisOrigen().getNombre()+ ","+ 
					this.pasajes_en_tabla.get(i).getViaje().getCiudadOrigen().getNombre(),
					this.pasajes_en_tabla.get(i).getViaje().getPaisDestino().getNombre()+ ","+ 
					this.pasajes_en_tabla.get(i).getViaje().getCiudadDestino().getNombre(),
					this.pasajes_en_tabla.get(i).getViaje().getFechaSalida(),
					this.pasajes_en_tabla.get(i).getViaje().getFechaLlegada(),
					this.pasajes_en_tabla.get(i).getViaje().getTransporte().getNombre(),
					this.pasajes_en_tabla.get(i).getViaje().getPrecio()
			};
			this.ventanaViajes.getModelViajes().addRow(fila);
		}}		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}