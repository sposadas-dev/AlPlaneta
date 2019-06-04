package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.UUID;

import javax.swing.JOptionPane;

import correo.EnvioDeCorreo;
import dto.ClienteDTO;
import dto.LoginDTO;
import dto.MedioContactoDTO;
import dto.RolDTO;
import modelo.Cliente;
import modelo.Login;
import modelo.MedioContacto;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.administrativo.PanelCliente;
import presentacion.vista.administrativo.VentanaEditarCliente;
import presentacion.vista.administrativo.VentanaRegistrarCliente;

public class ControladorCliente implements ActionListener{
	
	private VentanaRegistrarCliente ventanaRegistrarCliente;
	private VentanaEditarCliente ventanaEditarCliente;
	private PanelCliente panelCliente;
	private List<ClienteDTO> clientes_en_tabla;
	private String contrasenaProvisoria;

	private Cliente cliente;
	private MedioContacto medioContacto; 
	private Login Modelologin;
	private EnvioDeCorreo enviodeCorreo;

	public ControladorCliente() {
		super();
	}
	
	public ControladorCliente(VentanaRegistrarCliente ventanaRegistrarCliente, VentanaEditarCliente ventanaEditarCliente, Cliente cliente){
		this.ventanaRegistrarCliente = ventanaRegistrarCliente;
		this.ventanaEditarCliente = ventanaEditarCliente;
		this.cliente = cliente;
		this.contrasenaProvisoria = null;
		this.enviodeCorreo = new EnvioDeCorreo();
		
		this.medioContacto =  new MedioContacto(new DAOSQLFactory());
		this.Modelologin = new Login(new DAOSQLFactory());
		this.panelCliente = new PanelCliente();
		this.ventanaRegistrarCliente.getBtnRegistrar().addActionListener(rc->registrarCliente(rc));
		this.ventanaRegistrarCliente.getBtnCancelar().addActionListener(cv->cerrarVentanaCliente(cv));
		
		
		this.ventanaEditarCliente.getBtnCancelar().addActionListener(ce->cancelarEditar());
		
	}
	
	public void registrarCliente(ActionEvent rc){
		/*Obtenemos la fecha de nacimiento , y la parseamos a tipo de date de SQL*/
		java.util.Date dateFechaNacimiento = ventanaRegistrarCliente.getDateFechaNacimiento().getDate();
		java.sql.Date fechaNacimiento = new java.sql.Date(dateFechaNacimiento.getTime());
	
		/*Obtenemos el medio de contacto del cliente*/
		MedioContactoDTO mContacto = new MedioContactoDTO();
		mContacto.setTelefonoFijo(this.ventanaRegistrarCliente.getTxtTelefonoFijo().getText());
		mContacto.setTelefonoCelular(this.ventanaRegistrarCliente.getTxtTelefonoCelular().getText());
		mContacto.setEmail(this.ventanaRegistrarCliente.getTxtEmail().getText());
		medioContacto.agregarMedioContacto(mContacto);
		this.contrasenaProvisoria = UUID.randomUUID().toString().toUpperCase().substring(0, 8);

		LoginDTO loginCliente = new LoginDTO();
		loginCliente.setUsuario(this.ventanaRegistrarCliente.getTxtUsuario().getText());
		// TODO: GENERAR CONTRASENA PROVISORIA
//		loginCliente.setContrasena(this.ventanaRegistrarCliente.getTxtContrasenia().getText());
		loginCliente.setContrasena(contrasenaProvisoria);
		loginCliente.setRol(new RolDTO(5,"cliente"));
		loginCliente.setEstado("activo");
		
		Modelologin.agregarLogin(loginCliente);
		
		LoginDTO loginProv = obtenerLoginDTO();
		
		ClienteDTO nuevoCliente = new ClienteDTO(0,
			this.ventanaRegistrarCliente.getTxtNombre().getText(),
			this.ventanaRegistrarCliente.getTxtApellido().getText(),
			this.ventanaRegistrarCliente.getTxtDni().getText(),
			fechaNacimiento,
			obtenerMedioContactoDTO(),
			loginProv
												);
		System.out.println("Generamos el cliente: "+nuevoCliente.getNombre()+"-"+nuevoCliente.getMail());
		
		if(camposLlenos()){
			cliente.agregarCliente(nuevoCliente);
			this.llenarTablaClientes();
			this.ventanaRegistrarCliente.limpiarCampos();
			this.ventanaRegistrarCliente.cerrarVentana();
		}
		Cliente modeloCliente = new Cliente(new DAOSQLFactory());
		ClienteDTO clienteDTO = cliente.getByLoginId(loginProv.getIdDatosLogin());
		enviodeCorreo.enviarNuevaContrasena(clienteDTO.getMail(), contrasenaProvisoria,"Generacion de Usuario");
	}

