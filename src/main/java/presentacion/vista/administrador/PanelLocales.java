package presentacion.vista.administrador;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PanelLocales extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelLocales;
	private String[] nombreColumnasLocales = {"Nombre del local", "Dirección"};
	private JTable tablaLocales;
	private JLabel lblLocales;

	@SuppressWarnings("serial")
	public PanelLocales() {
	
		modelLocales = new DefaultTableModel(null,nombreColumnasLocales){
		@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		setLayout(null);
		

		JScrollPane spLocales = new JScrollPane();
		spLocales.setBounds(24, 96, 1319, 483);
		add(spLocales);
		
		tablaLocales = new JTable(modelLocales);
		spLocales.setViewportView(tablaLocales);

		JPanel panelLocales = new JPanel();
		panelLocales.setBackground(new Color(32, 178, 170));
		panelLocales.setBounds(22, 0, 1324, 64);
		add(panelLocales);
		panelLocales.setLayout(null);
		
		lblLocales = new JLabel("Locales AlPlaneta");
		lblLocales.setBounds(519, 1, 357, 65);
		lblLocales.setForeground(Color.WHITE);
		lblLocales.setFont(new Font("Tahoma", Font.BOLD, 36));
		panelLocales.add(lblLocales);
	}

	public void mostrarPanelLocales(boolean visibilidad){
		this.setVisible(visibilidad);
	}
	
	public DefaultTableModel getModelLocales() {
		return modelLocales;
	}

	public void setModelLocales(DefaultTableModel modelLocales) {
		this.modelLocales = modelLocales;
	}

	public String[] getNombreColumnasLocales() {
		return nombreColumnasLocales;
	}

	public void setNombreColumnasLocales(String[] nombreColumnasLocales) {
		this.nombreColumnasLocales = nombreColumnasLocales;
	}

	public JTable getTablaLocales() {
		return tablaLocales;
	}

	public void setTablaLocales(JTable tablaLocales) {
		this.tablaLocales = tablaLocales;
	}

	public JLabel getLblLocales() {
		return lblLocales;
	}

	public void setLblLocales(JLabel lblLocales) {
		this.lblLocales = lblLocales;
	}
}