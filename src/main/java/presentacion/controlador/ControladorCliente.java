package presentacion.controlador;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
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
import recursos.Mapper;

public class ControladorCliente implements ActionListener{
	private VistaAdministrativo vistaAdministrativo;
	private VentanaRegistrarCliente ventanaRegistrarCliente;
	private VentanaEditarCliente ventanaEditarCliente;
	
	private List<ClienteDTO> clientes_en_tabla;
	private List<ClienteDTO> clientes_aux;
	private String contrasenaProvisoria;

	private Cliente cliente;
	private MedioContacto medioContacto; 
	private Login Modelologin;
	private EnvioDeCorreo enviodeCorreo;
	private Mapper mapper;
	
	public ControladorCliente() {
		super();
	}
	
	public ControladorCliente(VentanaRegistrarCliente ventanaRegistrarCliente, VentanaEditarCliente ventanaEditarCliente, Cliente cliente){
		this.vistaAdministrativo = VistaAdministrativo.getInstance();
		
		this.ventanaRegistrarCliente = ventanaRegistrarCliente;
		this.ventanaEditarCliente = ventanaEditarCliente;
		this.cliente = cliente;
		this.contrasenaProvisoria = null;
		
		this.medioContacto =  new MedioContacto(new DAOSQLFactory());
		this.Modelologin = new Login(new DAOSQLFactory());
		this.enviodeCorreo = new EnvioDeCorreo();
		this.mapper = new Mapper();
		
		this.ventanaRegistrarCliente.getBtnRegistrar().addActionListener(rc->registrarCliente(rc));
		this.ventanaRegistrarCliente.getBtnCancelar().addActionListener(cv->cerrarVentanaCliente(cv));
		this.ventanaEditarCliente.getBtnCancelar().addActionListener(ce->cancelarEditar());
	
		int limiteTexto = 45;
		
		/* Filtros Registrar Cliente */
		this.ventanaRegistrarCliente.getTxtNombre().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				char letra = e.getKeyChar();
					if(Character.isDigit(letra)) {
						Toolkit.getDefaultToolkit().beep();
						e.consume();
					}
					if(ventanaRegistrarCliente.getTxtNombre().getText().length()== limiteTexto){
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
				if(ventanaRegistrarCliente.getTxtApellido().getText().length()== limiteTexto){
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
				if(ventanaRegistrarCliente.getTxtDni().getText().length()== 8){
					e.consume();
				}
			}
		});
		
		this.ventanaRegistrarCliente.getTxtUsuario().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				if(ventanaRegistrarCliente.getTxtUsuario().getText().length()== limiteTexto){
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
				if(ventanaRegistrarCliente.getTxtTelefonoFijo().getText().length()== 8){
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
				if(ventanaRegistrarCliente.getTxtTelefonoCelular().getText().length()== 10){
					e.consume();
				}
			}
		});
		
		this.ventanaRegistrarCliente.getTxtEmail().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				if(ventanaRegistrarCliente.getTxtEmail().getText().length()== limiteTexto){
					e.consume();
				}
			}
		});
		/*Final Filtros Registrar Cliente*/
		
		
		/* Filtros Registrar Cliente */
		this.ventanaEditarCliente.getTxtNombre().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				char letra = e.getKeyChar();
					if(Character.isDigit(letra)) {
						Toolkit.getDefaultToolkit().beep();
						e.consume();
					}
					if(ventanaEditarCliente.getTxtNombre().getText().length()== limiteTexto){
						e.consume();
					}
			}
		});
	
		this.ventanaEditarCliente.getTxtApellido().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				char letra = e.getKeyChar();
				if(Character.isDigit(letra)) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
				if(ventanaEditarCliente.getTxtApellido().getText().length()== limiteTexto){
					e.consume();
				}
			}
		});
		this.ventanaEditarCliente.getTxtDni().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				char letra = e.getKeyChar();
				if(!Character.isDigit(letra)) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
				if(ventanaEditarCliente.getTxtDni().getText().length()== 8){
					e.consume();
				}
			}
		});
		
		this.ventanaEditarCliente.getTxtUsuario().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				if(ventanaEditarCliente.getTxtUsuario().getText().length()== limiteTexto){
					e.consume();
				}
			}
		});
		
		this.ventanaEditarCliente.getTxtTelefonoFijo().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				char letra = e.getKeyChar();
				if(!Character.isDigit(letra)) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
				if(ventanaEditarCliente.getTxtTelefonoFijo().getText().length()== 8){
					e.consume();
				}
			}
		});
		this.ventanaEditarCliente.getTxtTelefonoCelular().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				char letra = e.getKeyChar();
				if(!Character.isDigit(letra)) {
					Toolkit.getDefaultToolkit().beep();
					e.consume();
				}
				if(ventanaEditarCliente.getTxtTelefonoCelular().getText().length()== 10){
					e.consume();
				}
			}
		});
		
		this.ventanaEditarCliente.getTxtEmail().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
				if(ventanaEditarCliente.getTxtEmail().getText().length()== limiteTexto){
					e.consume();
				}
			}
		});
		/*Final Filtros Editar Cliente*/
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
				loginProv);
			 System.out.println("Generamos el cliente: "+nuevoCliente.getNombre()+"-"+nuevoCliente.getMail());

				cliente.agregarCliente(nuevoCliente);
