package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import dto.AdministrativoDTO;
import dto.ClienteDTO;
import dto.EstadoEventoDTO;
import dto.EstadoPasajeDTO;
import dto.EventoDTO;
import dto.FormaPagoDTO;
import dto.LoginDTO;
import dto.MedioContactoDTO;
import dto.PagoDTO;
import dto.PasajeDTO;
import dto.PasajeroDTO;
import dto.RolDTO;
import dto.ViajeDTO;
import modelo.Cliente;
import modelo.EstadoPasaje;
import modelo.FormaPago;
import modelo.ModeloEstadoEvento;
import modelo.ModeloEvento;
import modelo.ModeloViaje;
import modelo.Pago;
import modelo.Pasaje;
import modelo.Pasajero;
import persistencia.dao.mysql.DAOSQLFactory;
import persistencia.dao.mysql.EstadoEventoDAOSQL;
import presentacion.reportes.Reporte;
import presentacion.vista.administrativo.PanelEvento;
import presentacion.vista.administrativo.VentanaCargaPasajero;
import presentacion.vista.administrativo.VentanaPago;
import presentacion.vista.administrativo.VentanaPasajero;
import presentacion.vista.administrativo.VentanaRegistrarEvento;
import presentacion.vista.administrativo.VentanaTablaViajes;
import presentacion.vista.administrativo.VentanaVisualizarClientes;
import presentacion.vista.administrativo.VentanaVisualizarPasaje;

public class ControladorEvento {

	private VentanaVisualizarClientes ventanaVisualizarClientes;
	private VentanaTablaViajes ventanaTablaViajes;
	private VentanaCargaPasajero ventanaCargaPasajero;
	private VentanaPasajero ventanaPasajero;
	private VentanaPago ventanaPago;
	private VentanaVisualizarPasaje ventanaVisualizarPasaje;
	
	private List<ClienteDTO> clientes_en_tabla;
	private List <ViajeDTO> viajes_en_tabla;
	private List<PasajeroDTO> pasajeros_en_reserva;
	private List<PasajeDTO> pasajes_en_tabla;
	private List<EventoDTO> eventos_en_tabla;
	private ModeloEvento evento;
	private ModeloEstadoEvento estadoEvento;
	private VentanaRegistrarEvento ventanaEvento;
	private ClienteDTO clienteSeleccionado; //cliente que selecciona en la tabla 
	private ViajeDTO viajeSeleccionado;
	private Cliente cliente;
	private Pasajero pasajero;
	private ModeloViaje viaje;
	private Pago pago;
	private Pasaje pasaje;
	private BigDecimal totalaPagar;
	private AdministrativoDTO administrativoLogueado;
	private PagoDTO pagoDTO;
	private PanelEvento panelEvento;

	public ControladorEvento(VentanaRegistrarEvento ventanaEvento, ModeloEvento evento, AdministrativoDTO administrativoLogueado){
		this.ventanaEvento = ventanaEvento;
		this.ventanaTablaViajes = VentanaTablaViajes.getInstance();
		this.ventanaCargaPasajero = VentanaCargaPasajero.getInstance();
		this.ventanaPasajero = VentanaPasajero.getInstance();
		this.ventanaPago = VentanaPago.getInstance();
		this.ventanaVisualizarPasaje = VentanaVisualizarPasaje.getInstance();
		
		this.cliente = cliente;
		this.viaje = new ModeloViaje(new DAOSQLFactory());
		this.pasajero = new Pasajero (new DAOSQLFactory());
		this.pago = new Pago(new DAOSQLFactory());
		this.pasaje = new Pasaje(new DAOSQLFactory());
		
		this.panelEvento = new PanelEvento();
		this.estadoEvento = new ModeloEstadoEvento(new DAOSQLFactory());
		this.evento = evento;
		
		this.pasajeros_en_reserva = new ArrayList<PasajeroDTO>();
		this.pasajes_en_tabla = pasaje.obtenerPasajes();
//		this.ventanaVisualizarClientes.getBtnConfirmar().addActionListener(c->confirmarSeleccionCliente(c));

//		this.ventanaCargaPasajero.getBtnAgregarPasajero().addActionListener(ap->cargarPasajero(ap));
//		this.ventanaCargaPasajero.getBtnConfirmar().addActionListener(ap->confirmarPasajeros(ap));
//		this.ventanaCargaPasajero.getBtnAtras().addActionListener(a->volverVentanaViaje(a));
//		
//		this.ventanaTablaViajes.getBtnAtras().addActionListener(a->volverVentanaCliente(a));
//		this.ventanaTablaViajes.getBtnConfirmar().addActionListener(cv->confirmarSeleccionViaje(cv));

//		this.ventanaPasajero.getBtnCargarDatos().addActionListener(cd->cargarDatosPasajero(cd));
//		this.ventanaPago.getBtnPago().addActionListener(pago->darAltaDelPago(pago));
//		this.ventanaPago.getBtnAtras().addActionListener(vc->volverVentanaCargaPasajero(vc));
//		this.ventanaVisualizarPasaje.getBtnAceptar().addActionListener(s->salirVentanaVisualizarPasaje(s));

		this.ventanaEvento.getBtnRegistrar().addActionListener(rc->registrarEvento(rc));
		this.ventanaEvento.getBtnAgregarCliente().addActionListener(r->mostrarClientes(r));
		this.ventanaEvento.getBtnCancelar().addActionListener(cv->cerrarVentanaEvento(cv));

		this.administrativoLogueado= administrativoLogueado;
	}


