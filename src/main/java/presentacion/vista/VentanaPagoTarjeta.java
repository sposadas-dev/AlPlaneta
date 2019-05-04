package presentacion.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;

public class VentanaPagoTarjeta extends JFrame {

	private JPanel contentPane;
	private static VentanaPagoTarjeta INSTANCE;
	private JTextField textNro1;
	private JTextField textNro2;
	private JTextField textNro3;
	private JTextField textNro4;
	private JLabel label_1;
	private JLabel label_2;
	private JTextField textNombreTitular;
	private JTextField textValidaHasta;
	private JLabel label_3;
	private JComboBox comboBoxMarcaTarjeta;
	private JButton btnEnviar;
	private JLabel lblMontoAPagar;
	private JTextField textImporteIngresado;

	
	public static VentanaPagoTarjeta getInstance(){
		if(INSTANCE == null)
			return new VentanaPagoTarjeta();
		else
			return INSTANCE;
	}
	
	private VentanaPagoTarjeta() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 541, 236);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("NUMERO DE TARJETA");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setBounds(23, 11, 138, 14);
		contentPane.add(label);
		
		textNro1 = new JTextField();
		textNro1.setColumns(10);
		textNro1.setBounds(23, 36, 111, 20);
		contentPane.add(textNro1);
		
		textNro2 = new JTextField();
		textNro2.setColumns(10);
		textNro2.setBounds(146, 36, 116, 20);
		contentPane.add(textNro2);
		
		textNro3 = new JTextField();
		textNro3.setColumns(10);
		textNro3.setBounds(272, 36, 116, 20);
		contentPane.add(textNro3);
		
		textNro4 = new JTextField();
		textNro4.setColumns(10);
		textNro4.setBounds(398, 36, 116, 20);
		contentPane.add(textNro4);
		
		label_1 = new JLabel("VALIDO HASTA");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1.setBounds(23, 81, 86, 14);
		contentPane.add(label_1);
		
		label_2 = new JLabel("NOMBRE TITULAR");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_2.setBounds(23, 122, 111, 14);
		contentPane.add(label_2);
		
		textNombreTitular = new JTextField();
		textNombreTitular.setColumns(10);
		textNombreTitular.setBounds(144, 119, 370, 20);
		contentPane.add(textNombreTitular);
		
		textValidaHasta = new JTextField();
		textValidaHasta.setColumns(10);
		textValidaHasta.setBounds(144, 78, 116, 20);
		contentPane.add(textValidaHasta);
		
		label_3 = new JLabel("FRANQUICIAS");
		label_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_3.setBounds(279, 81, 96, 14);
		contentPane.add(label_3);
		
		comboBoxMarcaTarjeta = new JComboBox();
		comboBoxMarcaTarjeta.setBounds(396, 78, 116, 20);
		contentPane.add(comboBoxMarcaTarjeta);
		
		btnEnviar = new JButton("Ingresar Pago");
		btnEnviar.setBounds(272, 157, 240, 23);
		contentPane.add(btnEnviar);
		
		lblMontoAPagar = new JLabel("MONTO A PAGAR");
		lblMontoAPagar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMontoAPagar.setBounds(23, 161, 111, 14);
		contentPane.add(lblMontoAPagar);
		
		textImporteIngresado = new JTextField();
		textImporteIngresado.setBounds(146, 158, 116, 20);
		contentPane.add(textImporteIngresado);
		textImporteIngresado.setColumns(10);
		
		this.setVisible(false);
	}
	
	public void mostrarVentana(boolean mostrar){
		this.setVisible(mostrar);
	}
	
	public JTextField getTextNro1() {
		return textNro1;
	}

	public void setTextNro1(JTextField textNro1) {
		this.textNro1 = textNro1;
	}

	public JTextField getTextNro2() {
		return textNro2;
	}

	public void setTextNro2(JTextField textNro2) {
		this.textNro2 = textNro2;
	}

	public JTextField getTextNro3() {
		return textNro3;
	}

	public void setTextNro3(JTextField textNro3) {
		this.textNro3 = textNro3;
	}

	public JTextField getTextNro4() {
		return textNro4;
	}

	public void setTextNro4(JTextField textNro4) {
		this.textNro4 = textNro4;
	}

	public JTextField getTextNombreTitular() {
		return textNombreTitular;
	}

	public void setTextNombreTitular(JTextField textNombreTitular) {
		this.textNombreTitular = textNombreTitular;
	}

	public JTextField getTextValidaHasta() {
		return textValidaHasta;
	}

	public void setTextValidaHasta(JTextField textValidaHasta) {
		this.textValidaHasta = textValidaHasta;
	}

	public JComboBox getComboBoxMarcaTarjeta() {
		return comboBoxMarcaTarjeta;
	}

	public void setComboBoxMarcaTarjeta(JComboBox comboBoxMarcaTarjeta) {
		this.comboBoxMarcaTarjeta = comboBoxMarcaTarjeta;
	}

	public JButton getBtnEnviar() {
		return btnEnviar;
	}

	public void setBtnEnviar(JButton btnEnviar) {
		this.btnEnviar = btnEnviar;
	}
	
	

	public JTextField getTextImporteIngresado() {
		return textImporteIngresado;
	}

	public void setTextImporte(JTextField textImporte) {
		this.textImporteIngresado = textImporte;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPagoTarjeta frame = new VentanaPagoTarjeta();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
