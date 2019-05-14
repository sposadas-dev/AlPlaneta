package presentacion.vista.administrativo;

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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaFormaPago extends JFrame {

	private JPanel contentPane;
	private JComboBox<?> comboBoxEstadoPago;
	private DefaultComboBoxModel modelo;
	private JButton btnPago;
	private static VentanaFormaPago INSTANCE;
	private JLabel lblSelecioneUnaForma;
	private JLabel lblMontoAPagar;
	private JTextField textImporteTotal;
	private JLabel lblMontoaPagar;
	
	
	public static VentanaFormaPago getInstance(){
		if(INSTANCE == null)
			return new VentanaFormaPago();
		else
			return INSTANCE;
	}
	
	private VentanaFormaPago() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 413, 271);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.comboBoxEstadoPago = new JComboBox();
		this.modelo = new DefaultComboBoxModel();
		modelo.addElement("EFECTIVO");
		modelo.addElement("TARJETA");
		modelo.addElement("NINGUN PAGO");
		comboBoxEstadoPago.setModel(modelo);
		comboBoxEstadoPago.setBounds(218, 45, 167, 20);
		contentPane.add(comboBoxEstadoPago);
		
		btnPago = new JButton("realizar Pago");
		btnPago.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPago.setBounds(10, 198, 375, 23); 	
		contentPane.add(btnPago);
		
		lblSelecioneUnaForma = new JLabel("Seleccione una forma de pago");
		lblSelecioneUnaForma.setBounds(10, 47, 198, 17);
		contentPane.add(lblSelecioneUnaForma);
		
		lblMontoAPagar = new JLabel("Monto a pagar :");
		lblMontoAPagar.setBounds(10, 98, 157, 14);
		contentPane.add(lblMontoAPagar);
		
		JLabel lblNewLabel = new JLabel("Importe Total");
		lblNewLabel.setBounds(10, 151, 157, 14);
		contentPane.add(lblNewLabel);
		
		textImporteTotal = new JTextField();
		textImporteTotal.setBounds(218, 148, 167, 20);
		contentPane.add(textImporteTotal);
		textImporteTotal.setColumns(10);
		
		lblMontoaPagar = new JLabel("-");
		lblMontoaPagar.setBounds(218, 98, 167, 14);
		contentPane.add(lblMontoaPagar);
		
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

	public JTextField getTextImporteTotal() {
		return textImporteTotal;
	}

	public void setTextImporteTotal(JTextField textImporteTotal) {
		this.textImporteTotal = textImporteTotal;
	}

	public JLabel getLblMontoaPagar() {
		return lblMontoaPagar;
	}

	public void setLblMontoaPagar(JLabel lblMontoaPagar) {
		this.lblMontoaPagar = lblMontoaPagar;
	}
	
	
	
}
