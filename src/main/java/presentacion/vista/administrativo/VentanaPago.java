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
import java.awt.Color;
import javax.swing.JSeparator;

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
	private JButton btnAtras;

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
		setBounds(100, 100, 690, 410);
		setLocationRelativeTo(null); 
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.comboBoxFormaDePago = new JComboBox<FormaPagoDTO>();
		comboBoxFormaDePago.setBounds(221, 116, 238, 20);
		contentPane.add(comboBoxFormaDePago);		
		comboBoxFormaDePago.setEnabled(false);

		btnPago = new JButton("Confirmar pago");
		btnPago.setBounds(367, 296, 142, 54); 	
		contentPane.add(btnPago);
		
		lblSelecioneUnaForma = new JLabel("Seleccione una forma de pago");
		lblSelecioneUnaForma.setBounds(36, 118, 198, 17);
		contentPane.add(lblSelecioneUnaForma);
		
		lblMontoAPagar = new JLabel("Monto a pagar :");
		lblMontoAPagar.setBounds(152, 194, 157, 14);
		contentPane.add(lblMontoAPagar);
		
		JLabel lblNewLabel = new JLabel("Importe:");
		lblNewLabel.setBounds(152, 242, 157, 14);
		contentPane.add(lblNewLabel);
		
		textImporteTotal = new JTextField();
		textImporteTotal.setBounds(367, 239, 167, 20);
		contentPane.add(textImporteTotal);
		textImporteTotal.setEditable(false);
		textImporteTotal.setColumns(10);
		
		lblMontoaPagar = new JLabel("-");
		lblMontoaPagar.setBounds(367, 194, 167, 14);
		contentPane.add(lblMontoaPagar);
		
		btnAtras = new JButton("Atrás");
		btnAtras.setBounds(171, 296, 134, 54);
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
		radioPaga.setBounds(200, 76, 109, 23);
		contentPane.add(radioPaga);
		
		radioReservaSinPagar = new JRadioButton("Reserva sin pagar");
		 radioReservaSinPagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(radioReservaSinPagar.isSelected()){
//					comboBoxFormaDePago.setEnabled(false);
					textImporteTotal.setText("0");
					textImporteTotal.setEditable(false);

				}
			}
		});
		radioReservaSinPagar .setBounds(342, 76, 167, 23);
		contentPane.add(radioReservaSinPagar );
		
		bg = new ButtonGroup();
		bg.add(radioPaga);
		bg.add(radioReservaSinPagar);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(9, 132, 227));
		panel.setBounds(0, 0, 684, 53);
		contentPane.add(panel);
		
		JLabel lblPago = new JLabel("Pago");
		lblPago.setForeground(Color.WHITE);
		lblPago.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblPago.setBounds(265, 0, 210, 53);
		panel.add(lblPago);
		
		JButton btnIngresarTarjeta = new JButton("Ingresar tarjeta");
		btnIngresarTarjeta.setBounds(490, 115, 167, 23);
		contentPane.add(btnIngresarTarjeta);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(67, 159, 467, 37);
		contentPane.add(separator);
		
		this.setVisible(false);
	}
		
	public JButton getBtnAtras() {
		return btnAtras;
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
	
	public void limpiarCampos(){
		this.textImporteTotal.setText("");
	}
}