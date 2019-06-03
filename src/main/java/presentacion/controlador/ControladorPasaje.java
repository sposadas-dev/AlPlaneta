package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import modelo.Cliente;
import modelo.EstadoPasaje;
import modelo.FormaPago;
import modelo.ModeloViaje;
import modelo.Pago;
import modelo.Pagos_Pasaje;
import modelo.Pasaje;
import modelo.Pasaje_Pasajeros;
import modelo.Pasajero;
import dto.AdministrativoDTO;
import dto.ClienteDTO;
import dto.EstadoPasajeDTO;
import dto.FormaPagoDTO;
import dto.PagoDTO;
import dto.Pagos_PasajeDTO;
import dto.PaisDTO;
import dto.PasajeDTO;
import dto.Pasaje_PasajerosDTO;
import dto.PasajeroDTO;
import dto.ViajeDTO;
import persistencia.dao.mysql.DAOSQLFactory;
import persistencia.dao.mysql.PaisDAOSQL;
import presentacion.reportes.Reporte;
import presentacion.vista.administrativo.VentanaCancelacionPasaje;
import presentacion.vista.administrativo.VentanaCargaPasajero;
import presentacion.vista.administrativo.VentanaComprobante;
import presentacion.vista.administrativo.VentanaConfirmacionPasaje;
import presentacion.vista.administrativo.VentanaPago;
import presentacion.vista.administrativo.VentanaPasajero;
import presentacion.vista.administrativo.VentanaTablaPagos;
import presentacion.vista.administrativo.VentanaTablaViajes;
import presentacion.vista.administrativo.VentanaTarjeta;
import presentacion.vista.administrativo.VentanaVisualizarClientes;
import presentacion.vista.administrativo.VentanaVisualizarPasaje;

public class ControladorPasaje implements ActionListener{

	private VentanaVisualizarClientes ventanaVisualizarClientes;
	private VentanaTablaViajes ventanaTablaViajes;
	private VentanaCargaPasajero ventanaCargaPasajero;
	private VentanaPasajero ventanaPasajero;
	private VentanaPago ventanaPago;
	private VentanaVisualizarPasaje ventanaVisualizarPasaje;
	private VentanaConfirmacionPasaje ventanaConfirmacionPasaje;
	private VentanaComprobante ventanaComprobante;
	private VentanaTablaPagos ventanaTablaPagos;
	private VentanaTarjeta ventanaTarjeta;
	private VentanaCancelacionPasaje ventanaCancelacionPasaje;
	
	private List<ClienteDTO> clientes_en_tabla;
	private List <ViajeDTO> viajes_en_tabla;
	private List<PasajeroDTO> pasajeros_en_reserva;
	private List<PasajeDTO> pasajes_en_tabla;
	
	private ClienteDTO clienteSeleccionado; //cliente que selecciona en la tabla 
	private ViajeDTO viajeSeleccionado; // viaje que seleeciona en la tabla
	private PasajeDTO pasajeDTO;
	private PasajeDTO pasajeAEditar; 
	private PasajeDTO pasajeACancelar;
	private Pagos_PasajeDTO pagos_pasajeDTO;
	
	/*Modelos*/
	private Cliente cliente;
	private Pasajero pasajero;
	private ModeloViaje viaje;
	private Pago pago;
	private Pasaje pasaje;
	private Pagos_Pasaje pagos_pasaje;
	private Pasaje_Pasajeros pasajes_pasajeros;
	/*Fin de modelos*/
	
	private BigDecimal totalaPagar;
	private AdministrativoDTO administrativoLogueado;
	private PagoDTO pagoDTO;
	private boolean editarPago;
	private DefaultTableModel dm;
	
