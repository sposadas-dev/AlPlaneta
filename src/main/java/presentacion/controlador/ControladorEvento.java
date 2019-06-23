package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.Time;
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
import modelo.Cliente;
import modelo.ModeloEstadoEvento;
import modelo.ModeloEvento;
import modelo.Pasaje;
import persistencia.dao.mysql.DAOSQLFactory;
import persistencia.dao.mysql.EstadoEventoDAOSQL;
import presentacion.vista.administrativo.NotificacionEmergente;
import presentacion.vista.administrativo.PanelEvento;
import presentacion.vista.administrativo.VentanaEditarEvento;
import presentacion.vista.administrativo.VentanaMotivosReprogramacionEvento;
import presentacion.vista.administrativo.VentanaRegistrarEvento;
import presentacion.vista.administrativo.VentanaTablaClientes;
import presentacion.vista.administrativo.VistaNotificacionDeEvento;
import recursos.Mapper;

public class ControladorEvento {

	private VentanaTablaClientes ventanaTablaClientes;
	private VentanaMotivosReprogramacionEvento ventanaMotivos;
	private EventoDTO eventoSeleccionado;
	private NotificacionEmergente notificacion;
	private VistaNotificacionDeEvento vistaNotificacion;
	private List<ClienteDTO> clientes_en_tabla;
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
	private EventoDTO eventoAsociado;
	private Mapper mapper;

	public ControladorEvento(ModeloEvento evento, List<EventoDTO> eventos_en_tabla){
		//DATOS EVENTO:
		java.util.Date fecha = new java.util.Date(); 
		this.fechaIngreso = new java.sql.Date(fecha.getTime());	
		this.fechaEvento = null;
		this.horaEvento = null;
		this.descripcion = null;
		this.clienteSeleccionado = null; //cliente que selecciona en la tabla 
		this.estado = null;
		this.motivoReprogramacion="";
		this.eventoRegistrado = null;
		
		this.mapper = new Mapper();
		this.ventanaEvento = VentanaRegistrarEvento.getInstance();
		this.ventanaEditarEvento = VentanaEditarEvento.getInstance();
		this.ventanaTablaClientes = VentanaTablaClientes.getInstance();
		this.ventanaMotivos = VentanaMotivosReprogramacionEvento.getInstance();
		this.notificacion = NotificacionEmergente.getInstance();
		this.vistaNotificacion = VistaNotificacionDeEvento.getInstance();
		
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
		this.ventanaEditarEvento.getBtnMotivos().addActionListener(rc->verVentanaMotivos(rc));

		this.ventanaTablaClientes.getBtnAtras().addActionListener(vc->volverVentanaAgregarEvento(vc));
		this.ventanaTablaClientes.getBtnConfirmar().addActionListener(ce->agregarClienteToEvento(ce));
		
		this.ventanaMotivos.getBtnCancelar().addActionListener(a->cerrarVentanaMotivos(a));
		
		this.notificacion.getBtnVerNotificacion().addActionListener(v->mostrarEvento(v));
		
//		this.administrativoLogueado= administrativoLogueado;		
	}

	public void iniciar(){
		llenarComboEstados();
		llenarComboHora();
		llenarComboMinutos();
	}
	
	public void llenarComboEstados() {
		List<EstadoEventoDTO> estados = new EstadoEventoDAOSQL().readAll();
		String[] nombresEstados = new String[estados.size()];
		for(int i=0; i<estados.size();i++){
			String e = estados.get(i).getNombre();
			nombresEstados [i] = e;
		}	
		this.ventanaEvento.getComboEstadoEvento().setModel(new DefaultComboBoxModel<String>(nombresEstados));
		this.ventanaEditarEvento.getComboEstadoEvento().setModel(new DefaultComboBoxModel<String>(nombresEstados));
		this.panelEvento.getComboFiltros().setModel(new DefaultComboBoxModel<String>(nombresEstados));
	}
	
	public void llenarComboHora() {
		String [] horas = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20"};
		this.ventanaEvento.getComboHora().setModel(new DefaultComboBoxModel<String>(horas));
		this.ventanaEditarEvento.getComboHora().setModel(new DefaultComboBoxModel<String>(horas));
	}
	
	public void llenarComboMinutos() {
		String [] minutos = new String [60];
		for(int i=0; i<=59; i++) {
			if(i<10) 
				minutos[i] = "0"+i+"";
			else
				minutos[i] = i+"";
		}
		this.ventanaEvento.getComboMinutos().setModel(new DefaultComboBoxModel<String>(minutos));
		this.ventanaEditarEvento.getComboMinutos().setModel(new DefaultComboBoxModel<String>(minutos));
	}
	
