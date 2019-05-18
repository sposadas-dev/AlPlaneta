package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import dto.TransporteDTO;
import modelo.Transporte;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.administrador.VentanaAgregarTransporte;
import presentacion.vista.administrador.VentanaEditarTransporte;
import presentacion.vista.administrador.VistaAdministrador;

public class ControladorCiudad implements ActionListener {

	private VentanaAgregarTransporte ventanaAgregarTransporte;
	private VentanaEditarTransporte ventanaEditarTransporte;
	private Transporte transporte;
	private List<TransporteDTO> transportes_en_tabla;
	private int filaSeleccionada;
	
	public ControladorCiudad(){
		this.ventanaAgregarTransporte = VentanaAgregarTransporte.getInstance();
		this.ventanaEditarTransporte = VentanaEditarTransporte.getInstance();
		
		this.ventanaAgregarTransporte.getBtnAgregar().addActionListener(rc->agregarTransporte(rc));
		this.ventanaEditarTransporte.getBtnEditar().addActionListener(ac->editarTransporte(ac));

		this.transporte = new Transporte(new DAOSQLFactory());
		transportes_en_tabla = transporte.obtenerTransportes();
	}

	public void mostrarVentanaAgregarTransporte() {
		this.ventanaAgregarTransporte.mostrarVentana();
	}
	
	public void editarTransporte() {
		this.ventanaEditarTransporte.mostrarVentana();
	}
	
	public void agregarTransporte(ActionEvent rc) {
		int confirm = JOptionPane.showOptionDialog(
	            null,"¿Estás seguro que quieres agregar el transporte?", 
			             "Agregar transporte", JOptionPane.YES_NO_OPTION,
			             JOptionPane.INFORMATION_MESSAGE, null, null, null);
		if (confirm == 0){
			TransporteDTO nuevoTransporte = new TransporteDTO();
			nuevoTransporte.setNombre(this.ventanaAgregarTransporte.getTxtNombreTransporte().getText());
			transporte.agregarTransporte(nuevoTransporte);
	
			this.ventanaAgregarTransporte.limpiarCampos();
			this.ventanaAgregarTransporte.cerrarVentana();
			JOptionPane.showMessageDialog(null, "Transporte agregado","Transporte", JOptionPane.INFORMATION_MESSAGE);

		}
	}
	
	public void editarTransporte(int filaSeleccionada){
		this.filaSeleccionada = filaSeleccionada;
		this.ventanaEditarTransporte.mostrarVentana();
		ventanaEditarTransporte.getTxtNombreTransporte().setText(this.transportes_en_tabla.get(this.filaSeleccionada).getNombre());
		
	}
	
	public void editarTransporte(ActionEvent ac) {
		int confirm = JOptionPane.showOptionDialog(
	            null,"¿Estás seguro que quieres editar el transporte?", 
			             "Editar transporte", JOptionPane.YES_NO_OPTION,
			             JOptionPane.WARNING_MESSAGE, null, null, null);
		if (confirm == 0){
			String nombreTransporte = this.ventanaEditarTransporte.getTxtNombreTransporte().getText();
			this.transporte.editarTransporte(new TransporteDTO(transportes_en_tabla.get(this.filaSeleccionada).getIdTransporte(),nombreTransporte));
			ventanaEditarTransporte.limpiarCampos();
			ventanaEditarTransporte.dispose();
			JOptionPane.showMessageDialog(null, "Transporte editado","Transporte", JOptionPane.INFORMATION_MESSAGE);

		}
		
	}
	
	public void eliminarTransporte(int filaSeleccionada){
		int confirm = JOptionPane.showOptionDialog(
		            null,"¿Estás seguro que quieres eliminar el transporte?", 
				             "Eliminar localidad", JOptionPane.YES_NO_OPTION,
				             JOptionPane.ERROR_MESSAGE, null, null, null);
	 if (confirm == 0){
		JOptionPane.showMessageDialog(null, "Transporte eliminado","Transporte", JOptionPane.INFORMATION_MESSAGE);
		this.transporte.borrarTransporte(transportes_en_tabla.get(filaSeleccionada));
	 }
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {	
	}
}