	public ControladorPasaje(VentanaVisualizarClientes ventanaVisualizarClientes, Cliente cliente, AdministrativoDTO administrativoLogueado){
		this.ventanaVisualizarClientes = ventanaVisualizarClientes;
		this.ventanaTablaViajes = VentanaTablaViajes.getInstance();
		this.ventanaCargaPasajero = VentanaCargaPasajero.getInstance();
		this.ventanaPasajero = VentanaPasajero.getInstance();
		this.ventanaPago = VentanaPago.getInstance();
		this.ventanaVisualizarPasaje = VentanaVisualizarPasaje.getInstance();
		this.ventanaConfirmacionPasaje = VentanaConfirmacionPasaje.getInstance();
		this.ventanaComprobante = VentanaComprobante.getInstance();
		this.ventanaTablaPagos = VentanaTablaPagos.getInstance();
		this.ventanaTarjeta = VentanaTarjeta.getInstance();
		this.ventanaCancelacionPasaje = VentanaCancelacionPasaje.getInstance();
		
		this.cliente = cliente;
		this.viaje = new ModeloViaje(new DAOSQLFactory());
		this.pasajero = new Pasajero(new DAOSQLFactory());
		this.pago = new Pago(new DAOSQLFactory());
		this.pasaje = new Pasaje(new DAOSQLFactory());
		this.pagos_pasaje = new Pagos_Pasaje(new DAOSQLFactory());
		this.pasajes_pasajeros = new Pasaje_Pasajeros(new DAOSQLFactory());
		
		this.pasajeros_en_reserva = new ArrayList<PasajeroDTO>();
		this.pasajes_en_tabla = pasaje.obtenerPasajes();
		
		this.ventanaVisualizarClientes.getBtnConfirmar().addActionListener(c->confirmarSeleccionCliente(c));
		this.ventanaVisualizarClientes.getBtnAplicarFiltro().addActionListener(bc->buscarCliente(bc));
		this.ventanaVisualizarClientes.getBtnBorrarFiltro().addActionListener(bf->borrarFiltros(bf));
		
		this.ventanaTablaViajes.getBtnConfirmar().addActionListener(cv->confirmarSeleccionViaje(cv));
		this.ventanaTablaViajes.getBtnAtras().addActionListener(a->volverVentanaCliente(a));
		
		this.ventanaCargaPasajero.getBtnAgregarPasajero().addActionListener(ap->mostrarVentanaAgregarPasajero(ap));
		this.ventanaCargaPasajero.getBtnEliminarPasajero().addActionListener(ep->eliminarPasajero(ep));
		this.ventanaCargaPasajero.getBtnConfirmar().addActionListener(ap->confirmarPasajeros(ap));
		this.ventanaCargaPasajero.getBtnAtras().addActionListener(a->volverVentanaViaje(a));
	
		this.ventanaPasajero.getBtnCargarDatos().addActionListener(cd->cargarDatosPasajero(cd));
		this.ventanaPasajero.getBtnAplicarBusqueda().addActionListener(af->aplicarFiltro(af));
		
		this.ventanaPago.getBtnPago().addActionListener(pago->darAltaDelPago(pago));
		this.ventanaPago.getBtnAtras().addActionListener(vc->volverVentanaCargaPasajero(vc));
		
		this.ventanaConfirmacionPasaje.getBtnGenerarPasaje().addActionListener(dp->darAltaDeUnPasaje(dp));
		this.ventanaConfirmacionPasaje.getBtnAtras().addActionListener(dp->volverVentanaPago(dp));

		this.ventanaComprobante.getBtnPdfReserva().addActionListener(cr->mostrarComprobanteReserva(cr));
		this.ventanaComprobante.getBtnPdfPago().addActionListener(cp->mostrarComprobantePago(cp));
	
		//Ventana Edición de Pasaje
		this.ventanaVisualizarPasaje.getBtnAceptar().addActionListener(s->salirVentanaVisualizarPasaje(s));
		this.ventanaVisualizarPasaje.getBtnVerPagos().addActionListener(vp->verTablaPagos(vp));
		this.ventanaVisualizarPasaje.getBtnPagar().addActionListener(p->pagarPasaje(p));

		this.ventanaCancelacionPasaje.getBtnAceptar().addActionListener(cp->cancelarPasaje(cp));
		this.administrativoLogueado = administrativoLogueado;
		this.editarPago = true;
	}


	private void pagarPasaje(ActionEvent p) {
		this.ventanaPago.mostrarVentana(true);
	}

	private void aplicarFiltro(ActionEvent af) {
		String dni = ventanaPasajero.getTxtFiltroDni().getText();
		if(cliente.getClienteByDni(dni)!=null){
		ClienteDTO clienteDTO = cliente.getClienteByDni(dni);
		this.ventanaPasajero.getTxtNombre().setText(clienteDTO.getNombre());
		this.ventanaPasajero.getTxtApellido().setText(clienteDTO.getApellido());
		this.ventanaPasajero.getTxtDni().setText(clienteDTO.getDni());
		this.ventanaPasajero.getDateFechaNacimiento().setDate(clienteDTO.getFechaNacimiento());
		this.ventanaPasajero.getTxtTelefono().setText(clienteDTO.getMedioContacto().getTelefonoCelular());
		this.ventanaPasajero.getTxtEmail().setText(clienteDTO.getMedioContacto().getEmail());
		}else
		if (pasajero.getPasajeroByDni(dni)!=null){
			
		PasajeroDTO pasajeroDTO = pasajero.getPasajeroByDni(dni);
		this.ventanaPasajero.getTxtNombre().setText(pasajeroDTO.getNombre());
		this.ventanaPasajero.getTxtApellido().setText(pasajeroDTO.getApellido());
		this.ventanaPasajero.getTxtDni().setText(pasajeroDTO.getDni());
		this.ventanaPasajero.getDateFechaNacimiento().setDate(pasajeroDTO.getFechaNacimiento());
		this.ventanaPasajero.getTxtTelefono().setText(pasajeroDTO.getTelefono());
		this.ventanaPasajero.getTxtEmail().setText(pasajeroDTO.getEmail());
		}else{
			JOptionPane.showMessageDialog(ventanaPasajero, "No existe ningún cliente ni pasajero con ese DNI", "Filtro", 0);
		}
	}
	
