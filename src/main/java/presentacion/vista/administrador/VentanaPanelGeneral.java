package presentacion.vista.administrador;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VentanaPanelGeneral extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnRecargarTabla;
	private DefaultTableModel model;
	private String[] nombreColumnas = {"Nombre"};
	private JTable tabla;
	private JButton btnConfirmar;
	private JLabel lbl;
	private JPanel panel;

	
	public VentanaPanelGeneral() {
		
		model = new DefaultTableModel(null,nombreColumnas){
		@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 96, 1302, 483);
		add(scrollPane);
		tabla = new JTable(model);
		scrollPane.setViewportView(tabla);
		
		btnRecargarTabla = new JButton("Recargar");
		btnRecargarTabla.setBounds(422, 608, 165, 54);
		add(btnRecargarTabla);
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(640, 608, 136, 54);
		add(btnConfirmar);
		
		panel = new JPanel();
		panel.setBackground(new Color(96, 163, 188));
		panel.setBounds(32, 32, 1302, 64);
		add(panel);
		panel.setLayout(null);
		
		lbl = new JLabel("-");
		lbl.setBounds(551, 0, 219, 65);
		lbl.setForeground(Color.WHITE);
		lbl.setFont(new Font("Tahoma", Font.BOLD, 36));
		panel.add(lbl);
	
		btnConfirmar.setVisible(false);
		btnRecargarTabla.setVisible(false);
	}

	public void mostrarPanel(){
		this.setVisible(true);
	}
	
	public JButton getBtnRecargarTabla() {
		return btnRecargarTabla;
	}

	public DefaultTableModel getModel() {
		return model;
	}

	public void setModel(DefaultTableModel model) {
		this.model = model;
	}

	public String[] getNombreColumnas() {
		return nombreColumnas;
	}

	public void setNombreColumnas(String[] nombreColumnas) {
		this.nombreColumnas = nombreColumnas;
	}

	public JTable getTabla() {
		return tabla;
	}

	public void setTabla(JTable tabla) {
		this.tabla = tabla;
	}
	
	public JButton getBtnConfirmar() {
		return btnConfirmar;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

}
