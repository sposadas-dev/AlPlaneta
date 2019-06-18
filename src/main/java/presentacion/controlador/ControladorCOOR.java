package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dto.CoordinadorDTO;
import dto.EstadoPasajeDTO;
import dto.PasajeDTO;
import dto.RegimenPuntoDTO;
import modelo.Administrativo;
import modelo.Coordinador;
import modelo.EstadoPasaje;
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
		
		this.ventanaGenerarReporte.getBtnGenerarReporte().addActionListener(gv->generarReporteVentas(gv));
		
		this.ventanaGenerarReporte.getBtnGenerarReportePasajes().addActionListener(gv->generarReportePasajes(gv));
		this.ventanaGenerarReporte.getComboBoxFiltro().addActionListener(f->activarFiltroFechas(f));
		
		this.coordinador = new Coordinador(new DAOSQLFactory());
		this.administrativo = new Administrativo(new DAOSQLFactory());
		this.punto = new ModeloRegimenPunto(new DAOSQLFactory()); 
		this.pasaje = new Pasaje(new DAOSQLFactory());
		this.coordinadorLogueado = coordinadorLogueado;
		
//CONTROLADORES		
		this.controladorRegimenPuntos = new ControladorRegimenPuntos();
	
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
	
	
	private void activarFiltroFechas(ActionEvent f) {
		if(ventanaGenerarReporte.getComboBoxFiltro().getSelectedIndex()!=0){
			this.ventanaGenerarReporte.getLblFiltroPasajes().setVisible(true);
			this.ventanaGenerarReporte.getComboBoxFiltro().setVisible(true);
			this.ventanaGenerarReporte.getLblDesde().setVisible(true);
			this.ventanaGenerarReporte.getDateDesdeChooser().setVisible(true);
			this.ventanaGenerarReporte.getLblHasta().setVisible(true);
			this.ventanaGenerarReporte.getDateHastaChooser().setVisible(true);
		}else{
			this.ventanaGenerarReporte.getLblFiltroPasajes().setVisible(true);
			this.ventanaGenerarReporte.getComboBoxFiltro().setVisible(true);
			this.ventanaGenerarReporte.getLblDesde().setVisible(false);
			this.ventanaGenerarReporte.getDateDesdeChooser().setVisible(false);
			this.ventanaGenerarReporte.getLblHasta().setVisible(false);
			this.ventanaGenerarReporte.getDateHastaChooser().setVisible(false);
		}
	}

	private void visualizarVentanaGenerarReportePasajes(ActionEvent l) {
		this.ventanaGenerarReporte.mostrarVentana(true);
		this.ventanaGenerarReporte.getBtnGenerarReportePasajes().setVisible(true);
		this.ventanaGenerarReporte.getBtnGenerarReporte().setVisible(false);
		
		this.ventanaGenerarReporte.getLblFiltroPasajes().setVisible(true);
		this.ventanaGenerarReporte.getComboBoxFiltro().setVisible(true);
		this.ventanaGenerarReporte.getLblDesde().setVisible(false);
		this.ventanaGenerarReporte.getDateDesdeChooser().setVisible(false);
		this.ventanaGenerarReporte.getLblHasta().setVisible(false);
		this.ventanaGenerarReporte.getDateHastaChooser().setVisible(false);
	}
	
	
	private void visualizarReporteEmpleados(ActionEvent l) {
		Reporte reporte = new Reporte();
		reporte.reporteEmpleados(administrativo.obtenerAdministrativosByLocal(coordinadorLogueado.getLocal().getIdLocal()));
		reporte.mostrar();
	}
	
	private void visualizarVentanaGenerarReporte(ActionEvent l) {
		this.ventanaGenerarReporte.limpiarCampos();
		this.ventanaGenerarReporte.mostrarVentana(true);
		this.ventanaGenerarReporte.getBtnGenerarReportePasajes().setVisible(false);
		this.ventanaGenerarReporte.getBtnGenerarReporte().setVisible(true);
		
		this.ventanaGenerarReporte.getLblFiltroPasajes().setVisible(false);
		this.ventanaGenerarReporte.getComboBoxFiltro().setVisible(false);
		this.ventanaGenerarReporte.getLblDesde().setVisible(true);
		this.ventanaGenerarReporte.getDateDesdeChooser().setVisible(true);
		this.ventanaGenerarReporte.getLblHasta().setVisible(true);
		this.ventanaGenerarReporte.getDateHastaChooser().setVisible(true);
		
	}
	
	private void generarReporteVentas(ActionEvent gv){
		if (this.ventanaGenerarReporte.getDateDesdeChooser().getDate() == null || this.ventanaGenerarReporte.getDateHastaChooser() == null) {
			JOptionPane.showMessageDialog(null, "Debe seleccionar ambas fechas para poder visualizar el reporte", "Error", JOptionPane.WARNING_MESSAGE);
		}else{	
			java.util.Date dateDesde = ventanaGenerarReporte.getDateDesdeChooser().getDate();
			java.sql.Date fechaDesde = new java.sql.Date(dateDesde.getTime());
			
			java.util.Date dateHasta = ventanaGenerarReporte.getDateHastaChooser().getDate();
			java.sql.Date fechaHasta = new java.sql.Date(dateHasta.getTime());
			
			List<PasajeDTO> pasajes = pasaje.obtenerPasajesEntreFechas(fechaDesde, fechaHasta,coordinadorLogueado.getLocal().getIdLocal());
			if(this.ventanaGenerarReporte.getDateDesdeChooser().getDate().before(this.ventanaGenerarReporte.getDateHastaChooser().getDate())){
				if(pasajes.size()!=0){
					Reporte reporte = new Reporte();
					reporte.reporteVentas(pasajes);
					reporte.mostrar();
					ventanaGenerarReporte.limpiarCampos();
				}else{
					JOptionPane.showMessageDialog(null, "No existen registros de pasajes en ese rango de fechas", "Atención", JOptionPane.WARNING_MESSAGE);	
				}
			}else{
				JOptionPane.showMessageDialog(null, "Error en el ingreso de fechas", "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private void generarReportePasajes(ActionEvent gv) {
		if(!this.ventanaGenerarReporte.getComboBoxFiltro().getSelectedItem().equals("Seleccione")){
		String estado = this.ventanaGenerarReporte.getComboBoxFiltro().getSelectedItem().toString();
		java.util.Date dateDesde = ventanaGenerarReporte.getDateDesdeChooser().getDate();
		java.sql.Date fechaDesde = new java.sql.Date(dateDesde.getTime());
		
		java.util.Date dateHasta = ventanaGenerarReporte.getDateHastaChooser().getDate();
		java.sql.Date fechaHasta = new java.sql.Date(dateHasta.getTime());
		
		Reporte reporte = new Reporte();
		if(this.ventanaGenerarReporte.getDateDesdeChooser().getDate().before(this.ventanaGenerarReporte.getDateHastaChooser().getDate())){
			EstadoPasaje estadoPasaje = new EstadoPasaje(new DAOSQLFactory());
			if(estado.equals("Todos")){
				List<PasajeDTO> pasajes = obtenerPasajesVendidosYReservados(fechaDesde, fechaHasta);
				
				for(PasajeDTO p: pasajes){
					System.out.println(p.getIdPasaje());
				}
					if(pasajes.size()!=0){
						reporte.reportePasajes(pasajes);
						reporte.mostrar();
						this.ventanaGenerarReporte.limpiarCampos();
					}else{
						JOptionPane.showMessageDialog(null, "No existen registros de pasajes en ese rango de fechas", "Atención", JOptionPane.WARNING_MESSAGE);	

				}
			}else if(estado.equals("Reservados")){
				List<PasajeDTO> pasajesReservados = pasaje.obtenerPasajesConEstado(estadoPasaje.getFormaPagoByName("Reservado"), fechaDesde, fechaHasta,coordinadorLogueado.getLocal().getIdLocal());
					if(pasajesReservados.size()!=0){
						reporte.reportePasajes(pasajesReservados);
						reporte.mostrar();
						this.ventanaGenerarReporte.limpiarCampos();
					}else{
						JOptionPane.showMessageDialog(null, "No existen registros de pasajes en ese rango de fechas", "Atención", JOptionPane.WARNING_MESSAGE);	
					}
			}else if(estado.equals("Vendidos")){
				List<PasajeDTO> pasajesVendidos= pasaje.obtenerPasajesConEstado(estadoPasaje.getFormaPagoByName("Vendido"), fechaDesde, fechaHasta, coordinadorLogueado.getLocal().getIdLocal());
				if(pasajesVendidos.size()!=0){
					reporte.reportePasajes(pasajesVendidos);		
					reporte.mostrar();
					this.ventanaGenerarReporte.limpiarCampos();
				}else{
					JOptionPane.showMessageDialog(null, "No existen registros de pasajes en ese rango de fechas", "Atención", JOptionPane.WARNING_MESSAGE);	
				}
			}
		}else{
			JOptionPane.showMessageDialog(null, "Error en el ingreso de fechas", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}else{
		JOptionPane.showMessageDialog(null, "Debe elegir una opción de la lista desplegable", "Error", JOptionPane.ERROR_MESSAGE);
	}
	}
	private List<PasajeDTO> obtenerPasajesVendidosYReservados(java.sql.Date fechaDesde, java.sql.Date fechaHasta){
		EstadoPasaje estadoPasaje = new EstadoPasaje(new DAOSQLFactory());
		List<PasajeDTO> pasajesVendidos = pasaje.obtenerPasajesConEstado(estadoPasaje.getFormaPagoByName("Vendido"), fechaDesde, fechaHasta,coordinadorLogueado.getLocal().getIdLocal());
		ArrayList<PasajeDTO> pasajes = new ArrayList<PasajeDTO>(pasajesVendidos);
		
		List<PasajeDTO> pasajesReservados = pasaje.obtenerPasajesConEstado(estadoPasaje.getFormaPagoByName("Reservado"), fechaDesde, fechaHasta,coordinadorLogueado.getLocal().getIdLocal());
		pasajes.addAll(pasajesReservados);
		
		return pasajes;
	}
}