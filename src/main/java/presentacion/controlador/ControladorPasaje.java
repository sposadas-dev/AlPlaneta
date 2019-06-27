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
import dto.CondicionDeCancelacionDTO;
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
import modelo.ModeloCondicionDeCancelacion;
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
import presentacion.vista.administrativo.VentanaPagoPuntos;
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
	private VentanaCancelacionPasaje ventanaCancelacionPasaje;
	private VentanaPagoPuntos ventanaPagoPuntos;
	private ModeloCondicionDeCancelacion modeloCondicionDeCancelacion;
	
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
	private Cliente modeloCliente;
	private Pasajero modeloPasajero;
	private ModeloViaje modeloViaje;
	private Pago modeloPago;
	private Pasaje modeloPasaje;
	private Pagos_Pasaje modeloPagos_pasaje;
	private Pasaje_Pasajeros modeloPasajes_pasajeros;
	private ModeloPromocion modeloPromocion;
	private ModeloViaje_Promocion viaje_promocion;
	/*Fin de modelos*/
	private Integer valorDelViajeEnPuntos;
	private BigDecimal totalaPagar;
	private BigDecimal precioOriginal;
	private BigDecimal valorFinal;
	private int porcentajeDescuento;
	private AdministrativoDTO administrativoLogueado;
	private PagoDTO pagoDTO;
	private boolean noEditarPago;
	private ViajeDTO viajeDTO;
	private java.util.Date fechaActual;

	private DefaultTableModel dm;
	private StringBuilder cad= new StringBuilder();
	private ModeloPunto modeloPunto;
	private ModeloRegimenPunto modeloRegimenPunto;
	private String aceptada="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private ControladorTarjeta controladorTarjeta;
	private VentanaTarjeta ventanaTarjeta;
	private PromocionDTO promocionSeleccionada;
	private int cantPasajerosEnPromo;
	
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
		this.ventanaPagoPuntos = VentanaPagoPuntos.getInstance();
		
		java.util.Date fecha = new java.util.Date(); 
		this.fechaActual = new java.sql.Date(fecha.getTime());	
		
		this.modeloCliente = cliente;
		this.modeloViaje = new ModeloViaje(new DAOSQLFactory());
		this.modeloPasajero = new Pasajero(new DAOSQLFactory());
		this.modeloPago = new Pago(new DAOSQLFactory());
		this.modeloPasaje = new Pasaje(new DAOSQLFactory());
		this.modeloPagos_pasaje = new Pagos_Pasaje(new DAOSQLFactory());
		this.modeloPasajes_pasajeros = new Pasaje_Pasajeros(new DAOSQLFactory());
		this.modeloPromocion = new ModeloPromocion(new DAOSQLFactory());
		this.modeloRegimenPunto = new ModeloRegimenPunto(new DAOSQLFactory());
		this.viaje_promocion = new ModeloViaje_Promocion (new DAOSQLFactory());
		this.modeloCondicionDeCancelacion = new ModeloCondicionDeCancelacion(new DAOSQLFactory());
		
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
		
		//TODO: Realizar el filtro de precios.
		
