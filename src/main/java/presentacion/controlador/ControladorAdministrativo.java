package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Cliente;
import modelo.Pasaje;
import dto.AdministrativoDTO;
import dto.ClienteDTO;
import dto.LoginDTO;
import dto.PasajeDTO;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.VentanaLogin;
import presentacion.vista.administrativo.VentanaRegistrarCliente;
import presentacion.vista.administrativo.VentanaVisualizarClientes;
import presentacion.vista.administrativo.VentanaVisualizarPasaje;
import presentacion.vista.administrativo.VistaAdministrativo;

public class ControladorAdministrativo implements ActionListener {

	private VistaAdministrativo vista;
	private VentanaRegistrarCliente ventanaCliente;
	private VentanaVisualizarClientes ventanaVisualizarCliente;
	private VentanaVisualizarPasaje ventanaVisualizarPasaje;
	private AdministrativoDTO administrativoLogueado;
	private List<ClienteDTO> clientes_en_tabla;
	private List<PasajeDTO> pasajes_en_tabla;
	private Cliente cliente;
	private Pasaje pasaje;
	private ControladorPasaje controladorPasaje;
	
	public ControladorAdministrativo(VistaAdministrativo vista,AdministrativoDTO administrativoLogueado) {
		this.vista = vista;
		this.ventanaCliente = VentanaRegistrarCliente.getInstance();
		this.ventanaVisualizarCliente = VentanaVisualizarClientes.getInstance();
		this.ventanaVisualizarPasaje = VentanaVisualizarPasaje.getInstance();
		
		this.vista.getItemVisualizarClientes().addActionListener(ac->agregarPanelClientes(ac));
		this.vista.getItemRegistrarCliente().addActionListener(ac->mostrarVentanaAgregarCliente(ac));
	
		this.vista.getItemVisualizarPasajes().addActionListener(ap->mostrarPasajes(ap));
		this.vista.getItemAgregarPasaje().addActionListener(ap->mostrarVentanaAgregarPasaje(ap));
		this.vista.getItemEditarPasaje().addActionListener(ep->mostrarVentanaEditarPasaje(ep));
		this.vista.getItemCancelarPasaje().addActionListener(cp->cancelarPasaje(cp));
		
		this.vista.getPanelCliente().getBtnRecargarTabla().addActionListener(r->recargarTabla(r));
//		this.vista.getPanelPasaje().getBtnVisualizarPasaje().addActionListener(vp->verDatosPasaje(vp));

		this.administrativoLogueado = administrativoLogueado;
		this.cliente = new Cliente(new DAOSQLFactory());
		this.pasaje = new Pasaje(new DAOSQLFactory());
		
		controladorPasaje = new ControladorPasaje(ventanaVisualizarCliente,cliente,administrativoLogueado);
	}

	public ControladorAdministrativo(){
		super();
	}
	
	private void recargarTabla(ActionEvent r) {
		this.llenarTablaClientes();
	}

	public void inicializar(){
		this.vista.mostrarVentana();
		JOptionPane.showMessageDialog(null, "Bienvenido" + " " + administrativoLogueado.getNombre(), "Al Planeta Project", JOptionPane.INFORMATION_MESSAGE);
		this.llenarTablaPasajes();
	}
	
	private void agregarPanelClientes(ActionEvent ac) {
		this.vista.getPanelCliente().mostrarPanelCliente(true);
		this.vista.getPanelPasaje().mostrarPanelPasaje(false);
		this.llenarTablaClientes();
	}
	
	private void mostrarVentanaAgregarPasaje(ActionEvent ap) {
		this.vista.getPanelPasaje().mostrarPanelPasaje(true);
		this.vista.getPanelCliente().mostrarPanelCliente(false);
		this.ventanaVisualizarCliente.mostrarVentana(true);
		this.llenarTablaPasajes();
		
		controladorPasaje.iniciar();
	}
	
	private void mostrarVentanaEditarPasaje(ActionEvent ep) {
		this.vista.getPanelPasaje().mostrarPanelPasaje(true);
		int filaSeleccionada = this.vista.getPanelPasaje().getTablaReservas().getSelectedRow();
		if (filaSeleccionada != -1){
			controladorPasaje.editarPasaje(filaSeleccionada);
			llenarTablaPasajes();
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}	
	}

