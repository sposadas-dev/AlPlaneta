package presentacion.vista.administrativo;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class VentanaMotivosReprogramacionEvento extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JEditorPane txtMotivos;
	private JButton btnAtras;

	private static VentanaMotivosReprogramacionEvento ventanaCliente;
	
	public static VentanaMotivosReprogramacionEvento getInstance(){
		if(ventanaCliente == null){	
			ventanaCliente = new VentanaMotivosReprogramacionEvento();
			return ventanaCliente;
		}else{
			return ventanaCliente;
		}
	}
	
	public VentanaMotivosReprogramacionEvento() {
		setTitle("Reprogramación");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 150, 425, 402);
		setLocationRelativeTo(null); // centrado en pantalla

		
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelCliente = new JPanel();
		panelCliente.setBackground(new Color(66, 99, 145));
		panelCliente.setBounds(0, 0, 419, 47);
		contentPane.add(panelCliente);
		panelCliente.setLayout(null);
		
		JLabel lblEditarEvento = new JLabel("Motivos de reprogramación");
		lblEditarEvento.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditarEvento.setForeground(Color.WHITE);
		lblEditarEvento.setBounds(0, 0, 419, 47);
		panelCliente.add(lblEditarEvento);
		lblEditarEvento.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 60, 397, 239);
		contentPane.add(scrollPane_1);
		
		txtMotivos = new JEditorPane();
		scrollPane_1.setViewportView(txtMotivos);
		txtMotivos.setEditable(false);
		
		btnAtras = new JButton("Atras");
		btnAtras.setForeground(Color.WHITE);
		btnAtras.setBackground(new Color(192, 57, 43));
		btnAtras.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAtras.setBounds(148, 312, 131, 42);
		contentPane.add(btnAtras);			
		
	}
	
	public JButton getBtnCancelar() {
		return btnAtras;
	}
	
	public void mostrarVentana(boolean b){
		this.setVisible(b);
	}
	
	public void limpiarCampos(){
		this.txtMotivos.setText("");
	}

	public JEditorPane getTxtMotivos() {
		return txtMotivos;
	}
	
	public static VentanaMotivosReprogramacionEvento getVentanaCliente() {
		return ventanaCliente;
	}

	public void cerrarVentana(){
		this.dispose();
	}
	
}