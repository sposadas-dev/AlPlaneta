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

public class VentanaLogin extends JFrame {

	private JPanel contentPane;
	private static VentanaLogin INSTANCE;
	private JTextField textUsuario;
	private JButton btnLogin;
	private JTextField textPassword;
	
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
		
		textPassword = new JTextField();
		textPassword.setColumns(10);
		textPassword.setBounds(30, 153, 189, 20);
		contentPane.add(textPassword);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(30, 128, 189, 14);
		contentPane.add(lblPassword);
		
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

	public void setBtnLogin(JButton btnLogin) {
		this.btnLogin = btnLogin;
	}

	public JTextField getTextPassword() {
		return textPassword;
	}

	public void setTextPassword(JTextField textPassword) {
		this.textPassword = textPassword;
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
