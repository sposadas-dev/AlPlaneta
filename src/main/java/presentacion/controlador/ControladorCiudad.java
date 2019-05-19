package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import dto.CiudadDTO;
import dto.ProvinciaDTO;
import modelo.ModeloCiudad;
import modelo.ModeloProvincia;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.administrador.TableroDeCiudades;
import presentacion.vista.administrador.VentanaAgregarCiudad;
import presentacion.vista.administrador.VentanaEditarCiudad;
import presentacion.vista.administrador.VentanaPanelGeneral;

public class ControladorCiudad implements ActionListener {

	private VentanaAgregarCiudad ventanaAgregarCiudad;
	private VentanaEditarCiudad ventanaEditarCiudad;
	private ModeloCiudad modeloCiudad;
	private ModeloProvincia modeloProvincia;
	private List<CiudadDTO> ciudades_en_tabla;
	private int filaSeleccionada;
	private VentanaPanelGeneral panel;
	private TableroDeCiudades tableroDeCiudades;

	private static ControladorCiudad INSTANCE;

	public static ControladorCiudad getInstance() {
		if (INSTANCE == null)
			return new ControladorCiudad();
		else
			return INSTANCE;
	}

	private ControladorCiudad() {
		this.ventanaAgregarCiudad = VentanaAgregarCiudad.getInstance();
		this.ventanaEditarCiudad = VentanaEditarCiudad.getInstance();
		this.tableroDeCiudades = TableroDeCiudades.getInstance();
		
		this.tableroDeCiudades.getBtnAgregar().addActionListener(a->mostrarVentanaAgregarCiudad(a));
		this.tableroDeCiudades.getBtnEditar().addActionListener(a->mostrarVentanaEditarCiudad(a));
		
		this.ventanaAgregarCiudad.getBtnAgregar().addActionListener(rc -> agregarCiudad(rc));
		this.ventanaEditarCiudad.getBtnEditar().addActionListener(ac -> editarCiudad(ac));

		this.modeloProvincia = new ModeloProvincia(new DAOSQLFactory());
		this.modeloCiudad = new ModeloCiudad(new DAOSQLFactory());
		this.ciudades_en_tabla = modeloCiudad.obtenerCiudades();
		this.panel = new VentanaPanelGeneral();
	}

	private void mostrarVentanaEditarCiudad(ActionEvent a) {
		this.ventanaEditarCiudad.mostrarVentana();
	}

	private void mostrarVentanaAgregarCiudad(ActionEvent a) {
		llenarComboBoxProvincias();
		this.ventanaAgregarCiudad.mostrarVentana();
	}

	@SuppressWarnings("unchecked")
	private void llenarComboBoxProvincias() {
		List<ProvinciaDTO> provincias = modeloProvincia.obtenerProvincias();

		String[] nombresProvincias = new String[provincias.size()];
		for (int i = 0; i < provincias.size(); i++) {
			String pais = provincias.get(i).getIdProvincia() + "-" + provincias.get(i).getNombre();
			nombresProvincias[i] = pais;
		}
		this.ventanaAgregarCiudad.getComboBoxProvincias().setModel(new DefaultComboBoxModel<Object>(nombresProvincias));
	}

	public void editarProvincia() {
		this.ventanaEditarCiudad.mostrarVentana();
	}

