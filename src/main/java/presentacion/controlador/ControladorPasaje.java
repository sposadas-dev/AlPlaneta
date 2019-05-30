package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

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
import dto.PasajeDTO;
import dto.Pasaje_PasajerosDTO;
import dto.PasajeroDTO;
import dto.ViajeDTO;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.reportes.Reporte;
import presentacion.vista.administrativo.VentanaCargaPasajero;
import presentacion.vista.administrativo.VentanaComprobante;
import presentacion.vista.administrativo.VentanaConfirmacionPasaje;
import presentacion.vista.administrativo.VentanaPago;
import presentacion.vista.administrativo.VentanaPasajero;
import presentacion.vista.administrativo.VentanaTablaPagos;
import presentacion.vista.administrativo.VentanaTablaViajes;
import presentacion.vista.administrativo.VentanaVisualizarClientes;
import presentacion.vista.administrativo.VentanaVisualizarPasaje;

public class ControladorPasaje {

	private VentanaVisualizarClientes ventanaVisualizarClientes;
	private VentanaTablaViajes ventanaTablaViajes;
	private VentanaCargaPasajero ventanaCargaPasajero;
	private VentanaPasajero ventanaPasajero;
	private VentanaPago ventanaPago;
	private VentanaVisualizarPasaje ventanaVisualizarPasaje;
	private VentanaConfirmacionPasaje ventanaConfirmacionPasaje;
	private VentanaComprobante ventanaComprobante;
	private VentanaTablaPagos ventanaTablaPagos;
	
	private List<ClienteDTO> clientes_en_tabla;
	private List <ViajeDTO> viajes_en_tabla;
	private List<PasajeroDTO> pasajeros_en_reserva;
	private List<PasajeDTO> pasajes_en_tabla;
	
	private ClienteDTO clienteSeleccionado; //cliente que selecciona en la tabla 
	private ViajeDTO viajeSeleccionado; // viaje que seleeciona en la tabla
	private PasajeDTO pasajeDTO;
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

		this.administrativoLogueado = administrativoLogueado;
	}

	private void aplicarFiltro(ActionEvent af) {
		String dni = ventanaPasajero.getTxtFiltroDni().getText();
		PasajeroDTO pasajeroDTO = pasajero.llenarCamposPasajeroPorDni(dni);
		this.ventanaPasajero.getTxtNombre().setText(pasajeroDTO.getNombre());
		this.ventanaPasajero.getTxtApellido().setText(pasajeroDTO.getApellido());
		this.ventanaPasajero.getTxtDni().setText(pasajeroDTO.getDni());
		this.ventanaPasajero.getDateFechaNacimiento().setDate(pasajeroDTO.getFechaNacimiento());
		this.ventanaPasajero.getTxtTelefono().setText(pasajeroDTO.getTelefono());
		this.ventanaPasajero.getTxtEmail().setText(pasajeroDTO.getEmail());
	}


	public void iniciar(){
		this.llenarTablaClientes();
	}
	
	public void buscarCliente(ActionEvent bc){
		String selected = this.ventanaVisualizarClientes.getComboBoxFiltro().getSelectedItem().toString();
		String datos = this.ventanaVisualizarClientes.getTxtFiltro().getText();
		System.out.println(datos);
		if (selected.equals("DNI")) {
//			filtrarDniSegun(datos);	
		}
	}
	
	
//	public List<ClienteDTO> filtrarDniSegun(String dniCliente) {
//		List<ClienteDTO> resultado = new ArrayList<ClienteDTO>();
//		this.clientes_en_tabla = cliente.obtenerClientes();
//		for (int i = 0; i < clientes_en_tabla.size(); i++) {
//			if (clientes_en_tabla.get(i).getDni().compareTo(dniCliente)==0) {
//				resultado.add(clientes_en_tabla.get(i));
//				System.out.println(clientes_en_tabla.get(i));
//			}
//			this.llenarTablaClientes();
//		}
//		if (resultado.size() == 0) {
//			JOptionPane.showMessageDialog(ventanaVisualizarClientes, "No existe nigún cliente con ese DNI.", "", 0);
//		}
//		return resultado;
//	}
	
	
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
		
