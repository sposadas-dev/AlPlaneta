package presentacion.vista.contador;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.SystemColor;

public class VentanaAgregarServicio extends JFrame {

	private JPanel contentPane;
	private JTextField txtEmpleado;
	private static VentanaAgregarServicio ventanaAgregarSueldo;
	private JTextField textField;
	
	
	public static VentanaAgregarServicio getInstance(){
		if(ventanaAgregarSueldo == null){	
			ventanaAgregarSueldo = new VentanaAgregarServicio();
			return ventanaAgregarSueldo;
		}else{
			return ventanaAgregarSueldo;
		}
	}
	
	public VentanaAgregarServicio() {
		setTitle("Comprobantes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 522, 335);
		setLocationRelativeTo(null); 
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(0, 128, 0));
		panel.setBounds(0, 0, 654, 53);
		contentPane.add(panel);
		
		JLabel lblSueldos = new JLabel("Agregar servicio");
		lblSueldos.setForeground(Color.WHITE);
		lblSueldos.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblSueldos.setBounds(154, 0, 210, 53);
		panel.add(lblSueldos);
		Image imgPdfPago = new ImageIcon(this.getClass().getResource("/recursos/pdf.png")).getImage();
		Image imgPdfReserva = new ImageIcon(this.getClass().getResource("/recursos/pdf.png")).getImage();
		
		JLabel lblNombreServicio = new JLabel("Nombre del servicio:");
		lblNombreServicio.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNombreServicio.setBounds(51, 78, 121, 14);
		contentPane.add(lblNombreServicio);
		
		txtEmpleado = new JTextField();
		txtEmpleado.setBackground(Color.WHITE);
		txtEmpleado.setEditable(false);
		txtEmpleado.setBounds(208, 75, 185, 20);
		contentPane.add(txtEmpleado);
		txtEmpleado.setColumns(10);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(178, 232, 178, 48);
		contentPane.add(btnConfirmar);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(214, 126, 155, 20);
		contentPane.add(comboBox);
		
		JLabel lblMes = new JLabel("Mes:");
		lblMes.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMes.setBounds(53, 129, 53, 14);
		contentPane.add(lblMes);
		
		textField = new JTextField();
		textField.setBounds(210, 180, 159, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblMonto = new JLabel("Monto:");
		lblMonto.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMonto.setBounds(51, 183, 46, 14);
		contentPane.add(lblMonto);
	}

	


	public JTextField getTxtEmpleado() {
		return txtEmpleado;
	}

	public void mostrarVentana(boolean visibilidad) {
		this.setVisible(visibilidad);
	}
}
