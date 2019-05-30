package presentacion.vista.administrativo;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JSeparator;
import javax.swing.JComboBox;

public class VentanaRegistrarEvento extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JDateChooser dateFechaEvento;
	private JComboBox comboHoraEvento;
	private JComboBox comboEstado;
	private JTextField txtDescripcion;
	private JButton btnRegistrar;
	private JButton btnCancelar;
	private JButton btnAgregarCliente;

	private static VentanaRegistrarEvento ventanaCliente;
	private JTextField txtDni;
	
	
	public static VentanaRegistrarEvento getInstance(){
		if(ventanaCliente == null){	
			ventanaCliente = new VentanaRegistrarEvento();
			return ventanaCliente;
		}else{
			return ventanaCliente;
		}
	}
	
	public VentanaRegistrarEvento() {
		setResizable(false);
		setTitle("Registrar evento");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 150, 597, 449);
		setLocationRelativeTo(null); // centrado en pantalla

		
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelCliente = new JPanel();
		panelCliente.setBackground(Color.PINK);
		panelCliente.setBounds(0, 0, 591, 53);
		contentPane.add(panelCliente);
		panelCliente.setLayout(null);
		
		JLabel lblRegistrarEvento = new JLabel("Registrar evento");
		lblRegistrarEvento.setForeground(Color.WHITE);
		lblRegistrarEvento.setBounds(191, 0, 214, 53);
		panelCliente.add(lblRegistrarEvento);
		lblRegistrarEvento.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		JLabel lblFechaEvento = new JLabel("Fecha evento:\t");
		lblFechaEvento.setBounds(35, 92, 87, 14);
		contentPane.add(lblFechaEvento);
		
		JLabel lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setBounds(35, 171, 72, 31);
		contentPane.add(lblDescripcion);
		
		dateFechaEvento = new JDateChooser();
		dateFechaEvento.setBounds(120, 86, 131, 20);
		JTextFieldDateEditor editor = (JTextFieldDateEditor) dateFechaEvento.getDateEditor();
		editor.setEditable(false);
		contentPane.add(dateFechaEvento);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(119, 176, 435, 20);
		contentPane.add(txtDescripcion);
		txtDescripcion.setColumns(10);
		
		JSeparator separadorCliente = new JSeparator();
		separadorCliente.setBounds(64, 327, 490, 2);
		contentPane.add(separadorCliente);
		
		btnRegistrar = new JButton("Registrar");
		btnRegistrar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnRegistrar.setBackground(new Color(52, 152, 219));
		btnRegistrar.setForeground(Color.WHITE);
		btnRegistrar.setBounds(135, 343, 131, 42);
		contentPane.add(btnRegistrar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setBackground(new Color(192, 57, 43));
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCancelar.setBounds(308, 343, 131, 42);
		contentPane.add(btnCancelar);			
		
		JLabel lblHoraEvento = new JLabel("Hora evento:");
		lblHoraEvento.setBounds(35, 132, 85, 14);
		contentPane.add(lblHoraEvento);
		
		JLabel lblContrasenia = new JLabel("Estado del evento:");
		lblContrasenia.setBounds(35, 215, 123, 14);
		contentPane.add(lblContrasenia);
		
		comboHoraEvento = new JComboBox();
		comboHoraEvento.setBounds(120, 128, 131, 22);
		contentPane.add(comboHoraEvento);
		
		comboEstado = new JComboBox();
		comboEstado.setBounds(150, 209, 131, 22);
		contentPane.add(comboEstado);
		
		btnAgregarCliente = new JButton("Cliente");
		btnAgregarCliente.setForeground(Color.WHITE);
		btnAgregarCliente.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAgregarCliente.setBackground(Color.PINK);
		btnAgregarCliente.setBounds(35, 244, 111, 31);
		contentPane.add(btnAgregarCliente);
		
		txtDni = new JTextField();
		txtDni.setEditable(false);
		txtDni.setColumns(10);
		txtDni.setBounds(195, 250, 116, 20);
		contentPane.add(txtDni);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(160, 245, 72, 31);
		contentPane.add(lblDni);
	}
	
	public void mostrarVentana(){
		this.setVisible(true);
	}
	public JDateChooser getDateFechaEvento() {
		return dateFechaEvento;
	}
	
	public JTextField getTxtDescripcion() {
		return txtDescripcion;
	}
	
	public JTextField getTxtDni() {
		return txtDni;
	}

	public JComboBox<?> getComboEstadoEvento() {
		return comboEstado;
	}
	
	public JComboBox<?> getComboHoraEvento() {
		return comboHoraEvento;
	}
	
	public JButton getBtnRegistrar() {
		return btnRegistrar;
	}
	
	public JButton getBtnCancelar() {
		return btnCancelar;
	}
	
	public JButton getBtnAgregarCliente() {
		return btnAgregarCliente;
	}
	
	public void cerrarVentana(){
		this.dispose();
	}
	
	public void limpiarCampos(){
		this.comboHoraEvento.setSelectedIndex(0);
		this.comboEstado.setSelectedIndex(0);
		this.dateFechaEvento.setDate(null);
		this.txtDescripcion.setText(null);
	}
}