package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JSeparator;

public class VentanaCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtDni;
	private JDateChooser dateFechaNacimiento; 
	private JTextField txtTelefonoFijo;
	private JTextField txtTelefonoCelular;
	private JTextField txtEmail;
	private JButton btnRegistrar;
	private JButton btnCancelar;

	private static VentanaCliente ventanaCliente;
	
	public static VentanaCliente getInstance(){
		if(ventanaCliente == null){	
			ventanaCliente = new VentanaCliente();
			return ventanaCliente;
		}else{
			return ventanaCliente;
		}
	}
	
	public VentanaCliente() {
		setResizable(false);
		setTitle("Registrar cliente");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 150, 560, 435);
		
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelCliente = new JPanel();
		panelCliente.setBackground(new Color(39, 174, 96));
		panelCliente.setBounds(0, 0, 554, 53);
		contentPane.add(panelCliente);
		panelCliente.setLayout(null);
		
		JLabel lblRegistrarCliente = new JLabel("Registrar cliente");
		lblRegistrarCliente.setForeground(Color.WHITE);
		lblRegistrarCliente.setBounds(27, 0, 214, 53);
		panelCliente.add(lblRegistrarCliente);
		lblRegistrarCliente.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(37, 92, 72, 14);
		contentPane.add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(288, 92, 72, 14);
		contentPane.add(lblApellido);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(35, 149, 87, 14);
		contentPane.add(lblDni);
		
		JLabel lblFechaNacimiento = new JLabel("Fecha de nacimiento:");
		lblFechaNacimiento.setBounds(286, 149, 155, 14);
		contentPane.add(lblFechaNacimiento);
		
		JLabel lblTelefonoFijo = new JLabel("Teléfono fijo:");
		lblTelefonoFijo.setBounds(35, 204, 72, 31);
		contentPane.add(lblTelefonoFijo);
		
		JLabel lblTelefonoCelular = new JLabel("Teléfono celular:");
		lblTelefonoCelular.setBounds(286, 212, 100, 14);
		contentPane.add(lblTelefonoCelular);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(37, 274, 72, 14);
		contentPane.add(lblEmail);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(97, 89, 131, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(342, 89, 155, 20);
		contentPane.add(txtApellido);
		txtApellido.setColumns(10);
		
		txtDni = new JTextField();
		txtDni.setBounds(97, 146, 131, 20);
		contentPane.add(txtDni);
		txtDni.setColumns(10);
		
		dateFechaNacimiento = new JDateChooser();
		dateFechaNacimiento.setBounds(413, 146, 131, 20);
		JTextFieldDateEditor editor = (JTextFieldDateEditor) dateFechaNacimiento.getDateEditor();
		editor.setEditable(false);
		contentPane.add(dateFechaNacimiento);
		
		txtTelefonoCelular = new JTextField();
		txtTelefonoCelular.setBounds(398, 209, 131, 20);
		contentPane.add(txtTelefonoCelular);
		txtTelefonoCelular.setColumns(10);
		
		txtTelefonoFijo = new JTextField();
		txtTelefonoFijo.setBounds(119, 209, 131, 20);
		contentPane.add(txtTelefonoFijo);
		txtTelefonoFijo.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(97, 271, 184, 20);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
		JSeparator separadorCliente = new JSeparator();
		separadorCliente.setBounds(37, 316, 490, 2);
		contentPane.add(separadorCliente);
		
		btnRegistrar = new JButton("Registrar");
		btnRegistrar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnRegistrar.setBackground(new Color(52, 152, 219));
		btnRegistrar.setForeground(Color.WHITE);
		btnRegistrar.setBounds(127, 342, 131, 42);
		contentPane.add(btnRegistrar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setBackground(new Color(192, 57, 43));
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCancelar.setBounds(290, 342, 131, 42);
		contentPane.add(btnCancelar);			
	}
	
	public void mostrarVentana()
	{
		this.setVisible(true);
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
	
	public JDateChooser getDateFechaNacimiento() {
		return dateFechaNacimiento;
	}
	
	public JTextField getTxtTelefonoFijo() {
		return txtTelefonoFijo;
	}
	
	public JTextField getTxtTelefonoCelular() {
		return txtTelefonoCelular;
	}
	
	public JTextField getTxtEmail() {
		return txtEmail;
	}
	
	public JButton getBtnRegistrar() {
		return btnRegistrar;
	}
	
	public JButton getBtnCancelar() {
		return btnCancelar;
	}
	
	public void cerrarVentana(){
		this.dispose();
	}
	
	public void limpiarCampos(){
		this.txtNombre.setText(null);
		this.txtApellido.setText(null);
		this.txtDni.setText(null);
		this.dateFechaNacimiento.setDate(null);
		this.txtTelefonoFijo.setText(null);
		this.txtTelefonoCelular.setText(null);
		this.txtEmail.setText(null);

	}
}