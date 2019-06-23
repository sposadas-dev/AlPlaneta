package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dto.TarjetaDTO;
import modelo.Tarjeta;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.administrativo.VentanaTarjeta;

public class ControladorTarjeta implements ActionListener {
	
	private VentanaTarjeta ventanaTarjeta;
	private Tarjeta tarjeta;
	TarjetaDTO tarjetaDTO;
	
	
	public ControladorTarjeta() {
		this.ventanaTarjeta = VentanaTarjeta.getInstance();
		this.tarjetaDTO = new TarjetaDTO();
		this.ventanaTarjeta.getBtnCargarDatos().addActionListener(cd -> CargarDatosTarjeta(cd));
		
		tarjeta = new Tarjeta(new DAOSQLFactory());
	}

	public void cargarTarjeta(){ 
		this.tarjeta.agregarTarjeta(datosTarjeta()); 
		}
	
	public void mostrarVentanaTarjeta() {
		this.ventanaTarjeta.mostrarVentana(true);
		
	}
	
	private void CargarDatosTarjeta(ActionEvent cd) {
		datosTarjeta();
//		tarjeta.agregarTarjeta(tarjetaDTO);
		this.ventanaTarjeta.mostrarVentana(false);
	}

	public TarjetaDTO datosTarjeta() {
		TarjetaDTO tarjetadto = new TarjetaDTO();
		int mes= this.ventanaTarjeta.getMesChooser().getMonth()+1;
		int anio = this.ventanaTarjeta.getAnioChooser().getYear();
		tarjetadto.setNroTarjeta(this.ventanaTarjeta.getTxtNumeroTarjeta().getText());
		System.out.println(Integer.toString(mes)+"/"+Integer.toString(anio));
		tarjetadto.setVencimiento(Integer.toString(mes)+"/"+Integer.toString(anio));
		return tarjetadto;
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
