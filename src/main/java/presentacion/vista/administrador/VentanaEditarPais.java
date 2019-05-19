package presentacion.vista.administrador;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

public class VentanaEditarPais extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombrePais;
	private JButton btnEditar;
	private static VentanaEditarPais INSTANCE;

	public void setTxtNombrePais(JTextField txtNombrePais) {
		this.txtNombrePais = txtNombrePais;
	}

	public static VentanaEditarPais getInstance(){
		if(INSTANCE == null)
			return new VentanaEditarPais();
		else
			return INSTANCE;
	}
	
	public VentanaEditarPais() {
		setTitle("Editar Pais");
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
		panel.setBounds(0, 0, 407, 53);
		contentPane.add(panel);
		
		JLabel lblAgregarPais = new JLabel("Editar Pais");
		lblAgregarPais.setForeground(Color.WHITE);
		lblAgregarPais.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblAgregarPais.setBounds(27, 0, 253, 53);
		panel.add(lblAgregarPais);
		
		JLabel lblNewLabel = new JLabel("Nombre del Pais:");
		lblNewLabel.setBounds(47, 111, 131, 14);
		contentPane.add(lblNewLabel);
		
		txtNombrePais = new JTextField();
		txtNombrePais.setBounds(181, 108, 170, 20);
		contentPane.add(txtNombrePais);
		txtNombrePais.setColumns(10);
		
		JButton button = new JButton("Cancelar");
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Tahoma", Font.BOLD, 14));
		button.setBackground(new Color(192, 57, 43));
		button.setBounds(206, 177, 131, 42);
		contentPane.add(button);
		
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
	
	public JButton getBtnEditar() {
		return btnEditar;
	}

	public JTextField getTxtNombrePais() {
		return txtNombrePais;
	}
	
	public void limpiarCampos(){
		this.txtNombrePais.setText(null);
	}
}