//		pasajero.agregarPasajero(nuevoPasajero);
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
		String[] formasPagos = new String[formasPagosDTO.size()]; 
		for(int i=0; i < formasPagosDTO.size();i++){
			String rango = formasPagosDTO.get(i).getTipo();
			formasPagos [i] = rango;
		}
		this.ventanaPago.getComboBoxFormaPago().setModel(new DefaultComboBoxModel(formasPagos));
	}
	
	private void darAltaDelPago(ActionEvent p) {
		FormaPago f = new FormaPago(new DAOSQLFactory());
		FormaPagoDTO formaPago = f.getFormaPagoByName(ventanaPago.getComboBoxFormaPago().getSelectedItem().toString());
		
		Calendar currenttime = Calendar.getInstance();
		
		pagoDTO = new PagoDTO();	
		pagoDTO.setIdFormaPago(formaPago);
		pagoDTO.setMonto(new BigDecimal(this.ventanaPago.getTextImporteTotal().getText()));
		pagoDTO.setFechaPago(new Date((currenttime.getTime()).getTime()));
//		pago.agregarPago(pagoDTO);
//		darDeAltaUnPasaje();
		this.ventanaPago.setVisible(false);
		mostrarVentanaConfirmacionPasaje();
	}
	
	private void mostrarVentanaConfirmacionPasaje(){
		this.ventanaConfirmacionPasaje.mostrarVentana(true);
		this.ventanaConfirmacionPasaje.getTxtCliente().setText(""+clienteSeleccionado.getNombre()+ " "+ clienteSeleccionado.getApellido());
		this.ventanaConfirmacionPasaje.getTxtDni().setText(""+clienteSeleccionado.getDni());
		this.ventanaConfirmacionPasaje.getTxtOrigen().setText(" "+ viajeSeleccionado.getPaisOrigen().getNombre()+ ", "+viajeSeleccionado.getProvinciaOrigen().getNombre()+", "+viajeSeleccionado.getCiudadOrigen().getNombre());
		this.ventanaConfirmacionPasaje.getTxtDestino().setText(" "+ viajeSeleccionado.getPaisDestino().getNombre()+ ", "+viajeSeleccionado.getProvinciaDestino().getNombre()+", "+viajeSeleccionado.getCiudadDestino().getNombre());
		this.ventanaConfirmacionPasaje.getTxtFormaPago().setText(""+pagoDTO.getIdFormaPago().getTipo());
		this.ventanaConfirmacionPasaje.getTxtPagado().setText(""+pagoDTO.getMonto());
		this.ventanaConfirmacionPasaje.getTxtTotal().setText(""+totalaPagar);
		llenarTablaDePasajerosConfirmarPasaje();
	}
		
	private void mostrarVentanaComprobante(){
		this.ventanaComprobante.mostrarVentana(true);
		this.ventanaComprobante.getTxtCliente().setText(""+clienteSeleccionado.getNombre()+ " "+ clienteSeleccionado.getApellido());
		this.ventanaComprobante.getTxtDni().setText(""+clienteSeleccionado.getDni());
		this.ventanaComprobante.getTxtOrigenViaje().setText(" "+ viajeSeleccionado.getPaisOrigen().getNombre()+ ", "+viajeSeleccionado.getProvinciaOrigen().getNombre()+", "+viajeSeleccionado.getCiudadOrigen().getNombre());
		this.ventanaComprobante.getTxtDestinoViaje().setText(" "+ viajeSeleccionado.getPaisDestino().getNombre()+ ", "+viajeSeleccionado.getProvinciaDestino().getNombre()+", "+viajeSeleccionado.getCiudadDestino().getNombre());
		this.ventanaComprobante.getTxtImportePagado().setText(" "+ pagoDTO.getMonto());
		this.ventanaComprobante.getTxtValorViaje().setText(""+totalaPagar);
	}
	
	
	private void darAltaDeUnPasaje(ActionEvent dp) {
		ViajeDTO viajeDTO = viajeSeleccionado;
		ClienteDTO cliente = clienteSeleccionado;
		BigDecimal valorViaje = totalaPagar;
		EstadoPasajeDTO estadoPasaje = calcularEstadoPasaje();
		List<PasajeroDTO> pasajeros = pasajeros_en_reserva;
		
		
		viajeDTO.setCapacidad(viajeSeleccionado.getCapacidad()-pasajeros.size()); //Restamos la capacidad del viaje segun la cantidad de pasajeros
		//Modelo viaje
		viaje.editarViaje(viajeDTO);
		
		//Agrego el pago
		pagoDTO.setAdministrativo(administrativoLogueado);
		pago.agregarPago(pagoDTO);
		
		PagoDTO pagoPasaje = pago.getUltimoRegistroPago();
		
		//Agrego el pasaje
		pasajeDTO = new PasajeDTO(0,viajeDTO,administrativoLogueado,cliente,calcularFechaReserva(viajeDTO.getFechaSalida()),valorViaje,estadoPasaje,
				pasajeros);
		
		pasaje.agregarPasaje(pasajeDTO);
		
		PasajeDTO pDTO = pasaje.getUltimoRegistroPasaje();
		
		Pagos_PasajeDTO pagos_pasajeDTO = new Pagos_PasajeDTO();
		pagos_pasajeDTO.setPago(pagoPasaje);
		pagos_pasajeDTO.setPasaje(pDTO);
		
		pagos_pasaje.agregarPagoPasaje(pagos_pasajeDTO);
		
//		pago.agregarPago(pagoDTO);
//		pasaje.agregarPasaje(pasajeDTO);
//		//Vinculamos el pasaje con los pasajeros
//		Pasaje_PasajerosDTO pasaje_pasajeros = new Pasaje_PasajerosDTO();
//		
//		//Agregamos los pasajeros 
//		for(PasajeroDTO pasajeroDTO : pasajeros_en_reserva) {
//			pasajero.agregarPasajero(pasajeroDTO);
//			pasaje_pasajeros.setIdPasaje(pasajeDTO.getIdPasaje());
//			pasaje_pasajeros.setIdPasajero(pasajeroDTO.getIdPasajero());
//		
//		pasajes_pasajeros.agregarPasajePasajero(pasaje_pasajeros);
//		}
		this.ventanaConfirmacionPasaje.setVisible(false);
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
			ret = estado.getFormaPagoByName("vendido");
		}
		else {
			if(pagoDTO.getMonto().equals(new BigDecimal(0))) {
				ret = estado.getFormaPagoByName("pendiente");
			}
			else {
				ret = estado.getFormaPagoByName("reservado");
			}
		}
		return ret;
	}
	
	private void mostrarComprobanteReserva(ActionEvent cr){
		Reporte reporte = new Reporte();
		reporte.reporteReserva(pasajeDTO);
		reporte.mostrar();
	}
	
	private void mostrarComprobantePago(ActionEvent cp){
		if(pagoDTO.getMonto().compareTo(new BigDecimal(0))!=0){
			Reporte reporte = new Reporte();
			reporte.reportePago(pasajeDTO);
			reporte.mostrar();
		}else{
			JOptionPane.showMessageDialog(null, "Solo se realizó reserva", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void eliminarPasaje(int filaSeleccionada){
		int confirm = JOptionPane.showOptionDialog(
		            null,"¿Estás seguro que quieres cancelar el pasaje?", 
				             "Cancelar pasaje", JOptionPane.YES_NO_OPTION,
				             JOptionPane.ERROR_MESSAGE, null, null, null);
	 if (confirm == 0){
		 this.pasaje.borrarPasaje(pasajes_en_tabla.get(filaSeleccionada));
	 }
	}
	
	public void editarPasaje(int filaSeleccionada){
		verDatosDelPasaje(filaSeleccionada);
	}
	
	private void verDatosDelPasaje(int filaSeleccionada) {
		if (filaSeleccionada != -1){
			this.ventanaVisualizarPasaje.getBtnPagar().setVisible(true);
			this.ventanaVisualizarPasaje.getLblClienteDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getCliente().getNombre());
			this.ventanaVisualizarPasaje.getLblDniDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getCliente().getDni());
			this.ventanaVisualizarPasaje.getLblOrigenDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getCiudadOrigen().getNombre());
			this.ventanaVisualizarPasaje.getLblDestinoDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getCiudadDestino().getNombre());
			this.ventanaVisualizarPasaje.getLblTransporteDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getTransporte().getNombre());
			this.ventanaVisualizarPasaje.getLblEstadoPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getEstadoDelPasaje().getNombre());
			this.ventanaVisualizarPasaje.getLblMontoDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getValorViaje());
//			this.ventanaVisualizarPasaje.getLblRestante().setText(" "+ calcularMontoQueSeDebeDelPasaje(filaSeleccionada));
			
			llenarTablaPagos(pasajes_en_tabla.get(filaSeleccionada).getIdPasaje());
			
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

	private void verTablaPagos(ActionEvent vp) {
		this.ventanaTablaPagos.mostrarVentana(true);
	}

//	private BigDecimal calcularMontoQueSeDebeDelPasaje(int filaSeleccionada) {
//		BigDecimal Valor1 = this.pasajes_en_tabla.get(filaSeleccionada).getValorViaje();
//		BigDecimal Valor2 = this.pasajes_en_tabla.get(filaSeleccionada).getPago().getMonto();
//		totalaPagar = Valor1.subtract(Valor2);
//		return totalaPagar;
//	}
		
	private void salirVentanaVisualizarPasaje(ActionEvent s) {
		this.ventanaVisualizarPasaje.mostrarVentana(false);
	}
	
}