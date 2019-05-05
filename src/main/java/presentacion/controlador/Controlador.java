package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import dto.CiudadDTO;
import dto.HorarioReservaDTO;
import dto.PasajeDTO;
import dto.PasajeroDTO;
import dto.TransporteDTO;
import dto.ViajeDTO;
import modelo.Cliente;
import modelo.ModeloCiudad;
import modelo.ModeloViaje;
import persistencia.dao.mysql.CiudadDAOSQL;
import persistencia.dao.mysql.DAOSQLFactory;
import persistencia.dao.mysql.HorarioReservaDAOSQL;
import persistencia.dao.mysql.PasajeroDAOSQL;
import persistencia.dao.mysql.TransporteDAOSQL;
import persistencia.dao.mysql.ViajeDAOSQL;
import presentacion.vista.VentanaCargaPasajero;
import presentacion.vista.VentanaCargarViaje;
import presentacion.vista.VentanaCliente;
import presentacion.vista.VentanaFormaPago;
import presentacion.vista.VentanaPagoEfectivo;
import presentacion.vista.VentanaPagoTarjeta;
import presentacion.vista.VentanaPasajero;
import presentacion.vista.VentanaReserva;
import presentacion.vista.Vista;

public class Controlador implements ActionListener {
	
	ModeloCiudad modeloCiudad;
	private Vista vista;
	private VentanaCliente ventanaCliente;
	private Cliente cliente;
	private VentanaReserva ventanaReserva;
	private VentanaFormaPago ventanaFormaDePagos;
	private VentanaPagoTarjeta ventanaPagoTarjeta;
	private VentanaPagoEfectivo ventanaPagoEfectivo;
	private VentanaCargaPasajero ventanaCargaPasajero;
	private VentanaPasajero ventanaPasajero;
	private VentanaCargarViaje ventanaCargarViaje;
	
	public Controlador() {
		
		this.ventanaReserva = VentanaReserva.getInstance();
		this.ventanaFormaDePagos = VentanaFormaPago.getInstance();
		this.ventanaPagoTarjeta = VentanaPagoTarjeta.getInstance();
		this.ventanaPagoEfectivo = VentanaPagoEfectivo.getInstance();
		this.ventanaPasajero = VentanaPasajero.getInstance();
		this.ventanaCargarViaje = VentanaCargarViaje.getInstance();
		this.ventanaCargaPasajero = VentanaCargaPasajero.getInstance();

		
		this.ventanaReserva.getBtnReservar().addActionListener(reserv->SeleccionFormaDePago(reserv));
		this.ventanaReserva.getBtnCargaPasajeros().addActionListener(cP->mostrarVentanaCargaDePasajeros(cP));
		
		this.ventanaFormaDePagos.getBtnPago().addActionListener(pago->seleccionEstadoDelPago(pago));
		this.ventanaPagoEfectivo.getBtnRegistrarPago().addActionListener(rP->generarPasajeEfectivo(rP));
		this.ventanaPagoTarjeta.getBtnEnviar().addActionListener(rP->generarPasajeTarjeta(rP));
		
		this.ventanaCargaPasajero.getBtnAgregarPasajero().addActionListener(aP->mostrarVentanaAltaDePasajeros(aP));
		this.ventanaPasajero.getBtnCargarDatos().addActionListener(aP->darDeAltaUnPasajero(aP));
		this.ventanaCargarViaje.getBtnCrearViaje().addActionListener(aV->darAltaUnViajes(aV));
		
	}

/* - - - - - - - - - - - - - - - - - INICIALIZAR - - - - - - - - - - - - - - - - - - - -*/
	public void inicializar() throws Exception{	
//		this.vista.show();
		mostrarVentanaReserva();
		llenarValoresEnCargaDeViaje();
	}
	
	
/*- - - - - - - -  - - - - - - - METODOS DE VIAJE - - - - - - - - - - - - - - - - --  */
	private void darAltaUnViajes(ActionEvent aV) {
		ModeloCiudad modeloCiudad = new ModeloCiudad(new DAOSQLFactory());
		ModeloViaje modeloViaje = new ModeloViaje(new DAOSQLFactory());
		
/*OBTENEMOS LA CIUDAD ELEGIDA EN LA VENTANACARGA DE VIAJES*/		
		CiudadDTO origen = modeloCiudad.getCiudadByName(ventanaCargarViaje.getComboBoxCiudadOrigen().getSelectedItem().toString());
		CiudadDTO destino = modeloCiudad.getCiudadByName(ventanaCargarViaje.getComboBoxCiudadDestino().getSelectedItem().toString());

/*OBTENEMOS LAS HORAS TANDO DE SALIDA COMO DE LLEGADA, Y LA PARSEAMOS A TIPO DATE DE SQL*/
		java.util.Date dateOrigen = ventanaCargarViaje.getDateChooserFechaOrigen().getDate();
		java.sql.Date fechaSalida = new java.sql.Date(dateOrigen.getTime());
		
		java.util.Date dateDestino = ventanaCargarViaje.getDateChooserFechaDestino().getDate();
		java.sql.Date fechaLlegada = new java.sql.Date(dateDestino.getTime());
		
		String horaSalida = ventanaCargarViaje.getComboBoxHorarioSalida().getSelectedItem().toString();
		BigDecimal precio = new BigDecimal(ventanaCargarViaje.getTextPrecioViaje().getText());
		
		ViajeDTO nuevoViaje = new ViajeDTO(0, origen, destino, fechaSalida, fechaLlegada, precio, horaSalida);

		// VER POR QUE NO FUNCIONA LA CONSULTA SQL EN EL MODELO	
//		modeloViaje.agregarViaje(nuevoViaje);

		ViajeDAOSQL sql = new ViajeDAOSQL();		
		sql.insert(nuevoViaje);
		
	}
	
