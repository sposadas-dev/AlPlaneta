package presentacion.vista.administrativo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class VentanaCargaPasajero extends JFrame {

	private static VentanaCargaPasajero INSTANCE;
	private JPanel contentPane;
	private JTable tablaPasajeros;
	private DefaultTableModel modelPasajeros;
	private JButton btnAgregarPasajero; 
	private JButton btnEliminarPasajero;
	private JLabel lblPasajerosCargados;
	private JButton btnConfirmar;
	private JButton btnAtras;
	private String[] nombreColumnas = {"Nombre","Apellido","DNI","Fecha de nacimiento","Teléfono","Email"};
	private JPanel panel;
	private JLabel labelPasajero;

	
	public static VentanaCargaPasajero getInstance(){
		if(INSTANCE == null)
			return new VentanaCargaPasajero();
		else
			return INSTANCE;
	}
	
	private VentanaCargaPasajero() {
		setTitle("Carga de pasajeros");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 900, 430);
		setLocationRelativeTo(null); 
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnAgregarPasajero = new JButton("Agregar");
		btnAgregarPasajero.setBounds(730, 78, 109, 37);
		contentPane.add(btnAgregarPasajero);
		
		JScrollPane spPasajeros = new JScrollPane();
		spPasajeros.setBounds(10, 66, 692, 234);
		contentPane.add(spPasajeros);
		
		modelPasajeros = new DefaultTableModel(null,nombreColumnas){
			@Override
				public boolean isCellEditable(int row, int column){
				return false;
				}
			};
		tablaPasajeros = new JTable(modelPasajeros);
		spPasajeros.setViewportView(tablaPasajeros);
		
		btnEliminarPasajero = new JButton("Eliminar");
		btnEliminarPasajero.setBounds(730, 160, 109, 37);
		contentPane.add(btnEliminarPasajero);
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(400, 311, 124, 55);
		contentPane.add(btnConfirmar);
		
		btnAtras = new JButton("Atrás");
		btnAtras.setBounds(189, 311, 124, 55);
		contentPane.add(btnAtras);
		
		lblPasajerosCargados = new JLabel("");
		lblPasajerosCargados.setBounds(571, 331, 261, 14);
		contentPane.add(lblPasajerosCargados);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(9, 132, 227));
		panel.setBounds(0, 0, 894, 53);
		contentPane.add(panel);
		
		labelPasajero = new JLabel("Pasajeros");
		labelPasajero.setForeground(Color.WHITE);
		labelPasajero.setFont(new Font("Tahoma", Font.BOLD, 24));
		labelPasajero.setBounds(371, 0, 241, 53);
		panel.add(labelPasajero);
		
		this.setVisible(false);
	}
	
	public JButton getBtnEliminarPasajero() {
		return btnEliminarPasajero;
	}

	public void setBtnEliminarPasajero(JButton btnEliminarPasajero) {
		this.btnEliminarPasajero = btnEliminarPasajero;
	}

	public JLabel getLblPasajerosCargados() {
		return lblPasajerosCargados;
	}

	public void mostrarVentana(boolean b) {
		this.setVisible(b);
	}

	public JButton getBtnAgregarPasajero() {
		return btnAgregarPasajero;
	}

	public void setBtnAgregarPasajero(JButton btnAgregarPasajero) {
		this.btnAgregarPasajero = btnAgregarPasajero;
	}

	public JTable getTablaPasajeros() {
		return tablaPasajeros;
	}

	public void setTablaPasajeros(JTable tablaPasajeros) {
		this.tablaPasajeros = tablaPasajeros;
	}

	public DefaultTableModel getModelPasajeros() {
		return modelPasajeros;
	}

	public void setModelPasajeros(DefaultTableModel modelPasajeros) {
		this.modelPasajeros = modelPasajeros;
	}

	public String[] getNombreColumnas() {
		return nombreColumnas;
	}

	public void setNombreColumnas(String[] nombreColumnas) {
		this.nombreColumnas = nombreColumnas;
	}

	public JButton getBtnConfirmar() {
		return btnConfirmar;
	}

	public void setBtnConfirmar(JButton btnConfirmar) {
		this.btnConfirmar = btnConfirmar;
	}
	
	public JButton getBtnAtras() {
		return btnAtras;
	}
}