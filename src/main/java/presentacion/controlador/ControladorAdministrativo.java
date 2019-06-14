package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import dto.AdministrativoDTO;
import dto.ClienteDTO;
import dto.EventoDTO;
import dto.LoginDTO;
import dto.MedioContactoDTO;
import dto.PasajeDTO;
import dto.PromocionDTO;
import dto.RolDTO;
import modelo.Cliente;
import modelo.ModeloEvento;
import modelo.ModeloPromocion;
import modelo.Pasaje;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.administrativo.VentanaEditarCliente;
import presentacion.vista.administrativo.VentanaEditarEvento;
import presentacion.vista.administrativo.VentanaRegistrarCliente;
import presentacion.vista.administrativo.VentanaRegistrarEvento;
import presentacion.vista.administrativo.VentanaRegistrarPromocion;
import presentacion.vista.administrativo.VentanaVisualizarClientes;
import presentacion.vista.administrativo.VentanaVisualizarPasaje;
import presentacion.vista.administrativo.VistaAdministrativo;

public class ControladorAdministrativo implements ActionListener {

	private VistaAdministrativo vista;

	private VentanaRegistrarCliente ventanaCliente;
	private VentanaRegistrarEvento ventanaEvento;
	private VentanaRegistrarPromocion ventanaPromocion;
	private VentanaEditarEvento ventanaEditarEvento;
	private VentanaVisualizarClientes ventanaVisualizarCliente;
	private VentanaRegistrarCliente ventanaRegistrarCliente;
	private VentanaEditarCliente ventanaEditarCliente;
	private VentanaVisualizarPasaje ventanaVisualizarPasaje;
	private AdministrativoDTO administrativoLogueado;
	private List<ClienteDTO> clientes_en_tabla;
	private List<PasajeDTO> pasajes_en_tabla;
	private List<PromocionDTO> promociones_en_tabla;

	private List<ClienteDTO> clientes_aux;
	private List<PasajeDTO> pasajes_aux;
	
	private List<EventoDTO> eventos_en_tabla;
	private Cliente cliente;
	private Pasaje pasaje;
	private ModeloEvento evento;
	private ControladorPasaje controladorPasaje;
	private ModeloPromocion promocion;
	private ControladorCliente controladorCliente;
	private int filaSeleccionada;
	private ControladorEvento controladorEvento;
	private ControladorPromocion controladorPromocion;
	private controladorDatosLogin controladorDatosLogin;

	private static ControladorAdministrativo INSTANCE;
	
	public static ControladorAdministrativo getInstance(){
		if(INSTANCE == null)
			return new ControladorAdministrativo();
		else
			return INSTANCE;
	}
	
