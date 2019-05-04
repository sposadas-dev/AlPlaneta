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
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPagoEfectivo extends JFrame {

	private JPanel contentPane;
	private static VentanaPagoEfectivo INSTANCE;
	private JTextField textImporteIngresado;
	private JButton btnRegistrarPago;
	
	public static VentanaPagoEfectivo getInstance(){
		if(INSTANCE == null)
			return new VentanaPagoEfectivo();
		else
			return INSTANCE;
	}
	
	private VentanaPagoEfectivo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 283, 121);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textImporteIngresado = new JTextField();
		textImporteIngresado.setBounds(84, 11, 161, 20);
		contentPane.add(textImporteIngresado);
		textImporteIngresado.setColumns(10);
		
		JLabel lblImporte = new JLabel("IMPORTE");
		lblImporte.setBounds(10, 14, 46, 14);
		contentPane.add(lblImporte);
		
		btnRegistrarPago = new JButton("Registrar Pago Efectivo");
		btnRegistrarPago.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnRegistrarPago.setBounds(16, 49, 229, 23);
		contentPane.add(btnRegistrarPago);
		
		this.setVisible(false);
	}
	
	
	
	public void mostrarVentana(boolean mostrar){
		this.setVisible(mostrar);
	}
	

	public JTextField getTextImporteIngresado() {
		return textImporteIngresado;
	}

	public void setTextImporteIngresado(JTextField textImporteIngresado) {
		this.textImporteIngresado = textImporteIngresado;
	}

	
	
	
	public JButton getBtnRegistrarPago() {
		return btnRegistrarPago;
	}

	public void setBtnRegistrarPago(JButton btnRegistrarPago) {
		this.btnRegistrarPago = btnRegistrarPago;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPagoEfectivo frame = new VentanaPagoEfectivo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