	public void iniciar(){
		System.out.println("ENTRA A INICIAR");
		this.llenarComboEstados();
		this.llenarComboHoraEvento();
	}
	
	public void llenarComboEstados() {
		List<EstadoEventoDTO> estados = new EstadoEventoDAOSQL().readAll();
		String[] nombresEstados = new String[estados.size()];
		for(int i=0; i<estados.size();i++){
			String e = estados.get(i).getNombre();
			nombresEstados [i] = e;
		}	
		this.ventanaEvento.getComboEstadoEvento().setModel(new DefaultComboBoxModel(nombresEstados));
	}
	
	private void llenarComboHoraEvento() {
		String [] horarios = {"8:00", "8:30", "9:00", "9:30", "10:00","10:30","11:00","11:30","12:30",
				"13:00","13:30","14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30"};
		this.ventanaEvento.getComboHoraEvento().setModel(new DefaultComboBoxModel(horarios));
	}
	
	
	public void registrarEvento(ActionEvent rc){
		if(camposLlenos()){
			java.util.Date fecha = new java.util.Date(); java.sql.Date fechaIngreso = new java.sql.Date(fecha.getTime());	
			java.sql.Date fechaEvento = convertUtilToSql(this.ventanaEvento.getDateFechaEvento().getDate());
			java.sql.Time horaEvento = obtenerHora(this.ventanaEvento.getComboHoraEvento().getSelectedItem().toString());
			String descripcion = this.ventanaEvento.getTxtDescripcion().getText();
			
			MedioContactoDTO medioContacto = new MedioContactoDTO(0,"44510021","1545899865","juan.perez@gmail.com");
			RolDTO rol = new RolDTO(5,"cliente");
			LoginDTO login = new LoginDTO (1,"usuariojuan","juan123",rol);
			ClienteDTO cliente = new ClienteDTO(2,"Nico","Avila","32125322",fechaIngreso,medioContacto,login);
			
			AdministrativoDTO administrativo = this.administrativoLogueado;
			EstadoEventoDTO estado = obtenerEstadoEventoPorNombre(this.ventanaEvento.getComboEstadoEvento().getSelectedItem().toString());
			
			EventoDTO eventoRegistrado = new EventoDTO(0,fechaIngreso,fechaEvento,horaEvento,descripcion,cliente,administrativo,estado);
		
			evento.agregarEvento(eventoRegistrado);
			this.ventanaEvento.limpiarCampos();
			this.ventanaEvento.cerrarVentana();
		}
	}
	
	private void mostrarClientes(ActionEvent r) {
		
	}
	
	private Time obtenerHora(String miHora) {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
		long ms = 0;
		try { ms = sdf.parse(miHora).getTime();
		} catch (ParseException e) { e.printStackTrace();}
		
		Time t = new Time(ms);
		return t;
	}
	
	private boolean camposLlenos(){
		if (ventanaEvento.getDateFechaEvento().getDate() == null || ventanaEvento.getTxtDescripcion().getText().isEmpty()){
				JOptionPane.showMessageDialog(null, "Debe cargar todos los campos", "Mensaje", JOptionPane.ERROR_MESSAGE);
				return false;
		}
			return true;
	}
	
	private EstadoEventoDTO obtenerEstadoEventoPorNombre(String nombre){
		for(EstadoEventoDTO e : estadoEvento.obtenerEstadosEvento())
			if(e.getNombre().equals(nombre))
				return e;
		return null;
	}
	
	private void cerrarVentanaEvento(ActionEvent cv) {
		this.ventanaEvento.limpiarCampos();
		this.ventanaEvento.cerrarVentana();
	}
	
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
	
	private void volverVentanaCliente(ActionEvent a) {
		this.ventanaTablaViajes.mostrarVentana(false);
		this.ventanaVisualizarClientes.mostrarVentana(true);
	}
	
