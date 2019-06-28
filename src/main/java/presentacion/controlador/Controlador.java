package presentacion.controlador;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import correo.EnvioDeCorreo;
import dto.AdministradorDTO;
import dto.AdministrativoDTO;
import dto.CiudadDTO;
import dto.ClienteDTO;
import dto.HorarioReservaDTO;
import dto.LoginDTO;
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
import modelo.ModeloPais;
import modelo.ModeloProvincia;
import modelo.ModeloViaje;
import modelo.Pasaje;
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
import persistencia.dao.mysql.TransporteDAOSQL;
import persistencia.dao.mysql.ViajeDAOSQL;
import presentacion.vista.VentanaLogin;
//import presentacion.vista.VentanaPagoEfectivo;
//import presentacion.vista.VentanaPagoTarjeta;
import presentacion.vista.VentanaReserva;
import presentacion.vista.Vista;
import presentacion.vista.administrador.PanelViajes;
import presentacion.vista.administrador.VentanaAgregarPais;
import presentacion.vista.administrador.VentanaCargarViaje;
import presentacion.vista.administrador.VentanaEditarViaje;
import presentacion.vista.administrador.VistaAdministrador;
import presentacion.vista.administrativo.VentanaCargaPasajero;
import presentacion.vista.administrativo.VentanaPago;
import presentacion.vista.administrativo.VentanaPasajero;
import presentacion.vista.administrativo.VentanaRegistrarCliente;
import presentacion.vista.administrativo.VentanaTablaViajes;
import recursos.Mapper;

public class Controlador implements ActionListener {

	/* AMB PAIS PROV CIUDAD */
	private ModeloPais controladorAdministrador_modeloPais;
	private ModeloCiudad controladorAdministrador_modeloCiudad;
	private ModeloProvincia controladorAdministrador_modeloProvincia;
	private VentanaAgregarPais controladorAdministrador_ventanaAgregarPais;

	private Mapper mapper;
	private PanelViajes panelViajes;
	private List<ViajeDTO> viajes_en_tabla;
	private List<ClienteDTO> clientes_en_tabla;
	private Vista vista;
	private VentanaRegistrarCliente ventanaCliente;
	private Cliente cliente;
	private MedioContacto medioContacto;
	private VentanaReserva ventanaReserva;
	private VentanaPago ventanaFormaDePagos;
	// private VentanaPagoTarjeta ventanaPagoTarjeta;
	// private VentanaPagoEfectivo ventanaPagoEfectivo;
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

	// DATOS DEL VIAJE
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
	// FIN DATOS VIAJE

	private String provinciaOrigenSelected;
	private String provinciaDestinoSelected;
	private String ciudadOrigenSelected;
	private String ciudadDestinoSelected;
	private AdministrativoDTO administrativoLogueado;
	private ClienteDTO clienteLogueado;
	private AdministradorDTO administradorLogueado;

	/* AGREGADO PARA 3ER REUNION */
	private VentanaLogin ventanaLogin;
	private Administrativo modeloAdminisrativo;
	private static DAOSQLFactory daoSqlFactory;
	private VentanaEditarViaje ventanaEditarViaje;

	private boolean origenListo;
	private boolean destinoListo;
	private ArrayList<String> msjErrorOrigenDestino;

	/* ADMINSITRADOR */
	private ModeloPais modeloPais;
	private ModeloProvincia modeloProvincia;
	private ModeloCiudad modeloCiudad;
	private Transporte modeloTransporte;
	private ModeloViaje modeloViaje;
	// Filtro
	private DefaultTableModel dm;
	private StringBuilder cad = new StringBuilder();
	private TableRowSorter<DefaultTableModel> tr;
	private String aceptada = "0123456789abcdefghijklmnopqrstuvwxyz";
	private DefaultTableModel tableModel;
	private static Controlador INSTANCE;

	public static Controlador getInstance() {
		if (INSTANCE == null)
			return new Controlador();
		else
			return INSTANCE;
	}