//		this.ventanaTablaViajes.getComboBoxPrecioDesde().addPropertyChangeListener( new PropertyChangeListener() {
//			@Override
//			public void propertyChange(PropertyChangeEvent e) {
//				if(ventanaTablaViajes.getComboBoxPrecioDesde().getSelectedItem() != null) {
//					Integer desde = ((Integer)e.getNewValue());
//					if(ventanaTablaViajes.getComboBoxPrecioHasta().getSelectedItem() != null){
//						Integer hasta = ((Integer)ventanaTablaViajes.getComboBoxPrecioHasta().getSelectedItem());
//						llenarTablaViajes(modeloViaje.obtenerBetweenPrecio(desde, hasta));
//					}
//				}
//			}
//		});
//		
//		this.ventanaTablaViajes.getComboBoxPrecioHasta().addPropertyChangeListener( new PropertyChangeListener() {
//			@Override
//			public void propertyChange(PropertyChangeEvent e) {
//				if(ventanaTablaViajes.getComboBoxPrecioHasta().getSelectedItem() != null) {
//					Integer hasta = ((Integer)e.getNewValue());
//					if(ventanaTablaViajes.getComboBoxPrecioDesde().getSelectedItem() != null){
//						Integer desde = ((Integer)ventanaTablaViajes.getComboBoxPrecioDesde().getSelectedItem());
//						llenarTablaViajes(modeloViaje.obtenerBetweenPrecio(desde, hasta));
//					}
//				}
//			}
//		});	
		
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
		this.ventanaTablaViajes.getBtnLimpiarFiltros().addActionListener(lf->limpiarFiltros(lf));
		
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
					if(ventanaPasajero.getTxtNombre().getText().length() == 45) {
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
				if(ventanaPasajero.getTxtApellido().getText().length() == 45) {
					e.consume();
				}
			}});
		this.ventanaPasajero.getTxtDni().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				char letra = e.getKeyChar();
				if(!Character.isDigit(letra)) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
				if(ventanaPasajero.getTxtDni().getText().length() == 8) {
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
				if(ventanaPasajero.getTxtTelefono().getText().length() == 45) {
					e.consume();
				}
			}
		});
		
		this.ventanaPasajero.getTxtEmail().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				char letra = e.getKeyChar();
				if(ventanaPasajero.getTxtEmail().getText().length() == 45) {
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
		
//		this.ventanaPago.getPanelPasaje().getCancelCheckBox().addActionListener(ccb->cargarCancelados(ccb));
		this.ventanaPago.getComboBoxFormaPago().addActionListener(fp->verFormaDePago(fp));
		this.ventanaPagoPuntos.getBtnPago().addActionListener(p->pagarConPuntos(p));
		this.ventanaPagoPuntos.getBtnAtras().addActionListener(a->volverVentanaPago(a));
		this.ventanaPago.getBtnIngresarTarjeta().addActionListener(it->ingresarTarjeta(it));
		
		this.noEditarPago = true;
		this.modeloPunto = new ModeloPunto(new DAOSQLFactory());
		
		this.controladorTarjeta = new ControladorTarjeta();
	
	}
	
	private void ingresarTarjeta(ActionEvent it) {
		
		controladorTarjeta.mostrarVentanaTarjeta();
	}
	

	private void pagarConPuntos(ActionEvent p) {
		if(valorDelViajeEnPuntos<=clienteSeleccionado.getTotalPuntos()){
			darAltaDelPagoConPuntos();
		}
		else{
			JOptionPane.showMessageDialog(null, "No tiene los puntos suficientes ", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void verFormaDePago(ActionEvent fp) {
		String formaDePago = ventanaPago.getComboBoxFormaPago().getSelectedItem().toString();
		clienteSeleccionado.setPuntos(modeloPunto.getPuntosByClienteID(clienteSeleccionado));

		if(formaDePago.equals("Puntos")){ // 1: Pago
			this.ventanaPagoPuntos.setVisible(true);
			this.ventanaPago.setVisible(false);
			this.ventanaPagoPuntos.getLblPuntosDelCliente().setText(String.valueOf(clienteSeleccionado.getTotalPuntos()));
			this.ventanaPagoPuntos.getLblCostoDelPasajeEnPuntos().setText(String.valueOf(calcularValorDeViajeEnPuntos(viajeSeleccionado.getPrecio())));
		}
		else if(formaDePago.equals("Tarjeta")) {
		this.ventanaPago.getBtnIngresarTarjeta().setVisible(true);
	}
		
	}

	private Integer calcularValorDeViajeEnPuntos(BigDecimal precio) {
		RegimenPuntoDTO regimen = this.modeloRegimenPunto.obtenerUltimoRegistro();
		
	
		int integerPrecio = precio.multiply(new BigDecimal(	pasajeros_en_reserva.size())).intValue();
		int valorDelRegimen = regimen.getARS();
		this.valorDelViajeEnPuntos = integerPrecio/valorDelRegimen;
		return valorDelViajeEnPuntos;
	}

	private void pagarPasaje(ActionEvent p) {
		this.ventanaPago.mostrarVentana(true);
		this.noEditarPago = false;
		if(!noEditarPago)
			cargarComboBoxFormaDePagoEdicion();
	}

	private void aplicarFiltro(ActionEvent af) {
		String dni = ventanaPasajero.getTxtFiltroDni().getText();
		if(modeloCliente.getClienteByDni(dni)!=null){
		ClienteDTO clienteDTO = modeloCliente.getClienteByDni(dni);
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
		}
		else{
			JOptionPane.showMessageDialog(ventanaPasajero, "No existe ningún cliente ni pasajero con ese DNI", "Filtro", 0);
		}
	}
	
	public void iniciar(){
		this.llenarTablaClientes();
		this.llenarTablaPasajes();
	}
	

	public void limpiarFiltros(ActionEvent lf){
		this.llenarTablaViajes();
	}
	
	/*----------------------------------Filtro Cliente------------------------------------*/
	public List<ClienteDTO> filtrarDniSegun(String dniCliente) {
		List<ClienteDTO> resultado = new ArrayList<ClienteDTO>();
		this.clientes_en_tabla = modeloCliente.obtenerClientes();
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
			if(viajes_en_tabla.get(filaSeleccionada).getCapacidad()!=0) {
				viajeSeleccionado = viajes_en_tabla.get(filaSeleccionada);	
				this.ventanaTablaViajes.mostrarVentana(false);
				this.ventanaCargaPasajero.mostrarVentana(true);
			}
			else{
				JOptionPane.showMessageDialog(null, "No quedan cupos disponibles en este viaje", "Mensaje", JOptionPane.ERROR_MESSAGE);
			}
		}
		else{
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
		this.ventanaCargaPasajero.getModelPasajeros().setRowCount(0);
		this.ventanaCargaPasajero.getModelPasajeros().setColumnCount(0);
		this.ventanaCargaPasajero.getLblPasajerosCargados().setText("");
		this.ventanaTablaViajes.mostrarVentana(true);
	}
	
	private void volverVentanaCargaPasajero(ActionEvent vc) {
		this.ventanaPago.mostrarVentana(false);
		this.ventanaPago.limpiarCampos();
		this.ventanaCargaPasajero.mostrarVentana(true);
	}
	
	private void volverVentanaPago(ActionEvent dp) {
		this.ventanaConfirmacionPasaje.mostrarVentana(false);
		this.ventanaPagoPuntos.setVisible(false);
		this.ventanaPago.mostrarVentana(true);
	}
	/*Fin de métodos - Boton Atras*/
	
	
	/*Métodos Llenar Tablas*/
	private void llenarTablaClientes(){
		this.ventanaVisualizarClientes.getModelClientes().setRowCount(0); //Para vaciar la tabla
		this.ventanaVisualizarClientes.getModelClientes().setColumnCount(0);
		this.ventanaVisualizarClientes.getModelClientes().setColumnIdentifiers(this.ventanaVisualizarClientes.getNombreColumnasClientes());
	
		this.clientes_en_tabla = modeloCliente.obtenerClientes();
		
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
			
//		this.viajes_en_tabla = modeloViaje.obtenerViajes();
		this.viajes_en_tabla = viajesActivos();
			
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
	
	private ArrayList<ViajeDTO> viajesActivos() {
		ArrayList<ViajeDTO> ret = new ArrayList<ViajeDTO>();
		for(ViajeDTO v : modeloViaje.obtenerViajes()) {
			if(v.getEstado().equals("activo")) {
				ret.add(v);
			}
		}
		return ret;
	}

	private void llenarTablaViajes(List<ViajeDTO> viajes){
		this.ventanaTablaViajes.getModelViajes().setRowCount(0); //Para vaciar la tabla
		this.ventanaTablaViajes.getModelViajes().setColumnCount(0);
		this.ventanaTablaViajes.getModelViajes().setColumnIdentifiers(this.ventanaTablaViajes.getNombreColumnas());
		
		for (int i = 0; i < viajes.size(); i++){
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
		
		if(dniExistente(ventanaPasajero.getTxtDni().getText(),ventanaPasajero.getTxtNombre().getText(),
				ventanaPasajero.getTxtApellido().getText(),mapper.parseToString(fechaNacimiento))){
			JOptionPane.showMessageDialog(null, "Los datos ingresados no coinciden con un pasajero registrado con ese DNI", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
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
		int capacidad = this.viajeSeleccionado.getCapacidad(); 
		if(capacidad >= pasajeros_en_reserva.size()) {
			this.ventanaCargaPasajero.setVisible(false);
			cargarComboBoxFormaDePago();
			this.ventanaPago.getLblMontoaPagar().setText("$ "+calcularMontoDePasaje().toString());
			this.ventanaPago.setVisible(true);
		}
		else {
			if(capacidad == 1)
				JOptionPane.showMessageDialog(null, "Error: sólo queda un cupo disponible en este viaje", "Mensaje", JOptionPane.ERROR_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, "Error: sólo quedan "+capacidad+" cupos disponibles en este viaje", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void cargarComboBoxFormaDePago(){
		//ventanaPago.getComboBoxFormaPago().removeAllItems();
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
	
	private void cargarComboBoxFormaDePagoEdicion(){
		//ventanaPago.getComboBoxFormaPago().removeAllItems();
		FormaPago formaPago = new FormaPago(new DAOSQLFactory());
		List<FormaPagoDTO> formaPagosDTO = formaPago.obtenerFormaPago();
		List<FormaPagoDTO> formasPagosDTO = new ArrayList<FormaPagoDTO>();
		
		for(FormaPagoDTO f:formaPagosDTO){
			if(f.getIdFormaPago()!=3)
				formasPagosDTO.add(f);
		}
		
		String[] formasPagos = new String[formasPagosDTO.size()+1]; 
		formasPagos[0]="Seleccione forma de pago";
		for(int i=0; i < formasPagosDTO.size();i++){
			if(formasPagosDTO.get(i).getIdFormaPago()!=3){
				String rango = formasPagosDTO.get(i).getTipo();
				formasPagos [i+1] = rango;
			}
		}
		this.ventanaPago.getComboBoxFormaPago().setModel(new DefaultComboBoxModel(formasPagos));
	}
	
	private void darAltaDelPago(ActionEvent cp)  {
		BigDecimal montoUsuario = new BigDecimal(this.ventanaPago.getTextImporteTotal().getText());
		System.out.println("Pago Directo:" +noEditarPago );
		
		if((noEditarPago && montoUsuario.compareTo(calcularMontoDePasaje())>0) || 
				(!noEditarPago && montoUsuario.compareTo(pasajeAEditar.getMontoAPagar())>0)){
				JOptionPane.showMessageDialog(null, "Error: El monto igresado es mayor al monto a pagar", "Mensaje", JOptionPane.ERROR_MESSAGE);
			}else{
				if(this.ventanaPago.getComboBoxFormaPago().getSelectedIndex()!=0) {
					FormaPago f = new FormaPago(new DAOSQLFactory());
					this.ventanaPago.getRadioReservaSinPagar().setVisible(true);
					
					FormaPagoDTO formaPago = f.getFormaPagoByName(ventanaPago.getComboBoxFormaPago().getSelectedItem().toString());
					Calendar currenttime = Calendar.getInstance();
					
					pagoDTO = new PagoDTO();	
					pagoDTO.setIdFormaPago(formaPago);
					pagoDTO.setAdministrativo(administrativoLogueado);
					pagoDTO.setMonto(new BigDecimal(this.ventanaPago.getTextImporteTotal().getText()));	
					pagoDTO.setFechaPago(new Date((currenttime.getTime()).getTime()));
					
					
					if(formaPago.getIdFormaPago()==2){
						pagoDTO.setIdtarjeta(controladorTarjeta.datosTarjeta()); //AGREGA IDTARJETAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
						pagoDTO.setIdtarjeta(controladorTarjeta.getUltimoRegistro());
						System.out.println("El ID de la tarjeta es:" + pagoDTO.getIdtarjeta().getIdTarjeta());
					}
	
					if (noEditarPago){
						this.ventanaPago.setVisible(false);
						mostrarVentanaConfirmacionPasaje();
					}
					else{ 
						modeloPago.agregarPago(pagoDTO);
						pagos_pasajeDTO = new Pagos_PasajeDTO();
						PagoDTO pagoPasaje = modeloPago.getUltimoRegistroPago();
						pagos_pasajeDTO.setPago(pagoPasaje);
						pagos_pasajeDTO.setPasaje(pasajeAEditar);
						modeloPagos_pasaje.agregarPagoPasaje(pagos_pasajeDTO);
						pasajeAEditar.setMontoAPagar(pasajeAEditar.getMontoAPagar().subtract(pagoDTO.getMonto()));
						pasajeAEditar.setEstadoDelPasaje(estadoPasaje(pasajeAEditar.getMontoAPagar()));
						modeloPasaje.editarPasaje(pasajeAEditar);
						noEditarPago = true;
					
						if(pagoDTO.getIdFormaPago().getIdFormaPago()!= 3){
							verificarSumaDePuntosDeCliente(pasajeAEditar);
							}
					
						this.ventanaPago.limpiarCampos();
						this.ventanaPago.mostrarVentana(false);
						this.ventanaVisualizarPasaje.mostrarVentana(false);
						reportePago();
						this.llenarTablaPasajes();
					}
				}
				else {
					JOptionPane.showMessageDialog(ventanaPasajero, "Seleccione forma de pago", "Filtro", 0);
				}	
			}
	}
	
	private void darAltaDelPagoConPuntos()  {
		restarPuntosViejosDelCliente();
		this.ventanaPagoPuntos.setVisible(false);

		FormaPago f = new FormaPago(new DAOSQLFactory());
		FormaPagoDTO formaPago = f.getFormaPagoByName("Puntos");
		Calendar currenttime = Calendar.getInstance();
		
			
		pagoDTO = new PagoDTO();	
		pagoDTO.setIdFormaPago(formaPago);
		pagoDTO.setAdministrativo(administrativoLogueado);
		pagoDTO.setMonto(viajeSeleccionado.getPrecio().multiply(new BigDecimal(pasajeros_en_reserva.size())));	
		pagoDTO.setFechaPago(new Date((currenttime.getTime()).getTime()));
		
			
		if (noEditarPago){
			this.ventanaPagoPuntos.setVisible(false);
			mostrarVentanaConfirmacionPasaje();
		}
//		else{ 
//			modeloPago.agregarPago(pagoDTO);
//			pagos_pasajeDTO = new Pagos_PasajeDTO();
//			PagoDTO pagoPasaje = modeloPago.getUltimoRegistroPago();
//			pagos_pasajeDTO.setPago(pagoPasaje);
//			pagos_pasajeDTO.setPasaje(pasajeAEditar);
//			modeloPagos_pasaje.agregarPagoPasaje(pagos_pasajeDTO);
//				
//			pasajeAEditar.setMontoAPagar(pasajeAEditar.getMontoAPagar().subtract(pagoDTO.getMonto()));
//			pasajeAEditar.setEstadoDelPasaje(estadoPasaje(pasajeAEditar.getMontoAPagar()));
//						
//			modeloPasaje.editarPasaje(pasajeAEditar);
//			this.ventanaPago.limpiarCampos();
//			this.ventanaPago.mostrarVentana(false);
//			this.ventanaVisualizarPasaje.mostrarVentana(false);
//			reportePago();
//			this.llenarTablaPasajes();
//		}
	}
	
	private void restarPuntosViejosDelCliente() {
		ArrayList<PuntoDTO> puntosDelCliente = modeloPunto.getPuntosAscendente(this.clienteSeleccionado.getIdCliente());
		Integer precioViajeEnPuntos = calcularValorDeViajeEnPuntos(viajeSeleccionado.getPrecio());

		for(PuntoDTO p: puntosDelCliente){
			if(precioViajeEnPuntos >0 && p.getPuntos()<=precioViajeEnPuntos ){
				precioViajeEnPuntos = precioViajeEnPuntos - p.getPuntos();
				this.modeloPunto.borrarPunto(p);
			}else
				if(precioViajeEnPuntos >0 && p.getPuntos()>precioViajeEnPuntos){
					p.setPuntos(p.getPuntos()-precioViajeEnPuntos);
					precioViajeEnPuntos = 0;
					this.modeloPunto.editarPunto(p);
			}
		}
	}

	private void calcularPuntos(ClienteDTO cliente, BigDecimal montoAPagar) {
		String[] parts;
		Integer parteEntera = montoAPagar.intValue();
		if(montoAPagar.toString().contains(".")){  //RECORRER Y VER SI CONTIENE @,@ O @.@
			parts = (montoAPagar+"").split(".");
			parteEntera = Integer.parseInt(parts[0]);
		}
// BUSCAR EL REGIMEN DE LA BASE
        ModeloRegimenPunto modeloPuntos = new ModeloRegimenPunto(new DAOSQLFactory());
        RegimenPuntoDTO regimen = modeloPuntos.obtenerUltimoRegistro();
        PuntoDTO punto = new PuntoDTO();
        
// MONTO PAGADO X REGIMEN.PUNTO / REGIMEN.ARS
        int puntoCalculado = (parteEntera*regimen.getPunto())/regimen.getARS();
        punto.setPuntos(puntoCalculado);
        
//FECHA ACTUAL + DURACION
        Calendar calendar = Calendar.getInstance();
        java.sql.Date ourJavaDateObject = new java.sql.Date(calendar.getTime().getTime());
        calendar.setTime(ourJavaDateObject); 
        calendar.add(Calendar.MONTH, regimen.getVencimiento());
        
        java.util.Date vencimiento = calendar.getTime();
        punto.setVencimiento(convertUtilToSql(vencimiento));

//SETEAR EL PUNTO AL CLIENTE
        cliente.getPuntos().add(punto);
        punto.setCliente(cliente);
        
//CALCULAR EL TOTAL DE PUNTO TENIENDO EN CUENTA EL VENCIMIENTO DE LOS PUNTOS
        int puntosAux = cliente.getTotalPuntos();
        for(PuntoDTO p:cliente.getPuntos())
        		if(estaVencido(p.getVencimiento())){
        			modeloPunto.borrarPunto(p);
        		}else{
        			puntosAux += p.getPuntos();
        		}
// SET PUNTO AL CLIENTE
        cliente.setTotalPuntos(puntosAux);
        
        this.modeloCliente.actualizar(cliente);
//GUARDARLO EN MODELO
        this.modeloPunto.agregarPunto(punto);
        
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
		this.ventanaConfirmacionPasaje.getTxtTotal().setText("$ "+this.totalaPagar);
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
		this.ventanaComprobante.getTxtValorViaje().setText("$ "+this.valorFinal);
	}
	
	private void darAltaDeUnPasaje(ActionEvent dp) {
		viajeDTO = viajeSeleccionado;
		BigDecimal valorViaje = this.valorFinal;
		ClienteDTO cliente = clienteSeleccionado;
		EstadoPasajeDTO estadoPasaje = calcularEstadoPasaje();
		System.out.println(estadoPasaje.getNombre()+" ACA");
		List<PasajeroDTO> pasajeros = pasajeros_en_reserva;
		
		viajeDTO.setCapacidad(viajeSeleccionado.getCapacidad()-pasajeros.size()); //Restamos la capacidad del viaje segun la cantidad de pasajeros
		
		//DESCONTAR STOCK EN PROMO
		//IF EL VIAJE TIENE PROMO:
		if(tienePromo(viajeDTO)){
			promocionSeleccionada.setStock(promocionSeleccionada.getStock()-cantPasajerosEnPromo);
			modeloPromocion.editarPromocion(promocionSeleccionada);
			System.out.println("PROMO SELECCIONADA: "+promocionSeleccionada.getIdPromocion()+", NUEVO STOCK DE LA PROMO: "+promocionSeleccionada.getStock()+", CANT PASAJEROS QUE USARON LA PROMO: "+cantPasajerosEnPromo);
		}
				
		modeloViaje.editarViaje(viajeDTO);
		modeloPago.agregarPago(pagoDTO);
		
		Calendar fecha = Calendar.getInstance();
		java.sql.Date fechaEmision = convertUtilToSql(fecha.getTime()); 
		
		PagoDTO pagoPasaje = modeloPago.getUltimoRegistroPago();
		BigDecimal montoAPagar = valorViaje.subtract(pagoDTO.getMonto());
		String numeroComprobante = Validador.getNumeroComprobante(6);
		
		pasajeDTO = new PasajeDTO(0,fechaEmision,numeroComprobante,viajeDTO,administrativoLogueado,cliente,calcularFechaReserva(viajeDTO.getFechaSalida()),valorViaje,montoAPagar,estadoPasaje,
				pasajeros_en_reserva,"",null,null,false);
		
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
		
		if(pagoPasaje.getIdFormaPago().getIdFormaPago()!= 3){
			verificarSumaDePuntosDeCliente(pasajeDTO);
			}
		
//		generarVoucherMail(pasajeDTO,cliente);
		this.ventanaConfirmacionPasaje.setVisible(false);
		this.pasajeros_en_reserva.clear();
		this.llenarTablaDePasajeros();
		this.ventanaPago.limpiarCampos();
		mostrarVentanaComprobante();
		this.llenarTablaPasajes();
		
	}

	private void verificarSumaDePuntosDeCliente(PasajeDTO pasaje) {
		if(pasaje.getEstadoDelPasaje().getNombre().equals("Vendido")){
		System.out.println("El pasaje esta vendido, se le suman los puntos a: "+pasaje.getCliente().getNombre());
		if(!noEditarPago){
			System.out.println("Se edita el pago. El valor del pasaje es: "+pasaje.getValorViaje());
			calcularPuntos(pasaje.getCliente(),pasaje.getValorViaje());
			}
		else{
			System.out.println("Se paga el total. El valor del pasaje es: " + totalaPagar);
			calcularPuntos(pasaje.getCliente(),totalaPagar);
		}
		}
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
		this.precioOriginal = this.viajeSeleccionado.getPrecio().multiply(new BigDecimal(pasajeros_en_reserva.size()));
		this.valorFinal = calcularValorPasajesNuevo(viajeSeleccionado,pasajeros_en_reserva.size());
		this.totalaPagar = valorFinal;
		return valorFinal;
	}
		
	private BigDecimal calcularValorPasajesNuevo(ViajeDTO viaje, int cantPasajeros){
		BigDecimal ret = viaje.getPrecio().multiply(new BigDecimal(cantPasajeros));
		BigDecimal valorUnitario = viaje.getPrecio();
		
		for(Viaje_PromocionDTO vp : viaje_promocion.obtenerViajePromocion()) {
			if(vp.getIdViaje() == viaje.getIdViaje()) {
				for(PromocionDTO p : modeloPromocion.obtenerPromocion()) {
					if(p.getIdPromocion() == vp.getIdPromocion()) {
						if(promocionActiva(p) != -1) {
							if(p.getStock() >= cantPasajeros){
								//calcular valor de los pasajes - todos con promo
								BigDecimal valorUnitarioConDescuento = valorUnitario.multiply(new BigDecimal(100-p.getPorcentaje())).divide(new BigDecimal(100));
								this.ventanaPago.mostrarDatosPromocion();
								this.ventanaPago.setLblDatoMontoOriginal("$ "+valorUnitario.multiply(new BigDecimal(cantPasajeros)).toString());
								this.ventanaPago.setLblDatoPorcentajeDescuento("-"+p.getPorcentaje()+""+" %");
								ret = valorUnitarioConDescuento.multiply(new BigDecimal(cantPasajeros));
								promocionSeleccionada = p;	
								cantPasajerosEnPromo = cantPasajeros;
							}
							else{
								if(p.getStock() == 0){
									//calcular valor de los pasajes - sin promo
									JOptionPane.showMessageDialog(null,"Promoción sin stock", "Mensaje", JOptionPane.ERROR_MESSAGE);
									BigDecimal valorPasajesSinPromo =  viaje.getPrecio().multiply(new BigDecimal(cantPasajeros));
									ret = valorPasajesSinPromo;
								}
								if(p.getStock() > 0){
									JOptionPane.showMessageDialog(null,"Atención! Stock de la promoción: "+p.getStock(), "Mensaje", JOptionPane.ERROR_MESSAGE);
									//calcular valor de los pasajes utilizando promo:
									BigDecimal valorUnitarioConDescuento = valorUnitario.multiply(new BigDecimal(100-p.getPorcentaje())).divide(new BigDecimal(100));
									this.ventanaPago.mostrarDatosPromocion();
									this.ventanaPago.setLblDatoMontoOriginal("$ "+valorUnitario.multiply(new BigDecimal(cantPasajeros)).toString());
									if(p.getStock()==1)
										this.ventanaPago.setLblDatoPorcentajeDescuento("-"+p.getPorcentaje()+""+" %"+"  (x 1 pasajero)");
									else
										this.ventanaPago.setLblDatoPorcentajeDescuento("-"+p.getPorcentaje()+""+" %"+"  (x "+p.getStock()+" pasajeros)");							
									BigDecimal valorPasajesConPromo = valorUnitarioConDescuento.multiply(new BigDecimal(p.getStock()));
									//calcular valor de los pasajes que se quedaron sin promo:
									int pasajesSinPromo = cantPasajeros - p.getStock();
									BigDecimal valorPasajesSinPromo =  viaje.getPrecio().multiply(new BigDecimal(pasajesSinPromo));
									//suma de valores anteriores:
									ret = valorPasajesConPromo.add(valorPasajesSinPromo);	
									promocionSeleccionada = p;	
									cantPasajerosEnPromo = pasajesSinPromo;
								}
							}
						}
					}
				}
			}
		}	
		return ret;
	}
	
	public int promocionActiva(PromocionDTO p) {
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
			return p.getStock();
		else
			return -1;
	}

	private BigDecimal calcularMontoDePasajeConDescuento(BigDecimal valor, int porcentaje) {
		BigDecimal nuevoValor = new BigDecimal(100-porcentaje);
		return valor.multiply(nuevoValor).divide(new BigDecimal(100));
	}
	
	private EstadoPasajeDTO calcularEstadoPasaje() {
		EstadoPasaje estado = new EstadoPasaje(new DAOSQLFactory());
		EstadoPasajeDTO ret;
		if(this.valorFinal.compareTo(pagoDTO.getMonto())==0){ 
			ret = estado.getFormaPagoByName("Vendido");
		}
		else {
				ret = estado.getFormaPagoByName("Reservado");		
		}
		return ret;
	}
	
	private EstadoPasajeDTO estadoPasaje(BigDecimal monto){
		EstadoPasaje estado = new EstadoPasaje(new DAOSQLFactory());
		EstadoPasajeDTO ret;
		//ACA
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
		pasajeAEditar = this.pasajes_en_tabla.get(filaSeleccionada);
		this.clienteSeleccionado = pasajeAEditar.getCliente();
		
		verDatosDelPasaje(filaSeleccionada);
		
	}
	
	private void verDatosDelPasaje(int filaSeleccionada) {
		if (filaSeleccionada != -1){
			this.ventanaPago.limpiarCampos();
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
		this.noEditarPago = false;
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
//TODO: CANCELACION DE PASAJE
	public void eliminarPasaje(int filaSeleccionada){
		int confirm = JOptionPane.showOptionDialog(
		            null,"¿Estás seguro que quieres cancelar el pasaje?", 
				             "Cancelar pasaje", JOptionPane.YES_NO_OPTION,
				             JOptionPane.ERROR_MESSAGE, null, null, null);
	 if (confirm == 0){
		 System.out.println("Cancelamos el viaje");
		 pasajeACancelar = pasajes_en_tabla.get(filaSeleccionada);
//		 int diff = calcularDiferenciasDeDiasAlCancelarPasaje(pasajeACancelar);
//		 calcularRetencionDeDineroPorCancelacionDelPasaje(pasajeACancelar,diff);
		 this.ventanaCancelacionPasaje.mostrarVentana(true);
	 }
	}
	
	private int calcularDiferenciasDeDiasAlCancelarPasaje(PasajeDTO pasajeACancelar){
		Calendar calendar = Calendar.getInstance(); //obtiene la fecha de hoy
		 
		Date fechaFinal= pasajeACancelar.getViaje().getFechaSalida();
		java.util.Date fechaInicial = (java.util.Date) calendar.getTime();
 
		int dias=(int) ((fechaFinal.getTime()-fechaInicial.getTime())/86400000);
 
		System.out.println("Hay "+dias+" dias de diferencia");
		return dias;
	}
	
	private BigDecimal calcularRetencionDeDineroPorCancelacionDelPasaje(PasajeDTO pasajeACancelar, int diferenciaEnDias){
		BigDecimal ONE_HUNDRED = new BigDecimal(100);
		BigDecimal base = pasajeACancelar.getValorViaje();
		BigDecimal porcentajeDeRetencion = null;
		BigDecimal montoRetenidoPorLaEmpreza = null;
		
		if(diferenciaEnDias>=35){
			porcentajeDeRetencion = new BigDecimal(25);
		}
		if(diferenciaEnDias<35 && diferenciaEnDias>=21){
			porcentajeDeRetencion = new BigDecimal(35);
		}
		if(diferenciaEnDias<21 && diferenciaEnDias>=7){
			porcentajeDeRetencion = new BigDecimal(50);
		}
		if(diferenciaEnDias<7){
			porcentajeDeRetencion = new BigDecimal(35);
		}
		montoRetenidoPorLaEmpreza = base.multiply(porcentajeDeRetencion).divide(ONE_HUNDRED);
		System.out.println("Total del viaje es: "+base);
		System.out.println("Le retenemos el:"+porcentajeDeRetencion);
		System.out.println("El monto retenido es: "+montoRetenidoPorLaEmpreza);
		return montoRetenidoPorLaEmpreza;
	}
	//TODO: SE CANCELA EL PASAJE
	public void cancelarPasaje(ActionEvent cp){
		Reporte reporte = new Reporte();
		Calendar currenttime = Calendar.getInstance();
		
		if(pasajeACancelar.getEstadoDelPasaje().getIdEstadoPasaje()==1){
			pasajeACancelar.setMontoAReembolsar(calcularReembolsoPasajeVendido(pasajeACancelar));
			pasajeACancelar.setEstadoDelPasaje(new EstadoPasajeDTO(4,"Cancelado","Se cancelo el pasaje"));
			pasajeACancelar.setDateCancelacion(new Date((currenttime.getTime()).getTime()));
			pasajeACancelar.setMotivoCancelacion(this.ventanaCancelacionPasaje.getTxtMotivoCancelacion().getText());
			modeloPasaje.editarPasaje(pasajeACancelar);
			reporte.reportePasajeCancelacionVenta(pasajeACancelar);
			reporte.mostrar();
		}else if(pasajeACancelar.getEstadoDelPasaje().getIdEstadoPasaje()==2){
			pasajeACancelar.setMontoAReembolsar(calcularReembolso(pasajeACancelar));
			pasajeACancelar.setEstadoDelPasaje(new EstadoPasajeDTO(4,"Cancelado","Se cancelo el pasaje"));
			pasajeACancelar.setDateCancelacion(new Date((currenttime.getTime()).getTime()));
			pasajeACancelar.setMotivoCancelacion(this.ventanaCancelacionPasaje.getTxtMotivoCancelacion().getText());
			modeloPasaje.editarPasaje(pasajeACancelar);
			reporte.reportePasajeCancelacionReserva(pasajeACancelar);
			reporte.mostrar();
		}
		this.ventanaCancelacionPasaje.mostrarVentana(false);
		this.llenarTablaPasajes();
	}
	
	
	public BigDecimal calcularPorcentaje(BigDecimal base, BigDecimal pct){ 
	    return base.multiply(pct).divide(new BigDecimal(100)); 
	} 
	
	
	public BigDecimal calcularReembolso(PasajeDTO pasaje){
		BigDecimal montoPagado = pasaje.getValorViaje().subtract(pasaje.getMontoAPagar());
		BigDecimal montoAReembolsar = new BigDecimal(0);
		Calendar currenttime = Calendar.getInstance();
		int diferenciaDias = numeroDiasEntreDosFechas(new Date((currenttime.getTime()).getTime()),pasaje.getViaje().getFechaSalida());
		ArrayList<CondicionDeCancelacionDTO> condiciones = (ArrayList<CondicionDeCancelacionDTO>) this.modeloCondicionDeCancelacion.getByEstado("reservado");
		for(CondicionDeCancelacionDTO c:condiciones){
			int desde = c.getInicio();
			int hasta = c.getFin();
			int porcentaje = c.getPorcentaje();
			
			if(diferenciaDias>=desde && diferenciaDias<=hasta){
				montoAReembolsar = montoPagado.subtract(calcularPorcentaje(montoPagado,new BigDecimal(porcentaje)));
				System.out.println("Se cancelo a los "+diferenciaDias+" se reembolsa %"+porcentaje+" que es: "+montoAReembolsar);
			}else if(diferenciaDias>=desde && diferenciaDias<=hasta){
				montoAReembolsar = montoPagado.subtract(calcularPorcentaje(montoPagado,new BigDecimal(porcentaje)));
				System.out.println("Se cancelo a los "+diferenciaDias+" se reembolsa %"+porcentaje+" que es: "+montoAReembolsar);
			}else if(diferenciaDias>=desde && diferenciaDias<=hasta){
				montoAReembolsar = montoPagado.subtract(calcularPorcentaje(montoPagado,new BigDecimal(porcentaje)));
				System.out.println("Se cancelo a los "+diferenciaDias+" se reembolsa %"+porcentaje+" que es: "+montoAReembolsar);
			}else if(diferenciaDias>=desde && diferenciaDias<=hasta){
				montoAReembolsar = montoPagado.subtract(calcularPorcentaje(montoPagado,new BigDecimal(porcentaje)));
				System.out.println("Se cancelo a los "+diferenciaDias+" se reembolsa %"+porcentaje+" que es: "+montoAReembolsar);
			}
		}
		return montoAReembolsar;
	}
	
	private BigDecimal calcularReembolsoPasajeVendido(PasajeDTO pasajeACancelar) {
		
		BigDecimal montoPagado = pasajeACancelar.getValorViaje().subtract(pasajeACancelar.getMontoAPagar());
		BigDecimal montoAReembolsar = new BigDecimal(0);
		Calendar currenttime = Calendar.getInstance();
		int diferenciaDias = numeroDiasEntreDosFechas(new Date((currenttime.getTime()).getTime()),pasajeACancelar.getViaje().getFechaSalida());
		ArrayList<CondicionDeCancelacionDTO> condiciones = (ArrayList<CondicionDeCancelacionDTO>) this.modeloCondicionDeCancelacion.getByEstado("vendido");		
		
		for(CondicionDeCancelacionDTO c:condiciones){
			int desde = c.getInicio();
			int hasta = c.getFin();
			int porcentaje = c.getPorcentaje();
			
			if(diferenciaDias>=desde && diferenciaDias<=hasta){
				montoAReembolsar = montoPagado.subtract(calcularPorcentaje(montoPagado,new BigDecimal(porcentaje)));
				
			}else if(diferenciaDias>=desde && diferenciaDias<=hasta){
				montoAReembolsar = montoPagado.subtract(calcularPorcentaje(montoPagado,new BigDecimal(porcentaje)));
				System.out.println("Se cancelo a los "+diferenciaDias+" se reembolsa %"+porcentaje+" que es: "+montoAReembolsar);
			}else if(diferenciaDias>=desde && diferenciaDias<=hasta){
				montoAReembolsar = montoPagado.subtract(calcularPorcentaje(montoPagado,new BigDecimal(porcentaje)));
				System.out.println("Se cancelo a los "+diferenciaDias+" se reembolsa %"+porcentaje+" que es: "+montoAReembolsar);
			}else if(diferenciaDias>=desde && diferenciaDias<=hasta){
				montoAReembolsar = montoPagado.subtract(calcularPorcentaje(montoPagado,new BigDecimal(porcentaje)));
				System.out.println("Se cancelo a los "+diferenciaDias+" se reembolsa %"+porcentaje+" que es: "+montoAReembolsar);
			}
		}
		return montoAReembolsar;
	}
//		if(diferenciaDias>35){
//			montoAReembolsar = montoPagado.subtract(calcularPorcentaje(montoPagado,new BigDecimal(25)));
	
//		}else if(diferenciaDias>21 && diferenciaDias<34){
//			montoAReembolsar = montoPagado.subtract(calcularPorcentaje(montoPagado,new BigDecimal(50)));
	
//		}else if(diferenciaDias>7 && diferenciaDias<20){
//			montoAReembolsar = montoPagado.subtract(calcularPorcentaje(montoPagado,new BigDecimal(75)));
	
//		}else if(diferenciaDias>5 && diferenciaDias<9){
//			montoAReembolsar = montoPagado.subtract(calcularPorcentaje(montoPagado,new BigDecimal(100)));
	
//		}
//		return montoAReembolsar;
//	}

	
	public int numeroDiasEntreDosFechas(java.sql.Date fecha1, java.sql.Date fecha2){
	     long startTime = fecha1.getTime();
	     long endTime = fecha2.getTime();
	     long diffTime = endTime - startTime;
	     long diffDays = diffTime / (1000 * 60 * 60 * 24);
	     return (int)diffDays;
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
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}
	
	private boolean dniExistente(String dni, String nombre, String apellido, String fechaNacimiento){
		Pasajero modeloPasajero= new Pasajero(new DAOSQLFactory());
		for(PasajeroDTO pasajero: modeloPasajero.obtenerPasajeros()){
			
			if(pasajero.getDni().equals(dni)){
				
				System.out.println("Pasajero: "+pasajero.getNombre()+" "+pasajero.getNombre().length());
				System.out.println("Tabla pa: "+nombre+" "+nombre.length());
				if(!pasajero.getNombre().equals(nombre)){
					System.out.println("nombres distintos");
					return true;}
				
				if(!pasajero.getApellido().equals(apellido)){
					System.out.println("apellidos distintos");
					return true;}

				if(!mapper.parseToString(pasajero.getFechaNacimiento()).equals(fechaNacimiento)){
					System.out.println("nacimiento distintos");
					return true;}
						
			}
		}
		return false;
	}
	
	public boolean tienePromo(ViajeDTO viaje){
		for(Viaje_PromocionDTO x : viaje_promocion.obtenerViajePromocion()){
			if(x.getIdViaje() == viaje.getIdViaje())
				return true;
		}
		return false;
	}
	
}