	public void agregarCiudad(ActionEvent rc) {

		int confirm = JOptionPane.showOptionDialog(null, "¿Estás seguro que quieres agregar la provincia?",
				"Agregar provincia", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
		if (confirm == 0 && permiteAgregarCiudad()) {

			String idProvincia = obtenerId(
					this.ventanaAgregarCiudad.getComboBoxProvincias().getSelectedItem().toString());
			CiudadDTO nuevoCiudad = new CiudadDTO();

			nuevoCiudad.setNombre(this.ventanaAgregarCiudad.getTxtNombreCiudad().getText());
			nuevoCiudad.setProvincia(modeloProvincia.getProvinciaById(Integer.parseInt(idProvincia)));
			modeloCiudad.agregarCiudad(nuevoCiudad);

			this.ventanaAgregarCiudad.limpiarCampos();
			this.ventanaAgregarCiudad.cerrarVentana();
			JOptionPane.showMessageDialog(null, "Transporte agregado", "Transporte", JOptionPane.INFORMATION_MESSAGE);

		}
		llenarTablaVistaCiudades();
	}

	private boolean permiteAgregarCiudad() {
		boolean ret = true;
		ArrayList<CiudadDTO> ciudadDB = (ArrayList<CiudadDTO>) modeloCiudad.obtenerCiudades();
		for (CiudadDTO p : ciudadDB)
			ret = ret && !(p.getNombre().equals(this.ventanaAgregarCiudad.getTxtNombreCiudad().getText()));
		return ret;
	}

	public void editarCiudad(ActionEvent ac) {
		int confirm = JOptionPane.showOptionDialog(null, "¿Estás seguro que quieres editar la provincia?",
				"Editar provincia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
		if (confirm == 0) {
			CiudadDTO nuevaCiudad = ciudades_en_tabla.get(this.filaSeleccionada);
			nuevaCiudad.setNombre(this.ventanaEditarCiudad.getTxtNombreCiudad().getText());
			System.out.println("a editar " + nuevaCiudad.getNombre());
			this.modeloCiudad.editarCiudad(nuevaCiudad);
			ventanaEditarCiudad.limpiarCampos();
			ventanaEditarCiudad.dispose();
			JOptionPane.showMessageDialog(null, "Ciudad editada", "Ciudad", JOptionPane.INFORMATION_MESSAGE);

		}
		llenarTablaVistaCiudades();

	}
	public void llenarTablaVistaCiudades(){
		System.out.println("ControladorPais-LlenarTablaPaises");
		
		this.tableroDeCiudades.getModelCiudades().setRowCount(0); //Para vaciar la tabla
		this.tableroDeCiudades.getModelCiudades().setColumnCount(0);
		this.tableroDeCiudades.getModelCiudades().setColumnIdentifiers(this.tableroDeCiudades.getNombreColumnas());

		this.ciudades_en_tabla = modeloCiudad.obtenerCiudades();
			
		for (int i = 0; i < this.ciudades_en_tabla.size(); i++){
			
			String[] fila = {
					this.ciudades_en_tabla.get(i).getProvincia().getNombre(),
					this.ciudades_en_tabla.get(i).getNombre()
			};
			this.tableroDeCiudades.getModelCiudades().addRow(fila);
		}
	}
	
	public void eliminarProvincia(int filaSeleccionada) {
		int confirm = JOptionPane.showOptionDialog(null, "¿Estás seguro que quieres eliminar la provincia?",
				"Eliminar provincia", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
		if (confirm == 0) {
			JOptionPane.showMessageDialog(null, "Provincia eliminada", "Provincia", JOptionPane.INFORMATION_MESSAGE);
			// comentario:
			// RELACIONAR EL VIAJE CON LA PROVINCIA Y PAIS ADEMAS DE CIUDAD PARA
			// DARLE DE BAJA
			// SI ES QUE NO TIENE NINGUN VIAJE RELACIONADO.
			// this.modeloProvincia.borrarProvincia(provincias_en_tabla.get(filaSeleccionada));
		}
	}

	private String obtenerId(String s) {
		String ret = "";
		for (int n = 0; n < s.length(); n++) {
			char c = s.charAt(n);
			if (c != '-') {
				ret += c;
			} else {
				return ret;
			}
		}
		return ret;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	}

	public VentanaAgregarCiudad getVentanaAgregarCiudad() {
		return ventanaAgregarCiudad;
	}

	public void setVentanaAgregarCiudad(VentanaAgregarCiudad ventanaAgregarCiudad) {
		this.ventanaAgregarCiudad = ventanaAgregarCiudad;
	}

	public VentanaEditarCiudad getVentanaEditarCiudad() {
		return ventanaEditarCiudad;
	}

	public void setVentanaEditarCiudad(VentanaEditarCiudad ventanaEditarCiudad) {
		this.ventanaEditarCiudad = ventanaEditarCiudad;
	}

	public ModeloCiudad getModeloCiudad() {
		return modeloCiudad;
	}

	public void setModeloCiudad(ModeloCiudad modeloCiudad) {
		this.modeloCiudad = modeloCiudad;
	}

	public ModeloProvincia getModeloProvincia() {
		return modeloProvincia;
	}

	public void setModeloProvincia(ModeloProvincia modeloProvincia) {
		this.modeloProvincia = modeloProvincia;
	}

	public List<CiudadDTO> getCiudades_en_tabla() {
		return ciudades_en_tabla;
	}

	public void setCiudades_en_tabla(List<CiudadDTO> ciudades_en_tabla) {
		this.ciudades_en_tabla = ciudades_en_tabla;
	}

	public int getFilaSeleccionada() {
		return filaSeleccionada;
	}

	public void setFilaSeleccionada(int filaSeleccionada) {
		this.filaSeleccionada = filaSeleccionada;
	}

	public VentanaPanelGeneral getPanel() {
		return panel;
	}

	public void setPanel(VentanaPanelGeneral panel) {
		this.panel = panel;
	}

	public static ControladorCiudad getINSTANCE() {
		return INSTANCE;
	}

	public static void setINSTANCE(ControladorCiudad iNSTANCE) {
		INSTANCE = iNSTANCE;
	}

	public void mostrarVistaCiudad(){
		this.tableroDeCiudades.show();
	}
}