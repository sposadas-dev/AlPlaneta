package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

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
import dto.PaisDTO;
import dto.PasajeDTO;
import dto.PasajeroDTO;
import dto.ProvinciaDTO;
import dto.TransporteDTO;
import dto.ViajeDTO;
import modelo.Administrativo;
import modelo.Cliente;
import modelo.MedioContacto;
import modelo.ModeloCiudad;
import modelo.ModeloProvincia;
import modelo.ModeloViaje;
import modelo.Pais;
import modelo.Transporte;
import persistencia.dao.mysql.AdministradorDAOSQL;
import persistencia.dao.mysql.AdministrativoDAOSQL;
import persistencia.dao.mysql.CiudadDAOSQL;
import persistencia.dao.mysql.ClienteDAOSQL;
import persistencia.dao.mysql.DAOSQLFactory;
import persistencia.dao.mysql.HorarioReservaDAOSQL;
import persistencia.dao.mysql.LoginDAOSQL;
import persistencia.dao.mysql.PagoDAOSQL;
import persistencia.dao.mysql.PaisDAOSQL;
import persistencia.dao.mysql.PasajeroDAOSQL;
import persistencia.dao.mysql.TransporteDAOSQL;
import persistencia.dao.mysql.ViajeDAOSQL;
import presentacion.vista.VentanaLogin;
//import presentacion.vista.VentanaPagoEfectivo;
//import presentacion.vista.VentanaPagoTarjeta;
import presentacion.vista.VentanaReserva;
import presentacion.vista.Vista;
import presentacion.vista.administrador.VentanaCargarViaje;
import presentacion.vista.administrador.VistaAdministrador;
import presentacion.vista.administrativo.VentanaCargaPasajero;
import presentacion.vista.administrativo.VentanaPago;
import presentacion.vista.administrativo.VentanaPasajero;
import presentacion.vista.administrativo.VentanaRegistrarCliente;
import presentacion.vista.administrativo.VentanaTablaViajes;

public class Controlador implements ActionListener {
	private List<ViajeDTO> viajes_en_tabla;
	private List<ClienteDTO> clientes_en_tabla;
	private Vista vista;
	private VentanaRegistrarCliente ventanaCliente;
	private Cliente cliente;
	private MedioContacto medioContacto;
	private VentanaReserva ventanaReserva;
	private VentanaPago ventanaFormaDePagos;
//	private VentanaPagoTarjeta ventanaPagoTarjeta;
//	private VentanaPagoEfectivo ventanaPagoEfectivo;
	private VentanaCargaPasajero ventanaCargaPasajero;
	private VentanaPasajero ventanaPasajero;
	private VentanaCargarViaje ventanaCargarViaje;
	private VentanaTablaViajes ventanaTablaViajes;
	private VistaAdministrador ventanaAdministrador;
	
	private ArrayList<PasajeroDTO> pasajerosEnEstaReserva;
	private ViajeDTO viajeSeleccionado;
	
	private HorarioReservaDTO horarioElegido;
	private PagoDTO pagoDTO;
	private LoginDTO usuarioLogueado;
	private BigDecimal totalaPagar;
	
	
	//DATOS DEL VIAJE
	private Date fechaSalida;
	private String horarioSalida;
	private Integer horasEstimadas;
	private Date fechaLlegada;
	private BigDecimal precioViaje;
	private TransporteDTO transporteSeleccionado;
	private int capacidad;
	
	private PaisDTO paisOrigen;
	private PaisDTO paisDestino;
	private ProvinciaDTO provinciaOrigen;
	private ProvinciaDTO provinciaDestino;
	private CiudadDTO ciudadOrigen;
	private CiudadDTO ciudadDestino;
	//FIN DATOS VIAJE
	
	private String provinciaOrigenSelected;
	private String provinciaDestinoSelected;
	private String ciudadOrigenSelected;
	private String ciudadDestinoSelected;
	private AdministrativoDTO administrativoLogueado;
	private ClienteDTO clienteLogueado;
	private AdministradorDTO administradorLogueado;
	
	/*AGREGADO PARA 3ER REUNION */
	private VentanaLogin ventanaLogin;
	private Administrativo modeloAdminisrativo;
	private static DAOSQLFactory daoSqlFactory;
	

	
	private boolean origenListo;
	private boolean destinoListo;
	
	/*ADMINSITRADOR*/
	private Pais modeloPais;
	private ModeloProvincia modeloProvincia;
	private ModeloCiudad modeloCiudad;
	private Transporte modeloTransporte;
	private ModeloViaje modeloViaje;
	
