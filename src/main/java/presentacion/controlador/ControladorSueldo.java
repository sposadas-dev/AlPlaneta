package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import dto.AdministradorDTO;
import dto.AdministrativoDTO;
import dto.CoordinadorDTO;
import dto.RolDTO;
import dto.SueldoDTO;
import dto.Sueldos_EmpleadosDTO;
import modelo.Administrador;
import modelo.Administrativo;
import modelo.Coordinador;
import modelo.Rol;
import modelo.Sueldo;
import modelo.Sueldos_Empleados;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.contador.VentanaAgregarSueldo;
import presentacion.vista.contador.VentanaAgregarSueldo;
import presentacion.vista.contador.VistaContador;

public class ControladorSueldo {

	private VistaContador vistaContador;
	private VentanaAgregarSueldo ventanaAgregarSueldo;
	private Sueldo sueldo;
	private Sueldos_Empleados sueldos_empleados;

	private Administrador administrador;
	private Administrativo administrativo;
	private Coordinador coordinador;
	
	private List<AdministradorDTO> administradores_en_tabla;
	private List<AdministrativoDTO> administrativos_en_tabla;
	private List<CoordinadorDTO> coordinadores_en_tabla;
	
	public ControladorSueldo(VentanaAgregarSueldo ventanaVisualizarEmpleados){
		this.vistaContador = VistaContador.getInstance();
		this.ventanaAgregarSueldo = ventanaVisualizarEmpleados;
		this.ventanaAgregarSueldo = VentanaAgregarSueldo.getInstance();
		
		this.sueldo = new Sueldo(new DAOSQLFactory());
		this.sueldos_empleados = new Sueldos_Empleados(new DAOSQLFactory());
		
		this.ventanaAgregarSueldo.getBtnConfirmar().addActionListener(gs->agregarSueldo(gs));
		this.administrador = new Administrador(new DAOSQLFactory());
		this.administrativo = new Administrativo(new DAOSQLFactory());
		this.coordinador = new Coordinador(new DAOSQLFactory());
//
//		administradores_en_tabla = administrador.obtenerAdministradores();
//		administrativos_en_tabla = administrativo.obtenerAdministrativos();
//		coordinadores_en_tabla = coordinador.obtenerCoordinadores();
	}
	

