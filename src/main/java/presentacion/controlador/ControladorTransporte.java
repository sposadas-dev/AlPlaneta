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

public class ControladorTransporte implements ActionListener {

	private VistaAdministrador vistaAdministrador;
	private VentanaAgregarTransporte ventanaAgregarTransporte;
	private VentanaEditarTransporte ventanaEditarTransporte;
	private Transporte transporte;
	private List<TransporteDTO> transportes_en_tabla;
	private int filaSeleccionada;
	
	public ControladorTransporte(){
		this.ventanaAgregarTransporte = VentanaAgregarTransporte.getInstance();
		this.ventanaEditarTransporte = VentanaEditarTransporte.getInstance();
		this.vistaAdministrador = VistaAdministrador.getInstance();

		this.ventanaAgregarTransporte.getBtnAgregar().addActionListener(rc->agregarTransporte(rc));
		this.ventanaAgregarTransporte.getBtnCancelar().addActionListener(c->cancelarVentanaAgregarTransporte(c));
		this.ventanaEditarTransporte.getBtnEditar().addActionListener(ac->editarTransporte(ac));
		this.ventanaEditarTransporte.getBtnCancelar().addActionListener(c->cancelarVentanaEditarTransporte(c));

		this.transporte = new Transporte(new DAOSQLFactory());
		transportes_en_tabla = transporte.obtenerTransportes();
	}
	
	public void llenarTablaTransportes(){
		this.vistaAdministrador.getPanelTransporte().getModelTransportes().setRowCount(0); //Para vaciar la tabla
		this.vistaAdministrador.getPanelTransporte().getModelTransportes().setColumnCount(0);
		this.vistaAdministrador.getPanelTransporte().getModelTransportes().setColumnIdentifiers(this.vistaAdministrador.getPanelTransporte().getNombreColumnasTransporte());
			
		this.transportes_en_tabla = transporte.obtenerTransportes();
			
		for (int i = 0; i < this.transportes_en_tabla.size(); i++){
			Object[] fila = {this.transportes_en_tabla.get(i).getNombre(),
			};
			this.vistaAdministrador.getPanelTransporte().getModelTransportes().addRow(fila);
		}		
	}
	
	public void mostrarVentanaAgregarTransporte() {
		this.ventanaAgregarTransporte.limpiarCampos();
		this.ventanaAgregarTransporte.mostrarVentana();
	}
	
	public void editarTransporte() {
		this.ventanaEditarTransporte.limpiarCampos();
		this.ventanaEditarTransporte.mostrarVentana();
	}

	public void agregarTransporte(ActionEvent rc) {
		obtenerTransportesActualizado();
		TransporteDTO nuevoTransporte = new TransporteDTO();
		nuevoTransporte.setNombre(this.ventanaAgregarTransporte.getTxtNombreTransporte().getText());
		transporte.agregarTransporte(nuevoTransporte);
		llenarTablaTransportes();
		this.ventanaAgregarTransporte.limpiarCampos();
		this.ventanaAgregarTransporte.cerrarVentana();
	}

	private void cancelarVentanaAgregarTransporte(ActionEvent c) {
		this.ventanaAgregarTransporte.limpiarCampos();
		this.ventanaAgregarTransporte.cerrarVentana();
	}
	public void editarTransporte(int filaSeleccionada){
		obtenerTransportesActualizado();
		this.filaSeleccionada = filaSeleccionada;
		this.ventanaEditarTransporte.mostrarVentana();
		ventanaEditarTransporte.getTxtNombreTransporte().setText(this.transportes_en_tabla.get(this.filaSeleccionada).getNombre());
	}
	
	public void editarTransporte(ActionEvent ac) {
		obtenerTransportesActualizado();
		String nombreTransporte = this.ventanaEditarTransporte.getTxtNombreTransporte().getText();
		this.transporte.editarTransporte(new TransporteDTO(transportes_en_tabla.get(this.filaSeleccionada).getIdTransporte(),nombreTransporte));
		llenarTablaTransportes();
		ventanaEditarTransporte.limpiarCampos();
		ventanaEditarTransporte.dispose();
	}
	
	private void cancelarVentanaEditarTransporte(ActionEvent c) {
		this.ventanaEditarTransporte.limpiarCampos();
		this.ventanaEditarTransporte.cerrarVentana();
	}
	
	public void obtenerTransportesActualizado() {
		this.transportes_en_tabla = transporte.obtenerTransportes();
	}
	
	public void eliminarTransporte(int filaSeleccionada){
		obtenerTransportesActualizado();
		this.transporte.borrarTransporte(transportes_en_tabla.get(filaSeleccionada));
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {	
	}
}