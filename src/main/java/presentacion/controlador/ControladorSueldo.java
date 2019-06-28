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
import dto.EgresosDTO;
import dto.RolDTO;
import dto.SueldoDTO;
import dto.Sueldos_EmpleadosDTO;
import modelo.Administrador;
import modelo.Administrativo;
import modelo.Coordinador;
import modelo.Egreso;
import modelo.Rol;
import modelo.Sueldo;
import modelo.Sueldos_Empleados;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.contador.VentanaAgregarSueldo;
import presentacion.vista.contador.VentanaAgregarSueldo;
import presentacion.vista.contador.VistaContador;
import recursos.Mapper;

public class ControladorSueldo {

	private VistaContador vistaContador;
	private VentanaAgregarSueldo ventanaAgregarSueldo;
	private Sueldo sueldo;
	private Sueldos_Empleados sueldos_empleados;

	private Egreso egreso;
	private Administrador administrador;
	private Administrativo administrativo;
	private Coordinador coordinador;
	
	private List<AdministradorDTO> administradores_en_tabla;
	private List<AdministrativoDTO> administrativos_en_tabla;
	private List<CoordinadorDTO> coordinadores_en_tabla;
	
	public ControladorSueldo(VentanaAgregarSueldo ventanaAgregarSueldo){
		this.vistaContador = VistaContador.getInstance();
		this.ventanaAgregarSueldo = ventanaAgregarSueldo;
		
		this.sueldo = new Sueldo(new DAOSQLFactory());
		this.sueldos_empleados = new Sueldos_Empleados(new DAOSQLFactory());
		
		this.ventanaAgregarSueldo.getBtnConfirmar().addActionListener(gs->agregarSueldo(gs));
		this.administrador = new Administrador(new DAOSQLFactory());
		this.administrativo = new Administrativo(new DAOSQLFactory());
		this.coordinador = new Coordinador(new DAOSQLFactory());
		this.egreso = new Egreso(new DAOSQLFactory());
	}
	

