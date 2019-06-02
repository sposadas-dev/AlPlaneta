package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import dto.FormaPagoDTO;

import modelo.FormaPago;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.administrador.VentanaAgregarFormaPago;
import presentacion.vista.administrador.VentanaEditarFormaPago;
import presentacion.vista.administrador.VistaAdministrador;

public class ControladorFormaPago implements ActionListener {

	private VentanaAgregarFormaPago ventanaAgregarFormaPago;
	private VentanaEditarFormaPago ventanaEditarFormaPago;
	private FormaPago formaPago;
	private List<FormaPagoDTO> formas_de_pagos_en_tabla;
	private int filaSeleccionada;
	
	public ControladorFormaPago(){
		this.ventanaAgregarFormaPago = VentanaAgregarFormaPago.getInstance();
		this.ventanaEditarFormaPago = VentanaEditarFormaPago.getInstance();
		
		this.ventanaAgregarFormaPago.getBtnAgregar().addActionListener(rc->agregarFormaPago(rc));
		this.ventanaAgregarFormaPago.getBtnCancelar().addActionListener(c->cancelarVentanaAgregarFormaPago(c));
		this.ventanaEditarFormaPago.getBtnEditar().addActionListener(ac->editarFormaPago(ac));
		this.ventanaEditarFormaPago.getBtnCancelar().addActionListener(c->cancelarVentanaEditarFormaPago(c));
		this.formaPago = new FormaPago(new DAOSQLFactory());
		this.formas_de_pagos_en_tabla = formaPago.obtenerFormaPago();
	}

	public void mostrarVentanaAgregarFormaPago() {
		this.ventanaAgregarFormaPago.limpiarCampos();
		this.ventanaAgregarFormaPago.mostrarVentana();
	}
	
	public void editarFormaPago() {
		this.ventanaEditarFormaPago.limpiarCampos();
		this.ventanaEditarFormaPago.mostrarVentana();
	}
	
	public void agregarFormaPago(ActionEvent rc) {
		int confirm = JOptionPane.showOptionDialog(
	            null,"¿Estás seguro que quieres agregar la forma de pago?", 
			             "Agregar forma de pago", JOptionPane.YES_NO_OPTION,
			             JOptionPane.INFORMATION_MESSAGE, null, null, null);
		if (confirm == 0){
			FormaPagoDTO nuevoFormaPago = new FormaPagoDTO();
			nuevoFormaPago.setTipo(this.ventanaAgregarFormaPago.getTxtTipoPago().getText());
			formaPago.agregarFormaPago(nuevoFormaPago);
	
			this.ventanaAgregarFormaPago.limpiarCampos();
			this.ventanaAgregarFormaPago.cerrarVentana();
			
		}
	}
	
	private void cancelarVentanaAgregarFormaPago(ActionEvent c) {
		this.ventanaAgregarFormaPago.limpiarCampos();
		this.ventanaAgregarFormaPago.cerrarVentana();
	}
	
	public void editarFormaPago(int filaSeleccionada){
		this.filaSeleccionada = filaSeleccionada;
		this.ventanaEditarFormaPago.mostrarVentana();
		ventanaEditarFormaPago.getTxtTipoFormaPago().setText(this.formas_de_pagos_en_tabla.get(this.filaSeleccionada).getTipo());
		
	}
	
	public void editarFormaPago(ActionEvent ac) {
		int confirm = JOptionPane.showOptionDialog(
	            null,"¿Estás seguro que quieres editar la forma de pago?", 
			             "Editar Forma Pago", JOptionPane.YES_NO_OPTION,
			             JOptionPane.WARNING_MESSAGE, null, null, null);
		if (confirm == 0){
			String tipo = this.ventanaEditarFormaPago.getTxtTipoFormaPago().getText();
			this.formaPago.editarFormaPago(new FormaPagoDTO(formas_de_pagos_en_tabla.get(this.filaSeleccionada).getIdFormaPago(),tipo));
			ventanaEditarFormaPago.limpiarCampos();
			ventanaEditarFormaPago.dispose();
			

		}
		
	}
	
	private void cancelarVentanaEditarFormaPago(ActionEvent c) {
		this.ventanaEditarFormaPago.limpiarCampos();
		this.ventanaEditarFormaPago.cerrarVentana();
	}

	
	public void eliminarFormaPago(int filaSeleccionada){
		int confirm = JOptionPane.showOptionDialog(
		            null,"¿Estás seguro que quieres eliminar la forma de pago?", 
				             "Eliminar FormaPago", JOptionPane.YES_NO_OPTION,
				             JOptionPane.ERROR_MESSAGE, null, null, null);
	 if (confirm == 0){
		this.formaPago.borrarFormaPago(formas_de_pagos_en_tabla.get(filaSeleccionada));
	 }
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {	
	}
}