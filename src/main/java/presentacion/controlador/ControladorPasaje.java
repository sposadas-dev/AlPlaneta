package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.util.List;

import modelo.Cliente;
import modelo.ModeloViaje;
import dto.ClienteDTO;
import dto.ViajeDTO;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.administrativo.VentanaCargaPasajero;
import presentacion.vista.administrativo.VentanaTablaViajes;
import presentacion.vista.administrativo.VentanaVisualizarClientes;

public class ControladorPasaje {

	private VentanaVisualizarClientes ventanaVisualizarClientes;
	private VentanaTablaViajes ventanaTablaViajes;
	private VentanaCargaPasajero ventanaCargaPasajero;
	
	private List<ClienteDTO> clientes_en_tabla;
	private List <ViajeDTO> viajes_en_tabla;
	private ClienteDTO clienteSeleccionado; //cliente que selecciona en la tabla 
	private ViajeDTO viajeSeleccionado;
	private Cliente cliente;
	private ModeloViaje viaje;
	
	public ControladorPasaje(VentanaVisualizarClientes ventanaVisualizarClientes, Cliente cliente){
		this.ventanaVisualizarClientes = ventanaVisualizarClientes;
		this.ventanaTablaViajes = VentanaTablaViajes.getInstance();

		this.cliente = cliente;
		this.viaje = new ModeloViaje (new DAOSQLFactory());
		this.ventanaVisualizarClientes.getPanelCliente().getBtnConfirmar().addActionListener(c->confirmarSeleccionCliente(c));
		this.ventanaTablaViajes.getBtnAtras().addActionListener(a->volverAtras(a));
	}

	public void iniciar(){
		this.llenarTablaClientes();
	}
	
	private void confirmarSeleccionCliente(ActionEvent c) {
		this.ventanaVisualizarClientes.mostrarVentana(false);
		int filaSeleccionada = this.ventanaVisualizarClientes.getPanelCliente().getTablaClientes().getSelectedRow();
		clienteSeleccionado = clientes_en_tabla.get(filaSeleccionada);
		
		this.ventanaTablaViajes.mostrarVentana(true);
		llenarTablaViajes();
	}
	
//	private confirmarSeleccionViaje(ActionEvent sv){
//		this.ventanaTablaViajes.mostrarVentana(true);
//		int filaSeleccionada = this.ventanaTablaViajes.getTablaViajes().getSelectedRow();
//		viajeSeleccionado = viajes_en_tabla.get(filaSeleccionada);
//	}
	
	private void volverAtras(ActionEvent a) {
		this.ventanaTablaViajes.mostrarVentana(false);
		this.ventanaVisualizarClientes.mostrarVentana(true);
	}
	
	private void llenarTablaClientes(){
		this.ventanaVisualizarClientes.getPanelCliente().getModelClientes().setRowCount(0); //Para vaciar la tabla
		this.ventanaVisualizarClientes.getPanelCliente().getModelClientes().setColumnCount(0);
		this.ventanaVisualizarClientes.getPanelCliente().getModelClientes().setColumnIdentifiers(this.ventanaVisualizarClientes.getPanelCliente().getNombreColumnasClientes());
			
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
							this.ventanaVisualizarClientes.getPanelCliente().getModelClientes().addRow(fila);
		}		
	}
	
	private void llenarTablaViajes(){
		this.ventanaTablaViajes.getModelViajes().setRowCount(0); //Para vaciar la tabla
		this.ventanaTablaViajes.getModelViajes().setColumnCount(0);
		this.ventanaTablaViajes.getModelViajes().setColumnIdentifiers(this.ventanaTablaViajes.getNombreColumnas());
			
		this.viajes_en_tabla = viaje.obtenerViajes();
			
		for (int i = 0; i < this.viajes_en_tabla.size(); i++){
			Object[] fila = {this.viajes_en_tabla.get(i).getOrigenViaje().getNombre(),
							this.viajes_en_tabla.get(i).getDestinoViaje().getNombre(),
							this.viajes_en_tabla.get(i).getFechaSalida(),
							this.viajes_en_tabla.get(i).getFechaLlegada(),
							this.viajes_en_tabla.get(i).getHoraSalida(),
							this.viajes_en_tabla.get(i).getHorasEstimadas(),
							this.viajes_en_tabla.get(i).getCapacidad(),
							this.viajes_en_tabla.get(i).getTransporte().getNombre(),
							this.viajes_en_tabla.get(i).getPrecio()					
			};
							this.ventanaTablaViajes.getModelViajes().addRow(fila);
		}		
	}
}