	private Controlador() {
		this.mapper = new Mapper();
		this.ventanaReserva = VentanaReserva.getInstance();
		this.ventanaFormaDePagos = VentanaPago.getInstance();
		this.ventanaPasajero = VentanaPasajero.getInstance();
		this.ventanaCargarViaje = VentanaCargarViaje.getInstance();
		this.ventanaCargaPasajero = VentanaCargaPasajero.getInstance();
		this.ventanaTablaViajes = VentanaTablaViajes.getInstance();
		this.ventanaLogin = VentanaLogin.getInstance();
		this.ventanaEditarViaje = VentanaEditarViaje.getInstance();

		/* abm pais ciudad prov */
		this.controladorAdministrador_ventanaAgregarPais = VentanaAgregarPais.getInstance();

		// INICIALIZACION datos del viaje
		this.fechaSalida = null;
		this.horarioSalida = "";
		this.horasEstimadas = null;
		this.fechaLlegada = null;
		this.precioViaje = null;
		this.transporteSeleccionado = null;
		this.paisOrigen = null;
		this.paisDestino = null;
		this.provinciaOrigen = null;
		this.provinciaDestino = null;
		this.ciudadOrigen = null;
		this.ciudadDestino = null;
		this.origenListo = false;
		this.destinoListo = false;
		this.msjErrorOrigenDestino = new ArrayList<String>();
		// FIN datos del viaje

		/* ENTIDADES LOGEADAS */
		this.administrativoLogueado = null;
		this.clienteLogueado = null;
		this.administradorLogueado = null;

		/* ventanas auxiliares */
		this.ventanaAdministrador = VistaAdministrador.getInstance();

		/* Inicio de Modelos */
		this.daoSqlFactory = new DAOSQLFactory();
		this.modeloAdminisrativo = new Administrativo(daoSqlFactory);
		this.modeloProvincia = new ModeloProvincia(daoSqlFactory);
		this.usuarioLogueado = new LoginDTO();
		this.modeloCiudad = new ModeloCiudad(daoSqlFactory);
		this.modeloProvincia = new ModeloProvincia(daoSqlFactory);
		this.modeloPais = new ModeloPais(daoSqlFactory);
		this.modeloTransporte = new Transporte(daoSqlFactory);
		this.modeloViaje = new ModeloViaje(daoSqlFactory);
		this.medioContacto = new MedioContacto(daoSqlFactory);
		this.cliente = new Cliente(daoSqlFactory);
		this.panelViajes = new PanelViajes();

		/* Fin de Modelos */

		this.viajes_en_tabla = new ArrayList<ViajeDTO>();
		this.clientes_en_tabla = new ArrayList<ClienteDTO>();
		this.pasajerosEnEstaReserva = new ArrayList<PasajeroDTO>();
		this.viajeSeleccionado = new ViajeDTO();
		this.transporteSeleccionado = new TransporteDTO();
		this.controladorAdministrador_ventanaAgregarPais.getBtnAgregar().addActionListener(agP -> agregarPais(agP));
		this.ventanaLogin.getBtnLogin().addActionListener(log -> logearse(log));
		this.ventanaReserva.getBtnIrViajes().addActionListener(iV -> mostrarViajesDisponibles(iV));
		this.ventanaReserva.getBtnRealizarPago().addActionListener(rP -> realizarPago(rP));
		this.ventanaFormaDePagos.getBtnPago().addActionListener(pago -> darAltaDelPago(pago));
		this.ventanaCliente = VentanaRegistrarCliente.getInstance();
		this.ventanaCliente.getBtnCancelar().addActionListener(bc -> salirVentanaCliente(bc));
		
		this.ventanaCargarViaje.getBtnCrearViaje().addActionListener(aV -> darAltaViaje(aV));
		//TODO: BOTON CANCELAR
		this.ventanaEditarViaje.getBtnEditarViaje().addActionListener(ed -> accionEditarViaje(ed));
		//TODO: BOTON CANCELAR
		this.ventanaAdministrador.getPanelViajes().getActivos().addActionListener(mv -> mostrarViajesActivos(mv));
		this.ventanaAdministrador.getPanelViajes().getInactivos().addActionListener(mv -> mostrarViajesInactivos(mv));
		this.ventanaAdministrador.getPanelViajes().getCheckBoxAll().addActionListener(mv -> mostrarTodosLosViajes(mv));
		this.ventanaAdministrador.getPanelViajes().getBtnLimpiarFiltros().addActionListener(lc -> limpiarCampos(lc));

		this.ventanaCargarViaje.getComboBoxPaisOrigen().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obtenerProvincias_porPaisOrigen(e);
			}
		});

		this.ventanaCargarViaje.getComboBoxPaisDestino().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obtenerProvincias_porPaisDestino(e);
			}
		});

		this.ventanaCargarViaje.getComboBoxProvinciaOrigen().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obtenerCiudades_porProvinciaOrigen(e);
			}
		});

		this.ventanaCargarViaje.getComboBoxProvinciaDestino().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obtenerCiudades_porProvinciaDestino(e);
			}
		});
		// - - - - - - - - - - - - - -- - - - - - FILTROS - - -- - - -- -- -- -

		this.ventanaAdministrador.getPanelViajes().getTxtFiltro().addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char letra = e.getKeyChar();
				if (Character.isDigit(letra)) {
					Toolkit.getDefaultToolkit().beep();
					e.consume(); 
				}
				tableModel = (DefaultTableModel) ventanaAdministrador.getPanelViajes().getModelViajes();
				tr = new TableRowSorter<>(tableModel);
				ventanaAdministrador.getPanelViajes().getTablaViajes().setRowSorter(tr);
				if (aceptada.indexOf(letra) != -1 || letra == KeyEvent.VK_BACK_SPACE) {
					if (letra == KeyEvent.VK_BACK_SPACE){
						if(cad.length() != 0) {
							cad.deleteCharAt(cad.length()-1);
					        tr.setRowFilter(RowFilter.regexFilter(cad.toString()));
						}
					}
					else{
						if(!Character.isDigit(letra)) {
							cad.append(String.valueOf(letra));
			    			tr.setRowFilter(RowFilter.regexFilter(cad.toString()));
						}
					}
				}
		}
		});

		// FILTRO CALENDAR DESDE
		this.ventanaAdministrador.getPanelViajes().getFechaDesde()
				.addPropertyChangeListener(new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent e) {
						String desde = obtenerFecha(e.getNewValue().toString());
						if (ventanaAdministrador.getPanelViajes().getFechaHasta().getDate() != null) {
							String hasta = obtenerFecha(
									ventanaAdministrador.getPanelViajes().getFechaHasta().getDate().toString());
							llenarViajesEnPanelViajes(modeloViaje.obtenerBetween(desde, hasta));
						}
					}
				});

		this.ventanaAdministrador.getPanelViajes().getFechaHasta()
				.addPropertyChangeListener(new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent e) {
						String hasta = obtenerFecha(e.getNewValue().toString());
						if (ventanaAdministrador.getPanelViajes().getFechaDesde().getDate() != null) {
							String desde = obtenerFecha(
									ventanaAdministrador.getPanelViajes().getFechaDesde().getDate().toString());
							llenarViajesEnPanelViajes(modeloViaje.obtenerBetween(desde, hasta));
						}
					}
				});

		this.ventanaAdministrador.getPanelViajes().getTxtPrecioDesde().addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char letra = e.getKeyChar();
				if (!Character.isDigit(letra)) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
			}
			public void keyReleased(KeyEvent e){
				char letra = e.getKeyChar();
				if (aceptada.indexOf(letra) != -1 || letra == KeyEvent.VK_BACK_SPACE) {
					if(esUnNumero(ventanaAdministrador.getPanelViajes().getTxtPrecioDesde().getText())) {
						if(esUnNumero(ventanaAdministrador.getPanelViajes().getTextPrecioHasta().getText())) {
							Integer desde = Integer.parseInt(ventanaAdministrador.getPanelViajes().getTxtPrecioDesde().getText());
							Integer hasta = Integer.parseInt(ventanaAdministrador.getPanelViajes().getTextPrecioHasta().getText());
							llenarViajesEnPanelViajes(modeloViaje.obtenerBetweenPrecio(desde, hasta));	
						}
					}
					if(ventanaAdministrador.getPanelViajes().getTxtPrecioDesde().getText().length() == 0) {
						llenarViajesEnPanelViajes(modeloViaje.obtenerViajes());
					}
				}
			}
		});

		this.ventanaAdministrador.getPanelViajes().getTextPrecioHasta().addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char letra = e.getKeyChar();
				if (!Character.isDigit(letra)) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
			}
			public void keyReleased(KeyEvent e){
				char letra = e.getKeyChar();
				if (aceptada.indexOf(letra) != -1 || letra == KeyEvent.VK_BACK_SPACE) {
					if(esUnNumero(ventanaAdministrador.getPanelViajes().getTextPrecioHasta().getText())) {
						if(esUnNumero(ventanaAdministrador.getPanelViajes().getTxtPrecioDesde().getText())) {
							Integer desde = Integer.parseInt(ventanaAdministrador.getPanelViajes().getTxtPrecioDesde().getText());
							Integer hasta = Integer.parseInt(ventanaAdministrador.getPanelViajes().getTextPrecioHasta().getText());
							llenarViajesEnPanelViajes(modeloViaje.obtenerBetweenPrecio(desde, hasta));	
						}
					}
					if(ventanaAdministrador.getPanelViajes().getTextPrecioHasta().getText().length() == 0) {
						llenarViajesEnPanelViajes(modeloViaje.obtenerViajes());
					}
				}
			}
		});
		
		
		this.ventanaCargarViaje.getTextCapacidad().addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char letra = e.getKeyChar();
				if (!Character.isDigit(letra)) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
				if(ventanaCargarViaje.getTextCapacidad().getText().length() == 11) {
					e.consume();
				}
			}
		});

		this.ventanaCargarViaje.getTextHorasEstimadas().addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char letra = e.getKeyChar();
				if (!Character.isDigit(letra)) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
				if(ventanaCargarViaje.getTextHorasEstimadas().getText().length() == 11) {
					e.consume();
				}
			}
		});
		

