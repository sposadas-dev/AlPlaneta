package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import dto.AdministrativoDTO;
import dto.CoordinadorDTO;
import dto.EventoDTO;
import dto.LoginDTO;
import dto.PasajeDTO;
import dto.RegimenPuntoDTO;
import modelo.Administrativo;
import modelo.Coordinador;
import modelo.EstadoPasaje;
import modelo.Login;
import modelo.ModeloEvento;
import modelo.ModeloRegimenPunto;
import modelo.Pasaje;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.reportes.Reporte;
import presentacion.vista.administrativo.VentanaMotivosReprogramacionEvento;
import presentacion.vista.administrativo.VentanaReasignarEvento;
import presentacion.vista.administrativo.VentanaTablaAdministrativos;
import presentacion.vista.coordinador.VentanaCambiarContrasena;
import presentacion.vista.coordinador.VentanaGenerarReporte;
import presentacion.vista.coordinador.VistaCoordinador;
import recursos.Mapper;

public class ControladorCOOR {
	
	private VistaCoordinador vistaCoordinador;
	private VentanaGenerarReporte ventanaGenerarReporte;
	private VentanaCambiarContrasena ventanaCambiarContrasenia;
	private VentanaReasignarEvento ventanaReasignarEvento;
	private VentanaTablaAdministrativos ventanaTablaAdministrativos;
	private VentanaMotivosReprogramacionEvento ventanaMotivos;
	private List<RegimenPuntoDTO> puntos_en_tabla;
	private List<EventoDTO> eventosEnTabla;
	private List<AdministrativoDTO> administrativosEnTabla;
	private Login login;	
	private Mapper mapper;
	private CoordinadorDTO coordinadorLogueado;
	private Coordinador coordinador;
	private Administrativo administrativo;
	private ModeloRegimenPunto punto;
	private ModeloEvento modeloEvento;
	private ControladorRegimenPuntos controladorRegimenPuntos;
	private ControladorEvento controladorEvento;
	private String aceptada="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private DefaultTableModel tableModel;
	private StringBuilder cad = new StringBuilder();
	private Pasaje pasaje;
	private AdministrativoDTO administrativoElegido;
	
