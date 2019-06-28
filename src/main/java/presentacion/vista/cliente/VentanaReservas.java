package presentacion.vista.cliente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;

import presentacion.vista.administrativo.VentanaConfirmacionPasaje;

public class VentanaReservas extends JFrame {

	private JTable tablaReservas;
	private DefaultTableModel modelReservas;
	private  String[] columnasReservas = {"Origen","Destino","Fecha de vencimiento","Fecha de salida", "Fecha de llegada","Hora de salida","Transporte","Monto pagado","Importe a pagar"};
	private JButton btnAceptar;
	private static VentanaReservas ventanaReservas;
	private JTextField txtFiltro;
	private JButton btnVerPagos;
	
	public static VentanaReservas getInstance(){
		if (ventanaReservas == null) {
			ventanaReservas = new VentanaReservas();
			return ventanaReservas;
		} else {
			return ventanaReservas;
		}
	}
	
	public VentanaReservas() {
		setTitle("Mis reservas");
		setBounds(100, 100, 1246, 465);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(128, 0, 128));
		panel.setBounds(0, 0, 1240, 53);
		getContentPane().add(panel);
		
		JLabel lblReservas = new JLabel("Mis reservas");
		lblReservas.setForeground(Color.WHITE);
		lblReservas.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblReservas.setBounds(33, 0, 253, 53);
		panel.add(lblReservas);
		
		JScrollPane spReservas = new JScrollPane();
		spReservas.setBounds(10, 109, 1220, 252);
		getContentPane().add(spReservas);
		
		modelReservas = new DefaultTableModel(null,columnasReservas){
			@Override
				public boolean isCellEditable(int row, int column){
				return false;
				}
			};
		tablaReservas = new JTable(modelReservas);
		spReservas.setViewportView(tablaReservas);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setForeground(Color.WHITE);
		btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAceptar.setBackground(new Color(30, 144, 255));
		btnAceptar.setBounds(347, 372, 131, 42);
		getContentPane().add(btnAceptar);
		
		txtFiltro = new JTextField();
		txtFiltro.setBounds(457, 79, 154, 19);
		getContentPane().add(txtFiltro);
		txtFiltro.setColumns(10);
		
		JLabel lblFiltro = new JLabel("Filtro");
		lblFiltro.setBounds(401, 85, 46, 13);
		getContentPane().add(lblFiltro);
		lblFiltro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		btnVerPagos = new JButton("Ver pagos");
		btnVerPagos.setBounds(549, 372, 131, 42);
		getContentPane().add(btnVerPagos);
		
	}
	
	public JTextField getTxtFiltro() {
		return txtFiltro;
	}
	
	public JTable getTablaReservas() {
		return tablaReservas;
	}

	public DefaultTableModel getModelReservas() {
		return modelReservas;
	}

	public String[] getColumnasReservas() {
		return columnasReservas;
	}
	
	public JButton getBtnAceptar() {
		return btnAceptar;
	}
	
	public JButton getBtnVerPagos() {
		return btnVerPagos;
	}


	public void mostrarVentana(boolean visibilidad){
		this.setVisible(visibilidad);
	}
}