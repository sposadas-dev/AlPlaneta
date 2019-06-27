package presentacion.controlador;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import correo.EnvioDeCorreo;
import dto.AdministrativoDTO;
import dto.ClienteDTO;
import dto.EventoDTO;
import dto.LoginDTO;
import dto.MedioContactoDTO;
import dto.PasajeDTO;
import dto.PromocionDTO;
import dto.RolDTO;
import dto.ViajeDTO;
import generatePDF.GeneratePDF;
import modelo.Cliente;
import modelo.Login;
import modelo.ModeloEvento;
import modelo.ModeloPromocion;
import modelo.ModeloViaje;
import modelo.ModeloViaje_Promocion;
import modelo.Pasaje;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.administrativo.VentanaCambiarContrasena;
import presentacion.vista.administrativo.VentanaEditarCliente;
import presentacion.vista.administrativo.VentanaEditarEvento;
import presentacion.vista.administrativo.VentanaEditarPromocion;
import presentacion.vista.administrativo.VentanaRegistrarCliente;
import presentacion.vista.administrativo.VentanaRegistrarEvento;
import presentacion.vista.administrativo.VentanaRegistrarPromocion;
import presentacion.vista.administrativo.VentanaTablaViajes;
import presentacion.vista.administrativo.VentanaVisualizarClientes;
import presentacion.vista.administrativo.VentanaVisualizarPasaje;
import presentacion.vista.administrativo.VistaAdministrativo;
import recursos.Mapper;

public class ControladorAdministrativo implements ActionListener {

	private VistaAdministrativo vista;
	private EnvioDeCorreo envioCorreo;		
	private Mapper mapper;
	private GeneratePDF pdf;
	private Pasaje modeloPasaje;
	private VentanaTablaViajes ventanaTablaViajes;
	private VentanaRegistrarCliente ventanaCliente;
	private VentanaRegistrarEvento ventanaEvento;
	private VentanaRegistrarPromocion ventanaPromocion;
	private VentanaEditarPromocion ventanaEditarPromocion;
	private VentanaEditarEvento ventanaEditarEvento;
	private VentanaVisualizarClientes ventanaVisualizarCliente;
	private VentanaRegistrarCliente ventanaRegistrarCliente;
	private VentanaEditarCliente ventanaEditarCliente;
	private VentanaVisualizarPasaje ventanaVisualizarPasaje;
	private VentanaCambiarContrasena ventanaCambiarContrasenia;
	private AdministrativoDTO administrativoLogueado;
	private List<ClienteDTO> clientes_en_tabla;
	private List<PasajeDTO> pasajes_en_tabla;
	private List<PromocionDTO> promociones_en_tabla;

	private List<ClienteDTO> clientes_aux;
	private List<PasajeDTO> pasajes_aux;
	
	private List<EventoDTO> eventos_en_tabla;
	private Cliente cliente;
	private Pasaje pasaje;
	private ModeloEvento evento;
	private ControladorPasaje controladorPasaje;
	private ModeloPromocion promocion;
	private ModeloViaje viaje;
	private ModeloViaje_Promocion viaje_promocion;
	private ControladorCliente controladorCliente;
	
	private Login login;
	private int filaSeleccionada;
	private ControladorEvento controladorEvento;
	private ControladorPromocion controladorPromocion;
	private ControladorDatosLogin controladorDatosLogin;
	private String aceptada="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private DefaultTableModel tableModel;
	private StringBuilder cad = new StringBuilder();


	private static ControladorAdministrativo INSTANCE;
	
	public static ControladorAdministrativo getInstance(){
		if(INSTANCE == null)
			return new ControladorAdministrativo();
		else
			return INSTANCE;
	}
	