	public void registrarEvento(ActionEvent rc){
		if(camposLlenosRegistrarEvento()){
			this.fechaEvento = convertUtilToSql(this.ventanaEvento.getDateFechaEvento().getDate());
			this.horaEvento = obtenerHora(this.ventanaEvento.getComboHora().getSelectedItem().toString(),this.ventanaEvento.getComboMinutos().getSelectedItem().toString());
			this.descripcion = this.ventanaEvento.getTxtDescripcion().getText();
			this.estado = obtenerEstadoEventoPorNombre(this.ventanaEvento.getComboEstadoEvento().getSelectedItem().toString());
			
			this.eventoRegistrado = new EventoDTO(0,fechaIngreso,fechaEvento,horaEvento,descripcion,clienteSeleccionado,administrativoLogueado,estado,motivoReprogramacion,0);
		
			evento.agregarEvento(eventoRegistrado);
			this.ventanaEvento.limpiarCampos();
			this.ventanaEvento.cerrarVentana();
		}
	}
	
	public void editarEvento(ActionEvent rc){
		Date fechaEvento = convertUtilToSql(this.ventanaEditarEvento.getDateFechaEvento().getDate());
		Time horaEvento = obtenerHora(this.ventanaEditarEvento.getComboHora().getSelectedItem().toString(),this.ventanaEditarEvento.getComboMinutos().getSelectedItem().toString());
		String prefijo = "("+mapper.parseToString(fechaIngreso)+") "+administrativoLogueado.getNombre()+":\n";
		String motivoReprogramacion = "";
		
		if(eventoSeleccionado.getMotivoReprogramacion().equals(""))
			motivoReprogramacion += prefijo+this.ventanaEditarEvento.getTxtReprogramacion().getText()+"\n";	
		else
			motivoReprogramacion += eventoSeleccionado.getMotivoReprogramacion()+"\n"+prefijo.toString()+this.ventanaEditarEvento.getTxtReprogramacion().getText()+"\n";	
		EstadoEventoDTO estado = obtenerEstadoEventoPorNombre(this.ventanaEditarEvento.getComboEstadoEvento().getSelectedItem().toString());
		
		EventoDTO nuevoEvento = new EventoDTO(eventoSeleccionado.getIdEvento(),fechaIngreso,fechaEvento,horaEvento,descripcion,eventoSeleccionado.getCliente(),administrativoLogueado,estado,motivoReprogramacion,0);
		evento.editarEvento(nuevoEvento);
		
		this.ventanaEditarEvento.limpiarCampos();
		this.ventanaEditarEvento.cerrarVentana();
	}
	
	public void llenarMotivos(EventoDTO evento) {
		ventanaMotivos.getTxtMotivos().setText(evento.getMotivoReprogramacion());
	}
	
	public void verVentanaMotivos(ActionEvent e) {
		ventanaEditarEvento.setEnabled(false);
		llenarMotivos(eventoSeleccionado);
		ventanaMotivos.mostrarVentana(true);	
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
	
	private Time obtenerHora(String hora, String minutos) {
		String miHora = hora+":"+minutos+":"+"00";
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
	
	private void cerrarVentanaMotivos(ActionEvent e) {
		this.ventanaEditarEvento.setEnabled(true);
		this.ventanaMotivos.limpiarCampos();
		this.ventanaMotivos.cerrarVentana();
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
				
				String[] fechaStringActual = fechaActual.toString().split("-");
				String diaActual = fechaStringActual[2];
				String mesActual = fechaStringActual[1];
				String añoActual = fechaStringActual[0];
				//System.out.println("HORA ACTUAL: "+horaActual+":"+minutosActual+":"+segundosActual);
				for(EventoDTO e : evento.obtenerEvento()) {
					
					String[] horarioParticular = e.getHoraEvento().toString().split(":");
					String horaParticular = horarioParticular[0];
					String minutosParticular = horarioParticular[1];
					String segundosParticular = horarioParticular[2];
					
					String[] fechaStringParticular = e.getFechaEvento().toString().split("-");
					String diaParticular = fechaStringParticular[2];
					String mesParticular = fechaStringParticular[1];
					String añoParticular = fechaStringParticular[0];

					if(horaParticular.equals(horaActual) && minutosParticular.equals(minutosActual) && segundosActual.equals(segundosParticular)) {	
						if(añoParticular.equals(añoActual) && mesParticular.equals(mesActual) && diaActual.equals(diaParticular)) {	
							eventoAsociado = e;
							notificacion.mostrarBtnVerNotificacion();
				    		notificacion.setLblMensajeEventoNuevo("Tienes un evento nuevo!");
							notificacion.mostrarVentana(true);							
							esperarYcerrarVentana();
						}
					}
				}
			}
		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, 0, 1000);//1000=1 segundo
	}
	
