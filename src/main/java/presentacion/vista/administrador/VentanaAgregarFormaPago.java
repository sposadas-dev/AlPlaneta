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

public class VentanaAgregarFormaPago extends JFrame {

	private JPanel contentPane;
	private JTextField txtTipoPago;
	private JButton btnAgregar;
	private JButton btnCancelar;
	private static VentanaAgregarFormaPago INSTANCE;


	public static VentanaAgregarFormaPago getInstance(){
		if(INSTANCE == null)
			return new VentanaAgregarFormaPago();
		else
			return INSTANCE;
	}
	
	public VentanaAgregarFormaPago() {
		setTitle("Agregar forma de pago");
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
		
		JLabel lblAgregarFormaPago = new JLabel("Agregar forma de pago");
		lblAgregarFormaPago.setForeground(Color.WHITE);
		lblAgregarFormaPago.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblAgregarFormaPago.setBounds(27, 0, 390, 53);
		panel.add(lblAgregarFormaPago);
		
		JLabel lblNewLabel = new JLabel("Nombre de la forma de pago:");
		lblNewLabel.setBounds(34, 111, 188, 14);
		contentPane.add(lblNewLabel);
		
		txtTipoPago = new JTextField();
		txtTipoPago.setBounds(206, 108, 170, 20);
		contentPane.add(txtTipoPago);
		txtTipoPago.setColumns(10);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCancelar.setBackground(new Color(192, 57, 43));
		btnCancelar.setBounds(206, 177, 131, 42);
		contentPane.add(btnCancelar);
		
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
	
	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JTextField getTxtTipoPago() {
		return txtTipoPago;
	}
	
	public void limpiarCampos(){
		this.txtTipoPago.setText(null);
	}
}