	public void editarCliente(ClienteDTO cliente_a_editar) {
		this.Modelologin.editarLogin(cliente_a_editar.getLogin());
		this.medioContacto.editarMedioContacto(cliente_a_editar.getMedioContacto());
		this.cliente.editarCliente(cliente_a_editar);

		this.ventanaEditarCliente.setVisible(false);
	}
	
	private void cancelarEditar() {
		this.ventanaEditarCliente.setVisible(false);
	}
	
	public void desactivarCliente(int clienteSeleccionado) {
		String estado = "inactivo";
		this.Modelologin.editarEstado(estado, clienteSeleccionado);
	}
	
	public void activarCliente(int clienteSeleccionado) {
		String estado = "activo";
		this.Modelologin.editarEstado(estado, clienteSeleccionado);
	}
	
	private MedioContactoDTO obtenerMedioContactoDTO() {
		MedioContactoDTO mContactoDTO = new MedioContactoDTO();
		List<MedioContactoDTO> medios = medioContacto.obtenerMediosContacto();
		for(MedioContactoDTO m: medios){
			if(m.getEmail().toString().equals(this.ventanaRegistrarCliente.getTxtEmail().getText()) &&
					m.getTelefonoCelular().equals(this.ventanaRegistrarCliente.getTxtTelefonoCelular().getText())&&
					m.getTelefonoFijo().equals(this.ventanaRegistrarCliente.getTxtTelefonoFijo().getText())){
			mContactoDTO = m;
		}
	}
		return mContactoDTO;
	}
	
	private LoginDTO obtenerLoginDTO() {
		LoginDTO loginDTO = new LoginDTO();
		List<LoginDTO> logins = Modelologin.obtenerLogin();
		for(LoginDTO l: logins){
			if(l.getUsuario().equals(this.ventanaRegistrarCliente.getTxtUsuario().getText()) &&
					l.getContrasena().equals(contrasenaProvisoria)){
			loginDTO = l;
		}
	}
		return loginDTO;
	}
	
	private void cerrarVentanaCliente(ActionEvent cv) {
		this.ventanaRegistrarCliente.limpiarCampos();
		this.ventanaRegistrarCliente.cerrarVentana();
	}


	private boolean camposLlenos(){
		if (ventanaRegistrarCliente.getTxtNombre().getText().isEmpty() ||
				ventanaRegistrarCliente.getTxtApellido().getText().isEmpty() ||
				ventanaRegistrarCliente.getTxtDni().getText().isEmpty() ||				
				(ventanaRegistrarCliente.getDateFechaNacimiento().getDate()== null) ||
				ventanaRegistrarCliente.getTxtUsuario().getText().isEmpty() ||
				ventanaRegistrarCliente.getTxtTelefonoFijo().getText().isEmpty() ||
				ventanaRegistrarCliente.getTxtTelefonoCelular().getText().isEmpty() ||
				ventanaRegistrarCliente.getTxtEmail().getText().isEmpty()
			){
				JOptionPane.showMessageDialog(null, "Debe cargar todos los campos", "Mensaje", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			return true;
		}
	
	private void llenarTablaClientes(){
		panelCliente.getModelClientes().setRowCount(0); //Para vaciar la tabla
		panelCliente.getModelClientes().setColumnCount(0);
		panelCliente.getModelClientes().setColumnIdentifiers(this.panelCliente.getNombreColumnasClientes());
			
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
			this.panelCliente.getModelClientes().addRow(fila);
		}		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}
}