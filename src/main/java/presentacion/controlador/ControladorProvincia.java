package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import dto.PaisDTO;
import dto.ProvinciaDTO;
import dto.ViajeDTO;
import modelo.ModeloPais;
import modelo.ModeloProvincia;
import modelo.ModeloViaje;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.administrador.TableroDeProvincias;
import presentacion.vista.administrador.VentanaAgregarProvincia;
import presentacion.vista.administrador.VentanaEditarProvincia;

public class ControladorProvincia implements ActionListener {

	private VentanaAgregarProvincia ventanaAgregarProvincia;
	private VentanaEditarProvincia ventanaEditarProvincia;
	private ModeloProvincia modeloProvincia;
	private ModeloViaje modeloViaje;
	private ModeloPais modeloPais;
	private List<ProvinciaDTO> provincias_en_tabla;
	private int filaSeleccionada;
	private TableroDeProvincias tableroDeProvincias;
	private static ControladorProvincia INSTANCE;
	
	public static ControladorProvincia getInstance(){
		if(INSTANCE == null)
			return new ControladorProvincia();
		else
			return INSTANCE;
	}
	
	private ControladorProvincia(){
		this.ventanaAgregarProvincia = VentanaAgregarProvincia.getInstance();
		this.ventanaEditarProvincia = VentanaEditarProvincia.getInstance();
		this.tableroDeProvincias = TableroDeProvincias.getInstance();
		
		this.tableroDeProvincias.getBtnAgregar().addActionListener(a->mostrarVentanaAgregarProvincia(a));
		
		this.tableroDeProvincias.getBtnEditar().addActionListener(a->mostrarVentanaEditarProvincia(a));
		this.tableroDeProvincias.getBtnBorrar().addActionListener(a->eliminarProvincia(a));
		
		this.ventanaAgregarProvincia.getBtnAgregar().addActionListener(rc->agregarProvincia(rc));
		this.ventanaAgregarProvincia.getBtnCancelar().addActionListener(c->cancelarVentanaAgregarProvincia(c));
		this.ventanaEditarProvincia.getBtnEditar().addActionListener(ac->editarProvincia(ac));
		this.ventanaEditarProvincia.getBtnCancelar().addActionListener(c->cancelarVentanaEditarProvincia(c));
		
		
		this.modeloViaje = new ModeloViaje(new DAOSQLFactory());
		this.modeloPais = new ModeloPais(new DAOSQLFactory());
		this.modeloProvincia = new ModeloProvincia(new DAOSQLFactory());
		this.provincias_en_tabla = modeloProvincia.obtenerProvincias();
	}

	private void eliminarProvincia(ActionEvent a) {
		eliminarProvincia();
	}

	private void mostrarVentanaEditarProvincia(ActionEvent a) {
		ProvinciaDTO p = provincias_en_tabla.get(this.tableroDeProvincias.getTablaProvincias().getSelectedRow());
		this.ventanaEditarProvincia.getTxtNombreProvincia().setText(p.getNombre());
		this.ventanaEditarProvincia.mostrarVentana();
	}

	private void mostrarVentanaAgregarProvincia(ActionEvent a) {
		llenarComboBoxPaises();
		this.ventanaAgregarProvincia.limpiarCampos();
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
		this.ventanaEditarProvincia.limpiarCampos();
		this.ventanaEditarProvincia.mostrarVentana();
	}
	
	public void agregarProvincia(ActionEvent rc) {
		
			String idPais = obtenerId(this.ventanaAgregarProvincia.getComboBoxPaises().getSelectedItem().toString());
			ProvinciaDTO nuevoProvincia = new ProvinciaDTO();
			
			nuevoProvincia.setNombre(this.ventanaAgregarProvincia.getTxtNombreProvincia().getText());
			nuevoProvincia.setPais(modeloPais.getPaisById(Integer.parseInt(idPais)));
			modeloProvincia.agregarProvincia(nuevoProvincia);
	
			this.ventanaAgregarProvincia.limpiarCampos();
			this.ventanaAgregarProvincia.cerrarVentana();
		llenarTablaVistaProvincias();
	}
	
	private void cancelarVentanaAgregarProvincia(ActionEvent c) {
		this.ventanaAgregarProvincia.limpiarCampos();
		this.ventanaAgregarProvincia.cerrarVentana();
	}

	
	private boolean permiteAgregarProvincia() {
		boolean ret = true;
		ArrayList<ProvinciaDTO> provDB = (ArrayList<ProvinciaDTO>) modeloProvincia.obtenerProvincias();
		for(ProvinciaDTO p: provDB)
			ret = ret && !(p.getNombre().equals(this.ventanaAgregarProvincia.getTxtNombreProvincia().getText()));
		return ret;
	}
	
	public void editarProvincia(ActionEvent ac) {
			ProvinciaDTO p = provincias_en_tabla.get(this.tableroDeProvincias.getTablaProvincias().getSelectedRow());
//			ProvinciaDTO nuevaProvincia = provincias_en_tabla.get(this.filaSeleccionada);
			p.setNombre(this.ventanaEditarProvincia.getTxtNombreProvincia().getText());
			System.out.println("a editar "+p.getNombre());
			this.modeloProvincia.editarProvincia(p);
			ventanaEditarProvincia.limpiarCampos();
			ventanaEditarProvincia.dispose();
		llenarTablaVistaProvincias();
	}
	
	private void cancelarVentanaEditarProvincia(ActionEvent c) {
		this.ventanaEditarProvincia.limpiarCampos();
		this.ventanaEditarProvincia.cerrarVentana();
	}
	
	public void llenarTablaVistaProvincias(){
		this.tableroDeProvincias.getModelProvincias().setRowCount(0); //Para vaciar la tabla
		this.tableroDeProvincias.getModelProvincias().setColumnCount(0);
		this.tableroDeProvincias.getModelProvincias().setColumnIdentifiers(this.tableroDeProvincias.getNombreColumnas());

		this.provincias_en_tabla = modeloProvincia.obtenerProvincias();
		System.out.println("provincias en tabla:"+this.provincias_en_tabla.size());	
		
		for (int i = 0; i < this.provincias_en_tabla.size(); i++){
			String[] fila = {
					this.provincias_en_tabla.get(i).getPais().getNombre(),
					this.provincias_en_tabla.get(i).getNombre()
			};
			this.tableroDeProvincias.getModelProvincias().addRow(fila);
		}
	}
	
	public void eliminarProvincia(){
		ProvinciaDTO p = provincias_en_tabla.get(this.tableroDeProvincias.getTablaProvincias().getSelectedRow());
		this.modeloProvincia.borrarProvincia(p);
		llenarTablaVistaProvincias();
	}
	

	private boolean permiteEliminar(ProvinciaDTO provinciaDTO) {
		ArrayList<ViajeDTO> viajes = (ArrayList<ViajeDTO>) modeloViaje.obtenerViajes();
		System.out.println("cantidad de viajes "+viajes.size());
		for(ViajeDTO v: viajes){
			if(v.getProvinciaOrigen().getIdProvincia() == provinciaDTO.getIdProvincia()||(v.getProvinciaDestino().getIdProvincia() == provinciaDTO.getIdProvincia()))
				return false;
		}	
		return true;
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
	
	public void mostrarVistaProvincia() {
		this.tableroDeProvincias.show();
	}
	
	
	
}