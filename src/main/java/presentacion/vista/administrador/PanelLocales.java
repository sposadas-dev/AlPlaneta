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
	private String[] nombreColumnasLocales = {"Nombre Local", "Direccion"};
	private JTable tablaLocales;
	private JButton btnConfirmar;
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
		spLocales.setBounds(450, 96, 602, 483);
		add(spLocales);
		tablaLocales = new JTable(modelLocales);
		spLocales.setViewportView(tablaLocales);
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(827, 608, 136, 54);
		add(btnConfirmar);
		
		JPanel panelLocales = new JPanel();
		panelLocales.setBackground(new Color(96, 163, 188));
		panelLocales.setBounds(450, 32, 602, 64);
		add(panelLocales);
		panelLocales.setLayout(null);
		
		lblLocales = new JLabel("Locales AlPlaneta");
		lblLocales.setBounds(132, 0, 397, 65);
		lblLocales.setForeground(Color.WHITE);
		lblLocales.setFont(new Font("Tahoma", Font.BOLD, 36));
		panelLocales.add(lblLocales);
	
		btnConfirmar.setVisible(false);
//		btnRecargarTabla.setVisible(false);
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

	public void setBtnConfirmar(JButton btnConfirmar) {
		this.btnConfirmar = btnConfirmar;
	}

	public JButton getBtnConfirmar() {
		return btnConfirmar;
	}
}
