package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import dto.ClienteDTO;
import dto.LoginDTO;
import dto.PagoDTO;
import dto.Pagos_PasajeDTO;
import dto.PasajeDTO;
import modelo.Login;
import modelo.Pagos_Pasaje;
import modelo.Pasaje;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.administrativo.VentanaTablaPagos;
import presentacion.vista.cliente.VentanaCambiarContrasena;
import presentacion.vista.cliente.VentanaReservas;
import presentacion.vista.cliente.VentanaViajes;
import presentacion.vista.cliente.VentanaVisualizarDatos;
import presentacion.vista.cliente.VistaCliente;
import recursos.Mapper;

public class ControladorUsuario implements ActionListener {

	private static final String BUTTON_NAME_VER = "Ver";
	private VistaCliente vistaCliente;
	private ClienteDTO cliente;
	private Pasaje pasaje;
	private Login login;
	private Pagos_Pasaje pagos_pasaje;
	private Mapper mapper;
	
	private List<PasajeDTO> pasajes_en_tabla;
	private VentanaViajes ventanaViajes;
	private VentanaReservas ventanaReservas;
	private VentanaVisualizarDatos ventanaVisualizarDatos;
	private VentanaCambiarContrasena ventanaCambiarContrasenia;
	private VentanaTablaPagos ventanaTablaPagos;
	private PasajeDTO pasajeSeleccionado;
	
	private DefaultTableModel dm;
	
	private StringBuilder cad = new StringBuilder();
	private String aceptada="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public ControladorUsuario(VistaCliente vistaCliente, ClienteDTO cliente){
		this.vistaCliente = vistaCliente;
		this.cliente = cliente;
		this.mapper = new Mapper();
		this.ventanaReservas = VentanaReservas.getInstance();
		this.ventanaViajes = VentanaViajes.getInstance();
		this.ventanaVisualizarDatos = VentanaVisualizarDatos.getInstance();
		this.ventanaCambiarContrasenia = VentanaCambiarContrasena.getInstance();
		this.ventanaTablaPagos = VentanaTablaPagos.getInstance();
		
		this.vistaCliente.getItemVisualizarReservas().addActionListener(vr->mostrarVentanaReservas(vr));
		this.vistaCliente.getItemVisualizarViajesHistoricos().addActionListener(vr->mostrarVentanaViajes(vr));
		this.vistaCliente.getItemVisualizarDatos().addActionListener(vd->mostrarVentanaVisualizarDatos(vd));
		this.vistaCliente.getItemCambiarContrasenia().addActionListener(c->mostrarVentanaCambiarContrasenia(c));
		
