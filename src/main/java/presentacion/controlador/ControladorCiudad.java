package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import dto.CiudadDTO;
import dto.PaisDTO;
import dto.ProvinciaDTO;
import dto.ViajeDTO;
import modelo.ModeloCiudad;
import modelo.ModeloProvincia;
import modelo.ModeloViaje;
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
	private ModeloViaje modeloViaje;
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
		
		this.tableroDeCiudades.getBtnBorrar().addActionListener(bc->borrarCiudad(bc));
		
		this.ventanaAgregarCiudad.getBtnAgregar().addActionListener(rc -> agregarCiudad(rc));
		this.ventanaAgregarCiudad.getBtnCancelar().addActionListener(c->cancelarVentanaAgregarCiudad(c));

		this.ventanaEditarCiudad.getBtnEditar().addActionListener(ac -> editarCiudad(ac));
		this.ventanaEditarCiudad.getBtnCancelar().addActionListener(c->cancelarVentanaEditarCiudad(c));
		
		this.modeloProvincia = new ModeloProvincia(new DAOSQLFactory());
		this.modeloCiudad = new ModeloCiudad(new DAOSQLFactory());
		this.modeloViaje = new ModeloViaje(new DAOSQLFactory());
		this.ciudades_en_tabla = modeloCiudad.obtenerCiudades();
		this.panel = new VentanaPanelGeneral();
	}

	private void mostrarVentanaEditarCiudad(ActionEvent a) {
		CiudadDTO c = ciudades_en_tabla.get(this.tableroDeCiudades.getTablaCiudades().getSelectedRow());
		this.ventanaEditarCiudad.getTxtNombreCiudad().setText(c.getNombre());
		this.ventanaEditarCiudad.mostrarVentana();
	}

	private void mostrarVentanaAgregarCiudad(ActionEvent a) {
		llenarComboBoxProvincias();
		this.ventanaAgregarCiudad.limpiarCampos();
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

	public void agregarCiudad(ActionEvent rc) {

		if ( permiteAgregarCiudad() ) {

			String idProvincia = obtenerId(
					this.ventanaAgregarCiudad.getComboBoxProvincias().getSelectedItem().toString());
			CiudadDTO nuevoCiudad = new CiudadDTO();

			nuevoCiudad.setNombre(this.ventanaAgregarCiudad.getTxtNombreCiudad().getText());
			nuevoCiudad.setProvincia(modeloProvincia.getProvinciaById(Integer.parseInt(idProvincia)));
			modeloCiudad.agregarCiudad(nuevoCiudad);

			this.ventanaAgregarCiudad.limpiarCampos();
			this.ventanaAgregarCiudad.cerrarVentana();

		}
		llenarTablaVistaCiudades();
	}

	private void cancelarVentanaEditarCiudad(ActionEvent c) {
		this.ventanaEditarCiudad.limpiarCampos();
		this.ventanaEditarCiudad.cerrarVentana();
	}

	private boolean permiteAgregarCiudad() {
		boolean ret = true;
		ArrayList<CiudadDTO> ciudadDB = (ArrayList<CiudadDTO>) modeloCiudad.obtenerCiudades();
		for (CiudadDTO p : ciudadDB)
			ret = ret && !(p.getNombre().equals(this.ventanaAgregarCiudad.getTxtNombreCiudad().getText()));
		return ret;
	}

	public void editarCiudad(ActionEvent ac) {
		CiudadDTO c = ciudades_en_tabla.get(this.tableroDeCiudades.getTablaCiudades().getSelectedRow());
		c.setNombre(this.ventanaEditarCiudad.getTxtNombreCiudad().getText());
		this.modeloCiudad.editarCiudad(c);
		ventanaEditarCiudad.limpiarCampos();
		ventanaEditarCiudad.dispose();
		llenarTablaVistaCiudades();

	}
	
	private void cancelarVentanaAgregarCiudad(ActionEvent c) {
		this.ventanaAgregarCiudad.limpiarCampos();
		this.ventanaAgregarCiudad.cerrarVentana();
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
	

	private void borrarCiudad(ActionEvent bc) {
		eliminarCiudad();
	}
	
	public void eliminarCiudad(){
		CiudadDTO p = ciudades_en_tabla.get(this.tableroDeCiudades.getTablaCiudades().getSelectedRow());
		if ( permiteEliminar(p) ){
			this.modeloCiudad.borrarCiudad(p);
		}else{
			JOptionPane.showMessageDialog(null, "No pudo eliminarse","Ciudad", JOptionPane.ERROR_MESSAGE);
		}
		llenarTablaVistaCiudades();
	}

	private boolean permiteEliminar(CiudadDTO ciudadDTO) {
		ArrayList<ViajeDTO> viajes = (ArrayList<ViajeDTO>) modeloViaje.obtenerViajes();
		System.out.println("cantidad de viajes "+viajes.size());
		for(ViajeDTO v: viajes){
			if(v.getCiudadOrigen().getIdCiudad() == ciudadDTO.getIdCiudad()||(v.getCiudadDestino().getIdCiudad() == ciudadDTO.getIdCiudad()))
				return false;
		}	
		return true;
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