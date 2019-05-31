package presentacion.vista.administrativo;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

public class VentanaRegistrarEvento extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JDateChooser dateFechaEvento;
	private JComboBox<String> comboHora;
	private JComboBox<String> comboEstado;
	private JComboBox<String> comboMinutos;
	private JTextField txtDescripcion;
	private JTextField txtDni;
	private JButton btnRegistrar;
	private JButton btnCancelar;
	private JButton btnAgregarCliente;
	private JLabel label;

	private static VentanaRegistrarEvento ventanaCliente;
	
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
		panelCliente.setBackground(new Color(66, 99, 145));
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
		
		comboHora = new JComboBox<String>();
		comboHora.setBounds(120, 128, 40, 22);
		contentPane.add(comboHora);
		comboHora.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation()>0)
					if(comboHora.getSelectedIndex() < comboHora.getItemCount()-1) 
						comboHora.setSelectedIndex(comboHora.getSelectedIndex()+1);
				if(e.getWheelRotation()<0)
					if(comboHora.getSelectedIndex() > 0)
						comboHora.setSelectedIndex(comboHora.getSelectedIndex()-1);
			}
		});
		
		comboMinutos = new JComboBox<String>();
		comboMinutos.setBounds(172, 128, 40, 22);
		contentPane.add(comboMinutos);
		comboMinutos.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation()>0)
					if(comboMinutos.getSelectedIndex() < comboMinutos.getItemCount()-1) 
						comboMinutos.setSelectedIndex(comboMinutos.getSelectedIndex()+1);
				if(e.getWheelRotation()<0)
					if(comboMinutos.getSelectedIndex() > 0)
						comboMinutos.setSelectedIndex(comboMinutos.getSelectedIndex()-1);
			}
		});
		
		comboEstado = new JComboBox<String>();
		comboEstado.setBounds(150, 209, 131, 22);
		contentPane.add(comboEstado);
		
		btnAgregarCliente = new JButton("Cliente");
		btnAgregarCliente.setFont(new Font("Tahoma", Font.BOLD, 14));
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
		
		label = new JLabel(":");
		label.setBounds(164, 132, 10, 14);
		contentPane.add(label);
	}
	
	public void seleccionarElementodeAbajo(){
		comboHora.setSelectedIndex(comboHora.getSelectedIndex()+1);
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

	public JComboBox<String> getComboEstadoEvento() {
		return comboEstado;
	}
	
	public JComboBox<String> getComboHora() {
		return comboHora;
	}
	
	public JComboBox<String> getComboMinutos() {
		return comboMinutos;
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
		this.comboHora.setSelectedIndex(0);
		this.comboEstado.setSelectedIndex(0);
		this.dateFechaEvento.setDate(null);
		this.txtDescripcion.setText(null);
	}
}