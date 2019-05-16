package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import modelo.Cliente;
import dto.ClienteDTO;
import dto.LoginDTO;
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
	
	private Cliente cliente;	
	
	public ControladorPrueba(VistaAdministrativo vista) {
		this.vista = vista;
		this.ventanaCliente = VentanaRegistrarCliente.getInstance();
		this.ventanaVisualizarCliente = VentanaVisualizarClientes.getInstance();
	
		this.vista.getItemVisualizarClientes().addActionListener(ac->agregarPanelClientes(ac));
		this.vista.getItemRegistrarCliente().addActionListener(ac->mostrarVentanaAgregarCliente(ac));
		this.vista.getItemAgregarPasaje().addActionListener(ap->mostrarVentanaAgregarPasaje(ap));
		
		this.vista.getPanelCliente().getBtnRecargarTabla().addActionListener(r->recargarTabla(r));
	
		this.cliente = new Cliente(new DAOSQLFactory());
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
}