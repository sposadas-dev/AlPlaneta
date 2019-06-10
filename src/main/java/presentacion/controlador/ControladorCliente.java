package presentacion.controlador;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import presentacion.vista.administrativo.VistaAdministrativo;

public class ControladorCliente implements ActionListener{
	private VistaAdministrativo vistaAdministrativo;
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
		
		/* Filtros */
		this.ventanaRegistrarCliente.getTxtNombre().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
					char letra = e.getKeyChar();
					if(Character.isDigit(letra)) {
						Toolkit.getDefaultToolkit().beep();
						e.consume();
					}
			}
		});
		this.ventanaRegistrarCliente.getTxtApellido().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				char letra = e.getKeyChar();
				if(Character.isDigit(letra)) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
			}
		});
		this.ventanaRegistrarCliente.getTxtDni().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				char letra = e.getKeyChar();
				if(!Character.isDigit(letra)) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
			}
		});
		this.ventanaRegistrarCliente.getTxtTelefonoFijo().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				char letra = e.getKeyChar();
				if(!Character.isDigit(letra)) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
			}
		});
		this.ventanaRegistrarCliente.getTxtTelefonoCelular().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				char letra = e.getKeyChar();
				if(!Character.isDigit(letra)) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
			}
		});
		
		/*Final Filtros*/
		
		this.ventanaEditarCliente.getBtnCancelar().addActionListener(ce->cancelarEditar());
		this.vistaAdministrativo = VistaAdministrativo.getInstance();
	}
	

	public void registrarCliente(ActionEvent rc){
		if (validarCamposRegistrarCliente()){
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
		
			cliente.agregarCliente(nuevoCliente);
			vistaAdministrativo.getPanelCliente().getModelClientes().getColumnName(1);
			this.llenarTablaClientes();
			this.ventanaRegistrarCliente.limpiarCampos();
			this.ventanaRegistrarCliente.cerrarVentana();
		
		ClienteDTO clienteDTO = cliente.getByLoginId(loginProv.getIdDatosLogin());
		enviodeCorreo.enviarNuevaContrasena(clienteDTO.getMail(), contrasenaProvisoria,"Generacion de Usuario");
		}else{
			JOptionPane.showMessageDialog(null, "Verifique los campos", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
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
	
	/*------------------------------Validaciones al registrar cliente-------------------------------------------/*/
	private boolean validarCamposRegistrarCliente(){
		if (camposLlenosRegistrarCliente()) {
			this.ventanaRegistrarCliente.getTxtNombre().setText(Validador.validarCampo(this.ventanaRegistrarCliente.getTxtNombre().getText()));
			this.ventanaRegistrarCliente.getTxtApellido().setText(Validador.validarCampo(this.ventanaRegistrarCliente.getTxtApellido().getText()));
			return Validador.esSoloLetras(this.ventanaRegistrarCliente.getTxtNombre().getText()) &&
				Validador.esSoloLetras(this.ventanaRegistrarCliente.getTxtApellido().getText()) &&
				Validador.esDniValido(this.ventanaRegistrarCliente.getTxtDni().getText()) &&
				Validador.esTelefonoFijoValido(this.ventanaRegistrarCliente.getTxtTelefonoFijo().getText()) &&
				Validador.esTelefonoCelularValido(this.ventanaRegistrarCliente.getTxtTelefonoCelular().getText()) &&	
				Validador.esMailValido(this.ventanaRegistrarCliente.getTxtEmail().getText());
		}
		return camposLlenosRegistrarCliente() ? true : false;
	}
	
	private boolean camposLlenosRegistrarCliente(){
		if (ventanaRegistrarCliente.getTxtNombre().getText().isEmpty() ||
				ventanaRegistrarCliente.getTxtApellido().getText().isEmpty() ||
				ventanaRegistrarCliente.getTxtDni().getText().isEmpty() ||				
				(ventanaRegistrarCliente.getDateFechaNacimiento().getDate()== null) ||
				ventanaRegistrarCliente.getTxtUsuario().getText().isEmpty() ||
				ventanaRegistrarCliente.getTxtTelefonoFijo().getText().isEmpty() ||
				ventanaRegistrarCliente.getTxtTelefonoCelular().getText().isEmpty() ||
				ventanaRegistrarCliente.getTxtEmail().getText().isEmpty()
			){
				return false;
			}
			return true;
		}
	/*------------------------------Fin de validaciones al registrar cliente-------------------------------------------/*/

	/*------------------------------Validaciones al editar cliente-------------------------------------------/*/

	public boolean validarCamposEditarCliente(){
		if (camposLlenosEditarCliente()) {
			this.ventanaEditarCliente.getTxtNombre().setText(Validador.validarCampo(this.ventanaEditarCliente.getTxtNombre().getText()));
			this.ventanaEditarCliente.getTxtApellido().setText(Validador.validarCampo(this.ventanaEditarCliente.getTxtApellido().getText()));
			return Validador.esSoloLetras(this.ventanaEditarCliente.getTxtNombre().getText()) &&
				Validador.esSoloLetras(this.ventanaEditarCliente.getTxtApellido().getText())&&
				Validador.esDniValido(this.ventanaEditarCliente.getTxtDni().getText()) &&
				Validador.esTelefonoFijoValido(this.ventanaEditarCliente.getTxtTelefonoFijo().getText()) &&
				Validador.esTelefonoCelularValido(this.ventanaEditarCliente.getTxtTelefonoCelular().getText()) &&	
				Validador.esMailValido(this.ventanaEditarCliente.getTxtEmail().getText());
		}
		return camposLlenosEditarCliente() ? true : false;
	}
	
	private boolean camposLlenosEditarCliente(){
		if (ventanaEditarCliente.getTxtNombre().getText().isEmpty() ||
				ventanaEditarCliente.getTxtApellido().getText().isEmpty() ||
				ventanaEditarCliente.getTxtDni().getText().isEmpty() ||				
				(ventanaEditarCliente.getDateFechaNacimiento().getDate()== null) ||
				ventanaEditarCliente.getTxtUsuario().getText().isEmpty() ||
				ventanaEditarCliente.getTxtTelefonoFijo().getText().isEmpty() ||
				ventanaEditarCliente.getTxtTelefonoCelular().getText().isEmpty() ||
				ventanaEditarCliente.getTxtEmail().getText().isEmpty()
			){
				return false;
			}
			return true;
		}
	/*------------------------------Fin de validaciones al editar cliente-------------------------------------------/*/

	
	
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