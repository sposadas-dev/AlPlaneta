package main;

import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.controlador.Controlador;
import presentacion.vista.Vista;
import modelo.Cliente;

public class Main {
	
	public static void main(String[] args) {
	
		Vista vista = new Vista();
		Cliente cliente = new Cliente(new DAOSQLFactory());
		Controlador controlador = new Controlador(vista, cliente);
		controlador.inicializar();
	}
}
