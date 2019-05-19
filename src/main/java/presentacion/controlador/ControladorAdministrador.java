package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import modelo.Administrativo;
import modelo.FormaPago;
import modelo.Login;
import modelo.Rol;
import modelo.Transporte;
import dto.AdministrativoDTO;
import dto.FormaPagoDTO;
import dto.LoginDTO;
import dto.RolDTO;
import dto.TransporteDTO;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.administrador.VentanaAgregarEmpleado;
import presentacion.vista.administrador.VistaAdministrador;

public class ControladorAdministrador {
	
	private VistaAdministrador vistaAdministrador;
	private VentanaAgregarEmpleado ventanaAgregarEmpleado;
	private List<TransporteDTO> transportes_en_tabla;
	private List<FormaPagoDTO> fpago_en_tabla;
	private Transporte transporte;
	private FormaPago formapago;
	private ControladorTransporte controladorTransporte;
	private ControladorFormaPago controladorFormaPago;
	
	private Login login;
	
	

	
	public ControladorAdministrador(VistaAdministrador vistaAdministrador){
		this.vistaAdministrador = vistaAdministrador;
		this.ventanaAgregarEmpleado = VentanaAgregarEmpleado.getInstance();
		
		this.vistaAdministrador.getItemAgregarCuenta().addActionListener(ac->mostrarVentanaAgregarEmpleado(ac));
		
		this.vistaAdministrador.getItemAgregarTransporte().addActionListener(ac->agregarPanelTransporte(ac));
		this.vistaAdministrador.getItemVisualizarTransportes().addActionListener(vt->visualizarTransportes(vt));
		this.vistaAdministrador.getItemEditarTransporte().addActionListener(et->editarTransporte(et));
		this.vistaAdministrador.getItemEliminarTransporte().addActionListener(dt->eliminarTransporte(dt));
		this.vistaAdministrador.getPanelTransporte().getBtnRecargarTabla().addActionListener(r->recargarTabla(r));
		
		this.vistaAdministrador.getItemAgregarFormaPago().addActionListener(afp->agregarPanelFormaPago(afp));
		this.vistaAdministrador.getItemVisualizarFormaPago().addActionListener(vfp->visualizarFormaPago(vfp));
		this.vistaAdministrador.getItemEditarFormaPago().addActionListener(efp->editarFormaPago(efp));
		this.vistaAdministrador.getItemEliminarFormaPago().addActionListener(dfp->eliminarFormaPago(dfp));
		this.vistaAdministrador.getPanelFormaPago().getBtnRecargarTabla().addActionListener(r->recargarTabla(r));
	
		

		this.ventanaAgregarEmpleado.getBtnRegistrar().addActionListener(ae->agregarCuentaEmpleado(ae));
		
		this.transporte = new Transporte(new DAOSQLFactory());
		this.formapago = new FormaPago(new DAOSQLFactory());
		this.login = new Login(new DAOSQLFactory());
		this.controladorTransporte = new ControladorTransporte();
		this.controladorFormaPago = new ControladorFormaPago();
	}
	

	public void inicializar(){
		this.vistaAdministrador.mostrarVentana();
		this.llenarTablaTransportes();
		this.llenarTablaFormaPago();
	}
	
	/*Mostrar la ventana para agregar un empleado y carga el comboBox de roles*/
	private void mostrarVentanaAgregarEmpleado(ActionEvent ac) {
		cargarcomboBoxRoles();
		this.ventanaAgregarEmpleado.mostrarVentana(true);
		this.vistaAdministrador.getPanelTransporte().mostrarPanelTransporte(false);
		this.vistaAdministrador.getPanelFormaPago().mostrarPanelFormaPago(false);

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
	//----------------------Transportes-----------------------------------
	private void visualizarTransportes(ActionEvent vt) {
		this.vistaAdministrador.getPanelTransporte().mostrarPanelTransporte(true);
		this.vistaAdministrador.getPanelFormaPago().mostrarPanelFormaPago(false);
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
		this.vistaAdministrador.getPanelTransporte().getModelTransportes().setRowCount(0); //Para vaciar la tabla
		this.vistaAdministrador.getPanelTransporte().getModelTransportes().setColumnCount(0);
		this.vistaAdministrador.getPanelTransporte().getModelTransportes().setColumnIdentifiers(this.vistaAdministrador.getPanelTransporte().getNombreColumnasTransporte());
			
		this.transportes_en_tabla = transporte.obtenerTransportes();
			
		for (int i = 0; i < this.transportes_en_tabla.size(); i++){
			Object[] fila = {this.transportes_en_tabla.get(i).getNombre(),
			};
			this.vistaAdministrador.getPanelTransporte().getModelTransportes().addRow(fila);
		}		
	}
	
	//------------------------------FormaPago-------------------------------------------------
	
	private void visualizarFormaPago(ActionEvent vfp) {
		this.vistaAdministrador.getPanelFormaPago().mostrarPanelFormaPago(true);
		this.vistaAdministrador.getPanelTransporte().mostrarPanelTransporte(false);
		this.llenarTablaFormaPago();
	}
	/*Agrega el panel de Forma pago en la vistaPrinciapal del Administrador*/
	private void agregarPanelFormaPago(ActionEvent afp) {
		this.vistaAdministrador.getPanelFormaPago().mostrarPanelFormaPago(true);
		controladorFormaPago.mostrarVentanaAgregarFormaPago();
	}
	
	private void editarFormaPago(ActionEvent efp) {
		this.vistaAdministrador.getPanelFormaPago().mostrarPanelFormaPago(true);
		int filaSeleccionada = this.vistaAdministrador.getPanelFormaPago().getTablaFormaPago().getSelectedRow();
		if (filaSeleccionada != -1){
			controladorFormaPago.editarFormaPago(filaSeleccionada);
			llenarTablaFormaPago();

		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
		llenarTablaFormaPago();
	}
	
	private void eliminarFormaPago(ActionEvent dt) {
		this.vistaAdministrador.getPanelFormaPago().mostrarPanelFormaPago(true);
		int filaSeleccionada = this.vistaAdministrador.getPanelFormaPago().getTablaFormaPago().getSelectedRow();
		if (filaSeleccionada != -1){
			controladorFormaPago.eliminarFormaPago(filaSeleccionada);
			llenarTablaFormaPago();
		
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}
	

	public void llenarTablaFormaPago(){
		this.vistaAdministrador.getPanelFormaPago().getModelFormaPago().setRowCount(0); //Para vaciar la tabla
		this.vistaAdministrador.getPanelFormaPago().getModelFormaPago().setColumnCount(0);
		this.vistaAdministrador.getPanelFormaPago().getModelFormaPago().setColumnIdentifiers(this.vistaAdministrador.getPanelFormaPago().getNombreColumnasFormaPago());
			
		this.fpago_en_tabla = formapago.obtenerFormaPago();
			
		for (int i = 0; i < this.fpago_en_tabla.size(); i++){
			Object[] fila = {this.fpago_en_tabla.get(i).getTipo(),
			};
			this.vistaAdministrador.getPanelFormaPago().getModelFormaPago().addRow(fila);
		}		
	}
}