//		llenarComboPrecios();

	}


	private void limpiarCampos(ActionEvent lc) {
		this.ventanaAdministrador.getPanelViajes().getTxtFiltro().setText("");
//		cad.delete(0, cad.length());
//        tr.setRowFilter(RowFilter.regexFilter(cad.toString()));
		this.ventanaAdministrador.getPanelViajes().getTxtPrecioDesde().setText("");
		this.ventanaAdministrador.getPanelViajes().getTextPrecioHasta().setText("");
		this.ventanaAdministrador.getPanelViajes().limpiarFiltrosFechas();
		llenarViajesEnPanelViajes();
	}

	private void mostrarTodosLosViajes(ActionEvent mv) {
		llenarViajesEnPanelViajes();
	}

	private void mostrarViajesInactivos(ActionEvent mv) {

		llenarViajesEnPanelViajesInactivos();
	}

	private void mostrarViajesActivos(ActionEvent mv) {

		llenarViajesEnPanelViajesActivos();
	}

	private void llenarViajesEnPanelViajesInactivos() {
		this.ventanaAdministrador.getPanelViajes().getActivos().setSelected(false);
		this.ventanaAdministrador.getPanelViajes().getCheckBoxAll().setSelected(false);
		this.panelViajes = this.ventanaAdministrador.getPanelViajes();
		this.panelViajes.getModelViajes().setRowCount(0);
		this.panelViajes.getModelViajes().setColumnCount(0);
		this.panelViajes.getModelViajes().setColumnIdentifiers(this.panelViajes.getNombreColumnasViajes());
		viajes_en_tabla = (ArrayList<ViajeDTO>) modeloViaje.obtenerViajes();
		for (int i = 0; i < viajes_en_tabla.size(); i++) {
			if (viajes_en_tabla.get(i).getEstado().equals("inactivo")) {
				Object[] fila = { viajes_en_tabla.get(i).getCiudadOrigen().getNombre(),
						viajes_en_tabla.get(i).getCiudadDestino().getNombre(),
						mapper.parseToString(viajes_en_tabla.get(i).getFechaSalida()),
						mapper.parseToString(viajes_en_tabla.get(i).getFechaLlegada()),
						viajes_en_tabla.get(i).getHoraSalida(), viajes_en_tabla.get(i).getHorasEstimadas(),
						viajes_en_tabla.get(i).getCapacidad(), viajes_en_tabla.get(i).getTransporte().getNombre(),
						"$ " + viajes_en_tabla.get(i).getPrecio(), viajes_en_tabla.get(i).getEstado() };
				this.panelViajes.getModelViajes().addRow(fila);
			}
		}
	}

	private void llenarViajesEnPanelViajesActivos() {
		this.ventanaAdministrador.getPanelViajes().getInactivos().setSelected(false);
		this.ventanaAdministrador.getPanelViajes().getCheckBoxAll().setSelected(false);
		this.panelViajes = this.ventanaAdministrador.getPanelViajes();
		this.panelViajes.getModelViajes().setRowCount(0);
		this.panelViajes.getModelViajes().setColumnCount(0);
		this.panelViajes.getModelViajes().setColumnIdentifiers(this.panelViajes.getNombreColumnasViajes());
		viajes_en_tabla = (ArrayList<ViajeDTO>) modeloViaje.obtenerViajes();
		for (int i = 0; i < viajes_en_tabla.size(); i++) {
			if (viajes_en_tabla.get(i).getEstado().equals("activo")) {
				Object[] fila = { viajes_en_tabla.get(i).getCiudadOrigen().getNombre(),
						viajes_en_tabla.get(i).getCiudadDestino().getNombre(),
						mapper.parseToString(viajes_en_tabla.get(i).getFechaSalida()),
						mapper.parseToString(viajes_en_tabla.get(i).getFechaLlegada()),
						viajes_en_tabla.get(i).getHoraSalida(), viajes_en_tabla.get(i).getHorasEstimadas(),
						viajes_en_tabla.get(i).getCapacidad(), viajes_en_tabla.get(i).getTransporte().getNombre(),
						"$ " + viajes_en_tabla.get(i).getPrecio(), viajes_en_tabla.get(i).getEstado() };
				this.panelViajes.getModelViajes().addRow(fila);
			}
		}
	}

	private void accionEditarViaje(ActionEvent ed) {
		int respuesta =-1;
		this.viajeSeleccionado.setHoraSalida(this.ventanaEditarViaje.getComboBoxHorarioSalida().getSelectedItem().toString());
		String estadoAntesDeLaModificacion = this.viajeSeleccionado.getEstado();
		this.viajeSeleccionado.setEstado(this.ventanaEditarViaje.getComboBoxEstados().getSelectedItem().toString());
		String estadoDespuesDeLaModificacion = this.viajeSeleccionado.getEstado();

		if(!(estadoAntesDeLaModificacion.equals(estadoDespuesDeLaModificacion)) && (viajeSeleccionado.getEstado().equals("inactivo"))){
			 respuesta = JOptionPane.showConfirmDialog(null, "Esta deshabilitando el viaje. Los pasajes relacionados a este viaje se cancelarán. ¿Desea continuar?", "Confirmar salida", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			 if(respuesta!=2){
				JOptionPane.showMessageDialog(null, "Se le ha enviado un correo a los clientes para informar la cancelación de los pasajes", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
				cancelarTodosLosPasajesConElViaje(viajeSeleccionado);
			 }
		 }
		if(respuesta!=2){ // 0 acepta, 2 cancela, -1 no muestra cartel	
			modeloViaje.editarViaje(viajeSeleccionado);
		}
		llenarViajesEnPanelViajes();
		this.ventanaEditarViaje.setVisible(false);
	}
	
	private void cancelarTodosLosPasajesConElViaje(ViajeDTO viaje){
		Pasaje modeloPasaje = new Pasaje(new DAOSQLFactory());
		EnvioDeCorreo envioDeMail = new EnvioDeCorreo();
		
		java.util.Date fecha = new java.util.Date(); 
		Date fechaActual = new java.sql.Date(fecha.getTime());	
		
		ArrayList<PasajeDTO> pasajes = (ArrayList<PasajeDTO>) modeloPasaje.obtenerPasajes();
	
		for(PasajeDTO p:pasajes){
			if(p.getViaje().getIdViaje()==viaje.getIdViaje()){
				
				p.getEstadoDelPasaje().setIdEstadoPasaje(4);
				p.setMotivoCancelacion("Cancelacion por Administrador");
				p.setDateCancelacion(fechaActual);
				
				modeloPasaje.editarPasaje(p);
				String adjunto = "Cancelacion de viaje";
				String cuerpoMail = "Estimado cliente "+p.getCliente().getNombre()+", se le informa que el viaje con destino a :"+viaje.getCiudadDestino().getNombre()+" del dia :"+viaje.getFechaSalidaParseada()+" fue cancelado por razones propias a la empresa. \n"
						+ " Acérquese al local más cercano para la devolución total de su dinero. \n "
						+ "Disculpe las molestias, \n "
						+ "Al Planeta - Empresa de venta de pasajes.";
				envioDeMail.enviarNotificacion(p.getCliente().getMail(), cuerpoMail, adjunto);
			}
		}
	}

	private void agregarPais(ActionEvent agP) {
		PaisDTO paisNuevo = new PaisDTO();
		paisNuevo.setNombre(this.controladorAdministrador_ventanaAgregarPais.getTxtNombrePais().getText());
		this.controladorAdministrador_modeloPais.agregarPais(paisNuevo);
	}


	/* < ORIGEN-DESTINOS > */
	private void llenarComboBoxPaises() {
		List<PaisDTO> paises = new PaisDAOSQL().readAll();
		String[] nombresPaises = new String[paises.size() + 1];
		nombresPaises[0] = "-Seleccione pais-";
		for (int i = 0; i < paises.size(); i++) {
			String pais = paises.get(i).getIdPais() + "-" + paises.get(i).getNombre();
			nombresPaises[i + 1] = pais;
		}
		/*
		 * LUEGO VER QUE NO SE PUEDA SELECCIONAR LA MISMA CIUDAD COMO ORIGEN Y
		 * DESTINO AL MISMO TIEMPO
		 */
		this.ventanaCargarViaje.getComboBoxPaisOrigen().setModel(new DefaultComboBoxModel(nombresPaises));
		this.ventanaCargarViaje.getComboBoxPaisDestino().setModel(new DefaultComboBoxModel(nombresPaises));
		this.ventanaEditarViaje.getComboBoxPaisOrigen().setModel(new DefaultComboBoxModel(nombresPaises));
		this.ventanaEditarViaje.getComboBoxPaisDestino().setModel(new DefaultComboBoxModel(nombresPaises));
	}

	private void obtenerProvincias_porPaisOrigen(ActionEvent e) {// MouseEvent
																	// evt)
																	// {//obtener
																	// provinciasOrigen
																	// y
																	// setEnComboProvincia
		String pais = ventanaCargarViaje.getComboBoxPaisOrigen().getSelectedItem().toString();
		ventanaCargarViaje.getComboBoxProvinciaOrigen().setEnabled(true);
		if (!this.ventanaCargarViaje.getComboBoxPaisOrigen().getSelectedItem().equals("-Seleccione pais-"))
			llenarComboBoxProvinciasOrigen(Integer.parseInt(obtenerIdDesdeCombo(pais)));
		else {
			this.ventanaCargarViaje.getComboBoxProvinciaOrigen().setEnabled(false);
			this.ventanaCargarViaje.getComboBoxCiudadOrigen().setEnabled(false);
			// this.ventanaCargarViaje.getBtnOK().setEnabled(false);
			this.ventanaCargarViaje.getBtnCrearViaje().setEnabled(false);
		}
	}

	private String obtenerIdDesdeCombo(String s) {
		String ret = "";
		for (int n = 0; n < s.length(); n++) {
			char c = s.charAt(n);
			if (c != '-') {
				ret += c;
			} else {
				return ret;
			}
		}
		return ret;
	}

	private String quitarIdDeCombo(String s) {
		String ret = "";
		for (int n = 0; n < s.length(); n++) {
			char c = s.charAt(n);
			if (c != '0' && c != '1' && c != '2' && c != '3' && c != '4' && c != '5' && c != '6' && c != '7' && c != '8'
					&& c != '9' && c != '-') {
				ret += c;
			}
		}
		return ret;
	}

	private void obtenerProvincias_porPaisDestino(ActionEvent evt) {// obteer
																	// provinciasDestino
																	// y
																	// setEnComboProvincia
		String pais = ventanaCargarViaje.getComboBoxPaisDestino().getSelectedItem().toString();
		ventanaCargarViaje.getComboBoxProvinciaDestino().setEnabled(true);
		if (!this.ventanaCargarViaje.getComboBoxPaisDestino().getSelectedItem().equals("-Seleccione pais-"))
			llenarComboBoxProvinciasDestino(Integer.parseInt(obtenerIdDesdeCombo(pais)));
		else {
			this.ventanaCargarViaje.getComboBoxProvinciaDestino().setEnabled(false);
			this.ventanaCargarViaje.getComboBoxCiudadDestino().setEnabled(false);
			// this.ventanaCargarViaje.getBtnOK().setEnabled(false);
			this.ventanaCargarViaje.getBtnCrearViaje().setEnabled(false);
		}
	}

	private void obtenerCiudades_porProvinciaOrigen(ActionEvent evt) {
		String provincia = ventanaCargarViaje.getComboBoxProvinciaOrigen().getSelectedItem().toString();
		ventanaCargarViaje.getComboBoxCiudadOrigen().setEnabled(true);
		if (!this.ventanaCargarViaje.getComboBoxProvinciaOrigen().getSelectedItem().equals("-Seleccione provincia-")) {
			llenarComboBoxCiudadesOrigen(Integer.parseInt(obtenerIdDesdeCombo(provincia)));
			origenListo = true;
			if (origenListo && destinoListo) // Activa boton OK si seleccion
												// destino y origen esta
												// completa
				ventanaCargarViaje.getBtnCrearViaje().setEnabled(true);
		} else {
			this.ventanaCargarViaje.getComboBoxCiudadOrigen().setEnabled(false);
			// this.ventanaCargarViaje.getBtnOK().setEnabled(false);
			this.ventanaCargarViaje.getBtnCrearViaje().setEnabled(false);
		}

	}

	private void obtenerCiudades_porProvinciaDestino(ActionEvent evt) {
		String provincia = ventanaCargarViaje.getComboBoxProvinciaDestino().getSelectedItem().toString();
		ventanaCargarViaje.getComboBoxCiudadDestino().setEnabled(true);
		if (!this.ventanaCargarViaje.getComboBoxProvinciaDestino().getSelectedItem().equals("-Seleccione provincia-")) {
			llenarComboBoxCiudadesDestino(Integer.parseInt(obtenerIdDesdeCombo(provincia)));
			destinoListo = true;
			if (origenListo && destinoListo) // Activa boton OK si seleccion
												// destino y origen esta
												// completa
				ventanaCargarViaje.getBtnCrearViaje().setEnabled(true);
		} else {
			this.ventanaCargarViaje.getComboBoxCiudadDestino().setEnabled(false);
			// this.ventanaCargarViaje.getBtnOK().setEnabled(false);
			this.ventanaCargarViaje.getBtnCrearViaje().setEnabled(false);
		}
	}

	private void llenarComboBoxProvinciasOrigen(int idPais) {
		List<ProvinciaDTO> provincias = null;
		provincias = modeloProvincia.obtenerProvinciaPorIdPais(idPais);
		String[] nombresProvincias = new String[provincias.size() + 1];
		nombresProvincias[0] = "-Seleccione provincia-";
		for (int i = 0; i < provincias.size(); i++) {
			String provincia = provincias.get(i).getIdProvincia() + "-" + provincias.get(i).getNombre();
			nombresProvincias[i + 1] = provincia;
		}
		/*
		 * LUEGO VER QUE NO SE PUEDA SELECCIONAR LA MISMA CIUDAD COMO ORIGEN Y
		 * DESTINO AL MISMO TIEMPO
		 */
		this.ventanaCargarViaje.getComboBoxProvinciaOrigen().setModel(new DefaultComboBoxModel(nombresProvincias));
		this.ventanaEditarViaje.getComboBoxProvinciaOrigen().setModel(new DefaultComboBoxModel(nombresProvincias));
	}

	private void llenarComboBoxProvinciasDestino(int idPais) {
		List<ProvinciaDTO> provincias = null;
		provincias = modeloProvincia.obtenerProvinciaPorIdPais(idPais);
		String[] nombresProvincias = new String[provincias.size() + 1];
		nombresProvincias[0] = "-Seleccione provincia-";
		for (int i = 0; i < provincias.size(); i++) {
			String provincia = provincias.get(i).getIdProvincia() + "-" + provincias.get(i).getNombre();
			nombresProvincias[i + 1] = provincia;
		}
		this.ventanaCargarViaje.getComboBoxProvinciaDestino().setModel(new DefaultComboBoxModel(nombresProvincias));
		this.ventanaEditarViaje.getComboBoxProvinciaDestino().setModel(new DefaultComboBoxModel(nombresProvincias));
	}

	private void llenarComboBoxCiudadesOrigen(int idProvincia) {
		List<CiudadDTO> ciudades = null;
		ciudades = modeloCiudad.obtenerCiudadPorIdProvincia(idProvincia);
		String[] nombresCiudades = new String[ciudades.size() + 1];
		nombresCiudades[0] = "-Seleccione ciudad-";
		for (int i = 0; i < ciudades.size(); i++) {
			String ciudad = ciudades.get(i).getIdCiudad() + "-" + ciudades.get(i).getNombre();
			nombresCiudades[i + 1] = ciudad;
		}
		this.ventanaCargarViaje.getComboBoxCiudadOrigen().setModel(new DefaultComboBoxModel(nombresCiudades));
		this.ventanaEditarViaje.getComboBoxCiudadOrigen().setModel(new DefaultComboBoxModel(nombresCiudades));

	}

	private void llenarComboBoxCiudadesDestino(int idProvincia) {
		List<CiudadDTO> ciudades = null;
		ciudades = modeloCiudad.obtenerCiudadPorIdProvincia(idProvincia);
		String[] nombresCiudades = new String[ciudades.size() + 1];
		nombresCiudades[0] = "-Seleccione ciudad-";
		for (int i = 0; i < ciudades.size(); i++) {
			String ciudad = ciudades.get(i).getIdCiudad() + "-" + ciudades.get(i).getNombre();
			nombresCiudades[i + 1] = ciudad;
		}
		this.ventanaCargarViaje.getComboBoxCiudadDestino().setModel(new DefaultComboBoxModel(nombresCiudades));
		this.ventanaEditarViaje.getComboBoxCiudadDestino().setModel(new DefaultComboBoxModel(nombresCiudades));
	}

	/* < / ORIGEN-DESTINOS > */

	/* < VALIDACION DE ALTA VIAJES > */
	private boolean viajeValido() {
		boolean ret = true;
		if (!intValido(this.ventanaCargarViaje.getTextCapacidad().getText())) {
			this.msjErrorOrigenDestino.add("CAPACIDAD");
			ret = ret && false;
		}
		if (!intValido(this.ventanaCargarViaje.getTextHorasEstimadas().getText())) {
			this.msjErrorOrigenDestino.add("HORAS ESTIMADAS");
			ret = ret && false;
		}
		if (!precioValido()) {
			this.msjErrorOrigenDestino.add("PRECIO");
			ret = ret && false;
		}
		if (!origenDestinoValido()) {
			this.msjErrorOrigenDestino.add("ORIGEN-DESTINO");
			ret = ret && false;
		}
		if (!fechaOrigenValida()) {
			this.msjErrorOrigenDestino.add("FECHA DE SALIDA");
			ret = ret && false;
		}
		// if (!ret) {
		// this.ventanaCargarViaje.getBtnCrearViaje().setEnabled(false);
		// }
		// else {
		// this.ventanaCargarViaje.getBtnCrearViaje().setEnabled(true);
		// }
		return ret;
	}

	private boolean intValido(String s) {
		if (entradaValida(s, Pattern.compile("[0-9]+")))
			return true;
		return false;
	}

	private boolean precioValido() {
		if (entradaValida(this.ventanaCargarViaje.getTextPrecioViaje().getText(),
				Pattern.compile("[0-9]+(\\,{1}[0-9]+)?")))
			return true;
		return false;
	}

	private boolean origenDestinoValido() {
		if (this.ventanaCargarViaje.getComboBoxCiudadOrigen().getSelectedItem().equals("-Seleccione ciudad-")
				|| this.ventanaCargarViaje.getComboBoxCiudadDestino().getSelectedItem().equals("-Seleccione ciudad-"))
			return false;
		if (this.ventanaCargarViaje.getComboBoxCiudadOrigen().getSelectedIndex() == -1
				|| this.ventanaCargarViaje.getComboBoxCiudadDestino().getSelectedIndex() == -1)
			return false;
		String ciudadOrigenElegida = quitarIdDeCombo(
				this.ventanaCargarViaje.getComboBoxCiudadOrigen().getSelectedItem().toString());
		String ciudadDestinoElegida = quitarIdDeCombo(
				this.ventanaCargarViaje.getComboBoxCiudadDestino().getSelectedItem().toString());
		if (!(ciudadOrigenElegida.equals(ciudadDestinoElegida)))
			return true;
		return false;
	}

	private boolean fechaOrigenValida() {
		// calcular fecha actual:
		String hoy = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now());

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		try {
			date = sdf1.parse(hoy);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());

		if (this.ventanaCargarViaje.getDateChooserFechaOrigen().getDate() == null)
			return false;
		if (this.ventanaCargarViaje.getDateChooserFechaOrigen().getDate().before(sqlStartDate))
			return false;
		return true;
	}

	private boolean entradaValida(String text, Pattern pattern) {
		if (text.matches(pattern.toString())) {
			return true;
		}
		return false;
	}

	private boolean mostrarDatosViaje() {
		this.msjErrorOrigenDestino = new ArrayList<String>();
		this.ventanaCargarViaje.getLblErrores().setText("");
		if (viajeValido()) {
			this.fechaSalida = convertUtilToSql(this.ventanaCargarViaje.getDateChooserFechaOrigen().getDate());
			this.horarioSalida = this.ventanaCargarViaje.getComboBoxHorarioSalida().getSelectedItem().toString();
			this.horasEstimadas = Integer.parseInt(this.ventanaCargarViaje.getTextHorasEstimadas().getText());

			this.fechaLlegada = calcularFechaLlegada(this.fechaSalida, this.horarioSalida, this.horasEstimadas);
			this.ventanaCargarViaje.getTxtFechaDestino().setText(this.fechaLlegada.toString());
			this.ventanaCargarViaje.getBtnCrearViaje().setEnabled(true);
			return true;
		} else {
			String mensaje = ("DATOS INVÁLIDOS: ");
			mensaje += this.msjErrorOrigenDestino.get(0);
			for (int i = 1; i < this.msjErrorOrigenDestino.size(); i++) {
				mensaje += ", " + this.msjErrorOrigenDestino.get(i);
			}
			mensaje += ".";
			this.ventanaCargarViaje.getLblErrores().setText(mensaje);
			return false;
		}
	}

	private java.sql.Date convertUtilToSql(java.util.Date uDate) {
		java.sql.Date sDate = new java.sql.Date(uDate.getTime());
		return sDate;
	}

	public Date sumarRestarHorasFecha(Date fecha, int horas) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); // Configuramos la fecha que se recibe
		calendar.add(Calendar.HOUR, horas); // numero de horas a aÃ±adir, o
											// restar en caso de horas<0
		return convertUtilToSql(calendar.getTime()); // Devuelve el objeto Date
														// con las nuevas horas
														// aÃ±adidas

	}

	private Date calcularFechaLlegada(Date fecha, String horario, Integer horaAsumar) {
		Date ret = null;
		String diaRet = "";
		String mesRet = "";
		String añoRet = "";

		String[] fechaStr = fecha.toString().split("-");
		String año = fechaStr[0];
		String mes = fechaStr[1];
		String dia = fechaStr[2];

		if (mes.equals("01") || mes.equals("03") || mes.equals("05") || mes.equals("07") || mes.equals("08")
				|| mes.equals("10") || mes.equals("12")) { // ENERO,MARZO,MAYO,JULIO,AGOSTO,OCTUBRE,DICIEMBRE
			// tiene 31 dias
			if (dia.equals("31")) {
				if (horaAsumar + eliminarPuntosEnFecha(horario) >= 24) {
					diaRet = "1";
					if (mes.equals("12")) {
						mesRet = "1";
						añoRet = (Integer.parseInt(año) + 1) + "";
					} else {
						mesRet = (Integer.parseInt(mes) + 1) + "";
						añoRet = año;
					}
				} else {// no son mas de 24 hs, es mismo dia, mes y año
					diaRet = dia;
					mesRet = mes;
					añoRet = año;
				}
			} else { // no cambia mes
				if (horaAsumar + eliminarPuntosEnFecha(horario) >= 24) {
					diaRet = (Integer.parseInt(dia) + 1) + "";
					mesRet = mes;
					añoRet = año;
				} else {
					diaRet = dia;
					mesRet = mes;
					añoRet = año;
				}
			}
		}
		if (mes.equals("04") || mes.equals("06") || mes.equals("09") || mes.equals("11")) {
			// tiene 30 dias
			if (dia.equals("30")) {
				if (horaAsumar + eliminarPuntosEnFecha(horario) >= 24) {
					diaRet = "1";
					mesRet = (Integer.parseInt(mes) + 1) + "";
					;
					añoRet = año;
				} else {// no son mas de 24 hs, es mismo dia, mes y año
					diaRet = dia;
					mesRet = mes;
					añoRet = año;
				}
			} else { // no cambia mes
				if (horaAsumar + eliminarPuntosEnFecha(horario) >= 24) {
					diaRet = (Integer.parseInt(dia) + 1) + "";
					;
					mesRet = mes;
					añoRet = año;
				} else {
					diaRet = dia;
					mesRet = mes;
					añoRet = año;
				}
			}
		}
		if (mes.equals("02")) {
			if ((Integer.parseInt(año) % 4 == 0)
					&& ((Integer.parseInt(año) % 100 != 0) || (Integer.parseInt(año) % 400 == 0))) {
				// año bisiesto, tiene 29 dias
				if (dia.equals("29")) {
					if (horaAsumar + eliminarPuntosEnFecha(horario) >= 24) {
						diaRet = "1";
						mesRet = (Integer.parseInt(mes) + 1) + "";
						;
						añoRet = año;
					} else {// no son mas de 24 hs, es mismo dia, mes y año
						diaRet = dia;
						mesRet = mes;
						añoRet = año;
					}
				} else { // no cambia mes
					if (horaAsumar + eliminarPuntosEnFecha(horario) >= 24) {
						diaRet = (Integer.parseInt(dia) + 1) + "";
						;
						mesRet = mes;
						añoRet = año;
					} else {
						diaRet = dia;
						mesRet = mes;
						añoRet = año;
					}
				}

			} else {
				// año no bisiesto, tiene 28 dias
				if (dia.equals("28")) {
					if (horaAsumar + eliminarPuntosEnFecha(horario) >= 24) {
						diaRet = "1";
						mesRet = (Integer.parseInt(mes) + 1) + "";
						añoRet = año;
					} else {// no son mas de 24 hs, es mismo dia, mes y año
						diaRet = dia;
						mesRet = mes;
						añoRet = año;
					}
				} else { // no cambia mes
					if (horaAsumar + eliminarPuntosEnFecha(horario) >= 24) {
						diaRet = (Integer.parseInt(dia) + 1) + "";
						mesRet = mes;
						añoRet = año;
					} else {
						diaRet = dia;
						mesRet = mes;
						añoRet = año;
					}
				}
			}
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			ret = convertUtilToSql(sdf.parse(añoRet + "-" + mesRet + "-" + diaRet));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return ret;

	}

	private int eliminarPuntosEnFecha(String fecha) {
		String ret = "";
		for (int n = 0; n < fecha.length(); n++) {
			char c = fecha.charAt(n);
			if (c != ':') {
				ret += c;
			} else {
				return Integer.parseInt(ret);
			}
		}
		return Integer.parseInt(ret);
	}

	private void limpiarVentana() {
		this.ventanaCargarViaje.getTextCapacidad().setText("");
		this.ventanaCargarViaje.getTextHorasEstimadas().setText("");
		this.ventanaCargarViaje.getTextPrecioViaje().setText("");
		this.ventanaCargarViaje.getTxtFechaDestino().setText("");
		this.ventanaCargarViaje.getComboBoxCiudadDestino().setSelectedItem("-Seleccione ciudad-");
		this.ventanaCargarViaje.getComboBoxCiudadOrigen().setSelectedItem("-Seleccione ciudad-");
		this.ventanaCargarViaje.getComboBoxCiudadOrigen().setEnabled(false);
		this.ventanaCargarViaje.getComboBoxCiudadDestino().setEnabled(false);
		this.ventanaCargarViaje.getComboBoxProvinciaDestino().setSelectedItem("-Seleccione provincia-");
		this.ventanaCargarViaje.getComboBoxProvinciaOrigen().setSelectedItem("-Seleccione provincia-");
		this.ventanaCargarViaje.getComboBoxProvinciaDestino().setEnabled(false);
		this.ventanaCargarViaje.getComboBoxProvinciaOrigen().setEnabled(false);
		this.ventanaCargarViaje.getComboBoxPaisDestino().setSelectedItem("-Seleccione pais-");
		this.ventanaCargarViaje.getComboBoxPaisOrigen().setSelectedItem("-Seleccione pais-");
		this.ventanaCargarViaje.getComboBoxHorarioSalida().setSelectedIndex(0);
		this.ventanaCargarViaje.getComboBoxTransporte().setSelectedIndex(0);
	}

	private void darAltaViaje(ActionEvent aV) {// throws Exception {
		// mostrarDatosViaje();
		if (mostrarDatosViaje()) {

			this.fechaSalida = convertUtilToSql(this.ventanaCargarViaje.getDateChooserFechaOrigen().getDate());
			this.horarioSalida = this.ventanaCargarViaje.getComboBoxHorarioSalida().getSelectedItem().toString();

			this.horasEstimadas = Integer.parseInt(this.ventanaCargarViaje.getTextHorasEstimadas().getText());
			this.fechaLlegada = calcularFechaLlegada(this.fechaSalida, this.horarioSalida, this.horasEstimadas);
			System.out.println(ventanaCargarViaje.getTextPrecioViaje().getText());
			System.out.println(new BigDecimal(ventanaCargarViaje.getTextPrecioViaje().getText()));
			this.precioViaje = new BigDecimal(ventanaCargarViaje.getTextPrecioViaje().getText());

			for (TransporteDTO t : modeloTransporte.obtenerTransportes())
				if (t.getNombre().equals(
						quitarIdDeCombo(this.ventanaCargarViaje.getComboBoxTransporte().getSelectedItem().toString())))
					this.transporteSeleccionado = t;

			this.capacidad = Integer.parseInt(this.ventanaCargarViaje.getTextCapacidad().getText());

			for (PaisDTO pa : modeloPais.obtenerPaises())
				if (pa.getNombre().equals(
						quitarIdDeCombo(this.ventanaCargarViaje.getComboBoxPaisOrigen().getSelectedItem().toString())))
					this.paisOrigen = pa;
			for (PaisDTO pa : modeloPais.obtenerPaises())
				if (pa.getNombre().equals(
						quitarIdDeCombo(this.ventanaCargarViaje.getComboBoxPaisDestino().getSelectedItem().toString())))
					this.paisDestino = pa;

			for (ProvinciaDTO pr : modeloProvincia.obtenerProvincias())
				if (pr.getNombre().equals(quitarIdDeCombo(
						this.ventanaCargarViaje.getComboBoxProvinciaOrigen().getSelectedItem().toString())))
					this.provinciaOrigen = pr;
			for (ProvinciaDTO pr : modeloProvincia.obtenerProvincias())
				if (pr.getNombre().equals(quitarIdDeCombo(
						this.ventanaCargarViaje.getComboBoxProvinciaDestino().getSelectedItem().toString())))
					this.provinciaDestino = pr;

			for (CiudadDTO c : modeloCiudad.obtenerCiudades()) {
				if (c.getNombre().equals(quitarIdDeCombo(
						this.ventanaCargarViaje.getComboBoxCiudadOrigen().getSelectedItem().toString())))
					this.ciudadOrigen = c;
			}
			for (CiudadDTO c : modeloCiudad.obtenerCiudades())
				if (c.getNombre().equals(quitarIdDeCombo(
						this.ventanaCargarViaje.getComboBoxCiudadDestino().getSelectedItem().toString())))
					this.ciudadDestino = c;

			// Creo el viaje
			this.viajeSeleccionado = new ViajeDTO(0, this.ciudadOrigen, this.ciudadDestino, this.provinciaOrigen,
					this.provinciaDestino, this.paisOrigen, this.paisDestino, this.fechaSalida, this.fechaLlegada,
					this.horarioSalida, this.horasEstimadas, this.transporteSeleccionado, this.capacidad,
					this.precioViaje, "activo");
			// TODO: BORRAR PRINTS / AGREGAR ESTADO DESDE EL VIAJE
			System.out.print(this.viajeSeleccionado.getCiudadOrigen().getNombre() + ", "
					+ this.viajeSeleccionado.getProvinciaOrigen().getNombre() + ", "
					+ this.viajeSeleccionado.getPaisOrigen().getNombre());
			System.out.print(" a ");
			System.out.println(this.viajeSeleccionado.getCiudadDestino().getNombre() + ", "
					+ this.viajeSeleccionado.getProvinciaDestino().getNombre() + ", "
					+ this.viajeSeleccionado.getPaisDestino().getNombre());

			// Agrego el viaje
			modeloViaje.agregarViaje(this.viajeSeleccionado);
			// llenarViajesEnTabla();
			llenarViajesEnPanelViajes();
			limpiarVentana();
			this.ventanaCargarViaje.setVisible(false);

		}
		// else {
		// String mensaje = ("DATOS INVÁLIDOS: ");
		// mensaje += this.msjErrorOrigenDestino.get(0);
		// for(int i=1; i< this.msjErrorOrigenDestino.size(); i++) {
		// mensaje += ", "+this.msjErrorOrigenDestino.get(i);
		// }
		// mensaje += ".";
		// this.ventanaCargarViaje.getLblErrores().setText(mensaje);
		// }
	}

	/*- - - - - - - -  - - - - - - - < / METODOS DE VIAJE> - - - - - - - - - - - - - - - - --  */
	/*
	 * /////////////////////////////////////////////////////////////////////////
	 * ////////////////////////////
	 */
	/* IMPLEMENTADO BRANCH V3.0 */
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

		if (usuarioLogueado == null) {
			this.ventanaLogin.getLblError().setVisible(true);
			System.out.println("EL USUARIO O CONTRASENA ES INCORRECTO");
		} else {
			System.out.println("SE LOGEO CORRECTAMENTE CON:" + usuarioLogueado.getUsuario() + " "
					+ usuarioLogueado.getContrasena() + " " + usuarioLogueado.getRol().getIdRol());
			if (usuarioLogueado.getRol().getIdRol() == 2) {
				administrativoLogueado = obtenerAdministrativo(usuarioLogueado);
				mostrarVentanaAdministrativo();
			} else {
				if (usuarioLogueado.getRol().getIdRol() == 5) {
					clienteLogueado = obtenerCliente(usuarioLogueado);
					mostrarVentanaCliente();
				} else {
					if (usuarioLogueado.getRol().getIdRol() == 1) {
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

	/* MOSTRAR LA VENTANA PRINCIPAL DEL PARSONAL ADMINISTRATIVO */
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

	private void salirVentanaCliente(ActionEvent bc) {
		this.ventanaCliente.cerrarVentana();
	}

	public void llenarCiudadesEnCargaViajes() {
		llenarComboBoxPaises();
		llenarComboTransporte();
		llenarCombroHorarioSalida();

		// this.ventanaTablaViajes.getModelViajes().setRowCount(0);
		// this.ventanaTablaViajes.getModelViajes().setColumnCount(0);
		// this.ventanaTablaViajes.getModelViajes().setColumnIdentifiers(this.ventanaTablaViajes.getNombreColumnas());
		//
		//
		// viajes_en_tabla = (ArrayList<ViajeDTO>) modeloViaje.obtenerViajes();
		// for(int i=0; i< viajes_en_tabla.size();i++){
		// Object[] fila = {
		// viajes_en_tabla.get(i).getCiudadOrigen().getNombre(),
		// viajes_en_tabla.get(i).getCiudadDestino().getNombre(),
		// mapper.parseToString(viajes_en_tabla.get(i).getFechaSalida()),
		// mapper.parseToString(viajes_en_tabla.get(i).getFechaLlegada()),
		// viajes_en_tabla.get(i).getPrecio(),
		// viajes_en_tabla.get(i).getHoraSalida(),
		// viajes_en_tabla.get(i).getCapacidad(),
		// viajes_en_tabla.get(i).getTransporte().getNombre(),
		// viajes_en_tabla.get(i).getPrecio()
		// };
		// this.ventanaTablaViajes.getModelViajes().addRow(fila);
		// }
		// this.ventanaTablaViajes.setVisible(true);
		// this.ventanaTablaViajes.getBtnConfirmar().setVisible(false);
		// this.ventanaTablaViajes.getBtnAtras().setVisible(false);

	}

	public void llenarViajesEnPanelViajes() {
		this.ventanaAdministrador.getPanelViajes().getActivos().setSelected(false);
		this.ventanaAdministrador.getPanelViajes().getInactivos().setSelected(false);
		this.ventanaAdministrador.getPanelViajes().getCheckBoxAll().setSelected(true);

		this.panelViajes = this.ventanaAdministrador.getPanelViajes();
		this.panelViajes.getModelViajes().setRowCount(0);
		this.panelViajes.getModelViajes().setColumnCount(0);
		this.panelViajes.getModelViajes().setColumnIdentifiers(this.panelViajes.getNombreColumnasViajes());

		this.viajes_en_tabla = (ArrayList<ViajeDTO>) modeloViaje.obtenerViajes(); 	
		for (int i = 0; i < viajes_en_tabla.size(); i++) {
			Object[] fila = { viajes_en_tabla.get(i).getCiudadOrigen().getNombre(),
					viajes_en_tabla.get(i).getCiudadDestino().getNombre(),
					mapper.parseToString(viajes_en_tabla.get(i).getFechaSalida()),
					mapper.parseToString(viajes_en_tabla.get(i).getFechaLlegada()),
					viajes_en_tabla.get(i).getHoraSalida(), viajes_en_tabla.get(i).getHorasEstimadas(),
					viajes_en_tabla.get(i).getCapacidad(), viajes_en_tabla.get(i).getTransporte().getNombre(),
					"$ " + viajes_en_tabla.get(i).getPrecio(), viajes_en_tabla.get(i).getEstado() };
			this.panelViajes.getModelViajes().addRow(fila);
		}

		// this.panelViajes.getBtnConfirmar().setVisible(false);

		// this.panelViajes.getBtnAtras().setVisible(false);

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

	
	private void llenarTablaClientes() {
		this.vista.getModelClientes().setRowCount(0); // Para vaciar la tabla
		this.vista.getModelClientes().setColumnCount(0);
		this.vista.getModelClientes().setColumnIdentifiers(this.vista.getNombreColumnasClientes());

		this.clientes_en_tabla = cliente.obtenerClientes();

		for (int i = 0; i < this.clientes_en_tabla.size(); i++) {
			Object[] fila = { this.clientes_en_tabla.get(i).getNombre(), this.clientes_en_tabla.get(i).getApellido(),
					this.clientes_en_tabla.get(i).getDni(), this.clientes_en_tabla.get(i).getFechaNacimiento(),
					this.clientes_en_tabla.get(i).getMedioContacto().getTelefonoFijo(),
					this.clientes_en_tabla.get(i).getMedioContacto().getTelefonoCelular(),
					this.clientes_en_tabla.get(i).getMedioContacto().getEmail() };
			this.vista.getModelClientes().addRow(fila);
		}
	}

	/* LABEL CANTIDAD DE PASAJEROS */

	private void llenarComboRangoHorariosEnCargarViaje() {
		String[] horarios = { "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00",
				"12:00" };
		this.ventanaCargarViaje.getComboBoxHorarioSalida().setModel(new DefaultComboBoxModel(horarios));
	}

	private void llenarComboCiudadesEnCargarViaje() {
		/*
		 * CARGAMOS LOS VALORES DE LAS CIUDADES EN LA VENTANA DE DAR ALTA VIAJE
		 */
		List<CiudadDTO> ciudades = new CiudadDAOSQL().readAll();
		String[] nombresCiudades = new String[ciudades.size()];
		for (int i = 0; i < ciudades.size(); i++) {
			nombresCiudades[i] = ciudades.get(i).getNombre();
		}
		/*
		 * LUEGO VER QUE NO SE PUEDA SELECCIONAR LA MISMA CIUDAD COMO ORIGEN Y
		 * DESTINO AL MISMO TIEMPO
		 */
		this.ventanaCargarViaje.getComboBoxCiudadOrigen().setModel(new DefaultComboBoxModel(nombresCiudades));
		this.ventanaCargarViaje.getComboBoxCiudadDestino().setModel(new DefaultComboBoxModel(nombresCiudades));
	}

	private void llenarComboTransporte() {
		ArrayList<TransporteDTO> transportes = (ArrayList<TransporteDTO>) new Transporte(daoSqlFactory)
				.obtenerTransportes();
		String[] nombresTransportes = new String[transportes.size()];
		for (int i = 0; i < transportes.size(); i++)
			nombresTransportes[i] = transportes.get(i).getNombre();

		this.ventanaCargarViaje.getComboBoxTransporte().setModel(new DefaultComboBoxModel(nombresTransportes));
		this.ventanaEditarViaje.getComboBoxTransporte().setModel(new DefaultComboBoxModel(nombresTransportes));
	}


	private void llenarCombroHorarioSalida() {
		String[] horarios = { 
				"00:00","00:30","01:00","01:30","02:00","02:30","03:00","03:30","04:00","04:30","05:00","05:30",
				"06:00","06:30","07:00","07:30","08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30",
				"12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30",
				"18:00","18:30","19:00","19:30","20:00","20:30","21:00","21:30","22:00","22:30","23:00","23:30"};
		this.ventanaCargarViaje.getComboBoxHorarioSalida().setModel(new DefaultComboBoxModel(horarios));
		this.ventanaEditarViaje.getComboBoxHorarioSalida().setModel(new DefaultComboBoxModel(horarios));
	}

	public void mostrarVentanaCargarViaje() {
		this.ventanaCargarViaje.setVisible(true);
	}

	private void llenarViajesEnTabla() {
		llenarCiudadesEnCargaViajes();

	}

	private void seleccionarViaje(ActionEvent sV) {

		this.ventanaTablaViajes.setVisible(false);
		this.ventanaReserva.setVisible(true);

		int filaSeleccionada = this.ventanaTablaViajes.getTablaViajes().getSelectedRow();
		viajeSeleccionado = viajes_en_tabla.get(filaSeleccionada);
	}

	private HorarioReservaDTO obtenerHorarioElegidoPorCliente(String horarioComboBox) {
		HorarioReservaDAOSQL tDAO = new HorarioReservaDAOSQL();
		HorarioReservaDTO ret = null;
		ArrayList<HorarioReservaDTO> rangoshorarios = (ArrayList<HorarioReservaDTO>) tDAO.readAll();
		for (HorarioReservaDTO h : rangoshorarios) {
			if (h.getHoraInicio().equals(horarioComboBox.substring(0, 5))
					&& (h.getHoraFin().equals(horarioComboBox.substring(8, 13)))) {
				ret = h;
			}
		}
		return ret;
	}
	/*- - - - - - - -  - - - - - - - METODOS DE RESERVA - - - - - - - - - - - - - - - - --  */

	private void llenarValoresEnReserva() {
		llenarComboRangoDeHorarioEnReserva();

		// llenarComboTransporteEnReserva();

		/* CAMBIAR LA SOLICITUD AL DAOSQL POR LA SOLICITUD AL MODELO */
		/* PREGUNTAR A SOL POR QUE NO FUNCIONA !! -.-" */
		llenarListModelViajesEnReserva();

	}

	private void llenarListModelViajesEnReserva() {

		this.ventanaReserva.getListModelViajesDisponibles().removeAllElements();

		List<ViajeDTO> viajesDisponibles = new ViajeDAOSQL().readAll();
		System.out.println("Se pidieron" + viajesDisponibles.size() + " valores en la db de viajes");
		String[] viajes = new String[viajesDisponibles.size()];

		for (int i = 0; i < viajesDisponibles.size(); i++) {
			CiudadDTO origen = viajesDisponibles.get(i).getCiudadOrigen();
			CiudadDTO destino = viajesDisponibles.get(i).getCiudadDestino();

			viajes[i] = origen.getNombre() + " - " + destino.getNombre();
			this.ventanaReserva.getListModelViajesDisponibles()
					.addElement(origen.getNombre() + " - " + destino.getNombre());
		}
	}

	// private void llenarComboTransporteEnReserva() {
	// List<TransporteDTO> transportesDTO = new TransporteDAOSQL().readAll();
	// String[] transoportes = new String[transportesDTO.size()];
	// for(int i=0; i<transportesDTO.size();i++){
	// String rango = transportesDTO.get(i).getNombreTransporte();
	// transoportes [i] = rango;
	// }
	// this.ventanaReserva.getComboBoxTransporte().setModel(new
	// DefaultComboBoxModel(transoportes));
	// }

	private void llenarComboRangoDeHorarioEnReserva() {
		List<HorarioReservaDTO> horarios = new HorarioReservaDAOSQL().readAll();
		String[] rangoHorarios = new String[horarios.size()];
		for (int i = 0; i < horarios.size(); i++) {
			String rango = horarios.get(i).getHoraInicio() + " - " + horarios.get(i).getHoraFin();
			rangoHorarios[i] = rango;
		}
		this.ventanaReserva.getComboBoxRangoHorario().setModel(new DefaultComboBoxModel(rangoHorarios));
	}

	private void mostrarVentanaReserva() {
		// llenarValoresEnReserva();
		this.ventanaReserva.setVisible(true);
	}

	private void mostrarViajesDisponibles(ActionEvent iV) {
		this.ventanaTablaViajes.getModelViajes().setRowCount(0);
		this.ventanaTablaViajes.getModelViajes().setColumnCount(0);
		this.ventanaTablaViajes.getModelViajes().setColumnIdentifiers(this.ventanaTablaViajes.getNombreColumnas());
		ArrayList<ViajeDTO> viajes_en_tabla = (ArrayList<ViajeDTO>) new ViajeDAOSQL().readAll();
		for (int i = 0; i < viajes_en_tabla.size(); i++) {
			Object[] fila = { viajes_en_tabla.get(i).getCiudadOrigen().getNombre(),
					viajes_en_tabla.get(i).getCiudadDestino().getNombre(),
					mapper.parseToString(viajes_en_tabla.get(i).getFechaSalida()),
					mapper.parseToString(viajes_en_tabla.get(i).getFechaLlegada()), viajes_en_tabla.get(i).getPrecio(),
					viajes_en_tabla.get(i).getHoraSalida() };
			this.ventanaTablaViajes.getModelViajes().addRow(fila);
		}

		this.ventanaTablaViajes.setVisible(true);
		this.ventanaTablaViajes.getBtnConfirmar().setVisible(false);
		this.ventanaTablaViajes.getBtnAtras().setVisible(false);
	}

	/*
	 * - - - - - - - - - - - - - - -- METODOS DE PAGOS - - - - - - - - - - - - -
	 * - - - - -
	 */

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

		this.transporteSeleccionado = obtenerTransporteElegidoPorCliente(
				this.ventanaReserva.getComboBoxTransporte().getSelectedItem().toString());
		this.ventanaFormaDePagos.getLblMontoaPagar().setText("$ " + calcularMontoDePasaje().toString());

	}

	private BigDecimal calcularMontoDePasaje() {
		BigDecimal Valor1 = this.viajeSeleccionado.getPrecio();
		// BigDecimal Valor2 = this.transporteSeleccionado.getPrecioBase();
		// Valor2 = Valor2.add(Valor1);
		// totalaPagar = Valor2;

		return totalaPagar.multiply(new BigDecimal(pasajerosEnEstaReserva.size()));
	}

	private TransporteDTO obtenerTransporteElegidoPorCliente(String transporteComboBox) {
		TransporteDAOSQL tDAO = new TransporteDAOSQL();
		TransporteDTO ret = null;
		ArrayList<TransporteDTO> transportes = (ArrayList<TransporteDTO>) tDAO.readAll();
		for (TransporteDTO t : transportes)
			// if(t.getNombreTransporte().equals(transporteComboBox))
			ret = t;
		return ret;
	}

	// private void generarPasajeTarjeta(ActionEvent rP) {
	// String importeIngresado =
	// ventanaPagoTarjeta.getTextImporteIngresado().toString();
	// }
	//

	private void redirigirSegunItemSeleccionado(String itemSeleccionado) {
		if (itemSeleccionado.equals("TARJETA")) {
			// ventanaPagoTarjeta.mostrarVentana(true);
			// ventanaFormaDePagos.mostrarVentana(false);
		} else if (itemSeleccionado.equals("EFECTIVO")) {
			// ventanaPagoEfectivo.mostrarVentana(true);
			ventanaFormaDePagos.mostrarVentana(false);
		}
	}

	// private void modificarViajeDePasaje(PasajeDTO pasaje, Date
	// nuevaHorarioSalida){
	// ViajeDTO nuevoViaje = pasaje.getViaje();
	// nuevoViaje.setFechaSalida((java.sql.Date) nuevaHorarioSalida);
	// pasaje.setViaje(nuevoViaje);
	// }
	//

	public static void main(String[] args) throws Exception {
		Vista vista = new Vista();
		// Controlador controlador = new Controlador(vista);
		// controlador.inicializar();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public void mostrarPanelDeViajes() {
		llenarViajesEnPanelViajes();
		this.ventanaAdministrador.getPanelViajes().mostrarPanelViajes(true);

	}

	public void mostrarPanelEditarViaje() {
		this.ventanaAdministrador.getPanelViajes().mostrarPanelViajes(true);
		int filaSeleccionada = this.ventanaAdministrador.getPanelViajes().getTablaViajes().getSelectedRow();
		if (filaSeleccionada != -1) {
			this.viajeSeleccionado = viajes_en_tabla.get(filaSeleccionada);
			if(this.viajeSeleccionado.getEstado().equals("activo"))
				llenarValoresEnVentanaConViajeEditar();	
			else
				JOptionPane.showMessageDialog(null, "No se puede editar un viaje inactivo", "Mensaje", JOptionPane.ERROR_MESSAGE);		
		}
		else {
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void llenarValoresEnVentanaConViajeEditar() {
		llenarComboBoxPaises();
		llenarComboTransporte();
		llenarCombroHorarioSalida();

		this.ventanaEditarViaje.getComboBoxPaisOrigen()
				.setSelectedItem(this.viajeSeleccionado.getPaisOrigen().getIdPais() + "-"
						+ this.viajeSeleccionado.getPaisOrigen().getNombre()); // LOS
																				// DATOS
																				// DEL
																				// VIAJE
		this.ventanaEditarViaje.getComboBoxPaisDestino()
				.setSelectedItem(this.viajeSeleccionado.getPaisDestino().getIdPais() + "-"
						+ this.viajeSeleccionado.getPaisDestino().getNombre());

		this.ventanaEditarViaje.getComboBoxPaisOrigen().setEnabled(false);
		this.ventanaEditarViaje.getComboBoxPaisDestino().setEnabled(false);

		llenarComboBoxProvinciasOrigen(viajeSeleccionado.getPaisOrigen().getIdPais());
		llenarComboBoxProvinciasDestino(viajeSeleccionado.getPaisDestino().getIdPais());

		this.ventanaEditarViaje.getComboBoxProvinciaOrigen()
				.setSelectedItem(this.viajeSeleccionado.getProvinciaOrigen().getIdProvincia() + "-"
						+ this.viajeSeleccionado.getProvinciaOrigen().getNombre());
		this.ventanaEditarViaje.getComboBoxProvinciaDestino()
				.setSelectedItem(this.viajeSeleccionado.getProvinciaDestino().getIdProvincia() + "-"
						+ this.viajeSeleccionado.getProvinciaDestino().getNombre());

		llenarComboBoxCiudadesOrigen(viajeSeleccionado.getProvinciaOrigen().getIdProvincia());
		llenarComboBoxCiudadesDestino(viajeSeleccionado.getProvinciaDestino().getIdProvincia());

		this.ventanaEditarViaje.getComboBoxCiudadOrigen()
				.setSelectedItem(this.viajeSeleccionado.getCiudadOrigen().getIdCiudad() + "-"
						+ this.viajeSeleccionado.getCiudadOrigen().getNombre());
		this.ventanaEditarViaje.getComboBoxCiudadDestino()
				.setSelectedItem(this.viajeSeleccionado.getCiudadDestino().getIdCiudad() + "-"
						+ this.viajeSeleccionado.getCiudadDestino().getNombre());

		this.ventanaEditarViaje.getTextHorasEstimadas()
				.setText(String.valueOf(this.viajeSeleccionado.getHorasEstimadas()));
		this.ventanaEditarViaje.getTextHorasEstimadas().setEnabled(false);

		this.ventanaEditarViaje.getTextPrecioViaje().setText(String.valueOf(this.viajeSeleccionado.getPrecio()));
		this.ventanaEditarViaje.getTextPrecioViaje().setEnabled(false);

		this.ventanaEditarViaje.getComboBoxTransporte()
				.setSelectedItem(this.viajeSeleccionado.getTransporte().getNombre());
		this.ventanaEditarViaje.getComboBoxTransporte().setEnabled(false);

		this.ventanaEditarViaje.getTextCapacidad().setText(String.valueOf(this.viajeSeleccionado.getCapacidad()));
		this.ventanaEditarViaje.getTextCapacidad().setEnabled(false);

		this.ventanaEditarViaje.getDateChooserFechaOrigen().setDate(this.viajeSeleccionado.getFechaSalida());
		this.ventanaEditarViaje.getDateChooserFechaOrigen().setEnabled(false);

		this.ventanaEditarViaje.getTextFechaDestino()
				.setText(mapper.parseToString(this.viajeSeleccionado.getFechaLlegada()));

		this.ventanaEditarViaje.getComboBoxHorarioSalida().setSelectedItem(this.viajeSeleccionado.getHoraSalida());

		this.ventanaEditarViaje.getComboBoxEstados().setSelectedItem(this.viajeSeleccionado.getEstado());
		this.ventanaEditarViaje.setVisible(true);
	}

	protected String obtenerFecha(String string) {
		String[] aux = string.split(" ");
		String ret = "";
		for (int i = 0; i < aux.length; i++) {

			if (i == 5) // año
				ret = aux[5] + ret;

			if (i == 2)
				ret = ret + aux[2]; // dia;

			if (i == 1) {
				if (aux[1].equals("Jan")) // mes
					ret += "01";
				if (aux[1].equals("Feb"))
					ret += "02";
				if (aux[1].equals("Mar"))
					ret += "03";
				if (aux[1].equals("Apr"))
					ret += "04";
				if (aux[1].equals("May"))
					ret += "05";
				if (aux[1].equals("Jun"))
					ret += "06";
				if (aux[1].equals("Jul"))
					ret += "07";
				if (aux[1].equals("Aug"))
					ret += "08";
				if (aux[1].equals("Sep"))
					ret += "09";
				if (aux[1].equals("Oct"))
					ret += "10";
				if (aux[1].equals("Nov"))
					ret += "11";
				if (aux[1].equals("Dec"))
					ret += "12";
			}
		}
		return ret;
	}

	private void llenarViajesEnPanelViajes(List<ViajeDTO> obtenerBetween) {
		this.ventanaAdministrador.getPanelViajes().getActivos().setSelected(false);
		this.ventanaAdministrador.getPanelViajes().getInactivos().setSelected(false);
		this.ventanaAdministrador.getPanelViajes().getCheckBoxAll().setSelected(true);

		this.panelViajes = this.ventanaAdministrador.getPanelViajes();
		this.panelViajes.getModelViajes().setRowCount(0);
		this.panelViajes.getModelViajes().setColumnCount(0);
		this.panelViajes.getModelViajes().setColumnIdentifiers(this.panelViajes.getNombreColumnasViajes());

		for (int i = 0; i < obtenerBetween.size(); i++) {
			Object[] fila = { obtenerBetween.get(i).getCiudadOrigen().getNombre(),
					obtenerBetween.get(i).getCiudadDestino().getNombre(),
					mapper.parseToString(obtenerBetween.get(i).getFechaSalida()),
					mapper.parseToString(obtenerBetween.get(i).getFechaLlegada()),
					obtenerBetween.get(i).getHoraSalida(), obtenerBetween.get(i).getHorasEstimadas(),
					obtenerBetween.get(i).getCapacidad(), obtenerBetween.get(i).getTransporte().getNombre(),
					"$ " + obtenerBetween.get(i).getPrecio(), obtenerBetween.get(i).getEstado() };
			this.panelViajes.getModelViajes().addRow(fila);
		}
	}
	
	public boolean esUnNumero(String s) {
		if (s.length()==0)
			return false;
		
		for(int i=0; i<s.length(); i++)
			if(!Character.isDigit(s.charAt(i)))
				return false;
		return true;
	}

//	public void llenarComboPrecios() {
//		String[] precioDesde = { "0", "300", "500", "1000", "1300", "1500", "2000", "2500", "3000", "3500", "5000",
//				"7000", "8000", "10000", "13000", "15000", "20000", "25000", "30000", "35000", "50000" };
//		String[] precioHasta = { "0", "300", "500", "1000", "1300", "1500", "2000", "2500", "3000", "3500", "5000",
//				"7000", "8000", "10000", "13000", "15000", "20000", "25000", "30000", "35000", "50000", "60000" };
//		this.ventanaAdministrador.getPanelViajes().getComboBoxPrecioDesde()
//				.setModel(new DefaultComboBoxModel<String>(precioDesde));
//		this.ventanaAdministrador.getPanelViajes().getComboBoxPrecioHasta().setSelectedIndex(this.ventanaAdministrador.getPanelViajes().getComboBoxPrecioHasta().getItemCount()-1);
//		this.ventanaAdministrador.getPanelViajes().getComboBoxPrecioHasta()
//				.setModel(new DefaultComboBoxModel<String>(precioHasta));
//	}

}
