package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import dto.PaisDTO;
import modelo.ModeloPais;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.administrador.PanelGeneral;
import presentacion.vista.administrador.VentanaAgregarPais;
import presentacion.vista.administrador.VentanaEditarPais;

public class ControladorPais implements ActionListener {

	private VentanaAgregarPais ventanaAgregarPais;
	private VentanaEditarPais ventanaEditarPais;
	private ModeloPais modeloPais;
	private List<PaisDTO> pais_en_tabla;
	private int filaSeleccionada;
	private static ControladorPais INSTANCE;
	private PanelGeneral panelGeneral;
	
	public static ControladorPais getInstance(){
		if(INSTANCE == null)
			return new ControladorPais ();
		else
			return INSTANCE;
	}
	
	private ControladorPais(){
		this.ventanaAgregarPais = VentanaAgregarPais.getInstance();
		this.ventanaEditarPais = VentanaEditarPais.getInstance();
		
		this.ventanaAgregarPais.getBtnAgregar().addActionListener(rc->agregarPais(rc));
		this.ventanaEditarPais.getBtnEditar().addActionListener(ac->editarPais(ac));

		this.modeloPais = new ModeloPais(new DAOSQLFactory());
		pais_en_tabla = modeloPais.obtenerPaises();
		this.panelGeneral = new PanelGeneral();
	}

	public void mostrarVentanaAgregarPais() {
		visualizarPaises();
		this.ventanaAgregarPais.mostrarVentana();
	}
	
	public void editarPais() {
		this.ventanaEditarPais.mostrarVentana();
	}
	
	public void agregarPais(ActionEvent rc) {
		int confirm = JOptionPane.showOptionDialog(
	            null,"¿Estás seguro que quieres agregar el pais?", 
			             "Agregar pais", JOptionPane.YES_NO_OPTION,
			             JOptionPane.INFORMATION_MESSAGE, null, null, null);
		if (confirm == 0){
			PaisDTO nuevoPais = new PaisDTO();
			nuevoPais.setNombre(this.ventanaAgregarPais.getTxtNombrePais().getText());
			modeloPais.agregarPais(nuevoPais);
	
			this.ventanaAgregarPais.limpiarCampos();
			this.ventanaAgregarPais.cerrarVentana();
			JOptionPane.showMessageDialog(null, "Pais agregado","Pais", JOptionPane.INFORMATION_MESSAGE);

		}
	}
	
	public void editarPais(int filaSeleccionada){
		this.filaSeleccionada = filaSeleccionada;
		this.ventanaEditarPais.mostrarVentana();
		ventanaEditarPais.getTxtNombrePais().setText(this.pais_en_tabla.get(this.filaSeleccionada).getNombre());
		
	}
	
	public void editarPais(ActionEvent ac) {
		int confirm = JOptionPane.showOptionDialog(
	            null,"¿Estás seguro que quieres editar el Pais?", 
			             "Editar pais", JOptionPane.YES_NO_OPTION,
			             JOptionPane.WARNING_MESSAGE, null, null, null);
		if (confirm == 0){
			String nombrePais = this.ventanaEditarPais.getTxtNombrePais().getText();
			this.modeloPais.editarPais(new PaisDTO(pais_en_tabla.get(this.filaSeleccionada).getIdPais(),nombrePais));
			ventanaEditarPais.limpiarCampos();
			ventanaEditarPais.dispose();
			JOptionPane.showMessageDialog(null, "Pais editado","Pais", JOptionPane.INFORMATION_MESSAGE);

		}
		
	}
	
	public void eliminarPais(int filaSeleccionada){
		this.llenarTabla();
		int confirm = JOptionPane.showOptionDialog(
		            null,"¿Estás seguro que quieres eliminar el pais?", 
				             "Eliminar pais", JOptionPane.YES_NO_OPTION,
				             JOptionPane.ERROR_MESSAGE, null, null, null);
	 if (confirm == 0){
		JOptionPane.showMessageDialog(null, "Pais eliminado","Pais", JOptionPane.INFORMATION_MESSAGE);
//comentario:
		// verificar que ningun viaje tenga relacionado el pais a borrar
//		this.modeloPais.borrarPais(pais_en_tabla.get(filaSeleccionada));
	 }
	}
	

	public void llenarTabla(){
		
		panelGeneral.getModel().setRowCount(0); //Para vaciar la tabla
		panelGeneral.getModel().setColumnCount(0);
		panelGeneral.getModel().setColumnIdentifiers(this.panelGeneral.getNombreColumnas());

		this.pais_en_tabla = modeloPais.obtenerPaises();
			
		for (int i = 0; i < this.pais_en_tabla.size(); i++){
			Object[] fila = {this.pais_en_tabla.get(i).getNombre(),
							this.pais_en_tabla.get(i).getIdPais(),
			};
			this.panelGeneral.getModel().addRow(fila);
		}
	}
	
	private void visualizarPaises() {
		this.panelGeneral.mostrarPanel(true);
		this.llenarTabla();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {	
	}

	public VentanaAgregarPais getVentanaAgregarPais() {
		return ventanaAgregarPais;
	}

	public void setVentanaAgregarPais(VentanaAgregarPais ventanaAgregarPais) {
		this.ventanaAgregarPais = ventanaAgregarPais;
	}

	public VentanaEditarPais getVentanaEditarPais() {
		return ventanaEditarPais;
	}

	public void setVentanaEditarPais(VentanaEditarPais ventanaEditarPais) {
		this.ventanaEditarPais = ventanaEditarPais;
	}

	public ModeloPais getModeloPais() {
		return modeloPais;
	}

	public void setModeloPais(ModeloPais modeloPais) {
		this.modeloPais = modeloPais;
	}

	public List<PaisDTO> getPais_en_tabla() {
		return pais_en_tabla;
	}

	public void setPais_en_tabla(List<PaisDTO> pais_en_tabla) {
		this.pais_en_tabla = pais_en_tabla;
	}
	
	
}