	private void volverVentanaViaje(ActionEvent a) {
		//this.ventanaAgregarEvento.mostrarVentana(false);
		this.ventanaTablaViajes.mostrarVentana(true);
	}
	
	/*private void llenarTablaEventos(){
		System.out.println("ENTRA A TABLA EVENTO EN CONTROLADOREVENTO");
		this.panelEvento.getModelEventos().setRowCount(0); //Para vaciar la tabla
		this.panelEvento.getModelEventos().setColumnCount(0);
		this.panelEvento.getModelEventos().setColumnIdentifiers(this.panelEvento.getNombreColumnasEventos());
			
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
							this.eventos_en_tabla.get(i).getEstadoEvento().getNombre()
			};
							this.panelEvento.getModelEventos().addRow(fila);
		}		
	}*/
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
	
	private void cargarPasajero(ActionEvent ap) {
		this.ventanaPasajero.setVisible(true);
		PasajeroDTO nuevoPasajero = new PasajeroDTO();
		nuevoPasajero.setNombre(ventanaPasajero.getTxtNombre().getText());
		nuevoPasajero.setApellido(ventanaPasajero.getTxtApellido().getText());
		nuevoPasajero.setDni(ventanaPasajero.getTxtDni().getText());
		nuevoPasajero.setTelefono(ventanaPasajero.getTxtTelefono().getText());
		nuevoPasajero.setEmail(ventanaPasajero.getTxtEmail().getText());
		
		pasajero.agregarPasajero(nuevoPasajero);
		pasajeros_en_reserva.add(nuevoPasajero); 
	
		llenarTablaDePasajeros();
	}
	
	private void llenarTablaDePasajeros(){
		this.ventanaCargaPasajero.getModelPasajeros().setRowCount(0);
		this.ventanaCargaPasajero.getModelPasajeros().setColumnCount(0);
		this.ventanaCargaPasajero.getModelPasajeros().setColumnIdentifiers(this.ventanaCargaPasajero.getNombreColumnas());
		for(int i=0; i<pasajeros_en_reserva.size();i++){
			Object[] fila = { 
					pasajeros_en_reserva.get(i).getNombre(),
					pasajeros_en_reserva.get(i).getApellido(),
					pasajeros_en_reserva.get(i).getDni()
			};
			this.ventanaCargaPasajero.getModelPasajeros().addRow(fila);
		}
//		this.ventanaReserva.getLblCantidadDePasajeros().setText(pasajerosEnEstaReserva.size()+" Pasajeros fueron cargados");
	}
	
	private void cargarDatosPasajero(ActionEvent cd) {
		this.ventanaPasajero.limpiarCampos();
		this.ventanaPasajero.setVisible(false);
	}
	
	private void confirmarPasajeros(ActionEvent ap) {
		this.ventanaCargaPasajero.setVisible(false);
		cargarComboBoxFormaDePago();
		this.ventanaPago.getLblMontoaPagar().setText("$ "+calcularMontoDePasaje().toString());
		this.ventanaPago.setVisible(true);
	}
	
	
	private void volverVentanaCargaPasajero(ActionEvent vc) {
		this.ventanaPago.mostrarVentana(false);
		this.ventanaCargaPasajero.mostrarVentana(true);
	}
	
