package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import dto.EgresosDTO;
import dto.LocalDTO;
import dto.ServicioDTO;
import dto.TransporteDTO;
import modelo.Egreso;
import modelo.Local;
import modelo.Rol;
import modelo.Servicio;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.contador.VentanaAgregarServicio;
import presentacion.vista.contador.VentanaEditarServicio;
import presentacion.vista.contador.VistaContador;
import recursos.Mapper;

public class ControladorServicio {

	private VistaContador vistaContador;
	private VentanaAgregarServicio ventanaAgregarServicio;
	private VentanaEditarServicio ventanaEditarServicio;
	private List<ServicioDTO> servicios_en_tabla;
	private int filaSeleccionada;
	private Egreso egreso;
	
	private Servicio servicio;
	private Local local;
	
	public ControladorServicio(VentanaAgregarServicio ventanaAgregarServicio, VentanaEditarServicio ventanaEditarServicio){
	
		this.vistaContador = VistaContador.getInstance();
		this.ventanaAgregarServicio = ventanaAgregarServicio;
		this.ventanaEditarServicio = ventanaEditarServicio;
		this.ventanaAgregarServicio.getBtnConfirmar().addActionListener(as->agregarServicio(as));
		this.ventanaEditarServicio.getBtnConfirmar().addActionListener(es->editarServicio(es));
		this.servicio = new Servicio(new DAOSQLFactory());
		this.egreso = new Egreso(new DAOSQLFactory());
		this.local = new Local(new DAOSQLFactory());
	}

	private void agregarServicio(ActionEvent as) {
		ServicioDTO servicioDTO = new ServicioDTO();
		int mes = ventanaAgregarServicio.getMesChooser().getMonth();
		int anio = ventanaAgregarServicio.getAnioChooser().getYear();

		Calendar fecha = Calendar.getInstance();
		fecha.set(anio, mes, 1); // Specify day of month
		java.sql.Date fechaServicio = convertUtilToSql(fecha.getTime()); 
		servicioDTO.setNombreServicio(this.ventanaAgregarServicio.getTxtServicio().getText());
		servicioDTO.setMonto(new BigDecimal(this.ventanaAgregarServicio.getTxtMonto().getText()));
		servicioDTO.setMes(fechaServicio);
		servicioDTO.setLocal(obtenerLocalDTO((String)ventanaAgregarServicio.getComboBoxLocales().getSelectedItem()));
		servicio.agregarServicio(servicioDTO);
		
//		EgresosDTO egresoDTO = new EgresosDTO();
//		egresoDTO.setServicio(servicioDTO);
//		egreso.agregarEgreso(egresoDTO);
		
		this.llenarTablaServicios();
		this.ventanaAgregarServicio.limpiarCampos();
		ventanaAgregarServicio.mostrarVentana(false);
	}
	
	private java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
	
	private LocalDTO obtenerLocalDTO(String nombreLocal) {
		return this.local.obtenerLocal(nombreLocal);
	}
	
	public void editarServicio(int filaSeleccionada){
		llenarTablaServicios();
		this.filaSeleccionada = filaSeleccionada;
		this.ventanaEditarServicio.mostrarVentana(true);
//		int mes = ventanaEditarServicio.getMesChooser().getMonth()+1;
		ventanaEditarServicio.getMesChooser().setMonth(this.servicios_en_tabla.get(this.filaSeleccionada).getMes().getMonth());
//		ventanaEditarServicio.getAnioChooser().setYear(anio);
		ventanaEditarServicio.getTxtServicio().setText(this.servicios_en_tabla.get(this.filaSeleccionada).getNombreServicio());
		ventanaEditarServicio.getTxtMonto().setText(this.servicios_en_tabla.get(this.filaSeleccionada).getMonto().toString());
		ventanaEditarServicio.getComboBoxLocales().setSelectedItem(this.servicios_en_tabla.get(this.filaSeleccionada).getLocal().getNombreLocal());
	}
	
	
	public void editarServicio(ActionEvent es){
		if(!ventanaEditarServicio.getTxtServicio().getText().isEmpty() || !ventanaEditarServicio.getTxtMonto().getText().isEmpty()){
//			llenarTablaServicios();
			int mes = ventanaEditarServicio.getMesChooser().getMonth();
			int anio = ventanaEditarServicio.getAnioChooser().getYear();
			Calendar fecha = Calendar.getInstance();
			fecha.set(anio, mes, 1); // Specify day of month
			java.sql.Date fechaServicio = convertUtilToSql(fecha.getTime()); 
			
			
			ServicioDTO servicioDTO = new ServicioDTO();
			servicioDTO.setIdServicio(this.servicios_en_tabla.get(filaSeleccionada).getIdServicio());
			servicioDTO.setNombreServicio(this.ventanaEditarServicio.getTxtServicio().getText());
			servicioDTO.setMonto(new BigDecimal(this.ventanaEditarServicio.getTxtMonto().getText()));
			servicioDTO.setMes(fechaServicio);
			servicioDTO.setLocal(obtenerLocalDTO((String)ventanaEditarServicio.getComboBoxLocales().getSelectedItem()));
			this.servicio.editarServicio(servicioDTO);
			llenarTablaServicios();
			ventanaEditarServicio.limpiarCampos();
			ventanaEditarServicio.mostrarVentana(false);
		}else{
			JOptionPane.showMessageDialog(null, "Verifique los campos vacíos", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void llenarTablaServicios(){
		this.vistaContador.getPanelServicios().getModelServicios().setRowCount(0); //Para vaciar la tabla
		this.vistaContador.getPanelServicios().getModelServicios().setColumnCount(0);
		this.vistaContador.getPanelServicios().getModelServicios().setColumnIdentifiers(this.vistaContador.getPanelServicios().getNombreColumnasServicios());
		servicios_en_tabla = servicio.obtenerServicios();
		Mapper mapper = new Mapper();
		for (int i = 0; i < this.servicios_en_tabla.size(); i++) {
			Object[] fila = {
					this.servicios_en_tabla.get(i).getNombreServicio(),
					"$ "+this.servicios_en_tabla.get(i).getMonto(),
					mapper.parseToStringMes(this.servicios_en_tabla.get(i).getMes()),
					this.servicios_en_tabla.get(i).getLocal().getNombreLocal(),
			};	 
			this.vistaContador.getPanelServicios().getModelServicios().addRow(fila);
		}
	}

	public void eliminarServicio(int filaSeleccionada) {
		int confirm = JOptionPane.showOptionDialog(
	            null,"¿Estás seguro que quieres eliminar el servicio?", 
			             "Cancelar pasaje", JOptionPane.YES_NO_OPTION,
			             JOptionPane.ERROR_MESSAGE, null, null, null);
		if (confirm == 0){
			this.servicio.eliminarServicio(servicios_en_tabla.get(filaSeleccionada));
			this.llenarTablaServicios();
		}
	}
}