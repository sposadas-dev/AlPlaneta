package presentacion.vista.administrativo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dto.FormaPagoDTO;

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
	private JLabel lblDatoPorcentajeDescuento;
	private JLabel lblDatoMontoOriginal;
	private Component lblPorcentajeDeDescuento;
	private JLabel lblMontoOriginal;
	private JLabel lblImporte;

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
		btnPago.setBounds(367, 308, 142, 54); 	
		contentPane.add(btnPago);
		
		lblSelecioneUnaForma = new JLabel("Seleccione una forma de pago");
		lblSelecioneUnaForma.setBounds(36, 118, 198, 17);
		contentPane.add(lblSelecioneUnaForma);
		
		lblMontoAPagar = new JLabel("Monto a pagar:");
		lblMontoAPagar.setBounds(152, 201, 157, 14);
		contentPane.add(lblMontoAPagar);
		
		lblImporte = new JLabel("Importe:");
		lblImporte.setBounds(152, 255, 157, 14);
		contentPane.add(lblImporte);
		
		textImporteTotal = new JTextField();
		textImporteTotal.setBounds(367, 252, 167, 20);
		contentPane.add(textImporteTotal);
		textImporteTotal.setEditable(false);
		textImporteTotal.setColumns(10);
		
		lblMontoaPagar = new JLabel("-");
		lblMontoaPagar.setBounds(367, 201, 167, 14);
		contentPane.add(lblMontoaPagar);
		
		btnAtras = new JButton("Atrás");
		btnAtras.setBounds(171, 308, 134, 54);
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
		separator.setBounds(67, 159, 467, 16);
		contentPane.add(separator);
		
		lblMontoOriginal = new JLabel("Monto original:");
		lblMontoOriginal.setBounds(152, 188, 157, 14);
		contentPane.add(lblMontoOriginal);
		lblMontoOriginal.setVisible(false);
		
		lblDatoMontoOriginal = new JLabel("-");
		lblDatoMontoOriginal.setBounds(367, 188, 167, 14);
		contentPane.add(lblDatoMontoOriginal);
		lblDatoMontoOriginal.setVisible(false);
		
		lblPorcentajeDeDescuento = new JLabel("Porcentaje de descuento:");
		lblPorcentajeDeDescuento.setBounds(152, 215, 157, 14);
		contentPane.add(lblPorcentajeDeDescuento);
		lblPorcentajeDeDescuento.setVisible(false);
		
		lblDatoPorcentajeDescuento = new JLabel("-");
		lblDatoPorcentajeDescuento.setBounds(367, 212, 167, 14);
		contentPane.add(lblDatoPorcentajeDescuento);
		lblDatoPorcentajeDescuento.setVisible(false);
		
		this.setVisible(false);
		
		
	}
	
	public void mostrarDatosPromocion() {
		lblMontoOriginal.setVisible(true);
		lblDatoMontoOriginal.setVisible(true);
		lblPorcentajeDeDescuento.setVisible(true);
		lblDatoPorcentajeDescuento.setVisible(true);
		lblImporte.setBounds(152, 269, 157, 14);
		lblMontoAPagar.setBounds(152, 242, 157, 14);
		lblMontoaPagar.setBounds(367, 242, 167, 14);
		textImporteTotal.setBounds(367, 266, 167, 20);
	}
	
	private void borrarDatosPromocion() {
		lblMontoOriginal.setVisible(false);
		lblDatoMontoOriginal.setVisible(false);
		lblPorcentajeDeDescuento.setVisible(false);
		lblDatoPorcentajeDescuento.setVisible(false);
		lblImporte.setBounds(152, 255, 157, 14);
		lblMontoAPagar.setBounds(152, 201, 157, 14);
		lblMontoaPagar.setBounds(367, 201, 167, 14);
		textImporteTotal.setBounds(367, 252, 167, 20);
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
		borrarDatosPromocion();
	}


	public void setLblDatoPorcentajeDescuento(String lblDatoPorcentajeDescuento) {
		this.lblDatoPorcentajeDescuento.setText(lblDatoPorcentajeDescuento);
	}


	public void setLblDatoMontoOriginal(String lblDatoMontoOriginal) {
		this.lblDatoMontoOriginal.setText(lblDatoMontoOriginal);
	}
	
	
}