//				vistaAdministrativo.getPanelCliente().getModelClientes().getColumnName(1);
				this.ventanaRegistrarCliente.limpiarCampos();
				this.ventanaRegistrarCliente.cerrarVentana();
				
			ClienteDTO clienteDTO = cliente.getByLoginId(loginProv.getIdDatosLogin());
			enviodeCorreo.enviarDatosDeCuenta(clienteDTO.getMail(), contrasenaProvisoria,clienteDTO.getNombre(),"Generación de usuario");
			this.llenarTablaClientes();
		}
	}

	public void editarCliente(ClienteDTO cliente_a_editar) {
		this.Modelologin.editarLogin(cliente_a_editar.getLogin());
		this.medioContacto.editarMedioContacto(cliente_a_editar.getMedioContacto());
		this.cliente.editarCliente(cliente_a_editar);
		this.llenarTablaClientes();
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
			return validacionesTextFields();
		}
		return camposLlenosRegistrarCliente() ? true : false;
	}

	private boolean validacionesTextFields() {
		if (!Validador.esSoloLetras(this.ventanaRegistrarCliente.getTxtNombre().getText())){
			JOptionPane.showMessageDialog(null, "Debe ingresar un nombre válido", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if (!Validador.esSoloLetras(this.ventanaRegistrarCliente.getTxtApellido().getText())){
			JOptionPane.showMessageDialog(null, "Debe ingresar un nombre válido", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(!Validador.esDniValido(this.ventanaRegistrarCliente.getTxtDni().getText())){
			JOptionPane.showMessageDialog(null, "Debe ingresar un dni válido.\nEl dni se compone por 8 dígitos", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(!Validador.esTelefonoCelularValido(this.ventanaRegistrarCliente.getTxtTelefonoCelular().getText())){
			JOptionPane.showMessageDialog(null, "Debe ingresar un teléfono celular válido.\nEl teléfono se compone por 10 dígitos", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;	
		}else if(!this.ventanaRegistrarCliente.getTxtTelefonoFijo().getText().isEmpty() && !Validador.esTelefonoFijoValido(this.ventanaRegistrarCliente.getTxtTelefonoFijo().getText())){
				JOptionPane.showMessageDialog(null, "Debe ingresar un teléfono fijo válido.\nEl teléfono se compone por 8 dígitos", "Mensaje", JOptionPane.ERROR_MESSAGE);
				return false;
		}else if(!Validador.esMailValido(this.ventanaRegistrarCliente.getTxtEmail().getText())){
			JOptionPane.showMessageDialog(null, "Debe ingresar un email válido", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;	
		}else{
			return true;
		}
	}
	
	private boolean camposLlenosRegistrarCliente(){
		if (ventanaRegistrarCliente.getTxtNombre().getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Debe ingresar el nombre del cliente", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if (ventanaRegistrarCliente.getTxtApellido().getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Debe ingresar el apellido del cliente", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(ventanaRegistrarCliente.getTxtDni().getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Debe ingresar el dni del cliente", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if (ventanaRegistrarCliente.getDateFechaNacimiento().getDate()== null){
			JOptionPane.showMessageDialog(null, "Debe ingresar la fecha de nacimiento del cliente", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(ventanaRegistrarCliente.getTxtUsuario().getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Debe ingresar el nombre de usuario", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(ventanaRegistrarCliente.getTxtTelefonoCelular().getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Debe ingresar el teléfono celular del cliente", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(ventanaRegistrarCliente.getTxtEmail().getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Debe ingresar el email del cliente", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;
		}else{
			return true;
		}
	}
	/*------------------------------Fin de validaciones al registrar cliente-------------------------------------------/*/

	/*------------------------------Validaciones al editar cliente-------------------------------------------/*/

	public boolean validarCamposEditarCliente(){
		if (camposLlenosEditarCliente()) {
			this.ventanaEditarCliente.getTxtNombre().setText(Validador.validarCampo(this.ventanaEditarCliente.getTxtNombre().getText()));
			this.ventanaEditarCliente.getTxtApellido().setText(Validador.validarCampo(this.ventanaEditarCliente.getTxtApellido().getText()));
			return validacionesTextFieldsEditar();
		}
		return camposLlenosRegistrarCliente() ? true : false;
	}
	
	private boolean validacionesTextFieldsEditar() {
		if (!Validador.esSoloLetras(this.ventanaEditarCliente.getTxtNombre().getText())){
			JOptionPane.showMessageDialog(null, "Debe ingresar un nombre válido", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if (!Validador.esSoloLetras(this.ventanaEditarCliente.getTxtApellido().getText())){
			JOptionPane.showMessageDialog(null, "Debe ingresar un nombre válido", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(!Validador.esDniValido(this.ventanaEditarCliente.getTxtDni().getText())){
			JOptionPane.showMessageDialog(null, "Debe ingresar un dni válido.\nEl dni se compone por 8 dígitos", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(!Validador.esTelefonoCelularValido(this.ventanaEditarCliente.getTxtTelefonoCelular().getText())){
			JOptionPane.showMessageDialog(null, "Debe ingresar un teléfono celular válido.\nEl teléfono se compone por 10 dígitos", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;	
		}else if(!this.ventanaEditarCliente.getTxtTelefonoFijo().getText().isEmpty() && !Validador.esTelefonoFijoValido(this.ventanaEditarCliente.getTxtTelefonoFijo().getText())){
				JOptionPane.showMessageDialog(null, "Debe ingresar un teléfono fijo válido.\nEl teléfono se compone por 8 dígitos", "Mensaje", JOptionPane.ERROR_MESSAGE);
				return false;
		}else if(!Validador.esMailValido(this.ventanaEditarCliente.getTxtEmail().getText())){
			JOptionPane.showMessageDialog(null, "Debe ingresar un email válido", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;	
		}else{
			return true;
		}
	}
	
	private boolean camposLlenosEditarCliente(){
		if (ventanaEditarCliente.getTxtNombre().getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Debe ingresar el nombre del cliente", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if (ventanaEditarCliente.getTxtApellido().getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Debe ingresar el apellido del cliente", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(ventanaEditarCliente.getTxtDni().getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Debe ingresar el dni del cliente", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if (ventanaEditarCliente.getDateFechaNacimiento().getDate()== null){
			JOptionPane.showMessageDialog(null, "Debe ingresar la fecha de nacimiento del cliente", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(ventanaEditarCliente.getTxtUsuario().getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Debe ingresar el nombre de usuario", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(ventanaEditarCliente.getTxtTelefonoCelular().getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Debe ingresar el teléfono celular del cliente", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;
		}else if(ventanaEditarCliente.getTxtEmail().getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Debe ingresar el email del cliente", "Mensaje", JOptionPane.ERROR_MESSAGE);
			return false;
		}else{
			return true;
		}
	}
	/*------------------------------Fin de validaciones al editar cliente-------------------------------------------/*/

	
	public void llenarTablaClientes(){
		boolean activos = this.vistaAdministrativo.getPanelCliente().getActivos().isSelected();
		boolean inactivos = this.vistaAdministrativo.getPanelCliente().getInactivos().isSelected();
		
		this.vistaAdministrativo.getPanelCliente().getModelClientes().setRowCount(0); //Para vaciar la tabla
		this.vistaAdministrativo.getPanelCliente().getModelClientes().setColumnCount(0);
		this.vistaAdministrativo.getPanelCliente().getModelClientes().setColumnIdentifiers(this.vistaAdministrativo.getPanelCliente().getNombreColumnasClientes());
			
		this.clientes_en_tabla = new ArrayList<ClienteDTO>();
		this.clientes_aux = cliente.obtenerClientes();

		if(activos == true && inactivos == false) {
			for (ClienteDTO cliente : this.clientes_aux) {
				if (cliente.getLogin().getEstado().equals("activo")) {
					this.clientes_en_tabla.add(cliente);
				}
			}
		}else if(inactivos == true && activos == false) {
			for(ClienteDTO cliente : this.clientes_aux) {
				if(cliente.getLogin().getEstado().equals("inactivo")) {
					this.clientes_en_tabla.add(cliente);
				}
			}
		} else if(activos && inactivos) {
			for (ClienteDTO cliente: this.clientes_aux) {
				this.clientes_en_tabla.add(cliente);
			}
		}
		
		for (int i = 0; i < this.clientes_en_tabla.size(); i++){
			System.out.println("Tablitaaa:"+this.clientes_en_tabla.get(i).getNombre());
			Object[] fila = {this.clientes_en_tabla.get(i).getNombre(),
							 this.clientes_en_tabla.get(i).getApellido(),
							 this.clientes_en_tabla.get(i).getDni(),
							 mapper.parseToString(this.clientes_en_tabla.get(i).getFechaNacimiento()),
							 this.clientes_en_tabla.get(i).getMedioContacto().getTelefonoFijo(),
							 this.clientes_en_tabla.get(i).getMedioContacto().getTelefonoCelular(),
							 this.clientes_en_tabla.get(i).getMedioContacto().getEmail(),
							 this.clientes_en_tabla.get(i).getLogin().getEstado()
							};
			this.vistaAdministrativo.getPanelCliente().getModelClientes().addRow(fila);
		}		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}
}