	private void llenarValoresEnCargaDeViaje() throws Exception{
		
		llenarComboRangoHorariosEnCargarViaje();//modificar para q levante de la base

		llenarComboCiudadesEnCargarViaje();

		mostrarVentanaCargarViaje();
		
	}
	
	private void llenarComboRangoHorariosEnCargarViaje() {
		String [] horarios = {"1:00", "2:00", "3:00", "4:00", "5:00","6:00","7:00","8:00","9:00","10:00","11:00","12:00"};
		this.ventanaCargarViaje.getComboBoxHorarioSalida().setModel(new DefaultComboBoxModel(horarios));
	}

	private void llenarComboCiudadesEnCargarViaje() {
		/*CARGAMOS LOS VALORES DE LAS CIUDADES EN LA VENTANA DE DAR ALTA VIAJE*/
				List<CiudadDTO> ciudades = new CiudadDAOSQL().readAll();
				String[] nombresCiudades = new String[ciudades.size()];
				for(int i=0; i<ciudades.size();i++){
					nombresCiudades [i] = ciudades.get(i).getNombre();
				}	
		/* LUEGO VER QUE NO SE PUEDA SELECCIONAR LA MISMA CIUDAD COMO ORIGEN Y DESTINO AL MISMO TIEMPO */
				this.ventanaCargarViaje.getComboBoxCiudadOrigen().setModel(new DefaultComboBoxModel(nombresCiudades));
				this.ventanaCargarViaje.getComboBoxCiudadDestino().setModel(new DefaultComboBoxModel(nombresCiudades));
	}

	private void mostrarVentanaCargarViaje() {
		this.ventanaCargarViaje.setVisible(true);
		
	}
	
	
/*- - - - - - - -  - - - - - - - METODOS DE PASAJERO - - - - - - - - - - - - - - - - --  */	
	
	private void darDeAltaUnPasajero(ActionEvent aP) {
		
		PasajeroDTO pasajeroDTO = new PasajeroDTO();
		pasajeroDTO.setNombre(this.ventanaPasajero.getTxtNombre().getText());
		pasajeroDTO.setApellido(this.ventanaPasajero.getTxtApellido().getText());
		pasajeroDTO.setDni(this.ventanaPasajero.getTxtDni().getText());
		
		PasajeroDAOSQL DAO = new PasajeroDAOSQL();
		DAO.insert(pasajeroDTO);
		
		/*LLENAMOS LA VENTANA CON LOS PASAJEROS DEL VIAJE*/
		llenarTablaDePasajerosEnVentanaCargaPasajeros();
		
	}
	
