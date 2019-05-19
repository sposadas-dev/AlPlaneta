package presentacion.vista.administrador;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PanelTransporte extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnRecargarTabla;
	private DefaultTableModel modelTransportes;
	private String[] nombreColumnasClientes = {"Nombre del transporte"};
	private JTable tablaTransportes;
	private JButton btnConfirmar;
	private JLabel lblTransportes;

	
	public PanelTransporte() {
		
		modelTransportes = new DefaultTableModel(null,nombreColumnasClientes){
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
		
		lblTransportes = new JLabel("Transportes");
		lblTransportes.setBounds(551, 0, 219, 65);
		lblTransportes.setForeground(Color.WHITE);
		lblTransportes.setFont(new Font("Tahoma", Font.BOLD, 36));
		panelClientes.add(lblTransportes);
	
		btnConfirmar.setVisible(false);
		btnRecargarTabla.setVisible(false);
	}

	public void mostrarPanelTransporte(boolean visibilidad){
		this.setVisible(visibilidad);
	}
	
	public JButton getBtnRecargarTabla() {
		return btnRecargarTabla;
	}

	public DefaultTableModel getModelClientes() {
		return modelTransportes;
	}

	public void setModelClientes(DefaultTableModel modelClientes) {
		this.modelTransportes = modelClientes;
	}

	public String[] getNombreColumnasClientes() {
		return nombreColumnasClientes;
	}

	public void setNombreColumnasClientes(String[] nombreColumnasClientes) {
		this.nombreColumnasClientes = nombreColumnasClientes;
	}

	public JTable getTablaTransportes() {
		return tablaTransportes;
	}

	public void setTablaTransportes(JTable tablaTransportes) {
		this.tablaTransportes = tablaTransportes;
	}
	
	public JButton getBtnConfirmar() {
		return btnConfirmar;
	}
}