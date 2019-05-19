package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import dto.PaisDTO;
import dto.ProvinciaDTO;
import modelo.ModeloPais;
import modelo.ModeloProvincia;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.administrador.PanelGeneral;
import presentacion.vista.administrador.VentanaAgregarProvincia;
import presentacion.vista.administrador.VentanaEditarProvincia;

public class ControladorProvincia implements ActionListener {

	private VentanaAgregarProvincia ventanaAgregarProvincia;
	private VentanaEditarProvincia ventanaEditarProvincia;
	private ModeloProvincia modeloProvincia;
	private ModeloPais modeloPais;
	private List<ProvinciaDTO> provincias_en_tabla;
	private int filaSeleccionada;
	private PanelGeneral panel;
	
	private static ControladorProvincia INSTANCE;
	
	public static ControladorProvincia getInstance(){
		if(INSTANCE == null)
			return new ControladorProvincia ();
		else
			return INSTANCE;
	}
	
	private ControladorProvincia(){
		this.ventanaAgregarProvincia = VentanaAgregarProvincia.getInstance();
		this.ventanaEditarProvincia = VentanaEditarProvincia.getInstance();
		
		this.ventanaAgregarProvincia.getBtnAgregar().addActionListener(rc->agregarProvincia(rc));
		this.ventanaEditarProvincia.getBtnEditar().addActionListener(ac->editarProvincia(ac));

		this.modeloPais = new ModeloPais(new DAOSQLFactory());
		this.modeloProvincia = new ModeloProvincia(new DAOSQLFactory());
		provincias_en_tabla = modeloProvincia.obtenerProvincias();
		this.panel = new PanelGeneral();
	}

	public void mostrarVentanaAgregarProvincia() {
		llenarComboBoxPaises();
		this.ventanaAgregarProvincia.mostrarVentana();
	}
	
	@SuppressWarnings("unchecked")
	private void llenarComboBoxPaises() {
		List<PaisDTO> paises = modeloPais.obtenerPaises();
		
		String[] nombresPaises = new String[paises.size()];
		for(int i=0; i<paises.size();i++){
			String pais = paises.get(i).getIdPais()+"-"+paises.get(i).getNombre();
			nombresPaises [i] = pais;
		}	
		this.ventanaAgregarProvincia.getComboBoxPaises().setModel(new DefaultComboBoxModel<Object>(nombresPaises));
	}
	

	public void editarProvincia() {
		this.ventanaEditarProvincia.mostrarVentana();
	}
	
	public void agregarProvincia(ActionEvent rc) {
		
		int confirm = JOptionPane.showOptionDialog(
	            null,"¿Estás seguro que quieres agregar la provincia?", 
			             "Agregar provincia", JOptionPane.YES_NO_OPTION,
			             JOptionPane.INFORMATION_MESSAGE, null, null, null);
		if (confirm == 0 && permiteAgregarProvincia()){

			String idPais = obtenerId(this.ventanaAgregarProvincia.getComboBoxPaises().getSelectedItem().toString());
			ProvinciaDTO nuevoProvincia = new ProvinciaDTO();
			
			nuevoProvincia.setNombre(this.ventanaAgregarProvincia.getTxtNombreProvincia().getText());
			nuevoProvincia.setPais(modeloPais.getPaisById(Integer.parseInt(idPais)));
			modeloProvincia.agregarProvincia(nuevoProvincia);
	
			this.ventanaAgregarProvincia.limpiarCampos();
			this.ventanaAgregarProvincia.cerrarVentana();
			JOptionPane.showMessageDialog(null, "Transporte agregado","Transporte", JOptionPane.INFORMATION_MESSAGE);

		}
	}
	
	private boolean permiteAgregarProvincia() {
		boolean ret = true;
		ArrayList<ProvinciaDTO> provDB = (ArrayList<ProvinciaDTO>) modeloProvincia.obtenerProvincias();
		for(ProvinciaDTO p: provDB)
			ret = ret && !(p.getNombre().equals(this.ventanaAgregarProvincia.getTxtNombreProvincia().getText()));
		return ret;
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
			System.out.println("a editar "+nuevaProvincia.getNombre());
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
	private String obtenerId(String s) {
		String ret = "";
		for (int n = 0; n <s.length (); n ++) {
			char c = s.charAt (n);
			if(c != '-') {
				ret+= c;
			}
			else{
				return ret;
			}
		}
		return ret;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {	
	}

	public VentanaAgregarProvincia getVentanaAgregarProvincia() {
		return ventanaAgregarProvincia;
	}

	public void setVentanaAgregarProvincia(VentanaAgregarProvincia ventanaAgregarProvincia) {
		this.ventanaAgregarProvincia = ventanaAgregarProvincia;
	}

	public VentanaEditarProvincia getVentanaEditarProvincia() {
		return ventanaEditarProvincia;
	}

	public void setVentanaEditarProvincia(VentanaEditarProvincia ventanaEditarProvincia) {
		this.ventanaEditarProvincia = ventanaEditarProvincia;
	}

	public ModeloProvincia getModeloProvincia() {
		return modeloProvincia;
	}

	public void setModeloProvincia(ModeloProvincia modeloProvincia) {
		this.modeloProvincia = modeloProvincia;
	}

	public ModeloPais getModeloPais() {
		return modeloPais;
	}

	public void setModeloPais(ModeloPais modeloPais) {
		this.modeloPais = modeloPais;
	}

	public List<ProvinciaDTO> getProvincias_en_tabla() {
		return provincias_en_tabla;
	}

	public void setProvincias_en_tabla(List<ProvinciaDTO> provincias_en_tabla) {
		this.provincias_en_tabla = provincias_en_tabla;
	}

	public int getFilaSeleccionada() {
		return filaSeleccionada;
	}

	public void setFilaSeleccionada(int filaSeleccionada) {
		this.filaSeleccionada = filaSeleccionada;
	}
	
	
}