package presentacion.vista.administrativo;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class VistaNotificacionDeEvento extends JFrame {

	private JPanel contentPane;
	private JLabel lblDatoDescripcion;
	private JLabel lblDatoEstado;
	private JLabel lblDatoNombreCliente;
	private JLabel lblDatoDni;
	private JLabel lblDatoTelefono;
	private JLabel lblDatoApellido;
	private JLabel lblDatoMail;
	private JLabel lblDatoCelular;

	private static VistaNotificacionDeEvento INSTANCE;
	
	public static VistaNotificacionDeEvento getInstance(){
		if(INSTANCE == null){	
			INSTANCE = new VistaNotificacionDeEvento();
			return INSTANCE;
		}else{
			return INSTANCE;
		}
	}
	public VistaNotificacionDeEvento() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 520, 275);
		setResizable(false);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRegistrarEvento = new JLabel("Evento");
		lblRegistrarEvento.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistrarEvento.setForeground(Color.WHITE);
		lblRegistrarEvento.setBounds(10, 0, 492, 47);
		contentPane.add(lblRegistrarEvento);
		lblRegistrarEvento.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(66, 99, 145));
		panel.setBounds(0, 0, 514, 47);
		contentPane.add(panel);
		
		JLabel lblDescripcion = new JLabel("Descripcion:");
		lblDescripcion.setBounds(23, 59, 123, 14);
		contentPane.add(lblDescripcion);
		
		JLabel lblDatosCliente = new JLabel("Datos cliente");
		lblDatosCliente.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDatosCliente.setBounds(23, 125, 123, 14);
		contentPane.add(lblDatosCliente);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(23, 86, 123, 14);
		contentPane.add(lblEstado);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(23, 153, 123, 14);
		contentPane.add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(239, 153, 123, 14);
		contentPane.add(lblApellido);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(23, 180, 123, 14);
		contentPane.add(lblDni);
		
		JLabel lblTelefono = new JLabel("Telefono:");
		lblTelefono.setBounds(23, 207, 123, 14);
		contentPane.add(lblTelefono);
		
		JLabel lblCelular = new JLabel("Celular:");
		lblCelular.setBounds(239, 206, 123, 14);
		contentPane.add(lblCelular);
		
		JLabel lblMail = new JLabel("Mail:");
		lblMail.setBounds(239, 179, 123, 14);
		contentPane.add(lblMail);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(23, 110, 479, 2);
		contentPane.add(separator);
		
		lblDatoDescripcion = new JLabel("");
		lblDatoDescripcion.setBounds(100, 58, 402, 14);
		contentPane.add(lblDatoDescripcion);
		
		lblDatoEstado = new JLabel("");
		lblDatoEstado.setBounds(74, 86, 123, 14);
		contentPane.add(lblDatoEstado);
		
		lblDatoNombreCliente = new JLabel("");
		lblDatoNombreCliente.setBounds(74, 152, 164, 14);
		contentPane.add(lblDatoNombreCliente);
		
		lblDatoDni = new JLabel("");
		lblDatoDni.setBounds(56, 180, 123, 14);
		contentPane.add(lblDatoDni);
		
		lblDatoTelefono = new JLabel("");
		lblDatoTelefono.setBounds(84, 206, 123, 14);
		contentPane.add(lblDatoTelefono);
		
		lblDatoApellido = new JLabel("");
		lblDatoApellido.setBounds(296, 152, 206, 14);
		contentPane.add(lblDatoApellido);
		
		lblDatoMail = new JLabel("");
		lblDatoMail.setBounds(274, 180, 228, 14);
		contentPane.add(lblDatoMail);
		
		lblDatoCelular = new JLabel("");
		lblDatoCelular.setBounds(296, 206, 193, 14);
		contentPane.add(lblDatoCelular);
	}
	
	public JLabel getLblDatoDescripcion() {
		return lblDatoDescripcion;
	}
	public JLabel getLblDatoEstado() {
		return lblDatoEstado;
	}
	public JLabel getLblDatoNombreCliente() {
		return lblDatoNombreCliente;
	}
	public JLabel getLblDatoDni() {
		return lblDatoDni;
	}
	public JLabel getLblDatoTelefono() {
		return lblDatoTelefono;
	}
	public JLabel getLblDatoApellido() {
		return lblDatoApellido;
	}
	public JLabel getLblDatoMail() {
		return lblDatoMail;
	}
	public JLabel getLblDatoCelular() {
		return lblDatoCelular;
	}
	public void mostrarVentana(boolean visibilidad){
		this.setVisible(visibilidad);
	}
}