		this.ventanaViajes.getTxtFiltro().addKeyListener(new KeyAdapter(){            
		    public void keyTyped(KeyEvent e){
		            char letra = e.getKeyChar();
		            dm = (DefaultTableModel) ventanaViajes.getModelViajes();
		            TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
		            ventanaViajes.getTablaViajes().setRowSorter(tr);
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
		
		this.ventanaReservas.getTxtFiltro().addKeyListener(new KeyAdapter(){            
		    public void keyTyped(KeyEvent e){
		            char letra = e.getKeyChar();
		            dm = (DefaultTableModel) ventanaReservas.getModelReservas();
		            TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(dm);
		            ventanaReservas.getTablaReservas().setRowSorter(tr);
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
		
		this.ventanaReservas.getBtnAceptar().addActionListener(vd->cerrarVentanaReserva(vd));
		this.ventanaReservas.getBtnVerPagos().addActionListener(v->verPagos(v));
		this.ventanaViajes.getBtnAceptar().addActionListener(vd->cerrarVentanaViajes(vd));
		this.ventanaVisualizarDatos.getBtnAceptar().addActionListener(vd->cerrarVentana(vd));
		this.ventanaCambiarContrasenia.getBtnAceptar().addActionListener(c->cambiarContrasenia(c));
		this.ventanaCambiarContrasenia.getBtnCancelar().addActionListener(c->salirVentanaCambiarContrasenia(c));

		
		this.login = new Login(new DAOSQLFactory());
		this.pasaje = new Pasaje(new DAOSQLFactory());
		this.pagos_pasaje = new Pagos_Pasaje(new DAOSQLFactory());
	}


	public void inicializar(){
		this.vistaCliente.mostrarVentana();
		
	}
	
	private void mostrarVentanaReservas(ActionEvent vr) {
		this.ventanaReservas.mostrarVentana(true);
		llenarTablaReservas();
	}
	
	private void cerrarVentanaReserva(ActionEvent vd) {
		this.ventanaReservas.mostrarVentana(false);
	}
	
	private void cerrarVentanaViajes(ActionEvent vd) {
		this.ventanaViajes.mostrarVentana(false);

	}

	private void mostrarVentanaViajes(ActionEvent vr) {
		this.ventanaViajes.mostrarVentana(true);
		llenarTablaViajes();
	}
	
	private void mostrarVentanaVisualizarDatos(ActionEvent vd) {
		this.ventanaVisualizarDatos.mostrarVentana(true);
		this.ventanaVisualizarDatos.getLblNombreDelCliente().setText(cliente.getNombre());
		this.ventanaVisualizarDatos.getLblApellidoDelCliente().setText(cliente.getApellido());
		this.ventanaVisualizarDatos.getLblDniDelCliente().setText(cliente.getDni());
		this.ventanaVisualizarDatos.getLblFechaNacimientoDelCliente().setText(cliente.getFechaNacimiento().toString());
		this.ventanaVisualizarDatos.getLblNumeroFijoDelCliente().setText(cliente.getMedioContacto().getTelefonoFijo());
		this.ventanaVisualizarDatos.getLblNumeroCelularDelCliente().setText(cliente.getMedioContacto().getTelefonoCelular());
		this.ventanaVisualizarDatos.getLblEmailDelCliente().setText(cliente.getMedioContacto().getEmail());
	}

	private void cerrarVentana(ActionEvent vd) {
		this.ventanaVisualizarDatos.mostrarVentana(false);
	}

	private void mostrarVentanaCambiarContrasenia(ActionEvent c) {
		this.ventanaCambiarContrasenia.limpiarCampos();
		this.ventanaCambiarContrasenia.mostrarVentana(true);
	}
	
	private void verPagos(ActionEvent v) {
		int filaSeleccionada = this.ventanaReservas.getTablaReservas().getSelectedRow();
		if (filaSeleccionada != -1){
			this.ventanaTablaPagos.mostrarVentana(true);
			pasajeSeleccionado = pasajes_en_tabla.get(filaSeleccionada);
			llenarTablaPagos(pasajeSeleccionado.getIdPasaje());
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void cambiarContrasenia(ActionEvent c) {
		String passwordActual = new String(this.ventanaCambiarContrasenia.getPassActual().getPassword());
		
		String passwordConfirmacion1 = new String(this.ventanaCambiarContrasenia.getPassNueva().getPassword());
		String passwordConfirmacion2 = new String(this.ventanaCambiarContrasenia.getConfirmacionContrasena().getPassword());
		
		System.out.println(passwordConfirmacion1+" "+passwordConfirmacion2);
		
		if(!passwordConfirmacion1.equals(passwordConfirmacion2)){
			JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden ", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
		else if(!passwordActual.equals(cliente.getLogin().getContrasena())){
			JOptionPane.showMessageDialog(null, "La contraseña actual es incorrecta", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}else{
			LoginDTO loginDTO = new LoginDTO();
			loginDTO.setIdDatosLogin(cliente.getLogin().getIdDatosLogin());
			loginDTO.setUsuario(cliente.getLogin().getUsuario());
			loginDTO.setRol(cliente.getLogin().getRol());
			loginDTO.setEstado(cliente.getLogin().getEstado());
		
			String password = new String(this.ventanaCambiarContrasenia.getPassNueva().getPassword());
			loginDTO.setContrasena(password);
			this.login.editarLogin(loginDTO);
			this.ventanaCambiarContrasenia.mostrarVentana(false);
			JOptionPane.showMessageDialog(null, "Contraseña actualizada", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void salirVentanaCambiarContrasenia(ActionEvent c) {
		this.ventanaCambiarContrasenia.limpiarCampos();
		this.ventanaCambiarContrasenia.mostrarVentana(false);;
	}


	@SuppressWarnings("serial")
	public void llenarTablaReservas(){
		this.ventanaReservas.getModelReservas().setRowCount(0); //Para vaciar la tabla
		this.ventanaReservas.getModelReservas().setColumnCount(0);
		this.ventanaReservas.getModelReservas().setColumnIdentifiers(this.ventanaReservas.getColumnasReservas());
			
		this.pasajes_en_tabla = pasaje.obtenerPasajes();
		
		for (int i = 0; i < this.pasajes_en_tabla .size(); i++){
			int idClienteReserva = pasajes_en_tabla.get(i).getCliente().getIdCliente();
			if (idClienteReserva == cliente.getIdCliente() &&
					pasajes_en_tabla.get(i).getEstadoDelPasaje().getIdEstadoPasaje()== 2
					|| pasajes_en_tabla.get(i).getEstadoDelPasaje().getIdEstadoPasaje()== 3){
				
			Object[] fila = {
					this.pasajes_en_tabla.get(i).getViaje().getCiudadOrigen().getNombre(),
					this.pasajes_en_tabla.get(i).getViaje().getCiudadDestino().getNombre(),
					mapper.parseToString(this.pasajes_en_tabla.get(i).getFechaVencimiento()),
					mapper.parseToString(this.pasajes_en_tabla.get(i).getViaje().getFechaSalida()),
					mapper.parseToString(this.pasajes_en_tabla.get(i).getViaje().getFechaLlegada()),
					this.pasajes_en_tabla.get(i).getViaje().getHoraSalida(),
					this.pasajes_en_tabla.get(i).getViaje().getTransporte().getNombre(),
					"$" +this.pasajes_en_tabla.get(i).getValorViaje().subtract(this.pasajes_en_tabla.get(i).getMontoAPagar()),
					"$" +this.pasajes_en_tabla.get(i).getMontoAPagar(),
					BUTTON_NAME_VER
			};
			this.ventanaReservas.getModelReservas().addRow(fila);
			}
		}
			new ButtonColumn(this.ventanaReservas.getTablaReservas(), verPagos(), 9);
			this.ventanaReservas.getTablaReservas().getColumnModel().getColumn(9).setMinWidth(50);
			this.ventanaReservas.getTablaReservas().getColumnModel().getColumn(9).setMaxWidth(50);
					
	}
	
	public Action verPagos() {
		Action verPago = new AbstractAction() {
		private static final long serialVersionUID = 1L;
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("mica");
			JTable table = (JTable) e.getSource();

			
			PasajeDTO sc = obtenerScSeleccionada((int) table.getModel().getValueAt(table.getSelectedRow(), 0));
			System.out.println((int) table.getModel().getValueAt(table.getSelectedRow(),0));
			if (sc != null)
//				new ControladorPasaje(vp, sc);
				llenarTablaPagos(sc.getIdPasaje());
			
		}
			private PasajeDTO obtenerScSeleccionada(int id) {
				for (PasajeDTO s : pasajes_en_tabla)
					if (s.getIdPasaje() == id)
						return s;
				return null;
			}
		};
		return verPago;
	}

	private void llenarTablaPagos(int idPasaje){
		this.ventanaTablaPagos.getModelPagos().setRowCount(0);
		this.ventanaTablaPagos.getModelPagos().setColumnCount(0);
		this.ventanaTablaPagos.getModelPagos().setColumnIdentifiers(this.ventanaTablaPagos.getNombreColumnas());
		
		Pagos_Pasaje pago_pasaje = new Pagos_Pasaje(new DAOSQLFactory());
		List<Pagos_PasajeDTO> pagos = pago_pasaje.obtenerPagosPasaje();
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
	public void llenarTablaViajes(){
		this.ventanaViajes.getModelViajes().setRowCount(0); //Para vaciar la tabla
		this.ventanaViajes.getModelViajes().setColumnCount(0);
		this.ventanaViajes.getModelViajes().setColumnIdentifiers(this.ventanaViajes.getColumnasViajes());
			
		this.pasajes_en_tabla = pasaje.obtenerPasajes();
		
		for (int i = 0; i < this.pasajes_en_tabla .size(); i++){
			int idClienteReserva = pasajes_en_tabla.get(i).getCliente().getIdCliente();
			if (idClienteReserva == cliente.getIdCliente() &&
					pasajes_en_tabla.get(i).getEstadoDelPasaje().getIdEstadoPasaje()== 1){
				
			Object[] fila = {
					this.pasajes_en_tabla.get(i).getViaje().getPaisOrigen().getNombre()+ ","+ 
					this.pasajes_en_tabla.get(i).getViaje().getCiudadOrigen().getNombre(),
					this.pasajes_en_tabla.get(i).getViaje().getPaisDestino().getNombre()+ ","+ 
					this.pasajes_en_tabla.get(i).getViaje().getCiudadDestino().getNombre(),
					mapper.parseToString(this.pasajes_en_tabla.get(i).getViaje().getFechaSalida()),
					mapper.parseToString(this.pasajes_en_tabla.get(i).getViaje().getFechaLlegada()),
					this.pasajes_en_tabla.get(i).getViaje().getTransporte().getNombre(),
					"$ "+this.pasajes_en_tabla.get(i).getViaje().getPrecio()
			};
			this.ventanaViajes.getModelViajes().addRow(fila);
		}}		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}