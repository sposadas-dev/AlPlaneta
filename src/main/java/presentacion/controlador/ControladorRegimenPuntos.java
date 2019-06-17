package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dto.PuntoDTO;
import dto.RegimenPuntoDTO;
import modelo.ModeloRegimenPunto;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.coordinador.VentanaModificarRegimenPuntos;
import presentacion.vista.coordinador.VentanaMostrarRegimenPuntos;
import presentacion.vista.coordinador.VentanaRegistroRegimenPuntos;
public class ControladorRegimenPuntos implements ActionListener {
	
	private VentanaRegistroRegimenPuntos ventanaRegistroRegimenPuntos;
	private VentanaModificarRegimenPuntos ventanaModificarRegimenPuntos;
	private VentanaMostrarRegimenPuntos ventanaMostrarRegimenPuntos;
	private ModeloRegimenPunto modeloRegimenPunto;
	private RegimenPuntoDTO regimenPunto;
	
	public ControladorRegimenPuntos(){
		this.ventanaRegistroRegimenPuntos = VentanaRegistroRegimenPuntos.getInstance();
		this.ventanaModificarRegimenPuntos = VentanaModificarRegimenPuntos.getInstance();
		this.ventanaMostrarRegimenPuntos = VentanaMostrarRegimenPuntos.getInstance();
		
		this.ventanaMostrarRegimenPuntos.getBtnModificar().addActionListener(ed->EditarRegimenPuntos(ed));
		this.ventanaMostrarRegimenPuntos.getBtnCancelar().addActionListener(c->cancenlarVentanaMostrarRegimenPuntos(c));
		
		this.ventanaRegistroRegimenPuntos.getBtnAgregar().addActionListener(rc->RegistroRegimenPuntos(rc));
		this.ventanaRegistroRegimenPuntos.getBtnCancelar().addActionListener(c->cancelarVentanaRegistroRegimenPuntos(c));
		
		this.ventanaModificarRegimenPuntos.getBtnModificar().addActionListener(ac->ModificarRegimenPuntos(ac));
		this.ventanaModificarRegimenPuntos.getBtnCancelar().addActionListener(c->cancelarVentanaModificarRegimenPuntos(c));

		this.modeloRegimenPunto = new ModeloRegimenPunto(new DAOSQLFactory());
		this.regimenPunto = this.modeloRegimenPunto.obtenerUltimoRegistro();
	}
	
	private void cancenlarVentanaMostrarRegimenPuntos(ActionEvent c) {
		this.ventanaMostrarRegimenPuntos.limpiarCampos();
		this.ventanaMostrarRegimenPuntos.cerrarVentana();
	}

	private void EditarRegimenPuntos(ActionEvent ed) {
		this.ventanaMostrarRegimenPuntos.setVisible(false);
		llenarValoresEnModificarRegimenPuntos();
	}

	private void llenarValoresEnModificarRegimenPuntos() {
		if(regimenPunto!=null){
			this.ventanaModificarRegimenPuntos.getTextARS().setText(String.valueOf(regimenPunto.getARS()));
			this.ventanaModificarRegimenPuntos.getTxtCantPuntos().setText(String.valueOf(regimenPunto.getPunto()));;
			this.ventanaModificarRegimenPuntos.getVencimiento().setText(String.valueOf(regimenPunto.getVencimiento()));;
		}
		this.ventanaModificarRegimenPuntos.setVisible(true);
	}

	public void mostrarVentanaRegimenPuntos(){
		this.regimenPunto = this.modeloRegimenPunto.obtenerUltimoRegistro();
		if(regimenPunto!=null){
		this.ventanaMostrarRegimenPuntos.getTextARS().setText(String.valueOf(regimenPunto.getARS()));
		this.ventanaMostrarRegimenPuntos.getVencimiento().setText(String.valueOf(regimenPunto.getVencimiento()));
		this.ventanaMostrarRegimenPuntos.getTxtCantPuntos().setText(String.valueOf(regimenPunto.getPunto()));
		this.ventanaMostrarRegimenPuntos.setVisible(true);
		}else{
			JOptionPane.showMessageDialog(null, "No se encuentra cargado ningun regimen de puntos, porfavor cargue uno.", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void mostrarVentanaRegistroRegimenPuntosConDarAlta() {
		this.ventanaRegistroRegimenPuntos.limpiarCampos();
		this.ventanaRegistroRegimenPuntos.mostrarVentana();
	}
	
	public void ModificarRegimenPuntos() {
		this.ventanaModificarRegimenPuntos.limpiarCampos();
		this.ventanaModificarRegimenPuntos.mostrarVentana();
	}
	
	public void RegistroRegimenPuntos(ActionEvent rc) {
		if(this.modeloRegimenPunto.obtenerPunto().size()!=0){
			JOptionPane.showMessageDialog(null, "Ya existe un regimen de puntos actualmente", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return;
		}
			RegimenPuntoDTO nuevoPunto = new RegimenPuntoDTO();
			nuevoPunto.setPunto(Integer.parseInt(this.ventanaRegistroRegimenPuntos.getTxtCantPuntos().getText()));
			nuevoPunto.setARS(Integer.parseInt(this.ventanaRegistroRegimenPuntos.getTextARS().getText()));
			nuevoPunto.setVencimiento(Integer.parseInt(this.ventanaRegistroRegimenPuntos.getVencimiento().getText()));
			modeloRegimenPunto.agregarPunto(nuevoPunto);
	
			this.ventanaRegistroRegimenPuntos.limpiarCampos();
			this.ventanaRegistroRegimenPuntos.cerrarVentana();
			
	}
	
	private void cancelarVentanaRegistroRegimenPuntos(ActionEvent c) {
		this.ventanaRegistroRegimenPuntos.limpiarCampos();
		this.ventanaRegistroRegimenPuntos.cerrarVentana();
	}
	
    public void ModificarRegimenPuntos(ActionEvent ac) { 
			this.modeloRegimenPunto.editarPunto(new RegimenPuntoDTO(
												regimenPunto.getIdPunto(),
												Integer.parseInt(this.ventanaModificarRegimenPuntos.getTxtCantPuntos().getText()),
												Integer.parseInt(this.ventanaModificarRegimenPuntos.getTextARS().getText()),
												Integer.parseInt(this.ventanaModificarRegimenPuntos.getVencimiento().getText()))
								);
			ventanaModificarRegimenPuntos.limpiarCampos();
			ventanaModificarRegimenPuntos.dispose(); 
			this.regimenPunto = this.modeloRegimenPunto.obtenerUltimoRegistro();
			mostrarVentanaRegimenPuntos();
	}  
		
	private void cancelarVentanaModificarRegimenPuntos(ActionEvent c) {
		this.ventanaModificarRegimenPuntos.limpiarCampos();
		this.ventanaModificarRegimenPuntos.cerrarVentana();
	}
	
	public void eliminarPunto(){
		mostrarVentanaRegimenPuntos();
		this.modeloRegimenPunto.borrarPunto(regimenPunto);
		this.ventanaMostrarRegimenPuntos.limpiarCampos();
		this.ventanaMostrarRegimenPuntos.cerrarVentana();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {	
	}
	


}