	public void iniciar(){
		this.llenarTablaClientes();
	}
	
	/*----------------------------------Filtro Cliente------------------------------------*/
	public void buscarCliente(ActionEvent bc){
		String filtroSeleccionado = this.ventanaVisualizarClientes.getComboBoxFiltro().getSelectedItem().toString();
		String dato = this.ventanaVisualizarClientes.getTxtFiltro().getText();
		if (filtroSeleccionado.equals("Seleccione")){
			JOptionPane.showMessageDialog(null, "Debe seleccionar una opción", "Mensaje", JOptionPane.ERROR_MESSAGE);		
		}else if (filtroSeleccionado.equals("DNI")) {
//			filtrarDniSegun(dato);
			buscarDniEnTabla(dato);	
		}
	}

	public List<ClienteDTO> filtrarDniSegun(String dniCliente) {
		List<ClienteDTO> resultado = new ArrayList<ClienteDTO>();
		this.clientes_en_tabla = cliente.obtenerClientes();
		for (int i = 0; i < clientes_en_tabla.size(); i++) {
			if (clientes_en_tabla.get(i).getDni().equals(dniCliente)) {
				resultado.add(clientes_en_tabla.get(i));
			}
			this.llenarTablaClientes();
		}
		if (resultado.size() == 0) {
			JOptionPane.showMessageDialog(ventanaVisualizarClientes, "No existe ningún cliente con ese DNI.", "Filtro", 0);
		}
		return resultado;
	}
	
	private void buscarDniEnTabla(String buscarDni){
		 dm = (DefaultTableModel) ventanaVisualizarClientes.getModelClientes();
	        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
	        ventanaVisualizarClientes.getTablaClientes().setRowSorter(tr);
	        tr.setRowFilter(RowFilter.regexFilter(buscarDni));
	}
	
	private void borrarFiltros(ActionEvent bf) {
		this.ventanaVisualizarClientes.getComboBoxFiltro().setSelectedIndex(0);
		this.ventanaVisualizarClientes.getTxtFiltro().setText("");
		this.llenarTablaClientes();
	}

	/*--------------------------------Fin de Filtro Cliente------------------------------------*/
	
