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

public class VentanaPasajero extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtDni;
	private JTextField txtApellido;

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

	public VentanaPasajero() {
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
		
		JLabel lblCargarPasajero = new JLabel("Cargar pasajeros");
		lblCargarPasajero.setBounds(70, 0, 210, 53);
		panelCargarPasajeros.add(lblCargarPasajero);
		lblCargarPasajero.setForeground(Color.WHITE);
		lblCargarPasajero.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		JLabel lblPasajero = new JLabel("Pasajero");
		lblPasajero.setBackground(new Color(70, 130, 180));
		lblPasajero.setBounds(118, 64, 80, 33);
		contentPane.add(lblPasajero);
		lblPasajero.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JLabel lblNroPasajero = new JLabel("X");
		lblNroPasajero.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNroPasajero.setBounds(203, 64, 46, 33);
		contentPane.add(lblNroPasajero);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(57, 130, 63, 14);
		contentPane.add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(57, 179, 63, 14);
		contentPane.add(lblApellido);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(57, 232, 46, 14);
		contentPane.add(lblDni);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(149, 127, 131, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(149, 176, 131, 20);
		contentPane.add(txtApellido);
		txtApellido.setColumns(10);
		
		txtDni = new JTextField();
		txtDni.setBounds(149, 229, 131, 20);
		contentPane.add(txtDni);
		txtDni.setColumns(10);
		
		JButton btnCargarDatos = new JButton("Cargar datos");
		btnCargarDatos.setForeground(Color.WHITE);
		btnCargarDatos.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCargarDatos.setBackground(new Color(76, 209, 55));
		btnCargarDatos.setBounds(32, 282, 131, 42);
		contentPane.add(btnCargarDatos);
		
		JButton btnAtras = new JButton("Atrás");
		btnAtras.setForeground(Color.WHITE);
		btnAtras.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAtras.setBackground(new Color(192, 192, 192));
		btnAtras.setBounds(186, 282, 131, 42);
		contentPane.add(btnAtras);
	}
}