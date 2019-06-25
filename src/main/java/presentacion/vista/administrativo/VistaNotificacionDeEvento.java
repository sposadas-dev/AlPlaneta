package presentacion.vista.administrativo;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

public class VistaNotificacionDeEvento extends JFrame {

	private JPanel contentPane;
	private JEditorPane lblDatoDescripcion;
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
		setBounds(100, 100, 520, 339);
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
		lblDescripcion.setBounds(16, 66, 123, 20);
		contentPane.add(lblDescripcion);
		
		JLabel lblDatosCliente = new JLabel("Datos cliente");
		lblDatosCliente.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDatosCliente.setBounds(16, 183, 123, 20);
		contentPane.add(lblDatosCliente);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(16, 144, 123, 20);
		contentPane.add(lblEstado);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(16, 211, 123, 20);
		contentPane.add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(232, 211, 123, 20);
		contentPane.add(lblApellido);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(16, 238, 123, 20);
		contentPane.add(lblDni);
		
		JLabel lblTelefono = new JLabel("Telefono:");
		lblTelefono.setBounds(16, 265, 123, 20);
		contentPane.add(lblTelefono);
		
		JLabel lblCelular = new JLabel("Celular:");
		lblCelular.setBounds(232, 264, 123, 20);
		contentPane.add(lblCelular);
		
		JLabel lblMail = new JLabel("Mail:");
		lblMail.setBounds(232, 237, 123, 20);
		contentPane.add(lblMail);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(15, 173, 479, 2);
		contentPane.add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(100, 67, 395, 69);
		contentPane.add(scrollPane);
		
		lblDatoDescripcion = new JEditorPane();
		lblDatoDescripcion.setEditable(false);
		scrollPane.setViewportView(lblDatoDescripcion);
		
		lblDatoEstado = new JLabel("");
		lblDatoEstado.setBounds(67, 144, 123, 20);
		contentPane.add(lblDatoEstado);
		
		lblDatoNombreCliente = new JLabel("");
		lblDatoNombreCliente.setBounds(67, 210, 164, 20);
		contentPane.add(lblDatoNombreCliente);
		
		lblDatoDni = new JLabel("");
		lblDatoDni.setBounds(45, 238, 123, 20);
		contentPane.add(lblDatoDni);
		
		lblDatoTelefono = new JLabel("");
		lblDatoTelefono.setBounds(77, 264, 123, 20);
		contentPane.add(lblDatoTelefono);
		
		lblDatoApellido = new JLabel("");
		lblDatoApellido.setBounds(289, 210, 206, 20);
		contentPane.add(lblDatoApellido);
		
		lblDatoMail = new JLabel("");
		lblDatoMail.setBounds(265, 238, 228, 20);
		contentPane.add(lblDatoMail);
		
		lblDatoCelular = new JLabel("");
		lblDatoCelular.setBounds(281, 264, 193, 20);
		contentPane.add(lblDatoCelular);
	}
	
	public JEditorPane getLblDatoDescripcion() {
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