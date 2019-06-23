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
	private String[] nombreColumnasCondiciones = {"Desde","Hasta","Porcentaje de retención","Estado del Pasaje"};
	private JTable tablaCondiciones;
	private JButton btnConfirmar;
	private JLabel lblCondiciones;
	private JCheckBox Reservas;
	private JCheckBox Ventas;
	private JCheckBox Todas;
	
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
		
		lblCondiciones = new JLabel("Devolución de dinero por cancelación");
		lblCondiciones.setBounds(228, 0, 740, 65);
		lblCondiciones.setForeground(Color.WHITE);
		lblCondiciones.setFont(new Font("Tahoma", Font.BOLD, 36));
		panelTransporte.add(lblCondiciones);
		
		Reservas = new JCheckBox("Reservas");
		Reservas.setBounds(70, 6, 95, 21);
		Reservas.setSelected(true);
		add(Reservas);
		
		Ventas = new JCheckBox("Ventas");
		Ventas.setBounds(167, 5, 95, 21);
		add(Ventas);
		
		Todas = new JCheckBox("Todas");
		Todas.setBounds(264, 5, 95, 21);
		add(Todas);
	
		btnConfirmar.setVisible(false);
	
	}

	public void mostrarPanelCondiciones(boolean visibilidad){
		this.setVisible(visibilidad);
	}
	
	public JCheckBox getReservas() {
		return Reservas;
	}
	
	public JCheckBox getVentas() {
		return Ventas;
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

	public JCheckBox getTodas() {
		return Todas;
	}

	public void setTodas(JCheckBox todas) {
		Todas = todas;
	}

	
}