	private void llenarTablaDePasajerosEnVentanaCargaPasajeros(){
		this.ventanaCargaPasajero.getModelPasajeros().setRowCount(0);
		this.ventanaCargaPasajero.getModelPasajeros().setColumnCount(0);
		this.ventanaCargaPasajero.getModelPasajeros().setColumnIdentifiers(this.ventanaCargaPasajero.getNombreColumnas());
		ArrayList<PasajeroDTO> pasajeros_en_tabla = (ArrayList<PasajeroDTO>) new PasajeroDAOSQL().readAll();
		for(int i=0; i< pasajeros_en_tabla.size();i++){
			Object[] fila = { 
					pasajeros_en_tabla.get(i).getNombre(),
					pasajeros_en_tabla.get(i).getApellido(),
					pasajeros_en_tabla.get(i).getDni()
					};
			this.ventanaCargaPasajero.getModelPasajeros().addRow(fila);
		}
	}

	private void mostrarVentanaAltaDePasajeros(ActionEvent aP) {
		this.ventanaReserva.mostrarVentana(false);
		this.ventanaPasajero.setVisible(true);
	}
	
	private void mostrarVentanaCargaDePasajeros(ActionEvent cP) {
		this.ventanaReserva.mostrarVentana(false);
		this.ventanaCargaPasajero.mostrarVentana(true);
	}
	
/*- - - - - - - -  - - - - - - - METODOS DE RESERVA - - - - - - - - - - - - - - - - --  */		
	
	
	private void llenarValoresEnReserva(){
		llenarComboRangoDeHorarioEnReserva();

		llenarComboTransporteEnReserva();
		
		/*CAMBIAR LA SOLICITUD AL DAOSQL POR LA SOLICITUD AL MODELO*/
/*       PREGUNTAR A SOL POR QUE NO FUNCIONA !! -.-"        */		
		llenarListModelViajesEnReserva();
		
	}

	private void llenarListModelViajesEnReserva() {
		this.ventanaReserva.getListModelViajesDisponibles().removeAllElements();
		
		List<ViajeDTO> viajesDisponibles = new ViajeDAOSQL().readAll();
		System.out.println("Se pidieron"+ viajesDisponibles.size()+" valores en la db de viajes");
		String[] viajes = new String[viajesDisponibles.size()];
		
		for(int i=0; i<viajesDisponibles.size();i++){
			CiudadDTO origen = viajesDisponibles.get(i).getOrigenViaje();
			CiudadDTO destino = viajesDisponibles.get(i).getDestinoViaje();
			
			viajes [i] = origen.getNombre()+" - "+destino.getNombre();
			this.ventanaReserva.getListModelViajesDisponibles().addElement(origen.getNombre()+" - "+destino.getNombre());
		}
	}

	private void llenarComboTransporteEnReserva() {
		List<TransporteDTO> transportesDTO = new TransporteDAOSQL().readAll();
		String[] transoportes = new String[transportesDTO.size()];
		for(int i=0; i<transportesDTO.size();i++){
			String rango = transportesDTO.get(i).getNombreTransporte();
			transoportes [i] = rango;
		}
		this.ventanaReserva.getComboBoxTransporte().setModel(new DefaultComboBoxModel(transoportes));
	}

	private void llenarComboRangoDeHorarioEnReserva() {
		List<HorarioReservaDTO> horarios = new HorarioReservaDAOSQL().readAll();
		String[] rangoHorarios = new String[horarios.size()];
		for(int i=0; i<horarios.size();i++){
			String rango = horarios.get(i).getHoraInicio() + " - " + horarios.get(i).getHoraFin();
			rangoHorarios [i] = rango;
		}
		this.ventanaReserva.getComboBoxRangoHorario().setModel(new DefaultComboBoxModel(rangoHorarios));
	}

