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

public class VentanaLogin extends JFrame {

	private JPanel contentPane;
	private static VentanaLogin INSTANCE;
	private JTextField textUsuario;
	private JButton btnLogin;
	private JPasswordField passwordField;
	private JLabel lblError;
	
	public static VentanaLogin getInstance(){
		if(INSTANCE == null)
			return new VentanaLogin();
		else
			return INSTANCE;
	}
	
	private VentanaLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 283, 333);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textUsuario = new JTextField();
		textUsuario.setBounds(30, 67, 189, 20);
		contentPane.add(textUsuario);
		textUsuario.setColumns(10);
		
		JLabel lblUsuario = new JLabel("User Name");
		lblUsuario.setBounds(30, 42, 189, 14);
		contentPane.add(lblUsuario);
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnLogin.setBounds(16, 227, 229, 23);
		contentPane.add(btnLogin);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(30, 128, 189, 14);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(30, 153, 189, 20);
		contentPane.add(passwordField);
		
		lblError = new JLabel("El usuario o contraseña es incorrecto");
		lblError.setFont(new Font("Source Code Pro Semibold", Font.PLAIN, 9));
		lblError.setForeground(Color.RED);
		lblError.setBounds(30, 184, 189, 14);
		contentPane.add(lblError);
		lblError.setVisible(false);
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

	public JButton getBtnLogin() {
		return btnLogin;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(JPasswordField passwordField) {
		this.passwordField = passwordField;
	}

	public JLabel getLblError() {
		return lblError;
	}

	public void setLblError(JLabel lblError) {
		this.lblError = lblError;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaLogin frame = new VentanaLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
