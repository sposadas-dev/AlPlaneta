package presentacion.vista.administrador;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dto.LocalDTO;
import dto.RolDTO;

public class VentanaAgregarEmpleado extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtUsuario;
	private JComboBox<RolDTO> comboBoxRoles;
	private JComboBox<LocalDTO> comboBoxLocales;
	private JButton btnRegistrar;
	private JButton btnCancelar;
	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	private static VentanaAgregarEmpleado INSTANCE;
	private JTextField textMail;
	private JTextField txtDni;
	
	public static VentanaAgregarEmpleado getInstance(){
		if(INSTANCE == null)
			return new VentanaAgregarEmpleado ();
		else
			return INSTANCE;
	}

	public JTextField getTextMail() {
		return textMail;
	}

	public void setTextMail(JTextField textMail) {
		this.textMail = textMail;
	}

	public VentanaAgregarEmpleado() {
		setTitle("Registrar empleado");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 415, 432);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(90, 96, 74, 14);
		contentPane.add(lblApellido);
//		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(90, 72, 87, 14);
		contentPane.add(lblNombre);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(90, 193, 74, 14);
		contentPane.add(lblUsuario);
		
		JLabel lblRol = new JLabel("Rol:");
		lblRol.setBounds(90, 287, 74, 14);
		contentPane.add(lblRol);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(189, 70, 122, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		comboBoxRoles = new JComboBox<RolDTO>();
		comboBoxRoles.setBounds(189, 290, 122, 20);
		contentPane.add(comboBoxRoles);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(189, 94, 122, 20);
		contentPane.add(txtApellido);
		txtApellido.setColumns(10);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(189, 191, 122, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		btnRegistrar = new JButton("Registrar");
		btnRegistrar.setForeground(Color.WHITE);
		btnRegistrar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnRegistrar.setBackground(new Color(5, 196, 107));
		btnRegistrar.setBounds(137, 336, 131, 42);
		contentPane.add(btnRegistrar);
		
		JPanel panelRegistrarEmpleado = new JPanel();
		panelRegistrarEmpleado.setLayout(null);
		panelRegistrarEmpleado.setBackground(new Color(87, 95, 207));
		panelRegistrarEmpleado.setBounds(0, 0, 414, 53);
		contentPane.add(panelRegistrarEmpleado);
		
		JLabel lblRegistrarEmpleado = new JLabel("Registrar empleado");
		lblRegistrarEmpleado.setForeground(Color.WHITE);
		lblRegistrarEmpleado.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblRegistrarEmpleado.setBounds(27, 0, 253, 53);
		panelRegistrarEmpleado.add(lblRegistrarEmpleado);
		
		textMail = new JTextField();
		textMail.setColumns(10);
		textMail.setBounds(189, 141, 122, 20);
		contentPane.add(textMail);
		
		JLabel lblMail = new JLabel("Mail :");
		lblMail.setBounds(90, 143, 74, 14);
		contentPane.add(lblMail);
		
		JLabel lblLocales = new JLabel("Locales:");
		lblLocales.setBounds(90, 264, 46, 13);
		contentPane.add(lblLocales);
		
		comboBoxLocales = new JComboBox<LocalDTO>();
		comboBoxLocales.setBounds(189, 259, 122, 21);
		contentPane.add(comboBoxLocales);
		
		JLabel lblDni = new JLabel("Dni:");
		lblDni.setBounds(90, 120, 46, 13);
		contentPane.add(lblDni);
		
		txtDni = new JTextField();
		txtDni.setBounds(189, 117, 122, 19);
		contentPane.add(txtDni);
		txtDni.setColumns(10);
	}
	
	public JTextField getTxtDni() {
		return txtDni;
	}
	
	public JButton getBtnRegistrar() {
		return btnRegistrar;
	}
	
	public JTextField getTxtNombre() {
		return txtNombre;
	}

	public JTextField getTxtApellido() {
		return txtApellido;
	}

	public JTextField getTxtUsuario() {
		return txtUsuario;
	}

	public JComboBox<RolDTO> getComboBoxRoles() {
		return comboBoxRoles;
	}
	
	public JComboBox<LocalDTO> getComboBoxLocales(){
		return comboBoxLocales;
	}

	public void mostrarVentana(boolean visibilidad){
		this.setVisible(visibilidad);
	}

	public void limpiarCampos() {
		this.txtNombre.setText("");
		this.txtApellido.setText("");
		this.txtDni.setText("");
		this.txtUsuario.setText("");
		this.textMail.setText("");
	}
}
