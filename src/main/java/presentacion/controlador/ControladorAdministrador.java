package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import dto.AdministrativoDTO;
import dto.LoginDTO;
import dto.RolDTO;
import dto.TransporteDTO;
import modelo.Administrativo;
import modelo.Login;
import modelo.Rol;
import modelo.Transporte;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.administrador.VentanaAgregarEmpleado;
import presentacion.vista.administrador.VistaAdministrador;

public class ControladorAdministrador {
	
	private VistaAdministrador vistaAdministrador;
	private VentanaAgregarEmpleado ventanaAgregarEmpleado;
	private List<TransporteDTO> transportes_en_tabla;
	
	private Transporte transporte;
	private ControladorTransporte controladorTransporte;
	private Login login;
	
	private ControladorPais controladorPais;
	private ControladorCiudad controladorCiudad;
	private ControladorProvincia controladorProvincia;
	
	public ControladorAdministrador(VistaAdministrador vistaAdministrador){
		this.vistaAdministrador = vistaAdministrador;
//INSTANCES		
		this.ventanaAgregarEmpleado = VentanaAgregarEmpleado.getInstance();

//MENU ITEMS		
		this.vistaAdministrador.getItemAgregarCuenta().addActionListener(ac->mostrarVentanaAgregarEmpleado(ac));
		this.vistaAdministrador.getItemAgregarTransporte().addActionListener(ac->agregarPanelTransporte(ac));
		this.vistaAdministrador.getItemVisualizarTransportes().addActionListener(vt->visualizarTransportes(vt));
		this.vistaAdministrador.getItemEditarTransporte().addActionListener(et->editarTransporte(et));
		this.vistaAdministrador.getItemEliminarTransporte().addActionListener(dt->eliminarTransporte(dt));

//ITEM DESTINOS	
		this.vistaAdministrador.getItemPais().addActionListener(p->mostrarVentanaAgregarPais(p));
		this.vistaAdministrador.getItemProvincia().addActionListener(p->mostrarVentanaAgregarProvincia(p));
		this.vistaAdministrador.getItemCiudad().addActionListener(p->mostrarVentanaAgregarCiudad(p));

		
//BTN.LISTENER		
		this.ventanaAgregarEmpleado.getBtnRegistrar().addActionListener(ae->agregarCuentaEmpleado(ae));
		
		this.transporte = new Transporte(new DAOSQLFactory());
		this.login = new Login(new DAOSQLFactory());

//CONTROLADORES		
		this.controladorTransporte = new ControladorTransporte();
		
		this.controladorPais = ControladorPais.getInstance();
		this.controladorProvincia = ControladorProvincia.getInstance();
		this.controladorCiudad = ControladorCiudad.getInstance();
		
	}

	private void mostrarVentanaAgregarCiudad(ActionEvent p) {
		this.controladorCiudad.llenarTablaVistaCiudades();
		this.controladorCiudad.mostrarVistaCiudad();
	}

	private void mostrarVentanaAgregarProvincia(ActionEvent p) {
		this.controladorProvincia.llenarTablaVistaProvincias();
		this.controladorProvincia.mostrarVistaProvincia();
	}

	private void mostrarVentanaAgregarPais(ActionEvent p) {
		this.controladorPais.llenarTablaVistaPaises();
		this.controladorPais.mostrarVistaPais();
	}

	/*Agrega el panel de transporte en la vistaPrinciapal del Administrador*/
	private void agregarPanelPaises(ActionEvent ac) {
		this.vistaAdministrador.getPanelTransporte().mostrarPanelTransporte(true);
		controladorTransporte.mostrarVentanaAgregarTransporte();
	}

	public void inicializar(){
		this.vistaAdministrador.mostrarVentana();
//		this.llenarTablaTransportes();
	}
	
	/*Mostrar la ventana para agregar un empleado y carga el comboBox de roles*/
	private void mostrarVentanaAgregarEmpleado(ActionEvent ac) {
		cargarcomboBoxRoles();
		this.ventanaAgregarEmpleado.mostrarVentana(true);
		this.vistaAdministrador.getPanelTransporte().mostrarPanelTransporte(false);

	}
	
