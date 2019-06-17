package presentacion.vista.administrador;

	
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

import dto.RolDTO;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JPasswordField;

public class VentanaEditarCuenta extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtUsuario;
	private JTextField txtMail;
	private JComboBox<RolDTO> comboBoxRoles;
	private JButton btnRegistrar;
	private JButton btnCancelar;
	private static VentanaEditarCuenta INSTANCE;
	private JPasswordField txtContrasena;
	private JLabel lblDni;
	private JTextField txtDni;
	
	public static VentanaEditarCuenta getInstance(){
		if(INSTANCE == null)
			return new VentanaEditarCuenta ();
		else
			return INSTANCE;
	}

	public VentanaEditarCuenta() {
		setTitle("Editar Cuenta");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 420, 460);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(92, 99, 74, 14);
		contentPane.add(lblApellido);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(92, 75, 87, 14);
		contentPane.add(lblNombre);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(92, 198, 74, 14);
		contentPane.add(lblUsuario);
		
		JLabel lblContrasenia = new JLabel("Contraseña:");
		lblContrasenia.setBounds(92, 222, 87, 14);
		contentPane.add(lblContrasenia);
		
		JLabel lblRol = new JLabel("Rol:");
		lblRol.setBounds(92, 290, 74, 14);
		contentPane.add(lblRol);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(189, 73, 122, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		comboBoxRoles = new JComboBox<RolDTO>();
		comboBoxRoles.setBounds(189, 287, 122, 20);
		contentPane.add(comboBoxRoles);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(189, 97, 122, 20);
		contentPane.add(txtApellido);
		txtApellido.setColumns(10);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(189, 196, 122, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);

		txtContrasena = new JPasswordField();
		txtContrasena.setBounds(189, 220, 122, 19);
		contentPane.add(txtContrasena);
		
		btnRegistrar = new JButton("Registrar");
		btnRegistrar.setForeground(Color.WHITE);
		btnRegistrar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnRegistrar.setBackground(new Color(5, 196, 107));
		btnRegistrar.setBounds(58, 338, 131, 42);
		contentPane.add(btnRegistrar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCancelar.setBackground(new Color(192, 57, 43));
		btnCancelar.setBounds(216, 338, 131, 42);
		contentPane.add(btnCancelar);
		
		JPanel panelRegistrarEmpleado = new JPanel();
		panelRegistrarEmpleado.setLayout(null);
		panelRegistrarEmpleado.setBackground(new Color(87, 95, 207));
		panelRegistrarEmpleado.setBounds(0, 0, 414, 53);
		contentPane.add(panelRegistrarEmpleado);
		
		JLabel lblRegistrarEmpleado = new JLabel("Editar empleado");
		lblRegistrarEmpleado.setForeground(Color.WHITE);
		lblRegistrarEmpleado.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblRegistrarEmpleado.setBounds(27, 0, 253, 53);
		panelRegistrarEmpleado.add(lblRegistrarEmpleado);
		
		txtMail = new JTextField();
		txtMail.setBounds(189, 143, 122, 19);
		contentPane.add(txtMail);
		txtMail.setColumns(10);
		
		JLabel lblMail = new JLabel("Mail:");
		lblMail.setBounds(92, 146, 46, 13);
		contentPane.add(lblMail);
		
		lblDni = new JLabel("Dni:");
		lblDni.setBounds(92, 123, 46, 13);
		contentPane.add(lblDni);
		
		txtDni = new JTextField();
		txtDni.setBounds(189, 120, 122, 19);
		contentPane.add(txtDni);
		txtDni.setColumns(10);
		
	}
	
	public JButton getBtnCancelar() {
		return btnCancelar;
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
	
	public JTextField getTxtDni() {
		return txtDni;
	}

	public JTextField getTxtMail() {
		return txtMail;
	}

	public JTextField getTxtUsuario() {
		return txtUsuario;
	}

	public JPasswordField getTxtContrasena() {
		return txtContrasena;
	}

	public JComboBox<RolDTO> getComboBoxRoles() {
		return comboBoxRoles;
	}

	public void mostrarVentana(boolean visibilidad){
		this.setVisible(visibilidad);
	}

	public void limpiarCampos() {
		this.txtNombre.setText("");
		this.txtUsuario.setText("");
		this.txtDni.setText("");
		this.txtMail.setText("");
		this.txtApellido.setText("");
		this.txtContrasena.setText("");
	}
}