	public Controlador(Vista vista) {
		this.vista = vista;
		this.vista.getBtnClientes().addActionListener(ac->agregarPanelClientes(ac));
		this.vista.getBtnPasajes().addActionListener(ap->agregarPanelPasajes(ap));
		this.vista.getBtnAgregarCliente().addActionListener(c->agregarCliente(c));
		this.vista.getBtnAgregarReserva().addActionListener(p->agregarPasaje(p));
		
		this.ventanaReserva = VentanaReserva.getInstance();
		this.ventanaFormaDePagos = VentanaPago.getInstance();
//		this.ventanaPagoTarjeta = VentanaPagoTarjeta.getInstance();
//		this.ventanaPagoEfectivo = VentanaPagoEfectivo.getInstance();
		this.ventanaPasajero = VentanaPasajero.getInstance();
		this.ventanaCargarViaje = VentanaCargarViaje.getInstance();
		this.ventanaCargaPasajero = VentanaCargaPasajero.getInstance();
		this.ventanaTablaViajes = VentanaTablaViajes.getInstance();
		this.ventanaLogin = VentanaLogin.getInstance();
		
		//INICIALIZACION datos del viaje
		this.fechaSalida=null;
		this.horarioSalida="";
		this.horasEstimadas=null;
		this.fechaLlegada=null;
		this.precioViaje=null;
		this.transporteSeleccionado=null;		
		this.paisOrigen=null;
		this.paisDestino=null;
		this.provinciaOrigen=null;
		this.provinciaDestino=null;
		this.ciudadOrigen=null;
		this.ciudadDestino=null;
		this.origenListo = false;
		this.destinoListo = false;
		//FIN datos del viaje
		
		/*ENTIDADES LOGEADAS*/
		this.administrativoLogueado = null;
		this.clienteLogueado = null;
		this.administradorLogueado = null;
		
		/*ventanas auxiliares*/
//		this.ventanaAdministrador = VistaAdministrador.getINSTANCE();
		
		/*Inicio de Modelos*/	
		this.daoSqlFactory = new DAOSQLFactory();
		this.modeloAdminisrativo = new Administrativo(daoSqlFactory);
		this.modeloProvincia = new ModeloProvincia(daoSqlFactory);
		this.usuarioLogueado = new LoginDTO();
		this.modeloCiudad = new ModeloCiudad(daoSqlFactory);
		this.modeloProvincia = new ModeloProvincia(daoSqlFactory);
		this.modeloPais = new Pais(daoSqlFactory);
		this.modeloTransporte = new Transporte(daoSqlFactory);
		this.modeloViaje = new ModeloViaje(daoSqlFactory);
		
		/*Fin de Modelos*/
		
		this.viajes_en_tabla = new ArrayList<ViajeDTO>();
		this.clientes_en_tabla = new ArrayList<ClienteDTO>();
		this.pasajerosEnEstaReserva = new ArrayList<PasajeroDTO>();
		this.viajeSeleccionado = new ViajeDTO();
		this.transporteSeleccionado = new TransporteDTO();
		
		this.ventanaLogin.getBtnLogin().addActionListener(log->logearse(log));
		
		this.ventanaReserva.getBtnReservar().addActionListener(reserv->darDeAltaUnPasaje(reserv));
//		this.ventanaReserva.getBtnCargaPasajeros().addActionListener(cP->mostrarVentanaCargaDePasajeros(cP));
		this.ventanaReserva.getBtnIrViajes().addActionListener(iV->mostrarViajesDisponibles(iV));
		this.ventanaReserva.getBtnRealizarPago().addActionListener(rP->realizarPago(rP));
//		this.ventanaReserva.getBtnReservar().addActionListener(gP->generarPasaje(gP));
		
		this.ventanaFormaDePagos.getBtnPago().addActionListener(pago->darAltaDelPago(pago));
//		this.ventanaPagoTarjeta.getBtnEnviar().addActionListener(rP->generarPasajeTarjeta(rP));
		this.ventanaCliente = VentanaRegistrarCliente.getInstance();
//		this.ventanaCliente.getBtnRegistrar().addActionListener(ac->altaCliente(ac));
		this.ventanaCliente.getBtnCancelar().addActionListener(bc->salirVentanaCliente(bc));
		
//		this.ventanaCargaPasajero.getBtnAgregarPasajero().addActionListener(aP->mostrarVentanaAltaDePasajeros(aP));
//		this.ventanaCargaPasajero.getBtnConfirmar().addActionListener(aP->altaPasajerosDeUnViaje(aP));
//		
//		this.ventanaPasajero.getBtnCargarDatos().addActionListener(aP->darDeAltaUnPasajero(aP));
		this.ventanaCargarViaje.getBtnCrearViaje().addActionListener(aV->darAltaViaje(aV));
		this.ventanaTablaViajes.getBtnConfirmar().addActionListener(sV->seleccionarViaje(sV));
/*//////////////////////////////////////////////////////////////////////////////////////////////////*/		
/*/////////////////////////////////////// CARGAR VALORES EN PAIS-PROVINCIA-CIUDAD /////////////////////*/
	
		this.ventanaCargarViaje.getBtnOK().addActionListener(v->mostrarDatosViaje(v));
		this.ventanaCargarViaje.getComboBoxPaisOrigen().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { obtenerProvincias_porPaisOrigen(e);}});
		
		
		this.ventanaCargarViaje.getComboBoxPaisDestino().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { obtenerProvincias_porPaisDestino(e);}});
		
		this.ventanaCargarViaje.getComboBoxProvinciaOrigen().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { obtenerCiudades_porProvinciaOrigen(e);}});
		
		this.ventanaCargarViaje.getComboBoxProvinciaDestino().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { obtenerCiudades_porProvinciaDestino(e);}});
		
//		this.ventanaCargarViaje.getComboBoxCiudadOrigen().addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseClicked(java.awt.event.MouseEvent cit){obtenerCiudadesOrigen(cit);}});
//		
//		this.ventanaCargarViaje.getComboBoxCiudadDestino().addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseClicked(java.awt.event.MouseEvent cit){obtenerCiudadesDestino(cit);}});
//		
/*//////////////////////////////////////////////////////////////////////////////////////////////////*/		
/*/////////////////////////////////////// PRUEBA //////////////////////////////////////////////////*/
		
		medioContacto = new MedioContacto(new DAOSQLFactory());
		cliente = new Cliente(new DAOSQLFactory());
	}

	
	/* - - - - - - - - - - - - - - - - - INICIALIZAR - - - - - - - - - - - - - - - - - - - -*/
	public void inicializar() throws Exception{	
//		this.ventanaLogin.setVisible(true);
	
		llenarCiudadesEnCargaViajes();
		
		this.ventanaCargarViaje.setVisible(true);

	//			this.llenarTablaClientes();

//		this.vista.show();
				
//		mostrarVentanaReserva();  // Ventana creacion de pasajes.
	
//		llenarViajesEnTabla();
		
//		mostrarVentanaPago();
		
//		llenarValoresEnCargaDeViaje();
	}
	
	
/*< METODOS DE VIAJE > - - -   - - - - - - - - - - - - - - - - - - - - - - - - - - --  */
	
