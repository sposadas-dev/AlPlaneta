package presentacion.vista.administrativo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JSeparator;

public class VentanaVisualizarPasaje extends JFrame {

	private JPanel contentPane;
	private JTextField txtClienteDelPasaje;
	private JTextField txtDniDelPasaje;
	private JTextField txtCodigoDelPasaje;
	private JTextField txtOrigenDelPasaje;
	private JTextField txtDestinoDelPasaje;
	private JTextField txtTransporteDelPasaje;
	private JTextField txtEstadoPasaje;
	private JTextField txtMontoDelPasaje;
	private JTextField txtImporteDebePasaje;
	private JButton btnPagar;
	private JButton btnImprimirComprobante;
	private JButton btnVerPagos;
	private static VentanaVisualizarPasaje INSTANCE;
	private JLabel lblCodigoPasaje;
	private JLabel lblMontoAPagar; 

	

	private JLabel lblMotivoCancelacion;
	private JTextField txtMotivoCancelacion;
	
	
	
	public static VentanaVisualizarPasaje getInstance(){
		if(INSTANCE == null){	
			INSTANCE = new VentanaVisualizarPasaje();
			return INSTANCE;
		}else{
			return INSTANCE;
		}
	}

	public VentanaVisualizarPasaje() {
		setTitle("Visualizar pasaje");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 708, 514);
		setResizable(false);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(95, 158, 160));
		panel.setBounds(0, 0, 702, 53);
		contentPane.add(panel);
		
		JLabel lblVisualizarPasaje = new JLabel("Visualizar pasaje");
		lblVisualizarPasaje.setForeground(Color.WHITE);
		lblVisualizarPasaje.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblVisualizarPasaje.setBounds(191, 0, 214, 53);
		panel.add(lblVisualizarPasaje);
		
		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCliente.setBounds(27, 74, 46, 14);
		contentPane.add(lblCliente);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDni.setBounds(363, 74, 46, 14);
		contentPane.add(lblDni);
		
		JLabel lblOrigen = new JLabel("Origen:");
		lblOrigen.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblOrigen.setBounds(27, 157, 46, 14);
		contentPane.add(lblOrigen);
		
		JLabel lblDestino = new JLabel("Destino:");
		lblDestino.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDestino.setBounds(27, 204, 77, 14);
		contentPane.add(lblDestino);
		
		JLabel lblTransporte = new JLabel("Transporte:");
		lblTransporte.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTransporte.setBounds(27, 247, 95, 14);
		contentPane.add(lblTransporte);
		
		JLabel lblEstadoDelPasaje = new JLabel("Estado del pasaje:");
		lblEstadoDelPasaje.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEstadoDelPasaje.setBounds(327, 247, 127, 14);
		contentPane.add(lblEstadoDelPasaje);
		
		JLabel lblMonto = new JLabel("Total: $");
		lblMonto.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblMonto.setBounds(27, 294, 95, 14);
		contentPane.add(lblMonto);
		
		lblMontoAPagar = new JLabel("Importe restante a pagar: $");
		lblMontoAPagar.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblMontoAPagar.setBounds(303, 294, 183, 14);
		contentPane.add(lblMontoAPagar);
		
		txtClienteDelPasaje = new JTextField();
		txtClienteDelPasaje.setBackground(Color.WHITE);
		txtClienteDelPasaje.setEditable(false);
		txtClienteDelPasaje.setBounds(83, 72, 208, 20);
		contentPane.add(txtClienteDelPasaje);
		txtClienteDelPasaje.setColumns(10);
		
		txtDniDelPasaje = new JTextField();
		txtDniDelPasaje.setBackground(Color.WHITE);
		txtDniDelPasaje.setEditable(false);
		txtDniDelPasaje.setBounds(411, 72, 201, 20);
		contentPane.add(txtDniDelPasaje);
		txtDniDelPasaje.setColumns(10);
		
		txtOrigenDelPasaje = new JTextField();
		txtOrigenDelPasaje.setBackground(Color.WHITE);
		txtOrigenDelPasaje.setEditable(false);
		txtOrigenDelPasaje.setBounds(95, 155, 405, 20);
		contentPane.add(txtOrigenDelPasaje);
		txtOrigenDelPasaje.setColumns(10);
		
		txtDestinoDelPasaje = new JTextField();
		txtDestinoDelPasaje.setEditable(false);
		txtDestinoDelPasaje.setBackground(Color.WHITE);
		txtDestinoDelPasaje.setColumns(10);
		txtDestinoDelPasaje.setBounds(95, 202, 405, 20);
		contentPane.add(txtDestinoDelPasaje);
		
		txtTransporteDelPasaje = new JTextField();
		txtTransporteDelPasaje.setBackground(Color.WHITE);
		txtTransporteDelPasaje.setEditable(false);
		txtTransporteDelPasaje.setBounds(102, 245, 183, 20);
		contentPane.add(txtTransporteDelPasaje);
		txtTransporteDelPasaje.setColumns(10);
		
		txtEstadoPasaje = new JTextField();
		txtEstadoPasaje.setBackground(Color.WHITE);
		txtEstadoPasaje.setEditable(false);
		txtEstadoPasaje.setBounds(464, 245, 149, 20);
		contentPane.add(txtEstadoPasaje);
		txtEstadoPasaje.setColumns(10);
		
		txtMontoDelPasaje = new JTextField();
		txtMontoDelPasaje.setBackground(Color.WHITE);
		txtMontoDelPasaje.setEditable(false);
		txtMontoDelPasaje.setBounds(102, 292, 143, 20);
		contentPane.add(txtMontoDelPasaje);
		txtMontoDelPasaje.setColumns(10);
		
		txtImporteDebePasaje = new JTextField();
		txtImporteDebePasaje.setBackground(Color.WHITE);
		txtImporteDebePasaje.setEditable(false);
		txtImporteDebePasaje.setBounds(497, 292, 135, 20);
		contentPane.add(txtImporteDebePasaje);
		txtImporteDebePasaje.setColumns(10);
		
		txtMotivoCancelacion = new JTextField();
		txtMotivoCancelacion.setEditable(false);
		txtMotivoCancelacion.setColumns(10);
		txtMotivoCancelacion.setBackground(Color.WHITE);
		txtMotivoCancelacion.setBounds(183, 337, 449, 28);
		contentPane.add(txtMotivoCancelacion);
		txtMotivoCancelacion.setVisible(false);
		
		btnPagar = new JButton("Pagar");
		btnPagar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnPagar.setBounds(521, 412, 150, 51);
		btnPagar.setBackground(new Color(5, 196, 107));
		btnPagar.setForeground(Color.WHITE);
		
		contentPane.add(btnPagar);
		
		btnVerPagos = new JButton("Ver pagos");
		btnVerPagos.setForeground(Color.WHITE);
		btnVerPagos.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnVerPagos.setBackground(new Color(192, 192, 192));
		btnVerPagos.setBounds(61, 412, 150, 51);
		contentPane.add(btnVerPagos);
		
		btnImprimirComprobante = new JButton("Imprimir comprobante");
		btnImprimirComprobante.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnImprimirComprobante.setBounds(255, 413, 221, 49);
		btnImprimirComprobante.setBackground(new Color(52, 152, 219));
		btnImprimirComprobante.setForeground(Color.WHITE);
		contentPane.add(btnImprimirComprobante);
		
		lblCodigoPasaje = new JLabel("Código del pasaje:");
		lblCodigoPasaje.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCodigoPasaje.setBounds(27, 116, 112, 14);
		contentPane.add(lblCodigoPasaje);
		
		lblMotivoCancelacion = new JLabel("Motivo de cancelacion:");
		lblMotivoCancelacion.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblMotivoCancelacion.setBounds(27, 341, 163, 14);
		contentPane.add(lblMotivoCancelacion);
		lblMotivoCancelacion.setVisible(false);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(27, 372, 605, 14);
		contentPane.add(separator);
		
		txtCodigoDelPasaje = new JTextField();
		txtCodigoDelPasaje.setEditable(false);
		txtCodigoDelPasaje.setBackground(Color.WHITE);
		txtCodigoDelPasaje.setBounds(149, 114, 321, 20);
		contentPane.add(txtCodigoDelPasaje);
		txtCodigoDelPasaje.setColumns(10);
	}
	
	public JTextField getTxtCodigoDelPasaje() {
		return txtCodigoDelPasaje;
	}

	public JTextField getTxtMotivoCancelacion() {
		return txtMotivoCancelacion;
	}

	public JTextField getTxtClienteDelPasaje() {
		return txtClienteDelPasaje;
	}
	
	public JTextField getTxtDniDelPasaje() {
		return txtDniDelPasaje;
	}
	
	public JTextField getTxtOrigenDelPasaje() {
		return txtOrigenDelPasaje;
	}

	public JTextField getTxtDestinoDelPasaje() {
		return txtDestinoDelPasaje;
	}
	
	public JTextField getTxtTransporteDelPasaje() {
		return txtTransporteDelPasaje;
	}
	
	public JTextField getTxtMontoDelPasaje() {
		return txtMontoDelPasaje;
	}
	
	public JTextField getTxtEstadoPasaje() {
		return txtEstadoPasaje;
	}
	
	public JTextField getTxtImporteDebePasaje() {
		return txtImporteDebePasaje;
	}
	
	public JButton getBtnImprimirComprobante() {
		return btnImprimirComprobante;
	}
	
	public JButton getBtnPagar() {
		return btnPagar;
	}
	
	public JButton getBtnVerPagos() {
		return btnVerPagos;
	}
	
	public JLabel getLblMontoAPagar() {
		return lblMontoAPagar;
	}
	
	public JLabel getLblMotivoCancelacion() {
		return lblMotivoCancelacion;
	}

	public void mostrarVentana(boolean visibilidad){
		this.setVisible(visibilidad);
	}
}