	/*El personal administrativo debe seleccionar un cliente*/
	private void confirmarSeleccionCliente(ActionEvent c) {
		int filaSeleccionada = this.ventanaVisualizarClientes.getTablaClientes().getSelectedRow();
		if (filaSeleccionada != -1){
			this.ventanaVisualizarClientes.mostrarVentana(false);
			clienteSeleccionado = clientes_en_tabla.get(filaSeleccionada);	
			this.ventanaTablaViajes.mostrarVentana(true);
			llenarTablaViajes();
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	/*El personal administrativo debe seleccionar un viaje*/
	private void confirmarSeleccionViaje(ActionEvent sv){
		int filaSeleccionada = this.ventanaTablaViajes.getTablaViajes().getSelectedRow();
		if (filaSeleccionada != -1){
			this.ventanaTablaViajes.mostrarVentana(false);
			viajeSeleccionado = viajes_en_tabla.get(filaSeleccionada);
			this.ventanaCargaPasajero.mostrarVentana(true);
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/*Métodos para volver a la ventana anterior - Boton Atras */
	private void volverVentanaCliente(ActionEvent a) {
		this.ventanaTablaViajes.mostrarVentana(false);
		this.ventanaVisualizarClientes.mostrarVentana(true);
	}
	
	private void volverVentanaViaje(ActionEvent a) {
		this.ventanaCargaPasajero.mostrarVentana(false);
		this.ventanaTablaViajes.mostrarVentana(true);
	}
	
	private void volverVentanaCargaPasajero(ActionEvent vc) {
		this.ventanaPago.mostrarVentana(false);
		this.ventanaCargaPasajero.mostrarVentana(true);
	}
	
	private void volverVentanaPago(ActionEvent dp) {
		this.ventanaConfirmacionPasaje.mostrarVentana(false);
		this.ventanaPago.mostrarVentana(true);
	}
	/*Fin de métodos - Boton Atras*/
	
	
	/*Métodos Llenar Tablas*/
	private void llenarTablaClientes(){
		this.ventanaVisualizarClientes.getModelClientes().setRowCount(0); //Para vaciar la tabla
		this.ventanaVisualizarClientes.getModelClientes().setColumnCount(0);
		this.ventanaVisualizarClientes.getModelClientes().setColumnIdentifiers(this.ventanaVisualizarClientes.getNombreColumnasClientes());
	
		this.clientes_en_tabla = cliente.obtenerClientes();
		
		for (int i = 0; i < this.clientes_en_tabla.size(); i++){
			Object[] fila = {this.clientes_en_tabla.get(i).getNombre(),
							this.clientes_en_tabla.get(i).getApellido(),
							this.clientes_en_tabla.get(i).getDni(),
							this.clientes_en_tabla.get(i).getFechaNacimiento(),
							this.clientes_en_tabla.get(i).getMedioContacto().getTelefonoFijo(),
							this.clientes_en_tabla.get(i).getMedioContacto().getTelefonoCelular(),
							this.clientes_en_tabla.get(i).getMedioContacto().getEmail()	
			};
			this.ventanaVisualizarClientes.getModelClientes().addRow(fila);
		}		
	}
	
	private void llenarTablaViajes(){
		this.ventanaTablaViajes.getModelViajes().setRowCount(0); //Para vaciar la tabla
		this.ventanaTablaViajes.getModelViajes().setColumnCount(0);
		this.ventanaTablaViajes.getModelViajes().setColumnIdentifiers(this.ventanaTablaViajes.getNombreColumnas());
			
		this.viajes_en_tabla = viaje.obtenerViajes();
			
		for (int i = 0; i < this.viajes_en_tabla.size(); i++){
			Object[] fila = {this.viajes_en_tabla.get(i).getCiudadOrigen().getNombre(),
							this.viajes_en_tabla.get(i).getCiudadDestino().getNombre(),
							this.viajes_en_tabla.get(i).getFechaSalida(),
							this.viajes_en_tabla.get(i).getFechaLlegada(),
							this.viajes_en_tabla.get(i).getHoraSalida(),
							this.viajes_en_tabla.get(i).getHorasEstimadas(),
							this.viajes_en_tabla.get(i).getCapacidad(),
							this.viajes_en_tabla.get(i).getTransporte().getNombre(),
							this.viajes_en_tabla.get(i).getPrecio()					
			};
		this.ventanaTablaViajes.getModelViajes().addRow(fila);
		}		
	}
	
	private void llenarTablaDePasajeros(){
		this.ventanaCargaPasajero.getModelPasajeros().setRowCount(0);
		this.ventanaCargaPasajero.getModelPasajeros().setColumnCount(0);
		this.ventanaCargaPasajero.getModelPasajeros().setColumnIdentifiers(this.ventanaCargaPasajero.getNombreColumnas());
		for(int i=0; i<pasajeros_en_reserva.size();i++){
			Object[] fila = { 
					pasajeros_en_reserva.get(i).getNombre(),
					pasajeros_en_reserva.get(i).getApellido(),
					pasajeros_en_reserva.get(i).getDni(),
					pasajeros_en_reserva.get(i).getFechaNacimiento(),
					pasajeros_en_reserva.get(i).getTelefono(),
					pasajeros_en_reserva.get(i).getEmail()
			};
			this.ventanaCargaPasajero.getModelPasajeros().addRow(fila);
		}
		this.ventanaCargaPasajero.getLblPasajerosCargados().setText(pasajeros_en_reserva.size()+" pasajeros fueron cargados");
	}
	
	private void llenarTablaDePasajerosConfirmarPasaje(){
		this.ventanaConfirmacionPasaje.getModelPasajeros().setRowCount(0);
		this.ventanaConfirmacionPasaje.getModelPasajeros().setColumnCount(0);
		this.ventanaConfirmacionPasaje.getModelPasajeros().setColumnIdentifiers(this.ventanaConfirmacionPasaje.getNombreColumnas());
		for(int i=0; i<pasajeros_en_reserva.size();i++){
			Object[] fila = { 
					pasajeros_en_reserva.get(i).getNombre(),
					pasajeros_en_reserva.get(i).getApellido(),
					pasajeros_en_reserva.get(i).getDni(),
			};
			this.ventanaConfirmacionPasaje.getModelPasajeros().addRow(fila);
		}
	}
	
	private void llenarTablaPagos(int idPasaje){
		this.ventanaTablaPagos.getModelPagos().setRowCount(0);
		this.ventanaTablaPagos.getModelPagos().setColumnCount(0);
		this.ventanaTablaPagos.getModelPagos().setColumnIdentifiers(this.ventanaTablaPagos.getNombreColumnas());
		Pagos_Pasaje pago_pasaje = new Pagos_Pasaje(new DAOSQLFactory());
		List<Pagos_PasajeDTO> pagos = pago_pasaje.obtenerPagosPasaje();
		for(int i=0; i < pagos.size();i++){
			if(pagos.get(i).getPasaje().getIdPasaje() == idPasaje){
				Object[] fila = { 
						pagos.get(i).getPago().getFechaPago(),
						pagos.get(i).getPago().getMonto(),
						pagos.get(i).getPago().getIdFormaPago().getTipo(),
						pagos.get(i).getPago().getAdministrativo().getNombre() 
				
			};
			this.ventanaTablaPagos.getModelPagos().addRow(fila);
			}
		}
	}
	/*Fin de Llenar tablas*/
	
	private void mostrarVentanaAgregarPasajero(ActionEvent ap) {
		this.ventanaPasajero.setVisible(true);		
	}

	/*Cargamos los datos del pasajero*/
	private void cargarDatosPasajero(ActionEvent cd) {
		/*Obtenemos la fecha de nacimiento , y la parseamos a tipo de date de SQL*/
		java.util.Date dateFechaNacimiento = ventanaPasajero.getDateFechaNacimiento().getDate();
		java.sql.Date fechaNacimiento = new java.sql.Date(dateFechaNacimiento.getTime());
		
		PasajeroDTO nuevoPasajero = new PasajeroDTO(0,
				ventanaPasajero.getTxtNombre().getText(),
				ventanaPasajero.getTxtApellido().getText(),
				ventanaPasajero.getTxtDni().getText(),
				fechaNacimiento,
				ventanaPasajero.getTxtTelefono().getText(),
				ventanaPasajero.getTxtEmail().getText()
		);
		pasajeros_en_reserva.add(nuevoPasajero); 
		this.ventanaPasajero.limpiarCampos();
		this.ventanaPasajero.dispose();
		llenarTablaDePasajeros();
	}
	
	/*Elimina el pasajero de la carga de pasajeros*/
	private void eliminarPasajero(ActionEvent ep) {
		int filaSeleccionada = this.ventanaCargaPasajero.getTablaPasajeros().getSelectedRow();
		if (filaSeleccionada != -1){
			this.pasajeros_en_reserva.remove(filaSeleccionada);
			llenarTablaDePasajeros();
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/*Se confirma la carga de pasajeros*/
	private void confirmarPasajeros(ActionEvent ap) {
		this.ventanaCargaPasajero.setVisible(false);
		cargarComboBoxFormaDePago();
		this.ventanaPago.getLblMontoaPagar().setText("$ "+calcularMontoDePasaje().toString());
		this.ventanaPago.setVisible(true);
	}
	
	
	private void cargarComboBoxFormaDePago(){
		ventanaPago.getComboBoxFormaPago().removeAllItems();
		FormaPago formaPago = new FormaPago(new DAOSQLFactory());
		List<FormaPagoDTO> formasPagosDTO = formaPago.obtenerFormaPago();
		String[] formasPagos = new String[formasPagosDTO.size()+1]; 
		formasPagos[0]="Seleccione forma de pago";
		for(int i=0; i < formasPagosDTO.size();i++){
			String rango = formasPagosDTO.get(i).getTipo();
			formasPagos [i+1] = rango;
		}
		this.ventanaPago.getComboBoxFormaPago().setModel(new DefaultComboBoxModel(formasPagos));
	}
	
	private void darAltaDelPago(ActionEvent cp)  {
			FormaPago f = new FormaPago(new DAOSQLFactory());
			this.ventanaPago.getRadioReservaSinPagar().setVisible(true);
			
			FormaPagoDTO formaPago = f.getFormaPagoByName(ventanaPago.getComboBoxFormaPago().getSelectedItem().toString());
			Calendar currenttime = Calendar.getInstance();
			pagoDTO = new PagoDTO();	
			pagoDTO.setIdFormaPago(formaPago);
			pagoDTO.setAdministrativo(administrativoLogueado);
			pagoDTO.setMonto(new BigDecimal(this.ventanaPago.getTextImporteTotal().getText()));
			pagoDTO.setFechaPago(new Date((currenttime.getTime()).getTime()));
	
			if (editarPago){
				this.ventanaPago.setVisible(false);
				mostrarVentanaConfirmacionPasaje();
			}else{
				pago.agregarPago(pagoDTO);
			
				pagos_pasajeDTO = new Pagos_PasajeDTO();
				PagoDTO pagoPasaje = pago.getUltimoRegistroPago();
				pagos_pasajeDTO.setPago(pagoPasaje);
				pagos_pasajeDTO.setPasaje(pasajeAEditar);
				pagos_pasaje.agregarPagoPasaje(pagos_pasajeDTO);
	
				pasajeAEditar.setMontoAPagar(pasajeAEditar.getMontoAPagar().subtract(pagoDTO.getMonto()));
				pasajeAEditar.setEstadoDelPasaje(estadoPasaje(pasajeAEditar.getMontoAPagar()));
			
				pasaje.editarPasaje(pasajeAEditar);
				this.ventanaPago.limpiarCampos();
				this.ventanaPago.mostrarVentana(false);
				this.ventanaVisualizarPasaje.mostrarVentana(false);
				
				reportePago();
			}
	}
	
	private void mostrarVentanaConfirmacionPasaje(){
		this.ventanaConfirmacionPasaje.mostrarVentana(true);
		this.ventanaConfirmacionPasaje.getTxtCliente().setText(""+clienteSeleccionado.getNombre()+ " "+ clienteSeleccionado.getApellido());
		this.ventanaConfirmacionPasaje.getTxtDni().setText(""+clienteSeleccionado.getDni());
		this.ventanaConfirmacionPasaje.getTxtOrigen().setText(" "+ viajeSeleccionado.getPaisOrigen().getNombre()+ ", "+viajeSeleccionado.getProvinciaOrigen().getNombre()+", "+viajeSeleccionado.getCiudadOrigen().getNombre());
		this.ventanaConfirmacionPasaje.getTxtDestino().setText(" "+ viajeSeleccionado.getPaisDestino().getNombre()+ ", "+viajeSeleccionado.getProvinciaDestino().getNombre()+", "+viajeSeleccionado.getCiudadDestino().getNombre());
		this.ventanaConfirmacionPasaje.getTxtFormaPago().setText(""+pagoDTO.getIdFormaPago().getTipo());
		this.ventanaConfirmacionPasaje.getTxtPagado().setText(""+pagoDTO.getMonto());
		this.ventanaConfirmacionPasaje.getTxtTotal().setText(""+calcularMontoDePasaje());
		llenarTablaDePasajerosConfirmarPasaje();
	}
		
	private void mostrarVentanaComprobante(){
		this.ventanaComprobante.mostrarVentana(true);
		this.ventanaComprobante.getTxtCliente().setText(""+clienteSeleccionado.getNombre()+ " "+ clienteSeleccionado.getApellido());
		this.ventanaComprobante.getTxtDni().setText(""+clienteSeleccionado.getDni());
		this.ventanaComprobante.getTxtCodigo().setText(""+pasajeDTO.getNumeroComprobante());
		this.ventanaComprobante.getTxtOrigenViaje().setText(" "+ viajeSeleccionado.getPaisOrigen().getNombre()+ ", "+viajeSeleccionado.getProvinciaOrigen().getNombre()+", "+viajeSeleccionado.getCiudadOrigen().getNombre());
		this.ventanaComprobante.getTxtDestinoViaje().setText(" "+ viajeSeleccionado.getPaisDestino().getNombre()+ ", "+viajeSeleccionado.getProvinciaDestino().getNombre()+", "+viajeSeleccionado.getCiudadDestino().getNombre());
		this.ventanaComprobante.getTxtImportePagado().setText(" "+ pagoDTO.getMonto());
		this.ventanaComprobante.getTxtValorViaje().setText(""+calcularMontoDePasaje());
	}
	
	
	private void darAltaDeUnPasaje(ActionEvent dp) {
		ViajeDTO viajeDTO = viajeSeleccionado;
		ClienteDTO cliente = clienteSeleccionado;
		BigDecimal valorViaje = calcularMontoDePasaje();
		EstadoPasajeDTO estadoPasaje = calcularEstadoPasaje();
		List<PasajeroDTO> pasajeros = pasajeros_en_reserva;
		
		viajeDTO.setCapacidad(viajeSeleccionado.getCapacidad()-pasajeros.size()); //Restamos la capacidad del viaje segun la cantidad de pasajeros
		//Modelo viaje
		viaje.editarViaje(viajeDTO);
		
		//Agrego el pago
		
		pago.agregarPago(pagoDTO);
		
		PagoDTO pagoPasaje = pago.getUltimoRegistroPago();
		BigDecimal montoAPagar = valorViaje.subtract(pagoDTO.getMonto());
		String numeroComprobante = Validador.getNumeroComprobante(6);
		
		pasajeDTO = new PasajeDTO(0,numeroComprobante,viajeDTO,administrativoLogueado,cliente,calcularFechaReserva(viajeDTO.getFechaSalida()),valorViaje,montoAPagar,estadoPasaje,
				pasajeros,"",null);
		
		pasaje.agregarPasaje(pasajeDTO);
		
		PasajeDTO pDTO = pasaje.getUltimoRegistroPasaje();
		
		pagos_pasajeDTO = new Pagos_PasajeDTO();
		pagos_pasajeDTO.setPago(pagoPasaje);
		pagos_pasajeDTO.setPasaje(pDTO);
		
		pagos_pasaje.agregarPagoPasaje(pagos_pasajeDTO);
		
//		Vinculamos el pasaje con los pasajeros
	
		
//		Agregamos los pasajeros 
		for(PasajeroDTO pasajeroDTO : pasajeros_en_reserva) {
			pasajero.agregarPasajero(pasajeroDTO);
			PasajeroDTO pasajeroRegistro = pasajero.getUltimoRegistroPasajero();
			
			Pasaje_PasajerosDTO pasaje_pasajeros = new Pasaje_PasajerosDTO();
			pasaje_pasajeros.setIdPasaje(pDTO.getIdPasaje());
			pasaje_pasajeros.setIdPasajero(pasajeroRegistro.getIdPasajero());
			
			pasajes_pasajeros.agregarPasajePasajero(pasaje_pasajeros);
		}
		
		this.ventanaConfirmacionPasaje.setVisible(false);
		this.pasajeros_en_reserva.clear();
		this.llenarTablaDePasajeros();
		this.ventanaPago.limpiarCampos();
		mostrarVentanaComprobante();
	}

	public Date calcularFechaReserva(Date fechaSalida){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaSalida); 
		calendar.add(Calendar.DATE, -20);  //La fecha de reserva son 20 días antes a la fecha de salida del viaje
		return convertUtilToSql(calendar.getTime()); 
	}
	
	private java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
	
	private BigDecimal calcularMontoDePasaje() {
		BigDecimal Valor1 = this.viajeSeleccionado.getPrecio();
		totalaPagar = Valor1;
		return totalaPagar.multiply(new BigDecimal(pasajeros_en_reserva.size()));
	}
		
	private EstadoPasajeDTO calcularEstadoPasaje() {
		EstadoPasaje estado = new EstadoPasaje(new DAOSQLFactory());
		EstadoPasajeDTO ret;
		if(totalaPagar.compareTo(pagoDTO.getMonto())==0){ 
			ret = estado.getFormaPagoByName("Vendido");
		}
		else {
			if(pagoDTO.getMonto().equals(new BigDecimal(0))) {
				ret = estado.getFormaPagoByName("Pendiente");
			}
			else{
				ret = estado.getFormaPagoByName("Reservado");		
			}
		}
		return ret;
	}
	
	private EstadoPasajeDTO estadoPasaje(BigDecimal monto){
		EstadoPasaje estado = new EstadoPasaje(new DAOSQLFactory());
		EstadoPasajeDTO ret;
		if(monto.compareTo(new BigDecimal(0))==0){ 
			ret = estado.getFormaPagoByName("Vendido");
		}
		else {
			ret = estado.getFormaPagoByName("Reservado");
			}
		return ret;
	}
	private void mostrarComprobanteReserva(ActionEvent cr){
		Reporte reporte = new Reporte();
		reporte.reporteReserva(pagos_pasajeDTO);
		reporte.mostrar();
	}
	
	
	private void reportePago(){
		if(pagoDTO.getMonto().compareTo(new BigDecimal(0))!=0){
			Reporte reporte = new Reporte();
			reporte.reportePago(pagos_pasajeDTO);
			reporte.mostrar();
		}else{
			JOptionPane.showMessageDialog(null, "Solo se realizó reserva", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void mostrarComprobantePago(ActionEvent cp){
		reportePago();
	}
	
	public void editarPasaje(int filaSeleccionada){
		verDatosDelPasaje(filaSeleccionada);
		pasajeAEditar = this.pasajes_en_tabla.get(filaSeleccionada);
	}
	
	private void verDatosDelPasaje(int filaSeleccionada) {
		if (filaSeleccionada != -1){
			this.ventanaVisualizarPasaje.getBtnPagar().setVisible(true);
			this.ventanaVisualizarPasaje.getTxtClienteDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getCliente().getNombre());
			this.ventanaVisualizarPasaje.getTxtCodigoDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getNumeroComprobante());
			this.ventanaVisualizarPasaje.getTxtDniDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getCliente().getDni());
			this.ventanaVisualizarPasaje.getTxtOrigenDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getPaisOrigen().getNombre()+", "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getCiudadOrigen().getNombre()+ ", "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getCiudadOrigen().getNombre());
			this.ventanaVisualizarPasaje.getTxtDestinoDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getPaisDestino().getNombre()+", "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getProvinciaDestino().getNombre()+ ", "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getCiudadDestino().getNombre());
			this.ventanaVisualizarPasaje.getTxtTransporteDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getTransporte().getNombre());
			this.ventanaVisualizarPasaje.getTxtEstadoPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getEstadoDelPasaje().getNombre());
			this.ventanaVisualizarPasaje.getTxtMontoDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getValorViaje());
			this.ventanaVisualizarPasaje.getTxtImporteDebePasaje().setText(""+this.pasajes_en_tabla.get(filaSeleccionada).getMontoAPagar());
			this.ventanaVisualizarPasaje.getTxtMotivoCancelacion().setVisible(false);
			this.ventanaVisualizarPasaje.getLblMotivoCancelacion().setVisible(false);
			llenarTablaPagos(pasajes_en_tabla.get(filaSeleccionada).getIdPasaje());
			pagarPasaje(filaSeleccionada);
			
			if(this.pasajes_en_tabla.get(filaSeleccionada).getEstadoDelPasaje().getIdEstadoPasaje()==4){
				this.ventanaVisualizarPasaje.getTxtMotivoCancelacion().setVisible(true);
				this.ventanaVisualizarPasaje.getLblMotivoCancelacion().setVisible(true);
				this.ventanaVisualizarPasaje.getTxtMotivoCancelacion().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getMotivoCancelacion());
			}
			
			if(this.pasajes_en_tabla.get(filaSeleccionada).getEstadoDelPasaje().getIdEstadoPasaje()==1){
				this.ventanaVisualizarPasaje.setVisible(true);
				this.ventanaVisualizarPasaje.getBtnPagar().setVisible(false);
			}else{
				this.ventanaVisualizarPasaje.setVisible(true);
			}
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void pagarPasaje(int filaSeleccionada) {
		cargarComboBoxFormaDePago();
		this.ventanaPago.getRadioReservaSinPagar().setVisible(false);
		this.ventanaPago.getBtnAtras().setVisible(false);
		this.ventanaPago.getLblMontoaPagar().setText(""+this.pasajes_en_tabla.get(filaSeleccionada).getMontoAPagar());
		this.editarPago = false;
	}

	private void verTablaPagos(ActionEvent vp) {
		this.ventanaTablaPagos.mostrarVentana(true);
	}
			
	private void salirVentanaVisualizarPasaje(ActionEvent s) {
		this.ventanaVisualizarPasaje.mostrarVentana(false);
	}

	
	public void eliminarPasaje(int filaSeleccionada){
		int confirm = JOptionPane.showOptionDialog(
		            null,"¿Estás seguro que quieres cancelar el pasaje?", 
				             "Cancelar pasaje", JOptionPane.YES_NO_OPTION,
				             JOptionPane.ERROR_MESSAGE, null, null, null);
	 if (confirm == 0){
		 pasajeACancelar = pasajes_en_tabla.get(filaSeleccionada);
		 this.ventanaCancelacionPasaje.mostrarVentana(true);
	 }
	}
	
	public void cancelarPasaje(ActionEvent cp){
		Calendar currenttime = Calendar.getInstance();
		pasajeACancelar.setEstadoDelPasaje(new EstadoPasajeDTO(4,"Cancelado","Se cancelo el pasaje"));
		pasajeACancelar.setMotivoCancelacion(this.ventanaCancelacionPasaje.getTxtMotivoCancelacion().getText());
		pasajeACancelar.setDateCancelacion(new Date(currenttime.getTime().getTime()));
		pasaje.editarPasaje(pasajeACancelar);
		this.ventanaCancelacionPasaje.mostrarVentana(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}
}