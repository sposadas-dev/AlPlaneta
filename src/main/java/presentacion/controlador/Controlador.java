package presentacion.controlador;

import java.awt.PrintGraphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import dto.AdministradorDTO;
import dto.AdministrativoDTO;
import dto.CiudadDTO;
import dto.ClienteDTO;
import dto.EstadoPasajeDTO;
import dto.HorarioReservaDTO;
import dto.LoginDTO;
import dto.MedioContactoDTO;
import dto.PagoDTO;
import dto.PasajeDTO;
import dto.PasajeroDTO;
import dto.RolDTO;
import dto.TransporteDTO;
import dto.ViajeDTO;
import modelo.Administrativo;
import modelo.Cliente;
import modelo.MedioContacto;
import modelo.ModeloCiudad;
import modelo.ModeloViaje;
import persistencia.dao.mysql.AdministradorDAOSQL;
import persistencia.dao.mysql.AdministrativoDAOSQL;
import persistencia.dao.mysql.CiudadDAOSQL;
import persistencia.dao.mysql.ClienteDAOSQL;
import persistencia.dao.mysql.DAOSQLFactory;
import persistencia.dao.mysql.HorarioReservaDAOSQL;
import persistencia.dao.mysql.LoginDAOSQL;
import persistencia.dao.mysql.PagoDAOSQL;
import persistencia.dao.mysql.PasajeroDAOSQL;
import persistencia.dao.mysql.TransporteDAOSQL;
import persistencia.dao.mysql.ViajeDAOSQL;
import presentacion.vista.VentanaCargaPasajero;
import presentacion.vista.VentanaCargarViaje;
import presentacion.vista.VentanaCliente;
import presentacion.vista.VentanaFormaPago;
import presentacion.vista.VentanaLogin;
import presentacion.vista.VentanaPagoEfectivo;
import presentacion.vista.VentanaPagoTarjeta;
import presentacion.vista.VentanaPasajero;
import presentacion.vista.VentanaReserva;
import presentacion.vista.VentanaTablaViajes;
import presentacion.vista.Vista;
import presentacion.vista.VistaAdministrador;

public class Controlador implements ActionListener {
	private List<ViajeDTO> viajes_en_tabla;
	private List<ClienteDTO> clientes_en_tabla;
	ModeloCiudad modeloCiudad;
	private Vista vista;
	private VentanaCliente ventanaCliente;
	private Cliente cliente;
	private MedioContacto medioContacto;
	private VentanaReserva ventanaReserva;
	private VentanaFormaPago ventanaFormaDePagos;
	private VentanaPagoTarjeta ventanaPagoTarjeta;
	private VentanaPagoEfectivo ventanaPagoEfectivo;
	private VentanaCargaPasajero ventanaCargaPasajero;
	private VentanaPasajero ventanaPasajero;
	private VentanaCargarViaje ventanaCargarViaje;
	private VentanaTablaViajes ventanaTablaViajes;
	private VistaAdministrador ventanaAdministrador;
	
	private ArrayList<PasajeroDTO> pasajerosEnEstaReserva;
	private ViajeDTO viajeSeleccionado;
	private TransporteDTO transporteSeleccionado;
	private BigDecimal totalaPagar;
	private HorarioReservaDTO horarioElegido;
	private PagoDTO pagoDTO;
	private LoginDTO usuarioLogeado;
	
	private AdministrativoDTO administrativoLogueado;
	private ClienteDTO clienteLogueado;
	private AdministradorDTO administradorLogueado;
	
	/*AGREGADO PARA 3ER REUNION */
	private VentanaLogin ventanaLogin;
	private Administrativo modeloAdminisrativo;
	private DAOSQLFactory daoSqlFactory;
	
