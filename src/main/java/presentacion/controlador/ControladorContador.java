package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import modelo.Egreso;
import modelo.Local;
import modelo.Login;
import modelo.Pasaje;
import modelo.Rol;
import modelo.Sueldos_Empleados;
import dto.ContadorDTO;
import dto.EgresosDTO;
import dto.LocalDTO;
import dto.LoginDTO;
import dto.PasajeDTO;
import dto.RolDTO;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.reportes.Reporte;
import presentacion.vista.contador.VentanaAgregarServicio;
import presentacion.vista.contador.VentanaAgregarSueldo;
import presentacion.vista.contador.VentanaCambiarContrasena;
import presentacion.vista.contador.VentanaEditarServicio;
import presentacion.vista.contador.VentanaGenerarReporte;
import presentacion.vista.contador.VistaContador;

public class ControladorContador implements ActionListener {

	private VistaContador vistaContador;
	private VentanaAgregarSueldo ventanaAgregarSueldo;
	private VentanaAgregarServicio ventanaAgregarServicio;
	private VentanaEditarServicio ventanaEditarServicio;
	private VentanaGenerarReporte ventanaGenerarReporte;
	private VentanaCambiarContrasena ventanaCambiarContrasenia;
	private ContadorDTO contadorLogueado;
	private Login login;
	private Pasaje pasaje;
	private Local local;
	private Egreso egreso;
	private ControladorSueldo controladorSueldo;
	private ControladorServicio controladorServicio;

	public ControladorContador(VistaContador vistaContador,ContadorDTO contadorLogueado) {
	
		this.vistaContador = vistaContador;		
		this.contadorLogueado = contadorLogueado;
		this.ventanaAgregarSueldo = VentanaAgregarSueldo.getInstance();
		this.ventanaAgregarServicio = VentanaAgregarServicio.getInstance();
		this.ventanaEditarServicio = VentanaEditarServicio.getInstance();
		this.ventanaGenerarReporte = VentanaGenerarReporte.getInstance();
		this.ventanaCambiarContrasenia = VentanaCambiarContrasena.getInstance();
		this.ventanaAgregarSueldo = VentanaAgregarSueldo.getInstance();
		
		this.vistaContador.getItemCambiarContrasenia().addActionListener(dp->mostrarVentanaCambiarContrasenia(dp));
		this.vistaContador.getItemVisualizarSueldos().addActionListener(vs->mostrarPanelSueldos(vs));
		this.vistaContador.getItemSueldos().addActionListener(ve->mostrarVentanaAgregarSueldo(ve));
		
		this.vistaContador.getItemVisualizarServicios().addActionListener(ps->mostrarPanelServicios(ps));
		this.vistaContador.getItemAgregarServicio().addActionListener(as->mostrarVentanaAgregarServicio(as));
		this.vistaContador.getItemEditarServicio().addActionListener(as->mostrarVentanaEditarServicio(as));
		this.vistaContador.getItemEliminarServicio().addActionListener(es->eliminarServicio(es));

		this.vistaContador.getItemIngresosReportes().addActionListener(ir->mostrarVentanaGenerarReportes(ir));
	
//		this.vistaContador.getItemEgresosReportes().addActionListener(er->egresosReportes(er));
		this.ventanaGenerarReporte.getComboBoxFiltro().addActionListener(gr->activarFiltros(gr));	
		this.ventanaGenerarReporte.getComboBoxOpciones().addActionListener(co->activarFiltrosOpciones(co));
		this.ventanaGenerarReporte.getComboBoxLocales().addActionListener(l->activarComboBoxLocales(l));
		this.ventanaGenerarReporte.getBtnGenerarReporte().addActionListener(gr->generarReporte(gr));
				
		this.ventanaCambiarContrasenia.getBtnAceptar().addActionListener(c->cambiarContrasenia(c));
		this.ventanaCambiarContrasenia.getBtnCancelar().addActionListener(c->salirVentanaCambiarContrasenia(c));
		
		this.login = new Login(new DAOSQLFactory());
		this.pasaje = new Pasaje(new DAOSQLFactory());
		this.local = new Local(new DAOSQLFactory());
		this.egreso = new Egreso(new DAOSQLFactory());
		this.contadorLogueado = contadorLogueado;
		this.controladorSueldo = new ControladorSueldo(ventanaAgregarSueldo);
		this.controladorServicio = new ControladorServicio(ventanaAgregarServicio,ventanaEditarServicio);
	}

