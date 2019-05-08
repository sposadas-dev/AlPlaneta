package main;

import presentacion.controlador.Controlador;
import presentacion.vista.Vista;

public class Main {
	
	public static void main(String[] args) throws Exception {
		Vista vista = new Vista();
		Controlador controlador = new Controlador(vista);
		controlador.inicializar();
	}
}