	public ControladorAdministrativo(VistaAdministrativo vista,AdministrativoDTO administrativoLogueado) {
	
		this.vista = vista;
		this.ventanaCliente = VentanaRegistrarCliente.getInstance();
		this.ventanaEvento = VentanaRegistrarEvento.getInstance(); 
		this.ventanaEditarEvento = VentanaEditarEvento.getInstance();

		this.ventanaVisualizarCliente = VentanaVisualizarClientes.getInstance();
		this.ventanaRegistrarCliente = VentanaRegistrarCliente.getInstance();
		this.ventanaEditarCliente = VentanaEditarCliente.getInstance();

		this.ventanaVisualizarPasaje = VentanaVisualizarPasaje.getInstance();
		
		this.ventanaPromocion = VentanaRegistrarPromocion.getInstance();
		
		this.vista.getItemRegistrarCliente().addActionListener(ac->mostrarVentanaAgregarCliente(ac));
		this.vista.getItemVisualizarClientes().addActionListener(ac->agregarPanelClientes(ac));
		this.vista.getItemEditarCliente().addActionListener(mve->mostrarVentanaEditarCliente(mve));
		this.vista.getItemActivarCliente().addActionListener(acc->activarCliente(acc));
		this.vista.getItemDesactivarCliente().addActionListener(dc->desactivarCliente(dc));
        this.vista.getItemRestablecerContrasena().addActionListener(r->restablecerContrasena(r));

		this.vista.getItemVisualizarPasajes().addActionListener(ap->mostrarPasajes(ap));
		this.vista.getItemAgregarPasaje().addActionListener(ap->mostrarVentanaAgregarPasaje(ap));
		this.vista.getItemEditarPasaje().addActionListener(ep->mostrarVentanaEditarPasaje(ep));
		this.vista.getItemCancelarPasaje().addActionListener(cp->cancelarPasaje(cp));
		
		this.vista.getPanelCliente().getActivos().addActionListener(sa->cargarActivos(sa));
		this.vista.getPanelCliente().getInactivos().addActionListener(si->cargarInactivos(si));
		
		this.ventanaEditarCliente.getBtnEditar().addActionListener(ec->editarCliente(ec));
//		this.vista.getPanelPasaje().getBtnVisualizarPasaje().addActionListener(vp->verDatosPasaje(vp));
		
		this.vista.getPanelPasaje().getCancelCheckBox().addActionListener(ccb->cargarCancelados(ccb));
		this.vista.getPanelPasaje().getPendCheckBox().addActionListener(pcb->cargarPendientes(pcb));
		this.vista.getPanelPasaje().getReserCheckBox().addActionListener(rcb->cargarReservados(rcb));
		this.vista.getPanelPasaje().getVendCheckBox().addActionListener(vcb->cargarVendidos(vcb));
		
		
//		this.vista.getPanelPasaje().getBtnBuscar().addActionListener(b->filtrar(b));
//		this.vista.getPanelPasaje().getBtnBorrarFiltros().addActionListener(bf->borrarFiltros(bf));
		
		this.vista.getItemAgregarEvento().addActionListener(ac->mostrarVentanaAgregarEvento(ac));
		this.vista.getItemVisualizarEventos().addActionListener(ac->mostrarEventos(ac));
		this.vista.getItemEditarEvento().addActionListener(ac->mostrarVentanaEditarEvento(ac));
		this.ventanaEditarEvento.getBtnEditar().addActionListener(ac->actualizarTablaEventos(ac));
		this.ventanaEvento.getBtnRegistrar().addActionListener(ac->actualizarTablaEventos(ac));
		this.vista.getPanelEvento().getBtnBuscar().addActionListener(b->filtrarEvento(b));
		this.vista.getPanelEvento().getBtnBorrarFiltros().addActionListener(bf->borrarFiltrosEvento(bf));
		
		this.vista.getItemAgregarPromocion().addActionListener(ac->mostrarVentanaAgregarPromocion(ac));
		this.vista.getItemVisualizarPromociones().addActionListener(ac->mostrarPromociones(ac));
		this.vista.getItemDarBajaPromocion().addActionListener(ac->darBajaPromocion(ac));
		this.ventanaPromocion.getBtnRegistrar().addActionListener(ac->actualizarTablaPromocion(ac));
		
		this.administrativoLogueado = administrativoLogueado;
		this.cliente = new Cliente(new DAOSQLFactory());
		this.pasaje = new Pasaje(new DAOSQLFactory());
		this.evento = new ModeloEvento(new DAOSQLFactory());
		this.promocion = new ModeloPromocion(new DAOSQLFactory());
		
		controladorPasaje = new ControladorPasaje(ventanaVisualizarCliente,cliente,administrativoLogueado);

		controladorCliente = new ControladorCliente(ventanaRegistrarCliente, ventanaEditarCliente, cliente);

		controladorEvento = new ControladorEvento(ventanaEvento, evento, administrativoLogueado, this.eventos_en_tabla);
		
        controladorPromocion = new ControladorPromocion(ventanaPromocion, promocion, this.promociones_en_tabla);
        controladorDatosLogin = new controladorDatosLogin();

	}

	private void restablecerContrasena(ActionEvent r) {
		controladorDatosLogin.restablecerContrasena();
	}

	public void cargarCancelados(ActionEvent ccb) {
		this.llenarTablaPasajes();
	}
	
	public void cargarPendientes(ActionEvent pcb) {
		this.llenarTablaPasajes();
	}
	
	public void cargarReservados(ActionEvent rcb) {
		this.llenarTablaPasajes();
	}
	
	public void cargarVendidos(ActionEvent vcb) {
		this.llenarTablaPasajes();
	}
	
	public void cargarInactivos(ActionEvent si) {
		this.llenarTablaClientes();
	}


	public void cargarActivos(ActionEvent sa) {
		this.llenarTablaClientes();
	}
	
	public ControladorAdministrativo(){
		super();
	}
	
	public void inicializar(){
		this.vista.mostrarVentana();
		this.llenarTablaPasajes(pasaje.obtenerPasajes());
		controladorEvento.controlarNotificacionesInicioSesion();
		controladorEvento.controlarNotificacionesContinuo();
	}
	
	private void agregarPanelClientes(ActionEvent ac) {
		this.vista.getPanelCliente().mostrarPanelCliente(true);
		this.vista.getPanelPasaje().mostrarPanelPasaje(false);
		this.vista.getPanelEvento().mostrarPanelEvento(false);
		this.vista.getPanelPromocion().mostrarPanelPromocion(false);
		this.llenarTablaClientes();
	}
	// ------------------------------------------- Desactivar Cliente ------------------------

