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
import javax.swing.JCheckBox;

public class PanelPasaje extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelReservas;
	private String[] nombreColumnasReservas = {"DNI del Cliente","Nombre", "Apellido", "Código del pasaje", "Origen" , "Destino", "Fecha de salida", "Fecha de llegada", "Hora de salida", "Valor unitario", "Transporte","Estado"};
	private JTable tablaReservas;
	private JCheckBox cancelCheckBox;
	private JCheckBox pendCheckBox;
	private JCheckBox reserCheckBox;
	private JCheckBox vendCheckBox;


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
		
		cancelCheckBox = new JCheckBox("Cancelados");
		cancelCheckBox.setBounds(415, 99, 114, 21);
		add(cancelCheckBox);

		pendCheckBox = new JCheckBox("Pendientes");
		pendCheckBox.setBounds(544, 99, 114, 21);
		add(pendCheckBox);
		
		reserCheckBox = new JCheckBox("Reservados");
		reserCheckBox.setBounds(683, 99, 114, 21);
		add(reserCheckBox);
		
		vendCheckBox = new JCheckBox("Vendidos");
		vendCheckBox.setBounds(820, 99, 95, 21);
		add(vendCheckBox);
		
	}
	
	public JCheckBox getCancelCheckBox() {
		return cancelCheckBox;
	}
	
	public JCheckBox getPendCheckBox() {
		return pendCheckBox;
	}
	
	public JCheckBox getReserCheckBox() {
		return reserCheckBox;
	}
	
	public JCheckBox getVendCheckBox() {
		return vendCheckBox;
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