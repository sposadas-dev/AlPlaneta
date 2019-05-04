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

public class VentanaFormaPago extends JFrame {

	private JPanel contentPane;
	private JComboBox<?> comboBoxEstadoPago;
	private DefaultComboBoxModel modelo;
	private JButton btnPago;
	private static VentanaFormaPago INSTANCE;
	
	
	public static VentanaFormaPago getInstance(){
		if(INSTANCE == null)
			return new VentanaFormaPago();
		else
			return INSTANCE;
	}
	
	private VentanaFormaPago() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 492, 224);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JRadioButton rdbtnEfectivo = new JRadioButton("Efectivo");
		rdbtnEfectivo.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdbtnEfectivo.setBounds(155, 0, 109, 37);
		contentPane.add(rdbtnEfectivo);
		
	
		JRadioButton rdbtnTarjetaDeCrditodbito = new JRadioButton("Tarjeta de crédito");
		rdbtnTarjetaDeCrditodbito.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdbtnTarjetaDeCrditodbito.setBounds(295, 0, 175, 51);
		contentPane.add(rdbtnTarjetaDeCrditodbito);
		
		JRadioButton rdbtnPendiente = new JRadioButton("Pendiente");
		rdbtnPendiente.setFont(new Font("Tahoma", Font.BOLD, 14));
		rdbtnPendiente.setBounds(6, 7, 109, 23);
		contentPane.add(rdbtnPendiente);
		
		this.comboBoxEstadoPago = new JComboBox();
		this.modelo = new DefaultComboBoxModel();
		modelo.addElement("EFECTIVO");
		modelo.addElement("TARJETA");
		modelo.addElement("NINGUN PAGO");
		comboBoxEstadoPago.setModel(modelo);
		comboBoxEstadoPago.setBounds(57, 76, 167, 20);
		contentPane.add(comboBoxEstadoPago);
		
		btnPago = new JButton("Seleccionar");
		btnPago.setBounds(60, 126, 89, 23); 	
		contentPane.add(btnPago);
		
		this.setVisible(false);
	}
	
	
	public JComboBox<?> getComboBoxEstadoPago() {
		return comboBoxEstadoPago;
	}
	public void setComboBoxEstadoPago(JComboBox<?> comboBoxEstadoPago) {
		this.comboBoxEstadoPago = comboBoxEstadoPago;
	}
	public DefaultComboBoxModel getModelo() {
		return modelo;
	}
	public void setModelo(DefaultComboBoxModel modelo) {
		this.modelo = modelo;
	}
	
	public void mostrarVentana(boolean mostrar){
		this.setVisible(mostrar);
	}
	
	public JButton getBtnPago() {
		return btnPago;
	}

	public void setBtnPago(JButton btnPago) {
		this.btnPago = btnPago;
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

	public void redimensionar() {
		setBounds(100, 100, 500, 400);
	}
}
