package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import dto.AdministrativoDTO;
import dto.ClienteDTO;
import dto.EstadoEventoDTO;
import dto.EventoDTO;
import dto.TransporteDTO;
import modelo.Cliente;
import modelo.ModeloEstadoEvento;
import modelo.ModeloEvento;
import modelo.Pasaje;
import persistencia.dao.mysql.DAOSQLFactory;
import persistencia.dao.mysql.EstadoEventoDAOSQL;
import presentacion.vista.administrativo.PanelEvento;
import presentacion.vista.administrativo.VentanaEditarEvento;
import presentacion.vista.administrativo.VentanaRegistrarEvento;
import presentacion.vista.administrativo.VentanaTablaClientes;

public class ControladorEvento {

	private VentanaTablaClientes ventanaTablaClientes;
	private EventoDTO eventoSeleccionado;
	
	private List<ClienteDTO> clientes_en_tabla;
	private List<EventoDTO> eventos_en_tabla;
	
	private ModeloEvento evento;
	private ModeloEstadoEvento estadoEvento;
	private VentanaRegistrarEvento ventanaEvento;
	private VentanaEditarEvento ventanaEditarEvento;
	private Cliente cliente;
	private Pasaje pasaje;
	private PanelEvento panelEvento;
	
	//DATOS EVENTO:
	private java.sql.Date fechaIngreso; 
	private java.sql.Date fechaEvento; 
	private java.sql.Time horaEvento;
	private String descripcion;
	private ClienteDTO clienteSeleccionado; //cliente que selecciona en la tabla 		
	private AdministrativoDTO administrativoLogueado;
	private EstadoEventoDTO estado; 	
	private String motivoReprogramacion;
	private EventoDTO eventoRegistrado;

	public ControladorEvento(VentanaRegistrarEvento ventanaEvento, ModeloEvento evento, AdministrativoDTO administrativoLogueado, List<EventoDTO> eventos_en_tabla){
		//DATOS EVENTO:
		java.util.Date fecha = new java.util.Date(); 
		this.fechaIngreso = new java.sql.Date(fecha.getTime());	
		this.fechaEvento = null;
		this.horaEvento = null;
		this.descripcion = null;
		this.clienteSeleccionado = null; //cliente que selecciona en la tabla 		
		this.administrativoLogueado = administrativoLogueado;
		this.estado = null;
		this.motivoReprogramacion=" - ";
		this.eventoRegistrado = null;
		this.eventos_en_tabla = eventos_en_tabla;
		
		this.ventanaEvento = ventanaEvento;
		this.ventanaEditarEvento = VentanaEditarEvento.getInstance();
		this.ventanaTablaClientes = VentanaTablaClientes.getInstance();
		
		this.cliente = new Cliente(new DAOSQLFactory());
		this.setPasaje(new Pasaje(new DAOSQLFactory()));
		
		this.setPanelEvento(new PanelEvento());
		this.estadoEvento = new ModeloEstadoEvento(new DAOSQLFactory());
		this.evento = new ModeloEvento(new DAOSQLFactory());

		this.ventanaEvento.getBtnRegistrar().addActionListener(rc->registrarEvento(rc));
		this.ventanaEvento.getBtnAgregarCliente().addActionListener(r->mostrarClientes(r));
		this.ventanaEvento.getBtnCancelar().addActionListener(cv->cerrarVentanaEvento(cv));
		
		this.ventanaEditarEvento.getBtnEditar().addActionListener(rc->editarEvento(rc));
		this.ventanaEditarEvento.getBtnCancelar().addActionListener(rc->cancelarEditarEvento(rc));

		this.ventanaTablaClientes.getBtnAtras().addActionListener(vc->volverVentanaAgregarEvento(vc));
		this.ventanaTablaClientes.getBtnConfirmar().addActionListener(ce->agregarClienteToEvento(ce));
		
		this.administrativoLogueado= administrativoLogueado;
	}


	public void iniciar(){
		llenarComboEstados();
		llenarComboHoraEvento();
	}
	
	public void llenarComboEstados() {
		List<EstadoEventoDTO> estados = new EstadoEventoDAOSQL().readAll();
		String[] nombresEstados = new String[estados.size()];
		for(int i=0; i<estados.size();i++){
			String e = estados.get(i).getNombre();
			nombresEstados [i] = e;
		}	
		this.ventanaEvento.getComboEstadoEvento().setModel(new DefaultComboBoxModel(nombresEstados));
		this.ventanaEditarEvento.getComboEstadoEvento().setModel(new DefaultComboBoxModel(nombresEstados));
	}
	
