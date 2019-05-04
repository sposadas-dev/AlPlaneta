package presentacion.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class VistaRegistrarPago {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VistaRegistrarPago window = new VistaRegistrarPago();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VistaRegistrarPago() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnEfectivo = new JButton("Efectivo");
		btnEfectivo.setBounds(29, 70, 89, 23);
		frame.getContentPane().add(btnEfectivo);
		
		JButton btnNewButton = new JButton("Tarjeta");
		btnNewButton.setBounds(146, 70, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblDni = new JLabel("DNI");
		lblDni.setBounds(29, 11, 46, 14);
		frame.getContentPane().add(lblDni);
		
		textField = new JTextField();
		textField.setBounds(118, 8, 178, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
	}

}
