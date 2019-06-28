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

public class PanelServicios extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelServicios;
	private String[] nombreColumnasServicios = {"Nombre del servicio","Monto","Mes","Local"};
	private JTable tablaServicios;

	public PanelServicios() {
		modelServicios = new DefaultTableModel(null,nombreColumnasServicios){
		@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		setLayout(null);
			
		JScrollPane spSueldos = new JScrollPane();
		spSueldos.setBounds(10, 140, 1342, 470);
		add(spSueldos);
		tablaServicios = new JTable(modelServicios);
		spSueldos.setViewportView(tablaServicios);
		
		JPanel panelServicios = new JPanel();
		panelServicios.setBackground(new Color(0, 0, 255));
		panelServicios.setBounds(10, 11, 1342, 69);
		add(panelServicios);
		panelServicios.setLayout(null);
		
		JLabel lblServicios = new JLabel("Servicios");
		lblServicios.setForeground(Color.WHITE);
		lblServicios.setFont(new Font("Tahoma", Font.BOLD, 36));
		lblServicios.setBounds(589, 0, 219, 65);
		panelServicios.add(lblServicios);
	}

	public DefaultTableModel getModelServicios() {
		return modelServicios;
	}

	public String[] getNombreColumnasServicios() {
		return nombreColumnasServicios;
	}

	public JTable getTablaServicios() {
		return tablaServicios;
	}
}