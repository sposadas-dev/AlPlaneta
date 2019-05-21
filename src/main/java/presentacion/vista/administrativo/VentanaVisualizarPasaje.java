package presentacion.vista.administrativo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

public class VentanaVisualizarPasaje extends JFrame {

	private JPanel contentPane;
	private JLabel lblClienteDelPasaje;
	private JLabel lblDniDelPasaje;
	private JLabel lblOrigenDelPasaje;
	private JLabel lblDestinoDelPasaje;
	private JLabel lblTransporteDelPasaje;
	private JLabel lblEstadoPasaje;
	private JLabel lblRestante;
	private JButton btnPagar;
	private JButton btnAceptar;
	private JButton btnCancelar;
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
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(27, 39, 46, 14);
		contentPane.add(lblCliente);
		
		JLabel lblOrigen = new JLabel("Origen:");
		lblOrigen.setBounds(27, 89, 46, 14);
		contentPane.add(lblOrigen);
		
		JLabel lblDestino = new JLabel("Destino:");
		lblDestino.setBounds(27, 136, 46, 14);
		contentPane.add(lblDestino);
		
		JLabel lblTransporte = new JLabel("Transporte:");
		lblTransporte.setBounds(27, 188, 95, 14);
		contentPane.add(lblTransporte);
		
		JLabel lblEstadoDelPasaje = new JLabel("Estado del pasaje:");
		lblEstadoDelPasaje.setBounds(285, 188, 127, 14);
		contentPane.add(lblEstadoDelPasaje);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(326, 39, 46, 14);
		contentPane.add(lblDni);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(228, 301, 151, 47);
		contentPane.add(btnAceptar);
		
		
		lblClienteDelPasaje = new JLabel("");
		lblClienteDelPasaje.setBounds(76, 39, 180, 14);
		contentPane.add(lblClienteDelPasaje);
		
		lblDniDelPasaje = new JLabel("");
		lblDniDelPasaje.setBounds(382, 39, 143, 14);
		contentPane.add(lblDniDelPasaje);
		
		lblOrigenDelPasaje = new JLabel("");
		lblOrigenDelPasaje.setBounds(76, 89, 285, 14);
		contentPane.add(lblOrigenDelPasaje);
		
		lblDestinoDelPasaje = new JLabel("");
		lblDestinoDelPasaje.setBounds(83, 150, 208, 14);
		contentPane.add(lblDestinoDelPasaje);
		
		lblTransporteDelPasaje = new JLabel("");
		lblTransporteDelPasaje.setBounds(100, 212, 180, 14);
		contentPane.add(lblTransporteDelPasaje);
		
		lblEstadoPasaje = new JLabel("");
		lblEstadoPasaje.setBounds(426, 212, 127, 14);
		contentPane.add(lblEstadoPasaje);
		
		JLabel lblMonto = new JLabel("Monto:");
		lblMonto.setBounds(27, 237, 95, 14);
		contentPane.add(lblMonto);
		
		JLabel lblMontoDelPasaje = new JLabel("");
		lblMontoDelPasaje.setBounds(83, 263, 46, 14);
		contentPane.add(lblMontoDelPasaje);
		
		JLabel lblMontoAPagar = new JLabel("Importe restante:");
		lblMontoAPagar.setBounds(285, 237, 116, 14);
		contentPane.add(lblMontoAPagar);
		
		lblRestante = new JLabel("");
		lblRestante.setBounds(382, 263, 143, 14);
		contentPane.add(lblRestante);
		
		btnPagar = new JButton("Pagar");
		btnPagar.setBounds(23, 299, 150, 51);
		contentPane.add(btnPagar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(426, 301, 148, 47);
		contentPane.add(btnCancelar);
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
	
	public JLabel getLblRestante() {
		return lblRestante;
	}
	
	public JButton getBtnAceptar() {
		return btnAceptar;
	}
	
	public JButton getBtnPagar() {
		return btnPagar;
	}
	
	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	
	public void mostrarVentana(boolean visibilidad){
		this.setVisible(visibilidad);
	}
}