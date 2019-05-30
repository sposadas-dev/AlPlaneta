package presentacion.vista.administrativo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class VentanaCancelacionPasaje extends JFrame {

	private JPanel contentPane;
	private JTextField txtCancelacionPasaje;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaCancelacionPasaje frame = new VentanaCancelacionPasaje();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaCancelacionPasaje() {
		setTitle("Cancelación del pasaje");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 520, 421);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMotivoDeLa = new JLabel("Motivo de la cancelación (opcional)");
		lblMotivoDeLa.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMotivoDeLa.setBounds(10, 77, 249, 14);
		contentPane.add(lblMotivoDeLa);
		
		txtCancelacionPasaje = new JTextField();
		txtCancelacionPasaje.setBounds(10, 102, 484, 182);
		contentPane.add(txtCancelacionPasaje);
		txtCancelacionPasaje.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(189, 295, 130, 55);
		contentPane.add(btnAceptar);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(128, 128, 128));
		panel.setBounds(0, 0, 504, 53);
		contentPane.add(panel);
		
		JLabel lblCancelacin = new JLabel("Cancelación del pasaje");
		lblCancelacin.setForeground(Color.WHITE);
		lblCancelacin.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblCancelacin.setBounds(110, 0, 301, 53);
		panel.add(lblCancelacin);
	}
}