	public ControladorAdministrativo(VistaAdministrativo vista,AdministrativoDTO administrativoLogueado) {
	
		this.vista = vista;
		this.ventanaCliente = VentanaRegistrarCliente.getInstance();
		this.ventanaEvento = VentanaRegistrarEvento.getInstance(); 
		this.ventanaEditarEvento = VentanaEditarEvento.getInstance();

		this.ventanaVisualizarCliente = VentanaVisualizarClientes.getInstance();
		this.ventanaRegistrarCliente = VentanaRegistrarCliente.getInstance();
		this.ventanaEditarCliente = VentanaEditarCliente.getInstance();
		this.ventanaTablaViajes = VentanaTablaViajes.getInstance();
		this.ventanaVisualizarPasaje = VentanaVisualizarPasaje.getInstance();
		
		this.ventanaPromocion = VentanaRegistrarPromocion.getInstance();
		this.ventanaEditarPromocion = VentanaEditarPromocion.getInstance();
		this.ventanaCambiarContrasenia = VentanaCambiarContrasena.getInstance();
		this.mapper = new Mapper();
		this.pdf = new GeneratePDF();
		this.envioCorreo = new EnvioDeCorreo();
		this.modeloPasaje = new Pasaje(new DAOSQLFactory());
		this.login = new Login(new DAOSQLFactory());
		
		this.vista.getItemRegistrarCliente().addActionListener(ac->mostrarVentanaAgregarCliente(ac));
		this.vista.getItemVisualizarClientes().addActionListener(ac->agregarPanelClientes(ac));
		this.vista.getItemEditarCliente().addActionListener(mve->mostrarVentanaEditarCliente(mve));
		this.vista.getItemActivarCliente().addActionListener(acc->activarCliente(acc));
		this.vista.getItemDesactivarCliente().addActionListener(dc->desactivarCliente(dc));
        this.vista.getItemRestablecerContrasena().addActionListener(r->restablecerContrasena(r));

		this.vista.getItemVisualizarPasajes().addActionListener(ap->mostrarPasajes(ap));
		this.vista.getItemAgregarPasaje().addActionListener(ap->mostrarVentanaAgregarPasaje(ap));
		this.vista.getItemEditarPasaje().addActionListener(ep->mostrarVentanaEditarPasaje(ep));
		this.vista.getItemCancelarPasaje().addActionListener(cp->cancelarPasaje(cp));
		
		this.vista.getItemCambiarContrasenia().addActionListener(dp->mostrarVentanaCambiarContrasenia(dp));
		
		this.vista.getPanelCliente().getActivos().addActionListener(sa->cargarActivos(sa));
		this.vista.getPanelCliente().getInactivos().addActionListener(si->cargarInactivos(si));
		this.vista.getPanelCliente().getBtnAgregar().addActionListener(a->mostrarVentanaAgregarCliente(a));
		this.vista.getPanelCliente().getBtnEditar().addActionListener(a->mostrarVentanaEditarCliente(a));
		
		this.ventanaEditarCliente.getBtnEditar().addActionListener(ec->editarCliente(ec));
		this.ventanaCambiarContrasenia.getBtnAceptar().addActionListener(c->cambiarContrasenia(c));
		this.ventanaCambiarContrasenia.getBtnCancelar().addActionListener(c->salirVentanaCambiarContrasenia(c));
		
		
		this.vista.getPanelCliente().getTxtFiltro().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
					char letra = e.getKeyChar();
					tableModel = (DefaultTableModel) vista.getPanelCliente().getModelClientes();
					TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(tableModel);
					vista.getPanelCliente().getTablaClientes().setRowSorter(tr);
					if (aceptada.indexOf(letra) != -1 || letra == KeyEvent.VK_BACK_SPACE) {
						if (letra == KeyEvent.VK_BACK_SPACE){
							if(cad.length() != 0) {
								cad.deleteCharAt(cad.length()-1);
						        tr.setRowFilter(RowFilter.regexFilter(cad.toString()));
							}
						} else{
							cad.append(String.valueOf(letra));
			    			tr.setRowFilter(RowFilter.regexFilter(cad.toString()));
						}
					}
			}
		});
		
		this.vista.getPanelEvento().getFiltroApellido().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
					char letra = e.getKeyChar();
					tableModel = (DefaultTableModel) vista.getPanelEvento().getModelEventos();
					TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(tableModel);
					vista.getPanelEvento().getTablaEventos().setRowSorter(tr);
					if (aceptada.indexOf(letra) != -1 || letra == KeyEvent.VK_BACK_SPACE) {
						if (letra == KeyEvent.VK_BACK_SPACE){
							if(cad.length() != 0) {
								cad.deleteCharAt(cad.length()-1);
						        tr.setRowFilter(RowFilter.regexFilter(cad.toString()));
							}
						} else{
							cad.append(String.valueOf(letra));
			    			tr.setRowFilter(RowFilter.regexFilter(cad.toString()));
						}
					}
			}
		});
		
		this.vista.getPanelEvento().getFiltroDesde().addPropertyChangeListener( new PropertyChangeListener() {
		    @Override
		    public void propertyChange(PropertyChangeEvent e) {
		    	String desde = obtenerFecha(e.getNewValue().toString());
		    	if(vista.getPanelEvento().getFiltroHasta().getDate() != null){
		    		String hasta = obtenerFecha(vista.getPanelEvento().getFiltroHasta().getDate().toString());
		    		llenarTablaEventos(evento.obtenerBetween(desde, hasta));
		    	}
		    }
		});
		
		this.vista.getPanelEvento().getFiltroHasta().addPropertyChangeListener( new PropertyChangeListener() {
		    @Override
		    public void propertyChange(PropertyChangeEvent e) {
		    	String hasta = obtenerFecha(e.getNewValue().toString());
		    	if(vista.getPanelEvento().getFiltroDesde().getDate() != null){
		    		String desde = obtenerFecha(vista.getPanelEvento().getFiltroDesde().getDate().toString());
		    		llenarTablaEventos(evento.obtenerBetween(desde, hasta));
		    	}
		    }
		});		
		
		this.ventanaTablaViajes.getFiltroDesde().addPropertyChangeListener( new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				String desde = obtenerFecha(e.getNewValue().toString());
				if(ventanaTablaViajes.getFiltroHasta().getDate() != null){
					String hasta = obtenerFecha(ventanaTablaViajes.getFiltroHasta().getDate().toString());
					llenarTablaViajes(viaje.obtenerBetween(desde, hasta));
				}
			}
		});
		
		this.ventanaTablaViajes.getFiltroHasta().addPropertyChangeListener( new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				String hasta = obtenerFecha(e.getNewValue().toString());
				if(ventanaTablaViajes.getFiltroDesde().getDate() != null){
					String desde = obtenerFecha(ventanaTablaViajes.getFiltroDesde().getDate().toString());
					llenarTablaViajes(viaje.obtenerBetween(desde, hasta));
				}
			}
		});
		
		//Precios Viajes
		
		this.ventanaTablaViajes.getTextPrecioDesde().addKeyListener(new KeyAdapter() {
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
					if(esUnNumero(ventanaTablaViajes.getTextPrecioDesde().getText())) {
						if(esUnNumero(ventanaTablaViajes.getTextPrecioHasta().getText())) {
							Integer desde = Integer.parseInt(ventanaTablaViajes.getTextPrecioDesde().getText());
							Integer hasta = Integer.parseInt(ventanaTablaViajes.getTextPrecioHasta().getText());
							llenarTablaViajes(viaje.obtenerBetweenPrecio(desde, hasta));	
						}
					}
					if(ventanaTablaViajes.getTextPrecioDesde().getText().length() == 0) {
						llenarTablaViajes(viaje.obtenerViajes());
					}
				}
			}
		});

		this.ventanaTablaViajes.getTextPrecioHasta().addKeyListener(new KeyAdapter() {
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
					if(esUnNumero(ventanaTablaViajes.getTextPrecioHasta().getText())) {
						if(esUnNumero(ventanaTablaViajes.getTextPrecioDesde().getText())) {
							Integer desde = Integer.parseInt(ventanaTablaViajes.getTextPrecioDesde().getText());
							Integer hasta = Integer.parseInt(ventanaTablaViajes.getTextPrecioHasta().getText());
							llenarTablaViajes(viaje.obtenerBetweenPrecio(desde, hasta));	
						}
					}
					if(ventanaTablaViajes.getTextPrecioHasta().getText().length() == 0) {
						llenarTablaViajes(viaje.obtenerViajes());
					}
				}
			}
		});


		this.vista.getPanelEvento().getComboFiltros().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String filtro = vista.getPanelEvento().getComboFiltros().getSelectedItem().toString();
				if(!filtro.equals("Todos"))
					llenarTablaEventos(filtrarEventoSegun(filtro));		
				else {					
					limpiarFiltrosEvento();
					llenarTablaEventos(evento.obtenerEvento());
				}
			}
		});
		
		this.vista.getPanelEvento().getFiltroNombre().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
					char letra = e.getKeyChar();
					tableModel = (DefaultTableModel) vista.getPanelEvento().getModelEventos();
					TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(tableModel);
					vista.getPanelEvento().getTablaEventos().setRowSorter(tr);
					if (aceptada.indexOf(letra) != -1 || letra == KeyEvent.VK_BACK_SPACE) {
						if (letra == KeyEvent.VK_BACK_SPACE){
							if(cad.length() != 0) {
								cad.deleteCharAt(cad.length()-1);
						        tr.setRowFilter(RowFilter.regexFilter(cad.toString()));
							}
						} else{
							cad.append(String.valueOf(letra));
			    			tr.setRowFilter(RowFilter.regexFilter(cad.toString()));
						}
					}
			}
		});
		
		this.ventanaEditarCliente.getTxtEmail().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				char letra = e.getKeyChar();
				if(ventanaEditarCliente.getTxtEmail().getText().length() == 45) {
					e.consume();
				}
			}
		});
		
		this.ventanaEditarCliente.getTxtNombre().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				char letra = e.getKeyChar();
				if(Character.isDigit(letra)) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
				if(ventanaEditarCliente.getTxtNombre().getText().length() == 45) {
					e.consume();
				}
			}
		});
		this.ventanaEditarCliente.getTxtApellido().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				char letra = e.getKeyChar();
				if(Character.isDigit(letra)) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
				if(ventanaEditarCliente.getTxtApellido().getText().length() == 45) {
					e.consume();
				}
			}
		});
		this.ventanaEditarCliente.getTxtDni().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				char letra = e.getKeyChar();
				if(!Character.isDigit(letra)) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
				if(ventanaEditarCliente.getTxtDni().getText().length() == 8) {
					e.consume();
				}
			}
		});
		this.ventanaEditarCliente.getTxtTelefonoFijo().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				char letra = e.getKeyChar();
				if(!Character.isDigit(letra)) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
				if(ventanaEditarCliente.getTxtTelefonoFijo().getText().length() == 45) {
					e.consume();
				}
			}
		});
		this.ventanaEditarCliente.getTxtTelefonoCelular().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				char letra = e.getKeyChar();
				if(!Character.isDigit(letra)) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
				if(ventanaEditarCliente.getTxtTelefonoCelular().getText().length() == 45) {
					e.consume();
				}
			}
		});
		

