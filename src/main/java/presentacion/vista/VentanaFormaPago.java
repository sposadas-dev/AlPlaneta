package presentacion.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dto.PagoDTO;

public class VentanaFormaPago extends JFrame {

	private JPanel contentPane;
	private JComboBox<PagoDTO> comboBoxFormaPago;
//	private DefaultComboBoxModel modelo;
	private static VentanaFormaPago INSTANCE;
	private JTextField txtMonto;
	
	
	public static VentanaFormaPago getInstance(){
		if(INSTANCE == null)
			return new VentanaFormaPago();
		else
			return INSTANCE;
	}
	
	private VentanaFormaPago() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 508, 286);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.comboBoxFormaPago = new JComboBox<PagoDTO>();
//		this.modelo = new DefaultComboBoxModel();
//		modelo.addElement("EFECTIVO");
//		modelo.addElement("TARJETA");
//		modelo.addElement("NINGUN PAGO");
//		comboBoxFormaPago.setModel(modelo);
		comboBoxFormaPago.setBounds(137, 46, 167, 20);
		contentPane.add(comboBoxFormaPago);
		
		JLabel lblFormaDePago = new JLabel("Forma de pago:");
		lblFormaDePago.setBounds(23, 49, 118, 14);
		contentPane.add(lblFormaDePago);
		
		JButton btnRegistrarPago = new JButton("Registrar pago");
		btnRegistrarPago.setBounds(171, 160, 123, 48);
		contentPane.add(btnRegistrarPago);
		
		txtMonto = new JTextField();
		txtMonto.setBounds(137, 102, 86, 20);
		contentPane.add(txtMonto);
		txtMonto.setColumns(10);
		
		JLabel lblMonto = new JLabel("Monto:");
		lblMonto.setBounds(23, 105, 46, 14);
		contentPane.add(lblMonto);
		
		this.setVisible(false);
	}
	
//	
//	public JComboBox<?> getComboBoxEstadoPago() {
//		return comboBoxFormaPago;
//	}
//	public void setComboBoxEstadoPago(JComboBox<?> comboBoxEstadoPago) {
//		this.comboBoxFormaPago = comboBoxEstadoPago;
//	}
//	
//	
////	public DefaultComboBoxModel getModelo() {
//		return modelo;
//	}
//	public void setModelo(DefaultComboBoxModel modelo) {
//		this.modelo = modelo;
//	}
	
	public void mostrarVentana(boolean mostrar){
		this.setVisible(mostrar);
	}
	

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

	public JComboBox<?> getComboBoxFormaPago() {
		return comboBoxFormaPago;
	}

//	public void setComboBoxFormaPago(JComboBox<?> comboBoxFormaPago) {
//		this.comboBoxFormaPago = comboBoxFormaPago;
//	}

	public JTextField getTxtMonto() {
		return txtMonto;
	}

	public void setTxtMonto(JTextField txtMonto) {
		this.txtMonto = txtMonto;
	}

//	public void redimensionar() {
//		setBounds(100, 100, 500, 400);
//	}
}
