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
import javax.swing.SwingConstants;
import javax.swing.JEditorPane;

public class VentanaCancelacionPasaje extends JFrame {

	private JPanel contentPane;
	private JButton btnAceptar;
	private JEditorPane txtMotivoCancelacion;

	private static VentanaCancelacionPasaje INSTANCE;
	
	public static VentanaCancelacionPasaje getInstance(){
		if(INSTANCE == null)
			return new VentanaCancelacionPasaje();
		else
			return INSTANCE;
	}
	
	public VentanaCancelacionPasaje() {
		setTitle("Cancelación del pasaje");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 520, 361);
		setLocationRelativeTo(null); 
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMotivoDeLa = new JLabel("Motivo de la cancelación (opcional)");
		lblMotivoDeLa.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMotivoDeLa.setBounds(10, 77, 249, 14);
		contentPane.add(lblMotivoDeLa);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(176, 252, 130, 55);
		contentPane.add(btnAceptar);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(128, 128, 128));
		panel.setBounds(0, 0, 514, 53);
		contentPane.add(panel);
		
		JLabel lblCancelacion = new JLabel("Cancelación del pasaje");
		lblCancelacion.setForeground(Color.WHITE);
		lblCancelacion.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblCancelacion.setBounds(110, 0, 301, 53);
		panel.add(lblCancelacion);
		
		txtMotivoCancelacion = new JEditorPane();
		txtMotivoCancelacion.setBounds(10, 94, 492, 147);
		contentPane.add(txtMotivoCancelacion);
	}


	public JEditorPane getTxtMotivoCancelacion() {
		return txtMotivoCancelacion;
	}

	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	public void mostrarVentana(boolean visibilidad){
		this.setVisible(visibilidad);
	}
}