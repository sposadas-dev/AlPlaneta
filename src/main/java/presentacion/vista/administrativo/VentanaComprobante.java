package presentacion.vista.administrativo;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JTextField;

public class VentanaComprobante extends JFrame {

	private JPanel contentPane;
	private JTextField txtCliente;
	private JTextField txtDni;
	private JTextField txtCodigo;
	private JTextField txtOrigenViaje;
	private JTextField txtDestinoViaje;
	private JTextField txtValorViaje;
	private JButton btnPdfReserva;
	private JButton btnPdfPago;
	private static VentanaComprobante INSTANCE;
	private JTextField txtImportePagado;
	
	
	public static VentanaComprobante getInstance(){
		if(INSTANCE == null)
			return new VentanaComprobante();
		else
			return INSTANCE;
	}
	
	public VentanaComprobante() {
		setTitle("Comprobantes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 660, 480);
		setLocationRelativeTo(null); 
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblOrigenViaje = new JLabel("Origen del viaje:");
		lblOrigenViaje.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblOrigenViaje.setBounds(33, 155, 111, 14);
		contentPane.add(lblOrigenViaje);
		
		JLabel lblCodigoPasaje = new JLabel("Código del pasaje:");
		lblCodigoPasaje.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCodigoPasaje.setBounds(33, 118, 160, 14);
		contentPane.add(lblCodigoPasaje);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(0, 128, 128));
		panel.setBounds(0, 0, 654, 53);
		contentPane.add(panel);
		
		JLabel lblComprobantes = new JLabel("Comprobantes");
		lblComprobantes.setForeground(Color.WHITE);
		lblComprobantes.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblComprobantes.setBounds(247, 0, 210, 53);
		panel.add(lblComprobantes);
		
		JLabel lblDestinoViaje = new JLabel("Destino del viaje:");
		lblDestinoViaje.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDestinoViaje.setBounds(33, 195, 143, 14);
		contentPane.add(lblDestinoViaje);
		

		btnPdfPago = new JButton("");
		btnPdfPago.setBounds(375, 340, 86, 81);
		Image imgPdfPago = new ImageIcon(this.getClass().getResource("/recursos/pdf.png")).getImage();
		btnPdfPago.setIcon(new ImageIcon(imgPdfPago.getScaledInstance(75, 72, 85)));
		contentPane.add(btnPdfPago);
		
		JLabel lblComprobanteDePago = new JLabel("Comprobante de pago");
		lblComprobanteDePago.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblComprobanteDePago.setBounds(341, 315, 191, 25);
		contentPane.add(lblComprobanteDePago);
		
		JLabel lblComprobanteDeReserva = new JLabel("Comprobante de reserva");
		lblComprobanteDeReserva.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblComprobanteDeReserva.setBounds(75, 315, 172, 25);
		contentPane.add(lblComprobanteDeReserva);
		
		btnPdfReserva = new JButton("");
		btnPdfReserva.setBounds(122, 340, 86, 81);
		Image imgPdfReserva = new ImageIcon(this.getClass().getResource("/recursos/pdf.png")).getImage();
		btnPdfReserva.setIcon(new ImageIcon(imgPdfReserva.getScaledInstance(75, 72, 85)));
		contentPane.add(btnPdfReserva);
		
		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCliente.setBounds(33, 78, 46, 14);
		contentPane.add(lblCliente);
		
		JLabel lblValorTotalViaje = new JLabel("Valor total del viaje: $");
		lblValorTotalViaje.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblValorTotalViaje.setBounds(33, 279, 143, 14);
		contentPane.add(lblValorTotalViaje);
		
		txtCliente = new JTextField();
		txtCliente.setBackground(Color.WHITE);
		txtCliente.setEditable(false);
		txtCliente.setBounds(89, 75, 185, 20);
		contentPane.add(txtCliente);
		txtCliente.setColumns(10);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDni.setBounds(377, 78, 46, 14);
		contentPane.add(lblDni);
		
		txtDni = new JTextField();
		txtDni.setBackground(Color.WHITE);
		txtDni.setEditable(false);
		txtDni.setBounds(444, 75, 160, 20);
		contentPane.add(txtDni);
		txtDni.setColumns(10);
		
		txtCodigo = new JTextField();
		txtCodigo.setBackground(Color.WHITE);
		txtCodigo.setEditable(false);
		txtCodigo.setBounds(149, 115, 215, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		txtOrigenViaje = new JTextField();
		txtOrigenViaje.setBackground(Color.WHITE);
		txtOrigenViaje.setEditable(false);
		txtOrigenViaje.setBounds(149, 152, 356, 20);
		contentPane.add(txtOrigenViaje);
		txtOrigenViaje.setColumns(10);
		
		txtDestinoViaje = new JTextField();
		txtDestinoViaje.setBackground(Color.WHITE);
		txtDestinoViaje.setEditable(false);
		txtDestinoViaje.setBounds(149, 192, 356, 20);
		contentPane.add(txtDestinoViaje);
		txtDestinoViaje.setColumns(10);
		
		txtValorViaje = new JTextField();
		txtValorViaje.setBackground(Color.WHITE);
		txtValorViaje.setEditable(false);
		txtValorViaje.setBounds(174, 275, 172, 20);
		contentPane.add(txtValorViaje);
		txtValorViaje.setColumns(10);
		
		JLabel lblImportePagado = new JLabel("Importe pagado: $");
		lblImportePagado.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblImportePagado.setBounds(33, 237, 122, 14);
		contentPane.add(lblImportePagado);
		
		txtImportePagado = new JTextField();
		txtImportePagado.setEditable(false);
		txtImportePagado.setColumns(10);
		txtImportePagado.setBackground(Color.WHITE);
		txtImportePagado.setBounds(174, 234, 172, 20);
		contentPane.add(txtImportePagado);
	}

	
	public JTextField getTxtImportePagado() {
		return txtImportePagado;
	}

	public JTextField getTxtCliente() {
		return txtCliente;
	}

	public JTextField getTxtDni() {
		return txtDni;
	}

	public JTextField getTxtCodigo() {
		return txtCodigo;
	}

	public JTextField getTxtOrigenViaje() {
		return txtOrigenViaje;
	}

	public JTextField getTxtDestinoViaje() {
		return txtDestinoViaje;
	}

	public JTextField getTxtValorViaje() {
		return txtValorViaje;
	}

	public JButton getBtnPdfReserva() {
		return btnPdfReserva;
	}

	public JButton getBtnPdfPago() {
		return btnPdfPago;
	}

	public void mostrarVentana(boolean visibilidad) {
		this.setVisible(visibilidad);
	}
}
