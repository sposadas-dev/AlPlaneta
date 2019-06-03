package presentacion.vista.coordinador;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PanelRegimenPuntos extends JPanel{
	
	

	private static final long serialVersionUID = 1L;
	//private JButton btnRecargarTabla;
	private DefaultTableModel modelRegimenPuntos;
	private String[] nombreColumnasRegimenPuntos = {"Cant. puntos", "ARS", "vencimiento"};
	private JTable tablaRegimenPuntos;
	private JButton btnConfirmar;
	private JLabel lblRegimenPuntos;

	@SuppressWarnings("serial")
	public PanelRegimenPuntos() {
		
		modelRegimenPuntos = new DefaultTableModel(null,nombreColumnasRegimenPuntos){
		@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		setLayout(null);
		

		JScrollPane spPasajeros = new JScrollPane();
		spPasajeros.setBounds(32, 96, 810, 401);
		add(spPasajeros);
		tablaRegimenPuntos = new JTable(modelRegimenPuntos);
		spPasajeros.setViewportView(tablaRegimenPuntos);
		
//		btnRecargarTabla = new JButton("Recargar");
//		btnRecargarTabla.setBounds(422, 608, 165, 54);
//		add(btnRecargarTabla);
//		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(948, 237, 136, 54);
		add(btnConfirmar);
		
		JPanel panelRegimenPuntos = new JPanel();
		panelRegimenPuntos.setBackground(new Color(96, 163, 188));
		panelRegimenPuntos.setBounds(32, 32, 1315, 64);
		add(panelRegimenPuntos);
		panelRegimenPuntos.setLayout(null);
		
		lblRegimenPuntos = new JLabel("Regimen puntos");
		lblRegimenPuntos.setBounds(551, 0, 315, 65);
		lblRegimenPuntos.setForeground(Color.WHITE);
		lblRegimenPuntos.setFont(new Font("Tahoma", Font.BOLD, 36));
		panelRegimenPuntos.add(lblRegimenPuntos);
	
		btnConfirmar.setVisible(false);
		//btnRecargarTabla.setVisible(false);
	}

	public void mostrarPanelRegimenPuntos(boolean visibilidad){
		this.setVisible(visibilidad);
	}
	
//	public JButton getBtnRecargarTabla() {
//		return btnRecargarTabla;
//	}
	
	public DefaultTableModel getModelRegimenPuntos() {
		return modelRegimenPuntos;
	}

	public void setModelRegimenPuntos(DefaultTableModel modelRegimenPuntos) {
		this.modelRegimenPuntos = modelRegimenPuntos;
	}

	public String[] getNombreColumnasRegimenPuntos() {
		return nombreColumnasRegimenPuntos;
	}

	public void setNombreColumnasRegimenPuntos(String[] nombreColumnasRegimenPuntos) {
		this.nombreColumnasRegimenPuntos = nombreColumnasRegimenPuntos;
	}

	public JTable getTablaRegimenPuntos() {
		return tablaRegimenPuntos;
	}

	public void setTablaRegimenPuntos(JTable tablaRegimenPuntos) {
		this.tablaRegimenPuntos = tablaRegimenPuntos;
	}

	public JLabel getLblRegimenPuntos() {
		return lblRegimenPuntos;
	}

	public void setLblRegimenPuntos(JLabel lblRegimenPuntos) {
		this.lblRegimenPuntos = lblRegimenPuntos;
	}

//	public void setBtnRecargarTabla(JButton btnRecargarTabla) {
//		this.btnRecargarTabla = btnRecargarTabla;
//	}

	public void setBtnConfirmar(JButton btnConfirmar) {
		this.btnConfirmar = btnConfirmar;
	}

	public JButton getBtnConfirmar() {
		return btnConfirmar;
	}

}
