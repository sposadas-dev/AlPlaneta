package presentacion.vista.administrador;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JRadioButton;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import com.toedter.calendar.JDateChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VentanaCargarViaje extends JFrame {

	private JPanel contentPane;

	private JButton btnCrearViaje;
	private static VentanaCargarViaje INSTANCE;
	private JTextField textPrecioViaje;
	private JDateChooser dateChooserFechaOrigen;
	private JComboBox<?> comboBoxHorarioSalida;
	
	private JComboBox<?> comboBoxCiudadOrigen;
	private JComboBox<?> comboBoxCiudadDestino;
	private JComboBox<?> comboBoxPaisOrigen;
	private JComboBox<?> comboBoxPaisDestino;
	private JComboBox<?> comboBoxProvinciaOrigen;
	private JComboBox<?> comboBoxProvinciaDestino;
	private JComboBox<?> comboBoxTransporte;


	private JButton btnOK;
	
	private DefaultTableModel modelViajes;
	private  String[] nombreColumnas = {"Origen","Destino"};
	
	private JLabel lblProvinciaOrigen;
	private JLabel lblCiudadOrigen;
	private JTextField textCapacidad;
	private JLabel lblHorasEstimadas;
	private JTextField textHorasEstimadas;
	private JLabel lblHs;
	private JLabel lblErrorOrigenDestino;
	private JTextField textFechaDestino;
	
	public static VentanaCargarViaje getInstance(){
		if(INSTANCE == null)
			return new VentanaCargarViaje();
		else
			return INSTANCE;
	}
	
	private VentanaCargarViaje() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 150, 871, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JLabel lblAgregarReserva = new JLabel("Cargar viajee");
		lblAgregarReserva.setForeground(Color.WHITE);
		lblAgregarReserva.setBounds(26, 0, 210, 53);
		lblAgregarReserva.setFont(new Font("Tahoma", Font.BOLD, 24));
		contentPane.add(lblAgregarReserva);
		
		JPanel panelReserva = new JPanel();
		panelReserva.setBackground(new Color(63, 72, 204));
		panelReserva.setBounds(0, 0, 867, 53);
		contentPane.add(panelReserva);
		panelReserva.setLayout(null);
		
		modelViajes = new DefaultTableModel(null,nombreColumnas){
			@Override
				public boolean isCellEditable(int row, int column){
				return false;
				}
			};
		
		
		btnCrearViaje = new JButton("Cargar Viaje");
		btnCrearViaje.setEnabled(false);
		btnCrearViaje.setBounds(345, 307, 173, 37); 	
		contentPane.add(btnCrearViaje);
		
		dateChooserFechaOrigen = new JDateChooser();
		dateChooserFechaOrigen.setBounds(98, 72, 153, 20);
		contentPane.add(dateChooserFechaOrigen);
		
		comboBoxCiudadOrigen = new JComboBox();
		comboBoxCiudadOrigen.setBounds(446, 176, 190, 20);
		contentPane.add(comboBoxCiudadOrigen);
		
		comboBoxCiudadDestino = new JComboBox();
		comboBoxCiudadDestino.setBounds(446, 239, 190, 20);
		contentPane.add(comboBoxCiudadDestino);
		
		JLabel lblNewLabel = new JLabel("Precio del Viaje:");
		lblNewLabel.setBounds(503, 103, 100, 14);
		contentPane.add(lblNewLabel);
		
		textPrecioViaje = new JTextField();
		textPrecioViaje.setBounds(601, 100, 153, 20);
		contentPane.add(textPrecioViaje);
		textPrecioViaje.setColumns(10);
		
		JLabel lblFechaSalida = new JLabel("Fecha Salida: ");
		lblFechaSalida.setBounds(10, 72, 96, 14);
		contentPane.add(lblFechaSalida);
		
		JLabel lblNewLabel_1 = new JLabel("Fecha Llegada:");
		lblNewLabel_1.setBounds(510, 69, 100, 20);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblHorarioSalida = new JLabel("Horario Salida:");
		lblHorarioSalida.setBounds(10, 106, 109, 14);
		contentPane.add(lblHorarioSalida);
		
		comboBoxHorarioSalida = new JComboBox<String>();
		comboBoxHorarioSalida.setBounds(98, 103, 153, 20);
		contentPane.add(comboBoxHorarioSalida);
		
		comboBoxPaisOrigen = new JComboBox();
		comboBoxPaisOrigen.setBounds(13, 177, 190, 20);
		contentPane.add(comboBoxPaisOrigen);
		
		JLabel lblPaisOrigen = new JLabel("Pais Origen");
		lblPaisOrigen.setBounds(13, 152, 98, 14);
		contentPane.add(lblPaisOrigen);
		
		comboBoxProvinciaOrigen = new JComboBox();
		comboBoxProvinciaOrigen.setBounds(228, 176, 190, 20);
		contentPane.add(comboBoxProvinciaOrigen);
		
		lblProvinciaOrigen = new JLabel("Provincia Origen");
		lblProvinciaOrigen.setBounds(227, 152, 98, 14);
		contentPane.add(lblProvinciaOrigen);
		
		lblCiudadOrigen = new JLabel("Ciudad Origen");
		lblCiudadOrigen.setBounds(446, 149, 98, 14);
		contentPane.add(lblCiudadOrigen);
		
		btnOK = new JButton("OK");
		btnOK.setBounds(764, 232, 88, 29);
		contentPane.add(btnOK);
		
		JLabel lblPaisDestino = new JLabel("Pais Destino");
		lblPaisDestino.setBounds(13, 214, 98, 14);
		contentPane.add(lblPaisDestino);
		
		comboBoxPaisDestino = new JComboBox();
		comboBoxPaisDestino.setBounds(13, 239, 190, 20);
		contentPane.add(comboBoxPaisDestino);
		
		comboBoxProvinciaDestino = new JComboBox();
		comboBoxProvinciaDestino.setBounds(228, 238, 190, 20);
		contentPane.add(comboBoxProvinciaDestino);
		
		JLabel lblProvinciaDestino = new JLabel("Provincia Destino");
		lblProvinciaDestino.setBounds(227, 213, 98, 14);
		contentPane.add(lblProvinciaDestino);
		
		JLabel lblCiudadDestino = new JLabel("Ciudad Destino");
		lblCiudadDestino.setBounds(446, 214, 98, 14);
		contentPane.add(lblCiudadDestino);
		
		JLabel lblTransporte = new JLabel("Transporte");
		lblTransporte.setBounds(662, 151, 98, 14);
		contentPane.add(lblTransporte);
		
		comboBoxTransporte = new JComboBox();
		comboBoxTransporte.setBounds(662, 175, 190, 20);
		contentPane.add(comboBoxTransporte);
		
		JLabel lblCapacidad = new JLabel("Capacidad");
		lblCapacidad.setBounds(662, 213, 98, 14);
		contentPane.add(lblCapacidad);
		
		textCapacidad = new JTextField();
		textCapacidad.setColumns(10);
		textCapacidad.setBounds(662, 238, 92, 20);
		contentPane.add(textCapacidad);
		
		lblHorasEstimadas = new JLabel("Horas estimadas:");
		lblHorasEstimadas.setBounds(275, 72, 100, 20);
		contentPane.add(lblHorasEstimadas);
		
		textHorasEstimadas = new JTextField();
		textHorasEstimadas.setColumns(10);
		textHorasEstimadas.setBounds(379, 72, 74, 20);
		contentPane.add(textHorasEstimadas);
		
		lblHs = new JLabel("hs.");
		lblHs.setBounds(456, 72, 27, 20);
		contentPane.add(lblHs);
		
		lblErrorOrigenDestino = new JLabel("");
		lblErrorOrigenDestino.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblErrorOrigenDestino.setForeground(Color.RED);
		lblErrorOrigenDestino.setBounds(13, 272, 840, 14);
		contentPane.add(lblErrorOrigenDestino);
		
		textFechaDestino = new JTextField();
		textFechaDestino.setEditable(false);
		textFechaDestino.setColumns(10);
		textFechaDestino.setBounds(601, 66, 153, 20);
		contentPane.add(textFechaDestino);
		
		inicializar();
		this.setVisible(false);
	}
	public JTextField getTextHorasEstimadas() {
		return textHorasEstimadas;
	}

	public void setTextHorasEstimadas(JTextField textHorasEstimadas) {
		this.textHorasEstimadas = textHorasEstimadas;
	}

	public JLabel getLblProvinciaOrigen() {
		return lblProvinciaOrigen;
	}

	public void setLblProvinciaOrigen(JLabel lblProvinciaOrigen) {
		this.lblProvinciaOrigen = lblProvinciaOrigen;
	}
	
	public JLabel getLblErrorOrigenDestino() {
		return lblErrorOrigenDestino;
	}

	public JLabel getLblCiudadOrigen() {
		return lblCiudadOrigen;
	}

	public void setLblCiudadOrigen(JLabel lblCiudadOrigen) {
		this.lblCiudadOrigen = lblCiudadOrigen;
	}

	public JTextField getTextCapacidad() {
		return textCapacidad;
	}

	public void setTextCapacidad(JTextField textCapacidad) {
		this.textCapacidad = textCapacidad;
	}

	private void inicializar(){
		/*REDIMENCIONAR LA VENTANA PARA LUEGO DE CARGAR VALORES SE MUESTRE LA TABLA*/
		
		this.comboBoxCiudadOrigen.setEnabled(false);
		this.comboBoxCiudadDestino.setEnabled(false);
		
		this.comboBoxProvinciaOrigen.setEnabled(false);
		this.comboBoxProvinciaDestino.setEnabled(false);
		
		this.btnOK.setEnabled(false);
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

	public JTextField getTxtFechaDestino() {
		return textFechaDestino;
	}

	public void setTxtFechaDestino(JTextField textFechaDestino) {
		this.textFechaDestino = textFechaDestino;
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

	public DefaultTableModel getModelViajes() {
		return modelViajes;
	}

	public void setModelViajes(DefaultTableModel modelViajes) {
		this.modelViajes = modelViajes;
	}
	
	public JComboBox<?> getComboBoxPaisOrigen() {
		return comboBoxPaisOrigen;
	}

	public void setComboBoxPaisOrigen(JComboBox<?> comboBoxPaisOrigen) {
		this.comboBoxPaisOrigen = comboBoxPaisOrigen;
	}

	public JComboBox<?> getComboBoxPaisDestino() {
		return comboBoxPaisDestino;
	}

	public void setComboBoxPaisDestino(JComboBox<?> comboBoxPaisDestino) {
		this.comboBoxPaisDestino = comboBoxPaisDestino;
	}

	public JComboBox<?> getComboBoxProvinciaOrigen() {
		return comboBoxProvinciaOrigen;
	}

	public void setComboBoxProvinciaOrigen(JComboBox<?> comboBoxProvinciaOrigen) {
		this.comboBoxProvinciaOrigen = comboBoxProvinciaOrigen;
	}

	public JComboBox<?> getComboBoxProvinciaDestino() {
		return comboBoxProvinciaDestino;
	}

	public void setComboBoxProvinciaDestino(JComboBox<?> comboBoxProvinciaDestino) {
		this.comboBoxProvinciaDestino = comboBoxProvinciaDestino;
	}


	public JButton getBtnOK() {
		return btnOK;
	}

	public void setBtnOK(JButton btnSeleccionOrigen) {
		this.btnOK = btnSeleccionOrigen;
	}
	
	public JComboBox<?> getComboBoxTransporte() {
		return comboBoxTransporte;
	}

	public void setComboBoxTransporte(JComboBox<?> comboBoxTransporte) {
		this.comboBoxTransporte = comboBoxTransporte;
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
