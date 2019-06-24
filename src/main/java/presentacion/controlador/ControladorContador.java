package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import modelo.Administrador;
import modelo.Administrativo;
import modelo.Coordinador;
import modelo.Login;
import modelo.Rol;
import dto.AdministradorDTO;
import dto.AdministrativoDTO;
import dto.ClienteDTO;
import dto.ContadorDTO;
import dto.CoordinadorDTO;
import dto.LoginDTO;
import dto.RolDTO;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.contador.VentanaAgregarSueldo;
import presentacion.vista.contador.VentanaCambiarContrasena;
import presentacion.vista.contador.VentanaVisualizarEmpleados;
import presentacion.vista.contador.VistaContador;

public class ControladorContador implements ActionListener {

	private VistaContador vistaContador;
	private VentanaVisualizarEmpleados ventanaVisualizarEmpleados;
	private VentanaAgregarSueldo ventanaAgregarSueldo;
	private VentanaCambiarContrasena ventanaCambiarContrasenia;
	private ContadorDTO contadorLogueado;
	private Login login;
	
	private List<AdministradorDTO> administradores_en_tabla;
	private List<AdministrativoDTO> administrativos_en_tabla;
	private List<CoordinadorDTO> coordinadores_en_tabla;
	
	private ControladorSueldo controladorSueldo;
	private String aceptada = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private DefaultTableModel tableModel;
	private StringBuilder cad = new StringBuilder();


	public ControladorContador(VistaContador vistaContador,ContadorDTO contadorLogueado) {
	
		this.vistaContador = vistaContador;		
		this.contadorLogueado = contadorLogueado;
		this.ventanaVisualizarEmpleados = VentanaVisualizarEmpleados.getInstance();
		this.ventanaCambiarContrasenia = VentanaCambiarContrasena.getInstance();
		this.ventanaAgregarSueldo = VentanaAgregarSueldo.getInstance();
		
		this.vistaContador.getItemCambiarContrasenia().addActionListener(dp->mostrarVentanaCambiarContrasenia(dp));
		this.vistaContador.getItemVisualizarSueldos().addActionListener(vs->mostrarPanelSueldos(vs));
		this.vistaContador.getItemSueldos().addActionListener(ve->mostrarVentanaVisualizarEmpleados(ve));
		
//		this.ventanaVisualizarEmpleados.getBtnAgregarSueldo().addActionListener(ms->mostrarVentanaAgregarSueldo(ms));
		
		this.ventanaCambiarContrasenia.getBtnAceptar().addActionListener(c->cambiarContrasenia(c));
		this.ventanaCambiarContrasenia.getBtnCancelar().addActionListener(c->salirVentanaCambiarContrasenia(c));
		this.login = new Login(new DAOSQLFactory());
	
		this.contadorLogueado = contadorLogueado;
		this.controladorSueldo = new ControladorSueldo(ventanaVisualizarEmpleados);
		

	}

	public void inicializar(){
		this.vistaContador.mostrarVentana();
		this.vistaContador.getMenuUsuarioLogueado().setText(contadorLogueado.getNombre()+" "+contadorLogueado.getApellido());
	}
	
