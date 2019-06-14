package presentacion.vista;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPasswordField;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JSeparator;

import presentacion.vista.cliente.VistaCliente;
import javax.swing.border.TitledBorder;
import java.awt.SystemColor;

public class VentanaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static VentanaLogin ventanaLogin;
	private JTextField textUsuario;
	private JButton btnLogin;
	private JPasswordField passwordField;
	private JLabel lblError;
	private JLabel lblErrorInactividad;
	private JLabel lblClaveOlvidada;
	private JLabel lblUser;

	
	public static VentanaLogin getInstance(){
		if (ventanaLogin == null) {
			ventanaLogin = new VentanaLogin();
			return ventanaLogin;
		} else {
			return ventanaLogin;
		}
	}
	
	public VentanaLogin() {
		setTitle("Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 420, 420);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textUsuario = new JTextField();
		textUsuario.setBounds(107, 175, 207, 20);
		contentPane.add(textUsuario);
		textUsuario.setColumns(10);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUsuario.setBounds(107, 144, 189, 14);
		contentPane.add(lblUsuario);
		
		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnLogin.setBackground(new Color(5, 196, 107));
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBounds(96, 294, 229, 40);
		contentPane.add(btnLogin);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassword.setBounds(107, 217, 189, 14);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(107, 242, 207, 20);
		contentPane.add(passwordField);
		
		lblError = new JLabel("El usuario o contraseña es incorrecto");
		lblError.setFont(new Font("Source Code Pro Semibold", Font.PLAIN, 9));
		lblError.setForeground(Color.RED);
		lblError.setBounds(117, 273, 189, 14);
		contentPane.add(lblError);
		
		lblClaveOlvidada = new JLabel("¿Olvidaste tu contraseña?");
		lblClaveOlvidada.setBounds(137, 339, 173, 20);
		lblClaveOlvidada.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblClaveOlvidada.setForeground(Color.BLUE);
		
		Font font = lblClaveOlvidada.getFont(); 
		Hashtable<TextAttribute, Object> map = new Hashtable<TextAttribute, Object>();
		map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		font = font.deriveFont(map);
		lblClaveOlvidada.setFont(font); //Subrayado del texto 
		contentPane.add(lblClaveOlvidada);
		lblError.setVisible(false);
		this.setVisible(false);

		lblErrorInactividad = new JLabel("El usuario está inactivo");
		lblErrorInactividad.setFont(new Font("Source Code Pro Semibold", Font.PLAIN, 9));
		lblErrorInactividad.setForeground(Color.RED);
		lblErrorInactividad.setBounds(127, 273, 189, 14);
		contentPane.add(lblErrorInactividad);
		lblErrorInactividad.setVisible(false);
		
		lblUser = new JLabel("");
		Image imgUser = new ImageIcon(this.getClass().getResource("/recursos/user.png")).getImage();
		lblUser.setIcon(new ImageIcon(imgUser.getScaledInstance(120, 120, 100)));
		lblUser.setBounds(141, 0, 148, 155);
		contentPane.add(lblUser);
		
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

	public JLabel getLblErrorInactividad() {
		return lblErrorInactividad;
	}
	
	public void setLblErrorInactividad(JLabel lblErrorInactividad) {
		this.lblErrorInactividad = lblErrorInactividad;
	}

	public JLabel getLblClaveOlvidada() {
		return lblClaveOlvidada;
	}

	public void setLblClaveOlvidada(JLabel lblClaveOlvidada) {
		this.lblClaveOlvidada = lblClaveOlvidada;
	}
}