/*< ORIGEN-DESTINOS >*/
	private void llenarComboBoxPaises() {
		List<PaisDTO> paises = new PaisDAOSQL().readAll();
		String[] nombresPaises = new String[paises.size()];
		for(int i=0; i<paises.size();i++){
			String pais = paises.get(i).getIdPais()+"-"+paises.get(i).getNombre();
			nombresPaises [i] = pais;
		}	
/* LUEGO VER QUE NO SE PUEDA SELECCIONAR LA MISMA CIUDAD COMO ORIGEN Y DESTINO AL MISMO TIEMPO */
		this.ventanaCargarViaje.getComboBoxPaisOrigen().setModel(new DefaultComboBoxModel(nombresPaises));
		this.ventanaCargarViaje.getComboBoxPaisDestino().setModel(new DefaultComboBoxModel(nombresPaises));
	}
	
	private void obtenerProvincias_porPaisOrigen(ActionEvent e) {//MouseEvent evt) {//obtener provinciasOrigen y setEnComboProvincia
		String pais = ventanaCargarViaje.getComboBoxPaisOrigen().getSelectedItem().toString();
		System.out.println(pais);
		ventanaCargarViaje.getComboBoxProvinciaOrigen().setEnabled(true);
    	llenarComboBoxProvinciasOrigen(Integer.parseInt(obtenerIdDesdeCombo(pais)));
	}
	
	private String obtenerIdDesdeCombo(String s) {
		String ret = "";
		for (int n = 0; n <s.length (); n ++) {
			char c = s.charAt (n);
			if(c != '-') {
				ret+= c;
			}
			else{
				return ret;
			}
		}
		return ret;
	}
	
	private String quitarIdDeCombo(String s) {
		String ret = "";
		for (int n = 0; n <s.length (); n ++) {
			char c = s.charAt (n);
			if(c != '0' && c != '1' && c != '2' && c != '3' && c != '4' && c != '5' && c != '6' && c != '7' && c != '8' && c != '9' && c != '-') {
				ret+= c;
			}
		}
		return ret;
	}
	private void obtenerProvincias_porPaisDestino(ActionEvent evt) {//obteer provinciasDestino y setEnComboProvincia
		String pais = ventanaCargarViaje.getComboBoxPaisDestino().getSelectedItem().toString();
		ventanaCargarViaje.getComboBoxProvinciaDestino().setEnabled(true);
    	llenarComboBoxProvinciasDestino(Integer.parseInt(obtenerIdDesdeCombo(pais)));
	}
	
	private void obtenerCiudades_porProvinciaOrigen(ActionEvent evt) {
		String provincia = ventanaCargarViaje.getComboBoxProvinciaOrigen().getSelectedItem().toString();
		ventanaCargarViaje.getComboBoxCiudadOrigen().setEnabled(true);
		llenarComboBoxCiudadesOrigen(Integer.parseInt(obtenerIdDesdeCombo(provincia)));
		origenListo = true;
		if(origenListo && destinoListo) //Activa boton OK si seleccion destino y origen esta completa
    		ventanaCargarViaje.getBtnOK().setEnabled(true);
	} 
	
	private void obtenerCiudades_porProvinciaDestino(ActionEvent evt) {
		String provincia = ventanaCargarViaje.getComboBoxProvinciaDestino().getSelectedItem().toString();
		ventanaCargarViaje.getComboBoxCiudadDestino().setEnabled(true);
    	llenarComboBoxCiudadesDestino(Integer.parseInt(obtenerIdDesdeCombo(provincia)));
    	destinoListo = true;
    	if(origenListo && destinoListo) //Activa boton OK si seleccion destino y origen esta completa
    		ventanaCargarViaje.getBtnOK().setEnabled(true);
	} 
	
	private void llenarComboBoxProvinciasOrigen(int idPais) {
		List<ProvinciaDTO> provincias = null;
		//try {
			provincias = modeloProvincia.obtenerProvinciaPorIdPais(idPais);
		//} catch (Exception e) {
		//	e.printStackTrace();
		//}
		
		String[] nombresProvincias = new String[provincias.size()];
		for(int i=0; i<provincias.size();i++){
			String provincia = provincias.get(i).getIdProvincia()+"-"+provincias.get(i).getNombre();
			nombresProvincias [i] = provincia;
		}	
/* LUEGO VER QUE NO SE PUEDA SELECCIONAR LA MISMA CIUDAD COMO ORIGEN Y DESTINO AL MISMO TIEMPO */
		this.ventanaCargarViaje.getComboBoxProvinciaOrigen().setModel(new DefaultComboBoxModel(nombresProvincias));
	}
	
	private void llenarComboBoxProvinciasDestino(int idPais) {
		List<ProvinciaDTO> provincias = null;
		try {
			provincias = modeloProvincia.obtenerProvinciaPorIdPais(idPais);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String[] nombresProvincias = new String[provincias.size()];
		for(int i=0; i<provincias.size();i++){
			String provincia = provincias.get(i).getIdProvincia()+"-"+provincias.get(i).getNombre();
			nombresProvincias [i] = provincia;
		}	
		this.ventanaCargarViaje.getComboBoxProvinciaDestino().setModel(new DefaultComboBoxModel(nombresProvincias));
	}
	
	private void llenarComboBoxCiudadesOrigen(int idProvincia) {
		List<CiudadDTO> ciudades = null;
		ciudades = modeloCiudad.obtenerCiudadPorIdProvincia(idProvincia);
		String[] nombresCiudades = new String[ciudades.size()];
		System.out.println(nombresCiudades.length);
		for(int i=0; i<ciudades.size();i++){
			String ciudad = ciudades.get(i).getIdCiudad()+"-"+ciudades.get(i).getNombre();
			nombresCiudades [i] = ciudad;
		}	
		this.ventanaCargarViaje.getComboBoxCiudadOrigen().setModel(new DefaultComboBoxModel(nombresCiudades));
		
	}
	
	private void llenarComboBoxCiudadesDestino(int idProvincia) {
		List<CiudadDTO> ciudades = null;
		try {
			ciudades = modeloCiudad.obtenerCiudadPorIdProvincia(idProvincia);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] nombresCiudades = new String[ciudades.size()];
		
		for(int i=0; i<ciudades.size();i++){
			String ciudad = ciudades.get(i).getIdCiudad()+"-"+ciudades.get(i).getNombre();
			nombresCiudades [i] = ciudad;
		}	
		this.ventanaCargarViaje.getComboBoxCiudadDestino().setModel(new DefaultComboBoxModel(nombresCiudades));
	}

/*< / ORIGEN-DESTINOS >*/

/*< VALIDACION DE ALTA VIAJES >*/	
	private boolean viajeValido(){
		if (!intValido(this.ventanaCargarViaje.getTextCapacidad().getText()))//Chequeo capacidad
			return false;
		if (!intValido(this.ventanaCargarViaje.getTextHorasEstimadas().getText()))//Chequeo horas estimadas
			return false;
		if(!precioValido())														//Chequeo precio
			return false;
		if(!ciudadDestinoValido()) {											//Chequeo ciudad y destino no sean las mismas
			//this.ventanaCargarViaje.getLblErrorOrigenDestino().setText("La ciudad de origen y la ciudad de destino no pueden ser las mismas!");
			return false;
		}
		return true;
	}

	private boolean intValido(String s) {
		if(entradaValida(s, Pattern.compile("[0-9]+")))
			return true;
		return false;
	}
	private boolean precioValido() {
		if(entradaValida(this.ventanaCargarViaje.getTextCapacidad().getText(), Pattern.compile("[0-9]+\\,({1}[0-9]+)?")))
			return true;
		return false;
	}
	private boolean ciudadDestinoValido() {
		String ciudadOrigenElegida = quitarIdDeCombo(this.ventanaCargarViaje.getComboBoxCiudadOrigen().getSelectedItem().toString());
		String ciudadDestinoElegida = quitarIdDeCombo(this.ventanaCargarViaje.getComboBoxCiudadDestino().getSelectedItem().toString());
		if(!(ciudadOrigenElegida.equals(ciudadDestinoElegida)))
			return true;
		return false;
	}
	private boolean entradaValida(String text,Pattern pattern) {
		if(text.matches(pattern.toString())) {
			return true;
		}
		return false;
	}
/*< / VALIDACION DE ALTA VIAJESS >*/	
	
	private void mostrarDatosViaje(ActionEvent E) {

		System.out.println("implementar");
	}	
	private java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
	
	public Date sumarRestarHorasFecha(Date fecha, int horas){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); // Configuramos la fecha que se recibe
		calendar.add(Calendar.HOUR, horas);  // numero de horas a añadir, o restar en caso de horas<0
		return convertUtilToSql(calendar.getTime()); // Devuelve el objeto Date con las nuevas horas añadidas
	}
	private Date agregarHorasAdate(Date fecha, String hora) {
		String[] tokens = hora.split(":");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.fechaSalida);
		cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(tokens[0]) * 3600000); //horas
		cal.set(Calendar.MINUTE,Integer.parseInt(tokens[1]) * 60000); //minutos
		Date d = convertUtilToSql(cal.getTime());
		
	    return d;
	}
	
	private void darAltaViaje(ActionEvent aV) {//throws Exception {
		//if(viajeValido()){
			System.out.println("Dar de alta el viaje");
			
			this.fechaSalida = convertUtilToSql(this.ventanaCargarViaje.getDateChooserFechaOrigen().getDate());
			this.horarioSalida = this.ventanaCargarViaje.getComboBoxHorarioSalida().getSelectedItem().toString();
			
			this.horasEstimadas = Integer.parseInt(this.ventanaCargarViaje.getTextHorasEstimadas().getText());
			//this.fechaLlegada = sumarRestarHorasFecha(agregarHorasAdate(this.fechaSalida,this.horarioSalida),this.horasEstimadas);
			
			this.precioViaje = new BigDecimal(ventanaCargarViaje.getTextPrecioViaje().getText());
			
			for(TransporteDTO t : modeloTransporte.obtenerTransportes())
				if(t.getNombre().equals(quitarIdDeCombo(this.ventanaCargarViaje.getComboBoxTransporte().getSelectedItem().toString())))
					this.transporteSeleccionado = t;
			
			this.capacidad = Integer.parseInt(this.ventanaCargarViaje.getTextCapacidad().getText());
			
			for(PaisDTO pa : modeloPais.obtenerPaises())
				if(pa.getNombre().equals(quitarIdDeCombo(this.ventanaCargarViaje.getComboBoxPaisOrigen().getSelectedItem().toString())))
					this.paisOrigen = pa;		
			for(PaisDTO pa : modeloPais.obtenerPaises())
				if(pa.getNombre().equals(quitarIdDeCombo(this.ventanaCargarViaje.getComboBoxPaisDestino().getSelectedItem().toString())))
					this.paisDestino = pa;
			
			for(ProvinciaDTO pr : modeloProvincia.obtenerProvincias())
				if(pr.getNombre().equals(quitarIdDeCombo(this.ventanaCargarViaje.getComboBoxProvinciaOrigen().getSelectedItem().toString())))
					this.provinciaOrigen = pr;		
			for(ProvinciaDTO pr : modeloProvincia.obtenerProvincias())
				if(pr.getNombre().equals(quitarIdDeCombo(this.ventanaCargarViaje.getComboBoxProvinciaDestino().getSelectedItem().toString())))
					this.provinciaDestino = pr;
			
			for(CiudadDTO c : modeloCiudad.obtenerCiudades()){
				if(c.getNombre().equals(quitarIdDeCombo(this.ventanaCargarViaje.getComboBoxCiudadOrigen().getSelectedItem().toString())))
					this.ciudadOrigen = c;		
			}
			for(CiudadDTO c : modeloCiudad.obtenerCiudades())
				if(c.getNombre().equals(quitarIdDeCombo(this.ventanaCargarViaje.getComboBoxCiudadDestino().getSelectedItem().toString())))
					this.ciudadDestino = c;
			
			//Creo el viaje
			this.viajeSeleccionado = new ViajeDTO(0,this.ciudadOrigen,
													this.ciudadDestino,
													this.fechaSalida,
					/*Cambiar x calculo de fecha*/	this.fechaSalida,
													this.horarioSalida,
													this.horasEstimadas,
													this.transporteSeleccionado,
													this.capacidad,
													this.precioViaje);
			System.out.println("VIAJE CREADO: ");
			System.out.print(this.viajeSeleccionado.getOrigenViaje().getIdCiudad()+"-"+this.viajeSeleccionado.getOrigenViaje().getNombre());
			System.out.print(" a ");
			System.out.println(this.viajeSeleccionado.getDestinoViaje().getIdCiudad()+"-"+this.viajeSeleccionado.getDestinoViaje().getNombre());

			//Agrego el viaje
			modeloViaje.agregarViaje(this.viajeSeleccionado);
			
		//}
		//else {
			//MOSTRAR VENTANA ERROR?
		//}
		llenarViajesEnTabla();
	}	
	
	/*- - - - - - - -  - - - - - - - < / METODOS DE VIAJE> - - - - - - - - - - - - - - - - --  */
