package presentacion.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import com.toedter.calendar.JDateChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaCargarViaje extends JFrame {

	private JPanel contentPane;

	private JButton btnCrearViaje;
	private static VentanaCargarViaje INSTANCE;
	private JTextField textPrecioViaje;
	private JDateChooser dateChooserFechaOrigen;
	private JDateChooser dateChooserFechaDestino;
	private JComboBox<?> comboBoxCiudadOrigen;
	private JComboBox<?> comboBoxCiudadDestino;
	private JComboBox<?> comboBoxHorarioSalida;
	
	public static VentanaCargarViaje getInstance(){
		if(INSTANCE == null)
			return new VentanaCargarViaje();
		else
			return INSTANCE;
	}
	
	private VentanaCargarViaje() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 596, 272);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnCrearViaje = new JButton("Crear Viaje");
		btnCrearViaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnCrearViaje.setBounds(13, 178, 553, 44); 	
		contentPane.add(btnCrearViaje);
		
		dateChooserFechaOrigen = new JDateChooser();
		dateChooserFechaOrigen.setBounds(121, 63, 153, 20);
		contentPane.add(dateChooserFechaOrigen);
		
		dateChooserFechaDestino = new JDateChooser();
		dateChooserFechaDestino.setBounds(413, 63, 153, 20);
		contentPane.add(dateChooserFechaDestino);
		
		comboBoxCiudadOrigen = new JComboBox();
		comboBoxCiudadOrigen.setBounds(121, 8, 153, 20);
		contentPane.add(comboBoxCiudadOrigen);
		
		JLabel lblOrigen = new JLabel("Ciudad Origen:");
		lblOrigen.setBounds(13, 11, 77, 14);
		contentPane.add(lblOrigen);
		
		JLabel lblDestino = new JLabel("Ciudad Destino :");
		lblDestino.setBounds(295, 11, 68, 14);
		contentPane.add(lblDestino);
		
		comboBoxCiudadDestino = new JComboBox();
		comboBoxCiudadDestino.setBounds(413, 8, 153, 20);
		contentPane.add(comboBoxCiudadDestino);
		
		JLabel lblNewLabel = new JLabel("Precio del Viaje :");
		lblNewLabel.setBounds(295, 128, 100, 14);
		contentPane.add(lblNewLabel);
		
		textPrecioViaje = new JTextField();
		textPrecioViaje.setBounds(415, 125, 151, 20);
		contentPane.add(textPrecioViaje);
		textPrecioViaje.setColumns(10);
		
		JLabel lblFechaSalida = new JLabel("Fecha Salida: ");
		lblFechaSalida.setBounds(13, 63, 77, 14);
		contentPane.add(lblFechaSalida);
		
		JLabel lblNewLabel_1 = new JLabel("Fecha Llegada :");
		lblNewLabel_1.setBounds(284, 63, 119, 20);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblHorarioSalida = new JLabel("Horario Salida :");
		lblHorarioSalida.setBounds(13, 128, 109, 14);
		contentPane.add(lblHorarioSalida);
		
		comboBoxHorarioSalida = new JComboBox<String>();
		comboBoxHorarioSalida.setBounds(121, 125, 153, 20);
		contentPane.add(comboBoxHorarioSalida);
		
		this.setVisible(false);
	}

	public JButton getBtnCrearViaje() {
		return btnCrearViaje;
	}

	public void setBtnCrearViaje(JButton btnCrearViaje) {
		this.btnCrearViaje = btnCrearViaje;
	}


	public JTextField getTextPrecioViaje() {
		return textPrecioViaje;
	}

	public void setTextPrecioViaje(JTextField textPrecioViaje) {
		this.textPrecioViaje = textPrecioViaje;
	}

	public JDateChooser getDateChooserFechaOrigen() {
		return dateChooserFechaOrigen;
	}

	public void setDateChooserFechaOrigen(JDateChooser dateChooserFechaOrigen) {
		this.dateChooserFechaOrigen = dateChooserFechaOrigen;
	}

	public JDateChooser getDateChooserFechaDestino() {
		return dateChooserFechaDestino;
	}

	public void setDateChooserFechaDestino(JDateChooser dateChooserFechaDestino) {
		this.dateChooserFechaDestino = dateChooserFechaDestino;
	}

	public JComboBox<?> getComboBoxCiudadOrigen() {
		return comboBoxCiudadOrigen;
	}

	public void setComboBoxCiudadOrigen(JComboBox<?> comboBoxCiudadOrigen) {
		this.comboBoxCiudadOrigen = comboBoxCiudadOrigen;
	}

	public JComboBox<?> getComboBoxCiudadDestino() {
		return comboBoxCiudadDestino;
	}

	public void setComboBoxCiudadDestino(JComboBox<?> comboBoxCiudadDestino) {
		this.comboBoxCiudadDestino = comboBoxCiudadDestino;
	}

	public JComboBox<?> getComboBoxHorarioSalida() {
		return comboBoxHorarioSalida;
	}

	public void setComboBoxHorarioSalida(JComboBox<?> comboBoxHorarioSalida) {
		this.comboBoxHorarioSalida = comboBoxHorarioSalida;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaCargarViaje frame = new VentanaCargarViaje();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
