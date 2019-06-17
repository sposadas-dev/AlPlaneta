package presentacion.vista.administrativo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

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

public class VentanaPagoPuntos extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7511173230268773962L;
	private JPanel contentPane;
	private ButtonGroup bg;
	private JButton btnPago;
	private JButton btnAtras;
	private JLabel lblPuntosDelCliente;
	private JLabel lblCostoDelPasajeEnPuntos;

	private static VentanaPagoPuntos INSTANCE;
	
	public static VentanaPagoPuntos getInstance(){
		if(INSTANCE == null)
			return new VentanaPagoPuntos();
		else
			return INSTANCE;
	}

	
	private VentanaPagoPuntos() {
		setTitle("Pago");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 690, 326);
		setLocationRelativeTo(null); 
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnPago = new JButton("Confirmar pago");
		btnPago.setBounds(362, 197, 142, 54); 	
		contentPane.add(btnPago);
		
		btnAtras = new JButton("Atrás");
		btnAtras.setBounds(172, 197, 134, 54);
		contentPane.add(btnAtras);
		
		bg = new ButtonGroup();
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(9, 132, 227));
		panel.setBounds(0, 0, 684, 53);
		contentPane.add(panel);
		
		JLabel lblPago = new JLabel("Pago con Puntos");
		lblPago.setForeground(Color.WHITE);
		lblPago.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblPago.setBounds(265, 0, 210, 53);
		panel.add(lblPago);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(67, 159, 467, 14);
		contentPane.add(separator);
		
		JLabel lbldisponible = new JLabel("Puntos disponible :");
		lbldisponible.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbldisponible.setBounds(148, 71, 184, 14);
		contentPane.add(lbldisponible);
		
		lblPuntosDelCliente = new JLabel("");
		lblPuntosDelCliente.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPuntosDelCliente.setBounds(342, 71, 107, 14);
		contentPane.add(lblPuntosDelCliente);
		
		JLabel lblpuntospasaje = new JLabel("Costo del viaje en puntos :");
		lblpuntospasaje.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblpuntospasaje.setBounds(92, 121, 227, 14);
		contentPane.add(lblpuntospasaje);
		
		lblCostoDelPasajeEnPuntos = new JLabel("");
		lblCostoDelPasajeEnPuntos.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCostoDelPasajeEnPuntos.setBounds(342, 121, 107, 14);
		contentPane.add(lblCostoDelPasajeEnPuntos);
		
		this.setVisible(false);
		
		
	}
	
	public JButton getBtnAtras() {
		return btnAtras;
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

	public JLabel getLblPuntosDelCliente() {
		return lblPuntosDelCliente;
	}


	public void setLblPuntosDelCliente(JLabel lblPuntosDelCliente) {
		this.lblPuntosDelCliente = lblPuntosDelCliente;
	}


	public JLabel getLblCostoDelPasajeEnPuntos() {
		return lblCostoDelPasajeEnPuntos;
	}


	public void setLblCostoDelPasajeEnPuntos(JLabel lblCostoDelPasajeEnPuntos) {
		this.lblCostoDelPasajeEnPuntos = lblCostoDelPasajeEnPuntos;
	}
}	