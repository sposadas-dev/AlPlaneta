package presentacion.vista.administrativo;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

public class PanelEvento extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelEventos;
	private String[] nombreColumnasEventos = {"Fecha de Ingreso", "Fecha del Evento", "Hora del Evento", "Descripción" , "Apellido Cliente","Nombre Cliente", "Administrativo", "Estado del Evento","Reprogramado" };
	private JTable tablaEventos;
	private JComboBox<String> comboFiltros;
	private JTextField txtNombre;
	private JDateChooser dateDesde;
	private JDateChooser dateHasta;
	private JTextField txtApellido;
	private JLabel lblApellido;

	public PanelEvento() {
		
		modelEventos = new DefaultTableModel(null,nombreColumnasEventos){
		private static final long serialVersionUID = 1L;

		@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		setLayout(null);
			
		JScrollPane spPasajeros = new JScrollPane();
		spPasajeros.setBounds(10, 141, 1342, 469);
		add(spPasajeros);
		tablaEventos = new JTable(modelEventos);
		spPasajeros.setViewportView(tablaEventos);
		
		JPanel panelEventos = new JPanel();
		panelEventos.setBackground(new Color(66, 99, 145));
		panelEventos.setBounds(10, 11, 1342, 69);
		add(panelEventos);
		panelEventos.setLayout(null);
		
		JLabel lblEventos = new JLabel("Eventos");
		lblEventos.setForeground(Color.WHITE);
		lblEventos.setFont(new Font("Tahoma", Font.BOLD, 36));
		lblEventos.setBounds(589, 0, 219, 65);
		panelEventos.add(lblEventos);
	
		JLabel lblFiltro = new JLabel("Estado:");
		lblFiltro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFiltro.setBounds(57, 107, 100, 14);
		add(lblFiltro);
		
		comboFiltros = new JComboBox<String>();
		comboFiltros.setBounds(107, 104, 130, 20);
		comboFiltros.addItem("Todos");
		comboFiltros.addItem("pendiente");
		comboFiltros.addItem("realizado");
		comboFiltros.addItem("vencido");
		comboFiltros.addItem("cancelado");
		add(comboFiltros);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNombre.setBounds(249, 107, 100, 14);
		add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(308, 103, 139, 22);
		add(txtNombre);
		txtNombre.setColumns(10);
		
		dateDesde = new JDateChooser();
		dateDesde.setBounds(860, 103, 131, 20);
		JTextFieldDateEditor editor = (JTextFieldDateEditor) dateDesde.getDateEditor();
		editor.setEditable(false);
		add(dateDesde);
		
		JLabel lblFechaDelEvento = new JLabel("Fecha del Evento: Desde");
		lblFechaDelEvento.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFechaDelEvento.setBounds(712, 107, 183, 14);
		add(lblFechaDelEvento);
		
		dateHasta = new JDateChooser();
		JTextFieldDateEditor editor2 = (JTextFieldDateEditor) dateHasta.getDateEditor();
		editor2.setEditable(false);
		dateHasta.setBounds(1032, 103, 131, 20);
		add(dateHasta);
		
		JLabel lblHasta = new JLabel("Hasta");
		lblHasta.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblHasta.setBounds(995, 107, 78, 14);
		add(lblHasta);
		
		txtApellido = new JTextField();
		txtApellido.setColumns(10);
		txtApellido.setBounds(517, 103, 139, 22);
		add(txtApellido);
		
		lblApellido = new JLabel("Apellido:");
		lblApellido.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblApellido.setBounds(458, 107, 100, 14);
		add(lblApellido);
	}
	
	public void mostrarPanelEvento(boolean visibilidad){
		this.setVisible(visibilidad);
	}

	public DefaultTableModel getModelEventos() {
		return modelEventos;
	}

	public void setModelEventos(DefaultTableModel modelEventos) {
		this.modelEventos = modelEventos;
	}

	public String[] getNombreColumnasEventos() {
		return nombreColumnasEventos;
	}

	public void setNombreColumnasEventos(String[] nombreColumnasEventos) {
		this.nombreColumnasEventos = nombreColumnasEventos;
	}

	public JTable getTablaEventos() {
		return tablaEventos;
	}

	public void setTablaEventos(JTable tablaEventos) {
		this.tablaEventos = tablaEventos;
	}

	public JComboBox<String> getComboFiltros() {
		return comboFiltros;
	}
	
	public JTextField getFiltroNombre(){
		return txtNombre;
	}
	
	public JTextField getFiltroApellido(){
		return txtApellido;
	}
	
	public JDateChooser getFiltroDesde() {
		return dateDesde;	
	}
	
	public JDateChooser getFiltroHasta() {
		return dateHasta;	
	}
}