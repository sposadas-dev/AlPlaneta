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

public class VentanaAgregarSueldo extends JFrame {

	private JPanel contentPane;
	private JTextField txtEmpleado;
	private JTextField txtDni;
	private JTextField txtRol;
	private JTextField txtLocal;
	private static VentanaAgregarSueldo ventanaAgregarSueldo;
	private JTextField txtSueldo;
	
	
	public static VentanaAgregarSueldo getInstance(){
		if(ventanaAgregarSueldo == null){	
			ventanaAgregarSueldo = new VentanaAgregarSueldo();
			return ventanaAgregarSueldo;
		}else{
			return ventanaAgregarSueldo;
		}
	}
	
	public VentanaAgregarSueldo() {
		setTitle("Comprobantes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 660, 402);
		setLocationRelativeTo(null); 
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLocal = new JLabel("Local:");
		lblLocal.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLocal.setBounds(33, 179, 111, 14);
		contentPane.add(lblLocal);
		
		JLabel lblRol = new JLabel("Rol");
		lblRol.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRol.setBounds(33, 128, 160, 14);
		contentPane.add(lblRol);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(0, 128, 0));
		panel.setBounds(0, 0, 654, 53);
		contentPane.add(panel);
		
		JLabel lblSueldos = new JLabel("Carga de sueldo");
		lblSueldos.setForeground(Color.WHITE);
		lblSueldos.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblSueldos.setBounds(247, 0, 210, 53);
		panel.add(lblSueldos);
		Image imgPdfPago = new ImageIcon(this.getClass().getResource("/recursos/pdf.png")).getImage();
		Image imgPdfReserva = new ImageIcon(this.getClass().getResource("/recursos/pdf.png")).getImage();
		
		JLabel lblNombreEmpleado = new JLabel("Empleado:");
		lblNombreEmpleado.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNombreEmpleado.setBounds(33, 78, 92, 14);
		contentPane.add(lblNombreEmpleado);
		
		txtEmpleado = new JTextField();
		txtEmpleado.setBackground(Color.WHITE);
		txtEmpleado.setEditable(false);
		txtEmpleado.setBounds(148, 75, 185, 20);
		contentPane.add(txtEmpleado);
		txtEmpleado.setColumns(10);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDni.setBounds(377, 78, 46, 14);
		contentPane.add(lblDni);
		
		txtDni = new JTextField();
		txtDni.setBackground(Color.WHITE);
		txtDni.setEditable(false);
		txtDni.setBounds(444, 75, 160, 20);
		contentPane.add(txtDni);
		txtDni.setColumns(10);
		
		txtRol = new JTextField();
		txtRol.setBackground(Color.WHITE);
		txtRol.setEditable(false);
		txtRol.setBounds(150, 125, 215, 20);
		contentPane.add(txtRol);
		txtRol.setColumns(10);
		
		txtLocal = new JTextField();
		txtLocal.setEditable(false);
		txtLocal.setBackground(Color.WHITE);
		txtLocal.setBounds(148, 176, 356, 20);
		contentPane.add(txtLocal);
		txtLocal.setColumns(10);
		
		JLabel lblSueldo = new JLabel("Sueldo:");
		lblSueldo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSueldo.setBounds(50, 286, 122, 14);
		contentPane.add(lblSueldo);
		
		txtSueldo = new JTextField();
		txtSueldo.setColumns(10);
		txtSueldo.setBackground(Color.WHITE);
		txtSueldo.setBounds(148, 283, 172, 20);
		contentPane.add(txtSueldo);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(243, 314, 178, 48);
		contentPane.add(btnConfirmar);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(148, 235, 155, 20);
		contentPane.add(comboBox);
		
		JLabel lblMes = new JLabel("Mes:");
		lblMes.setBounds(33, 238, 53, 14);
		contentPane.add(lblMes);
	}

	


	public JTextField getTxtEmpleado() {
		return txtEmpleado;
	}

	public void mostrarVentana(boolean visibilidad) {
		this.setVisible(visibilidad);
	}
}
