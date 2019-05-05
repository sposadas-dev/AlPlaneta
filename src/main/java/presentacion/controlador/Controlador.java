package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import javax.swing.JOptionPane;
import modelo.Cliente;
import modelo.MedioContacto;
import dto.ClienteDTO;
import dto.MedioContactoDTO;
import dto.PasajeDTO;
import dto.ViajeDTO;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.VentanaCargaPasajero;
import presentacion.vista.VentanaCliente;
import presentacion.vista.VentanaFormaPago;
import presentacion.vista.VentanaPagoEfectivo;
import presentacion.vista.VentanaPagoTarjeta;
import presentacion.vista.VentanaPasajero;
import presentacion.vista.VentanaReserva;
import presentacion.vista.Vista;

public class Controlador implements ActionListener {

	private Vista vista;
	private VentanaCliente ventanaCliente;
	private VentanaReserva ventanaReserva;
	private VentanaFormaPago ventanaFormaDePagos;
	private VentanaPagoTarjeta ventanaPagoTarjeta;
	private VentanaPagoEfectivo ventanaPagoEfectivo;
	private VentanaCargaPasajero ventanaCargaPasajero;
	private VentanaPasajero ventanaPasajero;
	
	public Controlador(Vista vista) {
		this.vista = vista;
		this.vista.getBtnClientes().addActionListener(panelClientes->agregarTabla(panelClientes));
		this.vista.getBtnPasajes().addActionListener(panelReservas->agregarTablaPasaje(panelReservas));
		this.vista.getBtnAgregarCliente().addActionListener(ag->agregarCliente(ag));
		
		this.ventanaReserva = VentanaReserva.getInstance();
		this.ventanaFormaDePagos = VentanaFormaPago.getInstance();
		this.ventanaPagoTarjeta = VentanaPagoTarjeta.getInstance();
		this.ventanaPagoEfectivo = VentanaPagoEfectivo.getInstance();
		this.ventanaPasajero = VentanaPasajero.getInstance();
		
		this.ventanaCliente = VentanaCliente.getInstance();
		this.ventanaCliente.getBtnRegistrar().addActionListener(client->cargarCliente(client));

		this.ventanaCargaPasajero = VentanaCargaPasajero.getInstance();
		this.ventanaReserva.getBtnReservar().addActionListener(reserv->SeleccionFormaDePago(reserv));
		this.ventanaReserva.getBtnCargaPasajeros().addActionListener(cP->cargarPasajeros(cP));
//		this.ventanaFormaDePagos.get.addActionListener(pago->seleccionEstadoDelPago(pago));
		this.ventanaPagoEfectivo.getBtnRegistrarPago().addActionListener(rP->generarPasajeEfectivo(rP));
		this.ventanaPagoTarjeta.getBtnEnviar().addActionListener(rP->generarPasajeTarjeta(rP));
		this.ventanaCargaPasajero.getBtnAgregarPasajero().addActionListener(aP->agregarPasajero(aP));
	}
	
	private void agregarCliente(ActionEvent l) {
		ventanaCliente.setVisible(true);
	}
	
	private void agregarTablaPasaje(ActionEvent panelReservas) {
		this.vista.getPanelReservas().setVisible(true);
		this.vista.getPanelClientes().setVisible(false);
	}

	private void agregarTabla(ActionEvent panelClientes) {
		this.vista.getPanelClientes().setVisible(true);
		this.vista.getPanelReservas().setVisible(false);
	}
		
	private void cargarCliente(ActionEvent client) {
		MedioContacto medioContacto = new MedioContacto(new DAOSQLFactory());
		Cliente cliente = new Cliente(new DAOSQLFactory());

	/*Obtenemos la fecha de nacimiento , y la parseamos a tipo de date de SQL*/
		java.util.Date dateFechaNacimiento = ventanaCliente.getDateFechaNacimiento().getDate();
		java.sql.Date fechaNacimiento = new java.sql.Date(dateFechaNacimiento.getTime());
		
		/*Obtenemos el medio de contacto del cliente*/
		MedioContactoDTO mContacto = new MedioContactoDTO(0,this.ventanaCliente.getTxtTelefonoFijo().getText(),
				this.ventanaCliente.getTxtTelefonoCelular().getText(),
				this.ventanaCliente.getTxtEmail().getText()
		);
	
		ClienteDTO nuevoCliente = new ClienteDTO(0,
				this.ventanaCliente.getTxtNombre().getText(),
				this.ventanaCliente.getTxtApellido().getText(),
				this.ventanaCliente.getTxtDni().getText(),
				fechaNacimiento,
				mContacto);		
		
		medioContacto.agregarMedioContacto(mContacto);
		cliente.agregarCliente(nuevoCliente);
	}
	
