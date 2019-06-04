package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import dto.PromocionDTO;
import dto.ViajeDTO;
import modelo.ModeloPromocion;
import modelo.ModeloViaje;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.administrativo.PanelPromocion;
import presentacion.vista.administrativo.VentanaRegistrarPromocion;
import presentacion.vista.administrativo.VentanaTablaViajes;

public class ControladorPromocion {

	
	private VentanaTablaViajes ventanaTablaViajes;
	private List<ViajeDTO> viajes_en_tabla;
	private ModeloPromocion promocion;
	private ModeloViaje modeloViaje;
	private VentanaRegistrarPromocion ventanaPromocion;
	private PanelPromocion panelPromocion;
	
	//DATOS PROMOCION:
	private ViajeDTO viaje;
	private int porcentaje;
	private int stock;
	private java.sql.Date fechaVencimiento; 
	private String estado;		
	
	private PromocionDTO promocionRegistrada;

	public ControladorPromocion(VentanaRegistrarPromocion ventanaPromocion, ModeloPromocion promocion, List<PromocionDTO> promociones_en_tabla){

		//DATOS PROMOCION:
		this.viaje = null;
		this.fechaVencimiento = null;	
		this.estado = null;
		
		
		this.ventanaPromocion = ventanaPromocion;
		this.ventanaTablaViajes = VentanaTablaViajes.getInstance();		
		this.promocion = new ModeloPromocion(new DAOSQLFactory());
		this.modeloViaje = new ModeloViaje(new DAOSQLFactory());

		this.ventanaPromocion.getBtnRegistrar().addActionListener(rc->registrarPromocion(rc));
		this.ventanaPromocion.getBtnAsociarViaje().addActionListener(r->mostrarViajes(r));
		this.ventanaPromocion.getBtnCancelar().addActionListener(cv->cerrarVentanaPromocion(cv));

		this.ventanaTablaViajes.getBtnAtras().addActionListener(vc->volverVentanaAgregarPromocion(vc));
		this.ventanaTablaViajes.getBtnConfirmar().addActionListener(ce->asociarViajeEnPromocion(ce));

	}
	public void iniciar(){
		llenarComboEstado();
		llenarComboPorcentaje();
	}
	
	public void llenarComboEstado() {
		String[] estados = new String[2];
		estados[0] = "activa";
		estados[1] = "inactiva";
		this.ventanaPromocion.getComboEstado().setModel(new DefaultComboBoxModel<String>(estados));
	}
	
	public void llenarComboPorcentaje() {
		String [] porcentajes = {"10","15","20","25","30","35","40","45","50","55","60","65","70"};
		this.ventanaPromocion.getComboPorcentaje().setModel(new DefaultComboBoxModel<String>(porcentajes));
	}
	
	public void registrarPromocion(ActionEvent rc){
		if(camposLlenosRegistrarPromocion()){
			this.fechaVencimiento = convertUtilToSql(this.ventanaPromocion.getDateFechaVencimiento().getDate());
			this.porcentaje = Integer.parseInt(this.ventanaPromocion.getComboPorcentaje().getSelectedItem().toString());
			this.stock = Integer.parseInt(this.ventanaPromocion.getTxtStock().getText());
			this.estado = this.ventanaPromocion.getComboEstado().getSelectedItem().toString();
			
			this.promocionRegistrada = new PromocionDTO(0,viaje,porcentaje,stock,fechaVencimiento,estado);
		
			promocion.agregarPromocion(promocionRegistrada);
			this.ventanaPromocion.limpiarCampos();
			this.ventanaPromocion.cerrarVentana();
		}
	}
	

	
/*	public void editarEvento(ActionEvent rc){
		if(camposLlenosEditarEvento()){		
			Date fechaEvento = convertUtilToSql(this.ventanaEditarEvento.getDateFechaEvento().getDate());
			Time horaEvento = obtenerHora(this.ventanaEditarEvento.getComboHora().getSelectedItem().toString(),this.ventanaEditarEvento.getComboMinutos().getSelectedItem().toString());
			String motivoReprogramacion = this.ventanaEditarEvento.getTxtReprogramacion().getText();	
			EstadoEventoDTO estado = obtenerEstadoEventoPorNombre(this.ventanaEditarEvento.getComboEstadoEvento().getSelectedItem().toString());
			
			EventoDTO nuevoEvento = new EventoDTO(eventoSeleccionado.getIdEvento(),fechaIngreso,fechaEvento,horaEvento,descripcion,eventoSeleccionado.getCliente(),administrativoLogueado,estado,motivoReprogramacion,0);
			evento.editarEvento(nuevoEvento);
			
			this.ventanaEditarEvento.limpiarCampos();
			this.ventanaEditarEvento.cerrarVentana();
		}
	}*/
	
