package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dto.ClienteDTO;
import dto.LoginDTO;
import dto.MedioContactoDTO;
import dto.RolDTO;
import modelo.Cliente;
import modelo.Login;
import modelo.MedioContacto;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.administrativo.PanelCliente;
import presentacion.vista.administrativo.VentanaRegistrarCliente;

public class ControladorCliente implements ActionListener{
	
	private VentanaRegistrarCliente ventanaCliente;
	private PanelCliente panelCliente;
	private List<ClienteDTO> clientes_en_tabla;

	private Cliente cliente;
	private MedioContacto medioContacto; 
	private Login login;

	public ControladorCliente(VentanaRegistrarCliente ventanaCliente, Cliente cliente){
		this.ventanaCliente = ventanaCliente;
		this.cliente = cliente;
		
		this.medioContacto =  new MedioContacto(new DAOSQLFactory());
		this.login = new Login(new DAOSQLFactory());
		this.panelCliente = new PanelCliente();
		this.ventanaCliente.getBtnRegistrar().addActionListener(rc->registrarCliente(rc));
		
	}

	public void registrarCliente(ActionEvent rc){
		/*Obtenemos la fecha de nacimiento , y la parseamos a tipo de date de SQL*/
		java.util.Date dateFechaNacimiento = ventanaCliente.getDateFechaNacimiento().getDate();
		java.sql.Date fechaNacimiento = new java.sql.Date(dateFechaNacimiento.getTime());
	
		/*Obtenemos el medio de contacto del cliente*/
		MedioContactoDTO mContacto = new MedioContactoDTO();
		mContacto.setTelefonoFijo(this.ventanaCliente.getTxtTelefonoFijo().getText());
		mContacto.setTelefonoCelular(this.ventanaCliente.getTxtTelefonoCelular().getText());
		mContacto.setEmail(this.ventanaCliente.getTxtEmail().getText());
	
		medioContacto.agregarMedioContacto(mContacto);
	
		LoginDTO loginCliente = new LoginDTO();
		loginCliente.setUsuario(this.ventanaCliente.getTxtUsuario().getText());
		loginCliente.setContrasena(this.ventanaCliente.getTxtContrasenia().getText());
		loginCliente.setRol(new RolDTO(5,"cliente"));
		
		login.agregarLogin(loginCliente);
		
		ClienteDTO nuevoCliente = new ClienteDTO(0,
			this.ventanaCliente.getTxtNombre().getText(),
			this.ventanaCliente.getTxtApellido().getText(),
			this.ventanaCliente.getTxtDni().getText(),
			fechaNacimiento,
			obtenerMedioContactoDTO(),
			obtenerLoginDTO()
	);
//		if(camposCorrectos()){
			cliente.agregarCliente(nuevoCliente);
			this.llenarTablaClientes();
			this.ventanaCliente.limpiarCampos();
			this.ventanaCliente.dispose();
//		}
	}
	
	private MedioContactoDTO obtenerMedioContactoDTO() {
		MedioContactoDTO mContactoDTO = new MedioContactoDTO();
		List<MedioContactoDTO> medios = medioContacto.obtenerMediosContacto();
		for(MedioContactoDTO m: medios){
			if(m.getEmail().toString().equals(this.ventanaCliente.getTxtEmail().getText()) &&
					m.getTelefonoCelular().equals(this.ventanaCliente.getTxtTelefonoCelular().getText())&&
					m.getTelefonoFijo().equals(this.ventanaCliente.getTxtTelefonoFijo().getText())){
			mContactoDTO = m;
		}
	}
		return mContactoDTO;
	}
	
	private LoginDTO obtenerLoginDTO() {
		LoginDTO loginDTO = new LoginDTO();
		List<LoginDTO> logins = login.obtenerLogin();
		for(LoginDTO l: logins){
			if(l.getUsuario().equals(this.ventanaCliente.getTxtUsuario().getText()) &&
					l.getContrasena().equals(this.ventanaCliente.getTxtContrasenia().getText())){
			loginDTO = l;
		}
	}
		return loginDTO;
	}

	private boolean camposLlenos(){
		if (ventanaCliente.getTxtNombre().getText().isEmpty() ||
				ventanaCliente.getTxtApellido().getText().isEmpty() ||
				ventanaCliente.getTxtDni().getText().isEmpty() ||				
				(ventanaCliente.getDateFechaNacimiento().getDate()== null) ||
				ventanaCliente.getTxtUsuario().getText().isEmpty() ||
				ventanaCliente.getTxtContrasenia().getText().isEmpty() ||
				ventanaCliente.getTxtTelefonoFijo().getText().isEmpty() ||
				ventanaCliente.getTxtTelefonoCelular().getText().isEmpty() ||
				ventanaCliente.getTxtEmail().getText().isEmpty()
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