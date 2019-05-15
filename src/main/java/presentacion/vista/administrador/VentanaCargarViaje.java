package presentacion.vista.administrador;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VentanaCargarViaje extends JFrame {

	private JPanel contentPane;

	private JButton btnCrearViaje;
	private static VentanaCargarViaje INSTANCE;
	private JTable tablaViajes;
	private JTextField textPrecioViaje;
	private JDateChooser dateChooserFechaOrigen;
	private JDateChooser dateChooserFechaDestino;
	private JComboBox<?> comboBoxHorarioSalida;
	
	private JComboBox<?> comboBoxCiudadOrigen;
	private JComboBox<?> comboBoxCiudadDestino;
	private JComboBox<?> comboBoxPaisOrigen;
	private JComboBox<?> comboBoxPaisDestino;
	private JComboBox<?> comboBoxProvinciaOrigen;
	private JComboBox<?> comboBoxProvinciaDestino;
	private JButton btnSeleccionDestino;
	private JButton btnSeleccionOrigen;
	
	private DefaultTableModel modelViajes;
	private  String[] nombreColumnas = {"Origen","Destino"};
	
	private JLabel lblProvinciaOrigen;
	private JLabel lblCiudadOrigen;
	
	public static VentanaCargarViaje getInstance(){
		if(INSTANCE == null)
			return new VentanaCargarViaje();
		else
			return INSTANCE;
	}
	
	private VentanaCargarViaje() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 150, 596, 495);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane spPasajeros = new JScrollPane();
		spPasajeros.setBounds(13, 274, 553, 140);
		contentPane.add(spPasajeros);
		
		modelViajes = new DefaultTableModel(null,nombreColumnas){
			@Override
				public boolean isCellEditable(int row, int column){
				return false;
				}
			};
		tablaViajes = new JTable(modelViajes);
		spPasajeros.setViewportView(tablaViajes);
		
		
		btnCrearViaje = new JButton("Crear Viaje");
		btnCrearViaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnCrearViaje.setBounds(376, 425, 190, 20); 	
		contentPane.add(btnCrearViaje);
		
		dateChooserFechaOrigen = new JDateChooser();
		dateChooserFechaOrigen.setBounds(121, 63, 153, 20);
		contentPane.add(dateChooserFechaOrigen);
		
		dateChooserFechaDestino = new JDateChooser();
		dateChooserFechaDestino.setBounds(413, 63, 153, 20);
		contentPane.add(dateChooserFechaDestino);
		
		comboBoxCiudadOrigen = new JComboBox();
		comboBoxCiudadOrigen.setBounds(250, 180, 113, 20);
		contentPane.add(comboBoxCiudadOrigen);
		
		JLabel lblOrigen = new JLabel("Ciudad Origen:");
		lblOrigen.setBounds(13, 11, 77, 14);
		contentPane.add(lblOrigen);
		
		JLabel lblDestino = new JLabel("Ciudad Destino :");
		lblDestino.setBounds(295, 11, 68, 14);
		contentPane.add(lblDestino);
		
		comboBoxCiudadDestino = new JComboBox();
		comboBoxCiudadDestino.setBounds(250, 243, 113, 20);
		contentPane.add(comboBoxCiudadDestino);
		
		JLabel lblNewLabel = new JLabel("Precio del Viaje :");
		lblNewLabel.setBounds(284, 97, 100, 14);
		contentPane.add(lblNewLabel);
		
		textPrecioViaje = new JTextField();
		textPrecioViaje.setBounds(413, 94, 151, 20);
		contentPane.add(textPrecioViaje);
		textPrecioViaje.setColumns(10);
		
		JLabel lblFechaSalida = new JLabel("Fecha Salida: ");
		lblFechaSalida.setBounds(13, 63, 77, 14);
		contentPane.add(lblFechaSalida);
		
		JLabel lblNewLabel_1 = new JLabel("Fecha Llegada :");
		lblNewLabel_1.setBounds(284, 63, 119, 20);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblHorarioSalida = new JLabel("Horario Salida :");
		lblHorarioSalida.setBounds(13, 88, 109, 14);
		contentPane.add(lblHorarioSalida);
		
		comboBoxHorarioSalida = new JComboBox<String>();
		comboBoxHorarioSalida.setBounds(121, 94, 153, 20);
		contentPane.add(comboBoxHorarioSalida);
		
		comboBoxPaisOrigen = new JComboBox();
		comboBoxPaisOrigen.setBounds(13, 180, 113, 20);
		contentPane.add(comboBoxPaisOrigen);
		
		JLabel lblPaisOrigen = new JLabel("Pais Origen");
		lblPaisOrigen.setBounds(13, 155, 98, 14);
		contentPane.add(lblPaisOrigen);
		
		comboBoxProvinciaOrigen = new JComboBox();
		comboBoxProvinciaOrigen.setBounds(132, 180, 113, 20);
		contentPane.add(comboBoxProvinciaOrigen);
		
		lblProvinciaOrigen = new JLabel("Provincia Origen");
		lblProvinciaOrigen.setBounds(131, 156, 98, 14);
		contentPane.add(lblProvinciaOrigen);
		
		lblCiudadOrigen = new JLabel("Ciudad Origen");
		lblCiudadOrigen.setBounds(250, 153, 98, 14);
		contentPane.add(lblCiudadOrigen);
		
		btnSeleccionOrigen = new JButton("Seleccion Origen");
		btnSeleccionOrigen.setBounds(376, 179, 188, 23);
		contentPane.add(btnSeleccionOrigen);
		
		JLabel lblPaisDestino = new JLabel("Pais Destino");
		lblPaisDestino.setBounds(13, 217, 98, 14);
		contentPane.add(lblPaisDestino);
		
		comboBoxPaisDestino = new JComboBox();
		comboBoxPaisDestino.setBounds(13, 242, 113, 20);
		contentPane.add(comboBoxPaisDestino);
		
		comboBoxProvinciaDestino = new JComboBox();
		comboBoxProvinciaDestino.setBounds(132, 242, 113, 20);
		contentPane.add(comboBoxProvinciaDestino);
		
		btnSeleccionDestino = new JButton("Seleccion Destino");
		btnSeleccionDestino.setBounds(373, 240, 188, 23);
		contentPane.add(btnSeleccionDestino);
		
		inicializar();
		this.setVisible(false);
	}
	private void inicializar(){
		/*REDIMENCIONAR LA VENTANA PARA LUEGO DE CARGAR VALORES SE MUESTRE LA TABLA*/
		
		this.comboBoxCiudadOrigen.setEnabled(false);
		this.comboBoxCiudadDestino.setEnabled(false);
		
		this.comboBoxProvinciaOrigen.setEnabled(false);
		this.comboBoxProvinciaDestino.setEnabled(false);
		
		this.btnSeleccionOrigen.setEnabled(false);
		this.btnSeleccionDestino.setEnabled(false);
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

	
	public JTable getTablaViajes() {
		return tablaViajes;
	}

	public void setTablaViajes(JTable tablaViajes) {
		this.tablaViajes = tablaViajes;
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

	public JButton getBtnSeleccionDestino() {
		return btnSeleccionDestino;
	}

	public void setBtnSeleccionDestino(JButton btnSeleccionDestino) {
		this.btnSeleccionDestino = btnSeleccionDestino;
	}

	public JButton getBtnSeleccionOrigen() {
		return btnSeleccionOrigen;
	}

	public void setBtnSeleccionOrigen(JButton btnSeleccionOrigen) {
		this.btnSeleccionOrigen = btnSeleccionOrigen;
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
