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

public class VentanaMostrarRegimenPuntos  extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCantPuntos;
	private JTextField textARS;
	private JButton btnModificar;
	private JButton btnCancelar;
	private static VentanaMostrarRegimenPuntos INSTANCE;
	private JTextField vencimiento;
	
	

	public static VentanaMostrarRegimenPuntos getInstance(){
		if(INSTANCE == null)
			return new VentanaMostrarRegimenPuntos();
		else
			return INSTANCE;
	}
	
	public VentanaMostrarRegimenPuntos() {
		
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
		txtCantPuntos.setEditable(false);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCancelar.setBackground(new Color(192, 57, 43));
		btnCancelar.setBounds(206, 177, 131, 42);
		contentPane.add(btnCancelar);
		
		btnModificar = new JButton("Modificar");
		btnModificar.setForeground(Color.WHITE);
		btnModificar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnModificar.setBackground(new Color(5, 196, 107));
		btnModificar.setBounds(47, 177, 131, 42);
		contentPane.add(btnModificar);
		
		JLabel lblARS = new JLabel("ARS");
		lblARS.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblARS.setBounds(41, 89, 46, 14);
		contentPane.add(lblARS);
		
		textARS = new JTextField();
		textARS.setBounds(119, 89, 86, 20);
		contentPane.add(textARS);
		textARS.setColumns(10);
		textARS.setEditable(false);
		
		JLabel lblVencimiento = new JLabel("Vencimiento");
		lblVencimiento.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblVencimiento.setBounds(41, 114, 78, 14);
		contentPane.add(lblVencimiento);
		
		vencimiento = new JTextField();
		vencimiento.setColumns(10);
		vencimiento.setBounds(119, 111, 86, 20);
		contentPane.add(vencimiento);
		vencimiento.setEditable(false);
		
		
	}

	public JTextField getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(JTextField vencimiento) {
		this.vencimiento = vencimiento;
	}

	public JButton getBtnRegistrar() {
		return btnModificar;
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
		this.btnModificar = btnRegistrar;
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
		return btnModificar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public void limpiarCampos() {
		this.txtCantPuntos.setText(null);
		
	}
}
