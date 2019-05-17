package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import modelo.Cliente;
import modelo.ModeloViaje;
import modelo.Pago;
import modelo.Pasaje;
import modelo.Pasajero;
import dto.AdministrativoDTO;
import dto.ClienteDTO;
import dto.EstadoPasajeDTO;
import dto.LoginDTO;
import dto.PagoDTO;
import dto.PasajeDTO;
import dto.PasajeroDTO;
import dto.RolDTO;
import dto.ViajeDTO;
import persistencia.dao.mysql.DAOSQLFactory;
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
	
	private PagoDTO pagoDTO;

	public ControladorPasaje(VentanaVisualizarClientes ventanaVisualizarClientes, Cliente cliente){
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
		this.ventanaPago.getBtnPago().addActionListener(p->darDeAltaUnPasaje(p));
		this.ventanaPago.getBtnPago().addActionListener(pago->darAltaDelPago(pago));

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
			Object[] fila = {this.viajes_en_tabla.get(i).getOrigenViaje().getNombre(),
							this.viajes_en_tabla.get(i).getDestinoViaje().getNombre(),
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
		this.ventanaPago.getLblMontoaPagar().setText("$ "+calcularMontoDePasaje().toString());
		this.ventanaPago.setVisible(true);
	}
	
	private void darAltaDelPago(ActionEvent p) {
		pagoDTO = new PagoDTO();
		Calendar currenttime = Calendar.getInstance();
		 
		pagoDTO.setMonto(new BigDecimal(this.ventanaPago.getTextImporteTotal().getText()));
		pagoDTO.setFechaPago(new Date((currenttime.getTime()).getTime()));
	
		pago.agregarPago(pagoDTO);
				
		this.ventanaPago.setVisible(false);
	}
	
	private void darDeAltaUnPasaje(ActionEvent aP) {
		
		ViajeDTO viaje = viajeSeleccionado;
		ClienteDTO cliente = clienteSeleccionado;
		RolDTO rol = new RolDTO(2,"administrativo");
		LoginDTO login = new LoginDTO(1,"andres","asd",rol);
		AdministrativoDTO administrativo = new AdministrativoDTO (1,"Andres Gandolfi",login);
		int cantPasajeros = pasajeros_en_reserva.size();

		BigDecimal valorViaje = totalaPagar;
		EstadoPasajeDTO estadoPasaje = calcularEstadoPasaje();
		List<PasajeroDTO> pasajeros = pasajeros_en_reserva;
		
		
		PasajeDTO pasajeDTO = new PasajeDTO(0,viaje,administrativo,cantPasajeros,cliente,null,
				valorViaje,estadoPasaje,pagoDTO,pasajeros);
		
		
		pasaje.agregarPasaje(pasajeDTO);
//		this.llenarTablaPasajes();
		this.ventanaPago.setVisible(false);
		
	}
	

//	private void llenarTablaPasajes(){
//		this.vista.getPanelPasaje().getModelClientes().setRowCount(0); //Para vaciar la tabla
//		this.vista.getPanelPasaje().getModelClientes().setColumnCount(0);
//		this.vista.getPanelPasaje().getModelClientes().setColumnIdentifiers(this.vista.getPanelPasaje().getNombreColumnasClientes());
//
//
//		this.pasajes_en_tabla = pasaje.obtenerPasajes();
//			
//		for (int i = 0; i < this.pasajes_en_tabla.size(); i++)
//		{
//			Object[] fila = {
//							this.pasajes_en_tabla.get(i).getCliente().getDni(),
//							this.pasajes_en_tabla.get(i).getCliente().getNombre(),
//							this.pasajes_en_tabla.get(i).getCliente().getApellido(),
//							this.pasajes_en_tabla.get(i).getIdPasaje(),
//							this.pasajes_en_tabla.get(i).getViaje().getOrigenViaje().getNombre(),
//							this.pasajes_en_tabla.get(i).getViaje().getDestinoViaje().getNombre(),
//							this.pasajes_en_tabla.get(i).getViaje().getFechaSalida(),
//							this.pasajes_en_tabla.get(i).getViaje().getHoraSalida(),
//							this.pasajes_en_tabla.get(i).getViaje().getPrecio(),
//							this.pasajes_en_tabla.get(i).getCantidadPasajeros(),
//							this.pasajes_en_tabla.get(i).getEstadoDelPasaje().getNombre()
//			};
//							this.vista.getPanelPasaje().getModelClientes().addRow(fila);
//		}		
//	}	
	
	private BigDecimal calcularMontoDePasaje() {
		BigDecimal Valor1 = this.viajeSeleccionado.getPrecio();
		totalaPagar = Valor1;
		return totalaPagar.multiply(new BigDecimal(pasajeros_en_reserva.size()));
	}
		
	private EstadoPasajeDTO calcularEstadoPasaje() {
		EstadoPasajeDTO ret;
		if(totalaPagar.compareTo(pagoDTO.getMonto()) == 0){ //si son iguales
			ret = new EstadoPasajeDTO(1,"Vendido","El monto abonado es el total a pagar");
		}
		else {
			if(pagoDTO.getMonto().equals(new BigDecimal(0))) {
				ret = new EstadoPasajeDTO(3,"Pendiente","El monto abonado es 0");
			}
			else {
				ret = new EstadoPasajeDTO(2,"Reservado","El monto abonado es menor al total a pagar");
			}
		}
		return ret;
	}
}