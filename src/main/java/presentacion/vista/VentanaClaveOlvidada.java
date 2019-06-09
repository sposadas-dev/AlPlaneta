package presentacion.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaClaveOlvidada extends JFrame {

	private JPanel contentPane;
	private static VentanaClaveOlvidada INSTANCE;
	private JTextField textUsuario;
	private JButton btnRecuperarContraseña;
	
	public static VentanaClaveOlvidada getInstance(){
		if(INSTANCE == null)
			return new VentanaClaveOlvidada();
		else
			return INSTANCE;
	}
	
	public VentanaClaveOlvidada() {
		setTitle("Recuperar contraseña");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 283, 192);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textUsuario = new JTextField();
		textUsuario.setBounds(30, 67, 189, 20);
		contentPane.add(textUsuario);
		textUsuario.setColumns(10);
		
		JLabel lblMail = new JLabel("Mail:");
		lblMail.setBounds(30, 42, 189, 14);
		contentPane.add(lblMail);
		
		btnRecuperarContraseña = new JButton("Recuperar contraseña");
		btnRecuperarContraseña.setBounds(30, 114, 209, 23);
		contentPane.add(btnRecuperarContraseña);
		this.setVisible(false);
	}
	
	
	public void mostrarVentana(boolean mostrar){
		this.setVisible(mostrar);
	}
	
	public JTextField getTextUsuario() {
		return textUsuario;
	}

	public void setTextUsuario(JTextField textUsuario) {
		this.textUsuario = textUsuario;
	}

	public JButton getBtnRecuperarContrasena() {
		return btnRecuperarContraseña;
	}
}