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

public class VentanaVisualizarPasaje extends JFrame {

	private JPanel contentPane;
	private JLabel lblClienteDelPasaje;
	private JLabel lblDniDelPasaje;
	private JLabel lblOrigenDelPasaje;
	private JLabel lblDestinoDelPasaje;
	private JLabel lblTransporteDelPasaje;
	private JLabel lblEstadoPasaje;
	private JLabel lblMontoDelPasaje;
	private JLabel lblRestante;
	private JButton btnPagar;
	private JButton btnAceptar;
	
	private static VentanaVisualizarPasaje INSTANCE;
	
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
		setBounds(100, 100, 600, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCliente.setBounds(27, 74, 46, 14);
		contentPane.add(lblCliente);
		
		JLabel lblOrigen = new JLabel("Origen:");
		lblOrigen.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblOrigen.setBounds(27, 122, 46, 14);
		contentPane.add(lblOrigen);
		
		JLabel lblDestino = new JLabel("Destino:");
		lblDestino.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDestino.setBounds(27, 163, 77, 14);
		contentPane.add(lblDestino);
		
		JLabel lblTransporte = new JLabel("Transporte:");
		lblTransporte.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTransporte.setBounds(27, 200, 95, 14);
		contentPane.add(lblTransporte);
		
		JLabel lblEstadoDelPasaje = new JLabel("Estado del pasaje:");
		lblEstadoDelPasaje.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEstadoDelPasaje.setBounds(310, 200, 127, 14);
		contentPane.add(lblEstadoDelPasaje);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDni.setBounds(310, 74, 46, 14);
		contentPane.add(lblDni);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAceptar.setBounds(290, 299, 156, 49);
		btnAceptar.setBackground(new Color(52, 152, 219));
		btnAceptar.setForeground(Color.WHITE);
		contentPane.add(btnAceptar);
		
		lblClienteDelPasaje = new JLabel("");
		lblClienteDelPasaje.setBounds(83, 74, 180, 14);
		contentPane.add(lblClienteDelPasaje);
		
		lblDniDelPasaje = new JLabel("");
		lblDniDelPasaje.setBounds(366, 74, 143, 14);
		contentPane.add(lblDniDelPasaje);
		
		lblOrigenDelPasaje = new JLabel("");
		lblOrigenDelPasaje.setBounds(86, 122, 285, 14);
		contentPane.add(lblOrigenDelPasaje);
		
		lblDestinoDelPasaje = new JLabel("");
		lblDestinoDelPasaje.setBounds(93, 163, 208, 14);
		contentPane.add(lblDestinoDelPasaje);
		
		lblTransporteDelPasaje = new JLabel("");
		lblTransporteDelPasaje.setBounds(106, 200, 180, 14);
		contentPane.add(lblTransporteDelPasaje);
		
		lblEstadoPasaje = new JLabel("");
		lblEstadoPasaje.setBounds(425, 200, 127, 14);
		contentPane.add(lblEstadoPasaje);
		
		JLabel lblMonto = new JLabel("Monto: $");
		lblMonto.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblMonto.setBounds(27, 248, 95, 14);
		contentPane.add(lblMonto);
		
		lblMontoDelPasaje = new JLabel("");
		lblMontoDelPasaje.setBounds(93, 248, 97, 14);
		contentPane.add(lblMontoDelPasaje);
		
		JLabel lblMontoAPagar = new JLabel("Importe restante: $");
		lblMontoAPagar.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblMontoAPagar.setBounds(310, 248, 143, 14);
		contentPane.add(lblMontoAPagar);
		
		lblRestante = new JLabel("");
		lblRestante.setBounds(441, 248, 143, 14);
		contentPane.add(lblRestante);
		
		btnPagar = new JButton("Pagar");
		btnPagar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnPagar.setBounds(121, 299, 150, 51);
		btnPagar.setBackground(new Color(5, 196, 107));
		btnPagar.setForeground(Color.WHITE);

		contentPane.add(btnPagar);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(95, 158, 160));
		panel.setBounds(0, 0, 594, 53);
		contentPane.add(panel);
		
		JLabel lblVisualizarPasaje = new JLabel("Visualizar pasaje");
		lblVisualizarPasaje.setForeground(Color.WHITE);
		lblVisualizarPasaje.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblVisualizarPasaje.setBounds(191, 0, 214, 53);
		panel.add(lblVisualizarPasaje);
	}
	
	public JLabel getLblClienteDelPasaje() {
		return lblClienteDelPasaje;
	}

	public JLabel getLblDniDelPasaje() {
		return lblDniDelPasaje;
	}
	
	public JLabel getLblOrigenDelPasaje() {
		return lblOrigenDelPasaje;
	}
	
	public JLabel getLblDestinoDelPasaje() {
		return lblDestinoDelPasaje;
	}
	
	public JLabel getLblTransporteDelPasaje() {
		return lblTransporteDelPasaje;
	}
	
	public JLabel getLblEstadoPasaje() {
		return lblEstadoPasaje;
	}
	
	public JLabel getLblMontoDelPasaje() {
		return lblMontoDelPasaje;
	}

		
	public JLabel getLblRestante() {
		return lblRestante;
	}
	
	public JButton getBtnAceptar() {
		return btnAceptar;
	}
	
	public JButton getBtnPagar() {
		return btnPagar;
	}
	
	public void mostrarVentana(boolean visibilidad){
		this.setVisible(visibilidad);
	}
}