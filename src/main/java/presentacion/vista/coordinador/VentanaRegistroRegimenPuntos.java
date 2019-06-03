package presentacion.vista.coordinador;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

public class VentanaRegistroRegimenPuntos  extends JFrame {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCantPuntos;
	private JTextField textARS;
	private JDateChooser dateVencimiento;
	private JButton btnRegistrar;
	private JButton btnCancelar;
	private static VentanaRegistroRegimenPuntos INSTANCE;
	
	

	public static VentanaRegistroRegimenPuntos getInstance(){
		if(INSTANCE == null)
			return new VentanaRegistroRegimenPuntos();
		else
			return INSTANCE;
	}
	
	public VentanaRegistroRegimenPuntos() {
		
		setTitle("Registro de Regimen de Puntos");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 423, 303);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(96, 163, 188));
		panel.setBounds(0, 0, 450, 53);
		contentPane.add(panel);
		
		JLabel lblRegistrar = new JLabel("Regimen de puntos");
		lblRegistrar.setForeground(Color.WHITE);
		lblRegistrar.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblRegistrar.setBounds(27, 0, 253, 53);
		panel.add(lblRegistrar);
		
		JLabel lblCANTPUNTOS = new JLabel("Cant. Puntos");
		lblCANTPUNTOS.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblCANTPUNTOS.setBounds(41, 64, 78, 14);
		contentPane.add(lblCANTPUNTOS);
		
		txtCantPuntos = new JTextField();
		txtCantPuntos.setBounds(119, 64, 86, 20);
		contentPane.add(txtCantPuntos);
		txtCantPuntos.setColumns(10);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCancelar.setBackground(new Color(192, 57, 43));
		btnCancelar.setBounds(206, 177, 131, 42);
		contentPane.add(btnCancelar);
		
		btnRegistrar = new JButton("Registrar ");
		btnRegistrar.setForeground(Color.WHITE);
		btnRegistrar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnRegistrar.setBackground(new Color(5, 196, 107));
		btnRegistrar.setBounds(47, 177, 131, 42);
		contentPane.add(btnRegistrar);
		
		JLabel lblARS = new JLabel("ARS");
		lblARS.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblARS.setBounds(41, 89, 46, 14);
		contentPane.add(lblARS);
		
		textARS = new JTextField();
		textARS.setBounds(119, 89, 86, 20);
		contentPane.add(textARS);
		textARS.setColumns(10);
		
		JLabel lblVencimiento = new JLabel("Vencimiento");
		lblVencimiento.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblVencimiento.setBounds(41, 114, 78, 14);
		contentPane.add(lblVencimiento);dateVencimiento = new JDateChooser();
		dateVencimiento.setBounds(119, 114, 120, 20);
		contentPane.add(dateVencimiento);
	}
	public JDateChooser getDateVencimiento() {
		return dateVencimiento;
	}
	
	public void setDateVencimiento(JDateChooser dateVencimiento) {
		this.dateVencimiento = dateVencimiento;
	}

	public JButton getBtnRegistrar() {
		return btnRegistrar;
	}

	public JTextField getTxtCantPuntos() {
		return txtCantPuntos;
	}

	public void setTxtCantPuntos(JTextField txtCantPuntos) {
		this.txtCantPuntos = txtCantPuntos;
	}

	public JTextField getTextARS() {
		return textARS;
	}

	public void setTextARS(JTextField textARS) {
		this.textARS = textARS;
	}

	public void setBtnRegistrar(JButton btnRegistrar) {
		this.btnRegistrar = btnRegistrar;
	}

	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public void mostrarVentana(){
		this.setVisible(true);
	}

	public void cerrarVentana(){
		this.setVisible(false);
	}
	
	public JButton getBtnAgregar() {
		return btnRegistrar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public void limpiarCampos() {
		this.txtCantPuntos.setText(null);
		
	}
	
	
}
