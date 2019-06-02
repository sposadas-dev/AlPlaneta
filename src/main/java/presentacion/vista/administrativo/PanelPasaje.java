package presentacion.vista.administrativo;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JComboBox;

public class PanelPasaje extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelReservas;
	private String[] nombreColumnasReservas = {"DNI del Cliente","Nombre", "Apellido", "Código del pasaje", "Origen" , "Destino", "Fecha de salida", "Fecha de llegada", "Hora de salida", "Valor unitario", "Transporte","Estado"};
	private JTable tablaReservas;
	private JComboBox<String>comboBoxFiltros;

	public PanelPasaje() {
		
		modelReservas = new DefaultTableModel(null,nombreColumnasReservas){
		@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		setLayout(null);
			
		JScrollPane spPasajeros = new JScrollPane();
		spPasajeros.setBounds(10, 140, 1342, 470);
		add(spPasajeros);
		tablaReservas = new JTable(modelReservas);
		spPasajeros.setViewportView(tablaReservas);
		
		JPanel panelPasajes = new JPanel();
		panelPasajes.setBackground(new Color(230, 126, 34));
		panelPasajes.setBounds(10, 11, 1342, 69);
		add(panelPasajes);
		panelPasajes.setLayout(null);
		
		JLabel lblPasajes = new JLabel("Pasajes");
		lblPasajes.setForeground(Color.WHITE);
		lblPasajes.setFont(new Font("Tahoma", Font.BOLD, 36));
		lblPasajes.setBounds(589, 0, 219, 65);
		panelPasajes.add(lblPasajes);
		
		JLabel lblFiltro = new JLabel("Filtrar por:");
		lblFiltro.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFiltro.setBounds(389, 105, 100, 14);
		add(lblFiltro);
		
		JButton btnBuscar = new JButton("Aplicar búsqueda");
		btnBuscar.setBounds(760, 101, 216, 23);
		add(btnBuscar);
		
		comboBoxFiltros = new JComboBox<String>();
		comboBoxFiltros.setBounds(509, 102, 216, 20);
		comboBoxFiltros.addItem("Seleccione");
		comboBoxFiltros.addItem("Cancelado");
		comboBoxFiltros.addItem("Pendiente");
		comboBoxFiltros.addItem("Reservado");
		comboBoxFiltros.addItem("Vendido");
		add(comboBoxFiltros);
	}
	
	public void mostrarPanelPasaje(boolean visibilidad){
		this.setVisible(visibilidad);
	}

	public DefaultTableModel getModelReservas() {
		return modelReservas;
	}

	public void setModelReservas(DefaultTableModel modelReservas) {
		this.modelReservas = modelReservas;
	}

	public String[] getNombreColumnasReservas() {
		return nombreColumnasReservas;
	}

	public void setNombreColumnasReservas(String[] nombreColumnasReservas) {
		this.nombreColumnasReservas = nombreColumnasReservas;
	}

	public JTable getTablaReservas() {
		return tablaReservas;
	}

	public void setTablaReservas(JTable tablaReservas) {
		this.tablaReservas = tablaReservas;
	}
}