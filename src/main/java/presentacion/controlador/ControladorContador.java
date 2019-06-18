package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dto.ContadorDTO;
import presentacion.vista.contador.VistaContador;

public class ControladorContador implements ActionListener {

	private VistaContador vistaContador;
	private ContadorDTO contadorLogueado;

	public ControladorContador(VistaContador vistaContador,ContadorDTO contadorLogueado) {
	
		this.vistaContador = vistaContador;		
		this.contadorLogueado = contadorLogueado;
	}

	public void inicializar(){
		this.vistaContador.mostrarVentana();
	}
	
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
    }
}