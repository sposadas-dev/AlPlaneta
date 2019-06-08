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
	private static VentanaViajes instance;
	private JTextField txtFiltro;
	
	public static VentanaViajes  getInstance() {
		if (instance == null)
			return new VentanaViajes();
		else
			return instance;
	}
	
	public VentanaViajes() {
		setTitle("Mis viajes históricos");
		setBounds(100, 100, 928, 465);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(30, 144, 255));
		panel.setBounds(0, 0, 922, 53);
		getContentPane().add(panel);
		
		JLabel lblViajes = new JLabel("Mis viajes históricos");
		lblViajes.setForeground(Color.WHITE);
		lblViajes.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblViajes.setBounds(10, 0, 253, 53);
		panel.add(lblViajes);
		
		txtFiltro = new JTextField();
		txtFiltro.setBounds(389, 23, 168, 19);
		panel.add(txtFiltro);
		txtFiltro.setColumns(10);
		
		JLabel lblFiltro = new JLabel("Filtro");
		lblFiltro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFiltro.setBounds(351, 26, 35, 13);
		panel.add(lblFiltro);
		
		JScrollPane spViajes = new JScrollPane();
		spViajes.setBounds(10, 64, 902, 262);
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
		btnAceptar.setBounds(351, 359, 131, 42);
		getContentPane().add(btnAceptar);
		
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