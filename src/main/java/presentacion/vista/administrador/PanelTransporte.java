package presentacion.vista.administrador;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelTransporte extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnRecargarTabla;
	private DefaultTableModel modelTransportes;
	private String[] nombreColumnasTransporte = {"Nombre del transporte"};
	private JTable tablaTransportes;
	private JButton btnConfirmar;
	private JLabel lblTransportes;

	@SuppressWarnings("serial")
	public PanelTransporte() {
		
		modelTransportes = new DefaultTableModel(null,nombreColumnasTransporte){
		@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		setLayout(null);
		

		JScrollPane spPasajeros = new JScrollPane();
		spPasajeros.setBounds(32, 96, 1302, 483);
		add(spPasajeros);
		tablaTransportes = new JTable(modelTransportes);
		spPasajeros.setViewportView(tablaTransportes);
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(827, 608, 136, 54);
		add(btnConfirmar);
		
		JPanel panelTransporte = new JPanel();
		panelTransporte.setBackground(new Color(96, 163, 188));
		panelTransporte.setBounds(32, 32, 1302, 64);
		add(panelTransporte);
		panelTransporte.setLayout(null);
		
		lblTransportes = new JLabel("Transportes");
		lblTransportes.setBounds(551, 0, 219, 65);
		lblTransportes.setForeground(Color.WHITE);
		lblTransportes.setFont(new Font("Tahoma", Font.BOLD, 36));
		panelTransporte.add(lblTransportes);
	
		btnConfirmar.setVisible(false);
//		btnRecargarTabla.setVisible(false);
	}

	public void mostrarPanelTransporte(boolean visibilidad){
		this.setVisible(visibilidad);
	}
	
//	public JButton getBtnRecargarTabla() {
//		return btnRecargarTabla;
//	}
	
	public DefaultTableModel getModelTransportes() {
		return modelTransportes;
	}

	public void setModelTransportes(DefaultTableModel modelTransportes) {
		this.modelTransportes = modelTransportes;
	}

	public String[] getNombreColumnasTransporte() {
		return nombreColumnasTransporte;
	}

	public void setNombreColumnasTransporte(String[] nombreColumnasTransporte) {
		this.nombreColumnasTransporte = nombreColumnasTransporte;
	}

	public JTable getTablaTransportes() {
		return tablaTransportes;
	}

	public void setTablaTransportes(JTable tablaTransportes) {
		this.tablaTransportes = tablaTransportes;
	}

	public JLabel getLblTransportes() {
		return lblTransportes;
	}

	public void setLblTransportes(JLabel lblTransportes) {
		this.lblTransportes = lblTransportes;
	}

	public void setBtnRecargarTabla(JButton btnRecargarTabla) {
		this.btnRecargarTabla = btnRecargarTabla;
	}

	public void setBtnConfirmar(JButton btnConfirmar) {
		this.btnConfirmar = btnConfirmar;
	}

	public JButton getBtnConfirmar() {
		return btnConfirmar;
	}
}