	private void editarCliente(ActionEvent ec) {

		java.util.Date dateFechaNacimiento = this.ventanaEditarCliente.getDateFechaNacimiento().getDate();
		java.sql.Date fechaNacimientoCliente = new java.sql.Date(dateFechaNacimiento.getTime());
		
		String estado = "activo";
		int idCliente = this.clientes_en_tabla.get(this.filaSeleccionada).getIdCliente();
		String nombreCliente = this.ventanaEditarCliente.getTxtNombre().getText();
		String apellidoCliente = this.ventanaEditarCliente.getTxtApellido().getText();
		String dniCliente = this.ventanaEditarCliente.getTxtDni().getText();
		RolDTO rolCliente = new RolDTO(
				this.clientes_en_tabla.get(this.filaSeleccionada).getLogin().getRol().getIdRol(),
				this.clientes_en_tabla.get(this.filaSeleccionada).getLogin().getRol().getNombre()
				);
		String contrasenia = cliente.getByClienteById(idCliente).getLogin().getContrasena();
		
		LoginDTO loginCliente = new LoginDTO(
				this.clientes_en_tabla.get(this.filaSeleccionada).getLogin().getIdDatosLogin(),
				this.ventanaEditarCliente.getTxtUsuario().getText(),
				contrasenia,
				rolCliente,
				estado
				);
		
		MedioContactoDTO medioContactoCliente= new MedioContactoDTO(
				this.clientes_en_tabla.get(this.filaSeleccionada).getMedioContacto().getIdMedioContacto(),
				this.ventanaEditarCliente.getTxtTelefonoFijo().getText(),
				this.ventanaEditarCliente.getTxtTelefonoCelular().getText(),
				this.ventanaEditarCliente.getTxtEmail().getText()
				);
		
		ClienteDTO clienteEditable = new ClienteDTO(idCliente, nombreCliente, apellidoCliente, dniCliente, fechaNacimientoCliente, medioContactoCliente, loginCliente);
		controladorCliente.editarCliente(clienteEditable);
		
		this.llenarTablaClientes();
		
	}
	
