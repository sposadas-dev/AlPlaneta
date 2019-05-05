package presentacion.vista;

import java.awt.EventQueue;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;


import javax.swing.JComboBox;

import com.toedter.calendar.JDateChooser;

import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.ListModel;

public class VentanaReserva extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtMonto;
	private JButton btnReservar;
	private JButton btnCancelar;
	private JComboBox<?> comboBoxOrigen;
	private JComboBox<?> comboBoxDestino;
	private JComboBox<?> comboBoxTransporte;
	private JComboBox<?> comboBoxRangoHorario;
	private JButton btnCargaPasajeros;
	private JDateChooser dateChooserFechaSalida;
	private JDateChooser dateChooserFechaLlegada;
	private static VentanaReserva INSTANCE;
	
	private JList<String> listViajesDisponibles;
	private DefaultListModel<String> listModelViajesDisponibles;
	
	
	public static VentanaReserva getInstance(){
		if(INSTANCE == null)
			return new VentanaReserva();
		else
			return INSTANCE;
	}
	
	private VentanaReserva() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 200, 616, 491);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlHighlight);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelReserva = new JPanel();
		panelReserva.setBackground(new Color(230, 126, 34));
		panelReserva.setBounds(0, 0, 610, 53);
		contentPane.add(panelReserva);
		panelReserva.setLayout(null);
		
		JLabel lblAgregarReserva = new JLabel("Agregar reserva");
		lblAgregarReserva.setForeground(Color.WHITE);
		lblAgregarReserva.setBounds(26, 0, 210, 53);
		lblAgregarReserva.setFont(new Font("Tahoma", Font.BOLD, 24));
		panelReserva.add(lblAgregarReserva);
		
		JLabel lblFechaSalida = new JLabel("Fecha de salida:");
		lblFechaSalida.setBounds(37, 82, 94, 14);
		contentPane.add(lblFechaSalida);
		
		JLabel lblFechaLlegada = new JLabel("Fecha de llegada:");
		lblFechaLlegada.setBounds(347, 82, 107, 14);
		contentPane.add(lblFechaLlegada);
		
		JLabel lblOrigen = new JLabel("Origen:");
		lblOrigen.setBounds(37, 136, 46, 14);
		contentPane.add(lblOrigen);
		
		JLabel lblDestino = new JLabel("Destino:");
		lblDestino.setBounds(357, 136, 46, 14);
		contentPane.add(lblDestino);
		
		JLabel lblTransporte = new JLabel("Medio de transporte:");
		lblTransporte.setBounds(6, 196, 125, 14);
		contentPane.add(lblTransporte);
		
		JLabel lblRangoHorario = new JLabel("Rango de horario:");
		lblRangoHorario.setBounds(37, 253, 113, 14);
		contentPane.add(lblRangoHorario);
		
		JLabel lblMonto = new JLabel("Monto:");
		lblMonto.setBounds(37, 316, 46, 14);
		contentPane.add(lblMonto);
		
		dateChooserFechaSalida = new JDateChooser();
		dateChooserFechaSalida.setBounds(141, 82, 125, 20);
		contentPane.add(dateChooserFechaSalida);
		
		dateChooserFechaLlegada = new JDateChooser();
		dateChooserFechaLlegada.setBounds(448, 82, 125, 20);
		contentPane.add(dateChooserFechaLlegada);
		
		comboBoxOrigen = new JComboBox();
		comboBoxOrigen.setBounds(141, 133, 125, 20);
		contentPane.add(comboBoxOrigen);
		
		comboBoxDestino = new JComboBox();
		comboBoxDestino.setBounds(452, 133, 121, 20);
		contentPane.add(comboBoxDestino);
		
		comboBoxTransporte = new JComboBox();
		comboBoxTransporte.setBounds(141, 193, 125, 20);
		contentPane.add(comboBoxTransporte);
		
		comboBoxRangoHorario = new JComboBox();
		comboBoxRangoHorario.setBounds(141, 250, 125, 20);
		contentPane.add(comboBoxRangoHorario);
		
		txtMonto = new JTextField();
		txtMonto.setBounds(141, 313, 117, 20);
		contentPane.add(txtMonto);
		txtMonto.setColumns(10);
		
		JSeparator separadorReserva = new JSeparator();
		separadorReserva.setBounds(24, 356, 549, 20);
		contentPane.add(separadorReserva);
		
		btnReservar = new JButton("Reservar");
		btnReservar.setForeground(Color.WHITE);
		btnReservar.setBackground(new Color(52, 152, 219));
		btnReservar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnReservar.setBounds(152, 375, 131, 42);
		contentPane.add(btnReservar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setBackground(new Color(192, 57, 43));
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCancelar.setBounds(323, 375, 131, 42);
		contentPane.add(btnCancelar);
		
		btnCargaPasajeros = new JButton("Carga de pasajeros");
		btnCargaPasajeros.setBounds(356, 181, 217, 44);
		contentPane.add(btnCargaPasajeros);
		
		JScrollPane scrollPaneEstilosDisponibles = new JScrollPane();
		scrollPaneEstilosDisponibles.setBounds(360, 250, 213, 90);
		contentPane.add(scrollPaneEstilosDisponibles);
		
		listModelViajesDisponibles = new DefaultListModel<String>();
		listViajesDisponibles = new JList<String>((listModelViajesDisponibles));
		listViajesDisponibles.setModel(listModelViajesDisponibles);
		
	}

	public void mostrarVentana(boolean visibilidad){
		this.setVisible(visibilidad);
	}
	
	public JTextField getTxtMonto() {
		return txtMonto;
	}

	public void setTxtMonto(JTextField txtMonto) {
		this.txtMonto = txtMonto;
	}

	public JButton getBtnReservar() {
		return btnReservar;
	}

	public void setBtnReservar(JButton btnReservar) {
		this.btnReservar = btnReservar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public JComboBox<?> getComboBoxOrigen() {
		return comboBoxOrigen;
	}

	public void setComboBoxOrigen(JComboBox<?> comboBoxOrigen) {
		this.comboBoxOrigen = comboBoxOrigen;
	}

	public JComboBox<?> getComboBoxDestino() {
		return comboBoxDestino;
	}

	public void setComboBoxDestino(JComboBox<?> comboBoxDestino) {
		this.comboBoxDestino = comboBoxDestino;
	}

	public JComboBox<?> getComboBoxTransporte() {
		return comboBoxTransporte;
	}

	public void setComboBoxTransporte(JComboBox<?> comboBoxTransporte) {
		this.comboBoxTransporte = comboBoxTransporte;
	}

	public JComboBox<?> getComboBoxRangoHorario() {
		return comboBoxRangoHorario;
	}

	public void setComboBoxRangoHorario(JComboBox<?> comboBoxRangoHorario) {
		this.comboBoxRangoHorario = comboBoxRangoHorario;
	}

	public JButton getBtnCargaPasajeros() {
		return btnCargaPasajeros;
	}

	public void setBtnCargaPasajeros(JButton btnCargaPasajeros) {
		this.btnCargaPasajeros = btnCargaPasajeros;
	}

	public JDateChooser getDateChooserFechaSalida() {
		return dateChooserFechaSalida;
	}

	public void setDateChooserFechaSalida(JDateChooser dateChooserFechaSalida) {
		this.dateChooserFechaSalida = dateChooserFechaSalida;
	}

	public JDateChooser getDateChooserFechaLlegada() {
		return dateChooserFechaLlegada;
	}

	public void setDateChooserFechaLlegada(JDateChooser dateChooserFechaLlegada) {
		this.dateChooserFechaLlegada = dateChooserFechaLlegada;
	}
	
	
	
	public JList<String> getListViajesDisponibles() {
		return listViajesDisponibles;
	}

	public void setListViajesDisponibles(JList<String> listViajesDisponibles) {
		this.listViajesDisponibles = listViajesDisponibles;
	}

	public DefaultListModel<String> getListModelViajesDisponibles() {
		return listModelViajesDisponibles;
	}

	public void setListModelViajesDisponibles(DefaultListModel<String> listModelViajesDisponibles) {
		this.listModelViajesDisponibles = listModelViajesDisponibles;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaReserva frame = new VentanaReserva();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