	/*Método para agregar a un empleado según el item que selecciona en el comboBox*/
	private void agregarCuentaEmpleado(ActionEvent ac) {
		//TODO: VER
		if(ventanaAgregarEmpleado.getComboBoxRoles().getSelectedItem().equals("administrativo")){
			LoginDTO nuevoLogin = new LoginDTO();
			nuevoLogin.setUsuario(ventanaAgregarEmpleado.getTxtUsuario().getText());
			nuevoLogin.setContrasena(ventanaAgregarEmpleado.getTxtContrasenia().getText());
			nuevoLogin.setRol(new RolDTO(2,"administrativo"));

			login.agregarLogin(nuevoLogin);
			
			AdministrativoDTO nuevoAdministrativo = new AdministrativoDTO(0,
					ventanaAgregarEmpleado.getTxtNombre().getText(),
					obtenerLoginDTO());
			
			Administrativo administrativo = new Administrativo(new DAOSQLFactory());
			administrativo.agregarAdministrativo(nuevoAdministrativo);
			this.ventanaAgregarEmpleado.mostrarVentana(false);
		}
	}
	
	private LoginDTO obtenerLoginDTO() {
		LoginDTO loginDTO = new LoginDTO();
		List<LoginDTO> logins = login.obtenerLogin();
		for(LoginDTO l: logins){
			if(l.getUsuario().equals(this.ventanaAgregarEmpleado.getTxtUsuario().getText()) &&
					l.getContrasena().equals(this.ventanaAgregarEmpleado.getTxtContrasenia().getText())){
			loginDTO = l;
		}
	}
		return loginDTO;
	}
	
	/*Carga del comboBox de roles*/
	private void cargarcomboBoxRoles(){
		ventanaAgregarEmpleado.getComboBoxRoles().removeAllItems();
		Rol rol = new Rol(new DAOSQLFactory());
		List<RolDTO> rolesDTO = rol.obtenerRoles();
		String[] roles = new String[rolesDTO.size()-1]; //TODO: Puse -1 porque no se deberia cargar el rol "cliente" //VER
		for(int i=0; i<rolesDTO.size()-1;i++){
			String rango = rolesDTO.get(i).getNombre();
			roles [i] = rango;
		}
		this.ventanaAgregarEmpleado.getComboBoxRoles().setModel(new DefaultComboBoxModel(roles));
	}
	
	private void visualizarTransportes(ActionEvent vt) {
		this.vistaAdministrador.getPanelTransporte().mostrarPanelTransporte(true);
		this.llenarTablaTransportes();
	}
	/*Agrega el panel de transporte en la vistaPrinciapal del Administrador*/
	private void agregarPanelTransporte(ActionEvent ac) {
		this.vistaAdministrador.getPanelTransporte().mostrarPanelTransporte(true);
		controladorTransporte.mostrarVentanaAgregarTransporte();
	}
	
	private void editarTransporte(ActionEvent et) {
		this.vistaAdministrador.getPanelTransporte().mostrarPanelTransporte(true);
		int filaSeleccionada = this.vistaAdministrador.getPanelTransporte().getTablaTransportes().getSelectedRow();
		if (filaSeleccionada != -1){
			controladorTransporte.editarTransporte(filaSeleccionada);
			llenarTablaTransportes();

		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
		llenarTablaTransportes();
	}
	
	private void eliminarTransporte(ActionEvent dt) {
		this.vistaAdministrador.getPanelTransporte().mostrarPanelTransporte(true);
		int filaSeleccionada = this.vistaAdministrador.getPanelTransporte().getTablaTransportes().getSelectedRow();
		if (filaSeleccionada != -1){
			controladorTransporte.eliminarTransporte(filaSeleccionada);
			llenarTablaTransportes();
		
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void recargarTabla(ActionEvent r){
		llenarTablaTransportes();
	}

	public void llenarTablaTransportes(){
		this.vistaAdministrador.getPanelTransporte().getModelClientes().setRowCount(0); //Para vaciar la tabla
		this.vistaAdministrador.getPanelTransporte().getModelClientes().setColumnCount(0);
		this.vistaAdministrador.getPanelTransporte().getModelClientes().setColumnIdentifiers(this.vistaAdministrador.getPanelTransporte().getNombreColumnasClientes());
			
		this.transportes_en_tabla = transporte.obtenerTransportes();
			
		for (int i = 0; i < this.transportes_en_tabla.size(); i++){
			Object[] fila = {this.transportes_en_tabla.get(i).getNombre(),
			};
			this.vistaAdministrador.getPanelTransporte().getModelClientes().addRow(fila);
		}		
	}
}