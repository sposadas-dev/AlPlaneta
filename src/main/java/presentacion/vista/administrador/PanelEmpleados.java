package presentacion.vista.administrador;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JCheckBox;

public class PanelEmpleados extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelEmpleados;
	private String[] nombreColumnasEmpleados = {"Nombre","Usuario","Rol","Estado"};
	private JTable tablaEmpleados;
	private JButton btnConfirmar;
	private JLabel lblEmpleados;
	private JCheckBox activos;
	private JCheckBox inactivos;

	@SuppressWarnings("serial")
	public PanelEmpleados() {
		
		modelEmpleados = new DefaultTableModel(null,nombreColumnasEmpleados){
		@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		setLayout(null);
		
		JScrollPane spPasajeros = new JScrollPane();
		spPasajeros.setBounds(32, 96, 1302, 483);
		add(spPasajeros);
		tablaEmpleados = new JTable(modelEmpleados);
		spPasajeros.setViewportView(tablaEmpleados);
		

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(640, 608, 136, 54);
		add(btnConfirmar);
		
		JPanel panelTransporte = new JPanel();
		panelTransporte.setBackground(new Color(87, 95, 207));
		panelTransporte.setBounds(32, 32, 1302, 64);
		add(panelTransporte);
		panelTransporte.setLayout(null);
		
		lblEmpleados = new JLabel("Empleados");
		lblEmpleados.setBounds(551, 0, 219, 65);
		lblEmpleados.setForeground(Color.WHITE);
		lblEmpleados.setFont(new Font("Tahoma", Font.BOLD, 36));
		panelTransporte.add(lblEmpleados);
		
		activos = new JCheckBox("Activos");
		activos.setBounds(70, 6, 95, 21);
		activos.setSelected(true);
		add(activos);
		
		inactivos = new JCheckBox("Inactivos");
		inactivos.setBounds(167, 5, 95, 21);
		add(inactivos);
	
		btnConfirmar.setVisible(false);
	
	}

	public void mostrarPanelTransporte(boolean visibilidad){
		this.setVisible(visibilidad);
	}
	
	public JCheckBox getActivos() {
		return activos;
	}
	
	public JCheckBox getInactivos() {
		return inactivos;
	}

	public DefaultTableModel getModelEmpleados() {
		return modelEmpleados;
	}

	public String[] getNombreColumnasEmpleados() {
		return nombreColumnasEmpleados;
	}

	public JTable getTablaEmpleados() {
		return tablaEmpleados;
	}

	public JButton getBtnConfirmar() {
		return btnConfirmar;
	}
}