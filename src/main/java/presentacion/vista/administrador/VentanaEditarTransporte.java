package presentacion.vista.administrador;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

public class VentanaEditarTransporte extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombreTransporte;
	private JButton btnEditar;
	private JButton btnCancelar;

	private static VentanaEditarTransporte INSTANCE;

	public void setTxtNombreTransporte(JTextField txtNombreTransporte) {
		this.txtNombreTransporte = txtNombreTransporte;
	}

	public static VentanaEditarTransporte getInstance(){
		if(INSTANCE == null)
			return new VentanaEditarTransporte();
		else
			return INSTANCE;
	}
	
	public VentanaEditarTransporte() {
		setTitle("Editar transporte");
		setResizable(false);;
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
		
		JLabel lblAgregarTransporte = new JLabel("Editar transporte");
		lblAgregarTransporte.setForeground(Color.WHITE);
		lblAgregarTransporte.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblAgregarTransporte.setBounds(27, 0, 253, 53);
		panel.add(lblAgregarTransporte);
		
		JLabel lblNewLabel = new JLabel("Nombre del transporte:");
		lblNewLabel.setBounds(47, 111, 159, 14);
		contentPane.add(lblNewLabel);
		
		txtNombreTransporte = new JTextField();
		txtNombreTransporte.setBounds(216, 108, 170, 20);
		contentPane.add(txtNombreTransporte);
		txtNombreTransporte.setColumns(10);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCancelar.setBackground(new Color(192, 57, 43));
		btnCancelar.setBounds(206, 177, 131, 42);
		contentPane.add(btnCancelar);
		
		btnEditar = new JButton("Editar");
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnEditar.setBackground(new Color(5, 196, 107));
		btnEditar.setBounds(47, 177, 131, 42);
		contentPane.add(btnEditar);
	}
	
	public void mostrarVentana(){
		this.setVisible(true);
	}

	public void cerrarVentana(){
		this.setVisible(false);
	}
	
	public JButton getBtnCancelar() {
		return btnCancelar;
	}
	
	public JButton getBtnEditar() {
		return btnEditar;
	}

	public JTextField getTxtNombreTransporte() {
		return txtNombreTransporte;
	}
	
	public void limpiarCampos(){
		this.txtNombreTransporte.setText(null);
	}
}