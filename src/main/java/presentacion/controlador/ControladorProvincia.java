package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import dto.ProvinciaDTO;
import modelo.ModeloProvincia;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.administrador.VentanaAgregarProvincia;
import presentacion.vista.administrador.VentanaEditarProvincia;

public class ControladorProvincia implements ActionListener {

	private VentanaAgregarProvincia ventanaAgregarProvincia;
	private VentanaEditarProvincia ventanaEditarProvincia;
	private ModeloProvincia modeloProvincia;
	private List<ProvinciaDTO> provincias_en_tabla;
	private int filaSeleccionada;
	
	public ControladorProvincia(){
		this.ventanaAgregarProvincia = VentanaAgregarProvincia.getInstance();
		this.ventanaEditarProvincia = VentanaEditarProvincia.getInstance();
		
		this.ventanaAgregarProvincia.getBtnAgregar().addActionListener(rc->agregarProvincia(rc));
		this.ventanaEditarProvincia.getBtnEditar().addActionListener(ac->editarProvincia(ac));

		this.modeloProvincia = new ModeloProvincia(new DAOSQLFactory());
		provincias_en_tabla = modeloProvincia.obtenerProvincias();
	}

	public void mostrarVentanaAgregarProvincia() {
		this.ventanaAgregarProvincia.mostrarVentana();
	}
	
	public void editarTransporte() {
		this.ventanaEditarProvincia.mostrarVentana();
	}
	
	public void agregarProvincia(ActionEvent rc) {
		int confirm = JOptionPane.showOptionDialog(
	            null,"¿Estás seguro que quieres agregar la provincia?", 
			             "Agregar provincia", JOptionPane.YES_NO_OPTION,
			             JOptionPane.INFORMATION_MESSAGE, null, null, null);
		if (confirm == 0){
			ProvinciaDTO nuevoProvincia = new ProvinciaDTO();
			nuevoProvincia.setNombre(this.ventanaAgregarProvincia.getTxtNombreProvincia().getText());
			modeloProvincia.agregarProvincia(nuevoProvincia);
	
			this.ventanaAgregarProvincia.limpiarCampos();
			this.ventanaAgregarProvincia.cerrarVentana();
			JOptionPane.showMessageDialog(null, "Transporte agregado","Transporte", JOptionPane.INFORMATION_MESSAGE);

		}
	}
	
	public void editarProvincia(int filaSeleccionada){
		this.filaSeleccionada = filaSeleccionada;
		this.ventanaEditarProvincia.mostrarVentana();
		ventanaEditarProvincia.getTxtNombreProvincia().setText(this.provincias_en_tabla.get(this.filaSeleccionada).getNombre());
		
	}
	
	public void editarProvincia(ActionEvent ac) {
		int confirm = JOptionPane.showOptionDialog(
	            null,"¿Estás seguro que quieres editar la provincia?", 
			             "Editar provincia", JOptionPane.YES_NO_OPTION,
			             JOptionPane.WARNING_MESSAGE, null, null, null);
		if (confirm == 0){
			ProvinciaDTO nuevaProvincia = provincias_en_tabla.get(this.filaSeleccionada);
			nuevaProvincia.setNombre(this.ventanaEditarProvincia.getTxtNombreProvincia().getText());
			
			this.modeloProvincia.editarProvincia(nuevaProvincia);
			ventanaEditarProvincia.limpiarCampos();
			ventanaEditarProvincia.dispose();
			JOptionPane.showMessageDialog(null, "Provincia editada","Provincia", JOptionPane.INFORMATION_MESSAGE);

		}
		
	}
	
	public void eliminarProvincia(int filaSeleccionada){
		int confirm = JOptionPane.showOptionDialog(
		            null,"¿Estás seguro que quieres eliminar la provincia?", 
				             "Eliminar provincia", JOptionPane.YES_NO_OPTION,
				             JOptionPane.ERROR_MESSAGE, null, null, null);
	 if (confirm == 0){
		JOptionPane.showMessageDialog(null, "Provincia eliminada","Provincia", JOptionPane.INFORMATION_MESSAGE);
// comentario:
		// RELACIONAR EL VIAJE CON LA PROVINCIA Y PAIS ADEMAS DE CIUDAD PARA DARLE DE BAJA
		// SI ES QUE NO TIENE NINGUN VIAJE RELACIONADO.
//		this.modeloProvincia.borrarProvincia(provincias_en_tabla.get(filaSeleccionada));
	 }
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {	
	}
}