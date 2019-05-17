package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import modelo.Cliente;
import modelo.Pasaje;
import dto.ClienteDTO;
import dto.LoginDTO;
import dto.PasajeDTO;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.VentanaLogin;
import presentacion.vista.administrativo.VentanaRegistrarCliente;
import presentacion.vista.administrativo.VentanaVisualizarClientes;
import presentacion.vista.administrativo.VistaAdministrativo;

public class ControladorPrueba implements ActionListener {

	private VistaAdministrativo vista;
	private VentanaRegistrarCliente ventanaCliente;
	private VentanaVisualizarClientes ventanaVisualizarCliente;
	
	private List<ClienteDTO> clientes_en_tabla;
	private List<PasajeDTO> pasajes_en_tabla;
	private Cliente cliente;
	private Pasaje pasaje;
	
	public ControladorPrueba(VistaAdministrativo vista) {
		this.vista = vista;
		this.ventanaCliente = VentanaRegistrarCliente.getInstance();
		this.ventanaVisualizarCliente = VentanaVisualizarClientes.getInstance();
	
		this.vista.getItemVisualizarClientes().addActionListener(ac->agregarPanelClientes(ac));
		this.vista.getItemRegistrarCliente().addActionListener(ac->mostrarVentanaAgregarCliente(ac));
		this.vista.getItemAgregarPasaje().addActionListener(ap->mostrarVentanaAgregarPasaje(ap));
		
		this.vista.getPanelCliente().getBtnRecargarTabla().addActionListener(r->recargarTabla(r));
	
		this.cliente = new Cliente(new DAOSQLFactory());
		this.pasaje = new Pasaje(new DAOSQLFactory());

	}
	
	public ControladorPrueba(){
		super();
	}
	
	private void recargarTabla(ActionEvent r) {
		this.llenarTablaClientes();
	}

	public void inicializar(){
		this.vista.mostrarVentana();
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
		ControladorPasaje controladorPasaje = new ControladorPasaje(ventanaVisualizarCliente,cliente);
		controladorPasaje.iniciar();
	}
	
	private void mostrarVentanaAgregarCliente(ActionEvent ac)  {
		this.vista.getPanelCliente().mostrarPanelCliente(true);
		this.vista.getPanelPasaje().mostrarPanelPasaje(false);
		this.ventanaCliente.mostrarVentana();
		ControladorCliente controladorCliente = new ControladorCliente(ventanaCliente,cliente);
//		this.llenarTablaClientes();
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
		this.vista.getPanelPasaje().getModelClientes().setRowCount(0); //Para vaciar la tabla
		this.vista.getPanelPasaje().getModelClientes().setColumnCount(0);
		this.vista.getPanelPasaje().getModelClientes().setColumnIdentifiers(this.vista.getPanelPasaje().getNombreColumnasClientes());


		this.pasajes_en_tabla = pasaje.obtenerPasajes();
			
		for (int i = 0; i < this.pasajes_en_tabla.size(); i++)
		{
			Object[] fila = {
							this.pasajes_en_tabla.get(i).getCliente().getDni(),
							this.pasajes_en_tabla.get(i).getCliente().getNombre(),
							this.pasajes_en_tabla.get(i).getCliente().getApellido(),
							this.pasajes_en_tabla.get(i).getIdPasaje(),
							this.pasajes_en_tabla.get(i).getViaje().getOrigenViaje().getNombre(),
							this.pasajes_en_tabla.get(i).getViaje().getDestinoViaje().getNombre(),
							this.pasajes_en_tabla.get(i).getViaje().getFechaSalida(),
							this.pasajes_en_tabla.get(i).getViaje().getHoraSalida(),
							this.pasajes_en_tabla.get(i).getViaje().getPrecio(),
							this.pasajes_en_tabla.get(i).getViaje().getTransporte().getNombre(),
							this.pasajes_en_tabla.get(i).getCantidadPasajeros(),
							this.pasajes_en_tabla.get(i).getEstadoDelPasaje().getNombre()
			};
							this.vista.getPanelPasaje().getModelClientes().addRow(fila);
		}		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
}