/*/////////////////////////////////////////////////////////////////////////////////////////////////////*/
	/*IMPLEMENTADO BRANCH V3.0*/	
	private void logearse(ActionEvent log) {
		String usuario = ventanaLogin.getTextUsuario().getText();
		String password = new String(ventanaLogin.getPasswordField().getPassword());
		
		
		LoginDAOSQL dao = new LoginDAOSQL();
		usuarioLogueado = null;
		try {
			usuarioLogueado = dao.getByDatos(usuario, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(usuarioLogueado==null){
			this.ventanaLogin.getLblError().setVisible(true);
			System.out.println("EL USUARIO O CONTRASENA ES INCORRECTO");
		}
		else{
			System.out.println("SE LOGEO CORRECTAMENTE CON:"+ usuarioLogueado.getUsuario()+" "+usuarioLogueado.getContrasena()+" "+usuarioLogueado.getRol().getIdRol());
			if(usuarioLogueado.getRol().getIdRol()==2){
				 administrativoLogueado = obtenerAdministrativo(usuarioLogueado);
				 mostrarVentanaAdministrativo();
			}
			else{
				if(usuarioLogueado.getRol().getIdRol()==5){
					 clienteLogueado = obtenerCliente(usuarioLogueado);
					 mostrarVentanaCliente();
				}
				else{
					if(usuarioLogueado.getRol().getIdRol()==1){
						 administradorLogueado = obtenerAdministrador(usuarioLogueado);
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
	
//	private AdministrativoDTO busquedaRolAdministrativo(String user, String password) {
//		return modeloAdminisrativo.obtenerAdministrativoDatosLogin(user,password);
//	}
/*FIN IMPLEMENTACION BRANCH V3.0*/
	
	
	
	private void salirVentanaCliente(ActionEvent bc) {
		this.ventanaCliente.cerrarVentana();
	}

	private void llenarCiudadesEnCargaViajes() {
/*INGRESAR VALOR DEFAULT A LOS COMBOBOX."Seleccione un pais - -*/
		
/*CARGAR LOS PAISES DE LA BASE DE DATOS.*/
		llenarComboBoxPaises();
		llenarComboTransporte();
		llenarCombroHorarioSalida();
		calcularFechaLlegada();
/*UNA VEZ SELECIONADO EL PAIS, HABILITAR EL COMBO PROVINCIA, CORRESPONDIENTE AL PAIS CLICKOUT*/
		/*obtener el id del pais*/
/*MISMA ACCION PARA CIUDAD*/

/*HABILITAR EL BOTON SELECCIONAR ORIGEN*/

/*REALIZAR MISMA TAREA PARA DESTINO.*/

/*UNA VEZ ACEPTADO: MOSTRAR LA CIUDAD ORIGEN Y DESTINO EN LA TABLA CON LOS DEMAS DATOS CORRESPONDIENTES*/

		this.ventanaCargarViaje.getModelViajes().setRowCount(0);
		this.ventanaCargarViaje.getModelViajes().setColumnCount(0);
		this.ventanaCargarViaje.getModelViajes().setColumnIdentifiers(this.ventanaTablaViajes.getNombreColumnas());
		
		
/*CAMBIAR, CREAR LA CIUDAD*/		
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
		
//		this.ventanaCargarViaje.getTablaViajes();
		
	}
	
	
/*////////////////////////////////////////////////////////////////////////////////////////////////*/	
	
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
	
	private void llenarComboTransporte() {
		ArrayList<TransporteDTO> transportes = (ArrayList<TransporteDTO>) new Transporte(daoSqlFactory).obtenerTransportes();
		String[] nombresTransportes = new String[transportes.size()];
		for(int i=0; i<transportes.size();i++)
			nombresTransportes [i] = transportes.get(i).getNombre();
			
		this.ventanaCargarViaje.getComboBoxTransporte().setModel(new DefaultComboBoxModel(nombresTransportes));
	}

	private void llenarCombroHorarioSalida() {
		String [] horarios = {"1:00", "2:00", "3:00", "4:00", "5:00","6:00","7:00","8:00","9:00","10:00","11:00","12:00"};
		this.ventanaCargarViaje.getComboBoxHorarioSalida().setModel(new DefaultComboBoxModel(horarios));
		//ASI? O HACER HORARIODTO?
	}
	
	private void calcularFechaLlegada() {
		//horasEstimadas;
		//CALCULAR
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

//	private void altaCliente(ActionEvent client) {
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
////		mostrarVentanaReserva();  // Ventana creacion de pasajes.
//	
////		llenarViajesEnTabla();
//		
////		mostrarVentanaPago();
//		
////		llenarValoresEnCargaDeViaje();
//	}
//	
//	
///*< METODOS DE VIAJE > - - -   - - - - - - - - - - - - - - - - - - - - - - - - - - --  */
//	
///*< ORIGEN-DESTINOS >*/
//	private void llenarComboBoxPaises() {
//		List<PaisDTO> paises = new PaisDAOSQL().readAll();
//		String[] nombresPaises = new String[paises.size()];
//		for(int i=0; i<paises.size();i++){
//			String pais = paises.get(i).getIdPais()+"-"+paises.get(i).getNombre();
//			nombresPaises [i] = pais;
//		}	
///* LUEGO VER QUE NO SE PUEDA SELECCIONAR LA MISMA CIUDAD COMO ORIGEN Y DESTINO AL MISMO TIEMPO */
//		this.ventanaCargarViaje.getComboBoxCiudadOrigen().setModel(new DefaultComboBoxModel(nombresPaises));
//		this.ventanaCargarViaje.getComboBoxCiudadDestino().setModel(new DefaultComboBoxModel(nombresPaises));
//	}
//	
//	private void obtenerProvincias_porPaisOrigen(MouseEvent evt) {//obtener provinciasOrigen y setEnComboProvincia
//		String pais = ventanaCargarViaje.getComboBoxPaisOrigen().getSelectedItem().toString();
//		ventanaCargarViaje.getComboBoxProvinciaOrigen().setEnabled(true);
//    	llenarComboBoxProvinciasOrigen(Integer.parseInt(pais.substring(0)));
//	}
//
//	private void obtenerProvincias_porPaisDestino(MouseEvent evt) {//obteer provinciasDestino y setEnComboProvincia
//		String pais = ventanaCargarViaje.getComboBoxPaisDestino().getSelectedItem().toString();
//		ventanaCargarViaje.getComboBoxProvinciaDestino().setEnabled(true);
//    	llenarComboBoxProvinciasDestino(Integer.parseInt(pais.substring(0)));
//	}
//	
//	private void obtenerCiudades_porProvinciaOrigen(MouseEvent evt) {
//		String provincia = ventanaCargarViaje.getComboBoxProvinciaOrigen().getSelectedItem().toString();
//    	llenarComboBoxCiudadesOrigen(Integer.parseInt(provincia.substring(0)));
//	} 
//	
//	private void obtenerCiudades_porProvinciaDestino(MouseEvent evt) {
//		String provincia = ventanaCargarViaje.getComboBoxProvinciaDestino().getSelectedItem().toString();
//    	llenarComboBoxCiudadesDestino(Integer.parseInt(provincia.substring(0)));
//	} 
//	
//	private void llenarComboBoxProvinciasOrigen(int idPais) {
//		List<ProvinciaDTO> provincias = null;
//		try {
//			provincias = modeloProvincia.obtenerProvinciaPorIdPais(idPais);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		String[] nombresProvincias = new String[provincias.size()];
//		for(int i=0; i<provincias.size();i++){
//			String provincia = provincias.get(i).getIdProvincia()+"-"+provincias.get(i).getNombre();
//			nombresProvincias [i] = provincia;
//		}	
///* LUEGO VER QUE NO SE PUEDA SELECCIONAR LA MISMA CIUDAD COMO ORIGEN Y DESTINO AL MISMO TIEMPO */
//		this.ventanaCargarViaje.getComboBoxProvinciaOrigen().setModel(new DefaultComboBoxModel(nombresProvincias));
//	}
//	
//	private void llenarComboBoxProvinciasDestino(int idPais) {
//		List<ProvinciaDTO> provincias = null;
//		try {
//			provincias = modeloProvincia.obtenerProvinciaPorIdPais(idPais);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		String[] nombresProvincias = new String[provincias.size()];
//		for(int i=0; i<provincias.size();i++){
//			String provincia = provincias.get(i).getIdProvincia()+"-"+provincias.get(i).getNombre();
//			nombresProvincias [i] = provincia;
//		}	
//		this.ventanaCargarViaje.getComboBoxProvinciaDestino().setModel(new DefaultComboBoxModel(nombresProvincias));
//	}
//	
//	private void llenarComboBoxCiudadesOrigen(int idProvincia) {
//		List<CiudadDTO> ciudades = null;
//		try {
//			ciudades = modeloCiudad.obtenerCiudadPorIdProvincia(idProvincia);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		String[] nombresCiudades = new String[ciudades.size()];
//		
//		for(int i=0; i<ciudades.size();i++){
//			String ciudad = ciudades.get(i).getIdCiudad()+"-"+ciudades.get(i).getNombre();
//			nombresCiudades [i] = ciudad;
//		}	
//		this.ventanaCargarViaje.getComboBoxCiudadOrigen().setModel(new DefaultComboBoxModel(nombresCiudades));
//		
//	}
//	
//	private void llenarComboBoxCiudadesDestino(int idProvincia) {
//		List<CiudadDTO> ciudades = null;
//		try {
//			ciudades = modeloCiudad.obtenerCiudadPorIdProvincia(idProvincia);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		String[] nombresCiudades = new String[ciudades.size()];
//		
//		for(int i=0; i<ciudades.size();i++){
//			String ciudad = ciudades.get(i).getIdCiudad()+"-"+ciudades.get(i).getNombre();
//			nombresCiudades [i] = ciudad;
//		}	
//		this.ventanaCargarViaje.getComboBoxCiudadDestino().setModel(new DefaultComboBoxModel(nombresCiudades));
//	}
//
///*< / ORIGEN-DESTINOS >*/
//
///*< VALIDACION DE ALTA VIAJESS >*/	
//	
//	private boolean viajeValido(){
//		boolean ret = false;
//		
//		this.ciudadOrigenSelected = ventanaCargarViaje.getComboBoxCiudadOrigen().getSelectedItem().toString();
//		this.ciudadDestinoSelected = ventanaCargarViaje.getComboBoxCiudadDestino().getSelectedItem().toString();
//		
//		if(!(ciudadOrigenSelected.equals(ciudadDestinoSelected)) &&
//			!(provinciaOrigenSelected.equals(provinciaDestinoSelected))){
//			System.out.println("El origen y el destino tienen que ser diferentes");
//			ret = true;
//		}
//		return ret;
//	}
///*< / VALIDACION DE ALTA VIAJESS >*/	
//	
//	private void darAltaUnViajes(ActionEvent aV) {
//		if(viajeValido())
//			System.out.println("Dar de alta el viaje");
//		
//		
//		
////		ModeloCiudad modeloCiudad = new ModeloCiudad(new DAOSQLFactory());
////		ModeloViaje modeloViaje = new ModeloViaje(new DAOSQLFactory());
////		
/////*OBTENEMOS LA CIUDAD ELEGIDA EN LA VENTANACARGA DE VIAJES*/		
////		CiudadDTO origen = modeloCiudad.getCiudadByName(ventanaCargarViaje.getComboBoxCiudadOrigen().getSelectedItem().toString());
////		CiudadDTO destino = modeloCiudad.getCiudadByName(ventanaCargarViaje.getComboBoxCiudadDestino().getSelectedItem().toString());
////
/////*OBTENEMOS LAS HORAS TANDO DE SALIDA COMO DE LLEGADA, Y LA PARSEAMOS A TIPO DATE DE SQL*/
////		java.util.Date dateOrigen = ventanaCargarViaje.getDateChooserFechaOrigen().getDate();
////		java.sql.Date fechaSalida = new java.sql.Date(dateOrigen.getTime());
////		
////		java.util.Date dateDestino = ventanaCargarViaje.getDateChooserFechaDestino().getDate();
////		java.sql.Date fechaLlegada = new java.sql.Date(dateDestino.getTime());
////		
////		String horaSalida = ventanaCargarViaje.getComboBoxHorarioSalida().getSelectedItem().toString();
////		BigDecimal precio = new BigDecimal(ventanaCargarViaje.getTextPrecioViaje().getText());
////		
////		ViajeDTO nuevoViaje = new ViajeDTO(0, origen, destino, fechaSalida, fechaLlegada, precio, horaSalida);
//
//		// VER POR QUE NO FUNCIONA LA CONSULTA SQL EN EL MODELO	
////		modeloViaje.agregarViaje(nuevoViaje);
////
////		ViajeDAOSQL sql = new ViajeDAOSQL();		
////		sql.insert(nuevoViaje);
//		
//		llenarViajesEnTabla();
//		
//	}	
//	
//	/*- - - - - - - -  - - - - - - - < / METODOS DE VIAJE> - - - - - - - - - - - - - - - - --  */
///*/////////////////////////////////////////////////////////////////////////////////////////////////////*/
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	/*IMPLEMENTADO BRANCH V3.0*/	
//	private void logearse(ActionEvent log) {
//		String usuario = ventanaLogin.getTextUsuario().getText();
//		String password = new String(ventanaLogin.getPasswordField().getPassword());
//		
//		
//		LoginDAOSQL dao = new LoginDAOSQL();
//		usuarioLogeado = null;
//		try {
//			usuarioLogeado = dao.getByDatos(usuario, password);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		if(usuarioLogeado==null){
//			this.ventanaLogin.getLblError().setVisible(true);
//			System.out.println("EL USUARIO O CONTRASENA ES INCORRECTO");
//		}
//		else{
//			System.out.println("SE LOGEO CORRECTAMENTE CON:"+ usuarioLogeado.getUsuario()+" "+usuarioLogeado.getContrasena()+" "+usuarioLogeado.getRol().getIdRol());
//			if(usuarioLogeado.getRol().getIdRol()==2){
//				 administrativoLogueado = obtenerAdministrativo(usuarioLogeado);
//				 mostrarVentanaAdministrativo();
//			}
//			else{
//				if(usuarioLogeado.getRol().getIdRol()==5){
//					 clienteLogueado = obtenerCliente(usuarioLogeado);
//					 mostrarVentanaCliente();
//				}
//			}
//		}
//		
//	}
//
//	private void mostrarVentanaCliente() {
//		System.out.println("Se Loguea como Cliente");
//		System.out.println(clienteLogueado.getNombre());
//		this.ventanaLogin.setVisible(false);
//		
//	}
//
//	/*MOSTRAR LA VENTANA PRINCIPAL DEL PARSONAL ADMINISTRATIVO*/
//	private void mostrarVentanaAdministrativo() {
//		System.out.println("Se Loguea Como Administrativo");
//		System.out.println(administrativoLogueado.getNombre());
//		this.ventanaLogin.setVisible(false);
//		
//
//	}
//	
//	private void mostrarVentanaAdministrador() {
//		System.out.println("Se Loguea Como Administrador");
//		System.out.println(administradorLogueado.getNombre());
//		this.ventanaLogin.setVisible(false);
//		
//
//	}
///*-----------------------METODOS BUSCADOR POR ROLES ---------------------*/
//	private AdministrativoDTO obtenerAdministrativo(LoginDTO loginUsuario) {
//		AdministrativoDAOSQL dao = new AdministrativoDAOSQL();
//		return dao.getByLoginId(loginUsuario.getIdDatosLogin());
//	}
//	
//	private AdministradorDTO obtenerAdministrador(LoginDTO loginUsuario) {
//		AdministradorDAOSQL dao = new AdministradorDAOSQL();		
//		return dao.getByLoginId(loginUsuario.getIdDatosLogin());
//	}
//	
//	private ClienteDTO obtenerCliente(LoginDTO loginUsuario) {
//		ClienteDAOSQL sql = new ClienteDAOSQL();
//		return sql.getByLoginId(loginUsuario.getIdDatosLogin());
//	}
//	
////	private AdministrativoDTO busquedaRolAdministrativo(String user, String password) {
////		return modeloAdminisrativo.obtenerAdministrativoDatosLogin(user,password);
////	}
///*FIN IMPLEMENTACION BRANCH V3.0*/
//	
//	
//	
//	private void salirVentanaCliente(ActionEvent bc) {
//		this.ventanaCliente.cerrarVentana();
//	}
//
//	
//	
//	
//	
//	
//	private void llenarCiudadesEnCargaViajes() {
///*INGRESAR VALOR DEFAULT A LOS COMBOBOX."Seleccione un pais - -*/
//		
///*CARGAR LOS PAISES DE LA BASE DE DATOS.*/
//		llenarComboBoxPaises();
///*UNA VEZ SELECIONADO EL PAIS, HABILITAR EL COMBO PROVINCIA, CORRESPONDIENTE AL PAIS CLICKOUT*/
//		/*obtener el id del pais*/
///*MISMA ACCION PARA CIUDAD*/
//
///*HABILITAR EL BOTON SELECCIONAR ORIGEN*/
//
///*REALIZAR MISMA TAREA PARA DESTINO.*/
//
///*UNA VEZ ACEPTADO: MOSTRAR LA CIUDAD ORIGEN Y DESTINO EN LA TABLA CON LOS DEMAS DATOS CORRESPONDIENTES*/
//
//		this.ventanaCargarViaje.getModelViajes().setRowCount(0);
//		this.ventanaCargarViaje.getModelViajes().setColumnCount(0);
//		this.ventanaCargarViaje.getModelViajes().setColumnIdentifiers(this.ventanaTablaViajes.getNombreColumnas());
//		
//		
///*CAMBIAR, CREAR LA CIUDAD*/		
//		ArrayList<ViajeDTO> viajes_en_tabla = (ArrayList<ViajeDTO>) new ViajeDAOSQL().readAll();
//		for(int i=0; i< viajes_en_tabla.size();i++){
//			Object[] fila = { 
//					viajes_en_tabla.get(i).getOrigenViaje().getNombre(),
//					viajes_en_tabla.get(i).getDestinoViaje().getNombre(),
//					viajes_en_tabla.get(i).getFechaSalida(),
//					viajes_en_tabla.get(i).getFechaLlegada(),
//					viajes_en_tabla.get(i).getPrecio(),
//					viajes_en_tabla.get(i).getHoraSalida()
//			};
//			this.ventanaTablaViajes.getModelViajes().addRow(fila);
//		}
//		this.ventanaTablaViajes.setVisible(true);
//		
////		this.ventanaCargarViaje.getTablaViajes();
//		
//	}
//	
//	
///*////////////////////////////////////////////////////////////////////////////////////////////////*/	
//	
//	private void agregarPanelClientes(ActionEvent ac) {
//		this.vista.getPanelClientes().setVisible(true);
//		this.vista.getPanelReservas().setVisible(false);
//	}
//
//	private void agregarPanelPasajes(ActionEvent ap) {
//		this.vista.getPanelClientes().setVisible(false);
//		this.vista.getPanelReservas().setVisible(true);
//	}
//
//	private void agregarCliente(ActionEvent c) {
//		this.ventanaCliente.mostrarVentana();
//	}
//
//	private void agregarPasaje(ActionEvent p) {
//		mostrarVentanaReserva();
//	}
//
//	private void llenarTablaClientes(){
//		this.vista.getModelClientes().setRowCount(0); //Para vaciar la tabla
//		this.vista.getModelClientes().setColumnCount(0);
//		this.vista.getModelClientes().setColumnIdentifiers(this.vista.getNombreColumnasClientes());
//			
//		this.clientes_en_tabla = cliente.obtenerClientes();
//			
//		for (int i = 0; i < this.clientes_en_tabla.size(); i++)
//		{
//			Object[] fila = {this.clientes_en_tabla.get(i).getNombre(),
//							this.clientes_en_tabla.get(i).getApellido(),
//							this.clientes_en_tabla.get(i).getDni(),
//							this.clientes_en_tabla.get(i).getFechaNacimiento(),
//							this.clientes_en_tabla.get(i).getMedioContacto().getTelefonoFijo(),
//							this.clientes_en_tabla.get(i).getMedioContacto().getTelefonoCelular(),
//							this.clientes_en_tabla.get(i).getMedioContacto().getEmail()	
//			};
//							this.vista.getModelClientes().addRow(fila);
//		}		
//	}
////	
////		
//	
//	/*LABEL CANTIDAD DE PASAJEROS*/
//	
//	
//	private void llenarValoresEnCargaDeViaje() throws Exception{
//		
//		llenarComboRangoHorariosEnCargarViaje();//modificar para q levante de la base
//
//		llenarComboCiudadesEnCargarViaje();
//
//		mostrarVentanaCargarViaje();
//		
//	}
//	
//	private void llenarComboRangoHorariosEnCargarViaje() {
//		String [] horarios = {"1:00", "2:00", "3:00", "4:00", "5:00","6:00","7:00","8:00","9:00","10:00","11:00","12:00"};
//		this.ventanaCargarViaje.getComboBoxHorarioSalida().setModel(new DefaultComboBoxModel(horarios));
//	}
//
//	private void llenarComboCiudadesEnCargarViaje() {
//		/*CARGAMOS LOS VALORES DE LAS CIUDADES EN LA VENTANA DE DAR ALTA VIAJE*/
//				List<CiudadDTO> ciudades = new CiudadDAOSQL().readAll();
//				String[] nombresCiudades = new String[ciudades.size()];
//				for(int i=0; i<ciudades.size();i++){
//					nombresCiudades [i] = ciudades.get(i).getNombre();
//				}	
//		/* LUEGO VER QUE NO SE PUEDA SELECCIONAR LA MISMA CIUDAD COMO ORIGEN Y DESTINO AL MISMO TIEMPO */
//				this.ventanaCargarViaje.getComboBoxCiudadOrigen().setModel(new DefaultComboBoxModel(nombresCiudades));
//				this.ventanaCargarViaje.getComboBoxCiudadDestino().setModel(new DefaultComboBoxModel(nombresCiudades));
//	}
//
//	private void mostrarVentanaCargarViaje() {
//		this.ventanaCargarViaje.setVisible(true);
//		
//	}
//	
//	private void llenarViajesEnTabla(){
//		ViajeDAOSQL viajes = new ViajeDAOSQL();
//		viajes_en_tabla = viajes.readAll();
//	}
//	
//	private void seleccionarViaje(ActionEvent sV) {
//		
//		this.ventanaTablaViajes.setVisible(false);
//		this.ventanaReserva.setVisible(true);
//		
//		int filaSeleccionada = this.ventanaTablaViajes.getTablaViajes().getSelectedRow();
//		viajeSeleccionado = viajes_en_tabla.get(filaSeleccionada);
////		
////		String viajeString = String.valueOf(viajeSeleccionado.getId()) +
////				" -   DE : "+  viajeSeleccionado.getOrigenViaje().getNombre() +
////				"  -  HACIA : "+ viajeSeleccionado.getDestinoViaje().getNombre();
//		
////		this.ventanaReserva.getLblViajeSeleccionado().setText(viajeString);
//	}
//	
//	/*- - - - - - - -  - - - - - - - METODO DE CLIENTE - - - - - - - - - - - - - - - - --  */	
//
//	private void altaCliente(ActionEvent client) {
////		if(validarCampos()){	
////		/*Obtenemos la fecha de nacimiento , y la parseamos a tipo de date de SQL*/
////		java.util.Date dateFechaNacimiento = ventanaCliente.getDateFechaNacimiento().getDate();
////		java.sql.Date fechaNacimiento = new java.sql.Date(dateFechaNacimiento.getTime());
////		
////		/*Obtenemos el medio de contacto del cliente*/
////		MedioContactoDTO mContacto = new MedioContactoDTO();
////		mContacto.setTelefonoFijo(this.ventanaCliente.getTxtTelefonoFijo().getText());
////		mContacto.setTelefonoCelular(this.ventanaCliente.getTxtTelefonoCelular().getText());
////		mContacto.setEmail(this.ventanaCliente.getTxtEmail().getText());
////		
////		medioContacto.agregarMedioContacto(mContacto);
////		
////		ClienteDTO nuevoCliente = new ClienteDTO(0,
////				this.ventanaCliente.getTxtNombre().getText(),
////				this.ventanaCliente.getTxtApellido().getText(),
////				this.ventanaCliente.getTxtDni().getText(),
////				fechaNacimiento,
////				obtenerMedioContactoDTO(),
////				
////		);
////			cliente.agregarCliente(nuevoCliente);
////			llenarTablaClientes();
////			this.ventanaCliente.limpiarCampos();
////			this.ventanaCliente.dispose();
////		}
//	}
//	
//	private MedioContactoDTO obtenerMedioContactoDTO() {
//		MedioContacto medioContacto = new MedioContacto(new DAOSQLFactory());
//		MedioContactoDTO mContactoDTO = new MedioContactoDTO();
//		ArrayList<MedioContactoDTO> medios = (ArrayList<MedioContactoDTO>) medioContacto.obtenerMediosContacto();
//		for(MedioContactoDTO m: medios){
//			if(m.getEmail().toString().equals(this.ventanaCliente.getTxtEmail().getText()) &&
//				m.getTelefonoCelular().equals(this.ventanaCliente.getTxtTelefonoCelular().getText())&&
//				m.getTelefonoFijo().equals(this.ventanaCliente.getTxtTelefonoFijo().getText())){
//				mContactoDTO = m;
//			}
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
//		llenarValoresEnReserva();
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
//			ventanaPagoTarjeta.mostrarVentana(true);
//			ventanaFormaDePagos.mostrarVentana(false);
			}
		else if(itemSeleccionado.equals("EFECTIVO")){
//			ventanaPagoEfectivo.mostrarVentana(true);
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
		this.ventanaCliente = VentanaRegistrarCliente.getInstance();
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
//		this.ventanaFormaDePagos.setVisible(true);
//		
//		this.transporteSeleccionado = obtenerTransporteElegidoPorCliente(this.ventanaReserva.getComboBoxTransporte().getSelectedItem().toString());
//		this.ventanaFormaDePagos.getLblMontoaPagar().setText("$ "+calcularMontoDePasaje().toString());
//		
//	}
//	
//	private BigDecimal calcularMontoDePasaje() {
//		BigDecimal Valor1 = this.viajeSeleccionado.getPrecio();
////		BigDecimal Valor2 = this.transporteSeleccionado.getPrecioBase();
////		Valor2 = Valor2.add(Valor1);
////		totalaPagar = Valor2;
//		
//		return totalaPagar.multiply(new BigDecimal(pasajerosEnEstaReserva.size()));
//	}
//
//
//	/* - - - -  - - - - - - - - - -  - - OTROS METODOS - - - - - - - - - - - - - - - -  -*/	
//	
//	private TransporteDTO obtenerTransporteElegidoPorCliente(String transporteComboBox) {
//		TransporteDAOSQL tDAO = new TransporteDAOSQL();
//		TransporteDTO ret = null;
//		ArrayList<TransporteDTO> transportes = (ArrayList<TransporteDTO>) tDAO.readAll();
//		for(TransporteDTO t: transportes)
////			if(t.getNombreTransporte().equals(transporteComboBox))
//				ret = t;
//		return ret;
//	}
//	
////	private void generarPasajeTarjeta(ActionEvent rP) {
////		String importeIngresado = ventanaPagoTarjeta.getTextImporteIngresado().toString();
////	}
////	
//
//	private void redirigirSegunItemSeleccionado(String itemSeleccionado) {
//		if(itemSeleccionado.equals("TARJETA")){
////			ventanaPagoTarjeta.mostrarVentana(true);
////			ventanaFormaDePagos.mostrarVentana(false);
//			ventanaFormaDePagos.redimensionar();
//			}
//		else if(itemSeleccionado.equals("EFECTIVO")){
////			ventanaPagoEfectivo.mostrarVentana(true);
//			ventanaFormaDePagos.mostrarVentana(false);
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
	
	public static void main(String[] args) throws Exception {
		Vista vista = new Vista();
		Controlador controlador = new Controlador(vista);
		controlador.inicializar();
		
//		ModeloCiudad modelo = new ModeloCiudad(daoSqlFactory);
//		for(CiudadDTO c : modelo.obtenerCiudadPorIdProvincia(1818)) {
//			System.out.println(c.getIdCiudad() + c.getNombre());
//		}
	}
}