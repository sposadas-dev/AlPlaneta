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

public class ControladorPrueba implements ActionListener {

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
	
	public ControladorPrueba(VistaAdministrativo vista,AdministrativoDTO administrativoLogueado) {
		this.vista = vista;
		this.ventanaCliente = VentanaRegistrarCliente.getInstance();
		this.ventanaVisualizarCliente = VentanaVisualizarClientes.getInstance();
		this.ventanaVisualizarPasaje = VentanaVisualizarPasaje.getInstance();
		
		this.vista.getItemVisualizarClientes().addActionListener(ac->agregarPanelClientes(ac));
		this.vista.getItemRegistrarCliente().addActionListener(ac->mostrarVentanaAgregarCliente(ac));
		this.vista.getItemAgregarPasaje().addActionListener(ap->mostrarVentanaAgregarPasaje(ap));
		this.vista.getItemVisualizarPasajes().addActionListener(ap->mostrarPasajes(ap));
		this.vista.getItemCancelarPasaje().addActionListener(cp->cancelarPasaje(cp));
		
		this.vista.getPanelCliente().getBtnRecargarTabla().addActionListener(r->recargarTabla(r));
		this.vista.getPanelPasaje().getBtnVisualizarPasaje().addActionListener(vp->verDatosPasaje(vp));

		this.administrativoLogueado = administrativoLogueado;
		this.cliente = new Cliente(new DAOSQLFactory());
		this.pasaje = new Pasaje(new DAOSQLFactory());
		
		controladorPasaje = new ControladorPasaje(ventanaVisualizarCliente,cliente,administrativoLogueado);
	}

//	private BigDecimal calcularMontoDePasajePagado(int filaSeleccionada) {
//		BigDecimal Valor1 = this.pasajes_en_tabla.get(filaSeleccionada).getValorViaje();
//		BigDecimal totalaPagar = this.pasajes_en_tabla.get(filaSeleccionada).getPago().getMonto();
//		return Valor1.subtract(totalaPagar);
//	}
	
	private void verDatosPasaje(ActionEvent vp) {
		int filaSeleccionada = this.vista.getPanelPasaje().getTablaReservas().getSelectedRow();
		if (filaSeleccionada != -1){
			this.ventanaVisualizarPasaje.setVisible(true);
			this.ventanaVisualizarPasaje.getLblClienteDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getCliente().getNombre());
			this.ventanaVisualizarPasaje.getLblDniDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getCliente().getDni());
			this.ventanaVisualizarPasaje.getLblOrigenDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getCiudadOrigen().getNombre());
			this.ventanaVisualizarPasaje.getLblDestinoDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getCiudadDestino().getNombre());
			this.ventanaVisualizarPasaje.getLblTransporteDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getTransporte().getNombre());
			this.ventanaVisualizarPasaje.getLblEstadoPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getEstadoDelPasaje().getNombre());
//			this.ventanaVisualizarPasaje.getLblRestante().setText(" "+calcularMontoDePasajePagado(filaSeleccionada));

		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}

	public ControladorPrueba(){
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
		this.vista.getPanelPasaje().mostrarPanelPasaje(true);
		this.llenarTablaPasajes();
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