	public Date calcularFechaReserva(Date fecha){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); 
		calendar.add(Calendar.DATE, -20);  
		return convertUtilToSql(calendar.getTime()); 
	}
	
	private java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
		
	private void darDeAltaUnPasaje() {
		ViajeDTO viaje = viajeSeleccionado;
		ClienteDTO cliente = clienteSeleccionado;
		BigDecimal valorViaje = totalaPagar;
	
		EstadoPasajeDTO estadoPasaje = calcularEstadoPasaje();
		List<PasajeroDTO> pasajeros = pasajeros_en_reserva;
	
		PagoDTO pagoPasaje = pago.getUltimoRegistroPago();
		PasajeDTO pasajeDTO = new PasajeDTO(0,viaje,administrativoLogueado,cliente,calcularFechaReserva(viaje.getFechaSalida()),valorViaje,estadoPasaje,pagoPasaje,
		pasajeros);
		pasaje.agregarPasaje(pasajeDTO);
	
//		for(PasajeroDTO p : pasajeros_en_reserva) {
//			Pasaje_PasajerosDTO pasaje_pasajero = new Pasaje_PasajerosDTO();
//			pasaje_pasajero.setIdPasaje(pasajeDTO.getIdPasaje());
//			pasaje_pasajero.setIdPasajero(p.getIdPasajero());
//
//			Pasaje_Pasajeros pasaje_pasajeros = new Pasaje_Pasajeros(new DAOSQLFactory());
//			pasaje_pasajeros.agregarPasajePasajero(pasaje_pasajero);
//		}
//		this.llenarTablaPasajes();
		this.ventanaPago.setVisible(false);
		
		generarComprobantes(pasajeDTO,pagoPasaje);
		
	}
	
	private void generarComprobantes(PasajeDTO pasajeDTO, PagoDTO pagoPasaje) {
		JOptionPane.showMessageDialog(null, "Generando comprobantes", "Comprobantes", JOptionPane.INFORMATION_MESSAGE);
		mostrarComprobanteReserva(pasajeDTO);
		if(pagoPasaje.getMonto().compareTo(new BigDecimal(0))!=0){
			mostrarComprobantePago(pasajeDTO);
		}
		
	}

	private void mostrarComprobanteReserva(PasajeDTO pasaje){
		Reporte reporte = new Reporte();
		reporte.reporteReserva(pasaje);
		reporte.mostrar();
	}
	
	private void mostrarComprobantePago(PasajeDTO pasaje){
		Reporte reporte = new Reporte();
		reporte.reportePago(pasaje);
		reporte.mostrar();
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
	
	public void eliminarPasaje(int filaSeleccionada){
		int confirm = JOptionPane.showOptionDialog(
		            null,"¿Estás seguro que quieres cancelar el pasaje?", 
				             "Cancelar pasaje", JOptionPane.YES_NO_OPTION,
				             JOptionPane.ERROR_MESSAGE, null, null, null);
	 if (confirm == 0){
		JOptionPane.showMessageDialog(null, "Pasaje cancelado","Pasaje", JOptionPane.INFORMATION_MESSAGE);
		this.pasaje.borrarPasaje(pasajes_en_tabla.get(filaSeleccionada));
	 }
	}
	
	public void editarPasaje(int filaSeleccionada){
		verDatosDelPasaje(filaSeleccionada);
	}
	
	private void verDatosDelPasaje(int filaSeleccionada) {
		if (filaSeleccionada != -1){
			if(this.pasajes_en_tabla.get(filaSeleccionada).getEstadoDelPasaje().getIdEstadoPasaje()==1){
				this.ventanaVisualizarPasaje.setVisible(true);
				this.ventanaVisualizarPasaje.getBtnPagar().setVisible(false);
				this.ventanaVisualizarPasaje.getLblClienteDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getCliente().getNombre());
				this.ventanaVisualizarPasaje.getLblDniDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getCliente().getDni());
				this.ventanaVisualizarPasaje.getLblOrigenDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getCiudadOrigen().getNombre());
				this.ventanaVisualizarPasaje.getLblDestinoDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getCiudadDestino().getNombre());
				this.ventanaVisualizarPasaje.getLblTransporteDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getTransporte().getNombre());
				this.ventanaVisualizarPasaje.getLblEstadoPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getEstadoDelPasaje().getNombre());
				this.ventanaVisualizarPasaje.getLblMontoDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getValorViaje());
				this.ventanaVisualizarPasaje.getLblRestante().setText(" "+ calcularMontoQueSeDebeDelPasaje(filaSeleccionada));

			}else{
				this.ventanaVisualizarPasaje.setVisible(true);
				this.ventanaVisualizarPasaje.getBtnPagar().setVisible(true);
				this.ventanaVisualizarPasaje.getLblClienteDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getCliente().getNombre());
				this.ventanaVisualizarPasaje.getLblDniDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getCliente().getDni());
				this.ventanaVisualizarPasaje.getLblOrigenDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getCiudadOrigen().getNombre());
				this.ventanaVisualizarPasaje.getLblDestinoDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getCiudadDestino().getNombre());
				this.ventanaVisualizarPasaje.getLblTransporteDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getTransporte().getNombre());
				this.ventanaVisualizarPasaje.getLblEstadoPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getEstadoDelPasaje().getNombre());
				this.ventanaVisualizarPasaje.getLblMontoDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getValorViaje());
				this.ventanaVisualizarPasaje.getLblRestante().setText(" "+ calcularMontoQueSeDebeDelPasaje(filaSeleccionada));
			
			}
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}

	private BigDecimal calcularMontoQueSeDebeDelPasaje(int filaSeleccionada) {
		BigDecimal Valor1 = this.pasajes_en_tabla.get(filaSeleccionada).getValorViaje();
		BigDecimal Valor2 = this.pasajes_en_tabla.get(filaSeleccionada).getPago().getMonto();
		totalaPagar = Valor1.subtract(Valor2);
		return totalaPagar;
	}
		
	private void salirVentanaVisualizarPasaje(ActionEvent s) {
		this.ventanaVisualizarPasaje.mostrarVentana(false);
	}
	
}