	public void mostrarEvento(ActionEvent e) {
		vistaNotificacion.getLblDatoDescripcion().setText(eventoAsociado.getDescripcion());
		vistaNotificacion.getLblDatoEstado().setText(eventoAsociado.getEstadoEvento().getNombre());
		vistaNotificacion.getLblDatoApellido().setText(eventoAsociado.getCliente().getApellido());
		vistaNotificacion.getLblDatoCelular().setText(eventoAsociado.getCliente().getMedioContacto().getTelefonoCelular());
		vistaNotificacion.getLblDatoDni().setText(eventoAsociado.getCliente().getDni());
		
		vistaNotificacion.mostrarVentana(true);
		notificacion.mostrarVentana(false);
		
		eventoAsociado.setVisto(1);
		evento.editarVistoEvento(eventoAsociado);
	}
	
	public void controlarNotificacionesInicioSesion() {		
		boolean vistos = false;
		for(EventoDTO e : evento.obtenerEvento()) { //hay eventos sin ver			
			if(e.getVisto()==0)
				if(esEventoPasado(e)) //hay un evento que pasó, no visto
					vistos = vistos || true;
		}
		if(vistos) {
			notificacion.setLblMensajeEventoNuevo("Tienes evento(s) sin ver!");
			notificacion.mostrarVentana(true);		
			esperarYcerrarVentana();
		}
			
	}

	private void esperarYcerrarVentana() {
		TimerTask timerTask = new TimerTask() {
			int espera=0;
		    public void run() {
		    	espera++;
		    	if (espera>120) {
		    		notificacion.mostrarVentana(false);
		    		this.cancel();
		    	}
		    }
		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, 0, 1000);
	}
	
	private boolean esEventoPasado(EventoDTO e) {
		if(this.fechaIngreso.before(e.getFechaEvento()))//es un evento futuro
			return false;
		else // es un evento del dia de hoy o de un evento anterior
			if(fechasIguales(this.fechaIngreso,e.getFechaEvento())) //es un evento de HOY 
				if(!esHorarioPasado(e.getHoraEvento())) 
					return false;
				else //el horario ya paso
					return true; 
			else //es un evento pasado
				return true; 
	}
	
	private boolean fechasIguales(Date a, Date b) {
		String[] fechaA = a.toString().split("-");
		String diaA = fechaA[2];
		String mesA = fechaA[1];
		String añoA = fechaA[0];
		
		String[] fechaB = b.toString().split("-");
		String diaB = fechaB[2];
		String mesB = fechaB[1];
		String añoB = fechaB[0];
		
		if(diaA.equals(diaB) && mesA.equals(mesB) && añoA.equals(añoB))
			return true;
		return false;
	}
	
	private boolean esHorarioPasado(Time t) {		
		String[] horarioActual = new java.sql.Time(System.currentTimeMillis()).toString().split(":");
		String horaActual = horarioActual[0];
		String minutosActual = horarioActual[1];
		
		String[] horarioParticular = t.toString().split(":");
		String horaParticular = horarioParticular[0];
		String minutosParticular = horarioParticular[1];
		
		if(Integer.compare((Integer.parseInt(horaActual)),(Integer.parseInt(horaParticular)))==0) //IF la hora actual es igual que la del evento
			if(Integer.compare((Integer.parseInt(minutosActual)),(Integer.parseInt(minutosParticular)))>=0) //IF minutos acutal es mayor O IGUAL a los minutos del evento
				return true;
			else//ELSE, minutos actual es menor los minutos del evento
				return false;
		else 
			if(Integer.compare((Integer.parseInt(horaActual)),(Integer.parseInt(horaParticular)))>0) // 0 si son iguales, -1 si a<b, 1 si a>b
				return true;
			else // la hora actual es menor que la del evento 
				return false;
		
	}
	
	public void actualizarEventosVistos() {
		for(EventoDTO e : evento.obtenerEvento()) { //hay eventos sin ver			
			if(e.getVisto()==0)
				if(esEventoPasado(e)) //hay un evento que pasó, no visto
					e.setVisto(1);
					evento.editarVistoEvento(e);
		}
	}
	
	public void setAdministrativoLogueado(AdministrativoDTO adm) {
		this.administrativoLogueado = adm;
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