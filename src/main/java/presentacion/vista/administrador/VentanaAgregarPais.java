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

public class VentanaAgregarPais extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombrePais;
	private JButton btnAgregar;
	private JButton btnCancelar;
	private static VentanaAgregarPais INSTANCE;
	private VentanaPanelGeneral panelGeneral;


	public static VentanaAgregarPais getInstance(){
		if(INSTANCE == null)
			return new VentanaAgregarPais();
		else
			return INSTANCE;
	}
	
	public VentanaAgregarPais() {
		setTitle("Agregar Pais");
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
		
		JLabel lblAgregarTransporte = new JLabel("Agregar Pais");
		lblAgregarTransporte.setForeground(Color.WHITE);
		lblAgregarTransporte.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblAgregarTransporte.setBounds(27, 0, 253, 53);
		panel.add(lblAgregarTransporte);
		
		JLabel lblNewLabel = new JLabel("Nombre del Pais:");
		lblNewLabel.setBounds(47, 111, 131, 14);
		contentPane.add(lblNewLabel);
		
		txtNombrePais = new JTextField();
		txtNombrePais.setBounds(193, 108, 170, 20);
		contentPane.add(txtNombrePais);
		txtNombrePais.setColumns(10);
		
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
	
	public JButton getBtnCancelar() {
		return btnCancelar;
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

	public JTextField getTxtNombrePais() {
		return txtNombrePais;
	}
	
	public void limpiarCampos(){
		this.txtNombrePais.setText(null);
	}
}