	private void mostrarVentanaEditarCliente(ActionEvent mve) {
		this.vista.getPanelCliente().mostrarPanelCliente(true);
		int clienteAEditar = this.vista.getPanelCliente().getTablaClientes().getSelectedRow();
		if (clienteAEditar != -1){
			mostrarCliente(clienteAEditar);
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void mostrarCliente(int filaSeleccionada){
		this.filaSeleccionada = filaSeleccionada;
		this.ventanaEditarCliente.mostrarVentana();
		
		this.ventanaEditarCliente.getTxtNombre().setText(this.clientes_en_tabla.get(this.filaSeleccionada).getNombre());
		this.ventanaEditarCliente.getTxtApellido().setText(this.clientes_en_tabla.get(this.filaSeleccionada).getApellido());
		this.ventanaEditarCliente.getTxtDni().setText(this.clientes_en_tabla.get(this.filaSeleccionada).getDni());
		this.ventanaEditarCliente.getDateFechaNacimiento().setDate(this.clientes_en_tabla.get(this.filaSeleccionada).getFechaNacimiento());
		this.ventanaEditarCliente.getTxtUsuario().setText(this.clientes_en_tabla.get(this.filaSeleccionada).getLogin().getUsuario());
		this.ventanaEditarCliente.getTxtTelefonoFijo().setText(this.clientes_en_tabla.get(this.filaSeleccionada).getMedioContacto().getTelefonoFijo());
		this.ventanaEditarCliente.getTxtTelefonoCelular().setText(this.clientes_en_tabla.get(this.filaSeleccionada).getMedioContacto().getTelefonoCelular());
		this.ventanaEditarCliente.getTxtEmail().setText(this.clientes_en_tabla.get(this.filaSeleccionada).getMedioContacto().getEmail());
		
	}
	
	private void desactivarCliente(ActionEvent dc) {
		this.vista.getPanelCliente().mostrarPanelCliente(true);
		int clienteSeleccionado = this.vista.getPanelCliente().getTablaClientes().getSelectedRow();
		if (clienteSeleccionado != -1){
			int idLogin = this.clientes_en_tabla.get(clienteSeleccionado).getLogin().getIdDatosLogin();
			controladorCliente.desactivarCliente(idLogin);
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
		this.llenarTablaClientes();
	}
	private void activarCliente(ActionEvent acc) {
		this.vista.getPanelCliente().mostrarPanelCliente(true);
		int clienteSeleccionado = this.vista.getPanelCliente().getTablaClientes().getSelectedRow();
		if (clienteSeleccionado != -1){
			int idLogin = this.clientes_en_tabla.get(clienteSeleccionado).getLogin().getIdDatosLogin();
			controladorCliente.activarCliente(idLogin);
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
		this.llenarTablaClientes();
	}
	//----------------------------------------------------------------------------------------
	private void mostrarVentanaAgregarPasaje(ActionEvent ap) {
		this.vista.getPanelPasaje().mostrarPanelPasaje(true);
		this.vista.getPanelCliente().mostrarPanelCliente(false);
		this.ventanaVisualizarCliente.mostrarVentana(true);
		this.llenarTablaPasajes(pasaje.obtenerPasajes());
		
		controladorPasaje.iniciar();
	}
	
	private void mostrarVentanaEditarPasaje(ActionEvent ep) {
		this.vista.getPanelPasaje().mostrarPanelPasaje(true);
		int filaSeleccionada = this.vista.getPanelPasaje().getTablaReservas().getSelectedRow();
		if (filaSeleccionada != -1){
			controladorPasaje.editarPasaje(filaSeleccionada);
			llenarTablaPasajes(pasaje.obtenerPasajes());
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	private void mostrarVentanaEditarEvento(ActionEvent ep) {
		int filaSeleccionada = this.vista.getPanelEvento().getTablaEventos().getSelectedRow();
		if (filaSeleccionada != -1){
			verDatosDelEvento(filaSeleccionada);
//			llenarTablaEventos(evento.obtenerEvento());
//			controladorEvento.llenarMotivos(this.eventos_en_tabla.get(filaSeleccionada));
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	/*private void mostrarVentanaEditarPromocion(ActionEvent ep) {
		int filaSeleccionada = this.vista.getPanelPromocion().getTablaPromocion().getSelectedRow();
		if (filaSeleccionada != -1){
			verDatosDeLaPromocion(filaSeleccionada);
			llenarTablaPromociones();
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}	
	}*/
	
	private void verDatosDelEvento(int filaSeleccionada) {
		controladorEvento.llenarComboEstados();
		controladorEvento.llenarComboHora();
		controladorEvento.llenarComboMinutos();
        controladorEvento.setEventoSeleccionado(this.eventos_en_tabla.get(filaSeleccionada));
        System.out.println(this.eventos_en_tabla.get(filaSeleccionada).getMotivoReprogramacion()+"DATO VIEJO");
		//controladorEvento.llenarMotivos(this.eventos_en_tabla.get(filaSeleccionada));
		if (filaSeleccionada != -1){
			
			ventanaEditarEvento.mostrarVentana(true);
			ventanaEditarEvento.getDateFechaEvento().setDate(this.eventos_en_tabla.get(filaSeleccionada).getFechaEvento());
			ventanaEditarEvento.getComboHora().setSelectedItem(obtenerHora(this.eventos_en_tabla.get(filaSeleccionada).getHoraEvento()));
			ventanaEditarEvento.getComboMinutos().setSelectedItem(obtenerMinutos(this.eventos_en_tabla.get(filaSeleccionada).getHoraEvento()));
			
			ventanaEditarEvento.getTxtDescripcion().setText(this.eventos_en_tabla.get(filaSeleccionada).getDescripcion());
			ventanaEditarEvento.getComboEstadoEvento().setSelectedItem(this.eventos_en_tabla.get(filaSeleccionada).getEstadoEvento().getNombre());
			ventanaEditarEvento.getTxtDni().setText(this.eventos_en_tabla.get(filaSeleccionada).getCliente().getDni());
			ventanaEditarEvento.getTxtApellido().setText(this.eventos_en_tabla.get(filaSeleccionada).getCliente().getApellido());
			ventanaEditarEvento.getTxtNombre().setText(this.eventos_en_tabla.get(filaSeleccionada).getCliente().getNombre());
			
		}
		else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private String obtenerHora(Time horario) {
		String[] horarioString = horario.toString().split(":");
		return horarioString[0];
	}
	
	private String obtenerMinutos(Time horario) {
		String[] horarioString = horario.toString().split(":");
		return horarioString[1];
	}
	
	/*private void verDatosDeLaPromocion(int filaSeleccionada) {
		controladorPromocion.setPromocionSeleccionada(this.promociones_en_tabla.get(filaSeleccionada));
		if (filaSeleccionada != -1){
			ventanaEditarPromocion.mostrarVentana(true);
			ventanaEditarPromocion.getComboPorcentaje().setSelectedItem(this.promociones_en_tabla.get(filaSeleccionada).getPorcentaje().toString());
			ventanaEditarPromocion.get.setText(this.promociones_en_tabla.get(filaSeleccionada).getStock());
		}
		else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}*/

	private void cancelarPasaje(ActionEvent cp) {
		this.vista.getPanelPasaje().mostrarPanelPasaje(true);
		int filaSeleccionada = this.vista.getPanelPasaje().getTablaReservas().getSelectedRow();
		if (filaSeleccionada != -1){
			controladorPasaje.eliminarPasaje(filaSeleccionada);
			llenarTablaPasajes(pasaje.obtenerPasajes());
		
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void mostrarPasajes(ActionEvent ap) {
		this.vista.getPanelCliente().mostrarPanelCliente(false);
		this.vista.getPanelPasaje().mostrarPanelPasaje(true);
		this.vista.getPanelEvento().mostrarPanelEvento(false);
		this.vista.getPanelPromocion().mostrarPanelPromocion(false);
		this.llenarTablaPasajes(pasaje.obtenerPasajes());
	}
	
	private void mostrarEventos(ActionEvent ap) {
		this.vista.getPanelEvento().mostrarPanelEvento(true);
		this.vista.getPanelCliente().mostrarPanelCliente(false);
		this.vista.getPanelPasaje().mostrarPanelPasaje(false);
		this.vista.getPanelPromocion().mostrarPanelPromocion(false);
		limpiarFiltrosEvento();
		this.llenarTablaEventos(evento.obtenerEvento());
		this.controladorEvento.actualizarEventosVistos();
	}
	
	private void mostrarPromociones(ActionEvent ap) {
		this.vista.getPanelEvento().mostrarPanelEvento(false);
		this.vista.getPanelCliente().mostrarPanelCliente(false);
		this.vista.getPanelPasaje().mostrarPanelPasaje(false);
		this.vista.getPanelPromocion().mostrarPanelPromocion(true);
		this.llenarTablaPromociones();
	}
	
	private void darBajaPromocion(ActionEvent v) {
		this.vista.getPanelPromocion().mostrarPanelPromocion(true);
		int promoSeleccionada = this.vista.getPanelPromocion().getTablaPromocion().getSelectedRow();
		if (promoSeleccionada != -1){
			controladorPromocion.darBajaPromocion(promoSeleccionada);
			llenarTablaPromociones();
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
		this.llenarTablaClientes();
	}
	

	private void mostrarVentanaAgregarCliente(ActionEvent ac)  {
		this.vista.getPanelCliente().mostrarPanelCliente(true);
		this.vista.getPanelPasaje().mostrarPanelPasaje(false);
		this.llenarTablaClientes();
		this.ventanaRegistrarCliente.limpiarCampos();
		this.ventanaCliente.limpiarCampos();
		this.ventanaCliente.mostrarVentana();
//		ControladorCliente controladorCliente = new ControladorCliente(ventanaCliente,cliente);
	}
	
	private void mostrarVentanaAgregarEvento(ActionEvent ac)  {
		controladorEvento.iniciar();
		this.vista.getPanelEvento().mostrarPanelEvento(true);
		this.vista.getPanelCliente().mostrarPanelCliente(false);
		this.vista.getPanelPasaje().mostrarPanelPasaje(false);
		this.vista.getPanelPromocion().mostrarPanelPromocion(false);
//		this.llenarTablaEventos(evento.obtenerEvento());
		this.ventanaEvento.limpiarCampos();
		this.ventanaEvento.mostrarVentana();
	}
	
	private void mostrarVentanaAgregarPromocion(ActionEvent ac)  {
		controladorPromocion.iniciar();
		this.vista.getPanelPromocion().mostrarPanelPromocion(true);
		this.vista.getPanelCliente().mostrarPanelCliente(false);
		this.vista.getPanelPasaje().mostrarPanelPasaje(false);
		this.llenarTablaPromociones();
		this.ventanaPromocion.limpiarCampos();
		this.ventanaPromocion.mostrarVentana();
	}
	
	public void llenarComboFiltroEvento(){
		ArrayList<String> datos = new ArrayList<String>();
		this.vista.getPanelEvento().getComboFiltros().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filtro = vista.getPanelEvento().getComboFiltros().getSelectedItem().toString();
				if(!filtro.equals("Seleccione")) {
		 			vista.getPanelEvento().getComboOpcionesFiltros().setEnabled(true);
					if(filtro.equals("Fecha de Ingreso")) {
						datos.clear();
			 			for(EventoDTO x : eventos_en_tabla)
			 				if(!datos.contains(x.getFechaIngreso().toString()))
			 					datos.add(x.getFechaIngreso().toString());
					}
					if(filtro.equals("Fecha del Evento")) {
						datos.clear();
			 			for(EventoDTO x : eventos_en_tabla)
			 				if(!datos.contains(x.getFechaEvento().toString()))
			 					datos.add(x.getFechaEvento().toString());
					}
					if(filtro.equals("Apellido del Cliente")) {
						datos.clear();
			 			for(EventoDTO x : eventos_en_tabla)
			 				if(!datos.contains(x.getCliente().getApellido()))
			 					datos.add(x.getCliente().getApellido());
					}
					if(filtro.equals("Estado")) {
						datos.clear();
			 			for(EventoDTO x : eventos_en_tabla)
			 				if(!datos.contains(x.getEstadoEvento().getNombre()))
			 					datos.add(x.getEstadoEvento().getNombre());
					}
					String [] datosCombo = new String[datos.size()];
		 			vista.getPanelEvento().getComboOpcionesFiltros().setModel(new DefaultComboBoxModel<String>(datos.toArray(datosCombo)));
				}
				else {					
					limpiarFiltrosEvento();
					llenarTablaEventos(evento.obtenerEvento());
				}
			}
		});
	}
	
	public void filtrarEvento(ActionEvent e) {
		if(this.vista.getPanelEvento().getComboOpcionesFiltros().getSelectedIndex() !=-1)
			llenarTablaEventos(filtrarEventoSegun(this.vista.getPanelEvento().getComboOpcionesFiltros().getSelectedItem().toString()));
		else
			JOptionPane.showMessageDialog(vista.getPanelEvento(), "No se ha seleccionado ningún filtro");
	}
	
	public List<EventoDTO> filtrarEventoSegun(String datoCombo){
		List<EventoDTO> ret = new ArrayList<EventoDTO>();
		for(EventoDTO x : evento.obtenerEvento()) {
			if(this.vista.getPanelEvento().getComboFiltros().getSelectedItem().equals("Fecha de Ingreso"))
				if(x.getFechaIngreso().toString().equals(datoCombo))
					ret.add(x);
			if(this.vista.getPanelEvento().getComboFiltros().getSelectedItem().equals("Fecha del Evento"))
				if(x.getFechaEvento().toString().equals(this.vista.getPanelEvento().getComboOpcionesFiltros().getSelectedItem()))
					ret.add(x);
			if(this.vista.getPanelEvento().getComboFiltros().getSelectedItem().equals("Apellido del Cliente"))
				if(x.getCliente().getApellido().equals(this.vista.getPanelEvento().getComboOpcionesFiltros().getSelectedItem()))
					ret.add(x);
			if(this.vista.getPanelEvento().getComboFiltros().getSelectedItem().equals("Estado"))
				if(x.getEstadoEvento().getNombre().equals(this.vista.getPanelEvento().getComboOpcionesFiltros().getSelectedItem()))
					ret.add(x);
		}
		return ret;
	}
	
	public List<PasajeDTO> filtrarPasajeSegun(String estado) {
		List<PasajeDTO> resultado = new ArrayList<PasajeDTO>();
		this.clientes_en_tabla = cliente.obtenerClientes();
		for (int i = 0; i < pasajes_en_tabla.size(); i++) {
			if (pasajes_en_tabla.get(i).getEstadoDelPasaje().getNombre().compareTo(estado)==0) {
				resultado.add(pasajes_en_tabla.get(i));
			}
		}
		if (resultado.size() == 0) {
			JOptionPane.showMessageDialog(vista.getPanelPasaje(), "No existe ningún pasaje con ese estado", "", 0);
		}
		return resultado;
	}
	
	public void llenarTabla() {
		this.llenarTablaClientes();
	}
	
	private void borrarFiltrosEvento(ActionEvent e) {
		llenarTablaEventos(evento.obtenerEvento());
		limpiarFiltrosEvento();
	}
		
	private void llenarTablaClientes(){
		boolean activos = this.vista.getPanelCliente().getActivos().isSelected();
		boolean inactivos = this.vista.getPanelCliente().getInactivos().isSelected();
		
		this.vista.getPanelCliente().getModelClientes().setRowCount(0); //Para vaciar la tabla
		this.vista.getPanelCliente().getModelClientes().setColumnCount(0);
		this.vista.getPanelCliente().getModelClientes().setColumnIdentifiers(this.vista.getPanelCliente().getNombreColumnasClientes());
			
		this.clientes_en_tabla = new ArrayList<ClienteDTO>();
		this.clientes_aux = cliente.obtenerClientes();

		if(activos == true && inactivos == false) {
			for (ClienteDTO cliente : this.clientes_aux) {
				if (cliente.getLogin().getEstado().equals("activo")) {
					this.clientes_en_tabla.add(cliente);
				}
			}
		}else if(inactivos == true && activos == false) {
			for(ClienteDTO cliente : this.clientes_aux) {
				if(cliente.getLogin().getEstado().equals("inactivo")) {
					this.clientes_en_tabla.add(cliente);
				}
			}
		} else if(activos && inactivos) {
			for (ClienteDTO cliente: this.clientes_aux) {
				this.clientes_en_tabla.add(cliente);
			}
		}
		
		for (int i = 0; i < this.clientes_en_tabla.size(); i++){
			Object[] fila = {this.clientes_en_tabla.get(i).getNombre(),
							 this.clientes_en_tabla.get(i).getApellido(),
							 this.clientes_en_tabla.get(i).getDni(),
							 this.clientes_en_tabla.get(i).getFechaNacimiento(),
							 this.clientes_en_tabla.get(i).getMedioContacto().getTelefonoFijo(),
							 this.clientes_en_tabla.get(i).getMedioContacto().getTelefonoCelular(),
							 this.clientes_en_tabla.get(i).getMedioContacto().getEmail(),
							 this.clientes_en_tabla.get(i).getLogin().getEstado()
							};
			this.vista.getPanelCliente().getModelClientes().addRow(fila);
		}		
	}
	
	private void llenarTablaPasajes(){
		this.vista.getPanelPasaje().getModelReservas().setRowCount(0); //Para vaciar la tabla
		this.vista.getPanelPasaje().getModelReservas().setColumnCount(0);
		this.vista.getPanelPasaje().getModelReservas().setColumnIdentifiers(this.vista.getPanelPasaje().getNombreColumnasReservas());

		this.pasajes_en_tabla = new ArrayList<PasajeDTO>();
		this.pasajes_aux = pasaje.obtenerPasajes();
		
		this.pasajes_en_tabla = obtenerPasajesFiltrados(this.pasajes_aux);
		
		for (int i = 0; i < this.pasajes_en_tabla.size(); i++){

			Object[] fila = {
							this.pasajes_en_tabla.get(i).getCliente().getDni(),
							this.pasajes_en_tabla.get(i).getCliente().getNombre(),
							this.pasajes_en_tabla.get(i).getCliente().getApellido(),
							this.pasajes_en_tabla.get(i).getNumeroComprobante(),
							this.pasajes_en_tabla.get(i).getViaje().getCiudadOrigen().getNombre(),
							this.pasajes_en_tabla.get(i).getViaje().getCiudadDestino().getNombre(),
							this.pasajes_en_tabla.get(i).getViaje().getFechaSalida(),
							this.pasajes_en_tabla.get(i).getViaje().getFechaLlegada(),
							this.pasajes_en_tabla.get(i).getViaje().getHoraSalida(),
							this.pasajes_en_tabla.get(i).getValorViaje(),
							this.pasajes_en_tabla.get(i).getViaje().getTransporte().getNombre(),
							this.pasajes_en_tabla.get(i).getEstadoDelPasaje().getNombre()
			};
							this.vista.getPanelPasaje().getModelReservas().addRow(fila);
		}		
	}
	
	private void llenarTablaPasajes(List<PasajeDTO> pasajes){
		this.vista.getPanelPasaje().getModelReservas().setRowCount(0); //Para vaciar la tabla
		this.vista.getPanelPasaje().getModelReservas().setColumnCount(0);
		this.vista.getPanelPasaje().getModelReservas().setColumnIdentifiers(this.vista.getPanelPasaje().getNombreColumnasReservas());

		this.pasajes_en_tabla = pasajes;
			
		for (int i = 0; i < this.pasajes_en_tabla.size(); i++){

			Object[] fila = {
							this.pasajes_en_tabla.get(i).getCliente().getDni(),
							this.pasajes_en_tabla.get(i).getCliente().getNombre(),
							this.pasajes_en_tabla.get(i).getCliente().getApellido(),
							this.pasajes_en_tabla.get(i).getNumeroComprobante(),
							this.pasajes_en_tabla.get(i).getViaje().getCiudadOrigen().getNombre(),
							this.pasajes_en_tabla.get(i).getViaje().getCiudadDestino().getNombre(),
							this.pasajes_en_tabla.get(i).getViaje().getFechaSalida(),
							this.pasajes_en_tabla.get(i).getViaje().getFechaLlegada(),
							this.pasajes_en_tabla.get(i).getViaje().getHoraSalida(),
							this.pasajes_en_tabla.get(i).getValorViaje(),
							this.pasajes_en_tabla.get(i).getViaje().getTransporte().getNombre(),
							this.pasajes_en_tabla.get(i).getEstadoDelPasaje().getNombre()
			};
							this.vista.getPanelPasaje().getModelReservas().addRow(fila);
		}		
	}
	
	private void llenarTablaEventos(List<EventoDTO> tabla){
		this.vista.getPanelEvento().getModelEventos().setRowCount(0); //Para vaciar la tabla
		this.vista.getPanelEvento().getModelEventos().setColumnCount(0);
		this.vista.getPanelEvento().getModelEventos().setColumnIdentifiers(this.vista.getPanelEvento().getNombreColumnasEventos());

		this.eventos_en_tabla = evento.obtenerEvento();
			
		for (int i = 0; i < tabla.size(); i++){

			Object[] fila = {
							tabla.get(i).getFechaIngreso(),
							tabla.get(i).getFechaEvento(),
							tabla.get(i).getHoraEvento(),
							tabla.get(i).getDescripcion(),
							tabla.get(i).getCliente().getApellido(),
							tabla.get(i).getCliente().getNombre(),
							tabla.get(i).getAdministrativo().getNombre(),
							tabla.get(i).getEstadoEvento().getNombre(),
							this.estaReprogramado(tabla.get(i))
			};
							this.vista.getPanelEvento().getModelEventos().addRow(fila);
		}	
		this.llenarComboFiltroEvento();
	}
	
	private void actualizarTablaEventos(ActionEvent e) {
		llenarComboFiltroEvento();
		limpiarFiltrosEvento();
		llenarTablaEventos(evento.obtenerEvento());
	}
	
	private void llenarTablaPromociones(){
		this.vista.getPanelPromocion().getModelPromocion().setRowCount(0); //Para vaciar la tabla
		this.vista.getPanelPromocion().getModelPromocion().setColumnCount(0);
		this.vista.getPanelPromocion().getModelPromocion().setColumnIdentifiers(this.vista.getPanelPromocion().getNombreColumnasPromociones());

		this.promociones_en_tabla = promocion.obtenerPromocion();
			
		for (int i = 0; i < this.promociones_en_tabla.size(); i++){

			Object[] fila = {
							//this.promociones_en_tabla.get(i).getViaje().getIdViaje(),
							this.promociones_en_tabla.get(i).getPorcentaje()+" %",
							this.promociones_en_tabla.get(i).getStock(),
							this.promociones_en_tabla.get(i).getFechaVencimiento(),
							this.promociones_en_tabla.get(i).getViaje().getCiudadOrigen().getNombre()+", "+this.promociones_en_tabla.get(i).getViaje().getProvinciaOrigen().getNombre(),
							this.promociones_en_tabla.get(i).getViaje().getCiudadDestino().getNombre()+", "+this.promociones_en_tabla.get(i).getViaje().getProvinciaDestino().getNombre(),
							this.promociones_en_tabla.get(i).getViaje().getFechaSalida().toString(),
							this.promociones_en_tabla.get(i).getViaje().getFechaLlegada().toString(),
							this.promociones_en_tabla.get(i).getEstado()
			};
							this.vista.getPanelPromocion().getModelPromocion().addRow(fila);
		}		
	}
	
	public void actualizarTablaPromocion(ActionEvent e) {
		this.llenarTablaPromociones();
	}
	
	public String estaReprogramado(EventoDTO e) {
		if(e.getMotivoReprogramacion().equals(""))
			return "no";
		else
			return "si";
	}
	
	private void limpiarFiltrosEvento() {
		this.vista.getPanelEvento().getComboFiltros().setSelectedIndex(0);
		this.vista.getPanelEvento().getComboOpcionesFiltros().setSelectedIndex(-1);
		this.vista.getPanelEvento().getComboOpcionesFiltros().setEnabled(false);
	}
	
	private ArrayList<PasajeDTO> obtenerPasajesFiltrados(List<PasajeDTO> pasajes_aux){
		
		ArrayList<PasajeDTO> pasajes = new ArrayList<PasajeDTO>();
		
		boolean cancel = this.vista.getPanelPasaje().getCancelCheckBox().isSelected();
		boolean pend = this.vista.getPanelPasaje().getPendCheckBox().isSelected();
		boolean reser = this.vista.getPanelPasaje().getReserCheckBox().isSelected();
		boolean vend = this.vista.getPanelPasaje().getVendCheckBox().isSelected();

		if (cancel == false && pend == false && reser == false && vend == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				pasajes.add(pasaje);
			}
		} else if (cancel == true && pend == true && reser == true && vend == true ) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				pasajes.add(pasaje);
			}
		} else if (cancel == true && pend == true && reser == true && vend == false ) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if(!pasaje.getEstadoDelPasaje().getNombre().equals("Vendido")) {
					pasajes.add(pasaje);
				}
			}
		} else if (cancel == true && pend == true && reser == false && vend == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if(!(pasaje.getEstadoDelPasaje().getNombre().equals("Vendido") || pasaje.getEstadoDelPasaje().getNombre().equals("Reservado"))) {
					pasajes.add(pasaje);
				}
			}
		} else if (cancel == true && pend == false && reser == false && vend == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if(!(pasaje.getEstadoDelPasaje().getNombre().equals("Vendido") || 
						pasaje.getEstadoDelPasaje().getNombre().equals("Reservado") || 
							pasaje.getEstadoDelPasaje().getNombre().equals("Pendiente") )) {
					pasajes.add(pasaje);
				}
			}
		} else if (vend == true && reser == true && pend == true && cancel == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if( !(pasaje.getEstadoDelPasaje().getNombre().equals("Cancelado")) ) {
					pasajes.add(pasaje);
				}
			}
		} else if (vend == true && reser == true && pend == false && cancel == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if(!(pasaje.getEstadoDelPasaje().getNombre().equals("Cancelado") || pasaje.getEstadoDelPasaje().getNombre().equals("Pendiente"))) {
					pasajes.add(pasaje);
				}
			}
		} else if (vend == true && reser == false && pend == false && cancel == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if(!(pasaje.getEstadoDelPasaje().getNombre().equals("Cancelado") || 
						pasaje.getEstadoDelPasaje().getNombre().equals("Pendiente") || 
							pasaje.getEstadoDelPasaje().getNombre().equals("Reservado") )) {
					pasajes.add(pasaje);
				}
			}
		} else if (reser == true && pend == true && vend == true && cancel == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if( !(pasaje.getEstadoDelPasaje().getNombre().equals("Cancelado")) ) {
					pasajes.add(pasaje);
				}
			}
		} else if (reser == true && pend == true && vend == false && cancel == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if(!(pasaje.getEstadoDelPasaje().getNombre().equals("Cancelado") || pasaje.getEstadoDelPasaje().getNombre().equals("Vendido"))) {
					pasajes.add(pasaje);
				}
			}
		} else if (reser == true && pend == false && vend == false && cancel == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if(!(pasaje.getEstadoDelPasaje().getNombre().equals("Cancelado") || 
						pasaje.getEstadoDelPasaje().getNombre().equals("Vendido") || 
							pasaje.getEstadoDelPasaje().getNombre().equals("Pendiente") )) {
					pasajes.add(pasaje);
				}
			}
		} else if (pend == true && vend == true && reser == true && cancel == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if( !(pasaje.getEstadoDelPasaje().getNombre().equals("Cancelado")) ) {
					pasajes.add(pasaje);
				}
			}
		} else if (pend == true && vend == true && reser == false && cancel == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if(!(pasaje.getEstadoDelPasaje().getNombre().equals("Cancelado") || pasaje.getEstadoDelPasaje().getNombre().equals("Reservado"))) {
					pasajes.add(pasaje);
				}
			}
		} else if (pend == true && vend == false && reser == false && cancel == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if(!(pasaje.getEstadoDelPasaje().getNombre().equals("Cancelado") || 
						pasaje.getEstadoDelPasaje().getNombre().equals("Reservado") || 
							pasaje.getEstadoDelPasaje().getNombre().equals("Vendido") )) {
					pasajes.add(pasaje);
				}
			}
		} else if (cancel == true && reser == false && pend == true && vend == true) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if( !(pasaje.getEstadoDelPasaje().getNombre().equals("Reservado")) ) {
					pasajes.add(pasaje);
				}
			}
		} else if (cancel == true && pend == false && reser == true && vend == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if(!(pasaje.getEstadoDelPasaje().getNombre().equals("Pendiente") || pasaje.getEstadoDelPasaje().getNombre().equals("Vendido"))) {
					pasajes.add(pasaje);
				}
			}
		} else if (cancel == true && reser == true && vend == true && pend == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if( !(pasaje.getEstadoDelPasaje().getNombre().equals("Pendiente")) ) {
					pasajes.add(pasaje);
				}
			}
		}
		
		return pasajes;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
    }
}