	private void mostrarVentanaReserva(){
		llenarValoresEnReserva();
		this.ventanaReserva.setVisible(true);
	}

/* - - - - - - - - - - - - - -  -- OTROS METODOS  - - - - - - -  - - - - - - - - - - - */	
	
	

	

	
	/**/
	
	
	private void generarPasajeTarjeta(ActionEvent rP) {
		String importeIngresado = ventanaPagoTarjeta.getTextImporteIngresado().toString();
	}

	private void generarPasajeEfectivo(ActionEvent rP) {
		
	}

	private void SeleccionFormaDePago(ActionEvent pagar) {
		this.ventanaReserva.mostrarVentana(false);
		this.ventanaFormaDePagos.mostrarVentana(true);
	}

	private void seleccionEstadoDelPago(ActionEvent pago) {
		String itemSeleccionado = this.ventanaFormaDePagos.getComboBoxEstadoPago().getSelectedItem().toString();
		redirigirSegunItemSeleccionado(itemSeleccionado);
	}

	private void redirigirSegunItemSeleccionado(String itemSeleccionado) {
		if(itemSeleccionado.equals("TARJETA")){
			ventanaPagoTarjeta.mostrarVentana(true);
//			ventanaFormaDePagos.mostrarVentana(false);
			ventanaFormaDePagos.redimensionar();
			}
		else if(itemSeleccionado.equals("EFECTIVO")){
			ventanaPagoEfectivo.mostrarVentana(true);
			ventanaFormaDePagos.mostrarVentana(false);
		}
	}
	
	private void modificarViajeDePasaje(PasajeDTO pasaje, Date nuevaHorarioSalida){
		ViajeDTO nuevoViaje = pasaje.getViaje();
		nuevoViaje.setFechaSalida((java.sql.Date) nuevaHorarioSalida);
		pasaje.setViaje(nuevoViaje);
	}
	
	public Controlador(Vista vista, Cliente cliente){
		this.vista = vista;
		this.vista.getBtnClientes().addActionListener(this);
		this.ventanaCliente = VentanaCliente.getInstance();
		this.ventanaCliente.getBtnRegistrar().addActionListener(this);
		this.cliente = cliente;
		this.ventanaReserva = VentanaReserva.getInstance();
	}
	
	
	

	
	
	
//	private void insertarCliente(VentanaCliente ventanaCliente) {	
//		MedioContactoDTO medioContacto = new MedioContactoDTO(ventanaCliente.getTxtTelefonoFijo().getText(),
//			ventanaCliente.getTxtTelefonoCelular().getText(),
//			ventanaCliente.getTxtEmail().getText()
//		);
//			
//		ClienteDTO nuevoCliente = new ClienteDTO(0,
//			ventanaCliente.getTxtNombre().getText(),
//			ventanaCliente.getTxtApellido().getText(),
//			ventanaCliente.getTxtDni().getText(),
//			ventanaCliente.getDateFechaNacimiento().getDate(),
//			medioContacto
//		);
//		
//		this.cliente.agregarCliente(nuevoCliente);
//	}
	
	//Validamos que los campos esten completos
		private boolean validarCampos(){
			if (ventanaCliente.getTxtNombre().getText().isEmpty() ||
				ventanaCliente.getTxtApellido().getText().isEmpty() ||
				ventanaCliente.getTxtDni().getText().isEmpty() ||
				ventanaCliente.getDateFechaNacimiento().getDate()== null ||
				ventanaCliente.getTxtTelefonoFijo().getText().isEmpty() ||
				ventanaCliente.getTxtTelefonoCelular().getText().isEmpty() ||
				ventanaCliente.getTxtEmail().getText().isEmpty()
			){
				JOptionPane.showMessageDialog(null, "Debe cargar todos los campos", "Mensaje", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			return true;
		}
		
	@Override
	public void actionPerformed(ActionEvent evento){
		if(evento.getSource() == vista.getBtnClientes()){
			ventanaCliente.mostrarVentana();
		}
		else if(evento.getSource() == ventanaCliente.getBtnRegistrar()){
			if (validarCampos()){
//				insertarCliente(ventanaCliente);
				ventanaCliente.dispose();
			}
		}
	}
	public static void main(String[] args) throws Exception {
		Controlador c = new Controlador();
		c.inicializar();
	}
}