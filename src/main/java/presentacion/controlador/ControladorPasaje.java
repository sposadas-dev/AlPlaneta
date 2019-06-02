package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import correo.EnvioDeCorreo;
import dto.AdministrativoDTO;
import dto.ClienteDTO;
import dto.EstadoPasajeDTO;
import dto.FormaPagoDTO;
import dto.PagoDTO;
import dto.PasajeDTO;
import dto.PasajeroDTO;
import dto.ViajeDTO;
import generatePDF.GeneratePDF;
import modelo.Cliente;
import modelo.EstadoPasaje;
import modelo.FormaPago;
import modelo.ModeloViaje;
import modelo.Pago;
import modelo.Pasaje;
import modelo.Pasajero;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.reportes.Reporte;
import presentacion.vista.administrativo.VentanaCargaPasajero;
import presentacion.vista.administrativo.VentanaPago;
import presentacion.vista.administrativo.VentanaPasajero;
import presentacion.vista.administrativo.VentanaTablaViajes;
import presentacion.vista.administrativo.VentanaVisualizarClientes;
import presentacion.vista.administrativo.VentanaVisualizarPasaje;

public class ControladorPasaje {
	private EnvioDeCorreo envioCorreo;
	private GeneratePDF pdf;
	
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

	public ControladorPasaje(VentanaVisualizarClientes ventanaVisualizarClientes, Cliente cliente, AdministrativoDTO administrativoLogueado){
		this.ventanaVisualizarClientes = ventanaVisualizarClientes;
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
		this.pdf = new GeneratePDF();
		this.envioCorreo = new EnvioDeCorreo();
		
		this.pasajeros_en_reserva = new ArrayList<PasajeroDTO>();
		this.pasajes_en_tabla = pasaje.obtenerPasajes();
		this.ventanaVisualizarClientes.getBtnConfirmar().addActionListener(c->confirmarSeleccionCliente(c));

		this.ventanaCargaPasajero.getBtnAgregarPasajero().addActionListener(ap->cargarPasajero(ap));
		this.ventanaCargaPasajero.getBtnConfirmar().addActionListener(ap->confirmarPasajeros(ap));
		this.ventanaCargaPasajero.getBtnAtras().addActionListener(a->volverVentanaViaje(a));
		
		this.ventanaTablaViajes.getBtnAtras().addActionListener(a->volverVentanaCliente(a));
		this.ventanaTablaViajes.getBtnConfirmar().addActionListener(cv->confirmarSeleccionViaje(cv));

		this.ventanaPasajero.getBtnCargarDatos().addActionListener(cd->cargarDatosPasajero(cd));
		this.ventanaPago.getBtnPago().addActionListener(pago->darAltaDelPago(pago));
		this.ventanaPago.getBtnAtras().addActionListener(vc->volverVentanaCargaPasajero(vc));
		this.ventanaVisualizarPasaje.getBtnAceptar().addActionListener(s->salirVentanaVisualizarPasaje(s));

		this.administrativoLogueado= administrativoLogueado;
	}


	public void iniciar(){
		this.llenarTablaClientes();
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
		this.ventanaCargaPasajero.mostrarVentana(false);
		this.ventanaTablaViajes.mostrarVentana(true);
	}
	
	private void llenarTablaClientes(){
		this.ventanaVisualizarClientes.getModelClientes().setRowCount(0); //Para vaciar la tabla
		this.ventanaVisualizarClientes.getModelClientes().setColumnCount(0);
		this.ventanaVisualizarClientes.getModelClientes().setColumnIdentifiers(this.ventanaVisualizarClientes.getNombreColumnasClientes());
			
		this.clientes_en_tabla = cliente.obtenerClientes();
			
		for (int i = 0; i < this.clientes_en_tabla.size(); i++)
		{
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
	
	private void darAltaDelPago(ActionEvent p) {
		
		FormaPago f = new FormaPago(new DAOSQLFactory());
		FormaPagoDTO formaPago = f.getFormaPagoByName(ventanaPago.getComboBoxFormaPago().getSelectedItem().toString());
		
		Calendar currenttime = Calendar.getInstance();
		
		pagoDTO = new PagoDTO();	
		pagoDTO.setIdFormaPago(formaPago);
		pagoDTO.setMonto(new BigDecimal(this.ventanaPago.getTextImporteTotal().getText()));
		pagoDTO.setFechaPago(new Date((currenttime.getTime()).getTime()));
		pago.agregarPago(pagoDTO);
		
		obtenerPagoConID();
		
		darDeAltaUnPasaje();
		this.ventanaPago.setVisible(false);
		
	}

	private void obtenerPagoConID() {
		ArrayList<PagoDTO> pagos = (ArrayList<PagoDTO>) pago.obtenerPagos();
		System.out.println("obtenemos todos los pagos:"+pagos.size());
		
		System.out.println("dto:"+pagoDTO.getFechaPago());
		System.out.println("dto:"+pagoDTO.getMonto());
		System.out.println("dto:"+pagoDTO.getIdFormaPago().getIdFormaPago());
		
		for(PagoDTO pag: pagos){
//			System.out.println("db:"+pag.getFechaPago());
//			System.out.println("db:"+pag.getMonto());
//			System.out.println("db:"+pag.getIdFormaPago().getIdFormaPago());
			
			if(pag.getFechaPago().compareTo(pagoDTO.getFechaPago())==0){
				System.out.println("Fechas Iguales");
				if(pag.getIdFormaPago().getIdFormaPago()== pagoDTO.getIdFormaPago().getIdFormaPago()){
					System.out.println("IdPago iguales");
					if(pag.getMonto()==(pagoDTO.getMonto())){
						System.out.println("Monto Iguales");
						pagoDTO = pag;
					}
				}
				
			}
		}		
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
		System.out.println("ID:" + pagoDTO.getIdPago());
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
		generarVoucherMail(pasajeDTO,cliente);
		
	}
	private void generarVoucherMail(PasajeDTO pasaje,ClienteDTO cliente){
		this.pdf.createPDF(pasaje, cliente);//(pasaje,cliente); // se crea el pdf en resource
	//	TODO: this.envioCorreo.enviarAdjunto(cliente.getMail());
		this.envioCorreo.enviarAdjunto(cliente.getMail());
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