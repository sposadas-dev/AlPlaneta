package main;

import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.controlador.Controlador;
import presentacion.vista.VentanaCliente;
import presentacion.vista.VentanaFormaPago;
import presentacion.vista.VentanaPasajero;
import presentacion.vista.VentanaReserva;
import presentacion.vista.Vista;
import modelo.Cliente;

public class Main {
	
	public static void main(String[] args) {
	
//		VentanaCliente cliente= new VentanaCliente();
//		cliente.setVisible(true);
//		VentanaPasajero pasajero = new VentanaPasajero();
//		pasajero.setVisible(true);
		VentanaReserva reserva = new VentanaReserva();
		reserva.setVisible(true);		
//		
		
//		Vista vista = new Vista();
//		Cliente cliente = new Cliente(new DAOSQLFactory());
//		Controlador controlador = new Controlador(vista, cliente);
//		controlador.inicializar();
//		VentanaFormaPago pago = new VentanaFormaPago();
//		pago.setVisible(true);
	}
}
