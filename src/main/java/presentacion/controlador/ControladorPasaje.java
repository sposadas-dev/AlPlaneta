package presentacion.controlador;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import correo.EnvioDeCorreo;
import dto.AdministrativoDTO;
import dto.ClienteDTO;
import dto.EstadoPasajeDTO;
import dto.FormaPagoDTO;
import dto.PagoDTO;
import dto.Pagos_PasajeDTO;
import dto.PasajeDTO;
import dto.Pasaje_PasajerosDTO;
import dto.PasajeroDTO;
import dto.PromocionDTO;
import dto.PuntoDTO;
import dto.RegimenPuntoDTO;
import dto.ViajeDTO;
import dto.Viaje_PromocionDTO;
import generatePDF.GeneratePDF;
import modelo.Cliente;
import modelo.EstadoPasaje;
import modelo.FormaPago;
import modelo.ModeloPromocion;
import modelo.ModeloPunto;
import modelo.ModeloRegimenPunto;
import modelo.ModeloViaje;
import modelo.ModeloViaje_Promocion;
import modelo.Pago;
import modelo.Pagos_Pasaje;
import modelo.Pasaje;
import modelo.Pasaje_Pasajeros;
import modelo.Pasajero;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.reportes.Reporte;
import presentacion.vista.administrativo.VentanaCancelacionPasaje;
import presentacion.vista.administrativo.VentanaCargaPasajero;
import presentacion.vista.administrativo.VentanaComprobante;
import presentacion.vista.administrativo.VentanaConfirmacionPasaje;
import presentacion.vista.administrativo.VentanaPago;
import presentacion.vista.administrativo.VentanaPasajero;
import presentacion.vista.administrativo.VentanaTablaPagos;
import presentacion.vista.administrativo.VentanaTablaViajes;
import presentacion.vista.administrativo.VentanaTarjeta;
import presentacion.vista.administrativo.VentanaVisualizarClientes;
import presentacion.vista.administrativo.VentanaVisualizarPasaje;
import presentacion.vista.administrativo.VistaAdministrativo;
import recursos.Mapper;

public class ControladorPasaje implements ActionListener{
	
	private EnvioDeCorreo envioCorreo;		
	private GeneratePDF pdf;
	private Mapper mapper;
	private VistaAdministrativo vistaAdministrativo;
	private VentanaVisualizarClientes ventanaVisualizarClientes;
	private VentanaTablaViajes ventanaTablaViajes;
	private VentanaCargaPasajero ventanaCargaPasajero;
	private VentanaPasajero ventanaPasajero;
	private VentanaPago ventanaPago;
	private VentanaVisualizarPasaje ventanaVisualizarPasaje;
	private VentanaConfirmacionPasaje ventanaConfirmacionPasaje;
	private VentanaComprobante ventanaComprobante;
	private VentanaTablaPagos ventanaTablaPagos;
	private VentanaTarjeta ventanaTarjeta;
	private VentanaCancelacionPasaje ventanaCancelacionPasaje;
	
	private List<ClienteDTO> clientes_en_tabla;
	private List <ViajeDTO> viajes_en_tabla;
	private List<PasajeroDTO> pasajeros_en_reserva;
	private List<PasajeDTO> pasajes_en_tabla;
	
	private ClienteDTO clienteSeleccionado; //cliente que selecciona en la tabla 
	private ViajeDTO viajeSeleccionado; // viaje que seleeciona en la tabla
	private PasajeDTO pasajeDTO;
	private PasajeDTO pasajeAEditar; 
	private PasajeDTO pasajeACancelar;
	private Pagos_PasajeDTO pagos_pasajeDTO;
	
	/*Modelos*/
	private Cliente cliente;
	private Pasajero modeloPasajero;
	private ModeloViaje modeloViaje;
	private Pago modeloPago;
	private Pasaje modeloPasaje;
	private Pagos_Pasaje modeloPagos_pasaje;
	private Pasaje_Pasajeros modeloPasajes_pasajeros;
	private ModeloPromocion modeloPromocion;
	private ModeloViaje_Promocion viaje_promocion;
	/*Fin de modelos*/
	
	private BigDecimal totalaPagar;
	private BigDecimal precioOriginal;
	private int porcentajeDescuento;
	private AdministrativoDTO administrativoLogueado;
	private PagoDTO pagoDTO;
	private boolean editarPago;
	private ViajeDTO viajeDTO;
	private java.util.Date fechaActual;
	
	private DefaultTableModel dm;
	private StringBuilder cad= new StringBuilder();
<<<<<<< src/main/java/presentacion/controlador/ControladorPasaje.java
	private ModeloPunto modeloPunto;
	private String aceptada="0123456789abcdefghijklmnopqrstuvwxyz";
=======
	private String aceptada="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
>>>>>>> src/main/java/presentacion/controlador/ControladorPasaje.java
	
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
		this.ventanaTarjeta = VentanaTarjeta.getInstance();
		this.ventanaCancelacionPasaje = VentanaCancelacionPasaje.getInstance();
		this.vistaAdministrativo = VistaAdministrativo.getInstance();
		
		java.util.Date fecha = new java.util.Date(); 
		this.fechaActual = new java.sql.Date(fecha.getTime());	
		