	public Controlador(Vista vista) {
		this.vista = vista;
		this.vista.getBtnClientes().addActionListener(ac->agregarPanelClientes(ac));
		this.vista.getBtnPasajes().addActionListener(ap->agregarPanelPasajes(ap));
		this.vista.getBtnAgregarCliente().addActionListener(c->agregarCliente(c));
		this.vista.getBtnAgregarReserva().addActionListener(p->agregarPasaje(p));
		
		this.ventanaReserva = VentanaReserva.getInstance();
		this.ventanaFormaDePagos = VentanaFormaPago.getInstance();
		this.ventanaPagoTarjeta = VentanaPagoTarjeta.getInstance();
		this.ventanaPagoEfectivo = VentanaPagoEfectivo.getInstance();
		this.ventanaPasajero = VentanaPasajero.getInstance();
		this.ventanaCargarViaje = VentanaCargarViaje.getInstance();
		this.ventanaCargaPasajero = VentanaCargaPasajero.getInstance();
		this.ventanaTablaViajes = VentanaTablaViajes.getInstance();
		this.ventanaLogin = VentanaLogin.getInstance();
		
		/*ENTIDADES LOGEADAS*/
		this.administrativoLogueado = null;
		this.clienteLogueado = null;
		this.administradorLogueado = null;
		
		
		
		/*ventanas auxiliares*/
		this.ventanaAdministrador = VistaAdministrador.getINSTANCE();
		
		/*Inicio de Modelos*/
		this.daoSqlFactory = new DAOSQLFactory();
		this.modeloAdminisrativo = new Administrativo(daoSqlFactory); 
		this.usuarioLogeado = new LoginDTO();
		
		/*Fin de Modelos*/
		
		this.viajes_en_tabla = new ArrayList<ViajeDTO>();
		this.clientes_en_tabla = new ArrayList<ClienteDTO>();
		this.pasajerosEnEstaReserva = new ArrayList<PasajeroDTO>();
		viajeSeleccionado = new ViajeDTO();
		transporteSeleccionado = new TransporteDTO();
		
		this.ventanaLogin.getBtnLogin().addActionListener(log->logearse(log));
		
		this.ventanaReserva.getBtnReservar().addActionListener(reserv->darDeAltaUnPasaje(reserv));
		this.ventanaReserva.getBtnCargaPasajeros().addActionListener(cP->mostrarVentanaCargaDePasajeros(cP));
		this.ventanaReserva.getBtnIrViajes().addActionListener(iV->mostrarViajesDisponibles(iV));
		this.ventanaReserva.getBtnRealizarPago().addActionListener(rP->realizarPago(rP));
//		this.ventanaReserva.getBtnReservar().addActionListener(gP->generarPasaje(gP));
		
		this.ventanaFormaDePagos.getBtnPago().addActionListener(pago->darAltaDelPago(pago));
//		this.ventanaPagoTarjeta.getBtnEnviar().addActionListener(rP->generarPasajeTarjeta(rP));
		this.ventanaCliente = VentanaCliente.getInstance();
		this.ventanaCliente.getBtnRegistrar().addActionListener(ac->altaCliente(ac));
		this.ventanaCliente.getBtnCancelar().addActionListener(bc->salirVentanaCliente(bc));
		
		this.ventanaCargaPasajero.getBtnAgregarPasajero().addActionListener(aP->mostrarVentanaAltaDePasajeros(aP));
		this.ventanaCargaPasajero.getBtnConfirmar().addActionListener(aP->altaPasajerosDeUnViaje(aP));
		
		this.ventanaPasajero.getBtnCargarDatos().addActionListener(aP->darDeAltaUnPasajero(aP));
		this.ventanaCargarViaje.getBtnCrearViaje().addActionListener(aV->darAltaUnViajes(aV));
		
		this.ventanaTablaViajes.getBtnConfirmar().addActionListener(sV->seleccionarViaje(sV));
		
		medioContacto = new MedioContacto(new DAOSQLFactory());
		cliente = new Cliente(new DAOSQLFactory());
	}