	public ControladorCOOR(VistaCoordinador vistaCoordinador,CoordinadorDTO coordinadorLogueado){

		this.vistaCoordinador = vistaCoordinador;
		this.ventanaGenerarReporte = VentanaGenerarReporte.getInstance();
		this.ventanaCambiarContrasenia = VentanaCambiarContrasena.getInstance();
		this.ventanaReasignarEvento = VentanaReasignarEvento.getInstance();
		this.ventanaMotivos = VentanaMotivosReprogramacionEvento.getInstance();
		this.ventanaTablaAdministrativos = VentanaTablaAdministrativos.getInstance();
		

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
		
		this.vistaCoordinador.getItemCambiarContrasenia().addActionListener(dp->mostrarVentanaCambiarContrasenia(dp));
		
		this.ventanaCambiarContrasenia.getBtnAceptar().addActionListener(c->cambiarContrasenia(c));
		this.ventanaCambiarContrasenia.getBtnCancelar().addActionListener(c->salirVentanaCambiarContrasenia(c));

		this.coordinador = new Coordinador(new DAOSQLFactory());
		this.administrativo = new Administrativo(new DAOSQLFactory());
		this.punto = new ModeloRegimenPunto(new DAOSQLFactory()); 
		this.modeloEvento = new ModeloEvento(new DAOSQLFactory());
		this.pasaje = new Pasaje(new DAOSQLFactory());
		this.login = new Login(new DAOSQLFactory());
		this.coordinadorLogueado = coordinadorLogueado;
		this.mapper = new Mapper();
		this.administrativoElegido = null;
		
		this.vistaCoordinador.getItemVisualizarEventos().addActionListener(e->mostrarTodosEventos(e));
		this.vistaCoordinador.getItemReasignarEvento().addActionListener(e->reasignarEvento(e));
		
		this.eventosEnTabla = modeloEvento.obtenerEvento();
		this.administrativosEnTabla = administrativo.obtenerAdministrativos();
//CONTROLADORES		
		this.controladorRegimenPuntos = new ControladorRegimenPuntos();
		this.controladorEvento = new ControladorEvento(modeloEvento,eventosEnTabla);

		this.vistaCoordinador.getPanelEvento().getFiltroApellido().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
					char letra = e.getKeyChar();
					tableModel = (DefaultTableModel) vistaCoordinador.getPanelEvento().getModelEventos();
					TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(tableModel);
					vistaCoordinador.getPanelEvento().getTablaEventos().setRowSorter(tr);
					if (aceptada.indexOf(letra) != -1 || letra == KeyEvent.VK_BACK_SPACE) {
						if (letra == KeyEvent.VK_BACK_SPACE){
							if(cad.length() != 0) {
								cad.deleteCharAt(cad.length()-1);
						        tr.setRowFilter(RowFilter.regexFilter(cad.toString()));
							}
						} else{
							cad.append(String.valueOf(letra));
			    			tr.setRowFilter(RowFilter.regexFilter(cad.toString()));
						}
					}
			}
		});
		
		this.vistaCoordinador.getPanelEvento().getFiltroDesde().addPropertyChangeListener( new PropertyChangeListener() {
		    @Override
		    public void propertyChange(PropertyChangeEvent e) {
		    	String desde = obtenerFecha(e.getNewValue().toString());
		    	if(vistaCoordinador.getPanelEvento().getFiltroHasta().getDate() != null){
		    		String hasta = obtenerFecha(vistaCoordinador.getPanelEvento().getFiltroHasta().getDate().toString());
		    		llenarTablaEventos(modeloEvento.obtenerBetween(desde, hasta));
		    	}
		    }
		});
		
		this.vistaCoordinador.getPanelEvento().getFiltroHasta().addPropertyChangeListener( new PropertyChangeListener() {
		    @Override
		    public void propertyChange(PropertyChangeEvent e) {
		    	String hasta = obtenerFecha(e.getNewValue().toString());
		    	if(vistaCoordinador.getPanelEvento().getFiltroDesde().getDate() != null){
		    		String desde = obtenerFecha(vistaCoordinador.getPanelEvento().getFiltroDesde().getDate().toString());
		    		llenarTablaEventos(modeloEvento.obtenerBetween(desde, hasta));
		    	}
		    }
		});	
		
		this.vistaCoordinador.getPanelEvento().getComboFiltros().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String filtro = vistaCoordinador.getPanelEvento().getComboFiltros().getSelectedItem().toString();
				if(!filtro.equals("Todos"))
					llenarTablaEventos(filtrarEventoSegun(filtro));		
				else {					
					limpiarFiltrosEvento();
					llenarTablaEventos(modeloEvento.obtenerEvento());
				}
			}
		});
		
		this.vistaCoordinador.getPanelEvento().getFiltroNombre().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
					char letra = e.getKeyChar();
					tableModel = (DefaultTableModel) vistaCoordinador.getPanelEvento().getModelEventos();
					TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(tableModel);
					vistaCoordinador.getPanelEvento().getTablaEventos().setRowSorter(tr);
					if (aceptada.indexOf(letra) != -1 || letra == KeyEvent.VK_BACK_SPACE) {
						if (letra == KeyEvent.VK_BACK_SPACE){
							if(cad.length() != 0) {
								cad.deleteCharAt(cad.length()-1);
						        tr.setRowFilter(RowFilter.regexFilter(cad.toString()));
							}
						} else{
							cad.append(String.valueOf(letra));
			    			tr.setRowFilter(RowFilter.regexFilter(cad.toString()));
						}
					}
			}
		});
	}
		


	public void inicializar(){
		this.vistaCoordinador.mostrarVentana();
		this.vistaCoordinador.getMenuUsuarioLogueado().setText(coordinadorLogueado.getNombre()+" "+coordinadorLogueado.getApellido());
		this.llenarTablaRegimenPuntos();
	}
	
	private void cambiarContrasenia(ActionEvent c) {
		
		String passwordActual = new String(this.ventanaCambiarContrasenia.getPassActual().getPassword());
		String passwordConfirmacion1 = new String(this.ventanaCambiarContrasenia.getPassNueva().getPassword());
		String passwordConfirmacion2 = new String(this.ventanaCambiarContrasenia.getConfirmacionContrasena().getPassword());
		
		System.out.println(passwordConfirmacion1+" "+passwordConfirmacion2);
		
		if(!passwordConfirmacion1.equals(passwordConfirmacion2)){
			JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden ", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
		else if(!passwordActual.equals(coordinadorLogueado.getDatosLogin().getContrasena())){
			JOptionPane.showMessageDialog(null, "La contraseña actual es incorrecta", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}else{
			LoginDTO loginDTO = new LoginDTO();
			loginDTO.setIdDatosLogin(coordinadorLogueado.getDatosLogin().getIdDatosLogin());
			loginDTO.setUsuario(coordinadorLogueado.getDatosLogin().getUsuario());
			loginDTO.setRol(coordinadorLogueado.getDatosLogin().getRol());
			loginDTO.setEstado(coordinadorLogueado.getDatosLogin().getEstado());
		
			String password = new String(this.ventanaCambiarContrasenia.getPassNueva().getPassword());
			loginDTO.setContrasena(password);
			this.login.editarLogin(loginDTO);
			this.ventanaCambiarContrasenia.mostrarVentana(false);
			this.ventanaCambiarContrasenia.limpiarCampos();
			JOptionPane.showMessageDialog(null, "Contraseña actualizada", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	private void mostrarVentanaCambiarContrasenia(ActionEvent dp) {
		this.ventanaCambiarContrasenia.limpiarCampos();
		this.ventanaCambiarContrasenia.mostrarVentana(true);
	}
	
	private void salirVentanaCambiarContrasenia(ActionEvent c) {
		this.ventanaCambiarContrasenia.limpiarCampos();
		this.ventanaCambiarContrasenia.mostrarVentana(false);;
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
	
	private void mostrarTodosEventos(ActionEvent e) {
		this.vistaCoordinador.getPanelEvento().mostrarPanelEvento(true);
		limpiarFiltrosEvento();
		llenarTablaEventos(modeloEvento.obtenerEvento());
	}
	
	private void llenarTablaEventos(List<EventoDTO> tabla){
		this.vistaCoordinador.getPanelEvento().getModelEventos().setRowCount(0); //Para vaciar la tabla
		this.vistaCoordinador.getPanelEvento().getModelEventos().setColumnCount(0);
		this.vistaCoordinador.getPanelEvento().getModelEventos().setColumnIdentifiers(this.vistaCoordinador.getPanelEvento().getNombreColumnasEventos());

		this.eventosEnTabla = modeloEvento.obtenerEvento();
			
		for (int i = 0; i < tabla.size(); i++){

			Object[] fila = {
							mapper.parseToString(tabla.get(i).getFechaIngreso()),
							mapper.parseToString(tabla.get(i).getFechaEvento()),
							tabla.get(i).getHoraEvento(),
							tabla.get(i).getDescripcion(),
							tabla.get(i).getCliente().getApellido(),
							tabla.get(i).getCliente().getNombre(),
							tabla.get(i).getAdministrativo().getApellido()+" "+tabla.get(i).getAdministrativo().getNombre(),
							tabla.get(i).getEstadoEvento().getNombre(),
							this.estaReprogramado(tabla.get(i))
			};
							this.vistaCoordinador.getPanelEvento().getModelEventos().addRow(fila);
		}	
		controladorEvento.llenarComboEstados();
	}
	
	public String estaReprogramado(EventoDTO e) {
		if(e.getMotivoReprogramacion().equals(""))
			return "no";
		else
			return "si";
	}
	
	protected String obtenerFecha(String string) {
		String [] aux = string.split(" ");
		String ret="";	
		for(int i=0; i<aux.length; i++) {

			if(i==5)	//año
				ret= aux[5] + ret;
			
			if(i==2)
				ret = ret + aux[2] ; //dia;
			
			if(i==1) {
				if(aux[1].equals("Jan")) //mes
					ret += "01";
				if(aux[1].equals("Feb"))
					ret += "02";
				if(aux[1].equals("Mar"))
					ret += "03";
				if(aux[1].equals("Apr"))
					ret += "04";
				if(aux[1].equals("May"))
					ret += "05";
				if(aux[1].equals("Jun"))
					ret += "06";
				if(aux[1].equals("Jul"))
					ret += "07";
				if(aux[1].equals("Aug"))
					ret += "08";
				if(aux[1].equals("Sep"))
					ret += "09";
				if(aux[1].equals("Oct"))
					ret += "10";
				if(aux[1].equals("Nov"))
					ret += "11";
				if(aux[1].equals("Dec"))
					ret += "12";
			}
		}	
		return ret;
	}
	
	public List<EventoDTO> filtrarEventoSegun(String datoCombo){
		List<EventoDTO> ret = new ArrayList<EventoDTO>();
		for (int i = 0; i < eventosEnTabla.size(); i++) {
			if (eventosEnTabla.get(i).getEstadoEvento().getNombre().compareTo(datoCombo)==0) {
				ret.add(eventosEnTabla.get(i));
			}
		}
		return ret;
	}
	
	private void limpiarFiltrosEvento() {
		this.vistaCoordinador.getPanelEvento().getComboFiltros().setSelectedIndex(0);
	}
	
	private void reasignarEvento(ActionEvent e) {
		int filaSeleccionada = this.vistaCoordinador.getPanelEvento().getTablaEventos().getSelectedRow();
		if (filaSeleccionada != -1){
			verDatosDelEvento(filaSeleccionada);
		}
		else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	private void verDatosDelEvento(int filaSeleccionada) {
		EventoDTO eventoSeleccionado = this.eventosEnTabla.get(filaSeleccionada);
		ventanaReasignarEvento.mostrarVentana(true);
		ventanaReasignarEvento.getFechaEvento().setText(mapper.parseToString(eventoSeleccionado.getFechaEvento()));
		ventanaReasignarEvento.getHoraEvento().setText(eventoSeleccionado.getHoraEvento().toString());
		ventanaReasignarEvento.getTxtDescripcion().setText(eventoSeleccionado.getDescripcion());
		ventanaReasignarEvento.getEstado().setText(eventoSeleccionado.getEstadoEvento().getNombre());
		ventanaReasignarEvento.getTxtDni().setText(eventoSeleccionado.getCliente().getDni());
		ventanaReasignarEvento.getTxtApellido().setText(eventoSeleccionado.getCliente().getApellido());
		ventanaReasignarEvento.getTxtNombre().setText(eventoSeleccionado.getCliente().getNombre());
		ventanaReasignarEvento.getAdministrativo().setText(eventoSeleccionado.getAdministrativo().getApellido()+" "+eventoSeleccionado.getAdministrativo().getNombre());		
		botonesEnReasignarEvento(eventoSeleccionado);
	}



	private void botonesEnReasignarEvento(EventoDTO selec) {
		ventanaReasignarEvento.getBtnReasignar().addActionListener(r->mostrarTablaAdministrativos(r));
		ventanaReasignarEvento.getBtnMotivos().addActionListener(cv->mostrarMotivos(cv,selec));
		ventanaReasignarEvento.getBtnOk().addActionListener(rc->modificarAdministrativoEnEvento(rc,selec));
		ventanaMotivos.getBtnCancelar().addActionListener(a->cerrarVentanaMotivos(a));
	}
	
	private void mostrarTablaAdministrativos(ActionEvent e) {
		ventanaReasignarEvento.mostrarVentana(false);
		ventanaTablaAdministrativos.setVisible(true);
		ventanaTablaAdministrativos.getBtnConfirmar().addActionListener(f->confirmarAdministrativo(f));
		ventanaTablaAdministrativos.getBtnAtras().addActionListener(f->cancelarSeleccionAdministrativo(f));
		llenarTablaAdministrativosSin();
	}
	
	private void llenarTablaAdministrativosSin(){
		ventanaTablaAdministrativos.getModelAdministrativos().setRowCount(0); //Para vaciar la tabla
		ventanaTablaAdministrativos.getModelAdministrativos().setColumnCount(0);
		ventanaTablaAdministrativos.getModelAdministrativos().setColumnIdentifiers(ventanaTablaAdministrativos.getNombreColumnas());
			
		administrativosEnTabla = administrativo.obtenerAdministrativos();
		
		for (int i = 0; i < administrativosEnTabla.size(); i++){
				Object[] fila = {
						administrativosEnTabla.get(i).getApellido(),
						administrativosEnTabla.get(i).getNombre(),
						administrativosEnTabla.get(i).getDni(),
						administrativosEnTabla.get(i).getMail()
				};
			ventanaTablaAdministrativos.getModelAdministrativos().addRow(fila);
		}		
	}
	
	private void confirmarAdministrativo(ActionEvent e) {
		AdministrativoDTO ret = null;
		int filaSeleccionada = this.ventanaTablaAdministrativos.getTablaAdministrativos().getSelectedRow();
		if (filaSeleccionada != -1){
			this.ventanaTablaAdministrativos.mostrarVentana(false);
			ret = administrativosEnTabla.get(filaSeleccionada);
			ventanaReasignarEvento.mostrarVentana(true);
			ventanaReasignarEvento.getAdministrativo().setText(ret.getApellido()+" "+ret.getNombre());
			administrativoElegido = ret;
			ventanaReasignarEvento.getBtnOk().setEnabled(true);
		}
		else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	private void cancelarSeleccionAdministrativo(ActionEvent e) {
		this.ventanaTablaAdministrativos.mostrarVentana(false);
		this.ventanaReasignarEvento.mostrarVentana(true);
		administrativoElegido = null;
		ventanaReasignarEvento.getBtnOk().setEnabled(false);

	}
	
	private void mostrarMotivos(ActionEvent e,EventoDTO selec) {
		ventanaReasignarEvento.setEnabled(false);
		ventanaMotivos.getTxtMotivos().setText(selec.getMotivoReprogramacion());
		ventanaMotivos.mostrarVentana(true);	
	}
	
	private void modificarAdministrativoEnEvento(ActionEvent e,EventoDTO evento) {
			evento.setAdministrativo(administrativoElegido);
			modeloEvento.editarAdminEnEvento(evento);
			ventanaReasignarEvento.mostrarVentana(false);
			llenarTablaEventos(modeloEvento.obtenerEvento());
	}
	
	private void cerrarVentanaMotivos(ActionEvent e) {
		this.ventanaReasignarEvento.setEnabled(true);
		this.ventanaMotivos.limpiarCampos();
		this.ventanaMotivos.cerrarVentana();
	}
}