	public void inicializar(){
		this.vistaContador.mostrarVentana();
		this.vistaContador.getMenuUsuarioLogueado().setText(contadorLogueado.getNombre()+" "+contadorLogueado.getApellido());
	}
	
	private void cambiarContrasenia(ActionEvent c) {
		String passwordActual = new String(this.ventanaCambiarContrasenia.getPassActual().getPassword());
		String passwordConfirmacion1 = new String(this.ventanaCambiarContrasenia.getPassNueva().getPassword());
		String passwordConfirmacion2 = new String(this.ventanaCambiarContrasenia.getConfirmacionContrasena().getPassword());
		
		System.out.println(passwordConfirmacion1+" "+passwordConfirmacion2);
		
		if(!passwordConfirmacion1.equals(passwordConfirmacion2)){
			JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden ", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
		else if(!passwordActual.equals(contadorLogueado.getDatosLogin().getContrasena())){
			JOptionPane.showMessageDialog(null, "La contraseña actual es incorrecta", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}else{
			LoginDTO loginDTO = new LoginDTO();
			loginDTO.setIdDatosLogin(contadorLogueado.getDatosLogin().getIdDatosLogin());
			loginDTO.setUsuario(contadorLogueado.getDatosLogin().getUsuario());
			loginDTO.setRol(contadorLogueado.getDatosLogin().getRol());
			loginDTO.setEstado(contadorLogueado.getDatosLogin().getEstado());
		
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
	
	private void mostrarPanelSueldos(ActionEvent ve) {
		controladorSueldo.llenarTablaEmpleados();
		this.vistaContador.getPanelSueldos().setVisible(true);
		this.vistaContador.getPanelServicios().setVisible(false);
	}	
		
	private void mostrarPanelServicios(ActionEvent ps) {
		controladorServicio.llenarTablaServicios();
		this.vistaContador.getPanelServicios().setVisible(true);
		this.vistaContador.getPanelSueldos().setVisible(false);
	}
	
	private void mostrarVentanaAgregarServicio(ActionEvent as) {
		cargarComboBoxLocales();
		this.vistaContador.getPanelServicios().setVisible(true);
		this.vistaContador.getPanelSueldos().setVisible(false);
		this.ventanaAgregarServicio.mostrarVentana(true);
	}

	private void mostrarVentanaEditarServicio(ActionEvent as) {
		this.vistaContador.getPanelServicios().setVisible(true);
//		controladorServicio.llenarTablaServicios();
		int filaSeleccionada = this.vistaContador.getPanelServicios().getTablaServicios().getSelectedRow();
		if (filaSeleccionada != -1){
			cargarComboBoxLocales();
			controladorServicio.editarServicio(filaSeleccionada);
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
		controladorServicio.llenarTablaServicios();
	}
	
	private void eliminarServicio(ActionEvent es) {
		this.vistaContador.getPanelServicios().setVisible(true);
		int filaSeleccionada = this.vistaContador.getPanelServicios().getTablaServicios().getSelectedRow();
		if (filaSeleccionada != -1){
			controladorServicio.eliminarServicio(filaSeleccionada);
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
		controladorServicio.llenarTablaServicios();
	}

	
	private void mostrarVentanaAgregarSueldo(ActionEvent ve) {
		cargarComboBoxRoles();
		this.vistaContador.getPanelSueldos().setVisible(true);
		this.ventanaAgregarSueldo.mostrarVentana(true);
	}	
		
	
	private void cargarComboBoxRoles(){
		Rol rol = new Rol(new DAOSQLFactory());
		List<RolDTO> rolesDTO = rol.obtenerRoles();
		String[] roles = new String[rolesDTO.size()-1]; //TODO: Puse -1 porque no se deberia cargar el rol "cliente" //VER
		roles[0]="Seleccione un rol";
		for(int i=0; i<rolesDTO.size()-2;i++){
			String rango = rolesDTO.get(i).getNombre();
			roles [i+1] = rango;
		}
		this.ventanaAgregarSueldo.getComboBoxRoles().setModel(new DefaultComboBoxModel(roles));
	}
	
	
	private void mostrarVentanaGenerarReportes(ActionEvent ir) {
		this.ventanaGenerarReporte.mostrarVentana(true);
		this.ventanaGenerarReporte.limpiarCampos();
		this.vistaContador.getPanelServicios().setVisible(false);
		this.vistaContador.getPanelSueldos().setVisible(false);
	}
	
	private void activarFiltros(ActionEvent gr) {
		if(ventanaGenerarReporte.getComboBoxFiltro().getSelectedIndex()!=0){
			this.ventanaGenerarReporte.getComboBoxOpciones().setVisible(true);
			this.ventanaGenerarReporte.getLblSeleccioneOpcion().setVisible(true);
//			this.ventanaGenerarReporte.getDateDesdeChooser().setVisible(true);
//			this.ventanaGenerarReporte.getDateHastaChooser().setVisible(true);
//			this.ventanaGenerarReporte.getLblDesde().setVisible(true);
//			this.ventanaGenerarReporte.getLblHasta().setVisible(true);
		}else{
			this.ventanaGenerarReporte.getComboBoxOpciones().setVisible(false);
			this.ventanaGenerarReporte.getLblSeleccioneOpcion().setVisible(false);

//			this.ventanaGenerarReporte.getDateDesdeChooser().setVisible(false);
//			this.ventanaGenerarReporte.getDateHastaChooser().setVisible(false);
//			this.ventanaGenerarReporte.getLblDesde().setVisible(false);
//			this.ventanaGenerarReporte.getLblHasta().setVisible(false);
		}
	}
	
	private void activarFiltrosOpciones(ActionEvent co) {
		if(ventanaGenerarReporte.getComboBoxOpciones().getSelectedIndex()!=0){
			if(ventanaGenerarReporte.getComboBoxOpciones().getSelectedItem().equals("Local")){
				cargarComboBoxLocales();
				this.ventanaGenerarReporte.getComboBoxLocales().setVisible(true);
				this.ventanaGenerarReporte.getLblLocal().setVisible(true);
			}else{
				this.ventanaGenerarReporte.getComboBoxLocales().setVisible(false);
				this.ventanaGenerarReporte.getLblLocal().setVisible(false);
			}
			if (ventanaGenerarReporte.getComboBoxOpciones().getSelectedItem().equals("General de la empresa")){
				this.ventanaGenerarReporte.getDateDesdeChooser().setVisible(true);
				this.ventanaGenerarReporte.getDateHastaChooser().setVisible(true);
				this.ventanaGenerarReporte.getLblDesde().setVisible(true);
				this.ventanaGenerarReporte.getLblHasta().setVisible(true);
			}else{
				this.ventanaGenerarReporte.getDateDesdeChooser().setVisible(false);
				this.ventanaGenerarReporte.getDateHastaChooser().setVisible(false);
				this.ventanaGenerarReporte.getLblDesde().setVisible(false);
				this.ventanaGenerarReporte.getLblHasta().setVisible(false);
			}
		}else{
			this.ventanaGenerarReporte.getDateDesdeChooser().setVisible(false);
			this.ventanaGenerarReporte.getDateHastaChooser().setVisible(false);
			this.ventanaGenerarReporte.getLblDesde().setVisible(false);
			this.ventanaGenerarReporte.getLblHasta().setVisible(false);
			this.ventanaGenerarReporte.getComboBoxLocales().setVisible(false);
			this.ventanaGenerarReporte.getLblLocal().setVisible(false);
		}
	}
	
	private void activarComboBoxLocales(ActionEvent l) {
		if(ventanaGenerarReporte.getComboBoxLocales().getSelectedIndex()!=0){
			this.ventanaGenerarReporte.getDateDesdeChooser().setVisible(true);
			this.ventanaGenerarReporte.getDateHastaChooser().setVisible(true);
			this.ventanaGenerarReporte.getLblDesde().setVisible(true);
			this.ventanaGenerarReporte.getLblHasta().setVisible(true);
		}else{
			this.ventanaGenerarReporte.getDateDesdeChooser().setVisible(false);
			this.ventanaGenerarReporte.getDateHastaChooser().setVisible(false);
			this.ventanaGenerarReporte.getLblDesde().setVisible(false);
			this.ventanaGenerarReporte.getLblHasta().setVisible(false);
		}
	}
	
	
	
	private void generarReporte(ActionEvent gr) {
		if(!this.ventanaGenerarReporte.getComboBoxFiltro().getSelectedItem().equals("Seleccione")){
			String tipoReporte = this.ventanaGenerarReporte.getComboBoxFiltro().getSelectedItem().toString();
			String opcion = this.ventanaGenerarReporte.getComboBoxOpciones().getSelectedItem().toString();
			java.util.Date dateDesde = ventanaGenerarReporte.getDateDesdeChooser().getDate();
			java.sql.Date fechaDesde = new java.sql.Date(dateDesde.getTime());
			
			java.util.Date dateHasta = ventanaGenerarReporte.getDateHastaChooser().getDate();
			java.sql.Date fechaHasta = new java.sql.Date(dateHasta.getTime());
			

			Reporte reporte = new Reporte();
			if(this.ventanaGenerarReporte.getDateDesdeChooser().getDate().before(this.ventanaGenerarReporte.getDateHastaChooser().getDate())){
				if(tipoReporte.equals("Cliente") && opcion.equals("General de la empresa")){
					List<PasajeDTO> pasajes = pasaje.obtenerPasajesEntreFechas(fechaDesde, fechaHasta);
					if(pasajes.size()!=0){
						reporte.reporteIngresosClientes(pasajes);
						reporte.mostrar();
						this.ventanaGenerarReporte.limpiarCampos();
						this.ventanaGenerarReporte.mostrarVentana(false);
					}else{
						JOptionPane.showMessageDialog(null, "No existen registros de pasajes en ese rango de fechas", "Atención", JOptionPane.WARNING_MESSAGE);	
					}
				}else if(tipoReporte.equals("Cliente") && !this.ventanaGenerarReporte.getComboBoxLocales().getSelectedItem().equals("Seleccione")){
					LocalDTO localSeleccionado = local.obtenerLocal(ventanaGenerarReporte.getComboBoxLocales().getSelectedItem().toString());
					List<PasajeDTO> pasajesClienteByLocal = pasaje.obtenerPasajesEntreFechasByLocal(fechaDesde, fechaHasta, localSeleccionado.getIdLocal());
					if(pasajesClienteByLocal.size()!=0){
						reporte.reporteIngresosClientes(pasajesClienteByLocal);
						reporte.mostrar();
						this.ventanaGenerarReporte.limpiarCampos();
						this.ventanaGenerarReporte.mostrarVentana(false);

					}else{
						JOptionPane.showMessageDialog(null, "No existen registros de pasajes en ese rango de fechas", "Atención", JOptionPane.WARNING_MESSAGE);	
					}
					
				}else if(tipoReporte.equals("Vendedor") && opcion.equals("General de la empresa")){
					List<PasajeDTO> pasajesVendedor = pasaje.obtenerPasajesEntreFechas(fechaDesde, fechaHasta);
					if(pasajesVendedor.size()!=0){
						reporte.reporteIngresosVendedor(pasajesVendedor);
						reporte.mostrar();
						this.ventanaGenerarReporte.limpiarCampos();
						this.ventanaGenerarReporte.mostrarVentana(false);
				}else{
					JOptionPane.showMessageDialog(null, "No existen registros de pasajes en ese rango de fechas", "Atención", JOptionPane.WARNING_MESSAGE);	
				}
			}else if(tipoReporte.equals("Vendedor") && !this.ventanaGenerarReporte.getComboBoxLocales().getSelectedItem().equals("Seleccione")){
				LocalDTO localSeleccionado = local.obtenerLocal(ventanaGenerarReporte.getComboBoxLocales().getSelectedItem().toString());
				List<PasajeDTO> pasajesVendedorByLocal = pasaje.obtenerPasajesEntreFechasByLocal(fechaDesde, fechaHasta, localSeleccionado.getIdLocal());
				if(pasajesVendedorByLocal .size()!=0){
					reporte.reporteIngresosVendedor(pasajesVendedorByLocal );
					reporte.mostrar();
					this.ventanaGenerarReporte.limpiarCampos();
					this.ventanaGenerarReporte.mostrarVentana(false);
				}else{
					JOptionPane.showMessageDialog(null, "No existen registros de pasajes en ese rango de fechas", "Atención", JOptionPane.WARNING_MESSAGE);	
				}
			}else if(tipoReporte.equals("Destino") && opcion.equals("General de la empresa")){
				List<PasajeDTO> pasajesPorDestino = pasaje.obtenerPasajesEntreFechas(fechaDesde, fechaHasta);
				if(pasajesPorDestino.size()!=0){
					reporte.reporteIngresoDestino(pasajesPorDestino);
					reporte.mostrar();
					this.ventanaGenerarReporte.limpiarCampos();
					this.ventanaGenerarReporte.mostrarVentana(false);
			}else{
				JOptionPane.showMessageDialog(null, "No existen registros de pasajes en ese rango de fechas", "Atención", JOptionPane.WARNING_MESSAGE);	
			}
		}else if(tipoReporte.equals("Destino") && !this.ventanaGenerarReporte.getComboBoxLocales().getSelectedItem().equals("Seleccione")){
			LocalDTO localSeleccionado = local.obtenerLocal(ventanaGenerarReporte.getComboBoxLocales().getSelectedItem().toString());
			List<PasajeDTO> pasajesPorDestinoByLocal = pasaje.obtenerPasajesEntreFechasByLocal(fechaDesde, fechaHasta, localSeleccionado.getIdLocal());
			if(pasajesPorDestinoByLocal.size()!=0){
				reporte.reporteIngresoDestino(pasajesPorDestinoByLocal );
				reporte.mostrar();
				this.ventanaGenerarReporte.limpiarCampos();
				this.ventanaGenerarReporte.mostrarVentana(false);
			}else{
				JOptionPane.showMessageDialog(null, "No existen registros de pasajes en ese rango de fechas", "Atención", JOptionPane.WARNING_MESSAGE);	
			}
		}
			}
		}else{
			JOptionPane.showMessageDialog(null, "Error en el ingreso de fechas", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}
	



	private void cargarComboBoxLocales() {
		ventanaGenerarReporte.getComboBoxLocales().removeAllItems();
		ventanaAgregarServicio.getComboBoxLocales().removeAllItems();
		ventanaEditarServicio.getComboBoxLocales().removeAllItems();
		Local local = new Local(new DAOSQLFactory());
		List<LocalDTO> localesDTO = local.readAll();
		String[] locales = new String[localesDTO.size()+1];
		locales[0] = "Seleccione";
		for(int i = 0; i < localesDTO.size(); i++) {
			String rango = localesDTO.get(i).getNombreLocal();
			locales[i+1] = rango;
		}
		this.ventanaGenerarReporte.getComboBoxLocales().setModel(new DefaultComboBoxModel(locales));
		this.ventanaAgregarServicio.getComboBoxLocales().setModel(new DefaultComboBoxModel(locales));
		this.ventanaEditarServicio.getComboBoxLocales().setModel(new DefaultComboBoxModel(locales));
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
    }
}