//		this.vista.getPanelPasaje().getBtnVisualizarPasaje().addActionListener(vp->verDatosPasaje(vp));
		
		this.vista.getPanelPasaje().getCancelCheckBox().addActionListener(ccb->cargarCancelados(ccb));
		this.vista.getPanelPasaje().getVenciCheckBox().addActionListener(pcb->cargarVencidos(pcb));
		this.vista.getPanelPasaje().getReserCheckBox().addActionListener(rcb->cargarReservados(rcb));
		this.vista.getPanelPasaje().getVendCheckBox().addActionListener(vcb->cargarVendidos(vcb));
		
		
//		this.vista.getPanelPasaje().getBtnBuscar().addActionListener(b->filtrar(b));
//		this.vista.getPanelPasaje().getBtnBorrarFiltros().addActionListener(bf->borrarFiltros(bf));
		
		this.vista.getItemAgregarEvento().addActionListener(ac->mostrarVentanaAgregarEvento(ac));
		this.vista.getItemVisualizarEventos().addActionListener(ac->mostrarEventos(ac));
		this.vista.getItemEditarEvento().addActionListener(ac->mostrarVentanaEditarEvento(ac));
		this.ventanaEditarEvento.getBtnEditar().addActionListener(ac->actualizarTablaEventos(ac));
		this.ventanaEvento.getBtnRegistrar().addActionListener(ac->actualizarTablaEventos(ac));
		
		this.vista.getItemAgregarPromocion().addActionListener(ac->mostrarVentanaAgregarPromocion(ac));
		this.vista.getItemEditarPromocion().addActionListener(ac->mostrarVentanaEditarPromocion(ac));

		this.vista.getItemVisualizarPromociones().addActionListener(ac->mostrarPromociones(ac));
