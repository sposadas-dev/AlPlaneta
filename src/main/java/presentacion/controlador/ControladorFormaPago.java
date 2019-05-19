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
	private FormaPago FormaPago;
	private List<FormaPagoDTO> formapago_en_tabla;
	private int filaSeleccionada;
	
	public ControladorFormaPago(){
		this.ventanaAgregarFormaPago = VentanaAgregarFormaPago.getInstance();
		this.ventanaEditarFormaPago = VentanaEditarFormaPago.getInstance();
		
		this.ventanaAgregarFormaPago.getBtnAgregar().addActionListener(rc->agregarFormaPago(rc));
		this.ventanaEditarFormaPago.getBtnEditar().addActionListener(ac->editarFormaPago(ac));

		this.FormaPago = new FormaPago(new DAOSQLFactory());
		formapago_en_tabla = FormaPago.obtenerFormaPago();
	}

	public void mostrarVentanaAgregarFormaPago() {
		this.ventanaAgregarFormaPago.mostrarVentana();
	}
	
	public void editarFormaPago() {
		this.ventanaEditarFormaPago.mostrarVentana();
	}
	
	public void agregarFormaPago(ActionEvent rc) {
		int confirm = JOptionPane.showOptionDialog(
	            null,"¿Estás seguro que quieres agregar el FormaPago?", 
			             "Agregar forma de pago", JOptionPane.YES_NO_OPTION,
			             JOptionPane.INFORMATION_MESSAGE, null, null, null);
		if (confirm == 0){
			FormaPagoDTO nuevoFormaPago = new FormaPagoDTO();
			nuevoFormaPago.setTipo(this.ventanaAgregarFormaPago.getTxtTipoPago().getText());
			FormaPago.agregarFormaPago(nuevoFormaPago);
	
			this.ventanaAgregarFormaPago.limpiarCampos();
			this.ventanaAgregarFormaPago.cerrarVentana();
			JOptionPane.showMessageDialog(null, "FormaPago agregado","FormaPago", JOptionPane.INFORMATION_MESSAGE);

		}
	}
	
	public void editarFormaPago(int filaSeleccionada){
		this.filaSeleccionada = filaSeleccionada;
		this.ventanaEditarFormaPago.mostrarVentana();
		ventanaEditarFormaPago.getTxtTipoFormaPago().setText(this.formapago_en_tabla.get(this.filaSeleccionada).getTipo());
		
	}
	
	public void editarFormaPago(ActionEvent ac) {
		int confirm = JOptionPane.showOptionDialog(
	            null,"¿Estás seguro que quieres editar el FormaPago?", 
			             "Editar Forma Pago", JOptionPane.YES_NO_OPTION,
			             JOptionPane.WARNING_MESSAGE, null, null, null);
		if (confirm == 0){
			String tipo = this.ventanaEditarFormaPago.getTxtTipoFormaPago().getText();
			this.FormaPago.editarFormaPago(new FormaPagoDTO(formapago_en_tabla.get(this.filaSeleccionada).getIdFormaPago(),tipo));
			ventanaEditarFormaPago.limpiarCampos();
			ventanaEditarFormaPago.dispose();
			JOptionPane.showMessageDialog(null, "Forma de pago editado","Forma Pago", JOptionPane.INFORMATION_MESSAGE);

		}
		
	}
	
	public void eliminarFormaPago(int filaSeleccionada){
		int confirm = JOptionPane.showOptionDialog(
		            null,"¿Estás seguro que quieres eliminar el FormaPago?", 
				             "Eliminar FormaPago", JOptionPane.YES_NO_OPTION,
				             JOptionPane.ERROR_MESSAGE, null, null, null);
	 if (confirm == 0){
		JOptionPane.showMessageDialog(null, "FormaPago eliminado","FormaPago", JOptionPane.INFORMATION_MESSAGE);
		this.FormaPago.borrarFormaPago(formapago_en_tabla.get(filaSeleccionada));
	 }
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {	
	}
}