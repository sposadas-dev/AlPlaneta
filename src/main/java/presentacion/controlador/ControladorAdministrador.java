package presentacion.controlador;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import dto.AdministradorDTO;
import dto.AdministrativoDTO;
import dto.ContadorDTO;
import dto.CoordinadorDTO;
import dto.FormaPagoDTO;
import dto.LocalDTO;
import dto.LoginDTO;
import dto.RolDTO;
import dto.TransporteDTO;
import modelo.Administrador;
import modelo.Administrativo;
import modelo.Cliente;
import modelo.Contador;
import modelo.Coordinador;
import modelo.FormaPago;
import modelo.Local;
import modelo.Login;
import modelo.Rol;
import modelo.Transporte;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.administrador.LoadingWorker;
import presentacion.vista.administrador.PanelEmpleados;
import presentacion.vista.administrador.VentanaAgregarEmpleado;
import presentacion.vista.administrador.VentanaAgregarLocal;
import presentacion.vista.administrador.VentanaEditarCuenta;
import presentacion.vista.administrador.VentanaEditarViaje;
import presentacion.vista.administrador.VistaAdministrador;

public class ControladorAdministrador {
	
	private VistaAdministrador vistaAdministrador;
	private VentanaAgregarEmpleado ventanaAgregarEmpleado;
	private VentanaEditarCuenta ventanaEditarCuenta;
	private VentanaAgregarLocal ventanaAgregarLocal;
	private VentanaEditarViaje ventanaEditarViaje;
	private int filaSeleccionada;
	private PanelEmpleados panel;
	
	private List<TransporteDTO> transportes_en_tabla;
	private List<FormaPagoDTO> fpago_en_tabla;
//	private List<AdministrativoDTO> administrativos_en_tabla;
	private List<LoginDTO> logins_en_tabla;
	private List<LoginDTO> logins_aux;
	private List<LocalDTO> locales_en_tabla;
	
	private Administrador administrador;
	private Administrativo administrativo;
	private Cliente cliente;
	private Coordinador coordinador;
	private Local local;
	private Contador contador;
	
	private AdministradorDTO administradorEdit;
	private AdministrativoDTO administrativoEdit;
	private CoordinadorDTO coordinadorEdit;
	private ContadorDTO contadorEdit;
	
	private Transporte transporte;
	private FormaPago formapago;
	private ControladorTransporte controladorTransporte;
	private ControladorFormaPago controladorFormaPago;
	private Controlador controlador;
	
	private Login login;

	private ControladorPais controladorPais;
	private ControladorCiudad controladorCiudad;
	private ControladorProvincia controladorProvincia;
	
