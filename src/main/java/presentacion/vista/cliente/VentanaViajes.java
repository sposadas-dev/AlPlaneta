package presentacion.vista.cliente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;

public class VentanaViajes extends JFrame {

	private JTable tablaViajes;
	private DefaultTableModel modelViajes;
	private String[] columnasViajes = {"Origen","Destino","Fecha de salida", "Fecha de llegada","Transporte","Precio"};
	private JButton btnAceptar;
	private static VentanaViajes ventanaViajes;
	private JTextField txtFiltro;
	
	public static VentanaViajes getInstance(){
		if (ventanaViajes == null) {
			ventanaViajes = new VentanaViajes();
			return ventanaViajes;
		} else {
			return ventanaViajes;
		}
	}
	
	public VentanaViajes() {
		setTitle("Mis viajes históricos");
		setBounds(100, 100, 1044, 465);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(30, 144, 255));
		panel.setBounds(0, 0, 1038, 53);
		getContentPane().add(panel);
		
		JLabel lblViajes = new JLabel("Mis viajes históricos");
		lblViajes.setForeground(Color.WHITE);
		lblViajes.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblViajes.setBounds(20, 0, 253, 53);
		panel.add(lblViajes);
		
		JScrollPane spViajes = new JScrollPane();
		spViajes.setBounds(10, 115, 1018, 246);
		getContentPane().add(spViajes);
		
		modelViajes = new DefaultTableModel(null,columnasViajes){
			@Override
				public boolean isCellEditable(int row, int column){
				return false;
				}
			};
		tablaViajes = new JTable(modelViajes);
		
		spViajes.setViewportView(tablaViajes);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setForeground(Color.WHITE);
		btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAceptar.setBackground(new Color(30, 144, 255));
		btnAceptar.setBounds(437, 372, 131, 42);
		getContentPane().add(btnAceptar);
		
		txtFiltro = new JTextField();
		txtFiltro.setBounds(418, 73, 168, 19);
		getContentPane().add(txtFiltro);
		txtFiltro.setColumns(10);
		
		JLabel lblFiltro = new JLabel("Filtro");
		lblFiltro.setBounds(351, 76, 35, 13);
		getContentPane().add(lblFiltro);
		lblFiltro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
	}

	public JTextField getTxtFiltro() {
		return txtFiltro;
	}
	
	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	public JTable getTablaViajes() {
		return tablaViajes;
	}

	public DefaultTableModel getModelViajes() {
		return modelViajes;
	}

	public String[] getColumnasViajes() {
		return columnasViajes;
	}

	public void mostrarVentana(boolean visibilidad){
		this.setVisible(visibilidad);
	}
}