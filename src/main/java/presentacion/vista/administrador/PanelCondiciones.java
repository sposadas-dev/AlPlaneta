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

public class PanelCondiciones extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelCondiciones;
	private String[] nombreColumnasCondiciones = {"Inicio","Fin","Porcentaje","Parae Estado"};
	private JTable tablaCondiciones;
	private JButton btnConfirmar;
	private JLabel lblCondiciones;
	private JCheckBox activos;
	private JCheckBox inactivos;

	@SuppressWarnings("serial")
	public PanelCondiciones() {
		
		modelCondiciones = new DefaultTableModel(null,nombreColumnasCondiciones){
		@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		setLayout(null);
		
		JScrollPane spPasajeros = new JScrollPane();
		spPasajeros.setBounds(32, 96, 1302, 483);
		add(spPasajeros);
		tablaCondiciones = new JTable(modelCondiciones);
		spPasajeros.setViewportView(tablaCondiciones);
		

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(640, 608, 136, 54);
		add(btnConfirmar);
		
		JPanel panelTransporte = new JPanel();
		panelTransporte.setBackground(new Color(87, 95, 207));
		panelTransporte.setBounds(32, 32, 1302, 64);
		add(panelTransporte);
		panelTransporte.setLayout(null);
		
		lblCondiciones = new JLabel("Empleados");
		lblCondiciones.setBounds(551, 0, 219, 65);
		lblCondiciones.setForeground(Color.WHITE);
		lblCondiciones.setFont(new Font("Tahoma", Font.BOLD, 36));
		panelTransporte.add(lblCondiciones);
		
		activos = new JCheckBox("Activos");
		activos.setBounds(70, 6, 95, 21);
		activos.setSelected(true);
		add(activos);
		
		inactivos = new JCheckBox("Inactivos");
		inactivos.setBounds(167, 5, 95, 21);
		add(inactivos);
	
		btnConfirmar.setVisible(false);
	
	}

	public void mostrarPanelCondiciones(boolean visibilidad){
		this.setVisible(visibilidad);
	}
	
	public JCheckBox getActivos() {
		return activos;
	}
	
	public JCheckBox getInactivos() {
		return inactivos;
	}

	public DefaultTableModel getModelCondiciiones() {
		return modelCondiciones;
	}

	public String[] getNombreColumnasCondiciones() {
		return nombreColumnasCondiciones;
	}

	public JTable getTablaCondiciones() {
		return tablaCondiciones;
	}

	public JButton getBtnConfirmar() {
		return btnConfirmar;
	}
}