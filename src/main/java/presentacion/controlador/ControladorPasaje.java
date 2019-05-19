package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

import modelo.Cliente;
import modelo.EstadoPasaje;
import modelo.FormaPago;
import modelo.ModeloViaje;
import modelo.Pago;
import modelo.Pasaje;
import modelo.Pasaje_Pasajeros;
import modelo.Pasajero;
import dto.AdministrativoDTO;
import dto.ClienteDTO;
import dto.EstadoPasajeDTO;
import dto.FormaPagoDTO;
import dto.PagoDTO;
import dto.PasajeDTO;
import dto.Pasaje_PasajerosDTO;
import dto.PasajeroDTO;
import dto.ViajeDTO;
import persistencia.dao.mysql.DAOSQLFactory;
import persistencia.dao.mysql.Pasaje_PasajerosDAOSQL;
import presentacion.vista.administrativo.VentanaCargaPasajero;
import presentacion.vista.administrativo.VentanaPago;
import presentacion.vista.administrativo.VentanaPasajero;
import presentacion.vista.administrativo.VentanaTablaViajes;
import presentacion.vista.administrativo.VentanaVisualizarClientes;

public class ControladorPasaje {

	private VentanaVisualizarClientes ventanaVisualizarClientes;
	private VentanaTablaViajes ventanaTablaViajes;
	private VentanaCargaPasajero ventanaCargaPasajero;
	private VentanaPasajero ventanaPasajero;
	private VentanaPago ventanaPago;
	
	private List<ClienteDTO> clientes_en_tabla;
	private List <ViajeDTO> viajes_en_tabla;
	private List<PasajeroDTO> pasajeros_en_reserva;
	
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
		
		this.cliente = cliente;
		this.viaje = new ModeloViaje(new DAOSQLFactory());
		this.pasajero = new Pasajero (new DAOSQLFactory());
		this.pago = new Pago(new DAOSQLFactory());
		this.pasaje = new Pasaje(new DAOSQLFactory());
		
		this.pasajeros_en_reserva = new ArrayList<PasajeroDTO>();
		
		this.ventanaVisualizarClientes.getBtnConfirmar().addActionListener(c->confirmarSeleccionCliente(c));
		
		this.ventanaTablaViajes.getBtnAtras().addActionListener(a->volverVentanaCliente(a));
		this.ventanaTablaViajes.getBtnConfirmar().addActionListener(cv->confirmarSeleccionViaje(cv));
	
		this.ventanaCargaPasajero.getBtnAgregarPasajero().addActionListener(ap->cargarPasajero(ap));
		this.ventanaCargaPasajero.getBtnConfirmar().addActionListener(ap->confirmarPasajeros(ap));
		this.ventanaCargaPasajero.getBtnAtras().addActionListener(a->volverVentanaViaje(a));
		
		this.ventanaPasajero.getBtnCargarDatos().addActionListener(cd->cargarDatosPasajero(cd));
//		this.ventanaPago.getRadioPaga().addActionListener(r->activarBotones(r));
		this.ventanaPago.getBtnPago().addActionListener(pago->darAltaDelPago(pago));
//		this.ventanaPago.getBtnPago().addActionListener(p->darDeAltaUnPasaje(p));
//		totalaPagar = new BigDecimal(0);
		this.administrativoLogueado= administrativoLogueado;
	}

	public void iniciar(){
		this.llenarTablaClientes();
	}
	
	private void confirmarSeleccionCliente(ActionEvent c) {
		this.ventanaVisualizarClientes.mostrarVentana(false);
		int filaSeleccionada = this.ventanaVisualizarClientes.getTablaClientes().getSelectedRow();
		clienteSeleccionado = clientes_en_tabla.get(filaSeleccionada);
		
		this.ventanaTablaViajes.mostrarVentana(true);
		llenarTablaViajes();
	}
	
	private void confirmarSeleccionViaje(ActionEvent sv){
		this.ventanaTablaViajes.mostrarVentana(false);
		int filaSeleccionada = this.ventanaTablaViajes.getTablaViajes().getSelectedRow();
		viajeSeleccionado = viajes_en_tabla.get(filaSeleccionada);
		this.ventanaCargaPasajero.mostrarVentana(true);
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
							this.viajes_en_tabla.get(i).getCiudadOrigen().getNombre(),
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
	
		
		darDeAltaUnPasaje();
		this.ventanaPago.setVisible(false);
		
	}
	
	private void darDeAltaUnPasaje() {
		ViajeDTO viaje = viajeSeleccionado;
		ClienteDTO cliente = clienteSeleccionado;
		BigDecimal valorViaje = totalaPagar;
	
		EstadoPasajeDTO estadoPasaje = calcularEstadoPasaje();
		List<PasajeroDTO> pasajeros = pasajeros_en_reserva;
	
		PasajeDTO pasajeDTO = new PasajeDTO();
		pasajeDTO.setViaje(viaje);
		pasajeDTO.setAdministrativo(administrativoLogueado);
		pasajeDTO.setCliente(cliente);
		pasajeDTO.setFechaVencimiento(null);
		pasajeDTO.setValorViaje(valorViaje);
		pasajeDTO.setEstadoDelPasaje(estadoPasaje);
		pasajeDTO.setPago(pagoDTO);
		pasajeDTO.setPasajeros(pasajeros);
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
}