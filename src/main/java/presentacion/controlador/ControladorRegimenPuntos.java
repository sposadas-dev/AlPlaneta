package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import dto.PuntoDTO;
import modelo.Punto;

import persistencia.dao.mysql.DAOSQLFactory;


import presentacion.vista.coordinador.VentanaModificarRegimenPuntos;
import presentacion.vista.coordinador.VentanaRegistroRegimenPuntos;
public class ControladorRegimenPuntos implements ActionListener {
	
	private VentanaRegistroRegimenPuntos ventanaRegistroRegimenPuntos;
	private VentanaModificarRegimenPuntos ventanaModificarRegimenPuntos;
	private Punto punto;
	private List<PuntoDTO> puntos_en_tabla;
	private int filaSeleccionada;
	
	public ControladorRegimenPuntos(){
		this.ventanaRegistroRegimenPuntos = VentanaRegistroRegimenPuntos.getInstance();
		this.ventanaModificarRegimenPuntos = VentanaModificarRegimenPuntos.getInstance();
		
		this.ventanaRegistroRegimenPuntos.getBtnAgregar().addActionListener(rc->RegistroRegimenPuntos(rc));
		this.ventanaRegistroRegimenPuntos.getBtnCancelar().addActionListener(c->cancelarVentanaRegistroRegimenPuntos(c));
		this.ventanaModificarRegimenPuntos.getBtnModificar().addActionListener(ac->ModificarRegimenPuntos(ac));
		this.ventanaModificarRegimenPuntos.getBtnCancelar().addActionListener(c->cancelarVentanaModificarRegimenPuntos(c));

		this.punto = new Punto(new DAOSQLFactory());
		puntos_en_tabla = punto.obtenerPunto();
	}

	public void mostrarVentanaRegistroRegimenPuntos() {
		this.ventanaRegistroRegimenPuntos.limpiarCampos();
		this.ventanaRegistroRegimenPuntos.mostrarVentana();
	}
	
	public void ModificarRegimenPuntos() {
		this.ventanaModificarRegimenPuntos.limpiarCampos();
		this.ventanaModificarRegimenPuntos.mostrarVentana();
	}
	
	public void RegistroRegimenPuntos(ActionEvent rc) {
		int confirm = JOptionPane.showOptionDialog(
	            null,"¿Estás seguro que quieres agregar el Punto?", 
			             "Agregar Punto", JOptionPane.YES_NO_OPTION,
			             JOptionPane.INFORMATION_MESSAGE, null, null, null);
		if (confirm == 0){		
			java.util.Date dateFechaVencimiento = this.ventanaRegistroRegimenPuntos.getDateVencimiento().getDate();
			java.sql.Date fechaVencimiento = new java.sql.Date(dateFechaVencimiento.getTime());
			PuntoDTO nuevoPunto = new PuntoDTO();
			nuevoPunto.setPunto(Integer.parseInt(this.ventanaRegistroRegimenPuntos.getTxtCantPuntos().getText()));
			nuevoPunto.setARS(Integer.parseInt(this.ventanaRegistroRegimenPuntos.getTextARS().getText()));
			nuevoPunto.setVencimiento(fechaVencimiento);
			punto.agregarPunto(nuevoPunto);
	
			this.ventanaRegistroRegimenPuntos.limpiarCampos();
			this.ventanaRegistroRegimenPuntos.cerrarVentana();
			
		}
	}
	
	private void cancelarVentanaRegistroRegimenPuntos(ActionEvent c) {
		this.ventanaRegistroRegimenPuntos.limpiarCampos();
		this.ventanaRegistroRegimenPuntos.cerrarVentana();
	}
	public void ModificarRegimenPuntos(int filaSeleccionada){
		this.filaSeleccionada = filaSeleccionada;
		this.ventanaModificarRegimenPuntos.mostrarVentana();
		System.out.println(puntos_en_tabla.size()+ "--" + filaSeleccionada);
		this.ventanaModificarRegimenPuntos.getTxtCantPuntos().setText(Integer.toString(this.puntos_en_tabla.get(this.filaSeleccionada).getPunto()));
		this.ventanaModificarRegimenPuntos.getTextARS().setText(Integer.toString(this.puntos_en_tabla.get(this.filaSeleccionada).getARS()));
		this.ventanaModificarRegimenPuntos.getDateVencimiento().setDate(this.puntos_en_tabla.get(this.filaSeleccionada).getVencimiento());
	}
	
    public void ModificarRegimenPuntos(ActionEvent ac) { 
		int confirm = JOptionPane.showOptionDialog(
	            null,"¿Estás seguro que quieres editar el regimen puntos?", 
			             "Editar puntos", JOptionPane.YES_NO_OPTION,
			             JOptionPane.WARNING_MESSAGE, null, null, null);
		if (confirm == 0){
			java.util.Date dateFechaVencimiento = this.ventanaModificarRegimenPuntos.getDateVencimiento().getDate();
			java.sql.Date fechaVencimiento = new java.sql.Date(dateFechaVencimiento.getTime());
			this.punto.editarPunto(new PuntoDTO(puntos_en_tabla.get(this.filaSeleccionada).getIdPunto(),
												Integer.parseInt(this.ventanaModificarRegimenPuntos.getTxtCantPuntos().getText()),
												Integer.parseInt(this.ventanaModificarRegimenPuntos.getTextARS().getText()),
												fechaVencimiento));
			ventanaModificarRegimenPuntos.limpiarCampos();
			ventanaModificarRegimenPuntos.dispose(); 
		}  
	}  
		
	}
	private void cancelarVentanaModificarRegimenPuntos(ActionEvent c) {
		this.ventanaModificarRegimenPuntos.limpiarCampos();
		this.ventanaModificarRegimenPuntos.cerrarVentana();
	}
	
	public void eliminarPunto(int filaSeleccionada){
		int confirm = JOptionPane.showOptionDialog(
		            null,"¿Estás seguro que quieres eliminar el regimen de puntos?", 
				             "Eliminar puntos", JOptionPane.YES_NO_OPTION,
				             JOptionPane.ERROR_MESSAGE, null, null, null);
	 if (confirm == 0){
		this.punto.borrarPunto(puntos_en_tabla.get(filaSeleccionada));
	 }
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {	
	}
	


}
