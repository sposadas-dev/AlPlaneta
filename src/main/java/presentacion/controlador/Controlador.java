package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JOptionPane;

import modelo.Cliente;
import modelo.MedioContacto;
import dto.AdministrativoDTO;
import dto.ClienteDTO;
import dto.MedioContactoDTO;
import dto.PasajeDTO;
import dto.ViajeDTO;
import persistencia.dao.mysql.ClienteDAOSQL;
import persistencia.dao.mysql.DAOSQLFactory;
import persistencia.dao.mysql.MedioContactoDAOSQL;
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
	private Cliente cliente;
	private MedioContacto medioContacto;
	private VentanaReserva ventanaReserva;
	private VentanaFormaPago ventanaFormaDePagos;
	private VentanaPagoTarjeta ventanaPagoTarjeta;
	private VentanaPagoEfectivo ventanaPagoEfectivo;
	private VentanaCargaPasajero ventanaCargaPasajero;
	private VentanaPasajero ventanaPasajero;
	
	public Controlador() {
		this.ventanaReserva = VentanaReserva.getInstance();
		this.ventanaFormaDePagos = VentanaFormaPago.getInstance();
		this.ventanaPagoTarjeta = VentanaPagoTarjeta.getInstance();
		this.ventanaPagoEfectivo = VentanaPagoEfectivo.getInstance();
		this.ventanaPasajero = VentanaPasajero.getInstance();
		this.ventanaCliente = VentanaCliente.getInstance();

		this.ventanaCargaPasajero = VentanaCargaPasajero.getInstance();
		this.ventanaCliente.getBtnRegistrar().addActionListener(cliente->cargarCliente(cliente));
		this.ventanaReserva.getBtnReservar().addActionListener(reserv->SeleccionFormaDePago(reserv));
		this.ventanaReserva.getBtnCargaPasajeros().addActionListener(cP->cargarPasajeros(cP));
//		this.ventanaFormaDePagos.get).addActionListener(pago->seleccionEstadoDelPago(pago));
		this.ventanaPagoEfectivo.getBtnRegistrarPago().addActionListener(rP->generarPasajeEfectivo(rP));
		this.ventanaPagoTarjeta.getBtnEnviar().addActionListener(rP->generarPasajeTarjeta(rP));
		
		this.ventanaCargaPasajero.getBtnAgregarPasajero().addActionListener(aP->agregarPasajero(aP));
	}
	
	private void cargarCliente(ActionEvent cliente) {
	/*Obtenemos la fecha de nacimiento , y la parseamos a tipo de date de SQL*/
		java.util.Date dateFechaNacimiento = ventanaCliente.getDateFechaNacimiento().getDate();
		java.sql.Date fechaNacimiento = new java.sql.Date(dateFechaNacimiento.getTime());
		
		/*Obtenemos el medio de contacto del cliente*/
		MedioContactoDTO medioContacto = new MedioContactoDTO(0,ventanaCliente.getTxtTelefonoFijo().getText(),
				ventanaCliente.getTxtTelefonoCelular().getText(),
				ventanaCliente.getTxtEmail().getText()
		);
		
//		/*Agregamos un nuevo cliente */
		ClienteDTO nuevoCliente = new ClienteDTO(0,
				ventanaCliente.getTxtNombre().getText(),
				ventanaCliente.getTxtApellido().getText(),
				ventanaCliente.getTxtDni().getText(),
				fechaNacimiento,
				medioContacto);
		
		MedioContactoDAOSQL md = new MedioContactoDAOSQL(); //Inserta en la bd 		
		md.insert(medioContacto);
		
		ClienteDAOSQL sqlCliente = new ClienteDAOSQL();		//deberia insertar en la bd 
		sqlCliente.insert(nuevoCliente);	
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

	private void seleccionEstadoDelPago(ActionEvent pago) {
		String itemSeleccionado = this.ventanaFormaDePagos.getComboBoxEstadoPago().getSelectedItem().toString();
		redirigirSegunItemSeleccionado(itemSeleccionado);
	}

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
//		this.vista.show();
//		mostrarVentanaReserva();
		this.ventanaCliente.setVisible(true);
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
		
//	@Override
//	public void actionPerformed(ActionEvent evento){
//		if(evento.getSource() == vista.getBtnClientes()){
//			ventanaCliente.mostrarVentana();
//		}
//		else if(evento.getSource() == ventanaCliente.getBtnRegistrar()){
//			if (validarCampos()){
////				insertarCliente(ventanaCliente);
//				ventanaCliente.dispose();
//			}
//		}
//	}
	public static void main(String[] args) {
		Controlador c = new Controlador();
		c.inicializar();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}