	public ControladorAdministrador(VistaAdministrador vistaAdministrador){
		this.vistaAdministrador = vistaAdministrador;
//INSTANCES		
		this.ventanaAgregarEmpleado = VentanaAgregarEmpleado.getInstance();
		this.ventanaEditarCuenta = VentanaEditarCuenta.getInstance();
		this.ventanaAgregarLocal = VentanaAgregarLocal.getInstance();

		this.ventanaEditarViaje = VentanaEditarViaje.getInstance();
//MENU ITEMS		
		this.vistaAdministrador.getItemAgregarCuenta().addActionListener(ac->mostrarVentanaAgregarEmpleado(ac));
		this.vistaAdministrador.getItemEditarCuenta().addActionListener(mve->mostrarVentanaEditarCuenta(mve));
		this.vistaAdministrador.getItemEliminarCuenta().addActionListener(cc->desactivarCuenta(cc));
		this.vistaAdministrador.getItemActivarCuenta().addActionListener(acc->activarCuenta(acc));
		
		this.vistaAdministrador.getItemAgregarTransporte().addActionListener(ac->agregarPanelTransporte(ac));
		this.vistaAdministrador.getItemVisualizarTransportes().addActionListener(vt->visualizarTransportes(vt));
		this.vistaAdministrador.getItemEditarTransporte().addActionListener(et->editarTransporte(et));
		this.vistaAdministrador.getItemEliminarTransporte().addActionListener(dt->eliminarTransporte(dt));

		this.vistaAdministrador.getItemVisualizarLocales().addActionListener(vl->visualizarLocales(vl));
		this.vistaAdministrador.getItemAgregarLocal().addActionListener(al->agregarPanelLocales(al));
//		this.vistaAdministrador.getItemEditarLocal().addActionListener(el->editarLocal(el));
//		this.vistaAdministrador.getItemEliminarLocal().addActionListener(dl->eliminarLocal(dl));

//		this.vistaAdministrador.getPanelTransporte().getBtnRecargarTabla().addActionListener(r->recargarTabla(r));
		
		this.vistaAdministrador.getItemAgregarFormaPago().addActionListener(afp->agregarPanelFormaPago(afp));
		this.vistaAdministrador.getItemVisualizarFormaPago().addActionListener(vfp->visualizarFormaPago(vfp));
		this.vistaAdministrador.getItemEditarFormaPago().addActionListener(efp->editarFormaPago(efp));
		this.vistaAdministrador.getItemEliminarFormaPago().addActionListener(dfp->eliminarFormaPago(dfp));
		
//		this.vistaAdministrador.getPanelFormaPago().getBtnRecargarTabla().addActionListener(r->recargarTablaFormaPago(r));
		this.vistaAdministrador.getPanelEmpleados().getActivos().addActionListener(sa->cargarActivos(sa));
		this.vistaAdministrador.getPanelEmpleados().getInactivos().addActionListener(si->cargarInactivos(si));

//ITEM DESTINOS	
		this.vistaAdministrador.getItemPais().addActionListener(p->mostrarVentanaAgregarPais(p));
		this.vistaAdministrador.getItemProvincia().addActionListener(p->mostrarVentanaAgregarProvincia(p));
		this.vistaAdministrador.getItemCiudad().addActionListener(p->mostrarVentanaAgregarCiudad(p));
//ITEM VIAJES
		this.vistaAdministrador.getItemVisualizarViajes().addActionListener(v->mostrarPanelDeViajes(v));
		this.vistaAdministrador.getItemAgregarViaje().addActionListener(v->mostrarVentanaViaje(v));
		this.vistaAdministrador.getItemEditarViaje().addActionListener(v->mostrarVentanaEditarViaje(v));
		//		BTN.LISTENER		
		this.ventanaAgregarEmpleado.getBtnRegistrar().addActionListener(ae->agregarCuentaEmpleado(ae));
		this.ventanaAgregarEmpleado.getTxtNombre().addKeyListener(new KeyAdapter(){            
			public void keyTyped(KeyEvent e){
					char letra = e.getKeyChar();
					if(Character.isDigit(letra)) {
						Toolkit.getDefaultToolkit().beep();
						e.consume();
					}
			}
		});
//		this.ventanaAgregarEmpleado.getBtnCancelar().addActionListener(c->cancelarAgregarCuentaEmpleado(c));
		this.ventanaAgregarLocal.getBtnAgregar().addActionListener(agl->agregarLocal(agl));
		
		this.ventanaEditarCuenta.getBtnRegistrar().addActionListener(ec->editarCuenta(ec));
		this.ventanaEditarCuenta.getBtnCancelar().addActionListener(can->cancelarEditarCuenta(can));
		
		this.vistaAdministrador.getItemBackup().addActionListener(b -> crearBackup(b));
		this.vistaAdministrador.getItemRestore().addActionListener(r -> cargarRestore(r));


		this.administrador = new Administrador(new DAOSQLFactory());
		this.administrativo = new Administrativo(new DAOSQLFactory());
		this.coordinador = new Coordinador(new DAOSQLFactory());
		this.contador = new Contador(new DAOSQLFactory());
		
		this.transporte = new Transporte(new DAOSQLFactory());
		this.formapago = new FormaPago(new DAOSQLFactory());
		this.login = new Login(new DAOSQLFactory());
		this.local = new Local(new DAOSQLFactory());

//CONTROLADORES		
		this.controladorTransporte = new ControladorTransporte();
		this.controladorFormaPago = new ControladorFormaPago();
		
		this.controladorPais = ControladorPais.getInstance();
		this.controladorProvincia = ControladorProvincia.getInstance();
		this.controladorCiudad = ControladorCiudad.getInstance();
		this.controlador = Controlador.getInstance();
	}
	
	private void mostrarPanelDeViajes(ActionEvent v) {
		this.controlador.mostrarPanelDeViajes();
	}

	private void mostrarVentanaEditarViaje(ActionEvent v) {
		this.controlador.mostrarPanelEditarViaje();
	}