	/*IMPLEMENTADO BRANCH V3.0*/	
	private void logearse(ActionEvent log) {
		String usuario = ventanaLogin.getTextUsuario().getText();
		String password = new String(ventanaLogin.getPasswordField().getPassword());
		
		
		LoginDAOSQL dao = new LoginDAOSQL();
		usuarioLogeado = null;
		try {
			usuarioLogeado = dao.getByDatos(usuario, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(usuarioLogeado==null){
			this.ventanaLogin.getLblError().setVisible(true);
			System.out.println("EL USUARIO O CONTRASENA ES INCORRECTO");
		}
		else{
			System.out.println("SE LOGEO CORRECTAMENTE CON:"+ usuarioLogeado.getUsuario()+" "+usuarioLogeado.getContrasena()+" "+usuarioLogeado.getRol().getIdRol());
			if(usuarioLogeado.getRol().getIdRol()==2){
				 administrativoLogueado = obtenerAdministrativo(usuarioLogeado);
				 mostrarVentanaAdministrativo();
			}
			else{
				if(usuarioLogeado.getRol().getIdRol()==5){
					 clienteLogueado = obtenerCliente(usuarioLogeado);
					 mostrarVentanaCliente();
				}
				else{
					if(usuarioLogeado.getRol().getIdRol()==1){
						 administradorLogueado = obtenerAdministrador(usuarioLogeado);
						 mostrarVentanaAdministrador();
					}
				}
			}
		}
		
	}

	private void mostrarVentanaCliente() {
		System.out.println("Se Loguea como Cliente");
		System.out.println(clienteLogueado.getNombre());
		this.ventanaLogin.setVisible(false);
		
	}

	/*MOSTRAR LA VENTANA PRINCIPAL DEL PARSONAL ADMINISTRATIVO*/
	private void mostrarVentanaAdministrativo() {
		System.out.println("Se Loguea Como Administrativo");
		System.out.println(administrativoLogueado.getNombre());
		this.ventanaLogin.setVisible(false);
		

	}
	
	private void mostrarVentanaAdministrador() {
		System.out.println("Se Loguea Como Administrador");
		System.out.println(administradorLogueado.getNombre());
		this.ventanaLogin.setVisible(false);
		

	}
/*-----------------------METODOS BUSCADOR POR ROLES ---------------------*/
	private AdministrativoDTO obtenerAdministrativo(LoginDTO loginUsuario) {
		AdministrativoDAOSQL dao = new AdministrativoDAOSQL();
		return dao.getByLoginId(loginUsuario.getIdDatosLogin());
	}
	
	private AdministradorDTO obtenerAdministrador(LoginDTO loginUsuario) {
		AdministradorDAOSQL dao = new AdministradorDAOSQL();		
		return dao.getByLoginId(loginUsuario.getIdDatosLogin());
	}
	
	private ClienteDTO obtenerCliente(LoginDTO loginUsuario) {
		ClienteDAOSQL sql = new ClienteDAOSQL();
		return sql.getByLoginId(loginUsuario.getIdDatosLogin());
	}
	
	private AdministrativoDTO busquedaRolAdministrativo(String user, String password) {
		return modeloAdminisrativo.obtenerAdministrativoDatosLogin(user,password);
	}
/*FIN IMPLEMENTACION BRANCH V3.0*/
	
	
	
	private void salirVentanaCliente(ActionEvent bc) {
		this.ventanaCliente.cerrarVentana();
	}

	/* - - - - - - - - - - - - - - - - - INICIALIZAR - - - - - - - - - - - - - - - - - - - -*/
	public void inicializar() throws Exception{	
		this.ventanaLogin.setVisible(true);
//		this.llenarTablaClientes();
//		this.vista.show();
				
//		mostrarVentanaReserva();  // Ventana creacion de pasajes.
	
//		llenarViajesEnTabla();
		
//		mostrarVentanaPago();
		
//		llenarValoresEnCargaDeViaje();
	}
		
	private void agregarPanelClientes(ActionEvent ac) {
		this.vista.getPanelClientes().setVisible(true);
		this.vista.getPanelReservas().setVisible(false);
	}

	private void agregarPanelPasajes(ActionEvent ap) {
		this.vista.getPanelClientes().setVisible(false);
		this.vista.getPanelReservas().setVisible(true);
	}

	private void agregarCliente(ActionEvent c) {
		this.ventanaCliente.mostrarVentana();
	}

	private void agregarPasaje(ActionEvent p) {
		mostrarVentanaReserva();
	}

	private void llenarTablaClientes(){
		this.vista.getModelClientes().setRowCount(0); //Para vaciar la tabla
		this.vista.getModelClientes().setColumnCount(0);
		this.vista.getModelClientes().setColumnIdentifiers(this.vista.getNombreColumnasClientes());
			
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
							this.vista.getModelClientes().addRow(fila);
		}		
	}
//	
//		
	
	/*LABEL CANTIDAD DE PASAJEROS*/
	
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
		
//		ViajeDTO nuevoViaje = new ViajeDTO(0, origen, destino, fechaSalida, fechaLlegada, precio, horaSalida);

		// VER POR QUE NO FUNCIONA LA CONSULTA SQL EN EL MODELO	
//		modeloViaje.agregarViaje(nuevoViaje);
//
//		ViajeDAOSQL sql = new ViajeDAOSQL();		
//		sql.insert(nuevoViaje);
		
		llenarViajesEnTabla();
		
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
	
	private void llenarViajesEnTabla(){
		ViajeDAOSQL viajes = new ViajeDAOSQL();
		viajes_en_tabla = viajes.readAll();
	}
	
	private void seleccionarViaje(ActionEvent sV) {
		
		this.ventanaTablaViajes.setVisible(false);
		this.ventanaReserva.setVisible(true);
		
		int filaSeleccionada = this.ventanaTablaViajes.getTablaViajes().getSelectedRow();
		viajeSeleccionado = viajes_en_tabla.get(filaSeleccionada);
//		
//		String viajeString = String.valueOf(viajeSeleccionado.getId()) +
//				" -   DE : "+  viajeSeleccionado.getOrigenViaje().getNombre() +
//				"  -  HACIA : "+ viajeSeleccionado.getDestinoViaje().getNombre();
		
//		this.ventanaReserva.getLblViajeSeleccionado().setText(viajeString);
	}
	
	/*- - - - - - - -  - - - - - - - METODO DE CLIENTE - - - - - - - - - - - - - - - - --  */	

	private void altaCliente(ActionEvent client) {
//		if(validarCampos()){	
//		/*Obtenemos la fecha de nacimiento , y la parseamos a tipo de date de SQL*/
//		java.util.Date dateFechaNacimiento = ventanaCliente.getDateFechaNacimiento().getDate();
//		java.sql.Date fechaNacimiento = new java.sql.Date(dateFechaNacimiento.getTime());
//		
//		/*Obtenemos el medio de contacto del cliente*/
//		MedioContactoDTO mContacto = new MedioContactoDTO();
//		mContacto.setTelefonoFijo(this.ventanaCliente.getTxtTelefonoFijo().getText());
//		mContacto.setTelefonoCelular(this.ventanaCliente.getTxtTelefonoCelular().getText());
//		mContacto.setEmail(this.ventanaCliente.getTxtEmail().getText());
//		
//		medioContacto.agregarMedioContacto(mContacto);
//		
//		ClienteDTO nuevoCliente = new ClienteDTO(0,
//				this.ventanaCliente.getTxtNombre().getText(),
//				this.ventanaCliente.getTxtApellido().getText(),
//				this.ventanaCliente.getTxtDni().getText(),
//				fechaNacimiento,
//				obtenerMedioContactoDTO(),
//				
//		);
//			cliente.agregarCliente(nuevoCliente);
//			llenarTablaClientes();
//			this.ventanaCliente.limpiarCampos();
//			this.ventanaCliente.dispose();
//		}
	}
	
	private MedioContactoDTO obtenerMedioContactoDTO() {
		MedioContacto medioContacto = new MedioContacto(new DAOSQLFactory());
		MedioContactoDTO mContactoDTO = new MedioContactoDTO();
		ArrayList<MedioContactoDTO> medios = (ArrayList<MedioContactoDTO>) medioContacto.obtenerMediosContacto();
		for(MedioContactoDTO m: medios){
			if(m.getEmail().toString().equals(this.ventanaCliente.getTxtEmail().getText()) &&
				m.getTelefonoCelular().equals(this.ventanaCliente.getTxtTelefonoCelular().getText())&&
				m.getTelefonoFijo().equals(this.ventanaCliente.getTxtTelefonoFijo().getText())){
				mContactoDTO = m;
			}
		}
		return mContactoDTO;
	}
	
	
	/*- - - - - - - -  - - - - - - - METODOS DE PASAJERO - - - - - - - - - - - - - - - - --  */	
	
	private void darDeAltaUnPasajero(ActionEvent aP) {
		
		PasajeroDTO pasajeroDTO = new PasajeroDTO();
		pasajeroDTO.setNombre(this.ventanaPasajero.getTxtNombre().getText());
		pasajeroDTO.setApellido(this.ventanaPasajero.getTxtApellido().getText());
		pasajeroDTO.setDni(this.ventanaPasajero.getTxtDni().getText());
		
		PasajeroDAOSQL DAO = new PasajeroDAOSQL();
		DAO.insert(pasajeroDTO);
		
		pasajerosEnEstaReserva.add(pasajeroDTO);
		
		/*LLENAMOS LA VENTANA CON LOS PASAJEROS DEL VIAJE*/
		llenarTablaDePasajerosEnVentanaCargaPasajeros();
		
		/*VACIAR LOS TXTFIELD*/		
		this.ventanaPasajero.getTxtNombre().setText("");
		this.ventanaPasajero.getTxtApellido().setText("");;
		this.ventanaPasajero.getTxtDni().setText("");
		
		this.ventanaPasajero.setVisible(false);	
	}
	
	private void altaPasajerosDeUnViaje(ActionEvent aP) {
		this.ventanaCargaPasajero.setVisible(false);
		this.ventanaReserva.setVisible(true);
	}
	
	private void llenarTablaDePasajerosEnVentanaCargaPasajeros(){
		
		this.ventanaCargaPasajero.getModelPasajeros().setRowCount(0);
		this.ventanaCargaPasajero.getModelPasajeros().setColumnCount(0);
		this.ventanaCargaPasajero.getModelPasajeros().setColumnIdentifiers(this.ventanaCargaPasajero.getNombreColumnas());
		for(int i=0; i<pasajerosEnEstaReserva.size();i++){
			Object[] fila = { 
					pasajerosEnEstaReserva.get(i).getNombre(),
					pasajerosEnEstaReserva.get(i).getApellido(),
					pasajerosEnEstaReserva.get(i).getDni()
			};
			this.ventanaCargaPasajero.getModelPasajeros().addRow(fila);
		}
		this.ventanaReserva.getLblCantidadDePasajeros().setText(pasajerosEnEstaReserva.size()+" Pasajeros fueron cargados");
		
		
//		ArrayList<PasajeroDTO> pasajeros_en_tabla = (ArrayList<PasajeroDTO>) new PasajeroDAOSQL().readAll();
//		for(int i=0; i< pasajeros_en_tabla.size();i++){
//			Object[] fila = { 
//					pasajeros_en_tabla.get(i).getNombre(),
//					pasajeros_en_tabla.get(i).getApellido(),
//					pasajeros_en_tabla.get(i).getDni()
//					};
		
//		}
	}

	private void mostrarVentanaAltaDePasajeros(ActionEvent aP) {
		this.ventanaReserva.mostrarVentana(false);
		this.ventanaPasajero.setVisible(true);
	}
	
	private void mostrarVentanaCargaDePasajeros(ActionEvent cP) {
		this.ventanaReserva.mostrarVentana(false);
		this.ventanaCargaPasajero.mostrarVentana(true);
	}
	
	private void generarPasaje(ActionEvent pagar) {
		this.ventanaReserva.mostrarVentana(false);
		this.ventanaFormaDePagos.mostrarVentana(true);
		
		PasajeDTO pasajeDTO = new PasajeDTO();
		
/*OBTENER EL VIAJE SELECCIONADO*/
		pasajeDTO.setViaje(this.viajeSeleccionado);
		
/*OBTENER EL TRASNPORTE*/
//		System.out.println("------"+this.ventanaReserva.getComboBoxTransporte().getSelectedItem());
//		transporteSeleccionado = obtenerTransporteElegidoPorCliente(
//								(String) this.ventanaReserva.getComboBoxTransporte().getSelectedItem());
//		pasajeDTO.setTransporte(transporteSeleccionado);
				
		
/*OBTENER EL RANGO ELEGIDO POR EL CLIENTE*/
		String rangoElegido = this.ventanaReserva.getComboBoxRangoHorario().getSelectedItem().toString();
		
/*OBTENER LOS PASAJEROS RELACIONADO A ESE PASAJE*/
		pasajeDTO.setPasajeros(this.pasajerosEnEstaReserva);
		
/*OBTENER EL PAGO DEL PASAJE*/
		horarioElegido = obtenerHorarioElegidoPorCliente(this.ventanaReserva.getComboBoxRangoHorario().getSelectedItem().toString());

/*GENERAR EL PASAJE Y DARLO DE ALTA EN LA BASE DE DATOS*/
		
	}

	private HorarioReservaDTO obtenerHorarioElegidoPorCliente(String horarioComboBox) {
		HorarioReservaDAOSQL tDAO = new HorarioReservaDAOSQL();
		HorarioReservaDTO ret = null;
		ArrayList<HorarioReservaDTO> rangoshorarios = (ArrayList<HorarioReservaDTO>) tDAO.readAll();
		for(HorarioReservaDTO h : rangoshorarios) {
			if(h.getHoraInicio().equals(horarioComboBox.substring(0,5)) && (h.getHoraFin().equals(horarioComboBox.substring(8,13)))){
				ret = h;
			}
		}
		return ret;
	}
	/*- - - - - - - -  - - - - - - - METODOS DE RESERVA - - - - - - - - - - - - - - - - --  */		
	
	private void llenarValoresEnReserva(){
		llenarComboRangoDeHorarioEnReserva();

//		llenarComboTransporteEnReserva();
		
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

//	private void llenarComboTransporteEnReserva() {
//		List<TransporteDTO> transportesDTO = new TransporteDAOSQL().readAll();
//		String[] transoportes = new String[transportesDTO.size()];
//		for(int i=0; i<transportesDTO.size();i++){
//			String rango = transportesDTO.get(i).getNombreTransporte();
//			transoportes [i] = rango;
//		}
//		this.ventanaReserva.getComboBoxTransporte().setModel(new DefaultComboBoxModel(transoportes));
//	}

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

	private void mostrarViajesDisponibles(ActionEvent iV) {
		this.ventanaTablaViajes.getModelViajes().setRowCount(0);
		this.ventanaTablaViajes.getModelViajes().setColumnCount(0);
		this.ventanaTablaViajes.getModelViajes().setColumnIdentifiers(this.ventanaTablaViajes.getNombreColumnas());
		ArrayList<ViajeDTO> viajes_en_tabla = (ArrayList<ViajeDTO>) new ViajeDAOSQL().readAll();
		for(int i=0; i< viajes_en_tabla.size();i++){
			Object[] fila = { 
					viajes_en_tabla.get(i).getOrigenViaje().getNombre(),
					viajes_en_tabla.get(i).getDestinoViaje().getNombre(),
					viajes_en_tabla.get(i).getFechaSalida(),
					viajes_en_tabla.get(i).getFechaLlegada(),
					viajes_en_tabla.get(i).getPrecio(),
					viajes_en_tabla.get(i).getHoraSalida()
			};
			this.ventanaTablaViajes.getModelViajes().addRow(fila);
		}
		this.ventanaTablaViajes.setVisible(true);
	}

	
/* - - - - - - - - - - - - - -  -- METODOS DE PAGOS - - - - - - -  - - - - - - - - - - - */	
	
	private void darAltaDelPago(ActionEvent pago) {
		pagoDTO = new PagoDTO();
		Calendar currenttime = Calendar.getInstance();
		 
		pagoDTO.setMonto(new BigDecimal(this.ventanaFormaDePagos.getTextImporteTotal().getText()));
		pagoDTO.setFechaPago(new Date((currenttime.getTime()).getTime()));
		
		PagoDAOSQL pagoDAO = new PagoDAOSQL();
		pagoDAO.insert(pagoDTO);
		
		this.ventanaFormaDePagos.setVisible(false);
		this.ventanaReserva.setVisible(true);
	}

	private void realizarPago(ActionEvent rP) {
		this.ventanaReserva.setVisible(false);
		this.ventanaFormaDePagos.setVisible(true);
		
		this.transporteSeleccionado = obtenerTransporteElegidoPorCliente(this.ventanaReserva.getComboBoxTransporte().getSelectedItem().toString());
		this.ventanaFormaDePagos.getLblMontoaPagar().setText("$ "+calcularMontoDePasaje().toString());
		
	}
	
	private BigDecimal calcularMontoDePasaje() {
		BigDecimal Valor1 = this.viajeSeleccionado.getPrecio();
//		BigDecimal Valor2 = this.transporteSeleccionado.getPrecioBase();
//		Valor2 = Valor2.add(Valor1);
//		totalaPagar = Valor2;
		
		return totalaPagar.multiply(new BigDecimal(pasajerosEnEstaReserva.size()));
	}


	/* - - - -  - - - - - - - - - -  - - OTROS METODOS - - - - - - - - - - - - - - - -  -*/	
	
	private TransporteDTO obtenerTransporteElegidoPorCliente(String transporteComboBox) {
		TransporteDAOSQL tDAO = new TransporteDAOSQL();
		TransporteDTO ret = null;
		ArrayList<TransporteDTO> transportes = (ArrayList<TransporteDTO>) tDAO.readAll();
		for(TransporteDTO t: transportes)
//			if(t.getNombreTransporte().equals(transporteComboBox))
				ret = t;
		return ret;
	}
	
//	private void generarPasajeTarjeta(ActionEvent rP) {
//		String importeIngresado = ventanaPagoTarjeta.getTextImporteIngresado().toString();
//	}
//	

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
	
private void darDeAltaUnPasaje(ActionEvent aP) {
//		MedioContactoDTO medio = new MedioContactoDTO(1,"44514652","1578966321","contacto@gmail.com");
//		java.util.Date d = new java.util.Date(); 
//		java.sql.Date date2 = new java.sql.Date(d.getTime());
//		ClienteDTO cliente = new ClienteDTO(1,"Pedro","Lopez","17325562",date2, medio);
//		
//		ViajeDTO viaje = viajeSeleccionado;
//		AdministrativoDTO administrativo = new AdministrativoDTO (1,"Andres Gandolfi");
//		int cantPasajeros = pasajerosEnEstaReserva.size();
//		TransporteDTO transporte = obtenerTransporteElegidoPorCliente(this.ventanaReserva.getComboBoxTransporte().getSelectedItem().toString());
//		BigDecimal valorViaje = totalaPagar;
//		EstadoPasajeDTO estadoPasaje = calcularEstadoPasaje();
//		List<PasajeroDTO> pasajeros = pasajerosEnEstaReserva;
//		
//		PasajeDTO pasajeDTO = new PasajeDTO(0,viaje,administrativo,cantPasajeros,cliente,transporte,null,
//				valorViaje,estadoPasaje,pagoDTO,pasajeros);
//		
//		PasajeDAOSQL DAO = new PasajeDAOSQL();
//		
//		DAO.insert(pasajeDTO);
//		this.ventanaReserva.setVisible(false);
//		
//		for(PasajeroDTO p : pasajerosEnEstaReserva) {
//			Pasaje_PasajerosDTO pasaje_pasajero = new Pasaje_PasajerosDTO (0, pasajeDTO.getIdPasaje(), p.getIdPasajero());
//			/*FALTA CHEQUEAR LOS ID = 0*/
//			Pasaje_PasajerosDAOSQL DAOPP = new Pasaje_PasajerosDAOSQL();
//			DAOPP.insert(pasaje_pasajero);
//		}

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
	
	

	
//	Validamos que los campos esten completos		
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

	}
//	public static void main(String[] args) throws Exception {
//		Controlador c = new Controlador();
//		c.inicializar();
//	}
}