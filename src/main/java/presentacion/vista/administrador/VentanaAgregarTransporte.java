package presentacion.vista.administrador;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

public class VentanaAgregarTransporte extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombreTransporte;
	private JButton btnAgregar;
	private static VentanaAgregarTransporte INSTANCE;


	public static VentanaAgregarTransporte getInstance(){
		if(INSTANCE == null)
			return new VentanaAgregarTransporte();
		else
			return INSTANCE;
	}
	
	public VentanaAgregarTransporte() {
		setTitle("Agregar transporte");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 423, 303);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(96, 163, 188));
		panel.setBounds(0, 0, 417, 53);
		contentPane.add(panel);
		
		JLabel lblAgregarTransporte = new JLabel("Agregar transporte");
		lblAgregarTransporte.setForeground(Color.WHITE);
		lblAgregarTransporte.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblAgregarTransporte.setBounds(27, 0, 253, 53);
		panel.add(lblAgregarTransporte);
		
		JLabel lblNewLabel = new JLabel("Nombre del transporte:");
		lblNewLabel.setBounds(47, 111, 131, 14);
		contentPane.add(lblNewLabel);
		
		txtNombreTransporte = new JTextField();
		txtNombreTransporte.setBounds(193, 108, 170, 20);
		contentPane.add(txtNombreTransporte);
		txtNombreTransporte.setColumns(10);
		
		JButton button = new JButton("Cancelar");
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Tahoma", Font.BOLD, 14));
		button.setBackground(new Color(192, 57, 43));
		button.setBounds(206, 177, 131, 42);
		contentPane.add(button);
		
		btnAgregar = new JButton("Agregar ");
		btnAgregar.setForeground(Color.WHITE);
		btnAgregar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAgregar.setBackground(new Color(5, 196, 107));
		btnAgregar.setBounds(47, 177, 131, 42);
		contentPane.add(btnAgregar);
	}
	
	public void mostrarVentana(){
		this.setVisible(true);
	}

	public void cerrarVentana(){
		this.setVisible(false);
	}
	
	public JButton getBtnAgregar() {
		return btnAgregar;
	}

	public JTextField getTxtNombreTransporte() {
		return txtNombreTransporte;
	}
	
	public void limpiarCampos(){
		this.txtNombreTransporte.setText(null);
	}
}