	private void crearBackup(ActionEvent b) {
		try {
			JFileChooser f = new JFileChooser();
			f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			f.showSaveDialog(null);
			
			
			
			String bat = "C:\\mysql-5.7.19-winx64\\bin\\mysqldump -uroot -ppass alplaneta > " + f.getSelectedFile().toString() + "\\alplaneta.sql";

			final File file = new File("backup.bat");
			file.createNewFile();
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			writer.println(bat);
			writer.close();

			Process p = Runtime.getRuntime().exec("cmd /c backup.bat");
			p.waitFor();
			file.delete();

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "No se creó el backup. " + ex.getMessage());
		}
	}
	
	private void cargarRestore(ActionEvent r) {
		JFileChooser f = new JFileChooser();
		f.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		f.showOpenDialog(null);

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				String bat = "C:\\mysql-5.7.19-winx64\\bin\\mysql -uroot -ppass alplaneta < " + f.getSelectedFile().toString();

				try {
					final File file = new File("backup.bat");
					file.createNewFile();
					PrintWriter writer = new PrintWriter(file, "UTF-8");
					writer.println(bat);
					writer.close();

					Process p = Runtime.getRuntime().exec("cmd /c backup.bat");
					p.waitFor();
					file.delete();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "No se cargó el backup correctamente. "+ ex.getMessage() );
				}
			}
		});
		try {
			generarThread(thread);
		} catch (InterruptedException ex1) {
			JOptionPane.showMessageDialog(null, "No se cargó el backup correctamente. " + ex1.getMessage());
		}
	}
	
	private void generarThread(Thread thread) throws InterruptedException {
		LoadingWorker work = new LoadingWorker(vistaAdministrador, "Por favor aguarde mientras se realiza la restauración de datos .", thread,"/recursos/loading.gif");
		work.mostrar();
	    inicializar();
	
	}
	
	public void cargarInactivos(ActionEvent si) {
		this.llenarTablaEmpleados();
	}

	public void cargarActivos(ActionEvent sa) {
		this.llenarTablaEmpleados();
	}

	private void desactivarCuenta(ActionEvent cc) {
		int filaSelect = this.vistaAdministrador.getPanelEmpleados().getTablaEmpleados().getSelectedRow();
		if (filaSelect != -1){
			String estado = "inactivo";
			this.login.editarEstado(estado, this.logins_en_tabla.get(filaSelect).getIdDatosLogin());
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
		llenarTablaEmpleados();
	}
	
	private void activarCuenta(ActionEvent acc) {
		int filaSelect = this.vistaAdministrador.getPanelEmpleados().getTablaEmpleados().getSelectedRow();
		if (filaSelect != -1){
			String estado = "activo";
			this.login.editarEstado(estado, this.logins_en_tabla.get(filaSelect).getIdDatosLogin());
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
		llenarTablaEmpleados();
	}

	private void visualizarLocales(ActionEvent vl) {
		this.vistaAdministrador.getPanelTransporte().mostrarPanelTransporte(false);
		this.vistaAdministrador.getPanelFormaPago().mostrarPanelFormaPago(false);
		this.vistaAdministrador.getPanelEmpleados().mostrarPanelTransporte(false);
		this.vistaAdministrador.getPanelLocales().mostrarPanelLocales(true);
		this.llenarTablaLocales();
	}
	
	private void mostrarVentanaEditarCuenta(ActionEvent mve) {
		int filaSeleccionada = this.vistaAdministrador.getPanelEmpleados().getTablaEmpleados().getSelectedRow();
		if (filaSeleccionada != -1){
			ventanaEditarCuenta.setVisible(true);
			cargarcomboBoxRoles();
			mostrarCuenta(filaSeleccionada);
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void mostrarVentanaAgregarCiudad(ActionEvent p) {
		this.vistaAdministrador.getPanelTransporte().mostrarPanelTransporte(false);
		this.vistaAdministrador.getPanelFormaPago().mostrarPanelFormaPago(false);
		this.vistaAdministrador.getPanelEmpleados().mostrarPanelTransporte(false);
		this.controladorCiudad.llenarTablaVistaCiudades();
		this.controladorCiudad.mostrarVistaCiudad();
	}

	private void mostrarVentanaAgregarProvincia(ActionEvent p) {
		System.out.println("se muestran las ventanas");
		this.vistaAdministrador.getPanelTransporte().mostrarPanelTransporte(false);
		this.vistaAdministrador.getPanelFormaPago().mostrarPanelFormaPago(false);
		this.vistaAdministrador.getPanelEmpleados().mostrarPanelTransporte(false);
		this.controladorProvincia.llenarTablaVistaProvincias();
		this.controladorProvincia.mostrarVistaProvincia();
	}

	private void mostrarVentanaAgregarPais(ActionEvent p) {
		this.vistaAdministrador.getPanelTransporte().mostrarPanelTransporte(false);
		this.vistaAdministrador.getPanelFormaPago().mostrarPanelFormaPago(false);
		this.vistaAdministrador.getPanelEmpleados().mostrarPanelTransporte(false);
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
		this.llenarTablaTransportes();
		this.llenarTablaFormaPago();
		this.llenarTablaEmpleados();
	}
	
	/*Mostrar la ventana para agregar un empleado y carga el comboBox de roles*/
	private void mostrarVentanaAgregarEmpleado(ActionEvent ac) {
		this.vistaAdministrador.getPanelEmpleados().setVisible(true);
		cargarcomboBoxRoles();
		cargarComboBoxLocales();
		this.ventanaAgregarEmpleado.limpiarCampos();
		this.ventanaAgregarEmpleado.mostrarVentana(true);
		this.vistaAdministrador.getPanelTransporte().mostrarPanelTransporte(false);
		this.vistaAdministrador.getPanelFormaPago().mostrarPanelFormaPago(false);

	}
	
//	------------------------------------------- Empleados Cuenta -----------------------------------------
	//TERMINA METODO AGREGADO
		/*Método para agregar a un empleado según el item que selecciona en el comboBox*/
	private void agregarCuentaEmpleado(ActionEvent ac) {
		//TODO: VER
		if(ventanaAgregarEmpleado.getComboBoxRoles().getSelectedItem().equals("administrador")){
			LoginDTO nuevoLogin = new LoginDTO();
			nuevoLogin.setUsuario(ventanaAgregarEmpleado.getTxtUsuario().getText());
			nuevoLogin.setContrasena(new String(ventanaAgregarEmpleado.getTxtContrasena().getPassword()));
			nuevoLogin.setRol(new RolDTO(1,"administrador"));
			nuevoLogin.setEstado("activo");
			
			login.agregarLogin(nuevoLogin);
			
			AdministradorDTO nuevoAdministrador = new AdministradorDTO(0,
					ventanaAgregarEmpleado.getTxtNombre().getText(),
					ventanaAgregarEmpleado.getTxtApellido().getText(),
					ventanaAgregarEmpleado.getTxtDni().getText(),
					obtenerLoginDTO(),ventanaAgregarEmpleado.getTextMail().getText(),
					obtenerLocalDTO((String)ventanaAgregarEmpleado.getComboBoxLocales().getSelectedItem()));
			
			administrador.agregarAdministrador(nuevoAdministrador);
			llenarTablaEmpleados();
			this.ventanaAgregarEmpleado.mostrarVentana(false);
		}
		
		if(ventanaAgregarEmpleado.getComboBoxRoles().getSelectedItem().equals("administrativo")){
			LoginDTO nuevoLogin = new LoginDTO();
			nuevoLogin.setUsuario(ventanaAgregarEmpleado.getTxtUsuario().getText());
			nuevoLogin.setContrasena(new String(ventanaAgregarEmpleado.getTxtContrasena().getPassword()));
			nuevoLogin.setRol(new RolDTO(2,"administrativo"));
			nuevoLogin.setEstado("activo");

			login.agregarLogin(nuevoLogin);
			
			AdministrativoDTO nuevoAdministrativo = new AdministrativoDTO(0,
					ventanaAgregarEmpleado.getTxtNombre().getText(),
					ventanaAgregarEmpleado.getTxtApellido().getText(),
					ventanaAgregarEmpleado.getTxtDni().getText(),
					obtenerLoginDTO(),ventanaAgregarEmpleado.getTextMail().getText(),
					obtenerLocalDTO((String)ventanaAgregarEmpleado.getComboBoxLocales().getSelectedItem()));
			
			Administrativo administrativo = new Administrativo(new DAOSQLFactory());
			administrativo.agregarAdministrativo(nuevoAdministrativo);
			llenarTablaEmpleados();
			this.ventanaAgregarEmpleado.mostrarVentana(false);
		}
		
		if(ventanaAgregarEmpleado.getComboBoxRoles().getSelectedItem().equals("coordinador")){
			LoginDTO nuevoLogin = new LoginDTO();
			nuevoLogin.setUsuario(ventanaAgregarEmpleado.getTxtUsuario().getText());
			nuevoLogin.setContrasena(new String(ventanaAgregarEmpleado.getTxtContrasena().getPassword()));
			nuevoLogin.setRol(new RolDTO(3,"coordinador"));
			nuevoLogin.setEstado("activo");

			login.agregarLogin(nuevoLogin);
			
			CoordinadorDTO nuevoCoordinador = new CoordinadorDTO(0,
					ventanaAgregarEmpleado.getTxtNombre().getText(),
					ventanaAgregarEmpleado.getTxtApellido().getText(),
					ventanaAgregarEmpleado.getTxtDni().getText(),
					obtenerLoginDTO(),ventanaAgregarEmpleado.getTextMail().getText(),
					obtenerLocalDTO((String)ventanaAgregarEmpleado.getComboBoxLocales().getSelectedItem()));
			
			Coordinador coordinador = new Coordinador(new DAOSQLFactory());
			coordinador.agregarCoordinador(nuevoCoordinador);
			llenarTablaEmpleados();
			this.ventanaAgregarEmpleado.mostrarVentana(false);
		}

		if(ventanaAgregarEmpleado.getComboBoxRoles().getSelectedItem().equals("contador")){
			LoginDTO nuevoLogin = new LoginDTO();
			nuevoLogin.setUsuario(ventanaAgregarEmpleado.getTxtUsuario().getText());
			nuevoLogin.setContrasena(new String(ventanaAgregarEmpleado.getTxtContrasena().getPassword()));
			nuevoLogin.setRol(new RolDTO(4,"contador"));
			nuevoLogin.setEstado("activo");
			
			login.agregarLogin(nuevoLogin);
			
			ContadorDTO nuevoContador = new ContadorDTO(0,
					ventanaAgregarEmpleado.getTxtNombre().getText(),
					ventanaAgregarEmpleado.getTxtApellido().getText(),
					ventanaAgregarEmpleado.getTxtDni().getText(),
					obtenerLoginDTO(),ventanaAgregarEmpleado.getTextMail().getText(),
					obtenerLocalDTO((String)ventanaAgregarEmpleado.getComboBoxLocales().getSelectedItem()));
			
			Contador contador = new Contador(new DAOSQLFactory());
			contador.agregarContador(nuevoContador);
			llenarTablaEmpleados();
			this.ventanaAgregarEmpleado.mostrarVentana(false);
		}
	}
		
	public void editarCuenta(ActionEvent ec) {
			String estado = "activo";
			String rolNombre = this.ventanaEditarCuenta.getComboBoxRoles().getSelectedItem().toString();
			int idLogin = this.logins_en_tabla.get(this.filaSeleccionada).getIdDatosLogin();
			LocalDTO localRespaldo = null;

			if( administradorEdit != null ) {
				localRespaldo = administradorEdit.getLocal();
				administrador.eliminarAdministrador(administradorEdit.getIdAdministrador());
			} else if ( administrativoEdit != null ) {
				localRespaldo = administrativoEdit.getLocal();
				administrativo.delete(administrativoEdit.getIdAdministrativo());
			} else if ( coordinadorEdit != null ) {
				localRespaldo = coordinadorEdit.getLocal();
				coordinador.eliminarCoordinador(coordinadorEdit.getIdCoordinador());
			} else if ( contadorEdit != null) {
				localRespaldo = contadorEdit.getLocal();
				contador.eliminarContador(contadorEdit.getIdContador());
			}
			
			int idRol = 0;
			switch(rolNombre) {
				case "administrador":
					idRol = 1;
					
					String usuarioLogin = this.ventanaEditarCuenta.getTxtUsuario().getText();
					String contrasenaLogin = new String(this.ventanaEditarCuenta.getTxtContrasena().getPassword());
					RolDTO rol = new RolDTO(idRol, rolNombre);
					LoginDTO login = new LoginDTO(idLogin, usuarioLogin, contrasenaLogin, rol, estado);
					this.login.editarLogin(login);

					administradorEdit = new AdministradorDTO();
					administradorEdit.setNombre(this.ventanaEditarCuenta.getTxtNombre().getText());
					administradorEdit.setApellido(this.ventanaEditarCuenta.getTxtApellido().getText());
					administradorEdit.setDni(this.ventanaEditarCuenta.getTxtDni().getText());
					administradorEdit.setDatosLogin(login);
					administradorEdit.setMail(this.ventanaEditarCuenta.getTxtMail().getText());
					administradorEdit.setLocal(localRespaldo);
					this.administrador.agregarAdministrador(administradorEdit);
					
					break;
				case "administrativo":
					idRol = 2;
					
					String usuarioLogin2 = this.ventanaEditarCuenta.getTxtUsuario().getText();
					String contrasenaLogin2 = new String(this.ventanaEditarCuenta.getTxtContrasena().getPassword());
					RolDTO rol2 = new RolDTO(idRol, rolNombre);
					LoginDTO login2 = new LoginDTO(idLogin, usuarioLogin2, contrasenaLogin2, rol2, estado);
					this.login.editarLogin(login2);
					
					administrativoEdit = new AdministrativoDTO();
					administrativoEdit.setNombre(this.ventanaEditarCuenta.getTxtNombre().getText());
					administrativoEdit.setApellido(this.ventanaEditarCuenta.getTxtApellido().getText());
					administrativoEdit.setDni(this.ventanaEditarCuenta.getTxtDni().getText());
					administrativoEdit.setDatosLogin(login2);
					administrativoEdit.setMail(this.ventanaEditarCuenta.getTxtMail().getText());
					administrativoEdit.setLocal(localRespaldo);
					this.administrativo.agregarAdministrativo(administrativoEdit);
					
					break;
				case "coordinador":
					idRol = 3;
					
					String usuarioLogin3 = this.ventanaEditarCuenta.getTxtUsuario().getText();
					String contrasenaLogin3 = new String(this.ventanaEditarCuenta.getTxtContrasena().getPassword());
					RolDTO rol3 = new RolDTO(idRol, rolNombre);
					LoginDTO login3 = new LoginDTO(idLogin, usuarioLogin3, contrasenaLogin3, rol3, estado);
					this.login.editarLogin(login3);
					
					coordinadorEdit = new CoordinadorDTO();
					coordinadorEdit.setNombre(this.ventanaEditarCuenta.getTxtNombre().getText());
					coordinadorEdit.setApellido(this.ventanaEditarCuenta.getTxtApellido().getText());
					coordinadorEdit.setDni(this.ventanaEditarCuenta.getTxtDni().getText());
					coordinadorEdit.setDatosLogin(login3);
					coordinadorEdit.setMail(this.ventanaEditarCuenta.getTxtMail().getText());
					coordinadorEdit.setLocal(localRespaldo);
					this.coordinador.agregarCoordinador(coordinadorEdit);
					
					break;
				case "contador":
					idRol = 4;
					
					String usuarioLogin4 = this.ventanaEditarCuenta.getTxtUsuario().getText();
					String contrasenaLogin4 = new String(this.ventanaEditarCuenta.getTxtContrasena().getPassword());
					RolDTO rol4 = new RolDTO(idRol, rolNombre);
					LoginDTO login4 = new LoginDTO(idLogin, usuarioLogin4, contrasenaLogin4, rol4, estado);
					this.login.editarLogin(login4);
					
					contadorEdit = new ContadorDTO();
					contadorEdit.setNombre(this.ventanaEditarCuenta.getTxtNombre().getText());
					contadorEdit.setApellido(this.ventanaEditarCuenta.getTxtApellido().getText());
					contadorEdit.setDni(this.ventanaEditarCuenta.getTxtDni().getText());
					contadorEdit.setDatosLogin(login4);
					contadorEdit.setMail(this.ventanaEditarCuenta.getTxtMail().getText());
					contadorEdit.setLocal(localRespaldo);
					this.contador.agregarContador(contadorEdit);
					
					break;
			}
			llenarTablaEmpleados();
			this.ventanaEditarCuenta.setVisible(false);
	}
	
	private void mostrarCuenta(int filaSeleccionada){
		this.filaSeleccionada = filaSeleccionada;
		this.ventanaEditarCuenta.mostrarVentana(true);
		obtenerEmpleado(this.filaSeleccionada);
		
		if( administradorEdit != null ) {
			ventanaEditarCuenta.getTxtNombre().setText(administradorEdit.getNombre());
			ventanaEditarCuenta.getTxtApellido().setText(administradorEdit.getApellido());
			ventanaEditarCuenta.getTxtDni().setText(administradorEdit.getDni());
			ventanaEditarCuenta.getTxtMail().setText(administradorEdit.getMail());
		} else if ( administrativoEdit != null ) {
			ventanaEditarCuenta.getTxtNombre().setText(administrativoEdit.getNombre());
			ventanaEditarCuenta.getTxtApellido().setText(administradorEdit.getApellido());
			ventanaEditarCuenta.getTxtDni().setText(administradorEdit.getDni());
			ventanaEditarCuenta.getTxtMail().setText(administrativoEdit.getMail());
		} else if ( coordinadorEdit != null ) {
			ventanaEditarCuenta.getTxtNombre().setText(coordinadorEdit.getNombre());
			ventanaEditarCuenta.getTxtApellido().setText(administradorEdit.getApellido());
			ventanaEditarCuenta.getTxtDni().setText(administradorEdit.getDni());
			ventanaEditarCuenta.getTxtMail().setText(coordinadorEdit.getMail());
		} else if ( contadorEdit != null) {
			ventanaEditarCuenta.getTxtNombre().setText(contadorEdit.getNombre());
			ventanaEditarCuenta.getTxtApellido().setText(administradorEdit.getApellido());
			ventanaEditarCuenta.getTxtDni().setText(administradorEdit.getDni());
			ventanaEditarCuenta.getTxtMail().setText(contadorEdit.getMail());
		}
		ventanaEditarCuenta.getTxtUsuario().setText(this.logins_en_tabla.get(this.filaSeleccionada).getUsuario());
		ventanaEditarCuenta.getTxtContrasena().setText(this.logins_en_tabla.get(this.filaSeleccionada).getContrasena());
	}
	
	public void obtenerEmpleado(int seleccionado) {
		if(this.logins_en_tabla.get(seleccionado).getRol().getNombre().equals("administrador")) {
			this.administradorEdit = administrador.getByLoginId(this.logins_en_tabla.get(seleccionado).getIdDatosLogin());
		} else if(this.logins_en_tabla.get(seleccionado).getRol().getNombre().equals("administrativo")) {
			this.administrativoEdit = administrativo.getByLoginId(this.logins_en_tabla.get(seleccionado).getIdDatosLogin());
		} else if(this.logins_en_tabla.get(seleccionado).getRol().getNombre().equals("coordinador")) {
			this.coordinadorEdit = coordinador.getByLoginId(this.logins_en_tabla.get(seleccionado).getIdDatosLogin());
		} else if(this.logins_en_tabla.get(seleccionado).getRol().getNombre().equals("contador")) {
			this.contadorEdit = contador.getByLoginId(this.logins_en_tabla.get(seleccionado).getIdDatosLogin());
		}
	}
	
	private void cancelarEditarCuenta(ActionEvent can) {
		this.administradorEdit = null;
		this.administrativoEdit = null;
		this.coordinadorEdit = null;
		this.contadorEdit = null;
		
		this.ventanaEditarCuenta.limpiarCampos();
		this.ventanaEditarCuenta.mostrarVentana(false);
	}
	
	private void mostrarVentanaViaje(ActionEvent ac) { //METODO AGREGADO!!
//	TODO:	this.controlador.llenarCiudadesEnCargaViajes();
		this.controlador.mostrarVentanaCargarViaje();
	}
	
	private LoginDTO obtenerLoginDTO() {
		LoginDTO loginDTO = new LoginDTO();
		List<LoginDTO> logins = login.obtenerLogin();
		for(LoginDTO l: logins){
			if(l.getUsuario().equals(this.ventanaAgregarEmpleado.getTxtUsuario().getText()) &&
					l.getContrasena().equals(new String(this.ventanaAgregarEmpleado.getTxtContrasena().getPassword()))){
			loginDTO = l;
		}
	}
		return loginDTO;
	}
	
	private LocalDTO obtenerLocalDTO(String nombreLocal) {
		return this.local.obtenerLocal(nombreLocal);
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
		this.ventanaEditarCuenta.getComboBoxRoles().setModel(new DefaultComboBoxModel(roles));
	}
	
	private void cargarComboBoxLocales() {
		ventanaAgregarEmpleado.getComboBoxLocales().removeAllItems();
		Local local = new Local(new DAOSQLFactory());
		List<LocalDTO> localesDTO = local.readAll();
		String[] locales = new String[localesDTO.size()];
		for(int i = 0; i < localesDTO.size(); i++) {
			String rango = localesDTO.get(i).getNombreLocal();
			locales[i] = rango;
		}
		this.ventanaAgregarEmpleado.getComboBoxLocales().setModel(new DefaultComboBoxModel(locales));
	}
	
	private void cancelarAgregarCuentaEmpleado(ActionEvent c) {
		this.ventanaAgregarEmpleado.limpiarCampos();
		this.ventanaAgregarEmpleado.mostrarVentana(false);
	}

	//----------------------Transportes-----------------------------------
	private void visualizarTransportes(ActionEvent vt) {
		this.vistaAdministrador.getPanelTransporte().mostrarPanelTransporte(true);
		this.vistaAdministrador.getPanelFormaPago().mostrarPanelFormaPago(false);
		this.vistaAdministrador.getPanelEmpleados().mostrarPanelTransporte(false);
		this.vistaAdministrador.getPanelLocales().mostrarPanelLocales(false);
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
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
		llenarTablaTransportes();
	}
	
	public void recargarTabla(ActionEvent r){
		llenarTablaTransportes();
	}

	public void llenarTablaLocales() {
		this.vistaAdministrador.getPanelLocales().getModelLocales().setRowCount(0); //Para vaciar la tabla
		this.vistaAdministrador.getPanelLocales().getModelLocales().setColumnCount(0);
		this.vistaAdministrador.getPanelLocales().getModelLocales().setColumnIdentifiers(this.vistaAdministrador.getPanelLocales().getNombreColumnasLocales());
		
		this.locales_en_tabla = local.readAll();
		
		for (int i = 0; i < this.locales_en_tabla.size(); i++){
			Object[] fila = {this.locales_en_tabla.get(i).getNombreLocal(),
							 this.locales_en_tabla.get(i).getDireccionLocal()
							};
			this.vistaAdministrador.getPanelLocales().getModelLocales().addRow(fila);
		}	
		
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
	
	public void llenarTablaEmpleados(){
		boolean activos = this.vistaAdministrador.getPanelEmpleados().getActivos().isSelected();
		boolean inactivos = this.vistaAdministrador.getPanelEmpleados().getInactivos().isSelected();
		
		this.vistaAdministrador.getPanelEmpleados().getModelEmpleados().setRowCount(0); //Para vaciar la tabla
		this.vistaAdministrador.getPanelEmpleados().getModelEmpleados().setColumnCount(0);
		this.vistaAdministrador.getPanelEmpleados().getModelEmpleados().setColumnIdentifiers(this.vistaAdministrador.getPanelEmpleados().getNombreColumnasEmpleados());
		
		this.logins_en_tabla = new ArrayList<LoginDTO>();
		this.logins_aux = login.obtenerLogin();
		
		if(activos == true && inactivos == false) {
			for (LoginDTO login : this.logins_aux) {
				if(login.getRol().getIdRol() != 5) {
					if(login.getEstado().equals("activo") || login.getEstado().equals("Activo"))
					this.logins_en_tabla.add(login);
				}
			}
		} else if (inactivos == true && activos == false) {
			for (LoginDTO login : this.logins_aux) {
				if(login.getRol().getIdRol() != 5) {
					if(login.getEstado().equals("inactivo") || login.getEstado().equals("Inactivo"))
						this.logins_en_tabla.add(login);
				}
			}
		} else if (activos && inactivos) {
			for (LoginDTO login : this.logins_aux) {
				if(login.getRol().getIdRol() != 5) {
						this.logins_en_tabla.add(login);
				}
			}
		} 
		
		for (int i = 0; i < this.logins_en_tabla.size(); i++) {
				Object[] fila = {
						this.logins_en_tabla.get(i).getUsuario(),
						this.logins_en_tabla.get(i).getContrasena(),
						this.logins_en_tabla.get(i).getRol().getNombre(),
						this.logins_en_tabla.get(i).getEstado()
				};
				this.vistaAdministrador.getPanelEmpleados().getModelEmpleados().addRow(fila);
		}
	}
	
	//------------------------------FormaPago-------------------------------------------------
	
	private void visualizarFormaPago(ActionEvent vfp) {
		this.vistaAdministrador.getPanelFormaPago().mostrarPanelFormaPago(true);
		this.vistaAdministrador.getPanelTransporte().mostrarPanelTransporte(false);
		this.vistaAdministrador.getPanelEmpleados().mostrarPanelTransporte(false);
		this.vistaAdministrador.getPanelLocales().mostrarPanelLocales(false);
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
	
	public void recargarTablaFormaPago(ActionEvent r){
		llenarTablaFormaPago();
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
	private void agregarPanelLocales(ActionEvent al) {
		this.vistaAdministrador.getPanelLocales().mostrarPanelLocales(true);
		this.ventanaAgregarLocal.mostrarVentana();
	}
	
	public void agregarLocal(ActionEvent agl) {
		LocalDTO nuevoLocal = new LocalDTO();
		nuevoLocal.setNombreLocal(this.ventanaAgregarLocal.getTxtNombreLocal().getText());
		nuevoLocal.setDireccionLocal(this.ventanaAgregarLocal.getTxtDireccionLocal().getText());
		local.insert(nuevoLocal);
		this.llenarTablaLocales();
		this.ventanaAgregarLocal.limpiarCampos();
		this.ventanaAgregarLocal.cerrarVentana();
	}
}