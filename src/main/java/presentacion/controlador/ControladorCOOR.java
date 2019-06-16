package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JOptionPane;

import dto.CoordinadorDTO;
import dto.RegimenPuntoDTO;
import modelo.Administrativo;
import modelo.Coordinador;
import modelo.ModeloRegimenPunto;
import modelo.Pasaje;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.reportes.Reporte;
import presentacion.vista.coordinador.VentanaGenerarReporte;
import presentacion.vista.coordinador.VistaCoordinador;

public class ControladorCOOR {
	
	private VistaCoordinador vistaCoordinador;
	private VentanaGenerarReporte ventanaGenerarReporte;
	private List<RegimenPuntoDTO> puntos_en_tabla;
	
	private CoordinadorDTO coordinadorLogueado;
	private Coordinador coordinador;
	private Administrativo administrativo;
	private ModeloRegimenPunto punto;
	private ControladorRegimenPuntos controladorRegimenPuntos;
	
	private Pasaje pasaje;
	
	public ControladorCOOR(VistaCoordinador vistaCoordinador,CoordinadorDTO coordinadorLogueado){

		this.vistaCoordinador = vistaCoordinador;
		this.ventanaGenerarReporte = VentanaGenerarReporte.getInstance();

//INSTANCES
		

//MENU ITEMS		
		this.vistaCoordinador.getItemAgregarRegimenPuntos().addActionListener(a->agregarPanelRegimenPuntos(a));
		this.vistaCoordinador.getItemVisualizarRegimenPuntos().addActionListener(vRP->visualizarRegimenPuntos(vRP));
//		this.vistaCoordinador.getItemEditarRegimenPuntos().addActionListener(eRP->editarRegimenPuntos(eRP));
		this.vistaCoordinador.getItemEliminarRegimenPuntos().addActionListener(dt->eliminarRegimenPuntos(dt));
		
		this.vistaCoordinador.getItemListaEmpleados().addActionListener(l->visualizarReporteEmpleados(l));
		this.vistaCoordinador.getItemVentas().addActionListener(l->visualizarVentanaGenerarReporte(l));
		this.vistaCoordinador.getItemPasajes().addActionListener(l->visualizarVentanaGenerarReportePasajes(l));
//		this.vistaCoordinador.getPanelRegimenPuntos().getBtnRecargarTabla().addActionListener(r->recargarTabla(r));
		this.ventanaGenerarReporte.getBtnGenerarReporte().addActionListener(gv->generarReporteVentas(gv));
//		this.ventanaGenerarReporte.getBtnGenerarReportePasajes().addActionListener(gv->generarReportePasajes(gv));

		
		this.coordinador = new Coordinador(new DAOSQLFactory());
		this.administrativo = new Administrativo(new DAOSQLFactory());
		this.punto = new ModeloRegimenPunto(new DAOSQLFactory()); 
		this.pasaje = new Pasaje(new DAOSQLFactory());
		this.coordinadorLogueado = coordinadorLogueado;
		
//CONTROLADORES		
		this.controladorRegimenPuntos = new ControladorRegimenPuntos();
	
	} 



	private void visualizarVentanaGenerarReportePasajes(ActionEvent l) {
		this.ventanaGenerarReporte.mostrarVentana(true);
		this.ventanaGenerarReporte.getBtnGenerarReportePasajes().setVisible(true);
		this.ventanaGenerarReporte.getBtnGenerarReporte().setVisible(false);

	}

	public void inicializar(){
		this.vistaCoordinador.mostrarVentana();
		this.llenarTablaRegimenPuntos();
		
	}
	
	//------------------------------REgimen Puntos-------------------------------------------------
	
	private void visualizarRegimenPuntos(ActionEvent vfp) {
		mostrarVentanaPuntosConValores();
		
	}
	private void mostrarVentanaPuntosConValores() {
		this.controladorRegimenPuntos.mostrarVentanaRegimenPuntos();
	}

	/*Agrega el panel de RegimenPuntos en la vistaPrinciapal del coordinador*/ 
	private void agregarPanelRegimenPuntos(ActionEvent afp) {
		controladorRegimenPuntos.mostrarVentanaRegistroRegimenPuntosConDarAlta();
	}
	
//	private void editarRegimenPuntos(ActionEvent efp) {
//		this.vistaCoordinador.getPanelRegimenPuntos().mostrarPanelRegimenPuntos(true);
//		int filaSeleccionada = this.vistaCoordinador.getPanelRegimenPuntos().getTablaRegimenPuntos().getSelectedRow();
//		if (filaSeleccionada != -1){
//			controladorRegimenPuntos.ModificarRegimenPuntos(filaSeleccionada);
//			llenarTablaRegimenPuntos(); 
//
//		}else{
//			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
//		}
//		llenarTablaRegimenPuntos();
//	} 
	
	private void eliminarRegimenPuntos(ActionEvent dt) {
		controladorRegimenPuntos.eliminarPunto();
	}
 
	public void llenarTablaRegimenPuntos(){
		this.vistaCoordinador.getPanelRegimenPuntos().getModelRegimenPuntos().setRowCount(0); //Para vaciar la tabla
		this.vistaCoordinador.getPanelRegimenPuntos().getModelRegimenPuntos().setColumnCount(0);
		this.vistaCoordinador.getPanelRegimenPuntos().getModelRegimenPuntos().setColumnIdentifiers(this.vistaCoordinador.getPanelRegimenPuntos().getNombreColumnasRegimenPuntos());
			
		this.puntos_en_tabla = punto.obtenerPunto();
			
		for (int i = 0; i < this.puntos_en_tabla.size(); i++){
			Object[] fila = {
					this.puntos_en_tabla.get(i).getPunto(),
					this.puntos_en_tabla.get(i).getARS(),
					this.puntos_en_tabla.get(i).getVencimiento()
					
			};
			this.vistaCoordinador.getPanelRegimenPuntos().getModelRegimenPuntos().addRow(fila);
		}		
	}
	
	private void visualizarReporteEmpleados(ActionEvent l) {
		Reporte reporte = new Reporte();
		reporte.reporteEmpleados(administrativo.obtenerAdministrativos());
		reporte.mostrar();
	}
	
	private void visualizarVentanaGenerarReporte(ActionEvent l) {
		this.ventanaGenerarReporte.mostrarVentana(true);
		this.ventanaGenerarReporte.getBtnGenerarReportePasajes().setVisible(false);
		this.ventanaGenerarReporte.getBtnGenerarReporte().setVisible(true);
	}
	
	private void generarReporteVentas(ActionEvent gv){
		Reporte reporte = new Reporte();
		reporte.reporteVentas(pasaje.obtenerPasajes());
		reporte.mostrar();
	}

}