	private void cancelarPasaje(ActionEvent cp) {
		this.vista.getPanelPasaje().mostrarPanelPasaje(true);
		int filaSeleccionada = this.vista.getPanelPasaje().getTablaReservas().getSelectedRow();
		if (filaSeleccionada != -1){
			controladorPasaje.eliminarPasaje(filaSeleccionada);
			llenarTablaPasajes();
		
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void mostrarPasajes(ActionEvent ap) {
		this.vista.getPanelCliente().mostrarPanelCliente(false);
		this.vista.getPanelPasaje().mostrarPanelPasaje(true);
		this.llenarTablaPasajes();
	}
	
	
	private void mostrarVentanaAgregarCliente(ActionEvent ac)  {
		this.vista.getPanelCliente().mostrarPanelCliente(true);
		this.vista.getPanelPasaje().mostrarPanelPasaje(false);
		this.llenarTablaClientes();
		this.ventanaCliente.mostrarVentana();
		ControladorCliente controladorCliente = new ControladorCliente(ventanaCliente,cliente);
	}

	
	private void llenarTablaClientes(){
		this.vista.getPanelCliente().getModelClientes().setRowCount(0); //Para vaciar la tabla
		this.vista.getPanelCliente().getModelClientes().setColumnCount(0);
		this.vista.getPanelCliente().getModelClientes().setColumnIdentifiers(this.vista.getPanelCliente().getNombreColumnasClientes());
			
		this.clientes_en_tabla = cliente.obtenerClientes();
			
		for (int i = 0; i < this.clientes_en_tabla.size(); i++){
			Object[] fila = {this.clientes_en_tabla.get(i).getNombre(),
							this.clientes_en_tabla.get(i).getApellido(),
							this.clientes_en_tabla.get(i).getDni(),
							this.clientes_en_tabla.get(i).getFechaNacimiento(),
							this.clientes_en_tabla.get(i).getMedioContacto().getTelefonoFijo(),
							this.clientes_en_tabla.get(i).getMedioContacto().getTelefonoCelular(),
							this.clientes_en_tabla.get(i).getMedioContacto().getEmail()	
			};
			this.vista.getPanelCliente().getModelClientes().addRow(fila);
		}		
	}
	
	private void llenarTablaPasajes(){
		this.vista.getPanelPasaje().getModelReservas().setRowCount(0); //Para vaciar la tabla
		this.vista.getPanelPasaje().getModelReservas().setColumnCount(0);
		this.vista.getPanelPasaje().getModelReservas().setColumnIdentifiers(this.vista.getPanelPasaje().getNombreColumnasReservas());

		this.pasajes_en_tabla = pasaje.obtenerPasajes();
			
		for (int i = 0; i < this.pasajes_en_tabla.size(); i++){

			Object[] fila = {
							this.pasajes_en_tabla.get(i).getCliente().getDni(),
							this.pasajes_en_tabla.get(i).getCliente().getNombre(),
							this.pasajes_en_tabla.get(i).getCliente().getApellido(),
							this.pasajes_en_tabla.get(i).getIdPasaje(),
							this.pasajes_en_tabla.get(i).getViaje().getCiudadOrigen().getNombre(),
							this.pasajes_en_tabla.get(i).getViaje().getCiudadDestino().getNombre(),
							this.pasajes_en_tabla.get(i).getViaje().getFechaSalida(),
							this.pasajes_en_tabla.get(i).getViaje().getFechaLlegada(),
							this.pasajes_en_tabla.get(i).getViaje().getHoraSalida(),
							this.pasajes_en_tabla.get(i).getViaje().getPrecio(),
							this.pasajes_en_tabla.get(i).getViaje().getTransporte().getNombre(),
							this.pasajes_en_tabla.get(i).getEstadoDelPasaje().getNombre()
			};
							this.vista.getPanelPasaje().getModelReservas().addRow(fila);
		}		
	}

	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
}