	public void agregarSueldo(ActionEvent gs){
		SueldoDTO sueldoDTO = new SueldoDTO();
//		int mes = ventanaVisualizarEmpleados.getMesChooser().getMonth();
		Rol rol = new Rol(new DAOSQLFactory());
		
		if(!ventanaAgregarSueldo.getComboBoxRoles().getSelectedItem().equals("Seleccione un rol")){
			RolDTO rolDTO = rol.getRolByName(ventanaAgregarSueldo.getComboBoxRoles().getSelectedItem().toString());
			Calendar c = Calendar.getInstance();
			sueldoDTO.setMes(new Date(c.getTime().getTime()));
			sueldoDTO.setMontoSueldo(new BigDecimal(ventanaAgregarSueldo.getTxtSueldo().getText()));
			sueldoDTO.setRol(rolDTO);
			
			sueldo.agregarSueldo(sueldoDTO);
			if(rolDTO.getNombre().equals("administrador")){
				for(AdministradorDTO adm: administrador.obtenerAdministradores()){
					Sueldos_EmpleadosDTO sueldo_empleadoDTO = new Sueldos_EmpleadosDTO();
					if(sueldoDTO.getRol().getIdRol()==1){
						System.out.println(sueldo.getUltimoRegistroSueldo());
						sueldo_empleadoDTO.setIdSueldo(sueldo.getUltimoRegistroSueldo().getIdSueldo());
						sueldo_empleadoDTO.setIdEmpleado(adm.getIdAdministrador());
					sueldos_empleados.agregarSueldoEmpleado(sueldo_empleadoDTO);
					}
				}
			}
			else if(rolDTO.getNombre().equals("administrativo")){
				for(AdministrativoDTO a: administrativo.obtenerAdministrativos()){
					Sueldos_EmpleadosDTO sueldo_empleadoDTO = new Sueldos_EmpleadosDTO();
					if(sueldoDTO.getRol().getIdRol()==2){
						System.out.println(sueldo.getUltimoRegistroSueldo());
						sueldo_empleadoDTO.setIdSueldo(sueldo.getUltimoRegistroSueldo().getIdSueldo());
						sueldo_empleadoDTO.setIdEmpleado(a.getIdAdministrativo());
					sueldos_empleados.agregarSueldoEmpleado(sueldo_empleadoDTO);
					}
				}
			}
			else if(rolDTO.getNombre().equals("coordinador")){
				for(CoordinadorDTO coord: coordinador.obtenerCoordinadores()){
					Sueldos_EmpleadosDTO sueldo_empleadoDTO = new Sueldos_EmpleadosDTO();
					if(sueldoDTO.getRol().getIdRol()==3){
						System.out.println(sueldo.getUltimoRegistroSueldo());
						sueldo_empleadoDTO.setIdSueldo(sueldo.getUltimoRegistroSueldo().getIdSueldo());
						sueldo_empleadoDTO.setIdEmpleado(coord.getIdCoordinador());
						sueldos_empleados.agregarSueldoEmpleado(sueldo_empleadoDTO);
					}
				}
			}
			ventanaAgregarSueldo.mostrarVentana(false);
		}else{
			JOptionPane.showMessageDialog(null,"Debe seleccionar un rol","Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void llenarTablaEmpleados(){
		this.vistaContador.getPanelSueldos().getModelSueldos().setRowCount(0); //Para vaciar la tabla
		this.vistaContador.getPanelSueldos().getModelSueldos().setColumnCount(0);
		this.vistaContador.getPanelSueldos().getModelSueldos().setColumnIdentifiers(this.vistaContador.getPanelSueldos().getNombreColumnasReservas());
		Rol rol = new Rol(new DAOSQLFactory());
		administradores_en_tabla = administrador.obtenerAdministradores();

		for (int i = 0; i < this.administradores_en_tabla.size(); i++) {
			Object[] fila = {
					this.administradores_en_tabla.get(i).getNombre(),
					this.administradores_en_tabla.get(i).getApellido(),
					this.administradores_en_tabla.get(i).getDni(),
					this.administradores_en_tabla.get(i).getDatosLogin().getRol().getNombre(),
					this.administradores_en_tabla.get(i).getLocal().getNombreLocal(),
					sueldos_empleados.obtenerSueldoEmpleado(administradores_en_tabla.get(i).getIdAdministrador(),rol.getRolById(1).getIdRol()).getMes(),
					"$ "+sueldos_empleados.obtenerSueldoEmpleado(administradores_en_tabla.get(i).getIdAdministrador(),rol.getRolById(1).getIdRol()).getMontoSueldo()

			};	 
			this.vistaContador.getPanelSueldos().getModelSueldos().addRow(fila);
		}
		
		this.administrativos_en_tabla = administrativo.obtenerAdministrativos();

		for (int i = 0; i < this.administrativos_en_tabla.size(); i++) {
			Object[] fila = {
					this.administrativos_en_tabla.get(i).getNombre(),
					this.administrativos_en_tabla.get(i).getApellido(),
					this.administrativos_en_tabla.get(i).getDni(),
					this.administrativos_en_tabla.get(i).getDatosLogin().getRol().getNombre(),
					this.administrativos_en_tabla.get(i).getLocal().getNombreLocal(),
					sueldos_empleados.obtenerSueldoEmpleado(administrativos_en_tabla.get(i).getIdAdministrativo(),rol.getRolById(2).getIdRol()).getMes(),
					"$ "+sueldos_empleados.obtenerSueldoEmpleado(administrativos_en_tabla.get(i).getIdAdministrativo(),rol.getRolById(2).getIdRol()).getMontoSueldo()

			};	 
			this.vistaContador.getPanelSueldos().getModelSueldos().addRow(fila);
		}
		
		this.coordinadores_en_tabla = coordinador.obtenerCoordinadores();
//		
		for (int i = 0; i < this.coordinadores_en_tabla.size(); i++) {
			Object[] fila = {
					this.coordinadores_en_tabla.get(i).getNombre(),
					this.coordinadores_en_tabla.get(i).getApellido(),
					this.coordinadores_en_tabla.get(i).getDni(),
					this.coordinadores_en_tabla.get(i).getDatosLogin().getRol().getNombre(),
					this.coordinadores_en_tabla.get(i).getLocal().getNombreLocal(),
					sueldos_empleados.obtenerSueldoEmpleado(coordinadores_en_tabla.get(i).getIdCoordinador(),rol.getRolById(3).getIdRol()).getMes(),
					"$ "+sueldos_empleados.obtenerSueldoEmpleado(coordinadores_en_tabla.get(i).getIdCoordinador(),rol.getRolById(3).getIdRol()).getMontoSueldo()
			};
			this.vistaContador.getPanelSueldos().getModelSueldos().addRow(fila);
		}
	}
}