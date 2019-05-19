package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import dto.PaisDTO;
import modelo.ModeloPais;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.administrador.VentanaPanelGeneral;
import presentacion.vista.administrador.TableroDePaises;
import presentacion.vista.administrador.VentanaAgregarPais;
import presentacion.vista.administrador.VentanaEditarPais;

public class ControladorPais implements ActionListener {

	private VentanaAgregarPais ventanaAgregarPais;
	private VentanaEditarPais ventanaEditarPais;
	private ModeloPais modeloPais;
	private List<PaisDTO> pais_en_tabla;
	private static ControladorPais INSTANCE;
	private VentanaPanelGeneral panelGeneral;
	
	private TableroDePaises tableroDePaises;
	
	public static ControladorPais getInstance(){
		if(INSTANCE == null)
			return new ControladorPais ();
		else
			return INSTANCE;
	}
	
	private ControladorPais(){
		this.ventanaAgregarPais = VentanaAgregarPais.getInstance();
		this.ventanaEditarPais = VentanaEditarPais.getInstance();
		this.tableroDePaises = TableroDePaises.getInstance();
		
		this.tableroDePaises.getBtnAgregar().addActionListener(a->mostrarVentanaAgregarPais(a));
		this.tableroDePaises.getBtnEditar().addActionListener(a->mostrarVentanaEditarPais(a));
		
		this.ventanaAgregarPais.getBtnAgregar().addActionListener(rc->agregarPais(rc));
		this.ventanaEditarPais.getBtnEditar().addActionListener(ac->editarPais(ac));
		
		this.modeloPais = new ModeloPais(new DAOSQLFactory());
		this.pais_en_tabla = modeloPais.obtenerPaises();
		this.panelGeneral = new VentanaPanelGeneral();
	}

	private void mostrarVentanaEditarPais(ActionEvent a) {
		System.out.println("ControladorPais-MostrarVentanaEditarPais");
		this.ventanaEditarPais.mostrarVentana();
	}

	private void mostrarVentanaAgregarPais(ActionEvent a) {
		System.out.println("ControladorPais-MostrarVentanaAgregarPais");
		this.ventanaAgregarPais.mostrarVentana();
	}

	public void agregarPais(ActionEvent rc) {
		System.out.println();
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
		llenarTablaVistaPaises();
	}
	
	public void editarPais(ActionEvent ac) {
		
		int confirm = JOptionPane.showOptionDialog(
	            null,"¿Estás seguro que quieres editar el Pais?", 
			             "Editar pais", JOptionPane.YES_NO_OPTION,
			             JOptionPane.WARNING_MESSAGE, null, null, null);
		if (confirm == 0){
			String nombrePais = this.ventanaEditarPais.getTxtNombrePais().getText();
			this.modeloPais.editarPais(new PaisDTO(pais_en_tabla.get(this.tableroDePaises.getTablaPaises().getSelectedRow()).getIdPais(),nombrePais));
			System.out.println("se edito el pais");
			ventanaEditarPais.limpiarCampos();
			ventanaEditarPais.dispose();
			JOptionPane.showMessageDialog(null, "Pais editado","Pais", JOptionPane.INFORMATION_MESSAGE);
		}
		llenarTablaVistaPaises();
	}
	
	public void eliminarPais(int filaSeleccionada){
		int confirm = JOptionPane.showOptionDialog(
		            null,"¿Estás seguro que quieres eliminar el pais?", 
				             "Eliminar pais", JOptionPane.YES_NO_OPTION,
				             JOptionPane.ERROR_MESSAGE, null, null, null);
	 if (confirm == 0){
		JOptionPane.showMessageDialog(null, "Pais eliminado","Pais", JOptionPane.INFORMATION_MESSAGE);
	 }
	}
	

	public void llenarTablaVistaPaises(){
		System.out.println("ControladorPais-LlenarTablaPaises");
		
		this.tableroDePaises.getModelPaises().setRowCount(0); //Para vaciar la tabla
		this.tableroDePaises.getModelPaises().setColumnCount(0);
		this.tableroDePaises.getModelPaises().setColumnIdentifiers(this.panelGeneral.getNombreColumnas());

		this.pais_en_tabla = modeloPais.obtenerPaises();
			
		for (int i = 0; i < this.pais_en_tabla.size(); i++){
			
			String[] fila = {this.pais_en_tabla.get(i).getNombre(),
							String.valueOf(this.pais_en_tabla.get(i).getIdPais()),
			};
			this.tableroDePaises.getModelPaises().addRow(fila);
		}
	}
	
	private void visualizarPaises() {
		this.llenarTablaVistaPaises();
		
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

	public void mostrarVistaPais() {
		this.tableroDePaises.show();
	}
	
	
}