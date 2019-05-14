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

public class VentanaPasajero extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtDni;
	private JTextField txtApellido;
	private static VentanaPasajero INSTANCE;
	private JButton btnCargarDatos;
	private JTextField txtTelefono;
	private JTextField txtEmail;

	public static VentanaPasajero getInstance(){
		if(INSTANCE == null)
			return new VentanaPasajero();
		else
			return INSTANCE;
	}
	
	private VentanaPasajero() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); 
		setBounds(500, 200, 366, 463);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(SystemColor.controlHighlight);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelCargarPasajeros = new JPanel();
		panelCargarPasajeros.setBackground(new Color(9, 132, 227));
		panelCargarPasajeros.setBounds(0, 0, 350, 53);
		contentPane.add(panelCargarPasajeros);
		panelCargarPasajeros.setLayout(null);
		
		JLabel lblCargarPasajero = new JLabel("Cargar pasajero");
		lblCargarPasajero.setBounds(70, 0, 210, 53);
		panelCargarPasajeros.add(lblCargarPasajero);
		lblCargarPasajero.setForeground(Color.WHITE);
		lblCargarPasajero.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(57, 92, 63, 14);
		contentPane.add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(57, 140, 63, 14);
		contentPane.add(lblApellido);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(57, 187, 46, 14);
		contentPane.add(lblDni);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(130, 89, 131, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(130, 137, 131, 20);
		contentPane.add(txtApellido);
		txtApellido.setColumns(10);
		
		txtDni = new JTextField();
		txtDni.setBounds(130, 184, 131, 20);
		contentPane.add(txtDni);
		txtDni.setColumns(10);
		
		btnCargarDatos = new JButton("Cargar datos");
		btnCargarDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnCargarDatos.setForeground(Color.WHITE);
		btnCargarDatos.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCargarDatos.setBackground(new Color(76, 209, 55));
		btnCargarDatos.setBounds(112, 371, 131, 42);
		contentPane.add(btnCargarDatos);
		
		JLabel lblTelfono = new JLabel("Teléfono:");
		lblTelfono.setBounds(57, 267, 46, 14);
		contentPane.add(lblTelfono);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(130, 264, 131, 20);
		contentPane.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		JLabel lblMail = new JLabel("Email:");
		lblMail.setBounds(57, 313, 46, 14);
		contentPane.add(lblMail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(130, 310, 131, 20);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblFechaNacimiento = new JLabel("Fecha de nacimiento:");
		lblFechaNacimiento.setBounds(57, 228, 123, 14);
		contentPane.add(lblFechaNacimiento);
		
		JDateChooser dateFechaNacimiento = new JDateChooser();
		dateFechaNacimiento.setBounds(171, 225, 135, 20);
		contentPane.add(dateFechaNacimiento);
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


	public void setTxtApellido(JTextField txtApellido) {
		this.txtApellido = txtApellido;
	}


	public JButton getBtnCargarDatos() {
		return btnCargarDatos;
	}


	public void setBtnCargarDatos(JButton btnCargarDatos) {
		this.btnCargarDatos = btnCargarDatos;
	}


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPasajero frame = new VentanaPasajero();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}