	public void llenarComboHoraEvento() {
		String [] horarios = {"08:00:00", "08:30:00", "09:00:00", "09:30:00", "10:00:00","10:30:00","11:00:00","11:30:00","12:30:00",
				"13:00:00","13:30:00","14:00:00", "14:30:00", "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00","18:00:00"};
		this.ventanaEvento.getComboHoraEvento().setModel(new DefaultComboBoxModel(horarios));
		this.ventanaEditarEvento.getComboHoraEvento().setModel(new DefaultComboBoxModel(horarios));
	}

	
	
	public void registrarEvento(ActionEvent rc){
		if(camposLlenosRegistrarEvento()){
			this.fechaEvento = convertUtilToSql(this.ventanaEvento.getDateFechaEvento().getDate());
			this.horaEvento = obtenerHora(this.ventanaEvento.getComboHoraEvento().getSelectedItem().toString());
			this.descripcion = this.ventanaEvento.getTxtDescripcion().getText();
			this.estado = obtenerEstadoEventoPorNombre(this.ventanaEvento.getComboEstadoEvento().getSelectedItem().toString());
			
			this.eventoRegistrado = new EventoDTO(0,fechaIngreso,fechaEvento,horaEvento,descripcion,clienteSeleccionado,administrativoLogueado,estado,motivoReprogramacion,0);
		
			evento.agregarEvento(eventoRegistrado);
			this.ventanaEvento.limpiarCampos();
			this.ventanaEvento.cerrarVentana();
		}
	}
	
	public void editarEvento(ActionEvent rc){
		if(camposLlenosEditarEvento()){		
			Date fechaEvento = convertUtilToSql(this.ventanaEditarEvento.getDateFechaEvento().getDate());
			Time horaEvento = obtenerHora(this.ventanaEditarEvento.getComboHoraEvento().getSelectedItem().toString());
			String motivoReprogramacion = this.ventanaEditarEvento.getTxtReprogramacion().getText();	
			EstadoEventoDTO estado = obtenerEstadoEventoPorNombre(this.ventanaEditarEvento.getComboEstadoEvento().getSelectedItem().toString());
			
			EventoDTO nuevoEvento = new EventoDTO(eventoSeleccionado.getIdEvento(),fechaIngreso,fechaEvento,horaEvento,descripcion,eventoSeleccionado.getCliente(),administrativoLogueado,estado,motivoReprogramacion,0);
			evento.editarEvento(nuevoEvento);
			
			this.ventanaEditarEvento.limpiarCampos();
			this.ventanaEditarEvento.cerrarVentana();
		}
	}
	
	private void mostrarClientes(ActionEvent r) {
		ventanaEvento.setVisible(false);
		ventanaTablaClientes.setVisible(true);
		llenarTablaClientes();
	}
	
	private void llenarTablaClientes(){
		this.ventanaTablaClientes.getModelClientes().setRowCount(0); //Para vaciar la tabla
		this.ventanaTablaClientes.getModelClientes().setColumnCount(0);
		this.ventanaTablaClientes.getModelClientes().setColumnIdentifiers(this.ventanaTablaClientes.getNombreColumnas());
			
		this.clientes_en_tabla = cliente.obtenerClientes();
		
		for (int i = 0; i < this.clientes_en_tabla.size(); i++){

			Object[] fila = {
							this.clientes_en_tabla.get(i).getNombre(),
							this.clientes_en_tabla.get(i).getApellido(),
							this.clientes_en_tabla.get(i).getDni(),
							this.clientes_en_tabla.get(i).getFechaNacimiento(),
							this.clientes_en_tabla.get(i).getMedioContacto().getTelefonoFijo(),
							this.clientes_en_tabla.get(i).getMedioContacto().getTelefonoCelular(),
							this.clientes_en_tabla.get(i).getMedioContacto().getEmail()
			};
			this.ventanaTablaClientes.getModelClientes().addRow(fila);
		}		
	}
	
	private Time obtenerHora(String miHora) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		long ms = 0;
		try { ms = sdf.parse(miHora).getTime();
		} catch (ParseException e) { e.printStackTrace();}
		
