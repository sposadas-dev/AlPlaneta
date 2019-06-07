package presentacion.vista.administrativo;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;

public class PanelEvento extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelEventos;
	private String[] nombreColumnasEventos = {"Fecha de Ingreso", "Fecha del Evento", "Hora del Evento", "Descripción" , "Apellido Cliente","Nombre Cliente", "Administrativo", "Estado del Evento","Reprogramado" };
	private JTable tablaEventos;
	private JComboBox<String> comboFiltros;
	private JComboBox<String> comboOpcionesFiltro;
	private JButton btnBuscar;
	private JButton btnBorrarFiltros;

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
	
		JLabel lblFiltro = new JLabel("Filtrar por:");
		lblFiltro.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFiltro.setBounds(235, 106, 100, 14);
		add(lblFiltro);
		
		comboFiltros = new JComboBox<String>();
		comboFiltros.setBounds(306, 102, 207, 20);
		comboFiltros.addItem("Seleccione");
		comboFiltros.addItem("Fecha de Ingreso");
		comboFiltros.addItem("Fecha del Evento");
		comboFiltros.addItem("Apellido del Cliente");
		comboFiltros.addItem("Estado");
		add(comboFiltros);
		
		btnBuscar = new JButton("Aplicar");
		btnBuscar.setBounds(754, 91, 139, 38);
		add(btnBuscar);
		
		btnBorrarFiltros = new JButton("Borrar Filtros");
		btnBorrarFiltros.setBounds(913, 91, 130, 38);
		add(btnBorrarFiltros);
		
		comboOpcionesFiltro = new JComboBox<String>();
		comboOpcionesFiltro.setBounds(525, 101, 207, 20);
		comboOpcionesFiltro.setEnabled(false);
		add(comboOpcionesFiltro);
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

	public JComboBox<String> getComboOpcionesFiltros() {
		return comboOpcionesFiltro;
	}

	public JButton getBtnBuscar() {
		return btnBuscar;
	}

	public JButton getBtnBorrarFiltros() {
		return btnBorrarFiltros;
	}
	
	
}