package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import dto.FormaPagoDTO;
import modelo.FormaPago;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.administrador.VentanaAgregarFormaPago;
import presentacion.vista.administrador.VentanaEditarFormaPago;
import presentacion.vista.administrador.VistaAdministrador;

public class ControladorFormaPago implements ActionListener {

	private VistaAdministrador vistaAdministrador;
	private VentanaAgregarFormaPago ventanaAgregarFormaPago;
	private VentanaEditarFormaPago ventanaEditarFormaPago;
	private FormaPago formaPago;
	private List<FormaPagoDTO> formas_de_pagos_en_tabla;
	private int filaSeleccionada;
	
	public ControladorFormaPago(){
		this.ventanaAgregarFormaPago = VentanaAgregarFormaPago.getInstance();
		this.ventanaEditarFormaPago = VentanaEditarFormaPago.getInstance();
		this.vistaAdministrador = VistaAdministrador.getInstance();
		
		this.ventanaAgregarFormaPago.getBtnAgregar().addActionListener(rc->agregarFormaPago(rc));
		this.ventanaAgregarFormaPago.getBtnCancelar().addActionListener(c->cancelarVentanaAgregarFormaPago(c));
		this.ventanaEditarFormaPago.getBtnEditar().addActionListener(ac->editarFormaPago(ac));
		this.ventanaEditarFormaPago.getBtnCancelar().addActionListener(c->cancelarVentanaEditarFormaPago(c));
		this.formaPago = new FormaPago(new DAOSQLFactory());
		this.formas_de_pagos_en_tabla = formaPago.obtenerFormaPago();
	}

	public void obtenerFormaDePagoActualizado() {
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
		FormaPagoDTO nuevoFormaPago = new FormaPagoDTO();
		nuevoFormaPago.setTipo(this.ventanaAgregarFormaPago.getTxtTipoPago().getText());
		formaPago.agregarFormaPago(nuevoFormaPago);
		llenarTablaFormaPago();
		this.ventanaAgregarFormaPago.limpiarCampos();
		this.ventanaAgregarFormaPago.cerrarVentana();
	}
	
	private void cancelarVentanaAgregarFormaPago(ActionEvent c) {
		this.ventanaAgregarFormaPago.limpiarCampos();
		this.ventanaAgregarFormaPago.cerrarVentana();
	}
	
	public void editarFormaPago(int filaSeleccionada){
		obtenerFormaDePagoActualizado();
		this.filaSeleccionada = filaSeleccionada;
		this.ventanaEditarFormaPago.mostrarVentana();
		ventanaEditarFormaPago.getTxtTipoFormaPago().setText(this.formas_de_pagos_en_tabla.get(this.filaSeleccionada).getTipo());
	}
	
	public void editarFormaPago(ActionEvent ac) {
		obtenerFormaDePagoActualizado();
		String tipo = this.ventanaEditarFormaPago.getTxtTipoFormaPago().getText();
		this.formaPago.editarFormaPago(new FormaPagoDTO(formas_de_pagos_en_tabla.get(this.filaSeleccionada).getIdFormaPago(),tipo));
		llenarTablaFormaPago();
		ventanaEditarFormaPago.limpiarCampos();
		ventanaEditarFormaPago.dispose();
	}
	
	private void cancelarVentanaEditarFormaPago(ActionEvent c) {
		this.ventanaEditarFormaPago.limpiarCampos();
		this.ventanaEditarFormaPago.cerrarVentana();
	}

	
	public void eliminarFormaPago(int filaSeleccionada){
		obtenerFormaDePagoActualizado();
		this.formaPago.borrarFormaPago(formas_de_pagos_en_tabla.get(filaSeleccionada));
		llenarTablaFormaPago();
	}
	
	public void llenarTablaFormaPago(){
		this.vistaAdministrador.getPanelFormaPago().getModelFormaPago().setRowCount(0); //Para vaciar la tabla
		this.vistaAdministrador.getPanelFormaPago().getModelFormaPago().setColumnCount(0);
		this.vistaAdministrador.getPanelFormaPago().getModelFormaPago().setColumnIdentifiers(this.vistaAdministrador.getPanelFormaPago().getNombreColumnasFormaPago());
			
		this.formas_de_pagos_en_tabla = formaPago.obtenerFormaPago();
			
		for (int i = 0; i < this.formas_de_pagos_en_tabla.size(); i++){
			Object[] fila = {this.formas_de_pagos_en_tabla.get(i).getTipo(),
			};
			this.vistaAdministrador.getPanelFormaPago().getModelFormaPago().addRow(fila);
		}		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {	
	}
}