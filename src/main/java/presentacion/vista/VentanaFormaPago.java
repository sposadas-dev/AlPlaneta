package presentacion.vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;

import java.awt.Font;

public class VentanaFormaPago extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaFormaPago frame = new VentanaFormaPago();
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
	public VentanaFormaPago() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 492, 308);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JRadioButton rdbtnEfectivo = new JRadioButton("Efectivo");
		rdbtnEfectivo.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdbtnEfectivo.setBounds(172, 136, 109, 37);
		contentPane.add(rdbtnEfectivo);
		
	
		JRadioButton rdbtnTarjetaDeCrditodbito = new JRadioButton("Tarjeta de crédito");
		rdbtnTarjetaDeCrditodbito.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdbtnTarjetaDeCrditodbito.setBounds(295, 129, 175, 51);
		contentPane.add(rdbtnTarjetaDeCrditodbito);
		
		JRadioButton rdbtnPendiente = new JRadioButton("Pendiente");
		rdbtnPendiente.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdbtnPendiente.setBounds(23, 143, 109, 23);
		contentPane.add(rdbtnPendiente);
	}
}