	private void agregarPasajero(ActionEvent aP) {
		this.ventanaReserva.mostrarVentana(false);
		this.ventanaCargaPasajero.setVisible(true);
	}

	private void cargarPasajeros(ActionEvent cP) {
		this.ventanaReserva.mostrarVentana(false);
		this.ventanaCargaPasajero.mostrarVentana(true);
	}

	private void generarPasajeTarjeta(ActionEvent rP) {
		String importeIngresado = ventanaPagoTarjeta.getTextImporteIngresado().toString();
	}

	private void generarPasajeEfectivo(ActionEvent rP) {
		
	}

	private void SeleccionFormaDePago(ActionEvent pagar) {
		this.ventanaReserva.mostrarVentana(false);
		this.ventanaFormaDePagos.mostrarVentana(true);
	}

//	private void seleccionEstadoDelPago(ActionEvent pago) {
//		String itemSeleccionado = this.ventanaFormaDePagos.getComboBoxEstadoPago().getSelectedItem().toString();
//		redirigirSegunItemSeleccionado(itemSeleccionado);
//	}

	private void redirigirSegunItemSeleccionado(String itemSeleccionado) {
		if(itemSeleccionado.equals("TARJETA")){
			ventanaPagoTarjeta.mostrarVentana(true);
//			ventanaFormaDePagos.mostrarVentana(false);
//			ventanaFormaDePagos.redimensionar();
			}
		else if(itemSeleccionado.equals("EFECTIVO")){
			ventanaPagoEfectivo.mostrarVentana(true);
			ventanaFormaDePagos.mostrarVentana(false);
		}
	}
	
	private void modificarViajeDePasaje(PasajeDTO pasaje, Date nuevaHorarioSalida){
		ViajeDTO nuevoViaje = pasaje.getViaje();
		nuevoViaje.setFechaSalida((java.sql.Date) nuevaHorarioSalida);
		pasaje.setViaje(nuevoViaje);
	}
	
//	public Controlador(Vista vista, Cliente cliente){
//		this.vista = vista;
//		this.vista.getBtnClientes().addActionListener(this);
//		this.ventanaCliente = VentanaCliente.getInstance();
//		this.ventanaCliente.getBtnRegistrar().addActionListener(this);
//		this.cliente = cliente;
//		this.ventanaReserva = VentanaReserva.getInstance();
//	}
	
	public void inicializar(){	
		this.vista.show();
////		mostrarVentanaReserva();
//		this.ventanaCliente.setVisible(true);
	}
	
	private void mostrarVentanaReserva(){
		this.ventanaReserva.setVisible(true);
	}
	
	
//	private void insertarCliente(VentanaCliente ventanaCliente) {	
//		MedioContactoDTO medioContacto = new MedioContactoDTO(ventanaCliente.getTxtTelefonoFijo().getText(),
//			ventanaCliente.getTxtTelefonoCelular().getText(),
//			ventanaCliente.getTxtEmail().getText()
//		);
//			
//		ClienteDTO nuevoCliente = new ClienteDTO(0,
//			ventanaCliente.getTxtNombre().getText(),
//			ventanaCliente.getTxtApellido().getText(),
//			ventanaCliente.getTxtDni().getText(),
//			ventanaCliente.getDateFechaNacimiento().getDate(),
//			medioContacto
//		);
//		
//		this.cliente.agregarCliente(nuevoCliente);
//	}
	
	//Validamos que los campos esten completos
		private boolean validarCampos(){
			if (ventanaCliente.getTxtNombre().getText().isEmpty() ||
				ventanaCliente.getTxtApellido().getText().isEmpty() ||
				ventanaCliente.getTxtDni().getText().isEmpty() ||
				ventanaCliente.getDateFechaNacimiento().getDate()== null ||
				ventanaCliente.getTxtTelefonoFijo().getText().isEmpty() ||
				ventanaCliente.getTxtTelefonoCelular().getText().isEmpty() ||
				ventanaCliente.getTxtEmail().getText().isEmpty()
			){
				JOptionPane.showMessageDialog(null, "Debe cargar todos los campos", "Mensaje", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			return true;
		}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}