		this.cliente = cliente;
		this.modeloViaje = new ModeloViaje(new DAOSQLFactory());
		this.modeloPasajero = new Pasajero(new DAOSQLFactory());
		this.modeloPago = new Pago(new DAOSQLFactory());
		this.modeloPasaje = new Pasaje(new DAOSQLFactory());
		this.modeloPagos_pasaje = new Pagos_Pasaje(new DAOSQLFactory());
		this.modeloPasajes_pasajeros = new Pasaje_Pasajeros(new DAOSQLFactory());
		this.modeloPromocion = new ModeloPromocion(new DAOSQLFactory());
		this.viaje_promocion = new ModeloViaje_Promocion (new DAOSQLFactory());
		
		this.pdf = new GeneratePDF();				
		this.envioCorreo = new EnvioDeCorreo();
		this.mapper = new Mapper();
		this.pasajeros_en_reserva = new ArrayList<PasajeroDTO>();
		this.pasajes_en_tabla = modeloPasaje.obtenerPasajes();
		
		
		this.ventanaVisualizarClientes.getTxtFiltro().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
					char letra = e.getKeyChar();
					dm = (DefaultTableModel) ventanaVisualizarClientes.getModelClientes();
					TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
					ventanaVisualizarClientes.getTablaClientes().setRowSorter(tr);
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
		
		this.ventanaTablaViajes.getTxtFiltro().addKeyListener(new KeyAdapter(){            
		    public void keyTyped(KeyEvent e){
		            char letra = e.getKeyChar();
		            dm = (DefaultTableModel) ventanaTablaViajes.getModelViajes();
		            TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
		           ventanaTablaViajes.getTablaViajes().setRowSorter(tr);
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
		
		this.ventanaPasajero.getTxtFiltroDni().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				char letra = e.getKeyChar();
				if(!Character.isDigit(letra)) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
			}
		});
		
		this.ventanaVisualizarClientes.getBtnConfirmar().addActionListener(c->confirmarSeleccionCliente(c));
		
		this.ventanaTablaViajes.getBtnConfirmar().addActionListener(cv->confirmarSeleccionViaje(cv));
		this.ventanaTablaViajes.getBtnAtras().addActionListener(a->volverVentanaCliente(a));
		
		this.ventanaCargaPasajero.getBtnAgregarPasajero().addActionListener(ap->mostrarVentanaAgregarPasajero(ap));
		this.ventanaCargaPasajero.getBtnEliminarPasajero().addActionListener(ep->eliminarPasajero(ep));
		this.ventanaCargaPasajero.getBtnConfirmar().addActionListener(ap->confirmarPasajeros(ap));
		this.ventanaCargaPasajero.getBtnAtras().addActionListener(a->volverVentanaViaje(a));
	
		this.ventanaPasajero.getBtnCargarDatos().addActionListener(cd->cargarDatosPasajero(cd));
		