//		this.vista.getItemDarBajaPromocion().addActionListener(ac->darBajaPromocion(ac));
		this.vista.getItemEditarEstadoPromocion().addActionListener(ac->editarEstadoPromocionUno(ac));
		this.ventanaPromocion.getBtnRegistrar().addActionListener(ac->actualizarTablaPromocion(ac));
		this.ventanaEditarPromocion.getBtnEditar().addActionListener(ac->actualizarTablaPromocion(ac));

		
		this.administrativoLogueado = administrativoLogueado;
		this.cliente = new Cliente(new DAOSQLFactory());
		this.pasaje = new Pasaje(new DAOSQLFactory());
		this.evento = new ModeloEvento(new DAOSQLFactory());
		this.promocion = new ModeloPromocion(new DAOSQLFactory());
		this.viaje_promocion = new ModeloViaje_Promocion(new DAOSQLFactory());
		this.viaje = new ModeloViaje(new DAOSQLFactory());
		
		controladorPasaje = new ControladorPasaje(ventanaVisualizarCliente,cliente,administrativoLogueado);

		controladorCliente = new ControladorCliente(ventanaRegistrarCliente, ventanaEditarCliente, cliente);

		controladorEvento = new ControladorEvento(evento, this.eventos_en_tabla);
		this.controladorEvento.setAdministrativoLogueado(administrativoLogueado);
		
        controladorPromocion = new ControladorPromocion(ventanaPromocion, promocion, this.promociones_en_tabla);
        controladorDatosLogin = new ControladorDatosLogin();

        //TODO: FIN DEL CONTROLADOR
	}

	private void cambiarContrasenia(ActionEvent c) {
		
		String passwordActual = new String(this.ventanaCambiarContrasenia.getPassActual().getPassword());
		String passwordConfirmacion1 = new String(this.ventanaCambiarContrasenia.getPassNueva().getPassword());
		String passwordConfirmacion2 = new String(this.ventanaCambiarContrasenia.getConfirmacionContrasena().getPassword());
		
		System.out.println(passwordConfirmacion1+" "+passwordConfirmacion2);
		
		if(!passwordConfirmacion1.equals(passwordConfirmacion2)){
			JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden ", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
		else if(!passwordActual.equals(administrativoLogueado.getDatosLogin().getContrasena())){
			JOptionPane.showMessageDialog(null, "La contraseña actual es incorrecta", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}else{
			LoginDTO loginDTO = new LoginDTO();
			loginDTO.setIdDatosLogin(administrativoLogueado.getDatosLogin().getIdDatosLogin());
			loginDTO.setUsuario(administrativoLogueado.getDatosLogin().getUsuario());
			loginDTO.setRol(administrativoLogueado.getDatosLogin().getRol());
			loginDTO.setEstado(administrativoLogueado.getDatosLogin().getEstado());
		
			String password = new String(this.ventanaCambiarContrasenia.getPassNueva().getPassword());
			loginDTO.setContrasena(password);
			this.login.editarLogin(loginDTO);
			this.ventanaCambiarContrasenia.mostrarVentana(false);
			JOptionPane.showMessageDialog(null, "Contraseña actualizada", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void mostrarVentanaCambiarContrasenia(ActionEvent dp) {
		this.ventanaCambiarContrasenia.limpiarCampos();
		this.ventanaCambiarContrasenia.mostrarVentana(true);
	}
	
	private void salirVentanaCambiarContrasenia(ActionEvent c) {
		this.ventanaCambiarContrasenia.limpiarCampos();
		this.ventanaCambiarContrasenia.mostrarVentana(false);;
	}
	
	private void llenarTablaViajes(List<ViajeDTO> viajes){
		this.ventanaTablaViajes.getModelViajes().setRowCount(0); //Para vaciar la tabla
		this.ventanaTablaViajes.getModelViajes().setColumnCount(0);
		this.ventanaTablaViajes.getModelViajes().setColumnIdentifiers(this.ventanaTablaViajes.getNombreColumnas());
		
		for (int i = 0; i < viajes.size(); i++){
			if(viajes.get(i).getEstado().equals("activo")){ 
				Object[] fila = {viajes.get(i).getCiudadOrigen().getNombre(),
						viajes.get(i).getCiudadDestino().getNombre(),
						mapper.parseToString(viajes.get(i).getFechaSalida()),
						mapper.parseToString(viajes.get(i).getFechaLlegada()),
						viajes.get(i).getHoraSalida(),
						viajes.get(i).getHorasEstimadas(),
						viajes.get(i).getCapacidad(),
						viajes.get(i).getTransporte().getNombre(),
						"$ "+viajes.get(i).getPrecio()					
				};
				this.ventanaTablaViajes.getModelViajes().addRow(fila);
			}
		}		
	}

	private void restablecerContrasena(ActionEvent r) {
		controladorDatosLogin.restablecerContrasena();
	}

	public void cargarCancelados(ActionEvent ccb) {
		this.llenarTablaPasajes();
	}
	
	public void cargarVencidos(ActionEvent pcb) {
		this.llenarTablaPasajes();
	}
	
	public void cargarReservados(ActionEvent rcb) {
		this.llenarTablaPasajes();
	}
	
	public void cargarVendidos(ActionEvent vcb) {
		this.llenarTablaPasajes();
	}
	
	public void cargarInactivos(ActionEvent si) {
		this.llenarTablaClientes();
	}


	public void cargarActivos(ActionEvent sa) {
		this.llenarTablaClientes();
	}
	
	public ControladorAdministrativo(){
		super();
	}
	
	public void inicializar(){
		this.vista.mostrarVentana();
		this.vista.getMenuUsuarioLogueado().setText(""+ administrativoLogueado.getNombre()+" "+administrativoLogueado.getApellido());
		this.llenarTablaClientes();
		this.llenarTablaPasajes(pasaje.obtenerPasajes());
		controladorEvento.controlarNotificacionesInicioSesion();
		controladorEvento.controlarNotificacionesContinuo();
		controlarAutomatizacionDelEnvioDeVoucher();
		controlDeVencimientoDeReserva();
	}
	
	private void agregarPanelClientes(ActionEvent ac) {
		this.vista.getPanelCliente().mostrarPanelCliente(true);
		this.vista.getPanelPasaje().mostrarPanelPasaje(false);
		this.vista.getPanelEvento().mostrarPanelEvento(false);
		this.vista.getPanelPromocion().mostrarPanelPromocion(false);
		controladorCliente.llenarTablaClientes();
	}
	// ------------------------------------------- Desactivar Cliente ------------------------

	private void editarCliente(ActionEvent ec) {
		if(controladorCliente.validarCamposEditarCliente()){
		java.util.Date dateFechaNacimiento = this.ventanaEditarCliente.getDateFechaNacimiento().getDate();
		java.sql.Date fechaNacimientoCliente = new java.sql.Date(dateFechaNacimiento.getTime());
		
		String estado = "activo";
		int idCliente = this.clientes_en_tabla.get(this.filaSeleccionada).getIdCliente();
		String nombreCliente = this.ventanaEditarCliente.getTxtNombre().getText();
		String apellidoCliente = this.ventanaEditarCliente.getTxtApellido().getText();
		String dniCliente = this.ventanaEditarCliente.getTxtDni().getText();
		RolDTO rolCliente = new RolDTO(
				this.clientes_en_tabla.get(this.filaSeleccionada).getLogin().getRol().getIdRol(),
				this.clientes_en_tabla.get(this.filaSeleccionada).getLogin().getRol().getNombre()
				);
		String contrasenia = cliente.getByClienteById(idCliente).getLogin().getContrasena();
		
		LoginDTO loginCliente = new LoginDTO(
				this.clientes_en_tabla.get(this.filaSeleccionada).getLogin().getIdDatosLogin(),
				this.ventanaEditarCliente.getTxtUsuario().getText(),
				contrasenia,
				rolCliente,
				estado
				);
		
		MedioContactoDTO medioContactoCliente= new MedioContactoDTO(
				this.clientes_en_tabla.get(this.filaSeleccionada).getMedioContacto().getIdMedioContacto(),
				this.ventanaEditarCliente.getTxtTelefonoFijo().getText(),
				this.ventanaEditarCliente.getTxtTelefonoCelular().getText(),
				this.ventanaEditarCliente.getTxtEmail().getText()
				);
		
		ClienteDTO clienteEditable = new ClienteDTO(idCliente, nombreCliente, apellidoCliente, dniCliente, fechaNacimientoCliente, medioContactoCliente, loginCliente);
		controladorCliente.editarCliente(clienteEditable);
		controladorCliente.llenarTablaClientes();
		}
	}
	
	private void mostrarVentanaEditarCliente(ActionEvent mve) {
		this.vista.getPanelCliente().mostrarPanelCliente(true);
		int clienteAEditar = this.vista.getPanelCliente().getTablaClientes().getSelectedRow();
		if (clienteAEditar != -1){
			mostrarCliente(clienteAEditar);
			
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void mostrarCliente(int filaSeleccionada){
		this.filaSeleccionada = filaSeleccionada;
		this.ventanaEditarCliente.mostrarVentana();
		
		this.ventanaEditarCliente.getTxtNombre().setText(this.clientes_en_tabla.get(this.filaSeleccionada).getNombre());
		this.ventanaEditarCliente.getTxtApellido().setText(this.clientes_en_tabla.get(this.filaSeleccionada).getApellido());
		this.ventanaEditarCliente.getTxtDni().setText(this.clientes_en_tabla.get(this.filaSeleccionada).getDni());
		this.ventanaEditarCliente.getDateFechaNacimiento().setDate(this.clientes_en_tabla.get(this.filaSeleccionada).getFechaNacimiento());
		this.ventanaEditarCliente.getTxtUsuario().setText(this.clientes_en_tabla.get(this.filaSeleccionada).getLogin().getUsuario());
		this.ventanaEditarCliente.getTxtTelefonoFijo().setText(this.clientes_en_tabla.get(this.filaSeleccionada).getMedioContacto().getTelefonoFijo());
		this.ventanaEditarCliente.getTxtTelefonoCelular().setText(this.clientes_en_tabla.get(this.filaSeleccionada).getMedioContacto().getTelefonoCelular());
		this.ventanaEditarCliente.getTxtEmail().setText(this.clientes_en_tabla.get(this.filaSeleccionada).getMedioContacto().getEmail());
		
	}
	
	private void desactivarCliente(ActionEvent dc) {
		this.vista.getPanelCliente().mostrarPanelCliente(true);
		int clienteSeleccionado = this.vista.getPanelCliente().getTablaClientes().getSelectedRow();
		if (clienteSeleccionado != -1){
			int idLogin = this.clientes_en_tabla.get(clienteSeleccionado).getLogin().getIdDatosLogin();
			controladorCliente.desactivarCliente(idLogin);
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
		this.llenarTablaClientes();
	}
	private void activarCliente(ActionEvent acc) {
		this.vista.getPanelCliente().mostrarPanelCliente(true);
		int clienteSeleccionado = this.vista.getPanelCliente().getTablaClientes().getSelectedRow();
		if (clienteSeleccionado != -1){
			int idLogin = this.clientes_en_tabla.get(clienteSeleccionado).getLogin().getIdDatosLogin();
			controladorCliente.activarCliente(idLogin);
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
		this.llenarTablaClientes();
	}
	//----------------------------------------------------------------------------------------
	private void mostrarVentanaAgregarPasaje(ActionEvent ap) {
		this.vista.getPanelPasaje().mostrarPanelPasaje(true);
		this.vista.getPanelCliente().mostrarPanelCliente(false);
		this.vista.getPanelEvento().mostrarPanelEvento(false);
		this.vista.getPanelPromocion().mostrarPanelPromocion(false);
		this.ventanaVisualizarCliente.mostrarVentana(true);
		this.llenarTablaPasajes(pasaje.obtenerPasajes());
		controladorPasaje.iniciar();
	}
	
	private void mostrarVentanaEditarPasaje(ActionEvent ep) {
		
		this.vista.getPanelPasaje().mostrarPanelPasaje(true);
		int filaSeleccionada = this.vista.getPanelPasaje().getTablaReservas().getSelectedRow();
		
		if (filaSeleccionada != -1){
			controladorPasaje.editarPasaje(filaSeleccionada);
			llenarTablaPasajes(pasaje.obtenerPasajes());
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	private void mostrarVentanaEditarEvento(ActionEvent ep) {
		int filaSeleccionada = this.vista.getPanelEvento().getTablaEventos().getSelectedRow();
		if (filaSeleccionada != -1){
			verDatosDelEvento(filaSeleccionada);
//			llenarTablaEventos(evento.obtenerEvento());
//			controladorEvento.llenarMotivos(this.eventos_en_tabla.get(filaSeleccionada));
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	private void mostrarVentanaEditarPromocion(ActionEvent ep) {
		this.vista.getPanelPromocion().mostrarPanelPromocion(true);
		int filaSeleccionada = this.vista.getPanelPromocion().getTablaPromocion().getSelectedRow();
		if (filaSeleccionada != -1){
			verDatosDeLaPromocion(filaSeleccionada);
			llenarTablaPromociones();
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	private void verDatosDelEvento(int filaSeleccionada) {
		controladorEvento.llenarComboEstados();
		controladorEvento.llenarComboHora();
		controladorEvento.llenarComboMinutos();
        controladorEvento.setEventoSeleccionado(this.eventos_en_tabla.get(filaSeleccionada));
		if (filaSeleccionada != -1){
			
			ventanaEditarEvento.mostrarVentana(true);
			ventanaEditarEvento.getDateFechaEvento().setDate(this.eventos_en_tabla.get(filaSeleccionada).getFechaEvento());
			ventanaEditarEvento.getComboHora().setSelectedItem(obtenerHora(this.eventos_en_tabla.get(filaSeleccionada).getHoraEvento()));
			ventanaEditarEvento.getComboMinutos().setSelectedItem(obtenerMinutos(this.eventos_en_tabla.get(filaSeleccionada).getHoraEvento()));
			
			ventanaEditarEvento.getTxtDescripcion().setText(this.eventos_en_tabla.get(filaSeleccionada).getDescripcion());
			ventanaEditarEvento.getComboEstadoEvento().setSelectedItem(this.eventos_en_tabla.get(filaSeleccionada).getEstadoEvento().getNombre());
			ventanaEditarEvento.getTxtDni().setText(this.eventos_en_tabla.get(filaSeleccionada).getCliente().getDni());
			ventanaEditarEvento.getTxtApellido().setText(this.eventos_en_tabla.get(filaSeleccionada).getCliente().getApellido());
			ventanaEditarEvento.getTxtNombre().setText(this.eventos_en_tabla.get(filaSeleccionada).getCliente().getNombre());
			
		}
		else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private String obtenerHora(Time horario) {
		String[] horarioString = horario.toString().split(":");
		return horarioString[0];
	}
	
	private String obtenerMinutos(Time horario) {
		String[] horarioString = horario.toString().split(":");
		return horarioString[1];
	}
	
	private void verDatosDeLaPromocion(int filaSeleccionada) {
		controladorPromocion.llenarComboPorcentaje();
		controladorPromocion.setPromocionSeleccionada(this.promociones_en_tabla.get(filaSeleccionada));
		if (filaSeleccionada != -1){
			ventanaEditarPromocion.setVisible(true);
			ventanaEditarPromocion.getDateFechaVencimiento().setDate(this.promociones_en_tabla.get(filaSeleccionada).getFechaVencimiento());
			ventanaEditarPromocion.getComboPorcentaje().setSelectedItem(this.promociones_en_tabla.get(filaSeleccionada).getPorcentaje()+"");
			ventanaEditarPromocion.getTxtStock().setText(this.promociones_en_tabla.get(filaSeleccionada).getStock()+"");
		}
		else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}
//TODO: CANCELACION DE PASAJE
	private void cancelarPasaje(ActionEvent cp) {
		this.vista.getPanelPasaje().mostrarPanelPasaje(true);
		int filaSeleccionada = this.vista.getPanelPasaje().getTablaReservas().getSelectedRow();
		if (filaSeleccionada != -1){
			controladorPasaje.eliminarPasaje(filaSeleccionada);
			llenarTablaPasajes(pasaje.obtenerPasajes());
		
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void mostrarPasajes(ActionEvent ap) {
		this.vista.getPanelCliente().mostrarPanelCliente(false);
		this.vista.getPanelPasaje().mostrarPanelPasaje(true);
		this.vista.getPanelEvento().mostrarPanelEvento(false);
		this.vista.getPanelPromocion().mostrarPanelPromocion(false);
		this.llenarTablaPasajes(pasaje.obtenerPasajes());
	}
	
	public void mostrarEventos(ActionEvent ap) {
		this.vista.getPanelEvento().mostrarPanelEvento(true);
		this.vista.getPanelCliente().mostrarPanelCliente(false);
		this.vista.getPanelPasaje().mostrarPanelPasaje(false);
		this.vista.getPanelPromocion().mostrarPanelPromocion(false);
		limpiarFiltrosEvento();
		this.llenarTablaEventos(evento.obtenerEvento());
		this.controladorEvento.actualizarEventosVistos();
	}
	
	private void mostrarPromociones(ActionEvent ap) {
		this.vista.getPanelEvento().mostrarPanelEvento(false);
		this.vista.getPanelCliente().mostrarPanelCliente(false);
		this.vista.getPanelPasaje().mostrarPanelPasaje(false);
		this.vista.getPanelPromocion().mostrarPanelPromocion(true);
		this.llenarTablaPromociones();
	}
	
//	private void darBajaPromocion(ActionEvent v) {
//		this.vista.getPanelPromocion().mostrarPanelPromocion(true);
//		int promoSeleccionada = this.vista.getPanelPromocion().getTablaPromocion().getSelectedRow();
//		if (promoSeleccionada != -1){
//			controladorPromocion.darBajaPromocion(promoSeleccionada);
//			llenarTablaPromociones();
//		}else{
//			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
//		}
//		this.llenarTablaClientes();
//	}
	
	private void editarEstadoPromocionUno(ActionEvent ep) {
		this.vista.getPanelPromocion().mostrarPanelPromocion(true);
		int filaSeleccionada = this.vista.getPanelPromocion().getTablaPromocion().getSelectedRow();
		if (filaSeleccionada != -1){
			editarEstadoPromocionDos(filaSeleccionada);
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}	
	}
	
	private void editarEstadoPromocionDos(int filaSeleccionada) {
		controladorPromocion.setPromocionSeleccionada(this.promociones_en_tabla.get(filaSeleccionada));
		if (filaSeleccionada != -1){
			PromocionDTO seleccionada = this.promociones_en_tabla.get(filaSeleccionada);
			if(seleccionada.getEstado().equals("inactiva"))
				seleccionada.setEstado("activa");
			else
				if(seleccionada.getEstado().equals("activa"))
					seleccionada.setEstado("inactiva");
			promocion.editarEstadoPromocion(seleccionada);
		}
		this.llenarTablaPromociones();
	}



	private void mostrarVentanaAgregarCliente(ActionEvent ac)  {
		this.vista.getPanelCliente().mostrarPanelCliente(true);
		this.vista.getPanelPasaje().mostrarPanelPasaje(false);
		this.vista.getPanelEvento().mostrarPanelEvento(false);
		this.vista.getPanelPromocion().mostrarPanelPromocion(false);
		this.llenarTablaClientes();
		this.ventanaRegistrarCliente.limpiarCampos();
		this.ventanaCliente.limpiarCampos();
		this.ventanaCliente.mostrarVentana();
//		ControladorCliente controladorCliente = new ControladorCliente(ventanaCliente,cliente);
	}
	
	private void mostrarVentanaAgregarEvento(ActionEvent ac)  {
		controladorEvento.iniciar();
		this.vista.getPanelEvento().mostrarPanelEvento(true);
		this.vista.getPanelCliente().mostrarPanelCliente(false);
		this.vista.getPanelPasaje().mostrarPanelPasaje(false);
		this.vista.getPanelPromocion().mostrarPanelPromocion(false);
		this.llenarTablaEventos(evento.obtenerEvento());
		this.ventanaEvento.limpiarCampos();
		this.ventanaEvento.mostrarVentana();
	}
	
	private void mostrarVentanaAgregarPromocion(ActionEvent ac)  {
		controladorPromocion.iniciar();
		this.vista.getPanelPromocion().mostrarPanelPromocion(true);
		this.vista.getPanelCliente().mostrarPanelCliente(false);
		this.vista.getPanelPasaje().mostrarPanelPasaje(false);
		this.vista.getPanelEvento().mostrarPanelEvento(false);
		this.llenarTablaPromociones();
		this.ventanaPromocion.limpiarCampos();
		this.ventanaPromocion.mostrarVentana();
	}
	
	public List<EventoDTO> filtrarEventoSegun(String datoCombo){
		List<EventoDTO> ret = new ArrayList<EventoDTO>();
		for (int i = 0; i < eventos_en_tabla.size(); i++) {
			if (eventos_en_tabla.get(i).getEstadoEvento().getNombre().compareTo(datoCombo)==0) {
				ret.add(eventos_en_tabla.get(i));
			}
		}
		return ret;
	}
	
	public List<PasajeDTO> filtrarPasajeSegun(String estado) {
		List<PasajeDTO> resultado = new ArrayList<PasajeDTO>();
		this.clientes_en_tabla = cliente.obtenerClientes();
		for (int i = 0; i < pasajes_en_tabla.size(); i++) {
			if (pasajes_en_tabla.get(i).getEstadoDelPasaje().getNombre().compareTo(estado)==0) {
				resultado.add(pasajes_en_tabla.get(i));
			}
		}
		if (resultado.size() == 0) {
			JOptionPane.showMessageDialog(vista.getPanelPasaje(), "No existe ningún pasaje con ese estado", "", 0);
		}
		return resultado;
	}
		
	private void llenarTablaClientes(){
		boolean activos = this.vista.getPanelCliente().getActivos().isSelected();
		boolean inactivos = this.vista.getPanelCliente().getInactivos().isSelected();
		
		this.vista.getPanelCliente().getModelClientes().setRowCount(0); //Para vaciar la tabla
		this.vista.getPanelCliente().getModelClientes().setColumnCount(0);
		this.vista.getPanelCliente().getModelClientes().setColumnIdentifiers(this.vista.getPanelCliente().getNombreColumnasClientes());
			
		this.clientes_en_tabla = new ArrayList<ClienteDTO>();
		this.clientes_aux = cliente.obtenerClientes();

		if(activos == true && inactivos == false) {
			for (ClienteDTO cliente : this.clientes_aux) {
				if (cliente.getLogin().getEstado().equals("activo")) {
					this.clientes_en_tabla.add(cliente);
				}
			}
		}else if(inactivos == true && activos == false) {
			for(ClienteDTO cliente : this.clientes_aux) {
				if(cliente.getLogin().getEstado().equals("inactivo")) {
					this.clientes_en_tabla.add(cliente);
				}
			}
		} else if(activos && inactivos) {
			for (ClienteDTO cliente: this.clientes_aux) {
				this.clientes_en_tabla.add(cliente);
			}
		}
		
		for (int i = 0; i < this.clientes_en_tabla.size(); i++){
			System.out.println("Tabla"+ this.clientes_en_tabla.get(i).getNombre());
			Object[] fila = {this.clientes_en_tabla.get(i).getNombre(),
							 this.clientes_en_tabla.get(i).getApellido(),
							 this.clientes_en_tabla.get(i).getDni(),
							 mapper.parseToString(this.clientes_en_tabla.get(i).getFechaNacimiento()),
							 this.clientes_en_tabla.get(i).getMedioContacto().getTelefonoFijo(),
							 this.clientes_en_tabla.get(i).getMedioContacto().getTelefonoCelular(),
							 this.clientes_en_tabla.get(i).getMedioContacto().getEmail(),
							 this.clientes_en_tabla.get(i).getLogin().getEstado()
							};
			this.vista.getPanelCliente().getModelClientes().addRow(fila);
		}		
	}
	
	private void llenarTablaPasajes(){
		this.vista.getPanelPasaje().getModelReservas().setRowCount(0); //Para vaciar la tabla
		this.vista.getPanelPasaje().getModelReservas().setColumnCount(0);
		this.vista.getPanelPasaje().getModelReservas().setColumnIdentifiers(this.vista.getPanelPasaje().getNombreColumnasReservas());

		this.pasajes_en_tabla = new ArrayList<PasajeDTO>();
		this.pasajes_aux = pasaje.obtenerPasajes();
		
		this.pasajes_en_tabla = obtenerPasajesFiltrados(this.pasajes_aux);
		
		for (int i = 0; i < this.pasajes_en_tabla.size(); i++){

			Object[] fila = {
							this.pasajes_en_tabla.get(i).getCliente().getDni(),
							this.pasajes_en_tabla.get(i).getCliente().getNombre(),
							this.pasajes_en_tabla.get(i).getCliente().getApellido(),
							this.pasajes_en_tabla.get(i).getNumeroComprobante(),
							this.pasajes_en_tabla.get(i).getViaje().getCiudadOrigen().getNombre(),
							this.pasajes_en_tabla.get(i).getViaje().getCiudadDestino().getNombre(),
							mapper.parseToString(this.pasajes_en_tabla.get(i).getViaje().getFechaSalida()),
							mapper.parseToString(this.pasajes_en_tabla.get(i).getViaje().getFechaLlegada()),
							this.pasajes_en_tabla.get(i).getViaje().getHoraSalida(),
							this.pasajes_en_tabla.get(i).getValorViaje(),
							this.pasajes_en_tabla.get(i).getViaje().getTransporte().getNombre(),
							this.pasajes_en_tabla.get(i).getEstadoDelPasaje().getNombre()
			};
							this.vista.getPanelPasaje().getModelReservas().addRow(fila);
		}		
	}
	
	private void llenarTablaPasajes(List<PasajeDTO> pasajes){
		this.vista.getPanelPasaje().getModelReservas().setRowCount(0); //Para vaciar la tabla
		this.vista.getPanelPasaje().getModelReservas().setColumnCount(0);
		this.vista.getPanelPasaje().getModelReservas().setColumnIdentifiers(this.vista.getPanelPasaje().getNombreColumnasReservas());

		this.pasajes_en_tabla = pasajes;
			
		for (int i = 0; i < this.pasajes_en_tabla.size(); i++){

			Object[] fila = {
							this.pasajes_en_tabla.get(i).getCliente().getDni(),
							this.pasajes_en_tabla.get(i).getCliente().getNombre(),
							this.pasajes_en_tabla.get(i).getCliente().getApellido(),
							this.pasajes_en_tabla.get(i).getNumeroComprobante(),
							this.pasajes_en_tabla.get(i).getViaje().getCiudadOrigen().getNombre(),
							this.pasajes_en_tabla.get(i).getViaje().getCiudadDestino().getNombre(),
							this.mapper.parseToString(this.pasajes_en_tabla.get(i).getViaje().getFechaSalida()),
							this.mapper.parseToString(this.pasajes_en_tabla.get(i).getViaje().getFechaLlegada()),
							this.pasajes_en_tabla.get(i).getViaje().getHoraSalida(),
							"$ "+this.pasajes_en_tabla.get(i).getValorViaje(),
							this.pasajes_en_tabla.get(i).getViaje().getTransporte().getNombre(),
							this.pasajes_en_tabla.get(i).getEstadoDelPasaje().getNombre()
			};
							this.vista.getPanelPasaje().getModelReservas().addRow(fila);
		}		
	}
	
	private void llenarTablaEventos(List<EventoDTO> tabla){
		this.vista.getPanelEvento().getModelEventos().setRowCount(0); //Para vaciar la tabla
		this.vista.getPanelEvento().getModelEventos().setColumnCount(0);
		this.vista.getPanelEvento().getModelEventos().setColumnIdentifiers(this.vista.getPanelEvento().getNombreColumnasEventos());

		this.eventos_en_tabla = evento.obtenerEvento();
			
		for (int i = 0; i < tabla.size(); i++){

			Object[] fila = {
							mapper.parseToString(tabla.get(i).getFechaIngreso()),
							mapper.parseToString(tabla.get(i).getFechaEvento()),
							tabla.get(i).getHoraEvento(),
							tabla.get(i).getDescripcion(),
							tabla.get(i).getCliente().getApellido(),
							tabla.get(i).getCliente().getNombre(),
							tabla.get(i).getAdministrativo().getApellido()+" "+tabla.get(i).getAdministrativo().getNombre(),
							tabla.get(i).getEstadoEvento().getNombre(),
							this.estaReprogramado(tabla.get(i))
			};
							this.vista.getPanelEvento().getModelEventos().addRow(fila);
		}	
		controladorEvento.llenarComboEstados();
	}
	
	private void actualizarTablaEventos(ActionEvent e) {
		controladorEvento.llenarComboEstados();
		limpiarFiltrosEvento();
		llenarTablaEventos(evento.obtenerEvento());
	}
	
	private void llenarTablaPromociones(){
		this.vista.getPanelPromocion().getModelPromocion().setRowCount(0); //Para vaciar la tabla
		this.vista.getPanelPromocion().getModelPromocion().setColumnCount(0);
		this.vista.getPanelPromocion().getModelPromocion().setColumnIdentifiers(this.vista.getPanelPromocion().getNombreColumnasPromociones());

		this.promociones_en_tabla = promocion.obtenerPromocion();
			
		for (int i = 0; i < this.promociones_en_tabla.size(); i++){

			Object[] fila = {
							this.promociones_en_tabla.get(i).getPorcentaje()+" %",
							this.promociones_en_tabla.get(i).getStock(),
							mapper.parseToString(this.promociones_en_tabla.get(i).getFechaVencimiento()),
							this.promociones_en_tabla.get(i).getEstado()
			};
							this.vista.getPanelPromocion().getModelPromocion().addRow(fila);
		}		
	}
	
	public void actualizarTablaPromocion(ActionEvent e) {
		this.llenarTablaPromociones();
	}
	
	public String estaReprogramado(EventoDTO e) {
		if(e.getMotivoReprogramacion().equals(""))
			return "no";
		else
			return "si";
	}
	
	private void limpiarFiltrosEvento() {
		this.vista.getPanelEvento().getComboFiltros().setSelectedIndex(0);
	}
	
	private ArrayList<PasajeDTO> obtenerPasajesFiltrados(List<PasajeDTO> pasajes_aux){
		
		ArrayList<PasajeDTO> pasajes = new ArrayList<PasajeDTO>();
		
		boolean cancel = this.vista.getPanelPasaje().getCancelCheckBox().isSelected();
		boolean venci = this.vista.getPanelPasaje().getVenciCheckBox().isSelected();
		boolean reser = this.vista.getPanelPasaje().getReserCheckBox().isSelected();
		boolean vend = this.vista.getPanelPasaje().getVendCheckBox().isSelected();

		if (cancel == false && venci == false && reser == false && vend == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				pasajes.add(pasaje);
			}
		} else if (cancel == true && venci == true && reser == true && vend == true ) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				pasajes.add(pasaje);
			}
		} else if (cancel == true && venci == true && reser == true && vend == false ) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if(!pasaje.getEstadoDelPasaje().getNombre().equals("Vendido")) {
					pasajes.add(pasaje);
				}
			}
		} else if (cancel == true && venci == true && reser == false && vend == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if(!(pasaje.getEstadoDelPasaje().getNombre().equals("Vendido") || pasaje.getEstadoDelPasaje().getNombre().equals("Reservado"))) {
					pasajes.add(pasaje);
				}
			}
		} else if (cancel == true && venci == false && reser == false && vend == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if(!(pasaje.getEstadoDelPasaje().getNombre().equals("Vendido") || 
						pasaje.getEstadoDelPasaje().getNombre().equals("Reservado") || 
							pasaje.getEstadoDelPasaje().getNombre().equals("Vencido") )) {
					pasajes.add(pasaje);
				}
			}
		} else if (vend == true && reser == true && venci == true && cancel == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if( !(pasaje.getEstadoDelPasaje().getNombre().equals("Cancelado")) ) {
					pasajes.add(pasaje);
				}
			}
		} else if (vend == true && reser == true && venci == false && cancel == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if(!(pasaje.getEstadoDelPasaje().getNombre().equals("Cancelado") || pasaje.getEstadoDelPasaje().getNombre().equals("Vencido"))) {
					pasajes.add(pasaje);
				}
			}
		} else if (vend == true && reser == false && venci == false && cancel == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if(!(pasaje.getEstadoDelPasaje().getNombre().equals("Cancelado") || 
						pasaje.getEstadoDelPasaje().getNombre().equals("Vencido") || 
							pasaje.getEstadoDelPasaje().getNombre().equals("Reservado") )) {
					pasajes.add(pasaje);
				}
			}
		} else if (reser == true && venci == true && vend == true && cancel == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if( !(pasaje.getEstadoDelPasaje().getNombre().equals("Cancelado")) ) {
					pasajes.add(pasaje);
				}
			}
		} else if (reser == true && venci == true && vend == false && cancel == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if(!(pasaje.getEstadoDelPasaje().getNombre().equals("Cancelado") || pasaje.getEstadoDelPasaje().getNombre().equals("Vendido"))) {
					pasajes.add(pasaje);
				}
			}
		} else if (reser == true && venci == false && vend == false && cancel == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if(!(pasaje.getEstadoDelPasaje().getNombre().equals("Cancelado") || 
						pasaje.getEstadoDelPasaje().getNombre().equals("Vendido") || 
							pasaje.getEstadoDelPasaje().getNombre().equals("Vencido") )) {
					pasajes.add(pasaje);
				}
			}
		} else if (venci == true && vend == true && reser == true && cancel == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if( !(pasaje.getEstadoDelPasaje().getNombre().equals("Cancelado")) ) {
					pasajes.add(pasaje);
				}
			}
		} else if (venci == true && vend == true && reser == false && cancel == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if(!(pasaje.getEstadoDelPasaje().getNombre().equals("Cancelado") || pasaje.getEstadoDelPasaje().getNombre().equals("Reservado"))) {
					pasajes.add(pasaje);
				}
			}
		} else if (venci == true && vend == false && reser == false && cancel == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if(!(pasaje.getEstadoDelPasaje().getNombre().equals("Cancelado") || 
						pasaje.getEstadoDelPasaje().getNombre().equals("Reservado") || 
							pasaje.getEstadoDelPasaje().getNombre().equals("Vendido") )) {
					pasajes.add(pasaje);
				}
			}
		} else if (cancel == true && reser == false && venci == true && vend == true) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if( !(pasaje.getEstadoDelPasaje().getNombre().equals("Reservado")) ) {
					pasajes.add(pasaje);
				}
			}
		} else if (cancel == true && venci == false && reser == true && vend == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if(!(pasaje.getEstadoDelPasaje().getNombre().equals("Vencido") || pasaje.getEstadoDelPasaje().getNombre().equals("Vendido"))) {
					pasajes.add(pasaje);
				}
			}
		} else if (cancel == true && reser == true && vend == true && venci == false) {
			for(PasajeDTO pasaje : this.pasajes_aux) {
				if( !(pasaje.getEstadoDelPasaje().getNombre().equals("Vencido")) ) {
					pasajes.add(pasaje);
				}
			}
		}
		return pasajes;
	}

	protected String obtenerFecha(String string) {
		String [] aux = string.split(" ");
		String ret="";	
		for(int i=0; i<aux.length; i++) {

			if(i==5)	//año
				ret= aux[5] + ret;
			
			if(i==2)
				ret = ret + aux[2] ; //dia;
			
			if(i==1) {
				if(aux[1].equals("Jan")) //mes
					ret += "01";
				if(aux[1].equals("Feb"))
					ret += "02";
				if(aux[1].equals("Mar"))
					ret += "03";
				if(aux[1].equals("Apr"))
					ret += "04";
				if(aux[1].equals("May"))
					ret += "05";
				if(aux[1].equals("Jun"))
					ret += "06";
				if(aux[1].equals("Jul"))
					ret += "07";
				if(aux[1].equals("Aug"))
					ret += "08";
				if(aux[1].equals("Sep"))
					ret += "09";
				if(aux[1].equals("Oct"))
					ret += "10";
				if(aux[1].equals("Nov"))
					ret += "11";
				if(aux[1].equals("Dec"))
					ret += "12";
			}
		}	
		return ret;
	}

	private void generarVoucherMail(PasajeDTO pasaje,ClienteDTO cliente){		
		GeneratePDF pdf = new GeneratePDF();
		pdf.createPDF(pasaje, cliente);//(pasaje,cliente); // se crea el pdf en resource	
	
		EnvioDeCorreo envioCorreo = new EnvioDeCorreo();
		envioCorreo.enviarAdjunto(cliente.getMail());				
	}
	
	public void controlarAutomatizacionDelEnvioDeVoucher(){
		TimerTask timerTask = new TimerTask() {
		    public void run() {
		    	Calendar salida = Calendar.getInstance(); //obtiene la fecha de hoy
				Calendar calendar = Calendar.getInstance(); //obtiene la fecha de hoy
				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
				
				for(PasajeDTO p : modeloPasaje.obtenerPasajes()) {
					
					salida.setTime(p.getViaje().getFechaSalida());
					salida.add(calendar.DATE, -2);
					
					String salidaViaje = format.format(salida.getTime());
					String fechaActual = format.format(calendar.getTime());
					
					System.out.println("Fecha Salida viaje: "+salidaViaje+" "+salidaViaje.length());
					System.out.println("Fecha Actual      : "+fechaActual+" "+fechaActual.length());
					
					if(salidaViaje.equals(fechaActual) && p.isNotificacion()==false){
						System.out.println("obtenemos pasaje "+p.getIdPasaje());
						generarVoucherMail(p, p.getCliente());
						p.setNotificacion(true);
						modeloPasaje.editarPasaje(p);
					}
				}
			}};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, 0, 60000);//1000=1 segundo
	}
	
	//Cada medio dia se verifica que la fecha de vencimiento de la reserva y el monto pagado x la persona, sea mayor al 30 porciento. Sino, pasa a vencido.
	public void controlDeVencimientoDeReserva() {
			TimerTask timerTask = new TimerTask() {
			    public void run() {
			    	Calendar vencimiento = Calendar.getInstance(); 
					Calendar calendar = Calendar.getInstance(); 
					SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
					double porcentaje = 0.30;
					for(PasajeDTO p : modeloPasaje.obtenerPasajes()) {
						double porcentajePagado = porcentaje * p.getValorViaje().doubleValue();
						
						if((p.getValorViaje().doubleValue() - p.getMontoAPagar().doubleValue()) < porcentajePagado) {

							vencimiento.setTime(p.getFechaVencimiento());
							
							String fechaVencimiento = format.format(vencimiento.getTime());
							String fechaActual = format.format(calendar.getTime());

							if(fechaVencimiento.equals(fechaActual)){
								p.getEstadoDelPasaje().setIdEstadoPasaje(3);
								p.setEstadoDelPasaje(p.getEstadoDelPasaje());
								modeloPasaje.editarPasaje(p);
							}
						}
					}
				}};
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(timerTask, 0, 43200000);//1000=1 segundo
	}

	public boolean esUnNumero(String s) {
		if (s.length()==0)
			return false;
		
		for(int i=0; i<s.length(); i++)
			if(!Character.isDigit(s.charAt(i)))
				return false;
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}