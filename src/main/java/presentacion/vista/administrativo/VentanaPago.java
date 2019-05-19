package presentacion.vista.administrativo;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;

import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dto.FormaPagoDTO;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPago extends JFrame {

	private JPanel contentPane;
	private ButtonGroup bg;
	private JRadioButton radioPaga;
	private JRadioButton radioReservaSinPagar; 
	

	private JComboBox<FormaPagoDTO> comboBoxFormaDePago;
	private JLabel lblSelecioneUnaForma;
	private JLabel lblMontoAPagar;
	private JTextField textImporteTotal;
	private JLabel lblMontoaPagar;
	private JButton btnPago;
	private static VentanaPago INSTANCE;
	
	public static VentanaPago getInstance(){
		if(INSTANCE == null)
			return new VentanaPago();
		else
			return INSTANCE;
	}

	
	private VentanaPago() {
		setTitle("Pago");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 413, 271);
		setLocationRelativeTo(null); 
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.comboBoxFormaDePago = new JComboBox<FormaPagoDTO>();
		comboBoxFormaDePago.setBounds(218, 68, 167, 20);
		contentPane.add(comboBoxFormaDePago);
		comboBoxFormaDePago.setEnabled(false);

		
		
		btnPago = new JButton("Realizar pago");
		btnPago.setBounds(218, 179, 121, 42); 	
		contentPane.add(btnPago);
		
		lblSelecioneUnaForma = new JLabel("Seleccione una forma de pago");
		lblSelecioneUnaForma.setBounds(10, 70, 198, 17);
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
		textImporteTotal.setEditable(false);
		textImporteTotal.setColumns(10);
		
		lblMontoaPagar = new JLabel("-");
		lblMontoaPagar.setBounds(218, 98, 167, 14);
		contentPane.add(lblMontoaPagar);
		
		JButton btnAtras = new JButton("Atrás");
		btnAtras.setBounds(46, 179, 121, 42);
		contentPane.add(btnAtras);
		
		radioPaga = new JRadioButton("Paga");
		radioPaga.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(radioPaga.isSelected()){
					comboBoxFormaDePago.setEnabled(true);
					textImporteTotal.setText("");
					textImporteTotal.setEditable(true);

				}
			}
		});
		radioPaga.setBounds(60, 27, 109, 23);
		contentPane.add(radioPaga);
		
		radioReservaSinPagar = new JRadioButton("Reserva sin pagar");
		 radioReservaSinPagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(radioReservaSinPagar.isSelected()){
					comboBoxFormaDePago.setEnabled(false);
					textImporteTotal.setText("0");
					textImporteTotal.setEditable(false);
				}
			}
		});
		radioReservaSinPagar .setBounds(207, 27, 167, 23);
		contentPane.add(radioReservaSinPagar );
		
		bg = new ButtonGroup();
		bg.add(radioPaga);
		bg.add(radioReservaSinPagar);
		
		this.setVisible(false);
	}
		
	public JComboBox<FormaPagoDTO> getComboBoxFormaPago() {
		return comboBoxFormaDePago;
	}
	public void setComboBoxEstadoPago(JComboBox<FormaPagoDTO> comboBoxEstadoPago) {
		this.comboBoxFormaDePago = comboBoxEstadoPago;
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
					VentanaPago frame = new VentanaPago();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
	
	public JRadioButton getRadioPaga() {
		return radioPaga;
	}

	public JRadioButton getRadioReservaSinPagar() {
		return radioReservaSinPagar;
	}
}