	private void cambiarContrasenia(ActionEvent c) {
		String passwordActual = new String(this.ventanaCambiarContrasenia.getPassActual().getPassword());
		String passwordConfirmacion1 = new String(this.ventanaCambiarContrasenia.getPassNueva().getPassword());
		String passwordConfirmacion2 = new String(this.ventanaCambiarContrasenia.getConfirmacionContrasena().getPassword());
		
		System.out.println(passwordConfirmacion1+" "+passwordConfirmacion2);
		
		if(!passwordConfirmacion1.equals(passwordConfirmacion2)){
			JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden ", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
		else if(!passwordActual.equals(contadorLogueado.getDatosLogin().getContrasena())){
			JOptionPane.showMessageDialog(null, "La contraseña actual es incorrecta", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}else{
			LoginDTO loginDTO = new LoginDTO();
			loginDTO.setIdDatosLogin(contadorLogueado.getDatosLogin().getIdDatosLogin());
			loginDTO.setUsuario(contadorLogueado.getDatosLogin().getUsuario());
			loginDTO.setRol(contadorLogueado.getDatosLogin().getRol());
			loginDTO.setEstado(contadorLogueado.getDatosLogin().getEstado());
		
			String password = new String(this.ventanaCambiarContrasenia.getPassNueva().getPassword());
			loginDTO.setContrasena(password);
			this.login.editarLogin(loginDTO);
			this.ventanaCambiarContrasenia.mostrarVentana(false);
			this.ventanaCambiarContrasenia.limpiarCampos();
			JOptionPane.showMessageDialog(null, "Contraseña actualizada", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
		}
	}
		private void mostrarVentanaCambiarContrasenia(ActionEvent dp) {
			this.ventanaCambiarContrasenia.limpiarCampos();
			this.ventanaCambiarContrasenia.mostrarVentana(true);
	}
	
		private void salirVentanaCambiarContrasenia(ActionEvent c) {
			this.ventanaCambiarContrasenia.limpiarCampos();
			this.ventanaCambiarContrasenia.mostrarVentana(false);;
	}
	
	private void mostrarPanelSueldos(ActionEvent ve) {
		controladorSueldo.llenarTablaEmpleados();
		this.vistaContador.getPanelSueldos().setVisible(true);
		
	}	
		
	private void mostrarVentanaVisualizarEmpleados(ActionEvent ve) {
		this.ventanaVisualizarEmpleados.mostrarVentana(true);
		cargarComboBoxRoles();
	}	
		
	private void mostrarVentanaAgregarSueldo(ActionEvent ms) {

//		}else{
//			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
//		}	
	}

	
	private void cargarComboBoxRoles(){
		Rol rol = new Rol(new DAOSQLFactory());
		List<RolDTO> rolesDTO = rol.obtenerRoles();
		String[] roles = new String[rolesDTO.size()]; //TODO: Puse -1 porque no se deberia cargar el rol "cliente" //VER
		roles[0]="Seleccione un rol";
		for(int i=0; i<rolesDTO.size()-1;i++){
			String rango = rolesDTO.get(i).getNombre();
			roles [i+1] = rango;
		}
		this.ventanaVisualizarEmpleados.getComboBoxRoles().setModel(new DefaultComboBoxModel(roles));
	}

//	public void llenarTablaEmpleados(){
//		this.ventanaVisualizarEmpleados.getModelEmpleados().setRowCount(0); //Para vaciar la tabla
//		this.ventanaVisualizarEmpleados.getModelEmpleados().setColumnCount(0);
//		this.ventanaVisualizarEmpleados.getModelEmpleados().setColumnIdentifiers(this.ventanaVisualizarEmpleados.getNombreColumnas());
//		
//		Administrador administrador = new Administrador(new DAOSQLFactory());
//		Administrativo administrativo = new Administrativo(new DAOSQLFactory());
//		Coordinador coordinador = new Coordinador(new DAOSQLFactory());
//
//		administradores_en_tabla = administrador.obtenerAdministradores();
//		administrativos_en_tabla = administrativo.obtenerAdministrativos();
//		coordinadores_en_tabla = coordinador.obtenerCoordinadores();
//		
//		for (int i = 0; i < this.administradores_en_tabla.size(); i++) {
//			Object[] fila = {
//					this.administradores_en_tabla.get(i).getNombre(),
//					this.administradores_en_tabla.get(i).getApellido(),
//					this.administradores_en_tabla.get(i).getDni(),
//					this.administradores_en_tabla.get(i).getDatosLogin().getRol().getNombre(),
//					this.administradores_en_tabla.get(i).getLocal().getNombreLocal()
//			};
//		 
//			this.ventanaVisualizarEmpleados.getModelEmpleados().addRow(fila);
//		}
//		for (int i = 0; i < this.administrativos_en_tabla .size(); i++) {
//			Object[] fila = {
//					this.administrativos_en_tabla .get(i).getNombre(),
//					this.administrativos_en_tabla .get(i).getApellido(),
//					this.administrativos_en_tabla .get(i).getDni(),
//					this.administrativos_en_tabla .get(i).getDatosLogin().getRol().getNombre(),
//					this.administrativos_en_tabla .get(i).getLocal().getNombreLocal()
//			};
//		 
//			this.ventanaVisualizarEmpleados.getModelEmpleados().addRow(fila);
//		}	
//		
//		for (int i = 0; i < this.coordinadores_en_tabla.size(); i++) {
//			Object[] fila = {
//					this.coordinadores_en_tabla.get(i).getNombre(),
//					this.coordinadores_en_tabla.get(i).getApellido(),
//					this.coordinadores_en_tabla.get(i).getDni(),
//					this.coordinadores_en_tabla.get(i).getDatosLogin().getRol().getNombre(),
//					this.coordinadores_en_tabla.get(i).getLocal().getNombreLocal()
//			};
//		 
//			this.ventanaVisualizarEmpleados.getModelEmpleados().addRow(fila);
//		}	
//	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
    }
}