		/* Filtros */
		this.ventanaPasajero.getTxtNombre().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
					char letra = e.getKeyChar();
					if(Character.isDigit(letra)) {
						Toolkit.getDefaultToolkit().beep();
						e.consume();
					}
			}
		});
		this.ventanaPasajero.getTxtApellido().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				char letra = e.getKeyChar();
				if(Character.isDigit(letra)) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
			}
		});
		this.ventanaPasajero.getTxtDni().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				char letra = e.getKeyChar();
				if(!Character.isDigit(letra)) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
			}
		});
		this.ventanaPasajero.getTxtTelefono().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				char letra = e.getKeyChar();
				if(!Character.isDigit(letra)) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
			}
		});
		/* Fin Filtrado*/
		
		this.ventanaPasajero.getBtnAplicarBusqueda().addActionListener(af->aplicarFiltro(af));
		
		this.ventanaPago.getBtnPago().addActionListener(pago->darAltaDelPago(pago));
		this.ventanaPago.getBtnAtras().addActionListener(vc->volverVentanaCargaPasajero(vc));
		
		this.ventanaConfirmacionPasaje.getBtnGenerarPasaje().addActionListener(dp->darAltaDeUnPasaje(dp));
		this.ventanaConfirmacionPasaje.getBtnAtras().addActionListener(dp->volverVentanaPago(dp));

		this.ventanaComprobante.getBtnPdfReserva().addActionListener(cr->mostrarComprobanteReserva(cr));
		this.ventanaComprobante.getBtnPdfPago().addActionListener(cp->mostrarComprobantePago(cp));
	
		//Ventana Edición de Pasaje
		this.ventanaVisualizarPasaje.getBtnImprimirComprobante().addActionListener(s->imprimirComprobantePasaje(s));
		this.ventanaVisualizarPasaje.getBtnVerPagos().addActionListener(vp->verTablaPagos(vp));
		this.ventanaVisualizarPasaje.getBtnPagar().addActionListener(p->pagarPasaje(p));

		this.ventanaTablaPagos.getBtnImprimirComprobante().addActionListener(i->imprimirComprobante(i));
		this.ventanaCancelacionPasaje.getBtnAceptar().addActionListener(cp->cancelarPasaje(cp));
		this.administrativoLogueado = administrativoLogueado;
		this.editarPago = true;
		this.modeloPunto = new ModeloPunto(new DAOSQLFactory());
	}
	

	private void pagarPasaje(ActionEvent p) {
		this.ventanaPago.mostrarVentana(true);
	}

	private void aplicarFiltro(ActionEvent af) {
		String dni = ventanaPasajero.getTxtFiltroDni().getText();
		if(cliente.getClienteByDni(dni)!=null){
		ClienteDTO clienteDTO = cliente.getClienteByDni(dni);
		this.ventanaPasajero.getTxtNombre().setText(clienteDTO.getNombre());
		this.ventanaPasajero.getTxtApellido().setText(clienteDTO.getApellido());
		this.ventanaPasajero.getTxtDni().setText(clienteDTO.getDni());
		this.ventanaPasajero.getDateFechaNacimiento().setDate(clienteDTO.getFechaNacimiento());
		this.ventanaPasajero.getTxtTelefono().setText(clienteDTO.getMedioContacto().getTelefonoCelular());
		this.ventanaPasajero.getTxtEmail().setText(clienteDTO.getMedioContacto().getEmail());
		}else
		if (modeloPasajero.getPasajeroByDni(dni)!=null){
			
		PasajeroDTO pasajeroDTO = modeloPasajero.getPasajeroByDni(dni);
		this.ventanaPasajero.getTxtNombre().setText(pasajeroDTO.getNombre());
		this.ventanaPasajero.getTxtApellido().setText(pasajeroDTO.getApellido());
		this.ventanaPasajero.getTxtDni().setText(pasajeroDTO.getDni());
		this.ventanaPasajero.getDateFechaNacimiento().setDate(pasajeroDTO.getFechaNacimiento());
		this.ventanaPasajero.getTxtTelefono().setText(pasajeroDTO.getTelefono());
		this.ventanaPasajero.getTxtEmail().setText(pasajeroDTO.getEmail());
		}else{
			JOptionPane.showMessageDialog(ventanaPasajero, "No existe ningún cliente ni pasajero con ese DNI", "Filtro", 0);
		}
	}
	
	public void iniciar(){
		this.llenarTablaClientes();
		this.llenarTablaPasajes();
	}
	
	/*----------------------------------Filtro Cliente------------------------------------*/
	public List<ClienteDTO> filtrarDniSegun(String dniCliente) {
		List<ClienteDTO> resultado = new ArrayList<ClienteDTO>();
		this.clientes_en_tabla = cliente.obtenerClientes();
		for (int i = 0; i < clientes_en_tabla.size(); i++) {
			if (clientes_en_tabla.get(i).getDni().equals(dniCliente)) {
				resultado.add(clientes_en_tabla.get(i));
			}
			this.llenarTablaClientes();
		}
		if (resultado.size() == 0) {
			JOptionPane.showMessageDialog(ventanaVisualizarClientes, "No existe ningún cliente con ese DNI.", "Filtro", 0);
		}
		return resultado;
	}
	
	private void buscarDniEnTabla(String buscarDni){
		 dm = (DefaultTableModel) ventanaVisualizarClientes.getModelClientes();
	        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
	        ventanaVisualizarClientes.getTablaClientes().setRowSorter(tr);
	        tr.setRowFilter(RowFilter.regexFilter(buscarDni));
	}
	
	/*--------------------------------Fin de Filtro Cliente------------------------------------*/
	
	/*El personal administrativo debe seleccionar un cliente*/
	private void confirmarSeleccionCliente(ActionEvent c) {
		this.pasajeros_en_reserva.clear();
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
		this.ventanaPago.limpiarCampos();
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
							mapper.parseToString(this.clientes_en_tabla.get(i).getFechaNacimiento()),
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
			
		this.viajes_en_tabla = modeloViaje.obtenerViajes();
			
		for (int i = 0; i < this.viajes_en_tabla.size(); i++){
			Object[] fila = {this.viajes_en_tabla.get(i).getCiudadOrigen().getNombre(),
							this.viajes_en_tabla.get(i).getCiudadDestino().getNombre(),
							mapper.parseToString(this.viajes_en_tabla.get(i).getFechaSalida()),
							mapper.parseToString(this.viajes_en_tabla.get(i).getFechaLlegada()),
							this.viajes_en_tabla.get(i).getHoraSalida(),
							this.viajes_en_tabla.get(i).getHorasEstimadas(),
							this.viajes_en_tabla.get(i).getCapacidad(),
							this.viajes_en_tabla.get(i).getTransporte().getNombre(),
							"$ "+this.viajes_en_tabla.get(i).getPrecio()					
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
					mapper.parseToString(pasajeros_en_reserva.get(i).getFechaNacimiento()),
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
					mapper.parseToString(pasajeros_en_reserva.get(i).getFechaNacimiento())
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
		if(pagos.size()==0){
			this.ventanaTablaPagos.getBtnImprimirComprobante().setVisible(false);
		}
		for(int i=0; i < pagos.size();i++){
			if(pagos.get(i).getPasaje().getIdPasaje() == idPasaje){
				Object[] fila = { 
						mapper.parseToString(pagos.get(i).getPago().getFechaPago()),
						"$ "+pagos.get(i).getPago().getMonto(),
						pagos.get(i).getPago().getIdFormaPago().getTipo(),
						pagos.get(i).getPago().getAdministrativo().getNombre() 
				
			};
			this.ventanaTablaPagos.getModelPagos().addRow(fila);
			}
		}
	}
	/*Fin de Llenar tablas*/
	
	private void mostrarVentanaAgregarPasajero(ActionEvent ap) {
		this.ventanaPasajero.limpiarCampos();
		this.ventanaPasajero.setVisible(true);		
	}

	/*Cargamos los datos del pasajero*/
	private void cargarDatosPasajero(ActionEvent cd) {
		if(validarCamposRegistrarPasajero()){
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
		pasajeros_en_reserva.add(nuevoPasajero); 
		this.ventanaPasajero.limpiarCampos();
		this.ventanaPasajero.dispose();
		llenarTablaDePasajeros();
		}else{
			JOptionPane.showMessageDialog(null, "Verifique los campos", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private boolean validarCamposRegistrarPasajero(){
		if (camposLlenosRegistrarPasajero()) {
			this.ventanaPasajero.getTxtNombre().setText(Validador.validarCampo(this.ventanaPasajero.getTxtNombre().getText()));
			this.ventanaPasajero.getTxtApellido().setText(Validador.validarCampo(this.ventanaPasajero.getTxtApellido().getText()));
			return Validador.esSoloLetras(this.ventanaPasajero.getTxtNombre().getText()) &&
				Validador.esSoloLetras(this.ventanaPasajero.getTxtApellido().getText()) &&
				Validador.esDniValido(this.ventanaPasajero.getTxtDni().getText()) &&
				(Validador.esTelefonoCelularValido(this.ventanaPasajero.getTxtTelefono().getText()) || this.ventanaPasajero.getTxtTelefono().getText().isEmpty()) &&	
				(Validador.esMailValido(this.ventanaPasajero.getTxtEmail().getText()) || this.ventanaPasajero.getTxtEmail().getText().isEmpty());
		}
		return camposLlenosRegistrarPasajero() ? true : false;
	}
	
	private boolean camposLlenosRegistrarPasajero(){
		if (ventanaPasajero.getTxtNombre().getText().isEmpty() ||
				ventanaPasajero.getTxtApellido().getText().isEmpty() ||
				ventanaPasajero.getTxtDni().getText().isEmpty() ||				
				(ventanaPasajero.getDateFechaNacimiento().getDate()== null) 
			){
				return false;
			}
			return true;
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
		String[] formasPagos = new String[formasPagosDTO.size()+1]; 
		formasPagos[0]="Seleccione forma de pago";
		for(int i=0; i < formasPagosDTO.size();i++){
			String rango = formasPagosDTO.get(i).getTipo();
			formasPagos [i+1] = rango;
		}
		this.ventanaPago.getComboBoxFormaPago().setModel(new DefaultComboBoxModel(formasPagos));
	}
	
	private void darAltaDelPago(ActionEvent cp)  {
// Validar que se haya seleccionado una forma de pago 
			FormaPago f = new FormaPago(new DAOSQLFactory());
			this.ventanaPago.getRadioReservaSinPagar().setVisible(true);
			
			FormaPagoDTO formaPago = f.getFormaPagoByName(ventanaPago.getComboBoxFormaPago().getSelectedItem().toString());
			Calendar currenttime = Calendar.getInstance();
			
			pagoDTO = new PagoDTO();	
			pagoDTO.setIdFormaPago(formaPago);
			pagoDTO.setAdministrativo(administrativoLogueado);
			pagoDTO.setMonto(new BigDecimal(this.ventanaPago.getTextImporteTotal().getText()));	
			pagoDTO.setFechaPago(new Date((currenttime.getTime()).getTime()));
			
			
	
			if (editarPago){
				System.out.println("editaPago");
				this.ventanaPago.setVisible(false);
				mostrarVentanaConfirmacionPasaje();
			}else{ 

				modeloPago.agregarPago(pagoDTO);
<<<<<<< src/main/java/presentacion/controlador/ControladorPasaje.java

				pagos_pasajeDTO = new Pagos_PasajeDTO();
				PagoDTO pagoPasaje = modeloPago.getUltimoRegistroPago();
				pagos_pasajeDTO.setPago(pagoPasaje);
				pagos_pasajeDTO.setPasaje(pasajeAEditar);
				modeloPagos_pasaje.agregarPagoPasaje(pagos_pasajeDTO);
		
				pasajeAEditar.setMontoAPagar(pasajeAEditar.getMontoAPagar().subtract(pagoDTO.getMonto()));
				pasajeAEditar.setEstadoDelPasaje(estadoPasaje(pasajeAEditar.getMontoAPagar()));
				
				modeloPasaje.editarPasaje(pasajeAEditar);

				this.ventanaPago.limpiarCampos();
				this.ventanaPago.mostrarVentana(false);
				this.ventanaVisualizarPasaje.mostrarVentana(false);
				reportePago();
=======
			
			pagos_pasajeDTO = new Pagos_PasajeDTO();
			PagoDTO pagoPasaje = modeloPago.getUltimoRegistroPago();
			pagos_pasajeDTO.setPago(pagoPasaje);
			pagos_pasajeDTO.setPasaje(pasajeAEditar);
			modeloPagos_pasaje.agregarPagoPasaje(pagos_pasajeDTO);
	
			pasajeAEditar.setMontoAPagar(pasajeAEditar.getMontoAPagar().subtract(pagoDTO.getMonto()));
			pasajeAEditar.setEstadoDelPasaje(estadoPasaje(pasajeAEditar.getMontoAPagar()));
			
			modeloPasaje.editarPasaje(pasajeAEditar);
// SUMAMOS LOS PUNTOS DEL CLIENTE
			if(pasajeAEditar.getEstadoDelPasaje().getDescripcion().equals("Vendido"))
				calcularPuntos(pasajeAEditar.getCliente(),pasajeAEditar.getMontoAPagar());
				
			this.ventanaPago.limpiarCampos();
			this.ventanaPago.mostrarVentana(false);
			this.ventanaVisualizarPasaje.mostrarVentana(false);
				
			reportePago();
			this.llenarTablaPasajes();
>>>>>>> src/main/java/presentacion/controlador/ControladorPasaje.java
			}
	}
	
	private void calcularPuntos(ClienteDTO cliente, BigDecimal montoAPagar) {
		
        String[] parts = montoAPagar.toString().split("\\.");
        Integer parteEntera= Integer.parseInt(parts[0]);
        Integer parteDecimal= Integer.parseInt(parts[1]);
        
        
        // BUSCAR EL REGIMEN DE LA BASE
        ModeloRegimenPunto modeloPuntos = new ModeloRegimenPunto(new DAOSQLFactory());
        RegimenPuntoDTO regimen = modeloPuntos.obtenerUltimoRegistro();
        PuntoDTO punto = new PuntoDTO();
        
        // MONTO PAGADO X REGIMEN.PUNTO / REGIMEN.ARS
        punto.setPuntos((parteEntera*regimen.getPunto())/regimen.getARS());
        
        //FECHA ACTUAL + DURACION
        Calendar calendar = Calendar.getInstance();
        java.sql.Date ourJavaDateObject = new java.sql.Date(calendar.getTime().getTime());
        calendar.setTime(ourJavaDateObject); 
        calendar.add(Calendar.MONTH, regimen.getVencimiento());
        Date vencimiento = (Date) calendar.getTime();
        punto.setVencimiento(vencimiento);

        //SETEAR EL PUNTO AL CLIENTE
        cliente.getPuntos().add(punto);
        punto.setCliente(cliente);
        
        //CALCULAR EL TOTAL DE PUNTO TENIENDO EN CUENTA EL VENCIMIENTO DE LOS PUNTOS
        int puntosAux = cliente.getTotalPuntos();
        for(PuntoDTO p:cliente.getPuntos())
        		if(!estaVencido(p.getVencimiento()))
        				puntosAux += p.getPuntos();

        // SET PUNTO AL CLIENTE

        cliente.setTotalPuntos(puntosAux);
        //GUARDARLO EN MODELO
        this.modeloPunto.agregarPunto(punto);
        
        
        //TODO:http://javaeefuncional.blogspot.com/2015/11/sumar-dias-una-fecha-date-in-java.html
        /*
         * Calendar cal = Calendar.getInstance(); 
                 cal.setTime(yourDAte); 
                 cal.add(Calendar.MONTH, 5);
                 nuevaFecha = cal.getTime();
         */
        
        
        
	}

	private boolean estaVencido(java.sql.Date fechaDePunto) {
		java.util.Date fecha = new java.util.Date(); 
		java.sql.Date fechaActual= new java.sql.Date(fecha.getTime());	
		
		if(fechaActual.before(fechaDePunto))//es un evento futuro
			return false;
		return true;
	}

	private void mostrarVentanaConfirmacionPasaje(){
		this.ventanaConfirmacionPasaje.mostrarVentana(true);
		this.ventanaConfirmacionPasaje.getTxtCliente().setText(""+clienteSeleccionado.getNombre()+ " "+ clienteSeleccionado.getApellido());
		this.ventanaConfirmacionPasaje.getTxtDni().setText(""+clienteSeleccionado.getDni());
		this.ventanaConfirmacionPasaje.getTxtOrigen().setText(" "+ viajeSeleccionado.getPaisOrigen().getNombre()+ ", "+viajeSeleccionado.getProvinciaOrigen().getNombre()+", "+viajeSeleccionado.getCiudadOrigen().getNombre());
		this.ventanaConfirmacionPasaje.getTxtDestino().setText(" "+ viajeSeleccionado.getPaisDestino().getNombre()+ ", "+viajeSeleccionado.getProvinciaDestino().getNombre()+", "+viajeSeleccionado.getCiudadDestino().getNombre());
		this.ventanaConfirmacionPasaje.getTxtFormaPago().setText(""+pagoDTO.getIdFormaPago().getTipo());
		this.ventanaConfirmacionPasaje.getTxtPagado().setText("$ "+pagoDTO.getMonto());
		this.ventanaConfirmacionPasaje.getTxtTotal().setText("$ "+calcularMontoDePasaje());
		llenarTablaDePasajerosConfirmarPasaje();
	}
		
	private void mostrarVentanaComprobante(){
		this.ventanaComprobante.mostrarVentana(true);
		this.ventanaComprobante.getTxtCliente().setText(""+clienteSeleccionado.getNombre()+ " "+ clienteSeleccionado.getApellido());
		this.ventanaComprobante.getTxtDni().setText(""+clienteSeleccionado.getDni());
		this.ventanaComprobante.getTxtCodigo().setText(""+pasajeDTO.getNumeroComprobante());
		this.ventanaComprobante.getTxtOrigenViaje().setText(" "+ viajeSeleccionado.getPaisOrigen().getNombre()+ ", "+viajeSeleccionado.getProvinciaOrigen().getNombre()+", "+viajeSeleccionado.getCiudadOrigen().getNombre());
		this.ventanaComprobante.getTxtDestinoViaje().setText(" "+ viajeSeleccionado.getPaisDestino().getNombre()+ ", "+viajeSeleccionado.getProvinciaDestino().getNombre()+", "+viajeSeleccionado.getCiudadDestino().getNombre());
		this.ventanaComprobante.getTxtImportePagado().setText("$ "+ pagoDTO.getMonto());
		this.ventanaComprobante.getTxtValorViaje().setText("$ "+calcularMontoDePasaje());
	}
	
	
	private void darAltaDeUnPasaje(ActionEvent dp) {
		viajeDTO = viajeSeleccionado;
		BigDecimal valorViaje = calcularMontoDePasaje();

		ClienteDTO cliente = clienteSeleccionado;
		EstadoPasajeDTO estadoPasaje = calcularEstadoPasaje();
		List<PasajeroDTO> pasajeros = pasajeros_en_reserva;
		
		viajeDTO.setCapacidad(viajeSeleccionado.getCapacidad()-pasajeros.size()); //Restamos la capacidad del viaje segun la cantidad de pasajeros
		modeloViaje.editarViaje(viajeDTO);
		modeloPago.agregarPago(pagoDTO);
		
		PagoDTO pagoPasaje = modeloPago.getUltimoRegistroPago();
		BigDecimal montoAPagar = valorViaje.subtract(pagoDTO.getMonto());
		String numeroComprobante = Validador.getNumeroComprobante(6);
		
		pasajeDTO = new PasajeDTO(0,numeroComprobante,viajeDTO,administrativoLogueado,cliente,calcularFechaReserva(viajeDTO.getFechaSalida()),valorViaje,montoAPagar,estadoPasaje,
				pasajeros_en_reserva,"",null);
		
		modeloPasaje.agregarPasaje(pasajeDTO);
		
		PasajeDTO pDTO = modeloPasaje.getUltimoRegistroPasaje();
		
		pagos_pasajeDTO = new Pagos_PasajeDTO();
		pagos_pasajeDTO.setPago(pagoPasaje);
		pagos_pasajeDTO.setPasaje(pDTO);
		modeloPagos_pasaje.agregarPagoPasaje(pagos_pasajeDTO);

		//		Vinculamos el pasaje con los pasajeros
//		Agregamos los pasajeros 
		for(PasajeroDTO pasajeroDTO : pasajeros_en_reserva) {
			modeloPasajero.agregarPasajero(pasajeroDTO);
			PasajeroDTO pasajeroRegistro = modeloPasajero.getUltimoRegistroPasajero();
			
			Pasaje_PasajerosDTO pasaje_pasajeros = new Pasaje_PasajerosDTO();
			pasaje_pasajeros.setIdPasaje(pDTO.getIdPasaje());
			pasaje_pasajeros.setIdPasajero(pasajeroRegistro.getIdPasajero());
			
			modeloPasajes_pasajeros.agregarPasajePasajero(pasaje_pasajeros);
		}
		verificarSumaDePuntosDeCliente(pasajeDTO);
		
		generarVoucherMail(pasajeDTO,cliente);
		this.ventanaConfirmacionPasaje.setVisible(false);
		this.pasajeros_en_reserva.clear();
		this.llenarTablaDePasajeros();
		this.ventanaPago.limpiarCampos();
		mostrarVentanaComprobante();
		this.llenarTablaPasajes();
		
	}

	private void verificarSumaDePuntosDeCliente(PasajeDTO pasajeDTO2) {
		if(pasajeDTO.getEstadoDelPasaje().getDescripcion().equals("Vendido"))
			calcularPuntos(pasajeDTO.getCliente(),pasajeDTO.getMontoAPagar());		
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
		BigDecimal valorFinal;
		BigDecimal Valor1 = this.viajeSeleccionado.getPrecio();
		totalaPagar = Valor1;
		valorFinal = totalaPagar.multiply(new BigDecimal(pasajeros_en_reserva.size()));
		this.precioOriginal = valorFinal;
		
		for(Viaje_PromocionDTO vp : viaje_promocion.obtenerViajePromocion()) {
			if(vp.getIdViaje() == viajeSeleccionado.getIdViaje()) {
				for(PromocionDTO p : modeloPromocion.obtenerPromocion()) {
					if(p.getIdPromocion() == vp.getIdPromocion()) {
						if(promocionActiva(p)) {
							this.ventanaPago.mostrarDatosPromocion();
							this.ventanaPago.setLblDatoMontoOriginal("$ "+this.precioOriginal.toString());
							this.porcentajeDescuento = p.getPorcentaje();
							this.ventanaPago.setLblDatoPorcentajeDescuento("-"+this.porcentajeDescuento+""+" %");
							valorFinal = calcularMontoDePasajeConDescuento(valorFinal, p.getPorcentaje());
						}
					}
				}
			}
		}	
		return valorFinal;
	}
	
	public boolean promocionActiva(PromocionDTO p) {
		boolean activa = false;
		boolean noVencida = false;
		for(PromocionDTO x : modeloPromocion.obtenerPromocion()) {
			if(x.getIdPromocion()==p.getIdPromocion()) {
				if(x.getEstado().equals("activa")) {
					activa = true;
					if(this.fechaActual.before(x.getFechaVencimiento()))
						noVencida = true;
				}
			}
		}
		if(activa && noVencida)
			return true;
		else
			return false;
		
	}
		
	
	private BigDecimal calcularMontoDePasajeConDescuento(BigDecimal valor, int porcentaje) {
		BigDecimal nuevoValor = new BigDecimal(100-porcentaje);
		return valor.multiply(nuevoValor).divide(new BigDecimal(100));
	}
	
	private EstadoPasajeDTO calcularEstadoPasaje() {
		EstadoPasaje estado = new EstadoPasaje(new DAOSQLFactory());
		EstadoPasajeDTO ret;
		if(totalaPagar.compareTo(pagoDTO.getMonto())==0){ 
			ret = estado.getFormaPagoByName("Vendido");
		}
		else {
			if(pagoDTO.getMonto().equals(new BigDecimal(0))) {
				ret = estado.getFormaPagoByName("Pendiente");
			}
			else{
				ret = estado.getFormaPagoByName("Reservado");		
			}
		}
		return ret;
	}
	
	private EstadoPasajeDTO estadoPasaje(BigDecimal monto){
		EstadoPasaje estado = new EstadoPasaje(new DAOSQLFactory());
		EstadoPasajeDTO ret;
		if(monto.compareTo(new BigDecimal(0))==0){ 
			ret = estado.getFormaPagoByName("Vendido");
		}
		else {
			ret = estado.getFormaPagoByName("Reservado");
			}
		return ret;
	}
	private void mostrarComprobanteReserva(ActionEvent cr){
		Reporte reporte = new Reporte();
		reporte.reporteReserva(pagos_pasajeDTO);
		reporte.mostrar();
	}
	
	private void generarVoucherMail(PasajeDTO pasaje,ClienteDTO cliente){			
		this.pdf.createPDF(pasaje, cliente);//(pasaje,cliente); // se crea el pdf en resource				
<<<<<<< src/main/java/presentacion/controlador/ControladorPasaje.java
//			TODO: this.envioCorreo.enviarAdjunto(cliente.getMail());				
=======
		//	TODO: this.envioCorreo.enviarAdjunto(cliente.getMail());				
>>>>>>> src/main/java/presentacion/controlador/ControladorPasaje.java
		this.envioCorreo.enviarAdjunto(cliente.getMail());				
	}
	
	private void reportePago(){
		if(pagoDTO.getMonto().compareTo(new BigDecimal(0))!=0){
			Reporte reporte = new Reporte();
			reporte.reportePago(pagos_pasajeDTO);
			reporte.mostrar();
		}else{
			JOptionPane.showMessageDialog(null, "Solo se realizó reserva", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void mostrarComprobantePago(ActionEvent cp){
		reportePago();
	}
	
	public void editarPasaje(int filaSeleccionada){
		verDatosDelPasaje(filaSeleccionada);
		pasajeAEditar = this.pasajes_en_tabla.get(filaSeleccionada);
	}
	
	private void verDatosDelPasaje(int filaSeleccionada) {
		if (filaSeleccionada != -1){
			this.ventanaVisualizarPasaje.getBtnPagar().setVisible(true);
			this.ventanaVisualizarPasaje.getTxtClienteDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getCliente().getNombre());
			this.ventanaVisualizarPasaje.getTxtCodigoDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getNumeroComprobante());
			this.ventanaVisualizarPasaje.getTxtDniDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getCliente().getDni());
			this.ventanaVisualizarPasaje.getTxtOrigenDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getPaisOrigen().getNombre()+", "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getCiudadOrigen().getNombre()+ ", "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getCiudadOrigen().getNombre());
			this.ventanaVisualizarPasaje.getTxtDestinoDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getPaisDestino().getNombre()+", "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getProvinciaDestino().getNombre()+ ", "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getCiudadDestino().getNombre());
			this.ventanaVisualizarPasaje.getTxtTransporteDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getTransporte().getNombre());
			this.ventanaVisualizarPasaje.getTxtEstadoPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getEstadoDelPasaje().getNombre());
			this.ventanaVisualizarPasaje.getTxtMontoDelPasaje().setText("$ "+this.pasajes_en_tabla.get(filaSeleccionada).getValorViaje());
			this.ventanaVisualizarPasaje.getTxtImporteDebePasaje().setText("$ "+this.pasajes_en_tabla.get(filaSeleccionada).getMontoAPagar());
			this.ventanaVisualizarPasaje.getTxtMotivoCancelacion().setVisible(false);
			this.ventanaVisualizarPasaje.getLblMotivoCancelacion().setVisible(false);
		
			llenarTablaPagos(pasajes_en_tabla.get(filaSeleccionada).getIdPasaje());
			pagarPasaje(filaSeleccionada);
			
			if(this.pasajes_en_tabla.get(filaSeleccionada).getEstadoDelPasaje().getIdEstadoPasaje()==4){
				this.ventanaVisualizarPasaje.getTxtMotivoCancelacion().setVisible(true);
				this.ventanaVisualizarPasaje.getLblMotivoCancelacion().setVisible(true);
				this.ventanaVisualizarPasaje.getTxtMotivoCancelacion().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getMotivoCancelacion());
				this.ventanaVisualizarPasaje.getLblMontoAPagar().setVisible(false);
				this.ventanaVisualizarPasaje.getTxtImporteDebePasaje().setVisible(false);
				this.ventanaVisualizarPasaje.getBtnPagar().setVisible(false);
				this.ventanaVisualizarPasaje.getBtnVerPagos().setVisible(false);
				this.ventanaVisualizarPasaje.getBtnImprimirComprobante().setVisible(false);
			}else{
				this.ventanaVisualizarPasaje.getLblMontoAPagar().setVisible(true);
				this.ventanaVisualizarPasaje.getTxtImporteDebePasaje().setVisible(true);
				this.ventanaVisualizarPasaje.getBtnPagar().setVisible(true);
				this.ventanaVisualizarPasaje.getBtnVerPagos().setVisible(true);
				this.ventanaVisualizarPasaje.getBtnImprimirComprobante().setVisible(true);
			}
			
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

	private void pagarPasaje(int filaSeleccionada) {
		cargarComboBoxFormaDePago();
		this.ventanaPago.getRadioReservaSinPagar().setVisible(false);
		this.ventanaPago.getBtnAtras().setVisible(false);
		this.ventanaPago.getLblMontoaPagar().setText(""+this.pasajes_en_tabla.get(filaSeleccionada).getMontoAPagar());
		this.editarPago = false;
	}

	private void verTablaPagos(ActionEvent vp) {
		this.ventanaTablaPagos.mostrarVentana(true);
		this.ventanaTablaPagos.getBtnImprimirComprobante().setVisible(true);

	}
	

	private void imprimirComprobante(ActionEvent i) {
		int filaSeleccionada = this.ventanaTablaPagos.getTablaPagos().getSelectedRow();
		if (filaSeleccionada != -1){
			Reporte reporte = new Reporte();
			Pagos_PasajeDTO pagoDTO = modeloPagos_pasaje.getPasajeById(pasajeAEditar.getIdPasaje());
			reporte.reportePago(pagoDTO);
			reporte.mostrar();
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}
			
	private void imprimirComprobantePasaje(ActionEvent s) {
		Reporte reporte = new Reporte();
		Pagos_PasajeDTO pagoDTO = modeloPagos_pasaje.getPasajeById(pasajeAEditar.getIdPasaje());
		reporte.reporteReserva(pagoDTO);
		reporte.mostrar();
	}

	
	public void eliminarPasaje(int filaSeleccionada){
		int confirm = JOptionPane.showOptionDialog(
		            null,"¿Estás seguro que quieres cancelar el pasaje?", 
				             "Cancelar pasaje", JOptionPane.YES_NO_OPTION,
				             JOptionPane.ERROR_MESSAGE, null, null, null);
	 if (confirm == 0){
		 pasajeACancelar = pasajes_en_tabla.get(filaSeleccionada);
		 this.ventanaCancelacionPasaje.mostrarVentana(true);
	 }
	}
	
	public void cancelarPasaje(ActionEvent cp){
		Calendar currenttime = Calendar.getInstance();
		pasajeACancelar.setEstadoDelPasaje(new EstadoPasajeDTO(4,"Cancelado","Se cancelo el pasaje"));
		pasajeACancelar.setMotivoCancelacion(this.ventanaCancelacionPasaje.getTxtMotivoCancelacion().getText());
		pasajeACancelar.setDateCancelacion(new Date(currenttime.getTime().getTime()));
		modeloPasaje.editarPasaje(pasajeACancelar);
		this.ventanaCancelacionPasaje.mostrarVentana(false);
		this.llenarTablaPasajes();
	}
	
	
	
	private void llenarTablaPasajes(){
		this.vistaAdministrativo.getPanelPasaje().getModelReservas().setRowCount(0); //Para vaciar la tabla
		this.vistaAdministrativo.getPanelPasaje().getModelReservas().setColumnCount(0);
		this.vistaAdministrativo.getPanelPasaje().getModelReservas().setColumnIdentifiers(this.vistaAdministrativo.getPanelPasaje().getNombreColumnasReservas());

		this.pasajes_en_tabla = modeloPasaje.obtenerPasajes();
			
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
							"$ "+this.pasajes_en_tabla.get(i).getValorViaje(),
							this.pasajes_en_tabla.get(i).getViaje().getTransporte().getNombre(),
							this.pasajes_en_tabla.get(i).getEstadoDelPasaje().getNombre()
			};
							this.vistaAdministrativo.getPanelPasaje().getModelReservas().addRow(fila);
		}		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}
//	public static void main(String[] args) {
//		ControladorPasaje c = new ControladorPasaje(new VentanaVisualizarClientes(), new Cliente(new DAOSQLFactory()), new AdministrativoDTO());
//		BigDecimal valor = new BigDecimal (4500);
//		int porcentajeDescuento = 45;
//		System.out.println(c.calcularMontoDePasajeConDescuento(valor, porcentajeDescuento).toString()+" precio con DESCUENTO!");
//		
//	}
}