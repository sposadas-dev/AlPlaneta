package presentacion.vista.contador;

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

public class PanelSueldos extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelSueldos;
	private String[] nombreColumnasReservas = {"Nombre", "Apellido","DNI", "Rol", "Local", "Mes", "Sueldo"};
	private JTable tablaSueldos;

	public PanelSueldos() {
		modelSueldos = new DefaultTableModel(null,nombreColumnasReservas){
		@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		setLayout(null);
			
		JScrollPane spSueldos = new JScrollPane();
		spSueldos.setBounds(10, 140, 1342, 470);
		add(spSueldos);
		tablaSueldos = new JTable(modelSueldos);
		spSueldos.setViewportView(tablaSueldos);
		
		JPanel panelSueldos = new JPanel();
		panelSueldos.setBackground(new Color(46, 139, 87));
		panelSueldos.setBounds(10, 11, 1342, 69);
		add(panelSueldos);
		panelSueldos.setLayout(null);
		
		JLabel lblSueldos = new JLabel("Sueldos");
		lblSueldos.setForeground(Color.WHITE);
		lblSueldos.setFont(new Font("Tahoma", Font.BOLD, 36));
		lblSueldos.setBounds(589, 0, 219, 65);
		panelSueldos.add(lblSueldos);
	}

	public JTable getTablaSueldos() {
		return tablaSueldos;
	}

	public DefaultTableModel getModelSueldos() {
		return modelSueldos;
	}

	public String[] getNombreColumnasReservas() {
		return nombreColumnasReservas;
	}
}