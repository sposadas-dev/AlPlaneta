package presentacion.vista.administrador;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PanelGeneral extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnRecargarTabla;
	private DefaultTableModel model;
	private String[] nombreColumnas = {"Nombre del transporte"};
	private JTable tabla;
	private JButton btnConfirmar;
	private JLabel lbl;

	
	public PanelGeneral() {
		
		model = new DefaultTableModel(null,nombreColumnas){
		@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		setLayout(null);

		JScrollPane spPasajeros = new JScrollPane();
		spPasajeros.setBounds(32, 96, 1302, 483);
		add(spPasajeros);
		tabla = new JTable(model);
		spPasajeros.setViewportView(tabla);
		
		btnRecargarTabla = new JButton("Recargar");
		btnRecargarTabla.setBounds(422, 608, 165, 54);
		add(btnRecargarTabla);
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(640, 608, 136, 54);
		add(btnConfirmar);
		
		JPanel panelClientes = new JPanel();
		panelClientes.setBackground(new Color(96, 163, 188));
		panelClientes.setBounds(32, 32, 1302, 64);
		add(panelClientes);
		panelClientes.setLayout(null);
		
		lbl = new JLabel("-");
		lbl.setBounds(551, 0, 219, 65);
		lbl.setForeground(Color.WHITE);
		lbl.setFont(new Font("Tahoma", Font.BOLD, 36));
		panelClientes.add(lbl);
	
		btnConfirmar.setVisible(false);
		btnRecargarTabla.setVisible(false);
	}

	public void mostrarPanel(boolean visibilidad){
		this.setVisible(visibilidad);
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
	
}