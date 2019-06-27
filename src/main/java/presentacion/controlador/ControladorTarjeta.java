package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

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

//	public void cargarTarjeta(){ 
//		this.tarjeta.agregarTarjeta(datosTarjeta()); 
//		}
//	
	public void mostrarVentanaTarjeta() {
		this.ventanaTarjeta.mostrarVentana(true);
		
	}
	
	private void CargarDatosTarjeta(ActionEvent cd) {
		String numeroTarjeta = this.ventanaTarjeta.getTxtNumeroTarjeta().getText();
		int mesTarjeta = this.ventanaTarjeta.getMesChooser().getMonth()+1;
		int anioTarjeta = this.ventanaTarjeta.getAnioChooser().getYear();
		
		java.sql.Date fechaHoy = new java.sql.Date(new java.util.Date().getTime());	
		String[] fechaArrayy = fechaHoy.toString().split("-");
		int mesHoy = Integer.parseInt(fechaArrayy[1]);
		int anioHoy = Integer.parseInt(fechaArrayy[0]);

		if(anioTarjeta > anioHoy){
			if(numeroTarjetaValido(numeroTarjeta)){
				this.tarjeta.agregarTarjeta(datosTarjeta()); 
				this.ventanaTarjeta.mostrarVentana(false);
			}
			else{ JOptionPane.showMessageDialog(null, "Error! El número de tarjeta no es válido", "Mensaje", JOptionPane.ERROR_MESSAGE);}
		}
		
		if(anioTarjeta == anioHoy){
			if(mesTarjeta >= mesHoy){
				if(numeroTarjetaValido(numeroTarjeta)){
					this.tarjeta.agregarTarjeta(datosTarjeta()); 
					this.ventanaTarjeta.mostrarVentana(false);
				}
				else{ JOptionPane.showMessageDialog(null, "Error! El número de tarjeta no es válido", "Mensaje", JOptionPane.ERROR_MESSAGE);}
			}
			else{ JOptionPane.showMessageDialog(null, "Error! La tarjeta ingresada está vencida", "Mensaje", JOptionPane.ERROR_MESSAGE);}
		}
		
		if(anioTarjeta < anioHoy)
			JOptionPane.showMessageDialog(null, "Error! La tarjeta ingresada está vencida", "Mensaje", JOptionPane.ERROR_MESSAGE);
				
	}
	
	public TarjetaDTO getUltimoRegistro() {
		return this.tarjeta.getUltimoRegistroTarjeta();
	}

	public TarjetaDTO datosTarjeta() {
		TarjetaDTO tarjetadto = new TarjetaDTO();
		int mesTarjeta = this.ventanaTarjeta.getMesChooser().getMonth()+1;
		int anioTarjeta = this.ventanaTarjeta.getAnioChooser().getYear();
		
		tarjetadto.setNroTarjeta(this.ventanaTarjeta.getTxtNumeroTarjeta().getText());
		System.out.println(Integer.toString(mesTarjeta)+"/"+Integer.toString(anioTarjeta));
		tarjetadto.setVencimiento(Integer.toString(mesTarjeta)+"/"+Integer.toString(anioTarjeta));	
		
		return tarjetadto;
	}

	private boolean numeroTarjetaValido(String numero){
		return numero.length()==16;
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
