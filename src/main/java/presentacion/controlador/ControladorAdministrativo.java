package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

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
		
		this.vista.getItemVisualizarPasajes().addActionListener(ap->mostrarPasajes(ap));
		this.vista.getItemAgregarPasaje().addActionListener(ap->mostrarVentanaAgregarPasaje(ap));
		this.vista.getItemEditarPasaje().addActionListener(ep->mostrarVentanaEditarPasaje(ep));
		this.vista.getItemCancelarPasaje().addActionListener(cp->cancelarPasaje(cp));
		
		this.vista.getPanelCliente().getActivos().addActionListener(sa->cargarActivos(sa));
		this.vista.getPanelCliente().getInactivos().addActionListener(si->cargarInactivos(si));
		
		this.ventanaEditarCliente.getBtnEditar().addActionListener(ec->editarCliente(ec));
//		this.vista.getPanelPasaje().getBtnVisualizarPasaje().addActionListener(vp->verDatosPasaje(vp));
		this.vista.getPanelPasaje().getBtnBuscar().addActionListener(b->filtrar(b));
		this.vista.getPanelPasaje().getBtnBorrarFiltros().addActionListener(bf->borrarFiltros(bf));
		
		this.vista.getItemAgregarEvento().addActionListener(ac->mostrarVentanaAgregarEvento(ac));
		this.vista.getItemVisualizarEventos().addActionListener(ac->mostrarEventos(ac));
		this.vista.getItemEditarEvento().addActionListener(ac->mostrarVentanaEditarEvento(ac));
		
		this.vista.getItemAgregarPromocion().addActionListener(ac->mostrarVentanaAgregarPromocion(ac));
		this.vista.getItemVisualizarPromociones().addActionListener(ac->mostrarPromociones(ac));
		this.vista.getItemDarBajaPromocion().addActionListener(ac->darBajaPromocion(ac));
		
		this.administrativoLogueado = administrativoLogueado;
		this.cliente = new Cliente(new DAOSQLFactory());
		this.pasaje = new Pasaje(new DAOSQLFactory());
		this.evento = new ModeloEvento(new DAOSQLFactory());
		this.promocion = new ModeloPromocion(new DAOSQLFactory());
		
		controladorPasaje = new ControladorPasaje(ventanaVisualizarCliente,cliente,administrativoLogueado);

		controladorCliente = new ControladorCliente(ventanaRegistrarCliente, ventanaEditarCliente, cliente);

		controladorEvento = new ControladorEvento(ventanaEvento, evento, administrativoLogueado, this.eventos_en_tabla);
		
		controladorPromocion = new ControladorPromocion(ventanaPromocion, promocion, this.promociones_en_tabla);
	}

	public void cargarInactivos(ActionEvent si) {
		this.llenarTablaClientes();
	}


	public void cargarActivos(ActionEvent sa) {
		this.llenarTablaClientes();
	}
	
	
	private void recargarTabla(ActionEvent r) {
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

	//TODO: Hacer acá el edit del Cliente.
	private void editarCliente(ActionEvent ec) {

		java.util.Date dateFechaNacimiento = this.ventanaEditarCliente.getDateFechaNacimiento().getDate();
		java.sql.Date fechaNacimientoCliente = new java.sql.Date(dateFechaNacimiento.getTime());
		
		String estado = "Activo";
		int idCliente = this.clientes_en_tabla.get(this.filaSeleccionada).getIdCliente();
		String nombreCliente = this.ventanaEditarCliente.getTxtNombre().getText();
		String apellidoCliente = this.ventanaEditarCliente.getTxtApellido().getText();
		String dniCliente = this.ventanaEditarCliente.getTxtDni().getText();
		RolDTO rolCliente = new RolDTO(
				this.clientes_en_tabla.get(this.filaSeleccionada).getLogin().getRol().getIdRol(),
				this.clientes_en_tabla.get(this.filaSeleccionada).getLogin().getRol().getNombre()
				);
		LoginDTO loginCliente = new LoginDTO(
				this.clientes_en_tabla.get(this.filaSeleccionada).getLogin().getIdDatosLogin(),
				this.ventanaEditarCliente.getTxtUsuario().getText(),
				this.ventanaEditarCliente.getTxtContrasenia().getText(),
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
		this.ventanaEditarCliente.getTxtContrasenia().setText(this.clientes_en_tabla.get(this.filaSeleccionada).getLogin().getContrasena());
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
			llenarTablaEventos();
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
		if (filaSeleccionada != -1){
			ventanaEditarEvento.mostrarVentana(true);
			//ventanaEditarEvento.getComboHoraEvento().setSelectedItem(this.eventos_en_tabla.get(filaSeleccionada).getHoraEvento().toString());
			ventanaEditarEvento.getTxtDescripcion().setText(this.eventos_en_tabla.get(filaSeleccionada).getDescripcion());
			ventanaEditarEvento.getComboEstadoEvento().setSelectedItem(this.eventos_en_tabla.get(filaSeleccionada).getEstadoEvento().toString());
			ventanaEditarEvento.getTxtDni().setText(this.eventos_en_tabla.get(filaSeleccionada).getCliente().getDni());
			ventanaEditarEvento.getTxtApellido().setText(this.eventos_en_tabla.get(filaSeleccionada).getCliente().getApellido());
			ventanaEditarEvento.getTxtNombre().setText(this.eventos_en_tabla.get(filaSeleccionada).getCliente().getNombre());
		}
		else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
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
		this.llenarTablaEventos();
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
		this.vista.getPanelEvento().mostrarPanelEvento(false);
		this.vista.getPanelCliente().mostrarPanelCliente(false);
		this.vista.getPanelPasaje().mostrarPanelPasaje(false);
		this.vista.getPanelPromocion().mostrarPanelPromocion(false);
		this.ventanaEvento.limpiarCampos();
		this.ventanaEvento.mostrarVentana();
	}
	
	private void mostrarVentanaAgregarPromocion(ActionEvent ac)  {
		controladorPromocion.iniciar();
		this.vista.getPanelPromocion().mostrarPanelPromocion(false);
		this.vista.getPanelCliente().mostrarPanelCliente(false);
		this.vista.getPanelPasaje().mostrarPanelPasaje(false);
		this.ventanaPromocion.limpiarCampos();
		this.ventanaPromocion.mostrarVentana();
	}
	
	public void filtrar(ActionEvent bc){
		String filtroSeleccionado = this.vista.getPanelPasaje().getComboBoxFiltros().getSelectedItem().toString();
		if (filtroSeleccionado.equals("Seleccione")){
			JOptionPane.showMessageDialog(null, "Debe seleccionar una opción", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}else if (filtroSeleccionado.equals("Cancelado")) {
			llenarTablaPasajes(filtrarPasajeSegun(filtroSeleccionado));
		}else if(filtroSeleccionado.equals("Pendiente")){
			llenarTablaPasajes(filtrarPasajeSegun(filtroSeleccionado));
		}else if(filtroSeleccionado.equals("Reservado")){
			llenarTablaPasajes(filtrarPasajeSegun(filtroSeleccionado));
		}else if(filtroSeleccionado.equals("Vendido")){
			llenarTablaPasajes(filtrarPasajeSegun(filtroSeleccionado));
		}
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
	
	private void borrarFiltros(ActionEvent bf) {
		llenarTablaPasajes(pasaje.obtenerPasajes());
		this.vista.getPanelPasaje().getComboBoxFiltros().setSelectedIndex(0);
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
	
	private void llenarTablaEventos(){
		this.vista.getPanelEvento().getModelEventos().setRowCount(0); //Para vaciar la tabla
		this.vista.getPanelEvento().getModelEventos().setColumnCount(0);
		this.vista.getPanelEvento().getModelEventos().setColumnIdentifiers(this.vista.getPanelEvento().getNombreColumnasEventos());

		this.eventos_en_tabla = evento.obtenerEvento();
			
		for (int i = 0; i < this.eventos_en_tabla.size(); i++){

			Object[] fila = {
							this.eventos_en_tabla.get(i).getIdEvento(),
							this.eventos_en_tabla.get(i).getFechaIngreso(),
							this.eventos_en_tabla.get(i).getFechaEvento(),
							this.eventos_en_tabla.get(i).getHoraEvento(),
							this.eventos_en_tabla.get(i).getDescripcion(),
							this.eventos_en_tabla.get(i).getCliente().getApellido(),
							this.eventos_en_tabla.get(i).getCliente().getNombre(),
							this.eventos_en_tabla.get(i).getAdministrativo().getNombre(),
							this.eventos_en_tabla.get(i).getEstadoEvento().getNombre(),
							this.eventos_en_tabla.get(i).getMotivoReprogramacion()
			};
							this.vista.getPanelEvento().getModelEventos().addRow(fila);
		}		
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
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
}