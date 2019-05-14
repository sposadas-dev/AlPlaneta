package presentacion.vista.administrativo;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;

public class PanelPasaje extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnAgregarReserva;
	private DefaultTableModel modelReservas;
	private String[] nombreColumnasReservas = {"DNI del Cliente","Nombre", "Apellido", "Código del pasaje", "Origen" , "Destino", "Fecha de salida", "Hora de salida", "Valor unitario", "Estado"};
	private JTable tablaReservas;
	private JButton btnEditarReserva;
	private JButton btnCancelar;
	private JButton btnVisualizarPasaje;

	public PanelPasaje() {
		
		modelReservas = new DefaultTableModel(null,nombreColumnasReservas){
		@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		setLayout(null);
			
		JScrollPane spPasajeros = new JScrollPane();
		spPasajeros.setBounds(10, 81, 1342, 399);
		add(spPasajeros);
		tablaReservas = new JTable(modelReservas);
		spPasajeros.setViewportView(tablaReservas);
		
		btnAgregarReserva = new JButton("Agregar pasaje");
		btnAgregarReserva.setBounds(108, 491, 133, 50);
		add(btnAgregarReserva);		
		
		btnEditarReserva = new JButton("Editar");
		btnEditarReserva.setBounds(323, 491, 120, 50);
		add(btnEditarReserva);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(541, 502, 126, 50);
		add(btnCancelar);
		
		btnVisualizarPasaje = new JButton("Visualizar pasaje");
		btnVisualizarPasaje.setBounds(710, 513, 133, 50);
		add(btnVisualizarPasaje);
		
		JPanel panelPasajes = new JPanel();
		panelPasajes.setBackground(new Color(230, 126, 34));
		panelPasajes.setBounds(10, 11, 1342, 69);
		add(panelPasajes);
		panelPasajes.setLayout(null);
		
		JLabel lblPasajes = new JLabel("Pasajes");
		lblPasajes.setForeground(Color.WHITE);
		lblPasajes.setFont(new Font("Tahoma", Font.BOLD, 36));
		lblPasajes.setBounds(589, 0, 219, 65);
		panelPasajes.add(lblPasajes);
	}
	
	public void mostrarPanelPasaje(boolean visibilidad){
		this.setVisible(visibilidad);
	}

	public JButton getBtnAgregarCliente() {
		return btnAgregarReserva;
	}

	public DefaultTableModel getModelClientes() {
		return modelReservas;
	}

	public void setModelClientes(DefaultTableModel modelClientes) {
		this.modelReservas = modelClientes;
	}

	public String[] getNombreColumnasClientes() {
		return nombreColumnasReservas;
	}

	public void setNombreColumnasClientes(String[] nombreColumnasClientes) {
		this.nombreColumnasReservas = nombreColumnasClientes;
	}

	public JTable getTablaClientes() {
		return tablaReservas;
	}

	public void setTablaClientes(JTable tablaClientes) {
		this.tablaReservas = tablaClientes;
	}
	
	public JButton getBtnAgregarReserva() {
		return btnAgregarReserva;
	}
}