	public void agregarSueldo(ActionEvent gs){
		SueldoDTO sueldoDTO = new SueldoDTO();
		Rol rol = new Rol(new DAOSQLFactory());
	
		if(!ventanaAgregarSueldo.getComboBoxRoles().getSelectedItem().equals("Seleccione un rol")){
			int mes = ventanaAgregarSueldo.getMesChooser().getMonth();
			int anio = ventanaAgregarSueldo.getAnioChooser().getYear();
			Calendar fecha = Calendar.getInstance();
			fecha.set(anio, mes, 1); // Specify day of month
			java.sql.Date fechaSueldo = convertUtilToSql(fecha.getTime()); 
			RolDTO rolDTO = rol.getRolByName(ventanaAgregarSueldo.getComboBoxRoles().getSelectedItem().toString());
			sueldoDTO.setMes(fechaSueldo);
			sueldoDTO.setMontoSueldo(new BigDecimal(ventanaAgregarSueldo.getTxtSueldo().getText()));
			sueldoDTO.setRol(rolDTO);
			
			sueldo.agregarSueldo(sueldoDTO);
			if(rolDTO.getNombre().equals("administrador")){
				for(AdministradorDTO adm: administrador.obtenerAdministradores()){
					Sueldos_EmpleadosDTO sueldo_empleadoDTO = new Sueldos_EmpleadosDTO();
					if(sueldoDTO.getRol().getIdRol()==1){
						sueldo_empleadoDTO.setIdSueldo(sueldo.getUltimoRegistroSueldo().getIdSueldo());
						sueldo_empleadoDTO.setIdEmpleado(adm.getIdAdministrador());
					sueldos_empleados.agregarSueldoEmpleado(sueldo_empleadoDTO);
					this.ventanaAgregarSueldo.limpiarCampos();
					this.llenarTablaEmpleados();
//					egresoDTO.setSueldos_empleados(sueldo_empleadoDTO);
//					egreso.agregarEgreso(egresoDTO);
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
					this.ventanaAgregarSueldo.limpiarCampos();
					this.llenarTablaEmpleados();
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
						this.ventanaAgregarSueldo.limpiarCampos();
						this.llenarTablaEmpleados();
//						egresoDTO.setSueldos_empleados(sueldo_empleadoDTO);
//						egreso.agregarEgreso(egresoDTO);
						
					}
				}
			}
			ventanaAgregarSueldo.mostrarVentana(false);
		}else{
			JOptionPane.showMessageDialog(null,"Debe seleccionar un rol","Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}

	private java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
	
	
	public void llenarTablaEmpleados(){
		this.vistaContador.getPanelSueldos().getModelSueldos().setRowCount(0); //Para vaciar la tabla
		this.vistaContador.getPanelSueldos().getModelSueldos().setColumnCount(0);
		this.vistaContador.getPanelSueldos().getModelSueldos().setColumnIdentifiers(this.vistaContador.getPanelSueldos().getNombreColumnasReservas());
		Rol rol = new Rol(new DAOSQLFactory());
		Mapper mapper = new Mapper();
		administradores_en_tabla = administrador.obtenerAdministradores();

		for (int i = 0; i < this.administradores_en_tabla.size(); i++) {
			List<SueldoDTO> sueldos = sueldos_empleados.obtenerSueldoEmpleado(administradores_en_tabla.get(i).getIdAdministrador(),rol.getRolById(1).getIdRol());
			for(int j=0 ;j<sueldos.size();j++){
			Object[] fila = {
					this.administradores_en_tabla.get(i).getNombre(),
					this.administradores_en_tabla.get(i).getApellido(),
					this.administradores_en_tabla.get(i).getDni(),
					this.administradores_en_tabla.get(i).getDatosLogin().getRol().getNombre(),
					this.administradores_en_tabla.get(i).getLocal().getNombreLocal(),
					mapper.parseToStringMes(sueldos.get(j).getMes()),
					"$ "+ sueldos.get(j).getMontoSueldo()
			};	 
			this.vistaContador.getPanelSueldos().getModelSueldos().addRow(fila);
		 }
		}
		
		this.administrativos_en_tabla = administrativo.obtenerAdministrativos();

		for (int i = 0; i < this.administrativos_en_tabla.size(); i++) {
			List<SueldoDTO> sueldos = sueldos_empleados.obtenerSueldoEmpleado(administrativos_en_tabla.get(i).getIdAdministrativo(),rol.getRolById(2).getIdRol());
			for(int j=0 ;j<sueldos.size();j++){
			Object[] fila = {
					this.administrativos_en_tabla.get(i).getNombre(),
					this.administrativos_en_tabla.get(i).getApellido(),
					this.administrativos_en_tabla.get(i).getDni(),
					this.administrativos_en_tabla.get(i).getDatosLogin().getRol().getNombre(),
					this.administrativos_en_tabla.get(i).getLocal().getNombreLocal(),
					mapper.parseToStringMes(sueldos.get(j).getMes()),
					"$ "+ sueldos.get(j).getMontoSueldo()
			};	 
			this.vistaContador.getPanelSueldos().getModelSueldos().addRow(fila);
		 }
		}
		
		this.coordinadores_en_tabla = coordinador.obtenerCoordinadores();
//		
		for (int i = 0; i < this.coordinadores_en_tabla.size(); i++) {
			List<SueldoDTO> sueldos = sueldos_empleados.obtenerSueldoEmpleado(coordinadores_en_tabla.get(i).getIdCoordinador(),rol.getRolById(3).getIdRol());
			for(int j=0 ;j<sueldos.size();j++){
				System.out.println(sueldos.get(j).getMontoSueldo());
				Object[] fila = {
					this.coordinadores_en_tabla.get(i).getNombre(),
					this.coordinadores_en_tabla.get(i).getApellido(),
					this.coordinadores_en_tabla.get(i).getDni(),
					this.coordinadores_en_tabla.get(i).getDatosLogin().getRol().getNombre(),
					this.coordinadores_en_tabla.get(i).getLocal().getNombreLocal(),
					mapper.parseToStringMes(sueldos.get(j).getMes()),
					"$ "+ sueldos.get(j).getMontoSueldo()
				};
			this.vistaContador.getPanelSueldos().getModelSueldos().addRow(fila);
			}
		}
	}
}