package presentacion.vista.administrador;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class VentanaEditarCiudad extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombreCiudad;
	private JButton btnEditar;
	private JButton btnCancelar;
	private static VentanaEditarCiudad INSTANCE;

	public void setTxtNombreCiudad(JTextField txtNombreCiudad) {
		this.txtNombreCiudad = txtNombreCiudad;
	}

	public static VentanaEditarCiudad getInstance(){
		if(INSTANCE == null)
			return new VentanaEditarCiudad();
		else
			return INSTANCE;
	}
	
	public VentanaEditarCiudad() {
		setTitle("Editar ciudad");
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
		
		JLabel lblAgregarCiudad = new JLabel("Editar ciudad");
		lblAgregarCiudad.setForeground(Color.WHITE);
		lblAgregarCiudad.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblAgregarCiudad.setBounds(27, 0, 253, 53);
		panel.add(lblAgregarCiudad);
		
		JLabel lblNewLabel = new JLabel("Nombre de la ciudad:");
		lblNewLabel.setBounds(47, 111, 131, 14);
		contentPane.add(lblNewLabel);
		
		txtNombreCiudad = new JTextField();
		txtNombreCiudad.setBounds(181, 108, 170, 20);
		contentPane.add(txtNombreCiudad);
		txtNombreCiudad.setColumns(10);
		
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
	
	public JButton getBtnCancelar() {
		return btnCancelar;
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

	public JTextField getTxtNombreCiudad() {
		return txtNombreCiudad;
	}
	
	public void limpiarCampos(){
		this.txtNombreCiudad.setText(null);
	}
}