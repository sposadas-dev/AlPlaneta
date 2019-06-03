package presentacion.vista.administrativo;

import java.awt.EventQueue;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import javax.swing.JComboBox;

public class VentanaPasajero extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtDni;
	private JTextField txtApellido;
	private JTextField txtTelefono;
	private JDateChooser dateFechaNacimiento;
	private JTextField txtEmail;
	private JButton btnCargarDatos;
	private JTextField txtFiltroDni;
	private JButton btnAplicarBusqueda;
	private static VentanaPasajero INSTANCE;
	
	public static VentanaPasajero getInstance(){
		if(INSTANCE == null)
			return new VentanaPasajero();
		else
			return INSTANCE;
	}
	
	private VentanaPasajero() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500, 200, 561, 450);
		setResizable(false);
		setLocationRelativeTo(null); 
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(SystemColor.controlHighlight);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelCargarPasajeros = new JPanel();
		panelCargarPasajeros.setBackground(new Color(9, 132, 227));
		panelCargarPasajeros.setBounds(0, 0, 555, 53);
		contentPane.add(panelCargarPasajeros);
		panelCargarPasajeros.setLayout(null);
		
		JLabel lblCargarPasajero = new JLabel("Cargar pasajero");
		lblCargarPasajero.setBounds(162, 0, 228, 53);
		panelCargarPasajeros.add(lblCargarPasajero);
		lblCargarPasajero.setForeground(Color.WHITE);
		lblCargarPasajero.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(39, 147, 63, 14);
		contentPane.add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(273, 147, 63, 14);
		contentPane.add(lblApellido);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(39, 208, 46, 14);
		contentPane.add(lblDni);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(95, 144, 131, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(347, 144, 131, 20);
		contentPane.add(txtApellido);
		txtApellido.setColumns(10);
		
		txtDni = new JTextField();
		txtDni.setBounds(95, 205, 131, 20);
		contentPane.add(txtDni);
		txtDni.setColumns(10);
		
		btnCargarDatos = new JButton("Cargar datos");
		btnCargarDatos.setForeground(Color.WHITE);
		btnCargarDatos.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCargarDatos.setBackground(new Color(76, 209, 55));
		btnCargarDatos.setBounds(203, 357, 166, 53);
		contentPane.add(btnCargarDatos);
		
		JLabel lblTelfono = new JLabel("Teléfono:");
		lblTelfono.setBounds(39, 277, 79, 14);
		contentPane.add(lblTelfono);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(95, 274, 131, 20);
		contentPane.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		JLabel lblMail = new JLabel("Email:");
		lblMail.setBounds(278, 277, 58, 14);
		contentPane.add(lblMail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(347, 274, 131, 20);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblFechaNacimiento = new JLabel("Fecha de nacimiento:");
		lblFechaNacimiento.setBounds(273, 208, 123, 14);
		contentPane.add(lblFechaNacimiento);
		
		dateFechaNacimiento = new JDateChooser();
		dateFechaNacimiento.setBounds(399, 202, 135, 20);
		JTextFieldDateEditor editor = (JTextFieldDateEditor) dateFechaNacimiento.getDateEditor();
		editor.setEditable(false);
		contentPane.add(dateFechaNacimiento);
		
		JLabel lblFIltro = new JLabel("Ingrese DNI:");
		lblFIltro.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFIltro.setBounds(79, 89, 103, 14);
		contentPane.add(lblFIltro);
		
		txtFiltroDni = new JTextField();
		txtFiltroDni.setBounds(192, 86, 123, 20);
		contentPane.add(txtFiltroDni);
		txtFiltroDni.setColumns(10);
		
		btnAplicarBusqueda = new JButton("Aplicar");
		btnAplicarBusqueda.setBounds(338, 85, 89, 23);
		contentPane.add(btnAplicarBusqueda);
	}
	
	
	public JTextField getTxtNombre() {
		return txtNombre;
	}


	public void setTxtNombre(JTextField txtNombre) {
		this.txtNombre = txtNombre;
	}


	public JTextField getTxtDni() {
		return txtDni;
	}


	public void setTxtDni(JTextField txtDni) {
		this.txtDni = txtDni;
	}


	public JTextField getTxtApellido() {
		return txtApellido;
	}


	public JDateChooser getDateFechaNacimiento() {
		return dateFechaNacimiento;
	}

	public void setTxtApellido(JTextField txtApellido) {
		this.txtApellido = txtApellido;
	}


	public JButton getBtnCargarDatos() {
		return btnCargarDatos;
	}

	public JTextField getTxtTelefono() {
		return txtTelefono;
	}

	public void setTxtTelefono(JTextField txtTelefono) {
		this.txtTelefono = txtTelefono;
	}

	public JTextField getTxtEmail() {
		return txtEmail;
	}

	public void setTxtEmail(JTextField txtEmail) {
		this.txtEmail = txtEmail;
	}

	public void setBtnCargarDatos(JButton btnCargarDatos) {
		this.btnCargarDatos = btnCargarDatos;
	}
	
	public JTextField getTxtFiltroDni() {
		return txtFiltroDni;
	}

	public JButton getBtnAplicarBusqueda() {
		return btnAplicarBusqueda;
	}
	
	public void limpiarCampos(){
		this.txtNombre.setText("");
		this.txtApellido.setText("");
		this.txtDni.setText("");
		this.dateFechaNacimiento.setDate(null);
		this.txtTelefono.setText("");
		this.txtEmail.setText("");
		this.txtFiltroDni.setText("");
	}
}