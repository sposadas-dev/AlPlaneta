package presentacion.vista.coordinador;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;

public class VentanaModificarRegimenPuntos extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCantPuntos;
	private JButton btnModificar;
	private JButton btnCancelar;

	private static VentanaModificarRegimenPuntos INSTANCE;
	private JLabel lblArs;
	private JTextField textARS;
	private JLabel lblVencimiento;
	private JTextField vencimiento;

	public static VentanaModificarRegimenPuntos getInstance(){
		if(INSTANCE == null)
			return new VentanaModificarRegimenPuntos();
		else
			return INSTANCE;
	}
	
	public VentanaModificarRegimenPuntos() {
		setTitle("Modificar Regimen de Puntos");
		setResizable(false);;
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
		panel.setBounds(0, 0, 407, 53);
		contentPane.add(panel);
		
		JLabel lblAgregarPunto = new JLabel("Modificar Regimen ");
		lblAgregarPunto.setForeground(Color.WHITE);
		lblAgregarPunto.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblAgregarPunto.setBounds(27, 0, 253, 53);
		panel.add(lblAgregarPunto);
		
		JLabel lblCANTPUNTOS = new JLabel("Cant. Puntos");
		lblCANTPUNTOS.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblCANTPUNTOS.setBounds(20, 64, 85, 14);
		contentPane.add(lblCANTPUNTOS);
		
		txtCantPuntos = new JTextField();
		txtCantPuntos.setBounds(115, 61, 85, 20);
		contentPane.add(txtCantPuntos);
		txtCantPuntos.setColumns(10);
		
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
		
		lblArs = new JLabel("ARS");
		lblArs.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblArs.setBounds(20, 89, 46, 14);
		contentPane.add(lblArs);
		
		textARS = new JTextField();
		textARS.setBounds(114, 86, 86, 20);
		contentPane.add(textARS);
		textARS.setColumns(10);
		
		lblVencimiento = new JLabel("Vencimiento");
		lblVencimiento.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblVencimiento.setBounds(20, 114, 74, 14);
		contentPane.add(lblVencimiento);
		
		vencimiento = new JTextField();
		vencimiento.setColumns(10);
		vencimiento.setBounds(114, 111, 86, 20);
		contentPane.add(vencimiento);
	}
	
	
	public JTextField getTxtCantPuntos() {
		return txtCantPuntos;
	}

	public void setTxtCantPuntos(JTextField txtCantPuntos) {
		this.txtCantPuntos = txtCantPuntos;
	}


	public JLabel getLblArs() {
		return lblArs;
	}

	public void setLblArs(JLabel lblArs) {
		this.lblArs = lblArs;
	}

	public JTextField getTextARS() {
		return textARS;
	}

	public void setTextARS(JTextField textARS) {
		this.textARS = textARS;
	}

	public JTextField getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(JTextField vencimiento) {
		this.vencimiento = vencimiento;
	}

	public JLabel getLblVencimiento() {
		return lblVencimiento;
	}

	public void setLblVencimiento(JLabel lblVencimiento) {
		this.lblVencimiento = lblVencimiento;
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
	
	public JButton getBtnCancelar() {
		return btnCancelar;
	}
	
	
	public JButton getBtnModificar() {
		return btnModificar;
	}

	public void setBtnModificar(JButton btnModificar) {
		this.btnModificar = btnModificar;
	}
	
	public void setTxtcantPuntos(JTextField txtcantPuntos) {
		this.txtCantPuntos = txtcantPuntos;
	}

	public void limpiarCampos(){
		this.txtCantPuntos.setText(null);
	}
}
