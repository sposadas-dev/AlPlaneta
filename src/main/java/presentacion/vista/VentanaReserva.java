package presentacion.vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Panel;

import javax.swing.JComboBox;

import com.toedter.calendar.JDateChooser;

import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class VentanaReserva extends JFrame {

	private JPanel contentPane;
	private JTextField txtMonto;
	private JButton btnReservar;
	private JButton btnCancelar;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public VentanaReserva() {
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
		lblFechaLlegada.setBounds(334, 82, 107, 14);
		contentPane.add(lblFechaLlegada);
		
		JLabel lblOrigen = new JLabel("Origen:");
		lblOrigen.setBounds(37, 136, 46, 14);
		contentPane.add(lblOrigen);
		
		JLabel lblDestino = new JLabel("Destino:");
		lblDestino.setBounds(334, 136, 46, 14);
		contentPane.add(lblDestino);
		
		JLabel lblTransporte = new JLabel("Medio de transporte:");
		lblTransporte.setBounds(37, 196, 125, 14);
		contentPane.add(lblTransporte);
		
		JLabel lblRangoHorario = new JLabel("Rango de horario:");
		lblRangoHorario.setBounds(37, 253, 113, 14);
		contentPane.add(lblRangoHorario);
		
		JLabel lblMonto = new JLabel("Monto:");
		lblMonto.setBounds(37, 316, 46, 14);
		contentPane.add(lblMonto);
		
		JDateChooser dateChooserFechaSalida = new JDateChooser();
		dateChooserFechaSalida.setBounds(141, 82, 125, 20);
		contentPane.add(dateChooserFechaSalida);
		
		JDateChooser dateChooserFechaLlegada = new JDateChooser();
		dateChooserFechaLlegada.setBounds(448, 82, 125, 20);
		contentPane.add(dateChooserFechaLlegada);
		
		JComboBox comboBoxOrigen = new JComboBox();
		comboBoxOrigen.setBounds(141, 133, 125, 20);
		contentPane.add(comboBoxOrigen);
		
		JComboBox comboBoxDestino = new JComboBox();
		comboBoxDestino.setBounds(411, 133, 121, 20);
		contentPane.add(comboBoxDestino);
		
		JComboBox comboBoxTransporte = new JComboBox();
		comboBoxTransporte.setBounds(168, 193, 125, 20);
		contentPane.add(comboBoxTransporte);
		
		JComboBox comboBoxRangoHorario = new JComboBox();
		comboBoxRangoHorario.setBounds(152, 250, 125, 20);
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
		
		JButton btnCargaPasajeros = new JButton("Carga de pasajeros");
		btnCargaPasajeros.setBounds(356, 181, 176, 44);
		contentPane.add(btnCargaPasajeros);
	}
}