	private void mostrarViajes(ActionEvent r) {
		ventanaPromocion.setVisible(false);
		ventanaTablaViajes.setVisible(true);
		llenarTablaViajes();
	}
	
	private void llenarTablaViajes(){
		this.ventanaTablaViajes.getModelViajes().setRowCount(0); //Para vaciar la tabla
		this.ventanaTablaViajes.getModelViajes().setColumnCount(0);
		this.ventanaTablaViajes.getModelViajes().setColumnIdentifiers(this.ventanaTablaViajes.getNombreColumnas());
			
		this.viajes_en_tabla = modeloViaje.obtenerViajes();
		
		for (int i = 0; i < this.viajes_en_tabla.size(); i++){

			Object[] fila = {
					viajes_en_tabla.get(i).getCiudadOrigen().getNombre(),
					viajes_en_tabla.get(i).getCiudadDestino().getNombre(),
					viajes_en_tabla.get(i).getFechaSalida(),
					viajes_en_tabla.get(i).getFechaLlegada(),
					viajes_en_tabla.get(i).getPrecio(),
					viajes_en_tabla.get(i).getHoraSalida(),
					viajes_en_tabla.get(i).getCapacidad(),
					viajes_en_tabla.get(i).getTransporte().getNombre(),
					viajes_en_tabla.get(i).getPrecio()
			};
			this.ventanaTablaViajes.getModelViajes().addRow(fila);
		}		
	}
	
	public void darBajaPromocion(int filaSeleccionada){
			for(PromocionDTO x : promocion.obtenerPromocion()) {
				if(x.getIdPromocion() == filaSeleccionada+1){
					x.setEstado("inactiva");
					promocion.editarPromocion(x);
				}
			}
	}
	
	private boolean camposLlenosRegistrarPromocion(){
		if (ventanaPromocion.getDateFechaVencimiento().getDate() == null || ventanaPromocion.getTxtStock().getText().isEmpty()
				|| ventanaPromocion.getTxtIdViaje().getText().isEmpty()){
				JOptionPane.showMessageDialog(null, "Debe cargar todos los campos", "Mensaje", JOptionPane.ERROR_MESSAGE);
				return false;
		}
			return true;
	}
	
	private void cerrarVentanaPromocion(ActionEvent cv) {
		this.ventanaPromocion.limpiarCampos();
		this.ventanaPromocion.cerrarVentana();
	}
	
	private void volverVentanaAgregarPromocion(ActionEvent vc) {
		this.ventanaTablaViajes.mostrarVentana(false);
		this.ventanaPromocion.mostrarVentana();
	}
	
	/*private void cancelarEditarEvento(ActionEvent vc) {
		this.ventanaEditarEvento.mostrarVentana(false);
	}*/
	
	private void asociarViajeEnPromocion(ActionEvent ce) {
		int filaSeleccionada = this.ventanaTablaViajes.getTablaViajes().getSelectedRow();
		if (filaSeleccionada != -1){
			this.ventanaTablaViajes.mostrarVentana(false);
			viaje = viajes_en_tabla.get(filaSeleccionada);
			this.ventanaPromocion.mostrarVentana();
			this.ventanaPromocion.getTxtIdViaje().setText(viaje.getIdViaje()+"");
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}
		
	private java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }

	public PanelPromocion getPanelPromocion() {
		return panelPromocion;
	}

	public void setPanelPromocion(PanelPromocion panelPromocion) {
		this.panelPromocion = panelPromocion;
	}
	
}