		Time t = new Time(ms);
		return t;
	}
	
	private boolean camposLlenosRegistrarEvento(){
		if (ventanaEvento.getDateFechaEvento().getDate() == null || ventanaEvento.getTxtDescripcion().getText().isEmpty() || ventanaEvento.getTxtDni().getText().isEmpty()){
				JOptionPane.showMessageDialog(null, "Debe cargar todos los campos", "Mensaje", JOptionPane.ERROR_MESSAGE);
				return false;
		}
			return true;
	}

	private boolean camposLlenosEditarEvento(){
		if (ventanaEditarEvento.getDateFechaEvento().getDate() == null || ventanaEditarEvento.getTxtReprogramacion().getText().isEmpty()){
				JOptionPane.showMessageDialog(null, "Debe cargar todos los campos", "Mensaje", JOptionPane.ERROR_MESSAGE);
				return false;
		}
			return true;
	}
	
	private EstadoEventoDTO obtenerEstadoEventoPorNombre(String nombre){
		for(EstadoEventoDTO e : estadoEvento.obtenerEstadosEvento())
			if(e.getNombre().equals(nombre))
				return e;
		return null;
	}
	
	private void cerrarVentanaEvento(ActionEvent cv) {
		this.ventanaEvento.limpiarCampos();
		this.ventanaEvento.cerrarVentana();
	}
	
	public void controlarNotificacionesContinuo(){
		TimerTask timerTask = new TimerTask() {
		    public void run() {
		    	java.util.Date fecha = new java.util.Date(); 
				Date fechaActual = new java.sql.Date(fecha.getTime());
				java.sql.Time horarioActual = new java.sql.Time(System.currentTimeMillis());
				
				String[] horarioStringActual = horarioActual.toString().split(":");
				String horaActual = horarioStringActual[0];
				String minutosActual = horarioStringActual[1];
				String segundosActual = horarioStringActual[2];
				System.out.println("HORA ACTUAL: "+horarioActual.toString());
				if(minutosActual.equals("54") || minutosActual.equals("00")) {
					for(EventoDTO e : evento.obtenerEvento()) {
						String[] horarioParticular = e.getHoraEvento().toString().split(":");
						String horaParticular = horarioParticular[0];
						String minutosParticular = horarioParticular[1];
						String segundosParticular = horarioParticular[2];
						
						if(horaParticular.equals(horaActual) && minutosParticular.equals(minutosActual) && segundosActual.equals(segundosParticular)) {	
							if(fechaActual.equals(e.getFechaEvento())) {
								//SIGNIFICA QUE HAY UN EVENTO EN ESTE DIA Y HORARIO
								e.setVisto(1);
								evento.editarVistoEvento(e);
								JOptionPane.showMessageDialog(null, "Tienes un nuevo evento", "Evento!", JOptionPane.INFORMATION_MESSAGE);							
							}
							
						}
					}
				}
		    }
		};

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, 0, 1000);//1000=3segundos
	}
	
	public void controlarNotificacionesInicioSesion() {
		boolean vistos = false;
		for(EventoDTO e : evento.obtenerEvento()) { //hay eventos sin ver
			if(e.getVisto()==0) //hay un evento no visto
				vistos = vistos || true;
		}
		if(vistos)
			JOptionPane.showMessageDialog(null, "Hay evento(s) sin ver", "Evento(s)", JOptionPane.INFORMATION_MESSAGE);
	}
	/*private void llenarTablaEventos(){
		System.out.println("ENTRA A TABLA EVENTO EN CONTROLADOREVENTO");
		this.panelEvento.getModelEventos().setRowCount(0); //Para vaciar la tabla
		this.panelEvento.getModelEventos().setColumnCount(0);
		this.panelEvento.getModelEventos().setColumnIdentifiers(this.panelEvento.getNombreColumnasEventos());
			
		this.eventos_en_tabla = evento.obtenerEvento();
		
		for (int i = 0; i < this.eventos_en_tabla.size(); i++){

			Object[] fila = {
							this.eventos_en_tabla.get(i).getIdEvento(),
							this.eventos_en_tabla.get(i).getFechaIngreso(),
							this.eventos_en_tabla.get(i).getFechaEvento(),
							this.eventos_en_tabla.get(i).getHoraEvento(),
							this.eventos_en_tabla.get(i).getDescripcion(),
							this.eventos_en_tabla.get(i).getCliente().getApellido(),
							this.eventos_en_tabla.get(i).getCliente().getNombre(),
							this.eventos_en_tabla.get(i).getAdministrativo().getNombre(),
							this.eventos_en_tabla.get(i).getEstadoEvento().getNombre()
			};
							this.panelEvento.getModelEventos().addRow(fila);
		}		
	}*/

	private void volverVentanaAgregarEvento(ActionEvent vc) {
		this.ventanaTablaClientes.mostrarVentana(false);
		this.ventanaEvento.mostrarVentana();
	}
	
	private void cancelarEditarEvento(ActionEvent vc) {
		this.ventanaEditarEvento.mostrarVentana(false);
	}
	
	private void agregarClienteToEvento(ActionEvent ce) {
		int filaSeleccionada = this.ventanaTablaClientes.getTablaClientes().getSelectedRow();
		if (filaSeleccionada != -1){
			this.ventanaTablaClientes.mostrarVentana(false);
			clienteSeleccionado = clientes_en_tabla.get(filaSeleccionada);
			this.ventanaEvento.mostrarVentana();
			this.ventanaEvento.getTxtDni().setText(clienteSeleccionado.getDni());
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public Date calcularFechaReserva(Date fecha){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); 
		calendar.add(Calendar.DATE, -20);  
		return convertUtilToSql(calendar.getTime()); 
	}
	
	private java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }


	public Pasaje getPasaje() {
		return pasaje;
	}

	public void setPasaje(Pasaje pasaje) {
		this.pasaje = pasaje;
	}

	public PanelEvento getPanelEvento() {
		return panelEvento;
	}

	public void setPanelEvento(PanelEvento panelEvento) {
		this.panelEvento = panelEvento;
	}
	
	public void setEventoSeleccionado(EventoDTO evento) {
		this.eventoSeleccionado=evento;
	}
	
	/*public void editarEvento(int filaSeleccionada){
		llenarComboEstados();
		llenarComboHoraEvento();
		verDatosDelEvento(filaSeleccionada);
	}
	private void verDatosDelEvento(int filaSeleccionada) {
		if (filaSeleccionada != -1){
			ventanaEditarEvento.mostrarVentana(true);
			System.out.println(eventos_en_tabla.get(filaSeleccionada)+"fila seleccionada");
			System.out.println((eventos_en_tabla.get(filaSeleccionada).getHoraEvento().toString())+"MOSTRAR");
			ventanaEditarEvento.getComboHoraEvento().setSelectedItem(this.eventos_en_tabla.get(filaSeleccionada).getHoraEvento().toString());
			if(this.eventos_en_tabla.get(filaSeleccionada).getEstadoDelPasaje().getIdEstadoPasaje()==1){
				this.ventanaVisualizarPasaje.setVisible(true);
				this.ventanaVisualizarPasaje.getBtnPagar().setVisible(false);
				this.ventanaVisualizarPasaje.getLblClienteDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getCliente().getNombre());
				this.ventanaVisualizarPasaje.getLblDniDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getCliente().getDni());
				this.ventanaVisualizarPasaje.getLblOrigenDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getCiudadOrigen().getNombre());
				this.ventanaVisualizarPasaje.getLblDestinoDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getCiudadDestino().getNombre());
				this.ventanaVisualizarPasaje.getLblTransporteDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getTransporte().getNombre());
				this.ventanaVisualizarPasaje.getLblEstadoPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getEstadoDelPasaje().getNombre());
				this.ventanaVisualizarPasaje.getLblMontoDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getValorViaje());
				this.ventanaVisualizarPasaje.getLblRestante().setText(" "+ calcularMontoQueSeDebeDelPasaje(filaSeleccionada));

			}else{
				this.ventanaVisualizarPasaje.setVisible(true);
				this.ventanaVisualizarPasaje.getBtnPagar().setVisible(true);
				this.ventanaVisualizarPasaje.getLblClienteDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getCliente().getNombre());
				this.ventanaVisualizarPasaje.getLblDniDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getCliente().getDni());
				this.ventanaVisualizarPasaje.getLblOrigenDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getCiudadOrigen().getNombre());
				this.ventanaVisualizarPasaje.getLblDestinoDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getCiudadDestino().getNombre());
				this.ventanaVisualizarPasaje.getLblTransporteDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getViaje().getTransporte().getNombre());
				this.ventanaVisualizarPasaje.getLblEstadoPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getEstadoDelPasaje().getNombre());
				this.ventanaVisualizarPasaje.getLblMontoDelPasaje().setText(" "+this.pasajes_en_tabla.get(filaSeleccionada).getValorViaje());
				this.ventanaVisualizarPasaje.getLblRestante().setText(" "+ calcularMontoQueSeDebeDelPasaje(filaSeleccionada));
			
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}*/
/*
	private BigDecimal calcularMontoQueSeDebeDelPasaje(int filaSeleccionada) {
		BigDecimal Valor1 = this.pasajes_en_tabla.get(filaSeleccionada).getValorViaje();
		BigDecimal Valor2 = this.pasajes_en_tabla.get(filaSeleccionada).getPago().getMonto();
		totalaPagar = Valor1.subtract(Valor2);
		return totalaPagar;
	}*/
		/*
	private void salirVentanaVisualizarPasaje(ActionEvent s) {
		this.ventanaVisualizarPasaje.mostrarVentana(false);
	}*/
	
}