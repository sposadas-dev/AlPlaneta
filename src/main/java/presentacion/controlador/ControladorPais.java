package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dto.PaisDTO;
import dto.ViajeDTO;
import modelo.ModeloPais;
import modelo.ModeloViaje;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.administrador.TableroDePaises;
import presentacion.vista.administrador.VentanaAgregarPais;
import presentacion.vista.administrador.VentanaEditarPais;
import presentacion.vista.administrador.VentanaPanelGeneral;

public class ControladorPais implements ActionListener {

	private VentanaAgregarPais ventanaAgregarPais;
	private VentanaEditarPais ventanaEditarPais;
	private ModeloPais modeloPais;
	private ModeloViaje modeloViaje;
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
		this.tableroDePaises.getBtnBorrar().addActionListener(a->mostrarVentanaBorrarPais(a));
		
		this.ventanaAgregarPais.getBtnAgregar().addActionListener(rc->agregarPais(rc));
		this.ventanaAgregarPais.getBtnCancelar().addActionListener(c->cancelarVentanaAgregarPais(c));
		
		this.ventanaEditarPais.getBtnEditar().addActionListener(ac->editarPais(ac));
		this.ventanaEditarPais.getBtnCancelar().addActionListener(c->cancelarVentanaEditarPais(c));
		
		this.modeloPais = new ModeloPais(new DAOSQLFactory());
		this.modeloViaje = new ModeloViaje(new DAOSQLFactory());
		
		this.pais_en_tabla = modeloPais.obtenerPaises();
		this.panelGeneral = new VentanaPanelGeneral();
	}

	private void mostrarVentanaBorrarPais(ActionEvent a) {
		this.eliminarPais();
	}

	private void mostrarVentanaEditarPais(ActionEvent a) {
		this.ventanaEditarPais.limpiarCampos();
		this.ventanaEditarPais.mostrarVentana();
	}

	private void mostrarVentanaAgregarPais(ActionEvent a) {
		this.ventanaAgregarPais.limpiarCampos();
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
			
		}
		llenarTablaVistaPaises();
	}
	
	private void cancelarVentanaAgregarPais(ActionEvent c) {
		this.ventanaAgregarPais.limpiarCampos();
		this.ventanaAgregarPais.cerrarVentana();
	}
	public void editarPais(ActionEvent ac) {
		
		int confirm = JOptionPane.showOptionDialog(
	            null,"¿Estás seguro que quieres editar el pais?", 
			             "Editar pais", JOptionPane.YES_NO_OPTION,
			             JOptionPane.WARNING_MESSAGE, null, null, null);
		if (confirm == 0){
			String nombrePais = this.ventanaEditarPais.getTxtNombrePais().getText();
			this.modeloPais.editarPais(new PaisDTO(pais_en_tabla.get(this.tableroDePaises.getTablaPaises().getSelectedRow()).getIdPais(),nombrePais));
			System.out.println("se edito el pais");
			ventanaEditarPais.limpiarCampos();
			ventanaEditarPais.cerrarVentana();
			
		}
		llenarTablaVistaPaises();
	}
	
	private void cancelarVentanaEditarPais(ActionEvent c) {
		this.ventanaEditarPais.limpiarCampos();
		this.ventanaEditarPais.cerrarVentana();
	}
	public void eliminarPais(){
		PaisDTO p = pais_en_tabla.get(this.tableroDePaises.getTablaPaises().getSelectedRow());
		System.out.println("ID-A ELIMINAR: "+ p.getIdPais());
		System.out.println("NOMBRE-A ELIMINAR: "+ p.getNombre());
		int confirm = JOptionPane.showOptionDialog(
	            null,"¿Estás seguro que quieres eliminar el pais?", 
			             "Eliminar pais", JOptionPane.YES_NO_OPTION,
			             JOptionPane.WARNING_MESSAGE, null, null, null);
		if (confirm == 0 && permiteEliminar(p) ){
			this.modeloPais.borrarPais(p);
			
		}
		else{
			JOptionPane.showMessageDialog(null, "No pudo eliminarse","Pais", JOptionPane.ERROR_MESSAGE);
		}
		llenarTablaVistaPaises();
	}
	

	private boolean permiteEliminar(PaisDTO paisDTO) {
		ArrayList<ViajeDTO> viajes = (ArrayList<ViajeDTO>) modeloViaje.obtenerViajes();
		System.out.println("cantidad de viajes "+viajes.size());
		for(ViajeDTO v: viajes){
			if(v.getPaisOrigen().getIdPais() == paisDTO.getIdPais()||(v.getPaisDestino().getIdPais() == paisDTO.getIdPais()))
				return false;
		}	
		return true;
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}