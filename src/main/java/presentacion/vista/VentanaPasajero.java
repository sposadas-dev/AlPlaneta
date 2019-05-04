package presentacion.vista;

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

public class VentanaPasajero extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtDni;
	private JTextField txtApellido;
	private static VentanaPasajero INSTANCE;
	private JButton btnCargarDatos;

	public static VentanaPasajero getInstance(){
		if(INSTANCE == null)
			return new VentanaPasajero();
		else
			return INSTANCE;
	}
	

	private VentanaPasajero() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 200, 366, 389);
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
		lblNombre.setBounds(57, 113, 63, 14);
		contentPane.add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(57, 161, 63, 14);
		contentPane.add(lblApellido);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(57, 218, 46, 14);
		contentPane.add(lblDni);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(149, 110, 131, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(149, 158, 131, 20);
		contentPane.add(txtApellido);
		txtApellido.setColumns(10);
		
		txtDni = new JTextField();
		txtDni.setBounds(149, 215, 131, 20);
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
		btnCargarDatos.setBounds(110, 273, 131, 42);
		contentPane.add(btnCargarDatos);
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