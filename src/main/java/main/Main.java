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
	
	public static void main(String[] args) throws Exception {
		Vista vista = new Vista();
		Controlador controlador = new Controlador(vista);
		controlador.inicializar();
	}
}
